/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import wingsnepal.dao.UserDao;
import wingsnepal.view.ResetPassword;
import wingsnepal.view.LoginPage;

/**
 *
 * @author Aayush Kharel
 */
public class ResetPasswordController {
    private final ResetPassword view;
    private final String userEmail;
    
    public ResetPasswordController(ResetPassword view, String email) {
        this.view = view;
        this.userEmail = email;
        this.view.getReseButton().addActionListener(new ResetButtonHandler());
    }
    
    public void open() {
        this.view.setVisible(true);
    }
    
    public void close() {
        this.view.dispose();
    }
    
    class ResetButtonHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            handlePasswordReset();
        }
    }
    
    private void handlePasswordReset() {
        String otp = view.getOTPTextField().getText().trim();
        String newPassword = new String(view.getNewPasswordTextField().getPassword());
        String confirmPassword = new String(view.getConfirmPasswordTextField().getPassword());

        if (otp.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Please fill in all fields.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!newPassword.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(view, "Passwords do not match.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (newPassword.length() < 6) {
             JOptionPane.showMessageDialog(view, "Password must be at least 6 characters long.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        UserDao userDao = new UserDao();
        // 1. Verify the token is valid and not expired (users table)
        if (userDao.verifyResetToken(this.userEmail, otp)) {
            // 2. If valid, reset the password (users table)
            if (userDao.resetPassword(this.userEmail, newPassword)) {
                JOptionPane.showMessageDialog(view, "Password has been reset successfully! Please log in.", "Success", JOptionPane.INFORMATION_MESSAGE);
                close();
                wingsnepal.view.LoginPage loginView = new wingsnepal.view.LoginPage();
                new LoginController(loginView).open();
            } else {
                JOptionPane.showMessageDialog(view, "Failed to reset password due to a database error. Please try again.", "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(view, "The OTP is invalid or has expired. Please request a new one.", "Invalid OTP", JOptionPane.ERROR_MESSAGE);
        }
    }
}
