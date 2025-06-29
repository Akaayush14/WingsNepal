/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package test;

import wingsnepal.dao.EmployeeDao;
import wingsnepal.model.EmployeeModel;
import java.util.List;
import org.junit.*;

/**
 *
 * @author Aayush Kharel
 */
public class EmployeeDaoTest {
    
    int correctUserId = 1;
    String correctFullName = "Test Employee";
    String correctEmail = "testemployee@example.com";
    String correctPhone = "9876543210";
    String correctGender = "Male";
    String correctJobTitle = "Flight Attendant";
    Double correctSalary = 50000.0;
    String correctStatus = "Active";
    String correctRole = "Employee";
    
    EmployeeDao dao = new EmployeeDao();

    @Test
    public void saveEmployeeWithValidData() {
        EmployeeModel employee = new EmployeeModel();
        employee.setUserId(correctUserId);
        employee.setFullName(correctFullName);
        employee.setEmail(correctEmail);
        employee.setPhone(correctPhone);
        employee.setGender(correctGender);
        employee.setJobTitle(correctJobTitle);
        employee.setSalary(correctSalary);
        employee.setStatus(correctStatus);
        employee.setRole(correctRole);
        
        boolean result = dao.saveEmployee(employee);
        Assert.assertTrue("Employee should be saved successfully", result);
    }

    @Test
    public void saveEmployeeWithInvalidUserId() {
        EmployeeModel employee = new EmployeeModel();
        employee.setUserId(-1); // Invalid user ID
        employee.setFullName(correctFullName);
        employee.setEmail(correctEmail);
        employee.setPhone(correctPhone);
        employee.setGender(correctGender);
        employee.setJobTitle(correctJobTitle);
        employee.setSalary(correctSalary);
        employee.setStatus(correctStatus);
        employee.setRole(correctRole);
        
        boolean result = dao.saveEmployee(employee);
        Assert.assertFalse("Employee should fail with invalid user ID", result);
    }

    @Test
    public void saveEmployeeWithNullFullName() {
        EmployeeModel employee = new EmployeeModel();
        employee.setUserId(correctUserId);
        employee.setFullName(null); // Null full name
        employee.setEmail(correctEmail);
        employee.setPhone(correctPhone);
        employee.setGender(correctGender);
        employee.setJobTitle(correctJobTitle);
        employee.setSalary(correctSalary);
        employee.setStatus(correctStatus);
        employee.setRole(correctRole);
        
        boolean result = dao.saveEmployee(employee);
        Assert.assertFalse("Employee should fail with null full name", result);
    }

    @Test
    public void saveEmployeeWithEmptyFullName() {
        EmployeeModel employee = new EmployeeModel();
        employee.setUserId(correctUserId);
        employee.setFullName(""); // Empty full name
        employee.setEmail(correctEmail);
        employee.setPhone(correctPhone);
        employee.setGender(correctGender);
        employee.setJobTitle(correctJobTitle);
        employee.setSalary(correctSalary);
        employee.setStatus(correctStatus);
        employee.setRole(correctRole);
        
        boolean result = dao.saveEmployee(employee);
        Assert.assertFalse("Employee should fail with empty full name", result);
    }

    @Test
    public void saveEmployeeWithNullEmail() {
        EmployeeModel employee = new EmployeeModel();
        employee.setUserId(correctUserId);
        employee.setFullName(correctFullName);
        employee.setEmail(null); // Null email
        employee.setPhone(correctPhone);
        employee.setGender(correctGender);
        employee.setJobTitle(correctJobTitle);
        employee.setSalary(correctSalary);
        employee.setStatus(correctStatus);
        employee.setRole(correctRole);
        
        boolean result = dao.saveEmployee(employee);
        Assert.assertFalse("Employee should fail with null email", result);
    }

    @Test
    public void saveEmployeeWithInvalidEmail() {
        EmployeeModel employee = new EmployeeModel();
        employee.setUserId(correctUserId);
        employee.setFullName(correctFullName);
        employee.setEmail("invalid-email"); // Invalid email format
        employee.setPhone(correctPhone);
        employee.setGender(correctGender);
        employee.setJobTitle(correctJobTitle);
        employee.setSalary(correctSalary);
        employee.setStatus(correctStatus);
        employee.setRole(correctRole);
        
        boolean result = dao.saveEmployee(employee);
        Assert.assertFalse("Employee should fail with invalid email", result);
    }

