package models;

/**
 * Represents the statistics for a player in the game, including the number of played games, wins, losses,
 * win percentage, dead characters, and destroyed towers.
 */
public class Statistics {
    private int playedGames;
    private int wins;
    private int losses;
    private double winPercentage;
    private int deadCharacters;
    private int destroyedTowers;

    /**
     * Constructs a new Statistics object with the given values.
     *
     * @param playedGames the total number of games played.
     * @param wins the number of games won.
     * @param losses the number of games lost.
     * @param deadCharacters the number of dead characters.
     * @param destroyedTowers the number of destroyed towers.
     */
    public Statistics(int playedGames, int wins, int losses, int deadCharacters, int destroyedTowers) {
        this.playedGames = playedGames;
        this.wins = wins;
        this.losses = losses;
        this.winPercentage = playedGames > 0 ? (double) ((wins / playedGames ) * 100) : 0;
        this.deadCharacters = deadCharacters;
        this.destroyedTowers = destroyedTowers;
    }

    /**
     * Returns the number of wins.
     *
     * @return the number of wins.
     */
    public int getWins() {
        return wins;
    }

    /**
     * Sets the number of wins.
     *
     * @param wins the new number of wins.
     */
    public void setWins(int wins) {
        this.wins = wins;
    }

    /**
     * Returns the number of losses.
     *
     * @return the number of losses.
     */
    public int getLosses() {
        return losses;
    }

    /**
     * Sets the number of losses.
     *
     * @param losses the new number of losses.
     */
    public void setLosses(int losses) {
        this.losses = losses;
    }

    /**
     * Returns the win percentage.
     *
     * @return the win percentage as a double value.
     */
    public double getWinPercentage() {
        return winPercentage;
    }

    /**
     * Sets the win percentage.
     *
     * @param winPercentage the new win percentage.
     */
    public void setWinPercentage(double winPercentage) {
        this.winPercentage = winPercentage;
    }

    /**
     * Returns the total number of games played.
     *
     * @return the number of played games.
     */
    public int getPlayedGames() {
        return playedGames;
    }

    /**
     * Sets the total number of games played.
     *
     * @param playedGames the new total number of played games.
     */
    public void setPlayedGames(int playedGames) {
        this.playedGames = playedGames;
    }

    /**
     * Returns the number of dead characters.
     *
     * @return the number of dead characters.
     */
    public int getDeadCharacters() {
        return deadCharacters;
    }

    /**
     * Sets the number of dead characters.
     *
     * @param deadCharacters the new number of dead characters.
     */
    public void setDeadCharacters(int deadCharacters) {
        this.deadCharacters = deadCharacters;
    }

    /**
     * Returns the number of destroyed towers.
     *
     * @return the number of destroyed towers.
     */
    public int getDestroyedTowers() {
        return destroyedTowers;
    }

    /**
     * Sets the number of destroyed towers.
     *
     * @param destroyedTowers the new number of destroyed towers.
     */
    public void setDestroyedTowers(int destroyedTowers) {
        this.destroyedTowers = destroyedTowers;
    }

    public void incrementDeadCharacters() {
        this.deadCharacters++;
    }

    public void incrementDestroyedTowers() {
        this.destroyedTowers++;
    }

    public void incrementWins() {
        this.wins++;
    }

    public void incrementLosses() {
        this.losses++;
    }
}