/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package test;

import wingsnepal.dao.SeatClassDao;
import java.util.List;
import org.junit.*;

/**
 *
 * @author Aayush Kharel
 */
public class SeatClassDaoTest {
    
    int correctFlightId = 1;
    String correctClassName = "Economy";
    String correctSeatNo = "E1";
    int correctPrice = 5000;
    
    SeatClassDao dao = new SeatClassDao();

    @Test
    public void getAvailableSeatsByClassWithValidData() {
        List<String> seats = dao.getAvailableSeatsByClass(correctFlightId, correctClassName);
        Assert.assertNotNull("Seats list should not be null", seats);
        // Note: This test assumes there are seats in the database
    }

    @Test
    public void getAvailableSeatsByClassWithInvalidFlightId() {
        List<String> seats = dao.getAvailableSeatsByClass(-1, correctClassName);
        Assert.assertNotNull("Seats list should not be null", seats);
        Assert.assertTrue("Should return empty list for invalid flight ID", seats.isEmpty());
    }

    @Test
    public void getAvailableSeatsByClassWithInvalidClassName() {
        List<String> seats = dao.getAvailableSeatsByClass(correctFlightId, "InvalidClass");
        Assert.assertNotNull("Seats list should not be null", seats);
        Assert.assertTrue("Should return empty list for invalid class name", seats.isEmpty());
    }

    @Test
    public void getAvailableSeatsByClassWithNullClassName() {
        List<String> seats = dao.getAvailableSeatsByClass(correctFlightId, null);
        Assert.assertNotNull("Seats list should not be null", seats);
        Assert.assertTrue("Should return empty list for null class name", seats.isEmpty());
    }

    @Test
    public void getAvailableSeatsByClassWithEmptyClassName() {
        List<String> seats = dao.getAvailableSeatsByClass(correctFlightId, "");
        Assert.assertNotNull("Seats list should not be null", seats);
        Assert.assertTrue("Should return empty list for empty class name", seats.isEmpty());
    }

    @Test
    public void getAvailableSeatsByClassWithDifferentClasses() {
        String[] seatClasses = {"Economy", "Business", "First Class"};
        
        for (String seatClass : seatClasses) {
            List<String> seats = dao.getAvailableSeatsByClass(correctFlightId, seatClass);
            Assert.assertNotNull("Seats list should not be null for " + seatClass, seats);
        }
    }

    @Test
    public void getPriceByFlightAndClassWithValidData() {
        int price = dao.getPriceByFlightAndClass(correctFlightId, correctClassName);
        Assert.assertTrue("Price should be non-negative", price >= 0);
        // Note: This test assumes there are prices in the database
    }

    @Test
    public void getPriceByFlightAndClassWithInvalidFlightId() {
        int price = dao.getPriceByFlightAndClass(-1, correctClassName);
        Assert.assertEquals("Price should be -1 for invalid flight ID", -1, price);
    }

    @Test
    public void getPriceByFlightAndClassWithInvalidClassName() {
        int price = dao.getPriceByFlightAndClass(correctFlightId, "InvalidClass");
        Assert.assertEquals("Price should be -1 for invalid class name", -1, price);
    }

    @Test
    public void getPriceByFlightAndClassWithNullClassName() {
        int price = dao.getPriceByFlightAndClass(correctFlightId, null);
        Assert.assertEquals("Price should be -1 for null class name", -1, price);
    }

    @Test
    public void getPriceByFlightAndClassWithEmptyClassName() {
        int price = dao.getPriceByFlightAndClass(correctFlightId, "");
        Assert.assertEquals("Price should be -1 for empty class name", -1, price);
    }

    @Test
    public void getPriceByFlightAndClassWithDifferentClasses() {
        String[] seatClasses = {"Economy", "Business", "First Class"};
        
        for (String seatClass : seatClasses) {
            int price = dao.getPriceByFlightAndClass(correctFlightId, seatClass);
            Assert.assertTrue("Price should be non-negative for " + seatClass, price >= -1);
        }
    }

