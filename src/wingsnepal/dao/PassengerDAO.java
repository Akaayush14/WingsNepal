/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package wingsnepal.dao;

import java.sql.*;
import java.util.*;
import wingsnepal.model.Passenger;
import javax.swing.JOptionPane;

public class PassengerDAO {

    private final String url = "jdbc:mysql://localhost:3306/wingsnepal";
    private final String user = "root";
    private final String password = "your_password"; // replace with your actual password

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    public List<Passenger> getAllPassengers() {
        List<Passenger> list = new ArrayList<>();

        String query = "SELECT * FROM Passenger";

        try (Connection con = getConnection();
             PreparedStatement pst = con.prepareStatement(query);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                Passenger p = new Passenger(
                        rs.getString("full_name"),
                        rs.getInt("Passenger_Id"),
                        rs.getDate("travel_date"),
                        rs.getString("Flight_Code"),
                        rs.getString("Seat_No"),
                        rs.getString("Seat_class"),
                        rs.getString("Payment_method")
                );
                list.add(p);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }

        return list;
    }
}
