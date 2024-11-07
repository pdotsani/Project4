import java.util.Objects;

/**
 * A descendent of Game, the parent class to both guessing games in the repository.
 * Common attributes consist of phrase, hiddenPhrase, and lives.
 */
abstract class GuessingGame extends Game {
    private String phrase;
    private String hiddenPhrase;
    private int lives;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GuessingGame that = (GuessingGame) o;
        return lives == that.lives && Objects.equals(phrase, that.phrase) && Objects.equals(hiddenPhrase, that.hiddenPhrase);
    }

    public void setPhrase(String phrase) {
        this.phrase = phrase;
    }

    public String getPhrase() {
        return this.phrase;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public int getLives() {
        return this.lives;
    }

    public void setHiddenPhrase(String hiddenPhrase) { this.hiddenPhrase = hiddenPhrase; }

    public String getHiddenPhrase() { return this.hiddenPhrase; }

    /**
     * Generates the random phrase that will be set for the game.
     */
    abstract void randomPhrase();

    /**
     * Generates the visible hidden phrase that is created from the ramdomPhase method.
     */
    abstract void generateHiddenPhrase();
}