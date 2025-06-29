package wingsnepal.view;

import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.JTable;
import java.awt.Component;
import java.awt.Color;

public class StatusButtonRenderer extends DefaultTableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        if (value != null) {
            String status = value.toString();
            if (status.equalsIgnoreCase("Active")) {
                c.setForeground(new Color(0, 153, 51)); // Dark green text
                c.setBackground(new Color(220, 255, 220)); // Light green background
            } else if (status.equalsIgnoreCase("Inactive")) {
                c.setForeground(new Color(200, 0, 0)); // Dark red text
                c.setBackground(new Color(255, 220, 220)); // Light red background
            } else {
                c.setForeground(Color.BLACK);
                c.setBackground(Color.WHITE);
            }
        }
        if (isSelected) {
            c.setBackground(table.getSelectionBackground());
            c.setForeground(table.getSelectionForeground());
        }
        return c;
    }
} 