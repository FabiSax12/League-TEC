package view;

import database.DB;
import models.Player;
import view.components.ButtonComponent;
import view.components.ComboBoxComponent;
import view.components.CustomColors;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * CreateGamePanel provides a user interface for selecting players and creating a new game,
 * as well as an option to create new player profiles.
 */
public class CreateGamePanel extends JPanel {
    private final ArrayList<String> players;
    private final JComboBox<String> player1ComboBox;
    private final JComboBox<String> player2ComboBox;

    /**
     * Constructs the CreateGamePanel, setting up UI elements for selecting players and creating a game.
     *
     * @param mainWindow The main game window that manages transitions between panels.
     */
    public CreateGamePanel(MainGameWindow mainWindow) {
        players = DB.getPlayerNames();

        setLayout(new BorderLayout());

        JLabel label = new JLabel("Selecciona Jugadores para la Partida", JLabel.CENTER);
        label.setFont(new Font("Consolas", Font.BOLD, 24));
        label.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(label, BorderLayout.NORTH);

        JPanel selectPlayersPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Player 1 selection
        JLabel player1Label = new JLabel("Jugador 1: ");
        player1Label.setFont(new Font("Consolas", Font.PLAIN, 18));
        gbc.gridx = 0;
        gbc.gridy = 0;
        selectPlayersPanel.add(player1Label, gbc);

        player1ComboBox = new ComboBoxComponent<>(players.toArray(new String[0]));
        gbc.gridx = 1;
        selectPlayersPanel.add(player1ComboBox, gbc);

        // Player 2 selection
        JLabel player2Label = new JLabel("Jugador 2: ");
        player2Label.setFont(new Font("Consolas", Font.PLAIN, 18));
        gbc.gridx = 0;
        gbc.gridy = 1;
        selectPlayersPanel.add(player2Label, gbc);

        player2ComboBox = new ComboBoxComponent<>(players.toArray(new String[0]));
        gbc.gridx = 1;
        selectPlayersPanel.add(player2ComboBox, gbc);

        // Create Profile button
        JButton createProfileButton = new ButtonComponent("Crear Perfil");
        createProfileButton.setFont(new Font("Consolas", Font.PLAIN, 16));
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        selectPlayersPanel.add(createProfileButton, gbc);

        createProfileButton.addActionListener(e -> createNewProfile());

        selectPlayersPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        add(selectPlayersPanel, BorderLayout.CENTER);

        // Back and Continue buttons
        JButton btnBack = new ButtonComponent("Volver al Menú", CustomColors.RED);
        JButton btnCreateGame = new ButtonComponent("Continuar", CustomColors.GREEN);
        btnBack.addActionListener(e -> mainWindow.showPanel("Menu"));
        btnCreateGame.addActionListener(e -> {
            Player p1 = DB.getPlayerByName(player1ComboBox.getItemAt(player1ComboBox.getSelectedIndex()));
            Player p2 = DB.getPlayerByName(player2ComboBox.getItemAt(player2ComboBox.getSelectedIndex()));

            if (p1 == p2) {
                JOptionPane.showMessageDialog(mainWindow, "Los jugadores deben ser diferentes");
                return;
            }

            mainWindow.startGameWindow(p1, p2);
        });

        JPanel btnsPanel = new JPanel();
        btnsPanel.add(btnBack);
        btnsPanel.add(btnCreateGame);
        btnsPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(btnsPanel, BorderLayout.SOUTH);
    }

    /**
     * Creates a new player profile by prompting the user for a name.
     * If the name is unique and valid, the new profile is added to the database.
     */
    private void createNewProfile() {
        String newPlayerName = JOptionPane.showInputDialog(this, "Ingrese el nombre del nuevo jugador:", "Crear Perfil", JOptionPane.PLAIN_MESSAGE);

        if (players.contains(newPlayerName)) {
            JOptionPane.showMessageDialog(
                    this,
                    "El nombre del nuevo jugador ya existe",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        } else if (newPlayerName != null && !newPlayerName.trim().isEmpty()) {
            DB.addProfile(newPlayerName);
            player1ComboBox.addItem(newPlayerName);
            player2ComboBox.addItem(newPlayerName);

            JOptionPane.showMessageDialog(
                    this,
                    "Nuevo perfil creado: " + newPlayerName,
                    "Perfil Creado",
                    JOptionPane.INFORMATION_MESSAGE
            );
        } else {
            JOptionPane.showMessageDialog(
                    this,
                    "El nombre no puede estar vacío.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}