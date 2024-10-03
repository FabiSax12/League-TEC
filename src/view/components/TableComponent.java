package view.components;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;
import java.awt.*;

public class TableComponent extends JTable {

    public TableComponent(TableModel dm) {
        super(dm);
        setFont(new Font("Consolas", Font.PLAIN, 16));
        setRowHeight(30);
        setFillsViewportHeight(true);
        setShowGrid(false);

        JTableHeader header = getTableHeader();
        header.setFont(new Font("Consolas", Font.BOLD, 12));
        header.setBackground(new Color(0, 111, 238));
        header.setForeground(Color.WHITE);
        header.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        setDefaultRenderer(Object.class, new CustomTableCellRenderer());
    }

    private static class CustomTableCellRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            if (!isSelected) {
                if (row % 2 == 0) {
                    cell.setBackground(new Color(240, 240, 240));
                } else {
                    cell.setBackground(Color.WHITE);
                }
            } else {
                cell.setBackground(new Color(23, 201, 100));
                cell.setForeground(Color.WHITE);
            }

            if (!isSelected) {
                cell.setForeground(Color.BLACK);
            }

            ((JLabel) cell).setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
            return cell;
        }
    }
}
