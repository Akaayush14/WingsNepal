package wingsnepal.view;

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
