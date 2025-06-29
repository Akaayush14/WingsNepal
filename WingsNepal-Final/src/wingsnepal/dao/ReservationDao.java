package wingsnepal.dao;

import database.MySqlConnection;
import wingsnepal.model.ReservationModel;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ReservationDao {

    /**
     * Retrieves all reservations from the database.
     * @return a list of all Reservation objects.
     */
    public List<ReservationModel> getAllReservations() {
        List<ReservationModel> reservations = new ArrayList<>();
        String sql = "SELECT * FROM reservations ORDER BY departure_datetime DESC";

        try (Connection conn = MySqlConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                ReservationModel res = new ReservationModel();
                res.setId(rs.getInt("id"));
                res.setReservationId(rs.getString("reservation_id"));
                res.setPassengerName(rs.getString("passenger_name"));
                res.setPassengerEmail(rs.getString("passenger_email"));
                res.setPassengerPhone(rs.getString("passenger_phone"));
                res.setFlightNumber(rs.getString("flight_number"));
                res.setRouteFrom(rs.getString("route_from"));
                res.setRouteTo(rs.getString("route_to"));
                res.setDepartureDateTime(rs.getObject("departure_datetime", LocalDateTime.class));
                res.setArrivalDateTime(rs.getObject("arrival_datetime", LocalDateTime.class));
                res.setSeatNumber(rs.getString("seat_number"));
                res.setSeatClass(rs.getString("seat_class"));
                res.setStatus(rs.getString("status"));
                res.setAmount(rs.getBigDecimal("amount"));
                reservations.add(res);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reservations;
    }

    /**
     * Adds a new reservation to the database.
     * @param reservation The Reservation object to add.
     * @return true if the operation was successful, false otherwise.
     */
    public boolean addReservation(ReservationModel reservation) {
        String sql = "INSERT INTO reservations (reservation_id, passenger_name, passenger_email, passenger_phone, " +
                     "flight_number, route_from, route_to, departure_datetime, arrival_datetime, " +
                     "seat_number, seat_class, status, amount) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = MySqlConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, reservation.getReservationId());
            stmt.setString(2, reservation.getPassengerName());
            stmt.setString(3, reservation.getPassengerEmail());
            stmt.setString(4, reservation.getPassengerPhone());
            stmt.setString(5, reservation.getFlightNumber());
            stmt.setString(6, reservation.getRouteFrom());
            stmt.setString(7, reservation.getRouteTo());
            stmt.setObject(8, reservation.getDepartureDateTime());
            stmt.setObject(9, reservation.getArrivalDateTime());
            stmt.setString(10, reservation.getSeatNumber());
            stmt.setString(11, reservation.getSeatClass());
            stmt.setString(12, reservation.getStatus());
            stmt.setBigDecimal(13, reservation.getAmount());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Updates an existing reservation in the database.
     * @param reservation The Reservation object with updated details.
     * @return true if the update was successful, false otherwise.
     */
    public boolean updateReservation(ReservationModel reservation) {
        String sql = "UPDATE reservations SET passenger_name = ?, passenger_email = ?, passenger_phone = ?, " +
                     "flight_number = ?, route_from = ?, route_to = ?, departure_datetime = ?, arrival_datetime = ?, " +
                     "seat_number = ?, seat_class = ?, status = ?, amount = ? WHERE id = ?";

        try (Connection conn = MySqlConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, reservation.getPassengerName());
            stmt.setString(2, reservation.getPassengerEmail());
            stmt.setString(3, reservation.getPassengerPhone());
            stmt.setString(4, reservation.getFlightNumber());
            stmt.setString(5, reservation.getRouteFrom());
            stmt.setString(6, reservation.getRouteTo());
            stmt.setObject(7, reservation.getDepartureDateTime());
            stmt.setObject(8, reservation.getArrivalDateTime());
            stmt.setString(9, reservation.getSeatNumber());
            stmt.setString(10, reservation.getSeatClass());
            stmt.setString(11, reservation.getStatus());
            stmt.setBigDecimal(12, reservation.getAmount());
            stmt.setInt(13, reservation.getId());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Deletes a reservation from the database by its ID.
     * @param reservationId The ID of the reservation to delete.
     * @return true if the deletion was successful, false otherwise.
     */
    public boolean deleteReservation(int reservationId) {
        String sql = "DELETE FROM reservations WHERE id = ?";
        try (Connection conn = MySqlConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, reservationId);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Update all reservation records for a specific user when user details change
     * This ensures data consistency across the system by matching email addresses
     * @param oldEmail The old email to find existing reservations
     * @param newFullName New full name to update
     * @param newEmail New email to update
     * @param newPhone New phone to update
     * @return true if successful, false otherwise
     */
    public boolean updateAllReservationRecordsForUser(String oldEmail, String newFullName, String newEmail, String newPhone) {
        String sql = "UPDATE reservations SET passenger_name = ?, passenger_email = ?, passenger_phone = ? WHERE passenger_email = ?";
        
        try (Connection conn = MySqlConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, newFullName);
            pstmt.setString(2, newEmail);
            pstmt.setString(3, newPhone);
            pstmt.setString(4, oldEmail);
            
            int rowsAffected = pstmt.executeUpdate();
            System.out.println("Updated " + rowsAffected + " reservation records for email: " + oldEmail);
            return true;
            
        } catch (SQLException e) {
            // Handle case where reservations table doesn't exist
            if (e.getMessage() != null && e.getMessage().contains("doesn't exist")) {
                System.out.println("Note: Reservations table doesn't exist - skipping reservation updates");
                return true; // Don't treat this as a failure since table is optional
            } else {
                e.printStackTrace();
                System.err.println("Error updating reservation records for user: " + e.getMessage());
                return false;
            }
        }
    }

    /**
     * Retrieves a single reservation by its primary key ID.
     * @param id The ID of the reservation.
     * @return A Reservation object or null if not found.
     */
    public ReservationModel getReservationById(int id) {
        String sql = "SELECT * FROM reservations WHERE id = ?";
        try (Connection conn = MySqlConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    ReservationModel res = new ReservationModel();
                    res.setId(rs.getInt("id"));
                    res.setReservationId(rs.getString("reservation_id"));
                    res.setPassengerName(rs.getString("passenger_name"));
                    res.setPassengerEmail(rs.getString("passenger_email"));
                    res.setPassengerPhone(rs.getString("passenger_phone"));
                    res.setFlightNumber(rs.getString("flight_number"));
                    res.setRouteFrom(rs.getString("route_from"));
                    res.setRouteTo(rs.getString("route_to"));
                    res.setDepartureDateTime(rs.getObject("departure_datetime", LocalDateTime.class));
                    res.setArrivalDateTime(rs.getObject("arrival_datetime", LocalDateTime.class));
                    res.setSeatNumber(rs.getString("seat_number"));
                    res.setSeatClass(rs.getString("seat_class"));
                    res.setStatus(rs.getString("status"));
                    res.setAmount(rs.getBigDecimal("amount"));
                    return res;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
} 