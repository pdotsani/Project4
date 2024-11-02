import java.util.Scanner;

public class WOFUserGame extends WheelOfFortune {
    @Override
    public char getGuess(String previousGuesses) {
        Scanner in = new Scanner(System.in);
        return in.nextLine().charAt(0);
    }

    public static void main(String[] args) {
        WOFUserGame wofUserGame = new WOFUserGame();
        AllGamesRecord record = wofUserGame.playAll();
        System.out.println(record.average());
    }
}