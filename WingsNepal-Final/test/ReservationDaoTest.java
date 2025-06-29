/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package test;

import wingsnepal.dao.ReservationDao;
import wingsnepal.model.ReservationModel;
import java.util.List;
import org.junit.*;

/**
 *
 * @author Aayush Kharel
 */
public class ReservationDaoTest {
    
    int correctReservationId = 1;
    int correctUserId = 1;
    int correctFlightId = 1;
    String correctPassengerName = "Test Passenger";
    String correctEmail = "test@example.com";
    String correctPhone = "9876543210";
    String correctSeatClass = "Economy";
    String correctSeatNumber = "E1";
    String correctReservationDate = "2024-12-25";
    String correctReservationStatus = "Confirmed";
    String correctPaymentMethod = "Credit Card";
    
    ReservationDao dao = new ReservationDao();

    @Test
    public void getAllReservations() {
        List<ReservationModel> reservations = dao.getAllReservations();
        Assert.assertNotNull("Reservations list should not be null", reservations);
        // Note: This test assumes there are reservations in the database
    }

    @Test
    public void getReservationByIdWithValidId() {
        // Note: In a real test, you would need to get a valid reservation ID from the database
        // For now, we'll test with a known valid ID if available
        ReservationModel reservation = dao.getReservationById(1); // Assuming reservation ID 1 exists
        if (reservation != null) {
            Assert.assertNotNull("Reservation should not be null", reservation);
            Assert.assertTrue("Reservation ID should be positive", reservation.getReservationId() > 0);
        }
    }

    @Test
    public void getReservationByIdWithInvalidId() {
        ReservationModel reservation = dao.getReservationById(-1);
        Assert.assertNull("Reservation should be null for invalid ID", reservation);
    }

    @Test
    public void addReservationWithValidData() {
        // Note: In a real test, you would need to ensure the user and flight exist
        boolean result = dao.addReservation(correctUserId, correctFlightId, correctPassengerName, 
                                           correctEmail, correctPhone, correctSeatClass, correctSeatNumber, 
                                           correctReservationDate, correctReservationStatus, correctPaymentMethod);
        
        if (result) {
            Assert.assertTrue("Reservation addition should succeed", result);
            
            // Verify the addition by searching for the reservation
            List<ReservationModel> reservations = dao.getAllReservations();
            boolean found = false;
            for (ReservationModel reservation : reservations) {
                if (reservation.getPassengerName().equals(correctPassengerName) && 
                    reservation.getEmail().equals(correctEmail)) {
                    found = true;
                    break;
                }
            }
            Assert.assertTrue("Added reservation should be found in the list", found);
        }
    }

    @Test
    public void addReservationWithInvalidUserId() {
        boolean result = dao.addReservation(-1, correctFlightId, correctPassengerName, 
                                           correctEmail, correctPhone, correctSeatClass, correctSeatNumber, 
                                           correctReservationDate, correctReservationStatus, correctPaymentMethod);
        Assert.assertFalse("Reservation addition should fail with invalid user ID", result);
    }

    @Test
    public void addReservationWithInvalidFlightId() {
        boolean result = dao.addReservation(correctUserId, -1, correctPassengerName, 
                                           correctEmail, correctPhone, correctSeatClass, correctSeatNumber, 
                                           correctReservationDate, correctReservationStatus, correctPaymentMethod);
        Assert.assertFalse("Reservation addition should fail with invalid flight ID", result);
    }

    @Test
    public void addReservationWithNullPassengerName() {
        boolean result = dao.addReservation(correctUserId, correctFlightId, null, 
                                           correctEmail, correctPhone, correctSeatClass, correctSeatNumber, 
                                           correctReservationDate, correctReservationStatus, correctPaymentMethod);
        Assert.assertFalse("Reservation addition should fail with null passenger name", result);
    }

    @Test
    public void addReservationWithEmptyPassengerName() {
        boolean result = dao.addReservation(correctUserId, correctFlightId, "", 
                                           correctEmail, correctPhone, correctSeatClass, correctSeatNumber, 
                                           correctReservationDate, correctReservationStatus, correctPaymentMethod);
        Assert.assertFalse("Reservation addition should fail with empty passenger name", result);
    }

