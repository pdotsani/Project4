import java.util.ArrayList;

/**
 * WOF game instance that uses AI players. Can take in a default AI,
 * selected AI, or an array of AIs.
 */
public class WOFAIGame extends WheelOfFortune {
    ArrayList<WOFPlayerInterface> players = new ArrayList<>();
    WOFPlayerInterface currentPlayer;

    public WOFAIGame(){
        players.add(new PGAIPlayer("PGAI"));
    }

    public WOFAIGame(WOFPlayerInterface player) {
        players.add(player);
    }

    public WOFAIGame(ArrayList<WOFPlayerInterface> players){
        this.players.addAll(players);
    }

    /**
     * Uses the given AI players guessing algorithm to submit the guess.
     *
     * @param previousGuesses
     * @return char
     */
    @Override
    public char getGuess(String previousGuesses) {
        return currentPlayer.nextGuess(previousGuesses);
    }

    @Override
    public GameRecord play() {
        super.setPlayerId(currentPlayer.playerId());
        super.run();
        return new GameRecord(super.getScore(), super.getPlayerId());
    }

    @Override
    public boolean playNext() {
        if (super.getPhraseIndexList().isEmpty()) {
            return true;
        } else if (super.getPhraseIndexList().size() < super.getPhraseList().size()) {
            System.out.println("Do you want to play Wheel of Fortune? (y/n) y");
            return true;
        } else {
            System.out.println("That's all the games we got today!");
            return false;
        }
    }

    @Override
    public AllGamesRecord playAll() {
        AllGamesRecord allGamesRecord = new AllGamesRecord();
        for(WOFPlayerInterface player : players){
            this.currentPlayer = player;
            while(this.playNext()) {
                allGamesRecord.add(this.play());
            }
            super.clearPhraseIndexList();
        }
        return allGamesRecord;
    }

    public static void main(String[] args) {
        WOFAIGame game = new WOFAIGame();
        AllGamesRecord record = game.playAll();
        System.out.println(record.average());

        WOFAIGame game2 = new WOFAIGame(new PGAIPlayer("PGAI2"));
        AllGamesRecord record2 = game2.playAll();
        System.out.println(record2.average());

        ArrayList<WOFPlayerInterface> playerArr = new ArrayList<>();
        playerArr.add(new PGAIPlayer("AIONE"));
        playerArr.add(new CommonAIPlayer("CAI"));
        playerArr.add(new CommonAIPlayer("CAI2"));
        WOFAIGame game3 = new WOFAIGame(playerArr);
        AllGamesRecord record3 = game3.playAll();
        System.out.println(record3.average());
    }
}
