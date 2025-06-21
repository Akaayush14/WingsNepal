/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package wingsnepal.view;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author Aayush Kharel
 */

public class EditButtonRenderer extends JPanel implements TableCellRenderer {

    private final JButton editButton = new JButton("Edit");
    private final JButton deleteButton = new JButton("Delete");

    public EditButtonRenderer() {
        setLayout(new FlowLayout(FlowLayout.CENTER, 5, 0));

        editButton.setBackground(new Color(0, 204, 102));
        editButton.setForeground(Color.WHITE);
        editButton.setFocusPainted(false);

        deleteButton.setBackground(new Color(255, 0, 0));
        deleteButton.setForeground(Color.WHITE);
        deleteButton.setFocusPainted(false);

        add(editButton);
        add(deleteButton);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
        return this;
    }
}

