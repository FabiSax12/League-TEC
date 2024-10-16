package models;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Represents a game between two players, with information about the winner and game statistics.
 */
public class Game {
    private UUID id;
    private Player player1;
    private Player player2;
    private Player winner;
    private Statistics statistics;

    /**
     * Constructs a new Game with the specified players, winner, and statistics.
     *
     * @param id the unique identifier for the game.
     * @param player1 the first player in the game.
     * @param player2 the second player in the game.
     * @param winner the player who won the game.
     * @param statistics the statistics related to the game.
     */
    public Game(UUID id, Player player1, Player player2, Player winner, Statistics statistics) {
        this.id = id;
        this.player1 = player1;
        this.player2 = player2;
        this.winner = winner;
        this.statistics = statistics;
    }

    /**
     * Returns the unique identifier of the game.
     *
     * @return the game's UUID.
     */
    public UUID getId() {
        return id;
    }

    /**
     * Sets the unique identifier for the game.
     *
     * @param id the UUID to be assigned to the game.
     */
    public void setId(UUID id) {
        this.id = id;
    }

    /**
     * Returns the first player in the game.
     *
     * @return the first player.
     */
    public Player getPlayer1() {
        return player1;
    }

    /**
     * Sets the first player in the game.
     *
     * @param player1 the player to set as player 1.
     */
    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }

    /**
     * Returns the second player in the game.
     *
     * @return the second player.
     */
    public Player getPlayer2() {
        return player2;
    }

    /**
     * Sets the second player in the game.
     *
     * @param player2 the player to set as player 2.
     */
    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }

    /**
     * Returns a list of both players in the game.
     *
     * @return a list containing player 1 and player 2.
     */
    public List<Player> getPlayers() {
        ArrayList<Player> players = new ArrayList<>();
        if (player1 != null) players.add(player1);
        if (player2 != null) players.add(player2);
        return players;
    }

    /**
     * Sets both players in the game using an array list.
     *
     * @param players a list of players, where the first player is set as player 1
     *                and the second player is set as player 2.
     */
    public void setPlayers(ArrayList<Player> players) {
        if (players.size() >= 2) {
            this.player1 = players.get(0);
            this.player2 = players.get(1);
        }
    }

    /**
     * Returns the player who won the game.
     *
     * @return the winner of the game.
     */
    public Player getWinner() {
        return winner;
    }

    /**
     * Sets the winner of the game.
     *
     * @param winner the player who won the game.
     */
    public void setWinner(Player winner) {
        this.winner = winner;
    }

    /**
     * Returns the statistics associated with the game.
     *
     * @return the game's statistics.
     */
    public Statistics getStatistics() {
        return statistics;
    }

    /**
     * Sets the statistics for the game.
     *
     * @param statistics the statistics to assign to the game.
     */
    public void setStatistics(Statistics statistics) {
        this.statistics = statistics;
    }
}
