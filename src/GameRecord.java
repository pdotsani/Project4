import java.util.Objects;

public class GameRecord implements Comparable {
    private int score;
    private String playerId;

    public GameRecord(int score, String playerId) {
        this.score = score;
        this.playerId = playerId;
    }

    public int getScore() {
        return this.score;
    }

    public String getPlayerId() {
        return this.playerId;
    }

    @Override
    public int compareTo(Object o) {
        GameRecord gameRecord = (GameRecord) o;
        return Integer.compare(this.score, gameRecord.score);
    }

    @Override
    public String toString() {
        return "Player: " + this.playerId + "[Score: " + this.score + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameRecord that = (GameRecord) o;
        return score == that.score && Objects.equals(playerId, that.playerId);
    }
}
