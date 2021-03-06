/* MachinePlayer.java */

package player;

import player.list.*;

import java.lang.System;

/**
 *  An implementation of an automatic Network player.  Keeps track of moves
 *  made by both players.  Can select a move for itself.
 */
public class MachinePlayer extends Player {

    int color;
    int opponentColor;
    int searchDepth;
    protected GameBoard g;

    public static final double MACHINE_WIN = 20.00;
    public static final double HUMAN_WIN = -20.00;

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
      Move ourMove = minimaxTreeSearch(g, color, HUMAN_WIN, MACHINE_WIN, 0).move;
      if (g.isValidMove(ourMove, color)) {
          g.recordMoveOnBoard(ourMove, color);
          return ourMove;
      } else {
          System.out.println("That move was caught by isValidMove() as invalid");
          return new Move();
      }

      /*
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
      */
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
                    if (m.moveKind == Move.STEP) {
                        ((GameChip) node.item()).setPositionOfChip(m.x2, m.y2);
                        break;
                    } else {
                        node.remove();
                        break;
                    }
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
            return MACHINE_WIN;
        } else if (g.networkFound(opponentColor)) {
            return HUMAN_WIN;
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
    public Best minimaxTreeSearch(GameBoard g, int side, double alpha, double beta, int accumulator) {
        // use a helper function in which the recursion works on this helper function itself
        // and the current depth of the recursion is counted as an accumulator in this function.
        // we can stick depth in as the accumulator parameter each time recursion is called
        // with the value of accumulator reducing by one each time until it is 0. That's when we
        // stop the recursion by not allowing the function to be called recursively if the value
        // is 0 (or maybe less than 0?).
        Best myBest = new Best();
        Best reply;
        if (g.networkFound(0) || g.networkFound(1)) {
            return new Best(evaluationFunction(g));
        }

        if (side == color) {
            myBest.score = alpha;
        } else {
            myBest.score = beta;
        }


        if (accumulator == searchDepth) {
            return new Best(evaluationFunction(g));
        }


        try {
            myBest.move = (Move) g.listOfValidMoves().front().item();
            ListNode node = g.listOfValidMoves().front();

            for (int i = 0; i < g.listOfValidMoves().length(); i++) {
                g.recordMoveOnBoard((Move) node.item(), side);
                reply = minimaxTreeSearch(g, getOpponentColor(side), alpha, beta, accumulator+1);
                undoMove((Move) node.item());
                if (side == color && reply.score > myBest.score) {
                    myBest.move = (Move) node.item();
                    myBest.score = reply.score;
                    alpha = reply.score;
                } else if (side == getOpponentColor(side) && reply.score < myBest.score) {
                    myBest.move = (Move) node.item();
                    myBest.score = reply.score;
                    beta = reply.score;
                }
                if (alpha >= beta) {
                    return myBest;
                }

                node = node.next();
            }
        } catch (InvalidNodeException e) {
            System.err.println(e);
        }
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

        Move m10 = new Move(0, 1);
        Move m11 = new Move(2, 1);
        Move m12 = new Move(2, 2);
        Move m14 = new Move(4, 4);
        Move m15 = new Move(4, 6);
        Move m16 = new Move(6, 4);
        Move m17 = new Move(7, 5);
        MachinePlayer mp3 = new MachinePlayer(0);
        mp3.g.recordMoveOnBoard(m10, 1);
        mp3.g.recordMoveOnBoard(m11, 1);
        mp3.g.recordMoveOnBoard(m12, 1);
        mp3.g.recordMoveOnBoard(m14, 1);
        mp3.g.recordMoveOnBoard(m15, 1);
        mp3.g.recordMoveOnBoard(m16, 1);
        mp3.g.recordMoveOnBoard(m17, 1);
        System.out.println(mp3.g.networkFound(1));
        Move m13 = new Move(3, 3);
        mp3.g.recordMoveOnBoard(m13, 0);
        System.out.println(mp3.g.networkFound(1));

    }


    private int getOpponentColor(int color) {
        if (color == 0) {
            return 1;
        } else {
            return 0;
        }
    }

}


















