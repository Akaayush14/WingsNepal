package wingsnepal.dao;

import database.MySqlConnection;
import wingsnepal.model.WalkInPassengerListModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO class specifically for walk-in passenger operations
 * Handles passengers from bookings made by employees for walk-in customers
 * 
 * @author WingsNepal Team
 */
public class WalkInPassengerListDao {

    /**
     * Get all walk-in passengers (passengers from employee bookings)
     * @return List of walk-in passengers
     */
    public List<WalkInPassengerListModel> getAllWalkInPassengers() {
        List<WalkInPassengerListModel> passengers = new ArrayList<>();
        String sql = "SELECT b.booking_id, b.user_id, b.flight_id, f.flight_code, f.flight_name, f.from_city, f.to_city, " +
                    "b.full_name, b.email, b.class_name, b.seat_no, b.date, b.payment_method, b.booking_status, " +
                    "u.full_name as employee_name " +
                    "FROM bookings b " +
                    "JOIN flights f ON b.flight_id = f.flight_id " +
                    "JOIN users u ON b.user_id = u.user_id " +
                    "WHERE u.role = 'Employee' " +
                    "ORDER BY b.booking_id DESC";

        try (Connection conn = MySqlConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                WalkInPassengerListModel passenger = new WalkInPassengerListModel(
                    rs.getInt("booking_id"),
                    rs.getInt("user_id"),
                    rs.getInt("flight_id"),
                    rs.getString("flight_code"),
                    rs.getString("flight_name"),
                    rs.getString("from_city"),
                    rs.getString("to_city"),
                    rs.getString("full_name"),
                    rs.getString("email"),
                    rs.getString("class_name"),
                    rs.getString("seat_no"),
                    rs.getDate("date"),
                    rs.getString("payment_method"),
                    rs.getString("booking_status"),
                    rs.getString("employee_name")
                );
                passengers.add(passenger);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error getting all walk-in passengers: " + e.getMessage());
        }
        return passengers;
    }

    /**
     * Search walk-in passengers by keyword
     * @param keyword Search keyword
     * @return List of matching walk-in passengers
     */
    public List<WalkInPassengerListModel> searchWalkInPassengers(String keyword) {
        List<WalkInPassengerListModel> passengers = new ArrayList<>();
        String sql = "SELECT b.booking_id, b.user_id, b.flight_id, f.flight_code, f.flight_name, f.from_city, f.to_city, " +
                    "b.full_name, b.email, b.class_name, b.seat_no, b.date, b.payment_method, b.booking_status, " +
                    "u.full_name as employee_name " +
                    "FROM bookings b " +
                    "JOIN flights f ON b.flight_id = f.flight_id " +
                    "JOIN users u ON b.user_id = u.user_id " +
                    "WHERE u.role = 'Employee' AND " +
                    "(b.full_name LIKE ? OR b.email LIKE ? OR f.flight_code LIKE ? OR f.from_city LIKE ? OR f.to_city LIKE ?) " +
                    "ORDER BY b.booking_id DESC";

        try (Connection conn = MySqlConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            String searchPattern = "%" + keyword + "%";
            pstmt.setString(1, searchPattern);
            pstmt.setString(2, searchPattern);
            pstmt.setString(3, searchPattern);
            pstmt.setString(4, searchPattern);
            pstmt.setString(5, searchPattern);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    WalkInPassengerListModel passenger = new WalkInPassengerListModel(
                        rs.getInt("booking_id"),
                        rs.getInt("user_id"),
                        rs.getInt("flight_id"),
                        rs.getString("flight_code"),
                        rs.getString("flight_name"),
                        rs.getString("from_city"),
                        rs.getString("to_city"),
                        rs.getString("full_name"),
                        rs.getString("email"),
                        rs.getString("class_name"),
                        rs.getString("seat_no"),
                        rs.getDate("date"),
                        rs.getString("payment_method"),
                        rs.getString("booking_status"),
                        rs.getString("employee_name")
                    );
                    passengers.add(passenger);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error searching walk-in passengers: " + e.getMessage());
        }
        return passengers;
    }

