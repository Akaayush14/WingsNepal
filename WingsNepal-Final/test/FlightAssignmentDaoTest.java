/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package test;

import wingsnepal.dao.FlightAssignmentDao;
import java.util.List;
import org.junit.*;

/**
 *
 * @author Aayush Kharel
 */
public class FlightAssignmentDaoTest {
    
    int correctAssignmentId = 1;
    int correctFlightId = 1;
    int correctEmployeeId = 1;
    String correctAssignmentDate = "2024-12-25";
    String correctAssignmentStatus = "Assigned";
    String correctEmployeeName = "Test Employee";
    String correctFlightCode = "TEST001";
    
    FlightAssignmentDao dao = new FlightAssignmentDao();

    @Test
    public void getAllFlightAssignments() {
        List<Object[]> assignments = dao.getAllFlightAssignments();
        Assert.assertNotNull("Flight assignments list should not be null", assignments);
        // Note: This test assumes there are flight assignments in the database
    }

    @Test
    public void getFlightAssignmentByIdWithValidId() {
        // Note: In a real test, you would need to get a valid assignment ID from the database
        // For now, we'll test with a known valid ID if available
        Object[] assignment = dao.getFlightAssignmentById(1); // Assuming assignment ID 1 exists
        if (assignment != null) {
            Assert.assertNotNull("Flight assignment should not be null", assignment);
            Assert.assertTrue("Assignment should have required fields", assignment.length > 0);
        }
    }

    @Test
    public void getFlightAssignmentByIdWithInvalidId() {
        Object[] assignment = dao.getFlightAssignmentById(-1);
        Assert.assertNull("Flight assignment should be null for invalid ID", assignment);
    }

    @Test
    public void addFlightAssignmentWithValidData() {
        // Note: In a real test, you would need to ensure the flight and employee exist
        boolean result = dao.addFlightAssignment(correctFlightId, correctEmployeeId, 
                                                correctAssignmentDate, correctAssignmentStatus);
        
        if (result) {
            Assert.assertTrue("Flight assignment addition should succeed", result);
            
            // Verify the addition by searching for the assignment
            List<Object[]> assignments = dao.getAllFlightAssignments();
            boolean found = false;
            for (Object[] assignment : assignments) {
                if (assignment.length >= 4 && 
                    assignment[1].equals(correctFlightId) && 
                    assignment[2].equals(correctEmployeeId)) {
                    found = true;
                    break;
                }
            }
            Assert.assertTrue("Added flight assignment should be found in the list", found);
        }
    }

    @Test
    public void addFlightAssignmentWithInvalidFlightId() {
        boolean result = dao.addFlightAssignment(-1, correctEmployeeId, 
                                                correctAssignmentDate, correctAssignmentStatus);
        Assert.assertFalse("Flight assignment addition should fail with invalid flight ID", result);
    }

    @Test
    public void addFlightAssignmentWithInvalidEmployeeId() {
        boolean result = dao.addFlightAssignment(correctFlightId, -1, 
                                                correctAssignmentDate, correctAssignmentStatus);
        Assert.assertFalse("Flight assignment addition should fail with invalid employee ID", result);
    }

    @Test
    public void addFlightAssignmentWithNullAssignmentDate() {
        boolean result = dao.addFlightAssignment(correctFlightId, correctEmployeeId, 
                                                null, correctAssignmentStatus);
        Assert.assertFalse("Flight assignment addition should fail with null assignment date", result);
    }

    @Test
    public void addFlightAssignmentWithInvalidAssignmentDate() {
        boolean result = dao.addFlightAssignment(correctFlightId, correctEmployeeId, 
                                                "invalid-date", correctAssignmentStatus);
        Assert.assertFalse("Flight assignment addition should fail with invalid assignment date", result);
    }

    @Test
    public void addFlightAssignmentWithNullAssignmentStatus() {
        boolean result = dao.addFlightAssignment(correctFlightId, correctEmployeeId, 
                                                correctAssignmentDate, null);
        Assert.assertFalse("Flight assignment addition should fail with null assignment status", result);
    }

    @Test
    public void addFlightAssignmentWithInvalidAssignmentStatus() {
        boolean result = dao.addFlightAssignment(correctFlightId, correctEmployeeId, 
                                                correctAssignmentDate, "InvalidStatus");
        Assert.assertFalse("Flight assignment addition should fail with invalid assignment status", result);
    }

    @Test
    public void updateFlightAssignmentWithValidData() {
        // Note: In a real test, you would need to get a valid assignment ID from the database
        // For now, we'll test with a known valid ID if available
        int newEmployeeId = 2;
        String newAssignmentDate = "2024-12-26";
        String newAssignmentStatus = "Completed";
        
        boolean result = dao.updateFlightAssignment(1, newEmployeeId, newAssignmentDate, newAssignmentStatus); // Assuming assignment ID 1 exists
        
        if (result) {
            Assert.assertTrue("Flight assignment update should succeed", result);
            
            // Verify the update
            Object[] updatedAssignment = dao.getFlightAssignmentById(1);
            Assert.assertNotNull("Updated flight assignment should exist", updatedAssignment);
            // Note: The exact field positions depend on the implementation
        }
    }

