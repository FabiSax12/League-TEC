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
    private JPanel menuPanel, createGamePanel, statisticsPanel, galleryPanel, createCharacterPanel;

    public MainGameWindow() throws IOException {
        setTitle("League TEC - Menú Principal");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        menuPanel = new MainMenuPanel(this);
        createGamePanel = new CreateGamePanel(this);
        statisticsPanel = new StatisticsPanel(this);
        galleryPanel = new GalleryPanel(this);
        createCharacterPanel = new CreateCharacter(this);

        mainPanel.add(menuPanel, "Menu");
        mainPanel.add(createGamePanel, "CreateGame");
        mainPanel.add(statisticsPanel, "Statistics");
        mainPanel.add(galleryPanel, "Gallery");
        mainPanel.add(createCharacterPanel, "CreateCharacter");

        add(mainPanel);
        setVisible(true);
    }

    public void showPanel(String panelName) {
        if (panelName.equals("Gallery")) {
            mainPanel.remove(galleryPanel);
            galleryPanel = new GalleryPanel(this);
            mainPanel.add(galleryPanel, "Gallery");
        }

        cardLayout.show(mainPanel, panelName);
    }

    public void showCharacterGallery() {
        galleryPanel = new GalleryPanel(this);
    }

    public void showCharacterDetails(Character character) {
        CharacterDetailPanel detailPanel = new CharacterDetailPanel(character, this);
        mainPanel.add(detailPanel, "CharacterDetails");
        cardLayout.show(mainPanel, "CharacterDetails");
    }

    public void editCharacter(Character character) {
        EditCharacterPanel editCharacterPanel = new EditCharacterPanel(character, this);
        mainPanel.add(editCharacterPanel, "EditCharacter");
        cardLayout.show(mainPanel, "EditCharacter");
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

    public void startGameArena(Match match, MatrixButton[][] matrixButton) {
        mainPanel.add(new MainGameArena(this, match,matrixButton), "MainGameArena");
        cardLayout.show(mainPanel, "MainGameArena");
    }
}
