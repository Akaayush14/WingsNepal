package wingsnepal.dao;

import database.MySqlConnection;
import wingsnepal.model.PassengerModel;
import wingsnepal.model.BookingFlightModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO class to handle the relationship between bookings and passengers
 * This class helps sync passenger data from bookings and manage user-passenger relationships
 * 
 * @author WingsNepal Team
 */
public class BookingPassengerDao {
    
    private PassengerDao passengerDao;
    private BookingFlightDao bookingDao;
    
    public BookingPassengerDao() {
        this.passengerDao = new PassengerDao();
        this.bookingDao = new BookingFlightDao();
    }
    
    /**
     * Sync passenger data from a booking to the passengers table with phone number
     * @param booking The booking to sync
     * @param phone The phone number for the passenger
     * @return true if successful, false otherwise
     */
    public boolean syncBookingToPassengerWithPhone(BookingFlightModel booking, String phone) {
        try {
            System.out.println("[DEBUG] syncBookingToPassengerWithPhone called with booking: " + booking + ", phone: " + phone);
            // Check if passenger already exists
            PassengerModel existingPassenger = findPassengerByEmailAndUser(booking.getEmail(), booking.getUserId());
            
            if (existingPassenger != null) {
                System.out.println("[DEBUG] Existing passenger found: " + existingPassenger);
                // Update existing passenger with new flight info and phone
                existingPassenger.setUpcomingFlight(booking.getFlightCode());
                existingPassenger.setSeatClass(booking.getClassName());
                existingPassenger.setStatus("Active");
                existingPassenger.setPhone(phone);
                boolean updated = passengerDao.updatePassenger(existingPassenger);
                System.out.println("[DEBUG] Updated passenger result: " + updated);
                return updated;
            } else {
                // Create new passenger record
                PassengerModel newPassenger = new PassengerModel();
                newPassenger.setUserId(booking.getUserId());
                newPassenger.setFullName(booking.getFullName());
                newPassenger.setEmail(booking.getEmail());
                newPassenger.setPhone(phone);
                newPassenger.setStatus("Active");
                newPassenger.setUpcomingFlight(booking.getFlightCode());
                newPassenger.setSeatClass(booking.getClassName());
                newPassenger.setRelationship("Customer"); // Default relationship
                System.out.println("[DEBUG] Adding new passenger: " + newPassenger);
                boolean added = passengerDao.addPassenger(newPassenger);
                System.out.println("[DEBUG] Added passenger result: " + added);
                return added;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("[ERROR] Error syncing booking to passenger: " + e.getMessage());
            return false;
        }
    }

    /**
     * Sync passenger data from a booking to the passengers table
     * @param booking The booking to sync
     * @param relationship The relationship type ("Self", "Family", "Friend", etc.)
     * @return true if successful, false otherwise
     */
    public boolean syncBookingToPassenger(BookingFlightModel booking, String relationship) {
        try {
            System.out.println("[DEBUG] syncBookingToPassenger called with booking: " + booking + ", relationship: " + relationship);
            // Check if passenger already exists
            PassengerModel existingPassenger = findPassengerByEmailAndUser(booking.getEmail(), booking.getUserId());
            
            if (existingPassenger != null) {
                System.out.println("[DEBUG] Existing passenger found: " + existingPassenger);
                // Update existing passenger with new flight info
                existingPassenger.setUpcomingFlight(booking.getFlightCode());
                existingPassenger.setSeatClass(booking.getClassName());
                existingPassenger.setStatus("Active");
                boolean updated = passengerDao.updatePassenger(existingPassenger);
                System.out.println("[DEBUG] Updated passenger result: " + updated);
                return updated;
            } else {
                // Create new passenger record
                PassengerModel newPassenger = new PassengerModel();
                newPassenger.setUserId(booking.getUserId());
                newPassenger.setFullName(booking.getFullName());
                newPassenger.setEmail(booking.getEmail());
                newPassenger.setStatus("Active");
                newPassenger.setUpcomingFlight(booking.getFlightCode());
                newPassenger.setSeatClass(booking.getClassName());
                newPassenger.setRelationship(relationship);
                System.out.println("[DEBUG] Adding new passenger: " + newPassenger);
                boolean added = passengerDao.addPassenger(newPassenger);
                System.out.println("[DEBUG] Added passenger result: " + added);
                return added;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("[ERROR] Error syncing booking to passenger: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Find passenger by email and user ID
     * @param email Passenger email
     * @param userId User ID who created the passenger
     * @return Passenger object or null if not found
     */
    public PassengerModel findPassengerByEmailAndUser(String email, int userId) {
        String sql = "SELECT p.passenger_id, p.user_id, p.full_name, p.status, p.upcoming_flight, p.email, p.seat_class, p.relationship " +
                    "FROM passengers p WHERE p.email = ? AND p.user_id = ?";

        try (Connection conn = MySqlConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, email);
            pstmt.setInt(2, userId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new PassengerModel(
                        rs.getInt("passenger_id"),
                        rs.getInt("user_id"),
                        rs.getString("full_name"),
                        rs.getString("status"),
                        rs.getString("upcoming_flight"),
                        rs.getString("email"),
                        rs.getString("seat_class"),
                        rs.getString("relationship")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error finding passenger by email and user: " + e.getMessage());
        }
        return null;
    }
    
    /**
     * Get all passengers created by a specific user
     * @param userId The user ID
     * @return List of passengers created by the user
     */
    public List<PassengerModel> getPassengersByUser(int userId) {
        List<PassengerModel> passengers = new ArrayList<>();
        String sql = "SELECT p.passenger_id, p.user_id, p.full_name, p.status, p.upcoming_flight, p.email, p.seat_class, p.relationship " +
                    "FROM passengers p WHERE p.user_id = ? ORDER BY p.full_name";

        try (Connection conn = MySqlConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, userId);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    PassengerModel passenger = new PassengerModel(
                        rs.getInt("passenger_id"),
                        rs.getInt("user_id"),
                        rs.getString("full_name"),
                        rs.getString("status"),
                        rs.getString("upcoming_flight"),
                        rs.getString("email"),
                        rs.getString("seat_class"),
                        rs.getString("relationship")
                    );
                    passengers.add(passenger);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error getting passengers by user: " + e.getMessage());
        }
        return passengers;
    }
    
    /**
     * Get passengers by relationship type for a specific user
     * @param userId The user ID
     * @param relationship The relationship type
     * @return List of passengers with the specified relationship
     */
    public List<PassengerModel> getPassengersByRelationship(int userId, String relationship) {
        List<PassengerModel> passengers = new ArrayList<>();
        String sql = "SELECT p.passenger_id, p.user_id, p.full_name, p.status, p.upcoming_flight, p.email, p.seat_class, p.relationship " +
                    "FROM passengers p WHERE p.user_id = ? AND p.relationship = ? ORDER BY p.full_name";

        try (Connection conn = MySqlConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, userId);
            pstmt.setString(2, relationship);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    PassengerModel passenger = new PassengerModel(
                        rs.getInt("passenger_id"),
                        rs.getInt("user_id"),
                        rs.getString("full_name"),
                        rs.getString("status"),
                        rs.getString("upcoming_flight"),
                        rs.getString("email"),
                        rs.getString("seat_class"),
                        rs.getString("relationship")
                    );
                    passengers.add(passenger);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error getting passengers by relationship: " + e.getMessage());
        }
        return passengers;
    }
    
    /**
     * Update passenger-booking relationship when user books for an existing passenger
     * @param passengerId The passenger ID to update
     * @param flightCode The new flight code
     * @param seatClass The seat class
     * @return true if successful, false otherwise
     */
    public boolean updatePassengerFlight(int passengerId, String flightCode, String seatClass) {
        String sql = "UPDATE passengers SET upcoming_flight = ?, seat_class = ?, status = 'Active' WHERE passenger_id = ?";

        try (Connection conn = MySqlConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, flightCode);
            pstmt.setString(2, seatClass);
            pstmt.setInt(3, passengerId);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error updating passenger flight: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Get booking statistics for a user (total bookings, active bookings, etc.)
     * @param userId The user ID
     * @return int array [totalPassengers, activePassengers, cancelledPassengers]
     */
    public int[] getBookingStatistics(int userId) {
        String sql = "SELECT " +
                    "COUNT(*) as total_passengers, " +
                    "SUM(CASE WHEN status = 'Active' THEN 1 ELSE 0 END) as active_passengers, " +
                    "SUM(CASE WHEN status = 'Cancelled' THEN 1 ELSE 0 END) as cancelled_passengers " +
                    "FROM passengers WHERE user_id = ?";

        try (Connection conn = MySqlConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, userId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new int[]{
                        rs.getInt("total_passengers"),
                        rs.getInt("active_passengers"),
                        rs.getInt("cancelled_passengers")
                    };
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error getting booking statistics: " + e.getMessage());
        }
        return new int[]{0, 0, 0};
    }
    
    /**
     * Check if a user has already booked for a specific passenger email
     * @param userId The user ID
     * @param passengerEmail The passenger email
     * @return true if user has booked for this passenger, false otherwise
     */
    public boolean hasUserBookedForPassenger(int userId, String passengerEmail) {
        String sql = "SELECT COUNT(*) FROM passengers WHERE user_id = ? AND email = ?";

        try (Connection conn = MySqlConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, userId);
            pstmt.setString(2, passengerEmail);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error checking if user booked for passenger: " + e.getMessage());
        }
        return false;
    }
    
    /**
     * Get frequently booked passengers for a user (most frequent first)
     * @param userId The user ID
     * @param limit Maximum number of passengers to return
     * @return List of frequently booked passengers
     */
    public List<PassengerModel> getFrequentlyBookedPassengers(int userId, int limit) {
        List<PassengerModel> passengers = new ArrayList<>();
        String sql = "SELECT p.passenger_id, p.user_id, p.full_name, p.status, p.upcoming_flight, p.email, p.seat_class, p.relationship, " +
                    "COUNT(bf.booking_id) as booking_count " +
                    "FROM passengers p " +
                    "LEFT JOIN booking_flights bf ON p.email = bf.email AND p.user_id = bf.user_id " +
                    "WHERE p.user_id = ? " +
                    "GROUP BY p.passenger_id, p.user_id, p.full_name, p.status, p.upcoming_flight, p.email, p.seat_class, p.relationship " +
                    "ORDER BY booking_count DESC, p.full_name " +
                    "LIMIT ?";

        try (Connection conn = MySqlConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, userId);
            pstmt.setInt(2, limit);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    PassengerModel passenger = new PassengerModel(
                        rs.getInt("passenger_id"),
                        rs.getInt("user_id"),
                        rs.getString("full_name"),
                        rs.getString("status"),
                        rs.getString("upcoming_flight"),
                        rs.getString("email"),
                        rs.getString("seat_class"),
                        rs.getString("relationship")
                    );
                    passengers.add(passenger);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error getting frequently booked passengers: " + e.getMessage());
        }
        return passengers;
    }
} 