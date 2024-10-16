package view;

import database.DB;
import models.Character;
import view.components.ButtonComponent;
import view.components.CustomColors;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Objects;

public class GalleryPanel extends JPanel {

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

            try {
                File imageFile = new File(System.getProperty("user.dir") + "\\src" + character.getSpritePath().replace("/", "\\"));
                if (imageFile.exists()) {
                    ImageIcon characterIcon = new ImageIcon(imageFile.getAbsolutePath());
                    Image originalImage = characterIcon.getImage();
                    Image scaledImage = getScaledImage(originalImage, 200, 200);
                    JLabel imageLabel = new JLabel(new ImageIcon(scaledImage));
                    characterPanel.add(imageLabel, BorderLayout.CENTER);
                } else {
                    // Maneja el caso de que la imagen no se encuentre
                    System.out.println("Imagen no encontrada: " + character.getSpritePath());
                }
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

        JPanel buttonPanel = new JPanel();
        JButton btnBack = new ButtonComponent("Volver al Menú", CustomColors.RED);
        JButton btnCreate = new ButtonComponent("Crear Personaje", CustomColors.BLUE);
        btnBack.addActionListener(e -> mainWindow.showPanel("Menu"));
        btnCreate.addActionListener(e -> mainWindow.showPanel("CreateCharacter"));

        buttonPanel.add(btnBack);
        buttonPanel.add(btnCreate);
        add(buttonPanel, BorderLayout.SOUTH);
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
