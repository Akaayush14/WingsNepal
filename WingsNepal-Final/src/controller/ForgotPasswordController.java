package controller;

import wingsnepal.view.ForgotPassword;
import wingsnepal.dao.UserDao;
import wingsnepal.controller.mail.SmtpMailSender;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import javax.swing.JOptionPane;

public class ForgotPasswordController {
    private final ForgotPassword view;

    public ForgotPasswordController(ForgotPassword view) {
        this.view = view;

        // Set action listener for OTP button
        this.view.getOtpButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleOtpRequest();
            }
        });
    }

    /**
     * Generates a random 6-digit OTP string.
     * @return A 6-digit OTP string
     */
    private String generateOtp() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000); // Generates a number between 100000 and 999999
        return String.valueOf(otp);
    }

    private void handleOtpRequest() {
        String email = view.getEmailTextField().getText().trim();
        if (email.isEmpty() || !email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            JOptionPane.showMessageDialog(view, "Please enter a valid email address.", "Invalid Email", JOptionPane.ERROR_MESSAGE);
            return;
        }

        UserDao userDao = new UserDao();
        // Check if a user exists with this email in the users table.
        if (userDao.findUserByEmail(email) == null) {
            JOptionPane.showMessageDialog(view, "No account found with this email address.", "User Not Found", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Generate OTP and set an expiry time (e.g., 10 minutes from now)
        String otp = generateOtp();
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MINUTE, 10);
        Date expiryDate = cal.getTime();

        // Save the OTP and expiry date to the users table
        boolean tokenSaved = userDao.saveResetToken(email, otp, expiryDate);

        if (tokenSaved) {
            System.out.println("OTP and expiry saved for " + email + ". Sending email...");
            // Send the email
            String emailResult = wingsnepal.controller.mail.SmtpMailSender.sendOtpEmail(email, otp);
            if (emailResult.startsWith("OTP sent successfully")) {
                System.out.println("Email sent successfully to " + email);
                JOptionPane.showMessageDialog(view, "An OTP has been sent to your email. It will expire in 10 minutes.", "Success", JOptionPane.INFORMATION_MESSAGE);
                // Navigate to the reset password screen
                close();
                wingsnepal.view.ResetPassword resetView = new wingsnepal.view.ResetPassword(email);
                new ResetPasswordController(resetView, email).open();
            } else {
                System.err.println("Email sending failed. Error: " + emailResult);
                String errorMessage = "Failed to send OTP. Please check your system's configuration and try again.\n\nDetails: " + emailResult;
                JOptionPane.showMessageDialog(view, errorMessage, "Email Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            System.err.println("Failed to save OTP to the database for email: " + email);
            JOptionPane.showMessageDialog(view, "A database error occurred. Please try again later.", "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void open() {
        view.setVisible(true);
    }

    public void close() {
        view.setVisible(false);
    }
}
