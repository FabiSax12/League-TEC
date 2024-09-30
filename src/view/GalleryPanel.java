package view;

import database.DB;
import models.Character;
import view.components.Button;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class GalleryPanel extends JPanel {

    public GalleryPanel(MainGameWindow mainWindow) {
        Character[] characters = DB.getCharacters().toArray(new Character[0]);

        setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Galería de Personajes", JLabel.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 24));
        add(titleLabel, BorderLayout.NORTH);

        JPanel gridPanel = new JPanel(new GridLayout(0, 4, 10, 10));
        JScrollPane scrollPane = new JScrollPane(gridPanel);

        for (Character character : characters) {
            JPanel characterPanel = new JPanel();
            characterPanel.setLayout(new BorderLayout());

            ImageIcon characterIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource(character.getSpritePath())));
            JLabel imageLabel = new JLabel(new ImageIcon(characterIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH)));
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

        JButton btnBack = new Button("Volver al Menú");
        btnBack.addActionListener(e -> mainWindow.showPanel("Menu"));
        add(btnBack, BorderLayout.SOUTH);
    }
}