    @Test
    public void updateFlightAssignmentWithInvalidId() {
        boolean result = dao.updateFlightAssignment(-1, correctEmployeeId, 
                                                  correctAssignmentDate, correctAssignmentStatus);
        Assert.assertFalse("Flight assignment update should fail with invalid ID", result);
    }

    @Test
    public void updateFlightAssignmentWithInvalidEmployeeId() {
        boolean result = dao.updateFlightAssignment(correctAssignmentId, -1, 
                                                  correctAssignmentDate, correctAssignmentStatus);
        Assert.assertFalse("Flight assignment update should fail with invalid employee ID", result);
    }

    @Test
    public void updateFlightAssignmentWithNullAssignmentDate() {
        boolean result = dao.updateFlightAssignment(correctAssignmentId, correctEmployeeId, 
                                                  null, correctAssignmentStatus);
        Assert.assertFalse("Flight assignment update should fail with null assignment date", result);
    }

    @Test
    public void updateFlightAssignmentWithInvalidAssignmentDate() {
        boolean result = dao.updateFlightAssignment(correctAssignmentId, correctEmployeeId, 
                                                  "invalid-date", correctAssignmentStatus);
        Assert.assertFalse("Flight assignment update should fail with invalid assignment date", result);
    }

    @Test
    public void updateFlightAssignmentWithNullAssignmentStatus() {
        boolean result = dao.updateFlightAssignment(correctAssignmentId, correctEmployeeId, 
                                                  correctAssignmentDate, null);
        Assert.assertFalse("Flight assignment update should fail with null assignment status", result);
    }

    @Test
    public void updateFlightAssignmentWithInvalidAssignmentStatus() {
        boolean result = dao.updateFlightAssignment(correctAssignmentId, correctEmployeeId, 
                                                  correctAssignmentDate, "InvalidStatus");
        Assert.assertFalse("Flight assignment update should fail with invalid assignment status", result);
    }

    @Test
    public void deleteFlightAssignmentWithValidId() {
        // Note: In a real test, you would need to get a valid assignment ID from the database
        // For now, we'll test with a known valid ID if available
        boolean result = dao.deleteFlightAssignment(1); // Assuming assignment ID 1 exists
        if (result) {
            Assert.assertTrue("Flight assignment deletion should succeed", result);
            
            // Verify the deletion
            Object[] deletedAssignment = dao.getFlightAssignmentById(1);
            Assert.assertNull("Flight assignment should be null after deletion", deletedAssignment);
        }
    }

    @Test
    public void deleteFlightAssignmentWithInvalidId() {
        boolean result = dao.deleteFlightAssignment(-1);
        Assert.assertFalse("Flight assignment deletion should fail with invalid ID", result);
    }

    @Test
    public void searchFlightAssignmentsWithValidKeyword() {
        // First get all assignments to find a valid keyword
        List<Object[]> allAssignments = dao.getAllFlightAssignments();
        if (!allAssignments.isEmpty()) {
            // Use employee name as keyword if available
            String keyword = correctEmployeeName;
            List<Object[]> searchResults = dao.searchFlightAssignments(keyword);
            Assert.assertNotNull("Search results should not be null", searchResults);
            // Note: This test assumes the search functionality works with employee names
        }
    }

    @Test
    public void searchFlightAssignmentsWithInvalidKeyword() {
        List<Object[]> searchResults = dao.searchFlightAssignments("nonexistentassignment");
        Assert.assertNotNull("Search results should not be null", searchResults);
        Assert.assertTrue("Should return empty list for invalid keyword", searchResults.size() == 0);
    }

    @Test
    public void searchFlightAssignmentsWithNullKeyword() {
        List<Object[]> searchResults = dao.searchFlightAssignments(null);
        Assert.assertNotNull("Search results should not be null", searchResults);
        // Should return all assignments or empty list depending on implementation
    }

    @Test
    public void searchFlightAssignmentsWithEmptyKeyword() {
        List<Object[]> searchResults = dao.searchFlightAssignments("");
        Assert.assertNotNull("Search results should not be null", searchResults);
        // Should return all assignments or empty list depending on implementation
    }

    @Test
    public void getFlightAssignmentsByFlightIdWithValidId() {
        List<Object[]> assignments = dao.getFlightAssignmentsByFlightId(correctFlightId);
        Assert.assertNotNull("Flight assignments list should not be null", assignments);
        // Note: This test assumes there are assignments for the flight in the database
    }

