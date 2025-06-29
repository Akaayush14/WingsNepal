package wingsnepal.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.util.List;
import wingsnepal.model.UserDataModel;
import wingsnepal.dao.SearchFlightDao;
import wingsnepal.dao.BookingFlightDao;
import wingsnepal.dao.SeatClassDao;
import wingsnepal.dao.BookingPassengerDao;
import wingsnepal.model.BookingFlightModel;
import wingsnepal.model.SearchFlightModel;
import wingsnepal.model.StripePayment;
import controller.TicketGenerator;

public class WalkInBookingDialog extends JDialog {
    private UserDataModel employee;
    private SearchFlightDao searchFlightDao;
    private BookingFlightDao bookingFlightDao;
    private SeatClassDao seatClassDao;
    
    // Form components
    private JTextField customerNameField;
    private JTextField customerEmailField;
    private JTextField customerPhoneField;
    private JComboBox<String> flightComboBox;
    private JComboBox<String> seatClassComboBox;
    private JComboBox<String> seatNoComboBox;
    private JComboBox<String> paymentMethodComboBox;
    private JTextField priceField;
    private JButton bookButton;
    private JButton cancelButton;
    
    public WalkInBookingDialog(Frame parent, UserDataModel employee) {
        super(parent, "Walk-In Customer Booking", true);
        this.employee = employee;
        this.searchFlightDao = new SearchFlightDao();
        this.bookingFlightDao = new BookingFlightDao();
        this.seatClassDao = new SeatClassDao();
        
        initComponents();
        setupEventListeners();
        loadFlights();
        
        setSize(500, 400);
        setLocationRelativeTo(parent);
    }
    
    private void initComponents() {
        setLayout(new BorderLayout());
        
        // Title panel
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(45, 62, 80));
        JLabel titleLabel = new JLabel("Walk-In Customer Booking");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titleLabel.setForeground(Color.WHITE);
        titlePanel.add(titleLabel);
        add(titlePanel, BorderLayout.NORTH);
        
        // Main form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Customer Name
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Customer Name:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        customerNameField = new JTextField(20);
        formPanel.add(customerNameField, gbc);
        
