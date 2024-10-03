package view.components;

import javax.swing.*;
import java.awt.*;

public class ListComponent<E> extends JList<E> {

    public ListComponent(DefaultListModel<E> listModel) {
        super(listModel);
        setFont(new Font("Consolas", Font.PLAIN, 14));
        setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        setFixedCellHeight(35);
        setCellRenderer(new CustomListCellRenderer());
    }

    private static class CustomListCellRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

            if (!isSelected) {
                if (index % 2 == 0) {
                    label.setBackground(new Color(240, 240, 240));
                } else {
                    label.setBackground(Color.WHITE);
                }
            } else {
                label.setBackground(new Color(0, 111, 238));
                label.setForeground(Color.WHITE);
            }

            if (!isSelected) {
                label.setForeground(Color.BLACK);
            }

            label.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

            return label;
        }
    }
}