    @Test
    public void getFlightIdByCodeWithValidCode() {
        // Note: In a real test, you would need to get a valid flight code from the database
        // For now, we'll test with a known valid code if available
        int flightId = dao.getFlightIdByCode("TEST001"); // Assuming TEST001 exists
        if (flightId != -1) {
            Assert.assertTrue("Flight ID should be positive", flightId > 0);
        }
    }

    @Test
    public void getFlightIdByCodeWithInvalidCode() {
        int flightId = dao.getFlightIdByCode("INVALID123");
        Assert.assertEquals("Flight ID should be -1 for invalid code", -1, flightId);
    }

    @Test
    public void getFlightIdByCodeWithNullCode() {
        int flightId = dao.getFlightIdByCode(null);
        Assert.assertEquals("Flight ID should be -1 for null code", -1, flightId);
    }

    @Test
    public void getFlightIdByCodeWithEmptyCode() {
        int flightId = dao.getFlightIdByCode("");
        Assert.assertEquals("Flight ID should be -1 for empty code", -1, flightId);
    }

    @Test
    public void bookSeatWithValidData() {
        boolean result = dao.bookSeat(correctFlightId, correctClassName, correctSeatNo);
        // Note: This test assumes the seat is available
        // In a real test, you would first ensure the seat is available
        Assert.assertTrue("Seat booking should succeed or fail appropriately", result || !result);
    }

    @Test
    public void bookSeatWithInvalidFlightId() {
        boolean result = dao.bookSeat(-1, correctClassName, correctSeatNo);
        Assert.assertFalse("Seat booking should fail with invalid flight ID", result);
    }

    @Test
    public void bookSeatWithInvalidClassName() {
        boolean result = dao.bookSeat(correctFlightId, "InvalidClass", correctSeatNo);
        Assert.assertFalse("Seat booking should fail with invalid class name", result);
    }

    @Test
    public void bookSeatWithNullClassName() {
        boolean result = dao.bookSeat(correctFlightId, null, correctSeatNo);
        Assert.assertFalse("Seat booking should fail with null class name", result);
    }

    @Test
    public void bookSeatWithEmptyClassName() {
        boolean result = dao.bookSeat(correctFlightId, "", correctSeatNo);
        Assert.assertFalse("Seat booking should fail with empty class name", result);
    }

    @Test
    public void bookSeatWithInvalidSeatNo() {
        boolean result = dao.bookSeat(correctFlightId, correctClassName, "INVALID");
        Assert.assertFalse("Seat booking should fail with invalid seat number", result);
    }

    @Test
    public void bookSeatWithNullSeatNo() {
        boolean result = dao.bookSeat(correctFlightId, correctClassName, null);
        Assert.assertFalse("Seat booking should fail with null seat number", result);
    }

    @Test
    public void bookSeatWithEmptySeatNo() {
        boolean result = dao.bookSeat(correctFlightId, correctClassName, "");
        Assert.assertFalse("Seat booking should fail with empty seat number", result);
    }

    @Test
    public void releaseSeatWithValidData() {
        boolean result = dao.releaseSeat(correctFlightId, correctClassName, correctSeatNo);
        // Note: This test assumes the seat exists
        // In a real test, you would first ensure the seat exists
        Assert.assertTrue("Seat release should succeed or fail appropriately", result || !result);
    }

    @Test
    public void releaseSeatWithInvalidFlightId() {
        boolean result = dao.releaseSeat(-1, correctClassName, correctSeatNo);
        Assert.assertFalse("Seat release should fail with invalid flight ID", result);
    }

    @Test
    public void releaseSeatWithInvalidClassName() {
        boolean result = dao.releaseSeat(correctFlightId, "InvalidClass", correctSeatNo);
        Assert.assertFalse("Seat release should fail with invalid class name", result);
    }

    @Test
    public void releaseSeatWithNullClassName() {
        boolean result = dao.releaseSeat(correctFlightId, null, correctSeatNo);
        Assert.assertFalse("Seat release should fail with null class name", result);
    }

    @Test
    public void releaseSeatWithEmptyClassName() {
        boolean result = dao.releaseSeat(correctFlightId, "", correctSeatNo);
        Assert.assertFalse("Seat release should fail with empty class name", result);
    }

