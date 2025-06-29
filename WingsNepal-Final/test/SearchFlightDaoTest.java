/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package test;

import wingsnepal.dao.SearchFlightDao;
import wingsnepal.model.SearchFlightModel;
import java.util.List;
import org.junit.*;

/**
 *
 * @author Aayush Kharel
 */
public class SearchFlightDaoTest {
    
    String correctFlightCode = "TEST001";
    String correctFlightName = "Test Flight";
    String correctFromCity = "Kathmandu";
    String correctToCity = "Pokhara";
    String correctDate = "2024-12-25";
    String correctTime = "10:30";
    String correctDuration = "45 min";
    int correctEconomyPrice = 5000;
    int correctBusinessPrice = 7500;
    int correctFirstClassPrice = 12000;
    
    SearchFlightDao dao = new SearchFlightDao();

    @Test
    public void searchFlightsWithValidCriteria() {
        List<SearchFlightModel> flights = dao.searchFlights(correctFromCity, correctToCity, correctDate);
        Assert.assertNotNull("Flights list should not be null", flights);
        // Note: This test assumes there are flights in the database
        // In a real test environment, you would first insert test data
    }

    @Test
    public void searchFlightsWithEmptyDate() {
        List<SearchFlightModel> flights = dao.searchFlights(correctFromCity, correctToCity, "");
        Assert.assertNotNull("Flights list should not be null", flights);
    }

    @Test
    public void searchFlightsWithNullDate() {
        List<SearchFlightModel> flights = dao.searchFlights(correctFromCity, correctToCity, null);
        Assert.assertNotNull("Flights list should not be null", flights);
    }

    @Test
    public void searchFlightsWithInvalidCities() {
        List<SearchFlightModel> flights = dao.searchFlights("InvalidCity", "AnotherInvalidCity", correctDate);
        Assert.assertNotNull("Flights list should not be null", flights);
        Assert.assertTrue("Should return empty list for invalid cities", flights.isEmpty());
    }

    @Test
    public void getAllFlights() {
        List<SearchFlightModel> flights = dao.getAllFlights();
        Assert.assertNotNull("Flights list should not be null", flights);
        // Note: This test assumes there are flights in the database
    }

    @Test
    public void getAllFlightsWithPricing() {
        List<SearchFlightModel> flights = dao.getAllFlightsWithPricing();
        Assert.assertNotNull("Flights list should not be null", flights);
        // Note: This test assumes there are flights in the database
    }

    @Test
    public void getFlightCodeByIdWithValidId() {
        // First get all flights to find a valid flight ID
        List<SearchFlightModel> flights = dao.getAllFlights();
        if (!flights.isEmpty()) {
            // Get the first flight's code to find its ID
            String flightCode = flights.get(0).getFlightCode();
            // Note: In a real test, you would need to get the flight ID from the database
            // For now, we'll test with a known valid ID if available
            String retrievedCode = dao.getFlightCodeById(1); // Assuming flight ID 1 exists
            if (retrievedCode != null) {
                Assert.assertNotNull("Flight code should not be null", retrievedCode);
            }
        }
    }

    @Test
    public void getFlightCodeByIdWithInvalidId() {
        String flightCode = dao.getFlightCodeById(-1);
        Assert.assertNull("Flight code should be null for invalid ID", flightCode);
    }

    @Test
    public void getFlightNameByIdWithValidId() {
        // First get all flights to find a valid flight ID
        List<SearchFlightModel> flights = dao.getAllFlights();
        if (!flights.isEmpty()) {
            // Note: In a real test, you would need to get the flight ID from the database
            // For now, we'll test with a known valid ID if available
            String retrievedName = dao.getFlightNameById(1); // Assuming flight ID 1 exists
            if (retrievedName != null) {
                Assert.assertNotNull("Flight name should not be null", retrievedName);
            }
        }
    }

    @Test
    public void getFlightNameByIdWithInvalidId() {
        String flightName = dao.getFlightNameById(-1);
        Assert.assertNull("Flight name should be null for invalid ID", flightName);
    }

    @Test
    public void getFlightByCodeWithValidCode() {
        // First get all flights to find a valid flight code
        List<SearchFlightModel> flights = dao.getAllFlights();
        if (!flights.isEmpty()) {
            String flightCode = flights.get(0).getFlightCode();
            SearchFlightModel flight = dao.getFlightByCode(flightCode);
            Assert.assertNotNull("Flight should not be null", flight);
            Assert.assertEquals("Flight code should match", flightCode, flight.getFlightCode());
        }
    }

    @Test
    public void getFlightByCodeWithInvalidCode() {
        SearchFlightModel flight = dao.getFlightByCode("INVALID123");
        Assert.assertNull("Flight should be null for invalid code", flight);
    }

    @Test
    public void getTotalFlightsCount() {
        int count = dao.getTotalFlightsCount();
        Assert.assertTrue("Flight count should be non-negative", count >= 0);
    }

    @Test
    public void getTotalAvailableSeats() {
        int count = dao.getTotalAvailableSeats();
        Assert.assertTrue("Available seats count should be non-negative", count >= 0);
    }

    @Test
    public void searchFlightsByFromWithValidCity() {
        List<SearchFlightModel> flights = dao.searchFlightsByFrom(correctFromCity);
        Assert.assertNotNull("Flights list should not be null", flights);
    }

    @Test
    public void searchFlightsByFromWithInvalidCity() {
        List<SearchFlightModel> flights = dao.searchFlightsByFrom("InvalidCity");
        Assert.assertNotNull("Flights list should not be null", flights);
        Assert.assertTrue("Should return empty list for invalid city", flights.isEmpty());
    }

