package models;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Game {
    private UUID id;
    private Player player1;
    private Player player2;
    private Player winner;
    private Statistics statistics;

    public Game(UUID id, Player player1, Player player2, Player winner, Statistics statistics) {
        this.id = id;
        this.player1 = player1;
        this.player2 = player2;
        this.winner = winner;
        this.statistics = statistics;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Player getPlayer1() {
        return player1;
    }

    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }

    public List<Player> getPlayers() {
        ArrayList<Player> players = new ArrayList<>();
        if (player1 != null) players.add(player1);
        if (player2 != null) players.add(player2);
        return players;
    }

    public void setPlayers(ArrayList<Player> players) {
        if (players.size() >= 2) {
            this.player1 = players.get(0);
            this.player2 = players.get(1);
        }
    }

    public Player getWinner() {
        return winner;
    }

    public void setWinner(Player winner) {
        this.winner = winner;
    }

    public Statistics getStatistics() {
        return statistics;
    }

    public void setStatistics(Statistics statistics) {
        this.statistics = statistics;
    }
}
