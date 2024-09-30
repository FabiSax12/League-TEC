package database;

import models.Element;
import models.Game;
import models.Player;
import models.Statistics;
import models.Character;
import models.skills.AttackSkill;
import models.skills.BuffSkill;
import models.skills.HealSkill;

import java.util.ArrayList;

public abstract class DB {
    private static final String filePath = System.getProperty("user.dir") + "\\src\\database\\data.json";
    private static ArrayList<Player> players = new ArrayList<>();
    private static ArrayList<Game> games = new ArrayList<>();
    private static ArrayList<Character> characters = new ArrayList<>();

    public static void loadData() {
        players = new ArrayList<>();
        games = new ArrayList<>();
        characters = new ArrayList<>();

        players.add(new Player("User 1"));
        players.add(new Player("User 2"));
        players.add(new Player("User 3"));
        players.get(0).setStatistics(new Statistics(10, 6, 4, 20, 5));
        players.get(1).setStatistics(new Statistics(8, 4, 4, 15, 3));
        players.get(2).setStatistics(new Statistics(12, 8, 4, 25, 6));

        AttackSkill fireball = new AttackSkill("Bola de Fuego", 50, 20, Element.FIRE);
        AttackSkill rockSword = new AttackSkill("Espadazo de Piedra", 45, 15, Element.GROUND);
        AttackSkill waterWhip = new AttackSkill("Látigo de Agua", 40, 12, Element.WATER);
        AttackSkill airBlade = new AttackSkill("Hoja de Viento", 35, 10, Element.AIR);
        AttackSkill lavaBurst = new AttackSkill("Explosión de Lava", 60, 25, Element.FIRE);
        AttackSkill sandstormStrike = new AttackSkill("Golpe de Tormenta de Arena", 55, 20, Element.GROUND);
        AttackSkill tidalWave = new AttackSkill("Ola Gigante", 70, 30, Element.WATER);
        AttackSkill lightningBolt = new AttackSkill("Rayo Fulminante", 65, 22, Element.AIR);
        AttackSkill meteorShower = new AttackSkill("Lluvia de Meteoros", 80, 35, Element.FIRE);

        HealSkill healingLight = new HealSkill("Luz Sanadora", 30, 15, Element.WATER);
        HealSkill rejuvenatingMist = new HealSkill("Niebla Rejuvenecedora", 40, 18, Element.AIR);
        HealSkill earthEmbrace = new HealSkill("Abrazo de la Tierra", 50, 20, Element.GROUND);
        HealSkill infernalRevive = new HealSkill("Renacer Infernal", 45, 25, Element.FIRE);

        BuffSkill powerBoost = new BuffSkill("Sobrecarga de Arena", "ataque", 1.5, 10, Element.GROUND);
        BuffSkill flameAura = new BuffSkill("Aura Ígnea", "ataque", 1.3, 15, Element.FIRE);
        BuffSkill tidalFortress = new BuffSkill("Fortaleza de Marea", "vida", 1.4, 12, Element.WATER);
        BuffSkill windRush = new BuffSkill("Impulso del Viento", "velocidad", 1.5, 10, Element.AIR);
        BuffSkill stoneSkin = new BuffSkill("Piel de Piedra", "defensa", 1.6, 18, Element.GROUND);

        models.Character warrior = new models.Character("Guerrero de Tierra", 100, 50, 20, Element.GROUND);
        models.Character fireMage = new models.Character("Mago de Fuego", 80, 100, 25, Element.FIRE);
        models.Character waterPriest = new models.Character("Sacerdote del Agua", 70, 120, 15, Element.WATER);
        models.Character airAssassin = new models.Character("Asesino del Aire", 85, 60, 30, Element.AIR);
        models.Character stoneGolem = new models.Character("Gólem de Roca", 120, 40, 10, Element.GROUND);
        models.Character flamePhoenix = new models.Character("Fénix de Llamas", 90, 80, 35, Element.FIRE);
        models.Character tempestRanger = new models.Character("Guardabosques de la Tormenta", 95, 70, 22, Element.AIR);
        models.Character tidalMonk = new models.Character("Monje de la Marea", 75, 110, 18, Element.WATER);
        models.Character shadowReaper = new models.Character("Segador de Sombras", 80, 50, 40, Element.AIR);

        fireMage.addSkill(fireball);
        fireMage.addSkill(lavaBurst);
        fireMage.addSkill(flameAura);
        fireMage.addSkill(infernalRevive);

        warrior.addSkill(rockSword);
        warrior.addSkill(sandstormStrike);
        warrior.addSkill(powerBoost);
        warrior.addSkill(stoneSkin);

        waterPriest.addSkill(waterWhip);
        waterPriest.addSkill(tidalWave);
        waterPriest.addSkill(healingLight);
        waterPriest.addSkill(tidalFortress);

        airAssassin.addSkill(airBlade);
        airAssassin.addSkill(lightningBolt);
        airAssassin.addSkill(rejuvenatingMist);
        airAssassin.addSkill(windRush);

        stoneGolem.addSkill(rockSword);
        stoneGolem.addSkill(earthEmbrace);
        stoneGolem.addSkill(stoneSkin);

        flamePhoenix.addSkill(fireball);
        flamePhoenix.addSkill(meteorShower);
        flamePhoenix.addSkill(flameAura);

        tempestRanger.addSkill(airBlade);
        tempestRanger.addSkill(lightningBolt);
        tempestRanger.addSkill(windRush);

        tidalMonk.addSkill(waterWhip);
        tidalMonk.addSkill(tidalWave);
        tidalMonk.addSkill(healingLight);

        shadowReaper.addSkill(lightningBolt);
        shadowReaper.addSkill(rejuvenatingMist);
        shadowReaper.addSkill(windRush);

        // Añadir todos los personajes a la lista de disponibles
        characters.add(warrior);
        characters.add(fireMage);
        characters.add(waterPriest);
        characters.add(airAssassin);
        characters.add(stoneGolem);
        characters.add(flamePhoenix);
        characters.add(tempestRanger);
        characters.add(tidalMonk);
        characters.add(shadowReaper);
    }

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
