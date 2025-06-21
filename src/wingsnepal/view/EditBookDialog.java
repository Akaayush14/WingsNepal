package wingsnepal.view;

import wingsnepal.model.ManageBookingModel;
import wingsnepal.dao.ManageBookingDao;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
        // Use GridBagLayout for better control over component positioning
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);  // Padding around components

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

        // Set grid position for each component using GridBagConstraints
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(seatClassLabel, gbc);

        gbc.gridx = 1;
        add(seatClassComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        add(seatNoLabel, gbc);

        gbc.gridx = 1;
        add(seatNoTextField, gbc);

        // Add Save and Cancel buttons in a row at the bottom
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;  // Span across 2 columns
        gbc.anchor = GridBagConstraints.CENTER;
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 0));  // Add some spacing between buttons
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        add(buttonPanel, gbc);

        setSize(400, 250);  // Adjusted dialog size
        setResizable(false);  // Prevent resizing of the dialog
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
