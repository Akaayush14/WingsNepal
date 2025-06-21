/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package wingsnepal.view;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.TableCellRenderer;


aayush
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
public class ButtonRenderer extends JPanel implements TableCellRenderer {

    private final JButton bookButton = new JButton("Book");
    private final JButton editButton = new JButton("Edit");
    private final JButton deleteButton = new JButton("Delete");

    public ButtonRenderer() {
        setLayout(new FlowLayout(FlowLayout.CENTER, 5, 0));

        bookButton.setBackground(new Color(0, 102, 153));
        bookButton.setForeground(Color.WHITE);
        bookButton.setFocusPainted(false);

        editButton.setBackground(new Color(0, 204, 102));
        editButton.setForeground(Color.WHITE);
        editButton.setFocusPainted(false);

        deleteButton.setBackground(new Color(255, 0, 0));
        deleteButton.setForeground(Color.WHITE);
        deleteButton.setFocusPainted(false);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
        removeAll(); // Clear previous components

        if (value != null && value.toString().equalsIgnoreCase("Book")) {
            add(bookButton);
        } else {
            add(editButton);
            add(deleteButton);
        }

        return this;
    }
}








import java.awt.*;
import javax.swing.*;
import javax.swing.table.TableCellRenderer;

public class ButtonRenderer extends JPanel implements TableCellRenderer {
    public ButtonRenderer() {
        setLayout(new FlowLayout(FlowLayout.CENTER, 5, 0)); // Set the layout for buttons

        // Initialize buttons
        JButton editButton = new JButton("Edit");
        JButton deleteButton = new JButton("Delete");

        // Style buttons
        editButton.setBackground(new Color(0, 123, 255));  // Blue for Edit
        deleteButton.setBackground(new Color(220, 53, 69));  // Red for Delete
        editButton.setForeground(Color.WHITE);
        deleteButton.setForeground(Color.WHITE);

        // Set the preferred size for both buttons
        editButton.setPreferredSize(new Dimension(70, 25));
        deleteButton.setPreferredSize(new Dimension(70, 25));

        // Ensure buttons are not focusable and margin for spacing
        editButton.setFocusable(false);
        deleteButton.setFocusable(false);
        editButton.setMargin(new Insets(2, 8, 2, 8));
        deleteButton.setMargin(new Insets(2, 8, 2, 8));

        // Add buttons to the panel
        removeAll();  // Remove previous components if any
        add(editButton);
        add(deleteButton);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
                                                   boolean isSelected, boolean hasFocus, int row, int column) {
        return this;  // Return the panel containing buttons
    }
}
main
