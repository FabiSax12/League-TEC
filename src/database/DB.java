package database;

import models.Character;
import models.Game;
import models.Player;
import models.Statistics;
import models.Element;
import models.skills.AttackSkill;
import models.skills.BuffSkill;
import models.skills.HealSkill;
import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class DB {
    private static final String filePath = System.getProperty("user.dir") + "\\src\\database\\data.json";
    private static ArrayList<Player> players = new ArrayList<>();
    private static ArrayList<Game> games = new ArrayList<>();
    private static ArrayList<Character> characters = new ArrayList<>();
    private static int nextPlayerId = 1;

    /**
     * Loads game data including players, games and characters from a JSON file.
     * <p>
     * Clears existing player, game and character lists before loading new data.
     * </p>
     *
     * @throws JSONException if there is an error parsing the JSON data.
     */
    public static void loadData() {
        players.clear();
        games.clear();
        characters.clear();

        try {
            File file = new File(filePath);

            if (file.exists()) {
                InputStream inputStream = new FileInputStream(file);
                String jsonText = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
                JSONObject data = new JSONObject(jsonText);

                parsePlayers(data);
                parseGames(data);
                parseCharacters(data);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Parses player data from a given JSON object and populates the player list.
     *
     * <p>Each player's statistics and ID are also extracted and set. The `nextPlayerId`
     * is updated to ensure new players have unique IDs.</p>
     *
     * @param data the JSON object containing player data.
     */
    private static void parsePlayers(JSONObject data) {
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
    }

    /**
     * Parses game data from a given JSON object and populates the game list.
     *
     * <p>Each game's ID, winner, and participating players are extracted from the JSON data.
     * The method also ensures that the players are linked to each game based on their IDs.</p>
     *
     * @param data the JSON object containing game data.
     */
    private static void parseGames(JSONObject data) {
        JSONArray jsonGames = data.getJSONArray("games");
        for (int i = 0; i < jsonGames.length(); i++) {
            JSONObject jsonGame = jsonGames.getJSONObject(i);

            UUID id = UUID.fromString(jsonGame.getString("id"));
            Player winner = getPlayerByName(jsonGame.getString("winner"));

            ArrayList<Player> playersInGame = new ArrayList<>();
            JSONArray jsonPlayersInGame = jsonGame.getJSONArray("players");
            for (int j = 0; j < jsonPlayersInGame.length(); j++) {
                Player playerInGame = getPlayerById(jsonPlayersInGame.getInt(j));
                if (playerInGame != null) playersInGame.add(playerInGame);
            }

            Game game = new Game(id, playersInGame.get(0), playersInGame.get(1), winner);

            game.setPlayers(playersInGame);
            games.add(game);
        }
    }

    /**
     * Parses character data from a given JSON object and populates the character list.
     *
     * <p>For each character, it extracts properties such as name, health, mana, attack,
     * defense, movements, element, and sprite. Optionally, it sets the level and movements
     * with default values if not present. It also calls {@code parseSkills} to load each
     * character's skills.</p>
     *
     * @param data the JSON object containing character data.
     */
    private static void parseCharacters(JSONObject data) {
        JSONArray jsonCharacters = data.getJSONArray("characters");
        for (int i = 0; i < jsonCharacters.length(); i++) {
            JSONObject jsonCharacter = jsonCharacters.getJSONObject(i);
            Element element = Element.valueOf(jsonCharacter.getString("element").toUpperCase());

            Character character = new Character(
                    jsonCharacter.getString("name"),
                    jsonCharacter.getInt("health"),
                    jsonCharacter.getInt("mana"),
                    jsonCharacter.getInt("attack"),
                    jsonCharacter.getInt("defense"),
                    jsonCharacter.getInt("movements"),
                    element,
                    jsonCharacter.getString("sprite")
            );

            character.setLevel(jsonCharacter.optInt("level", 1));
            character.setMovements(jsonCharacter.optInt("movements", 1));

            parseSkills(jsonCharacter, character);

            characters.add(character);
        }
    }

    /**
     * Parses the skills of a character from a JSON object and adds them to the character.
     *
     * <p>For each skill, it determines its type (attack, buff, or heal) and creates the appropriate
     * skill object (AttackSkill, BuffSkill, or HealSkill), then adds it to the character.</p>
     *
     * @param jsonCharacter the JSON object containing character data, including skills.
     * @param character the character to which the parsed skills will be added.
     */
    private static void parseSkills(JSONObject jsonCharacter, Character character) {
        JSONArray skills = jsonCharacter.getJSONArray("skills");
        for (int j = 0; j < skills.length(); j++) {
            JSONObject skill = skills.getJSONObject(j);
            String name = skill.getString("name");
            int mana = skill.getInt("mana");
            Element skillElement = Element.valueOf(skill.getString("element").toUpperCase());
            String type = skill.getString("type");

            switch (type) {
                case "attack" -> {
                    int damage = skill.getInt("damage");
                    character.addSkill(new AttackSkill(name, damage, mana, skillElement));
                }
                case "buff" -> {
                    String stat = skill.getString("stat");
                    double boost = skill.getDouble("boost");
                    character.addSkill(new BuffSkill(name, stat, boost, mana, skillElement));
                }
                case "heal" -> {
                    int heal = skill.getInt("heal");
                    character.addSkill(new HealSkill(name, heal, mana, skillElement));
                }
            }
        }
    }

    /**\
     * Saves the current game data (players, games, and characters) into a JSON file.
     *
     * <p>This method collects the current state of players, games, and characters,
     * and writes them into a JSON file at the specified file path. If an I/O error
     * occurs during file writing, it will be caught and the stack trace will be printed.</p>
     */
    public static void saveData() {
        try {
            JSONObject data = new JSONObject();

            savePlayers(data);
            saveGames(data);
            saveCharacters(data);

            FileWriter fileWriter = new FileWriter(filePath);
            fileWriter.write(data.toString(4));
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Saves the list of players into the provided JSON object.
     *
     * <p>This method iterates over the list of players, converts each player and
     * their statistics into a JSON format, and adds them to the provided JSON object.</p>
     *
     * @param data the JSON object to which player data will be added.
     */
    public static void savePlayers(JSONObject data) {
        JSONArray jsonPlayers = new JSONArray();

        for (Player player : players) {
            JSONObject jsonPlayer = new JSONObject();
            jsonPlayer.put("id", player.getId());
            jsonPlayer.put("name", player.getName());

            JSONObject stats = new JSONObject();
            Statistics playerStats = player.getStatistics();

            stats.put("playedGames", playerStats.getPlayedGames());
            stats.put("wins", playerStats.getWins());
            stats.put("loses", playerStats.getLosses());
            stats.put("deadCharacters", playerStats.getDeadCharacters());
            stats.put("destroyedTowers", playerStats.getDestroyedTowers());

            jsonPlayer.put("statistics", stats);
            jsonPlayers.put(jsonPlayer);
        }

        data.put("players", jsonPlayers);
    }

    /**
     * Saves the list of games into the provided JSON object.
     *
     * <p>This method iterates over the list of games, converts each game into JSON format,
     * and adds them to the provided JSON object. It includes each game's ID, winner, and
     * the list of participating players' IDs.</p>
     *
     * @param data the JSON object to which game data will be added.
     */
    public static void saveGames(JSONObject data) {
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
    }

    /**
     * Saves the list of characters into the provided JSON object.
     *
     * <p>This method iterates over the list of characters, converts each character
     * into JSON format by calling the {@code toJson()} method on each character,
     * and adds them to the provided JSON object.</p>
     *
     * @param data the JSON object to which character data will be added.
     */
    public static void saveCharacters(JSONObject data) {
        JSONArray jsonCharacters = new JSONArray();
        for (Character character : characters) {
            JSONObject jsonCharacter = character.toJson();
            jsonCharacters.put(jsonCharacter);
        }
        data.put("characters", jsonCharacters);
    }

    /**
     * Adds a new character to the character list.
     *
     * @param newCharacter the character to be added.
     */
    public static void addCharacter(Character newCharacter) {
        characters.add(newCharacter);
    }

    /**
     * Adds a new player profile with the given name, assigns a unique ID,
     * initializes statistics, and saves the data.
     *
     * @param name the name of the new player.
     */
    public static void addProfile(String name) {
        Player newPlayer = new Player(name);
        newPlayer.setId(nextPlayerId++);
        newPlayer.setStatistics(new Statistics(0, 0, 0, 0, 0));
        players.add(newPlayer);
        saveData();
    }

    /**
     * Retrieves the list of player names.
     *
     * @return an ArrayList containing the names of all players.
     */
    public static ArrayList<String> getPlayerNames() {
        ArrayList<String> names = new ArrayList<>();
        for (Player player : players) {
            names.add(player.getName());
        }
        return names;
    }

    /**
     * Retrieves the list of all players.
     *
     * @return an ArrayList of Player objects.
     */
    public static ArrayList<Player> getPlayers() {
        return new ArrayList<>(players);
    }

    /**
     * Retrieves the statistics of a player by their name.
     *
     * @param name the name of the player.
     * @return the Statistics object of the player, or null if not found.
     */
    public static Statistics getPlayerStatisticsByName(String name) {
        Player player = getPlayerByName(name);
        if (player != null) {
            return player.getStatistics();
        }
        return null;
    }

    /**
     * Updates the statistics of a player by their name and saves the data.
     *
     * @param name the name of the player whose statistics will be updated.
     * @param newStats the new Statistics object to replace the current one.
     */
    public static void updatePlayerStatisticsByName(String name, Statistics newStats) {
        Player player = getPlayerByName(name);
        if (player != null) {
            player.setStatistics(newStats);
            saveData();
        } else {
            System.out.println("Jugador no encontrado.");
        }
    }

    /**
     * Retrieves a player by their name.
     *
     * @param name the name of the player.
     * @return the Player object if found, or null if not found.
     */
    public static Player getPlayerByName(String name) {
        for (Player player : players) {
            if (player.getName().equalsIgnoreCase(name)) {
                return player;
            }
        }
        return null;
    }

    /**
     * Retrieves a player by their ID.
     *
     * @param id the ID of the player.
     * @return the Player object if found, or null if not found.
     */
    public static Player getPlayerById(int id) {
        for (Player player : players) if (player.getId() == id) return player;
        return null;
    }

    /**
     * Retrieves the list of all characters.
     *
     * @return a List of Character objects.
     */
    public static List<Character> getCharacters() {
        return new ArrayList<>(characters);
    }

    public static void addGame(Game newGame) {
        games.add(newGame);
    }

    public static void restoreCharacters() {

    }
}
