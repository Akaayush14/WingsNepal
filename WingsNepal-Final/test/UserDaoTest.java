/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package test;

import wingsnepal.dao.UserDao;
import wingsnepal.model.UserDataModel;
import java.util.List;
import org.junit.*;

/**
 *
 * @author Aayush Kharel
 */
public class UserDaoTest {
    
    String correctEmail = "testuser@gmail.com";
    String correctFullName = "Test User";
    String correctPhone = "9876543210";
    String correctGender = "Male";
    String correctPassword = "password123";
    String correctRole = "Customer";
    String correctJobTitle = "Test Job";
    Double correctSalary = 50000.0;
    String correctStatus = "Active";
    
    UserDao dao = new UserDao();

    @Test
    public void registerUserWithNewDetails() {
        boolean result = dao.registerUser(correctFullName, correctEmail, correctPhone, correctGender, correctPassword, correctRole);
        Assert.assertTrue("Register should work with unique details", result);
    }

    @Test
    public void registerUserWithDuplicateEmail() {
        // First registration
        dao.registerUser(correctFullName, correctEmail, correctPhone, correctGender, correctPassword, correctRole);
        // Second registration with same email
        boolean result = dao.registerUser(correctFullName, correctEmail, correctPhone, correctGender, correctPassword, correctRole);
        Assert.assertFalse("Register should fail with duplicate email", result);
    }

    @Test
    public void loginUserWithCorrectCredentials() {
        // First register the user
        dao.registerUser(correctFullName, correctEmail, correctPhone, correctGender, correctPassword, correctRole);
        
        UserDataModel user = dao.loginUser(correctEmail, correctPassword, correctRole);
        Assert.assertNotNull("User should not be null", user);
        Assert.assertEquals("Full name should match", correctFullName, user.getFullName());
        Assert.assertEquals("Email should match", correctEmail, user.getEmail());
        Assert.assertEquals("Role should match", correctRole, user.getRole());
    }

    @Test
    public void loginUserWithInvalidCredentials() {
        UserDataModel user = dao.loginUser("invalid@test.com", "wrongpassword", correctRole);
        Assert.assertNull("User should be null for invalid credentials", user);
    }

    @Test
    public void findUserByEmailWithValidEmail() {
        // First register the user
        dao.registerUser(correctFullName, correctEmail, correctPhone, correctGender, correctPassword, correctRole);
        
        UserDataModel user = dao.findUserByEmail(correctEmail);
        Assert.assertNotNull("User should not be null", user);
        Assert.assertEquals("Email should match", correctEmail, user.getEmail());
        Assert.assertEquals("Full name should match", correctFullName, user.getFullName());
    }

    @Test
    public void findUserByEmailWithInvalidEmail() {
        UserDataModel user = dao.findUserByEmail("nonexistent@test.com");
        Assert.assertNull("User should be null for invalid email", user);
    }

    @Test
    public void saveResetTokenWithValidData() {
        // First register the user
        dao.registerUser(correctFullName, correctEmail, correctPhone, correctGender, correctPassword, correctRole);
        
        String token = "test123token";
        java.util.Date expiryDate = new java.util.Date(System.currentTimeMillis() + 3600000); // 1 hour from now
        
        boolean result = dao.saveResetToken(correctEmail, token, expiryDate);
        Assert.assertTrue("Reset token should be saved successfully", result);
    }

    @Test
    public void saveResetTokenWithInvalidEmail() {
        String token = "test123token";
        java.util.Date expiryDate = new java.util.Date(System.currentTimeMillis() + 3600000);
        
        boolean result = dao.saveResetToken("invalid@test.com", token, expiryDate);
        Assert.assertFalse("Reset token should fail with invalid email", result);
    }

    @Test
    public void verifyResetTokenWithValidToken() {
        // First register the user and save token
        dao.registerUser(correctFullName, correctEmail, correctPhone, correctGender, correctPassword, correctRole);
        String token = "test123token";
        java.util.Date expiryDate = new java.util.Date(System.currentTimeMillis() + 3600000);
        dao.saveResetToken(correctEmail, token, expiryDate);
        
        boolean result = dao.verifyResetToken(correctEmail, token);
        Assert.assertTrue("Reset token should be verified successfully", result);
    }