        // Customer Email
        gbc.gridx = 0; gbc.gridy = 1; gbc.fill = GridBagConstraints.NONE;
        formPanel.add(new JLabel("Customer Email:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        customerEmailField = new JTextField(20);
        formPanel.add(customerEmailField, gbc);
        
        // Customer Phone
        gbc.gridx = 0; gbc.gridy = 2; gbc.fill = GridBagConstraints.NONE;
        formPanel.add(new JLabel("Customer Phone:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        customerPhoneField = new JTextField(20);
        formPanel.add(customerPhoneField, gbc);
        
        // Flight Selection
        gbc.gridx = 0; gbc.gridy = 3; gbc.fill = GridBagConstraints.NONE;
        formPanel.add(new JLabel("Flight:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        flightComboBox = new JComboBox<>();
        formPanel.add(flightComboBox, gbc);
        
        // Seat Class
        gbc.gridx = 0; gbc.gridy = 4; gbc.fill = GridBagConstraints.NONE;
        formPanel.add(new JLabel("Seat Class:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        seatClassComboBox = new JComboBox<>(new String[]{"Economy", "Business", "First Class"});
        formPanel.add(seatClassComboBox, gbc);
        
        // Seat Number
        gbc.gridx = 0; gbc.gridy = 5; gbc.fill = GridBagConstraints.NONE;
        formPanel.add(new JLabel("Seat Number:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        seatNoComboBox = new JComboBox<>();
        formPanel.add(seatNoComboBox, gbc);
        
        // Price
        gbc.gridx = 0; gbc.gridy = 6; gbc.fill = GridBagConstraints.NONE;
        formPanel.add(new JLabel("Price:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        priceField = new JTextField(20);
        priceField.setEditable(false);
        formPanel.add(priceField, gbc);
        
        // Payment Method
        gbc.gridx = 0; gbc.gridy = 7; gbc.fill = GridBagConstraints.NONE;
        formPanel.add(new JLabel("Payment Method:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        paymentMethodComboBox = new JComboBox<>(new String[]{"Cash", "Stripe"});
        formPanel.add(paymentMethodComboBox, gbc);
        
        add(formPanel, BorderLayout.CENTER);
        
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        bookButton = new JButton("Book Flight");
        bookButton.setBackground(new Color(0, 153, 112));
        bookButton.setForeground(Color.WHITE);
        bookButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        
        cancelButton = new JButton("Cancel");
        cancelButton.setBackground(new Color(220, 53, 69));
        cancelButton.setForeground(Color.WHITE);
        cancelButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        
        bookButton.setPreferredSize(new Dimension(150, 40));
        bookButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        cancelButton.setPreferredSize(new Dimension(100, 40));
        cancelButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        
        buttonPanel.add(bookButton);
        buttonPanel.add(cancelButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    private void setupEventListeners() {
        flightComboBox.addActionListener(e -> updateSeatClassAndPrice());
        seatClassComboBox.addActionListener(e -> updateAvailableSeatsAndPrice());
        
        bookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleBooking();
            }
        });
        
        cancelButton.addActionListener(e -> dispose());
    }
    
    private void loadFlights() {
        List<SearchFlightModel> flights = searchFlightDao.getAllFlights();
        flightComboBox.removeAllItems();
        for (SearchFlightModel flight : flights) {
            flightComboBox.addItem(flight.getFlightCode() + " - " + flight.getFlightName() + 
                                  " (" + flight.getFromCity() + " to " + flight.getToCity() + ")");
        }
    }
    
    private void updateSeatClassAndPrice() {
        String selectedFlight = (String) flightComboBox.getSelectedItem();
        if (selectedFlight != null) {
            updateAvailableSeatsAndPrice();
        }
    }
    
    private void updateAvailableSeatsAndPrice() {
        String selectedFlight = (String) flightComboBox.getSelectedItem();
        String seatClass = (String) seatClassComboBox.getSelectedItem();
        
        if (selectedFlight != null && seatClass != null) {
            String flightCode = selectedFlight.split(" - ")[0];
            int flightId = seatClassDao.getFlightIdByCode(flightCode);
            
            // Update price
            int price = seatClassDao.getPriceByFlightAndClass(flightId, seatClass);
            priceField.setText("NPR " + price);
            
            // Update available seats
            List<String> availableSeats = seatClassDao.getAvailableSeatsByClass(flightId, seatClass);
            seatNoComboBox.removeAllItems();
            for (String seat : availableSeats) {
                seatNoComboBox.addItem(seat);
            }
        }
    }
    
    private void handleBooking() {
        // Validate form
        if (!validateForm()) {
            return;
        }
        
        String customerName = customerNameField.getText().trim();
        String customerEmail = customerEmailField.getText().trim();
        String customerPhone = customerPhoneField.getText().trim();
        String selectedFlight = (String) flightComboBox.getSelectedItem();
        String flightCode = selectedFlight.split(" - ")[0];
        String seatClass = (String) seatClassComboBox.getSelectedItem();
        String seatNo = (String) seatNoComboBox.getSelectedItem();
        String paymentMethod = (String) paymentMethodComboBox.getSelectedItem();
        
        int flightId = seatClassDao.getFlightIdByCode(flightCode);
        int seatId = 1; // Default seat ID
        Date travelDate = new Date(System.currentTimeMillis()); // Today's date for walk-in
        
        // Process payment and booking
        if ("Stripe".equals(paymentMethod)) {
            handleStripePayment(customerName, customerEmail, customerPhone, flightCode, flightId, seatId, seatClass, seatNo, travelDate);
        } else {
            // Cash payment - direct booking
            processBooking(customerName, customerEmail, customerPhone, flightCode, flightId, seatId, seatClass, seatNo, travelDate, "Cash");
        }
    }
    
    private void handleStripePayment(String customerName, String customerEmail, String customerPhone, 
                                   String flightCode, int flightId, int seatId, String seatClass, 
                                   String seatNo, Date travelDate) {
        try {
            int price = Integer.parseInt(priceField.getText().replaceAll("[^0-9]", ""));
            StripePayment stripe = new StripePayment();
            String sessionUrl = stripe.createCheckoutSession(price, customerName, customerEmail, flightCode, seatClass, seatNo);
            
            if (sessionUrl != null) {
                if (Desktop.isDesktopSupported()) {
                    Desktop.getDesktop().browse(new java.net.URI(sessionUrl));
                }
                
                JOptionPane.showMessageDialog(this, "Customer payment page opened in browser.\nWait for payment completion...");
                
                if (stripe.checkPaymentStatus(sessionUrl)) {
                    processBooking(customerName, customerEmail, customerPhone, flightCode, flightId, seatId, seatClass, seatNo, travelDate, "Stripe");
                } else {
                    JOptionPane.showMessageDialog(this, "Payment failed or not completed.", "Payment Failed", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Failed to create payment session.", "Payment Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Payment error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void processBooking(String customerName, String customerEmail, String customerPhone, 
                              String flightCode, int flightId, int seatId, String seatClass, 
                              String seatNo, Date travelDate, String paymentMethod) {
        
        // Debug: Check employee user ID
        System.out.println("🔍 DEBUG: Employee user ID = " + employee.getUserId());
        System.out.println("🔍 DEBUG: Employee name = " + employee.getFullName());
        System.out.println("🔍 DEBUG: Employee role = " + employee.getRole());
        
        // Create booking with employee's user ID (since this is a walk-in booking handled by employee)
        BookingFlightModel booking = new BookingFlightModel(employee.getUserId(), flightId, seatId, customerName, 
                                                customerEmail, seatClass, seatNo, 1, travelDate, paymentMethod, flightCode);
        booking.setBooked(true);
        
        // Debug: Check what was set in the booking
        System.out.println("🔍 DEBUG: Booking user ID = " + booking.getUserId());
        
        if (bookingFlightDao.saveBooking(booking)) {
            seatClassDao.markSeatAsBooked(flightId, seatClass, seatNo);
            BookingPassengerDao bookingPassengerDao = new BookingPassengerDao();
            bookingPassengerDao.syncBookingToPassengerWithPhone(booking, customerPhone);
            String flightName = searchFlightDao.getFlightNameById(flightId);
            
            // Generate ticket
            try {
                controller.TicketGenerator ticketGenerator = new controller.TicketGenerator();
                ticketGenerator.generateTicket(employee.getUserId(), flightCode, flightName, seatClass, seatNo, 
                                             customerName, customerEmail, travelDate, paymentMethod);
                
                JOptionPane.showMessageDialog(this, 
                    "Booking successful!\nTicket generation failed.", 
                    "Success", 
                    JOptionPane.WARNING_MESSAGE);
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, 
                    "Booking successful!\nTicket generation failed.", 
                    "Success", 
                    JOptionPane.WARNING_MESSAGE);
            }
            
            dispose(); // Close dialog after successful booking
        } else {
            JOptionPane.showMessageDialog(this, "Booking failed. Please try again.", "Booking Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private boolean validateForm() {
        if (customerNameField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter customer name.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (customerEmailField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter customer email.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (customerPhoneField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter customer phone.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (flightComboBox.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Please select a flight.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (seatNoComboBox.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "No available seats.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        // Validate email format
        String email = customerEmailField.getText().trim();
        if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            JOptionPane.showMessageDialog(this, "Please enter a valid email address.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        return true;
    }
} 