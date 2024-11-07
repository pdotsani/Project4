import java.util.Objects;

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

    abstract void randomPhrase();

    abstract void generateHiddenPhrase();
}