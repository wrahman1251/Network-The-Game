/* GameBoard.java */

package player;

import player.list.*;

/**
 * This is a GameBoard object implemented as a doubly linked list
 */


public class GameBoard {



    /**
     * Below this line are those methods which are specific to the game
     */


    /**
     * isValidMove() determines if a particular move is valid on "this" GameBoard.
     *
     * @param m is a Move object which could come from any player playing the game
     * @param side is the side (0 or 1) representing either the black or white side of the player respectively
     * @return false if move is invalid and violates the 4 rules of a valid move (and prints error message)
     *         true otherwise
     **/
    public boolean isValidMove(Move m, int side) {
        return false;
    }

    /**
     * listOfValidMoves() Generates and returns a list of all valid moves on "this" GameBoard
     * @return a List of all possible moves allowed by the current state of the GameBoard
     **/
    public List listOfValidMoves() {
        return new DList();
    }

    /**
     * networkFound() tells you whether a valid network has been found on "this" GameBoard for a particular
     * player on side 0 or 1 (black or white).
     * @param side is an integer 0 or 1 that picks which side player you are checking to see whether they have
     *        a valid and completed network.
     * @return either true or false for whether a player has a completed network or not.
     **/
    public boolean networkFound(int side) {
        return false;
    }


}