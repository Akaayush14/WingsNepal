package wingsnepal.view;

import javax.swing.*;
import java.awt.*;
import wingsnepal.dao.UserDao;
import wingsnepal.dao.PassengerDao;
import wingsnepal.dao.ManageBookingDao;
import wingsnepal.dao.ReservationDao;
import wingsnepal.model.UserDataModel;

public class EditUserDialog extends JDialog {

    private JTextField fullNameField, emailField, phoneField;
    private JComboBox<String> roleCombo, genderCombo, statusComboBox;
    private JPasswordField passwordField;
    private JButton updateButton, cancelButton;

    private final int userId;
    private final AdminDashboard dashboard;
    private UserDao userDao = new UserDao();
    private PassengerDao passengerDao = new PassengerDao();
    private ManageBookingDao manageBookingDao = new ManageBookingDao();
    private ReservationDao reservationDao = new ReservationDao();

    public EditUserDialog(AdminDashboard parent, int userId) {
        super(parent, "Edit User", true);
        this.userId = userId;
        this.dashboard = parent;

        setSize(650, 680); // Further increased height to display all 7 fields properly
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE);

        // Enhanced title with better styling
        JLabel titleLabel = new JLabel("Edit User");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 26));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setForeground(new Color(0, 102, 153));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(25, 20, 20, 20));

        // Improved form panel with better spacing
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 10, 12, 25); // Increased spacing between fields
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Initialize form fields
        fullNameField = new JTextField();
        emailField = new JTextField();
        phoneField = new JTextField();
        roleCombo = new JComboBox<>(new String[]{"User"}); // Only User role for regular users
        genderCombo = new JComboBox<>(new String[]{"Male", "Female"});
        passwordField = new JPasswordField();
        statusComboBox = new JComboBox<>(new String[]{"Active", "Inactive"});

        // Set consistent sizes - reduced height for better proportion between text fields and combo boxes
        Dimension fieldSize = new Dimension(350, 35); // Reduced height for better consistency
        fullNameField.setPreferredSize(fieldSize);
        emailField.setPreferredSize(fieldSize);
        phoneField.setPreferredSize(fieldSize);
        passwordField.setPreferredSize(fieldSize);
        roleCombo.setPreferredSize(fieldSize);
        genderCombo.setPreferredSize(fieldSize);
        statusComboBox.setPreferredSize(fieldSize);

        // Enhanced styling for all text fields
        styleTextField(fullNameField);
        styleTextField(emailField);
        styleTextField(phoneField);
        styleTextField(passwordField);
        
        // Enhanced styling for all combo boxes
        styleComboBox(roleCombo);
        styleComboBox(genderCombo);
        styleComboBox(statusComboBox);

        addField(formPanel, gbc, "Full Name:", fullNameField);
        addField(formPanel, gbc, "Email:", emailField);
        addField(formPanel, gbc, "Phone:", phoneField);
        addField(formPanel, gbc, "Role:", roleCombo);
        addField(formPanel, gbc, "Gender:", genderCombo);
        addField(formPanel, gbc, "Password:", passwordField);
        addField(formPanel, gbc, "Status:", statusComboBox);

        // Add helpful tooltip for password field
        passwordField.setToolTipText("Current password shown as asterisks. Clear this field and enter a new password to change it, or leave unchanged to keep current password.");

        // Enhanced button panel with better spacing
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(30, 0, 30, 0));
        
        // Create buttons with enhanced styling
        cancelButton = new JButton("Cancel");
        updateButton = new JButton("Save Changes");
        
        // Enhanced button styling similar to reference image
        styleButton(cancelButton, new Color(220, 53, 69), new Dimension(130, 45));
        styleButton(updateButton, new Color(0, 102, 153), new Dimension(160, 45));
        
        buttonPanel.add(cancelButton);
        buttonPanel.add(updateButton);

        cancelButton.addActionListener(e -> dispose());
        updateButton.addActionListener(e -> updateUser());

        add(titleLabel, BorderLayout.NORTH);
        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        populateFields();
    }

    private void addField(JPanel panel, GridBagConstraints gbc, String label, Component field) {
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.LINE_END;
        gbc.weightx = 0.0;
        
        // Enhanced label styling to match reference image
        JLabel jlabel = new JLabel(label);
        jlabel.setFont(new Font("Segoe UI", Font.BOLD, 16)); // Increased font size
        jlabel.setForeground(new Color(60, 60, 60)); // Darker gray for better readability
        panel.add(jlabel, gbc);
        
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.weightx = 1.0;
        panel.add(field, gbc);
    }

    // Enhanced text field styling method
    private void styleTextField(JTextField textField) {
        textField.setFont(new Font("Segoe UI", Font.PLAIN, 16)); // Increased font size for better readability
        textField.setBackground(Color.WHITE);
        textField.setForeground(new Color(60, 60, 60));
        textField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 2), // Light gray border
            BorderFactory.createEmptyBorder(7, 12, 7, 12) // Balanced padding for proper vertical centering
        ));
        // Set horizontal alignment to ensure text starts from left and is vertically centered
        textField.setHorizontalAlignment(JTextField.LEFT);
        textField.setMargin(new Insets(0, 0, 0, 0)); // Remove margin to let padding handle spacing
    }

    // Enhanced combo box styling method  
    private void styleComboBox(JComboBox<String> comboBox) {
        comboBox.setFont(new Font("Segoe UI", Font.PLAIN, 16)); // Increased font size
        comboBox.setBackground(Color.WHITE);
        comboBox.setForeground(new Color(60, 60, 60));
        comboBox.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 2)); // Light gray border
        
        // Style the dropdown arrow button
        for (Component comp : comboBox.getComponents()) {
            if (comp instanceof JButton) {
                comp.setBackground(new Color(240, 240, 240));
            }
        }
    }

    // Enhanced button styling method
    private void styleButton(JButton button, Color backgroundColor, Dimension size) {
        button.setFont(new Font("Segoe UI", Font.BOLD, 16));
        button.setBackground(backgroundColor);
        button.setForeground(Color.WHITE);
        button.setPreferredSize(size);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(12, 20, 12, 20));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Add subtle shadow effect
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createRaisedBevelBorder(),
            BorderFactory.createEmptyBorder(8, 15, 8, 15)
        ));
        
        // Add hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            Color originalColor = backgroundColor;
            
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(originalColor.brighter());
            }
            
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(originalColor);
            }
        });
    }

    private void populateFields() {
        UserDataModel user = userDao.findUserById(userId);
        if (user != null) {
            fullNameField.setText(user.getFullName());
            emailField.setText(user.getEmail());
            phoneField.setText(user.getPhone() != null ? user.getPhone() : "");
            roleCombo.setSelectedItem(user.getRole());
            genderCombo.setSelectedItem(user.getGender());
            // Show password placeholder (asterisks) to indicate password exists
            // Admin can clear this field and enter a new password to change it
            passwordField.setText(userDao.getPasswordPlaceholderById(userId));
            statusComboBox.setSelectedItem(user.getStatus());
        }
    }

    private void updateUser() {
        String fullName = fullNameField.getText().trim();
        String email = emailField.getText().trim();
        String phone = phoneField.getText().trim();
        String role = (String) roleCombo.getSelectedItem();
        String gender = (String) genderCombo.getSelectedItem();
        String password = new String(passwordField.getPassword()).trim();
        String status = statusComboBox.getSelectedItem().toString();

        if (fullName.isEmpty() || email.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Full Name and Email must be filled.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // STEP 1: Get the old user data before updating (needed for reservation updates)
        UserDataModel oldUser = userDao.findUserById(userId);
        if (oldUser == null) {
            JOptionPane.showMessageDialog(this, "User not found.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String oldEmail = oldUser.getEmail();

        try {
            // Handle password field: if it's still the placeholder, don't update password
            String passwordToUpdate = password;
            if ("********".equals(password)) {
                passwordToUpdate = null; // Don't update password if admin didn't change the placeholder
            }
            
            // STEP 2: Update user in users table (primary update)
            boolean userUpdateSuccess = userDao.updateUser(userId, fullName, email, phone, role, gender, passwordToUpdate, null, null, status);
            
            if (!userUpdateSuccess) {
                JOptionPane.showMessageDialog(this, "Failed to update user.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // STEP 3: Update all passenger records for this user (ensures passenger table consistency)
            boolean passengerUpdateSuccess = passengerDao.updateAllPassengerRecordsForUser(userId, fullName, email);
            if (!passengerUpdateSuccess) {
                System.err.println("Warning: Failed to update passenger records for user ID: " + userId);
            }

            // STEP 4: Update all booking records for this user (ensures booking table consistency)
            boolean bookingUpdateSuccess = manageBookingDao.updateAllBookingRecordsForUser(userId, fullName, email);
            if (!bookingUpdateSuccess) {
                System.err.println("Warning: Failed to update booking records for user ID: " + userId);
            }

            // STEP 5: Update all reservation records for this user (ensures reservation table consistency)
            // Handle case where reservations table might not exist
            boolean reservationUpdateSuccess = reservationDao.updateAllReservationRecordsForUser(oldEmail, fullName, email, phone);
            if (!reservationUpdateSuccess) {
                System.err.println("Warning: Failed to update reservation records for user email: " + oldEmail);
            }

            // STEP 6: Log status change for debugging
            if (!oldUser.getStatus().equals(status)) {
                System.out.println("User status changed from '" + oldUser.getStatus() + "' to '" + status + "' - tables will be refreshed by controller");
            }
            
            // STEP 7: Show success message 
            String updateMessage = "User updated successfully!";
            if (!passengerUpdateSuccess || !bookingUpdateSuccess || !reservationUpdateSuccess) {
                updateMessage += "\n\nNote: Some related records may need manual verification.";
            }
            
            // Add note about passenger table refresh for status changes
            if (!oldUser.getStatus().equals(status)) {
                updateMessage += "\n\nUser status changed - passenger bookings have been updated accordingly.";
            }
            
            JOptionPane.showMessageDialog(this, updateMessage, "Success", JOptionPane.INFORMATION_MESSAGE);
            
            // Note: The users table will automatically refresh when the user navigates back
            dispose();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, 
                "An error occurred while updating user data. Please try again.", 
                "System Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
} 