package wingsnepal.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet; // Import ResultSet here
import java.sql.SQLException;
import database.DbConnection;
import database.MySqlConnection;
import wingsnepal.model.BookingFlightModel;

public class BookingFlightDao {
    public boolean saveBooking(BookingFlightModel booking) {
        String query = "INSERT INTO bookings (user_id, flight_id, seat_id, full_name, email, class_name, seat_no, tickets, date, payment_method, booking_status) " +
                       "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, 'Confirmed')";
        
        // Debug: Check what we're about to save
        System.out.println("💾 DEBUG: About to save booking with user_id = " + booking.getUserId());
        System.out.println("💾 DEBUG: Flight ID = " + booking.getFlightId());
        System.out.println("💾 DEBUG: Customer = " + booking.getFullName());
        
        DbConnection db = new MySqlConnection();
        
        try (Connection conn = db.openConnection();
             PreparedStatement stmt = conn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            
            stmt.setInt(1, booking.getUserId());
            stmt.setInt(2, booking.getFlightId());
            stmt.setInt(3, booking.getSeatId());
            stmt.setString(4, booking.getFullName());
            stmt.setString(5, booking.getEmail());
            stmt.setString(6, booking.getClassName());
            stmt.setString(7, booking.getSeatNo());
            stmt.setInt(8, booking.getTickets());
            stmt.setDate(9, booking.getTravelDate());
            stmt.setString(10, booking.getPaymentMethod());
            
            // Debug: Check what parameters were set
            System.out.println("💾 DEBUG: Parameters set - user_id=" + booking.getUserId() + 
                             ", flight_id=" + booking.getFlightId() + 
                             ", customer=" + booking.getFullName());
            
            int rowsAffected = stmt.executeUpdate();
            
            if (rowsAffected > 0) {
                // Retrieve the generated booking ID
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        booking.setBookingId(generatedKeys.getInt(1));
                    }
                }
                return true;
            }
            
            return false;
            
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean deleteBooking(int bookingId) {
        // Step 1: Get booking details before deletion to release the seat and clean up passenger record
        String getBookingSql = "SELECT b.user_id, b.flight_id, b.class_name, b.seat_no, b.full_name, b.email, f.flight_code " +
                              "FROM bookings b " +
                              "JOIN flights f ON b.flight_id = f.flight_id " +
                              "WHERE b.booking_id = ?";
        DbConnection db = new MySqlConnection();
        
        try (Connection conn = db.openConnection()) {
            // First, get the booking details
            PreparedStatement getStmt = conn.prepareStatement(getBookingSql);
            getStmt.setInt(1, bookingId);
            ResultSet rs = getStmt.executeQuery();
            
            if (rs.next()) {
                int userId = rs.getInt("user_id");
                int flightId = rs.getInt("flight_id");
                String seatClass = rs.getString("class_name");
                String seatNo = rs.getString("seat_no");
                String fullName = rs.getString("full_name");
                String email = rs.getString("email");
                String flightCode = rs.getString("flight_code");
                
                // Step 2: Delete the booking from the 'bookings' table
                String deleteSql = "DELETE FROM bookings WHERE booking_id = ?";
                PreparedStatement deleteStmt = conn.prepareStatement(deleteSql);
                deleteStmt.setInt(1, bookingId);
                int rowsAffected = deleteStmt.executeUpdate();

                if (rowsAffected > 0) {
                    // Step 3: Release the seat
                    SeatClassDao seatDao = new SeatClassDao();
                    seatDao.releaseSeat(flightId, seatClass, seatNo);
                    
                    // Step 4: Remove corresponding passenger record
                    String deletePassengerSql = "DELETE FROM passengers WHERE user_id = ? AND email = ? AND upcoming_flight = ?";
                    PreparedStatement deletePassengerStmt = conn.prepareStatement(deletePassengerSql);
                    deletePassengerStmt.setInt(1, userId);
                    deletePassengerStmt.setString(2, email);
                    deletePassengerStmt.setString(3, flightCode);
                    int passengerRowsAffected = deletePassengerStmt.executeUpdate();
                    
                    System.out.println("USER BOOKING DELETED: Booking ID " + bookingId + 
                                     " | Seat released: " + seatClass + " " + seatNo + 
                                     " | Passenger records removed: " + passengerRowsAffected);
                    return true;
                }
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
