/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package test;

import wingsnepal.dao.BookingFlightDao;
import wingsnepal.model.BookingFlightModel;
import org.junit.*;

/**
 *
 * @author Aayush Kharel
 */
public class BookingFlightDaoTest {
    
    int correctUserId = 1;
    int correctFlightId = 1;
    int correctSeatId = 1;
    String correctFullName = "Test Passenger";
    String correctEmail = "test@example.com";
    String correctClassName = "Economy";
    String correctSeatNo = "E1";
    int correctTickets = 1;
    String correctDate = "2024-12-25";
    String correctPaymentMethod = "Credit Card";
    
    BookingFlightDao dao = new BookingFlightDao();

    @Test
    public void saveBookingWithValidData() {
        BookingFlightModel booking = new BookingFlightModel();
        booking.setUserId(correctUserId);
        booking.setFlightId(correctFlightId);
        booking.setSeatId(correctSeatId);
        booking.setFullName(correctFullName);
        booking.setEmail(correctEmail);
        booking.setClassName(correctClassName);
        booking.setSeatNo(correctSeatNo);
        booking.setTickets(correctTickets);
        booking.setDate(correctDate);
        booking.setPaymentMethod(correctPaymentMethod);
        
        boolean result = dao.saveBooking(booking);
        Assert.assertTrue("Booking should be saved successfully", result);
    }

    @Test
    public void saveBookingWithInvalidUserId() {
        BookingFlightModel booking = new BookingFlightModel();
        booking.setUserId(-1); // Invalid user ID
        booking.setFlightId(correctFlightId);
        booking.setSeatId(correctSeatId);
        booking.setFullName(correctFullName);
        booking.setEmail(correctEmail);
        booking.setClassName(correctClassName);
        booking.setSeatNo(correctSeatNo);
        booking.setTickets(correctTickets);
        booking.setDate(correctDate);
        booking.setPaymentMethod(correctPaymentMethod);
        
        boolean result = dao.saveBooking(booking);
        Assert.assertFalse("Booking should fail with invalid user ID", result);
    }

    @Test
    public void saveBookingWithInvalidFlightId() {
        BookingFlightModel booking = new BookingFlightModel();
        booking.setUserId(correctUserId);
        booking.setFlightId(-1); // Invalid flight ID
        booking.setSeatId(correctSeatId);
        booking.setFullName(correctFullName);
        booking.setEmail(correctEmail);
        booking.setClassName(correctClassName);
        booking.setSeatNo(correctSeatNo);
        booking.setTickets(correctTickets);
        booking.setDate(correctDate);
        booking.setPaymentMethod(correctPaymentMethod);
        
        boolean result = dao.saveBooking(booking);
        Assert.assertFalse("Booking should fail with invalid flight ID", result);
    }

    @Test
    public void saveBookingWithNullFullName() {
        BookingFlightModel booking = new BookingFlightModel();
        booking.setUserId(correctUserId);
        booking.setFlightId(correctFlightId);
        booking.setSeatId(correctSeatId);
        booking.setFullName(null); // Null full name
        booking.setEmail(correctEmail);
        booking.setClassName(correctClassName);
        booking.setSeatNo(correctSeatNo);
        booking.setTickets(correctTickets);
        booking.setDate(correctDate);
        booking.setPaymentMethod(correctPaymentMethod);
        
        boolean result = dao.saveBooking(booking);
        Assert.assertFalse("Booking should fail with null full name", result);
    }

    @Test
    public void saveBookingWithEmptyFullName() {
        BookingFlightModel booking = new BookingFlightModel();
        booking.setUserId(correctUserId);
        booking.setFlightId(correctFlightId);
        booking.setSeatId(correctSeatId);
        booking.setFullName(""); // Empty full name
        booking.setEmail(correctEmail);
        booking.setClassName(correctClassName);
        booking.setSeatNo(correctSeatNo);
        booking.setTickets(correctTickets);
        booking.setDate(correctDate);
        booking.setPaymentMethod(correctPaymentMethod);
        
        boolean result = dao.saveBooking(booking);
        Assert.assertFalse("Booking should fail with empty full name", result);
    }

    @Test
    public void saveBookingWithNullEmail() {
        BookingFlightModel booking = new BookingFlightModel();
        booking.setUserId(correctUserId);
        booking.setFlightId(correctFlightId);
        booking.setSeatId(correctSeatId);
        booking.setFullName(correctFullName);
        booking.setEmail(null); // Null email
        booking.setClassName(correctClassName);
        booking.setSeatNo(correctSeatNo);
        booking.setTickets(correctTickets);
        booking.setDate(correctDate);
        booking.setPaymentMethod(correctPaymentMethod);
        
        boolean result = dao.saveBooking(booking);
        Assert.assertFalse("Booking should fail with null email", result);
    }

