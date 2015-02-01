/* Best.java */

package player;



public class Best {

    public Move move;
    public double score;

    public Best() {

    }

    public Best(Move move, int score) {
        this.move = move;
        this.score = score;
    }

    public String toString() {
        return "Move: " + move + ", Score: " + score;
    }

}