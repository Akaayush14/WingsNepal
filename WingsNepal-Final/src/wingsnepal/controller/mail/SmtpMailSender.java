/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package wingsnepal.controller.mail;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

/**
 *
 * @author Aayush Kharel
 */
public class SmtpMailSender {
        public static String sendOtpEmail(String recipientEmail, String otp) {
        // You can replace this with System.getProperty if running via JVM args
        final String senderEmail = "akaayush30@gmail.com";
        final String senderPassword = "jbcxzpxkrctiojaa"; // Your app password here

        if (senderEmail == null || senderEmail.trim().isEmpty()) {
            return "Error: SMTP email not set.";
        }
        if (senderPassword == null || senderPassword.trim().isEmpty()) {
            return "Error: SMTP app password not set.";
        }

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            message.setSubject("Your WingsNepal OTP Code");
            message.setText("Your OTP for resetting password is: " + otp);

            Transport.send(message);
            return "OTP sent successfully to " + recipientEmail;
        } catch (MessagingException e) {
            e.printStackTrace();
            return "Error sending OTP: " + e.getMessage();
        }
    }
    
}
