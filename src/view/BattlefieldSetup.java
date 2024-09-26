package view;

import controller.BattlefieldSetupController;
import models.Character;
import models.Element;
import models.Team;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BattlefieldSetup extends JFrame {
    private final JPanel battlefieldPanel;
    private final JButton[][] gridButtons;
    private final JLabel turnLabel;
    private final JButton placeButton;
    private final JComboBox<Character> characterComboBox;
    private final int gridSize = 10;
    private final BattlefieldSetupController controller;
    private final Element element;
    private Character selectedCharacter;

    public BattlefieldSetup(BattlefieldSetupController controller, Team team1, Team team2, Element element) {
        this.controller = controller;
        this.element = element;
        setTitle("Configuración de la Batalla");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        battlefieldPanel = new JPanel(new GridLayout(gridSize, gridSize));
        gridButtons = new JButton[gridSize][gridSize];
        initializeBattlefield();

        characterComboBox = new JComboBox<>();
        characterComboBox.addActionListener(new CharacterSelectionListener());

        turnLabel = new JLabel("Turno de: " + team1.getName());
        turnLabel.setHorizontalAlignment(SwingConstants.CENTER);

        placeButton = new JButton("Colocar Torreta");
        placeButton.addActionListener(e -> controller.handlePlaceButtonAction());

        add(turnLabel, BorderLayout.NORTH);
        add(battlefieldPanel, BorderLayout.CENTER);
        add(characterComboBox, BorderLayout.EAST);
        add(placeButton, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void initializeBattlefield() {
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                JButton button = new JButton();
                setArenaColor(button);
                button.setEnabled(false);
                int row = i;
                int col = j;
                button.addActionListener(e -> controller.handleGridButtonAction(row, col, selectedCharacter));
                gridButtons[i][j] = button;
                battlefieldPanel.add(button);
            }
        }
    }

    public void updateTurnLabel(String text) {
        turnLabel.setText(text);
    }

    public void updatePlaceButtonText(String text) {
        placeButton.setText(text);
    }

    public JButton getGridButton(int row, int col) {
        return gridButtons[row][col];
    }

    public void enableBattlefieldButtons(int turnIndex) {
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                if ((turnIndex == 0 && i < gridSize / 2) || (turnIndex == 1 && i >= gridSize / 2)) {
                    gridButtons[i][j].setEnabled(true);
                } else {
                    gridButtons[i][j].setEnabled(false);
                }
            }
        }
    }

    public void updateCharactersOptions(Team team) {
        characterComboBox.removeAllItems();
        for (Character character : team.getCharacters()) {
            characterComboBox.addItem(character);
        }
    }

    private class CharacterSelectionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            selectedCharacter = (Character) characterComboBox.getSelectedItem();
        }
    }

    public void setArenaColor(JButton button) {
        if (element == Element.FIRE) button.setBackground(Color.RED);
        else if (element == Element.WATER) button.setBackground(Color.BLUE);
        else if (element == Element.GROUND) button.setBackground(Color.YELLOW);
        else button.setBackground(Color.LIGHT_GRAY);
    }

    public void disableCharacterOption(Character character) {
        characterComboBox.removeItem(character);
    }
}
