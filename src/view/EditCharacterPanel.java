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

public class EditCharacterPanel extends CharacterFormPanel {

    private final Character character;

    public EditCharacterPanel(Character character, MainGameWindow mainPanel) {
        super(character, mainPanel);
        this.character = character;

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

    @Override
    protected JButton getSaveButton() {
        JButton saveButton = new ButtonComponent("Guardar Cambios", CustomColors.GREEN);
        saveButton.addActionListener(this::saveCharacterData);
        return saveButton;
    }

    @Override
    protected void saveCharacterData(ActionEvent e) {
        try {
            // Guardar la imagen seleccionada si fue cambiada
            if (selectedImageFile != null) {
                saveImageToAssets(selectedImageFile);
                character.setSpritePath("/assets/" + selectedImageFile.getName());
            }

            // Actualizar los datos del personaje
            character.setName(nameField.getText());
            character.setHealth(Float.parseFloat(healthField.getText()));
            character.setMana(Integer.parseInt(manaField.getText()));
            character.setDamage(Integer.parseInt(attackField.getText()));
            character.setDefense(Integer.parseInt(defenseField.getText()));
            character.setMovements(Integer.parseInt(movementsField.getText()));
            character.setElement((Element) elementField.getSelectedItem());

            // Actualizar las habilidades
            character.clearSkills();
            for (SkillInputs skillInput : skillInputs) {
                skillInput.save(character);
            }

            // Guardar los cambios en la base de datos
            DB.saveData();
            JOptionPane.showMessageDialog(this, "Personaje actualizado correctamente.");
            this.mainPanel.showPanel("Gallery");
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al actualizar el personaje. Por favor, revisa los campos.");
        }
    }
}
