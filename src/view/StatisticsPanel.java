package view;

import database.DB;
import models.Player;
import view.components.Button;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class StatisticsPanel extends JPanel {
    private final Player[] players;
    private final JTable statsTable;
    private final DefaultTableModel tableModel;

    public StatisticsPanel(MainGameWindow mainWindow) {
        this.players = DB.getPlayers().toArray(new Player[0]);

        setLayout(new BorderLayout());

        // Título
        JLabel titleLabel = new JLabel("Estadísticas de Jugadores", JLabel.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 24));
        add(titleLabel, BorderLayout.NORTH);

        // Crear el modelo de la tabla
        String[] columnNames = {"Jugador", "PJ", "PG", "PP", "% Victorias", "Muertes Personajes", "Torres Destruidas"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Evitar que las celdas sean editables
            }
        };

        // Crear la tabla y colocarla en un JScrollPane
        statsTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(statsTable);
        add(scrollPane, BorderLayout.CENTER);

        // Botón para volver al menú principal
        JButton btnBack = new Button("Volver al Menú");
        btnBack.addActionListener(e -> mainWindow.showPanel("Menu"));
        add(btnBack, BorderLayout.SOUTH);

        // Llenar la tabla con la información de los jugadores
        populateTable();
    }

    // Método para llenar la tabla con las estadísticas de los jugadores
    private void populateTable() {
        tableModel.setRowCount(0); // Limpiar la tabla antes de añadir nuevas filas

        for (Player player : players) {
            if (player != null && player.getStatistics() != null) {
                // Obtener las estadísticas del jugador
                Object[] rowData = {
                        player.getName(),
                        player.getStatistics().getPlayedGames(),
                        player.getStatistics().getWins(),
                        player.getStatistics().getLosses(),
                        String.format("%.2f", player.getStatistics().getWinPercentage()),
                        player.getStatistics().getDeadCharacters(),
                        player.getStatistics().getDestroyedTowers()
                };
                // Añadir la fila a la tabla
                tableModel.addRow(rowData);
            }
        }
    }
}
