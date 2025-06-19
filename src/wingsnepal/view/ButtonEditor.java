package wingsnepal.view;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.event.*;
import java.util.EventObject;
import wingsnepal.dao.EmployeeDao;

public class ButtonEditor extends AbstractCellEditor implements TableCellEditor {
    private final JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
    private final JButton editButton = new JButton("Edit");
    private final JButton deleteButton = new JButton("Delete");

    private JTable table;
    private AdminDashboard dashboard;

    public ButtonEditor(JCheckBox checkBox, AdminDashboard dashboard) {
        this.dashboard = dashboard;

        // Style buttons with different background colors
        editButton.setBackground(new Color(0, 123, 255));  // Blue for Edit
        deleteButton.setBackground(new Color(220, 53, 69));  // Red for Delete
        editButton.setForeground(Color.WHITE);
        deleteButton.setForeground(Color.WHITE);
        editButton.setFocusable(false);
        deleteButton.setFocusable(false);
        editButton.setMargin(new Insets(2, 8, 2, 8));
        deleteButton.setMargin(new Insets(2, 8, 2, 8));

        // Add buttons to the panel
        panel.add(editButton);
        panel.add(deleteButton);

        // Edit button action
        editButton.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row != -1) {
                int empId = (int) table.getValueAt(row, 0);
                new EditEmployeeDialog(dashboard, empId).setVisible(true);
                dashboard.loadEmployeeTable();  // Refresh the employee table
            }
        });

        // Delete button action
        deleteButton.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row != -1) {
                int empId = (int) table.getValueAt(row, 0);
                int confirm = JOptionPane.showConfirmDialog(panel, "Are you sure you want to delete Employee ID: " + empId + "?",
                        "Delete Confirmation", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    new EmployeeDao().deleteEmployee(empId);
                    dashboard.loadEmployeeTable();  // Refresh the employee table
                }
            }
        });
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value,
                                                boolean isSelected, int row, int column) {
        this.table = table;
        return panel;  // Return the panel with buttons
    }

    @Override
    public Object getCellEditorValue() {
        return "";
    }

    @Override
    public boolean isCellEditable(EventObject e) {
        return true;  // Ensure the cell is editable
    }
}