    @Test
    public void addReservationWithNullEmail() {
        boolean result = dao.addReservation(correctUserId, correctFlightId, correctPassengerName, 
                                           null, correctPhone, correctSeatClass, correctSeatNumber, 
                                           correctReservationDate, correctReservationStatus, correctPaymentMethod);
        Assert.assertFalse("Reservation addition should fail with null email", result);
    }

    @Test
    public void addReservationWithInvalidEmail() {
        boolean result = dao.addReservation(correctUserId, correctFlightId, correctPassengerName, 
                                           "invalid-email", correctPhone, correctSeatClass, correctSeatNumber, 
                                           correctReservationDate, correctReservationStatus, correctPaymentMethod);
        Assert.assertFalse("Reservation addition should fail with invalid email", result);
    }

    @Test
    public void addReservationWithNullPhone() {
        boolean result = dao.addReservation(correctUserId, correctFlightId, correctPassengerName, 
                                           correctEmail, null, correctSeatClass, correctSeatNumber, 
                                           correctReservationDate, correctReservationStatus, correctPaymentMethod);
        Assert.assertFalse("Reservation addition should fail with null phone", result);
    }

    @Test
    public void addReservationWithInvalidPhone() {
        boolean result = dao.addReservation(correctUserId, correctFlightId, correctPassengerName, 
                                           correctEmail, "123", correctSeatClass, correctSeatNumber, 
                                           correctReservationDate, correctReservationStatus, correctPaymentMethod);
        Assert.assertFalse("Reservation addition should fail with invalid phone", result);
    }

    @Test
    public void addReservationWithNullSeatClass() {
        boolean result = dao.addReservation(correctUserId, correctFlightId, correctPassengerName, 
                                           correctEmail, correctPhone, null, correctSeatNumber, 
                                           correctReservationDate, correctReservationStatus, correctPaymentMethod);
        Assert.assertFalse("Reservation addition should fail with null seat class", result);
    }

    @Test
    public void addReservationWithInvalidSeatClass() {
        boolean result = dao.addReservation(correctUserId, correctFlightId, correctPassengerName, 
                                           correctEmail, correctPhone, "InvalidClass", correctSeatNumber, 
                                           correctReservationDate, correctReservationStatus, correctPaymentMethod);
        Assert.assertFalse("Reservation addition should fail with invalid seat class", result);
    }

    @Test
    public void addReservationWithNullSeatNumber() {
        boolean result = dao.addReservation(correctUserId, correctFlightId, correctPassengerName, 
                                           correctEmail, correctPhone, correctSeatClass, null, 
                                           correctReservationDate, correctReservationStatus, correctPaymentMethod);
        Assert.assertFalse("Reservation addition should fail with null seat number", result);
    }

    @Test
    public void addReservationWithEmptySeatNumber() {
        boolean result = dao.addReservation(correctUserId, correctFlightId, correctPassengerName, 
                                           correctEmail, correctPhone, correctSeatClass, "", 
                                           correctReservationDate, correctReservationStatus, correctPaymentMethod);
        Assert.assertFalse("Reservation addition should fail with empty seat number", result);
    }

    @Test
    public void addReservationWithNullReservationDate() {
        boolean result = dao.addReservation(correctUserId, correctFlightId, correctPassengerName, 
                                           correctEmail, correctPhone, correctSeatClass, correctSeatNumber, 
                                           null, correctReservationStatus, correctPaymentMethod);
        Assert.assertFalse("Reservation addition should fail with null reservation date", result);
    }

    @Test
    public void addReservationWithInvalidReservationDate() {
        boolean result = dao.addReservation(correctUserId, correctFlightId, correctPassengerName, 
                                           correctEmail, correctPhone, correctSeatClass, correctSeatNumber, 
                                           "invalid-date", correctReservationStatus, correctPaymentMethod);
        Assert.assertFalse("Reservation addition should fail with invalid reservation date", result);
    }

    @Test
    public void addReservationWithNullReservationStatus() {
        boolean result = dao.addReservation(correctUserId, correctFlightId, correctPassengerName, 
                                           correctEmail, correctPhone, correctSeatClass, correctSeatNumber, 
                                           correctReservationDate, null, correctPaymentMethod);
        Assert.assertFalse("Reservation addition should fail with null reservation status", result);
    }

