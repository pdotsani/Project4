import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

abstract class WheelOfFortune extends GuessingGame {
    private List<String> phraseList;
    private List<Integer> phraseIndexList = new ArrayList<>();
    private String previousGuesses = "";
    private boolean win;
    private boolean lose;
    private int score = 0;
    private String playerId;

    @Override
    public String toString() {
        return "Wheel Of Fortune[" + playerId +
                ", hiddenPhrase" + super.getHiddenPhrase() +
                ", score" + score +
                ", lives" + super.getLives() + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WheelOfFortune that = (WheelOfFortune) o;
        return super.getLives() == that.getLives() && win == that.win && lose == that.lose && score == that.score && Objects.equals(phraseList, that.phraseList) && Objects.equals(super.getPhrase(), that.getPhrase()) && Objects.equals(phraseIndexList, that.phraseIndexList) && Objects.equals(super.getHiddenPhrase(), that.getHiddenPhrase()) && Objects.equals(previousGuesses, that.previousGuesses) && Objects.equals(playerId, that.playerId);
    }

    @Override
    public void randomPhrase() {
        if (phraseList != null && phraseIndexList.size() == phraseList.size()) {
            return;
        }
        // Get the phrase from a file of phrases
        try {
            phraseList = Files.readAllLines(Paths.get("phrases.txt"));
        } catch (IOException e) {
            System.out.println(e);
        }

        // Get a random phrase from the list
        Random rand = new Random();
        int r = rand.nextInt(phraseList.size());
        while (phraseIndexList.contains(r)) {
            r = rand.nextInt(phraseList.size());
        }
        super.setPhrase(phraseList.get(r));
        phraseIndexList.add(r);
    }

    @Override
    public void generateHiddenPhrase() {
        // Created masked string for user "Game Board"
        String hiddenPhrase = "";

        for (char c : super.getPhrase().toCharArray()) {
            if (c != ' ') {
                if (Character.isLetter(c)) {
                    hiddenPhrase += '*';
                } else {
                    hiddenPhrase += c;
                }
            } else {
                hiddenPhrase += ' ';
            }
        }

        super.setHiddenPhrase(hiddenPhrase);
    }

    /**
     * A reusable getGuess function. Human user will enter input. AI player will submit
     * a guess based on AI logic.
     *
     * @param previousGuesses
     * @return char: guess
     */
    abstract char getGuess(String previousGuesses);

    public boolean processGuess(char guess) {
        StringBuilder sb = new StringBuilder(super.getHiddenPhrase());

        if (super.getPhrase().indexOf(guess) == -1) {
            return false;
        }

        for (int i = 0; i < super.getPhrase().length(); i++) {
            char c = super.getPhrase().charAt(i);
            if (!Character.isLetter(c) && c == ' ') {
                sb.setCharAt(i, ' ');
            } else if (!Character.isLetter(c) && c != ' ') {
                sb.setCharAt(i, c);
            } else if (this.previousGuesses.indexOf(c) != -1 || this.previousGuesses.indexOf(Character.toLowerCase(c)) != -1) {
                sb.setCharAt(i, c);
            } else if (this.previousGuesses.indexOf(c) == -1) {
                sb.setCharAt(i, '*');
            }
        }

        // return updated hiddenString and returns match true
        super.setHiddenPhrase(sb.toString());
        return true;
    }

    public boolean didUserWin() {
        return super.getPhrase().compareTo(super.getHiddenPhrase()) == 0;
    }

    public void setWin(boolean win) {
        this.win = win;
    }

    public void setLose(boolean lose) {
        this.lose = lose;
    }

    public void addScore(int score) {
        this.score += score;
    }

    public void resetScore() {
        this.score = 0;
    }

    public int getScore() {
        return this.score;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public String getPlayerId() {
        return this.playerId;
    }

    public void resetPreviousGuesses() {
        this.previousGuesses = "";
    }

    public void clearPhraseIndexList() {
        this.phraseIndexList.clear();
    }

    public List<String> getPhraseList() {
        return this.phraseList;
    }

    public List<Integer> getPhraseIndexList() {
        return this.phraseIndexList;
    }

    @Override
    public boolean playNext() {
        if (phraseIndexList.isEmpty()) {
            return true;
        } else if (phraseIndexList.size() < phraseList.size()) {
            Scanner in = new Scanner(System.in);
            System.out.println("Do you want to play Wheel of Fortune? (y/n)");
            char response = in.nextLine().charAt(0);
            return response == 'y';
        } else {
            return false;
        }
    }

    @Override
    public GameRecord play() {
        Scanner in = new Scanner(System.in);
        System.out.println("What is your name?");
        this.setPlayerId(in.nextLine());
        this.run();
        return new GameRecord(this.getScore(), this.getPlayerId());
    }

    public void run() {
        // Declare game over messages
        String winningPhrase = "Congrats you won!";
        String loosingPhrase = "Better luck next time!";

        this.resetScore();
        this.setLives(5);
        this.setWin(false);
        this.setLose(false);
        this.randomPhrase();
        this.generateHiddenPhrase();
        this.resetPreviousGuesses();

        System.out.println("Welcome to Wheel of Fortune " + this.playerId + "!");
        System.out.printf("Guess the phrase... you can make %d mistakes!", super.getLives());
        System.out.println();

        while (!this.win && !this.lose) {
            System.out.println();
            System.out.println(super.getHiddenPhrase());
            System.out.println("You have guessed these letters: " + this.previousGuesses);
            System.out.print("Choose a letter: ");
            char guess = this.getGuess(this.previousGuesses);

            if (guess >= 'a' && guess <= 'z' || guess >= 'A' && guess <= 'Z') {
                if (this.previousGuesses.indexOf(Character.toLowerCase(guess)) != -1) {
                    System.out.println("You guessed  the letter " + guess + " already!");
                } else {
                    this.previousGuesses += Character.toLowerCase(guess);
                    boolean correct = this.processGuess(guess);

                    if (!correct) {
                        System.out.println("The letter " + guess + " is not in the phrase!");
                        int newLives = super.getLives() - 1;
                        super.setLives(newLives);

                        if (super.getLives() == 0) {
                            this.setLose(true);
                        }
                    } else {
                        this.addScore(5);
                        if (this.didUserWin()) {
                            this.setWin(true);
                            this.addScore(50);
                        }
                    }
                }
            } else {
                System.out.println("You did not enter a letter!");
            }

            System.out.println("You have " + super.getLives() + " " + (super.getLives() != 1 ? "chances" : "chance") + " left!");
        }

        String gameOverPhrase = this.win ? winningPhrase : loosingPhrase;
        System.out.println(gameOverPhrase);
        if (this.didUserWin()) {
            System.out.println(super.getHiddenPhrase());
        }
    }
}
