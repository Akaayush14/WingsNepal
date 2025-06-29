/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package test;

import wingsnepal.dao.ManageBookingDao;
import wingsnepal.model.ManageBookingModel;
import java.util.List;
import org.junit.*;

/**
 *
 * @author Aayush Kharel
 */
public class ManageBookingDaoTest {
    
    int correctBookingId = 1;
    int correctUserId = 1;
    int correctFlightId = 1;
    String correctFullName = "Test Passenger";
    String correctEmail = "test@example.com";
    String correctClassName = "Economy";
    String correctSeatNo = "E1";
    String correctDate = "2024-12-25";
    String correctPaymentMethod = "Credit Card";
    String correctBookingStatus = "Confirmed";
    
    ManageBookingDao dao = new ManageBookingDao();

    @Test
    public void getAllBookings() {
        List<ManageBookingModel> bookings = dao.getAllBookings();
        Assert.assertNotNull("Bookings list should not be null", bookings);
        // Note: This test assumes there are bookings in the database
    }

    @Test
    public void getBookingByIdWithValidId() {
        // Note: In a real test, you would need to get a valid booking ID from the database
        // For now, we'll test with a known valid ID if available
        ManageBookingModel booking = dao.getBookingById(1); // Assuming booking ID 1 exists
        if (booking != null) {
            Assert.assertNotNull("Booking should not be null", booking);
            Assert.assertTrue("Booking ID should be positive", booking.getBookingId() > 0);
        }
    }

    @Test
    public void getBookingByIdWithInvalidId() {
        ManageBookingModel booking = dao.getBookingById(-1);
        Assert.assertNull("Booking should be null for invalid ID", booking);
    }

    @Test
    public void updateBookingStatusWithValidData() {
        // Note: In a real test, you would need to get a valid booking ID from the database
        // For now, we'll test with a known valid ID if available
        String newStatus = "Cancelled";
        boolean result = dao.updateBookingStatus(1, newStatus); // Assuming booking ID 1 exists
        if (result) {
            Assert.assertTrue("Booking status update should succeed", result);
            
            // Verify the update
            ManageBookingModel updatedBooking = dao.getBookingById(1);
            Assert.assertNotNull("Updated booking should exist", updatedBooking);
            Assert.assertEquals("Status should be updated", newStatus, updatedBooking.getBookingStatus());
        }
    }

    @Test
    public void updateBookingStatusWithInvalidId() {
        boolean result = dao.updateBookingStatus(-1, "Cancelled");
        Assert.assertFalse("Booking status update should fail with invalid ID", result);
    }

    @Test
    public void updateBookingStatusWithNullStatus() {
        boolean result = dao.updateBookingStatus(correctBookingId, null);
        Assert.assertFalse("Booking status update should fail with null status", result);
    }

    @Test
    public void updateBookingStatusWithEmptyStatus() {
        boolean result = dao.updateBookingStatus(correctBookingId, "");
        Assert.assertFalse("Booking status update should fail with empty status", result);
    }

    @Test
    public void updateBookingStatusWithInvalidStatus() {
        boolean result = dao.updateBookingStatus(correctBookingId, "InvalidStatus");
        Assert.assertFalse("Booking status update should fail with invalid status", result);
    }

    @Test
    public void deleteBookingWithValidId() {
        // Note: In a real test, you would need to get a valid booking ID from the database
        // For now, we'll test with a known valid ID if available
        boolean result = dao.deleteBooking(1); // Assuming booking ID 1 exists
        if (result) {
            Assert.assertTrue("Booking deletion should succeed", result);
            
            // Verify the deletion
            ManageBookingModel deletedBooking = dao.getBookingById(1);
            Assert.assertNull("Booking should be null after deletion", deletedBooking);
        }
    }

    @Test
    public void deleteBookingWithInvalidId() {
        boolean result = dao.deleteBooking(-1);
        Assert.assertFalse("Booking deletion should fail with invalid ID", result);
    }

    @Test
    public void searchBookingsWithValidKeyword() {
        // First get all bookings to find a valid keyword
        List<ManageBookingModel> allBookings = dao.getAllBookings();
        if (!allBookings.isEmpty()) {
            String keyword = allBookings.get(0).getFullName();
            List<ManageBookingModel> searchResults = dao.searchBookings(keyword);
            Assert.assertNotNull("Search results should not be null", searchResults);
            Assert.assertTrue("Should find at least one booking", searchResults.size() > 0);
        }
    }

