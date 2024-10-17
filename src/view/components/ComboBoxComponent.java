package view.components;

import javax.swing.*;
import java.awt.*;

/**
 * A custom JComboBox component with a personalized UI, including custom rendering and styles.
 *
 * @param <E> the type of the elements in this combo box.
 */
public class ComboBoxComponent<E> extends JComboBox<E> {

    /**
     * Constructs a ComboBoxComponent with the specified items.
     *
     * @param items the items to be displayed in the combo box.
     */
    public ComboBoxComponent(E[] items) {
        super(items);
        setUI(new CustomComboBoxUI());
        setFont(new Font("Consolas", Font.PLAIN, 16));
        setBackground(new Color(240, 240, 240));
        setForeground(Color.BLACK);
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setRenderer(new CustomComboBoxRenderer());
    }

    /**
     * Custom renderer for the combo box, allowing for personalized styling of list cells.
     */
    private class CustomComboBoxRenderer extends DefaultListCellRenderer {
        /**
         * Returns a component that has been configured to display the specified value in the combo box.
         *
         * @param list the JList we're painting.
         * @param value the value to display.
         * @param index the cell's index.
         * @param isSelected true if the specified cell was selected.
         * @param cellHasFocus true if the specified cell has focus.
         * @return the component used for drawing the value.
         */
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

            label.setFont(new Font("Consolas", Font.PLAIN, 16));
            label.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
            label.setOpaque(true);

            if (isSelected) {
                label.setBackground(new Color(0, 111, 238));
                label.setForeground(Color.WHITE);
            } else {
                label.setBackground(new Color(255, 255, 255));
                label.setForeground(Color.BLACK);
            }

            return label;
        }
    }

    /**
     * Custom UI for the combo box, applying custom borders and background colors.
     */
    private class CustomComboBoxUI extends javax.swing.plaf.basic.BasicComboBoxUI {
        /**
         * Installs the custom UI settings for the combo box.
         *
         * @param c the component to which this UI will be applied.
         */
        @Override
        public void installUI(JComponent c) {
            super.installUI(c);
            JComboBox<?> comboBox = (JComboBox<?>) c;
            comboBox.setBorder(BorderFactory.createLineBorder(new Color(0, 111, 238), 2));
            comboBox.setBackground(new Color(240, 240, 240));
        }
    }
}