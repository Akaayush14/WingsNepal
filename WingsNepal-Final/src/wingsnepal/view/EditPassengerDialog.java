package wingsnepal.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.border.EmptyBorder;

public class EditPassengerDialog extends JDialog {
    private JTextField fullNameField;
    private JTextField phoneField;
    private JTextField emailField;
    private JTextField seatNumberField;
    private JTextField flightCodeField;
    private JComboBox<String> bookingStatusBox;
    private JLabel seatClassLabel;
    private JButton saveButton, cancelButton;
    private Runnable onSave;
    private String seatClass;
    private boolean isAccountHolderBooking;

    public EditPassengerDialog(Frame owner, String fullName, String phone, String email, String bookingStatus, 
                              String seatClass, String seatNumber, String flightCode, 
                              boolean isAccountHolderBooking, Runnable onSave) {
        super(owner, "Edit Passenger", true);
        this.onSave = onSave;
        this.seatClass = seatClass;
        this.isAccountHolderBooking = isAccountHolderBooking;
        initComponents(fullName, phone, email, bookingStatus, seatNumber, flightCode);
        setLocationRelativeTo(owner);
    }

    private void initComponents(String fullName, String phone, String email, String bookingStatus, String seatNumber, String flightCode) {
        setLayout(new BorderLayout());
        setSize(700, 700);
        setMinimumSize(new Dimension(700, 700));
        getContentPane().setBackground(Color.WHITE);

        // Title
        String titleText = isAccountHolderBooking ? "Edit Passenger (Account Holder)" : "Edit Passenger (Guest)";
        JLabel titleLabel = new JLabel(titleText, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(new Color(51, 122, 183));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        add(titleLabel, BorderLayout.NORTH);

        // Main content panel with sections
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(new EmptyBorder(20, 40, 20, 40));

        // Create sections
        JPanel passengerInfoPanel = createPassengerInfoSection(fullName, phone, email, bookingStatus);
        JPanel flightInfoPanel = createFlightInfoSection(seatNumber, flightCode);

        // Add sections to main panel
        JPanel sectionsPanel = new JPanel(new BorderLayout(0, 20));
        sectionsPanel.setBackground(Color.WHITE);
        sectionsPanel.add(passengerInfoPanel, BorderLayout.NORTH);
        sectionsPanel.add(flightInfoPanel, BorderLayout.CENTER);

        mainPanel.add(sectionsPanel, BorderLayout.CENTER);
        add(mainPanel, BorderLayout.CENTER);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 18, 10));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setBorder(new EmptyBorder(0, 20, 16, 20));
        saveButton = new JButton("Save");
        cancelButton = new JButton("Cancel");

        // Style Save button (blue)
        saveButton.setBackground(new Color(0, 102, 153));
        saveButton.setForeground(Color.WHITE);
        saveButton.setFont(new Font("Segoe UI", Font.BOLD, 12));
        saveButton.setFocusable(false);
        saveButton.setPreferredSize(new Dimension(90, 32));

        // Style Cancel button (red)
        cancelButton.setBackground(new Color(220, 53, 69));
        cancelButton.setForeground(Color.WHITE);
        cancelButton.setFont(new Font("Segoe UI", Font.BOLD, 12));
        cancelButton.setFocusable(false);
        cancelButton.setPreferredSize(new Dimension(90, 32));

        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        add(buttonPanel, BorderLayout.SOUTH);

