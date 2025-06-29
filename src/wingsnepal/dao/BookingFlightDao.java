package wingsnepal.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet; // Import ResultSet here
import java.sql.SQLException;
import wingsnepal.database.DbConnection;
import wingsnepal.database.MySqlConnection;
import wingsnepal.model.BookingFlight;

public class BookingFlightDao {
    public boolean saveBooking(BookingFlight booking) {
        boolean success = false;

        String sql = "INSERT INTO bookings (user_Id, flight_id, seat_id, class_name, seat_no, tickets, date, payment_method, is_booked) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        DbConnection db = new MySqlConnection();
        try (Connection conn = db.openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, booking.getUserId());
            stmt.setInt(2, booking.getFlightId());
            stmt.setInt(3, booking.getSeatId());
            stmt.setString(4, booking.getClassName());
            stmt.setString(5, booking.getSeatNo());
            stmt.setInt(6, booking.getTickets());
            stmt.setDate(7, booking.getTravelDate());
            stmt.setString(8, booking.getPaymentMethod());
            stmt.setBoolean(9, booking.isBooked());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteBooking(int bookingId) {
        // Step 1: Delete the booking from the 'bookings' table
        String deleteSql = "DELETE FROM bookings WHERE booking_id = ?";

        DbConnection db = new MySqlConnection();
        try (Connection conn = db.openConnection();
             PreparedStatement stmt = conn.prepareStatement(deleteSql)) {

            stmt.setInt(1, bookingId);
            int rowsAffected = stmt.executeUpdate();

            // Step 2: If booking is deleted, update the seat status to available
            if (rowsAffected > 0) {
                // Assuming you have a 'seats' table with a column `is_booked`
                String updateSeatSql = "UPDATE seats SET is_booked = false WHERE seat_id = ?";
                try (PreparedStatement seatStmt = conn.prepareStatement(updateSeatSql)) {
                    int seatId = getSeatIdFromBooking(bookingId); // Get the seat ID from the booking
                    seatStmt.setInt(1, seatId);
                    seatStmt.executeUpdate();
                }
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    private int getSeatIdFromBooking(int bookingId) {
        // SQL query to get the seat ID from the bookings table based on booking ID
        String query = "SELECT seat_id FROM bookings WHERE booking_id = ?";
        int seatId = -1; // Default to -1 if not found
        DbConnection db = new MySqlConnection();
        try (Connection conn = db.openConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            // Set the booking ID in the prepared statement
            stmt.setInt(1, bookingId);

            // Execute the query and process the result
            try (ResultSet rs = stmt.executeQuery()) {  // Use ResultSet here
                if (rs.next()) {
                    seatId = rs.getInt("seat_id"); // Retrieve the seat_id from the result set
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return seatId; // Return the seat ID, or -1 if not found
    }


}
