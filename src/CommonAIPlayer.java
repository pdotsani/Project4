public class CommonAIPlayer extends WOFPlayer {
    private final String COMMON = "ETAOINSHRD";
    private final String OTHER = "BCFGJKLMPQUVWXYZ";

    public CommonAIPlayer(String playerId) {
        super(playerId);
    }

    @Override
    public char nextGuess(String previousGuesses) {
        super.setPreviousGuesses(previousGuesses);
        char guess;

        if (Math.random() > 0.7) {
            do {
                boolean upperCase = Math.random() > 0.5;
                int idx = (int)(Math.random() * COMMON.length());
                guess = upperCase ? COMMON.charAt(idx) : Character.toLowerCase(COMMON.charAt(idx));
            } while (super.getPreviousGuesses().indexOf(guess) != -1);
            return guess;
        } else {
            do {
                boolean upperCase = Math.random() > 0.5;
                int idx = (int)(Math.random() * OTHER.length());
                guess = upperCase ? OTHER.charAt(idx) : Character.toLowerCase(OTHER.charAt(idx));
            } while (super.getPreviousGuesses().indexOf(guess) != -1);
            return guess;
        }
    }
}