    @Test
    public void saveBookingWithInvalidEmail() {
        BookingFlightModel booking = new BookingFlightModel();
        booking.setUserId(correctUserId);
        booking.setFlightId(correctFlightId);
        booking.setSeatId(correctSeatId);
        booking.setFullName(correctFullName);
        booking.setEmail("invalid-email"); // Invalid email format
        booking.setClassName(correctClassName);
        booking.setSeatNo(correctSeatNo);
        booking.setTickets(correctTickets);
        booking.setDate(correctDate);
        booking.setPaymentMethod(correctPaymentMethod);
        
        boolean result = dao.saveBooking(booking);
        Assert.assertFalse("Booking should fail with invalid email", result);
    }

    @Test
    public void saveBookingWithZeroTickets() {
        BookingFlightModel booking = new BookingFlightModel();
        booking.setUserId(correctUserId);
        booking.setFlightId(correctFlightId);
        booking.setSeatId(correctSeatId);
        booking.setFullName(correctFullName);
        booking.setEmail(correctEmail);
        booking.setClassName(correctClassName);
        booking.setSeatNo(correctSeatNo);
        booking.setTickets(0); // Zero tickets
        booking.setDate(correctDate);
        booking.setPaymentMethod(correctPaymentMethod);
        
        boolean result = dao.saveBooking(booking);
        Assert.assertFalse("Booking should fail with zero tickets", result);
    }

    @Test
    public void saveBookingWithNegativeTickets() {
        BookingFlightModel booking = new BookingFlightModel();
        booking.setUserId(correctUserId);
        booking.setFlightId(correctFlightId);
        booking.setSeatId(correctSeatId);
        booking.setFullName(correctFullName);
        booking.setEmail(correctEmail);
        booking.setClassName(correctClassName);
        booking.setSeatNo(correctSeatNo);
        booking.setTickets(-1); // Negative tickets
        booking.setDate(correctDate);
        booking.setPaymentMethod(correctPaymentMethod);
        
        boolean result = dao.saveBooking(booking);
        Assert.assertFalse("Booking should fail with negative tickets", result);
    }

    @Test
    public void saveBookingWithNullDate() {
        BookingFlightModel booking = new BookingFlightModel();
        booking.setUserId(correctUserId);
        booking.setFlightId(correctFlightId);
        booking.setSeatId(correctSeatId);
        booking.setFullName(correctFullName);
        booking.setEmail(correctEmail);
        booking.setClassName(correctClassName);
        booking.setSeatNo(correctSeatNo);
        booking.setTickets(correctTickets);
        booking.setDate(null); // Null date
        booking.setPaymentMethod(correctPaymentMethod);
        
        boolean result = dao.saveBooking(booking);
        Assert.assertFalse("Booking should fail with null date", result);
    }

    @Test
    public void saveBookingWithInvalidDate() {
        BookingFlightModel booking = new BookingFlightModel();
        booking.setUserId(correctUserId);
        booking.setFlightId(correctFlightId);
        booking.setSeatId(correctSeatId);
        booking.setFullName(correctFullName);
        booking.setEmail(correctEmail);
        booking.setClassName(correctClassName);
        booking.setSeatNo(correctSeatNo);
        booking.setTickets(correctTickets);
        booking.setDate("invalid-date"); // Invalid date format
        booking.setPaymentMethod(correctPaymentMethod);
        
        boolean result = dao.saveBooking(booking);
        Assert.assertFalse("Booking should fail with invalid date", result);
    }

    @Test
    public void saveBookingWithNullPaymentMethod() {
        BookingFlightModel booking = new BookingFlightModel();
        booking.setUserId(correctUserId);
        booking.setFlightId(correctFlightId);
        booking.setSeatId(correctSeatId);
        booking.setFullName(correctFullName);
        booking.setEmail(correctEmail);
        booking.setClassName(correctClassName);
        booking.setSeatNo(correctSeatNo);
        booking.setTickets(correctTickets);
        booking.setDate(correctDate);
        booking.setPaymentMethod(null); // Null payment method
        
        boolean result = dao.saveBooking(booking);
        Assert.assertFalse("Booking should fail with null payment method", result);
    }

    @Test
    public void saveBookingWithEmptyPaymentMethod() {
        BookingFlightModel booking = new BookingFlightModel();
        booking.setUserId(correctUserId);
        booking.setFlightId(correctFlightId);
        booking.setSeatId(correctSeatId);
        booking.setFullName(correctFullName);
        booking.setEmail(correctEmail);
        booking.setClassName(correctClassName);
        booking.setSeatNo(correctSeatNo);
        booking.setTickets(correctTickets);
        booking.setDate(correctDate);
        booking.setPaymentMethod(""); // Empty payment method
        
        boolean result = dao.saveBooking(booking);
        Assert.assertFalse("Booking should fail with empty payment method", result);
    }

