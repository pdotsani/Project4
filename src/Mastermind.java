import java.util.Scanner;

public class Mastermind extends GuessingGame {
    final private char RED = 'R';
    final private char GREEN = 'G';
    final private char BLUE = 'B';
    final private char YELLOW = 'Y';
    final private char ORANGE = 'O';
    final private char PURPLE = 'P';
    private boolean active = true;
    private int score = 0;
    private String playerId;

    @Override
    public String toString() {
        return "GuessingGame{" +
                "phrase='" + super.getPhrase() + '\'' +
                ", hiddenPhrase='" + super.getHiddenPhrase() + '\'' +
                ", lives=" + super.getLives() +
                '}';
    }



    @Override
    public void randomPhrase() {
        char[] choices = {RED, GREEN, BLUE, YELLOW, ORANGE, PURPLE};
        String phrase = "";
        for (int i = 0; i < 4; i++) {
            int rand = (int)(Math.random() * 5);
            phrase += choices[rand];
        }
        super.setPhrase(phrase);
    }

    @Override
    public void generateHiddenPhrase() {
        super.setHiddenPhrase("****");
    }

    public boolean processGuess(String guess) {
        StringBuilder hiddenPhrase = new StringBuilder(super.getHiddenPhrase());
        int exact = 0;
        int partial = 0;

        for (int i = 0; i < super.getPhrase().length(); i++) {
            if (guess.charAt(i) == super.getPhrase().charAt(i)) {
                hiddenPhrase.setCharAt(i,guess.charAt(i));
                exact += 1;
                this.score =+ 2;
            }
        }

        super.setHiddenPhrase(hiddenPhrase.toString());

        partial += this.checkPartials(new StringBuilder(super.getPhrase()), new StringBuilder(guess));

        System.out.println(exact + " exact, " + partial + " partial");

        return exact > 0 || partial > 0;
    }

    @Override
    public boolean playNext() {
        System.out.println("Do you want to play again? (y/n)");
        Scanner in = new Scanner(System.in);
        char response =  in.nextLine().charAt(0);
        return response == 'y';
    }

    public int checkPartials(StringBuilder secretSB, StringBuilder guessSB) {
        int i = 0;

        int partials = 0;
        while (i < 4) {
            int j = 0;
            while (j < 4) {
                if (secretSB.charAt(i) == guessSB.charAt(j) && !(super.getHiddenPhrase().charAt(i) == guessSB.charAt(j))) {
                    partials = partials + 1;
                    this.score++;
                    secretSB.setCharAt(i,'-');
                    guessSB.setCharAt(j,'*');
                }
                j++;
            }
            i++;
        }
        return partials;
    }

    public String getGuess() {
        Scanner in = new Scanner(System.in);
        return in.nextLine();
    }

    @Override
    public GameRecord play() {
        super.setLives(5);
        this.randomPhrase();
        this.generateHiddenPhrase();

        System.out.println("Welcome to Mastermind!");
        System.out.println("What's your name?");
        Scanner in = new Scanner(System.in);
        this.playerId = in.nextLine();

        while (!super.getPhrase().equals(super.getHiddenPhrase()) && super.getLives() > 0) {
            System.out.println("Please enter your choice, 4 letters of (R,G,B,Y,O,P):");
            System.out.println(super.getHiddenPhrase());
            String guess = this.getGuess();

            if (this.processGuess(guess)) {
                System.out.println("Good guess!");
            } else {
                System.out.println("no match found");
                int newLives = super.getLives();
                super.setLives(newLives - 1);
                System.out.println("lives: " + super.getLives());
            }
        }

        if (!super.getPhrase().equals(super.getHiddenPhrase())) {
            System.out.println("You lost!");
        } else {
            System.out.println("You won!");
            this.score += 10;
        }

        return new GameRecord(this.score, this.playerId);
    }

    public static void main(String[] args) {
        Mastermind m = new Mastermind();
        AllGamesRecord record = m.playAll();
        System.out.println(record);

    }
}