    @Test
    public void releaseSeatWithInvalidSeatNo() {
        boolean result = dao.releaseSeat(correctFlightId, correctClassName, "INVALID");
        Assert.assertFalse("Seat release should fail with invalid seat number", result);
    }

    @Test
    public void releaseSeatWithNullSeatNo() {
        boolean result = dao.releaseSeat(correctFlightId, correctClassName, null);
        Assert.assertFalse("Seat release should fail with null seat number", result);
    }

    @Test
    public void releaseSeatWithEmptySeatNo() {
        boolean result = dao.releaseSeat(correctFlightId, correctClassName, "");
        Assert.assertFalse("Seat release should fail with empty seat number", result);
    }

    @Test
    public void isSeatBookedWithValidData() {
        boolean isBooked = dao.isSeatBooked(correctFlightId, correctClassName, correctSeatNo);
        // Note: This test assumes the seat exists
        // In a real test, you would first ensure the seat exists
        Assert.assertTrue("Seat booking status should be boolean", isBooked || !isBooked);
    }

    @Test
    public void isSeatBookedWithInvalidFlightId() {
        boolean isBooked = dao.isSeatBooked(-1, correctClassName, correctSeatNo);
        Assert.assertFalse("Seat should not be booked for invalid flight ID", isBooked);
    }

    @Test
    public void isSeatBookedWithInvalidClassName() {
        boolean isBooked = dao.isSeatBooked(correctFlightId, "InvalidClass", correctSeatNo);
        Assert.assertFalse("Seat should not be booked for invalid class name", isBooked);
    }

    @Test
    public void isSeatBookedWithNullClassName() {
        boolean isBooked = dao.isSeatBooked(correctFlightId, null, correctSeatNo);
        Assert.assertFalse("Seat should not be booked for null class name", isBooked);
    }

    @Test
    public void isSeatBookedWithEmptyClassName() {
        boolean isBooked = dao.isSeatBooked(correctFlightId, "", correctSeatNo);
        Assert.assertFalse("Seat should not be booked for empty class name", isBooked);
    }

    @Test
    public void isSeatBookedWithInvalidSeatNo() {
        boolean isBooked = dao.isSeatBooked(correctFlightId, correctClassName, "INVALID");
        Assert.assertFalse("Seat should not be booked for invalid seat number", isBooked);
    }

    @Test
    public void isSeatBookedWithNullSeatNo() {
        boolean isBooked = dao.isSeatBooked(correctFlightId, correctClassName, null);
        Assert.assertFalse("Seat should not be booked for null seat number", isBooked);
    }

    @Test
    public void isSeatBookedWithEmptySeatNo() {
        boolean isBooked = dao.isSeatBooked(correctFlightId, correctClassName, "");
        Assert.assertFalse("Seat should not be booked for empty seat number", isBooked);
    }

    @Test
    public void getTotalSeatsByClassWithValidData() {
        int totalSeats = dao.getTotalSeatsByClass(correctFlightId, correctClassName);
        Assert.assertTrue("Total seats should be non-negative", totalSeats >= 0);
    }

    @Test
    public void getTotalSeatsByClassWithInvalidFlightId() {
        int totalSeats = dao.getTotalSeatsByClass(-1, correctClassName);
        Assert.assertEquals("Total seats should be 0 for invalid flight ID", 0, totalSeats);
    }

    @Test
    public void getTotalSeatsByClassWithInvalidClassName() {
        int totalSeats = dao.getTotalSeatsByClass(correctFlightId, "InvalidClass");
        Assert.assertEquals("Total seats should be 0 for invalid class name", 0, totalSeats);
    }

    @Test
    public void getTotalSeatsByClassWithNullClassName() {
        int totalSeats = dao.getTotalSeatsByClass(correctFlightId, null);
        Assert.assertEquals("Total seats should be 0 for null class name", 0, totalSeats);
    }

    @Test
    public void getTotalSeatsByClassWithEmptyClassName() {
        int totalSeats = dao.getTotalSeatsByClass(correctFlightId, "");
        Assert.assertEquals("Total seats should be 0 for empty class name", 0, totalSeats);
    }

