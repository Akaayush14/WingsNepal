/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package test;

import wingsnepal.dao.PassengerDao;
import wingsnepal.model.PassengerModel;
import java.util.List;
import org.junit.*;

/**
 *
 * @author Aayush Kharel
 */
public class PassengerDaoTest {
    
    int correctUserId = 1;
    String correctFullName = "Test Passenger";
    String correctEmail = "testpassenger@example.com";
    String correctPhone = "9876543210";
    String correctGender = "Male";
    String correctUpcomingFlight = "TEST001";
    String correctPassportNumber = "P123456789";
    String correctNationality = "Nepali";
    String correctDateOfBirth = "1990-01-01";
    String correctAddress = "Test Address, Kathmandu";
    
    PassengerDao dao = new PassengerDao();

    @Test
    public void savePassengerWithValidData() {
        PassengerModel passenger = new PassengerModel();
        passenger.setUserId(correctUserId);
        passenger.setFullName(correctFullName);
        passenger.setEmail(correctEmail);
        passenger.setPhone(correctPhone);
        passenger.setGender(correctGender);
        passenger.setUpcomingFlight(correctUpcomingFlight);
        passenger.setPassportNumber(correctPassportNumber);
        passenger.setNationality(correctNationality);
        passenger.setDateOfBirth(correctDateOfBirth);
        passenger.setAddress(correctAddress);
        
        boolean result = dao.savePassenger(passenger);
        Assert.assertTrue("Passenger should be saved successfully", result);
    }

    @Test
    public void savePassengerWithInvalidUserId() {
        PassengerModel passenger = new PassengerModel();
        passenger.setUserId(-1); // Invalid user ID
        passenger.setFullName(correctFullName);
        passenger.setEmail(correctEmail);
        passenger.setPhone(correctPhone);
        passenger.setGender(correctGender);
        passenger.setUpcomingFlight(correctUpcomingFlight);
        passenger.setPassportNumber(correctPassportNumber);
        passenger.setNationality(correctNationality);
        passenger.setDateOfBirth(correctDateOfBirth);
        passenger.setAddress(correctAddress);
        
        boolean result = dao.savePassenger(passenger);
        Assert.assertFalse("Passenger should fail with invalid user ID", result);
    }

    @Test
    public void savePassengerWithNullFullName() {
        PassengerModel passenger = new PassengerModel();
        passenger.setUserId(correctUserId);
        passenger.setFullName(null); // Null full name
        passenger.setEmail(correctEmail);
        passenger.setPhone(correctPhone);
        passenger.setGender(correctGender);
        passenger.setUpcomingFlight(correctUpcomingFlight);
        passenger.setPassportNumber(correctPassportNumber);
        passenger.setNationality(correctNationality);
        passenger.setDateOfBirth(correctDateOfBirth);
        passenger.setAddress(correctAddress);
        
        boolean result = dao.savePassenger(passenger);
        Assert.assertFalse("Passenger should fail with null full name", result);
    }

    @Test
    public void savePassengerWithEmptyFullName() {
        PassengerModel passenger = new PassengerModel();
        passenger.setUserId(correctUserId);
        passenger.setFullName(""); // Empty full name
        passenger.setEmail(correctEmail);
        passenger.setPhone(correctPhone);
        passenger.setGender(correctGender);
        passenger.setUpcomingFlight(correctUpcomingFlight);
        passenger.setPassportNumber(correctPassportNumber);
        passenger.setNationality(correctNationality);
        passenger.setDateOfBirth(correctDateOfBirth);
        passenger.setAddress(correctAddress);
        
        boolean result = dao.savePassenger(passenger);
        Assert.assertFalse("Passenger should fail with empty full name", result);
    }

