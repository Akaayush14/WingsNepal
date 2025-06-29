package database;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;

public class DatabaseSetup {
    
    public static void createEmployeesTable() {
        Connection conn = null;
        Statement stmt = null;
        
        try {
            conn = MySqlConnection.getConnection();
            if (conn == null) {
                System.out.println("Failed to connect to database");
                return;
            }
            
            stmt = conn.createStatement();
            
            // Create employees table
            String createTableSQL = "CREATE TABLE IF NOT EXISTS employees (" +
                "emp_id INT AUTO_INCREMENT PRIMARY KEY," +
                "emp_full_name VARCHAR(100) NOT NULL," +
                "emp_email VARCHAR(100) UNIQUE NOT NULL," +
                "emp_role VARCHAR(50) NOT NULL," +
                "gender VARCHAR(10) NOT NULL," +
                "password VARCHAR(255) NOT NULL," +
                "job_title VARCHAR(100)," +
                "salary DECIMAL(10,2)," +
                "status VARCHAR(20) DEFAULT 'Active'," +
                "date_created TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                ")";
            
            stmt.executeUpdate(createTableSQL);
            System.out.println("✅ Employees table created successfully");
            
            // Migrate existing employees from users table
            String migrateSQL = "INSERT INTO employees (emp_full_name, emp_email, emp_role, gender, password, job_title, salary, status) " +
                "SELECT full_name, email, COALESCE(role, 'Employee') as emp_role, " +
                "COALESCE(gender, 'Male') as gender, password_hash, job_title, salary, " +
                "COALESCE(status, 'Active') as status FROM users WHERE role = 'Employee' " +
                "ON DUPLICATE KEY UPDATE emp_full_name = VALUES(emp_full_name), " +
                "emp_role = VALUES(emp_role), gender = VALUES(gender), " +
                "job_title = VALUES(job_title), salary = VALUES(salary), status = VALUES(status)";
            
            int migratedCount = stmt.executeUpdate(migrateSQL);
            System.out.println("✅ Migrated " + migratedCount + " employees to employees table");
            
        } catch (SQLException e) {
            System.err.println("❌ Error setting up employees table: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.err.println("Error closing database resources: " + e.getMessage());
            }
        }
    }
    
    public static void main(String[] args) {
        System.out.println("Setting up employees table...");
        createEmployeesTable();
        System.out.println("Database setup complete!");
    }
} 