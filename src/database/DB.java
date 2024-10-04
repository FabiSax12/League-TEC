package database;

import models.Character;
import models.Game;
import models.Player;
import models.Statistics;
import models.Element;
import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class DB {
    private static final String filePath = System.getProperty("user.dir") + "\\src\\database\\data.json";
    private static final ArrayList<Player> players = new ArrayList<>();
    private static final ArrayList<Game> games = new ArrayList<>();
    private static final ArrayList<Character> characters = new ArrayList<>();
    private static int nextPlayerId = 1;

    public static void loadData() {
        try {
            File file = new File(filePath);
            if (file.exists()) {
                InputStream inputStream = new FileInputStream(file);
                String jsonText = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
                JSONObject data = new JSONObject(jsonText);

                JSONArray jsonPlayers = data.getJSONArray("players");
                for (int i = 0; i < jsonPlayers.length(); i++) {
                    JSONObject jsonPlayer = jsonPlayers.getJSONObject(i);
                    Player player = new Player(jsonPlayer.getString("name"));
                    player.setId(jsonPlayer.getInt("id"));
                    JSONObject stats = jsonPlayer.getJSONObject("statistics");
                    player.setStatistics(new Statistics(
                            stats.getInt("playedGames"),
                            stats.getInt("wins"),
                            stats.getInt("loses"),
                            stats.getInt("deadCharacters"),
                            stats.getInt("destroyedTowers")
                    ));
                    players.add(player);
                    nextPlayerId = Math.max(nextPlayerId, player.getId() + 1);
                }

                JSONArray jsonGames = data.getJSONArray("games");
                for (int i = 0; i < jsonGames.length(); i++) {
                    JSONObject jsonGame = jsonGames.getJSONObject(i);
                    Game game = new Game(UUID.randomUUID(), null, null, null, new Statistics(0,0,0,0,0));
                    game.setId(UUID.fromString(jsonGame.getString("id")));
                    Player winner = getPlayerByName(jsonGame.getString("winner"));
                    game.setWinner(winner);

                    ArrayList<Player> playersInGame = new ArrayList<>();
                    JSONArray jsonPlayersInGame = jsonGame.getJSONArray("players");
                    for (int j = 0; j < jsonPlayersInGame.length(); j++) {
                        Player playerInGame = getPlayerById(jsonPlayersInGame.getInt(j));
                        if (playerInGame != null) {
                            playersInGame.add(playerInGame);
                        }
                    }
                    game.setPlayers(playersInGame);
                    games.add(game);
                }

                JSONArray jsonCharacters = data.getJSONArray("characters");
                for (int i = 0; i < jsonCharacters.length(); i++) {
                    JSONObject jsonCharacter = jsonCharacters.getJSONObject(i);
                    Element element = Element.valueOf(jsonCharacter.getString("element").toUpperCase());
                    Character character = new Character(
                            jsonCharacter.getString("name"),
                            jsonCharacter.getInt("health"),
                            jsonCharacter.getInt("mana"),
                            jsonCharacter.getInt("attack"),
                            element
                    );

                    character.setLevel(jsonCharacter.optInt("level", 1));
                    character.setMovements(jsonCharacter.optInt("movements", 1));

                    characters.add(character);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void saveData() {
        try {
            JSONObject data = new JSONObject();
            JSONArray jsonPlayers = new JSONArray();
            for (Player player : players) {
                JSONObject jsonPlayer = new JSONObject();
                jsonPlayer.put("id", player.getId());
                jsonPlayer.put("name", player.getName());
                JSONObject stats = new JSONObject();
                stats.put("playedGames", player.getStatistics().getPlayedGames());
                stats.put("wins", player.getStatistics().getWins());
                stats.put("loses", player.getStatistics().getLosses());
                stats.put("deadCharacters", player.getStatistics().getDeadCharacters());
                stats.put("destroyedTowers", player.getStatistics().getDestroyedTowers());
                jsonPlayer.put("statistics", stats);
                jsonPlayers.put(jsonPlayer);
            }
            data.put("players", jsonPlayers);

            JSONArray jsonGames = new JSONArray();
            for (Game game : games) {
                JSONObject jsonGame = new JSONObject();
                jsonGame.put("id", game.getId());
                jsonGame.put("winner", game.getWinner().getName());

                JSONArray jsonPlayersInGame = new JSONArray();
                for (Player player : game.getPlayers()) {
                    jsonPlayersInGame.put(player.getId());
                }
                jsonGame.put("players", jsonPlayersInGame);
                jsonGames.put(jsonGame);
            }
            data.put("games", jsonGames);

            JSONArray jsonCharacters = new JSONArray();
            for (Character character : characters) {
                JSONObject jsonCharacter = character.toJson();
                jsonCharacters.put(jsonCharacter);
            }
            data.put("characters", jsonCharacters);

            FileWriter fileWriter = new FileWriter(filePath);
            fileWriter.write(data.toString(4));
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void addProfile(String name) {
        Player newPlayer = new Player(name);
        newPlayer.setId(nextPlayerId++);
        newPlayer.setStatistics(new Statistics(0, 0, 0, 0, 0));
        players.add(newPlayer);
        saveData();
    }

    public static ArrayList<String> getPlayerNames() {
        loadData();
        ArrayList<String> names = new ArrayList<>();
        for (Player player : players) {
            names.add(player.getName());
        }
        return names;
    }

    public static ArrayList<Player> getPlayers() {
        return new ArrayList<>(players);
    }

    public static Statistics getPlayerStatisticsByName(String name) {
        Player player = getPlayerByName(name);
        if (player != null) {
            return player.getStatistics();
        }
        return null;
    }

    public static void updatePlayerStatisticsByName(String name, Statistics newStats) {
        Player player = getPlayerByName(name);
        if (player != null) {
            player.setStatistics(newStats);
            saveData();
        } else {
            System.out.println("Jugador no encontrado.");
        }
    }

    public static Player getPlayerByName(String name) {
        for (Player player : players) {
            if (player.getName().equalsIgnoreCase(name)) {
                return player;
            }
        }
        return null;
    }

    public static Player getPlayerById(int id) {
        for (Player player : players) {
            if (player.getId() == id) {
                return player;
            }
        }
        return null;
    }

    public static List<Character> getCharacters() {
        return new ArrayList<>(characters);
    }
}
