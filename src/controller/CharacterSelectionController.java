package controller;

import database.DB;
import models.*;
import models.Character;
import view.CharacterSelection;

import javax.swing.*;
import java.util.List;

/**
 * Controller class for managing the character selection process.
 * It handles the interactions between the view and the model during character selection for both teams.
 */
public class CharacterSelectionController {
    private final CharacterSelection view;  // Reference to the view (CharacterSelection panel)
    private List<Character> availableCharacters;  // List of available characters for selection

    /**
     * Constructor for CharacterSelectionController. It initializes the list of available characters
     * and associates the view with the controller.
     *
     * @param view The CharacterSelection view instance
     */
    public CharacterSelectionController(CharacterSelection view) {
        initializeCharacters();
        this.view = view;
    }

    /**
     * Initializes the list of available characters by retrieving them from the database.
     */
    private void initializeCharacters() {
        availableCharacters = DB.getCharacters();
    }

    /**
     * Returns the list of available characters for selection.
     *
     * @return List of available characters
     */
    public List<Character> getAvailableCharacters() {
        return availableCharacters;
    }

    /**
     * Adds the selected character to the specified team and updates the associated list model.
     * Also checks if the confirm button should be enabled.
     *
     * @param character  The selected character to be added
     * @param team       The team to which the character is added
     * @param listModel  The list model representing the team in the UI
     */
    public void addCharacterToTeam(Character character, Team team, DefaultListModel<Character> listModel) {
        if (character == null) {
            JOptionPane.showMessageDialog(view, "Por favor, selecciona un personaje.");
            return;
        }

        if (listModel.size() >= 3) {
            JOptionPane.showMessageDialog(view, team.getName() + " ya ha seleccionado 3 personajes.");
            return;
        }

        listModel.addElement(character);
        team.addCharacter(character);
        checkConfirmButtonStatus();  // Check if confirm button should be enabled
    }

    /**
     * Checks whether both teams have selected 3 characters each.
     * Enables the confirm button if both teams are ready.
     */
    private void checkConfirmButtonStatus() {
        view.enableConfirmButton(view.getPlayer1ListModel().size() == 3 && view.getPlayer2ListModel().size() == 3);
    }

    /**
     * Finalizes the selection process and starts a new match with the selected teams.
     *
     * @param team1 The first team
     * @param team2 The second team
     */
    public void confirmSelection(Team team1, Team team2) {
        new Match(ArenaFactory.create(), team1, team2);
    }
}
