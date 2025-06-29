/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package wingsnepal.dao;
import java.sql.*;
import java.util.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import wingsnepal.model.UserDataModel;
import database.MySqlConnection;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.mindrot.jbcrypt.BCrypt;
import javax.swing.JOptionPane;

/**
 *
 * @author ACER
 */
public class UserDao {
    
    /**
     * Registers a new user in the database with a BCrypt hashed password.
     *
     * @param fullName The user's full name.
     * @param email The user's email address.
     * @param phone The user's phone number.
     * @param gender The user's gender.
     * @param plainTextPassword The user's password in plain text.
     * @param role The role of the user ('Customer', 'Employee', 'Admin').
     * @return true if registration is successful, false otherwise.
     */
    public boolean registerUser(String fullName, String email, String phone, String gender, String plainTextPassword, String role) {
        System.out.println("Registering user: " + fullName + ", " + email + ", " + phone + ", " + gender + ", " + role);
        // Use BCrypt for password hashing
        String hashedPassword = org.mindrot.jbcrypt.BCrypt.hashpw(plainTextPassword, org.mindrot.jbcrypt.BCrypt.gensalt());
        String sql = "INSERT INTO users (full_name, email, phone, gender, password_hash, role, status) VALUES (?, ?, ?, ?, ?, ?, 'Active')";

        try (Connection conn = MySqlConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, fullName);
            pst.setString(2, email);
            pst.setString(3, phone);
            pst.setString(4, gender);
            pst.setString(5, hashedPassword);
            pst.setString(6, role);

            int rowsAffected = pst.executeUpdate();
            System.out.println("Rows affected: " + rowsAffected);
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "Registration successful! Please log in.", "Success", JOptionPane.INFORMATION_MESSAGE);
                return true;
            }
            return false;

        } catch (SQLException e) {
            System.err.println("Error registering user: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Updates users with NULL status to have 'Active' status.
     * This fixes existing users who were registered before the status field was added.
     * Only updates users with NULL status, not users with explicit 'Inactive' status.
     */
    public void fixNullStatusUsers() {
        String sql = "UPDATE users SET status = 'Active' WHERE status IS NULL";
        try (Connection conn = MySqlConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            
            int rowsAffected = pst.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Fixed " + rowsAffected + " users with NULL status");
            }
            
        } catch (SQLException e) {
            System.err.println("Error fixing NULL status users: " + e.getMessage());
            e.printStackTrace();
        }
    }



    /**
     * Authenticates a user by checking their email and password against the stored hash.
     *
     * @param email The user's email.
     * @param password The password to check.
     * @param role The role of the user.
     * @return A UserDataModel object if login is successful, null otherwise.
     */
    public UserDataModel loginUser(String email, String password, String role) {
        System.out.println("UserDao.loginUser called with email: " + email + ", role: " + role);
        
        String sql = "SELECT * FROM users WHERE email = ? AND role = ?";

        try (Connection conn = MySqlConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            stmt.setString(2, role);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String storedHash = rs.getString("password_hash");
                System.out.println("Found user in database, stored hash: " + storedHash);

                // If using plain text (not recommended), compare directly:
                // if (storedHash.equals(password))

                // If using BCrypt (RECOMMENDED):
                try {
                    if (org.mindrot.jbcrypt.BCrypt.checkpw(password, storedHash)) {
                        System.out.println("Password verification successful");
                        int userId = rs.getInt("user_id");
                        String fullName = rs.getString("full_name");
                        String userEmail = rs.getString("email");
                        String userRole = rs.getString("role");
                        String status = rs.getString("status");
                        return new UserDataModel(userId, fullName, userEmail, null, userRole, null, null, status, null, null);
                    } else {
                        System.out.println("Password verification failed");
                    }
                } catch (Exception bcryptException) {
                    System.err.println("BCrypt error: " + bcryptException.getMessage());
                    bcryptException.printStackTrace();
                }
            } else {
                System.out.println("No user found with email: " + email + " and role: " + role);
            }
        } catch (Exception e) {
            System.err.println("Database error during login: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Finds a user by their email address.
     *
     * @param email The email to search for.
     * @return A UserDataModel object if found, null otherwise.
     */
    public UserDataModel findUserByEmail(String email) {
        String sql = "SELECT * FROM users WHERE email = ?";
        try (Connection conn = MySqlConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, email);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                return new UserDataModel(
                    rs.getInt("user_id"),
                    rs.getString("full_name"),
                    rs.getString("email"),
                    rs.getString("role")
                );
            }
            return null;

        } catch (SQLException e) {
            System.err.println("Error finding user by email: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * Saves a password reset token (OTP) and its expiry date for a user.
     *
     * @param email The user's email.
     * @param token The reset token to save.
     * @param expiryDate The date when the token expires.
     * @return true if the token was saved successfully, false otherwise.
     */
    public boolean saveResetToken(String email, String token, Date expiryDate) {
        String sql = "UPDATE users SET reset_token = ?, reset_token_expires = ? WHERE email = ?";
        try (Connection conn = MySqlConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            
            pst.setString(1, token);
            pst.setTimestamp(2, new Timestamp(expiryDate.getTime()));
            pst.setString(3, email);

            int rowsAffected = pst.executeUpdate();
            System.out.println("Rows affected: " + rowsAffected);
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error saving reset token: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Verifies if the provided reset token is valid and not expired.
     *
     * @param email The user's email.
     * @param token The token to verify.
     * @return true if the token is valid, false otherwise.
     */
    public boolean verifyResetToken(String email, String token) {
        String sql = "SELECT reset_token_expires FROM users WHERE email = ? AND reset_token = ?";
        try (Connection conn = MySqlConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            
            pst.setString(1, email);
            pst.setString(2, token);
            
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                Timestamp expiry = rs.getTimestamp("reset_token_expires");
                // Check if the token has expired
                return expiry != null && expiry.after(new Date());
            }
            return false;

        } catch (SQLException e) {
            System.err.println("Error verifying reset token: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Resets the user's password to a new one and clears the reset token.
     *
     * @param email The user's email.
     * @param newPassword The new password in plain text.
     * @return true if the password was reset successfully, false otherwise.
     */
    public boolean resetPassword(String email, String newPassword) {
        String hashedPassword = org.mindrot.jbcrypt.BCrypt.hashpw(newPassword, org.mindrot.jbcrypt.BCrypt.gensalt());
        String sql = "UPDATE users SET password_hash = ?, reset_token = NULL, reset_token_expires = NULL WHERE email = ?";
        
        try (Connection conn = MySqlConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            
            pst.setString(1, hashedPassword);
            pst.setString(2, email);

            int rowsAffected = pst.executeUpdate();
            System.out.println("Rows affected: " + rowsAffected);
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error resetting password: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Retrieves a list of all users with the 'Employee' role.
     *
     * @return A list of UserDataModel objects representing employees.
     */
    public List<UserDataModel> getAllEmployees() {
        List<UserDataModel> employees = new ArrayList<>();
        String sql = "SELECT user_id, full_name, email, phone, role, job_title, salary, status, date_joined, gender FROM users WHERE role = 'Employee'";
        try (Connection conn = MySqlConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                UserDataModel emp = new UserDataModel(
                    rs.getInt("user_id"),
                    rs.getString("full_name"),
                    rs.getString("email"),
                    rs.getString("phone"),
                    rs.getString("role"),
                    rs.getString("job_title"),
                    rs.getObject("salary") != null ? rs.getDouble("salary") : null,
                    rs.getString("status"),
                    rs.getDate("date_joined"),
                    rs.getString("gender")
                );
                employees.add(emp);
            }
        } catch (SQLException e) {
            System.err.println("Error fetching employees: " + e.getMessage());
            e.printStackTrace();
        }
        return employees;
    }
    
    /**
     * Searches for employees by a given keyword in their name or email.
     *
     * @param keyword The search term.
     * @return A list of matching employees.
     */
    public List<UserDataModel> searchEmployees(String keyword) {
        List<UserDataModel> employees = new ArrayList<>();
        // Using LIKE for partial matching. The '%' are wildcards.
        String sql = "SELECT * FROM users WHERE role = 'Employee' AND (full_name LIKE ? OR email LIKE ?)";
        
        try (Connection conn = MySqlConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            
            String searchPattern = "%" + keyword + "%";
            pst.setString(1, searchPattern);
            pst.setString(2, searchPattern);
            
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    employees.add(new UserDataModel(
                        rs.getInt("user_id"),
                        rs.getString("full_name"),
                        rs.getString("email"),
                        rs.getString("role")
                    ));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error searching employees: " + e.getMessage());
            e.printStackTrace();
        }
        
        return employees;
    }

    /**
     * Deletes a user from the database with proper cascading deletion.
     * This removes all related data: bookings, passengers, reservations, etc.
     *
     * @param userId The ID of the user to delete.
     * @return true if deletion was successful, false otherwise.
     */
    public boolean deleteUser(int userId) {
        // First check if the user is an admin - prevent admin deletion for security
        String checkSql = "SELECT role FROM users WHERE user_id = ?";
        try (Connection conn = MySqlConnection.getConnection();
             PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
            checkStmt.setInt(1, userId);
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next() && "Admin".equals(rs.getString("role"))) {
                System.err.println("Cannot delete admin user for security reasons");
                return false;
            }
        } catch (SQLException e) {
            System.err.println("Error checking user role: " + e.getMessage());
            return false;
        }
        
        System.out.println("Starting complete cascading deletion for user ID: " + userId);
        
        try (Connection conn = MySqlConnection.getConnection()) {
            // Use transaction for data integrity
            conn.setAutoCommit(false);
            
            try {
                // STEP 1: Get all bookings for this user to release seats
                String getBookingsSql = "SELECT flight_id, class_name, seat_no FROM bookings WHERE user_id = ?";
                PreparedStatement getBookingsStmt = conn.prepareStatement(getBookingsSql);
                getBookingsStmt.setInt(1, userId);
                ResultSet bookingsRs = getBookingsStmt.executeQuery();
                
                wingsnepal.dao.SeatClassDao seatDao = new wingsnepal.dao.SeatClassDao();
                int seatsReleased = 0;
                
                // Release all seats before deletion
                while (bookingsRs.next()) {
                    int flightId = bookingsRs.getInt("flight_id");
                    String seatClass = bookingsRs.getString("class_name");
                    String seatNo = bookingsRs.getString("seat_no");
                    
                    if (seatNo != null && !seatNo.trim().isEmpty()) {
                        System.out.println("USER DELETION: Releasing seat - flightId=" + flightId + 
                                         ", seatClass=" + seatClass + ", seatNo=" + seatNo);
                        seatDao.releaseSeat(flightId, seatClass, seatNo);
                        seatsReleased++;
                    }
                }
                System.out.println("Released " + seatsReleased + " seats for user ID: " + userId);
                
                // STEP 2: Delete ALL passenger records for this user (by user_id AND by email)
                String getUserEmailSql = "SELECT email FROM users WHERE user_id = ?";
                PreparedStatement getUserEmailStmt = conn.prepareStatement(getUserEmailSql);
                getUserEmailStmt.setInt(1, userId);
                ResultSet userRs = getUserEmailStmt.executeQuery();
                
                String userEmail = null;
                if (userRs.next()) {
                    userEmail = userRs.getString("email");
                }
                
                // Delete passengers by user_id
                String deletePassengersByUserIdSql = "DELETE FROM passengers WHERE user_id = ?";
                PreparedStatement deletePassengersStmt1 = conn.prepareStatement(deletePassengersByUserIdSql);
                deletePassengersStmt1.setInt(1, userId);
                int passengersDeleted1 = deletePassengersStmt1.executeUpdate();
                
                // Also delete passengers by email (in case there are records with different user_id but same email)
                int passengersDeleted2 = 0;
                if (userEmail != null) {
                    String deletePassengersByEmailSql = "DELETE FROM passengers WHERE email = ?";
                    PreparedStatement deletePassengersStmt2 = conn.prepareStatement(deletePassengersByEmailSql);
                    deletePassengersStmt2.setString(1, userEmail);
                    passengersDeleted2 = deletePassengersStmt2.executeUpdate();
                }
                
                System.out.println("Deleted " + (passengersDeleted1 + passengersDeleted2) + " passenger records for user ID: " + userId);
                
                // STEP 3: Delete ALL booking records for this user
                String deleteBookingsSql = "DELETE FROM bookings WHERE user_id = ?";
                PreparedStatement deleteBookingsStmt = conn.prepareStatement(deleteBookingsSql);
                deleteBookingsStmt.setInt(1, userId);
                int bookingsDeleted = deleteBookingsStmt.executeUpdate();
                System.out.println("Deleted " + bookingsDeleted + " booking records for user ID: " + userId);
                
                // STEP 4: Delete reservation records if table exists (graceful handling)
                try {
                    if (userEmail != null) {
                        String deleteReservationsSql = "DELETE FROM reservations WHERE passenger_email = ?";
                        PreparedStatement deleteReservationsStmt = conn.prepareStatement(deleteReservationsSql);
                        deleteReservationsStmt.setString(1, userEmail);
                        int reservationsDeleted = deleteReservationsStmt.executeUpdate();
                        if (reservationsDeleted > 0) {
                            System.out.println("Deleted " + reservationsDeleted + " reservation records for user ID: " + userId);
                        }
                    }
        } catch (SQLException e) {
                    // Ignore if reservations table doesn't exist
                    if (!e.getMessage().contains("doesn't exist")) {
                        System.err.println("Error deleting reservations: " + e.getMessage());
                    }
                }
                
                // STEP 5: Finally delete the user record
                String deleteUserSql = "DELETE FROM users WHERE user_id = ? AND role != 'Admin'";
                PreparedStatement deleteUserStmt = conn.prepareStatement(deleteUserSql);
                deleteUserStmt.setInt(1, userId);
                boolean userDeleted = deleteUserStmt.executeUpdate() > 0;
                
                if (userDeleted) {
                    // Commit transaction
                    conn.commit();
                    System.out.println("Successfully deleted user ID: " + userId + " and all related data");
                    return true;
                } else {
                    // Rollback transaction
                    conn.rollback();
                    System.err.println("Failed to delete user record for user ID: " + userId);
                    return false;
                }
                
            } catch (Exception e) {
                // Rollback transaction on error
                conn.rollback();
                throw e;
            } finally {
                conn.setAutoCommit(true);
            }
            
        } catch (Exception e) {
            System.err.println("Error during cascading user deletion: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    // New method: Register Employee with all fields
    public boolean registerEmployee(String fullName, String email, String phone, String gender, String password, String jobTitle, Double salary, String status) {
        String hashedPassword = org.mindrot.jbcrypt.BCrypt.hashpw(password, org.mindrot.jbcrypt.BCrypt.gensalt());
        String sql = "INSERT INTO users (full_name, email, phone, gender, password_hash, role, job_title, salary, status) VALUES (?, ?, ?, ?, ?, 'Employee', ?, ?, ?)";
        try (Connection conn = MySqlConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, fullName);
            pst.setString(2, email);
            pst.setString(3, phone);
            pst.setString(4, gender);
            pst.setString(5, hashedPassword);
            pst.setString(6, jobTitle);
            if (salary != null) {
                pst.setDouble(7, salary);
            } else {
                pst.setNull(7, java.sql.Types.DOUBLE);
            }
            pst.setString(8, status);
            int rowsAffected = pst.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error registering employee: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Finds a user by their ID.
     *
     * @param userId The user ID to search for.
     * @return UserDataModel object if found, null otherwise.
     */
    public UserDataModel findUserById(int userId) {
        String sql = "SELECT user_id, full_name, email, phone, role, job_title, salary, status, gender FROM users WHERE user_id = ?";
        try (Connection conn = MySqlConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, userId);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return new UserDataModel(
                    rs.getInt("user_id"),
                    rs.getString("full_name"),
                    rs.getString("email"),
                    rs.getString("phone"),
                    rs.getString("role"),
                    rs.getString("job_title"),
                    rs.getObject("salary") != null ? rs.getDouble("salary") : null,
                    rs.getString("status"),
                    null, // date_joined not used
                    rs.getString("gender")
                );
            }
        } catch (SQLException e) {
            System.err.println("Error finding user by ID: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public boolean updateEmployee(int userId, String fullName, String email, String role, String gender, String password, String jobTitle, Double salary, String status) {
        StringBuilder sql = new StringBuilder("UPDATE users SET full_name=?, email=?, role=?, gender=?, job_title=?, salary=?, status=?");
        if (password != null && !password.isEmpty()) {
            sql.append(", password_hash=?");
        }
        sql.append(" WHERE user_id=?");
        try (Connection conn = MySqlConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql.toString())) {
            int idx = 1;
            pst.setString(idx++, fullName);
            pst.setString(idx++, email);
            pst.setString(idx++, role);
            pst.setString(idx++, gender);
            pst.setString(idx++, jobTitle);
            if (salary != null) {
                pst.setDouble(idx++, salary);
            } else {
                pst.setNull(idx++, java.sql.Types.DOUBLE);
            }
            pst.setString(idx++, status);
            if (password != null && !password.isEmpty()) {
                String hashedPassword = org.mindrot.jbcrypt.BCrypt.hashpw(password, org.mindrot.jbcrypt.BCrypt.gensalt());
                pst.setString(idx++, hashedPassword);
            }
            pst.setInt(idx, userId);
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Public method to manually fix users with NULL status.
     * Call this once to fix existing users in the database.
     */
    public void manuallyFixNullStatusUsers() {
        System.out.println("Manually fixing users with NULL status...");
        fixNullStatusUsers();
    }

    /**
     * Retrieves a list of all users (excluding admin accounts for security).
     *
     * @return A list of UserDataModel objects representing all non-admin users.
     */
    public List<UserDataModel> getAllUsers() {
        List<UserDataModel> users = new ArrayList<>();
        String sql = "SELECT user_id, full_name, email, phone, role, job_title, salary, status, gender FROM users WHERE role != 'Admin' ORDER BY role, full_name";
        try (Connection conn = MySqlConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                UserDataModel user = new UserDataModel(
                    rs.getInt("user_id"),
                    rs.getString("full_name"),
                    rs.getString("email"),
                    rs.getString("phone"),
                    rs.getString("role"),
                    rs.getString("job_title"),
                    rs.getObject("salary") != null ? rs.getDouble("salary") : null,
                    rs.getString("status"),
                    null, // date_joined not used
                    rs.getString("gender")
                );
                users.add(user);
            }
        } catch (SQLException e) {
            System.err.println("Error fetching all users: " + e.getMessage());
            e.printStackTrace();
        }
        return users;
    }

    /**
     * Searches for users by a given keyword in their name or email (excluding admin accounts).
     *
     * @param keyword The search term.
     * @return A list of matching non-admin users.
     */
    public List<UserDataModel> searchUsers(String keyword) {
        List<UserDataModel> users = new ArrayList<>();
        String sql = "SELECT * FROM users WHERE role != 'Admin' AND (full_name LIKE ? OR email LIKE ?) ORDER BY role, full_name";
        try (Connection conn = MySqlConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, "%" + keyword + "%");
            pst.setString(2, "%" + keyword + "%");
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                UserDataModel user = new UserDataModel(
                    rs.getInt("user_id"),
                    rs.getString("full_name"),
                    rs.getString("email"),
                    rs.getString("phone"),
                    rs.getString("role"),
                    rs.getString("job_title"),
                    rs.getObject("salary") != null ? rs.getDouble("salary") : null,
                    rs.getString("status"),
                    null, // date_joined not used
                    rs.getString("gender")
                );
                users.add(user);
            }
        } catch (SQLException e) {
            System.err.println("Error searching users: " + e.getMessage());
            e.printStackTrace();
        }
        return users;
    }

    /**
     * Updates any user (not just employees) with all fields. Excludes admin accounts for security.
     */
    public boolean updateUser(int userId, String fullName, String email, String phone, String role, String gender, String password, String jobTitle, Double salary, String status) {
        // First check if the user is an admin - prevent admin modification for security
        String checkSql = "SELECT role, status FROM users WHERE user_id = ?";
        String previousStatus = null;
        try (Connection conn = MySqlConnection.getConnection();
             PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
            checkStmt.setInt(1, userId);
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next()) {
                if ("Admin".equals(rs.getString("role"))) {
                    System.err.println("Cannot update admin user for security reasons");
                    return false;
                }
                previousStatus = rs.getString("status");
            }
        } catch (SQLException e) {
            System.err.println("Error checking user role: " + e.getMessage());
            return false;
        }
        
        StringBuilder sql = new StringBuilder("UPDATE users SET full_name=?, email=?, phone=?, role=?, gender=?, job_title=?, salary=?, status=?");
        if (password != null && !password.isEmpty()) {
            sql.append(", password_hash=?");
        }
        sql.append(" WHERE user_id=? AND role != 'Admin'");
        try (Connection conn = MySqlConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql.toString())) {
            int idx = 1;
            pst.setString(idx++, fullName);
            pst.setString(idx++, email);
            pst.setString(idx++, phone);
            pst.setString(idx++, role);
            pst.setString(idx++, gender);
            pst.setString(idx++, jobTitle);
            if (salary != null) {
                pst.setDouble(idx++, salary);
            } else {
                pst.setNull(idx++, java.sql.Types.DOUBLE);
            }
            pst.setString(idx++, status);
            if (password != null && !password.isEmpty()) {
                String hashedPassword = org.mindrot.jbcrypt.BCrypt.hashpw(password, org.mindrot.jbcrypt.BCrypt.gensalt());
                pst.setString(idx++, hashedPassword);
            }
            pst.setInt(idx, userId);
            
            boolean updateSuccess = pst.executeUpdate() > 0;
            
            // Handle booking status changes based on user status change
            if (updateSuccess && previousStatus != null && !previousStatus.equals(status)) {
                wingsnepal.dao.ManageBookingDao bookingDao = new wingsnepal.dao.ManageBookingDao();
                
                if ("Active".equals(previousStatus) && "Inactive".equals(status)) {
                    // User was deactivated - suspend their bookings
                    bookingDao.suspendBookingsForUser(userId);
                    System.out.println("User " + userId + " deactivated - bookings suspended");
                } else if ("Inactive".equals(previousStatus) && "Active".equals(status)) {
                    // User was reactivated - reactivate their bookings
                    bookingDao.reactivateBookingsForUser(userId);
                    System.out.println("User " + userId + " reactivated - bookings reactivated");
                }
            }
            
            return updateSuccess;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Updates only the phone number for a specific user.
     *
     * @param userId The ID of the user to update.
     * @param phone The new phone number.
     * @return true if the update was successful, false otherwise.
     */
    public boolean updateUserPhone(int userId, String phone) {
        String sql = "UPDATE users SET phone = ? WHERE user_id = ? AND role != 'Admin'";
        try (Connection conn = MySqlConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, phone);
            pst.setInt(2, userId);
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error updating user phone: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Updates only the full name for a specific user.
     *
     * @param userId The ID of the user to update.
     * @param fullName The new full name.
     * @return true if the update was successful, false otherwise.
     */
    public boolean updateUserFullName(int userId, String fullName) {
        String sql = "UPDATE users SET full_name = ? WHERE user_id = ? AND role != 'Admin'";
        try (Connection conn = MySqlConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, fullName);
            pst.setInt(2, userId);
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error updating user full name: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Gets a user's phone number by their user ID.
     *
     * @param userId The user's ID.
     * @return The phone number if found, null otherwise.
     */
    public String getUserPhoneById(int userId) {
        String sql = "SELECT phone FROM users WHERE user_id = ?";
        try (Connection conn = MySqlConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setInt(1, userId);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                return rs.getString("phone");
            }
            return null;

        } catch (SQLException e) {
            System.err.println("Error getting user phone by ID: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Gets a placeholder representation of the user's password for admin editing.
     * Since passwords are BCrypt hashed, we can't retrieve the original password.
     * This method returns a placeholder to indicate a password exists.
     *
     * @param userId The user's ID.
     * @return A placeholder string if password exists, empty string if no password found.
     */
    public String getPasswordPlaceholderById(int userId) {
        String sql = "SELECT password_hash FROM users WHERE user_id = ?";
        try (Connection conn = MySqlConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setInt(1, userId);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                String passwordHash = rs.getString("password_hash");
                // Return placeholder if password exists, empty if null
                return (passwordHash != null && !passwordHash.isEmpty()) ? "********" : "";
            }
            return "";

        } catch (SQLException e) {
            System.err.println("Error getting password placeholder by ID: " + e.getMessage());
            e.printStackTrace();
            return "";
        }
    }
}
    
