package view.components;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;
import java.awt.*;

/**
 * A custom JTable component with personalized styling for rows and headers.
 *
 * <p>This table applies alternate row colors, custom header styling, and a custom cell renderer.</p>
 */
public class TableComponent extends JTable {

    /**
     * Constructs a TableComponent with the specified table model.
     *
     * <p>Sets the font, row height, and custom header styles. It also applies a custom cell renderer
     * for alternating row colors and selected row highlighting.</p>
     *
     * @param dm the data model for the table.
     */
    public TableComponent(TableModel dm) {
        super(dm);
        setFont(new Font("Consolas", Font.PLAIN, 16));
        setRowHeight(30);
        setFillsViewportHeight(true);
        setShowGrid(false);  // Removes grid lines between cells

        JTableHeader header = getTableHeader();
        header.setFont(new Font("Consolas", Font.BOLD, 12));
        header.setBackground(new Color(0, 111, 238));  // Blue header background
        header.setForeground(Color.WHITE);  // White header text
        header.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));  // Padding in header

        // Apply custom renderer for table cells
        setDefaultRenderer(Object.class, new CustomTableCellRenderer());
    }

    /**
     * Custom cell renderer for alternating row colors and custom selection highlight.
     */
    private static class CustomTableCellRenderer extends DefaultTableCellRenderer {

        /**
         * Returns a component configured to display the specified value in the table.
         *
         * <p>This method alternates row colors between light gray and white, and applies
         * a green background with white text for selected rows.</p>
         *
         * @param table the JTable we're painting.
         * @param value the value to assign to the cell at {@code [row, column]}.
         * @param isSelected true if the cell is selected.
         * @param hasFocus true if the cell has focus.
         * @param row the row of the cell to render.
         * @param column the column of the cell to render.
         * @return the component used to render the cell.
         */
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            // Apply alternate row colors when not selected
            if (!isSelected) {
                if (row % 2 == 0) {
                    cell.setBackground(new Color(240, 240, 240));  // Light gray for even rows
                } else {
                    cell.setBackground(Color.WHITE);  // White for odd rows
                }
            } else {
                cell.setBackground(new Color(23, 201, 100));  // Green when selected
                cell.setForeground(Color.WHITE);  // White text when selected
            }

            if (!isSelected) {
                cell.setForeground(Color.BLACK);  // Black text when not selected
            }

            // Apply padding to the cell
            ((JLabel) cell).setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

            return cell;
        }
    }
}
