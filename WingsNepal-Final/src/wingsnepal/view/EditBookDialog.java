package wingsnepal.view;

import wingsnepal.model.ManageBookingModel;
import wingsnepal.dao.ManageBookingDao;
import wingsnepal.dao.SeatClassDao;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.JFormattedTextField;

public class EditBookDialog extends JDialog {
    private int bookingId;
    private UserPortal userPortal;
    private JTextField seatNoTextField;
    private JComboBox<String> seatClassComboBox;
    private JComboBox<String> seatNoComboBox;
    private JTextField priceTextField;
    private JTextField flightCodeTextField;
    private JTextField flightNameTextField;
    private JTextField fullNameTextField;
    private JTextField emailTextField;
    private JSpinner ticketsSpinner;
    private SeatClassDao seatClassDao;
    private ManageBookingModel currentBooking;
    private Runnable refreshCallback;

    // Modern UI Colors
    private static final Color BACKGROUND_COLOR = new Color(245, 245, 245);
    private static final Color FOREGROUND_COLOR = new Color(51, 51, 51);
    private static final Color PRIMARY_COLOR = new Color(0, 102, 153);
    private static final Color SECONDARY_COLOR = new Color(108, 117, 125);
    private static final Color DANGER_COLOR = new Color(220, 53, 69);
    private static final Color BORDER_COLOR = new Color(220, 220, 220);

    // Consistent field sizing
    private static final Dimension FIELD_SIZE = new Dimension(400, 35);

    public EditBookDialog(UserPortal userPortal, int bookingId, Runnable refreshCallback) {
        this.userPortal = userPortal;
        this.bookingId = bookingId;
        this.refreshCallback = refreshCallback;
        this.seatClassDao = new SeatClassDao();
        initComponents();
        loadBookingDetails();
        setTitle("Edit Booking - WingsNepal");
        setModal(true);
        setLocationRelativeTo(userPortal);
        setResizable(false);
    }

