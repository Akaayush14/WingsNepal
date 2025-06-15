/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package wingsnepal.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import wingsnepal.database.DbConnection;
import wingsnepal.database.MySqlConnection;
import wingsnepal.model.BookingFlight;
/**
 *
 * @author Aayush Kharel
 */

public class BookingFlightDao {
    public boolean saveBooking(BookingFlight booking) {
        boolean success = false;

        String sql = "INSERT INTO bookings (flight_id, seat_id, `date`, full_name, email, seat_class, seat_no, tickets, travel_date, payment_method) "
                   + "VALUES (?, ?, CURDATE(), ?, ?, ?, ?, ?, ?, ?)";
        
        DbConnection db = new MySqlConnection();
        try (Connection conn = db.openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, booking.getFlightId());
            stmt.setInt(2, booking.getSeatId());
            stmt.setString(3, booking.getFullName());
            stmt.setString(4, booking.getEmail());
            stmt.setString(5, booking.getSeatClass());
            stmt.setString(6, booking.getSeatNo());
            stmt.setInt(7, booking.getTickets());
            stmt.setDate(8, booking.getTravelDate());
            stmt.setString(9, booking.getPaymentMethod());

            success = stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return success;
    }
}
