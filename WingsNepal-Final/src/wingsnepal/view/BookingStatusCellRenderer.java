package wingsnepal.view;

import javax.swing.table.DefaultTableCellRenderer;
import java.awt.Component;
import java.awt.Color;

public class BookingStatusCellRenderer extends DefaultTableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(javax.swing.JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        if (value != null) {
            String status = value.toString();
            
            // Use the database ENUM values directly, no conversion needed
            Component c = super.getTableCellRendererComponent(table, status, isSelected, hasFocus, row, column);
            
            // Set colors based on status
            if (status.equalsIgnoreCase("Confirmed")) {
                c.setForeground(new Color(0, 153, 51)); // Dark green text
                c.setBackground(new Color(220, 255, 220)); // Light green background
            } else if (status.equalsIgnoreCase("Cancelled")) {
                c.setForeground(new Color(204, 0, 0)); // Dark red text
                c.setBackground(new Color(255, 220, 220)); // Light red background
            } else {
                c.setForeground(Color.BLACK);
                c.setBackground(Color.WHITE);
            }
            
            // Keep selection highlight
            if (isSelected) {
                c.setBackground(table.getSelectionBackground());
                c.setForeground(table.getSelectionForeground());
            }
            return c;
        } else {
            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            c.setForeground(Color.BLACK);
            c.setBackground(Color.WHITE);
            if (isSelected) {
                c.setBackground(table.getSelectionBackground());
                c.setForeground(table.getSelectionForeground());
            }
            return c;
        }
    }
} 