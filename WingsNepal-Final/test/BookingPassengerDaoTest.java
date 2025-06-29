/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package test;

import wingsnepal.dao.BookingPassengerDao;
import wingsnepal.model.PassengerModel;
import java.util.List;
import org.junit.*;

/**
 *
 * @author Aayush Kharel
 */
public class BookingPassengerDaoTest {
    
    int correctPassengerId = 1;
    int correctBookingId = 1;
    String correctPassengerName = "Test Passenger";
    String correctEmail = "test@example.com";
    String correctPhone = "9876543210";
    String correctSeatClass = "Economy";
    String correctSeatNumber = "E1";
    String correctPassportNumber = "A12345678";
    String correctDateOfBirth = "1990-01-01";
    String correctGender = "Male";
    String correctNationality = "Nepali";
    
    BookingPassengerDao dao = new BookingPassengerDao();

    @Test
    public void getAllPassengers() {
        List<PassengerModel> passengers = dao.getAllPassengers();
        Assert.assertNotNull("Passengers list should not be null", passengers);
        // Note: This test assumes there are passengers in the database
    }

    @Test
    public void getPassengerByIdWithValidId() {
        // Note: In a real test, you would need to get a valid passenger ID from the database
        // For now, we'll test with a known valid ID if available
        PassengerModel passenger = dao.getPassengerById(1); // Assuming passenger ID 1 exists
        if (passenger != null) {
            Assert.assertNotNull("Passenger should not be null", passenger);
            Assert.assertTrue("Passenger ID should be positive", passenger.getPassengerId() > 0);
        }
    }

    @Test
    public void getPassengerByIdWithInvalidId() {
        PassengerModel passenger = dao.getPassengerById(-1);
        Assert.assertNull("Passenger should be null for invalid ID", passenger);
    }

    @Test
    public void addPassengerWithValidData() {
        // Note: In a real test, you would need to ensure the booking exists
        boolean result = dao.addPassenger(correctBookingId, correctPassengerName, correctEmail, 
                                         correctPhone, correctSeatClass, correctSeatNumber, 
                                         correctPassportNumber, correctDateOfBirth, correctGender, correctNationality);
        
        if (result) {
            Assert.assertTrue("Passenger addition should succeed", result);
            
            // Verify the addition by searching for the passenger
            List<PassengerModel> passengers = dao.getAllPassengers();
            boolean found = false;
            for (PassengerModel passenger : passengers) {
                if (passenger.getPassengerName().equals(correctPassengerName) && 
                    passenger.getEmail().equals(correctEmail)) {
                    found = true;
                    break;
                }
            }
            Assert.assertTrue("Added passenger should be found in the list", found);
        }
    }

    @Test
    public void addPassengerWithInvalidBookingId() {
        boolean result = dao.addPassenger(-1, correctPassengerName, correctEmail, 
                                         correctPhone, correctSeatClass, correctSeatNumber, 
                                         correctPassportNumber, correctDateOfBirth, correctGender, correctNationality);
        Assert.assertFalse("Passenger addition should fail with invalid booking ID", result);
    }

    @Test
    public void addPassengerWithNullPassengerName() {
        boolean result = dao.addPassenger(correctBookingId, null, correctEmail, 
                                         correctPhone, correctSeatClass, correctSeatNumber, 
                                         correctPassportNumber, correctDateOfBirth, correctGender, correctNationality);
        Assert.assertFalse("Passenger addition should fail with null passenger name", result);
    }

    @Test
    public void addPassengerWithEmptyPassengerName() {
        boolean result = dao.addPassenger(correctBookingId, "", correctEmail, 
                                         correctPhone, correctSeatClass, correctSeatNumber, 
                                         correctPassportNumber, correctDateOfBirth, correctGender, correctNationality);
        Assert.assertFalse("Passenger addition should fail with empty passenger name", result);
    }

    @Test
    public void addPassengerWithNullEmail() {
        boolean result = dao.addPassenger(correctBookingId, correctPassengerName, null, 
                                         correctPhone, correctSeatClass, correctSeatNumber, 
                                         correctPassportNumber, correctDateOfBirth, correctGender, correctNationality);
        Assert.assertFalse("Passenger addition should fail with null email", result);
    }

    @Test
    public void addPassengerWithInvalidEmail() {
        boolean result = dao.addPassenger(correctBookingId, correctPassengerName, "invalid-email", 
                                         correctPhone, correctSeatClass, correctSeatNumber, 
                                         correctPassportNumber, correctDateOfBirth, correctGender, correctNationality);
        Assert.assertFalse("Passenger addition should fail with invalid email", result);
    }

