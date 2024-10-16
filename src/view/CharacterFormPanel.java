package view;

import models.ASkill;
import models.Character;
import models.Element;
import models.skills.AttackSkill;
import models.skills.BuffSkill;
import models.skills.HealSkill;
import view.components.ButtonComponent;
import view.components.ComboBoxComponent;
import view.components.CustomColors;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public abstract class CharacterFormPanel extends JPanel {
    protected final JTextField nameField;
    protected final JTextField healthField;
    protected final JTextField manaField;
    protected final JTextField attackField;
    protected final JTextField defenseField;
    protected final JTextField movementsField;
    protected final ComboBoxComponent<Element> elementField;
    protected final JLabel imageLabel;
    protected final SkillInputs[] skillInputs;
    protected File selectedImageFile;
    MainGameWindow mainPanel;

    public CharacterFormPanel(Character character, MainGameWindow mainPanel) {
        this.mainPanel = mainPanel;

        setLayout(new BorderLayout());
        setBackground(new Color(245, 245, 245));

        // Panel de entrada con scroll
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridBagLayout());  // Usamos GridBagLayout para alineación
        inputPanel.setBackground(new Color(250, 250, 250));
        inputPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JScrollPane scrollPane = new JScrollPane(inputPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;

        // Campos del personaje
        nameField = createTextField("Nombre:", character != null ? character.getName() : "", inputPanel, gbc);
        healthField = createTextField("Salud:", character != null ? String.valueOf(character.getHealth()) : "", inputPanel, gbc);
        manaField = createTextField("Mana:", character != null ? String.valueOf(character.getMana()) : "", inputPanel, gbc);
        attackField = createTextField("Ataque:", character != null ? String.valueOf(character.getDamage()) : "", inputPanel, gbc);
        defenseField = createTextField("Defensa:", character != null ? String.valueOf(character.getDefense()) : "", inputPanel, gbc);
        movementsField = createTextField("Movimientos:", character != null ? String.valueOf(character.getMovements()) : "", inputPanel, gbc);

        // Selector de elemento
        JLabel elementLabel = new JLabel("Elemento:");
        inputPanel.add(elementLabel, gbc);
        gbc.gridx = 1;
        elementField = new ComboBoxComponent<>(Element.values());
        elementField.setSelectedItem(character != null ? character.getElement() : Element.FIRE);
        inputPanel.add(elementField, gbc);
        gbc.gridx = 0;
        gbc.gridy++;

        // Cargar imagen
        imageLabel = new JLabel(character != null ? "Imagen actual: " + character.getSpritePath() : "Imagen no seleccionada");
        imageLabel.setBorder(new LineBorder(Color.LIGHT_GRAY, 1, true));
        inputPanel.add(imageLabel, gbc);
        gbc.gridx = 1;

        JButton loadImageButton = new JButton("Subir Imagen");
        styleButton(loadImageButton, new Color(70, 130, 180), Color.WHITE);
        loadImageButton.addActionListener(e -> loadImage());
        inputPanel.add(loadImageButton, gbc);

        gbc.gridx = 0;
        gbc.gridy++;

        // Habilidades del personaje
        skillInputs = new SkillInputs[4];
        int length = character != null ? character.getSkills().length : 4;
        for (int i = 0; i < length; i++) {
            skillInputs[i] = new SkillInputs(inputPanel, i + 1, character != null ? character.getSkills()[i] : null, gbc);
        }

        JPanel buttonsPanel = new JPanel();

        JButton cancelBtn = new ButtonComponent("Cancelar", CustomColors.RED);
        cancelBtn.addActionListener(e -> {
            mainPanel.showPanel("Gallery");
        });

        buttonsPanel.add(cancelBtn);
        buttonsPanel.add(getSaveButton());

        add(scrollPane, BorderLayout.CENTER);
        add(buttonsPanel, BorderLayout.SOUTH);
    }

    // Método para crear un campo de texto alineado con etiquetas
    private JTextField createTextField(String label, String value, JPanel panel, GridBagConstraints gbc) {
        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel(label), gbc);
        gbc.gridx = 1;
        JTextField textField = new JTextField(value);
        textField.setPreferredSize(new Dimension(200, 30));  // Tamaño predefinido para más espacio
        textField.setBorder(new LineBorder(Color.LIGHT_GRAY, 1, true));
        panel.add(textField, gbc);
        return textField;
    }

    protected abstract JButton getSaveButton();  // Método abstracto para definir el botón "Guardar"

    private void loadImage() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            selectedImageFile = fileChooser.getSelectedFile();
            imageLabel.setText("Imagen seleccionada: " + selectedImageFile.getName());

            try {
                saveImageToAssets(selectedImageFile);
            } catch (IOException ioException) {
                JOptionPane.showMessageDialog(null, "Error al guardar la imagen.");
            }
        }
    }

    protected void saveImageToAssets(File sourceFile) throws IOException {
        String projectPath = System.getProperty("user.dir");
        File assetsDir = new File(projectPath + "/src/assets");

        if (!assetsDir.exists()) assetsDir.mkdirs();

        Path destinationPath = new File(assetsDir, sourceFile.getName()).toPath();

        Files.copy(sourceFile.toPath(), destinationPath, StandardCopyOption.REPLACE_EXISTING);

        imageLabel.setText("Imagen guardada en: assets/" + sourceFile.getName());
    }

    protected abstract void saveCharacterData(ActionEvent e);  // Método abstracto para que cada subclase implemente la lógica de guardar

    protected static class SkillInputs {
        JPanel attackPanel, buffPanel, healPanel, skillPanel;
        JTextField nameField, manaField;
        JTextField damageField, statField, boostField, healthField;
        ComboBoxComponent<String> typeField;
        ComboBoxComponent<Element> elementField;

        SkillInputs(JPanel inputPanel, int n, ASkill skill, GridBagConstraints gbc) {
            gbc.gridx = 0;
            gbc.gridy++;

            JLabel subTitle = new JLabel("Habilidad " + n);
            subTitle.setFont(new Font("Arial", Font.BOLD, 16));
            inputPanel.add(subTitle, gbc);
            gbc.gridy++;

            gbc.gridx = 0;
            inputPanel.add(new JLabel("Nombre:"), gbc);
            gbc.gridx = 1;
            nameField = new JTextField(skill != null ? skill.getName() : "");
            inputPanel.add(nameField, gbc);

            gbc.gridy++;
            gbc.gridx = 0;
            inputPanel.add(new JLabel("Mana:"), gbc);
            gbc.gridx = 1;
            manaField = new JTextField(skill != null ? String.valueOf(skill.getManaCost()) : "");
            inputPanel.add(manaField, gbc);

            gbc.gridy++;
            gbc.gridx = 0;
            inputPanel.add(new JLabel("Tipo:"), gbc);
            gbc.gridx = 1;
            String[] skillTypes = {"Attack", "Buff", "Heal"};
            typeField = new ComboBoxComponent<>(skillTypes);
            inputPanel.add(typeField, gbc);

            gbc.gridy++;
            gbc.gridx = 0;
            inputPanel.add(new JLabel("Elemento:"), gbc);
            gbc.gridx = 1;
            elementField = new ComboBoxComponent<>(Element.values());
            inputPanel.add(elementField, gbc);

            skillPanel = new JPanel(new CardLayout());

            // Panel para tipo "Attack"
            attackPanel = new JPanel(new GridLayout(1, 2));
            attackPanel.add(new JLabel("Daño:"));
            damageField = new JTextField(skill != null && skill instanceof AttackSkill attack ? String.valueOf(attack.getDamage()) : "");
            attackPanel.add(damageField);

            // Panel para tipo "Buff"
            buffPanel = new JPanel(new GridLayout(2, 2));
            buffPanel.add(new JLabel("Estadística:"));
            statField = new JTextField(skill != null && skill instanceof BuffSkill buff ? buff.getStat() : "");
            buffPanel.add(statField);
            buffPanel.add(new JLabel("Aumento %:"));
            boostField = new JTextField(skill != null && skill instanceof BuffSkill buff ? String.valueOf(buff.getBoost()) : "");
            buffPanel.add(boostField);

            // Panel para tipo "Heal"
            healPanel = new JPanel(new GridLayout(1, 2));
            healPanel.add(new JLabel("Curación:"));
            healthField = new JTextField(skill != null && skill instanceof HealSkill heal ? String.valueOf(heal.getHealAmount()) : "");
            healPanel.add(healthField);

            skillPanel.add(attackPanel, "Attack");
            skillPanel.add(buffPanel, "Buff");
            skillPanel.add(healPanel, "Heal");

            gbc.gridy++;
            gbc.gridx = 1;
            inputPanel.add(skillPanel, gbc);

            CardLayout cl = (CardLayout) skillPanel.getLayout();
            cl.show(skillPanel, skill != null ? skill.getClass().getSimpleName() : "Attack");

            typeField.addActionListener(e -> cl.show(skillPanel, (String) typeField.getSelectedItem()));
        }

        public void save(Character character) {
            String skillType = (String) typeField.getSelectedItem();
            Element element = (Element) elementField.getSelectedItem();
            switch (skillType) {
                case "Attack" -> {
                    int damage = Integer.parseInt(damageField.getText());
                    AttackSkill attackSkill = new AttackSkill(nameField.getText(), damage, Integer.parseInt(manaField.getText()), element);
                    character.addSkill(attackSkill);
                }
                case "Buff" -> {
                    String stat = statField.getText();
                    double boost = Double.parseDouble(boostField.getText());
                    BuffSkill buffSkill = new BuffSkill(nameField.getText(), stat, boost, Integer.parseInt(manaField.getText()), element);
                    character.addSkill(buffSkill);
                }
                case "Heal" -> {
                    int heal = Integer.parseInt(healthField.getText());
                    HealSkill healSkill = new HealSkill(nameField.getText(), heal, Integer.parseInt(manaField.getText()), element);
                    character.addSkill(healSkill);
                }
            }
        }
    }

    private void styleButton(JButton button, Color background, Color foreground) {
        button.setBackground(background);
        button.setForeground(foreground);
        button.setFocusPainted(false);
        button.setBorder(new LineBorder(background.darker(), 1, true));
        button.setPreferredSize(new Dimension(150, 40));
    }
}