    /**
     * Get walk-in passengers by seat class
     * @param seatClass The seat class to filter by
     * @return List of walk-in passengers in the specified seat class
     */
    public List<WalkInPassengerListModel> getWalkInPassengersBySeatClass(String seatClass) {
        List<WalkInPassengerListModel> passengers = new ArrayList<>();
        String sql = "SELECT b.booking_id, b.user_id, b.flight_id, f.flight_code, f.flight_name, f.from_city, f.to_city, " +
                    "b.full_name, b.email, b.class_name, b.seat_no, b.date, b.payment_method, b.booking_status, " +
                    "u.full_name as employee_name " +
                    "FROM bookings b " +
                    "JOIN flights f ON b.flight_id = f.flight_id " +
                    "JOIN users u ON b.user_id = u.user_id " +
                    "WHERE u.role = 'Employee' AND b.class_name = ? " +
                    "ORDER BY b.booking_id DESC";

        try (Connection conn = MySqlConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, seatClass);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    WalkInPassengerListModel passenger = new WalkInPassengerListModel(
                        rs.getInt("booking_id"),
                        rs.getInt("user_id"),
                        rs.getInt("flight_id"),
                        rs.getString("flight_code"),
                        rs.getString("flight_name"),
                        rs.getString("from_city"),
                        rs.getString("to_city"),
                        rs.getString("full_name"),
                        rs.getString("email"),
                        rs.getString("class_name"),
                        rs.getString("seat_no"),
                        rs.getDate("date"),
                        rs.getString("payment_method"),
                        rs.getString("booking_status"),
                        rs.getString("employee_name")
                    );
                    passengers.add(passenger);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error getting walk-in passengers by seat class: " + e.getMessage());
        }
        return passengers;
    }

    /**
     * Update walk-in passenger information
     * @param bookingId The booking ID to update
     * @param passengerName New passenger name
     * @param passengerEmail New passenger email
     * @param seatClass New seat class
     * @param seatNo New seat number
     * @return true if successful, false otherwise
     */
    public boolean updateWalkInPassenger(int bookingId, String passengerName, String passengerEmail, 
                                        String seatClass, String seatNo) {
        String sql = "UPDATE bookings SET full_name = ?, email = ?, class_name = ?, seat_no = ? WHERE booking_id = ?";
        
        try (Connection conn = MySqlConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, passengerName);
            pstmt.setString(2, passengerEmail);
            pstmt.setString(3, seatClass);
            pstmt.setString(4, seatNo);
            pstmt.setInt(5, bookingId);
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error updating walk-in passenger: " + e.getMessage());
            return false;
        }
    }

    /**
     * Delete walk-in passenger booking
     * @param bookingId The booking ID to delete
     * @return true if successful, false otherwise
     */
    public boolean deleteWalkInPassenger(int bookingId) {
        // Fetch booking details before deletion
        int flightId = -1;
        String seatClass = null;
        String seatNo = null;
        int userId = -1;
        String email = null;
        String flightCode = null;
        
        String selectSql = "SELECT b.flight_id, b.class_name, b.seat_no, b.user_id, b.email, f.flight_code " +
                          "FROM bookings b " +
                          "JOIN flights f ON b.flight_id = f.flight_id " +
                          "WHERE b.booking_id = ?";
        
        String deleteBookingSql = "DELETE FROM bookings WHERE booking_id = ?";
        String deletePassengerSql = "DELETE FROM passengers WHERE user_id = ? AND email = ? AND upcoming_flight = ?";
        
        try (Connection conn = MySqlConnection.getConnection()) {
            conn.setAutoCommit(false); // Start transaction
            
            try {
                // Get booking details
                try (PreparedStatement selectStmt = conn.prepareStatement(selectSql)) {
                    selectStmt.setInt(1, bookingId);
                    try (ResultSet rs = selectStmt.executeQuery()) {
                        if (rs.next()) {
                            flightId = rs.getInt("flight_id");
                            seatClass = rs.getString("class_name");
                            seatNo = rs.getString("seat_no");
                            userId = rs.getInt("user_id");
                            email = rs.getString("email");
                            flightCode = rs.getString("flight_code");
                        }
                    }
                }
                
                // Delete booking
                try (PreparedStatement deleteBookingStmt = conn.prepareStatement(deleteBookingSql)) {
                    deleteBookingStmt.setInt(1, bookingId);
                    int bookingRowsAffected = deleteBookingStmt.executeUpdate();
                    
                    if (bookingRowsAffected > 0) {
                        // Delete corresponding passenger record
                        try (PreparedStatement deletePassengerStmt = conn.prepareStatement(deletePassengerSql)) {
                            deletePassengerStmt.setInt(1, userId);
                            deletePassengerStmt.setString(2, email);
                            deletePassengerStmt.setString(3, flightCode);
                            int passengerRowsAffected = deletePassengerStmt.executeUpdate();
                            
                            System.out.println("✅ WALK-IN DELETION: Booking " + bookingId + " deleted. " +
                                             "Passenger records removed: " + passengerRowsAffected);
                        }
                        
                        // Release the seat
                        if (flightId != -1 && seatClass != null && seatNo != null) {
                            SeatClassDao seatDao = new SeatClassDao();
                            seatDao.releaseSeat(flightId, seatClass, seatNo);
                            System.out.println("✅ WALK-IN DELETION: Seat " + seatClass + " " + seatNo + " released");
                        }
                        
                        conn.commit(); // Commit transaction
                        return true;
                    }
                }
                
                conn.rollback(); // Rollback if booking deletion failed
                return false;
                
            } catch (SQLException e) {
                conn.rollback(); // Rollback on any error
                throw e;
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error deleting walk-in passenger: " + e.getMessage());
        }
        return false;
    }

    /**
     * Get walk-in passenger by booking ID
     * @param bookingId The booking ID
     * @return WalkInPassengerListModel or null if not found
     */
    public WalkInPassengerListModel getWalkInPassengerById(int bookingId) {
        String sql = "SELECT b.booking_id, b.user_id, b.flight_id, f.flight_code, f.flight_name, f.from_city, f.to_city, " +
                    "b.full_name, b.email, b.class_name, b.seat_no, b.date, b.payment_method, b.booking_status, " +
                    "u.full_name as employee_name " +
                    "FROM bookings b " +
                    "JOIN flights f ON b.flight_id = f.flight_id " +
                    "JOIN users u ON b.user_id = u.user_id " +
                    "WHERE u.role = 'Employee' AND b.booking_id = ? " +
                    "LIMIT 1";

        try (Connection conn = MySqlConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, bookingId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new WalkInPassengerListModel(
                        rs.getInt("booking_id"),
                        rs.getInt("user_id"),
                        rs.getInt("flight_id"),
                        rs.getString("flight_code"),
                        rs.getString("flight_name"),
                        rs.getString("from_city"),
                        rs.getString("to_city"),
                        rs.getString("full_name"),
                        rs.getString("email"),
                        rs.getString("class_name"),
                        rs.getString("seat_no"),
                        rs.getDate("date"),
                        rs.getString("payment_method"),
                        rs.getString("booking_status"),
                        rs.getString("employee_name")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error getting walk-in passenger by ID: " + e.getMessage());
        }
        return null;
    }

    /**
     * Get total count of walk-in passengers
     * @return Total count
     */
    public int getTotalWalkInPassengersCount() {
        String sql = "SELECT COUNT(*) as total FROM bookings b " +
                    "JOIN users u ON b.user_id = u.user_id " +
                    "WHERE u.role = 'Employee'";
        
        try (Connection conn = MySqlConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error getting total walk-in passengers count: " + e.getMessage());
        }
        return 0;
    }

    /**
     * Update the booking status for a walk-in passenger
     * @param bookingId The booking ID
     * @param newStatus The new status ("Confirmed" or "Cancelled")
     * @return true if update was successful, false otherwise
     */
    public boolean updateBookingStatus(int bookingId, String newStatus) {
        String sql = "UPDATE bookings SET booking_status = ? WHERE booking_id = ?";
        try (Connection conn = MySqlConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newStatus);
            pstmt.setInt(2, bookingId);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error updating booking status: " + e.getMessage());
            return false;
        }
    }
} 