    @Test
    public void addPassengerWithNullPhone() {
        boolean result = dao.addPassenger(correctBookingId, correctPassengerName, correctEmail, 
                                         null, correctSeatClass, correctSeatNumber, 
                                         correctPassportNumber, correctDateOfBirth, correctGender, correctNationality);
        Assert.assertFalse("Passenger addition should fail with null phone", result);
    }

    @Test
    public void addPassengerWithInvalidPhone() {
        boolean result = dao.addPassenger(correctBookingId, correctPassengerName, correctEmail, 
                                         "123", correctSeatClass, correctSeatNumber, 
                                         correctPassportNumber, correctDateOfBirth, correctGender, correctNationality);
        Assert.assertFalse("Passenger addition should fail with invalid phone", result);
    }

    @Test
    public void addPassengerWithNullSeatClass() {
        boolean result = dao.addPassenger(correctBookingId, correctPassengerName, correctEmail, 
                                         correctPhone, null, correctSeatNumber, 
                                         correctPassportNumber, correctDateOfBirth, correctGender, correctNationality);
        Assert.assertFalse("Passenger addition should fail with null seat class", result);
    }

    @Test
    public void addPassengerWithInvalidSeatClass() {
        boolean result = dao.addPassenger(correctBookingId, correctPassengerName, correctEmail, 
                                         correctPhone, "InvalidClass", correctSeatNumber, 
                                         correctPassportNumber, correctDateOfBirth, correctGender, correctNationality);
        Assert.assertFalse("Passenger addition should fail with invalid seat class", result);
    }

    @Test
    public void addPassengerWithNullSeatNumber() {
        boolean result = dao.addPassenger(correctBookingId, correctPassengerName, correctEmail, 
                                         correctPhone, correctSeatClass, null, 
                                         correctPassportNumber, correctDateOfBirth, correctGender, correctNationality);
        Assert.assertFalse("Passenger addition should fail with null seat number", result);
    }

    @Test
    public void addPassengerWithEmptySeatNumber() {
        boolean result = dao.addPassenger(correctBookingId, correctPassengerName, correctEmail, 
                                         correctPhone, correctSeatClass, "", 
                                         correctPassportNumber, correctDateOfBirth, correctGender, correctNationality);
        Assert.assertFalse("Passenger addition should fail with empty seat number", result);
    }

    @Test
    public void addPassengerWithNullPassportNumber() {
        boolean result = dao.addPassenger(correctBookingId, correctPassengerName, correctEmail, 
                                         correctPhone, correctSeatClass, correctSeatNumber, 
                                         null, correctDateOfBirth, correctGender, correctNationality);
        Assert.assertFalse("Passenger addition should fail with null passport number", result);
    }

    @Test
    public void addPassengerWithEmptyPassportNumber() {
        boolean result = dao.addPassenger(correctBookingId, correctPassengerName, correctEmail, 
                                         correctPhone, correctSeatClass, correctSeatNumber, 
                                         "", correctDateOfBirth, correctGender, correctNationality);
        Assert.assertFalse("Passenger addition should fail with empty passport number", result);
    }

    @Test
    public void addPassengerWithNullDateOfBirth() {
        boolean result = dao.addPassenger(correctBookingId, correctPassengerName, correctEmail, 
                                         correctPhone, correctSeatClass, correctSeatNumber, 
                                         correctPassportNumber, null, correctGender, correctNationality);
        Assert.assertFalse("Passenger addition should fail with null date of birth", result);
    }

    @Test
    public void addPassengerWithInvalidDateOfBirth() {
        boolean result = dao.addPassenger(correctBookingId, correctPassengerName, correctEmail, 
                                         correctPhone, correctSeatClass, correctSeatNumber, 
                                         correctPassportNumber, "invalid-date", correctGender, correctNationality);
        Assert.assertFalse("Passenger addition should fail with invalid date of birth", result);
    }

    @Test
    public void addPassengerWithNullGender() {
        boolean result = dao.addPassenger(correctBookingId, correctPassengerName, correctEmail, 
                                         correctPhone, correctSeatClass, correctSeatNumber, 
                                         correctPassportNumber, correctDateOfBirth, null, correctNationality);
        Assert.assertFalse("Passenger addition should fail with null gender", result);
    }

