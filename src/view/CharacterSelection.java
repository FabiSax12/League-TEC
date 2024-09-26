package view;

import controller.CharacterSelectionController;
import models.*;
import models.Character;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class CharacterSelection extends JFrame {
    private final DefaultListModel<Character> listModel1;
    private final DefaultListModel<Character> listModel2;
    private final JList<Character> selectionList;
    private final JButton confirmButton;

    public CharacterSelection(CharacterSelectionController controller, Team team1, Team team2) {
        setTitle("Selección de Personajes");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Creación del panel principal
        JPanel mainPanel = new JPanel(new GridLayout(1, 3));

        // Inicializar modelos de listas
        listModel1 = new DefaultListModel<>();
        listModel2 = new DefaultListModel<>();
        DefaultListModel<Character> availableListModel = new DefaultListModel<>();

        // Obtener personajes disponibles del controlador
        List<Character> availableCharacters = controller.getAvailableCharacters();
        availableCharacters.forEach(availableListModel::addElement);

        selectionList = new JList<>(availableListModel);
        selectionList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Panel para jugador 1
        JPanel player1Panel = new JPanel(new BorderLayout());
        player1Panel.setBorder(BorderFactory.createTitledBorder(team1.getName()));
        JList<Character> player1List = new JList<>(listModel1);
        player1Panel.add(new JScrollPane(player1List), BorderLayout.CENTER);
        JButton addButton1 = new JButton("Añadir a " + team1.getName());
        player1Panel.add(addButton1, BorderLayout.SOUTH);

        // Panel para jugador 2
        JPanel player2Panel = new JPanel(new BorderLayout());
        player2Panel.setBorder(BorderFactory.createTitledBorder(team2.getName()));
        JList<Character> player2List = new JList<>(listModel2);
        player2Panel.add(new JScrollPane(player2List), BorderLayout.CENTER);
        JButton addButton2 = new JButton("Añadir a " + team2.getName());
        player2Panel.add(addButton2, BorderLayout.SOUTH);

        // Panel de selección
        JPanel selectionPanel = new JPanel(new BorderLayout());
        selectionPanel.setBorder(BorderFactory.createTitledBorder("Personajes Disponibles"));
        selectionPanel.add(new JScrollPane(selectionList), BorderLayout.CENTER);

        // Añadir paneles al panel principal
        mainPanel.add(player1Panel);
        mainPanel.add(selectionPanel);
        mainPanel.add(player2Panel);

        // Botón de confirmación
        confirmButton = new JButton("Confirmar Selección");
        confirmButton.setEnabled(false);

        // Añadir paneles y botón a la ventana
        add(mainPanel, BorderLayout.CENTER);
        add(confirmButton, BorderLayout.SOUTH);

        // Acción para añadir personaje al jugador 1
        addButton1.addActionListener(e -> controller.addCharacterToTeam(selectionList.getSelectedValue(), team1, listModel1));

        // Acción para añadir personaje al jugador 2
        addButton2.addActionListener(e -> controller.addCharacterToTeam(selectionList.getSelectedValue(), team2, listModel2));

        // Acción para confirmar selección
        confirmButton.addActionListener(e -> controller.confirmSelection());

        setVisible(true);
    }

    public DefaultListModel<Character> getPlayer1ListModel() {
        return listModel1;
    }

    public DefaultListModel<Character> getPlayer2ListModel() {
        return listModel2;
    }

    public void enableConfirmButton(boolean enabled) {
        confirmButton.setEnabled(enabled);
    }

    public void disableCharacterOption() {

    }
}
