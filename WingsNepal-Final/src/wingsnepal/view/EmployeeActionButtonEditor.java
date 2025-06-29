package wingsnepal.view;

import javax.swing.*;
import java.awt.*;

public class EmployeeActionButtonEditor extends AbstractCellEditor implements javax.swing.table.TableCellEditor {
    public interface EmployeeActionCallback {
        void onEdit(int row);
        void onDelete(int row);
    }
    private final JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
    private final JButton editButton = new JButton("Edit");
    private final JButton deleteButton = new JButton("Delete");
    private int row;
    private JTable table;
    private EmployeeActionCallback callback;
    public EmployeeActionButtonEditor(JTable table, EmployeeActionCallback callback) {
        this.table = table;
        this.callback = callback;
        editButton.setBackground(new Color(0, 153, 112));
        editButton.setForeground(Color.WHITE);
        editButton.setFocusPainted(false);
        deleteButton.setBackground(new Color(220, 53, 69));
        deleteButton.setForeground(Color.WHITE);
        deleteButton.setFocusPainted(false);
        panel.add(editButton);
        panel.add(deleteButton);
        editButton.addActionListener(e -> {
            fireEditingStopped();
            if (callback != null) callback.onEdit(row);
        });
        deleteButton.addActionListener(e -> {
            fireEditingStopped();
            if (callback != null) callback.onDelete(row);
        });
    }
    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        this.row = row;
        return panel;
    }
    @Override
    public Object getCellEditorValue() {
        return null;
    }
} 