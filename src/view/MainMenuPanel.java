package view;

import view.components.ButtonComponent;
import view.components.CustomColors;

import javax.swing.*;
import java.awt.*;

public class MainMenuPanel extends JPanel {

    public MainMenuPanel(MainGameWindow mainWindow) {
        setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("League TEC", JLabel.CENTER);
        titleLabel.setFont(new Font("Consolas", Font.BOLD, 36));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 1, 10, 10));

        JButton btnCreateGame = new ButtonComponent("Crear Partida", CustomColors.BLUE);
        JButton btnStatistics = new ButtonComponent("Estadísticas", CustomColors.BLUE);
        JButton btnGallery = new ButtonComponent("Galería de Personajes", CustomColors.BLUE);
        JButton btnClose = new ButtonComponent("Salir", CustomColors.RED);

        btnCreateGame.addActionListener(e -> mainWindow.showPanel("CreateGame"));
        btnStatistics.addActionListener(e -> mainWindow.showPanel("Statistics"));
        btnGallery.addActionListener(e -> mainWindow.showPanel("Gallery"));
        btnClose.addActionListener(e -> System.exit(0));

        buttonPanel.add(btnCreateGame);
        buttonPanel.add(btnStatistics);
        buttonPanel.add(btnGallery);
        buttonPanel.add(btnClose);

        JPanel centeredPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        centeredPanel.add(buttonPanel);
        centeredPanel.setBorder(BorderFactory.createEmptyBorder(50, 200, 50, 200));

        add(titleLabel, BorderLayout.NORTH);
        add(centeredPanel, BorderLayout.CENTER);
    }
}