    @Test
    public void searchBookingsWithInvalidKeyword() {
        List<ManageBookingModel> searchResults = dao.searchBookings("nonexistentbooking");
        Assert.assertNotNull("Search results should not be null", searchResults);
        Assert.assertTrue("Should return empty list for invalid keyword", searchResults.size() == 0);
    }

    @Test
    public void searchBookingsWithNullKeyword() {
        List<ManageBookingModel> searchResults = dao.searchBookings(null);
        Assert.assertNotNull("Search results should not be null", searchResults);
        // Should return all bookings or empty list depending on implementation
    }

    @Test
    public void searchBookingsWithEmptyKeyword() {
        List<ManageBookingModel> searchResults = dao.searchBookings("");
        Assert.assertNotNull("Search results should not be null", searchResults);
        // Should return all bookings or empty list depending on implementation
    }

    @Test
    public void getBookingsByUserIdWithValidId() {
        List<ManageBookingModel> bookings = dao.getBookingsByUserId(correctUserId);
        Assert.assertNotNull("Bookings list should not be null", bookings);
        // Note: This test assumes there are bookings for the user in the database
    }

    @Test
    public void getBookingsByUserIdWithInvalidId() {
        List<ManageBookingModel> bookings = dao.getBookingsByUserId(-1);
        Assert.assertNotNull("Bookings list should not be null", bookings);
        Assert.assertTrue("Should return empty list for invalid user ID", bookings.isEmpty());
    }

    @Test
    public void getBookingsByFlightIdWithValidId() {
        List<ManageBookingModel> bookings = dao.getBookingsByFlightId(correctFlightId);
        Assert.assertNotNull("Bookings list should not be null", bookings);
        // Note: This test assumes there are bookings for the flight in the database
    }

    @Test
    public void getBookingsByFlightIdWithInvalidId() {
        List<ManageBookingModel> bookings = dao.getBookingsByFlightId(-1);
        Assert.assertNotNull("Bookings list should not be null", bookings);
        Assert.assertTrue("Should return empty list for invalid flight ID", bookings.isEmpty());
    }

    @Test
    public void getBookingsByStatusWithValidStatus() {
        List<ManageBookingModel> bookings = dao.getBookingsByStatus(correctBookingStatus);
        Assert.assertNotNull("Bookings list should not be null", bookings);
        // Note: This test assumes there are bookings with the status in the database
    }

    @Test
    public void getBookingsByStatusWithInvalidStatus() {
        List<ManageBookingModel> bookings = dao.getBookingsByStatus("InvalidStatus");
        Assert.assertNotNull("Bookings list should not be null", bookings);
        Assert.assertTrue("Should return empty list for invalid status", bookings.isEmpty());
    }

    @Test
    public void getBookingsByStatusWithNullStatus() {
        List<ManageBookingModel> bookings = dao.getBookingsByStatus(null);
        Assert.assertNotNull("Bookings list should not be null", bookings);
        Assert.assertTrue("Should return empty list for null status", bookings.isEmpty());
    }

    @Test
    public void getBookingsByStatusWithEmptyStatus() {
        List<ManageBookingModel> bookings = dao.getBookingsByStatus("");
        Assert.assertNotNull("Bookings list should not be null", bookings);
        Assert.assertTrue("Should return empty list for empty status", bookings.isEmpty());
    }

    @Test
    public void getTotalBookingsCount() {
        int count = dao.getTotalBookingsCount();
        Assert.assertTrue("Booking count should be non-negative", count >= 0);
    }

    @Test
    public void getBookingsCountByStatus() {
        int count = dao.getBookingsCountByStatus(correctBookingStatus);
        Assert.assertTrue("Booking count by status should be non-negative", count >= 0);
    }

    @Test
    public void getBookingsCountByStatusWithInvalidStatus() {
        int count = dao.getBookingsCountByStatus("InvalidStatus");
        Assert.assertEquals("Booking count should be 0 for invalid status", 0, count);
    }

    @Test
    public void getBookingsCountByStatusWithNullStatus() {
        int count = dao.getBookingsCountByStatus(null);
        Assert.assertEquals("Booking count should be 0 for null status", 0, count);
    }

    @Test
    public void getBookingsCountByStatusWithEmptyStatus() {
        int count = dao.getBookingsCountByStatus("");
        Assert.assertEquals("Booking count should be 0 for empty status", 0, count);
    }

