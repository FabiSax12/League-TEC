package view;

import models.Player;
import view.components.Button;
import view.components.CustomColors;

import javax.swing.*;
import java.awt.*;

public class CreateGamePanel extends JPanel {
    private Player[] players = {
            new Player("User 1"),
            new Player("User 2"),
            new Player("User 3"),
    };

    private final JComboBox<Player> player1ComboBox;
    private final JComboBox<Player> player2ComboBox;

    public CreateGamePanel(MainGameWindow mainWindow) {
        setLayout(new BorderLayout());

        JLabel label = new JLabel("Selecciona Jugadores para la Partida", JLabel.CENTER);
        label.setFont(new Font("Serif", Font.BOLD, 24));
        label.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(label, BorderLayout.NORTH);

        JPanel selectPlayersPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel player1Label = new JLabel("Jugador 1: ");
        player1Label.setFont(new Font("Serif", Font.PLAIN, 18));
        gbc.gridx = 0;
        gbc.gridy = 0;
        selectPlayersPanel.add(player1Label, gbc);

        player1ComboBox = new JComboBox<>(players);
        gbc.gridx = 1;
        selectPlayersPanel.add(player1ComboBox, gbc);

        JLabel player2Label = new JLabel("Jugador 2: ");
        player2Label.setFont(new Font("Serif", Font.PLAIN, 18));
        gbc.gridx = 0;
        gbc.gridy = 1;
        selectPlayersPanel.add(player2Label, gbc);

        player2ComboBox = new JComboBox<>(players);
        gbc.gridx = 1;
        selectPlayersPanel.add(player2ComboBox, gbc);
        player2ComboBox.setSelectedIndex(1);

        JButton createProfileButton = new Button("Crear Perfil");
        createProfileButton.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        selectPlayersPanel.add(createProfileButton, gbc);

        createProfileButton.addActionListener(e -> createNewProfile());

        selectPlayersPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        add(selectPlayersPanel, BorderLayout.CENTER);

        JButton btnBack = new Button("Volver al Menú", CustomColors.RED);
        JButton btnCreateGame = new Button("Continuar", CustomColors.GREEN);
        btnBack.addActionListener(e -> mainWindow.showPanel("Menu"));
        btnCreateGame.addActionListener(e -> mainWindow.startGameWindow(
                player1ComboBox.getItemAt(player1ComboBox.getSelectedIndex()),
                player2ComboBox.getItemAt(player2ComboBox.getSelectedIndex())
        ));

        JPanel btnsPanel = new JPanel();
        btnsPanel.add(btnBack);
        btnsPanel.add(btnCreateGame);
        btnsPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(btnsPanel, BorderLayout.SOUTH);
    }

    private void createNewProfile() {
        String newPlayerName = JOptionPane.showInputDialog(this, "Ingrese el nombre del nuevo jugador:", "Crear Perfil", JOptionPane.PLAIN_MESSAGE);

        if (newPlayerName != null && !newPlayerName.trim().isEmpty()) {
            Player newPlayer = new Player(newPlayerName);

            player1ComboBox.addItem(newPlayer);
            player2ComboBox.addItem(newPlayer);

            JOptionPane.showMessageDialog(this, "Nuevo perfil creado: " + newPlayerName, "Perfil Creado", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "El nombre no puede estar vacío.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

