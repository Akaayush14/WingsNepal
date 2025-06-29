package wingsnepal.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.util.EventObject;
import java.awt.BorderLayout;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.SwingWorker;
import javax.swing.SwingConstants;

public class FlightButtonEditor extends AbstractCellEditor implements TableCellEditor {
    
    // Callback interface for table refresh
    public interface TableRefreshCallback {
        void refreshTable();
    }
    
    private final JPanel panel;
    private final JButton editButton = new JButton("Edit");
    private final JButton deleteButton = new JButton("Delete");
    private JTable table;
    private TableRefreshCallback refreshCallback;

    public FlightButtonEditor(JCheckBox checkBox) {
        this(checkBox, null);
    }
    
    public FlightButtonEditor(JCheckBox checkBox, TableRefreshCallback callback) {
        this.refreshCallback = callback;
        
        panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
        panel.setBorder(BorderFactory.createEmptyBorder(2, 5, 2, 5));

        // Style Edit Button
        editButton.setBackground(new Color(0, 153, 112));
        editButton.setForeground(Color.WHITE);
        editButton.setFont(new Font("Segoe UI", Font.BOLD, 12));
        editButton.setFocusable(false);

        // Style Delete Button
        deleteButton.setBackground(new Color(220, 53, 69));
        deleteButton.setForeground(Color.WHITE);
        deleteButton.setFont(new Font("Segoe UI", Font.BOLD, 12));
        deleteButton.setFocusable(false);

        panel.add(editButton);
        panel.add(deleteButton);

        editButton.addActionListener((ActionEvent e) -> {
            fireEditingStopped();
            int row = table.convertRowIndexToModel(table.getSelectedRow());
            if (row != -1) {
                String flightCode = (String) table.getModel().getValueAt(row, 1);
                
                // Create dialog with callback to refresh table after update
                EditFlightDialog.FlightUpdateCallback updateCallback = (updatedFlightCode) -> {
                    System.out.println("Flight " + updatedFlightCode + " updated! Refreshing table...");
                    if (refreshCallback != null) {
                        SwingUtilities.invokeLater(() -> refreshCallback.refreshTable());
                    }
                };
                
                new EditFlightDialog((JFrame) SwingUtilities.getWindowAncestor(checkBox), flightCode, updateCallback).setVisible(true);
            }
        });

        deleteButton.addActionListener((ActionEvent e) -> {
            fireEditingStopped();
            int row = table.convertRowIndexToModel(table.getSelectedRow());
            if (row != -1) {
                String flightCode = (String) table.getModel().getValueAt(row, 1);
                int confirm = JOptionPane.showConfirmDialog(panel,
                        "Are you sure you want to delete flight " + flightCode + "?\n\n" +
                        "This will permanently delete:\n" +
                        "• All bookings for this flight\n" +
                        "• All passenger records\n" +
                        "• All seats and pricing\n" +
                        "• The flight itself\n\n" +
                        "This action cannot be undone!",
                        "Delete Flight Confirmation", 
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE);
                
                if (confirm == JOptionPane.YES_OPTION) {
                    // Show progress dialog
                    JDialog progressDialog = new JDialog((JFrame) SwingUtilities.getWindowAncestor(panel), "Deleting Flight", true);
                    progressDialog.setLayout(new BorderLayout());
                    progressDialog.setSize(300, 100);
                    progressDialog.setLocationRelativeTo(panel);
                    
                    JLabel progressLabel = new JLabel("Deleting flight " + flightCode + "...", SwingConstants.CENTER);
                    progressDialog.add(progressLabel, BorderLayout.CENTER);
                    
                    // Show progress dialog in a separate thread
                    SwingUtilities.invokeLater(() -> progressDialog.setVisible(true));
                    
                    // Perform deletion in background thread
                    new SwingWorker<Boolean, Void>() {
                        @Override
                        protected Boolean doInBackground() throws Exception {
                            wingsnepal.dao.SearchFlightDao dao = new wingsnepal.dao.SearchFlightDao();
                            return dao.deleteFlight(flightCode);
                        }
                        
                        @Override
                        protected void done() {
                            progressDialog.dispose();
                            try {
                                boolean success = get();
                                if (success) {
                                    JOptionPane.showMessageDialog(panel, 
                                        "Flight " + flightCode + " deleted successfully!", 
                                        "Success", 
                                        JOptionPane.INFORMATION_MESSAGE);
                                    
                                    // Refresh the table
                                    if (refreshCallback != null) {
                                        SwingUtilities.invokeLater(() -> refreshCallback.refreshTable());
                                    }
                                } else {
                                    JOptionPane.showMessageDialog(panel, 
                                        "Failed to delete flight " + flightCode + ". Please try again.", 
                                        "Error", 
                                        JOptionPane.ERROR_MESSAGE);
                                }
                            } catch (Exception ex) {
                                ex.printStackTrace();
                                JOptionPane.showMessageDialog(panel, 
                                    "An error occurred while deleting the flight: " + ex.getMessage(), 
                                    "Error", 
                                    JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    }.execute();
                }
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