package view;

import models.Character;
import models.ASkill;
import view.components.ButtonComponent;
import view.components.CustomColors;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * CharacterDetailPanel displays detailed information about a specific character, including
 * its attributes and skills.
 */
public class CharacterDetailPanel extends JPanel {

    /**
     * Constructs the CharacterDetailPanel.
     *
     * @param character  The character whose details are displayed.
     * @param mainWindow The main game window to allow navigation.
     */
    public CharacterDetailPanel(Character character, MainGameWindow mainWindow) {
        setLayout(new BorderLayout());

        // Title with character name
        JLabel titleLabel = new JLabel(character.getName(), JLabel.CENTER);
        titleLabel.setFont(new Font("Consolas", Font.BOLD, 28));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        add(titleLabel, BorderLayout.NORTH);

        // Panel to display character details
        JPanel detailsPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;

        // Load character image
        File imageFile = new File(System.getProperty("user.dir") + "\\src" + character.getSpritePath());
        if (imageFile.exists()) {
            ImageIcon characterIcon = new ImageIcon(imageFile.getAbsolutePath());
            Image originalImage = characterIcon.getImage();
            Image scaledImage = getScaledImage(originalImage, 200, 200);
            JLabel imageLabel = new JLabel(new ImageIcon(scaledImage));
            gbc.gridwidth = 2;
            gbc.gridx = 0;
            gbc.gridy = 0;
            detailsPanel.add(imageLabel, gbc);

            gbc.gridwidth = 1;
        } else {
            // Handle missing image
            System.out.println("Image not found: " + character.getSpritePath());
        }

        // Add character details to the panel
        addDetailRow(detailsPanel, "Health:", String.valueOf(character.getHealth()), gbc, 1);
        addDetailRow(detailsPanel, "Mana:", String.valueOf(character.getMana()), gbc, 2);
        addDetailRow(detailsPanel, "Damage:", String.valueOf(character.getDamage()), gbc, 3);
        addDetailRow(detailsPanel, "Defense:", String.valueOf(character.getDefense()), gbc, 4);
        addDetailRow(detailsPanel, "Movements:", String.valueOf(character.getMovements()), gbc, 5);
        addDetailRow(detailsPanel, "Element:", character.getElement().toString(), gbc, 6);

        detailsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(detailsPanel, BorderLayout.CENTER);

        // Add skills panel
        JPanel skillsPanel = getSkillsPanel(character);
        add(skillsPanel, BorderLayout.SOUTH);

        // Buttons to navigate back or edit the character
        JButton btnBack = new ButtonComponent("Back", CustomColors.RED);
        JButton editCharacter = new ButtonComponent("Edit", CustomColors.BLUE);
        btnBack.addActionListener(e -> mainWindow.showPanel("Gallery"));
        editCharacter.addActionListener(e -> mainWindow.editCharacter(character));

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(btnBack);
        buttonPanel.add(editCharacter);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        add(buttonPanel, BorderLayout.PAGE_END);
    }

    /**
     * Creates a panel that displays the character's skills.
     *
     * @param character The character whose skills are displayed.
     * @return A JPanel containing the skills.
     */
    private static JPanel getSkillsPanel(Character character) {
        JPanel skillsPanel = new JPanel(new BorderLayout());
        skillsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel skillsLabel = new JLabel("Skills:", JLabel.CENTER);
        skillsLabel.setFont(new Font("Consolas", Font.BOLD, 22));
        skillsPanel.add(skillsLabel, BorderLayout.NORTH);

        JPanel skillListPanel = new JPanel(new GridLayout(0, 1, 5, 5));
        for (ASkill skill : character.getSkills()) {
            JLabel skillLabel = new JLabel("- " + skill.getName() + " (Mana cost: " + skill.getManaCost() + ")");
            skillLabel.setFont(new Font("Consolas", Font.PLAIN, 16));
            skillListPanel.add(skillLabel);
        }
        skillsPanel.add(skillListPanel, BorderLayout.CENTER);
        return skillsPanel;
    }

    /**
     * Adds a row to the details panel.
     *
     * @param panel The panel to which the row is added.
     * @param label The label for the detail.
     * @param value The value of the detail.
     * @param gbc   The GridBagConstraints for positioning.
     * @param row   The row index in the grid.
     */
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

    /**
     * Scales an image to the specified width and height.
     *
     * @param srcImg The source image to scale.
     * @param w      The desired width.
     * @param h      The desired height.
     * @return The scaled image.
     */
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
