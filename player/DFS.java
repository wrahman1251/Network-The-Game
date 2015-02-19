/* DFS.java */

package player;

import player.list.*;


public class DFS {

    protected GameBoard g;
    protected DList chips_currently_on_board;
    protected DFSPath newPath;


    public DFS(GameBoard g, DFSPath pathObject) {
        this.g = g;
        this.chips_currently_on_board = g.chips_currently_on_board;
        newPath = pathObject;
    }

    public void DFSExecute(DFSPath pathObject, GameChip c, int skip, int side) {
        int newSkip;
        pathObject.alreadyVisitedChips.insertBack(c);

        List edges = g.listOfConnectedChips(c, skip);
        ListNode node = edges.front();

        visitChip(pathObject.alreadyVisitedChips, c);

        try {
            for (int i = 0; i < edges.length(); i++) {
                //if (pathObject.isFound == true) {
                //    break;
                //}
                newSkip = connectedChipPathSkip(c, (GameChip)node.item());
                if (side == 0) {
                    if (chipInBottomGoal((GameChip)node.item(), pathObject) && pathObject.alreadyVisitedChips.length() >= 5) {
                        pathObject.isFound = true;
                    } else if (chipInTopGoal((GameChip)node.item(), pathObject)) {
                        // do nothing
                    } else {
                        DFSExecute(pathObject, (GameChip)node.item(), newSkip, side);
                    }
                } else {
                    if (chipInRightGoal((GameChip)node.item(), pathObject)) {
                        pathObject.isFound = true;
                    } else if (chipInLeftGoal((GameChip)node.item(), pathObject)) {
                        // do nothing
                    } else {
                        DFSExecute(pathObject, (GameChip)node.item(), newSkip, side);
                    }
                }

                node = node.next();
            }
        } catch (InvalidNodeException e) {
            System.err.println(e);
        }

        unvisitChip(pathObject.alreadyVisitedChips, c);
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



    private boolean chipInBottomGoal (GameChip inputChip, DFSPath pathObject) {
        for (GameChip c : g.arrayOfChipsInBottomGoal()) {
            if (c == null) {
                return false;
            } else if ((inputChip).equals(c) && pathObject.alreadyVisitedChips.length() >= 5) {
                return true;
            }
        }
        return false;
    }

    private boolean chipInTopGoal (GameChip inputChip, DFSPath pathObject) {
        for (GameChip c : g.arrayOfChipsInTopGoal()) {
            if (c == null) {
                return false;
            } else if ((inputChip).equals(c) && pathObject.alreadyVisitedChips.length() >= 5) {
                return true;
            }
        }
        return false;
    }

    private boolean chipInRightGoal (GameChip inputChip, DFSPath pathObject) {
        for (GameChip c : g.arrayOfChipsInRightGoal()) {
            if (c == null) {
                return false;
            } else if ((inputChip).equals(c) && pathObject.alreadyVisitedChips.length() >= 5) {
                return true;
            }
        }
        return false;
    }

    private boolean chipInLeftGoal (GameChip inputChip, DFSPath pathObject) {
        for (GameChip c : g.arrayOfChipsInLeftGoal()) {
            if (c == null) {
                return false;
            } else if ((inputChip).equals(c) && pathObject.alreadyVisitedChips.length() >= 5) {
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

    private int connectedChipPathSkip(GameChip c1, GameChip c2) {
        int i = c1.xPosition();
        int j = c1.yPosition();
        int k = c2.xPosition();
        int l = c2.yPosition();

        if (i == k) {
            if (l > j) {
                return 4;
            } else {
                return 2;
            }
        } else if (j == l) {
            if (k > i) {
                return 1;
            } else {
                return 3;
            }
        } else if (k-i > 0 && l-j > 0) {
            return 6;
        } else if (k-i < 0 && l-j < 0) {
            return 7;
        } else if (k-i > 0 && l-j < 0) {
            return 5;
        } else if (k-i < 0 && l-j > 0) {
            return 8;
        } else {
            return 0;
        }
    }

}

















