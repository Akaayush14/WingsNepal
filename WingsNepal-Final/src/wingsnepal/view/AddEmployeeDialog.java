package wingsnepal.view;

import javax.swing.*;
import java.awt.*;
import wingsnepal.dao.UserDao;

public class AddEmployeeDialog extends JDialog {

    private JTextField fullNameField, emailField, phoneField, jobTitleField, salaryField;
    private JComboBox<String> genderComboBox, statusComboBox;
    private JPasswordField passwordField, confirmPasswordField;
    private JButton addButton, cancelButton;

    public AddEmployeeDialog(JFrame parent) {
        super(parent, "Add New Employee", true);
        setSize(520, 540);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel("Add New Employee");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        JPanel fieldPanel = new JPanel(new GridBagLayout());
        fieldPanel.setBackground(Color.WHITE);
        fieldPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 5, 8, 20);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.ipadx = 20;

        // Fields
        fullNameField = new JTextField(20);
        emailField = new JTextField(20);
        phoneField = new JTextField(20);
        genderComboBox = new JComboBox<>(new String[]{"Male", "Female"});
        jobTitleField = new JTextField(20);
        salaryField = new JTextField(20);
        statusComboBox = new JComboBox<>(new String[]{"Active", "Inactive"});
        passwordField = new JPasswordField(20);
        confirmPasswordField = new JPasswordField(20);

        addFieldRow(fieldPanel, gbc, "Full Name:", fullNameField);
        addFieldRow(fieldPanel, gbc, "Email:", emailField);
        addFieldRow(fieldPanel, gbc, "Phone:", phoneField);
        addFieldRow(fieldPanel, gbc, "Gender:", genderComboBox);
        addFieldRow(fieldPanel, gbc, "Job Title:", jobTitleField);
        addFieldRow(fieldPanel, gbc, "Salary:", salaryField);
        addFieldRow(fieldPanel, gbc, "Status:", statusComboBox);
        addFieldRow(fieldPanel, gbc, "Password:", passwordField);
        addFieldRow(fieldPanel, gbc, "Confirm Password:", confirmPasswordField);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 0));
        buttonPanel.setBackground(Color.WHITE);
        cancelButton = new JButton("Cancel");
        addButton = new JButton("Add Employee");

        styleButton(cancelButton, false);
        styleButton(addButton, true);
        addButton.setBackground(new Color(0, 102, 153));
        addButton.setForeground(Color.WHITE);

        // Style Cancel button as red (danger)
        cancelButton.setBackground(new Color(220, 53, 69)); // Bootstrap danger red
        cancelButton.setForeground(Color.WHITE);
        cancelButton.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
        addButton.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
        // Reduce button size and center them
        cancelButton.setPreferredSize(new Dimension(120, 38));
        addButton.setPreferredSize(new Dimension(170, 38));
        // Ensure button panel has enough height and spacing
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        buttonPanel.add(cancelButton);
        buttonPanel.add(addButton);

        cancelButton.addActionListener(e -> dispose());

        addButton.addActionListener(e -> {
            String fullName = fullNameField.getText().trim();
            String email = emailField.getText().trim();
            String phone = phoneField.getText().trim();
            String gender = genderComboBox.getSelectedItem().toString();
            String jobTitle = jobTitleField.getText().trim();
            String salaryStr = salaryField.getText().trim();
            Double salary = null;
            if (!salaryStr.isEmpty()) {
                try { salary = Double.parseDouble(salaryStr); } catch (NumberFormatException ex) { salary = null; }
            }
            String status = statusComboBox.getSelectedItem().toString();
            String password = new String(passwordField.getPassword());
            String confirmPassword = new String(confirmPasswordField.getPassword());

            // --- Validation ---
            if (fullName.isEmpty() || email.isEmpty() || phone.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill all fields.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
                JOptionPane.showMessageDialog(this, "Please enter a valid email address.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (password.length() < 6) {
                JOptionPane.showMessageDialog(this, "Password must be at least 6 characters long.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!password.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(this, "Passwords do not match.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // --- Database Logic ---
            UserDao userDao = new UserDao();
            if (userDao.findUserByEmail(email) != null) {
                JOptionPane.showMessageDialog(this, "An account with this email already exists.", "Registration Failed", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Register employee (role = "Employee")
            boolean success = userDao.registerEmployee(fullName, email, phone, gender, password, jobTitle, salary, status);

            if (success) {
                JOptionPane.showMessageDialog(this, "Employee added successfully!");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to add employee due to a database error.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        add(titleLabel, BorderLayout.NORTH);
        add(fieldPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void addFieldRow(JPanel panel, GridBagConstraints gbc, String labelText, Component field) {
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.LINE_END;
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Segoe UI", Font.BOLD, 14));
        panel.add(label, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        panel.add(field, gbc);
    }

    private void styleButton(JButton button, boolean isPrimary) {
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(140, 35));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        if (isPrimary) {
            button.setBackground(new Color(0, 123, 255));
            button.setForeground(Color.WHITE);
        } else {
            button.setBackground(new Color(240, 240, 240));
            button.setForeground(Color.BLACK);
        }
    }
}