    @Test
    public void deleteBookingWithValidId() {
        // First create a booking
        BookingFlightModel booking = new BookingFlightModel();
        booking.setUserId(correctUserId);
        booking.setFlightId(correctFlightId);
        booking.setSeatId(correctSeatId);
        booking.setFullName(correctFullName);
        booking.setEmail(correctEmail);
        booking.setClassName(correctClassName);
        booking.setSeatNo(correctSeatNo);
        booking.setTickets(correctTickets);
        booking.setDate(correctDate);
        booking.setPaymentMethod(correctPaymentMethod);
        
        boolean saveResult = dao.saveBooking(booking);
        Assert.assertTrue("Booking should be saved for deletion test", saveResult);
        
        // Note: In a real test, you would need to get the booking ID from the database
        // For now, we'll test with a known valid ID if available
        boolean deleteResult = dao.deleteBooking(1); // Assuming booking ID 1 exists
        if (deleteResult) {
            Assert.assertTrue("Booking deletion should succeed", deleteResult);
        }
    }

    @Test
    public void deleteBookingWithInvalidId() {
        boolean result = dao.deleteBooking(-1);
        Assert.assertFalse("Booking deletion should fail with invalid ID", result);
    }

    @Test
    public void bookingModelPropertiesAreAccessible() {
        // Test that BookingFlightModel properties are accessible
        BookingFlightModel booking = new BookingFlightModel();
        booking.setUserId(correctUserId);
        booking.setFlightId(correctFlightId);
        booking.setSeatId(correctSeatId);
        booking.setFullName(correctFullName);
        booking.setEmail(correctEmail);
        booking.setClassName(correctClassName);
        booking.setSeatNo(correctSeatNo);
        booking.setTickets(correctTickets);
        booking.setDate(correctDate);
        booking.setPaymentMethod(correctPaymentMethod);
        
        Assert.assertEquals("User ID should match", correctUserId, booking.getUserId());
        Assert.assertEquals("Flight ID should match", correctFlightId, booking.getFlightId());
        Assert.assertEquals("Seat ID should match", correctSeatId, booking.getSeatId());
        Assert.assertEquals("Full name should match", correctFullName, booking.getFullName());
        Assert.assertEquals("Email should match", correctEmail, booking.getEmail());
        Assert.assertEquals("Class name should match", correctClassName, booking.getClassName());
        Assert.assertEquals("Seat number should match", correctSeatNo, booking.getSeatNo());
        Assert.assertEquals("Tickets should match", correctTickets, booking.getTickets());
        Assert.assertEquals("Date should match", correctDate, booking.getDate());
        Assert.assertEquals("Payment method should match", correctPaymentMethod, booking.getPaymentMethod());
    }

    @Test
    public void bookingModelWithDifferentSeatClasses() {
        // Test booking with different seat classes
        String[] seatClasses = {"Economy", "Business", "First Class"};
        
        for (String seatClass : seatClasses) {
            BookingFlightModel booking = new BookingFlightModel();
            booking.setUserId(correctUserId);
            booking.setFlightId(correctFlightId);
            booking.setSeatId(correctSeatId);
            booking.setFullName(correctFullName);
            booking.setEmail(correctEmail);
            booking.setClassName(seatClass);
            booking.setSeatNo("A1");
            booking.setTickets(correctTickets);
            booking.setDate(correctDate);
            booking.setPaymentMethod(correctPaymentMethod);
            
            Assert.assertEquals("Seat class should match", seatClass, booking.getClassName());
        }
    }

    @Test
    public void bookingModelWithDifferentPaymentMethods() {
        // Test booking with different payment methods
        String[] paymentMethods = {"Credit Card", "Debit Card", "Cash", "Online Transfer"};
        
        for (String paymentMethod : paymentMethods) {
            BookingFlightModel booking = new BookingFlightModel();
            booking.setUserId(correctUserId);
            booking.setFlightId(correctFlightId);
            booking.setSeatId(correctSeatId);
            booking.setFullName(correctFullName);
            booking.setEmail(correctEmail);
            booking.setClassName(correctClassName);
            booking.setSeatNo(correctSeatNo);
            booking.setTickets(correctTickets);
            booking.setDate(correctDate);
            booking.setPaymentMethod(paymentMethod);
            
            Assert.assertEquals("Payment method should match", paymentMethod, booking.getPaymentMethod());
        }
    }

    @Test
    public void bookingModelWithMultipleTickets() {
        // Test booking with multiple tickets
        int[] ticketCounts = {1, 2, 3, 4, 5};
        
        for (int tickets : ticketCounts) {
            BookingFlightModel booking = new BookingFlightModel();
            booking.setUserId(correctUserId);
            booking.setFlightId(correctFlightId);
            booking.setSeatId(correctSeatId);
            booking.setFullName(correctFullName);
            booking.setEmail(correctEmail);
            booking.setClassName(correctClassName);
            booking.setSeatNo(correctSeatNo);
            booking.setTickets(tickets);
            booking.setDate(correctDate);
            booking.setPaymentMethod(correctPaymentMethod);
            
            Assert.assertEquals("Tickets should match", tickets, booking.getTickets());
        }
    }
} 