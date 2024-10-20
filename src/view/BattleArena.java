package view;

import com.sun.tools.javac.Main;
import database.DB;
import models.*;
import models.Character;
import view.components.ActionsPanel;
import view.components.ButtonComponent;
import view.components.CustomColors;
import view.components.MatrixButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class BattleArena extends JPanel {
    private final JPanel sidebar;
    private final Match match;
    private Team turn;
    private final MatrixButton[][] matrixButtons;
    private final Set<Entity> actedEntities;
    private final Set<Character> pendingRevive;
    private final Set<Character> reviveQueue;

    public BattleArena(MainGameWindow mainWindow, Match match, MatrixButton[][] matrixButtons) {
        this.match = match;
        this.matrixButtons = matrixButtons;
        this.turn = decideFirstTurn();
        this.actedEntities = new HashSet<>();
        this.pendingRevive = new HashSet<>();
        this.reviveQueue = new HashSet<>();

        mainWindow.setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLayout(new BorderLayout());

        JPanel gridCharactersPanel = createGridPanel();
        sidebar = createSidebar();

        add(sidebar, BorderLayout.EAST);
        add(gridCharactersPanel, BorderLayout.CENTER);
    }

    private JPanel createGridPanel() {
        JPanel gridPanel = new JPanel(new GridLayout(10, 10, 5, 5));
        gridPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        if (match.getArena().getElement() == Element.AIR) gridPanel.setBackground(new Color(215, 215, 215));
        else if (match.getArena().getElement() == Element.FIRE) gridPanel.setBackground(new Color(240, 41, 26));
        else if (match.getArena().getElement() == Element.GROUND) gridPanel.setBackground(new Color(168, 85, 34));
        else if (match.getArena().getElement() == Element.WATER) gridPanel.setBackground(new Color(41, 115, 242));

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                MatrixButton b = matrixButtons[i][j];
                resetButtonListeners(b);
                b.addActionListener(e -> handleButtonClick(b));
                gridPanel.add(b);
            }
        }

        return gridPanel;
    }

    public void resetButtonListeners(MatrixButton b) {
        for (ActionListener al : b.getActionListeners()) {
            b.removeActionListener(al);
        }
        b.setEnabled(true);
    }

    public void handleButtonClick(MatrixButton b) {
        Entity entity = b.getEntity();
        if (isTurnOfEntity(entity) && !actedEntities.contains(entity)) {
            updateSidebarActions(b);
        }
    }

    private boolean isTurnOfEntity(Entity entity) {
        return turn.getEntities().contains(entity);
    }

    private JPanel createSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        sidebar.setPreferredSize(new Dimension(300, getHeight()));

        JPanel sidebarInfo = new JPanel();
        JLabel turnLabel = new JLabel("Turno de " + turn.getPlayer().getName());
        turnLabel.setFont(new Font("Arial", Font.BOLD, 20));
        sidebarInfo.add(turnLabel);

        JPanel sidebarButtons = new JPanel(new BorderLayout());

        sidebar.add(sidebarInfo);
        sidebar.add(sidebarButtons);
        return sidebar;
    }

    private void updateSidebarActions(MatrixButton b) {
        JPanel sidebarButtons = (JPanel) sidebar.getComponent(1);
        sidebarButtons.removeAll();
        sidebarButtons.add(new ActionsPanel(b, matrixButtons, turn, this, this.match), BorderLayout.NORTH);
        sidebarButtons.revalidate();
        sidebarButtons.repaint();
    }

    private Team decideFirstTurn() {
        return Math.random() < 0.5 ? match.getTeam1() : match.getTeam2();
    }

    public void entityActed(Entity entity) {
        actedEntities.add(entity);

        Set<Entity> activeEntities = getActiveEntities(turn);

        if (actedEntities.containsAll(activeEntities)) {
            endTurn();
        }
    }

    private Set<Entity> getActiveEntities(Team team) {
        Set<Entity> activeEntities = new HashSet<>();

        for (Character character : team.getCharacters()) {
            if (character.getHealth() > 0) {
                activeEntities.add(character);
            }
        }

        activeEntities.addAll(team.getTowers());
        return activeEntities;
    }

    private void endTurn() {
        actedEntities.clear();
        processReviveQueue();
        toggleTurn();
        updateSidebarInfo();
    }

    private void toggleTurn() {
        turn = turn.equals(match.getTeam1()) ? match.getTeam2() : match.getTeam1();
        match.getTeam1().getCharacters().forEach(Character::regenerateMana);
        match.getTeam2().getCharacters().forEach(Character::regenerateMana);
        for (MatrixButton[] row : matrixButtons) {
            for (MatrixButton b : row) {
                b.refresh();
            }
        }

        reviveQueue.addAll(pendingRevive);
        pendingRevive.clear();
    }

    private void processReviveQueue() {
        Set<Character> revivedCharacters = new HashSet<>();
        for (Character deadCharacter : reviveQueue) {
            MatrixButton towerBtn = findNearestTower(deadCharacter.getTeam());  // Encuentra una torre aliada
            if (towerBtn != null) {
                reviveCharacter(deadCharacter, towerBtn);  // Revive cerca de la torre
                revivedCharacters.add(deadCharacter);
            }
        }
        reviveQueue.removeAll(revivedCharacters);
    }

    private MatrixButton findNearestTower(Team team) {
        for (MatrixButton[] row : matrixButtons) {
            for (MatrixButton btn : row) {
                if (btn.getEntity() instanceof Tower && team.getEntities().contains(btn.getEntity())) {
                    return btn;  // Encuentra la primera torre aliada
                }
            }
        }
        return null;
    }

    private void reviveCharacter(Character character, MatrixButton towerBtn) {
        int btnRow = Math.divideExact(towerBtn.getIdentifier(), 10);
        int btnCol = towerBtn.getIdentifier() % 10;

        MatrixButton[] adjacents = getAdjacentButtons(btnRow, btnCol);
        for (MatrixButton btn : adjacents) {
            if (btn != null && btn.getEntity() == null) {
                btn.setEntity(character, match.getTeam1(), match.getTeam2());
                character.setHealth(character.getMaxHealth());
                return;
            }
        }
    }

    private MatrixButton[] getAdjacentButtons(int row, int col) {
        MatrixButton[] buttons = new MatrixButton[4];
        try {
            buttons[0] = matrixButtons[row - 1][col];  // Arriba
        } catch (ArrayIndexOutOfBoundsException ignored) {}
        try {
            buttons[1] = matrixButtons[row + 1][col];  // Abajo
        } catch (ArrayIndexOutOfBoundsException ignored) {}
        try {
            buttons[2] = matrixButtons[row][col - 1];  // Izquierda
        } catch (ArrayIndexOutOfBoundsException ignored) {}
        try {
            buttons[3] = matrixButtons[row][col + 1];  // Derecha
        } catch (ArrayIndexOutOfBoundsException ignored) {}

        return buttons;
    }

    // Llama cuando el personaje muere para añadirlo a la cola de reaparición
    public void addToPendingRevive(Character character) {
        pendingRevive.add(character);
    }

    private void updateSidebarInfo() {
        JPanel sidebarInfo = (JPanel) sidebar.getComponent(0);
        sidebarInfo.removeAll();
        JLabel turnLabel = new JLabel("Turno de " + turn.getPlayer().getName());
        turnLabel.setFont(new Font("Arial", Font.BOLD, 20));
        sidebarInfo.add(turnLabel);
        sidebarInfo.revalidate();
        sidebarInfo.repaint();
    }

    public void enableAllButtons() {
        for (MatrixButton[] row : matrixButtons) {
            for (MatrixButton btn : row) {
                btn.setEnabled(true);
            }
        }
    }

    public void endGame(Player winner) {
        JDialog dialog = new JDialog();
        dialog.setTitle("Game Over");
        dialog.setSize(300, 200);
        dialog.setLayout(new BorderLayout());
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setLocationRelativeTo(null);  // Centrar el diálogo en la pantalla

        JPanel messagePanel = new JPanel();
        messagePanel.setLayout(new BoxLayout(messagePanel, BoxLayout.Y_AXIS));
        messagePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel winnerMsg = new JLabel("The winner is " + winner.getName(), SwingConstants.CENTER);
        winnerMsg.setFont(new Font("Arial", Font.BOLD, 16));
        winnerMsg.setForeground(new Color(0, 102, 204));
        winnerMsg.setAlignmentX(Component.CENTER_ALIGNMENT);

        messagePanel.add(winnerMsg);

        JButton homeBtn = new ButtonComponent("Go Home", CustomColors.GREEN);
        homeBtn.setFont(new Font("Arial", Font.BOLD, 14));
        homeBtn.setBackground(new Color(0, 153, 76));
        homeBtn.setForeground(Color.WHITE);
        homeBtn.setFocusPainted(false);
        homeBtn.setPreferredSize(new Dimension(100, 40));
        homeBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        buttonPanel.add(homeBtn);

        dialog.add(messagePanel, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        homeBtn.addActionListener(e -> {
            Player p1 = match.getTeam1().getPlayer();
            Player p2 = match.getTeam2().getPlayer();

            p1.getStatistics().setPlayedGames(p1.getStatistics().getPlayedGames() + 1);
            p2.getStatistics().setPlayedGames(p2.getStatistics().getPlayedGames() + 1);
            winner.getStatistics().setWins(winner.getStatistics().getWins() + 1);

            if (winner.equals(p1)) p2.getStatistics().setLosses(p2.getStatistics().getLosses() + 1);
            else p1.getStatistics().setLosses(p1.getStatistics().getLosses() + 1);

            DB.addGame(new Game(UUID.randomUUID(), p1, p2, winner));
            DB.saveData();

            dialog.dispose();
            MainGameWindow mainWindow = (MainGameWindow) SwingUtilities.getRoot(this);
            mainWindow.showStartScreen();
        });

        dialog.setVisible(true);
    }


    public Match getMatch() {
        return match;
    }
}
