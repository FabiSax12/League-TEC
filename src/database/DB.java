package database;

import models.Game;
import models.Player;

import java.util.ArrayList;

public abstract class DB {
    private static final String filePath = System.getProperty("user.dir") + "\\src\\database\\data.json";
    private static ArrayList<Player> players;
    private static ArrayList<Game> games;
    private static ArrayList<Character> characters;

    public static void loadData() {}

    public static void saveData(String data) {}

    public static ArrayList<Player> getPlayers() {
        loadData();
        return players;
    }

    public static ArrayList<Game> getGames() {
        loadData();
        return games;
    }

    public static ArrayList<Character> getCharacters() {
        loadData();
        return characters;
    }
}
