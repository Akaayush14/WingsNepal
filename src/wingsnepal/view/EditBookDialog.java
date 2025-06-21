/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package wingsnepal.view;

import wingsnepal.model.ManageBookingModel;
import wingsnepal.dao.ManageBookingDao;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 *
 * @author Aayush Kharel
 */

public class EditBookDialog extends JDialog {
    private int bookingId;
    private UserPortal userPortal;
    private JTextField seatNoTextField;
    private JComboBox<String> seatClassComboBox;

    public EditBookDialog(UserPortal userPortal, int bookingId) {
        this.userPortal = userPortal;
        this.bookingId = bookingId;
        initComponents();
        loadBookingDetails();
        setTitle("Edit Booking");
        setModal(true);  // Makes the dialog modal (blocks interaction with other windows)
        setLocationRelativeTo(userPortal);  // Centers the dialog on the UserPortal
    }

    private void initComponents() {
        setLayout(new FlowLayout());

        JLabel seatClassLabel = new JLabel("Seat Class:");
        seatClassComboBox = new JComboBox<>(new String[] {"Economy", "Business", "First Class"});
        seatClassComboBox.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        JLabel seatNoLabel = new JLabel("Seat Number:");
        seatNoTextField = new JTextField(10);
        seatNoTextField.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        JButton saveButton = new JButton("Save");
        saveButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        saveButton.setBackground(new Color(0, 102, 153));
        saveButton.setForeground(Color.WHITE);
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveBookingDetails();
            }
        });

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        cancelButton.setBackground(new Color(255, 102, 102));
        cancelButton.setForeground(Color.WHITE);
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        // Add components to dialog
        add(seatClassLabel);
        add(seatClassComboBox);
        add(seatNoLabel);
        add(seatNoTextField);
        add(saveButton);
        add(cancelButton);

        setSize(300, 200);  // Set the size of the dialog
    }

    // Load the booking details into the dialog fields
    private void loadBookingDetails() {
        ManageBookingDao manageBookingDao = new ManageBookingDao();
        ManageBookingModel booking = manageBookingDao.getBookingDetailsById(bookingId); // Fetch booking by ID

        if (booking != null) {
            // Pre-fill the form with booking details
            seatClassComboBox.setSelectedItem(booking.getSeatClass());
            seatNoTextField.setText(booking.getSeatNo());
        }
    }

    // Save the edited booking details
    private void saveBookingDetails() {
        String seatClass = (String) seatClassComboBox.getSelectedItem();
        String seatNo = seatNoTextField.getText();

        if (seatNo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a seat number.");
            return;
        }

        ManageBookingDao manageBookingDao = new ManageBookingDao();
        boolean success = manageBookingDao.updateBooking(bookingId, seatNo, seatClass);

        if (success) {
            JOptionPane.showMessageDialog(this, "Booking updated successfully!");
            userPortal.showBookings(userPortal.getLoggedInUser().getUserId()); // Refresh bookings in UserPortal
            dispose();  // Close the dialog
        } else {
            JOptionPane.showMessageDialog(this, "Failed to update booking.");
        }
    }
    
}

