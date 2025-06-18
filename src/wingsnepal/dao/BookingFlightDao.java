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

        String sql = "INSERT INTO bookings (user_Id, flight_id, seat_id, seat_class, seat_no, tickets, travel_date, payment_method) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        DbConnection db = new MySqlConnection();
        try (Connection conn = db.openConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)){
            
            stmt.setInt(1, booking.getUserId());
            stmt.setInt(2, booking.getFlightId());
            stmt.setInt(3, booking.getSeatId());
            stmt.setString(4, booking.getSeatClass());
            stmt.setString(5, booking.getSeatNo());
            stmt.setInt(6, booking.getTickets());
            stmt.setDate(7, booking.getTravelDate());
            stmt.setString(8, booking.getPaymentMethod());
            
            return stmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