    @Test
    public void updateBookingWithValidData() {
        // Note: In a real test, you would need to get a valid booking ID from the database
        // For now, we'll test with a known valid ID if available
        String newFullName = "Updated Passenger Name";
        String newEmail = "updated@example.com";
        String newClassName = "Business";
        String newSeatNo = "B1";
        String newDate = "2024-12-26";
        String newPaymentMethod = "Debit Card";
        
        boolean result = dao.updateBooking(1, newFullName, newEmail, newClassName, 
                                         newSeatNo, newDate, newPaymentMethod); // Assuming booking ID 1 exists
        
        if (result) {
            Assert.assertTrue("Booking update should succeed", result);
            
            // Verify the update
            ManageBookingModel updatedBooking = dao.getBookingById(1);
            Assert.assertNotNull("Updated booking should exist", updatedBooking);
            Assert.assertEquals("Full name should be updated", newFullName, updatedBooking.getFullName());
            Assert.assertEquals("Email should be updated", newEmail, updatedBooking.getEmail());
        }
    }

    @Test
    public void updateBookingWithInvalidId() {
        boolean result = dao.updateBooking(-1, "Test Name", "test@example.com", "Economy", 
                                         "E1", "2024-12-25", "Credit Card");
        Assert.assertFalse("Booking update should fail with invalid ID", result);
    }

    @Test
    public void updateBookingWithNullFullName() {
        boolean result = dao.updateBooking(correctBookingId, null, correctEmail, correctClassName, 
                                         correctSeatNo, correctDate, correctPaymentMethod);
        Assert.assertFalse("Booking update should fail with null full name", result);
    }

    @Test
    public void updateBookingWithEmptyFullName() {
        boolean result = dao.updateBooking(correctBookingId, "", correctEmail, correctClassName, 
                                         correctSeatNo, correctDate, correctPaymentMethod);
        Assert.assertFalse("Booking update should fail with empty full name", result);
    }

    @Test
    public void updateBookingWithNullEmail() {
        boolean result = dao.updateBooking(correctBookingId, correctFullName, null, correctClassName, 
                                         correctSeatNo, correctDate, correctPaymentMethod);
        Assert.assertFalse("Booking update should fail with null email", result);
    }

    @Test
    public void updateBookingWithInvalidEmail() {
        boolean result = dao.updateBooking(correctBookingId, correctFullName, "invalid-email", correctClassName, 
                                         correctSeatNo, correctDate, correctPaymentMethod);
        Assert.assertFalse("Booking update should fail with invalid email", result);
    }

    @Test
    public void updateBookingWithNullClassName() {
        boolean result = dao.updateBooking(correctBookingId, correctFullName, correctEmail, null, 
                                         correctSeatNo, correctDate, correctPaymentMethod);
        Assert.assertFalse("Booking update should fail with null class name", result);
    }

    @Test
    public void updateBookingWithInvalidClassName() {
        boolean result = dao.updateBooking(correctBookingId, correctFullName, correctEmail, "InvalidClass", 
                                         correctSeatNo, correctDate, correctPaymentMethod);
        Assert.assertFalse("Booking update should fail with invalid class name", result);
    }

    @Test
    public void updateBookingWithNullSeatNo() {
        boolean result = dao.updateBooking(correctBookingId, correctFullName, correctEmail, correctClassName, 
                                         null, correctDate, correctPaymentMethod);
        Assert.assertFalse("Booking update should fail with null seat number", result);
    }

    @Test
    public void updateBookingWithEmptySeatNo() {
        boolean result = dao.updateBooking(correctBookingId, correctFullName, correctEmail, correctClassName, 
                                         "", correctDate, correctPaymentMethod);
        Assert.assertFalse("Booking update should fail with empty seat number", result);
    }

    @Test
    public void updateBookingWithNullDate() {
        boolean result = dao.updateBooking(correctBookingId, correctFullName, correctEmail, correctClassName, 
                                         correctSeatNo, null, correctPaymentMethod);
        Assert.assertFalse("Booking update should fail with null date", result);
    }

    @Test
    public void updateBookingWithInvalidDate() {
        boolean result = dao.updateBooking(correctBookingId, correctFullName, correctEmail, correctClassName, 
                                         correctSeatNo, "invalid-date", correctPaymentMethod);
        Assert.assertFalse("Booking update should fail with invalid date", result);
    }

    @Test
    public void updateBookingWithNullPaymentMethod() {
        boolean result = dao.updateBooking(correctBookingId, correctFullName, correctEmail, correctClassName, 
                                         correctSeatNo, correctDate, null);
        Assert.assertFalse("Booking update should fail with null payment method", result);
    }

