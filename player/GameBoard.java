/* GameBoard.java */

package player;

import player.list.*;

/**
 * This is a GameBoard object implemented as a doubly linked list
 */


public class GameBoard {

    private DList chips_currently_on_board;
    private int color;
    int usedWhiteGameChips;
    int usedBlackGameChips;

    GameBoard(int color, int whiteChips, int blackChips) {
        chips_currently_on_board = new DList();
        this.color = color;
        usedWhiteGameChips = whiteChips;
        usedBlackGameChips = blackChips;
    }

    public int color() {
        return color;
    }

    public recordMoveOnBoard(Move m) {
        chips_currently_on_board.insertBack()
    }

    /**
     * isValidMove() determines if a particular move is valid on "this" GameBoard.
     *
     * @param m is a Move object which could come from any player playing the game
     * @param side is the side (0 or 1) representing either the black or white side of the player respectively
     * @return false if move is invalid and violates the 4 rules of a valid move (and prints error message)
     *         true otherwise
     **/
    public boolean isValidMove(Move m, int side) {
        if ((m.x1 == 0 && m.y1 == 0) | (m.x1 == 7 && m.y1 == 0) | (m.x1 == 0 && m.y1 == 7) |
                (m.x1 == 7 && m.y1 == 7)) { //if move is adding to a corner of the grid
            return false;
        } else if ((side == 0 && (m.x1 == 0 | m.x1 == 7)) | (side == 1 && (m.y1 == 0 | m.y1 == 7))) { //wrong goal area
            return false;
        } else { // checks if space in grid is already occupied
            ListNode node = chips_currently_on_board.front();
            for (int k = 0; k < chips_currently_on_board.length(); k++) {
                try {
                    if (((GameChip)node.item()).xPosition == m.x1 && ((GameChip)node.item()).yPosition == m.y1) {
                    return false;
                    }
                } catch (InvalidNodeException e) {
                    System.err.println(e);
                }

                try {
                    node = node.next();
                } catch (InvalidNodeException e1) {
                    System.err.println(e1);
                }

            }
        }

        return true;
    }

    /**
     * listOfValidMoves() Generates and returns a list of all valid Moves on "this" MachinePlayer can execute
     * @return a List of all possible moves allowed by the current state of the GameBoard
     **/
    public List listOfValidMoves() {
        // return new DList();
        List result = new DList();

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if ((color == 1 && usedWhiteGameChips < 10) | (color == 0 && usedBlackGameChips < 10)) {  // Only ADD Moves
                    Move aMove = new Move(i, j);
                    if (isValidMove(aMove, color)) {
                        result.insertBack(aMove);
                    }
                } else {  // Only STEP moves
                    GameChip node = (GameChip)chips_currently_on_board.front();
                    for (int k = 0; k < chips_currently_on_board.length(); k++) {
                        Move aMove = new Move(i, j, node.xPosition, node.yPosition);
                        if (isValidMove(aMove, color)) {
                            result.insertBack(aMove);
                        }
                        try {
                            node = (GameChip) node.next();
                        } catch (InvalidNodeException e) {
                            System.err.println(e);
                        }
                    }
                }
            }
        }

        return result;
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