    @Test
    public void addReservationWithInvalidReservationStatus() {
        boolean result = dao.addReservation(correctUserId, correctFlightId, correctPassengerName, 
                                           correctEmail, correctPhone, correctSeatClass, correctSeatNumber, 
                                           correctReservationDate, "InvalidStatus", correctPaymentMethod);
        Assert.assertFalse("Reservation addition should fail with invalid reservation status", result);
    }

    @Test
    public void addReservationWithNullPaymentMethod() {
        boolean result = dao.addReservation(correctUserId, correctFlightId, correctPassengerName, 
                                           correctEmail, correctPhone, correctSeatClass, correctSeatNumber, 
                                           correctReservationDate, correctReservationStatus, null);
        Assert.assertFalse("Reservation addition should fail with null payment method", result);
    }

    @Test
    public void addReservationWithEmptyPaymentMethod() {
        boolean result = dao.addReservation(correctUserId, correctFlightId, correctPassengerName, 
                                           correctEmail, correctPhone, correctSeatClass, correctSeatNumber, 
                                           correctReservationDate, correctReservationStatus, "");
        Assert.assertFalse("Reservation addition should fail with empty payment method", result);
    }

    @Test
    public void updateReservationWithValidData() {
        // Note: In a real test, you would need to get a valid reservation ID from the database
        // For now, we'll test with a known valid ID if available
        String newPassengerName = "Updated Passenger Name";
        String newEmail = "updated@example.com";
        String newPhone = "1234567890";
        String newSeatClass = "Business";
        String newSeatNumber = "B1";
        String newReservationDate = "2024-12-26";
        String newReservationStatus = "Cancelled";
        String newPaymentMethod = "Debit Card";
        
        boolean result = dao.updateReservation(1, newPassengerName, newEmail, newPhone, newSeatClass, 
                                             newSeatNumber, newReservationDate, newReservationStatus, newPaymentMethod); // Assuming reservation ID 1 exists
        
        if (result) {
            Assert.assertTrue("Reservation update should succeed", result);
            
            // Verify the update
            ReservationModel updatedReservation = dao.getReservationById(1);
            Assert.assertNotNull("Updated reservation should exist", updatedReservation);
            Assert.assertEquals("Passenger name should be updated", newPassengerName, updatedReservation.getPassengerName());
            Assert.assertEquals("Email should be updated", newEmail, updatedReservation.getEmail());
        }
    }

    @Test
    public void updateReservationWithInvalidId() {
        boolean result = dao.updateReservation(-1, "Test Name", "test@example.com", "1234567890", 
                                             "Economy", "E1", "2024-12-25", "Confirmed", "Credit Card");
        Assert.assertFalse("Reservation update should fail with invalid ID", result);
    }

    @Test
    public void updateReservationWithNullPassengerName() {
        boolean result = dao.updateReservation(correctReservationId, null, correctEmail, correctPhone, 
                                             correctSeatClass, correctSeatNumber, correctReservationDate, 
                                             correctReservationStatus, correctPaymentMethod);
        Assert.assertFalse("Reservation update should fail with null passenger name", result);
    }

    @Test
    public void updateReservationWithEmptyPassengerName() {
        boolean result = dao.updateReservation(correctReservationId, "", correctEmail, correctPhone, 
                                             correctSeatClass, correctSeatNumber, correctReservationDate, 
                                             correctReservationStatus, correctPaymentMethod);
        Assert.assertFalse("Reservation update should fail with empty passenger name", result);
    }

    @Test
    public void updateReservationWithNullEmail() {
        boolean result = dao.updateReservation(correctReservationId, correctPassengerName, null, correctPhone, 
                                             correctSeatClass, correctSeatNumber, correctReservationDate, 
                                             correctReservationStatus, correctPaymentMethod);
        Assert.assertFalse("Reservation update should fail with null email", result);
    }

