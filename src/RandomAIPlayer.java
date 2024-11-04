public class RandomAIPlayer extends WOFPlayer {
    public RandomAIPlayer(String playerId) {
        super(playerId);
    }

    @Override
    public char nextGuess(String previousGuesses) {
        super.setPreviousGuesses(previousGuesses);
        char guess;
        do {
            guess = (char)(97 + (int)(Math.random() * 26));
        } while (super.getPreviousGuesses().indexOf(guess) != -1);
        return guess;
    }
}
