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
import database.DbConnection;
import database.MySqlConnection;

/**
 *
 * @author Aayush Kharel
 */
public class SeatClassDao {

    public int getPriceByFlightAndClass(int flightId, String seatClass) {
        int price = -1;
        String sql = "SELECT price FROM seat_classes WHERE flight_id = ? AND class_name = ?";

        System.out.println("SeatClassDao.getPriceByFlightAndClass - Flight ID: " + flightId + ", Seat Class: " + seatClass);
        System.out.println("SQL Query: " + sql);

        DbConnection db = new MySqlConnection();  // instantiate your connection provider

        try (Connection conn = db.openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, flightId);
            stmt.setString(2, seatClass);
            
            System.out.println("Executing query with parameters - Flight ID: " + flightId + ", Seat Class: '" + seatClass + "'");
            
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                price = rs.getInt("price");
                System.out.println("Price found: " + price);
            } else {
                System.out.println("No price found for Flight ID: " + flightId + ", Seat Class: " + seatClass);
            }

        } catch (SQLException ex) {
            System.out.println("SQL Exception in getPriceByFlightAndClass: " + ex.getMessage());
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
    
    public List<String> getAvailableSeatsByClass(int flightId, String seatClass) {
        List<String> availableSeats = new ArrayList<>();
        String sql = "SELECT seat_no FROM seats WHERE flight_id = ? AND class_name = ? AND is_booked = 0 ORDER BY CAST(SUBSTRING(seat_no, 2) AS UNSIGNED)";

        DbConnection db = new MySqlConnection();
        try (Connection conn = db.openConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, flightId);
            stmt.setString(2, seatClass);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
            availableSeats.add(rs.getString("seat_no"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return availableSeats;
    }

    public int getFlightIdByCode(String flightCode) {
        DbConnection db = new MySqlConnection();
        try (Connection conn = db.openConnection()) {
            return getFlightIdByCode(conn, flightCode);
        } catch (SQLException e) {
            System.out.println("SQL Exception in getFlightIdByCode: " + e.getMessage());
            e.printStackTrace();
            return -1;
        }
    }
    
    public int getFlightIdByCode(Connection conn, String flightCode) {    
        int flightId = -1;
        String sql = "SELECT flight_id FROM flights WHERE flight_code = ?";
        
        System.out.println("SeatClassDao.getFlightIdByCode - Flight Code: '" + flightCode + "'");
        System.out.println("SQL Query: " + sql);
        
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, flightCode);
            System.out.println("Executing query with Flight Code: '" + flightCode + "'");
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    flightId = rs.getInt("flight_id");
                    System.out.println("Flight ID found: " + flightId);
                } else {
                    System.out.println("No flight found for code: '" + flightCode + "'");
                }
            }
        } catch (SQLException e) {
            System.out.println("SQL Exception in getFlightIdByCode: " + e.getMessage());
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
    public void generateSeatsForFlight(Connection conn, int flightId, String className, int seatCount) throws SQLException {
        String sql = "INSERT INTO seats (flight_id, class_name, seat_no, is_booked) VALUES (?, ?, ?, 0)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            char row = className.charAt(0); // Use first letter of class for unique seat numbers (E, B, F)

            for (int i = 1; i <= seatCount; i++) {
                // Updated seat naming convention (e.g., E1, F1, B1)
                String seatNo = row + "" + i;
                stmt.setInt(1, flightId);
                stmt.setString(2, className);
                stmt.setString(3, seatNo);
                stmt.addBatch();
            }

            stmt.executeBatch();  // Fast insertion of all seats at once
        }
    }

    // Method to release a seat (mark as available)
    public void releaseSeat(int flightId, String seatClass, String seatNo) {
        String sql = "UPDATE seats SET is_booked = 0 WHERE flight_id = ? AND class_name = ? AND seat_no = ?";
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
    
    // Method to get seat ID by flight, class and seat number
    public int getSeatIdByFlightClassAndSeatNo(int flightId, String seatClass, String seatNo) {
        String query = "SELECT seat_id FROM seats WHERE flight_id = ? AND class_name = ? AND seat_no = ?";
        DbConnection db = new MySqlConnection();

        try (Connection conn = db.openConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, flightId);
            stmt.setString(2, seatClass);
            stmt.setString(3, seatNo);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("seat_id");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

}
