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
        String sql = "SELECT seat_no FROM seats WHERE flight_id = ? AND class_name = ? AND is_booked = 0";

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



}
