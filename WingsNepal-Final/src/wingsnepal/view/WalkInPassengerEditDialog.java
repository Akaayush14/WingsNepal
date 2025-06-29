package wingsnepal.view;

import wingsnepal.dao.WalkInPassengerListDao;
import wingsnepal.dao.SeatClassDao;
import wingsnepal.model.WalkInPassengerListModel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Dialog for editing walk-in passenger information
 * Allows modification of passenger details and seat assignments
 * 
 * @author WingsNepal Team
 */
public class WalkInPassengerEditDialog extends JDialog {
    private int bookingId;
    private WalkInPassengerListModel currentPassenger;
    private WalkInPassengerListDao walkInPassengerDao;
    private SeatClassDao seatClassDao;
    private Runnable refreshCallback;
    
    // Form components
    private JTextField passengerNameField;
    private JTextField passengerPhoneField;
    private JTextField passengerEmailField;
    private JComboBox<String> bookingStatusComboBox;
    private JTextField flightCodeField;
    private JTextField flightNameField;
    private JTextField fromCityField;
    private JTextField toCityField;
    private JTextField travelDateField;
    private JComboBox<String> seatClassComboBox;
    private JComboBox<String> seatNumberComboBox;
    private JTextField priceField;
    private JTextField paymentMethodField;
    private JTextField bookedByField;
    private JButton saveButton;
    private JButton cancelButton;

    // UI Colors
    private static final Color PRIMARY_COLOR = new Color(0, 123, 255);
    private static final Color SUCCESS_COLOR = new Color(40, 167, 69);
    private static final Color BACKGROUND_COLOR = new Color(248, 249, 250);
    private static final Color BORDER_COLOR = new Color(220, 220, 220);

    public WalkInPassengerEditDialog(Frame parent, int bookingId, Runnable refreshCallback) {
        super(parent, "Edit Passenger", true);
        this.bookingId = bookingId;
        this.refreshCallback = refreshCallback;
        this.walkInPassengerDao = new WalkInPassengerListDao();
        this.seatClassDao = new SeatClassDao();
        
        initComponents();
        loadPassengerDetails();
        setupEventListeners();
        
        setSize(600, 600);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE);

