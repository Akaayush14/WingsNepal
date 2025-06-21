/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package wingsnepal.view;

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

public class ButtonEditor extends AbstractCellEditor implements TableCellEditor {

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
            }
        });
    }

    @Override
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
    }

    @Override
    public Object getCellEditorValue() {
        if (isPushed) {
            int row = table.getSelectedRow();
            // Call the appropriate method in the controller based on button label (action)
            controller.handleBookingActions(row, label);  // Action could be Book, Edit, Delete
        }
        isPushed = false;  // Reset flag after action
        return label; // Return the action label
    }
}