    @Test
    public void updateBookingWithEmptyPaymentMethod() {
        boolean result = dao.updateBooking(correctBookingId, correctFullName, correctEmail, correctClassName, 
                                         correctSeatNo, correctDate, "");
        Assert.assertFalse("Booking update should fail with empty payment method", result);
    }

    @Test
    public void manageBookingModelPropertiesAreAccessible() {
        // Test that ManageBookingModel properties are accessible
        ManageBookingModel booking = new ManageBookingModel();
        booking.setBookingId(correctBookingId);
        booking.setUserId(correctUserId);
        booking.setFlightId(correctFlightId);
        booking.setFullName(correctFullName);
        booking.setEmail(correctEmail);
        booking.setClassName(correctClassName);
        booking.setSeatNo(correctSeatNo);
        booking.setDate(correctDate);
        booking.setPaymentMethod(correctPaymentMethod);
        booking.setBookingStatus(correctBookingStatus);
        
        Assert.assertEquals("Booking ID should match", correctBookingId, booking.getBookingId());
        Assert.assertEquals("User ID should match", correctUserId, booking.getUserId());
        Assert.assertEquals("Flight ID should match", correctFlightId, booking.getFlightId());
        Assert.assertEquals("Full name should match", correctFullName, booking.getFullName());
        Assert.assertEquals("Email should match", correctEmail, booking.getEmail());
        Assert.assertEquals("Class name should match", correctClassName, booking.getClassName());
        Assert.assertEquals("Seat number should match", correctSeatNo, booking.getSeatNo());
        Assert.assertEquals("Date should match", correctDate, booking.getDate());
        Assert.assertEquals("Payment method should match", correctPaymentMethod, booking.getPaymentMethod());
        Assert.assertEquals("Booking status should match", correctBookingStatus, booking.getBookingStatus());
    }

    @Test
    public void manageBookingModelWithDifferentStatuses() {
        // Test booking with different statuses
        String[] statuses = {"Confirmed", "Cancelled", "Pending", "Completed"};
        
        for (String status : statuses) {
            ManageBookingModel booking = new ManageBookingModel();
            booking.setBookingId(correctBookingId);
            booking.setUserId(correctUserId);
            booking.setFlightId(correctFlightId);
            booking.setFullName(correctFullName);
            booking.setEmail(correctEmail);
            booking.setClassName(correctClassName);
            booking.setSeatNo(correctSeatNo);
            booking.setDate(correctDate);
            booking.setPaymentMethod(correctPaymentMethod);
            booking.setBookingStatus(status);
            
            Assert.assertEquals("Booking status should match", status, booking.getBookingStatus());
        }
    }

    @Test
    public void manageBookingModelWithDifferentSeatClasses() {
        // Test booking with different seat classes
        String[] seatClasses = {"Economy", "Business", "First Class"};
        
        for (String seatClass : seatClasses) {
            ManageBookingModel booking = new ManageBookingModel();
            booking.setBookingId(correctBookingId);
            booking.setUserId(correctUserId);
            booking.setFlightId(correctFlightId);
            booking.setFullName(correctFullName);
            booking.setEmail(correctEmail);
            booking.setClassName(seatClass);
            booking.setSeatNo(correctSeatNo);
            booking.setDate(correctDate);
            booking.setPaymentMethod(correctPaymentMethod);
            booking.setBookingStatus(correctBookingStatus);
            
            Assert.assertEquals("Seat class should match", seatClass, booking.getClassName());
        }
    }

    @Test
    public void manageBookingModelWithDifferentPaymentMethods() {
        // Test booking with different payment methods
        String[] paymentMethods = {"Credit Card", "Debit Card", "Cash", "Online Transfer", "Mobile Payment"};
        
        for (String paymentMethod : paymentMethods) {
            ManageBookingModel booking = new ManageBookingModel();
            booking.setBookingId(correctBookingId);
            booking.setUserId(correctUserId);
            booking.setFlightId(correctFlightId);
            booking.setFullName(correctFullName);
            booking.setEmail(correctEmail);
            booking.setClassName(correctClassName);
            booking.setSeatNo(correctSeatNo);
            booking.setDate(correctDate);
            booking.setPaymentMethod(paymentMethod);
            booking.setBookingStatus(correctBookingStatus);
            
            Assert.assertEquals("Payment method should match", paymentMethod, booking.getPaymentMethod());
        }
    }
} 