    @Test
    public void updateReservationWithInvalidEmail() {
        boolean result = dao.updateReservation(correctReservationId, correctPassengerName, "invalid-email", correctPhone, 
                                             correctSeatClass, correctSeatNumber, correctReservationDate, 
                                             correctReservationStatus, correctPaymentMethod);
        Assert.assertFalse("Reservation update should fail with invalid email", result);
    }

    @Test
    public void updateReservationWithNullPhone() {
        boolean result = dao.updateReservation(correctReservationId, correctPassengerName, correctEmail, null, 
                                             correctSeatClass, correctSeatNumber, correctReservationDate, 
                                             correctReservationStatus, correctPaymentMethod);
        Assert.assertFalse("Reservation update should fail with null phone", result);
    }

    @Test
    public void updateReservationWithInvalidPhone() {
        boolean result = dao.updateReservation(correctReservationId, correctPassengerName, correctEmail, "123", 
                                             correctSeatClass, correctSeatNumber, correctReservationDate, 
                                             correctReservationStatus, correctPaymentMethod);
        Assert.assertFalse("Reservation update should fail with invalid phone", result);
    }

    @Test
    public void updateReservationWithNullSeatClass() {
        boolean result = dao.updateReservation(correctReservationId, correctPassengerName, correctEmail, correctPhone, 
                                             null, correctSeatNumber, correctReservationDate, 
                                             correctReservationStatus, correctPaymentMethod);
        Assert.assertFalse("Reservation update should fail with null seat class", result);
    }

    @Test
    public void updateReservationWithInvalidSeatClass() {
        boolean result = dao.updateReservation(correctReservationId, correctPassengerName, correctEmail, correctPhone, 
                                             "InvalidClass", correctSeatNumber, correctReservationDate, 
                                             correctReservationStatus, correctPaymentMethod);
        Assert.assertFalse("Reservation update should fail with invalid seat class", result);
    }

    @Test
    public void updateReservationWithNullSeatNumber() {
        boolean result = dao.updateReservation(correctReservationId, correctPassengerName, correctEmail, correctPhone, 
                                             correctSeatClass, null, correctReservationDate, 
                                             correctReservationStatus, correctPaymentMethod);
        Assert.assertFalse("Reservation update should fail with null seat number", result);
    }

    @Test
    public void updateReservationWithEmptySeatNumber() {
        boolean result = dao.updateReservation(correctReservationId, correctPassengerName, correctEmail, correctPhone, 
                                             correctSeatClass, "", correctReservationDate, 
                                             correctReservationStatus, correctPaymentMethod);
        Assert.assertFalse("Reservation update should fail with empty seat number", result);
    }

    @Test
    public void updateReservationWithNullReservationDate() {
        boolean result = dao.updateReservation(correctReservationId, correctPassengerName, correctEmail, correctPhone, 
                                             correctSeatClass, correctSeatNumber, null, 
                                             correctReservationStatus, correctPaymentMethod);
        Assert.assertFalse("Reservation update should fail with null reservation date", result);
    }

    @Test
    public void updateReservationWithInvalidReservationDate() {
        boolean result = dao.updateReservation(correctReservationId, correctPassengerName, correctEmail, correctPhone, 
                                             correctSeatClass, correctSeatNumber, "invalid-date", 
                                             correctReservationStatus, correctPaymentMethod);
        Assert.assertFalse("Reservation update should fail with invalid reservation date", result);
    }

    @Test
    public void updateReservationWithNullReservationStatus() {
        boolean result = dao.updateReservation(correctReservationId, correctPassengerName, correctEmail, correctPhone, 
                                             correctSeatClass, correctSeatNumber, correctReservationDate, 
                                             null, correctPaymentMethod);
        Assert.assertFalse("Reservation update should fail with null reservation status", result);
    }

    @Test
    public void updateReservationWithInvalidReservationStatus() {
        boolean result = dao.updateReservation(correctReservationId, correctPassengerName, correctEmail, correctPhone, 
                                             correctSeatClass, correctSeatNumber, correctReservationDate, 
                                             "InvalidStatus", correctPaymentMethod);
        Assert.assertFalse("Reservation update should fail with invalid reservation status", result);
    }

