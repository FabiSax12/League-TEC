package view.components;

import javax.swing.*;
import java.awt.*;

public class ComboBoxComponent<E> extends JComboBox<E> {

    public ComboBoxComponent(E[] items) {
        super(items);
        setUI(new CustomComboBoxUI());
        setFont(new Font("Consolas", Font.PLAIN, 16));
        setBackground(new Color(240, 240, 240));
        setForeground(Color.BLACK);
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setRenderer(new CustomComboBoxRenderer());
    }

    private class CustomComboBoxRenderer extends DefaultListCellRenderer {
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

    private class CustomComboBoxUI extends javax.swing.plaf.basic.BasicComboBoxUI {
        @Override
        public void installUI(JComponent c) {
            super.installUI(c);
            JComboBox comboBox = (JComboBox) c;
            comboBox.setBorder(BorderFactory.createLineBorder(new Color(0, 111, 238), 2));
            comboBox.setBackground(new Color(240, 240, 240));
        }
    }
}
