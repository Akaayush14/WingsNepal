/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package wingsnepal.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import database.MySqlConnection;

public class ForgotPasswordDao {
    private MySqlConnection dbConnection;

    public ForgotPasswordDao() {
        this.dbConnection = new MySqlConnection();
    }

    public boolean checkUserByEmail(String email) {
        // Check both registereduser and employees tables
        String userSql = "SELECT COUNT(*) FROM registereduser WHERE email = ?";
        String employeeSql = "SELECT COUNT(*) FROM employees WHERE emp_email = ?";
        
        try (Connection conn = dbConnection.openConnection()) {
            // Check registereduser table
            try (PreparedStatement pstmt = conn.prepareStatement(userSql)) {
                pstmt.setString(1, email);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next() && rs.getInt(1) > 0) {
                        return true;
                    }
                }
            }
            
            // Check employees table
            try (PreparedStatement pstmt = conn.prepareStatement(employeeSql)) {
                pstmt.setString(1, email);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next() && rs.getInt(1) > 0) {
                        return true;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean saveOtp(String email, String otp) {
        // Try to save OTP in registereduser table first
        String userSql = "UPDATE registereduser SET reset_token = ?, reset_token_expires = ? WHERE email = ?";
        String employeeSql = "UPDATE employees SET reset_token = ?, reset_token_expires = ? WHERE emp_email = ?";
        
        try (Connection conn = dbConnection.openConnection()) {
            // Try registereduser table first
            try (PreparedStatement pstmt = conn.prepareStatement(userSql)) {
                pstmt.setString(1, otp);
                pstmt.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now().plusMinutes(10))); // OTP expires in 10 minutes
                pstmt.setString(3, email);
                int result = pstmt.executeUpdate();
                if (result > 0) {
                    return true;
                }
            }
            
            // If not found in registereduser, try employees table
            try (PreparedStatement pstmt = conn.prepareStatement(employeeSql)) {
                pstmt.setString(1, otp);
                pstmt.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now().plusMinutes(10))); // OTP expires in 10 minutes
                pstmt.setString(3, email);
                int result = pstmt.executeUpdate();
                return result > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean verifyOtp(String email, String otp) {
        // Check both tables for OTP verification
        String userSql = "SELECT reset_token_expires FROM registereduser WHERE email = ? AND reset_token = ?";
        String employeeSql = "SELECT reset_token_expires FROM employees WHERE emp_email = ? AND reset_token = ?";
        
        try (Connection conn = dbConnection.openConnection()) {
            // Check registereduser table first
            try (PreparedStatement pstmt = conn.prepareStatement(userSql)) {
                pstmt.setString(1, email);
                pstmt.setString(2, otp);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        Timestamp expiry = rs.getTimestamp("reset_token_expires");
                        return expiry != null && expiry.toLocalDateTime().isAfter(LocalDateTime.now());
                    }
                }
            }
            
            // Check employees table
            try (PreparedStatement pstmt = conn.prepareStatement(employeeSql)) {
                pstmt.setString(1, email);
                pstmt.setString(2, otp);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        Timestamp expiry = rs.getTimestamp("reset_token_expires");
                        return expiry != null && expiry.toLocalDateTime().isAfter(LocalDateTime.now());
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean resetPassword(String email, String newPassword) {
        // Try to reset password in both tables
        String userSql = "UPDATE registereduser SET password = ?, reset_token = NULL, reset_token_expires = NULL WHERE email = ?";
        String employeeSql = "UPDATE employees SET password = ?, reset_token = NULL, reset_token_expires = NULL WHERE emp_email = ?";
        
        try (Connection conn = dbConnection.openConnection()) {
            // Try registereduser table first
            try (PreparedStatement pstmt = conn.prepareStatement(userSql)) {
                pstmt.setString(1, newPassword);
                pstmt.setString(2, email);
                int result = pstmt.executeUpdate();
                if (result > 0) {
                    return true;
                }
            }
            
            // If not found in registereduser, try employees table
            try (PreparedStatement pstmt = conn.prepareStatement(employeeSql)) {
                pstmt.setString(1, newPassword);
                pstmt.setString(2, email);
                int result = pstmt.executeUpdate();
                return result > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
} 