        saveButton.addActionListener(e -> {
            if (onSave != null) onSave.run();
            dispose();
        });
        cancelButton.addActionListener(e -> dispose());
    }

    private JPanel createPassengerInfoSection(String fullName, String phone, String email, String bookingStatus) {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            "Passenger Information",
            0, 0, new Font("Segoe UI", Font.BOLD, 14), new Color(70, 70, 70)
        ));

        Font labelFont = new Font("Segoe UI", Font.PLAIN, 14);
        GridBagConstraints gbc;
        int row = 0;

        // Full Name - Editable only if booking for someone else
        gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.gridy = row; gbc.insets = new Insets(15, 20, 8, 10); gbc.anchor = GridBagConstraints.WEST;
        JLabel fullNameLabel = new JLabel("Full Name:");
        fullNameLabel.setFont(labelFont);
        panel.add(fullNameLabel, gbc);
        
        gbc = new GridBagConstraints();
        gbc.gridx = 1; gbc.gridy = row++; gbc.insets = new Insets(15, 10, 8, 20); gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1;
        fullNameField = new JTextField(fullName);
        fullNameField.setPreferredSize(new Dimension(400, 35));
        fullNameField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        if (isAccountHolderBooking) {
            fullNameField.setEditable(false);
            fullNameField.setBackground(new Color(245, 245, 245));
            fullNameField.setToolTipText("Cannot edit - linked to account holder");
        }
        panel.add(fullNameField, gbc);

        // Phone - Editable only if booking for someone else
        gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.gridy = row; gbc.insets = new Insets(8, 20, 8, 10); gbc.anchor = GridBagConstraints.WEST;
        JLabel phoneLabel = new JLabel("Phone:");
        phoneLabel.setFont(labelFont);
        panel.add(phoneLabel, gbc);
        
        gbc = new GridBagConstraints();
        gbc.gridx = 1; gbc.gridy = row++; gbc.insets = new Insets(8, 10, 8, 20); gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1;
        phoneField = new JTextField(phone);
        phoneField.setPreferredSize(new Dimension(400, 35));
        phoneField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        if (isAccountHolderBooking) {
            phoneField.setEditable(false);
            phoneField.setBackground(new Color(245, 245, 245));
            phoneField.setToolTipText("Cannot edit - linked to account holder");
        }
        panel.add(phoneField, gbc);

        // Email - Always read-only
        gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.gridy = row; gbc.insets = new Insets(8, 20, 8, 10); gbc.anchor = GridBagConstraints.WEST;
        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setFont(labelFont);
        panel.add(emailLabel, gbc);
        
        gbc = new GridBagConstraints();
        gbc.gridx = 1; gbc.gridy = row++; gbc.insets = new Insets(8, 10, 8, 20); gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1;
        emailField = new JTextField(email);
        emailField.setEditable(false);
        emailField.setOpaque(true);
        emailField.setBackground(new Color(245, 245, 245));
        emailField.setPreferredSize(new Dimension(400, 35));
        emailField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        String emailTooltip = isAccountHolderBooking ? "Cannot edit - linked to account holder" : "Cannot edit - use to identify passenger";
        emailField.setToolTipText(emailTooltip);
        panel.add(emailField, gbc);

        // Booking Status - Always editable
        gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.gridy = row; gbc.insets = new Insets(8, 20, 15, 10); gbc.anchor = GridBagConstraints.WEST;
        JLabel bookingStatusLabel = new JLabel("Booking Status:");
        bookingStatusLabel.setFont(labelFont);
        panel.add(bookingStatusLabel, gbc);
        
        gbc = new GridBagConstraints();
        gbc.gridx = 1; gbc.gridy = row++; gbc.insets = new Insets(8, 10, 15, 20); gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1;
        bookingStatusBox = new JComboBox<>(new String[]{"Confirmed", "Cancelled"});
        bookingStatusBox.setSelectedItem(bookingStatus);
        bookingStatusBox.setPreferredSize(new Dimension(400, 35));
        bookingStatusBox.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        bookingStatusBox.setToolTipText("Always editable for operational management");
        panel.add(bookingStatusBox, gbc);

        return panel;
    }

    private JPanel createFlightInfoSection(String seatNumber, String flightCode) {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            "Flight Information",
            0, 0, new Font("Segoe UI", Font.BOLD, 14), new Color(70, 70, 70)
        ));

        Font labelFont = new Font("Segoe UI", Font.PLAIN, 14);
        GridBagConstraints gbc;
        int row = 0;

        // Seat Class (Read-only)
        gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.gridy = row; gbc.insets = new Insets(15, 20, 8, 10); gbc.anchor = GridBagConstraints.WEST;
        JLabel seatClassLabelText = new JLabel("Seat Class:");
        seatClassLabelText.setFont(labelFont);
        panel.add(seatClassLabelText, gbc);
        
        gbc = new GridBagConstraints();
        gbc.gridx = 1; gbc.gridy = row++; gbc.insets = new Insets(15, 10, 8, 20); gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1;
        this.seatClassLabel = new JLabel(seatClass);
        this.seatClassLabel.setOpaque(true);
        this.seatClassLabel.setBackground(new Color(245, 245, 245));
        this.seatClassLabel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(10, 12, 10, 12)
        ));
        this.seatClassLabel.setPreferredSize(new Dimension(400, 35));
        this.seatClassLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        panel.add(this.seatClassLabel, gbc);

        // Seat Number - Always editable by admin
        gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.gridy = row; gbc.insets = new Insets(8, 20, 8, 10); gbc.anchor = GridBagConstraints.WEST;
        JLabel seatNumberLabelText = new JLabel("Seat Number:");
        seatNumberLabelText.setFont(labelFont);
        panel.add(seatNumberLabelText, gbc);
        
        gbc = new GridBagConstraints();
        gbc.gridx = 1; gbc.gridy = row++; gbc.insets = new Insets(8, 10, 8, 20); gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1;
        seatNumberField = new JTextField(seatNumber);
        seatNumberField.setPreferredSize(new Dimension(400, 35));
        seatNumberField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        seatNumberField.setToolTipText("Admin can always change seat assignment");
        panel.add(seatNumberField, gbc);

        // Flight Code - Always editable by admin
        gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.gridy = row; gbc.insets = new Insets(8, 20, 15, 10); gbc.anchor = GridBagConstraints.WEST;
        JLabel flightCodeLabelText = new JLabel("Flight Code:");
        flightCodeLabelText.setFont(labelFont);
        panel.add(flightCodeLabelText, gbc);
        
        gbc = new GridBagConstraints();
        gbc.gridx = 1; gbc.gridy = row++; gbc.insets = new Insets(8, 10, 15, 20); gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1;
        flightCodeField = new JTextField(flightCode);
        flightCodeField.setPreferredSize(new Dimension(400, 35));
        flightCodeField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        flightCodeField.setToolTipText("Admin can transfer passenger to different flight");
        panel.add(flightCodeField, gbc);

        return panel;
    }

    // Getter methods
    public String getFullName() { return fullNameField.getText().trim(); }
    public String getPhone() { return phoneField.getText().trim(); }
    public String getEmail() { return emailField.getText().trim(); }
    public String getSeatNumber() { return seatNumberField.getText().trim(); }
    public String getFlightCode() { return flightCodeField.getText().trim(); }
    public String getBookingStatus() { return (String) bookingStatusBox.getSelectedItem(); }
} 