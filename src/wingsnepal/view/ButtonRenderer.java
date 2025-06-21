/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package wingsnepal.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author Aayush Kharel
 */

public class ButtonRenderer extends JButton implements TableCellRenderer {

    public ButtonRenderer() {
        setOpaque(true);
        setFont(new Font("Segoe UI Emoji", Font.BOLD, 10)); // Font style
        setCursor(new Cursor(Cursor.HAND_CURSOR)); // Hand cursor on hover
        setForeground(Color.WHITE); // Text color
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        String action = (value == null) ? "Action" : value.toString();
        setText(action);  // Update button text based on the action ("Book", "Edit", "Delete")

        // Apply button styling for each action (Book, Edit, Delete)
        if (action.equals("Book")) {
            setBackground(new Color(0, 102, 153));  // Blue color for Book
        } else if (action.equals("Edit")) {
            setBackground(new Color(0, 204, 102));  // Green color for Edit
        } else if (action.equals("Delete")) {
            setBackground(new Color(255, 0, 0));     // Red color for Delete
        }

        // Handle selection and focus for better UI feedback
        if (isSelected) {
            setBackground(getBackground().darker());
        } else if (hasFocus) {
            setBorder(BorderFactory.createLineBorder(Color.YELLOW, 2));  // Highlight with border when focused
        } else {
            setBorder(null);  // Reset border when not focused
        }

        return this;
    }
}







