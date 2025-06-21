package wingsnepal.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.util.EventObject;
import wingsnepal.dao.EmployeeDao;

/**
 * Used in the Admin Dashboard JTable for Edit and Delete employee actions.
 */
public class AdminButtonEditor extends AbstractCellEditor implements TableCellEditor {
    private final JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
    private final JButton editButton = new JButton("Edit");
    private final JButton deleteButton = new JButton("Delete");
    private JTable table;
    private AdminDashboard dashboard;

    public AdminButtonEditor(JCheckBox checkBox, AdminDashboard dashboard) {
        this.dashboard = dashboard;

        // Style the buttons
        editButton.setBackground(new Color(0, 123, 255));   // Bootstrap Blue
        editButton.setForeground(Color.WHITE);
        editButton.setFocusable(false);

        deleteButton.setBackground(new Color(220, 53, 69)); // Bootstrap Red
        deleteButton.setForeground(Color.WHITE);
        deleteButton.setFocusable(false);

        panel.add(editButton);
        panel.add(deleteButton);

        // Edit action
        editButton.addActionListener((ActionEvent e) -> {
            int row = table.getSelectedRow();
            if (row != -1) {
                int empId = (int) table.getValueAt(row, 0);
                new EditEmployeeDialog(dashboard, empId).setVisible(true);
                dashboard.loadEmployeeTable();  // Refresh table
            }
        });

        // Delete action
        deleteButton.addActionListener((ActionEvent e) -> {
            int row = table.getSelectedRow();
            if (row != -1) {
                int empId = (int) table.getValueAt(row, 0);
                int confirm = JOptionPane.showConfirmDialog(panel,
                        "Are you sure you want to delete Employee ID: " + empId + "?",
                        "Delete Confirmation", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    new EmployeeDao().deleteEmployee(empId);
                    dashboard.loadEmployeeTable();  // Refresh table
                }
            }
        });
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        this.table = table;
        return panel;
    }

    @Override
    public Object getCellEditorValue() {
        return "";
    }

    @Override
    public boolean isCellEditable(EventObject e) {
        return true;
    }
}