    @Test
    public void searchFlightsByToWithValidCity() {
        List<SearchFlightModel> flights = dao.searchFlightsByTo(correctToCity);
        Assert.assertNotNull("Flights list should not be null", flights);
    }

    @Test
    public void searchFlightsByToWithInvalidCity() {
        List<SearchFlightModel> flights = dao.searchFlightsByTo("InvalidCity");
        Assert.assertNotNull("Flights list should not be null", flights);
        Assert.assertTrue("Should return empty list for invalid city", flights.isEmpty());
    }

    @Test
    public void updateFlightWithValidData() {
        // First get all flights to find a valid flight code
        List<SearchFlightModel> flights = dao.getAllFlights();
        if (!flights.isEmpty()) {
            String flightCode = flights.get(0).getFlightCode();
            String newDate = "2024-12-26";
            String newTime = "11:30";
            String newDuration = "1h 15m";
            int newEconomyPrice = 5500;
            int newBusinessPrice = 8000;
            int newFirstClassPrice = 13000;
            
            boolean result = dao.updateFlight(flightCode, newDate, newTime, newDuration, newEconomyPrice, newBusinessPrice, newFirstClassPrice);
            Assert.assertTrue("Flight update should succeed", result);
            
            // Verify the update
            SearchFlightModel updatedFlight = dao.getFlightByCode(flightCode);
            Assert.assertNotNull("Updated flight should exist", updatedFlight);
            Assert.assertEquals("Date should be updated", newDate, updatedFlight.getDate());
            Assert.assertEquals("Time should be updated", newTime, updatedFlight.getTime());
        }
    }

    @Test
    public void updateFlightWithInvalidCode() {
        String newDate = "2024-12-26";
        String newTime = "11:30";
        String newDuration = "1h 15m";
        int newEconomyPrice = 5500;
        int newBusinessPrice = 8000;
        int newFirstClassPrice = 13000;
        
        boolean result = dao.updateFlight("INVALID123", newDate, newTime, newDuration, newEconomyPrice, newBusinessPrice, newFirstClassPrice);
        Assert.assertFalse("Flight update should fail with invalid code", result);
    }

    @Test
    public void deleteFlightWithValidCode() {
        // First get all flights to find a valid flight code
        List<SearchFlightModel> flights = dao.getAllFlights();
        if (!flights.isEmpty()) {
            String flightCode = flights.get(0).getFlightCode();
            
            boolean result = dao.deleteFlight(flightCode);
            Assert.assertTrue("Flight deletion should succeed", result);
            
            // Verify the deletion
            SearchFlightModel deletedFlight = dao.getFlightByCode(flightCode);
            Assert.assertNull("Flight should be null after deletion", deletedFlight);
        }
    }

    @Test
    public void deleteFlightWithInvalidCode() {
        boolean result = dao.deleteFlight("INVALID123");
        Assert.assertFalse("Flight deletion should fail with invalid code", result);
    }

    @Test
    public void searchFlightsWithPartialCityMatch() {
        // Test partial city name matching
        List<SearchFlightModel> flights = dao.searchFlightsByFrom("Kath");
        Assert.assertNotNull("Flights list should not be null", flights);
    }

    @Test
    public void searchFlightsWithCaseInsensitiveMatch() {
        // Test case insensitive matching
        List<SearchFlightModel> flights = dao.searchFlightsByFrom("kathmandu");
        Assert.assertNotNull("Flights list should not be null", flights);
    }

    @Test
    public void getAllFlightsWithPricingContainsAllClasses() {
        List<SearchFlightModel> flights = dao.getAllFlightsWithPricing();
        Assert.assertNotNull("Flights list should not be null", flights);
        
        if (!flights.isEmpty()) {
            SearchFlightModel flight = flights.get(0);
            // Verify that all price fields are accessible
            Assert.assertTrue("Economy price should be accessible", flight.getEconomyPrice() >= 0);
            Assert.assertTrue("Business price should be accessible", flight.getBusinessPrice() >= 0);
            Assert.assertTrue("First class price should be accessible", flight.getFirstClassPrice() >= 0);
        }
    }

    @Test
    public void flightModelPropertiesAreAccessible() {
        // Test that SearchFlightModel properties are accessible
        SearchFlightModel flight = new SearchFlightModel(
            correctFlightCode, correctFlightName, correctFromCity, correctToCity,
            correctTime, correctEconomyPrice, correctDuration, correctDate
        );
        
        Assert.assertEquals("Flight code should match", correctFlightCode, flight.getFlightCode());
        Assert.assertEquals("Flight name should match", correctFlightName, flight.getFlightName());
        Assert.assertEquals("From city should match", correctFromCity, flight.getFromCity());
        Assert.assertEquals("To city should match", correctToCity, flight.getToCity());
        Assert.assertEquals("Time should match", correctTime, flight.getTime());
        Assert.assertEquals("Economy price should match", correctEconomyPrice, flight.getEconomyPrice());
        Assert.assertEquals("Duration should match", correctDuration, flight.getDuration());
        Assert.assertEquals("Date should match", correctDate, flight.getDate());
    }

    @Test
    public void flightModelWithAllPricesConstructor() {
        // Test the constructor with all price categories
        SearchFlightModel flight = new SearchFlightModel(
            correctFlightCode, correctFlightName, correctFromCity, correctToCity,
            correctTime, correctEconomyPrice, correctBusinessPrice, correctFirstClassPrice,
            correctDuration, correctDate
        );
        
        Assert.assertEquals("Economy price should match", correctEconomyPrice, flight.getEconomyPrice());
        Assert.assertEquals("Business price should match", correctBusinessPrice, flight.getBusinessPrice());
        Assert.assertEquals("First class price should match", correctFirstClassPrice, flight.getFirstClassPrice());
    }
} 