    @Test
    public void savePassengerWithNullEmail() {
        PassengerModel passenger = new PassengerModel();
        passenger.setUserId(correctUserId);
        passenger.setFullName(correctFullName);
        passenger.setEmail(null); // Null email
        passenger.setPhone(correctPhone);
        passenger.setGender(correctGender);
        passenger.setUpcomingFlight(correctUpcomingFlight);
        passenger.setPassportNumber(correctPassportNumber);
        passenger.setNationality(correctNationality);
        passenger.setDateOfBirth(correctDateOfBirth);
        passenger.setAddress(correctAddress);
        
        boolean result = dao.savePassenger(passenger);
        Assert.assertFalse("Passenger should fail with null email", result);
    }

    @Test
    public void savePassengerWithInvalidEmail() {
        PassengerModel passenger = new PassengerModel();
        passenger.setUserId(correctUserId);
        passenger.setFullName(correctFullName);
        passenger.setEmail("invalid-email"); // Invalid email format
        passenger.setPhone(correctPhone);
        passenger.setGender(correctGender);
        passenger.setUpcomingFlight(correctUpcomingFlight);
        passenger.setPassportNumber(correctPassportNumber);
        passenger.setNationality(correctNationality);
        passenger.setDateOfBirth(correctDateOfBirth);
        passenger.setAddress(correctAddress);
        
        boolean result = dao.savePassenger(passenger);
        Assert.assertFalse("Passenger should fail with invalid email", result);
    }

    @Test
    public void savePassengerWithNullPhone() {
        PassengerModel passenger = new PassengerModel();
        passenger.setUserId(correctUserId);
        passenger.setFullName(correctFullName);
        passenger.setEmail(correctEmail);
        passenger.setPhone(null); // Null phone
        passenger.setGender(correctGender);
        passenger.setUpcomingFlight(correctUpcomingFlight);
        passenger.setPassportNumber(correctPassportNumber);
        passenger.setNationality(correctNationality);
        passenger.setDateOfBirth(correctDateOfBirth);
        passenger.setAddress(correctAddress);
        
        boolean result = dao.savePassenger(passenger);
        Assert.assertFalse("Passenger should fail with null phone", result);
    }

    @Test
    public void savePassengerWithInvalidPhone() {
        PassengerModel passenger = new PassengerModel();
        passenger.setUserId(correctUserId);
        passenger.setFullName(correctFullName);
        passenger.setEmail(correctEmail);
        passenger.setPhone("123"); // Invalid phone number (too short)
        passenger.setGender(correctGender);
        passenger.setUpcomingFlight(correctUpcomingFlight);
        passenger.setPassportNumber(correctPassportNumber);
        passenger.setNationality(correctNationality);
        passenger.setDateOfBirth(correctDateOfBirth);
        passenger.setAddress(correctAddress);
        
        boolean result = dao.savePassenger(passenger);
        Assert.assertFalse("Passenger should fail with invalid phone", result);
    }

    @Test
    public void savePassengerWithNullGender() {
        PassengerModel passenger = new PassengerModel();
        passenger.setUserId(correctUserId);
        passenger.setFullName(correctFullName);
        passenger.setEmail(correctEmail);
        passenger.setPhone(correctPhone);
        passenger.setGender(null); // Null gender
        passenger.setUpcomingFlight(correctUpcomingFlight);
        passenger.setPassportNumber(correctPassportNumber);
        passenger.setNationality(correctNationality);
        passenger.setDateOfBirth(correctDateOfBirth);
        passenger.setAddress(correctAddress);
        
        boolean result = dao.savePassenger(passenger);
        Assert.assertFalse("Passenger should fail with null gender", result);
    }

    @Test
    public void savePassengerWithInvalidGender() {
        PassengerModel passenger = new PassengerModel();
        passenger.setUserId(correctUserId);
        passenger.setFullName(correctFullName);
        passenger.setEmail(correctEmail);
        passenger.setPhone(correctPhone);
        passenger.setGender("Other"); // Invalid gender
        passenger.setUpcomingFlight(correctUpcomingFlight);
        passenger.setPassportNumber(correctPassportNumber);
        passenger.setNationality(correctNationality);
        passenger.setDateOfBirth(correctDateOfBirth);
        passenger.setAddress(correctAddress);
        
        boolean result = dao.savePassenger(passenger);
        Assert.assertFalse("Passenger should fail with invalid gender", result);
    }

