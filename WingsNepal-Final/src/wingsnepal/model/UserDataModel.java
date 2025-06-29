/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package wingsnepal.model;

/**
 * Represents a logged-in user's data.
 * This object is created after successful authentication.
 * It does NOT contain a password.
 * @author Aayush Kharel
 */
public class UserDataModel {
    private int userId;
    private String fullName;
    private String email;
    private String phone;
    private String role;
    private String jobTitle;
    private Double salary;
    private String status;
    private java.sql.Date dateJoined;
    private String gender;

    public UserDataModel(int userId, String fullName, String email, String role) {
        this.userId = userId;
        this.fullName = fullName;
        this.email = email;
        this.role = role;
    }

    // Constructor with phone number
    public UserDataModel(int userId, String fullName, String email, String phone, String role) {
        this.userId = userId;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.role = role;
    }

    // Full constructor for employees
    public UserDataModel(int userId, String fullName, String email, String phone, String role, String jobTitle, Double salary, String status, java.sql.Date dateJoined, String gender) {
        this.userId = userId;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.role = role;
        this.jobTitle = jobTitle;
        this.salary = salary;
        this.status = status;
        this.dateJoined = dateJoined;
        this.gender = gender;
    }

    // Getters
    public int getUserId() { return userId; }
    public String getFullName() { return fullName; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public String getRole() { return role; }
    public String getJobTitle() { return jobTitle; }
    public Double getSalary() { return salary; }
    public String getStatus() { return status; }
    public java.sql.Date getDateJoined() { return dateJoined; }
    public String getGender() { return gender; }

    // Setters
    public void setPhone(String phone) { this.phone = phone; }
    public void setJobTitle(String jobTitle) { this.jobTitle = jobTitle; }
    public void setSalary(Double salary) { this.salary = salary; }
    public void setStatus(String status) { this.status = status; }
    public void setDateJoined(java.sql.Date dateJoined) { this.dateJoined = dateJoined; }

    // No password getter or field.
}
