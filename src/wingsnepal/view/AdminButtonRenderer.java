package wingsnepal.view;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class AdminButtonRenderer extends JPanel implements TableCellRenderer {
    private final JButton editButton = new JButton("Edit");
    private final JButton deleteButton = new JButton("Delete");

    public AdminButtonRenderer() {
        // Align to top more by adjusting layout spacing
        setLayout(new FlowLayout(FlowLayout.CENTER, 5, -4)); // -4 top padding

        // Set colors
        editButton.setBackground(new Color(0, 123, 255));
        deleteButton.setBackground(new Color(220, 53, 69));
        editButton.setForeground(Color.WHITE);
        deleteButton.setForeground(Color.WHITE);

        // Simulate semi-bold and raise text
        Font semiBoldFont = new Font("Segoe UI Semibold", Font.PLAIN, 11);
        editButton.setFont(semiBoldFont);
        deleteButton.setFont(semiBoldFont);

        // Lift text inside buttons
        editButton.setVerticalAlignment(SwingConstants.TOP);
        deleteButton.setVerticalAlignment(SwingConstants.TOP);

        // Minimal padding to tighten button appearance
        editButton.setMargin(new Insets(0, 8, 0, 8));
        deleteButton.setMargin(new Insets(0, 8, 0, 8));

        // Border and focus tweaks
        editButton.setBorder(BorderFactory.createEmptyBorder(2, 8, 2, 8));
        deleteButton.setBorder(BorderFactory.createEmptyBorder(2, 8, 2, 8));
        editButton.setFocusable(false);
        deleteButton.setFocusable(false);

        add(editButton);
        add(deleteButton);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
                                                   boolean isSelected, boolean hasFocus, int row, int column) {
        return this;
    }
}