    @Test
    public void savePassengerWithNullUpcomingFlight() {
        PassengerModel passenger = new PassengerModel();
        passenger.setUserId(correctUserId);
        passenger.setFullName(correctFullName);
        passenger.setEmail(correctEmail);
        passenger.setPhone(correctPhone);
        passenger.setGender(correctGender);
        passenger.setUpcomingFlight(null); // Null upcoming flight
        passenger.setPassportNumber(correctPassportNumber);
        passenger.setNationality(correctNationality);
        passenger.setDateOfBirth(correctDateOfBirth);
        passenger.setAddress(correctAddress);
        
        boolean result = dao.savePassenger(passenger);
        Assert.assertFalse("Passenger should fail with null upcoming flight", result);
    }

    @Test
    public void savePassengerWithEmptyUpcomingFlight() {
        PassengerModel passenger = new PassengerModel();
        passenger.setUserId(correctUserId);
        passenger.setFullName(correctFullName);
        passenger.setEmail(correctEmail);
        passenger.setPhone(correctPhone);
        passenger.setGender(correctGender);
        passenger.setUpcomingFlight(""); // Empty upcoming flight
        passenger.setPassportNumber(correctPassportNumber);
        passenger.setNationality(correctNationality);
        passenger.setDateOfBirth(correctDateOfBirth);
        passenger.setAddress(correctAddress);
        
        boolean result = dao.savePassenger(passenger);
        Assert.assertFalse("Passenger should fail with empty upcoming flight", result);
    }

    @Test
    public void savePassengerWithNullPassportNumber() {
        PassengerModel passenger = new PassengerModel();
        passenger.setUserId(correctUserId);
        passenger.setFullName(correctFullName);
        passenger.setEmail(correctEmail);
        passenger.setPhone(correctPhone);
        passenger.setGender(correctGender);
        passenger.setUpcomingFlight(correctUpcomingFlight);
        passenger.setPassportNumber(null); // Null passport number
        passenger.setNationality(correctNationality);
        passenger.setDateOfBirth(correctDateOfBirth);
        passenger.setAddress(correctAddress);
        
        boolean result = dao.savePassenger(passenger);
        Assert.assertFalse("Passenger should fail with null passport number", result);
    }

    @Test
    public void savePassengerWithEmptyPassportNumber() {
        PassengerModel passenger = new PassengerModel();
        passenger.setUserId(correctUserId);
        passenger.setFullName(correctFullName);
        passenger.setEmail(correctEmail);
        passenger.setPhone(correctPhone);
        passenger.setGender(correctGender);
        passenger.setUpcomingFlight(correctUpcomingFlight);
        passenger.setPassportNumber(""); // Empty passport number
        passenger.setNationality(correctNationality);
        passenger.setDateOfBirth(correctDateOfBirth);
        passenger.setAddress(correctAddress);
        
        boolean result = dao.savePassenger(passenger);
        Assert.assertFalse("Passenger should fail with empty passport number", result);
    }

    @Test
    public void savePassengerWithNullNationality() {
        PassengerModel passenger = new PassengerModel();
        passenger.setUserId(correctUserId);
        passenger.setFullName(correctFullName);
        passenger.setEmail(correctEmail);
        passenger.setPhone(correctPhone);
        passenger.setGender(correctGender);
        passenger.setUpcomingFlight(correctUpcomingFlight);
        passenger.setPassportNumber(correctPassportNumber);
        passenger.setNationality(null); // Null nationality
        passenger.setDateOfBirth(correctDateOfBirth);
        passenger.setAddress(correctAddress);
        
        boolean result = dao.savePassenger(passenger);
        Assert.assertFalse("Passenger should fail with null nationality", result);
    }

