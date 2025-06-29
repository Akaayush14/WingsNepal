package wingsnepal.view;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.EventObject;

/**
 * Custom table cell editor for walk-in passenger action buttons
 * Handles Edit and Delete button clicks for walk-in passengers
 * 
 * @author WingsNepal Team
 */
public class WalkInPassengerActionButtonEditor extends AbstractCellEditor implements TableCellEditor {
    private final JPanel panel;
    private final JButton editButton = new JButton("Edit");
    private final JButton deleteButton = new JButton("Delete");
    private final JButton changeStatusButton = new JButton("Change Status");
    private JTable table;

    public WalkInPassengerActionButtonEditor(ActionListener editAction, ActionListener deleteAction, ActionListener changeStatusAction) {
        panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
        panel.setBorder(BorderFactory.createEmptyBorder(2, 5, 2, 5));

        // Style Edit Button - Blue theme for walk-in passenger management  
        editButton.setBackground(new Color(0, 123, 255));
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

        // Style Change Status Button - Green theme
        changeStatusButton.setBackground(new Color(40, 167, 69));
        changeStatusButton.setForeground(Color.WHITE);
        changeStatusButton.setFont(new Font("Segoe UI", Font.BOLD, 11));
        changeStatusButton.setFocusable(false);
        changeStatusButton.setPreferredSize(new Dimension(120, 28));
        changeStatusButton.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(30, 120, 50), 1),
            BorderFactory.createEmptyBorder(2, 6, 2, 6)
        ));

        panel.add(editButton);
        panel.add(deleteButton);
        panel.add(changeStatusButton);

        // Edit button action
        editButton.addActionListener((ActionEvent e) -> {
            fireEditingStopped();
            if (editAction != null) {
                editAction.actionPerformed(e);
            }
        });

        // Delete button action
        deleteButton.addActionListener((ActionEvent e) -> {
            fireEditingStopped();
            if (deleteAction != null) {
                deleteAction.actionPerformed(e);
            }
        });

        // Change Status button action
        changeStatusButton.addActionListener((ActionEvent e) -> {
            fireEditingStopped();
            if (changeStatusAction != null) {
                changeStatusAction.actionPerformed(e);
            }
        });

        // Add hover effects
        addHoverEffect(editButton, new Color(0, 123, 255), new Color(0, 86, 179));
        addHoverEffect(deleteButton, new Color(220, 53, 69), new Color(176, 42, 55));
        addHoverEffect(changeStatusButton, new Color(40, 167, 69), new Color(30, 120, 50));
    }

    /**
     * Add hover effect to buttons
     */
    private void addHoverEffect(JButton button, Color normalColor, Color hoverColor) {
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(hoverColor);
                button.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(normalColor);
                button.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        this.table = table;
        if (isSelected) {
            panel.setBackground(table.getSelectionBackground());
        } else {
            panel.setBackground(table.getBackground());
        }
        return panel;
    }

    @Override
    public Object getCellEditorValue() {
        return "";
    }

    @Override
    public boolean isCellEditable(EventObject e) {
        return true;
    }

    @Override
    public boolean stopCellEditing() {
        return super.stopCellEditing();
    }
} 