package wingsnepal.dao;

import java.sql.*;
import wingsnepal.database.MySqlConnection;

public class RegisterDao {
    public static boolean registerUser(String fullName, String email, String phone, String password, String gender) {
        try (Connection conn = MySqlConnection.getConnection()) {
            String sql = "INSERT INTO registereduser (full_name, email, phone, password, gender, role) VALUES (?, ?, ?, ?, ?, 'User')";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, fullName);
            pst.setString(2, email);
            pst.setString(3, phone);
            pst.setString(4, password);
            pst.setString(5, gender);
            return pst.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
