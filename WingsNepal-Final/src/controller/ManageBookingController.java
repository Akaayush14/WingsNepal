/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import wingsnepal.dao.ManageBookingDao;
import wingsnepal.model.ManageBookingModel;
import wingsnepal.view.UserPortal;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List; // Don't forget to import List for collections
import wingsnepal.view.EditBookDialog;


/**
 *
 * @author Aayush Kharel
 */

public class ManageBookingController {
    private final UserPortal view;
    private final ManageBookingDao manageBookingDao;

    public ManageBookingController(UserPortal view) {
        this.view = view;
        this.manageBookingDao = new ManageBookingDao();
    }

    // Handle Edit/Delete action
    public void handleBookingActions(int rowIndex, String action) {
        DefaultTableModel model = (DefaultTableModel) view.getJTable2().getModel();
        int bookingId = (int) model.getValueAt(rowIndex, 0);  // Get booking ID

        // Get the userId of the logged-in user
        int userId = view.getLoggedInUser().getUserId();

        if ("Delete".equals(action)) {
            int confirmation = JOptionPane.showConfirmDialog(view, "Are you sure you want to delete this booking?", "Delete Booking", JOptionPane.YES_NO_OPTION);
            if (confirmation == JOptionPane.YES_OPTION) {
                boolean success = manageBookingDao.deleteBooking(bookingId);
                if (success) {
                    JOptionPane.showMessageDialog(view, "Booking deleted successfully!");
                    
                    // Smart refresh: Only refresh if table already has data (Show Booking was clicked)
                    javax.swing.table.DefaultTableModel tableModel = (javax.swing.table.DefaultTableModel) view.getJTable2().getModel();
                    if (tableModel.getRowCount() > 0) {
                        // Table has data - user has clicked Show Booking before, so refresh it
                        int currentTab = view.getMainTabbedPane().getSelectedIndex();
                        if (currentTab == 3) { // Edit Flight tab
                            showAllBookingsForEdit();
                        } else if (currentTab == 4) { // Manage Booking tab  
                            showBookings(userId);
                        }
                    }
                    
                    // Refresh seats after deletion
                    String flightCode = view.getFlightCodeTextField().getText().trim();
                    String seatClass = (String) view.getSeatComboBox().getSelectedItem();
                    if (!flightCode.isEmpty() && seatClass != null) {
                        view.updateAvailableSeatsDirectly(flightCode, seatClass);
                    }
                } else {
                    JOptionPane.showMessageDialog(view, "Failed to delete booking.");
                }
            }
        } else if ("Edit".equals(action)) {
            // Create a smart refresh callback that only refreshes if table has data
            Runnable refreshCallback = () -> {
                // Smart refresh: Only refresh if table already has data (Show Booking was clicked)
                javax.swing.table.DefaultTableModel tableModel = (javax.swing.table.DefaultTableModel) view.getJTable2().getModel();
                if (tableModel.getRowCount() > 0) {
                    // Table has data - user has clicked Show Booking before, so refresh it
                    int currentTab = view.getMainTabbedPane().getSelectedIndex();
                    if (currentTab == 3) { // Edit Flight tab
                        showAllBookingsForEdit();
                    } else if (currentTab == 4) { // Manage Booking tab  
                        showBookings(userId);
                    }
                }
                
                // Also refresh seats after any booking change
                String flightCode = view.getFlightCodeTextField().getText().trim();
                String seatClass = (String) view.getSeatComboBox().getSelectedItem();
                if (!flightCode.isEmpty() && seatClass != null) {
                    view.updateAvailableSeatsDirectly(flightCode, seatClass);
                }
            };
            
            EditBookDialog editDialog = new EditBookDialog(view, bookingId, refreshCallback);
            editDialog.setVisible(true);
        }
    }


    // Refresh booking table
    public void showBookings(int userId) {
        // Use the new method that excludes walk-in bookings made by employees
        List<ManageBookingModel> bookings = manageBookingDao.getRegularUserBookingsByUserId(userId);
        DefaultTableModel model = (DefaultTableModel) view.getJTable2().getModel();
        model.setRowCount(0);

        for (ManageBookingModel booking : bookings) {
            // Use actual booking status or default to "Confirmed" if null/empty
            String bookingStatus = booking.getBookingStatus();
            if (bookingStatus == null || bookingStatus.trim().isEmpty()) {
                bookingStatus = "Confirmed";
            }
            
            int durationMin = booking.getDurationMinutes();
            String durationStr = (durationMin / 60) + "h " + (durationMin % 60) + "m";
            model.addRow(new Object[]{
                booking.getBookingId(),
                booking.getFlightCode(),
                booking.getSeatNo(),
                booking.getSeatClass(),
                booking.getTravelDate(),
                booking.getPaymentMethod(),
                bookingStatus,
                "Edit/Delete" // Placeholder for action buttons
            });
        }
        
        // Apply BookingStatusCellRenderer to Status column (index 6)
        view.getJTable2().getColumnModel().getColumn(6).setCellRenderer(new wingsnepal.view.BookingStatusCellRenderer());
        
        System.out.println("✅ MANAGE BOOKING: Loaded " + bookings.size() + " regular user bookings (walk-in bookings excluded)");
    }

    // Show all bookings for admin/edit flight table (exclude walk-in customer bookings)
    public void showAllBookingsForEdit() {
        List<ManageBookingModel> bookings = manageBookingDao.getRegularUserBookings();
        DefaultTableModel model = new DefaultTableModel(
            new Object[]{"Booking ID", "Flight Code", "From", "To", "Date", "Seat", "Class", "Status", "Actions"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 8; // Only Actions column is editable
            }
        };
        view.getJTable2().setModel(model);
        for (ManageBookingModel booking : bookings) {
            model.addRow(new Object[]{
                booking.getBookingId(),      // Booking ID
                booking.getFlightCode(),     // Flight Code
                booking.getFromCity(),       // From
                booking.getToCity(),         // To
                booking.getTravelDate(),     // Date
                booking.getSeatNo(),         // Seat
                booking.getSeatClass(),      // Class (ensure this is correct)
                booking.getBookingStatus(),  // Status
                "Edit/Delete"               // Actions
            });
        }
        // Enable real Edit/Delete buttons
        view.setupActionsColumn();
        
        // Apply BookingStatusCellRenderer to Status column (index 7)
        view.getJTable2().getColumnModel().getColumn(7).setCellRenderer(new wingsnepal.view.BookingStatusCellRenderer());
    }
}