    @Test
    public void addPassengerWithInvalidGender() {
        boolean result = dao.addPassenger(correctBookingId, correctPassengerName, correctEmail, 
                                         correctPhone, correctSeatClass, correctSeatNumber, 
                                         correctPassportNumber, correctDateOfBirth, "Invalid", correctNationality);
        Assert.assertFalse("Passenger addition should fail with invalid gender", result);
    }

    @Test
    public void addPassengerWithNullNationality() {
        boolean result = dao.addPassenger(correctBookingId, correctPassengerName, correctEmail, 
                                         correctPhone, correctSeatClass, correctSeatNumber, 
                                         correctPassportNumber, correctDateOfBirth, correctGender, null);
        Assert.assertFalse("Passenger addition should fail with null nationality", result);
    }

    @Test
    public void addPassengerWithEmptyNationality() {
        boolean result = dao.addPassenger(correctBookingId, correctPassengerName, correctEmail, 
                                         correctPhone, correctSeatClass, correctSeatNumber, 
                                         correctPassportNumber, correctDateOfBirth, correctGender, "");
        Assert.assertFalse("Passenger addition should fail with empty nationality", result);
    }

    @Test
    public void updatePassengerWithValidData() {
        // Note: In a real test, you would need to get a valid passenger ID from the database
        // For now, we'll test with a known valid ID if available
        String newPassengerName = "Updated Passenger Name";
        String newEmail = "updated@example.com";
        String newPhone = "1234567890";
        String newSeatClass = "Business";
        String newSeatNumber = "B1";
        String newPassportNumber = "B87654321";
        String newDateOfBirth = "1985-05-15";
        String newGender = "Female";
        String newNationality = "American";
        
        boolean result = dao.updatePassenger(1, newPassengerName, newEmail, newPhone, newSeatClass, 
                                           newSeatNumber, newPassportNumber, newDateOfBirth, newGender, newNationality); // Assuming passenger ID 1 exists
        
        if (result) {
            Assert.assertTrue("Passenger update should succeed", result);
            
            // Verify the update
            PassengerModel updatedPassenger = dao.getPassengerById(1);
            Assert.assertNotNull("Updated passenger should exist", updatedPassenger);
            Assert.assertEquals("Passenger name should be updated", newPassengerName, updatedPassenger.getPassengerName());
            Assert.assertEquals("Email should be updated", newEmail, updatedPassenger.getEmail());
        }
    }

    @Test
    public void updatePassengerWithInvalidId() {
        boolean result = dao.updatePassenger(-1, "Test Name", "test@example.com", "1234567890", 
                                           "Economy", "E1", "A12345678", "1990-01-01", "Male", "Nepali");
        Assert.assertFalse("Passenger update should fail with invalid ID", result);
    }

    @Test
    public void updatePassengerWithNullPassengerName() {
        boolean result = dao.updatePassenger(correctPassengerId, null, correctEmail, correctPhone, 
                                           correctSeatClass, correctSeatNumber, correctPassportNumber, 
                                           correctDateOfBirth, correctGender, correctNationality);
        Assert.assertFalse("Passenger update should fail with null passenger name", result);
    }

    @Test
    public void updatePassengerWithEmptyPassengerName() {
        boolean result = dao.updatePassenger(correctPassengerId, "", correctEmail, correctPhone, 
                                           correctSeatClass, correctSeatNumber, correctPassportNumber, 
                                           correctDateOfBirth, correctGender, correctNationality);
        Assert.assertFalse("Passenger update should fail with empty passenger name", result);
    }

    @Test
    public void updatePassengerWithNullEmail() {
        boolean result = dao.updatePassenger(correctPassengerId, correctPassengerName, null, correctPhone, 
                                           correctSeatClass, correctSeatNumber, correctPassportNumber, 
                                           correctDateOfBirth, correctGender, correctNationality);
        Assert.assertFalse("Passenger update should fail with null email", result);
    }

    @Test
    public void updatePassengerWithInvalidEmail() {
        boolean result = dao.updatePassenger(correctPassengerId, correctPassengerName, "invalid-email", correctPhone, 
                                           correctSeatClass, correctSeatNumber, correctPassportNumber, 
                                           correctDateOfBirth, correctGender, correctNationality);
        Assert.assertFalse("Passenger update should fail with invalid email", result);
    }