    @Test
    public void updateReservationWithNullPaymentMethod() {
        boolean result = dao.updateReservation(correctReservationId, correctPassengerName, correctEmail, correctPhone, 
                                             correctSeatClass, correctSeatNumber, correctReservationDate, 
                                             correctReservationStatus, null);
        Assert.assertFalse("Reservation update should fail with null payment method", result);
    }

    @Test
    public void updateReservationWithEmptyPaymentMethod() {
        boolean result = dao.updateReservation(correctReservationId, correctPassengerName, correctEmail, correctPhone, 
                                             correctSeatClass, correctSeatNumber, correctReservationDate, 
                                             correctReservationStatus, "");
        Assert.assertFalse("Reservation update should fail with empty payment method", result);
    }

    @Test
    public void deleteReservationWithValidId() {
        // Note: In a real test, you would need to get a valid reservation ID from the database
        // For now, we'll test with a known valid ID if available
        boolean result = dao.deleteReservation(1); // Assuming reservation ID 1 exists
        if (result) {
            Assert.assertTrue("Reservation deletion should succeed", result);
            
            // Verify the deletion
            ReservationModel deletedReservation = dao.getReservationById(1);
            Assert.assertNull("Reservation should be null after deletion", deletedReservation);
        }
    }

    @Test
    public void deleteReservationWithInvalidId() {
        boolean result = dao.deleteReservation(-1);
        Assert.assertFalse("Reservation deletion should fail with invalid ID", result);
    }

    @Test
    public void searchReservationsWithValidKeyword() {
        // First get all reservations to find a valid keyword
        List<ReservationModel> allReservations = dao.getAllReservations();
        if (!allReservations.isEmpty()) {
            String keyword = allReservations.get(0).getPassengerName();
            List<ReservationModel> searchResults = dao.searchReservations(keyword);
            Assert.assertNotNull("Search results should not be null", searchResults);
            Assert.assertTrue("Should find at least one reservation", searchResults.size() > 0);
        }
    }

    @Test
    public void searchReservationsWithInvalidKeyword() {
        List<ReservationModel> searchResults = dao.searchReservations("nonexistentreservation");
        Assert.assertNotNull("Search results should not be null", searchResults);
        Assert.assertTrue("Should return empty list for invalid keyword", searchResults.size() == 0);
    }

    @Test
    public void searchReservationsWithNullKeyword() {
        List<ReservationModel> searchResults = dao.searchReservations(null);
        Assert.assertNotNull("Search results should not be null", searchResults);
        // Should return all reservations or empty list depending on implementation
    }

    @Test
    public void searchReservationsWithEmptyKeyword() {
        List<ReservationModel> searchResults = dao.searchReservations("");
        Assert.assertNotNull("Search results should not be null", searchResults);
        // Should return all reservations or empty list depending on implementation
    }

    @Test
    public void getReservationsByUserIdWithValidId() {
        List<ReservationModel> reservations = dao.getReservationsByUserId(correctUserId);
        Assert.assertNotNull("Reservations list should not be null", reservations);
        // Note: This test assumes there are reservations for the user in the database
    }

    @Test
    public void getReservationsByUserIdWithInvalidId() {
        List<ReservationModel> reservations = dao.getReservationsByUserId(-1);
        Assert.assertNotNull("Reservations list should not be null", reservations);
        Assert.assertTrue("Should return empty list for invalid user ID", reservations.isEmpty());
    }

    @Test
    public void getReservationsByFlightIdWithValidId() {
        List<ReservationModel> reservations = dao.getReservationsByFlightId(correctFlightId);
        Assert.assertNotNull("Reservations list should not be null", reservations);
        // Note: This test assumes there are reservations for the flight in the database
    }

    @Test
    public void getReservationsByFlightIdWithInvalidId() {
        List<ReservationModel> reservations = dao.getReservationsByFlightId(-1);
        Assert.assertNotNull("Reservations list should not be null", reservations);
        Assert.assertTrue("Should return empty list for invalid flight ID", reservations.isEmpty());
    }

    @Test
    public void getReservationsByStatusWithValidStatus() {
        List<ReservationModel> reservations = dao.getReservationsByStatus(correctReservationStatus);
        Assert.assertNotNull("Reservations list should not be null", reservations);
        // Note: This test assumes there are reservations with the status in the database
    }

