/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import wingsnepal.dao.UserDao;
import wingsnepal.view.RegisterPage;
import wingsnepal.view.LoginPage;

/**
 *
 * @author Aayush Kharel
 */
public class RegisterPageController {
    private final RegisterPage view;
    
    public RegisterPageController(RegisterPage view) {
        this.view = view;
        this.view.getSignUpButton().addActionListener(new SignUpHandler());
    }
    
    public void open() {
        this.view.setVisible(true);
    }
    
    public void close() {
        this.view.dispose();
    }
    
    class SignUpHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            handleRegistration();
        }
    }
    
    private void handleRegistration() {
        String name = view.getNameTextField().getText().trim();
        String email = view.getEmailTextField().getText().trim();
        String phone = view.getPhoneTextField().getText().trim();
        String gender = view.getGenderComboBox().getSelectedItem().toString();
        String password = new String(view.getPasswordField().getPassword());
        String confirm = new String(view.getConfirmPasswordField().getPassword());

        // --- VALIDATION ---
        if (name.isEmpty() || email.isEmpty() || phone.isEmpty() || gender.isEmpty() || password.isEmpty() || confirm.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Please fill in all fields.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            JOptionPane.showMessageDialog(view, "Please enter a valid email address.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!password.equals(confirm)) {
            JOptionPane.showMessageDialog(view, "Passwords do not match.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // --- REGISTRATION LOGIC ---
        UserDao dao = new UserDao();

        // Check if user already exists
        if (dao.findUserByEmail(email) != null) {
            JOptionPane.showMessageDialog(view, "An account with this email already exists.", "Registration Failed", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Attempt to register the new user with the "Customer" role
        boolean success = dao.registerUser(name, email, phone, gender, password, "Customer");

        if (success) {
            JOptionPane.showMessageDialog(view, "Registration successful! Please log in.", "Success", JOptionPane.INFORMATION_MESSAGE);
            // Close registration window and open login window
            close();
            wingsnepal.view.LoginPage loginView = new wingsnepal.view.LoginPage();
            new LoginController(loginView).open();
        } else {
            JOptionPane.showMessageDialog(view, "Registration failed due to a database error. Please try again later.", "Registration Failed", JOptionPane.ERROR_MESSAGE);
        }
    }
}