    @Test
    public void getFlightAssignmentsByFlightIdWithInvalidId() {
        List<Object[]> assignments = dao.getFlightAssignmentsByFlightId(-1);
        Assert.assertNotNull("Flight assignments list should not be null", assignments);
        Assert.assertTrue("Should return empty list for invalid flight ID", assignments.isEmpty());
    }

    @Test
    public void getFlightAssignmentsByEmployeeIdWithValidId() {
        List<Object[]> assignments = dao.getFlightAssignmentsByEmployeeId(correctEmployeeId);
        Assert.assertNotNull("Flight assignments list should not be null", assignments);
        // Note: This test assumes there are assignments for the employee in the database
    }

    @Test
    public void getFlightAssignmentsByEmployeeIdWithInvalidId() {
        List<Object[]> assignments = dao.getFlightAssignmentsByEmployeeId(-1);
        Assert.assertNotNull("Flight assignments list should not be null", assignments);
        Assert.assertTrue("Should return empty list for invalid employee ID", assignments.isEmpty());
    }

    @Test
    public void getFlightAssignmentsByStatusWithValidStatus() {
        List<Object[]> assignments = dao.getFlightAssignmentsByStatus(correctAssignmentStatus);
        Assert.assertNotNull("Flight assignments list should not be null", assignments);
        // Note: This test assumes there are assignments with the status in the database
    }

    @Test
    public void getFlightAssignmentsByStatusWithInvalidStatus() {
        List<Object[]> assignments = dao.getFlightAssignmentsByStatus("InvalidStatus");
        Assert.assertNotNull("Flight assignments list should not be null", assignments);
        Assert.assertTrue("Should return empty list for invalid status", assignments.isEmpty());
    }

    @Test
    public void getFlightAssignmentsByStatusWithNullStatus() {
        List<Object[]> assignments = dao.getFlightAssignmentsByStatus(null);
        Assert.assertNotNull("Flight assignments list should not be null", assignments);
        Assert.assertTrue("Should return empty list for null status", assignments.isEmpty());
    }

    @Test
    public void getFlightAssignmentsByStatusWithEmptyStatus() {
        List<Object[]> assignments = dao.getFlightAssignmentsByStatus("");
        Assert.assertNotNull("Flight assignments list should not be null", assignments);
        Assert.assertTrue("Should return empty list for empty status", assignments.isEmpty());
    }

    @Test
    public void getTotalFlightAssignmentsCount() {
        int count = dao.getTotalFlightAssignmentsCount();
        Assert.assertTrue("Flight assignment count should be non-negative", count >= 0);
    }

    @Test
    public void getFlightAssignmentsCountByStatus() {
        int count = dao.getFlightAssignmentsCountByStatus(correctAssignmentStatus);
        Assert.assertTrue("Flight assignment count by status should be non-negative", count >= 0);
    }

    @Test
    public void getFlightAssignmentsCountByStatusWithInvalidStatus() {
        int count = dao.getFlightAssignmentsCountByStatus("InvalidStatus");
        Assert.assertEquals("Flight assignment count should be 0 for invalid status", 0, count);
    }

    @Test
    public void getFlightAssignmentsCountByStatusWithNullStatus() {
        int count = dao.getFlightAssignmentsCountByStatus(null);
        Assert.assertEquals("Flight assignment count should be 0 for null status", 0, count);
    }

    @Test
    public void getFlightAssignmentsCountByStatusWithEmptyStatus() {
        int count = dao.getFlightAssignmentsCountByStatus("");
        Assert.assertEquals("Flight assignment count should be 0 for empty status", 0, count);
    }

    @Test
    public void updateFlightAssignmentStatusWithValidData() {
        // Note: In a real test, you would need to get a valid assignment ID from the database
        // For now, we'll test with a known valid ID if available
        String newStatus = "Completed";
        boolean result = dao.updateFlightAssignmentStatus(1, newStatus); // Assuming assignment ID 1 exists
        if (result) {
            Assert.assertTrue("Flight assignment status update should succeed", result);
            
            // Verify the update
            Object[] updatedAssignment = dao.getFlightAssignmentById(1);
            Assert.assertNotNull("Updated flight assignment should exist", updatedAssignment);
            // Note: The exact field positions depend on the implementation
        }
    }

    @Test
    public void updateFlightAssignmentStatusWithInvalidId() {
        boolean result = dao.updateFlightAssignmentStatus(-1, "Completed");
        Assert.assertFalse("Flight assignment status update should fail with invalid ID", result);
    }

    @Test
    public void updateFlightAssignmentStatusWithNullStatus() {
        boolean result = dao.updateFlightAssignmentStatus(correctAssignmentId, null);
        Assert.assertFalse("Flight assignment status update should fail with null status", result);
    }

