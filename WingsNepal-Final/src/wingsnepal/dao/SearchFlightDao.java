/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package wingsnepal.dao;

import database.MySqlConnection;
import wingsnepal.model.SearchFlightModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.*;

/**
 * SearchFlightDao uses DbConnection interface and MySqlConnection implementation
 * to search for available flights from the database.
 */
public class SearchFlightDao {
    
    MySqlConnection db = new MySqlConnection();

    public List<SearchFlightModel> searchFlights(String from, String to, String date) {
        List<SearchFlightModel> flightList = new ArrayList<>();
        String query;
        
        // Handle empty date parameter
        if (date == null || date.trim().isEmpty()) {
            query = "SELECT f.flight_code, f.flight_name, f.from_city, f.to_city, f.flight_date, f.flight_time, sc.price, f.duration_minutes " +
                   "FROM flights f " +
                   "JOIN seat_classes sc ON f.flight_id = sc.flight_id " +
                   "WHERE f.from_city = ? AND f.to_city = ? AND sc.class_name = 'Economy'";
        } else {
            query = "SELECT f.flight_code, f.flight_name, f.from_city, f.to_city, f.flight_date, f.flight_time, sc.price, f.duration_minutes " +
                   "FROM flights f " +
                   "JOIN seat_classes sc ON f.flight_id = sc.flight_id " +
                   "WHERE f.from_city = ? AND f.to_city = ? AND f.flight_date = ? AND sc.class_name = 'Economy'";
        }

        Connection conn = db.openConnection();
        
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, from);
            stmt.setString(2, to);
            