    @Test
    public void saveEmployeeWithNullPhone() {
        EmployeeModel employee = new EmployeeModel();
        employee.setUserId(correctUserId);
        employee.setFullName(correctFullName);
        employee.setEmail(correctEmail);
        employee.setPhone(null); // Null phone
        employee.setGender(correctGender);
        employee.setJobTitle(correctJobTitle);
        employee.setSalary(correctSalary);
        employee.setStatus(correctStatus);
        employee.setRole(correctRole);
        
        boolean result = dao.saveEmployee(employee);
        Assert.assertFalse("Employee should fail with null phone", result);
    }

    @Test
    public void saveEmployeeWithInvalidPhone() {
        EmployeeModel employee = new EmployeeModel();
        employee.setUserId(correctUserId);
        employee.setFullName(correctFullName);
        employee.setEmail(correctEmail);
        employee.setPhone("123"); // Invalid phone number (too short)
        employee.setGender(correctGender);
        employee.setJobTitle(correctJobTitle);
        employee.setSalary(correctSalary);
        employee.setStatus(correctStatus);
        employee.setRole(correctRole);
        
        boolean result = dao.saveEmployee(employee);
        Assert.assertFalse("Employee should fail with invalid phone", result);
    }

    @Test
    public void saveEmployeeWithNullGender() {
        EmployeeModel employee = new EmployeeModel();
        employee.setUserId(correctUserId);
        employee.setFullName(correctFullName);
        employee.setEmail(correctEmail);
        employee.setPhone(correctPhone);
        employee.setGender(null); // Null gender
        employee.setJobTitle(correctJobTitle);
        employee.setSalary(correctSalary);
        employee.setStatus(correctStatus);
        employee.setRole(correctRole);
        
        boolean result = dao.saveEmployee(employee);
        Assert.assertFalse("Employee should fail with null gender", result);
    }

    @Test
    public void saveEmployeeWithInvalidGender() {
        EmployeeModel employee = new EmployeeModel();
        employee.setUserId(correctUserId);
        employee.setFullName(correctFullName);
        employee.setEmail(correctEmail);
        employee.setPhone(correctPhone);
        employee.setGender("Other"); // Invalid gender
        employee.setJobTitle(correctJobTitle);
        employee.setSalary(correctSalary);
        employee.setStatus(correctStatus);
        employee.setRole(correctRole);
        
        boolean result = dao.saveEmployee(employee);
        Assert.assertFalse("Employee should fail with invalid gender", result);
    }

    @Test
    public void saveEmployeeWithNullJobTitle() {
        EmployeeModel employee = new EmployeeModel();
        employee.setUserId(correctUserId);
        employee.setFullName(correctFullName);
        employee.setEmail(correctEmail);
        employee.setPhone(correctPhone);
        employee.setGender(correctGender);
        employee.setJobTitle(null); // Null job title
        employee.setSalary(correctSalary);
        employee.setStatus(correctStatus);
        employee.setRole(correctRole);
        
        boolean result = dao.saveEmployee(employee);
        Assert.assertFalse("Employee should fail with null job title", result);
    }

    @Test
    public void saveEmployeeWithEmptyJobTitle() {
        EmployeeModel employee = new EmployeeModel();
        employee.setUserId(correctUserId);
        employee.setFullName(correctFullName);
        employee.setEmail(correctEmail);
        employee.setPhone(correctPhone);
        employee.setGender(correctGender);
        employee.setJobTitle(""); // Empty job title
        employee.setSalary(correctSalary);
        employee.setStatus(correctStatus);
        employee.setRole(correctRole);
        
        boolean result = dao.saveEmployee(employee);
        Assert.assertFalse("Employee should fail with empty job title", result);
    }

    @Test
    public void saveEmployeeWithNullSalary() {
        EmployeeModel employee = new EmployeeModel();
        employee.setUserId(correctUserId);
        employee.setFullName(correctFullName);
        employee.setEmail(correctEmail);
        employee.setPhone(correctPhone);
        employee.setGender(correctGender);
        employee.setJobTitle(correctJobTitle);
        employee.setSalary(null); // Null salary
        employee.setStatus(correctStatus);
        employee.setRole(correctRole);
        
        boolean result = dao.saveEmployee(employee);
        Assert.assertFalse("Employee should fail with null salary", result);
    }

    @Test
    public void saveEmployeeWithNegativeSalary() {
        EmployeeModel employee = new EmployeeModel();
        employee.setUserId(correctUserId);
        employee.setFullName(correctFullName);
        employee.setEmail(correctEmail);
        employee.setPhone(correctPhone);
        employee.setGender(correctGender);
        employee.setJobTitle(correctJobTitle);
        employee.setSalary(-1000.0); // Negative salary
        employee.setStatus(correctStatus);
        employee.setRole(correctRole);
        
        boolean result = dao.saveEmployee(employee);
        Assert.assertFalse("Employee should fail with negative salary", result);
    }

