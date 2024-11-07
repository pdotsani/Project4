import java.util.ArrayList;
import java.util.List;

/**
 * Contains a group of GameRecords for a single gaming session. Allows client
 * to view variious score metrics based on play data.
 */
public class AllGamesRecord {
    ArrayList<GameRecord> gameRecords = new ArrayList<>();

    /**
     * adds the given Game record to the instance
     *
     * @param record
     */
    public void add(GameRecord record) {
        gameRecords.add(record);
    }

    /**
     * Returns the average of all GameRecords in the instance, regardless
     * of the playerId
     *
     * @return sum: average of all records
     */
    public int average() {
        int sum = 0;
        int count = 0;
        for (GameRecord record : gameRecords) {
            sum += record.getScore();
            count++;
        }
        return sum / count;
    }

    /**
     * returns the average score during the session
     * of the given playerId
     *
     * @param playerId
     * @return sum: average score of player
     */
    public int average(String playerId) {
        int sum = 0;
        int count = 0;
        for (GameRecord record : gameRecords) {
            if(record.getPlayerId().equals(playerId)) {
                sum += record.getScore();
                count++;
            }
        }
        return sum / count;
    }

    /**
     * Return the top n scores of GameRecords
     *
     * @param n
     * @return highGameList: list of top n scores
     */
    public List<Integer> highGameList(int n) {
        ArrayList<Integer> highGameList = new ArrayList<>();
        gameRecords.sort(GameRecord::compareTo);
        for (int i = 0; i < n; i++) {
            highGameList.add(i);
        }
        return highGameList;
    }

    /**
     * return the top n scores of a player with playerId
     *
     * @param n
     * @param playerId
     * @return highGameList: top n scores of player with playerId
     */
    public List<Integer> highGameList(int n, String playerId) {
        ArrayList<Integer> highGameList = new ArrayList<>();
        gameRecords.sort(GameRecord::compareTo);
        for (int i = 0; i < n; i++) {
            if(playerId.equals(gameRecords.get(i).getPlayerId())) {
                highGameList.add(i);
            }
        }
        return highGameList;
    }

    @Override
    public String toString() {
        String records = "";
        records += "AllGameRecord {\n";

        for (GameRecord record : gameRecords) {
            records += record.toString() + "\n";
        }

        records += "}";
        return records;
    }
}
