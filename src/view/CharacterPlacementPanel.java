package view;

import models.*;
import models.Character;
import utils.IMG;
import view.components.ButtonComponent;
import view.components.MatrixButton;

import java.util.ArrayList;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

/**
 * CharacterPlacementPanel is responsible for placing characters on the game grid before starting the match.
 * It handles the selection of characters by both teams and updates the grid accordingly.
 */
public class CharacterPlacementPanel extends JPanel {
    private final MatrixButton[][] gridButtons;
    private final Team team1;
    private final Team team2;
    private final DefaultListModel<Entity> team1Characters;
    private final DefaultListModel<Entity> team2Characters;
    private final JList<Entity> selectionList;
    private final JButton confirmButton;
    private boolean firstPlayerTime = true;
    private JLabel playerLabel;

    /**
     * Constructor to initialize the character placement panel.
     *
     * @param mainWindow Reference to the main game window.
     */
    public CharacterPlacementPanel(MainGameWindow mainWindow, Match match) {
        this.team1 = match.getTeam1();
        this.team2 = match.getTeam2();
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

        mainWindow.setExtendedState(JFrame.MAXIMIZED_BOTH);

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

        //GridBagLayout en lugar de GridLayout para mayor control sobre la distribución
        JPanel generalPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1.0;  // Permitir que se expanda verticalmente


        // Panel de la matriz (Grid de botones)
        JPanel gridCharactersPanel = new JPanel(new GridLayout(10, 10, 5, 5));
        gridCharactersPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        gridButtons = new MatrixButton[10][10];
        byte identifierCounter = 0;
        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                MatrixButton button = new MatrixButton();
                button.setPreferredSize(new Dimension(74, 74));
                button.setMaximumSize(new Dimension(74, 74));
                button.setMinimumSize(new Dimension(74, 74));
                button.setBackground(Color.LIGHT_GRAY);
                button.setIdentifier(identifierCounter);
                identifierCounter++;

                button.addActionListener(e -> placeCharacter(gridCharactersPanel,button));

                gridButtons[row][col] = button;
                gridCharactersPanel.add(button);
            }
        }
//        Ajustar la posición y proporciones de los componentes
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight=2;
        gbc.gridwidth=2;
        gbc.weightx = 1.0;  // La matriz ocupará el 80% del ancho
        generalPanel.add(gridCharactersPanel, gbc);
        gbc.weighty = 0.0;

        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.gridheight=2;
        gbc.gridwidth=1;
        gbc.weightx = 0.5;  // La lista ocupará el 10% del ancho
        gbc.weighty = 1.0;  // La lista ocupará el 10% del ancho
        generalPanel.add(scrollPane, gbc);

        add(generalPanel, BorderLayout.CENTER);

        confirmButton = new ButtonComponent("Confirmar");
        confirmButton.setEnabled(false);
        confirmButton.addActionListener(e -> mainWindow.startGameArena(match, gridButtons));

        JPanel controlPanel = new JPanel();
        controlPanel.add(confirmButton);
        add(controlPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    /**
     * Places the selected character on the grid.
     *
     * @param panel  The grid panel where the character will be placed.
     * @param button The button corresponding to the grid cell where the character will be placed.
     */
    private void placeCharacter(JPanel panel,MatrixButton button) {
        if(selectionList.getSelectedValue() == null){
            JOptionPane.showMessageDialog(
                    this,
                    "Debes seleccionar un personaje para agregarlo.",
                    "Error de selección",
                    JOptionPane.WARNING_MESSAGE
            );
        }else{
            if (firstPlayerTime && validatePlayerArea(button)) {
                button.setEnabled(false);
                button.setEntity(selectionList.getSelectedValue(), team1, team2);
                int selection = selectionList.getSelectedIndex();
                team1Characters.remove(selection);
                if (team1Characters.isEmpty()) changePlayer();
                ConfForEachButton(button);
            }else if (validatePlayerArea(button)){
                button.setEnabled(false);
                button.setEntity(selectionList.getSelectedValue(), team1, team2);
                int selection = selectionList.getSelectedIndex();
                team2Characters.remove(selection);
                if (team2Characters.isEmpty()){
                    confirmButton.setEnabled(true);
                    disableMatrixButtons();
                }
                ConfForEachButton(button);
            }
        }
    }

    /**
     * Changes the current player to the next one.
     */
    private void changePlayer() {
        firstPlayerTime = false;
        SwingUtilities.invokeLater(() -> {// Asegurar que la actualización de la interfaz ocurra en el Event Dispatch Thread
            playerLabel.setText(team2.getName()+" coloca tus personajes");
            selectionList.setModel(team2Characters);
        });
    }

    /**
     * Disables all buttons on the grid once both players have placed their characters.
     */
    private void disableMatrixButtons(){
        for(MatrixButton[] buttonRow: gridButtons){for(MatrixButton button: buttonRow){button.setEnabled(false);}}
    }

    /**
     * Validates if the selected button is within the allowed area for the current player.
     *
     * @param button The button to validate.
     * @return true if the button is in the valid area, false otherwise.
     */
    private boolean validatePlayerArea(MatrixButton button){
        int buttonID=button.getIdentifier();
        if (firstPlayerTime){
            return (50 - buttonID) >= 1;
        }else{
            return (50 - buttonID) <= 0;
        }
    }

    /**
     * Configures the button for each character after placement, including setting the sprite image.
     *
     * @param btn The button to configure.
     */
    private void ConfForEachButton (MatrixButton btn){
//        if (btn.getIdentifier()<50){
//            btn.setEntity(btn);
//            btn.setFilter(new Color(255,0,0,100));
//        }
//        else{
//            btn.setEntity(team2,btn.getImagepath());
//            btn.setFilter(new Color(0,0,255,100));
//        }
//        btn.revalidate();
//        btn.repaint();
    }
}