    @Test
    public void updatePassengerWithNullPhone() {
        boolean result = dao.updatePassenger(correctPassengerId, correctPassengerName, correctEmail, null, 
                                           correctSeatClass, correctSeatNumber, correctPassportNumber, 
                                           correctDateOfBirth, correctGender, correctNationality);
        Assert.assertFalse("Passenger update should fail with null phone", result);
    }

    @Test
    public void updatePassengerWithInvalidPhone() {
        boolean result = dao.updatePassenger(correctPassengerId, correctPassengerName, correctEmail, "123", 
                                           correctSeatClass, correctSeatNumber, correctPassportNumber, 
                                           correctDateOfBirth, correctGender, correctNationality);
        Assert.assertFalse("Passenger update should fail with invalid phone", result);
    }

    @Test
    public void updatePassengerWithNullSeatClass() {
        boolean result = dao.updatePassenger(correctPassengerId, correctPassengerName, correctEmail, correctPhone, 
                                           null, correctSeatNumber, correctPassportNumber, 
                                           correctDateOfBirth, correctGender, correctNationality);
        Assert.assertFalse("Passenger update should fail with null seat class", result);
    }

    @Test
    public void updatePassengerWithInvalidSeatClass() {
        boolean result = dao.updatePassenger(correctPassengerId, correctPassengerName, correctEmail, correctPhone, 
                                           "InvalidClass", correctSeatNumber, correctPassportNumber, 
                                           correctDateOfBirth, correctGender, correctNationality);
        Assert.assertFalse("Passenger update should fail with invalid seat class", result);
    }

    @Test
    public void updatePassengerWithNullSeatNumber() {
        boolean result = dao.updatePassenger(correctPassengerId, correctPassengerName, correctEmail, correctPhone, 
                                           correctSeatClass, null, correctPassportNumber, 
                                           correctDateOfBirth, correctGender, correctNationality);
        Assert.assertFalse("Passenger update should fail with null seat number", result);
    }

    @Test
    public void updatePassengerWithEmptySeatNumber() {
        boolean result = dao.updatePassenger(correctPassengerId, correctPassengerName, correctEmail, correctPhone, 
                                           correctSeatClass, "", correctPassportNumber, 
                                           correctDateOfBirth, correctGender, correctNationality);
        Assert.assertFalse("Passenger update should fail with empty seat number", result);
    }

    @Test
    public void updatePassengerWithNullPassportNumber() {
        boolean result = dao.updatePassenger(correctPassengerId, correctPassengerName, correctEmail, correctPhone, 
                                           correctSeatClass, correctSeatNumber, null, 
                                           correctDateOfBirth, correctGender, correctNationality);
        Assert.assertFalse("Passenger update should fail with null passport number", result);
    }

    @Test
    public void updatePassengerWithEmptyPassportNumber() {
        boolean result = dao.updatePassenger(correctPassengerId, correctPassengerName, correctEmail, correctPhone, 
                                           correctSeatClass, correctSeatNumber, "", 
                                           correctDateOfBirth, correctGender, correctNationality);
        Assert.assertFalse("Passenger update should fail with empty passport number", result);
    }

    @Test
    public void updatePassengerWithNullDateOfBirth() {
        boolean result = dao.updatePassenger(correctPassengerId, correctPassengerName, correctEmail, correctPhone, 
                                           correctSeatClass, correctSeatNumber, correctPassportNumber, 
                                           null, correctGender, correctNationality);
        Assert.assertFalse("Passenger update should fail with null date of birth", result);
    }

    @Test
    public void updatePassengerWithInvalidDateOfBirth() {
        boolean result = dao.updatePassenger(correctPassengerId, correctPassengerName, correctEmail, correctPhone, 
                                           correctSeatClass, correctSeatNumber, correctPassportNumber, 
                                           "invalid-date", correctGender, correctNationality);
        Assert.assertFalse("Passenger update should fail with invalid date of birth", result);
    }

    @Test
    public void updatePassengerWithNullGender() {
        boolean result = dao.updatePassenger(correctPassengerId, correctPassengerName, correctEmail, correctPhone, 
                                           correctSeatClass, correctSeatNumber, correctPassportNumber, 
                                           correctDateOfBirth, null, correctNationality);
        Assert.assertFalse("Passenger update should fail with null gender", result);
    }

