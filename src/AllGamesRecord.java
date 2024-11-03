import java.util.ArrayList;
import java.util.List;

public class AllGamesRecord {
    ArrayList<GameRecord> gameRecords = new ArrayList<>();

    public void add(GameRecord record) {
        gameRecords.add(record);
    }

    public int average() {
        int sum = 0;
        int count = 0;
        for (GameRecord record : gameRecords) {
            sum += record.getScore();
            count++;
        }
        return sum / count;
    }

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

    public List<Integer> highGameList(int n) {
        ArrayList<Integer> highGameList = new ArrayList<>();
        gameRecords.sort(GameRecord::compareTo);
        for (int i = 0; i < n; i++) {
            highGameList.add(i);
        }
        return highGameList;
    }

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
