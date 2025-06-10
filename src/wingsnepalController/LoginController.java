/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package wingsnepalController;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JOptionPane;
import wingsnepal.controller.mail.SMTPSMailSender;
import wingsnepal.dao.UserDao;
import wingsnepal.model.LoginRequest;
import wingsnepal.model.ResetRequest;
import wingsnepal.model.UserData;
import wingsnepal.view.AdminDashboard;
import wingsnepal.view.LoginPage;

/**
 *
 * @author ACER
 */
public class LoginController {
    LoginPage view = new LoginPage();
    public LoginController(LoginPage view){
        this.view = view;
        this.view.loginUser(new LoginUser());
        this.view.forgotPassword(new ForgotPassword());
    }
    public void open(){
        this.view.setVisible(true);
    }
    public void close(){
        this.view.dispose();
    }
    
    class LoginUser implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            String email = view.getEmailTextField().getText();            
            String password = String.valueOf(view.getPasswordField().getPassword());
            if (email.isEmpty()||password.isEmpty()){
                JOptionPane.showMessageDialog(view, "Fill in all the fields");
            } else {
                UserDao userDao = new UserDao();
                LoginRequest loginRequest = new LoginRequest(email,password);
                UserData user= (UserData) userDao.login(loginRequest);
                if (user==null){
                    JOptionPane.showMessageDialog(view, "Login failed");
                } else {
                    JOptionPane.showMessageDialog(view, "Logged in successfully");
                    AdminDashboard dashboardView = new AdminDashboard();
                    AdminDashboardController controller = new AdminDashboardController(dashboardView,user);
                    controller.open();
                    close();
                }
            }

             }
        
    }

    class ForgotPassword implements MouseListener{

        @Override
        public void mouseClicked(MouseEvent e) {
            String email = JOptionPane.showInputDialog(view,"Enter your email address");
            if (email==null||email.trim().isEmpty()){
                JOptionPane.showMessageDialog(view,"Field cannot be empty");
            } else {
                UserDao userDao = new UserDao();
                UserData user = (UserData) userDao.checkEmail(email);
                if (user==null){
                  JOptionPane.showMessageDialog(view,"Email does not exist");
                } else {
                  String otp =( ""+Math.random()).substring(2);
                    // Prepare email content
            String subject = "Your Password Reset OTP";
            String body = "Hello " + user.getName() + ",\n\nYour OTP for password reset is: " + otp 
                          + "\n\nUse this to reset your password.";
            
            // Send email using your SMTPSMailSender class
            boolean mailSent = SMTPSMailSender.sendMail(email, subject, body);
            if (!mailSent){
                JOptionPane.showMessageDialog(view,"Failed to send email. Try again later.");
            } else {
                   String responseOtp = JOptionPane.showInputDialog(view,"Enter the otp");
                  if (!otp.equals(responseOtp)){
                      JOptionPane.showMessageDialog(view, "Otp did not match");
                  } else {
                      String newPassword = JOptionPane.showInputDialog(view,"Enter the new password");
                      ResetRequest reset = new ResetRequest(email,newPassword);
                      boolean res=userDao.updatePassword(reset);
                      if (res){
                          JOptionPane.showMessageDialog(view, "Password changed");
                      } else {
                          JOptionPane.showMessageDialog(view, "Failed to reset password");
                      }
                  }
            }
                    
               
                  


                }
                
            }
            
        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }
        
    }
}


