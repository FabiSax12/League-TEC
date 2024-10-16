package view;

import database.DB;
import models.Character;
import models.Element;
import view.components.ButtonComponent;
import view.components.CustomColors;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class CreateCharacter extends CharacterFormPanel {

    public CreateCharacter(MainGameWindow mainPanel) {
        super(null, mainPanel);
    }

    @Override
    protected JButton getSaveButton() {
        JButton saveButton = new ButtonComponent("Guardar Personaje", CustomColors.GREEN);
        saveButton.addActionListener(this::saveCharacterData);
        return saveButton;
    }

    @Override
    protected void saveCharacterData(ActionEvent e) {
        try {
            saveImageToAssets(selectedImageFile);

            String name = nameField.getText();
            float health = Float.parseFloat(healthField.getText());
            int mana = Integer.parseInt(manaField.getText());
            int attack = Integer.parseInt(attackField.getText());
            int defense = Integer.parseInt(defenseField.getText());
            int movements = Integer.parseInt(movementsField.getText());
            Element element = (Element) elementField.getSelectedItem();
            String spritePath = "/assets/" + selectedImageFile.getName();

            Character newCharacter = new Character(name, health, mana, attack, defense, movements, element, spritePath);

            for (SkillInputs skillInput : skillInputs) {
                skillInput.save(newCharacter);
            }

            DB.addCharacter(newCharacter);
            DB.saveData();
            JOptionPane.showMessageDialog(this, "Personaje guardado correctamente.");
            this.mainPanel.showPanel("Gallery");
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al guardar el personaje. Por favor, revisa los campos.");
        }
    }
}
