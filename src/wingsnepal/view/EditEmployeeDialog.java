package wingsnepal.view;

import javax.swing.*;
import java.awt.*;
import wingsnepal.dao.EmployeeDao;
import wingsnepal.model.Employee;

public class EditEmployeeDialog extends JDialog {

    private JTextField fullNameField, emailField;
    private JComboBox<String> roleCombo, genderCombo;
    private JPasswordField passwordField;
    private JButton updateButton, cancelButton;

    private final int empId;
    private final AdminDashboard dashboard;

    public EditEmployeeDialog(AdminDashboard parent, int empId) {
        super(parent, "Edit Employee", true);
        this.empId = empId;
        this.dashboard = parent;

        setSize(450, 400);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel("Edit Employee");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 0));

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 20, 5, 20);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        fullNameField = new JTextField();
        emailField = new JTextField();
        roleCombo = new JComboBox<>(new String[]{"Pilot", "Flight Attendant", "IT Support", "AMEs", "Ground Operation Manager", "HR Manager"});
        genderCombo = new JComboBox<>(new String[]{"Male", "Female"});
        passwordField = new JPasswordField();

        // Set preferred sizes for text fields, similar to AddEmployeeDialog
        fullNameField.setPreferredSize(new Dimension(200, 28));
        emailField.setPreferredSize(new Dimension(200, 28));
        passwordField.setPreferredSize(new Dimension(200, 28));
        roleCombo.setPreferredSize(new Dimension(200, 28));
        genderCombo.setPreferredSize(new Dimension(200, 28));

        // Set border for all fields, similar to AddEmployeeDialog
        fullNameField.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        emailField.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        passwordField.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        roleCombo.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        genderCombo.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        addField(formPanel, gbc, "Full Name", fullNameField);
        addField(formPanel, gbc, "Email", emailField);
        addField(formPanel, gbc, "Role", roleCombo);
        addField(formPanel, gbc, "Gender", genderCombo);
        addField(formPanel, gbc, "Password", passwordField);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(Color.WHITE);
        cancelButton = new JButton("Cancel");
        updateButton = new JButton("Update");

        cancelButton.addActionListener(e -> dispose());
        updateButton.addActionListener(e -> updateEmployee());

        buttonPanel.add(cancelButton);
        buttonPanel.add(updateButton);

        add(titleLabel, BorderLayout.NORTH);
        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        populateFields();
    }

    private void addField(JPanel panel, GridBagConstraints gbc, String label, Component field) {
        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel(label), gbc);
        gbc.gridx = 1;
        panel.add(field, gbc);
    }

    private void populateFields() {
        EmployeeDao dao = new EmployeeDao();
        Employee emp = dao.getEmployeeById(empId);

        if (emp != null) {
            fullNameField.setText(emp.getFullName());
            emailField.setText(emp.getEmpEmail());
            roleCombo.setSelectedItem(emp.getEmpRole());
            genderCombo.setSelectedItem(emp.getGender());
            passwordField.setText(emp.getPassword());
        }
    }

    private void updateEmployee() {
        String fullName = fullNameField.getText().trim();
        String email = emailField.getText().trim();
        String role = (String) roleCombo.getSelectedItem();
        String gender = (String) genderCombo.getSelectedItem();
        String password = new String(passwordField.getPassword()).trim();

        if (fullName.isEmpty() || email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields must be filled.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Employee updated = new Employee(empId, fullName, email, role, gender, password);
        new EmployeeDao().updateEmployee(updated);

        JOptionPane.showMessageDialog(this, "Employee updated successfully!");
        dashboard.loadEmployeeTable();  // Refresh the table
        dispose();
    }
}
