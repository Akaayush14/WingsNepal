package wingsnepal.view;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class AdminButtonRenderer extends JPanel implements TableCellRenderer {
    private final JButton editButton = new JButton("Edit");
    private final JButton deleteButton = new JButton("Delete");

    public AdminButtonRenderer() {
        super(new FlowLayout(FlowLayout.CENTER, 5, 5));
        
        // Style Edit Button
        editButton.setBackground(new Color(0, 153, 112));
        editButton.setForeground(Color.WHITE);
        editButton.setFocusPainted(false);
        editButton.setFont(new Font("Segoe UI", Font.BOLD, 12));

        // Style Delete Button
        deleteButton.setBackground(new Color(220, 53, 69));
        deleteButton.setForeground(Color.WHITE);
        deleteButton.setFocusPainted(false);
        deleteButton.setFont(new Font("Segoe UI", Font.BOLD, 12));

        add(editButton);
        add(deleteButton);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
                                                   boolean isSelected, boolean hasFocus, int row, int column) {
        // This ensures the panel background matches the table's selection color
        if (isSelected) {
            setBackground(table.getSelectionBackground());
        } else {
            setBackground(UIManager.getColor("Button.background"));
        }
        return this;
    }
}
