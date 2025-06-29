/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package wingsnepal.model;

import com.stripe.Stripe;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import com.stripe.exception.StripeException;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.FlowLayout;
import javax.swing.JPanel;
import javax.swing.JButton;
import database.DbConnection;
import database.MySqlConnection;

/**
 *
 * @author Aayush Kharel
 */
public class StripePayment {
    private static final String STRIPE_API_KEY = "sk_test_51RbWr6Pra3RknifrmZfMOoPl1eSY2u8jcFbgcSutWrilcIL09y3vesrpme1oIJGQeAtS6VDzlaBzk81VqhGYjkQs00KXAUeIVD";

    public StripePayment() {
        // Set Stripe API key
        Stripe.apiKey = STRIPE_API_KEY;
    }

    public String createCheckoutSession() {
        try {
            // Create the checkout session parameters
            SessionCreateParams params = SessionCreateParams.builder()
                .addLineItem(
                    SessionCreateParams.LineItem.builder()
                        .setPriceData(
                            SessionCreateParams.LineItem.PriceData.builder()
                                .setCurrency("usd")
                                .setUnitAmount(2000L)  // $20.00 in cents
                                .setProductData(
                                    SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                        .setName("Sample Product")  // Product name
                                        .build()
                                )
                                .build()
                        )
                        .setQuantity(1L)  // Quantity of the product
                        .build()
                )
                .setMode(SessionCreateParams.Mode.PAYMENT)  // Payment mode (one-time payment)
                .setSuccessUrl("https://example.com/success?session_id={CHECKOUT_SESSION_ID}")  // Redirect URL after successful payment
                .setCancelUrl("https://example.com/cancel")    // Redirect URL if payment is canceled
                .build();

            // Create the session
            Session session = Session.create(params);

            // Return the session URL (this is the URL you should redirect the user to)
            return session.getUrl();
        } catch (StripeException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean checkPaymentStatus(String sessionUrl) throws InterruptedException {
    try {
        // Extract session ID from the URL
        String sessionId = extractSessionIdFromUrl(sessionUrl);

        if (sessionId == null || sessionId.isEmpty()) {
            System.out.println("Invalid session ID extracted from URL: " + sessionUrl);
            return false;
        }
        
        System.out.println("Extracted session ID: " + sessionId);
        System.out.println("Waiting 30 seconds before checking payment status...");
        Thread.sleep(30000); // Reduced wait time to 30 seconds
        
        // Retrieve the session details from Stripe using session ID
        Session session = Session.retrieve(sessionId);
        System.out.println("Session ID: " + session.getId());
        System.out.println("Payment Status: " + session.getPaymentStatus());
        System.out.println("Session Status: " + session.getStatus());
        
        // Check if payment status is 'paid'
        boolean isPaid = "paid".equals(session.getPaymentStatus());
        System.out.println("Payment successful: " + isPaid);
        return isPaid;
    } catch (StripeException e) {
        System.err.println("Stripe API error: " + e.getMessage());
        e.printStackTrace();
        return false;
    } catch (Exception e) {
        System.err.println("Unexpected error: " + e.getMessage());
        e.printStackTrace();
        return false;
    }
}

// Helper method to extract the session ID from the full URL
private String extractSessionIdFromUrl(String sessionUrl) {
    try {
        if (sessionUrl == null || sessionUrl.trim().isEmpty()) {
            System.out.println("Session URL is null or empty");
            return null;
        }
        
        System.out.println("Extracting session ID from URL: " + sessionUrl);
        
        // Try different patterns to extract session ID
        String sessionId = null;
        
        // Pattern 1: /pay/cs_test_...
        if (sessionUrl.contains("/pay/")) {
            String[] parts = sessionUrl.split("/pay/");
            if (parts.length >= 2) {
                sessionId = parts[1];
            }
        }
        // Pattern 2: cs_test_... (direct session ID)
        else if (sessionUrl.startsWith("cs_test_")) {
            sessionId = sessionUrl;
        }
        // Pattern 3: URL with session_id parameter
        else if (sessionUrl.contains("session_id=")) {
            String[] parts = sessionUrl.split("session_id=");
            if (parts.length >= 2) {
                sessionId = parts[1];
            }
        }
        
        if (sessionId != null) {
            // Remove anything after '#' or '?' or whitespace
            int hashIndex = sessionId.indexOf('#');
            if (hashIndex != -1) {
                sessionId = sessionId.substring(0, hashIndex);
            }
            
            int questionIndex = sessionId.indexOf('?');
            if (questionIndex != -1) {
                sessionId = sessionId.substring(0, questionIndex);
            }
            
            sessionId = sessionId.trim();
            
            // Validate that it looks like a Stripe session ID
            if (sessionId.startsWith("cs_test_") || sessionId.startsWith("cs_live_")) {
                System.out.println("Successfully extracted session ID: " + sessionId);
                return sessionId;
            } else {
                System.out.println("Extracted string doesn't look like a valid Stripe session ID: " + sessionId);
                return null;
            }
        } else {
            System.out.println("Could not extract session ID from URL");
            return null;
        }
    } catch (Exception e) {
        System.err.println("Error extracting session ID: " + e.getMessage());
        e.printStackTrace();
        return null;
    }
}

    public String createCheckoutSession(int amount, String name, String email, String flightCode, String seatClass, String seatNo) {
        try {
            Stripe.apiKey = STRIPE_API_KEY;

            SessionCreateParams params = SessionCreateParams.builder()
                .addLineItem(
                    SessionCreateParams.LineItem.builder()
                        .setPriceData(
                            SessionCreateParams.LineItem.PriceData.builder()
                                .setCurrency("npr")  // Use NPR currency
                                .setUnitAmount((long) amount * 100) // amount in paisa (NPR cents)
                                .setProductData(
                                    SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                        .setName("Flight " + flightCode + " (" + seatClass + " " + seatNo + ")")
                                        .setDescription("Flight booking for " + name + " (" + email + ")")
                                        .build()
                                )
                                .build()
                        )
                        .setQuantity(1L)
                        .build()
                )
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl("https://checkout.stripe.com/success") // Simple success URL
                .setCancelUrl("https://checkout.stripe.com/cancel")   // Simple cancel URL
                .setCustomerEmail(email)  // Pre-fill customer email
                .build();

            Session session = Session.create(params);
            return session.getUrl();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<BookingFlightModel> getAllBookingFlightsForEdit() {
        List<BookingFlightModel> bookings = new ArrayList<>();
        String sql = "SELECT b.booking_id, f.flight_code, b.seat_no, b.class_name, b.date, b.payment_method " +
                     "FROM bookings b JOIN flights f ON b.flight_id = f.flight_id ORDER BY b.booking_id ASC";
        DbConnection db = new MySqlConnection();
        try (Connection conn = db.openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                BookingFlightModel booking = new BookingFlightModel();
                booking.setBookingId(rs.getInt("booking_id"));
                booking.setFlightCode(rs.getString("flight_code"));
                booking.setSeatNo(rs.getString("seat_no"));
                booking.setClassName(rs.getString("class_name"));
                booking.setTravelDate(rs.getDate("date"));
                booking.setPaymentMethod(rs.getString("payment_method"));
                bookings.add(booking);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookings;
    }

}
