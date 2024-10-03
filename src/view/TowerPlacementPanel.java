package view;

import models.Team;
import models.Tower;
import view.components.ButtonComponent;

import javax.swing.*;
import java.awt.*;

public class TowerPlacementPanel extends JPanel {
    private final JButton[][] gridButtons;
    private final Team team1;
    private final Team team2;
    private int team1TowersPlaced = 0;
    private int team2TowersPlaced = 0;
    private final JLabel statusLabel;
    private final JButton confirmButton;

    public TowerPlacementPanel(MainGameWindow mainWindow, Team team1, Team team2) {
        this.team1 = team1;
        this.team2 = team2;

        setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Coloca tus torres", JLabel.CENTER);
        titleLabel.setFont(new Font("Consolas", Font.BOLD, 24));
        add(titleLabel, BorderLayout.NORTH);

        JPanel gridPanel = new JPanel(new GridLayout(10, 10, 5, 5));
        gridPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        gridButtons = new JButton[10][10];
        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                JButton button = new JButton();
                button.setBackground(Color.LIGHT_GRAY);
                int finalRow = row;
                int finalCol = col;

                button.addActionListener(e -> placeTower(finalRow, finalCol, button));

                gridButtons[row][col] = button;
                gridPanel.add(button);
            }
        }

        add(gridPanel, BorderLayout.CENTER);

        statusLabel = new JLabel("Jugador 1 coloca las torres", JLabel.CENTER);
        add(statusLabel, BorderLayout.SOUTH);

        confirmButton = new ButtonComponent("Confirmar");
        confirmButton.setEnabled(false);
        confirmButton.addActionListener(e -> {
            if (team1TowersPlaced >= 1 && team2TowersPlaced >= 1) {
                //mainWindow.startGame(team1, team2);
                System.out.println("Iniciar partida");
            } else {
                JOptionPane.showMessageDialog(this, "Cada jugador debe colocar al menos 1 torre.", "Error", JOptionPane.WARNING_MESSAGE);
            }
        });

        JPanel controlPanel = new JPanel();
        controlPanel.add(confirmButton);
        add(controlPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void placeTower(int row, int col, JButton button) {
        if (row < 5 && team1TowersPlaced < 1) {
            team1.addTower(new Tower());
            button.setBackground(Color.BLUE);
            button.setEnabled(false);
            team1TowersPlaced++;
            statusLabel.setText("Jugador 2 coloca las torres");
        }

        else if (row >= 5 && team2TowersPlaced < 1) {
            team2.addTower(new Tower());
            button.setBackground(Color.RED);
            button.setEnabled(false);
            team2TowersPlaced++;
            statusLabel.setText("Ambos jugadores han colocado las torres.");
        }

        if (team1TowersPlaced >= 1 && team2TowersPlaced >= 1) {
            confirmButton.setEnabled(true);
        }
    }
}
