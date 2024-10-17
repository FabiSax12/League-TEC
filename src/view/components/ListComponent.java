package view.components;

import javax.swing.*;
import java.awt.*;

/**
 * Custom JList component with a personalized cell renderer for alternate row colors and custom selection styling.
 *
 * @param <E> the type of elements in this list.
 */
public class ListComponent<E> extends JList<E> {

    /**
     * Constructs a ListComponent with the specified list model.
     *
     * <p>Applies a custom font, sets the selection mode to single selection,
     * and assigns a custom renderer to style the list cells.</p>
     *
     * @param listModel the model to use for the list.
     */
    public ListComponent(DefaultListModel<E> listModel) {
        super(listModel);
        setFont(new Font("Consolas", Font.PLAIN, 14));
        setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        setFixedCellHeight(35);
        setCellRenderer(new CustomListCellRenderer());
    }

    /**
     * Custom renderer for list cells, applying alternate background colors for rows and custom styles when selected.
     */
    private static class CustomListCellRenderer extends DefaultListCellRenderer {

        /**
         * Returns a component configured to display the specified value in the list.
         *
         * <p>This method alternates background colors for list items (gray and white)
         * and applies a blue background with white text when an item is selected.</p>
         *
         * @param list the JList we're painting.
         * @param value the value to display.
         * @param index the cell's index.
         * @param isSelected true if the specified cell is selected.
         * @param cellHasFocus true if the specified cell has focus.
         * @return the component used to render the list cell.
         */
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
