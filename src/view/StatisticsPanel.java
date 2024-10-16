package view;

import database.DB;
import models.Player;
import view.components.ButtonComponent;
import view.components.TableComponent;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * The StatisticsPanel displays player statistics such as games played, wins, losses, win percentage,
 * character deaths, and destroyed towers. It includes a table to visualize these statistics and a button
 * to return to the main menu.
 */
public class StatisticsPanel extends JPanel {
    private final Player[] players;  // Array of players whose statistics will be shown
    private final JTable statsTable;  // Table to display statistics
    private final DefaultTableModel tableModel;  // Table model to manage the table data

    /**
     * Constructs a StatisticsPanel that displays the player statistics and a back button to return to the menu.
     *
     * @param mainWindow The main game window that controls panel navigation.
     */
    public StatisticsPanel(MainGameWindow mainWindow) {
        this.players = DB.getPlayers().toArray(new Player[0]);

        setLayout(new BorderLayout());

        // Title label
        JLabel titleLabel = new JLabel("Estadísticas de Jugadores", JLabel.CENTER);
        titleLabel.setFont(new Font("Consolas", Font.BOLD, 24));
        add(titleLabel, BorderLayout.NORTH);

        // Table columns
        String[] columnNames = {"Jugador", "PJ", "PG", "PP", "% Victorias", "Muertes Personajes", "Torres Destruidas"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;  // Make the table non-editable
            }
        };

        // Create table with tableModel
        statsTable = new TableComponent(tableModel);
        JScrollPane scrollPane = new JScrollPane(statsTable);
        add(scrollPane, BorderLayout.CENTER);

        // Back button to return to the main menu
        JButton btnBack = new ButtonComponent("Volver al Menú");
        btnBack.addActionListener(e -> mainWindow.showPanel("Menu"));
        add(btnBack, BorderLayout.SOUTH);

        // Populate the table with data
        populateTable();
    }

    /**
     * Populates the table with player statistics.
     * Retrieves player data from the database and populates the table with each player's statistics.
     */
    private void populateTable() {
        tableModel.setRowCount(0);  // Clear the table before adding new rows

        for (Player player : players) {
            if (player != null && player.getStatistics() != null) {
                Object[] rowData = {
                        player.getName(),
                        player.getStatistics().getPlayedGames(),
                        player.getStatistics().getWins(),
                        player.getStatistics().getLosses(),
                        String.format("%.2f", player.getStatistics().getWinPercentage()),
                        player.getStatistics().getDeadCharacters(),
                        player.getStatistics().getDestroyedTowers()
                };
                tableModel.addRow(rowData);
            }
        }
    }
}