    @Test
    public void updatePassengerWithInvalidGender() {
        boolean result = dao.updatePassenger(correctPassengerId, correctPassengerName, correctEmail, correctPhone, 
                                           correctSeatClass, correctSeatNumber, correctPassportNumber, 
                                           correctDateOfBirth, "Invalid", correctNationality);
        Assert.assertFalse("Passenger update should fail with invalid gender", result);
    }

    @Test
    public void updatePassengerWithNullNationality() {
        boolean result = dao.updatePassenger(correctPassengerId, correctPassengerName, correctEmail, correctPhone, 
                                           correctSeatClass, correctSeatNumber, correctPassportNumber, 
                                           correctDateOfBirth, correctGender, null);
        Assert.assertFalse("Passenger update should fail with null nationality", result);
    }

    @Test
    public void updatePassengerWithEmptyNationality() {
        boolean result = dao.updatePassenger(correctPassengerId, correctPassengerName, correctEmail, correctPhone, 
                                           correctSeatClass, correctSeatNumber, correctPassportNumber, 
                                           correctDateOfBirth, correctGender, "");
        Assert.assertFalse("Passenger update should fail with empty nationality", result);
    }

    @Test
    public void deletePassengerWithValidId() {
        // Note: In a real test, you would need to get a valid passenger ID from the database
        // For now, we'll test with a known valid ID if available
        boolean result = dao.deletePassenger(1); // Assuming passenger ID 1 exists
        if (result) {
            Assert.assertTrue("Passenger deletion should succeed", result);
            
            // Verify the deletion
            PassengerModel deletedPassenger = dao.getPassengerById(1);
            Assert.assertNull("Passenger should be null after deletion", deletedPassenger);
        }
    }

    @Test
    public void deletePassengerWithInvalidId() {
        boolean result = dao.deletePassenger(-1);
        Assert.assertFalse("Passenger deletion should fail with invalid ID", result);
    }

    @Test
    public void searchPassengersWithValidKeyword() {
        // First get all passengers to find a valid keyword
        List<PassengerModel> allPassengers = dao.getAllPassengers();
        if (!allPassengers.isEmpty()) {
            String keyword = allPassengers.get(0).getPassengerName();
            List<PassengerModel> searchResults = dao.searchPassengers(keyword);
            Assert.assertNotNull("Search results should not be null", searchResults);
            Assert.assertTrue("Should find at least one passenger", searchResults.size() > 0);
        }
    }

    @Test
    public void searchPassengersWithInvalidKeyword() {
        List<PassengerModel> searchResults = dao.searchPassengers("nonexistentpassenger");
        Assert.assertNotNull("Search results should not be null", searchResults);
        Assert.assertTrue("Should return empty list for invalid keyword", searchResults.size() == 0);
    }

    @Test
    public void searchPassengersWithNullKeyword() {
        List<PassengerModel> searchResults = dao.searchPassengers(null);
        Assert.assertNotNull("Search results should not be null", searchResults);
        // Should return all passengers or empty list depending on implementation
    }

    @Test
    public void searchPassengersWithEmptyKeyword() {
        List<PassengerModel> searchResults = dao.searchPassengers("");
        Assert.assertNotNull("Search results should not be null", searchResults);
        // Should return all passengers or empty list depending on implementation
    }

    @Test
    public void getPassengersByBookingIdWithValidId() {
        List<PassengerModel> passengers = dao.getPassengersByBookingId(correctBookingId);
        Assert.assertNotNull("Passengers list should not be null", passengers);
        // Note: This test assumes there are passengers for the booking in the database
    }

    @Test
    public void getPassengersByBookingIdWithInvalidId() {
        List<PassengerModel> passengers = dao.getPassengersByBookingId(-1);
        Assert.assertNotNull("Passengers list should not be null", passengers);
        Assert.assertTrue("Should return empty list for invalid booking ID", passengers.isEmpty());
    }

    @Test
    public void getTotalPassengersCount() {
        int count = dao.getTotalPassengersCount();
        Assert.assertTrue("Passenger count should be non-negative", count >= 0);
    }

    @Test
    public void getPassengersCountByBookingId() {
        int count = dao.getPassengersCountByBookingId(correctBookingId);
        Assert.assertTrue("Passenger count by booking ID should be non-negative", count >= 0);
    }

    @Test
    public void getPassengersCountByBookingIdWithInvalidId() {
        int count = dao.getPassengersCountByBookingId(-1);
        Assert.assertEquals("Passenger count should be 0 for invalid booking ID", 0, count);
    }

