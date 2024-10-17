package view;

import database.DB;
import models.Character;
import models.Element;
import view.components.ButtonComponent;
import view.components.CustomColors;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Panel for creating a new character in the application.
 *
 * <p>This class extends {@link CharacterFormPanel} to provide functionality for creating a new character.
 * It handles saving the character's data to the database and provides a customized save button.</p>
 */
public class CreateCharacter extends CharacterFormPanel {

    /**
     * Constructs the CreateCharacter panel.
     *
     * @param mainPanel the main panel for switching views in the application.
     */
    public CreateCharacter(MainGameWindow mainPanel) {
        super(null, mainPanel);  // Pass null since this is for creating a new character
    }

    /**
     * Creates and returns a customized save button.
     *
     * @return the save button that triggers character saving.
     */
    @Override
    protected JButton getSaveButton() {
        JButton saveButton = new ButtonComponent("Guardar Personaje", CustomColors.GREEN);
        saveButton.addActionListener(this::saveCharacterData);
        return saveButton;
    }

    /**
     * Saves the new character's data to the database.
     *
     * <p>This method is triggered when the user clicks the save button.
     * It validates the input fields, saves the character's image,
     * and stores the new character data in the database.</p>
     *
     * @param e the action event triggered by the save button.
     */
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