package wingsnepalController;

import wingsnepal.dao.ManageBookingDao;
import wingsnepal.model.ManageBookingModel;
import wingsnepal.view.UserPortal;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import wingsnepal.dao.SeatClassDao;
import wingsnepal.view.EditBookDialog;

public class ManageBookingController {
    private UserPortal userPortal;
    private ManageBookingDao manageBookingDao;

    // Constructor to initialize the UserPortal and ManageBookingDao instances
    public ManageBookingController(UserPortal userPortal) {
        this.userPortal = userPortal;
        this.manageBookingDao = new ManageBookingDao();
    }

    // Method to handle booking actions like Edit and Delete
        public boolean handleBookingActions(int rowIndex, String action) {
        DefaultTableModel model = (DefaultTableModel) userPortal.getJTable2().getModel();

        // Ensure the rowIndex is valid
        if (rowIndex < 0 || rowIndex >= model.getRowCount()) {
            JOptionPane.showMessageDialog(userPortal, "Please select a valid booking.");
            return false;
        }

        // Get the bookingId value and check if it's null
        Object bookingIdValue = model.getValueAt(rowIndex, 0); // Column 0 should contain the booking ID
        if (bookingIdValue == null) {
            JOptionPane.showMessageDialog(userPortal, "Booking ID is not available in the selected row.");
            return false;
        }

        int bookingId = (int) bookingIdValue;

        // Check if the action is delete or edit
        if ("Delete".equals(action)) {
            int confirmation = JOptionPane.showConfirmDialog(userPortal, "Are you sure you want to delete this booking?");
            if (confirmation == JOptionPane.YES_OPTION) {
                boolean success = manageBookingDao.deleteBooking(bookingId); // Now bookingId is properly defined
                if (success) {
                    JOptionPane.showMessageDialog(userPortal, "Booking deleted successfully!");
                    // Refresh the booking table and available seats
                    showAvailableSeats(flightId, className); // Refresh seat options
                    showBookings(userPortal.getLoggedInUser().getUserId());
                } else {
                    JOptionPane.showMessageDialog(userPortal, "Failed to delete booking.");
                }
            }
        } else if ("Edit".equals(action)) {
            // Open the Edit Booking dialog
            EditBookDialog editDialog = new EditBookDialog(userPortal, bookingId);
            editDialog.setVisible(true);
        }

        return false;
    }


    // Method to handle delete action when a row is selected
    public void handleDeleteAction(int rowIndex) {
        DefaultTableModel model = (DefaultTableModel) userPortal.getJTable2().getModel();

        // Check if the table has any rows
        if (model.getRowCount() == 0) {
            JOptionPane.showMessageDialog(userPortal, "No rows available for deletion.");
            return; // Exit the method if the table is empty
        }

        // Ensure the rowIndex is valid
        if (rowIndex < 0 || rowIndex >= model.getRowCount()) {
            JOptionPane.showMessageDialog(userPortal, "Invalid row selected.");
            return; // Exit if the row index is invalid
        }

        // Now proceed with the logic (e.g., deleting the booking)
        Object bookingIdValue = model.getValueAt(rowIndex, 0); // Get booking ID from the selected row

        if (bookingIdValue != null) {
            int bookingId = (int) bookingIdValue;
            int confirmation = JOptionPane.showConfirmDialog(userPortal, "Are you sure you want to delete this booking?");
            if (confirmation == JOptionPane.YES_OPTION) {
                boolean success = manageBookingDao.deleteBooking(bookingId);
                if (success) {
                    JOptionPane.showMessageDialog(userPortal, "Booking deleted successfully!");
                    // Refresh the booking table to reflect the deletion
                    showBookings(userPortal.getLoggedInUser().getUserId());
                } else {
                    JOptionPane.showMessageDialog(userPortal, "Failed to delete booking.");
                }
            }
        } else {
            JOptionPane.showMessageDialog(userPortal, "Booking ID is missing in the selected row.");
        }
    }

    // Method to refresh the booking table
    public void showBookings(int userId) {
        // Fetch updated bookings from the DAO
        List<ManageBookingModel> bookings = manageBookingDao.getUserBookings(userId);

        // Update the table with the fetched data
        DefaultTableModel model = (DefaultTableModel) userPortal.getJTable2().getModel();
        model.setRowCount(0); // Clear existing rows

        for (ManageBookingModel booking : bookings) {
            model.addRow(new Object[] {
                booking.getBookingId(),
                booking.getFlightCode(),
                booking.getSeatNo(),
                booking.getClassName(),
                booking.getTravelDate(),
                booking.getPaymentMethod(),
                "Edit/Delete" // Placeholder for action buttons
            });
        }
    }
    
    public void showAvailableSeats(int flightId, String seatClass) {
        SeatClassDao seatClassDao = new SeatClassDao();
        List<String> availableSeats = seatClassDao.getAvailableSeats(flightId, className);
        // Assuming you have a JComboBox or a list showing seat numbers:
        JComboBox<String> seatComboBox = userPortal.getSeatComboBox();
        seatComboBox.removeAllItems();

        // Add available seats to the combo box
        for (String seat : availableSeats) {
            seatComboBox.addItem(seat); // Add available seats to the combo box
        }
    }

}
