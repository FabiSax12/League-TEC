package view;

import models.Character;
import models.Entity;
import models.Team;
import models.Tower;
import view.components.Button;
import java.util.ArrayList;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class CharacterPlacementPanel extends JPanel {
    private final JButton[][] gridButtons;
    private final Team team1;
    private final Team team2;
    private final DefaultListModel<Entity> team1Characters;
    private final DefaultListModel<Entity> team2Characters;
    private final JList<Entity> selectionList;
    private int team1TowersPlaced = 0;
    private int team2TowersPlaced = 0;
    private JLabel statusLabel;
    private final JButton confirmButton;
    private boolean firstPlayerTime = true;
    private JLabel playerLabel;

    public CharacterPlacementPanel(MainGameWindow mainWindow, Team team1, Team team2) {
        this.team1 = team1;
        this.team2 = team2;
        this.team1Characters = new DefaultListModel<>();
        this.team2Characters = new DefaultListModel<>();
        ArrayList<Character> tempList = team1.getCharacters();
        for (Entity c:tempList){team1Characters.addElement(c);}
        tempList = team2.getCharacters();
        for (Entity c:tempList){team2Characters.addElement(c);}

        ArrayList<Tower> tempTList = team1.getTowers();
        System.out.println("El team1 uno tiene " + tempTList.size() + " Torres");
        for (Entity c:tempTList){team1Characters.addElement(c);}
        tempTList = team2.getTowers();
        for (Entity c:tempTList){team2Characters.addElement(c);}
        System.out.println("El team2 uno tiene " + tempTList.size() + " Torres");


        selectionList = new JList<>(team1Characters);
        selectionList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        selectionList.setPreferredSize(new Dimension(150, 600));  // Ancho fijo de 200px para la JList
        JScrollPane scrollPane = new JScrollPane(selectionList);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(20, 0,20, 0));

        setLayout(new BorderLayout());
        JLabel titleLabel = new JLabel("Coloca tus Personajes", JLabel.CENTER);
        playerLabel = new JLabel(team1.getName()+" coloca tus personajes", JLabel.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 24));
        playerLabel.setFont(new Font("Serif", Font.BOLD, 12));
        JPanel labelsPanel = new JPanel(new BorderLayout());
        labelsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20,0, 20));
        labelsPanel.add(titleLabel, BorderLayout.NORTH);
        labelsPanel.add(playerLabel,BorderLayout.BEFORE_LINE_BEGINS);
        add(labelsPanel,BorderLayout.NORTH);

        // Usamos GridBagLayout en lugar de GridLayout para mayor control sobre la distribución
        JPanel generalPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1.0;  // Permitir que se expanda verticalmente


        // Panel de la matriz (Grid de botones)
        JPanel gridCharactersPanel = new JPanel(new GridLayout(10, 10, 5, 5));
        gridCharactersPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        gridButtons = new JButton[10][10];
        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                JButton button = new JButton();
                button.setBackground(Color.LIGHT_GRAY);
                int finalRow = row;
                int finalCol = col;

                button.addActionListener(e -> placeCharacter(finalRow, finalCol, button));

                gridButtons[row][col] = button;
                gridCharactersPanel.add(button);
            }
        }
//        Ajustar la posición y proporciones de los componentes
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.8;  // La matriz ocupará el 80% del ancho
        generalPanel.add(gridCharactersPanel, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.1;  // La lista ocupará el 10% del ancho
        generalPanel.add(scrollPane, gbc);

        add(generalPanel, BorderLayout.CENTER);

        statusLabel = new JLabel("Jugador 1 coloca las torres", JLabel.CENTER);
        add(statusLabel, BorderLayout.SOUTH);

        confirmButton = new Button("Confirmar");
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

    private void placeCharacter(int row, int col, JButton button) {
        if(selectionList.getSelectedValue() == null){
            JOptionPane.showMessageDialog(
                    this,
                    "Debes seleccionar un personaje para agregarlo.",
                    "Erro de selección",
                    JOptionPane.WARNING_MESSAGE
            );
        }else{
            if (firstPlayerTime) {
                button.setBackground(Color.BLUE);
                button.setEnabled(false);
                int selection = selectionList.getSelectedIndex();
                team1Characters.remove(selection);
                if (team1Characters.isEmpty()){changePlayer();}
            }else{
                button.setBackground(Color.RED);
                button.setEnabled(false);
                int selection = selectionList.getSelectedIndex();
                team2Characters.remove(selection);
                if (team1Characters.isEmpty()){
                    confirmButton.setEnabled(true);
                }
            }

//            else if (row >= 5 && team2TowersPlaced < 1) {
//                button.setBackground(Color.RED);
//                button.setEnabled(false);
//                team2TowersPlaced++;
//                statusLabel.setText("Ambos jugadores han colocado las torres.");
//            }
//
//            if (team1TowersPlaced >= 1 && team2TowersPlaced >= 1) {
//                confirmButton.setEnabled(true);
//            }
        }
    }
    private void changePlayer() {
        firstPlayerTime = false;
        SwingUtilities.invokeLater(() -> {// Asegurar que la actualización de la interfaz ocurra en el Event Dispatch Thread
            playerLabel.setText(team2.getName()+" coloca tus personajes");
            selectionList.setModel(team2Characters);
        });
    }

}
