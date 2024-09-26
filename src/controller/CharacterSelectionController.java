package controller;

import models.*;
import models.Character;
import models.abilities.*;
import view.CharacterSelection;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class CharacterSelectionController {
    private final CharacterSelection view;
    private List<Character> availableCharacters;
    private final Match match;

    public CharacterSelectionController(Team team1, Team team2, Match match) {
        this.match = match;
        initializeCharacters();
        this.view = new CharacterSelection(this, team1, team2);
    }

    private void initializeCharacters() {
        availableCharacters = new ArrayList<>();

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

        Character warrior = new Character("Guerrero de Tierra", 100, 50, 20, Element.GROUND);
        Character fireMage = new Character("Mago de Fuego", 80, 100, 25, Element.FIRE);
        Character waterPriest = new Character("Sacerdote del Agua", 70, 120, 15, Element.WATER);
        Character airAssassin = new Character("Asesino del Aire", 85, 60, 30, Element.AIR);
        Character stoneGolem = new Character("Gólem de Roca", 120, 40, 10, Element.GROUND);
        Character flamePhoenix = new Character("Fénix de Llamas", 90, 80, 35, Element.FIRE);
        Character tempestRanger = new Character("Guardabosques de la Tormenta", 95, 70, 22, Element.AIR);
        Character tidalMonk = new Character("Monje de la Marea", 75, 110, 18, Element.WATER);
        Character shadowReaper = new Character("Segador de Sombras", 80, 50, 40, Element.AIR);

        fireMage.addAbility(fireball);
        fireMage.addAbility(lavaBurst);
        fireMage.addAbility(flameAura);
        fireMage.addAbility(infernalRevive);

        warrior.addAbility(rockSword);
        warrior.addAbility(sandstormStrike);
        warrior.addAbility(powerBoost);
        warrior.addAbility(stoneSkin);

        waterPriest.addAbility(waterWhip);
        waterPriest.addAbility(tidalWave);
        waterPriest.addAbility(healingLight);
        waterPriest.addAbility(tidalFortress);

        airAssassin.addAbility(airBlade);
        airAssassin.addAbility(lightningBolt);
        airAssassin.addAbility(rejuvenatingMist);
        airAssassin.addAbility(windRush);

        stoneGolem.addAbility(rockSword);
        stoneGolem.addAbility(earthEmbrace);
        stoneGolem.addAbility(stoneSkin);

        flamePhoenix.addAbility(fireball);
        flamePhoenix.addAbility(meteorShower);
        flamePhoenix.addAbility(flameAura);

        tempestRanger.addAbility(airBlade);
        tempestRanger.addAbility(lightningBolt);
        tempestRanger.addAbility(windRush);

        tidalMonk.addAbility(waterWhip);
        tidalMonk.addAbility(tidalWave);
        tidalMonk.addAbility(healingLight);

        shadowReaper.addAbility(lightningBolt);
        shadowReaper.addAbility(rejuvenatingMist);
        shadowReaper.addAbility(windRush);

        // Añadir todos los personajes a la lista de disponibles
        availableCharacters.add(warrior);
        availableCharacters.add(fireMage);
        availableCharacters.add(waterPriest);
        availableCharacters.add(airAssassin);
        availableCharacters.add(stoneGolem);
        availableCharacters.add(flamePhoenix);
        availableCharacters.add(tempestRanger);
        availableCharacters.add(tidalMonk);
        availableCharacters.add(shadowReaper);
    }

    public List<Character> getAvailableCharacters() {
        return availableCharacters;
    }

    public void addCharacterToTeam(Character character, Team team, DefaultListModel<Character> listModel) {
        if (character == null) {
            JOptionPane.showMessageDialog(view, "Por favor, selecciona un personaje.");
            return;
        }

        if (listModel.size() >= 5) {
            JOptionPane.showMessageDialog(view, team.getName() + " ya ha seleccionado 5 personajes.");
            return;
        }

        listModel.addElement(character);
        team.addCharacter(character);
        checkConfirmButtonStatus();
    }

    private void checkConfirmButtonStatus() {
        view.enableConfirmButton(view.getPlayer1ListModel().size() == 5 && view.getPlayer2ListModel().size() == 5);
    }

    public void confirmSelection() {
        view.dispose();
        match.startMatch();
    }
}