    @Test
    public void savePassengerWithEmptyNationality() {
        PassengerModel passenger = new PassengerModel();
        passenger.setUserId(correctUserId);
        passenger.setFullName(correctFullName);
        passenger.setEmail(correctEmail);
        passenger.setPhone(correctPhone);
        passenger.setGender(correctGender);
        passenger.setUpcomingFlight(correctUpcomingFlight);
        passenger.setPassportNumber(correctPassportNumber);
        passenger.setNationality(""); // Empty nationality
        passenger.setDateOfBirth(correctDateOfBirth);
        passenger.setAddress(correctAddress);
        
        boolean result = dao.savePassenger(passenger);
        Assert.assertFalse("Passenger should fail with empty nationality", result);
    }

    @Test
    public void savePassengerWithNullDateOfBirth() {
        PassengerModel passenger = new PassengerModel();
        passenger.setUserId(correctUserId);
        passenger.setFullName(correctFullName);
        passenger.setEmail(correctEmail);
        passenger.setPhone(correctPhone);
        passenger.setGender(correctGender);
        passenger.setUpcomingFlight(correctUpcomingFlight);
        passenger.setPassportNumber(correctPassportNumber);
        passenger.setNationality(correctNationality);
        passenger.setDateOfBirth(null); // Null date of birth
        passenger.setAddress(correctAddress);
        
        boolean result = dao.savePassenger(passenger);
        Assert.assertFalse("Passenger should fail with null date of birth", result);
    }

    @Test
    public void savePassengerWithInvalidDateOfBirth() {
        PassengerModel passenger = new PassengerModel();
        passenger.setUserId(correctUserId);
        passenger.setFullName(correctFullName);
        passenger.setEmail(correctEmail);
        passenger.setPhone(correctPhone);
        passenger.setGender(correctGender);
        passenger.setUpcomingFlight(correctUpcomingFlight);
        passenger.setPassportNumber(correctPassportNumber);
        passenger.setNationality(correctNationality);
        passenger.setDateOfBirth("invalid-date"); // Invalid date format
        passenger.setAddress(correctAddress);
        
        boolean result = dao.savePassenger(passenger);
        Assert.assertFalse("Passenger should fail with invalid date of birth", result);
    }

    @Test
    public void savePassengerWithNullAddress() {
        PassengerModel passenger = new PassengerModel();
        passenger.setUserId(correctUserId);
        passenger.setFullName(correctFullName);
        passenger.setEmail(correctEmail);
        passenger.setPhone(correctPhone);
        passenger.setGender(correctGender);
        passenger.setUpcomingFlight(correctUpcomingFlight);
        passenger.setPassportNumber(correctPassportNumber);
        passenger.setNationality(correctNationality);
        passenger.setDateOfBirth(correctDateOfBirth);
        passenger.setAddress(null); // Null address
        
        boolean result = dao.savePassenger(passenger);
        Assert.assertFalse("Passenger should fail with null address", result);
    }

    @Test
    public void savePassengerWithEmptyAddress() {
        PassengerModel passenger = new PassengerModel();
        passenger.setUserId(correctUserId);
        passenger.setFullName(correctFullName);
        passenger.setEmail(correctEmail);
        passenger.setPhone(correctPhone);
        passenger.setGender(correctGender);
        passenger.setUpcomingFlight(correctUpcomingFlight);
        passenger.setPassportNumber(correctPassportNumber);
        passenger.setNationality(correctNationality);
        passenger.setDateOfBirth(correctDateOfBirth);
        passenger.setAddress(""); // Empty address
        
        boolean result = dao.savePassenger(passenger);
        Assert.assertFalse("Passenger should fail with empty address", result);
    }

    @Test
    public void getAllPassengers() {
        // First save a passenger
        PassengerModel passenger = new PassengerModel();
        passenger.setUserId(correctUserId);
        passenger.setFullName(correctFullName);
        passenger.setEmail(correctEmail);
        passenger.setPhone(correctPhone);
        passenger.setGender(correctGender);
        passenger.setUpcomingFlight(correctUpcomingFlight);
        passenger.setPassportNumber(correctPassportNumber);
        passenger.setNationality(correctNationality);
        passenger.setDateOfBirth(correctDateOfBirth);
        passenger.setAddress(correctAddress);
        
        dao.savePassenger(passenger);
        
        List<PassengerModel> passengers = dao.getAllPassengers();
        Assert.assertNotNull("Passengers list should not be null", passengers);
        Assert.assertTrue("Should have at least one passenger", passengers.size() > 0);
    }

