package wingsnepal.dao;

import java.sql.*;
import wingsnepal.model.EmployeeModel;
import database.MySqlConnection;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDao {
    
    public List<EmployeeModel> getAllEmployees() {
        List<EmployeeModel> employees = new ArrayList<>();
        String sql = "SELECT * FROM employees";
        
        try (Connection conn = MySqlConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {
            
            while (rs.next()) {
                EmployeeModel emp = new EmployeeModel(
                    rs.getInt("emp_id"),
                    rs.getString("emp_full_name"),
                    rs.getString("emp_email"),
                    rs.getString("emp_role"),
                    rs.getString("gender"),
                    rs.getString("password"),
                    rs.getString("job_title"),
                    rs.getObject("salary") != null ? rs.getDouble("salary") : null,
                    rs.getString("status")
                );
                employees.add(emp);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return employees;
    }
    
    public EmployeeModel getEmployeeById(int empId) {
        String sql = "SELECT * FROM employees WHERE emp_id = ?";
        
        try (Connection conn = MySqlConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            
            pst.setInt(1, empId);
            ResultSet rs = pst.executeQuery();
            
            if (rs.next()) {
                return new EmployeeModel(
                    rs.getInt("emp_id"),
                    rs.getString("emp_full_name"),
                    rs.getString("emp_email"),
                    rs.getString("emp_role"),
                    rs.getString("gender"),
                    rs.getString("password"),
                    rs.getString("job_title"),
                    rs.getObject("salary") != null ? rs.getDouble("salary") : null,
                    rs.getString("status")
                );
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    public boolean addEmployee(EmployeeModel employee) {
        String sql = "INSERT INTO employees (emp_full_name, emp_email, emp_role, gender, password, job_title, salary, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = MySqlConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            
            pst.setString(1, employee.getFullName());
            pst.setString(2, employee.getEmail());
            pst.setString(3, employee.getRole());
            pst.setString(4, employee.getGender());
            pst.setString(5, employee.getPassword());
            pst.setString(6, employee.getJobTitle());
            if (employee.getSalary() != null) {
                pst.setDouble(7, employee.getSalary());
            } else {
                pst.setNull(7, java.sql.Types.DOUBLE);
            }
            pst.setString(8, employee.getStatus());
            
            return pst.executeUpdate() > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean updateEmployee(EmployeeModel employee) {
        String sql = "UPDATE employees SET emp_full_name = ?, emp_email = ?, emp_role = ?, gender = ?, password = ?, job_title = ?, salary = ?, status = ? WHERE emp_id = ?";
        
        try (Connection conn = MySqlConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            
            pst.setString(1, employee.getFullName());
            pst.setString(2, employee.getEmail());
            pst.setString(3, employee.getRole());
            pst.setString(4, employee.getGender());
            pst.setString(5, employee.getPassword());
            pst.setString(6, employee.getJobTitle());
            if (employee.getSalary() != null) {
                pst.setDouble(7, employee.getSalary());
            } else {
                pst.setNull(7, java.sql.Types.DOUBLE);
            }
            pst.setString(8, employee.getStatus());
            pst.setInt(9, employee.getEmpId());
            
            return pst.executeUpdate() > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean deleteEmployee(int empId) {
        String sql = "DELETE FROM employees WHERE emp_id = ?";
        
        try (Connection conn = MySqlConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            
            pst.setInt(1, empId);
            return pst.executeUpdate() > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
} 