    @Test
    public void verifyResetTokenWithInvalidToken() {
        // First register the user
        dao.registerUser(correctFullName, correctEmail, correctPhone, correctGender, correctPassword, correctRole);
        
        boolean result = dao.verifyResetToken(correctEmail, "invalidtoken");
        Assert.assertFalse("Reset token should fail with invalid token", result);
    }

    @Test
    public void resetPasswordWithValidData() {
        // First register the user and save token
        dao.registerUser(correctFullName, correctEmail, correctPhone, correctGender, correctPassword, correctRole);
        String token = "test123token";
        java.util.Date expiryDate = new java.util.Date(System.currentTimeMillis() + 3600000);
        dao.saveResetToken(correctEmail, token, expiryDate);
        
        String newPassword = "newpassword123";
        boolean result = dao.resetPassword(correctEmail, newPassword);
        Assert.assertTrue("Password should be reset successfully", result);
    }

    @Test
    public void resetPasswordWithInvalidEmail() {
        String newPassword = "newpassword123";
        boolean result = dao.resetPassword("invalid@test.com", newPassword);
        Assert.assertFalse("Password reset should fail with invalid email", result);
    }

    @Test
    public void getAllEmployees() {
        // First register an employee
        dao.registerEmployee(correctFullName, correctEmail, correctPhone, correctGender, correctPassword, correctJobTitle, correctSalary, correctStatus);
        
        List<UserDataModel> employees = dao.getAllEmployees();
        Assert.assertNotNull("Employees list should not be null", employees);
        Assert.assertTrue("Should have at least one employee", employees.size() > 0);
    }

    @Test
    public void searchEmployeesWithValidKeyword() {
        // First register an employee
        dao.registerEmployee(correctFullName, correctEmail, correctPhone, correctGender, correctPassword, correctJobTitle, correctSalary, correctStatus);
        
        List<UserDataModel> employees = dao.searchEmployees(correctFullName);
        Assert.assertNotNull("Search results should not be null", employees);
        Assert.assertTrue("Should find at least one employee", employees.size() > 0);
    }

    @Test
    public void searchEmployeesWithInvalidKeyword() {
        List<UserDataModel> employees = dao.searchEmployees("nonexistentemployee");
        Assert.assertNotNull("Search results should not be null", employees);
        Assert.assertTrue("Should return empty list for invalid keyword", employees.size() == 0);
    }

    @Test
    public void registerEmployeeWithValidData() {
        boolean result = dao.registerEmployee(correctFullName, correctEmail, correctPhone, correctGender, correctPassword, correctJobTitle, correctSalary, correctStatus);
        Assert.assertTrue("Employee registration should succeed", result);
    }

    @Test
    public void registerEmployeeWithDuplicateEmail() {
        // First registration
        dao.registerEmployee(correctFullName, correctEmail, correctPhone, correctGender, correctPassword, correctJobTitle, correctSalary, correctStatus);
        // Second registration with same email
        boolean result = dao.registerEmployee(correctFullName, correctEmail, correctPhone, correctGender, correctPassword, correctJobTitle, correctSalary, correctStatus);
        Assert.assertFalse("Employee registration should fail with duplicate email", result);
    }

    @Test
    public void findUserByIdWithValidId() {
        // First register the user
        dao.registerUser(correctFullName, correctEmail, correctPhone, correctGender, correctPassword, correctRole);
        UserDataModel originalUser = dao.findUserByEmail(correctEmail);
        Assert.assertNotNull("Original user should exist", originalUser);
        
        UserDataModel foundUser = dao.findUserById(originalUser.getUserId());
        Assert.assertNotNull("Found user should not be null", foundUser);
        Assert.assertEquals("User IDs should match", originalUser.getUserId(), foundUser.getUserId());
        Assert.assertEquals("Full names should match", originalUser.getFullName(), foundUser.getFullName());
    }