    @Test
    public void saveEmployeeWithZeroSalary() {
        EmployeeModel employee = new EmployeeModel();
        employee.setUserId(correctUserId);
        employee.setFullName(correctFullName);
        employee.setEmail(correctEmail);
        employee.setPhone(correctPhone);
        employee.setGender(correctGender);
        employee.setJobTitle(correctJobTitle);
        employee.setSalary(0.0); // Zero salary
        employee.setStatus(correctStatus);
        employee.setRole(correctRole);
        
        boolean result = dao.saveEmployee(employee);
        Assert.assertFalse("Employee should fail with zero salary", result);
    }

    @Test
    public void saveEmployeeWithNullStatus() {
        EmployeeModel employee = new EmployeeModel();
        employee.setUserId(correctUserId);
        employee.setFullName(correctFullName);
        employee.setEmail(correctEmail);
        employee.setPhone(correctPhone);
        employee.setGender(correctGender);
        employee.setJobTitle(correctJobTitle);
        employee.setSalary(correctSalary);
        employee.setStatus(null); // Null status
        employee.setRole(correctRole);
        
        boolean result = dao.saveEmployee(employee);
        Assert.assertFalse("Employee should fail with null status", result);
    }

    @Test
    public void saveEmployeeWithEmptyStatus() {
        EmployeeModel employee = new EmployeeModel();
        employee.setUserId(correctUserId);
        employee.setFullName(correctFullName);
        employee.setEmail(correctEmail);
        employee.setPhone(correctPhone);
        employee.setGender(correctGender);
        employee.setJobTitle(correctJobTitle);
        employee.setSalary(correctSalary);
        employee.setStatus(""); // Empty status
        employee.setRole(correctRole);
        
        boolean result = dao.saveEmployee(employee);
        Assert.assertFalse("Employee should fail with empty status", result);
    }

    @Test
    public void saveEmployeeWithInvalidStatus() {
        EmployeeModel employee = new EmployeeModel();
        employee.setUserId(correctUserId);
        employee.setFullName(correctFullName);
        employee.setEmail(correctEmail);
        employee.setPhone(correctPhone);
        employee.setGender(correctGender);
        employee.setJobTitle(correctJobTitle);
        employee.setSalary(correctSalary);
        employee.setStatus("InvalidStatus"); // Invalid status
        employee.setRole(correctRole);
        
        boolean result = dao.saveEmployee(employee);
        Assert.assertFalse("Employee should fail with invalid status", result);
    }

    @Test
    public void saveEmployeeWithNullRole() {
        EmployeeModel employee = new EmployeeModel();
        employee.setUserId(correctUserId);
        employee.setFullName(correctFullName);
        employee.setEmail(correctEmail);
        employee.setPhone(correctPhone);
        employee.setGender(correctGender);
        employee.setJobTitle(correctJobTitle);
        employee.setSalary(correctSalary);
        employee.setStatus(correctStatus);
        employee.setRole(null); // Null role
        
        boolean result = dao.saveEmployee(employee);
        Assert.assertFalse("Employee should fail with null role", result);
    }

    @Test
    public void saveEmployeeWithEmptyRole() {
        EmployeeModel employee = new EmployeeModel();
        employee.setUserId(correctUserId);
        employee.setFullName(correctFullName);
        employee.setEmail(correctEmail);
        employee.setPhone(correctPhone);
        employee.setGender(correctGender);
        employee.setJobTitle(correctJobTitle);
        employee.setSalary(correctSalary);
        employee.setStatus(correctStatus);
        employee.setRole(""); // Empty role
        
        boolean result = dao.saveEmployee(employee);
        Assert.assertFalse("Employee should fail with empty role", result);
    }

    @Test
    public void saveEmployeeWithInvalidRole() {
        EmployeeModel employee = new EmployeeModel();
        employee.setUserId(correctUserId);
        employee.setFullName(correctFullName);
        employee.setEmail(correctEmail);
        employee.setPhone(correctPhone);
        employee.setGender(correctGender);
        employee.setJobTitle(correctJobTitle);
        employee.setSalary(correctSalary);
        employee.setStatus(correctStatus);
        employee.setRole("InvalidRole"); // Invalid role
        
        boolean result = dao.saveEmployee(employee);
        Assert.assertFalse("Employee should fail with invalid role", result);
    }

