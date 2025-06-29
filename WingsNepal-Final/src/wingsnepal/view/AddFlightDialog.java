package wingsnepal.view;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.sql.ResultSet;
import database.DbConnection;
import database.MySqlConnection;
import wingsnepal.dao.SeatClassDao;

public class AddFlightDialog extends JDialog {

    private JTextField flightCodeField, flightNameField, fromCityField, toCityField;
    private JTextField timeField, durationField, dateField;
    private JTextField economyPriceField, businessPriceField, firstClassPriceField;
    private JButton addButton, cancelButton;

    public AddFlightDialog(JFrame parent) {
        super(parent, "Add New Flight", true);
        setSize(500, 600);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel("Add New Flight");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel fieldPanel = new JPanel(new GridBagLayout());
        fieldPanel.setBackground(Color.WHITE);
        fieldPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 30));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Fields
        flightCodeField = new JTextField(15);
        flightNameField = new JTextField(15);
        fromCityField = new JTextField(15);
        toCityField = new JTextField(15);
        timeField = new JTextField(15);
        durationField = new JTextField(15);
        dateField = new JTextField(15);
        economyPriceField = new JTextField(15);
        businessPriceField = new JTextField(15);
        firstClassPriceField = new JTextField(15);

        // Apply a consistent, modern style to all text fields
        styleTextField(flightCodeField);
        styleTextField(flightNameField);
        styleTextField(fromCityField);
        styleTextField(toCityField);
        styleTextField(timeField);
        styleTextField(durationField);
        styleTextField(dateField);
        styleTextField(economyPriceField);
        styleTextField(businessPriceField);
        styleTextField(firstClassPriceField);

        addFieldRow(fieldPanel, gbc, "Flight Code", flightCodeField);
        addFieldRow(fieldPanel, gbc, "Flight Name", flightNameField);
        addFieldRow(fieldPanel, gbc, "From City", fromCityField);
        addFieldRow(fieldPanel, gbc, "To City", toCityField);
        addFieldRow(fieldPanel, gbc, "Date (YYYY-MM-DD)", dateField);
        addFieldRow(fieldPanel, gbc, "Time (HH:MM)", timeField);
        addFieldRow(fieldPanel, gbc, "Duration", durationField);
        addFieldRow(fieldPanel, gbc, "Economy Price", economyPriceField);
        addFieldRow(fieldPanel, gbc, "Business Price", businessPriceField);
        addFieldRow(fieldPanel, gbc, "First Class Price", firstClassPriceField);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 5));
        buttonPanel.setBackground(Color.WHITE);

        cancelButton = createStyledButton("Cancel", new Color(220, 53, 69), Color.WHITE);
        addButton = createStyledButton("Add Flight", new Color(0, 102, 153), Color.WHITE);

        buttonPanel.add(cancelButton);
        buttonPanel.add(addButton);

        gbc.gridx = 1;
        gbc.gridy++;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(20, 0, 10, 0);
        fieldPanel.add(buttonPanel, gbc);

        cancelButton.addActionListener(e -> dispose());

        addButton.addActionListener(e -> {
            // Disable button and set wait cursor to prevent multiple clicks and provide feedback
            setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            addButton.setEnabled(false);
            cancelButton.setEnabled(false);

            String flightCode = flightCodeField.getText().trim();
            String flightName = flightNameField.getText().trim();
            String fromCity = fromCityField.getText().trim();
            String toCity = toCityField.getText().trim();
            String date = dateField.getText().trim();
            String time = timeField.getText().trim();
            String duration = durationField.getText().trim();
            String economyPriceStr = economyPriceField.getText().trim();
            String businessPriceStr = businessPriceField.getText().trim();
            String firstClassPriceStr = firstClassPriceField.getText().trim();

            if (flightCode.isEmpty() || flightName.isEmpty() || fromCity.isEmpty() || 
                toCity.isEmpty() || date.isEmpty() || time.isEmpty() || duration.isEmpty() ||
                economyPriceStr.isEmpty() || businessPriceStr.isEmpty() || firstClassPriceStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill all fields.", "Error", JOptionPane.ERROR_MESSAGE);
                setCursor(Cursor.getDefaultCursor());
                addButton.setEnabled(true);
                cancelButton.setEnabled(true);
                return;
            }

            int economyPrice, businessPrice, firstClassPrice;
            try {
                economyPrice = Integer.parseInt(economyPriceStr);
                businessPrice = Integer.parseInt(businessPriceStr);
                firstClassPrice = Integer.parseInt(firstClassPriceStr);

                if (economyPrice <= 0 || businessPrice <= 0 || firstClassPrice <= 0) {
                    JOptionPane.showMessageDialog(this, "Prices must be positive numbers.", "Error", JOptionPane.ERROR_MESSAGE);
                    setCursor(Cursor.getDefaultCursor());
                    addButton.setEnabled(true);
                    cancelButton.setEnabled(true);
                    return;
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Prices must be valid numbers.", "Error", JOptionPane.ERROR_MESSAGE);
                setCursor(Cursor.getDefaultCursor());
                addButton.setEnabled(true);
                cancelButton.setEnabled(true);
                return;
            }

            // Validate date format (YYYY-MM-DD)
            if (!isValidDate(date)) {
                JOptionPane.showMessageDialog(this, "Invalid date. Please use YYYY-MM-DD format.", "Error", JOptionPane.ERROR_MESSAGE);
                setCursor(Cursor.getDefaultCursor());
                addButton.setEnabled(true);
                cancelButton.setEnabled(true);
                return;
            }

            // Validate time format and value
            if (!isValidTime(time)) {
                JOptionPane.showMessageDialog(this, "Invalid time format or value. Please use HH:MM (e.g., 09:30).", "Error", JOptionPane.ERROR_MESSAGE);
                setCursor(Cursor.getDefaultCursor());
                addButton.setEnabled(true);
                cancelButton.setEnabled(true);
                return;
            }

            // Perform database operation in a background thread
            new SwingWorker<Boolean, Void>() {
                @Override
                protected Boolean doInBackground() throws Exception {
                    return addFlightToDatabase(flightCode, flightName, fromCity, toCity, date, time, duration, economyPrice, businessPrice, firstClassPrice);
                }

                @Override
                protected void done() {
                    try {
                        if (get()) {
                            JOptionPane.showMessageDialog(AddFlightDialog.this, "Flight added successfully!");
                            dispose();
                        } else {
                            JOptionPane.showMessageDialog(AddFlightDialog.this, "Error adding flight. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (java.util.concurrent.ExecutionException ex) {
                        // Get the actual exception that caused the failure
                        Throwable cause = ex.getCause();
                        if (cause instanceof SQLException) {
                            SQLException sqlEx = (SQLException) cause;
                            JOptionPane.showMessageDialog(AddFlightDialog.this, 
                                sqlEx.getMessage(), 
                                "Database Error", 
                                JOptionPane.ERROR_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(AddFlightDialog.this, 
                                "An unexpected error occurred: " + cause.getMessage(), 
                                "Error", 
                                JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(AddFlightDialog.this, "An unexpected error occurred.", "Error", JOptionPane.ERROR_MESSAGE);
                    } finally {
                        setCursor(Cursor.getDefaultCursor());
                        addButton.setEnabled(true);
                        cancelButton.setEnabled(true);
                    }
                }
            }.execute();
        });

        add(titleLabel, BorderLayout.NORTH);
        add(fieldPanel, BorderLayout.CENTER);
    }

    private void styleTextField(JTextField field) {
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setMargin(new Insets(5, 8, 5, 8)); // Add internal padding
        field.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220))); // Softer border
    }

    private boolean isValidDate(String date) {
        try {
            java.time.LocalDate.parse(date);
            return true;
        } catch (java.time.format.DateTimeParseException e) {
            return false;
        }
    }

    private boolean isValidTime(String time) {
        if (!time.matches("\\d{2}:\\d{2}")) {
            return false;
        }
        try {
            java.time.LocalTime.parse(time);
            return true;
        } catch (java.time.format.DateTimeParseException e) {
            return false;
        }
    }

    private void addFieldRow(JPanel panel, GridBagConstraints gbc, String labelText, Component field) {
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.weightx = 0.1; // Give some weight to the label column
        gbc.anchor = GridBagConstraints.LINE_END; // Right-align labels
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Segoe UI", Font.BOLD, 14));
        panel.add(label, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.9; // Give more weight to the field column
        gbc.anchor = GridBagConstraints.LINE_START; // Left-align fields
        panel.add(field, gbc);
    }

    private JButton createStyledButton(String text, Color bg, Color fg) {
        JButton button = new JButton(text);
        button.setBackground(bg);
        button.setForeground(fg);
        button.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        button.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(120, 32));
        return button;
    }

    private boolean addFlightToDatabase(String flightCode, String flightName, String fromCity, String toCity,
                                      String date, String time, String duration, int economyPrice, int businessPrice, int firstClassPrice) throws SQLException {
        String flightSql = "INSERT INTO flights (flight_code, flight_name, from_city, to_city, flight_date, flight_time, duration) " +
                           "VALUES (?, ?, ?, ?, ?, ?, ?)";
        String priceSql = "INSERT INTO seat_classes (flight_id, class_name, price) VALUES (?, ?, ?)";
        String seatSql = "INSERT INTO seats (flight_id, class_name, seat_no, is_booked) VALUES (?, ?, ?, ?)";

        Connection conn = null;
        PreparedStatement flightPst = null;
        PreparedStatement pricePst = null;
        PreparedStatement seatPst = null;
        ResultSet generatedKeys = null;
        int newFlightId = -1;

        try {
            conn = MySqlConnection.getConnection();
            if (conn == null) {
                System.out.println("Failed to get database connection.");
                throw new SQLException("Database connection failed");
            }

            // Start transaction
            conn.setAutoCommit(false);

            // 1. Insert the flight and get its generated ID
            flightPst = conn.prepareStatement(flightSql, Statement.RETURN_GENERATED_KEYS);
            flightPst.setString(1, flightCode);
            flightPst.setString(2, flightName);
            flightPst.setString(3, fromCity);
            flightPst.setString(4, toCity);
            flightPst.setString(5, date);
            flightPst.setString(6, time);
            flightPst.setString(7, duration);
            flightPst.executeUpdate();

            generatedKeys = flightPst.getGeneratedKeys();
            if (generatedKeys.next()) {
                newFlightId = generatedKeys.getInt(1);
            } else {
                throw new SQLException("Creating flight failed, no ID obtained.");
            }

            // 2. Insert the prices for the new flight
            pricePst = conn.prepareStatement(priceSql);
            // Economy Price
            pricePst.setInt(1, newFlightId);
            pricePst.setString(2, "Economy");
            pricePst.setInt(3, economyPrice);
            pricePst.addBatch();
            // Business Price
            pricePst.setInt(1, newFlightId);
            pricePst.setString(2, "Business");
            pricePst.setInt(3, businessPrice);
            pricePst.addBatch();
            // First Class Price
            pricePst.setInt(1, newFlightId);
            pricePst.setString(2, "First Class");
            pricePst.setInt(3, firstClassPrice);
            pricePst.addBatch();
            pricePst.executeBatch();

            // 3. Insert all seats for the new flight
            seatPst = conn.prepareStatement(seatSql);
            // 15 Economy seats (E1 to E15)
            for (int i = 1; i <= 15; i++) {
                seatPst.setInt(1, newFlightId);
                seatPst.setString(2, "Economy");
                seatPst.setString(3, "E" + i);
                seatPst.setBoolean(4, false);
                seatPst.addBatch();
            }
            // 6 Business seats (B1 to B6)
            for (int i = 1; i <= 6; i++) {
                seatPst.setInt(1, newFlightId);
                seatPst.setString(2, "Business");
                seatPst.setString(3, "B" + i);
                seatPst.setBoolean(4, false);
                seatPst.addBatch();
            }
            // 10 First Class seats (F1 to F10)
            for (int i = 1; i <= 10; i++) {
                seatPst.setInt(1, newFlightId);
                seatPst.setString(2, "First Class");
                seatPst.setString(3, "F" + i);
                seatPst.setBoolean(4, false);
                seatPst.addBatch();
            }
            seatPst.executeBatch();

            // If everything is successful, commit the transaction
            conn.commit();
            return true;

        } catch (SQLIntegrityConstraintViolationException e) {
            e.printStackTrace();
            // Check if it's a duplicate key error
            if (e.getMessage().contains("Duplicate entry") && e.getMessage().contains("flight_code")) {
                throw new SQLException("Flight code '" + flightCode + "' already exists. Please use a different flight code.");
            } else if (e.getMessage().contains("Duplicate entry")) {
                throw new SQLException("A duplicate entry was detected. Please check your input and try again.");
            } else {
                throw new SQLException("Database constraint violation: " + e.getMessage());
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // If anything fails, roll back the entire transaction
            if (conn != null) {
                try {
                    conn.rollback();
                    System.out.println("Transaction rolled back.");
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            throw e; // Re-throw the exception to be handled by the caller
        } finally {
            // Restore auto-commit and close all resources
            try {
                if (conn != null) {
                    conn.setAutoCommit(true);
                }
                if (generatedKeys != null) generatedKeys.close();
                if (flightPst != null) flightPst.close();
                if (pricePst != null) pricePst.close();
                if (seatPst != null) seatPst.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}