package wingsnepal.view;

import java.awt.Component;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class CheckInButtonRenderer extends JButton implements TableCellRenderer {
    public CheckInButtonRenderer() {
        setText("Check In");
        setBackground(new java.awt.Color(0, 153, 51));
        setForeground(java.awt.Color.WHITE);
        setFocusPainted(false);
    }
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        return this;
    }
} 