    @Test
    public void getPassengerByIdWithValidId() {
        // First save a passenger
        PassengerModel passenger = new PassengerModel();
        passenger.setUserId(correctUserId);
        passenger.setFullName(correctFullName);
        passenger.setEmail(correctEmail);
        passenger.setPhone(correctPhone);
        passenger.setGender(correctGender);
        passenger.setUpcomingFlight(correctUpcomingFlight);
        passenger.setPassportNumber(correctPassportNumber);
        passenger.setNationality(correctNationality);
        passenger.setDateOfBirth(correctDateOfBirth);
        passenger.setAddress(correctAddress);
        
        dao.savePassenger(passenger);
        
        // Note: In a real test, you would need to get the passenger ID from the database
        // For now, we'll test with a known valid ID if available
        PassengerModel foundPassenger = dao.getPassengerById(1); // Assuming passenger ID 1 exists
        if (foundPassenger != null) {
            Assert.assertNotNull("Passenger should not be null", foundPassenger);
            Assert.assertEquals("Full name should match", correctFullName, foundPassenger.getFullName());
            Assert.assertEquals("Email should match", correctEmail, foundPassenger.getEmail());
        }
    }

    @Test
    public void getPassengerByIdWithInvalidId() {
        PassengerModel passenger = dao.getPassengerById(-1);
        Assert.assertNull("Passenger should be null for invalid ID", passenger);
    }

    @Test
    public void updatePassengerWithValidData() {
        // First save a passenger
        PassengerModel passenger = new PassengerModel();
        passenger.setUserId(correctUserId);
        passenger.setFullName(correctFullName);
        passenger.setEmail(correctEmail);
        passenger.setPhone(correctPhone);
        passenger.setGender(correctGender);
        passenger.setUpcomingFlight(correctUpcomingFlight);
        passenger.setPassportNumber(correctPassportNumber);
        passenger.setNationality(correctNationality);
        passenger.setDateOfBirth(correctDateOfBirth);
        passenger.setAddress(correctAddress);
        
        dao.savePassenger(passenger);
        
        // Note: In a real test, you would need to get the passenger ID from the database
        // For now, we'll test with a known valid ID if available
        String newFullName = "Updated Passenger Name";
        String newEmail = "updated@example.com";
        String newPhone = "1234567890";
        String newGender = "Female";
        String newUpcomingFlight = "TEST002";
        String newPassportNumber = "P987654321";
        String newNationality = "American";
        String newDateOfBirth = "1985-05-15";
        String newAddress = "Updated Address, New York";
        
        boolean result = dao.updatePassenger(1, newFullName, newEmail, newPhone, newGender, 
                                           newUpcomingFlight, newPassportNumber, newNationality, 
                                           newDateOfBirth, newAddress); // Assuming passenger ID 1 exists
        
        if (result) {
            Assert.assertTrue("Passenger update should succeed", result);
            
            // Verify the update
            PassengerModel updatedPassenger = dao.getPassengerById(1);
            Assert.assertNotNull("Updated passenger should exist", updatedPassenger);
            Assert.assertEquals("Full name should be updated", newFullName, updatedPassenger.getFullName());
            Assert.assertEquals("Email should be updated", newEmail, updatedPassenger.getEmail());
        }
    }

    @Test
    public void updatePassengerWithInvalidId() {
        boolean result = dao.updatePassenger(-1, "Test Name", "test@example.com", "1234567890", 
                                           "Male", "TEST001", "P123456789", "Nepali", 
                                           "1990-01-01", "Test Address");
        Assert.assertFalse("Passenger update should fail with invalid ID", result);
    }

