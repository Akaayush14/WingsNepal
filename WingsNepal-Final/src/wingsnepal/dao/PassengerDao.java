package wingsnepal.dao;

import database.MySqlConnection;
import wingsnepal.model.PassengerModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO class to handle passenger-related database operations
 * 
 * @author WingsNepal Team
 */
public class PassengerDao {

    /**
     * Get all passengers from the database
     * @return List of all passengers
     */
    public List<PassengerModel> getAllPassengers() {
        List<PassengerModel> passengers = new ArrayList<>();
        String sql = "SELECT DISTINCT p.passenger_id, p.user_id, p.full_name, p.status, p.upcoming_flight, p.email, p.seat_class, p.relationship, " +
                    "COALESCE(u.phone, '') as phone, " +
                    "COALESCE(b.seat_no, 'N/A') as seat_number, " +
                    "COALESCE(b.booking_status, 'N/A') as booking_status " +
                    "FROM passengers p " +
                    "LEFT JOIN users u ON p.user_id = u.user_id " +
                    "LEFT JOIN flights f ON f.flight_code = p.upcoming_flight " +
                    "LEFT JOIN bookings b ON b.flight_id = f.flight_id AND p.email = b.email AND p.user_id = b.user_id " +
                    "WHERE p.passenger_id IS NOT NULL " +
                    "ORDER BY p.passenger_id, p.full_name";

        try (Connection conn = MySqlConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                PassengerModel passenger = new PassengerModel(
                    rs.getInt("passenger_id"),
                    rs.getInt("user_id"),
                    rs.getString("full_name"),
                    rs.getString("status"),
                    rs.getString("upcoming_flight"),
                    rs.getString("email"),
                    rs.getString("seat_class"),
                    rs.getString("relationship"),
                    rs.getString("phone"),
                    rs.getString("seat_number"),
                    rs.getString("booking_status")
                );
                passengers.add(passenger);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error getting all passengers: " + e.getMessage());
        }
        return passengers;
    }