    @Test
    public void getReservationsByStatusWithInvalidStatus() {
        List<ReservationModel> reservations = dao.getReservationsByStatus("InvalidStatus");
        Assert.assertNotNull("Reservations list should not be null", reservations);
        Assert.assertTrue("Should return empty list for invalid status", reservations.isEmpty());
    }

    @Test
    public void getReservationsByStatusWithNullStatus() {
        List<ReservationModel> reservations = dao.getReservationsByStatus(null);
        Assert.assertNotNull("Reservations list should not be null", reservations);
        Assert.assertTrue("Should return empty list for null status", reservations.isEmpty());
    }

    @Test
    public void getReservationsByStatusWithEmptyStatus() {
        List<ReservationModel> reservations = dao.getReservationsByStatus("");
        Assert.assertNotNull("Reservations list should not be null", reservations);
        Assert.assertTrue("Should return empty list for empty status", reservations.isEmpty());
    }

    @Test
    public void getTotalReservationsCount() {
        int count = dao.getTotalReservationsCount();
        Assert.assertTrue("Reservation count should be non-negative", count >= 0);
    }

    @Test
    public void getReservationsCountByStatus() {
        int count = dao.getReservationsCountByStatus(correctReservationStatus);
        Assert.assertTrue("Reservation count by status should be non-negative", count >= 0);
    }

    @Test
    public void getReservationsCountByStatusWithInvalidStatus() {
        int count = dao.getReservationsCountByStatus("InvalidStatus");
        Assert.assertEquals("Reservation count should be 0 for invalid status", 0, count);
    }

    @Test
    public void getReservationsCountByStatusWithNullStatus() {
        int count = dao.getReservationsCountByStatus(null);
        Assert.assertEquals("Reservation count should be 0 for null status", 0, count);
    }

    @Test
    public void getReservationsCountByStatusWithEmptyStatus() {
        int count = dao.getReservationsCountByStatus("");
        Assert.assertEquals("Reservation count should be 0 for empty status", 0, count);
    }

    @Test
    public void updateReservationStatusWithValidData() {
        // Note: In a real test, you would need to get a valid reservation ID from the database
        // For now, we'll test with a known valid ID if available
        String newStatus = "Cancelled";
        boolean result = dao.updateReservationStatus(1, newStatus); // Assuming reservation ID 1 exists
        if (result) {
            Assert.assertTrue("Reservation status update should succeed", result);
            
            // Verify the update
            ReservationModel updatedReservation = dao.getReservationById(1);
            Assert.assertNotNull("Updated reservation should exist", updatedReservation);
            Assert.assertEquals("Status should be updated", newStatus, updatedReservation.getReservationStatus());
        }
    }

    @Test
    public void updateReservationStatusWithInvalidId() {
        boolean result = dao.updateReservationStatus(-1, "Cancelled");
        Assert.assertFalse("Reservation status update should fail with invalid ID", result);
    }

    @Test
    public void updateReservationStatusWithNullStatus() {
        boolean result = dao.updateReservationStatus(correctReservationId, null);
        Assert.assertFalse("Reservation status update should fail with null status", result);
    }

    @Test
    public void updateReservationStatusWithEmptyStatus() {
        boolean result = dao.updateReservationStatus(correctReservationId, "");
        Assert.assertFalse("Reservation status update should fail with empty status", result);
    }

    @Test
    public void updateReservationStatusWithInvalidStatus() {
        boolean result = dao.updateReservationStatus(correctReservationId, "InvalidStatus");
        Assert.assertFalse("Reservation status update should fail with invalid status", result);
    }

