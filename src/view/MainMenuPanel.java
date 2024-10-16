package view;

import view.components.ButtonComponent;
import view.components.CustomColors;

import javax.swing.*;
import java.awt.*;

/**
 * MainMenuPanel represents the main menu of the game. It contains buttons to navigate to different panels like
 * creating a game, viewing statistics, viewing the character gallery, or exiting the application.
 */
public class MainMenuPanel extends JPanel {

    /**
     * Constructs the MainMenuPanel, setting up the layout and adding buttons for navigation.
     *
     * @param mainWindow The main game window that controls the panel navigation.
     */
    public MainMenuPanel(MainGameWindow mainWindow) {
        setLayout(new BorderLayout());

        // Title label
        JLabel titleLabel = new JLabel("League TEC", JLabel.CENTER);
        titleLabel.setFont(new Font("Consolas", Font.BOLD, 36));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        // Button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 1, 10, 30));

        // Create buttons for navigation
        JButton btnCreateGame = new ButtonComponent("Crear Partida", CustomColors.BLUE);
        JButton btnStatistics = new ButtonComponent("Estadísticas", CustomColors.BLUE);
        JButton btnGallery = new ButtonComponent("Galería de Personajes", CustomColors.BLUE);
        JButton btnClose = new ButtonComponent("Salir", CustomColors.RED);

        // Add button actions
        btnCreateGame.addActionListener(e -> mainWindow.showPanel("CreateGame"));
        btnStatistics.addActionListener(e -> mainWindow.showPanel("Statistics"));
        btnGallery.addActionListener(e -> mainWindow.showPanel("Gallery"));
        btnClose.addActionListener(e -> System.exit(0)); // Exit application

        // Add buttons to the panel
        buttonPanel.add(btnCreateGame);
        buttonPanel.add(btnStatistics);
        buttonPanel.add(btnGallery);
        buttonPanel.add(btnClose);

        // Center the buttons
        JPanel centeredPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        centeredPanel.add(buttonPanel);
        centeredPanel.setBorder(BorderFactory.createEmptyBorder(50, 200, 50, 200));

        // Add components to the main layout
        add(titleLabel, BorderLayout.NORTH);
        add(centeredPanel, BorderLayout.CENTER);
    }
}