    /**
     * Add a new passenger to the database
     * @param passenger The passenger to add
     * @return true if successful, false otherwise
     */
    public boolean addPassenger(PassengerModel passenger) {
        System.out.println("[DEBUG] addPassenger called with: " + passenger);
        String sql = "INSERT INTO passengers (user_id, full_name, status, upcoming_flight, email, seat_class, relationship) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = MySqlConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, passenger.getUserId());
            pstmt.setString(2, passenger.getFullName());
            pstmt.setString(3, passenger.getStatus());
            pstmt.setString(4, passenger.getUpcomingFlight());
            pstmt.setString(5, passenger.getEmail());
            pstmt.setString(6, passenger.getSeatClass());
            pstmt.setString(7, passenger.getRelationship());

            int rowsAffected = pstmt.executeUpdate();
            System.out.println("[DEBUG] addPassenger rowsAffected: " + rowsAffected);
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("[ERROR] Error adding passenger: " + e.getMessage());
            return false;
        }
    }

    /**
     * Update an existing passenger in the database
     * @param passenger The passenger to update
     * @return true if successful, false otherwise
     */
    public boolean updatePassenger(PassengerModel passenger) {
        String sql = "UPDATE passengers SET user_id = ?, full_name = ?, status = ?, " +
                    "upcoming_flight = ?, email = ?, seat_class = ?, relationship = ? " +
                    "WHERE passenger_id = ?";

        try (Connection conn = MySqlConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, passenger.getUserId());
            pstmt.setString(2, passenger.getFullName());
            pstmt.setString(3, passenger.getStatus());
            pstmt.setString(4, passenger.getUpcomingFlight());
            pstmt.setString(5, passenger.getEmail());
            pstmt.setString(6, passenger.getSeatClass());
            pstmt.setString(7, passenger.getRelationship());
            pstmt.setInt(8, passenger.getPassengerId());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error updating passenger: " + e.getMessage());
            return false;
        }
    }

    /**
     * Delete a passenger from the database
     * @param passengerId The ID of the passenger to delete
     * @return true if successful, false otherwise
     */
    public boolean deletePassenger(int passengerId) {
        String sql = "DELETE FROM passengers WHERE passenger_id = ?";

        try (Connection conn = MySqlConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, passengerId);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error deleting passenger: " + e.getMessage());
            return false;
        }
    }

    /**
     * Search passengers by keyword
     * @param keyword Search keyword
     * @return List of matching passengers
     */
    public List<PassengerModel> searchPassengers(String keyword) {
        List<PassengerModel> passengers = new ArrayList<>();
        String sql = "SELECT DISTINCT p.passenger_id, p.user_id, p.full_name, p.status, p.upcoming_flight, p.email, p.seat_class, p.relationship, " +
                    "COALESCE(u.phone, '') as phone, " +
                    "COALESCE(b.seat_no, 'N/A') as seat_number, " +
                    "COALESCE(b.booking_status, 'N/A') as booking_status " +
                    "FROM passengers p " +
                    "LEFT JOIN users u ON p.user_id = u.user_id " +
                    "LEFT JOIN flights f ON f.flight_code = p.upcoming_flight " +
                    "LEFT JOIN bookings b ON b.flight_id = f.flight_id AND p.email = b.email AND p.user_id = b.user_id " +
                    "WHERE (p.full_name LIKE ? OR p.email LIKE ?) AND p.passenger_id IS NOT NULL " +
                    "ORDER BY p.passenger_id, p.full_name";

        try (Connection conn = MySqlConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            String searchPattern = "%" + keyword + "%";
            pstmt.setString(1, searchPattern);
            pstmt.setString(2, searchPattern);

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
                        rs.getString("relationship"),
                        rs.getString("phone"),
                        rs.getString("seat_number"),
                        rs.getString("booking_status")
                    );
                    passengers.add(passenger);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error searching passengers: " + e.getMessage());
        }
        return passengers;
    }

    /**
     * Get passengers by status
     * @param status The status to filter by
     * @return List of passengers with the specified status
     */
    public List<PassengerModel> getPassengersByStatus(String status) {
        List<PassengerModel> passengers = new ArrayList<>();
        String sql = "SELECT DISTINCT p.passenger_id, p.user_id, p.full_name, p.status, p.upcoming_flight, p.email, p.seat_class, p.relationship, " +
                    "COALESCE(u.phone, '') as phone, " +
                    "COALESCE(b.seat_no, 'N/A') as seat_number, " +
                    "COALESCE(b.booking_status, 'N/A') as booking_status " +
                    "FROM passengers p " +
                    "LEFT JOIN users u ON p.user_id = u.user_id " +
                    "LEFT JOIN flights f ON f.flight_code = p.upcoming_flight " +
                    "LEFT JOIN bookings b ON b.flight_id = f.flight_id AND p.email = b.email AND p.user_id = b.user_id " +
                    "WHERE p.status = ? AND p.passenger_id IS NOT NULL " +
                    "ORDER BY p.passenger_id, p.full_name";

        try (Connection conn = MySqlConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, status);

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
                        rs.getString("relationship"),
                        rs.getString("phone"),
                        rs.getString("seat_number"),
                        rs.getString("booking_status")
                    );
                    passengers.add(passenger);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error getting passengers by status: " + e.getMessage());
        }
        return passengers;
    }

    /**
     * Get passengers by seat class
     * @param seatClass The seat class to filter by
     * @return List of passengers in the specified seat class
     */
    public List<PassengerModel> getPassengersBySeatClass(String seatClass) {
        List<PassengerModel> passengers = new ArrayList<>();
        String sql = "SELECT DISTINCT p.passenger_id, p.user_id, p.full_name, p.status, p.upcoming_flight, p.email, p.seat_class, p.relationship, " +
                    "COALESCE(u.phone, '') as phone, " +
                    "COALESCE(b.seat_no, 'N/A') as seat_number, " +
                    "COALESCE(b.booking_status, 'N/A') as booking_status " +
                    "FROM passengers p " +
                    "LEFT JOIN users u ON p.user_id = u.user_id " +
                    "LEFT JOIN flights f ON f.flight_code = p.upcoming_flight " +
                    "LEFT JOIN bookings b ON b.flight_id = f.flight_id AND p.email = b.email AND p.user_id = b.user_id " +
                    "WHERE p.seat_class = ? AND p.passenger_id IS NOT NULL " +
                    "ORDER BY p.passenger_id, p.full_name";

        try (Connection conn = MySqlConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, seatClass);

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
                        rs.getString("relationship"),
                        rs.getString("phone"),
                        rs.getString("seat_number"),
                        rs.getString("booking_status")
                    );
                    passengers.add(passenger);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error getting passengers by seat class: " + e.getMessage());
        }
        return passengers;
    }

    /**
     * Update passenger records for a specific user when user details change
     * NOTE: This method should NOT overwrite individual passenger names or emails
     * Only updates shared account-level data like phone number
     * @param userId The user ID whose passenger records need updating
     * @param newFullName New full name (NOT USED - preserves individual names)
     * @param newEmail New email (NOT USED - preserves individual emails)
     * @return true if successful, false otherwise
     */
    public boolean updateAllPassengerRecordsForUser(int userId, String newFullName, String newEmail) {
        // DO NOT UPDATE individual passenger names or emails!
        // This would destroy the identity of people booked by someone else
        System.out.println("INFO: Not updating passenger records to preserve individual passenger identities for user ID: " + userId);
        return true; // Return success without making changes
    }

    /**
     * Get total number of passengers
     * @return Total passenger count
     */
    public int getTotalPassengersCount() {
        String sql = "SELECT COUNT(*) as total FROM passengers";
        
        try (Connection conn = MySqlConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error getting total passengers count: " + e.getMessage());
        }
        return 0;
    }

    /**
     * Get passenger by ID
     * @param passengerId The passenger ID
     * @return Passenger object or null if not found
     */
    public PassengerModel getPassengerById(int passengerId) {
        String sql = "SELECT p.passenger_id, p.user_id, p.full_name, p.status, p.upcoming_flight, p.email, p.seat_class, p.relationship, " +
                    "COALESCE(u.phone, '') as phone, " +
                    "COALESCE(b.seat_no, 'N/A') as seat_number, " +
                    "COALESCE(b.booking_status, 'N/A') as booking_status " +
                    "FROM passengers p " +
                    "LEFT JOIN users u ON p.user_id = u.user_id " +
                    "LEFT JOIN flights f ON f.flight_code = p.upcoming_flight " +
                    "LEFT JOIN bookings b ON b.flight_id = f.flight_id AND p.email = b.email AND p.user_id = b.user_id " +
                    "WHERE p.passenger_id = ? " +
                    "LIMIT 1";

        try (Connection conn = MySqlConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, passengerId);

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
                        rs.getString("relationship"),
                        rs.getString("phone"),
                        rs.getString("seat_number"),
                        rs.getString("booking_status")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error getting passenger by ID: " + e.getMessage());
        }
        return null;
    }

    /**
     * Get passengers with upcoming flights
     * @return List of passengers with upcoming flights
     */
    public List<PassengerModel> getPassengersWithUpcomingFlights() {
        List<PassengerModel> passengers = new ArrayList<>();
        String sql = "SELECT DISTINCT p.passenger_id, p.user_id, p.full_name, p.status, p.upcoming_flight, p.email, p.seat_class, p.relationship, " +
                    "COALESCE(u.phone, '') as phone, " +
                    "COALESCE(b.seat_no, 'N/A') as seat_number, " +
                    "COALESCE(b.booking_status, 'N/A') as booking_status " +
                    "FROM passengers p " +
                    "LEFT JOIN users u ON p.user_id = u.user_id " +
                    "LEFT JOIN flights f ON f.flight_code = p.upcoming_flight " +
                    "LEFT JOIN bookings b ON b.flight_id = f.flight_id AND p.email = b.email AND p.user_id = b.user_id " +
                    "WHERE p.upcoming_flight IS NOT NULL AND p.upcoming_flight != '' AND p.passenger_id IS NOT NULL " +
                    "ORDER BY p.passenger_id, p.full_name";

        try (Connection conn = MySqlConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                PassengerModel passenger = new PassengerModel(
                    rs.getInt("passenger_id"),
                    rs.getInt("user_id"),
                    rs.getString("full_name"),
                    rs.getString("status"),
                    rs.getString("upcoming_flight"),
                    rs.getString("email"),
                    rs.getString("seat_class"),
                    rs.getString("relationship"),
                    rs.getString("phone"),
                    rs.getString("seat_number"),
                    rs.getString("booking_status")
                );
                passengers.add(passenger);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error getting passengers with upcoming flights: " + e.getMessage());
        }
        return passengers;
    }

    public void markAsCheckedIn(int passengerId) {
        String sql = "UPDATE passengers SET status = 'Checked In' WHERE passenger_id = ?";
        try (Connection conn = MySqlConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, passengerId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Restore passenger data integrity by ensuring passenger records have their original individual details
     * This fixes the issue where passenger names were overwritten with account holder details
     * Only performs restoration when there's actual corruption detected
     * @return true if cleanup was successful
     */
    public boolean restorePassengerDataIntegrity() {
        try (Connection conn = MySqlConnection.getConnection()) {
            
            // Step 1: Only restore passenger names where they match account holder exactly (indicating corruption)
            // Don't touch legitimately different names (like family members booked by someone else)
            String restorePassengerSql = "UPDATE passengers p " +
                        "JOIN bookings b ON b.email = p.email AND b.user_id = p.user_id " +
                        "JOIN flights f ON f.flight_id = b.flight_id AND f.flight_code = p.upcoming_flight " +
                        "JOIN users u ON u.user_id = p.user_id " +
                        "SET p.full_name = b.full_name " +
                        "WHERE p.full_name = u.full_name " +  // Only restore if passenger name matches user name (corruption)
                        "AND p.full_name != b.full_name " +   // And it's different from booking name
                        "AND b.booking_status IN ('Confirmed', 'Cancelled')"; // Only consider valid bookings
            
            PreparedStatement restoreStmt = conn.prepareStatement(restorePassengerSql);
            int passengersRestored = restoreStmt.executeUpdate();
            
            // Step 2: Remove duplicate passenger records (keep only the one with lowest passenger_id)
            String removeDuplicatesSql = "DELETE p1 FROM passengers p1 " +
                        "INNER JOIN passengers p2 " +
                        "WHERE p1.passenger_id > p2.passenger_id " +
                        "AND p1.user_id = p2.user_id " +
                        "AND p1.email = p2.email " +
                        "AND p1.upcoming_flight = p2.upcoming_flight";
            
            PreparedStatement duplicateStmt = conn.prepareStatement(removeDuplicatesSql);
            int duplicatesRemoved = duplicateStmt.executeUpdate();
            
            if (passengersRestored > 0) {
                System.out.println("INTEGRITY RESTORE: Fixed " + passengersRestored + " corrupted passenger records (account holder name overwrite detected)");
            }
            if (duplicatesRemoved > 0) {
                System.out.println("INTEGRITY RESTORE: Removed " + duplicatesRemoved + " duplicate passenger records");
            }
            
            return true;
            
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error restoring passenger data integrity: " + e.getMessage());
            return false;
        }
    }
} 