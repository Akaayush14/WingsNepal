package wingsnepal.view;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

/**
 * Custom table cell renderer for walk-in passenger action buttons
 * Provides Edit and Delete buttons with modern styling
 * 
 * @author WingsNepal Team
 */
public class WalkInPassengerActionButtonRenderer extends JPanel implements TableCellRenderer {
    private final JButton editButton = new JButton("Edit");
    private final JButton deleteButton = new JButton("Delete");

    public WalkInPassengerActionButtonRenderer() {
        setLayout(new FlowLayout(FlowLayout.CENTER, 5, 0));
        setOpaque(true);
        setBorder(BorderFactory.createEmptyBorder(2, 5, 2, 5));

        // Style Edit Button - Green theme for walk-in passenger management (matches AdminDashboard)
        editButton.setBackground(new Color(0, 153, 112));
        editButton.setForeground(Color.WHITE);
        editButton.setFont(new Font("Segoe UI", Font.BOLD, 11));
        editButton.setFocusable(false);
        editButton.setPreferredSize(new Dimension(60, 28));
        editButton.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(0, 86, 179), 1),
            BorderFactory.createEmptyBorder(2, 6, 2, 6)
        ));

        // Style Delete Button - Red theme for danger actions
        deleteButton.setBackground(new Color(220, 53, 69));
        deleteButton.setForeground(Color.WHITE);
        deleteButton.setFont(new Font("Segoe UI", Font.BOLD, 11));
        deleteButton.setFocusable(false);
        deleteButton.setPreferredSize(new Dimension(65, 28));
        deleteButton.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(176, 42, 55), 1),
            BorderFactory.createEmptyBorder(2, 6, 2, 6)
        ));

        add(editButton);
        add(deleteButton);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, 
                                                 boolean hasFocus, int row, int column) {
        if (isSelected) {
            setBackground(table.getSelectionBackground());
        } else {
            setBackground(table.getBackground());
        }
        return this;
    }
} 