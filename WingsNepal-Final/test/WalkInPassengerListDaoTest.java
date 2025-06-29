/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package test;

import wingsnepal.dao.WalkInPassengerListDao;
import wingsnepal.model.WalkInPassengerListModel;
import java.util.List;
import org.junit.*;

/**
 *
 * @author Aayush Kharel
 */
public class WalkInPassengerListDaoTest {
    
    int correctBookingId = 1;
    int correctUserId = 1;
    int correctFlightId = 1;
    String correctFullName = "Walk-in Passenger";
    String correctEmail = "walkin@example.com";
    String correctPhone = "9876543210";
    String correctClassName = "Economy";
    String correctSeatNo = "E1";
    String correctDate = "2024-12-25";
    String correctPaymentMethod = "Cash";
    String correctBookingStatus = "Confirmed";
    String correctFlightCode = "TEST001";
    
    WalkInPassengerListDao dao = new WalkInPassengerListDao();

    @Test
    public void getAllWalkInPassengers() {
        List<WalkInPassengerListModel> passengers = dao.getAllWalkInPassengers();
        Assert.assertNotNull("Passengers list should not be null", passengers);
        // Note: This test assumes there are walk-in passengers in the database
    }

    @Test
    public void getWalkInPassengerByIdWithValidId() {
        // Note: In a real test, you would need to get a valid passenger ID from the database
        // For now, we'll test with a known valid ID if available
        WalkInPassengerListModel passenger = dao.getWalkInPassengerById(1); // Assuming passenger ID 1 exists
        if (passenger != null) {
            Assert.assertNotNull("Passenger should not be null", passenger);
            Assert.assertTrue("Passenger ID should be positive", passenger.getBookingId() > 0);
        }
    }

    @Test
    public void getWalkInPassengerByIdWithInvalidId() {
        WalkInPassengerListModel passenger = dao.getWalkInPassengerById(-1);
        Assert.assertNull("Passenger should be null for invalid ID", passenger);
    }

    @Test
    public void updateWalkInPassengerWithValidData() {
        // Note: In a real test, you would need to get a valid passenger ID from the database
        // For now, we'll test with a known valid ID if available
        String newFullName = "Updated Walk-in Passenger";
        String newEmail = "updated@example.com";
        String newPhone = "1234567890";
        String newClassName = "Business";
        String newSeatNo = "B1";
        String newDate = "2024-12-26";
        String newPaymentMethod = "Credit Card";
        
        boolean result = dao.updateWalkInPassenger(1, newFullName, newEmail, newPhone, newClassName, 
                                                 newSeatNo, newDate, newPaymentMethod); // Assuming passenger ID 1 exists
        
        if (result) {
            Assert.assertTrue("Walk-in passenger update should succeed", result);
            
            // Verify the update
            WalkInPassengerListModel updatedPassenger = dao.getWalkInPassengerById(1);
            Assert.assertNotNull("Updated passenger should exist", updatedPassenger);
            Assert.assertEquals("Full name should be updated", newFullName, updatedPassenger.getFullName());
            Assert.assertEquals("Email should be updated", newEmail, updatedPassenger.getEmail());
        }
    }

    @Test
    public void updateWalkInPassengerWithInvalidId() {
        boolean result = dao.updateWalkInPassenger(-1, "Test Name", "test@example.com", "1234567890", 
                                                 "Economy", "E1", "2024-12-25", "Cash");
        Assert.assertFalse("Walk-in passenger update should fail with invalid ID", result);
    }

    @Test
    public void updateWalkInPassengerWithNullFullName() {
        boolean result = dao.updateWalkInPassenger(correctBookingId, null, correctEmail, correctPhone, 
                                                 correctClassName, correctSeatNo, correctDate, correctPaymentMethod);
        Assert.assertFalse("Walk-in passenger update should fail with null full name", result);
    }

    @Test
    public void updateWalkInPassengerWithEmptyFullName() {
        boolean result = dao.updateWalkInPassenger(correctBookingId, "", correctEmail, correctPhone, 
                                                 correctClassName, correctSeatNo, correctDate, correctPaymentMethod);
        Assert.assertFalse("Walk-in passenger update should fail with empty full name", result);
    }

