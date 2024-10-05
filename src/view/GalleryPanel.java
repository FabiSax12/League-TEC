package view;

import database.DB;
import models.Character;
import view.components.ButtonComponent;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class GalleryPanel extends JPanel {

    public GalleryPanel(MainGameWindow mainWindow) throws IOException {
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

            try {
                ImageIcon characterIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource(character.getSpritePath())));
                Image originalImage = characterIcon.getImage();
                Image scaledImage = getScaledImage(originalImage, 200, 200);

                JLabel imageLabel = new JLabel(new ImageIcon(scaledImage));
                characterPanel.add(imageLabel, BorderLayout.CENTER);
            } catch (NullPointerException e) {
                e.printStackTrace();
            }

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

        JButton btnBack = new ButtonComponent("Volver al Menú");
        btnBack.addActionListener(e -> mainWindow.showPanel("Menu"));
        add(btnBack, BorderLayout.SOUTH);
    }

    private Image getScaledImage(Image srcImg, int w, int h) {
        BufferedImage scaledImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = scaledImage.createGraphics();

        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.drawImage(srcImg, 0, 0, w, h, null);
        g2d.dispose();

        return scaledImage;
    }
}