        // Title Panel
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.setBackground(Color.WHITE);
        titlePanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 10, 0));
        
        JLabel titleLabel = new JLabel("Edit Passenger (Walk-In Customer)");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titleLabel.setForeground(new Color(52, 144, 220));
        titlePanel.add(titleLabel);
        
        add(titlePanel, BorderLayout.NORTH);

        // Create scrollable main content
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(0, 30, 20, 30));

        // Passenger Information Section
        JPanel passengerSection = createBorderedSection("Passenger Information");
        JPanel passengerGrid = new JPanel(new GridBagLayout());
        passengerGrid.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.WEST;

        // Full Name
        gbc.gridx = 0; gbc.gridy = 0;
        passengerGrid.add(createFieldLabel("Full Name:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        passengerNameField = createStyledTextField();
        passengerGrid.add(passengerNameField, gbc);

        // Phone
        gbc.gridx = 0; gbc.gridy = 1; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0.0;
        passengerGrid.add(createFieldLabel("Phone:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        passengerPhoneField = createStyledTextField();
        passengerGrid.add(passengerPhoneField, gbc);

        // Email
        gbc.gridx = 0; gbc.gridy = 2; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0.0;
        passengerGrid.add(createFieldLabel("Email:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        passengerEmailField = createStyledTextField();
        passengerGrid.add(passengerEmailField, gbc);

        // Booking Status
        gbc.gridx = 0; gbc.gridy = 3; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0.0;
        passengerGrid.add(createFieldLabel("Booking Status:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        bookingStatusComboBox = createStyledComboBox(new String[]{"Confirmed", "Cancelled"});
        passengerGrid.add(bookingStatusComboBox, gbc);

        passengerSection.add(passengerGrid, BorderLayout.CENTER);
        contentPanel.add(passengerSection);
        contentPanel.add(Box.createVerticalStrut(15));

        // Flight Information Section
        JPanel flightSection = createBorderedSection("Flight Information");
        JPanel flightGrid = new JPanel(new GridBagLayout());
        flightGrid.setBackground(Color.WHITE);

        // Flight Code
        gbc.gridx = 0; gbc.gridy = 0; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0.0;
        flightGrid.add(createFieldLabel("Flight Code:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        flightCodeField = createStyledTextField();
        flightCodeField.setEditable(false);
        flightCodeField.setBackground(new Color(245, 245, 245));
        flightGrid.add(flightCodeField, gbc);

        // Flight Name
        gbc.gridx = 0; gbc.gridy = 1; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0.0;
        flightGrid.add(createFieldLabel("Flight Name:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        flightNameField = createStyledTextField();
        flightNameField.setEditable(false);
        flightNameField.setBackground(new Color(245, 245, 245));
        flightGrid.add(flightNameField, gbc);

        // From City
        gbc.gridx = 0; gbc.gridy = 2; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0.0;
        flightGrid.add(createFieldLabel("From:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        fromCityField = createStyledTextField();
        fromCityField.setEditable(false);
        fromCityField.setBackground(new Color(245, 245, 245));
        flightGrid.add(fromCityField, gbc);

        // To City
        gbc.gridx = 0; gbc.gridy = 3; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0.0;
        flightGrid.add(createFieldLabel("To:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        toCityField = createStyledTextField();
        toCityField.setEditable(false);
        toCityField.setBackground(new Color(245, 245, 245));
        flightGrid.add(toCityField, gbc);

        // Travel Date
        gbc.gridx = 0; gbc.gridy = 4; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0.0;
        flightGrid.add(createFieldLabel("Travel Date:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        travelDateField = createStyledTextField();
        travelDateField.setEditable(false);
        travelDateField.setBackground(new Color(245, 245, 245));
        flightGrid.add(travelDateField, gbc);

        // Seat Class
        gbc.gridx = 0; gbc.gridy = 5; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0.0;
        flightGrid.add(createFieldLabel("Seat Class:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        seatClassComboBox = createStyledComboBox(new String[]{"Economy", "Business", "First Class"});
        flightGrid.add(seatClassComboBox, gbc);

        // Seat Number
        gbc.gridx = 0; gbc.gridy = 6; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0.0;
        flightGrid.add(createFieldLabel("Seat Number:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        seatNumberComboBox = createStyledComboBox(new String[]{});
        flightGrid.add(seatNumberComboBox, gbc);

        // Price (based on seat class)
        gbc.gridx = 0; gbc.gridy = 7; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0.0;
        flightGrid.add(createFieldLabel("Price:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        priceField = createStyledTextField();
        priceField.setEditable(false);
        priceField.setBackground(new Color(245, 245, 245));
        flightGrid.add(priceField, gbc);

        flightSection.add(flightGrid, BorderLayout.CENTER);
        contentPanel.add(flightSection);
        contentPanel.add(Box.createVerticalStrut(15));

        // Booking Information Section
        JPanel bookingSection = createBorderedSection("Booking Information");
        JPanel bookingGrid = new JPanel(new GridBagLayout());
        bookingGrid.setBackground(Color.WHITE);

        // Payment Method
        gbc.gridx = 0; gbc.gridy = 0; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0.0;
        bookingGrid.add(createFieldLabel("Payment Method:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        paymentMethodField = createStyledTextField();
        paymentMethodField.setEditable(false);
        paymentMethodField.setBackground(new Color(245, 245, 245));
        bookingGrid.add(paymentMethodField, gbc);

        // Booked By
        gbc.gridx = 0; gbc.gridy = 1; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0.0;
        bookingGrid.add(createFieldLabel("Booked By:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        bookedByField = createStyledTextField();
        bookedByField.setEditable(false);
        bookedByField.setBackground(new Color(245, 245, 245));
        bookingGrid.add(bookedByField, gbc);

        bookingSection.add(bookingGrid, BorderLayout.CENTER);
        contentPanel.add(bookingSection);

        // Add scroll pane
        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        
        add(scrollPane, BorderLayout.CENTER);

        // Button Panel
        JPanel buttonPanel = createButtonPanel();
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private JLabel createFieldLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        label.setForeground(new Color(52, 58, 64));
        label.setPreferredSize(new Dimension(120, 25));
        return label;
    }

    private JPanel createBorderedSection(String title) {
        JPanel section = new JPanel(new BorderLayout());
        section.setBackground(Color.WHITE);
        section.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
            title,
            0,
            0,
            new Font("Segoe UI", Font.BOLD, 12),
            new Color(52, 58, 64)
        ));
        section.add(Box.createVerticalStrut(10), BorderLayout.NORTH);
        section.add(Box.createVerticalStrut(10), BorderLayout.SOUTH);
        return section;
    }

    private JTextField createStyledTextField() {
        JTextField field = new JTextField();
        field.setPreferredSize(new Dimension(150, 32));
        field.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_COLOR, 1),
            BorderFactory.createEmptyBorder(5, 8, 5, 8)
        ));
        return field;
    }

    private JComboBox<String> createStyledComboBox(String[] items) {
        JComboBox<String> comboBox = new JComboBox<>(items);
        comboBox.setPreferredSize(new Dimension(150, 32));
        comboBox.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        comboBox.setBorder(BorderFactory.createLineBorder(BORDER_COLOR, 1));
        return comboBox;
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 30, 15));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 15, 0));

        saveButton = new JButton("Save");
        saveButton.setPreferredSize(new Dimension(100, 36));
        saveButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        saveButton.setBackground(new Color(0, 102, 153)); // #006699
        saveButton.setForeground(Color.WHITE);
        saveButton.setFocusPainted(false);
        saveButton.setBorder(BorderFactory.createEmptyBorder());
        saveButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        cancelButton = new JButton("Cancel");
        cancelButton.setPreferredSize(new Dimension(100, 36));
        cancelButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        cancelButton.setBackground(new Color(211, 47, 47)); // #D32F2F
        cancelButton.setForeground(Color.WHITE);
        cancelButton.setFocusPainted(false);
        cancelButton.setBorder(BorderFactory.createEmptyBorder());
        cancelButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        return buttonPanel;
    }

    private void loadPassengerDetails() {
        try {
            currentPassenger = walkInPassengerDao.getWalkInPassengerById(bookingId);
            
            if (currentPassenger != null) {
                // Passenger Information
                passengerNameField.setText(currentPassenger.getPassengerName() != null ? currentPassenger.getPassengerName() : "");
                passengerPhoneField.setText("9842073200"); // Default phone - in real app, get from user data
                passengerEmailField.setText(currentPassenger.getPassengerEmail() != null ? currentPassenger.getPassengerEmail() : "");
                bookingStatusComboBox.setSelectedItem(currentPassenger.getBookingStatus() != null ? currentPassenger.getBookingStatus() : "Confirmed");
                
                // Flight Information
                flightCodeField.setText(currentPassenger.getFlightCode() != null ? currentPassenger.getFlightCode() : "");
                flightNameField.setText(currentPassenger.getFlightName() != null ? currentPassenger.getFlightName() : "");
                fromCityField.setText(currentPassenger.getFromCity() != null ? currentPassenger.getFromCity() : "");
                toCityField.setText(currentPassenger.getToCity() != null ? currentPassenger.getToCity() : "");
                travelDateField.setText(currentPassenger.getTravelDate() != null ? currentPassenger.getTravelDate().toString() : "");
                seatClassComboBox.setSelectedItem(currentPassenger.getSeatClass() != null ? currentPassenger.getSeatClass() : "Economy");
                
                // Load available seats and set current seat
                loadAvailableSeats();
                if (currentPassenger.getSeatNo() != null && !currentPassenger.getSeatNo().isEmpty()) {
                    seatNumberComboBox.setSelectedItem(currentPassenger.getSeatNo());
                }
                
                // Load and display price for current seat class
                updatePriceForSeatClass();
                
                // Booking Information
                paymentMethodField.setText(currentPassenger.getPaymentMethod() != null ? currentPassenger.getPaymentMethod() : "");
                bookedByField.setText(currentPassenger.getEmployeeName() != null ? currentPassenger.getEmployeeName() : "");
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading passenger details: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void setupEventListeners() {
        // Save button
        saveButton.addActionListener(e -> saveChanges());

        // Cancel button  
        cancelButton.addActionListener(e -> dispose());
        
        // Seat class combobox listener to update price and available seats
        seatClassComboBox.addActionListener(e -> {
            updatePriceForSeatClass();
            loadAvailableSeats();
        });
    }

    private void saveChanges() {
        try {
            if (!validateForm()) {
                return;
            }

            String passengerName = passengerNameField.getText().trim();
            String passengerEmail = passengerEmailField.getText().trim();
            String seatClass = (String) seatClassComboBox.getSelectedItem();
            String seatNo = (String) seatNumberComboBox.getSelectedItem();
            String newBookingStatus = (String) bookingStatusComboBox.getSelectedItem();
            String oldBookingStatus = currentPassenger.getBookingStatus();

            // Handle seat changes
            String oldSeatClass = currentPassenger.getSeatClass();
            String oldSeatNo = currentPassenger.getSeatNo();
            boolean seatChanged = !oldSeatClass.equals(seatClass) || !oldSeatNo.equals(seatNo);
            boolean statusChanged = !oldBookingStatus.equals(newBookingStatus);

            // Release the old seat if seat class or seat number changed
            if (seatChanged) {
                seatClassDao.releaseSeat(seatClassDao.getFlightIdByCode(currentPassenger.getFlightCode()), oldSeatClass, oldSeatNo);
            }

            // Handle status change - release seat if cancelled
            if (statusChanged && "Cancelled".equals(newBookingStatus)) {
                // Release the current seat when cancelling
                seatClassDao.releaseSeat(seatClassDao.getFlightIdByCode(currentPassenger.getFlightCode()), seatClass, seatNo);
                System.out.println("✅ Seat released due to cancellation: " + seatClass + " - " + seatNo);
            }

            // Update passenger details
            boolean passengerUpdated = walkInPassengerDao.updateWalkInPassenger(bookingId, passengerName, passengerEmail, seatClass, seatNo);
            
            // Update booking status separately
            boolean statusUpdated = walkInPassengerDao.updateBookingStatus(bookingId, newBookingStatus);

            if (passengerUpdated && statusUpdated) {
                // Mark the new seat as booked if status is confirmed and seat changed
                if ("Confirmed".equals(newBookingStatus) && seatChanged) {
                    seatClassDao.markSeatAsBooked(seatClassDao.getFlightIdByCode(currentPassenger.getFlightCode()), seatClass, seatNo);
                }

                // Show appropriate success message
                String message = "Passenger updated successfully!";
                
                if (statusChanged && "Cancelled".equals(newBookingStatus)) {
                    message += "\nSeat has been released.";
                }

                JOptionPane.showMessageDialog(this, message, "Success", JOptionPane.INFORMATION_MESSAGE);
                
                if (refreshCallback != null) {
                    refreshCallback.run();
                }
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Failed to update passenger details.\nPlease try again.", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error saving changes: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean validateForm() {
        if (passengerNameField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter passenger name.", "Error", JOptionPane.WARNING_MESSAGE);
            passengerNameField.requestFocus();
            return false;
        }

        if (passengerEmailField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter passenger email.", "Error", JOptionPane.WARNING_MESSAGE);
            passengerEmailField.requestFocus();
            return false;
        }

        if (seatClassComboBox.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Please select a seat class.", "Error", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        if (seatNumberComboBox.getSelectedItem() == null || seatNumberComboBox.getSelectedItem().toString().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select a seat number.", "Error", JOptionPane.WARNING_MESSAGE);
            seatNumberComboBox.requestFocus();
            return false;
        }

        return true;
    }
    
    private void loadAvailableSeats() {
        try {
            String selectedClass = (String) seatClassComboBox.getSelectedItem();
            if (selectedClass == null || currentPassenger == null) {
                return;
            }
            
            String flightCode = currentPassenger.getFlightCode();
            if (flightCode == null) {
                return;
            }
            
            // Get flight ID from flight code
            int flightId = seatClassDao.getFlightIdByCode(flightCode);
            if (flightId == -1) {
                System.err.println("Flight ID not found for flight code: " + flightCode);
                return;
            }
            
            // Fetch available seats from database
            System.out.println("DEBUG: Looking up seats for Flight ID: " + flightId + ", Class: '" + selectedClass + "'");
            java.util.List<String> availableSeats = seatClassDao.getAvailableSeatsByClass(flightId, selectedClass);
            System.out.println("DEBUG: Found " + availableSeats.size() + " available seats: " + availableSeats);
            
            // Clear and populate seat combobox
            seatNumberComboBox.removeAllItems();
            
            // Add current passenger's seat if it exists and belongs to selected class
            String currentSeat = currentPassenger.getSeatNo();
            if (currentSeat != null && !currentSeat.isEmpty()) {
                // Check if current seat belongs to the selected class by verifying it's in available seats
                // or by checking if it matches the class (for already booked seats)
                seatNumberComboBox.addItem(currentSeat);
            }
            
            // Add all available seats for the selected class
            for (String seat : availableSeats) {
                // Don't add duplicate seats
                if (currentSeat == null || !seat.equals(currentSeat)) {
                    seatNumberComboBox.addItem(seat);
                }
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error loading available seats: " + e.getMessage());
        }
    }
    
    private void updatePriceForSeatClass() {
        try {
            String selectedClass = (String) seatClassComboBox.getSelectedItem();
            if (selectedClass == null || currentPassenger == null) {
                priceField.setText("");
                return;
            }
            
            String flightCode = currentPassenger.getFlightCode();
            if (flightCode == null) {
                priceField.setText("");
                return;
            }
            
            // Get flight ID from flight code
            int flightId = seatClassDao.getFlightIdByCode(flightCode);
            if (flightId == -1) {
                System.err.println("Flight ID not found for flight code: " + flightCode);
                priceField.setText("N/A");
                return;
            }
            
            // Fetch price from database
            System.out.println("DEBUG: Looking up price for Flight ID: " + flightId + ", Class: '" + selectedClass + "'");
            int price = seatClassDao.getPriceByFlightAndClass(flightId, selectedClass);
            System.out.println("DEBUG: Price result: " + price);
            
            if (price != -1) {
                // Format price with currency
                priceField.setText("NPR " + price);
                System.out.println("DEBUG: Price set to: NPR " + price);
            } else {
                System.err.println("Price not found for flight: " + flightCode + ", class: " + selectedClass);
                priceField.setText("N/A");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error updating price: " + e.getMessage());
            priceField.setText("Error");
        }
    }
} 