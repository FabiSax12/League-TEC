package view;

import controller.CharacterSelectionController;
import models.*;
import models.Character;
import view.components.ButtonComponent;
import view.components.CustomColors;
import view.components.ListComponent;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * CharacterSelection is a JPanel that allows players to select characters for their teams before a match.
 * It handles the display of available characters, the selection for each player, and confirms the selection.
 */
public class CharacterSelection extends JPanel {
    private final DefaultListModel<Character> listModel1;
    private final DefaultListModel<Character> listModel2;
    private final JList<Character> selectionList;
    private final JButton confirmButton;

    /**
     * Constructor that initializes the character selection panel.
     *
     * @param mainWindow Reference to the main game window.
     * @param team1      First team to select characters.
     * @param team2      Second team to select characters.
     */
    public CharacterSelection(MainGameWindow mainWindow, Team team1, Team team2) {
        CharacterSelectionController controller = new CharacterSelectionController(this);

        setSize(500, 500);
        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel(new GridLayout(1, 3));

        listModel1 = new DefaultListModel<>();
        listModel2 = new DefaultListModel<>();
        DefaultListModel<Character> availableListModel = new DefaultListModel<>();

        List<Character> availableCharacters = controller.getAvailableCharacters();
        availableCharacters.forEach(availableListModel::addElement);

        selectionList = new ListComponent<>(availableListModel);
        selectionList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JPanel player1Panel = new JPanel(new BorderLayout());
        player1Panel.setBorder(BorderFactory.createTitledBorder(team1.getName()));
        JList<Character> player1List = new ListComponent<>(listModel1);
        player1Panel.add(new JScrollPane(player1List), BorderLayout.CENTER);
        JButton addButton1 = new ButtonComponent("Añadir a " + team1.getName());
        player1Panel.add(addButton1, BorderLayout.SOUTH);

        JPanel player2Panel = new JPanel(new BorderLayout());
        player2Panel.setBorder(BorderFactory.createTitledBorder(team2.getName()));
        JList<Character> player2List = new ListComponent<>(listModel2);
        player2Panel.add(new JScrollPane(player2List), BorderLayout.CENTER);
        JButton addButton2 = new ButtonComponent("Añadir a " + team2.getName());
        player2Panel.add(addButton2, BorderLayout.SOUTH);

        JPanel selectionPanel = new JPanel(new BorderLayout());
        selectionPanel.setBorder(BorderFactory.createTitledBorder("Personajes Disponibles"));
        selectionPanel.add(new JScrollPane(selectionList), BorderLayout.CENTER);

        mainPanel.add(player1Panel);
        mainPanel.add(selectionPanel);
        mainPanel.add(player2Panel);

        confirmButton = new ButtonComponent("Confirmar Selección", CustomColors.GREEN);
        confirmButton.setEnabled(false);

        add(mainPanel, BorderLayout.CENTER);
        add(confirmButton, BorderLayout.SOUTH);

        selectionList.setCellRenderer(new DefaultListCellRenderer() {
            public Component getListCellRendererComponent(JList<?> list, Character value, int index, boolean isSelected, boolean cellHasFocus) {
                System.out.println("Render");
                Component componente = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

                // Si el elemento está deshabilitado, cambiar su apariencia
                if (listModel1.contains(value) || listModel2.contains(value)) {
                    componente.setEnabled(false);  // Aparece gris
                    componente.setForeground(Color.GRAY);
                } else {
                    componente.setEnabled(true);   // Aparece normal
                    componente.setForeground(Color.BLACK);
                }

                return componente;
            }
        });

        selectionList.addListSelectionListener(e -> {
            System.out.println("Selection");
            if (!e.getValueIsAdjusting()) {
                Character valorSeleccionado = selectionList.getSelectedValue();

                // Evitar la selección de elementos deshabilitados
                if (listModel1.contains(valorSeleccionado) || listModel2.contains(valorSeleccionado)) {
                    JOptionPane.showMessageDialog(
                            mainWindow,
                            "Este personaje ya ha sido seleccionado por un jugador.",
                            "Elemento deshabilitado",
                            JOptionPane.WARNING_MESSAGE
                    );
                    selectionList.clearSelection();  // Quitar la selección
                }
            }
        });

        addButton1.addActionListener(e -> {
            controller.addCharacterToTeam(selectionList.getSelectedValue(), team1, listModel1);
            selectionList.clearSelection();
        });

        addButton2.addActionListener(e -> {
            controller.addCharacterToTeam(selectionList.getSelectedValue(), team2, listModel2);
            selectionList.clearSelection();
        });

        confirmButton.addActionListener(e -> {
            controller.confirmSelection(team1, team2);
            mainWindow.startGame(team1, team2);
        });

        setVisible(true);
    }

    /**
     * Gets the list model for player 1's selected characters.
     *
     * @return The list model for player 1's characters.
     */
    public DefaultListModel<Character> getPlayer1ListModel() {
        return listModel1;
    }

    /**
     * Gets the list model for player 2's selected characters.
     *
     * @return The list model for player 2's characters.
     */
    public DefaultListModel<Character> getPlayer2ListModel() {
        return listModel2;
    }

    /**
     * Enables or disables the confirm button based on the selection state.
     *
     * @param enabled true to enable the confirm button, false to disable it.
     */
    public void enableConfirmButton(boolean enabled) {
        confirmButton.setEnabled(enabled);
    }
}
