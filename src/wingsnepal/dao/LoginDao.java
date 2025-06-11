/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package wingsnepal.dao;

import java.sql.*;
import wingsnepal.database.DbConnection;
import wingsnepal.database.MySqlConnection;
import wingsnepal.model.Login;

/**
 *
 * @author Aayush Kharel
 */
public class LoginDao {
    DbConnection db = new MySqlConnection();

    public Login login(String email, String password) {
        String query = "SELECT * FROM users WHERE email = ? AND fpassword = ?";
        Connection conn = db.openConnection();
        Login user = null;

        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, email);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                user = new Login(
                    rs.getInt("user_id"),
                    rs.getString("full_name"),
                    rs.getString("email"),
                    rs.getString("fpassword"), 
                    rs.getString("frole")
                );
            }
        } catch (SQLException e) {
            System.out.println("Login error: " + e.getMessage());
        } finally {
            db.closeConnection(conn);
        }

        return user;
    }
}
