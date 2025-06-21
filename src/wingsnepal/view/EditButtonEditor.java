/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package wingsnepal.view;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.TableCellEditor;
import wingsnepal.controller.ManageBookingController;;

/**
 *
 * @author Aayush Kharel
 */


public class EditButtonEditor extends AbstractCellEditor implements TableCellEditor {
    private final JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
    private final JButton editButton = new JButton("Edit");
    private final JButton deleteButton = new JButton("Delete");

    private final UserPortal userPortal;
    private final JTable table;
    private final ManageBookingController controller;

    public EditButtonEditor(UserPortal userPortal, JTable table) {
        this.userPortal = userPortal;
        this.table = table;
        this.controller = new ManageBookingController(userPortal);

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

