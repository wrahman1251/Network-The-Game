/* GameBoard.java */

package player;

import player.list.*;

/**
 * This is a GameBoard object implemented as a doubly linked list
 */


public class GameBoard {

    public DList chips_currently_on_board;
    protected int color;
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

    /**
     * recordMoveOnBoard() updates the GameBoard object with a chip based on where the Move m
     * places the chip.
     * @param m
     * @param side
     * @return a List of GameChip objects which can be queried using List methods
     **/
    public void recordMoveOnBoard(Move m, int color) {
        if (m.moveKind == Move.ADD) {
            //chips_currently_on_board.insertBack()
            GameChip newChip = new GameChip(color, m.x1, m.y1);
            chips_currently_on_board.insertBack(newChip);
            if (color == 0) {
                usedBlackGameChips++;
            } else if (color == 1) {
                usedWhiteGameChips++;
            }
        } else {
            ListNode node = chips_currently_on_board.front();
            for (int i = 0; i < chips_currently_on_board.length(); i++) {
                try {
                    if (((GameChip)node.item()).xPosition() == m.x2 && ((GameChip)node.item()).yPosition() == m.y2) {
                        ((GameChip)node.item()).setPositionOfChip(m.x1, m.y1);
                    }
                    node = node.next();
                } catch (InvalidNodeException e1) {
                    System.err.println(e1);
                }
            }
        }
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

        ListNode node = chips_currently_on_board.front();

        if ((m.x1 == 0 && m.y1 == 0) | (m.x1 == 7 && m.y1 == 0) | (m.x1 == 0 && m.y1 == 7) |
                (m.x1 == 7 && m.y1 == 7)) { //if move is adding to a corner of the grid
            return false;
        } else if ((side == 0 && (m.x1 == 0 | m.x1 == 7)) | (side == 1 && (m.y1 == 0 | m.y1 == 7))) { //wrong goal area
            return false;
        } else if (node.isValidNode()) {
            for (int k = 0; k < chips_currently_on_board.length(); k++) {
                try {
                    // checks if space in grid is already occupied
                    if (((GameChip)node.item()).xPosition() == m.x1 && ((GameChip)node.item()).yPosition() == m.y1) {
                    return false;
                    }
                    // check if a cluster forms
                    if (((((GameChip)node.item()).xPosition() == m.x1-1 && ((GameChip)node.item()).yPosition() == m.y1-1)
                            | (((GameChip)node.item()).xPosition() == m.x1 && ((GameChip)node.item()).yPosition() ==
                            m.y1-1)

                            | (((GameChip)node.item()).xPosition() == m.x1+1 && ((GameChip)node.item()).yPosition()
                            == m.y1-1)

                            | (((GameChip)node.item()).xPosition() == m.x1-1 && ((GameChip)node.item()).yPosition()
                            == m.y1)

                            | (((GameChip)node.item()).xPosition() == m.x1+1 && ((GameChip)node.item()).yPosition()
                            == m.y1)

                            | (((GameChip)node.item()).xPosition() == m.x1-1 && ((GameChip)node.item()).yPosition() == m.y1+1)

                            | (((GameChip)node.item()).xPosition() == m.x1 && ((GameChip)node.item()).yPosition() == m.y1+1)

                            | (((GameChip)node.item()).xPosition() == m.x1+1 && ((GameChip)node.item()).yPosition()
                            == m.y1+1))

                            && ((GameChip)node.item()).color() == side) {


                        ListNode node2 = chips_currently_on_board.front();
                        for (int l = 0; l < chips_currently_on_board.length(); l++) {

                            if (((((GameChip)node2.item()).xPosition() == ((GameChip)node.item()).xPosition()-1 &&
                                    ((GameChip)node2.item()).yPosition() == ((GameChip)node.item()).yPosition()-1)

                                    | (((GameChip)node2.item()).xPosition() == ((GameChip)node.item()).xPosition() &&
                                    ((GameChip)node2.item()).yPosition() == ((GameChip)node.item()).yPosition()-1)

                                    | (((GameChip)node2.item()).xPosition() == ((GameChip)node.item()).xPosition()+1
                                    && ((GameChip)node2.item()).yPosition() == ((GameChip)node.item()).yPosition()-1)

                                    | (((GameChip)node2.item()).xPosition() == ((GameChip)node.item()).xPosition()-1 &&
                                    ((GameChip)node2.item()).yPosition() == ((GameChip)node.item()).yPosition())

                                    | (((GameChip)node2.item()).xPosition() == ((GameChip)node.item()).xPosition()+1 &&
                                    ((GameChip)node2.item()).yPosition() == ((GameChip)node.item()).yPosition())

                                    | (((GameChip)node2.item()).xPosition() == ((GameChip)node.item()).xPosition()-1
                                    && ((GameChip)node2.item()).yPosition() == ((GameChip)node.item()).yPosition()+1)

                                    | (((GameChip)node2.item()).xPosition() == ((GameChip)node.item()).xPosition() &&
                                    ((GameChip)node2.item()).yPosition() == ((GameChip)node.item()).yPosition()+1)

                                    | (((GameChip)node2.item()).xPosition() == ((GameChip)node.item()).xPosition()+1
                                    && ((GameChip)node2.item()).yPosition() == ((GameChip)node.item()).yPosition()+1))

                                    && ((GameChip)node2.item()).color() == side) {

                                return false;


                            }
                            try {
                                node2 = node2.next();
                            } catch (InvalidNodeException e1) {
                                System.err.println(e1);
                            }
                        }
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
     * listOfValidMoves() Generates and returns a DList of all valid Moves on "this" board that a MachinePlayer
     * can execute.
     * @return a List of all possible moves allowed by the current state of the GameBoard
     **/
    public List listOfValidMoves() {
        // return new DList();
        List result = new DList(); // create a new empty DList
        Move aMove;

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if ((color == 1 && usedWhiteGameChips < 10) | (color == 0 && usedBlackGameChips < 10)) {  // Only ADD Moves
                    aMove = new Move(i, j);
                    if (isValidMove(new Move(i, j), color)) {
                        result.insertBack(aMove);
                    }
                } else {  // Only STEP moves
                    ListNode node = chips_currently_on_board.front();
                    if (node.isValidNode()) {
                        for (int k = 0; k < chips_currently_on_board.length(); k++) {
                            try {
                            aMove = new Move(i, j, ((GameChip)node.item()).xPosition(), ((GameChip)node.item()).yPosition());
                            if (isValidMove(aMove, color) && ((GameChip)node.item()).color() == color) {
                                result.insertBack(aMove);
                            }
                                node = node.next();
                            } catch (InvalidNodeException e) {
                                System.err.println(e);
                            }
                        }
                    }
                }
            }
        }

        return result;

    }

    /**
     * listOfConnectedChips() Generates and returns a list of all chips currently connected with a particular
     * "this" GameChip on a particular GameBoard.
     * @param c is a GameChip object
     * @param skip tells the board not to look in this direction for a connected chip
     * @return a list of all possible GameChip objects connected to this chip
     **/
    public List listOfConnectedChips(GameChip c, int skip) {
        List connectedChips = new DList();
        ListNode node = chips_currently_on_board.front();
        try {
            for (int j = 0; j < chips_currently_on_board.length(); j++) {
                // 8 loops going through every direction to see if there is a match.
                // The skip number determines which direction not to look in to find
                // a connected chip so as to avoid finding a connected chip that does
                // not follow the same path as the previous chip
                if (skip != 1) {
                    for (int i = 1; i < 9; i++) {
                        if (c.xPosition() + i == ((GameChip) node.item()).xPosition() && c.yPosition() ==
                                ((GameChip) node.item()).yPosition()) {
                            if (((GameChip) node.item()).color() == c.color()) {
                                ((DList) connectedChips).insertFront(((GameChip) node.item()));
                                break;
                            } else {
                                break;
                            }
                        }
                    }
                }

                if (skip != 2) {
                    for (int i = 1; i < 9; i++) {
                        if (c.xPosition() == ((GameChip) node.item()).xPosition() && c.yPosition() + i ==
                                ((GameChip) node.item()).yPosition()) {
                            if (((GameChip) node.item()).color() == c.color()) {
                                ((DList) connectedChips).insertFront(((GameChip) node.item()));
                                break;
                            } else {
                                break;
                            }
                        }
                    }
                }

                if (skip != 3) {
                    for (int i = 1; i < 9; i++) {
                        if (c.xPosition() - i == ((GameChip) node.item()).xPosition() && c.yPosition() ==
                                ((GameChip) node.item()).yPosition()) {
                            if (((GameChip) node.item()).color() == c.color()) {
                                ((DList) connectedChips).insertFront(((GameChip) node.item()));
                                break;
                            } else {
                                break;
                            }
                        }
                    }
                }

                if (skip != 4) {
                    for (int i = 1; i < 9; i++) {
                        if (c.xPosition() == ((GameChip) node.item()).xPosition() && c.yPosition() - i ==
                                ((GameChip) node.item()).yPosition()) {
                            if (((GameChip) node.item()).color() == c.color()) {
                                ((DList) connectedChips).insertFront(((GameChip) node.item()));
                                break;
                            } else {
                                break;
                            }
                        }
                    }
                }

                if (skip != 5) {
                    for (int i = 1; i < 9; i++) {
                        if (c.xPosition() + i == ((GameChip) node.item()).xPosition() && c.yPosition() + i ==
                                ((GameChip) node.item()).yPosition()) {
                            if (((GameChip) node.item()).color() == c.color()) {
                                ((DList) connectedChips).insertFront(((GameChip) node.item()));
                                break;
                            } else {
                                break;
                            }
                        }
                    }
                }

                if (skip !=6) {
                    for (int i = 1; i < 9; i++) {
                        if (c.xPosition() + i == ((GameChip) node.item()).xPosition() && c.yPosition() - i ==
                                ((GameChip) node.item()).yPosition()) {
                            if (((GameChip) node.item()).color() == c.color()) {
                                ((DList) connectedChips).insertFront(((GameChip) node.item()));
                                break;
                            } else {
                                break;
                            }
                        }
                    }
                }

                if (skip != 7) {
                    for (int i = 1; i < 9; i++) {
                        if (c.xPosition() - i == ((GameChip) node.item()).xPosition() && c.yPosition() + i ==
                                ((GameChip) node.item()).yPosition()) {
                            if (((GameChip) node.item()).color() == c.color()) {
                                ((DList) connectedChips).insertFront(((GameChip) node.item()));
                                break;
                            } else {
                                break;
                            }
                        }
                    }
                }

                if (skip != 8) {
                    for (int i = 1; i < 9; i++) {
                        if (c.xPosition() - i == ((GameChip) node.item()).xPosition() && c.yPosition() - i ==
                                ((GameChip) node.item()).yPosition()) {
                            if (((GameChip) node.item()).color() == c.color()) {
                                ((DList) connectedChips).insertFront(((GameChip) node.item()));
                                break;
                            } else {
                                break;
                            }
                        }
                    }
                }

                node = node.next();
            }
        } catch (InvalidNodeException e) {
            System.err.println(e);
        }
        return connectedChips;
    }

    /**
     * networkFound() tells you whether a valid network has been found on "this" GameBoard for a particular
     * player on side 0 or 1 (black or white).
     * @param side is an integer 0 or 1 that picks which side player you are checking to see whether they have
     *        a valid and completed network.
     * @return either true or false for whether a player has a completed network or not.
     **/
    public boolean networkFound(int side) {
        // Change all chips on the board to visited = false
        // Use DFS to figure out a network starting from one of the GameChips in a goal
        // Search through all of side's GameChips on board to see if both: 6 chips have been visited &&
        //   that another chip in the other goal post was one of these six visited chips.
        // If not then repeat the above again starting at a different GameChip in the same goal area if there
        //   is another one, making sure not to count another goal area chip on this side.
        ListNode node = chips_currently_on_board.front();
        for (int i = 0; i < chips_currently_on_board.length(); i++) {
            ((GameChip)node.item()).visited = false;
            node = node.next();
        }


        boolean connectedChipInGoal1 = false;
        boolean connectedChipInGoal2 = false;


        node = chips_currently_on_board.front();
        int count = 0;
        for (int i = 0; i < chips_currently_on_board.length(); i++) {
            if (((GameChip)node.item()).visited == true) {
                count++;
            }
        }

    }

    public boolean DFS(DFSPath pathObject, GameChip c, int skip, int side) {
        pathObject.alreadyVistedChips.insertBack(c);

        List edges = listOfConnectedChips(c, skip);
        ListNode node = edges.front();
        try {
            for (int i = 0; i < edges.length(); i++) {
                if (side == 0) {
                    if (chipInBottomGoal((GameChip)node.item()) && pathObject.alreadyVistedChips.length() >= 5) {
                        pathObject.isFound = true;
                    } else if (chipInTopGoal((GameChip)node.item())) {
                        // do nothing
                    } else {
                        // calculate new skip here
                        DFS(pathObject, (GameChip)node.item(), skip, side);
                    }
                } else {
                    if (chipInRightGoal((GameChip)node.item())) {
                        pathObject.isFound = true;
                    } else if (chipInLeftGoal((GameChip)node.item())) {
                        // do nothing
                    } else {
                        // calculate new skip here
                        DFS(pathObject, (GameChip)node.item(), skip, side);
                    }
                }

                node = node.next();
            }
        } catch (InvalidNodeException e) {
            System.err.println(e);
        }

        unvisitChip(DFSPath.alreadyVisitedChips, c);
    }

    private void visitChip(DList l, GameChip c) {
        l.insertBack(c);
    }

    private void unvisitChip(DList l, GameChip c) {
        ListNode node = l.front();
        try {
            for (int i = 0; i < l.length(); i++) {
                if (((GameChip) node.item()).equals(c)) {
                    node.remove();
                    break;
                }
                node = node.next();
            }
        } catch (InvalidNodeException e) {
            System.err.println(e);
        }

    }

    private boolean alreadyVisited(List l, GameChip c) {
        ListNode node = l.front();
        try {
            for (int i = 0; i < l.length(); i++) {
                if (((GameChip) node.item()).equals(c)) {
                    return true;
                }
                node = node.next();
            }
        } catch (InvalidNodeException e) {
            System.err.println(e);
        }
        return false;
    }

    // returns back a list of GameChips in the left-side goal post (white)
    private GameChip[] arrayOfChipsInLeftGoal() {
        ListNode node = chips_currently_on_board.front();
        GameChip[] chipList = new GameChip[6];
        try {

            int curr_idx = 0;
            for (int i = 0; i < chips_currently_on_board.length(); i++) {
                if (((GameChip) node.item()).xPosition() == 0) {
                    chipList[curr_idx] = (GameChip) node.item();
                    curr_idx++;
                }
                node = node.next();
            }
        } catch (InvalidNodeException e) {
            System.err.println(e);
        }
        return chipList;
    }

    // returns back a list of GameChips in the top-side goal post (black)
    private GameChip[] arrayOfChipsInTopGoal() {
        ListNode node = chips_currently_on_board.front();
        GameChip[] chipList = new GameChip[6];
        try {

            int curr_idx = 0;
            for (int i = 0; i < chips_currently_on_board.length(); i++) {
                if (((GameChip) node.item()).yPosition() == 0) {
                    chipList[curr_idx] = (GameChip) node.item();
                    curr_idx++;
                }
                node = node.next();
            }
        } catch (InvalidNodeException e) {
            System.err.println(e);
        }
        return chipList;
    }

    private GameChip[] arrayOfChipsInRightGoal() {
        ListNode node = chips_currently_on_board.front();
        GameChip[] chipList = new GameChip[6];
        try {

            int curr_idx = 0;
            for (int i = 0; i < chips_currently_on_board.length(); i++) {
                if (((GameChip) node.item()).xPosition() == 7) {
                    chipList[curr_idx] = (GameChip) node.item();
                    curr_idx++;
                }
                node = node.next();
            }
        } catch (InvalidNodeException e) {
            System.err.println(e);
        }
        return chipList;
    }

    private GameChip[] arrayOfChipsInBottomGoal() {
        ListNode node = chips_currently_on_board.front();
        GameChip[] chipList = new GameChip[6];
        try {

            int curr_idx = 0;
            for (int i = 0; i < chips_currently_on_board.length(); i++) {
                if (((GameChip) node.item()).yPosition() == 7) {
                    chipList[curr_idx] = (GameChip) node.item();
                    curr_idx++;
                }
                node = node.next();
            }
        } catch (InvalidNodeException e) {
            System.err.println(e);
        }
        return chipList;
    }

    private boolean chipInBottomGoal (GameChip inputChip) {
        for (GameChip c : arrayOfChipsInBottomGoal()) {
            if (inputChip).equals(c) && alreadyVistedChips.length() >= 5) {
                return true;
            }
        }
        return false;
    }

    private boolean chipInTopGoal (GameChip inputChip) {
        for (GameChip c : arrayOfChipsInTopGoal()) {
            if (inputChip).equals(c) && alreadyVistedChips.length() >= 5) {
                return true;
            }
        }
        return false;
    }

    private boolean chipInRightGoal (GameChip inputChip) {
        for (GameChip c : arrayOfChipsInRightGoal()) {
            if (inputChip).equals(c) && alreadyVistedChips.length() >= 5) {
                return true;
            }
        }
        return false;
    }

    private boolean chipInLeftGoal (GameChip inputChip) {
        for (GameChip c : arrayOfChipsInLeftGoal()) {
            if (inputChip).equals(c) && alreadyVistedChips.length() >= 5) {
                return true;
            }
        }
        return false;
    }

    private boolean chipInThisList(List l, GameChip c) {
        ListNode node = l.front();
        try {
            for (int i = 0; i < l.length(); i++) {
                if (((GameChip) node.item()).equals(c)) {
                    return true;
                }
                node = node.next();
            }
        } catch (InvalidNodeException e) {
            System.err.println(e);
        }
        return false;
    }

}