package view;

import models.Character;
import models.ASkill;
import view.components.ButtonComponent;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;

public class CharacterDetailPanel extends JPanel {

    public CharacterDetailPanel(Character character, MainGameWindow mainWindow) {
        setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel(character.getName(), JLabel.CENTER);
        titleLabel.setFont(new Font("Consolas", Font.BOLD, 28));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        add(titleLabel, BorderLayout.NORTH);

        JPanel detailsPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;

        ImageIcon characterIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource(character.getSpritePath())));
        Image originalImage = characterIcon.getImage();
        Image scaledImage = getScaledImage(originalImage, 200, 200);
        JLabel imageLabel = new JLabel(new ImageIcon(scaledImage));
        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = 0;
        detailsPanel.add(imageLabel, gbc);

        gbc.gridwidth = 1;

        addDetailRow(detailsPanel, "Salud:", String.valueOf(character.getHealth()), gbc, 1);
        addDetailRow(detailsPanel, "Mana:", String.valueOf(character.getMana()), gbc, 2);
        addDetailRow(detailsPanel, "Daño:", String.valueOf(character.getDamage()), gbc, 3);
        addDetailRow(detailsPanel, "Defensa:", String.valueOf(character.getDefense()), gbc, 4);
        addDetailRow(detailsPanel, "Movimientos:", String.valueOf(character.getMovements()), gbc, 5);
        addDetailRow(detailsPanel, "Elemento:", character.getElement().toString(), gbc, 6);

        detailsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(detailsPanel, BorderLayout.CENTER);

        JPanel skillsPanel = getjPanel(character);

        add(skillsPanel, BorderLayout.SOUTH);

        JButton btnBack = new ButtonComponent("Volver a la galería");
        btnBack.addActionListener(e -> mainWindow.showPanel("Gallery"));

        JPanel backButtonPanel = new JPanel();
        backButtonPanel.add(btnBack);
        backButtonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        add(backButtonPanel, BorderLayout.PAGE_END);
    }

    private static JPanel getjPanel(Character character) {
        JPanel skillsPanel = new JPanel(new BorderLayout());
        skillsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel skillsLabel = new JLabel("Habilidades:", JLabel.CENTER);
        skillsLabel.setFont(new Font("Consolas", Font.BOLD, 22));
        skillsPanel.add(skillsLabel, BorderLayout.NORTH);

        JPanel skillListPanel = new JPanel(new GridLayout(0, 1, 5, 5));
        for (ASkill skill : character.getSkills()) {
            JLabel skillLabel = new JLabel("- " + skill.getName() + " (Costo de maná: " + skill.getManaCost() + ")");
            skillLabel.setFont(new Font("Consolas", Font.PLAIN, 16));
            skillListPanel.add(skillLabel);
        }
        skillsPanel.add(skillListPanel, BorderLayout.CENTER);
        return skillsPanel;
    }

    private void addDetailRow(JPanel panel, String label, String value, GridBagConstraints gbc, int row) {
        gbc.gridx = 0;
        gbc.gridy = row;
        JLabel nameLabel = new JLabel(label);
        nameLabel.setFont(new Font("Consolas", Font.BOLD, 18));
        panel.add(nameLabel, gbc);

        gbc.gridx = 1;
        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("Consolas", Font.PLAIN, 18));
        panel.add(valueLabel, gbc);
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
