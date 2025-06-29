package wingsnepal.view;

import wingsnepal.model.ManageBookingModel;
import wingsnepal.dao.ManageBookingDao;
import wingsnepal.dao.SeatClassDao;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class WalkInCustomerEditDialog extends JDialog {
    private int bookingId;
    private ManageBookingModel currentBooking;
    private SeatClassDao seatClassDao;
    private ManageBookingDao manageBookingDao;
    private Runnable refreshCallback;
    
    // Form components
    private JTextField customerNameField;
    private JTextField customerEmailField;
    private JTextField flightCodeField;
    private JTextField fromCityField;
    private JTextField toCityField;
    private JTextField travelDateField;
    private JComboBox<String> seatClassComboBox;
    private JComboBox<String> seatNoComboBox;
    private JTextField priceField;
    private JButton saveButton;
    private JButton cancelButton;

    // UI Colors
    private static final Color PRIMARY_COLOR = new Color(0, 102, 153);
    private static final Color BACKGROUND_COLOR = new Color(245, 245, 245);
    private static final Color BORDER_COLOR = new Color(220, 220, 220);

    public WalkInCustomerEditDialog(Frame parent, int bookingId, Runnable refreshCallback) {
        super(parent, "Edit Walk-In Customer Booking", true);
        this.bookingId = bookingId;
        this.refreshCallback = refreshCallback;
        this.seatClassDao = new SeatClassDao();
        this.manageBookingDao = new ManageBookingDao();
        
        initComponents();
        loadBookingDetails();
        setupEventListeners();
        
        setSize(600, 500);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }
    
    private void initComponents() {
        setLayout(new BorderLayout());
        getContentPane().setBackground(BACKGROUND_COLOR);
        
        // Title panel
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(PRIMARY_COLOR);
        titlePanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        JLabel titleLabel = new JLabel("Edit Walk-In Customer Booking");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);
        titlePanel.add(titleLabel);
        add(titlePanel, BorderLayout.NORTH);
        
        // Main form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_COLOR, 1),
            BorderFactory.createEmptyBorder(25, 25, 25, 25)
        ));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Customer Information Section
        addSectionHeader(formPanel, gbc, "Customer Information", 0);
        
        gbc.gridy = 1;
        customerNameField = addFormField(formPanel, gbc, "Customer Name:", true);
        
        gbc.gridy = 2;
        customerEmailField = addFormField(formPanel, gbc, "Customer Email:", true);
        
        // Flight Information Section
        addSectionHeader(formPanel, gbc, "Flight Information", 3);
        
        gbc.gridy = 4;
        flightCodeField = addFormField(formPanel, gbc, "Flight Code:", false);
        
        gbc.gridy = 5;
        fromCityField = addFormField(formPanel, gbc, "From:", false);
        
        gbc.gridy = 6;
        toCityField = addFormField(formPanel, gbc, "To:", false);
        
        gbc.gridy = 7;
        travelDateField = addFormField(formPanel, gbc, "Travel Date:", false);
        
        // Seat Information Section
        addSectionHeader(formPanel, gbc, "Seat Information", 8);
        
        // Seat Class
        gbc.gridy = 9;
        gbc.gridx = 0;
        formPanel.add(new JLabel("Seat Class:"), gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        seatClassComboBox = new JComboBox<>(new String[]{"Economy", "Business", "First Class"});
        seatClassComboBox.setPreferredSize(new Dimension(300, 35));
        formPanel.add(seatClassComboBox, gbc);
        
        // Seat Number
        gbc.gridy = 10;
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.NONE;
        formPanel.add(new JLabel("Seat Number:"), gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        seatNoComboBox = new JComboBox<>();
        seatNoComboBox.setPreferredSize(new Dimension(300, 35));
        formPanel.add(seatNoComboBox, gbc);
        
        gbc.gridy = 11;
        priceField = addFormField(formPanel, gbc, "Price:", false);
        
        add(formPanel, BorderLayout.CENTER);
        
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 30, 15));
        buttonPanel.setBackground(Color.WHITE);
        
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
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    private void addSectionHeader(JPanel panel, GridBagConstraints gbc, String title, int row) {
        gbc.gridy = row;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(row == 0 ? 0 : 15, 0, 10, 0);
        
        JLabel headerLabel = new JLabel(title);
        headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        headerLabel.setForeground(PRIMARY_COLOR);
        panel.add(headerLabel, gbc);
        
        gbc.gridwidth = 1;
        gbc.insets = new Insets(8, 10, 8, 10);
    }
    
    private JTextField addFormField(JPanel panel, GridBagConstraints gbc, String label, boolean editable) {
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.NONE;
        panel.add(new JLabel(label), gbc);
        
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        JTextField field = new JTextField();
        field.setPreferredSize(new Dimension(300, 35));
        field.setEditable(editable);
        if (!editable) {
            field.setBackground(new Color(245, 245, 245));
        }
        panel.add(field, gbc);
        
        return field;
    }
    
    private void loadBookingDetails() {
        try {
            // Get walk-in bookings and find the specific one
            List<ManageBookingModel> walkInBookings = manageBookingDao.getWalkInBookings();
            for (ManageBookingModel booking : walkInBookings) {
                if (booking.getBookingId() == bookingId) {
                    currentBooking = booking;
                    break;
                }
            }
            
            if (currentBooking != null) {
                customerNameField.setText(currentBooking.getPassengerName() != null ? currentBooking.getPassengerName() : "");
                customerEmailField.setText(currentBooking.getPassengerEmail() != null ? currentBooking.getPassengerEmail() : "");
                flightCodeField.setText(currentBooking.getFlightCode());
                fromCityField.setText(currentBooking.getFromCity() != null ? currentBooking.getFromCity() : "");
                toCityField.setText(currentBooking.getToCity() != null ? currentBooking.getToCity() : "");
                travelDateField.setText(currentBooking.getTravelDate().toString());
                seatClassComboBox.setSelectedItem(currentBooking.getClassName());
                
                // Load available seats and set current seat
                updateAvailableSeats();
                seatNoComboBox.setSelectedItem(currentBooking.getSeatNo());
                
                // Update price
                updatePrice();
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading booking details: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void setupEventListeners() {
        seatClassComboBox.addActionListener(e -> {
            updateAvailableSeats();
            updatePrice();
        });
        
        saveButton.addActionListener(e -> saveChanges());
        cancelButton.addActionListener(e -> dispose());
    }
    
    private void updateAvailableSeats() {
        if (currentBooking != null) {
            String selectedClass = (String) seatClassComboBox.getSelectedItem();
            int flightId = seatClassDao.getFlightIdByCode(currentBooking.getFlightCode());
            
            if (flightId != -1 && selectedClass != null) {
                List<String> availableSeats = seatClassDao.getAvailableSeatsByClass(flightId, selectedClass);
                seatNoComboBox.removeAllItems();
                
                // Add current seat first (even if different class)
                if (currentBooking.getSeatNo() != null) {
                    seatNoComboBox.addItem(currentBooking.getSeatNo());
                }
                
                // Add all available seats
                for (String seat : availableSeats) {
                    if (!seat.equals(currentBooking.getSeatNo())) {
                        seatNoComboBox.addItem(seat);
                    }
                }
            }
        }
    }
    
    private void updatePrice() {
        if (currentBooking != null) {
            String selectedClass = (String) seatClassComboBox.getSelectedItem();
            int flightId = seatClassDao.getFlightIdByCode(currentBooking.getFlightCode());
            
            if (flightId != -1 && selectedClass != null) {
                int price = seatClassDao.getPriceByFlightAndClass(flightId, selectedClass);
                priceField.setText("NPR " + price);
            }
        }
    }
    
    private void saveChanges() {
        try {
            if (!validateForm()) {
                return;
            }
            
            String customerName = customerNameField.getText().trim();
            String customerEmail = customerEmailField.getText().trim();
            String seatClass = (String) seatClassComboBox.getSelectedItem();
            String seatNo = (String) seatNoComboBox.getSelectedItem();
            
            // Get seat ID
            int flightId = seatClassDao.getFlightIdByCode(currentBooking.getFlightCode());
            int seatId = seatClassDao.getSeatIdByFlightClassAndSeatNo(flightId, seatClass, seatNo);
            
            if (seatId == -1) {
                JOptionPane.showMessageDialog(this, "Could not determine seat ID for the selected seat.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Release the old seat if seat class or seat number changed
            String oldSeatClass = currentBooking.getClassName();
            String oldSeatNo = currentBooking.getSeatNo();
            if (!oldSeatClass.equals(seatClass) || !oldSeatNo.equals(seatNo)) {
                seatClassDao.releaseSeat(seatClassDao.getFlightIdByCode(currentBooking.getFlightCode()), oldSeatClass, oldSeatNo);
            }

            // Update booking
            boolean success = manageBookingDao.updateWalkInBooking(bookingId, seatNo, seatClass, seatId, customerName, customerEmail);

            // Mark the new seat as booked if update succeeded
            if (success) {
                seatClassDao.markSeatAsBooked(seatClassDao.getFlightIdByCode(currentBooking.getFlightCode()), seatClass, seatNo);
                JOptionPane.showMessageDialog(this, "Booking updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                if (refreshCallback != null) {
                    refreshCallback.run();
                }
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to update booking.", "Error", JOptionPane.ERROR_MESSAGE);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error saving changes: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private boolean validateForm() {
        if (customerNameField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter customer name.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        String email = customerEmailField.getText().trim();
        if (email.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter customer email.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            JOptionPane.showMessageDialog(this, "Please enter a valid email address.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        if (seatNoComboBox.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Please select a seat number.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        return true;
    }
} 