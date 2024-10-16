package view.components;

import models.Character;

import javax.swing.*;
import java.awt.*;

/**
 * Custom JList component with a personalized cell renderer to display detailed character information.
 *
 * @param <E> the type of elements in this list, expected to be instances of Character.
 */
public class CharacterListComponent<E extends Character> extends JList<E> {

    /**
     * Constructs a CharacterListComponent with the specified list model.
     *
     * <p>Applies a custom font, sets the selection mode to single selection,
     * and assigns a custom renderer to display character details in the list cells.</p>
     *
     * @param listModel the model to use for the list.
     */
    public CharacterListComponent(DefaultListModel<E> listModel) {
        super(listModel);
        setFont(new Font("Consolas", Font.PLAIN, 14));
        setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        setFixedCellHeight(90);  // Fixed height for each cell to accommodate multiple lines
        setCellRenderer(new CharacterListCellRenderer());
    }

    /**
     * Custom renderer for list cells, displaying detailed character information
     * such as name, element, health, mana, damage, and defense.
     */
    private static class CharacterListCellRenderer extends DefaultListCellRenderer {

        /**
         * Returns a component configured to display the specified character details in the list.
         *
         * <p>This method displays detailed character information in a structured way,
         * including name, element, health, mana, damage, and defense.</p>
         *
         * @param list the JList we're painting.
         * @param value the value to display (an instance of Character).
         * @param index the cell's index.
         * @param isSelected true if the specified cell is selected.
         * @param cellHasFocus true if the specified cell has focus.
         * @return the component used to render the list cell.
         */
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            Character character = (Character) value;

            // Panel to hold the character information
            JPanel panel = new JPanel(new GridLayout(3, 1)); // 3 rows layout
            panel.setOpaque(true);

            // First row: character name and element
            JPanel firstRow = new JPanel(new FlowLayout(FlowLayout.LEFT));
            firstRow.setOpaque(false);
            JLabel nameLabel = new JLabel(character.getName());
            JLabel elementLabel = new JLabel(character.getElement().toString());
            firstRow.add(nameLabel);
            firstRow.add(Box.createHorizontalStrut(20)); // Spacer
            firstRow.add(elementLabel);

            // Second row: character stats (health, mana)
            JPanel secondRow = new JPanel(new FlowLayout(FlowLayout.LEFT));
            secondRow.setOpaque(false);
            JLabel healthLabel = new JLabel("HP: " + character.getHealth());
            JLabel manaLabel = new JLabel("Mana: " + character.getMana());
            secondRow.add(healthLabel);
            secondRow.add(Box.createHorizontalStrut(10));
            secondRow.add(manaLabel);

            // Third row: character stats (damage, defense)
            JPanel thirdRow = new JPanel(new FlowLayout(FlowLayout.LEFT));
            thirdRow.setOpaque(false);
            JLabel damageLabel = new JLabel("Damage: " + character.getDamage());
            JLabel defenseLabel = new JLabel("Defense: " + character.getDefense());
            thirdRow.add(damageLabel);
            thirdRow.add(Box.createHorizontalStrut(10));
            thirdRow.add(defenseLabel);

            // Add rows to the panel
            panel.add(firstRow);
            panel.add(secondRow);
            panel.add(thirdRow);

            // Apply alternate row colors when not selected
            if (!isSelected) {
                if (index % 2 == 0) {
                    panel.setBackground(new Color(240, 240, 240)); // Light gray for even rows
                } else {
                    panel.setBackground(Color.WHITE); // White for odd rows
                }
            } else {
                panel.setBackground(new Color(0, 111, 238)); // Blue when selected
                nameLabel.setForeground(Color.WHITE);
                elementLabel.setForeground(Color.WHITE);
                healthLabel.setForeground(Color.WHITE);
                manaLabel.setForeground(Color.WHITE);
                damageLabel.setForeground(Color.WHITE);
                defenseLabel.setForeground(Color.WHITE);
            }

            if (!isSelected) {
                nameLabel.setForeground(Color.BLACK);
                elementLabel.setForeground(Color.BLACK);
                healthLabel.setForeground(Color.BLACK);
                manaLabel.setForeground(Color.BLACK);
                damageLabel.setForeground(Color.BLACK);
                defenseLabel.setForeground(Color.BLACK);
            }

            panel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15)); // Padding for the list cell

            return panel;
        }
    }
}
