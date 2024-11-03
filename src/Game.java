abstract class Game {
    AllGamesRecord playAll() {
        AllGamesRecord allGamesRecord = new AllGamesRecord();
        while (this.playNext()) {
            allGamesRecord.add(this.play());
        }
        return allGamesRecord;
    }
    abstract GameRecord play();
    abstract boolean playNext();
}
