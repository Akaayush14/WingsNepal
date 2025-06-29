package wingsnepal.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.util.EventObject;

public class EmployeeFlightButtonEditor extends AbstractCellEditor implements TableCellEditor {
    private final JPanel panel;
    private final JButton editButton = new JButton("Edit");
    private final JButton viewButton = new JButton("View");
    private JTable table;

    public EmployeeFlightButtonEditor(JCheckBox checkBox) {
        panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 3, 0));
        panel.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));

        // Style Edit Button - Blue for employee (less permission than admin green)
        editButton.setBackground(new Color(0, 153, 112));
        editButton.setForeground(Color.WHITE);
        editButton.setFont(new Font("Segoe UI", Font.BOLD, 10));
        editButton.setFocusable(false);
        editButton.setPreferredSize(new Dimension(58, 26));

        // Style View Button - Gray for read-only
        viewButton.setBackground(new Color(108, 117, 125));
        viewButton.setForeground(Color.WHITE);
        viewButton.setFont(new Font("Segoe UI", Font.BOLD, 10));
        viewButton.setFocusable(false);
        viewButton.setPreferredSize(new Dimension(58, 26));

        panel.add(editButton);
        panel.add(viewButton);

        // Edit button - VERY LIMITED employee edit functionality
        editButton.addActionListener((ActionEvent e) -> {
            fireEditingStopped();
            int row = table.convertRowIndexToModel(table.getSelectedRow());
            if (row != -1) {
                String flightCode = (String) table.getModel().getValueAt(row, 1);
                String flightName = (String) table.getModel().getValueAt(row, 2);
                String currentDate = (String) table.getModel().getValueAt(row, 5);
                String currentTime = (String) table.getModel().getValueAt(row, 6);
                String currentDuration = (String) table.getModel().getValueAt(row, 8);
                
                // Open the professional Employee Edit Flight Dialog with callback
                try {
                    JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(panel);
                    
                    // Create callback to refresh the system after updates
                    EmployeeEditFlightDialog.FlightUpdateCallback callback = new EmployeeEditFlightDialog.FlightUpdateCallback() {
                        @Override
                        public void onFlightUpdated(String updatedFlightCode) {
                            // Refresh the flight table to show updated data
                            SwingUtilities.invokeLater(() -> {
                                try {
                                    // Get the Employee Dashboard controller and refresh the flight table
                                    if (parentFrame instanceof wingsnepal.view.EmployeeDashboard) {
                                        wingsnepal.view.EmployeeDashboard dashboard = (wingsnepal.view.EmployeeDashboard) parentFrame;
                                        
                                        // Use reflection to get the controller and refresh the system
                                        java.lang.reflect.Field controllerField = dashboard.getClass().getDeclaredField("controller");
                                        controllerField.setAccessible(true);
                                        controller.EmployeeDashboardController controller = 
                                            (controller.EmployeeDashboardController) controllerField.get(dashboard);
                                        
                                        if (controller != null) {
                                            controller.refreshFlightData(); // Full system refresh after flight update
                                            System.out.println("✅ System-wide flight data refreshed after employee update: " + updatedFlightCode);
                                        }
                                    }
                                } catch (Exception ex) {
                                    System.err.println("⚠️ Unable to refresh flight table after update: " + ex.getMessage());
                                    // Still show success message even if refresh fails
                                    JOptionPane.showMessageDialog(panel,
                                        "Flight updated successfully!\n" +
                                        "Please manually refresh the view if needed.",
                                        "Update Complete",
                                        JOptionPane.INFORMATION_MESSAGE);
                                }
                            });
                        }
                    };
                    
                    EmployeeEditFlightDialog editDialog = new EmployeeEditFlightDialog(parentFrame, flightCode, callback);
                    editDialog.setVisible(true);
                } catch (Exception ex) {
                    // Fallback message if dialog fails to open
                    JOptionPane.showMessageDialog(panel, 
                        "Unable to open edit dialog: " + ex.getMessage() + "\n\n" +
                        "Flight: " + flightCode + " (" + flightName + ")\n" +
                        "Employee can edit: Date, Time, Duration, Pricing",
                        "Edit Dialog Error", 
                        JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // View button - Read-only flight details
        viewButton.addActionListener((ActionEvent e) -> {
            fireEditingStopped();
            int row = table.convertRowIndexToModel(table.getSelectedRow());
            if (row != -1) {
                String flightCode = (String) table.getModel().getValueAt(row, 1);
                String flightName = (String) table.getModel().getValueAt(row, 2);
                String origin = (String) table.getModel().getValueAt(row, 3);
                String destination = (String) table.getModel().getValueAt(row, 4);
                String date = (String) table.getModel().getValueAt(row, 5);
                String time = (String) table.getModel().getValueAt(row, 6);
                Object price = table.getModel().getValueAt(row, 7);
                String duration = (String) table.getModel().getValueAt(row, 8);
                
                // Show comprehensive flight details in read-only mode
                JOptionPane.showMessageDialog(panel,
                    "FLIGHT DETAILS\n\n" +
                    "Flight Code: " + flightCode + "\n" +
                    "Flight Name: " + flightName + "\n" +
                    "Route: " + origin + " → " + destination + "\n" +
                    "Date: " + date + "\n" +
                    "Time: " + time + "\n" +
                    "Price: Rs. " + price + "\n" +
                    "Duration: " + duration + "\n\n" +
                    "Use 'Edit' button to make authorized changes.",
                    "Flight Information - " + flightCode,
                    JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        this.table = table;
        panel.setBackground(table.getSelectionBackground());
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

    @Override
    public boolean stopCellEditing() {
        return super.stopCellEditing();
    }
} 