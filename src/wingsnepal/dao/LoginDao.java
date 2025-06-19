/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package wingsnepal.dao;

import java.sql.*;
import wingsnepal.database.DbConnection;
import wingsnepal.database.MySqlConnection;
import wingsnepal.model.UserData;

public class LoginDao {
    DbConnection db = new MySqlConnection();

    public UserData login(String email, String password) {
        UserData user = null;
        Connection conn = db.openConnection();

        try {
            // 1. Check in users table
            String userQuery = "SELECT * FROM users WHERE email = ? AND fpassword = ?";
            PreparedStatement userStmt = conn.prepareStatement(userQuery);
            userStmt.setString(1, email);
            userStmt.setString(2, password);
            ResultSet userRs = userStmt.executeQuery();

            if (userRs.next()) {
                user = new UserData(
                    userRs.getInt("user_id"),
                    userRs.getString("full_name"),
                    userRs.getString("email"),
                    userRs.getString("fpassword"),
                    userRs.getString("frole")
                );
            } else {
                // 2. If not found, check in employees table
                String empQuery = "SELECT * FROM employees WHERE emp_email = ? AND password = ?";
                PreparedStatement empStmt = conn.prepareStatement(empQuery);
                empStmt.setString(1, email);
                empStmt.setString(2, password);
                ResultSet empRs = empStmt.executeQuery();

                if (empRs.next()) {
                    user = new UserData(
                        empRs.getInt("emp_id"),
                        empRs.getString("emp_full_name"),
                        empRs.getString("emp_email"),
                        empRs.getString("password"),
                        "Employee" // Force the role
                    );
                }
            }

        } catch (SQLException e) {
            System.out.println("Login error: " + e.getMessage());
        } finally {
            db.closeConnection(conn);
        }

        return user;
    }
}
