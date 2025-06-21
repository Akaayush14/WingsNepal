/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package wingsnepal.controller;

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
    private UserPortal userPortal;
    private ManageBookingDao manageBookingDao;

    public ManageBookingController(UserPortal userPortal) {
        this.userPortal = userPortal;
        this.manageBookingDao = new ManageBookingDao();
    }

    // Get user bookings
    public List<ManageBookingModel> getUserBookings(int userId) {
        return manageBookingDao.getUserBookings(userId);  // Fetch bookings from DAO
    }

    // Handle Edit/Delete action
    public void handleBookingActions(int rowIndex, String action) {
        DefaultTableModel model = (DefaultTableModel) userPortal.getJTable2().getModel();
        int bookingId = (int) model.getValueAt(rowIndex, 0);  // Get booking ID

        // Get the userId of the logged-in user
        int userId = userPortal.getLoggedInUser().getUserId();

        if ("Delete".equals(action)) {
            int confirmation = JOptionPane.showConfirmDialog(userPortal, "Are you sure you want to delete this booking?");
            if (confirmation == JOptionPane.YES_OPTION) {
                boolean success = manageBookingDao.deleteBooking(bookingId);
                if (success) {
                    JOptionPane.showMessageDialog(userPortal, "Booking deleted successfully!");
                    showBookings(userId);  // Pass the userId to refresh the booking table
                } else {
                    JOptionPane.showMessageDialog(userPortal, "Failed to delete booking.");
                }
            }
        } else if ("Edit".equals(action)) {
            EditBookDialog editDialog = new EditBookDialog(userPortal, bookingId);
            editDialog.setVisible(true);
        }
    }


    // Refresh booking table
    public void showBookings(int userId) {
        // Fetch the bookings from the DAO using the userId
        ManageBookingDao manageBookingDao = new ManageBookingDao();
        List<ManageBookingModel> bookings = manageBookingDao.getUserBookings(userId);

        // Now you can update the table with the fetched bookings
        DefaultTableModel model = (DefaultTableModel) userPortal.getJTable2().getModel();
        model.setRowCount(0); // Clear existing rows

        for (ManageBookingModel booking : bookings) {
            model.addRow(new Object[]{
                booking.getBookingId(),
                booking.getFlightCode(),
                booking.getSeatNo(),
                booking.getSeatClass(),
                booking.getTravelDate(),
                booking.getPaymentMethod(),
                "Edit/Delete" // Placeholder for action buttons
            });
        }
    }
}


