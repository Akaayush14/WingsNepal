package wingsnepal.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.util.EventObject;
import wingsnepal.dao.UserDao;

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
        editButton.setBackground(new Color(0, 153, 112)); // Vibrant green
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
            }
        });

        // Delete action
        deleteButton.addActionListener((ActionEvent e) -> {
            int row = table.getSelectedRow();
            if (row != -1) {
                int empId = (int) table.getValueAt(row, 0);
                String empName = (String) table.getValueAt(row, 1);
                
                String confirmMessage = "Are you sure you want to delete employee '" + empName + "'?\n\n" +
                                       "This will permanently remove:\n" +
                                       "• Employee account and profile\n" +
                                       "• All flight bookings (seats will be released)\n" +
                                       "• All passenger records\n" +
                                       "• All reservation data\n\n" +
                                       "This action cannot be undone!";
                
                int confirm = JOptionPane.showConfirmDialog(panel,
                        confirmMessage,
                        "Confirm Employee Deletion", 
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE);
                        
                if (confirm == JOptionPane.YES_OPTION) {
                    boolean success = new UserDao().deleteUser(empId);
                    
                    if (success) {
                        String successMessage = "Employee '" + empName + "' and all related data deleted successfully!\n\n" +
                                              "All bookings cancelled and seats released back to inventory.";
                        JOptionPane.showMessageDialog(panel, 
                            successMessage, 
                            "Deletion Complete", 
                            JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(panel, 
                            "Failed to delete employee '" + empName + "'.\n\nThe employee might be an admin account or there was a database error.", 
                            "Deletion Failed", 
                            JOptionPane.ERROR_MESSAGE);
                    }
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
