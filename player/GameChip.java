/* GameChip.java */

package player;

import player.list.*;

/**
 * This is a GameChip object implemented as a node for a doubly linked list
 */


public class GameChip extends DListNode {

    int color;
    GameBoard myGameBoard;
    int xPosition;
    int yPosition;

    protected GameChip(Object i, DList l, DListNode p, DListNode n, GameBoard g, int x, int y) {
        super(i, l, p, n);
        myGameBoard = g;
        color = myGameBoard.color();
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
        return new Move[10];
    }

}