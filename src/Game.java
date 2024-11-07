/**
 * Parent class to all games, allows for client to play a sequence of games
 * using playAll, until game options have been extinguished or user terminates
 * the cycle.
 */
abstract class Game {

    /**
     * Starts a game playing cycle and collects GameRecords after each game.
     * Returns the collection of all GameRecords in AllGamesRecord.
     *
     * @return allGamesRecord
     */
    AllGamesRecord playAll() {
        AllGamesRecord allGamesRecord = new AllGamesRecord();
        while (this.playNext()) {
            allGamesRecord.add(this.play());
        }
        return allGamesRecord;
    }

    /**
     * Starts a game and runs through the lifecycle of a game. When a game finishes,
     * a game record is added to the AllGamesRecord for the session.
     *
     * @return GameRecord
     */
    abstract GameRecord play();

    /**
     * Asks a user if they want to keep playing in the session. If not, the playAll loop
     * terminates. If yes, the session continues.
     *
     * @return boolean
     */
    abstract boolean playNext();
}
