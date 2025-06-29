package wingsnepal.view;

import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.JTable;
import java.awt.*;

public class EmployeeStatusCellRenderer extends DefaultTableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        if (value != null && value.toString().equalsIgnoreCase("Active")) {
            c.setForeground(new Color(0, 153, 51)); // Dark green text
            c.setBackground(new Color(220, 255, 220)); // Light green background
        } else if (value != null && value.toString().equalsIgnoreCase("Inactive")) {
            c.setForeground(Color.WHITE);
            c.setBackground(new Color(220, 53, 69)); // Red background
        } else {
            c.setForeground(Color.BLACK);
            c.setBackground(Color.WHITE);
        }
        if (isSelected) {
            c.setBackground(table.getSelectionBackground());
            c.setForeground(table.getSelectionForeground());
        }
        return c;
    }
} 