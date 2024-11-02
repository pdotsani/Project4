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
}
