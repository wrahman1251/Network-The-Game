/* GameChip.java */

package player.list;

import player.*;

/**
 * This is a GameChip object implemented as a node for a doubly linked list
 */


public class GameChip extends ListNode {
    protected GameBoard myList;

    /**
     *  (inherited)  item references the item stored in the current node.
     *  (inherited)  myList references the List that contains this node.
     *  prev references the previous node in the GameBoard.
     *  next references the next node in the GameBoard.
     *
     *  DO NOT CHANGE THE FOLLOWING FIELD DECLARATIONS.
     **/

    protected GameChip prev;
    protected GameChip next;

    /**
     *  GameChip() constructor.
     *  @param i the item to store in the node.
     *  @param l the list this node is in.
     *  @param p the node previous to this node.
     *  @param n the node following this node.
     */
    GameChip(Object i, GameBoard l, GameChip p, GameChip n) {
        item = i;
        myList = l;
        prev = p;
        next = n;
    }

    /**
     *  isValidNode returns true if this node is valid; false otherwise.
     *  An invalid node is represented by a `myList' field with the value null.
     *  Sentinel nodes are invalid, and nodes that don't belong to a list are
     *  also invalid.
     *
     *  @return true if this node is valid; false otherwise.
     *
     *  Performance:  runs in O(1) time.
     */
    public boolean isValidNode() {
        return myList != null;
    }

    /**
     *  next() returns the node following this node.  If this node is invalid,
     *  throws an exception.
     *
     *  @return the node following this node.
     *  @exception InvalidNodeException if this node is not valid.
     *
     *  Performance:  runs in O(1) time.
     */
    public ListNode next() throws InvalidNodeException {
        if (!isValidNode()) {
            throw new InvalidNodeException("next() called on invalid node");
        }
        return next;
    }

    /**
     *  prev() returns the node preceding this node.  If this node is invalid,
     *  throws an exception.
     *
     *  @return the node preceding this node.
     *  @exception InvalidNodeException if this node is not valid.
     *
     *  Performance:  runs in O(1) time.
     */
    public ListNode prev() throws InvalidNodeException {
        if (!isValidNode()) {
            throw new InvalidNodeException("prev() called on invalid node");
        }
        return prev;
    }

    /**
     *  insertAfter() inserts an item immediately following this node.  If this
     *  node is invalid, throws an exception.
     *
     *  @param item the item to be inserted.
     *  @exception InvalidNodeException if this node is not valid.
     *
     *  Performance:  runs in O(1) time.
     */
    public void insertAfter(Object item) throws InvalidNodeException {
        if (!isValidNode()) {
            throw new InvalidNodeException("insertAfter() called on invalid node");
        }
        // Your solution here.  Will look something like your Homework 4 solution,
        //   but changes are necessary.  For instance, there is no need to check if
        //   "this" is null.  Remember that this node's "myList" field tells you
        //   what GameBoard it's in.  You should use myList.newNode() to create the
        //   new node.
        next.prev = ((GameBoard)myList).newNode(item, myList, this, next);
        next = next.prev;
        myList.size++;
    }

    /**
     *  insertBefore() inserts an item immediately preceding this node.  If this
     *  node is invalid, throws an exception.
     *
     *  @param item the item to be inserted.
     *  @exception InvalidNodeException if this node is not valid.
     *
     *  Performance:  runs in O(1) time.
     */
    public void insertBefore(Object item) throws InvalidNodeException {
        if (!isValidNode()) {
            throw new InvalidNodeException("insertBefore() called on invalid node");
        }
        // Your solution here.  Will look something like your Homework 4 solution,
        //   but changes are necessary.  For instance, there is no need to check if
        //   "this" is null.  Remember that this node's "myList" field tells you
        //   what GameBoard it's in.  You should use myList.newNode() to create the
        //   new node.
        prev.next = myList.newNode(item, myList, prev, this);
        prev = prev.next;
        myList.size++;
    }

    /**
     *  remove() removes this node from its GameBoard.  If this node is invalid,
     *  throws an exception.
     *
     *  @exception InvalidNodeException if this node is not valid.
     *
     *  Performance:  runs in O(1) time.
     */
    public void remove() throws InvalidNodeException {
        if (!isValidNode()) {
            throw new InvalidNodeException("remove() called on invalid node");
        }
        // Your solution here.  Will look something like your Homework 4 solution,
        //   but changes are necessary.  For instance, there is no need to check if
        //   "this" is null.  Remember that this node's "myList" field tells you
        //   what GameBoard it's in.

        prev.next = next;
        next.prev = prev;
        myList.size--;


        // Make this node an invalid node, so it cannot be used to corrupt myList.
        myList = null;
        // Set other references to null to improve garbage collection.
        next = null;
        prev = null;
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