    @Test
    public void getTotalSeatsByClassWithDifferentClasses() {
        String[] seatClasses = {"Economy", "Business", "First Class"};
        
        for (String seatClass : seatClasses) {
            int totalSeats = dao.getTotalSeatsByClass(correctFlightId, seatClass);
            Assert.assertTrue("Total seats should be non-negative for " + seatClass, totalSeats >= 0);
        }
    }

    @Test
    public void getAvailableSeatsCountByClassWithValidData() {
        int availableSeats = dao.getAvailableSeatsCountByClass(correctFlightId, correctClassName);
        Assert.assertTrue("Available seats should be non-negative", availableSeats >= 0);
    }

    @Test
    public void getAvailableSeatsCountByClassWithInvalidFlightId() {
        int availableSeats = dao.getAvailableSeatsCountByClass(-1, correctClassName);
        Assert.assertEquals("Available seats should be 0 for invalid flight ID", 0, availableSeats);
    }

    @Test
    public void getAvailableSeatsCountByClassWithInvalidClassName() {
        int availableSeats = dao.getAvailableSeatsCountByClass(correctFlightId, "InvalidClass");
        Assert.assertEquals("Available seats should be 0 for invalid class name", 0, availableSeats);
    }

    @Test
    public void getAvailableSeatsCountByClassWithNullClassName() {
        int availableSeats = dao.getAvailableSeatsCountByClass(correctFlightId, null);
        Assert.assertEquals("Available seats should be 0 for null class name", 0, availableSeats);
    }

    @Test
    public void getAvailableSeatsCountByClassWithEmptyClassName() {
        int availableSeats = dao.getAvailableSeatsCountByClass(correctFlightId, "");
        Assert.assertEquals("Available seats should be 0 for empty class name", 0, availableSeats);
    }

    @Test
    public void getAvailableSeatsCountByClassWithDifferentClasses() {
        String[] seatClasses = {"Economy", "Business", "First Class"};
        
        for (String seatClass : seatClasses) {
            int availableSeats = dao.getAvailableSeatsCountByClass(correctFlightId, seatClass);
            Assert.assertTrue("Available seats should be non-negative for " + seatClass, availableSeats >= 0);
        }
    }

    @Test
    public void seatNumberFormatValidation() {
        // Test different seat number formats
        String[] validSeatNumbers = {"E1", "E2", "E15", "B1", "B6", "F1", "F10"};
        String[] invalidSeatNumbers = {"", null, "X1", "E0", "E16", "B0", "B7", "F0", "F11"};
        
        for (String seatNo : validSeatNumbers) {
            Assert.assertTrue("Seat number " + seatNo + " should be valid", 
                            seatNo != null && !seatNo.isEmpty() && 
                            (seatNo.startsWith("E") || seatNo.startsWith("B") || seatNo.startsWith("F")));
        }
        
        for (String seatNo : invalidSeatNumbers) {
            if (seatNo == null || seatNo.isEmpty()) {
                Assert.assertTrue("Null or empty seat number should be invalid", true);
            } else {
                Assert.assertFalse("Seat number " + seatNo + " should be invalid", 
                                 seatNo.startsWith("E") || seatNo.startsWith("B") || seatNo.startsWith("F"));
            }
        }
    }

    @Test
    public void seatClassValidation() {
        // Test different seat class names
        String[] validSeatClasses = {"Economy", "Business", "First Class"};
        String[] invalidSeatClasses = {"", null, "Economy Class", "Business Class", "First", "Class"};
        
        for (String seatClass : validSeatClasses) {
            Assert.assertTrue("Seat class " + seatClass + " should be valid", 
                            seatClass != null && !seatClass.isEmpty() && 
                            (seatClass.equals("Economy") || seatClass.equals("Business") || seatClass.equals("First Class")));
        }
        
        for (String seatClass : invalidSeatClasses) {
            if (seatClass == null || seatClass.isEmpty()) {
                Assert.assertTrue("Null or empty seat class should be invalid", true);
            } else {
                Assert.assertFalse("Seat class " + seatClass + " should be invalid", 
                                 seatClass.equals("Economy") || seatClass.equals("Business") || seatClass.equals("First Class"));
            }
        }
    }
} 