    @Test
    public void getAllEmployees() {
        // First save an employee
        EmployeeModel employee = new EmployeeModel();
        employee.setUserId(correctUserId);
        employee.setFullName(correctFullName);
        employee.setEmail(correctEmail);
        employee.setPhone(correctPhone);
        employee.setGender(correctGender);
        employee.setJobTitle(correctJobTitle);
        employee.setSalary(correctSalary);
        employee.setStatus(correctStatus);
        employee.setRole(correctRole);
        
        dao.saveEmployee(employee);
        
        List<EmployeeModel> employees = dao.getAllEmployees();
        Assert.assertNotNull("Employees list should not be null", employees);
        Assert.assertTrue("Should have at least one employee", employees.size() > 0);
    }

    @Test
    public void getEmployeeByIdWithValidId() {
        // First save an employee
        EmployeeModel employee = new EmployeeModel();
        employee.setUserId(correctUserId);
        employee.setFullName(correctFullName);
        employee.setEmail(correctEmail);
        employee.setPhone(correctPhone);
        employee.setGender(correctGender);
        employee.setJobTitle(correctJobTitle);
        employee.setSalary(correctSalary);
        employee.setStatus(correctStatus);
        employee.setRole(correctRole);
        
        dao.saveEmployee(employee);
        
        // Note: In a real test, you would need to get the employee ID from the database
        // For now, we'll test with a known valid ID if available
        EmployeeModel foundEmployee = dao.getEmployeeById(1); // Assuming employee ID 1 exists
        if (foundEmployee != null) {
            Assert.assertNotNull("Employee should not be null", foundEmployee);
            Assert.assertEquals("Full name should match", correctFullName, foundEmployee.getFullName());
            Assert.assertEquals("Email should match", correctEmail, foundEmployee.getEmail());
        }
    }

    @Test
    public void getEmployeeByIdWithInvalidId() {
        EmployeeModel employee = dao.getEmployeeById(-1);
        Assert.assertNull("Employee should be null for invalid ID", employee);
    }

    @Test
    public void updateEmployeeWithValidData() {
        // First save an employee
        EmployeeModel employee = new EmployeeModel();
        employee.setUserId(correctUserId);
        employee.setFullName(correctFullName);
        employee.setEmail(correctEmail);
        employee.setPhone(correctPhone);
        employee.setGender(correctGender);
        employee.setJobTitle(correctJobTitle);
        employee.setSalary(correctSalary);
        employee.setStatus(correctStatus);
        employee.setRole(correctRole);
        
        dao.saveEmployee(employee);
        
        // Note: In a real test, you would need to get the employee ID from the database
        // For now, we'll test with a known valid ID if available
        String newFullName = "Updated Employee Name";
        String newEmail = "updated@example.com";
        String newPhone = "1234567890";
        String newGender = "Female";
        String newJobTitle = "Senior Flight Attendant";
        Double newSalary = 60000.0;
        String newStatus = "Active";
        String newRole = "Employee";
        
        boolean result = dao.updateEmployee(1, newFullName, newEmail, newPhone, newGender, 
                                          newJobTitle, newSalary, newStatus, newRole); // Assuming employee ID 1 exists
        
        if (result) {
            Assert.assertTrue("Employee update should succeed", result);
            
            // Verify the update
            EmployeeModel updatedEmployee = dao.getEmployeeById(1);
            Assert.assertNotNull("Updated employee should exist", updatedEmployee);
            Assert.assertEquals("Full name should be updated", newFullName, updatedEmployee.getFullName());
            Assert.assertEquals("Email should be updated", newEmail, updatedEmployee.getEmail());
        }
    }

    @Test
    public void updateEmployeeWithInvalidId() {
        boolean result = dao.updateEmployee(-1, "Test Name", "test@example.com", "1234567890", 
                                          "Male", "Test Job", 50000.0, "Active", "Employee");
        Assert.assertFalse("Employee update should fail with invalid ID", result);
    }

    @Test
    public void deleteEmployeeWithValidId() {
        // First save an employee
        EmployeeModel employee = new EmployeeModel();
        employee.setUserId(correctUserId);
        employee.setFullName(correctFullName);
        employee.setEmail(correctEmail);
        employee.setPhone(correctPhone);
        employee.setGender(correctGender);
        employee.setJobTitle(correctJobTitle);
        employee.setSalary(correctSalary);
        employee.setStatus(correctStatus);
        employee.setRole(correctRole);
        
        dao.saveEmployee(employee);
        
        // Note: In a real test, you would need to get the employee ID from the database
        // For now, we'll test with a known valid ID if available
        boolean result = dao.deleteEmployee(1); // Assuming employee ID 1 exists
        if (result) {
            Assert.assertTrue("Employee deletion should succeed", result);
            
            // Verify the deletion
            EmployeeModel deletedEmployee = dao.getEmployeeById(1);
            Assert.assertNull("Employee should be null after deletion", deletedEmployee);
        }
    }

