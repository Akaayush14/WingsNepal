package wingsnepal.view;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.TableCellEditor;
import wingsnepal.controller.ManageBookingController;

public class EditButtonEditor extends AbstractCellEditor implements TableCellEditor {
    private JPanel panel;
    private JButton editButton;
    private JButton deleteButton;

    private final JTable table;
    private final UserPortal userPortal;
    private final ManageBookingController controller;

    public EditButtonEditor(UserPortal userPortal, JTable table) {
        this.userPortal = userPortal;
        this.table = table;
        this.controller = new ManageBookingController(userPortal);

        panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
        editButton = new JButton("Edit");
        deleteButton = new JButton("Delete");

        editButton.setPreferredSize(new Dimension(70, 20));
        deleteButton.setPreferredSize(new Dimension(75, 20));

        editButton.setBackground(new Color(0, 204, 102));
        editButton.setForeground(Color.WHITE);
        editButton.setFocusPainted(false);

        deleteButton.setBackground(new Color(255, 0, 0));
        deleteButton.setForeground(Color.WHITE);
        deleteButton.setFocusPainted(false);

        panel.add(editButton);
        panel.add(deleteButton);

        editButton.addActionListener(e -> {
            int row = table.getSelectedRow();
            controller.handleBookingActions(row, "Edit");
            fireEditingStopped();
        });

        deleteButton.addActionListener(e -> {
            int row = table.getSelectedRow();
            controller.handleBookingActions(row, "Delete");
            fireEditingStopped();
        });
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value,
            boolean isSelected, int row, int column) {
        return panel;
    }

    @Override
    public Object getCellEditorValue() {
        return "";
    }
}