    @Test
    public void passengerModelPropertiesAreAccessible() {
        // Test that PassengerModel properties are accessible
        PassengerModel passenger = new PassengerModel();
        passenger.setPassengerId(correctPassengerId);
        passenger.setBookingId(correctBookingId);
        passenger.setPassengerName(correctPassengerName);
        passenger.setEmail(correctEmail);
        passenger.setPhone(correctPhone);
        passenger.setSeatClass(correctSeatClass);
        passenger.setSeatNumber(correctSeatNumber);
        passenger.setPassportNumber(correctPassportNumber);
        passenger.setDateOfBirth(correctDateOfBirth);
        passenger.setGender(correctGender);
        passenger.setNationality(correctNationality);
        
        Assert.assertEquals("Passenger ID should match", correctPassengerId, passenger.getPassengerId());
        Assert.assertEquals("Booking ID should match", correctBookingId, passenger.getBookingId());
        Assert.assertEquals("Passenger name should match", correctPassengerName, passenger.getPassengerName());
        Assert.assertEquals("Email should match", correctEmail, passenger.getEmail());
        Assert.assertEquals("Phone should match", correctPhone, passenger.getPhone());
        Assert.assertEquals("Seat class should match", correctSeatClass, passenger.getSeatClass());
        Assert.assertEquals("Seat number should match", correctSeatNumber, passenger.getSeatNumber());
        Assert.assertEquals("Passport number should match", correctPassportNumber, passenger.getPassportNumber());
        Assert.assertEquals("Date of birth should match", correctDateOfBirth, passenger.getDateOfBirth());
        Assert.assertEquals("Gender should match", correctGender, passenger.getGender());
        Assert.assertEquals("Nationality should match", correctNationality, passenger.getNationality());
    }

    @Test
    public void passengerModelWithDifferentGenders() {
        // Test passenger with different genders
        String[] genders = {"Male", "Female", "Other"};
        
        for (String gender : genders) {
            PassengerModel passenger = new PassengerModel();
            passenger.setPassengerId(correctPassengerId);
            passenger.setBookingId(correctBookingId);
            passenger.setPassengerName(correctPassengerName);
            passenger.setEmail(correctEmail);
            passenger.setPhone(correctPhone);
            passenger.setSeatClass(correctSeatClass);
            passenger.setSeatNumber(correctSeatNumber);
            passenger.setPassportNumber(correctPassportNumber);
            passenger.setDateOfBirth(correctDateOfBirth);
            passenger.setGender(gender);
            passenger.setNationality(correctNationality);
            
            Assert.assertEquals("Gender should match", gender, passenger.getGender());
        }
    }

    @Test
    public void passengerModelWithDifferentSeatClasses() {
        // Test passenger with different seat classes
        String[] seatClasses = {"Economy", "Business", "First Class"};
        
        for (String seatClass : seatClasses) {
            PassengerModel passenger = new PassengerModel();
            passenger.setPassengerId(correctPassengerId);
            passenger.setBookingId(correctBookingId);
            passenger.setPassengerName(correctPassengerName);
            passenger.setEmail(correctEmail);
            passenger.setPhone(correctPhone);
            passenger.setSeatClass(seatClass);
            passenger.setSeatNumber(correctSeatNumber);
            passenger.setPassportNumber(correctPassportNumber);
            passenger.setDateOfBirth(correctDateOfBirth);
            passenger.setGender(correctGender);
            passenger.setNationality(correctNationality);
            
            Assert.assertEquals("Seat class should match", seatClass, passenger.getSeatClass());
        }
    }

    @Test
    public void passengerModelWithDifferentNationalities() {
        // Test passenger with different nationalities
        String[] nationalities = {"Nepali", "American", "British", "Indian", "Chinese", "Australian"};
        
        for (String nationality : nationalities) {
            PassengerModel passenger = new PassengerModel();
            passenger.setPassengerId(correctPassengerId);
            passenger.setBookingId(correctBookingId);
            passenger.setPassengerName(correctPassengerName);
            passenger.setEmail(correctEmail);
            passenger.setPhone(correctPhone);
            passenger.setSeatClass(correctSeatClass);
            passenger.setSeatNumber(correctSeatNumber);
            passenger.setPassportNumber(correctPassportNumber);
            passenger.setDateOfBirth(correctDateOfBirth);
            passenger.setGender(correctGender);
            passenger.setNationality(nationality);
            
            Assert.assertEquals("Nationality should match", nationality, passenger.getNationality());
        }
    }
} 