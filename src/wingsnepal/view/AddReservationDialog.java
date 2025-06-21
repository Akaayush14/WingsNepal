package wingsnepal.view;

import javax.swing.*;
import java.awt.*;

public class AddReservationDialog extends JDialog {

    private JTextField nameField, emailField, phoneField;
    private JTextField flightNumberField, fromField, toField;
    private JTextField departureTimeField, arrivalTimeField, seatNumberField, totalAmountField;
    private JComboBox<String> classComboBox;
    private JFormattedTextField departureDateField;
    private JButton saveButton, cancelButton;

    public AddReservationDialog(JFrame parent) {
        super(parent, "Add New Reservation", true);
        setSize(500, 500);
        setLocationRelativeTo(parent);
        setLayout(new GridLayout(10, 2, 10, 10));

        nameField = new JTextField();
        emailField = new JTextField();
        phoneField = new JTextField();
        flightNumberField = new JTextField();
        fromField = new JTextField();
        toField = new JTextField();
        departureDateField = new JFormattedTextField("dd/MM/yyyy");
        departureTimeField = new JTextField();
        arrivalTimeField = new JTextField();
        seatNumberField = new JTextField();
        totalAmountField = new JTextField();

        classComboBox = new JComboBox<>(new String[]{"Economy", "Business", "First"});
        classComboBox.setSelectedIndex(0);

        saveButton = new JButton("Create Reservation");
        cancelButton = new JButton("Cancel");

        add(new JLabel("Passenger Name:")); add(nameField);
        add(new JLabel("Email:")); add(emailField);
        add(new JLabel("Phone:")); add(phoneField);
        add(new JLabel("Flight Number:")); add(flightNumberField);
        add(new JLabel("From:")); add(fromField);
        add(new JLabel("To:")); add(toField);
        add(new JLabel("Departure Date:")); add(departureDateField);
        add(new JLabel("Departure Time:")); add(departureTimeField);
        add(new JLabel("Arrival Time:")); add(arrivalTimeField);
        add(new JLabel("Seat Number:")); add(seatNumberField);
        add(new JLabel("Class:")); add(classComboBox);
        add(new JLabel("Total Amount (NPR):")); add(totalAmountField);
        add(saveButton); add(cancelButton);

        saveButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Reservation for '" + nameField.getText() + "' created!");
            dispose();
        });

        cancelButton.addActionListener(e -> dispose());
    }
}
