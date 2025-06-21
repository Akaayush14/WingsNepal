package wingsnepal.dao;

import wingsnepal.model.Employee;
import wingsnepal.database.MySqlConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class EmployeeDao {

    public void insertEmployee(Employee emp) {
        String sql = "INSERT INTO employees (emp_id, emp_full_name, emp_email, emp_role, gender, password) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = new MySqlConnection().openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, emp.getEmpId()); // 👈 set random ID from form
            stmt.setString(2, emp.getFullName());
            stmt.setString(3, emp.getEmpEmail());
            stmt.setString(4, emp.getEmpRole());
            stmt.setString(5, emp.getGender());
            stmt.setString(6, emp.getPassword());

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Employee> getAllEmployees() {
        List<Employee> list = new ArrayList<>();
        String sql = "SELECT * FROM employees";
        try (Connection conn = new MySqlConnection().openConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                list.add(new Employee(
                    rs.getInt("emp_id"),
                    rs.getString("emp_full_name"),
                    rs.getString("emp_email"),
                    rs.getString("emp_role"),
                    rs.getString("gender"),
                    rs.getString("password")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void deleteEmployee(int empId) {
        String sql = "DELETE FROM employees WHERE emp_id = ?";
        try (Connection conn = new MySqlConnection().openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, empId);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

        public void updateEmployee(Employee emp) {
            String sql = "UPDATE employees SET emp_full_name = ?, emp_email = ?, emp_role = ?, gender = ?, password = ? WHERE emp_id = ?";

            try (Connection conn = new MySqlConnection().openConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setString(1, emp.getFullName());
                stmt.setString(2, emp.getEmpEmail());
                stmt.setString(3, emp.getEmpRole());
                stmt.setString(4, emp.getGender());
                stmt.setString(5, emp.getPassword());
                stmt.setInt(6, emp.getEmpId());

                stmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error updating employee: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        }


    public List<Employee> searchEmployeesByField(String column, String keyword, boolean isPartialMatch) {
        List<Employee> employees = new ArrayList<>();
        String sql = "SELECT * FROM employees WHERE ";

        if (isPartialMatch) {
            sql += "LOWER(" + column + ") LIKE ?";
            keyword = keyword.toLowerCase();
        } else {
            sql += column + " = ?";
        }

        try (Connection conn = new MySqlConnection().openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, keyword);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Employee emp = new Employee(
                    rs.getInt("emp_id"),
                    rs.getString("emp_full_name"),
                    rs.getString("emp_email"),
                    rs.getString("emp_role"),
                    rs.getString("gender"),
                    rs.getString("password")
                );
                employees.add(emp);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return employees;
    }




    public List<Employee> searchEmployeesByName(String keyword) {
        List<Employee> employees = new ArrayList<>();
        String sql = "SELECT * FROM employees WHERE LOWER(emp_full_name) LIKE ?";

        try (Connection conn = new MySqlConnection().openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + keyword.toLowerCase() + "%");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Employee emp = new Employee();
                emp.setEmpId(rs.getInt("emp_id"));
                emp.setFullName(rs.getString("emp_full_name"));
                emp.setEmpEmail(rs.getString("emp_email"));
                emp.setEmpRole(rs.getString("emp_role"));
                emp.setGender(rs.getString("gender"));
                emp.setPassword(rs.getString("password"));
                employees.add(emp);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return employees;
    }

        public Employee getEmployeeById(int empId) {
            String sql = "SELECT * FROM employees WHERE emp_id = ?";
            Employee emp = null;

            try (Connection conn = new MySqlConnection().openConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setInt(1, empId);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    emp = new Employee(
                        rs.getInt("emp_id"),
                        rs.getString("emp_full_name"),
                        rs.getString("emp_email"),
                        rs.getString("emp_role"),
                        rs.getString("gender"),
                        rs.getString("password")
                    );
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }

            return emp;
        }

}