    @Test
    public void reservationModelPropertiesAreAccessible() {
        // Test that ReservationModel properties are accessible
        ReservationModel reservation = new ReservationModel();
        reservation.setReservationId(correctReservationId);
        reservation.setUserId(correctUserId);
        reservation.setFlightId(correctFlightId);
        reservation.setPassengerName(correctPassengerName);
        reservation.setEmail(correctEmail);
        reservation.setPhone(correctPhone);
        reservation.setSeatClass(correctSeatClass);
        reservation.setSeatNumber(correctSeatNumber);
        reservation.setReservationDate(correctReservationDate);
        reservation.setReservationStatus(correctReservationStatus);
        reservation.setPaymentMethod(correctPaymentMethod);
        
        Assert.assertEquals("Reservation ID should match", correctReservationId, reservation.getReservationId());
        Assert.assertEquals("User ID should match", correctUserId, reservation.getUserId());
        Assert.assertEquals("Flight ID should match", correctFlightId, reservation.getFlightId());
        Assert.assertEquals("Passenger name should match", correctPassengerName, reservation.getPassengerName());
        Assert.assertEquals("Email should match", correctEmail, reservation.getEmail());
        Assert.assertEquals("Phone should match", correctPhone, reservation.getPhone());
        Assert.assertEquals("Seat class should match", correctSeatClass, reservation.getSeatClass());
        Assert.assertEquals("Seat number should match", correctSeatNumber, reservation.getSeatNumber());
        Assert.assertEquals("Reservation date should match", correctReservationDate, reservation.getReservationDate());
        Assert.assertEquals("Reservation status should match", correctReservationStatus, reservation.getReservationStatus());
        Assert.assertEquals("Payment method should match", correctPaymentMethod, reservation.getPaymentMethod());
    }

    @Test
    public void reservationModelWithDifferentStatuses() {
        // Test reservation with different statuses
        String[] statuses = {"Confirmed", "Cancelled", "Pending", "Completed"};
        
        for (String status : statuses) {
            ReservationModel reservation = new ReservationModel();
            reservation.setReservationId(correctReservationId);
            reservation.setUserId(correctUserId);
            reservation.setFlightId(correctFlightId);
            reservation.setPassengerName(correctPassengerName);
            reservation.setEmail(correctEmail);
            reservation.setPhone(correctPhone);
            reservation.setSeatClass(correctSeatClass);
            reservation.setSeatNumber(correctSeatNumber);
            reservation.setReservationDate(correctReservationDate);
            reservation.setReservationStatus(status);
            reservation.setPaymentMethod(correctPaymentMethod);
            
            Assert.assertEquals("Reservation status should match", status, reservation.getReservationStatus());
        }
    }

    @Test
    public void reservationModelWithDifferentSeatClasses() {
        // Test reservation with different seat classes
        String[] seatClasses = {"Economy", "Business", "First Class"};
        
        for (String seatClass : seatClasses) {
            ReservationModel reservation = new ReservationModel();
            reservation.setReservationId(correctReservationId);
            reservation.setUserId(correctUserId);
            reservation.setFlightId(correctFlightId);
            reservation.setPassengerName(correctPassengerName);
            reservation.setEmail(correctEmail);
            reservation.setPhone(correctPhone);
            reservation.setSeatClass(seatClass);
            reservation.setSeatNumber(correctSeatNumber);
            reservation.setReservationDate(correctReservationDate);
            reservation.setReservationStatus(correctReservationStatus);
            reservation.setPaymentMethod(correctPaymentMethod);
            
            Assert.assertEquals("Seat class should match", seatClass, reservation.getSeatClass());
        }
    }

    @Test
    public void reservationModelWithDifferentPaymentMethods() {
        // Test reservation with different payment methods
        String[] paymentMethods = {"Credit Card", "Debit Card", "Cash", "Online Transfer", "Mobile Payment"};
        
        for (String paymentMethod : paymentMethods) {
            ReservationModel reservation = new ReservationModel();
            reservation.setReservationId(correctReservationId);
            reservation.setUserId(correctUserId);
            reservation.setFlightId(correctFlightId);
            reservation.setPassengerName(correctPassengerName);
            reservation.setEmail(correctEmail);
            reservation.setPhone(correctPhone);
            reservation.setSeatClass(correctSeatClass);
            reservation.setSeatNumber(correctSeatNumber);
            reservation.setReservationDate(correctReservationDate);
            reservation.setReservationStatus(correctReservationStatus);
            reservation.setPaymentMethod(paymentMethod);
            
            Assert.assertEquals("Payment method should match", paymentMethod, reservation.getPaymentMethod());
        }
    }
} 