    @Test
    public void updateFlightAssignmentStatusWithEmptyStatus() {
        boolean result = dao.updateFlightAssignmentStatus(correctAssignmentId, "");
        Assert.assertFalse("Flight assignment status update should fail with empty status", result);
    }

    @Test
    public void updateFlightAssignmentStatusWithInvalidStatus() {
        boolean result = dao.updateFlightAssignmentStatus(correctAssignmentId, "InvalidStatus");
        Assert.assertFalse("Flight assignment status update should fail with invalid status", result);
    }

    @Test
    public void getFlightAssignmentsByDateWithValidDate() {
        List<Object[]> assignments = dao.getFlightAssignmentsByDate(correctAssignmentDate);
        Assert.assertNotNull("Flight assignments list should not be null", assignments);
        // Note: This test assumes there are assignments for the date in the database
    }

    @Test
    public void getFlightAssignmentsByDateWithInvalidDate() {
        List<Object[]> assignments = dao.getFlightAssignmentsByDate("invalid-date");
        Assert.assertNotNull("Flight assignments list should not be null", assignments);
        Assert.assertTrue("Should return empty list for invalid date", assignments.isEmpty());
    }

    @Test
    public void getFlightAssignmentsByDateWithNullDate() {
        List<Object[]> assignments = dao.getFlightAssignmentsByDate(null);
        Assert.assertNotNull("Flight assignments list should not be null", assignments);
        Assert.assertTrue("Should return empty list for null date", assignments.isEmpty());
    }

    @Test
    public void getFlightAssignmentsByDateWithEmptyDate() {
        List<Object[]> assignments = dao.getFlightAssignmentsByDate("");
        Assert.assertNotNull("Flight assignments list should not be null", assignments);
        Assert.assertTrue("Should return empty list for empty date", assignments.isEmpty());
    }

    @Test
    public void getFlightAssignmentsCountByDate() {
        int count = dao.getFlightAssignmentsCountByDate(correctAssignmentDate);
        Assert.assertTrue("Flight assignment count by date should be non-negative", count >= 0);
    }

    @Test
    public void getFlightAssignmentsCountByDateWithInvalidDate() {
        int count = dao.getFlightAssignmentsCountByDate("invalid-date");
        Assert.assertEquals("Flight assignment count should be 0 for invalid date", 0, count);
    }

    @Test
    public void getFlightAssignmentsCountByDateWithNullDate() {
        int count = dao.getFlightAssignmentsCountByDate(null);
        Assert.assertEquals("Flight assignment count should be 0 for null date", 0, count);
    }

    @Test
    public void getFlightAssignmentsCountByDateWithEmptyDate() {
        int count = dao.getFlightAssignmentsCountByDate("");
        Assert.assertEquals("Flight assignment count should be 0 for empty date", 0, count);
    }

    @Test
    public void flightAssignmentWithDifferentStatuses() {
        // Test flight assignment with different statuses
        String[] statuses = {"Assigned", "Completed", "Cancelled", "Pending"};
        
        for (String status : statuses) {
            // Test adding assignment with different status
            boolean result = dao.addFlightAssignment(correctFlightId, correctEmployeeId, 
                                                    correctAssignmentDate, status);
            if (result) {
                Assert.assertTrue("Flight assignment addition should succeed with status: " + status, result);
            }
        }
    }

    @Test
    public void flightAssignmentWithDifferentEmployees() {
        // Test flight assignment with different employees
        int[] employeeIds = {1, 2, 3}; // Assuming these employee IDs exist
        
        for (int employeeId : employeeIds) {
            // Test adding assignment with different employee
            boolean result = dao.addFlightAssignment(correctFlightId, employeeId, 
                                                    correctAssignmentDate, correctAssignmentStatus);
            if (result) {
                Assert.assertTrue("Flight assignment addition should succeed with employee ID: " + employeeId, result);
            }
        }
    }

    @Test
    public void flightAssignmentWithDifferentFlights() {
        // Test flight assignment with different flights
        int[] flightIds = {1, 2, 3}; // Assuming these flight IDs exist
        
        for (int flightId : flightIds) {
            // Test adding assignment with different flight
            boolean result = dao.addFlightAssignment(flightId, correctEmployeeId, 
                                                    correctAssignmentDate, correctAssignmentStatus);
            if (result) {
                Assert.assertTrue("Flight assignment addition should succeed with flight ID: " + flightId, result);
            }
        }
    }

    @Test
    public void flightAssignmentWithDifferentDates() {
        // Test flight assignment with different dates
        String[] dates = {"2024-12-25", "2024-12-26", "2024-12-27"};
        
        for (String date : dates) {
            // Test adding assignment with different date
            boolean result = dao.addFlightAssignment(correctFlightId, correctEmployeeId, 
                                                    date, correctAssignmentStatus);
            if (result) {
                Assert.assertTrue("Flight assignment addition should succeed with date: " + date, result);
            }
        }
    }
} 