    @Test
    public void findUserByIdWithInvalidId() {
        UserDataModel user = dao.findUserById(-1);
        Assert.assertNull("User should be null for invalid ID", user);
    }

    @Test
    public void updateEmployeeWithValidData() {
        // First register an employee
        dao.registerEmployee(correctFullName, correctEmail, correctPhone, correctGender, correctPassword, correctJobTitle, correctSalary, correctStatus);
        UserDataModel originalEmployee = dao.findUserByEmail(correctEmail);
        Assert.assertNotNull("Original employee should exist", originalEmployee);
        
        String newFullName = "Updated Employee Name";
        String newEmail = "updated@test.com";
        String newRole = "Employee";
        String newGender = "Female";
        String newPassword = "newpassword123";
        String newJobTitle = "Updated Job Title";
        Double newSalary = 60000.0;
        String newStatus = "Active";
        
        boolean result = dao.updateEmployee(originalEmployee.getUserId(), newFullName, newEmail, newRole, newGender, newPassword, newJobTitle, newSalary, newStatus);
        Assert.assertTrue("Employee update should succeed", result);
        
        UserDataModel updatedEmployee = dao.findUserById(originalEmployee.getUserId());
        Assert.assertNotNull("Updated employee should exist", updatedEmployee);
        Assert.assertEquals("Full name should be updated", newFullName, updatedEmployee.getFullName());
        Assert.assertEquals("Email should be updated", newEmail, updatedEmployee.getEmail());
    }

    @Test
    public void updateEmployeeWithInvalidId() {
        boolean result = dao.updateEmployee(-1, "Test Name", "test@test.com", "Employee", "Male", "password", "Job", 50000.0, "Active");
        Assert.assertFalse("Employee update should fail with invalid ID", result);
    }

    @Test
    public void getAllUsers() {
        // First register a user
        dao.registerUser(correctFullName, correctEmail, correctPhone, correctGender, correctPassword, correctRole);
        
        List<UserDataModel> users = dao.getAllUsers();
        Assert.assertNotNull("Users list should not be null", users);
        Assert.assertTrue("Should have at least one user", users.size() > 0);
    }

    @Test
    public void searchUsersWithValidKeyword() {
        // First register a user
        dao.registerUser(correctFullName, correctEmail, correctPhone, correctGender, correctPassword, correctRole);
        
        List<UserDataModel> users = dao.searchUsers(correctFullName);
        Assert.assertNotNull("Search results should not be null", users);
        Assert.assertTrue("Should find at least one user", users.size() > 0);
    }

    @Test
    public void searchUsersWithInvalidKeyword() {
        List<UserDataModel> users = dao.searchUsers("nonexistentuser");
        Assert.assertNotNull("Search results should not be null", users);
        Assert.assertTrue("Should return empty list for invalid keyword", users.size() == 0);
    }

    @Test
    public void updateUserWithValidData() {
        // First register a user
        dao.registerUser(correctFullName, correctEmail, correctPhone, correctGender, correctPassword, correctRole);
        UserDataModel originalUser = dao.findUserByEmail(correctEmail);
        Assert.assertNotNull("Original user should exist", originalUser);
        
        String newFullName = "Updated User Name";
        String newEmail = "updateduser@test.com";
        String newPhone = "1234567890";
        String newRole = "Customer";
        String newGender = "Female";
        String newPassword = "newpassword123";
        String newJobTitle = "Updated Job";
        Double newSalary = 55000.0;
        String newStatus = "Active";
        
        boolean result = dao.updateUser(originalUser.getUserId(), newFullName, newEmail, newPhone, newRole, newGender, newPassword, newJobTitle, newSalary, newStatus);
        Assert.assertTrue("User update should succeed", result);
        
        UserDataModel updatedUser = dao.findUserById(originalUser.getUserId());
        Assert.assertNotNull("Updated user should exist", updatedUser);
        Assert.assertEquals("Full name should be updated", newFullName, updatedUser.getFullName());
        Assert.assertEquals("Email should be updated", newEmail, updatedUser.getEmail());
    }