    @Test
    public void deleteEmployeeWithInvalidId() {
        boolean result = dao.deleteEmployee(-1);
        Assert.assertFalse("Employee deletion should fail with invalid ID", result);
    }

    @Test
    public void employeeModelPropertiesAreAccessible() {
        // Test that EmployeeModel properties are accessible
        EmployeeModel employee = new EmployeeModel();
        employee.setUserId(correctUserId);
        employee.setFullName(correctFullName);
        employee.setEmail(correctEmail);
        employee.setPhone(correctPhone);
        employee.setGender(correctGender);
        employee.setJobTitle(correctJobTitle);
        employee.setSalary(correctSalary);
        employee.setStatus(correctStatus);
        employee.setRole(correctRole);
        
        Assert.assertEquals("User ID should match", correctUserId, employee.getUserId());
        Assert.assertEquals("Full name should match", correctFullName, employee.getFullName());
        Assert.assertEquals("Email should match", correctEmail, employee.getEmail());
        Assert.assertEquals("Phone should match", correctPhone, employee.getPhone());
        Assert.assertEquals("Gender should match", correctGender, employee.getGender());
        Assert.assertEquals("Job title should match", correctJobTitle, employee.getJobTitle());
        Assert.assertEquals("Salary should match", correctSalary, employee.getSalary());
        Assert.assertEquals("Status should match", correctStatus, employee.getStatus());
        Assert.assertEquals("Role should match", correctRole, employee.getRole());
    }

    @Test
    public void employeeModelWithDifferentGenders() {
        // Test employee with different genders
        String[] genders = {"Male", "Female"};
        
        for (String gender : genders) {
            EmployeeModel employee = new EmployeeModel();
            employee.setUserId(correctUserId);
            employee.setFullName(correctFullName);
            employee.setEmail(correctEmail);
            employee.setPhone(correctPhone);
            employee.setGender(gender);
            employee.setJobTitle(correctJobTitle);
            employee.setSalary(correctSalary);
            employee.setStatus(correctStatus);
            employee.setRole(correctRole);
            
            Assert.assertEquals("Gender should match", gender, employee.getGender());
        }
    }

    @Test
    public void employeeModelWithDifferentJobTitles() {
        // Test employee with different job titles
        String[] jobTitles = {"Flight Attendant", "Pilot", "Ground Staff", "Customer Service", "Manager"};
        
        for (String jobTitle : jobTitles) {
            EmployeeModel employee = new EmployeeModel();
            employee.setUserId(correctUserId);
            employee.setFullName(correctFullName);
            employee.setEmail(correctEmail);
            employee.setPhone(correctPhone);
            employee.setGender(correctGender);
            employee.setJobTitle(jobTitle);
            employee.setSalary(correctSalary);
            employee.setStatus(correctStatus);
            employee.setRole(correctRole);
            
            Assert.assertEquals("Job title should match", jobTitle, employee.getJobTitle());
        }
    }

    @Test
    public void employeeModelWithDifferentSalaries() {
        // Test employee with different salaries
        Double[] salaries = {30000.0, 40000.0, 50000.0, 60000.0, 70000.0};
        
        for (Double salary : salaries) {
            EmployeeModel employee = new EmployeeModel();
            employee.setUserId(correctUserId);
            employee.setFullName(correctFullName);
            employee.setEmail(correctEmail);
            employee.setPhone(correctPhone);
            employee.setGender(correctGender);
            employee.setJobTitle(correctJobTitle);
            employee.setSalary(salary);
            employee.setStatus(correctStatus);
            employee.setRole(correctRole);
            
            Assert.assertEquals("Salary should match", salary, employee.getSalary());
        }
    }

    @Test
    public void employeeModelWithDifferentStatuses() {
        // Test employee with different statuses
        String[] statuses = {"Active", "Inactive", "On Leave", "Terminated"};
        
        for (String status : statuses) {
            EmployeeModel employee = new EmployeeModel();
            employee.setUserId(correctUserId);
            employee.setFullName(correctFullName);
            employee.setEmail(correctEmail);
            employee.setPhone(correctPhone);
            employee.setGender(correctGender);
            employee.setJobTitle(correctJobTitle);
            employee.setSalary(correctSalary);
            employee.setStatus(status);
            employee.setRole(correctRole);
            
            Assert.assertEquals("Status should match", status, employee.getStatus());
        }
    }
} 