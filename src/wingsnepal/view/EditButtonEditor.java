package wingsnepal.view;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import wingsnepalController.ManageBookingController;

public class EditButtonEditor extends AbstractCellEditor implements TableCellEditor {
    private final JButton editButton;
    private final JButton deleteButton;
    private final JTable table;
    private final UserPortal userPortal;
    private final ManageBookingController controller;

    public EditButtonEditor(UserPortal userPortal, JTable table) {
        this.userPortal = userPortal;
        this.table = table;
        this.controller = new ManageBookingController(userPortal);

        // Create buttons for "Edit" and "Delete"
        editButton = new JButton("Edit");
        deleteButton = new JButton("Delete");

        // Add action listeners for buttons
        editButton.addActionListener(e -> handleEditAction());
        deleteButton.addActionListener(e -> handleDeleteAction());
    }

    private void handleEditAction() {
        int row = table.getSelectedRow();
        if (row >= 0) {
            int bookingId = (int) table.getValueAt(row, 0); // Get booking ID (Column 0 should be the booking ID)
            controller.handleBookingActions(row, "Edit");
        }
        fireEditingStopped();  // Stop editing after action
    }

    private void handleDeleteAction() {
        int row = table.getSelectedRow();
        if (row >= 0) {
            int bookingId = (int) table.getValueAt(row, 0); // Get booking ID (Column 0 should be the booking ID)
            boolean isDeleted = controller.handleBookingActions(row, "Delete");
            if (isDeleted) {
                JOptionPane.showMessageDialog(userPortal, "Booking deleted successfully.");
            } else {
                JOptionPane.showMessageDialog(userPortal, "Failed to delete booking.");
            }
        }
        fireEditingStopped();  // Stop editing after action
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
        panel.setOpaque(true);

        // Add buttons to the panel
        panel.add(editButton);
        panel.add(deleteButton);

        return panel;
    }

    @Override
    public Object getCellEditorValue() {
        return null;  // No value to return since the buttons perform actions
    }
}

