/* MachinePlayer.java */

package player;

import player.list.*;

import java.lang.System;

/**
 *  An implementation of an automatic Network player.  Keeps track of moves
 *  made by both players.  Can select a move for itself.
 */
public class MachinePlayer extends Player {
    int recursionDepthCounter = 0;

    int color;
    int opponentColor;
    int searchDepth;
    protected GameBoard g;

  // Creates a machine player with the given color.  Color is either 0 (black)
  // or 1 (white).  (White has the first move.)
  public MachinePlayer(int color) {
      this.color = color;
      if (color == 1) {
          opponentColor = 0;
      } else {
          opponentColor = 1;
      }
      searchDepth = 3; // will change this later
      g = new GameBoard(color, 0, 0);
  }

  // Creates a machine player with the given color and search depth.  Color is
  // either 0 (black) or 1 (white).  (White has the first move.)
  public MachinePlayer(int color, int searchDepth) {
      this.color = color;
      if (color == 1) {
          opponentColor = 0;
      } else {
          opponentColor = 1;
      }
      this.searchDepth = searchDepth;
      g = new GameBoard(color, 0, 0);
  }

  // Returns a new move by "this" player.  Internally records the move (updates
  // the internal game board) as a move by "this" player.
  public Move chooseMove() {
      List list = g.listOfValidMoves(); // From the internal GameBoard, gets a list of possible moves player can make
      try {
          if (list.front().isValidNode()) {
              Move ourMove = (Move) list.front().item(); // chooses move based on first valid move possible on list
              g.recordMoveOnBoard(ourMove, color);
              return ourMove;
          }

      } catch (InvalidNodeException e) {
          System.err.println(e);
      }

      return new Move();
  } 

  // If the Move m is legal, records the move as a move by the opponent
  // (updates the internal game board) and returns true.  If the move is
  // illegal, returns false without modifying the internal state of "this"
  // player.  This method allows your opponents to inform you of their moves.
  public boolean opponentMove(Move m) {

      if (g.isValidMove(m, opponentColor)) {
          g.recordMoveOnBoard(m, opponentColor);
          return true;
      }
      return false;
  }

  // If the Move m is legal, records the move as a move by "this" player
  // (updates the internal game board) and returns true.  If the move is
  // illegal, returns false without modifying the internal state of "this"
  // player.  This method is used to help set up "Network problems" for your
  // player to solve.
  public boolean forceMove(Move m) {
      if (g.isValidMove(m, color)) {
          g.recordMoveOnBoard(m, color);
          return true;
      } else {
          return false;
      }
  }

    public void undoMove(Move m) {
        ListNode node = g.chips_currently_on_board.front();
        for (int i = 0; i < g.chips_currently_on_board.length(); i++) {
            try {
                if (((GameChip) node.item()).xPosition() == m.x1 && ((GameChip) node.item()).yPosition() == m.y1) {
                    node.remove();
                    break;
                }
                node = node.next();
            } catch (InvalidNodeException e) {
                System.err.println(e);
            }
        }
    }

    /**
     * This method assigns a score to a particular GameBoard giving the likelihood of winning the game from
     * this point onward.
     * @param g is the GameBoard object we are evaluating with the evaluation function.
     * @return a double that indicates how good the chances are of winning. The more positive the number, the
     *         better the chances are of winning.
     **/
    public double evaluationFunction(GameBoard g) {
        if (g.networkFound(color)) {
            return 20.00;
        } else if (g.networkFound(opponentColor)) {
            return -20.00;
        } else {
            double ourScore = 0.00;
            double opponentScore = 0.00;
            try {
                ListNode node = g.chips_currently_on_board.front();

                for (int i = 0; i < g.chips_currently_on_board.length(); i++) {
                    if (((GameChip) node.item()).color() == color) {
                        ourScore += g.listOfConnectedChips((GameChip) node.item(), 0).length();
                    } else {
                        opponentScore += g.listOfConnectedChips((GameChip) node.item(), 0).length();
                    }
                    if (color == 1 && (((GameChip) node.item()).xPosition() == 0 | ((GameChip) node.item()).xPosition()
                            == 7)) {
                        ourScore++;
                    } else if (color == 0 && (((GameChip) node.item()).yPosition() == 0 |
                            ((GameChip) node.item()).yPosition() == 7)) {
                        ourScore++;
                    } else if (opponentColor == 1 && (((GameChip) node.item()).xPosition() == 0 |
                            ((GameChip) node.item()).xPosition() == 7)) {
                        opponentScore++;
                    } else if (opponentColor == 0 && (((GameChip) node.item()).yPosition() == 0 |
                            ((GameChip) node.item()).yPosition() == 7)) {
                        opponentScore++;
                    }
                    node = node.next();
                }
            } catch (InvalidNodeException e) {
                System.err.println(e);
            }
            return ourScore - opponentScore;
        }
    }