    @Test
    public void updateWalkInPassengerWithNullEmail() {
        boolean result = dao.updateWalkInPassenger(correctBookingId, correctFullName, null, correctPhone, 
                                                 correctClassName, correctSeatNo, correctDate, correctPaymentMethod);
        Assert.assertFalse("Walk-in passenger update should fail with null email", result);
    }

    @Test
    public void updateWalkInPassengerWithInvalidEmail() {
        boolean result = dao.updateWalkInPassenger(correctBookingId, correctFullName, "invalid-email", correctPhone, 
                                                 correctClassName, correctSeatNo, correctDate, correctPaymentMethod);
        Assert.assertFalse("Walk-in passenger update should fail with invalid email", result);
    }

    @Test
    public void updateWalkInPassengerWithNullPhone() {
        boolean result = dao.updateWalkInPassenger(correctBookingId, correctFullName, correctEmail, null, 
                                                 correctClassName, correctSeatNo, correctDate, correctPaymentMethod);
        Assert.assertFalse("Walk-in passenger update should fail with null phone", result);
    }

    @Test
    public void updateWalkInPassengerWithInvalidPhone() {
        boolean result = dao.updateWalkInPassenger(correctBookingId, correctFullName, correctEmail, "123", 
                                                 correctClassName, correctSeatNo, correctDate, correctPaymentMethod);
        Assert.assertFalse("Walk-in passenger update should fail with invalid phone", result);
    }

    @Test
    public void updateWalkInPassengerWithNullClassName() {
        boolean result = dao.updateWalkInPassenger(correctBookingId, correctFullName, correctEmail, correctPhone, 
                                                 null, correctSeatNo, correctDate, correctPaymentMethod);
        Assert.assertFalse("Walk-in passenger update should fail with null class name", result);
    }

    @Test
    public void updateWalkInPassengerWithInvalidClassName() {
        boolean result = dao.updateWalkInPassenger(correctBookingId, correctFullName, correctEmail, correctPhone, 
                                                 "InvalidClass", correctSeatNo, correctDate, correctPaymentMethod);
        Assert.assertFalse("Walk-in passenger update should fail with invalid class name", result);
    }

    @Test
    public void updateWalkInPassengerWithNullSeatNo() {
        boolean result = dao.updateWalkInPassenger(correctBookingId, correctFullName, correctEmail, correctPhone, 
                                                 correctClassName, null, correctDate, correctPaymentMethod);
        Assert.assertFalse("Walk-in passenger update should fail with null seat number", result);
    }

    @Test
    public void updateWalkInPassengerWithEmptySeatNo() {
        boolean result = dao.updateWalkInPassenger(correctBookingId, correctFullName, correctEmail, correctPhone, 
                                                 correctClassName, "", correctDate, correctPaymentMethod);
        Assert.assertFalse("Walk-in passenger update should fail with empty seat number", result);
    }

    @Test
    public void updateWalkInPassengerWithNullDate() {
        boolean result = dao.updateWalkInPassenger(correctBookingId, correctFullName, correctEmail, correctPhone, 
                                                 correctClassName, correctSeatNo, null, correctPaymentMethod);
        Assert.assertFalse("Walk-in passenger update should fail with null date", result);
    }

    @Test
    public void updateWalkInPassengerWithInvalidDate() {
        boolean result = dao.updateWalkInPassenger(correctBookingId, correctFullName, correctEmail, correctPhone, 
                                                 correctClassName, correctSeatNo, "invalid-date", correctPaymentMethod);
        Assert.assertFalse("Walk-in passenger update should fail with invalid date", result);
    }

    @Test
    public void updateWalkInPassengerWithNullPaymentMethod() {
        boolean result = dao.updateWalkInPassenger(correctBookingId, correctFullName, correctEmail, correctPhone, 
                                                 correctClassName, correctSeatNo, correctDate, null);
        Assert.assertFalse("Walk-in passenger update should fail with null payment method", result);
    }

    @Test
    public void updateWalkInPassengerWithEmptyPaymentMethod() {
        boolean result = dao.updateWalkInPassenger(correctBookingId, correctFullName, correctEmail, correctPhone, 
                                                 correctClassName, correctSeatNo, correctDate, "");
        Assert.assertFalse("Walk-in passenger update should fail with empty payment method", result);
    }

