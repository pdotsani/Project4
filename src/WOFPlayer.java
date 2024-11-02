abstract class WOFPlayer implements WOFPlayerInterface {
    private String playerId;
    private String previousGuesses = "";

    public WOFPlayer(String playerID) {
        this.playerId = playerID;
    }

    public String playerId() {
        return this.playerId;
    }

    public void reset() {
        this.previousGuesses = "";
    }

    public void setPreviousGuesses(String guesses) {
        this.previousGuesses = guesses;
    }

    public String getPreviousGuesses() {
        return this.previousGuesses;
    }
}