    private void initComponents() {
        // Dialog settings
        getContentPane().setBackground(BACKGROUND_COLOR);
        setLayout(new BorderLayout());

        // Main panel with modern look
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_COLOR, 1),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 10, 12, 10); // Increased vertical spacing from 10 to 12
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Title
        JLabel titleLabel = new JLabel("Edit Flight Booking");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(PRIMARY_COLOR);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(0, 0, 25, 0); // Increased bottom padding from 20 to 25
        mainPanel.add(titleLabel, gbc);

        gbc.insets = new Insets(12, 10, 12, 10); // Reset insets with increased spacing

        // Flight Information Section
        addSectionHeader(mainPanel, gbc, "Flight Information", 1);
        flightCodeTextField = addFormField(mainPanel, gbc, "Flight Code:", 2);
        flightNameTextField = addFormField(mainPanel, gbc, "Flight Name:", 3);
        flightCodeTextField.setEditable(false);
        flightNameTextField.setEditable(false);

        // Passenger Information Section
        addSectionHeader(mainPanel, gbc, "Passenger Information", 4);
        fullNameTextField = addFormField(mainPanel, gbc, "Full Name:", 5);
        emailTextField = addFormField(mainPanel, gbc, "Email:", 6);
        addTicketsSpinner(mainPanel, gbc, 7);

        // Seat Information Section
        addSectionHeader(mainPanel, gbc, "Seat Information", 8);
        addSeatClassComboBox(mainPanel, gbc, 9);
        addSeatNumberComboBox(mainPanel, gbc, 10);
        priceTextField = addFormField(mainPanel, gbc, "Price (NPR):", 11);
        priceTextField.setEditable(false);

        // Buttons Panel
        JPanel buttonPanel = createButtonPanel();

        add(mainPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        setSize(700, 800); // Increased width to 700 to accommodate consistently sized form elements
    }

    private void addSectionHeader(JPanel panel, GridBagConstraints gbc, String title, int gridy) {
        JLabel sectionLabel = new JLabel(title);
        sectionLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        sectionLabel.setForeground(FOREGROUND_COLOR);
        sectionLabel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, BORDER_COLOR));
        gbc.gridx = 0;
        gbc.gridy = gridy;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(sectionLabel, gbc);
    }

    private JTextField addFormField(JPanel panel, GridBagConstraints gbc, String labelText, int gridy) {
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        label.setForeground(FOREGROUND_COLOR);
        gbc.gridx = 0;
        gbc.gridy = gridy;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(label, gbc);

        JTextField textField = new JTextField();
        textField.setPreferredSize(FIELD_SIZE); // Consistent sizing with other form elements
        textField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        textField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_COLOR, 1),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        gbc.gridx = 1;
        panel.add(textField, gbc);

        return textField;
    }
    
    private void addTicketsSpinner(JPanel panel, GridBagConstraints gbc, int gridy) {
        JLabel ticketsLabel = new JLabel("Number of Tickets:");
        ticketsLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        ticketsLabel.setForeground(FOREGROUND_COLOR);
        gbc.gridx = 0;
        gbc.gridy = gridy;
        panel.add(ticketsLabel, gbc);
        
        ticketsSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 10, 1));
        ticketsSpinner.setPreferredSize(FIELD_SIZE); // Consistent sizing with other form elements
        ticketsSpinner.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        ticketsSpinner.setEnabled(false); // As per previous request
        JFormattedTextField tf = ((JSpinner.DefaultEditor) ticketsSpinner.getEditor()).getTextField();
        tf.setEditable(false);
        tf.setBackground(Color.WHITE);
        gbc.gridx = 1;
        panel.add(ticketsSpinner, gbc);
    }

    private void addSeatClassComboBox(JPanel panel, GridBagConstraints gbc, int gridy) {
        JLabel seatClassLabel = new JLabel("Seat Class:");
        seatClassLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        seatClassLabel.setForeground(FOREGROUND_COLOR);
        gbc.gridx = 0;
        gbc.gridy = gridy;
        panel.add(seatClassLabel, gbc);

        seatClassComboBox = new JComboBox<>(new String[]{"Economy", "Business", "First Class"});
        seatClassComboBox.setPreferredSize(FIELD_SIZE); // Consistent sizing with other form elements
        seatClassComboBox.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        seatClassComboBox.addActionListener(e -> updateSeatNumbersAndPrice());
        gbc.gridx = 1;
        panel.add(seatClassComboBox, gbc);
    }

    private void addSeatNumberComboBox(JPanel panel, GridBagConstraints gbc, int gridy) {
        JLabel seatNoLabel = new JLabel("Seat Number:");
        seatNoLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        seatNoLabel.setForeground(FOREGROUND_COLOR);
        gbc.gridx = 0;
        gbc.gridy = gridy;
        panel.add(seatNoLabel, gbc);

        seatNoComboBox = new JComboBox<>();
        seatNoComboBox.setPreferredSize(FIELD_SIZE); // Consistent sizing with other form elements
        seatNoComboBox.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gbc.gridx = 1;
        panel.add(seatNoComboBox, gbc);
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 15));
        buttonPanel.setBackground(BACKGROUND_COLOR);

        JButton saveButton = new JButton("Save Changes");
        styleButtonWithHover(saveButton, PRIMARY_COLOR);
        saveButton.addActionListener(e -> saveBookingDetails());

        JButton cancelButton = new JButton("Cancel");
        styleButtonWithHover(cancelButton, DANGER_COLOR);
        cancelButton.addActionListener(e -> dispose());

        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        return buttonPanel;
    }
    
    private void styleButtonWithHover(JButton button, Color backgroundColor) {
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setBackground(backgroundColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(backgroundColor.brighter());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(backgroundColor);
            }
        });
    }

    // Load the booking details into the dialog fields
    private void loadBookingDetails() {
        ManageBookingDao manageBookingDao = new ManageBookingDao();
        currentBooking = manageBookingDao.getBookingDetailsById(bookingId);

        if (currentBooking != null) {
            flightCodeTextField.setText(currentBooking.getFlightCode());
            flightNameTextField.setText(currentBooking.getFlightName());
            fullNameTextField.setText(currentBooking.getFullName());
            emailTextField.setText(currentBooking.getEmail());
            ticketsSpinner.setValue(currentBooking.getTickets());
            seatClassComboBox.setSelectedItem(currentBooking.getClassName());
            updateSeatNumbersAndPrice();
            seatNoComboBox.setSelectedItem(currentBooking.getSeatNo());
            
            // Apply conditional editing based on booking type
            applyConditionalFieldEditing();
        }
    }

    /**
     * Determines if this is a self-booking (user booked for himself) or booking for someone else.
     * Applies appropriate field editability based on the booking type.
     */
    private void applyConditionalFieldEditing() {
        boolean isSelfBooking = isSelfBooking();
        
        if (isSelfBooking) {
            // Self-booking: Lock passenger details (tied to user account)
            makeFieldReadOnly(fullNameTextField, "This is your account information and cannot be changed here.");
            makeFieldReadOnly(emailTextField, "This is your account email and cannot be changed here.");
            
            // Add visual indicator
            addBookingTypeIndicator("Self-Booking: Personal details are locked", new Color(40, 167, 69));
        } else {
            // Booking for someone else: Allow editing passenger details
            makeFieldEditable(fullNameTextField);
            makeFieldEditable(emailTextField);
            
            // Add visual indicator
            addBookingTypeIndicator("Booking for Someone Else: Passenger details can be edited", new Color(255, 193, 7));
        }
        
        // Flight details are always locked in edit mode
        makeFieldReadOnly(flightCodeTextField, "Flight code cannot be changed.");
        makeFieldReadOnly(flightNameTextField, "Flight name cannot be changed.");
    }
    
    /**
     * Checks if the current booking is a self-booking by comparing passenger details
     * with the logged-in user's account information.
     */
    private boolean isSelfBooking() {
        if (currentBooking == null || userPortal.getLoggedInUser() == null) {
            return false;
        }
        
        String bookingName = currentBooking.getFullName();
        String bookingEmail = currentBooking.getEmail();
        String userAccountName = userPortal.getLoggedInUser().getFullName();
        String userAccountEmail = userPortal.getLoggedInUser().getEmail();
        
        // Check if both name and email match the user's account
        return bookingName != null && bookingEmail != null &&
               bookingName.trim().equalsIgnoreCase(userAccountName.trim()) &&
               bookingEmail.trim().equalsIgnoreCase(userAccountEmail.trim());
    }
    
    /**
     * Makes a text field read-only with appropriate styling and tooltip.
     */
    private void makeFieldReadOnly(JTextField field, String tooltip) {
        field.setEditable(false);
        field.setPreferredSize(FIELD_SIZE); // Maintain consistent sizing
        field.setBackground(new Color(245, 245, 245)); // Light gray background
        field.setForeground(new Color(100, 100, 100)); // Darker gray text
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        field.setToolTipText(tooltip);
    }
    
    /**
     * Makes a text field editable with normal styling.
     */
    private void makeFieldEditable(JTextField field) {
        field.setEditable(true);
        field.setPreferredSize(FIELD_SIZE); // Maintain consistent sizing
        field.setBackground(Color.WHITE);
        field.setForeground(FOREGROUND_COLOR);
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_COLOR, 1),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        field.setToolTipText(null);
    }
    
    /**
     * Adds a visual indicator showing the booking type at the top of the dialog.
     */
    private void addBookingTypeIndicator(String message, Color color) {
        JLabel indicator = new JLabel(message);
        indicator.setFont(new Font("Segoe UI", Font.BOLD, 12));
        indicator.setForeground(Color.WHITE);
        indicator.setOpaque(true);
        indicator.setBackground(color);
        indicator.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        indicator.setHorizontalAlignment(JLabel.CENTER);
        
        // Add to the top of the dialog
        add(indicator, BorderLayout.NORTH);
    }

    // Save the edited booking details
    private void saveBookingDetails() {
        String seatClass = (String) seatClassComboBox.getSelectedItem();
        String seatNo = (String) seatNoComboBox.getSelectedItem();
        String fullName = fullNameTextField.getText().trim();
        String email = emailTextField.getText().trim();
        int tickets = (Integer) ticketsSpinner.getValue();

        boolean isSelfBooking = isSelfBooking();

        // Validation - only validate editable fields
        if (!isSelfBooking) {
            // For bookings for someone else, validate passenger details
        if (fullName.isEmpty() || email.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in all required passenger details.");
                return;
            }

            if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
                JOptionPane.showMessageDialog(this, "Please enter a valid email address.");
            return;
            }
        }

        // Validate seat selection (always required)
        if (seatNo == null || seatNo.isEmpty() || seatNo.equals("No seats available")) {
            JOptionPane.showMessageDialog(this, "Please select a valid seat number.");
            return;
        }

        // Compute seatId using SeatClassDao
        int seatId = -1;
        if (currentBooking != null) {
            SeatClassDao seatDao = new SeatClassDao();
            int flightId = seatDao.getFlightIdByCode(currentBooking.getFlightCode());
            if (flightId != -1) {
                seatId = seatDao.getSeatIdByFlightClassAndSeatNo(flightId, seatClass, seatNo);
            }
        }
        if (seatId == -1) {
            JOptionPane.showMessageDialog(this, "Could not determine seat ID for the selected seat.");
            return;
        }

        // Update booking in database
        ManageBookingDao manageBookingDao = new ManageBookingDao();
        
        if (isSelfBooking) {
            // For self-bookings, only update seat-related information
        manageBookingDao.updateBooking(bookingId, seatNo, seatClass, seatId);
            JOptionPane.showMessageDialog(this, "Booking updated successfully!\n(Personal details are linked to your account and remain unchanged)");
        } else {
            // For bookings for others, update all editable information including passenger details
            boolean success = manageBookingDao.updateBookingWithPassengerDetails(bookingId, seatNo, seatClass, seatId, fullName, email);
            if (success) {
                JOptionPane.showMessageDialog(this, "Booking updated successfully!\n(Passenger details and flight information updated)");
            } else {
                JOptionPane.showMessageDialog(this, "Failed to update booking. Please try again.");
                return;
            }
        }
        
        // Refresh the seat selection in the main UserPortal
        if (userPortal != null) {
            String flightCode = flightCodeTextField.getText().trim();
            if (!flightCode.isEmpty()) {
                userPortal.updateAvailableSeatsDirectly(flightCode, seatClass);
            }
        }
        
        refreshCallback.run();
        dispose();  // Close the dialog
    }

    // Update seat numbers and price based on selected class
    private void updateSeatNumbersAndPrice() {
        String selectedClass = (String) seatClassComboBox.getSelectedItem();
        if (selectedClass != null && currentBooking != null) {
            // Get flight ID from the current booking
            SeatClassDao seatDao = new SeatClassDao();
            int flightId = seatDao.getFlightIdByCode(currentBooking.getFlightCode());
            
            if (flightId != -1) {
                // Fetch available seat numbers based on selected class
                List<String> seats = seatDao.getAvailableSeatsByClass(flightId, selectedClass);
                seatNoComboBox.removeAllItems(); // Clear existing items

                // Only add current seat if it belongs to the selected class
                if (currentBooking != null && currentBooking.getSeatNo() != null) {
                    // Check if the current seat number belongs to the selected class
                    boolean currentSeatBelongsToSelectedClass = false;
                    for (String seat : seats) {
                        if (seat.equals(currentBooking.getSeatNo())) {
                            currentSeatBelongsToSelectedClass = true;
                            break;
                        }
                    }
                    
                    // Only add current seat if it belongs to the selected class
                    if (currentSeatBelongsToSelectedClass) {
                        seatNoComboBox.addItem(currentBooking.getSeatNo());
                    }
                }

                // Add all available seats for the selected class
                for (String seat : seats) {
                    // Don't add duplicate seats
                    if (currentBooking == null || !seat.equals(currentBooking.getSeatNo())) {
                        seatNoComboBox.addItem(seat);
                    }
                }

                // Fetch and display price for selected class
                int price = seatDao.getPriceByFlightAndClass(flightId, selectedClass);
                if (price != -1) {
                    priceTextField.setText(String.valueOf(price));
                } else {
                    priceTextField.setText("N/A");
                }
            }
        }
    }
}