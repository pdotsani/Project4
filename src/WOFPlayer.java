import java.util.Objects;

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

    @Override
    public String toString() {
        return "PlayerId: " + this.playerId + "[, Guesses: " + this.previousGuesses + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WOFPlayer wofPlayer = (WOFPlayer) o;
        return Objects.equals(playerId, wofPlayer.playerId) && Objects.equals(previousGuesses, wofPlayer.previousGuesses);
    }
}
