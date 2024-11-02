public class RandomAIPlayer extends WOFPlayer {
    public RandomAIPlayer(String playerId) {
        super(playerId);
    }

    @Override
    public char nextGuess(String previousGuesses) {
        super.setPreviousGuesses(previousGuesses);
        char guess;
        do {
            boolean upperCase = Math.random() < 0.2;
            int start = upperCase ? 65 : 97;
            guess = (char)(start + (int)(Math.random() * 26));
        } while (super.getPreviousGuesses().indexOf(guess) != -1);
        return guess;
    }
}
