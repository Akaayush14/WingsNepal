/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package wingsnepal.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
}