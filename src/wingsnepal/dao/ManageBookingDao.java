package wingsnepal.dao;

import wingsnepal.model.ManageBookingModel;
import wingsnepal.database.DbConnection;
import wingsnepal.database.MySqlConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ManageBookingDao {

    private DbConnection db;

    public ManageBookingDao() {
        db = new MySqlConnection(); // Instantiate the MySQL connection
    }

    // Retrieve user bookings
    public List<ManageBookingModel> getUserBookings(int userId) {
        List<ManageBookingModel> bookings = new ArrayList<>();
        String query = "SELECT * FROM bookings WHERE user_id = ?";

        try (Connection conn = db.openConnection(); // Open the connection
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int bookingId = rs.getInt("booking_id");
                int flightId = rs.getInt("flight_id");
                String flightCode = rs.getString("flight_code");
                String className = rs.getString("class_name");
                String seatNo = rs.getString("seat_no");
                int tickets = rs.getInt("tickets");
                Date travelDate = rs.getDate("date");
                String paymentMethod = rs.getString("payment_method");

                bookings.add(new ManageBookingModel(bookingId, userId, flightId, flightCode, className, seatNo, tickets, travelDate, paymentMethod));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return bookings;
    }

    public boolean deleteBooking(int bookingId) {
        String sql = "DELETE FROM bookings WHERE booking_id = ?";
        try (Connection conn = db.openConnection(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, bookingId); // Set the booking ID in the query

            int rowsAffected = stmt.executeUpdate();

            // Debugging: Check the number of affected rows
            System.out.println("Rows affected: " + rowsAffected);

            return rowsAffected > 0;  // Return true if the booking was deleted successfully
        } catch (SQLException e) {
            e.printStackTrace();
            return false;  // Return false if there was an error during deletion
        }
    }


    // Update booking details
    public boolean updateBooking(int bookingId, String seatNo, String className) {
        String query = "UPDATE bookings SET seat_no = ?, class_name = ? WHERE booking_id = ?";
        try (Connection conn = db.openConnection(); // Open the connection
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, seatNo);
            ps.setString(2, className);
            ps.setInt(3, bookingId);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }
    
    // Retrieve booking details by bookingId
    public ManageBookingModel getBookingDetailsById(int bookingId) {
        ManageBookingModel booking = null;
        String query = "SELECT * FROM bookings WHERE booking_id = ?";

        try (Connection conn = db.openConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, bookingId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int userId = rs.getInt("user_id");
                int flightId = rs.getInt("flight_id");
                String flightCode = rs.getString("flight_code");
                String className = rs.getString("class_name");
                String seatNo = rs.getString("seat_no");
                int tickets = rs.getInt("tickets");
                Date travelDate = rs.getDate("date");
                String paymentMethod = rs.getString("payment_method");

                booking = new ManageBookingModel(bookingId, userId, flightId, flightCode, className, seatNo, tickets, travelDate, paymentMethod);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return booking;
    }
        
    // Get the flight code by bookingId
    public String getFlightCodeByBookingId(int bookingId) {
        String flightCode = null;
        try (Connection con = db.openConnection();
             PreparedStatement pst = con.prepareStatement("SELECT flight_code FROM bookings WHERE booking_id = ?")) {
            pst.setInt(1, bookingId);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                flightCode = rs.getString("flight_code");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flightCode;
    }
    
    public boolean markSeatAsAvailable(int flightId, String seatNo) {
    String query = "UPDATE seat_classes SET is_booked = 0 WHERE flight_id = ? AND seat_no = ?";
    try (Connection conn = db.openConnection(); 
         PreparedStatement stmt = conn.prepareStatement(query)) {

        stmt.setInt(1, flightId);
        stmt.setString(2, seatNo);
        int rowsAffected = stmt.executeUpdate();
        return rowsAffected > 0;
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}

}
