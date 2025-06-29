package wingsnepal.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import database.MySqlConnection;
import wingsnepal.model.ManageBookingModel;
import wingsnepal.dao.SeatClassDao;

/**
 * Data Access Object for managing flight bookings with proper separation between
 * regular user bookings and walk-in customer bookings made by employees.
 * 
 * BOOKING VISIBILITY RULES:
 * ========================
 * 
 * ✅ WALK-IN BOOKINGS (made by employees for walk-in customers):
 *    - Visible in: Employee Dashboard → Bookings
 *    - Visible in: Admin Dashboard → Booking Management  
 *    - NOT visible in: User Portal → Manage Bookings
 *    - Method: getWalkInBookings()
 * 
 * ✅ REGULAR USER BOOKINGS (made by registered users):
 *    - Visible in: User Portal → Manage Bookings
 *    - Visible in: Admin Dashboard → Booking Management
 *    - NOT visible in: Employee Dashboard → Bookings
 *    - Method: getRegularUserBookingsByUserId()
 * 
 * @author WingsNepal Team
 */
public class ManageBookingDao {

    public ManageBookingDao() {
        // Constructor is now empty, no view needed
    }

    public List<ManageBookingModel> getBookingsByUserId(int userId) {
        List<ManageBookingModel> bookings = new ArrayList<>();
        // Updated query to include duration and booking_status from flights and bookings tables
        String query = "SELECT b.booking_id, b.user_id, b.flight_id, f.flight_code, f.duration, b.class_name, b.seat_no, b.tickets, b.date, b.payment_method, b.booking_status " +
                       "FROM bookings b " +
                       "JOIN flights f ON b.flight_id = f.flight_id " +
                       "WHERE b.user_id = ?";
        
        try (Connection conn = MySqlConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                ManageBookingModel model = new ManageBookingModel(
                    rs.getInt("booking_id"),
                    rs.getInt("user_id"),
                    rs.getInt("flight_id"),
                    rs.getString("flight_code"),
                    rs.getString("class_name"),
                    rs.getString("seat_no"),
                    rs.getInt("tickets"),
                    rs.getDate("date"),
                    rs.getString("payment_method")
                );
                // Set durationMinutes and bookingStatus from the result set
                model.setDurationMinutes(rs.getInt("duration"));
                model.setBookingStatus(rs.getString("booking_status"));
                bookings.add(model);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return bookings;
    }

    public boolean deleteBooking(int bookingId) {
        // Step 1: Get booking details before deletion to release the seat and clean up passenger record
        String getBookingSql = "SELECT b.user_id, b.flight_id, b.class_name, b.seat_no, b.full_name, b.email, f.flight_code " +
                              "FROM bookings b " +
                              "JOIN flights f ON b.flight_id = f.flight_id " +
                              "WHERE b.booking_id = ?";
        
        try (Connection conn = MySqlConnection.getConnection()) {
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
                    System.out.println("Releasing seat: flightId=" + flightId + ", seatClass=" + seatClass + ", seatNo=" + seatNo);
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

    public String getSeatNoByBookingId(int bookingId) {
        String query = "SELECT seat_no FROM bookings WHERE booking_id = ?";
        try (Connection conn = MySqlConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, bookingId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("seat_no");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void releaseSeat(String flightCode, String seatNo) {
        String flightIdQuery = "SELECT flight_id FROM flights WHERE flight_code = ?";
        String seatUpdateQuery = "UPDATE seats SET is_booked = 0 WHERE flight_id = ? AND LOWER(class_name) = LOWER(?) AND seat_no = ?";
        try (Connection conn = MySqlConnection.getConnection();
             PreparedStatement flightIdStmt = conn.prepareStatement(flightIdQuery)) {
            flightIdStmt.setString(1, flightCode);
            ResultSet rs = flightIdStmt.executeQuery();
            if (rs.next()) {
                int flightId = rs.getInt("flight_id");
                try (PreparedStatement seatUpdateStmt = conn.prepareStatement(seatUpdateQuery)) {
                    seatUpdateStmt.setInt(1, flightId);
                    seatUpdateStmt.setString(2, seatNo);
                    seatUpdateStmt.setString(3, seatNo);
                    seatUpdateStmt.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public String getSeatClassByBookingId(int bookingId) {
        String query = "SELECT class_name FROM bookings WHERE booking_id = ?";
        try (Connection conn = MySqlConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, bookingId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("class_name");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public int getFlightIdByBookingId(int bookingId) {
        String query = "SELECT flight_id FROM bookings WHERE booking_id = ?";
        try (Connection conn = MySqlConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, bookingId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("flight_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
    
    public void updateBooking(int bookingId, String newSeatNo, String newSeatClass, int newSeatId) {
        // Step 1: Get the current booking details to release the original seat
        String getBookingSql = "SELECT flight_id, class_name, seat_no FROM bookings WHERE booking_id = ?";
        
        try (Connection conn = MySqlConnection.getConnection()) {
            // First, get the current booking details
            PreparedStatement getStmt = conn.prepareStatement(getBookingSql);
            getStmt.setInt(1, bookingId);
            ResultSet rs = getStmt.executeQuery();
            
            if (rs.next()) {
                int flightId = rs.getInt("flight_id");
                String originalSeatClass = rs.getString("class_name");
                String originalSeatNo = rs.getString("seat_no");
                
                // Step 2: Release the original seat
                SeatClassDao seatDao = new SeatClassDao();
                System.out.println("Releasing original seat: flightId=" + flightId + ", seatClass=" + originalSeatClass + ", seatNo=" + originalSeatNo);
                seatDao.releaseSeat(flightId, originalSeatClass, originalSeatNo);
                
                // Step 3: Update the booking record
                String updateSql = "UPDATE bookings SET seat_no = ?, class_name = ?, seat_id = ? WHERE booking_id = ?";
                PreparedStatement updateStmt = conn.prepareStatement(updateSql);
                updateStmt.setString(1, newSeatNo);
                updateStmt.setString(2, newSeatClass);
                updateStmt.setInt(3, newSeatId);
                updateStmt.setInt(4, bookingId);
                updateStmt.executeUpdate();
                
                // Step 4: Mark the new seat as booked
                System.out.println("Marking new seat as booked: flightId=" + flightId + ", seatClass=" + newSeatClass + ", seatNo=" + newSeatNo);
                seatDao.markSeatAsBooked(flightId, newSeatClass, newSeatNo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Updates booking with both seat information and passenger details.
     * Used when editing bookings for someone else where passenger details can be changed.
     */
    public boolean updateBookingWithPassengerDetails(int bookingId, String newSeatNo, String newSeatClass, int newSeatId, String fullName, String email) {
        // Step 1: Get the current booking details to release the original seat
        String getBookingSql = "SELECT flight_id, class_name, seat_no, user_id FROM bookings WHERE booking_id = ?";
        
        try (Connection conn = MySqlConnection.getConnection()) {
            // First, get the current booking details
            PreparedStatement getStmt = conn.prepareStatement(getBookingSql);
            getStmt.setInt(1, bookingId);
            ResultSet rs = getStmt.executeQuery();
            
            if (rs.next()) {
                int flightId = rs.getInt("flight_id");
                String originalSeatClass = rs.getString("class_name");
                String originalSeatNo = rs.getString("seat_no");
                int userId = rs.getInt("user_id");
                
                // Step 2: Release the original seat
                SeatClassDao seatDao = new SeatClassDao();
                System.out.println("Releasing original seat: flightId=" + flightId + ", seatClass=" + originalSeatClass + ", seatNo=" + originalSeatNo);
                seatDao.releaseSeat(flightId, originalSeatClass, originalSeatNo);
                
                // Step 3: Update the booking record with both seat and passenger details
                String updateSql = "UPDATE bookings SET seat_no = ?, class_name = ?, seat_id = ?, full_name = ?, email = ? WHERE booking_id = ?";
                PreparedStatement updateStmt = conn.prepareStatement(updateSql);
                updateStmt.setString(1, newSeatNo);
                updateStmt.setString(2, newSeatClass);
                updateStmt.setInt(3, newSeatId);
                updateStmt.setString(4, fullName);
                updateStmt.setString(5, email);
                updateStmt.setInt(6, bookingId);
                updateStmt.executeUpdate();
                
                // Step 4: Mark the new seat as booked
                System.out.println("Marking new seat as booked: flightId=" + flightId + ", seatClass=" + newSeatClass + ", seatNo=" + newSeatNo);
                seatDao.markSeatAsBooked(flightId, newSeatClass, newSeatNo);
                
                // Step 5: Update corresponding passenger record if it exists
                // This ensures passenger table stays in sync with booking table
                String updatePassengerSql = "UPDATE passengers SET full_name = ?, email = ? WHERE user_id = ? AND email = (SELECT email FROM bookings WHERE booking_id = ? LIMIT 1)";
                PreparedStatement updatePassengerStmt = conn.prepareStatement(updatePassengerSql);
                updatePassengerStmt.setString(1, fullName);
                updatePassengerStmt.setString(2, email);
                updatePassengerStmt.setInt(3, userId);
                updatePassengerStmt.setInt(4, bookingId);
                int passengerRowsAffected = updatePassengerStmt.executeUpdate();
                
                System.out.println("Booking " + bookingId + " updated with passenger details. Passenger records updated: " + passengerRowsAffected);
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error updating booking with passenger details: " + e.getMessage());
        }
        return false;
    }

    public List<ManageBookingModel> getAllBookingsWithFlightInfo() {
        List<ManageBookingModel> bookings = new ArrayList<>();
        String query = "SELECT b.booking_id, f.flight_code, f.from_city, f.to_city, b.date, b.seat_no, b.class_name, b.booking_status " +
                       "FROM bookings b " +
                       "JOIN flights f ON b.flight_id = f.flight_id " +
                       "ORDER BY b.booking_id ASC";
        try (Connection conn = MySqlConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                ManageBookingModel model = new ManageBookingModel(
                    rs.getInt("booking_id"),
                    0, // userId not needed here
                    0, // flightId not needed here
                    rs.getString("flight_code"),
                    null, // className will be set below
                    rs.getString("seat_no"),
                    0, // tickets not needed here
                    rs.getDate("date"),
                    null // paymentMethod not needed here
                );
                model.setClassName(rs.getString("class_name"));
                model.setFromCity(rs.getString("from_city"));
                model.setToCity(rs.getString("to_city"));
                model.setBookingStatus(rs.getString("booking_status"));
                bookings.add(model);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookings;
    }

    public ManageBookingModel getBookingDetailsById(int bookingId) {
        String query = "SELECT b.booking_id, b.user_id, b.flight_id, f.flight_code, f.flight_name, b.class_name, b.seat_no, b.tickets, b.date, b.payment_method, b.full_name, b.email " +
                       "FROM bookings b " +
                       "JOIN flights f ON b.flight_id = f.flight_id " +
                       "WHERE b.booking_id = ?";
        try (Connection conn = MySqlConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, bookingId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new ManageBookingModel(
                    rs.getInt("booking_id"),
                    rs.getInt("user_id"),
                    rs.getInt("flight_id"),
                    rs.getString("flight_code"),
                    rs.getString("flight_name"),
                    rs.getString("class_name"),
                    rs.getString("seat_no"),
                    rs.getInt("tickets"),
                    rs.getDate("date"),
                    rs.getString("payment_method"),
                    rs.getString("full_name"),
                    rs.getString("email")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Update booking records for a specific user when user details change
     * NOTE: This method should NOT overwrite individual booking names or emails
     * Only updates shared account-level data if needed
     * @param userId The user ID whose booking records need updating
     * @param newFullName New full name (NOT USED - preserves individual names)
     * @param newEmail New email (NOT USED - preserves individual emails)
     * @return true if successful, false otherwise  
     */
    public boolean updateAllBookingRecordsForUser(int userId, String newFullName, String newEmail) {
        // DO NOT UPDATE individual booking names or emails!
        // This would destroy the identity of people booked by someone else
        System.out.println("INFO: Not updating booking records to preserve individual passenger identities for user ID: " + userId);
        return true; // Return success without making changes
    }

    /**
     * Get total number of reservations/bookings
     * @return Total reservations count
     */
    public int getTotalReservations() {
        String sql = "SELECT COUNT(*) as total FROM bookings";
        
        try (Connection conn = MySqlConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error getting total reservations count: " + e.getMessage());
        }
        return 0;
    }

    public void deleteAllBookingsForPassenger(int passengerId, String email) {
        String getBookingsSql = "SELECT booking_id FROM bookings WHERE full_name = (SELECT full_name FROM passengers WHERE passenger_id = ?) AND email = ?";
        try (Connection conn = MySqlConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(getBookingsSql)) {
            stmt.setInt(1, passengerId);
            stmt.setString(2, email);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int bookingId = rs.getInt("booking_id");
                deleteBooking(bookingId); // This will also free the seat
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Suspend all bookings for a user when their account is deactivated
     * Releases all confirmed seats back to available inventory
     * @param userId The user ID whose bookings should be suspended
     */
    public void suspendBookingsForUser(int userId) {
        // Step 1: Get all confirmed bookings to release their seats
        String getBookingsSql = "SELECT flight_id, class_name, seat_no FROM bookings " +
                               "WHERE user_id = ? AND (booking_status IS NULL OR booking_status = 'Confirmed')";
        
        try (Connection conn = MySqlConnection.getConnection()) {
            PreparedStatement getStmt = conn.prepareStatement(getBookingsSql);
            getStmt.setInt(1, userId);
            ResultSet rs = getStmt.executeQuery();
            
            SeatClassDao seatDao = new SeatClassDao();
            int seatsReleased = 0;
            
            // Release all confirmed seats
            while (rs.next()) {
                int flightId = rs.getInt("flight_id");
                String seatClass = rs.getString("class_name");
                String seatNo = rs.getString("seat_no");
                
                System.out.println("USER SUSPENSION: Releasing seat - flightId=" + flightId + 
                                 ", seatClass=" + seatClass + ", seatNo=" + seatNo);
                seatDao.releaseSeat(flightId, seatClass, seatNo);
                seatsReleased++;
            }
            
            // Step 2: Update booking statuses to Cancelled
            String updateSql = "UPDATE bookings SET booking_status = 'Cancelled' WHERE user_id = ? AND (booking_status IS NULL OR booking_status = 'Confirmed')";
            PreparedStatement updateStmt = conn.prepareStatement(updateSql);
            updateStmt.setInt(1, userId);
            int rowsAffected = updateStmt.executeUpdate();
            
            System.out.println("User " + userId + " account suspended: " + rowsAffected + " bookings cancelled, " + seatsReleased + " seats released");
        } catch (SQLException e) {
            System.err.println("Error suspending bookings for user ID " + userId + ": " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Reactivate suspended bookings for a user when their account is reactivated
     * Only reactivates bookings where seats are still available
     * @param userId The user ID whose bookings should be reactivated
     */
    public void reactivateBookingsForUser(int userId) {
        // Step 1: Get all cancelled bookings for this user
        String getBookingsSql = "SELECT booking_id, flight_id, class_name, seat_no FROM bookings " +
                               "WHERE user_id = ? AND booking_status = 'Cancelled'";
        
        try (Connection conn = MySqlConnection.getConnection()) {
            PreparedStatement getStmt = conn.prepareStatement(getBookingsSql);
            getStmt.setInt(1, userId);
            ResultSet rs = getStmt.executeQuery();
            
            SeatClassDao seatDao = new SeatClassDao();
            int seatsRebooked = 0;
            int bookingsReactivated = 0;
            int seatsUnavailable = 0;
            
            while (rs.next()) {
                int bookingId = rs.getInt("booking_id");
                int flightId = rs.getInt("flight_id");
                String seatClass = rs.getString("class_name");
                String seatNo = rs.getString("seat_no");
                
                // Step 2: Check if seat is still available
                String checkSeatSql = "SELECT is_booked FROM seats WHERE flight_id = ? AND class_name = ? AND seat_no = ?";
                PreparedStatement checkStmt = conn.prepareStatement(checkSeatSql);
                checkStmt.setInt(1, flightId);
                checkStmt.setString(2, seatClass);
                checkStmt.setString(3, seatNo);
                ResultSet seatRs = checkStmt.executeQuery();
                
                if (seatRs.next() && seatRs.getInt("is_booked") == 0) {
                    // Seat is available - reactivate booking and mark seat as booked
                    String updateBookingSql = "UPDATE bookings SET booking_status = 'Confirmed' WHERE booking_id = ?";
                    PreparedStatement updateStmt = conn.prepareStatement(updateBookingSql);
                    updateStmt.setInt(1, bookingId);
                    updateStmt.executeUpdate();
                    
                    seatDao.markSeatAsBooked(flightId, seatClass, seatNo);
                    System.out.println("USER REACTIVATION: Booking " + bookingId + " reactivated, seat " + seatNo + " re-booked");
                    seatsRebooked++;
                    bookingsReactivated++;
                } else {
                    // Seat is no longer available - keep booking cancelled
                    System.out.println("USER REACTIVATION: Booking " + bookingId + " cannot be reactivated - seat " + seatNo + " is no longer available");
                    seatsUnavailable++;
                }
            }
            
            System.out.println("User " + userId + " account reactivated: " + bookingsReactivated + " bookings confirmed, " + 
                             seatsRebooked + " seats re-booked, " + seatsUnavailable + " seats unavailable");
            
        } catch (SQLException e) {
            System.err.println("Error reactivating bookings for user ID " + userId + ": " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Set default booking status for existing bookings that don't have a status
     */
    public void setDefaultBookingStatus() {
        String sql = "UPDATE bookings SET booking_status = 'Confirmed' WHERE booking_status IS NULL OR booking_status = ''";
        try (Connection conn = MySqlConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            int rowsAffected = stmt.executeUpdate();
            System.out.println("Set default status for " + rowsAffected + " bookings");
        } catch (SQLException e) {
            System.err.println("Error setting default booking status: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Updates the booking status for a specific user, flight, and seat with seat management
     * @param userId The user ID
     * @param flightCode The flight code (e.g., "BA101")
     * @param seatNumber The specific seat number (e.g., "A1")
     * @param newStatus The new booking status ("Confirmed" or "Cancelled")
     * @return true if the update was successful, false otherwise
     */
    public boolean updateBookingStatus(int userId, String flightCode, String seatNumber, String newStatus) {
        // Step 1: Get current booking details including current status
        String getBookingSql = "SELECT b.booking_status, b.flight_id, b.class_name " +
                              "FROM bookings b " +
                              "JOIN flights f ON b.flight_id = f.flight_id " +
                              "WHERE b.user_id = ? AND f.flight_code = ? AND b.seat_no = ?";
        
        try (Connection conn = MySqlConnection.getConnection()) {
            PreparedStatement getStmt = conn.prepareStatement(getBookingSql);
            getStmt.setInt(1, userId);
            getStmt.setString(2, flightCode);
            getStmt.setString(3, seatNumber);
            ResultSet rs = getStmt.executeQuery();
            
            if (rs.next()) {
                String currentStatus = rs.getString("booking_status");
                int flightId = rs.getInt("flight_id");
                String seatClass = rs.getString("class_name");
                
                // Step 2: Update the booking status
                String updateSql = "UPDATE bookings b " +
                                 "JOIN flights f ON b.flight_id = f.flight_id " +
                                 "SET b.booking_status = ? " +
                                 "WHERE b.user_id = ? AND f.flight_code = ? AND b.seat_no = ?";
                
                PreparedStatement updateStmt = conn.prepareStatement(updateSql);
                updateStmt.setString(1, newStatus);
                updateStmt.setInt(2, userId);
                updateStmt.setString(3, flightCode);
                updateStmt.setString(4, seatNumber);
                int rowsAffected = updateStmt.executeUpdate();
                
                // Step 3: Handle seat management based on status change
                if (rowsAffected > 0) {
                    SeatClassDao seatDao = new SeatClassDao();
                    
                    // If changing from Confirmed to Cancelled: Release the seat
                    if ("Confirmed".equals(currentStatus) && "Cancelled".equals(newStatus)) {
                        System.out.println("SEAT RELEASE: Releasing seat due to cancellation - flightId=" + flightId + 
                                         ", seatClass=" + seatClass + ", seatNo=" + seatNumber);
                        seatDao.releaseSeat(flightId, seatClass, seatNumber);
                    }
                    // If changing from Cancelled to Confirmed: Book the seat
                    else if ("Cancelled".equals(currentStatus) && "Confirmed".equals(newStatus)) {
                        System.out.println("SEAT BOOKING: Marking seat as booked due to reactivation - flightId=" + flightId + 
                                         ", seatClass=" + seatClass + ", seatNo=" + seatNumber);
                        seatDao.markSeatAsBooked(flightId, seatClass, seatNumber);
                    }
                    
                    System.out.println("Updated booking status from '" + currentStatus + "' to '" + newStatus + 
                                     "' for user " + userId + ", flight " + flightCode + ", seat " + seatNumber);
                    return true;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error updating booking status: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Verifies seat management integrity - checks for mismatches between booking status and seat availability
     * This method can be used for testing and system maintenance
     * @return String report of any inconsistencies found
     */
    public String verifySeatIntegrity() {
        StringBuilder report = new StringBuilder("SEAT INTEGRITY VERIFICATION REPORT:\n");
        int confirmedWithReleasedSeats = 0;
        int cancelledWithBookedSeats = 0;
        int totalChecked = 0;
        
        String sql = "SELECT b.booking_id, b.booking_status, b.flight_id, b.class_name, b.seat_no, " +
                    "s.is_booked " +
                    "FROM bookings b " +
                    "JOIN seats s ON b.flight_id = s.flight_id AND b.class_name = s.class_name AND b.seat_no = s.seat_no";
        
        try (Connection conn = MySqlConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                totalChecked++;
                int bookingId = rs.getInt("booking_id");
                String bookingStatus = rs.getString("booking_status");
                int flightId = rs.getInt("flight_id");
                String seatClass = rs.getString("class_name");
                String seatNo = rs.getString("seat_no");
                boolean seatIsBooked = rs.getBoolean("is_booked");
                
                // Check for inconsistencies
                if ("Confirmed".equals(bookingStatus) && !seatIsBooked) {
                    report.append("❌ MISMATCH: Booking ").append(bookingId)
                          .append(" is Confirmed but seat ").append(seatNo)
                          .append(" (flight ").append(flightId).append(") is marked as available\n");
                    confirmedWithReleasedSeats++;
                } else if ("Cancelled".equals(bookingStatus) && seatIsBooked) {
                    report.append("❌ MISMATCH: Booking ").append(bookingId)
                          .append(" is Cancelled but seat ").append(seatNo)
                          .append(" (flight ").append(flightId).append(") is still marked as booked\n");
                    cancelledWithBookedSeats++;
                }
            }
            
        } catch (SQLException e) {
            report.append("❌ ERROR during verification: ").append(e.getMessage()).append("\n");
            e.printStackTrace();
        }
        
        report.append("\n=== SUMMARY ===\n");
        report.append("Total bookings checked: ").append(totalChecked).append("\n");
        report.append("Confirmed bookings with released seats: ").append(confirmedWithReleasedSeats).append("\n");
        report.append("Cancelled bookings with booked seats: ").append(cancelledWithBookedSeats).append("\n");
        
        if (confirmedWithReleasedSeats == 0 && cancelledWithBookedSeats == 0) {
            report.append("✅ RESULT: Seat management integrity is PERFECT!\n");
        } else {
            report.append("❌ RESULT: Found ").append(confirmedWithReleasedSeats + cancelledWithBookedSeats)
                  .append(" integrity issues that need fixing.\n");
        }
        
        System.out.println(report.toString());
        return report.toString();
    }

    /**
     * Gets the total count of all bookings in the database
     * @return total number of bookings
     */
    public int getTotalBookingsCount() {
        String query = "SELECT COUNT(*) as total FROM bookings";
        try (Connection conn = MySqlConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (SQLException e) {
            System.err.println("Error getting total bookings count: " + e.getMessage());
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Get all walk-in customer bookings (bookings where passenger details differ from account holder)
     * These are bookings made by employees for walk-in customers
     */
    public List<ManageBookingModel> getWalkInBookings() {
        List<ManageBookingModel> walkInBookings = new ArrayList<>();
        String query = "SELECT b.booking_id, b.user_id, b.flight_id, f.flight_code, f.from_city, f.to_city, " +
                      "b.class_name, b.seat_no, b.date, b.payment_method, b.booking_status, " +
                      "b.full_name, b.email " +
                      "FROM bookings b " +
                      "JOIN flights f ON b.flight_id = f.flight_id " +
                      "JOIN users u ON b.user_id = u.user_id " +
                      "WHERE u.role = 'Employee' " + // Bookings created by employees
                      "ORDER BY b.booking_id ASC";
        
        try (Connection conn = MySqlConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                ManageBookingModel booking = new ManageBookingModel(
                    rs.getInt("booking_id"),
                    rs.getInt("user_id"),
                    rs.getInt("flight_id"),
                    rs.getString("flight_code"),
                    rs.getString("class_name"),
                    rs.getString("seat_no"),
                    0, // tickets not needed
                    rs.getDate("date"),
                    rs.getString("payment_method")
                );
                
                booking.setFromCity(rs.getString("from_city"));
                booking.setToCity(rs.getString("to_city"));
                booking.setBookingStatus(rs.getString("booking_status"));
                booking.setPassengerName(rs.getString("full_name"));
                booking.setPassengerEmail(rs.getString("email"));
                
                walkInBookings.add(booking);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return walkInBookings;
    }
    
    /**
     * Get regular user bookings (exclude walk-in customer bookings)
     * These are bookings made by regular users, not employees for walk-in customers
     */
    public List<ManageBookingModel> getRegularUserBookings() {
        List<ManageBookingModel> userBookings = new ArrayList<>();
        String query = "SELECT b.booking_id, b.user_id, b.flight_id, f.flight_code, f.from_city, f.to_city, " +
                      "b.class_name, b.seat_no, b.date, b.payment_method, b.booking_status " +
                      "FROM bookings b " +
                      "JOIN flights f ON b.flight_id = f.flight_id " +
                      "JOIN users u ON b.user_id = u.user_id " +
                      "WHERE u.role != 'Employee' " + // Exclude employee bookings (walk-ins)
                      "ORDER BY b.booking_id ASC";
        
        try (Connection conn = MySqlConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                ManageBookingModel booking = new ManageBookingModel(
                    rs.getInt("booking_id"),
                    rs.getInt("user_id"),
                    rs.getInt("flight_id"),
                    rs.getString("flight_code"),
                    rs.getString("class_name"),
                    rs.getString("seat_no"),
                    0, // tickets not needed
                    rs.getDate("date"),
                    rs.getString("payment_method")
                );
                
                booking.setFromCity(rs.getString("from_city"));
                booking.setToCity(rs.getString("to_city"));
                booking.setBookingStatus(rs.getString("booking_status"));
                
                userBookings.add(booking);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return userBookings;
    }
    
    /**
     * Update walk-in customer booking details
     */
    public boolean updateWalkInBooking(int bookingId, String seatNo, String seatClass, int seatId, 
                                      String passengerName, String passengerEmail) {
        String updateQuery = "UPDATE bookings SET seat_no = ?, class_name = ?, seat_id = ?, " +
                           "full_name = ?, email = ? WHERE booking_id = ?";
        
        try (Connection conn = MySqlConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(updateQuery)) {
            
            stmt.setString(1, seatNo);
            stmt.setString(2, seatClass);
            stmt.setInt(3, seatId);
            stmt.setString(4, passengerName);
            stmt.setString(5, passengerEmail);
            stmt.setInt(6, bookingId);
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Delete walk-in customer booking and free up the seat
     */
    public boolean deleteWalkInBooking(int bookingId) {
        // First get booking details to free up the seat and delete passenger record
        String selectQuery = "SELECT b.flight_id, b.class_name, b.seat_no, b.user_id, b.email, f.flight_code " +
                           "FROM bookings b " +
                           "JOIN flights f ON b.flight_id = f.flight_id " +
                           "WHERE b.booking_id = ?";
        
        String deleteBookingQuery = "DELETE FROM bookings WHERE booking_id = ?";
        String deletePassengerQuery = "DELETE FROM passengers WHERE user_id = ? AND email = ? AND upcoming_flight = ?";
        
        try (Connection conn = MySqlConnection.getConnection()) {
            conn.setAutoCommit(false);
            
            int flightId = 0;
            String seatClass = "";
            String seatNo = "";
            int userId = -1;
            String email = null;
            String flightCode = null;
            
            // Get booking details
            try (PreparedStatement selectStmt = conn.prepareStatement(selectQuery)) {
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
            try (PreparedStatement deleteBookingStmt = conn.prepareStatement(deleteBookingQuery)) {
                deleteBookingStmt.setInt(1, bookingId);
                int bookingRowsDeleted = deleteBookingStmt.executeUpdate();
                
                if (bookingRowsDeleted > 0) {
                    // Delete corresponding passenger record
                    try (PreparedStatement deletePassengerStmt = conn.prepareStatement(deletePassengerQuery)) {
                        deletePassengerStmt.setInt(1, userId);
                        deletePassengerStmt.setString(2, email);
                        deletePassengerStmt.setString(3, flightCode);
                        int passengerRowsDeleted = deletePassengerStmt.executeUpdate();
                        
                        System.out.println("✅ MANAGE BOOKING DELETION: Booking " + bookingId + " deleted. " +
                                         "Passenger records removed: " + passengerRowsDeleted);
                    }
                    
                    // Free up the seat
                    if (flightId > 0) {
                        SeatClassDao seatDao = new SeatClassDao();
                        seatDao.releaseSeat(flightId, seatClass, seatNo);
                        System.out.println("✅ MANAGE BOOKING DELETION: Seat " + seatClass + " " + seatNo + " released");
                    }
                    
                    conn.commit();
                    return true;
                }
            }
            
            conn.rollback();
            return false;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Get bookings for regular users only (exclude walk-in bookings made by employees)
     * This method ensures that users only see their own genuine bookings, not walk-in customer bookings
     * that might share the same user_id
     * @param userId The regular user's ID
     * @return List of user's own bookings (excluding any walk-in bookings)
     */
    public List<ManageBookingModel> getRegularUserBookingsByUserId(int userId) {
        List<ManageBookingModel> bookings = new ArrayList<>();
        String query = "SELECT b.booking_id, b.user_id, b.flight_id, f.flight_code, f.duration, b.class_name, b.seat_no, b.tickets, b.date, b.payment_method, b.booking_status " +
                       "FROM bookings b " +
                       "JOIN flights f ON b.flight_id = f.flight_id " +
                       "JOIN users u ON b.user_id = u.user_id " +
                       "WHERE b.user_id = ? AND u.role != 'Employee'"; // Exclude employee-made bookings
        
        try (Connection conn = MySqlConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                ManageBookingModel model = new ManageBookingModel(
                    rs.getInt("booking_id"),
                    rs.getInt("user_id"),
                    rs.getInt("flight_id"),
                    rs.getString("flight_code"),
                    rs.getString("class_name"),
                    rs.getString("seat_no"),
                    rs.getInt("tickets"),
                    rs.getDate("date"),
                    rs.getString("payment_method")
                );
                // Set durationMinutes and bookingStatus from the result set
                model.setDurationMinutes(rs.getInt("duration"));
                model.setBookingStatus(rs.getString("booking_status"));
                bookings.add(model);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return bookings;
    }
}