    /**
     * This algorithm recursively searches through a GameBoard, many moves down the line (specified by
     * MachinePlayer's searchDepth parameter) to return to you the move with the greatest likelihood of winning.
     * @param g is the GameBoard upon which you are implementing the tree search to see which move the computer
     *          should make next in order to increase its odds of winning.
     * @return a Move object that the MachinePlayer will use as its next move.
     **/
    public Best minimaxTreeSearch(GameBoard g, int side, double alpha, double beta) {

        Best myBest = new Best();
        Best reply;
        if (g.networkFound(0) | g.networkFound(1)) {
            myBest.score = evaluationFunction(g);
            return myBest;
        }
        if (color == side) {
            myBest.score = alpha;
        } else {
            myBest.score = beta;
        }
        try {
            myBest.move = (Move) g.listOfValidMoves().front().item();
            ListNode node = g.listOfValidMoves().front();
            while (recursionDepthCounter <= searchDepth) {
                for (int i = 0; i < g.listOfValidMoves().length(); i++) {
                    forceMove((Move) node.item());
                    recursionDepthCounter++;
                    reply = minimaxTreeSearch(g, opponentColor, alpha, beta); // how do we incorporate recursive depth here?
                    undoMove((Move) node.item());
                    if (side == color && reply.score > myBest.score) {
                        myBest.move = (Move) node.item();
                        myBest.score = reply.score;
                        alpha = reply.score;
                    } else if (side == opponentColor && reply.score < myBest.score) {
                        myBest.move = (Move) node.item();
                        myBest.score = reply.score;
                        beta = reply.score;
                    }
                    if (alpha >= beta) {
                        return myBest;
                    }

                    node = node.next();
                }
            }
        } catch (InvalidNodeException e) {
            System.err.println(e);
        }
        recursionDepthCounter = 0;
        return myBest;
    }

    public static void main(String[] args) {
        MachinePlayer mp = new MachinePlayer(0);
        Move m = new Move(3, 6);
        Move m1 = new Move(5, 5);
        Move m2 = new Move(2, 4);
        mp.g.recordMoveOnBoard(m, 0);
        mp.g.recordMoveOnBoard(m1, 1);
        mp.g.recordMoveOnBoard(m2, 0);
        System.out.println("Adding 3 moves to a GameBoard");
        System.out.println(mp.g.chips_currently_on_board.toString());
        mp.undoMove(m1);
        System.out.println("undoMove the second move from the GameBoard");
        System.out.println(mp.g.chips_currently_on_board.toString());
        System.out.println("undoMove the first move from the GameBoard");
        mp.undoMove(m);
        System.out.println(mp.g.chips_currently_on_board.toString());

        mp.undoMove(m2);
        mp.g.recordMoveOnBoard(m, 0);
        mp.g.recordMoveOnBoard(m1, 1);
        mp.g.recordMoveOnBoard(m2, 0);
        System.out.println("Adding back removed moves to GameBoard");
        System.out.println(mp.g.chips_currently_on_board.toString());
        //System.out.println(mp.minimaxTreeSearch(mp.g, mp.color, -1, 1).toString());

        System.out.println(mp.evaluationFunction(mp.g));

        Move m3 = new Move(6,6);
        mp.g.recordMoveOnBoard(m3, 1);
        System.out.println(mp.evaluationFunction(mp.g));
        System.out.println(mp.g.networkFound(0));
        System.out.println(mp.g.networkFound(1));

        MachinePlayer mp2 = new MachinePlayer(0);
        Move m4 = new Move(6, 0);
        Move m5 = new Move(6, 5);
        Move m6 = new Move(5, 5);
        Move m7 = new Move(3, 3);
        Move m8 = new Move(3, 5);
        Move m9 = new Move(5, 7);
        mp2.g.recordMoveOnBoard(m4, 0);
        mp2.g.recordMoveOnBoard(m5, 0);
        mp2.g.recordMoveOnBoard(m6, 0);
        mp2.g.recordMoveOnBoard(m7, 0);
        mp2.g.recordMoveOnBoard(m8, 0);
        mp2.g.recordMoveOnBoard(m9, 0);
        System.out.println(mp2.g.networkFound(0));

        mp = new MachinePlayer(0);
        mp.g.recordMoveOnBoard(m4, 1);
        mp.g.recordMoveOnBoard(m5, 1);
        mp.g.recordMoveOnBoard(m6, 1);
        mp.g.recordMoveOnBoard(m7, 1);
        mp.g.recordMoveOnBoard(m8, 1);
        mp.g.recordMoveOnBoard(m9, 1);
        System.out.println(mp.g.networkFound(0));

    }


}


















