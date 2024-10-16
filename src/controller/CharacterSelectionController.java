package controller;

import database.DB;
import models.Character;
import models.Team;

import javax.swing.*;

/**
 * The {@code CharacterSelectionController} class is responsible for handling the logic
 * of selecting characters for both teams during the character selection phase of the game.
 * It manages the list of available characters and the characters chosen by each team.
 */
public class CharacterSelectionController {
    private final DefaultListModel<Character> listModel1;
    private final DefaultListModel<Character> listModel2;
    private final DefaultListModel<Character> availableListModel;
    private final JList<Character> selectionList;

    /**
     * Constructs the controller for the character selection process.
     *
     * @param listModel1 the list model that holds characters selected by the first team
     * @param listModel2 the list model that holds characters selected by the second team
     * @param availableListModel the list model that holds the available characters for selection
     * @param selectionList the JList component displaying available characters
     */
    public CharacterSelectionController(DefaultListModel<Character> listModel1, DefaultListModel<Character> listModel2, DefaultListModel<Character> availableListModel, JList<Character> selectionList) {
        this.listModel1 = listModel1;
        this.listModel2 = listModel2;
        this.availableListModel = availableListModel;
        this.selectionList = selectionList;
    }

    /**
     * Populates the list of available characters from the database.
     */
    public void populateAvailableCharacters() {
        availableListModel.clear();
        DB.getCharacters().forEach(availableListModel::addElement);
    }

    /**
     * Adds a selected character to the specified team.
     *
     * @param character the character to be added to the team
     * @param team the team that the character will be added to
     * @param listModel the list model that holds the characters selected by the team
     */
    public void addCharacterToTeam(Character character, Team team, DefaultListModel<Character> listModel) {
        if (character == null) {
            JOptionPane.showMessageDialog(null, "Por favor, selecciona un personaje.");
            return;
        }

        // Check if character has already been selected by any team
        if (listModel1.contains(character) || listModel2.contains(character)) {
            JOptionPane.showMessageDialog(null, "Este personaje ya ha sido seleccionado.");
            return;
        }

        if (listModel.size() >= 3) {
            JOptionPane.showMessageDialog(null, team.getName() + " ya ha seleccionado 3 personajes.");
            return;
        }

        listModel.addElement(character);
        team.addCharacter(character);
        refreshCharacterList();
    }

    /**
     * Refreshes the list of available characters, updating the display to reflect the characters already selected.
     */
    public void refreshCharacterList() {
        selectionList.repaint();
    }
}
