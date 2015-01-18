/* MachinePlayer.java */

package player;

import player.list.*;

/**
 *  An implementation of an automatic Network player.  Keeps track of moves
 *  made by both players.  Can select a move for itself.
 */
public class MachinePlayer extends Player {

    int color;
    int searchDepth;
    GameBoard g;

  // Creates a machine player with the given color.  Color is either 0 (black)
  // or 1 (white).  (White has the first move.)
  public MachinePlayer(int color) {
      this.color = color;
      searchDepth = 3; // will change this later
      g = new GameBoard(color, 0, 0);
  }

  // Creates a machine player with the given color and search depth.  Color is
  // either 0 (black) or 1 (white).  (White has the first move.)
  public MachinePlayer(int color, int searchDepth) {
      this.color = color;
      this.searchDepth = searchDepth;
      g = new GameBoard(color, 0, 0);
  }

  // Returns a new move by "this" player.  Internally records the move (updates
  // the internal game board) as a move by "this" player.
  public Move chooseMove() {
      List list = g.listOfValidMoves();
      try {
          Move ourMove = (Move) list.front().item();
          if (ourMove.moveKind == Move.ADD && color == 0) {
              g.usedBlackGameChips += 1;
          } else if (ourMove.moveKind == Move.ADD && color == 1) {
              g.usedWhiteGameChips += 1;
          }
          return ourMove;
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
      if(side == 0) {
          if (g.isValid(m, 1)) {

          }
      }
  }

  // If the Move m is legal, records the move as a move by "this" player
  // (updates the internal game board) and returns true.  If the move is
  // illegal, returns false without modifying the internal state of "this"
  // player.  This method is used to help set up "Network problems" for your
  // player to solve.
  public boolean forceMove(Move m) { return false; }

    /**
     * This method assigns a score to a particular GameBoard giving the likelihood of winning the game from
     * this point onward.
     * @param g is the GameBoard object we are evaluating with the evaluation function.
     * @return a double that indicates how good the chances are of winning. The more positive the number, the
     *         better the chances are of winning.
     **/
    public double evaluationFunction(GameBoard g) {
        return 5.00;
    }

    /**
     * This algorithm recursively searches through a GameBoard, many moves down the line (specified by
     * MachinePlayer's searchDepth parameter) to return to you the move with the greatest likelihood of winning.
     * @param g is the GameBoard upon which you are implementing the tree search to see which move the computer
     *          should make next in order to increase its odds of winning.
     * @return a Move object that the MachinePlayer will use as its next move.
     **/
    public Move minimaxTreeSearch(GameBoard g) {
        return new Move();
    }


}
