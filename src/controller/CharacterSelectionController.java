package controller;

import database.DB;
import models.*;
import models.Character;
import view.CharacterSelection;

import javax.swing.*;
import java.util.List;

public class CharacterSelectionController {
    private final CharacterSelection view;
    private List<Character> availableCharacters;

    public CharacterSelectionController(CharacterSelection view) {
        initializeCharacters();
        this.view = view;
    }

    private void initializeCharacters() {
        availableCharacters = DB.getCharacters();
    }

    public List<Character> getAvailableCharacters() {
        return availableCharacters;
    }

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
        checkConfirmButtonStatus();
    }

    private void checkConfirmButtonStatus() {
        view.enableConfirmButton(view.getPlayer1ListModel().size() == 3 && view.getPlayer2ListModel().size() == 3);
    }

    public void confirmSelection(Team team1, Team team2) {
        new Match(ArenaFactory.create(), team1, team2);
    }
}
