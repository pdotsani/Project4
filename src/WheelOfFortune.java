import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

abstract class WheelOfFortune extends Game {
    private List<String> phraseList;
    private String phrase;
    private List<Integer> phraseIndexList = new ArrayList<>();
    private String hiddenPhrase;
    private String previousGuesses = "";
    private int lives;
    private boolean win;
    private boolean lose;
    private int score = 0;
    private String playerId;

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
        this.phrase = phraseList.get(r);
        phraseIndexList.add(r);
    }

    public void generateHiddenPhrase() {
        // Created masked string for user "Game Board"
        String hiddenPhrase = "";

        for (char c : this.phrase.toCharArray()) {
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

        this.hiddenPhrase = hiddenPhrase;
    }

    abstract char getGuess(String previousGuesses);

    public boolean processGuess(char guess) {
        StringBuilder sb = new StringBuilder(this.hiddenPhrase);

        if (this.phrase.indexOf(guess) == -1) {
            return false;
        }

        for (int i = 0; i < this.phrase.length(); i++) {
            char c = this.phrase.charAt(i);
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
        this.hiddenPhrase = sb.toString();
        return true;
    }

    public boolean didUserWin() {
        return this.phrase.compareTo(this.hiddenPhrase) == 0;
    }

    public void setLives(int lives) {
        this.lives = lives;
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

    public int getScore() {
        return this.score;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public String getPlayerId() {
        return this.playerId;
    }

    @Override
    public boolean playNext() {
        if (phraseIndexList.isEmpty()) {
            return true;
        } else if (phraseIndexList.size() > phraseList.size()) {
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

        this.setLives(5);
        this.setWin(false);
        this.setLose(false);
        this.randomPhrase();
        this.generateHiddenPhrase();

        System.out.println("Welcome to Wheel of this!");
        System.out.printf("Guess the phrase... you can make %d mistakes!", this.lives);
        System.out.println();

        while (!this.win && !this.lose) {
            System.out.println();
            System.out.println(this.hiddenPhrase);
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
                        this.lives--;

                        if (this.lives == 0) {
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

            System.out.println("You have " + this.lives + " " + (this.lives != 1 ? "chances" : "chance") + " left!");
        }

        String gameOverPhrase = this.win ? winningPhrase : loosingPhrase;
        System.out.println(gameOverPhrase);
        if (this.didUserWin()) {
            System.out.println(this.hiddenPhrase);
        }
    }
}
