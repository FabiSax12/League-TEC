package view;

import database.DB;
import models.ASkill;
import models.Character;
import models.Element;
import models.skills.AttackSkill;
import models.skills.BuffSkill;
import models.skills.HealSkill;
import view.components.ButtonComponent;
import view.components.CustomColors;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * EditCharacterPanel provides a user interface to edit the details and skills of an existing character.
 * It extends the abstract CharacterFormPanel and pre-populates the form with the character's current data.
 */
public class EditCharacterPanel extends CharacterFormPanel {
    private final Character character;

    /**
     * Constructs the EditCharacterPanel and initializes the form with the provided character's details.
     * The skills of the character are pre-set into the appropriate fields.
     *
     * @param character The character to be edited.
     * @param mainPanel The main game window that manages transitions between panels.
     */
    public EditCharacterPanel(Character character, MainGameWindow mainPanel) {
        super(character, mainPanel);
        this.character = character;

        // Pre-populate skill fields with the character's existing skills
        for (int i = 0; i < character.getSkills().length; i++) {
            ASkill skill = character.getSkills()[i];
            SkillInputs skillInput = this.skillInputs[i];

            skillInput.elementField.setSelectedItem(skill.getElement());

            switch (skill) {
                case AttackSkill attackSkill -> skillInput.typeField.setSelectedItem("Attack");
                case BuffSkill buffSkill -> skillInput.typeField.setSelectedItem("Buff");
                case HealSkill healSkill -> skillInput.typeField.setSelectedItem("Heal");
                default -> {
                }
            }
        }
    }

    /**
     * Creates and returns a "Save Changes" button that triggers the saving process when clicked.
     *
     * @return A JButton configured to save the character's changes.
     */
    @Override
    protected JButton getSaveButton() {
        JButton saveButton = new ButtonComponent("Guardar Cambios", CustomColors.GREEN);
        saveButton.addActionListener(this::saveCharacterData);
        return saveButton;
    }

    /**
     * Handles the process of saving the character's edited data. Updates the character's details
     * and saves them in the database. Displays appropriate messages upon success or failure.
     *
     * @param e The action event triggered by the save button.
     */
    @Override
    protected void saveCharacterData(ActionEvent e) {
        try {
            // Save the selected image if it has been changed
            if (selectedImageFile != null) {
                saveImageToAssets(selectedImageFile);
                character.setSpritePath("/assets/" + selectedImageFile.getName());
            }

            // Update character's attributes
            character.setName(nameField.getText());
            character.setHealth(Float.parseFloat(healthField.getText()));
            character.setMana(Integer.parseInt(manaField.getText()));
            character.setDamage(Integer.parseInt(attackField.getText()));
            character.setDefense(Integer.parseInt(defenseField.getText()));
            character.setMovements(Integer.parseInt(movementsField.getText()));
            character.setElement((Element) elementField.getSelectedItem());

            // Update character's skills
            character.clearSkills();
            for (SkillInputs skillInput : skillInputs) {
                skillInput.save(character);
            }

            // Save the updated character data to the database
            DB.saveData();
            JOptionPane.showMessageDialog(this, "Personaje actualizado correctamente.");
            this.mainPanel.showPanel("Gallery");
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al actualizar el personaje. Por favor, revisa los campos.");
        }
    }
}