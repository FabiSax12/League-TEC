package view;

import controller.CharacterSelectionController;
import models.Character;
import models.Team;
import view.components.ButtonComponent;
import view.components.CharacterListComponent;

import javax.swing.*;
import java.awt.*;

/**
 * The {@code CharacterSelection} class represents the view where two players select their characters for the game.
 * It displays available characters, allows adding characters to teams, and confirms the selection.
 */
public class CharacterSelection extends JPanel {
    private final DefaultListModel<Character> listModel1;
    private final DefaultListModel<Character> listModel2;
    private final JList<Character> selectionList;
    private final CharacterSelectionController controller;
    private final JButton confirmButton;

    /**
     * Constructs the character selection panel for two teams.
     *
     * @param mainWindow the main window of the game, used to switch between panels
     * @param team1 the first team selecting characters
     * @param team2 the second team selecting characters
     */
    public CharacterSelection(MainGameWindow mainWindow, Team team1, Team team2) {
        listModel1 = new DefaultListModel<>();
        listModel2 = new DefaultListModel<>();
        DefaultListModel<Character> availableListModel = new DefaultListModel<>();
        selectionList = new CharacterListComponent<>(availableListModel);

        controller = new CharacterSelectionController(listModel1, listModel2, availableListModel, selectionList);
        controller.populateAvailableCharacters();

        setLayout(new BorderLayout());
        JPanel mainPanel = new JPanel(new GridLayout(1, 3));

        JPanel selectionPanel = new JPanel(new BorderLayout());
        selectionPanel.setBorder(BorderFactory.createTitledBorder("Personajes Disponibles"));
        selectionPanel.add(new JScrollPane(selectionList), BorderLayout.CENTER);

        setupPlayerPanel(team1, mainPanel, listModel1);
        mainPanel.add(selectionPanel);
        setupPlayerPanel(team2, mainPanel, listModel2);
        add(mainPanel, BorderLayout.CENTER);

        confirmButton = new ButtonComponent("Confirmar Selección");
        confirmButton.addActionListener(e -> {
            if (team1.getCharacters().size() == 3 && team2.getCharacters().size() == 3) {
                mainWindow.startGame(team1, team2);
            } else {
                JOptionPane.showMessageDialog(mainWindow, "Cada equipo debe tener 3 personajes");
            }
        });
        add(confirmButton, BorderLayout.SOUTH);
    }

    /**
     * Sets up the panel for a team to display their selected characters.
     *
     * @param team the team for which the panel is created
     * @param mainPanel the main panel that contains all team panels
     * @param listModel the list model that holds the selected characters for the team
     */
    private void setupPlayerPanel(Team team, JPanel mainPanel, DefaultListModel<Character> listModel) {
        JPanel playerPanel = new JPanel(new BorderLayout());
        playerPanel.setBorder(BorderFactory.createTitledBorder(team.getName()));
        JList<Character> playerList = new CharacterListComponent<>(listModel);
        playerPanel.add(new JScrollPane(playerList), BorderLayout.CENTER);

        JButton addButton = new ButtonComponent("Añadir a " + team.getName());
        addButton.addActionListener(e -> controller.addCharacterToTeam(selectionList.getSelectedValue(), team, listModel));
        playerPanel.add(addButton, BorderLayout.SOUTH);

        mainPanel.add(playerPanel);
    }
}