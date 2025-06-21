/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package wingsnepal.view;

aayush
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import wingsnepal.controller.ManageBookingController;
/**
 *
 * @author Aayush Kharel
 */

import java.awt.*;
import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.*;
import java.util.EventObject;

public class AdminButtonEditor extends AbstractCellEditor implements TableCellEditor {
    private final JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
    private final JButton editButton = new JButton("Edit");
    private final JButton deleteButton = new JButton("Delete");
import wingsnepal.dao.EmployeeDao;
main

public class ButtonEditor extends AbstractCellEditor implements TableCellEditor {

    private JTable table;
    private AdminDashboard dashboard;

    public AdminButtonEditor(JCheckBox checkBox, AdminDashboard dashboard) {
        this.dashboard = dashboard;
        editButton.setBackground(new Color(0, 123, 255));
aayush
    private final JButton button;
    private String label;
    private boolean isPushed;
    private ManageBookingController controller;
    private JTable table;

    // Constructor for ButtonEditor
    public ButtonEditor(ManageBookingController controller, JTable table) {
        super();  // Use the default constructor of AbstractCellEditor
        this.controller = controller;
        this.table = table;
        button = new JButton();
        button.setOpaque(true);

        // Add action listener to handle button clicks (Edit/Delete)
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Fire editing stopped when button is clicked
                isPushed = true;
                fireEditingStopped(); // Stop editing, triggering the getCellEditorValue method

        // Style buttons with different background colors
        editButton.setBackground(new Color(0, 123, 255));  // Blue for Edit
        deleteButton.setBackground(new Color(220, 53, 69));  // Red for Delete
        editButton.setForeground(Color.WHITE);
        deleteButton.setBackground(new Color(220, 53, 69));
        deleteButton.setForeground(Color.WHITE);
        editButton.setFocusable(false);
        deleteButton.setFocusable(false);

        panel.add(editButton);
        panel.add(deleteButton);

        editButton.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row != -1) {
                int empId = (int) table.getValueAt(row, 0);
                new EditEmployeeDialog(dashboard, empId).setVisible(true);
                dashboard.loadEmployeeTable();
            }
        });

        deleteButton.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row != -1) {
                int empId = (int) table.getValueAt(row, 0);
                int confirm = JOptionPane.showConfirmDialog(panel, "Are you sure you want to delete Employee ID: " + empId + "?",
                        "Delete Confirmation", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    new wingsnepal.dao.EmployeeDao().deleteEmployee(empId);
                    dashboard.loadEmployeeTable();
                }
main
            }
        });
    }

    @Override
    HEAD:src/wingsnepal/view/AdminButtonEditor.java
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        this.table = table;
        return panel;

    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        // Set label based on action (Book, Edit, Delete)
        label = (value == null) ? "Action" : value.toString();
        button.setText(label);  // Set button text

        // Apply button styling for each action (Book, Edit, Delete)
        if (label.equals("Book")) {
            button.setBackground(new java.awt.Color(0, 102, 153));  // Blue color for Book
        } else if (label.equals("Edit")) {
            button.setBackground(new java.awt.Color(0, 204, 102));  // Green color for Edit
        } else if (label.equals("Delete")) {
            button.setBackground(new java.awt.Color(255, 0, 0));     // Red color for Delete
        }

        // Optionally: Add hover effects or reset styles (if needed)
        // button.setBorder(new LineBorder(Color.BLACK)); // for visual feedback

        return button;

    public Component getTableCellEditorComponent(JTable table, Object value,
                                                boolean isSelected, int row, int column) {
        this.table = table;
        return panel;  // Return the panel with buttons
main
    41f5b675fc0d94f39739f274ebdb8a0e60037bb2:src/wingsnepal/view/ButtonEditor.java
    }

    @Override
    public Object getCellEditorValue() {
aayush
        if (isPushed) {
            int row = table.getSelectedRow();
            // Call the appropriate method in the controller based on button label (action)
            controller.handleBookingActions(row, label);  // Action could be Book, Edit, Delete
        }
        isPushed = false;  // Reset flag after action
        return label; // Return the action label

        return "";
main
    }

aayush


    @Override
    public boolean isCellEditable(EventObject e) {
        return true;
    }
}


main
