package wingsnepal.view;

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
