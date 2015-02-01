/* GameChip.java */

package player.list;

import player.list.*;
import player.*;

/**
 * This is a GameChip object implemented as a node for a doubly linked list
 */


public class GameChip {

    int color;
    int xPosition;
    int yPosition;

    /**
     * GameChip() is the constructor for a new GameChip object which stores the color and coordinate of each GameChip
     * that is currently on the board.
     * @param i is the array which stores chip color at index 0, x-coordinate at index 1 and y-coordinate at index 2.
     * @param l is the GameChipList that "this" GameChip belongs to.
     * @param p is the GameChip that is previous to "this GameChip.
     * @param n is the GameChip that is next from "this" GameChip.
     */
    public GameChip(int color, int x, int y) {
        this.color = color;
        xPosition = x;
        yPosition = y;
    }


    /**
     * Below this line are those methods which are specific to the game
     */

    /**
     * listOfConnectedChips() Generates and returns a list of all chips currently connected with a particular
     * "this" GameChip on a particular GameBoard.
     * @param c is a GameChip object
     * @return a list of all possible GameChip objects connected to this chip
     **/
    public Move[] listOfConnectedChips() {
        // Use a switch statement to iteratively search through for connected chips in all 8 possible directions
        return new Move[10];
    }

    /**
     * A getter method that returns the x-coordinate of "this" GameChip.
     * @return the integer representing the x-position of this GameChip on the board.
     */
    public int xPosition() {
        return xPosition;
    }

    /**
     * A getter method that returns the y-coordinate of "this" GameChip.
     * @return the integer representing the y-position of this GameChip on the board.
     */
    public int yPosition() {
        return yPosition;
    }

    /**
     * A setter method that sets the coordinates of this GameChip on the GameBoard
     * @param x sets the x-coordinate of "this" GameChip
     * @param y sets the y-coordinate of "this" GameChip
     */
    public void setPositionOfChip(int x, int y) {
        xPosition = x;
        yPosition = y;
    }

    /**
     *
     * @return an integer 0 (black) or 1 (white) which represents the color of this chip
     */
    public int color() {
        return color;
    }

    public String toString() {
        String side;
        if (color == 0) {
            side = "Black";
        } else {
            side = "White";
        }
        return side + " GameChip @ x = " + xPosition + ", y = " + yPosition;
    }

}