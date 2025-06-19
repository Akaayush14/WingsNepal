package wingsnepal.view;

import javax.swing.*;
import java.awt.*;
import wingsnepal.dao.EmployeeDao;
import wingsnepal.model.Employee;
import java.util.Random;
import java.util.List;

public class AddEmployeeDialog extends JDialog {

    private JTextField empIdField, fullNameField, emailField;
    private JComboBox<String> roleBox, genderCombo;
    private JPasswordField passwordField, confirmPasswordField;
    private JButton addButton, cancelButton;

    public AddEmployeeDialog(JFrame parent) {
        super(parent, "Add New Employee", true);
        setSize(500, 520);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel("Add New Employee");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 0));

        JPanel fieldPanel = new JPanel(new GridBagLayout());
        fieldPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 20, 5, 20);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Fields
        empIdField = new JTextField();
        empIdField.setEditable(false);
        fullNameField = new JTextField();
        emailField = new JTextField();
        roleBox = new JComboBox<>(new String[]{"Select Role", "Admin", "Staff", "Support"});
        genderCombo = new JComboBox<>(new String[]{"Select Gender", "Male", "Female"});
        passwordField = new JPasswordField();
        confirmPasswordField = new JPasswordField();

        // Set borders for all fields
        empIdField.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        fullNameField.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        emailField.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        passwordField.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        confirmPasswordField.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        roleBox.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        genderCombo.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        // Generate unique Employee ID
        empIdField.setText(generateUniqueEmpId());

        addFieldRow(fieldPanel, gbc, "Employee ID", empIdField);
        addFieldRow(fieldPanel, gbc, "Full Name", fullNameField);
        addFieldRow(fieldPanel, gbc, "Email", emailField);
        addFieldRow(fieldPanel, gbc, "Employee Role", roleBox);
        addFieldRow(fieldPanel, gbc, "Gender", genderCombo);
        addFieldRow(fieldPanel, gbc, "Password", passwordField);
        addFieldRow(fieldPanel, gbc, "Confirm Password", confirmPasswordField);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(Color.WHITE);
        cancelButton = createStyledButton("Cancel", Color.WHITE, new Color(180, 180, 180));
        addButton = createStyledButton("Add Employee", new Color(40, 167, 69), Color.WHITE);
        buttonPanel.add(cancelButton);
        buttonPanel.add(addButton);

        cancelButton.addActionListener(e -> dispose());

        addButton.addActionListener(e -> {
            int empId = Integer.parseInt(empIdField.getText());
            String fullName = fullNameField.getText().trim();
            String email = emailField.getText().trim();
            String role = (String) roleBox.getSelectedItem();
            String gender = (String) genderCombo.getSelectedItem();
            String password = new String(passwordField.getPassword()).trim();
            String confirmPassword = new String(confirmPasswordField.getPassword()).trim();

            if (fullName.isEmpty() || email.isEmpty() || roleBox.getSelectedIndex() == 0 ||
                genderCombo.getSelectedIndex() == 0 || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill all fields.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!password.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(this, "Passwords do not match.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Employee emp = new Employee(empId, fullName, email, role, gender, password);
            new EmployeeDao().insertEmployee(emp);

            JOptionPane.showMessageDialog(this, "Employee added successfully!");
            dispose();
        });

        add(titleLabel, BorderLayout.NORTH);
        add(fieldPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void addFieldRow(JPanel panel, GridBagConstraints gbc, String labelText, Component field) {
        gbc.gridx = 0;
        gbc.gridy++;
        JLabel label = new JLabel(labelText);
        label.setPreferredSize(new Dimension(120, 25)); // consistent label width
        panel.add(label, gbc);

        gbc.gridx = 1;
        if (field instanceof JTextField || field instanceof JPasswordField || field instanceof JComboBox) {
            field.setPreferredSize(new Dimension(200, 28)); // Ensures borders are visible
        }
        panel.add(field, gbc);
    }

    private JButton createStyledButton(String text, Color bg, Color fg) {
        JButton button = new JButton(text);
        button.setBackground(bg);
        button.setForeground(fg);
        button.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        button.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(130, 35));
        return button;
    }

    private String generateUniqueEmpId() {
        EmployeeDao dao = new EmployeeDao();
        Random rand = new Random();
        int randomId;

        do {
            randomId = 10000 + rand.nextInt(90000);
        } while (employeeIdExists(dao, randomId));

        return String.valueOf(randomId);
    }

    private boolean employeeIdExists(EmployeeDao dao, int empId) {
        List<Employee> employees = dao.getAllEmployees();
        for (Employee emp : employees) {
            if (emp.getEmpId() == empId) {
                return true;
            }
        }
        return false;
    }
}