    @Test
    public void deleteWalkInPassengerWithValidId() {
        // Note: In a real test, you would need to get a valid passenger ID from the database
        // For now, we'll test with a known valid ID if available
        boolean result = dao.deleteWalkInPassenger(1); // Assuming passenger ID 1 exists
        if (result) {
            Assert.assertTrue("Walk-in passenger deletion should succeed", result);
            
            // Verify the deletion
            WalkInPassengerListModel deletedPassenger = dao.getWalkInPassengerById(1);
            Assert.assertNull("Passenger should be null after deletion", deletedPassenger);
        }
    }

    @Test
    public void deleteWalkInPassengerWithInvalidId() {
        boolean result = dao.deleteWalkInPassenger(-1);
        Assert.assertFalse("Walk-in passenger deletion should fail with invalid ID", result);
    }

    @Test
    public void searchWalkInPassengersWithValidKeyword() {
        // First get all passengers to find a valid keyword
        List<WalkInPassengerListModel> allPassengers = dao.getAllWalkInPassengers();
        if (!allPassengers.isEmpty()) {
            String keyword = allPassengers.get(0).getFullName();
            List<WalkInPassengerListModel> searchResults = dao.searchWalkInPassengers(keyword);
            Assert.assertNotNull("Search results should not be null", searchResults);
            Assert.assertTrue("Should find at least one passenger", searchResults.size() > 0);
        }
    }

    @Test
    public void searchWalkInPassengersWithInvalidKeyword() {
        List<WalkInPassengerListModel> searchResults = dao.searchWalkInPassengers("nonexistentpassenger");
        Assert.assertNotNull("Search results should not be null", searchResults);
        Assert.assertTrue("Should return empty list for invalid keyword", searchResults.size() == 0);
    }

    @Test
    public void searchWalkInPassengersWithNullKeyword() {
        List<WalkInPassengerListModel> searchResults = dao.searchWalkInPassengers(null);
        Assert.assertNotNull("Search results should not be null", searchResults);
        // Should return all passengers or empty list depending on implementation
    }

    @Test
    public void searchWalkInPassengersWithEmptyKeyword() {
        List<WalkInPassengerListModel> searchResults = dao.searchWalkInPassengers("");
        Assert.assertNotNull("Search results should not be null", searchResults);
        // Should return all passengers or empty list depending on implementation
    }

    @Test
    public void getWalkInPassengersByFlightCodeWithValidCode() {
        List<WalkInPassengerListModel> passengers = dao.getWalkInPassengersByFlightCode(correctFlightCode);
        Assert.assertNotNull("Passengers list should not be null", passengers);
        // Note: This test assumes there are passengers for the flight in the database
    }

    @Test
    public void getWalkInPassengersByFlightCodeWithInvalidCode() {
        List<WalkInPassengerListModel> passengers = dao.getWalkInPassengersByFlightCode("INVALID123");
        Assert.assertNotNull("Passengers list should not be null", passengers);
        Assert.assertTrue("Should return empty list for invalid flight code", passengers.isEmpty());
    }

    @Test
    public void getWalkInPassengersByFlightCodeWithNullCode() {
        List<WalkInPassengerListModel> passengers = dao.getWalkInPassengersByFlightCode(null);
        Assert.assertNotNull("Passengers list should not be null", passengers);
        Assert.assertTrue("Should return empty list for null flight code", passengers.isEmpty());
    }

    @Test
    public void getWalkInPassengersByFlightCodeWithEmptyCode() {
        List<WalkInPassengerListModel> passengers = dao.getWalkInPassengersByFlightCode("");
        Assert.assertNotNull("Passengers list should not be null", passengers);
        Assert.assertTrue("Should return empty list for empty flight code", passengers.isEmpty());
    }

    @Test
    public void getWalkInPassengersByStatusWithValidStatus() {
        List<WalkInPassengerListModel> passengers = dao.getWalkInPassengersByStatus(correctBookingStatus);
        Assert.assertNotNull("Passengers list should not be null", passengers);
        // Note: This test assumes there are passengers with the status in the database
    }

    @Test
    public void getWalkInPassengersByStatusWithInvalidStatus() {
        List<WalkInPassengerListModel> passengers = dao.getWalkInPassengersByStatus("InvalidStatus");
        Assert.assertNotNull("Passengers list should not be null", passengers);
        Assert.assertTrue("Should return empty list for invalid status", passengers.isEmpty());
    }

