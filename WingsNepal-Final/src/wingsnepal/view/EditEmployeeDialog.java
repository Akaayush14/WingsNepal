package wingsnepal.view;

import javax.swing.*;
import java.awt.*;
import wingsnepal.dao.UserDao;
import wingsnepal.model.UserDataModel;

public class EditEmployeeDialog extends JDialog {

    private JTextField fullNameField, emailField, jobTitleField, salaryField;
    private JComboBox<String> roleCombo, genderCombo, statusComboBox;
    private JPasswordField passwordField;
    private JButton updateButton, cancelButton;

    private final int empId;
    private final AdminDashboard dashboard;
    private UserDao userDao = new UserDao();

    public EditEmployeeDialog(AdminDashboard parent, int empId) {
        super(parent, "Edit Employee", true);
        this.empId = empId;
        this.dashboard = parent;

        setSize(520, 540);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel("Edit Employee");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 5, 8, 20);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.ipadx = 20;

        fullNameField = new JTextField();
        emailField = new JTextField();
        roleCombo = new JComboBox<>(new String[]{"Pilot", "Flight Attendant", "IT Support", "AMEs", "Ground Operation Manager", "HR Manager"});
        genderCombo = new JComboBox<>(new String[]{"Male", "Female"});
        passwordField = new JPasswordField();
        jobTitleField = new JTextField();
        salaryField = new JTextField();
        statusComboBox = new JComboBox<>(new String[]{"Active", "Inactive"});

        // Set preferred sizes for text fields, similar to AddEmployeeDialog
        fullNameField.setPreferredSize(new Dimension(200, 28));
        emailField.setPreferredSize(new Dimension(200, 28));
        passwordField.setPreferredSize(new Dimension(200, 28));
        roleCombo.setPreferredSize(new Dimension(200, 28));
        genderCombo.setPreferredSize(new Dimension(200, 28));
        jobTitleField.setPreferredSize(new Dimension(200, 28));
        salaryField.setPreferredSize(new Dimension(200, 28));
        statusComboBox.setPreferredSize(new Dimension(200, 28));

        // Set border for all fields, similar to AddEmployeeDialog
        fullNameField.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        emailField.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        passwordField.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        roleCombo.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        genderCombo.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        jobTitleField.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        salaryField.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        statusComboBox.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        // Add helpful tooltip for password field
        passwordField.setToolTipText("Current password shown as asterisks. Clear this field and enter a new password to change it, or leave unchanged to keep current password.");

        addField(formPanel, gbc, "Full Name:", fullNameField);
        addField(formPanel, gbc, "Email:", emailField);
        addField(formPanel, gbc, "Role:", roleCombo);
        addField(formPanel, gbc, "Gender:", genderCombo);
        addField(formPanel, gbc, "Password:", passwordField);
        addField(formPanel, gbc, "Job Title:", jobTitleField);
        addField(formPanel, gbc, "Salary:", salaryField);
        addField(formPanel, gbc, "Status:", statusComboBox);

        // Modern button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 0));
        buttonPanel.setBackground(Color.WHITE);
        cancelButton = new JButton("Cancel");
        updateButton = new JButton("Update");
        cancelButton.setBackground(new Color(220, 53, 69));
        cancelButton.setForeground(Color.WHITE);
        cancelButton.setPreferredSize(new Dimension(120, 38));
        cancelButton.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
        updateButton.setBackground(new Color(0, 102, 153));
        updateButton.setForeground(Color.WHITE);
        updateButton.setPreferredSize(new Dimension(170, 38));
        updateButton.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        buttonPanel.add(cancelButton);
        buttonPanel.add(updateButton);

        cancelButton.addActionListener(e -> dispose());
        updateButton.addActionListener(e -> updateEmployee());

        add(titleLabel, BorderLayout.NORTH);
        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        populateFields();
    }

    private void addField(JPanel panel, GridBagConstraints gbc, String label, Component field) {
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.LINE_END;
        JLabel jlabel = new JLabel(label);
        jlabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        panel.add(jlabel, gbc);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        panel.add(field, gbc);
    }

    private void populateFields() {
        UserDataModel emp = userDao.findUserById(empId);
        if (emp != null) {
            fullNameField.setText(emp.getFullName());
            emailField.setText(emp.getEmail());
            roleCombo.setSelectedItem(emp.getJobTitle()); // Using job title as the role selection
            genderCombo.setSelectedItem(emp.getGender());
            // Show password placeholder (asterisks) to indicate password exists
            // Admin can clear this field and enter a new password to change it
            passwordField.setText(userDao.getPasswordPlaceholderById(empId));
            jobTitleField.setText(emp.getJobTitle());
            salaryField.setText(emp.getSalary() != null ? emp.getSalary().toString() : "");
            statusComboBox.setSelectedItem(emp.getStatus());
        }
    }

    private void updateEmployee() {
        String fullName = fullNameField.getText().trim();
        String email = emailField.getText().trim();
        String jobTitle = jobTitleField.getText().trim();
        String roleSelection = (String) roleCombo.getSelectedItem();
        String role = "Employee"; // Always Employee role
        String gender = (String) genderCombo.getSelectedItem();
        String password = new String(passwordField.getPassword()).trim();
        String salaryStr = salaryField.getText().trim();
        Double salary = null;
        if (!salaryStr.isEmpty()) {
            try { salary = Double.parseDouble(salaryStr); } catch (NumberFormatException ex) { salary = null; }
        }
        String status = statusComboBox.getSelectedItem().toString();

        if (fullName.isEmpty() || email.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Full Name and Email must be filled.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Smart password handling: only update if user changed the placeholder
        String passwordToUpdate = null;
        if (!password.equals("********") && !password.isEmpty()) {
            // User entered a new password (not the placeholder)
            passwordToUpdate = password;
        }
        // If password is still "********" or empty, don't update it (keep existing password)

        // Update employee in users table
        boolean success = userDao.updateEmployee(empId, fullName, email, role, gender, passwordToUpdate, jobTitle, salary, status);
        if (success) {
            String message = "Employee updated successfully!";
            if (passwordToUpdate != null) {
                message += "\nPassword has been changed.";
            }
            JOptionPane.showMessageDialog(this, message);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Failed to update employee.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
