package wingsnepal.view;

import com.toedter.calendar.JDateChooser;
import wingsnepal.dao.ReservationDao;
import wingsnepal.model.ReservationModel;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Objects;

public class EditReservationDialog extends JDialog {

    private JTextField passengerNameField, emailField, phoneField, flightNumberField, fromField, toField, seatNumberField, amountField;
    private JDateChooser departureDateField;
    private JSpinner departureTimeSpinner, arrivalTimeSpinner;
    private JComboBox<String> classComboBox;
    private JComboBox<String> statusComboBox;
    private ReservationDao reservationDao;
    private ReservationModel reservation;
    private Runnable onSave;

    public EditReservationDialog(Frame owner, boolean modal, ReservationModel reservation, Runnable onSave) {
        super(owner, modal);
        this.reservation = reservation;
        this.onSave = onSave;
        this.reservationDao = new ReservationDao();
        initComponents();
        populateFields();
        setTitle("Edit Reservation - " + reservation.getReservationId());
        setLocationRelativeTo(owner);
    }

    private void initComponents() {
        // Form fields (similar to Add dialog, but with status)
        passengerNameField = new JTextField(20);
        emailField = new JTextField(20);
        phoneField = new JTextField(20);
        flightNumberField = new JTextField(20);
        fromField = new JTextField(20);
        toField = new JTextField(20);
        departureDateField = new JDateChooser();
        departureDateField.setDateFormatString("yyyy-MM-dd");
        departureTimeSpinner = new JSpinner(new SpinnerDateModel());
        ((JSpinner.DateEditor) departureTimeSpinner.getEditor()).getFormat().applyPattern("HH:mm");
        arrivalTimeSpinner = new JSpinner(new SpinnerDateModel());
        ((JSpinner.DateEditor) arrivalTimeSpinner.getEditor()).getFormat().applyPattern("HH:mm");
        seatNumberField = new JTextField(20);
        classComboBox = new JComboBox<>(new String[]{"Economy", "Business", "First Class"});
        statusComboBox = new JComboBox<>(new String[]{"Confirmed", "Pending", "Cancelled"});
        amountField = new JTextField(20);

        // Buttons
        JButton saveButton = new JButton("Save");
        saveButton.setPreferredSize(new Dimension(100, 36));
        saveButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        saveButton.setBackground(new Color(0, 102, 153)); // #006699
        saveButton.setForeground(Color.WHITE);
        saveButton.setFocusPainted(false);
        saveButton.setBorder(BorderFactory.createEmptyBorder());
        saveButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setPreferredSize(new Dimension(100, 36));
        cancelButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        cancelButton.setBackground(new Color(211, 47, 47)); // #D32F2F
        cancelButton.setForeground(Color.WHITE);
        cancelButton.setFocusPainted(false);
        cancelButton.setBorder(BorderFactory.createEmptyBorder());
        cancelButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Layout
        JPanel mainPanel = new JPanel(new GridLayout(0, 4, 10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        mainPanel.add(new JLabel("Passenger Name:")); mainPanel.add(passengerNameField);
        mainPanel.add(new JLabel("Email:")); mainPanel.add(emailField);
        mainPanel.add(new JLabel("Phone:")); mainPanel.add(phoneField);
        mainPanel.add(new JLabel("Flight Number:")); mainPanel.add(flightNumberField);
        mainPanel.add(new JLabel("From:")); mainPanel.add(fromField);
        mainPanel.add(new JLabel("To:")); mainPanel.add(toField);
        mainPanel.add(new JLabel("Departure Date:")); mainPanel.add(departureDateField);
        mainPanel.add(new JLabel("Departure Time:")); mainPanel.add(departureTimeSpinner);
        mainPanel.add(new JLabel("Arrival Time:")); mainPanel.add(arrivalTimeSpinner);
        mainPanel.add(new JLabel("Seat Number:")); mainPanel.add(seatNumberField);
        mainPanel.add(new JLabel("Class:")); mainPanel.add(classComboBox);
        mainPanel.add(new JLabel("Status:")); mainPanel.add(statusComboBox);
        mainPanel.add(new JLabel("Total Amount (NPR):")); mainPanel.add(amountField);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 30, 15));
        buttonPanel.add(cancelButton);
        buttonPanel.add(saveButton);

        setLayout(new BorderLayout());
        add(mainPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        pack();
    }

    private void populateFields() {
        passengerNameField.setText(reservation.getPassengerName());
        emailField.setText(reservation.getPassengerEmail());
        phoneField.setText(reservation.getPassengerPhone());
        flightNumberField.setText(reservation.getFlightNumber());
        fromField.setText(reservation.getRouteFrom());
        toField.setText(reservation.getRouteTo());
        if (reservation.getDepartureDateTime() != null) {
            departureDateField.setDate(Date.from(reservation.getDepartureDateTime().atZone(ZoneId.systemDefault()).toInstant()));
            departureTimeSpinner.setValue(Date.from(reservation.getDepartureDateTime().atZone(ZoneId.systemDefault()).toInstant()));
        }
        if (reservation.getArrivalDateTime() != null) {
            arrivalTimeSpinner.setValue(Date.from(reservation.getArrivalDateTime().atZone(ZoneId.systemDefault()).toInstant()));
        }
        seatNumberField.setText(reservation.getSeatNumber());
        classComboBox.setSelectedItem(reservation.getSeatClass());
        statusComboBox.setSelectedItem(reservation.getStatus());
        amountField.setText(reservation.getAmount().toString());
    }

    private void saveChanges() {
        // Update the reservation object with new values from the fields
        reservation.setPassengerName(passengerNameField.getText().trim());
        reservation.setPassengerEmail(emailField.getText().trim());
        reservation.setPassengerPhone(phoneField.getText().trim());
        reservation.setFlightNumber(flightNumberField.getText().trim());
        reservation.setRouteFrom(fromField.getText().trim());
        reservation.setRouteTo(toField.getText().trim());

        // Handle date and time updates
        Date departureDate = departureDateField.getDate();
        if (departureDate != null) {
            LocalDateTime newDeparture = LocalDateTime.of(
                departureDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
                ((Date) departureTimeSpinner.getValue()).toInstant().atZone(ZoneId.systemDefault()).toLocalTime()
            );
            reservation.setDepartureDateTime(newDeparture);
        }
        
        // Similar logic for arrival time
        if (departureDate != null && arrivalTimeSpinner.getValue() != null) {
             LocalDateTime newArrival = LocalDateTime.of(
                departureDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), // Assuming same day or handled by user
                ((Date) arrivalTimeSpinner.getValue()).toInstant().atZone(ZoneId.systemDefault()).toLocalTime()
            );
            // Handle overnight logic if needed
            if (newArrival.isBefore(reservation.getDepartureDateTime())) {
                 newArrival = newArrival.plusDays(1);
            }
            reservation.setArrivalDateTime(newArrival);
        }

        reservation.setSeatNumber(seatNumberField.getText().trim());
        reservation.setSeatClass((String) classComboBox.getSelectedItem());
        reservation.setStatus((String) statusComboBox.getSelectedItem());
        reservation.setAmount(new BigDecimal(amountField.getText().trim()));

        // Call DAO to update the database
        boolean success = reservationDao.updateReservation(reservation);
        if (success) {
            JOptionPane.showMessageDialog(this, "Reservation updated successfully!");
            onSave.run(); // Refresh the main table
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Failed to update reservation.", "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }
} 