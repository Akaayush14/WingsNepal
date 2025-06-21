/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package wingsnepal.dao;

import wingsnepal.model.ManageBookingModel;
import wingsnepal.database.DbConnection;
import wingsnepal.database.MySqlConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;;

/**
 *
 * @author Aayush Kharel
 */

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
                String seatClass = rs.getString("seat_class");
                String seatNo = rs.getString("seat_no");
                int tickets = rs.getInt("tickets");
                Date travelDate = rs.getDate("travel_date");
                String paymentMethod = rs.getString("payment_method");

                bookings.add(new ManageBookingModel(bookingId, userId, flightId, flightCode, seatClass, seatNo, tickets, travelDate, paymentMethod));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return bookings;
    }

    // Delete a booking by its ID
    public boolean deleteBooking(int bookingId) {
        String query = "DELETE FROM bookings WHERE booking_id = ?";
        try (Connection conn = db.openConnection(); // Open the connection
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, bookingId);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    // Update booking details
    public boolean updateBooking(int bookingId, String seatNo, String seatClass) {
        String query = "UPDATE bookings SET seat_no = ?, seat_class = ? WHERE booking_id = ?";
        try (Connection conn = db.openConnection(); // Open the connection
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, seatNo);
            ps.setString(2, seatClass);
            ps.setInt(3, bookingId);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }
    
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
                String seatClass = rs.getString("seat_class");
                String seatNo = rs.getString("seat_no");
                int tickets = rs.getInt("tickets");
                Date travelDate = rs.getDate("travel_date");
                String paymentMethod = rs.getString("payment_method");

                booking = new ManageBookingModel(bookingId, userId, flightId, flightCode, seatClass, seatNo, tickets, travelDate, paymentMethod);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return booking;
    }

}