/* InvalidNodeException.java */

package list;

/**
 * Implements an exception that signals when an invalid node is being attempted to be used.
 */

public class InvalidNodeException extends Exception {
    protected InvalidNodeException() {
        super();
    }

    protected InvalidNodeException(String s) {
        super(s);
    }
}