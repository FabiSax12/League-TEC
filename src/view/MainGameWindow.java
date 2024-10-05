package view;

import models.*;
import models.Character;
import view.components.MatrixButton;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class MainGameWindow extends JFrame {
    private final CardLayout cardLayout;
    private final JPanel mainPanel;

    public MainGameWindow() throws IOException {
        setTitle("League TEC - Menú Principal");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        JPanel menuPanel = new MainMenuPanel(this);
        JPanel createGamePanel = new CreateGamePanel(this);
        JPanel statisticsPanel = new StatisticsPanel(this);
        JPanel galleryPanel = new GalleryPanel(this);

        mainPanel.add(menuPanel, "Menu");
        mainPanel.add(createGamePanel, "CreateGame");
        mainPanel.add(statisticsPanel, "Statistics");
        mainPanel.add(galleryPanel, "Gallery");

        add(mainPanel);
        setVisible(true);
    }

    public void showPanel(String panelName) {
        cardLayout.show(mainPanel, panelName);
    }

    public void showCharacterDetails(Character character) {
        CharacterDetailPanel detailPanel = new CharacterDetailPanel(character, this);
        mainPanel.add(detailPanel, "CharacterDetails");
        cardLayout.show(mainPanel, "CharacterDetails");
    }

    public void startGameWindow(Player player1, Player player2) {
        Team team1 = new Team(player1);
        Team team2 = new Team(player2);

        JPanel selectCharacters = new CharacterSelection(this, team1, team2);
        mainPanel.add(selectCharacters, "SelectCharacters");
        cardLayout.show(mainPanel, "SelectCharacters");
    }

    public void startGame(Team team1, Team team2) {
        mainPanel.add(new CharacterPlacementPanel(this, team1, team2), "CharacterPlacementPanel");
        cardLayout.show(mainPanel, "CharacterPlacementPanel");
    }

    public void startGameArena(Team team1, Team team2, MatrixButton[][] matrixButton) {
        mainPanel.add(new CharacterPlacementPanel(this, team1, team2), "CharacterPlacementPanel");
        cardLayout.show(mainPanel, "CharacterPlacementPanel");
    }
}