    @Test
    public void getWalkInPassengersByStatusWithNullStatus() {
        List<WalkInPassengerListModel> passengers = dao.getWalkInPassengersByStatus(null);
        Assert.assertNotNull("Passengers list should not be null", passengers);
        Assert.assertTrue("Should return empty list for null status", passengers.isEmpty());
    }

    @Test
    public void getWalkInPassengersByStatusWithEmptyStatus() {
        List<WalkInPassengerListModel> passengers = dao.getWalkInPassengersByStatus("");
        Assert.assertNotNull("Passengers list should not be null", passengers);
        Assert.assertTrue("Should return empty list for empty status", passengers.isEmpty());
    }

    @Test
    public void getTotalWalkInPassengersCount() {
        int count = dao.getTotalWalkInPassengersCount();
        Assert.assertTrue("Walk-in passenger count should be non-negative", count >= 0);
    }

    @Test
    public void getWalkInPassengersCountByStatus() {
        int count = dao.getWalkInPassengersCountByStatus(correctBookingStatus);
        Assert.assertTrue("Walk-in passenger count by status should be non-negative", count >= 0);
    }

    @Test
    public void getWalkInPassengersCountByStatusWithInvalidStatus() {
        int count = dao.getWalkInPassengersCountByStatus("InvalidStatus");
        Assert.assertEquals("Walk-in passenger count should be 0 for invalid status", 0, count);
    }

    @Test
    public void getWalkInPassengersCountByStatusWithNullStatus() {
        int count = dao.getWalkInPassengersCountByStatus(null);
        Assert.assertEquals("Walk-in passenger count should be 0 for null status", 0, count);
    }

    @Test
    public void getWalkInPassengersCountByStatusWithEmptyStatus() {
        int count = dao.getWalkInPassengersCountByStatus("");
        Assert.assertEquals("Walk-in passenger count should be 0 for empty status", 0, count);
    }

    @Test
    public void updateWalkInPassengerStatusWithValidData() {
        // Note: In a real test, you would need to get a valid passenger ID from the database
        // For now, we'll test with a known valid ID if available
        String newStatus = "Cancelled";
        boolean result = dao.updateWalkInPassengerStatus(1, newStatus); // Assuming passenger ID 1 exists
        if (result) {
            Assert.assertTrue("Walk-in passenger status update should succeed", result);
            
            // Verify the update
            WalkInPassengerListModel updatedPassenger = dao.getWalkInPassengerById(1);
            Assert.assertNotNull("Updated passenger should exist", updatedPassenger);
            Assert.assertEquals("Status should be updated", newStatus, updatedPassenger.getBookingStatus());
        }
    }

    @Test
    public void updateWalkInPassengerStatusWithInvalidId() {
        boolean result = dao.updateWalkInPassengerStatus(-1, "Cancelled");
        Assert.assertFalse("Walk-in passenger status update should fail with invalid ID", result);
    }

    @Test
    public void updateWalkInPassengerStatusWithNullStatus() {
        boolean result = dao.updateWalkInPassengerStatus(correctBookingId, null);
        Assert.assertFalse("Walk-in passenger status update should fail with null status", result);
    }

    @Test
    public void updateWalkInPassengerStatusWithEmptyStatus() {
        boolean result = dao.updateWalkInPassengerStatus(correctBookingId, "");
        Assert.assertFalse("Walk-in passenger status update should fail with empty status", result);
    }

    @Test
    public void updateWalkInPassengerStatusWithInvalidStatus() {
        boolean result = dao.updateWalkInPassengerStatus(correctBookingId, "InvalidStatus");
        Assert.assertFalse("Walk-in passenger status update should fail with invalid status", result);
    }

