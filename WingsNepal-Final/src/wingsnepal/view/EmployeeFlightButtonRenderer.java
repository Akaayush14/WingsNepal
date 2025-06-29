package wingsnepal.view;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class EmployeeFlightButtonRenderer extends JPanel implements TableCellRenderer {
    private final JButton editButton = new JButton("Edit");
    private final JButton viewButton = new JButton("View");

    public EmployeeFlightButtonRenderer() {
        super(new FlowLayout(FlowLayout.CENTER, 3, 0));
        setOpaque(true);
        setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));

        // Edit button - more restricted styling for employees
        editButton.setBackground(new Color(0, 153, 112)); // Blue instead of green
        editButton.setForeground(Color.WHITE);
        editButton.setFont(new Font("Segoe UI", Font.BOLD, 10)); // Slightly smaller font
        editButton.setFocusable(false);
        editButton.setPreferredSize(new Dimension(58, 26)); // Increased width for full text

        // View button instead of Delete (employees can't delete flights)
        viewButton.setBackground(new Color(108, 117, 125)); // Gray
        viewButton.setForeground(Color.WHITE);
        viewButton.setFont(new Font("Segoe UI", Font.BOLD, 10)); // Slightly smaller font
        viewButton.setFocusable(false);
        viewButton.setPreferredSize(new Dimension(58, 26)); // Increased width for full text

        add(editButton);
        add(viewButton);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
                                                   boolean isSelected, boolean hasFocus, int row, int column) {
        if (isSelected) {
            setBackground(table.getSelectionBackground());
        } else {
            setBackground(table.getBackground());
        }
        return this;
    }
} 