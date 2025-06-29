package wingsnepal.view;

import javax.swing.*;
import java.awt.*;

public class EmployeeActionButtonRenderer extends JPanel implements javax.swing.table.TableCellRenderer {
    private final JButton editButton = new JButton("Edit");
    private final JButton deleteButton = new JButton("Delete");
    public EmployeeActionButtonRenderer() {
        setLayout(new FlowLayout(FlowLayout.CENTER, 5, 0));
        editButton.setBackground(new Color(0, 153, 112));
        editButton.setForeground(Color.WHITE);
        editButton.setFocusPainted(false);
        deleteButton.setBackground(new Color(220, 53, 69));
        deleteButton.setForeground(Color.WHITE);
        deleteButton.setFocusPainted(false);
        add(editButton);
        add(deleteButton);
        setOpaque(true);
    }
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        return this;
    }
} 