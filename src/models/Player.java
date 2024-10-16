package models;

import java.util.ArrayList;

/**
 * Represents a player in the game, with a unique ID, name, statistics, and a list of games they have participated in.
 */
public class Player {
    private int id;
    private String name;
    private Statistics statistics;
    private ArrayList<Game> games;

    /**
     * Constructs a new Player with the specified name.
     *
     * @param name the name of the player.
     */
    public Player(String name) {
        this.name = name;
    }

    /**
     * Sets the player's unique ID.
     *
     * @param id the ID to assign to the player.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returns the player's unique ID.
     *
     * @return the player's ID.
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the player's name.
     *
     * @return the player's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the player's name.
     *
     * @param name the new name of the player.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the player's statistics.
     *
     * @return the statistics of the player.
     */
    public Statistics getStatistics() {
        return statistics;
    }

    /**
     * Sets the player's statistics.
     *
     * @param statistics the new statistics to assign to the player.
     */
    public void setStatistics(Statistics statistics) {
        this.statistics = statistics;
    }

    /**
     * Returns the list of games the player has participated in.
     *
     * @return an ArrayList of Game objects representing the games played by the player.
     */
    public ArrayList<Game> getGames() {
        return games;
    }

    /**
     * Sets the list of games the player has participated in.
     *
     * @param games an ArrayList of Game objects representing the games played by the player.
     */
    public void setGames(ArrayList<Game> games) {
        this.games = games;
    }

    /**
     * Returns a string representation of the player, which is the player's name.
     *
     * @return the player's name as a string.
     */
    @Override
    public String toString() {
        return this.name;
    }
}
