/* GameChipList.java */

package player.list;

import player.list.*;
import player.*;

public class GameChipList extends DList {
    /**
     *  (inherited)  size is the number of items in the list.
     *  head references the sentinel node.
     *  Note that the sentinel node does not store an item, and is not included
     *  in the count stored by the "size" field.
     *
     *  DO NOT CHANGE THE FOLLOWING FIELD DECLARATION.
     **/

    protected GameChip head;

  /* GameChipList invariants:
   *  1)  head != null.
   *  2)  For every DListNode x in a GameChipList, x.next != null.
   *  3)  For every DListNode x in a GameChipList, x.prev != null.
   *  4)  For every DListNode x in a GameChipList, if x.next == y, then y.prev == x.
   *  5)  For every DListNode x in a GameChipList, if x.prev == y, then y.next == x.
   *  6)  For every GameChipList l, l.head.myList = null.  (Note that l.head is the
   *      sentinel.)
   *  7)  For every DListNode x in a GameChipList l EXCEPT l.head (the sentinel),
   *      x.myList = l.
   *  8)  size is the number of DListNodes, NOT COUNTING the sentinel,
   *      that can be accessed from the sentinel (head) by a sequence of
   *      "next" references.
   **/

    /**
     *  newNode() calls the DListNode constructor.  Use this method to allocate
     *  new DListNodes rather than calling the DListNode constructor directly.
     *  That way, only this method need be overridden if a subclass of GameChipList
     *  wants to use a different kind of node.
     *
     *  @param item the item to store in the node.
     *  @param list the list that owns this node.  (null for sentinels.)
     *  @param prev the node previous to this node.
     *  @param next the node following this node.
     **/
    protected GameChip newNode(int[] chipColorAndCoordinate, GameChipList list,
                                GameChip prev, GameChip next) {
        return new GameChip(chipColorAndCoordinate, list, prev, next);
    }

    /**
     * GameChipList() constructs a new GameChipList setting the head node to have: a null item.
     * The color of the chip is set to -1 since the head does not have a color since it is a sentinel node.
     * The position coordinates are also set to -1 for the same reason above.
     */
    public GameChipList() {
        // Your solution here.  Similar to Homework 4, but now you need to specify
        //   the `list' field (second parameter) as well.
        // head = newNode(null, null, head, head); WRONG WAY TO DO THIS since head = null when you set it as prev & next
        int[] i = {-1, -1, -1};
        head = newNode(i, this, null, null);
        head.prev = head;
        head.next = head;
    }

    /**
     *  insertBack() inserts an item at the back of this DList.
     *
     *  @param item is the item to be inserted.
     *
     *  Performance:  runs in O(1) time.
     **/
    public void insertBack(int[] chipColorAndCoordinate, int color, int x, int y) {
        // Your solution here.  Similar to Homework 4, but now you need to specify
        //   the `list' field (second parameter) as well.
        head.prev.next = newNode(chipColorAndCoordinate, this, head.prev, head);
        head.prev = head.prev.next;
        size++;
    }

}