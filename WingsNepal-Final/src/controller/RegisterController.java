package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import wingsnepal.dao.UserDao;
import wingsnepal.view.RegisterPage;

public class RegisterController {
    private final RegisterPage view;
    private final UserDao userDao;

    public RegisterController(RegisterPage view) {
        this.view = view;
        this.userDao = new UserDao();
        this.view.getSignUpButton().addActionListener(new RegisterAction());
    }

    class RegisterAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String fullName = view.getNameTextField().getText().trim();
            String email = view.getEmailTextField().getText().trim();
            String phone = view.getPhoneTextField().getText().trim();
            String gender = (String) view.getGenderComboBox().getSelectedItem();
            String password = new String(view.getPasswordField().getPassword());
            String confirmPassword = new String(view.getConfirmPasswordField().getPassword());

            // Validation
            if (fullName.isEmpty() || email.isEmpty() || phone.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                JOptionPane.showMessageDialog(view, "Please fill in all fields.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
                JOptionPane.showMessageDialog(view, "Please enter a valid email address.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (!password.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(view, "Passwords do not match.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (password.length() < 6) {
                JOptionPane.showMessageDialog(view, "Password must be at least 6 characters long.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Register user
            boolean success = userDao.registerUser(fullName, email, phone, gender, password, "User");
            if (success) {
                JOptionPane.showMessageDialog(view, "Registration successful! Please log in.", "Success", JOptionPane.INFORMATION_MESSAGE);
                view.dispose();
                wingsnepal.view.LoginPage loginView = new wingsnepal.view.LoginPage();
                new LoginController(loginView).open();
            } else {
                JOptionPane.showMessageDialog(view, "Registration failed. Email may already be registered.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
