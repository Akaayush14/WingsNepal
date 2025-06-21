package wingsnepal.view;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.*;
import java.util.EventObject;

public class AdminButtonEditor extends AbstractCellEditor implements TableCellEditor {
    private final JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
    private final JButton editButton = new JButton("Edit");
    private final JButton deleteButton = new JButton("Delete");
    private JTable table;
    private AdminDashboard dashboard;

    public AdminButtonEditor(JCheckBox checkBox, AdminDashboard dashboard) {
        this.dashboard = dashboard;
        editButton.setBackground(new Color(0, 123, 255));
        editButton.setForeground(Color.WHITE);
        deleteButton.setBackground(new Color(220, 53, 69));
        deleteButton.setForeground(Color.WHITE);
        editButton.setFocusable(false);
        deleteButton.setFocusable(false);

        panel.add(editButton);
        panel.add(deleteButton);

        editButton.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row != -1) {
                int empId = (int) table.getValueAt(row, 0);
                new EditEmployeeDialog(dashboard, empId).setVisible(true);
                dashboard.loadEmployeeTable();
            }
        });

        deleteButton.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row != -1) {
                int empId = (int) table.getValueAt(row, 0);
                int confirm = JOptionPane.showConfirmDialog(panel, "Are you sure you want to delete Employee ID: " + empId + "?",
                        "Delete Confirmation", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    new wingsnepal.dao.EmployeeDao().deleteEmployee(empId);
                    dashboard.loadEmployeeTable();
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
