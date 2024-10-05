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

    private static void parseGames(JSONObject data) {
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
    }

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
                    element,
                    jsonCharacter.getString("sprite")
            );

            character.setLevel(jsonCharacter.optInt("level", 1));
            character.setMovements(jsonCharacter.optInt("movements", 1));

            parseSkills(jsonCharacter, character);

            characters.add(character);
        }
    }

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

    public static void saveCharacters(JSONObject data) {
        JSONArray jsonCharacters = new JSONArray();
        for (Character character : characters) {
            JSONObject jsonCharacter = character.toJson();
            jsonCharacters.put(jsonCharacter);
        }
        data.put("characters", jsonCharacters);
    }

    public static void addProfile(String name) {
        Player newPlayer = new Player(name);
        newPlayer.setId(nextPlayerId++);
        newPlayer.setStatistics(new Statistics(0, 0, 0, 0, 0));
        players.add(newPlayer);
        saveData();
    }

    public static ArrayList<String> getPlayerNames() {
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
        for (Player player : players) if (player.getId() == id) return player;
        return null;
    }

    public static List<Character> getCharacters() {
        return new ArrayList<>(characters);
    }
}