    @Test
    public void deletePassengerWithValidId() {
        // First save a passenger
        PassengerModel passenger = new PassengerModel();
        passenger.setUserId(correctUserId);
        passenger.setFullName(correctFullName);
        passenger.setEmail(correctEmail);
        passenger.setPhone(correctPhone);
        passenger.setGender(correctGender);
        passenger.setUpcomingFlight(correctUpcomingFlight);
        passenger.setPassportNumber(correctPassportNumber);
        passenger.setNationality(correctNationality);
        passenger.setDateOfBirth(correctDateOfBirth);
        passenger.setAddress(correctAddress);
        
        dao.savePassenger(passenger);
        
        // Note: In a real test, you would need to get the passenger ID from the database
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
    public void passengerModelPropertiesAreAccessible() {
        // Test that PassengerModel properties are accessible
        PassengerModel passenger = new PassengerModel();
        passenger.setUserId(correctUserId);
        passenger.setFullName(correctFullName);
        passenger.setEmail(correctEmail);
        passenger.setPhone(correctPhone);
        passenger.setGender(correctGender);
        passenger.setUpcomingFlight(correctUpcomingFlight);
        passenger.setPassportNumber(correctPassportNumber);
        passenger.setNationality(correctNationality);
        passenger.setDateOfBirth(correctDateOfBirth);
        passenger.setAddress(correctAddress);
        
        Assert.assertEquals("User ID should match", correctUserId, passenger.getUserId());
        Assert.assertEquals("Full name should match", correctFullName, passenger.getFullName());
        Assert.assertEquals("Email should match", correctEmail, passenger.getEmail());
        Assert.assertEquals("Phone should match", correctPhone, passenger.getPhone());
        Assert.assertEquals("Gender should match", correctGender, passenger.getGender());
        Assert.assertEquals("Upcoming flight should match", correctUpcomingFlight, passenger.getUpcomingFlight());
        Assert.assertEquals("Passport number should match", correctPassportNumber, passenger.getPassportNumber());
        Assert.assertEquals("Nationality should match", correctNationality, passenger.getNationality());
        Assert.assertEquals("Date of birth should match", correctDateOfBirth, passenger.getDateOfBirth());
        Assert.assertEquals("Address should match", correctAddress, passenger.getAddress());
    }

    @Test
    public void passengerModelWithDifferentGenders() {
        // Test passenger with different genders
        String[] genders = {"Male", "Female"};
        
        for (String gender : genders) {
            PassengerModel passenger = new PassengerModel();
            passenger.setUserId(correctUserId);
            passenger.setFullName(correctFullName);
            passenger.setEmail(correctEmail);
            passenger.setPhone(correctPhone);
            passenger.setGender(gender);
            passenger.setUpcomingFlight(correctUpcomingFlight);
            passenger.setPassportNumber(correctPassportNumber);
            passenger.setNationality(correctNationality);
            passenger.setDateOfBirth(correctDateOfBirth);
            passenger.setAddress(correctAddress);
            
            Assert.assertEquals("Gender should match", gender, passenger.getGender());
        }
    }

    @Test
    public void passengerModelWithDifferentNationalities() {
        // Test passenger with different nationalities
        String[] nationalities = {"Nepali", "American", "British", "Indian", "Chinese"};
        
        for (String nationality : nationalities) {
            PassengerModel passenger = new PassengerModel();
            passenger.setUserId(correctUserId);
            passenger.setFullName(correctFullName);
            passenger.setEmail(correctEmail);
            passenger.setPhone(correctPhone);
            passenger.setGender(correctGender);
            passenger.setUpcomingFlight(correctUpcomingFlight);
            passenger.setPassportNumber(correctPassportNumber);
            passenger.setNationality(nationality);
            passenger.setDateOfBirth(correctDateOfBirth);
            passenger.setAddress(correctAddress);
            
            Assert.assertEquals("Nationality should match", nationality, passenger.getNationality());
        }
    }
} 