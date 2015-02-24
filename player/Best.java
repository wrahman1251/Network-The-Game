/* Best.java */

package player;



public class Best {

    public Move move;
    public double score;

    public Best() {

    }

    public Best(Move m) {
        move = m;
    }

    public Best(double score) {
        this.score = score;
    }

    public Best(Move move, double score) {
        this.move = move;
        this.score = score;
    }

    public String toString() {
        return "Move: " + move + ", Score: " + score;
    }

}