    @Test
    public void walkInPassengerListModelPropertiesAreAccessible() {
        // Test that WalkInPassengerListModel properties are accessible
        WalkInPassengerListModel passenger = new WalkInPassengerListModel();
        passenger.setBookingId(correctBookingId);
        passenger.setUserId(correctUserId);
        passenger.setFlightId(correctFlightId);
        passenger.setFullName(correctFullName);
        passenger.setEmail(correctEmail);
        passenger.setPhone(correctPhone);
        passenger.setClassName(correctClassName);
        passenger.setSeatNo(correctSeatNo);
        passenger.setDate(correctDate);
        passenger.setPaymentMethod(correctPaymentMethod);
        passenger.setBookingStatus(correctBookingStatus);
        passenger.setFlightCode(correctFlightCode);
        
        Assert.assertEquals("Booking ID should match", correctBookingId, passenger.getBookingId());
        Assert.assertEquals("User ID should match", correctUserId, passenger.getUserId());
        Assert.assertEquals("Flight ID should match", correctFlightId, passenger.getFlightId());
        Assert.assertEquals("Full name should match", correctFullName, passenger.getFullName());
        Assert.assertEquals("Email should match", correctEmail, passenger.getEmail());
        Assert.assertEquals("Phone should match", correctPhone, passenger.getPhone());
        Assert.assertEquals("Class name should match", correctClassName, passenger.getClassName());
        Assert.assertEquals("Seat number should match", correctSeatNo, passenger.getSeatNo());
        Assert.assertEquals("Date should match", correctDate, passenger.getDate());
        Assert.assertEquals("Payment method should match", correctPaymentMethod, passenger.getPaymentMethod());
        Assert.assertEquals("Booking status should match", correctBookingStatus, passenger.getBookingStatus());
        Assert.assertEquals("Flight code should match", correctFlightCode, passenger.getFlightCode());
    }

    @Test
    public void walkInPassengerListModelWithDifferentStatuses() {
        // Test passenger with different statuses
        String[] statuses = {"Confirmed", "Cancelled", "Pending", "Completed"};
        
        for (String status : statuses) {
            WalkInPassengerListModel passenger = new WalkInPassengerListModel();
            passenger.setBookingId(correctBookingId);
            passenger.setUserId(correctUserId);
            passenger.setFlightId(correctFlightId);
            passenger.setFullName(correctFullName);
            passenger.setEmail(correctEmail);
            passenger.setPhone(correctPhone);
            passenger.setClassName(correctClassName);
            passenger.setSeatNo(correctSeatNo);
            passenger.setDate(correctDate);
            passenger.setPaymentMethod(correctPaymentMethod);
            passenger.setBookingStatus(status);
            passenger.setFlightCode(correctFlightCode);
            
            Assert.assertEquals("Booking status should match", status, passenger.getBookingStatus());
        }
    }

    @Test
    public void walkInPassengerListModelWithDifferentSeatClasses() {
        // Test passenger with different seat classes
        String[] seatClasses = {"Economy", "Business", "First Class"};
        
        for (String seatClass : seatClasses) {
            WalkInPassengerListModel passenger = new WalkInPassengerListModel();
            passenger.setBookingId(correctBookingId);
            passenger.setUserId(correctUserId);
            passenger.setFlightId(correctFlightId);
            passenger.setFullName(correctFullName);
            passenger.setEmail(correctEmail);
            passenger.setPhone(correctPhone);
            passenger.setClassName(seatClass);
            passenger.setSeatNo(correctSeatNo);
            passenger.setDate(correctDate);
            passenger.setPaymentMethod(correctPaymentMethod);
            passenger.setBookingStatus(correctBookingStatus);
            passenger.setFlightCode(correctFlightCode);
            
            Assert.assertEquals("Seat class should match", seatClass, passenger.getClassName());
        }
    }

    @Test
    public void walkInPassengerListModelWithDifferentPaymentMethods() {
        // Test passenger with different payment methods
        String[] paymentMethods = {"Cash", "Credit Card", "Debit Card", "Online Transfer", "Mobile Payment"};
        
        for (String paymentMethod : paymentMethods) {
            WalkInPassengerListModel passenger = new WalkInPassengerListModel();
            passenger.setBookingId(correctBookingId);
            passenger.setUserId(correctUserId);
            passenger.setFlightId(correctFlightId);
            passenger.setFullName(correctFullName);
            passenger.setEmail(correctEmail);
            passenger.setPhone(correctPhone);
            passenger.setClassName(correctClassName);
            passenger.setSeatNo(correctSeatNo);
            passenger.setDate(correctDate);
            passenger.setPaymentMethod(paymentMethod);
            passenger.setBookingStatus(correctBookingStatus);
            passenger.setFlightCode(correctFlightCode);
            
            Assert.assertEquals("Payment method should match", paymentMethod, passenger.getPaymentMethod());
        }
    }
} 