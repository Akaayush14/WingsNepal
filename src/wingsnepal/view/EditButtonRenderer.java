package wingsnepal.view;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.TableCellRenderer;

public class EditButtonRenderer extends JPanel implements TableCellRenderer {

    public EditButtonRenderer() {
        setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        setOpaque(true);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {

        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
        panel.setOpaque(true);

        JButton editButton = new JButton("Edit");
        JButton deleteButton = new JButton("Delete");

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
        return panel;
    }
}

