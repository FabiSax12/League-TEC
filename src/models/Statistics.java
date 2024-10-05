package models;

public class Statistics {
    private int playedGames;
    private int wins;
    private int losses;
    private double winPercentage;
    private int deadCharacters;
    private int destroyedTowers;

    public Statistics(int playedGames, int wins, int losses, int deadCharacters, int destroyedTowers) {
        this.playedGames = playedGames;
        this.wins = wins;
        this.losses = losses;
        this.winPercentage = playedGames > 0 ? (double) wins / playedGames * 100 : 0;
        this.deadCharacters = deadCharacters;
        this.destroyedTowers = destroyedTowers;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getLosses() {
        return losses;
    }

    public void setLosses(int losses) {
        this.losses = losses;
    }

    public double getWinPercentage() {
        return winPercentage;
    }

    public void setWinPercentage(double winPercentage) {
        this.winPercentage = winPercentage;
    }

    public int getPlayedGames() {
        return playedGames;
    }

    public void setPlayedGames(int playedGames) {
        this.playedGames = playedGames;
    }

    public int getDeadCharacters() {
        return deadCharacters;
    }

    public void setDeadCharacters(int deadCharacters) {
        this.deadCharacters = deadCharacters;
    }

    public int getDestroyedTowers() {
        return destroyedTowers;
    }

    public void setDestroyedTowers(int destroyedTowers) {
        this.destroyedTowers = destroyedTowers;
    }
}
