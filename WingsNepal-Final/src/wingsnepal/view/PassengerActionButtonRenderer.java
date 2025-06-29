package wingsnepal.view;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class PassengerActionButtonRenderer extends JPanel implements TableCellRenderer {
    private final JButton editButton = new JButton("Edit");
    private final JButton deleteButton = new JButton("Delete");

    public PassengerActionButtonRenderer() {
        setLayout(new FlowLayout(FlowLayout.CENTER, 5, 0));
        setOpaque(true);
        setBorder(BorderFactory.createEmptyBorder(2, 5, 2, 5));

        editButton.setBackground(new Color(0, 153, 112));
        editButton.setForeground(Color.WHITE);
        editButton.setFont(new Font("Segoe UI", Font.BOLD, 12));
        editButton.setFocusable(false);

        deleteButton.setBackground(new Color(220, 53, 69));
        deleteButton.setForeground(Color.WHITE);
        deleteButton.setFont(new Font("Segoe UI", Font.BOLD, 12));
        deleteButton.setFocusable(false);

        add(editButton);
        add(deleteButton);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        if (isSelected) {
            setBackground(table.getSelectionBackground());
        } else {
            setBackground(table.getBackground());
        }
        return this;
    }
} 