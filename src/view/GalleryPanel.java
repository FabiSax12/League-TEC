package view;

import database.DB;
import models.Character;
import utils.IMG;
import view.components.ButtonComponent;
import view.components.CustomColors;

import javax.swing.*;
import java.awt.*;
import java.io.File;

/**
 * GalleryPanel displays a gallery of all characters, allowing users to view and create characters.
 * The gallery is shown in a grid format with each character's image and name.
 */
public class GalleryPanel extends JPanel {

    /**
     * Constructs the GalleryPanel, displaying a grid of characters retrieved from the database.
     * Provides buttons to navigate back to the menu or create a new character.
     *
     * @param mainWindow The main window that manages panel transitions and displays character details.
     */
    public GalleryPanel(MainGameWindow mainWindow) {
        Character[] characters = DB.getCharacters().toArray(new Character[0]);

        setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Galería de Personajes", JLabel.CENTER);
        titleLabel.setFont(new Font("Consolas", Font.BOLD, 24));
        add(titleLabel, BorderLayout.NORTH);

        JPanel gridPanel = new JPanel(new GridLayout(0, 3, 10, 10));
        JScrollPane scrollPane = new JScrollPane(gridPanel);

        for (Character character : characters) {
            JPanel characterPanel = new JPanel();
            characterPanel.setLayout(new BorderLayout());
            Image image = IMG.toImageAndScale(character.getSpritePath(), 200, 200);
            JLabel imageLabel = new JLabel(new ImageIcon(image));
            characterPanel.add(imageLabel, BorderLayout.CENTER);

            JLabel nameLabel = new JLabel(character.getName(), JLabel.CENTER);
            characterPanel.add(nameLabel, BorderLayout.SOUTH);

            characterPanel.setCursor(new Cursor(Cursor.HAND_CURSOR));
            characterPanel.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent e) {
                    mainWindow.showCharacterDetails(character);
                }
            });

            gridPanel.add(characterPanel);
        }

        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton btnBack = new ButtonComponent("Volver al Menú", CustomColors.RED);
        JButton btnCreate = new ButtonComponent("Crear Personaje", CustomColors.BLUE);
        btnBack.addActionListener(e -> mainWindow.showPanel("Menu"));
        btnCreate.addActionListener(e -> mainWindow.showPanel("CreateCharacter"));

        buttonPanel.add(btnBack);
        buttonPanel.add(btnCreate);
        add(buttonPanel, BorderLayout.SOUTH);
    }
}