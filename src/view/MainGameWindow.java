package view;

import models.*;
import models.Character;
import view.components.MatrixButton;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**
 * MainGameWindow manages the main game window, allowing users to navigate between various panels such as
 * the main menu, character gallery, character creation, and game-related panels.
 */
public class MainGameWindow extends JFrame {
    private final CardLayout cardLayout;
    private final JPanel mainPanel;
    private JPanel menuPanel, createGamePanel, statisticsPanel, galleryPanel, createCharacterPanel;

    /**
     * Constructs the MainGameWindow, initializes the layout and panels, and sets the window's basic properties.
     *
     * @throws IOException If there is an error during initialization.
     */
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

    /**
     * Switches to a different panel based on the given panel name.
     * Refreshes the gallery panel if "Gallery" is selected to ensure up-to-date data.
     *
     * @param panelName The name of the panel to show.
     */
    public void showPanel(String panelName) {
        if (panelName.equals("Gallery")) {
            mainPanel.remove(galleryPanel);
            galleryPanel = new GalleryPanel(this);
            mainPanel.add(galleryPanel, "Gallery");
        }

        cardLayout.show(mainPanel, panelName);
    }

    /**
     * Displays the character gallery by initializing a new GalleryPanel instance.
     */
    public void showCharacterGallery() {
        galleryPanel = new GalleryPanel(this);
    }

    /**
     * Displays the detailed view of a specific character.
     *
     * @param character The character whose details will be displayed.
     */
    public void showCharacterDetails(Character character) {
        CharacterDetailPanel detailPanel = new CharacterDetailPanel(character, this);
        mainPanel.add(detailPanel, "CharacterDetails");
        cardLayout.show(mainPanel, "CharacterDetails");
    }

    /**
     * Opens the panel to edit an existing character's data.
     *
     * @param character The character to be edited.
     */
    public void editCharacter(Character character) {
        EditCharacterPanel editCharacterPanel = new EditCharacterPanel(character, this);
        mainPanel.add(editCharacterPanel, "EditCharacter");
        cardLayout.show(mainPanel, "EditCharacter");
    }

    /**
     * Starts a new game by initializing teams for the given players and displaying the character selection screen.
     *
     * @param player1 The first player.
     * @param player2 The second player.
     */
    public void startGameWindow(Player player1, Player player2) {
        Team team1 = new Team(player1);
        Team team2 = new Team(player2);

        JPanel selectCharacters = new CharacterSelection(this, team1, team2);
        mainPanel.add(selectCharacters, "SelectCharacters");
        cardLayout.show(mainPanel, "SelectCharacters");
    }

    /**
     * Starts the character placement phase where players place their selected characters and towers on the game grid.
     *
     * @param team1 The first player's team.
     * @param team2 The second player's team.
     */
    public void startGame(Team team1, Team team2) {
        mainPanel.add(new CharacterPlacementPanel(this, team1, team2), "CharacterPlacementPanel");
        cardLayout.show(mainPanel, "CharacterPlacementPanel");
    }

    /**
     * Starts the main game arena where players battle. The arena layout is managed by a matrix of buttons.
     *
     * @param match        The current match being played.
     * @param matrixButton The matrix of buttons representing the game grid.
     */
    public void startGameArena(Match match, MatrixButton[][] matrixButton) {
        mainPanel.add(new MainGameArena(this, match,matrixButton), "MainGameArena");
        cardLayout.show(mainPanel, "MainGameArena");
    }
}