    @Test
    public void updateUserWithInvalidId() {
        boolean result = dao.updateUser(-1, "Test Name", "test@test.com", "1234567890", "Customer", "Male", "password", "Job", 50000.0, "Active");
        Assert.assertFalse("User update should fail with invalid ID", result);
    }

    @Test
    public void updateUserPhoneWithValidData() {
        // First register a user
        dao.registerUser(correctFullName, correctEmail, correctPhone, correctGender, correctPassword, correctRole);
        UserDataModel originalUser = dao.findUserByEmail(correctEmail);
        Assert.assertNotNull("Original user should exist", originalUser);
        
        String newPhone = "1112223333";
        boolean result = dao.updateUserPhone(originalUser.getUserId(), newPhone);
        Assert.assertTrue("Phone update should succeed", result);
    }

    @Test
    public void updateUserPhoneWithInvalidId() {
        boolean result = dao.updateUserPhone(-1, "1112223333");
        Assert.assertFalse("Phone update should fail with invalid ID", result);
    }

    @Test
    public void updateUserFullNameWithValidData() {
        // First register a user
        dao.registerUser(correctFullName, correctEmail, correctPhone, correctGender, correctPassword, correctRole);
        UserDataModel originalUser = dao.findUserByEmail(correctEmail);
        Assert.assertNotNull("Original user should exist", originalUser);
        
        String newFullName = "Updated Full Name";
        boolean result = dao.updateUserFullName(originalUser.getUserId(), newFullName);
        Assert.assertTrue("Full name update should succeed", result);
    }

    @Test
    public void updateUserFullNameWithInvalidId() {
        boolean result = dao.updateUserFullName(-1, "Updated Name");
        Assert.assertFalse("Full name update should fail with invalid ID", result);
    }

    @Test
    public void getUserPhoneByIdWithValidId() {
        // First register a user
        dao.registerUser(correctFullName, correctEmail, correctPhone, correctGender, correctPassword, correctRole);
        UserDataModel originalUser = dao.findUserByEmail(correctEmail);
        Assert.assertNotNull("Original user should exist", originalUser);
        
        String phone = dao.getUserPhoneById(originalUser.getUserId());
        Assert.assertNotNull("Phone should not be null", phone);
        Assert.assertEquals("Phone should match", correctPhone, phone);
    }

    @Test
    public void getUserPhoneByIdWithInvalidId() {
        String phone = dao.getUserPhoneById(-1);
        Assert.assertNull("Phone should be null for invalid ID", phone);
    }

    @Test
    public void getPasswordPlaceholderByIdWithValidId() {
        // First register a user
        dao.registerUser(correctFullName, correctEmail, correctPhone, correctGender, correctPassword, correctRole);
        UserDataModel originalUser = dao.findUserByEmail(correctEmail);
        Assert.assertNotNull("Original user should exist", originalUser);
        
        String placeholder = dao.getPasswordPlaceholderById(originalUser.getUserId());
        Assert.assertNotNull("Password placeholder should not be null", placeholder);
        Assert.assertEquals("Password placeholder should be '********'", "********", placeholder);
    }

    @Test
    public void getPasswordPlaceholderByIdWithInvalidId() {
        String placeholder = dao.getPasswordPlaceholderById(-1);
        Assert.assertNull("Password placeholder should be null for invalid ID", placeholder);
    }

    @Test
    public void deleteUserWithValidId() {
        // First register a user
        dao.registerUser(correctFullName, correctEmail, correctPhone, correctGender, correctPassword, correctRole);
        UserDataModel originalUser = dao.findUserByEmail(correctEmail);
        Assert.assertNotNull("Original user should exist", originalUser);
        
        boolean result = dao.deleteUser(originalUser.getUserId());
        Assert.assertTrue("User deletion should succeed", result);
        
        UserDataModel deletedUser = dao.findUserById(originalUser.getUserId());
        Assert.assertNull("User should be null after deletion", deletedUser);
    }

    @Test
    public void deleteUserWithInvalidId() {
        boolean result = dao.deleteUser(-1);
        Assert.assertFalse("User deletion should fail with invalid ID", result);
    }
} 