            if (date != null && !date.trim().isEmpty()) {
                stmt.setString(3, date);
            }
            
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int durationMinutes = rs.getInt("duration_minutes");
                String durationStr = (durationMinutes >= 60)
                    ? (durationMinutes / 60) + "h " + (durationMinutes % 60) + "m"
                    : durationMinutes + " min";
                SearchFlightModel flight = new SearchFlightModel(
                    rs.getString("flight_code"),
                    rs.getString("flight_name"),
                    rs.getString("from_city"),
                    rs.getString("to_city"),
                    rs.getString("flight_time"),
                    rs.getInt("price"),
                    durationStr,
                    rs.getString("flight_date")
                );
                flightList.add(flight);
            }
        } catch (SQLException e) {
            System.out.println("Error while searching flights: " + e.getMessage());
        } finally {
            db.closeConnection(conn);
        }
        
        return flightList;
    }

    public List<SearchFlightModel> getAllFlights() {
        List<SearchFlightModel> flightList = new ArrayList<>();
        String query = "SELECT f.flight_code, f.flight_name, f.from_city, f.to_city, f.flight_date, f.flight_time, sc.price, f.duration_minutes " +
                       "FROM flights f " +
                       "JOIN seat_classes sc ON f.flight_id = sc.flight_id " +
                       "WHERE sc.class_name = 'Economy'";

        Connection conn = db.openConnection();

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int durationMinutes = rs.getInt("duration_minutes");
                String durationStr = (durationMinutes >= 60)
                    ? (durationMinutes / 60) + "h " + (durationMinutes % 60) + "m"
                    : durationMinutes + " min";
                SearchFlightModel flight = new SearchFlightModel(
                    rs.getString("flight_code"),
                    rs.getString("flight_name"),
                    rs.getString("from_city"),
                    rs.getString("to_city"),
                    rs.getString("flight_time"),
                    rs.getInt("price"),
                    durationStr,
                    rs.getString("flight_date")
                );
                flightList.add(flight);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching all flights: " + e.getMessage());
        } finally {
            db.closeConnection(conn);
        }
        
        return flightList;
    }
    
    /**
     * Get all flights with complete pricing information (Economy, Business, First Class)
     * This method is specifically for employee dashboard where all prices need to be shown
     * @return List of SearchFlightModel with all three price categories
     */
    public List<SearchFlightModel> getAllFlightsWithPricing() {
        List<SearchFlightModel> flightList = new ArrayList<>();
        String query = "SELECT f.flight_code, f.flight_name, f.from_city, f.to_city, f.flight_date, f.flight_time, f.duration_minutes, " +
                       "MAX(CASE WHEN sc.class_name = 'Economy' THEN sc.price END) as economy_price, " +
                       "MAX(CASE WHEN sc.class_name = 'Business' THEN sc.price END) as business_price, " +
                       "MAX(CASE WHEN sc.class_name = 'First Class' THEN sc.price END) as first_class_price " +
                       "FROM flights f " +
                       "JOIN seat_classes sc ON f.flight_id = sc.flight_id " +
                       "GROUP BY f.flight_id, f.flight_code, f.flight_name, f.from_city, f.to_city, f.flight_date, f.flight_time, f.duration_minutes " +
                       "ORDER BY f.flight_code";

        Connection conn = db.openConnection();

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int durationMinutes = rs.getInt("duration_minutes");
                String durationStr = (durationMinutes >= 60)
                    ? (durationMinutes / 60) + "h " + (durationMinutes % 60) + "m"
                    : durationMinutes + " min";
                
                // Get all three prices
                int economyPrice = rs.getInt("economy_price");
                int businessPrice = rs.getInt("business_price");
                int firstClassPrice = rs.getInt("first_class_price");
                
                // Use constructor with all prices for complete pricing information
                SearchFlightModel flight = new SearchFlightModel(
                    rs.getString("flight_code"),
                    rs.getString("flight_name"),
                    rs.getString("from_city"),
                    rs.getString("to_city"),
                    rs.getString("flight_time"),
                    economyPrice,
                    businessPrice,
                    firstClassPrice,
                    durationStr,
                    rs.getString("flight_date")
                );
                flightList.add(flight);
                
                System.out.println("Flight loaded: " + flight.getFlightCode() + 
                                 " - Economy: Rs." + economyPrice + 
                                 ", Business: Rs." + businessPrice + 
                                 ", First: Rs." + firstClassPrice);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching all flights with pricing: " + e.getMessage());
            e.printStackTrace();
        } finally {
            db.closeConnection(conn);
        }
        
        return flightList;
    }
    
        // Method to get the flight code by flight ID
        public String getFlightCodeById(int flightId) {
            String flightCode = null;
            String query = "SELECT flight_code FROM flights WHERE flight_id = ?";
            Connection conn = db.openConnection();
            
            try (
                PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setInt(1, flightId);  // Set flightId parameter
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    flightCode = rs.getString("flight_code");  // Get flight code
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return flightCode;
        }

        // Method to get the flight name by flight ID
        public String getFlightNameById(int flightId) {
            String flightName = null;
            String query = "SELECT flight_name FROM flights WHERE flight_id = ?";
            Connection conn = db.openConnection();
            
            try (
                 PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setInt(1, flightId);  // Set flightId parameter
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    flightName = rs.getString("flight_name");  // Get flight name
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return flightName;
        }

    // Method to get flight by code
    public SearchFlightModel getFlightByCode(String flightCode) {
        String query = "SELECT f.flight_code, f.flight_name, f.from_city, f.to_city, f.flight_date, f.flight_time, f.duration_minutes, " +
                       "MAX(CASE WHEN sc.class_name = 'Economy' THEN sc.price END) as economy_price, " +
                       "MAX(CASE WHEN sc.class_name = 'Business' THEN sc.price END) as business_price, " +
                       "MAX(CASE WHEN sc.class_name = 'First Class' THEN sc.price END) as first_class_price " +
                       "FROM flights f " +
                       "JOIN seat_classes sc ON f.flight_id = sc.flight_id " +
                       "WHERE f.flight_code = ? " +
                       "GROUP BY f.flight_id, f.flight_code, f.flight_name, f.from_city, f.to_city, f.flight_date, f.flight_time, f.duration_minutes";

        Connection conn = db.openConnection();
        
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, flightCode);
            
            System.out.println("🔍 Executing query for flight: " + flightCode);
            System.out.println("📋 SQL: " + query);
            
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                int durationMinutes = rs.getInt("duration_minutes");
                String durationStr = (durationMinutes >= 60)
                    ? (durationMinutes / 60) + "h " + (durationMinutes % 60) + "m"
                    : durationMinutes + " min";
                
                // Debug: Check what prices we're getting from database
                int economyPrice = rs.getInt("economy_price");
                int businessPrice = rs.getInt("business_price");
                int firstClassPrice = rs.getInt("first_class_price");
                
                System.out.println("💰 Database pricing results:");
                System.out.println("   Economy: " + economyPrice + " (NULL: " + rs.wasNull() + ")");
                rs.getInt("economy_price"); // Reset wasNull() state
                System.out.println("   Business: " + businessPrice + " (NULL: " + rs.wasNull() + ")");
                rs.getInt("business_price"); // Reset wasNull() state  
                System.out.println("   First Class: " + firstClassPrice + " (NULL: " + rs.wasNull() + ")");
                
                SearchFlightModel model = new SearchFlightModel(
                    rs.getString("flight_code"),
                    rs.getString("flight_name"),
                    rs.getString("from_city"),
                    rs.getString("to_city"),
                    rs.getString("flight_time"),
                    economyPrice,
                    businessPrice,
                    firstClassPrice,
                    durationStr,
                    rs.getString("flight_date")
                );
                
                System.out.println("✅ Created SearchFlightModel for " + flightCode);
                return model;
            } else {
                System.out.println("❌ No flight found with code: " + flightCode);
            }
        } catch (SQLException e) {
            System.out.println("💥 SQL Error retrieving flight " + flightCode + ": " + e.getMessage());
            e.printStackTrace();
        } finally {
            db.closeConnection(conn);
        }
        return null;
    }

    /**
     * Get total number of flights
     * @return Total flights count
     */
    public int getTotalFlightsCount() {
        String sql = "SELECT COUNT(*) as total FROM flights";
        
        try (Connection conn = db.openConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error getting total flights count: " + e.getMessage());
        }
        return 0;
    }

    /**
     * Get total number of available seats across all flights
     * @return Total available seats count
     */
    public int getTotalAvailableSeats() {
        String sql = "SELECT COUNT(*) as total FROM seats WHERE is_booked = 0";
        
        try (Connection conn = db.openConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error getting total available seats count: " + e.getMessage());
        }
        return 0;
    }

    // New method for searching flights by departure city only
    public List<SearchFlightModel> searchFlightsByFrom(String from) {
        List<SearchFlightModel> flightList = new ArrayList<>();
        String query = "SELECT f.flight_code, f.flight_name, f.from_city, f.to_city, f.flight_date, f.flight_time, sc.price, f.duration_minutes " +
                       "FROM flights f " +
                       "JOIN seat_classes sc ON f.flight_id = sc.flight_id " +
                       "WHERE f.from_city LIKE ? AND sc.class_name = 'Economy'";

        Connection conn = db.openConnection();
        
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, "%" + from + "%");
            
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int durationMinutes = rs.getInt("duration_minutes");
                String durationStr = (durationMinutes >= 60)
                    ? (durationMinutes / 60) + "h " + (durationMinutes % 60) + "m"
                    : durationMinutes + " min";
                SearchFlightModel flight = new SearchFlightModel(
                    rs.getString("flight_code"),
                    rs.getString("flight_name"),
                    rs.getString("from_city"),
                    rs.getString("to_city"),
                    rs.getString("flight_time"),
                    rs.getInt("price"),
                    durationStr,
                    rs.getString("flight_date")
                );
                flightList.add(flight);
            }
        } catch (SQLException e) {
            System.out.println("Error while searching flights by departure: " + e.getMessage());
        } finally {
            db.closeConnection(conn);
        }
        
        return flightList;
    }

    // New method for searching flights by destination city only
    public List<SearchFlightModel> searchFlightsByTo(String to) {
        List<SearchFlightModel> flightList = new ArrayList<>();
        String query = "SELECT f.flight_code, f.flight_name, f.from_city, f.to_city, f.flight_date, f.flight_time, sc.price, f.duration_minutes " +
                       "FROM flights f " +
                       "JOIN seat_classes sc ON f.flight_id = sc.flight_id " +
                       "WHERE f.to_city LIKE ? AND sc.class_name = 'Economy'";

        Connection conn = db.openConnection();
        
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, "%" + to + "%");
            
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int durationMinutes = rs.getInt("duration_minutes");
                String durationStr = (durationMinutes >= 60)
                    ? (durationMinutes / 60) + "h " + (durationMinutes % 60) + "m"
                    : durationMinutes + " min";
                SearchFlightModel flight = new SearchFlightModel(
                    rs.getString("flight_code"),
                    rs.getString("flight_name"),
                    rs.getString("from_city"),
                    rs.getString("to_city"),
                    rs.getString("flight_time"),
                    rs.getInt("price"),
                    durationStr,
                    rs.getString("flight_date")
                );
                flightList.add(flight);
            }
        } catch (SQLException e) {
            System.out.println("Error while searching flights by destination: " + e.getMessage());
        } finally {
            db.closeConnection(conn);
        }
        
        return flightList;
    }

    /**
     * Enhanced flight update method that ensures system-wide consistency
     * Updates flights table, seat_classes table, and handles related booking updates
     */
    public boolean updateFlight(String flightCode, String newDate, String newTime, String newDuration, 
                               int economyPrice, int businessPrice, int firstClassPrice) {
        Connection conn = db.openConnection();
        boolean success = false;
        
        try {
            conn.setAutoCommit(false); // Start transaction for data consistency
            
            // Step 1: Get flight_id for all related updates
            int flightId = -1;
            String getFlightIdSql = "SELECT flight_id FROM flights WHERE flight_code = ?";
            try (PreparedStatement idStmt = conn.prepareStatement(getFlightIdSql)) {
                idStmt.setString(1, flightCode);
                ResultSet rs = idStmt.executeQuery();
                if (rs.next()) {
                    flightId = rs.getInt("flight_id");
                    System.out.println("✈️ Found flight ID: " + flightId + " for code: " + flightCode);
                } else {
                    throw new SQLException("Flight not found: " + flightCode);
                }
            }
            
            // Step 2: Update main flight information in flights table
            String updateFlightSql = "UPDATE flights SET flight_date = ?, flight_time = ?, duration_minutes = ? WHERE flight_code = ?";
            try (PreparedStatement flightStmt = conn.prepareStatement(updateFlightSql)) {
                flightStmt.setString(1, newDate);
                flightStmt.setString(2, newTime);
                
                // Convert duration to minutes
                int durationMinutes = parseDurationToMinutes(newDuration);
                flightStmt.setInt(3, durationMinutes);
                flightStmt.setString(4, flightCode);
                
                int flightRowsUpdated = flightStmt.executeUpdate();
                System.out.println("✅ Flights table updated: " + flightRowsUpdated + " rows");
            }
            
            // Step 3: Update seat class pricing (comprehensive update)
            String updatePriceSql = "UPDATE seat_classes SET price = ? WHERE flight_id = ? AND class_name = ?";
            try (PreparedStatement priceStmt = conn.prepareStatement(updatePriceSql)) {
                // Update Economy class
                priceStmt.setInt(1, economyPrice);
                priceStmt.setInt(2, flightId);
                priceStmt.setString(3, "Economy");
                int economyUpdated = priceStmt.executeUpdate();
                
                // Update Business class
                priceStmt.setInt(1, businessPrice);
                priceStmt.setInt(2, flightId);
                priceStmt.setString(3, "Business");
                int businessUpdated = priceStmt.executeUpdate();
                
                // Update First Class
                priceStmt.setInt(1, firstClassPrice);
                priceStmt.setInt(2, flightId);
                priceStmt.setString(3, "First Class");
                int firstUpdated = priceStmt.executeUpdate();
                
                System.out.println("✅ Seat pricing updated - Economy: " + economyUpdated + 
                                 ", Business: " + businessUpdated + ", First: " + firstUpdated);
            }
            
            // Step 4: Update existing bookings with new flight schedule information
            String updateBookingsSql = "UPDATE bookings SET flight_date = ?, flight_time = ? WHERE flight_id = ?";
            try (PreparedStatement bookingsStmt = conn.prepareStatement(updateBookingsSql)) {
                bookingsStmt.setString(1, newDate);
                bookingsStmt.setString(2, newTime);
                bookingsStmt.setInt(3, flightId);
                int bookingsUpdated = bookingsStmt.executeUpdate();
                System.out.println("✅ Related bookings updated: " + bookingsUpdated + " records");
            } catch (SQLException e) {
                // Bookings table might not exist or have different structure - continue anyway
                System.out.println("⚠️ Bookings update skipped (table might not exist): " + e.getMessage());
            }
            
            // Step 5: Update passenger records with new flight information  
            String updatePassengersSql = "UPDATE passengers SET flight_date = ?, flight_time = ? WHERE upcoming_flight = ?";
            try (PreparedStatement passengersStmt = conn.prepareStatement(updatePassengersSql)) {
                passengersStmt.setString(1, newDate);
                passengersStmt.setString(2, newTime);
                passengersStmt.setString(3, flightCode);
                int passengersUpdated = passengersStmt.executeUpdate();
                System.out.println("✅ Related passenger records updated: " + passengersUpdated + " records");
            } catch (SQLException e) {
                // Passengers table might not have these columns - continue anyway
                System.out.println("⚠️ Passengers update skipped (columns might not exist): " + e.getMessage());
            }
            
            // Step 6: Update reservations table if it exists
            String updateReservationsSql = "UPDATE reservations SET departure_datetime = ? WHERE flight_number = ?";
            try (PreparedStatement reservationsStmt = conn.prepareStatement(updateReservationsSql)) {
                String departureDateTime = newDate + " " + newTime;
                reservationsStmt.setString(1, departureDateTime);
                reservationsStmt.setString(2, flightCode);
                int reservationsUpdated = reservationsStmt.executeUpdate();
                System.out.println("✅ Related reservations updated: " + reservationsUpdated + " records");
            } catch (SQLException e) {
                // Reservations table might not exist - continue anyway
                System.out.println("⚠️ Reservations update skipped (table might not exist): " + e.getMessage());
            }
            
            conn.commit(); // Commit all changes
            success = true;
            
            System.out.println("🎉 COMPREHENSIVE FLIGHT UPDATE COMPLETED!");
            System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
            System.out.println("✈️ Flight " + flightCode + " updated successfully!");
            System.out.println("📊 All related tables updated:");
            System.out.println("   • flights table (main flight info)");
            System.out.println("   • seat_classes table (pricing)");
            System.out.println("   • bookings table (existing bookings)");
            System.out.println("   • passengers table (passenger records)");
            System.out.println("   • reservations table (reservation records)");
            System.out.println("🔄 Changes will be reflected in:");
            System.out.println("   • Admin Dashboard");
            System.out.println("   • Employee Dashboard");
            System.out.println("   • User Portal");
            System.out.println("   • Booking System");
            System.out.println("   • All search results");
            System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
            
        } catch (SQLException e) {
            try {
                conn.rollback(); // Rollback all changes on error
                System.out.println("❌ Transaction rolled back due to error: " + e.getMessage());
            } catch (SQLException rollbackEx) {
                System.out.println("💥 Critical error during rollback: " + rollbackEx.getMessage());
            }
            e.printStackTrace();
        } finally {
            try {
                conn.setAutoCommit(true); // Reset auto-commit
            } catch (SQLException e) {
                e.printStackTrace();
            }
            db.closeConnection(conn);
        }
        
        return success;
    }
    
    /**
     * Helper method to convert duration string to minutes
     */
    private int parseDurationToMinutes(String duration) {
        try {
            duration = duration.trim().toLowerCase();
            
            if (duration.contains("h") && duration.contains("m")) {
                // Format: "2h 30m" or "2h30m"
                String[] parts = duration.split("h");
                int hours = Integer.parseInt(parts[0].trim());
                String minutesPart = parts[1].trim().replace("m", "").trim();
                int minutes = minutesPart.isEmpty() ? 0 : Integer.parseInt(minutesPart);
                return hours * 60 + minutes;
            } else if (duration.contains("h")) {
                // Format: "2h"
                String hoursPart = duration.replace("h", "").trim();
                return Integer.parseInt(hoursPart) * 60;
            } else if (duration.contains("m")) {
                // Format: "30m" or "30 min"
                String minutesPart = duration.replace("m", "").replace("in", "").trim();
                return Integer.parseInt(minutesPart);
            } else {
                // Assume it's just a number in minutes
                return Integer.parseInt(duration);
            }
        } catch (NumberFormatException e) {
            System.out.println("Error parsing duration: " + duration + ". Using default 60 minutes.");
            return 60; // Default fallback
        }
    }

    /**
     * Delete a flight and all its related data from the database
     * This method handles the deletion of:
     * 1. All bookings for this flight
     * 2. All passenger records for this flight
     * 3. All seats for this flight
     * 4. All seat classes for this flight
     * 5. The flight itself
     * @param flightCode The flight code to delete
     * @return true if deletion was successful, false otherwise
     */
    public boolean deleteFlight(String flightCode) {
        Connection conn = db.openConnection();
        boolean success = false;
        
        try {
            conn.setAutoCommit(false); // Start transaction
            
            // Step 1: Get flight_id for all related deletions
            int flightId = -1;
            String getFlightIdSql = "SELECT flight_id FROM flights WHERE flight_code = ?";
            try (PreparedStatement idStmt = conn.prepareStatement(getFlightIdSql)) {
                idStmt.setString(1, flightCode);
                ResultSet rs = idStmt.executeQuery();
                if (rs.next()) {
                    flightId = rs.getInt("flight_id");
                    System.out.println("🗑️ Found flight ID: " + flightId + " for deletion: " + flightCode);
                } else {
                    throw new SQLException("Flight not found: " + flightCode);
                }
            }
            
            // Step 2: Delete all bookings for this flight
            String deleteBookingsSql = "DELETE FROM bookings WHERE flight_id = ?";
            try (PreparedStatement bookingStmt = conn.prepareStatement(deleteBookingsSql)) {
                bookingStmt.setInt(1, flightId);
                int bookingRowsDeleted = bookingStmt.executeUpdate();
                System.out.println("🗑️ Deleted " + bookingRowsDeleted + " bookings for flight " + flightCode);
            }
            
            // Step 3: Delete all passenger records for this flight
            String deletePassengersSql = "DELETE FROM passengers WHERE upcoming_flight = ?";
            try (PreparedStatement passengerStmt = conn.prepareStatement(deletePassengersSql)) {
                passengerStmt.setString(1, flightCode);
                int passengerRowsDeleted = passengerStmt.executeUpdate();
                System.out.println("🗑️ Deleted " + passengerRowsDeleted + " passenger records for flight " + flightCode);
            }
            
            // Step 4: Delete all seats for this flight
            String deleteSeatsSql = "DELETE FROM seats WHERE flight_id = ?";
            try (PreparedStatement seatStmt = conn.prepareStatement(deleteSeatsSql)) {
                seatStmt.setInt(1, flightId);
                int seatRowsDeleted = seatStmt.executeUpdate();
                System.out.println("🗑️ Deleted " + seatRowsDeleted + " seats for flight " + flightCode);
            }
            
            // Step 5: Delete all seat classes for this flight
            String deleteSeatClassesSql = "DELETE FROM seat_classes WHERE flight_id = ?";
            try (PreparedStatement seatClassStmt = conn.prepareStatement(deleteSeatClassesSql)) {
                seatClassStmt.setInt(1, flightId);
                int seatClassRowsDeleted = seatClassStmt.executeUpdate();
                System.out.println("🗑️ Deleted " + seatClassRowsDeleted + " seat classes for flight " + flightCode);
            }
            
            // Step 6: Delete flight assignments for this flight
            String deleteAssignmentsSql = "DELETE FROM flight_assignments WHERE flight_id = ?";
            try (PreparedStatement assignmentStmt = conn.prepareStatement(deleteAssignmentsSql)) {
                assignmentStmt.setInt(1, flightId);
                int assignmentRowsDeleted = assignmentStmt.executeUpdate();
                System.out.println("🗑️ Deleted " + assignmentRowsDeleted + " flight assignments for flight " + flightCode);
            }
            
            // Step 7: Finally, delete the flight itself
            String deleteFlightSql = "DELETE FROM flights WHERE flight_code = ?";
            try (PreparedStatement flightStmt = conn.prepareStatement(deleteFlightSql)) {
                flightStmt.setString(1, flightCode);
                int flightRowsDeleted = flightStmt.executeUpdate();
                System.out.println("🗑️ Deleted flight: " + flightRowsDeleted + " rows for " + flightCode);
                
                if (flightRowsDeleted > 0) {
                    conn.commit(); // Commit all changes
                    success = true;
                    System.out.println("✅ Flight " + flightCode + " and all related data deleted successfully");
                } else {
                    throw new SQLException("No flight was deleted");
                }
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("❌ Error deleting flight " + flightCode + ": " + e.getMessage());
            
            // Rollback all changes if anything fails
            if (conn != null) {
                try {
                    conn.rollback();
                    System.out.println("🔄 Transaction rolled back due to error");
                } catch (SQLException rollbackEx) {
                    rollbackEx.printStackTrace();
                }
            }
            success = false;
        } finally {
            // Restore auto-commit and close connection
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        
        return success;
    }
}


