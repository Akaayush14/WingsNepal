/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package wingsnepal.dao;

import java.sql.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import wingsnepal.database.DbConnection;
import wingsnepal.database.MySqlConnection;

/**
 *
 * @author Aayush Kharel
 */
public class SeatClassDao {

    public int getPriceByFlightAndClass(int flightId, String seatClass) {
        int price = -1;
        String sql = "SELECT price FROM seat_classes WHERE flight_id = ? AND class_name = ?";

        DbConnection db = new MySqlConnection();  // instantiate your connection provider

        try (Connection conn = db.openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, flightId);
            stmt.setString(2, seatClass);
            
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                price = rs.getInt("price");
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return price;
    }
    public int getSeatIdByFlightAndClass(int flightId, String className){
        String query = "SELECT seat_id FROM seats WHERE flight_id = ? AND class_name = ? AND is_booked = 0 LIMIT 1";
        DbConnection db = new MySqlConnection();

        try (Connection conn = db.openConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, flightId);
            stmt.setString(2, className);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("seat_id");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }
    
    public List<String> getAvailableSeats(int flightId, String seatClass) {
        List<String> availableSeats = new ArrayList<>();
        String query = "SELECT seat_no FROM seats WHERE flight_id = ? AND class_name = ? AND is_booked = 0";
        DbConnection db = new MySqlConnection();
        
        try (Connection conn = db.openConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, flightId);
            ps.setString(2, seatClass);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                availableSeats.add(rs.getString("seat_no"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return availableSeats;
    }

    public int getFlightIdByCode(String flightCode) {    
        int flightId = -1;
        DbConnection db = new MySqlConnection();
        Connection conn = db.openConnection();
        try{
            String sql = "SELECT flight_id FROM flights WHERE flight_code = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, flightCode);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                flightId = rs.getInt("flight_id");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flightId;
    }  
    
    //To marl seat as booked:
    public void markSeatAsBooked(int flightId, String seatClass, String seatNo) {
        String sql = "UPDATE seats SET is_booked = 1 WHERE flight_id = ? AND class_name = ? AND seat_no = ?";
        DbConnection db = new MySqlConnection();

        try (Connection conn = db.openConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, flightId);
            stmt.setString(2, seatClass);
            stmt.setString(3, seatNo);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    //To auto-generate seat numbers:
    public void generateSeatsForFlight(int flightId, String className, int seatCount) {
        DbConnection db = new MySqlConnection();
        String sql = "INSERT INTO seats (flight_id, class_name, seat_no, is_booked) VALUES (?, ?, ?, 0)";

        try (Connection conn = db.openConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            char row = 'A'; // You can use this for row label (e.g., A1, A2...)

            for (int i = 1; i <= seatCount; i++) {
                String seatNo = i + "" + row;  // e.g., "1A", "2A"
                stmt.setInt(1, flightId);
                stmt.setString(2, className);
                stmt.setString(3, seatNo);
                stmt.addBatch();
            }

            stmt.executeBatch();  // Fast insertion of all seats at once
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
        // Simulating fetching available seats based on seat class
    public List<String> getAvailableSeatsForClass(String seatClass) {
        List<String> availableSeats = new ArrayList<>();

        // Sample data for available seats based on class
        switch (seatClass) {
            case "Economy":
                availableSeats.add("A1");
                availableSeats.add("A2");
                availableSeats.add("A3");
                break;
            case "Business":
                availableSeats.add("B1");
                availableSeats.add("B2");
                availableSeats.add("B3");
                break;
            case "First Class":
                availableSeats.add("C1");
                availableSeats.add("C2");
                availableSeats.add("C3");
                break;
            default:
                break;
        }

        return availableSeats;
    }

    // Fetch price for selected seat class
    public int getPriceForClass(String class_name) {
        switch (class_name) {
            case "Economy":
                return 5000;  // Sample price for Economy
            case "Business":
                return 10000;  // Sample price for Business
            case "First Class":
                return 15000;  // Sample price for First Class
            default:
                return 0;
        }
    }
    
    public boolean isSeatAvailable(int seatId) {
        String checkSeatSql = "SELECT is_booked FROM seats WHERE seat_id = ?";
        DbConnection db = new MySqlConnection();
        try (Connection conn = db.openConnection();
             PreparedStatement stmt = conn.prepareStatement(checkSeatSql)) {

            stmt.setInt(1, seatId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return !rs.getBoolean("is_booked"); // true if available, false if booked
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // If seat status couldn't be determined
    }

}




