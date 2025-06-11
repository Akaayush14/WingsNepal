/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package wingsnepal.view;

import java.awt.Color;
import java.awt.Image;
import javax.swing.*;
import wingsnepal.dao.LoginDao;
import wingsnepal.model.Login;

/**
 *
 * @author Aayush Kharel
 */

public class LoginPage extends javax.swing.JFrame {

    private boolean passwordVisible = false;

    public LoginPage() {
        initComponents();
        setTitle("Login");
        scaleImage1();
        scaleImage2();
        scaleImage3();
        setResizable(false);
        setLocationRelativeTo(null);
        
        // Eye icon setup
     // Initial EyeLabel Setup
EyeLabel.setOpaque(false);
EyeLabel.setBackground(new Color(0, 0, 0, 0));
EyeLabel.setToolTipText("Show Password");
EyeLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
EyeLabel.setText("");

// Optional: Use scaled image with transparency preserved
ImageIcon viewIcon = new ImageIcon(getClass().getResource("/imagepicker/view.png"));
EyeLabel.setIcon(viewIcon);

// Mouse click to toggle password visibility
EyeLabel.addMouseListener(new java.awt.event.MouseAdapter() {
    public void mouseClicked(java.awt.event.MouseEvent evt) {
        passwordVisible = !passwordVisible;
        if (passwordVisible) {
            EyeLabel.setOpaque(false);
            EyeLabel.setBackground(new Color(0, 0, 0, 0));
            EyeLabel.setText("");
            PasswordTextField.setEchoChar((char) 0);
            EyeLabel.setToolTipText("Hide Password");
            EyeLabel.setIcon(new ImageIcon(getClass().getResource("/imagepicker/hide.png")));
        } else {
            EyeLabel.setOpaque(false);
            EyeLabel.setBackground(new Color(0, 0, 0, 0));
            EyeLabel.setText("");
            PasswordTextField.setEchoChar('•');
            EyeLabel.setToolTipText("Show Password");
            EyeLabel.setIcon(new ImageIcon(getClass().getResource("/imagepicker/view.png")));
        }
    }
});
    }

    // Login logic refactored into a method
    private void handleLogin() {
        String email = EmailTextField.getText().trim();
        String password = new String(PasswordTextField.getPassword()).trim();
        String selectedRole = (String) RoleComboBox.getSelectedItem();

        // Reset field borders
        EmailTextField.setBorder(UIManager.getBorder("TextField.border"));
        PasswordTextField.setBorder(UIManager.getBorder("TextField.border"));

        // Validation
        if (email.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter your email.");
            EmailTextField.setBorder(BorderFactory.createLineBorder(Color.RED));
            return;
        }
        if (!email.matches("^[\\w.-]+@[\\w.-]+\\.\\w{2,}$")) {
            JOptionPane.showMessageDialog(this, "Invalid email format.");
            EmailTextField.setBorder(BorderFactory.createLineBorder(Color.RED));
            return;
        }
        if (password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter your password.");
            PasswordTextField.setBorder(BorderFactory.createLineBorder(Color.RED));
            return;
        }
        if (selectedRole == null || selectedRole.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select a role.");
            return;
        }

        // Authenticating:
        LoginDao loginDao = new LoginDao();
        Login user = loginDao.login(email, password);

        if (user == null) {
            JOptionPane.showMessageDialog(this, "Invalid email or password.");
            return;
        }

        if (!user.getRole().equalsIgnoreCase(selectedRole)) {
            JOptionPane.showMessageDialog(this, "Incorrect role selected for this user.");
            return;
        }

        // Success
        JOptionPane.showMessageDialog(this, "Login successful! Welcome, " + user.getFullName());

        switch (user.getRole()) {
            case "User":
                new UserPortal(user).setVisible(true);
                break;
            case "Employee":
                new EmployeeDashboard(user).setVisible(true);
                break;
            case "Admin":
                new AdminDashboard(user).setVisible(true);
                break;
        }

        dispose(); // Close login
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        LoginPanel = new javax.swing.JPanel();
        EmailLabel = new javax.swing.JLabel();
        PasswordLabel = new javax.swing.JLabel();
        RoleLabel = new javax.swing.JLabel();
        EmailTextField = new javax.swing.JTextField();
        RoleComboBox = new javax.swing.JComboBox<>();
        LoginButton = new javax.swing.JButton();
        NoAccountTextLabel = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        ForgotPasswordLabel = new javax.swing.JLabel();
        SignUpLabel = new javax.swing.JLabel();
        PasswordTextField = new javax.swing.JPasswordField();
        jLabel6 = new javax.swing.JLabel();
        EyeLabel = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        BgLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        LoginPanel.setBackground(new java.awt.Color(102, 102, 102, 80));

        EmailLabel.setFont(new java.awt.Font("Segoe UI Emoji", 0, 16)); // NOI18N
        EmailLabel.setForeground(new java.awt.Color(255, 255, 255));
        EmailLabel.setText("Email");

        PasswordLabel.setFont(new java.awt.Font("Segoe UI Emoji", 0, 16)); // NOI18N
        PasswordLabel.setForeground(new java.awt.Color(255, 255, 255));
        PasswordLabel.setText("Password");

        RoleLabel.setFont(new java.awt.Font("Segoe UI Emoji", 0, 16)); // NOI18N
        RoleLabel.setForeground(new java.awt.Color(255, 255, 255));
        RoleLabel.setText("Role");

        EmailTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EmailTextFieldActionPerformed(evt);
            }
        });

        RoleComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "User", "Employee", "Admin" }));
        RoleComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RoleComboBoxActionPerformed(evt);
            }
        });

        LoginButton.setBackground(new java.awt.Color(0, 102, 153));
        LoginButton.setFont(new java.awt.Font("Segoe UI Emoji", 0, 16)); // NOI18N
        LoginButton.setForeground(new java.awt.Color(255, 255, 255));
        LoginButton.setText("Login");
        LoginButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LoginButtonActionPerformed(evt);
            }
        });

        NoAccountTextLabel.setFont(new java.awt.Font("Segoe UI Emoji", 0, 14)); // NOI18N
        NoAccountTextLabel.setText("Don't have an account?");

        jLabel8.setFont(new java.awt.Font("Segoe UI Emoji", 0, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("WingsNepal@2025");

        ForgotPasswordLabel.setText("Forgot password?");

        SignUpLabel.setFont(new java.awt.Font("Segoe UI Emoji", 0, 14)); // NOI18N
        SignUpLabel.setForeground(new java.awt.Color(255, 51, 0));
        SignUpLabel.setText("Sign up");

        jLabel6.setBackground(new java.awt.Color(204, 204, 204));
        jLabel6.setFont(new java.awt.Font("Segoe UI Emoji", 0, 24)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(204, 204, 204));
        jLabel6.setText("Welcome to the login page!");

        EyeLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagepicker/view.png")));

        javax.swing.GroupLayout LoginPanelLayout = new javax.swing.GroupLayout(LoginPanel);
        LoginPanel.setLayout(LoginPanelLayout);
        LoginPanelLayout.setHorizontalGroup(
            LoginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(LoginPanelLayout.createSequentialGroup()
                .addContainerGap(40, Short.MAX_VALUE)
                .addGroup(LoginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, LoginPanelLayout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addGap(131, 131, 131))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, LoginPanelLayout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(35, 35, 35))))
            .addGroup(LoginPanelLayout.createSequentialGroup()
                .addGroup(LoginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(LoginPanelLayout.createSequentialGroup()
                        .addGap(98, 98, 98)
                        .addComponent(NoAccountTextLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(SignUpLabel))
                    .addGroup(LoginPanelLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(LoginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(LoginPanelLayout.createSequentialGroup()
                                .addComponent(EmailLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(EmailTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(LoginPanelLayout.createSequentialGroup()
                                .addGroup(LoginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(PasswordLabel)
                                    .addComponent(RoleLabel))
                                .addGap(24, 24, 24)
                                .addGroup(LoginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(LoginButton, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(LoginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(ForgotPasswordLabel)
                                        .addGroup(LoginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(RoleComboBox, 0, 227, Short.MAX_VALUE)
                                            .addComponent(PasswordTextField))))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(EyeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(20, Short.MAX_VALUE))
        );
        LoginPanelLayout.setVerticalGroup(
            LoginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(LoginPanelLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel6)
                .addGap(42, 42, 42)
                .addGroup(LoginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(EmailTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(EmailLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(28, 28, 28)
                .addGroup(LoginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(LoginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(PasswordTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(PasswordLabel))
                    .addComponent(EyeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
                .addGroup(LoginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(RoleComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(RoleLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ForgotPasswordLabel)
                .addGap(18, 18, 18)
                .addComponent(LoginButton, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(LoginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(NoAccountTextLabel)
                    .addComponent(SignUpLabel))
                .addGap(18, 18, 18)
                .addComponent(jLabel8))
        );

        getContentPane().add(LoginPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 210, 370, 380));

        jLabel1.setFont(new java.awt.Font("Segoe UI Emoji", 1, 28)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Fly high.");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 10, 120, 40));

        jLabel2.setFont(new java.awt.Font("Segoe UI Emoji", 1, 28)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Above the sky.");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 50, 200, 40));

        jLabel3.setFont(new java.awt.Font("Segoe UI Emoji", 0, 22)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Comfortable.secure.your.way.");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 90, 290, -1));

        BgLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagepicker/Aeroplane image 1.jpg"))); // NOI18N
        BgLabel.setText("BgImageLabel");
        getContentPane().add(BgLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1000, 600));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void RoleComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RoleComboBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_RoleComboBoxActionPerformed

    private void LoginButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LoginButtonActionPerformed
        handleLogin();
    }//GEN-LAST:event_LoginButtonActionPerformed

    private void EmailTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EmailTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_EmailTextFieldActionPerformed

    public void scaleImage1(){
        ImageIcon icon1 = new ImageIcon(getClass().getResource("/imagepicker/Aeroplane Image 1.jpg"));
        //scaling image to fit in the hlabel.
        Image img1 = icon1.getImage();
        Image imgScale1 = img1.getScaledInstance(BgLabel.getWidth(), BgLabel.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon scaledIcon1 = new ImageIcon(imgScale1);
        BgLabel.setIcon(scaledIcon1);
    }
    
    public void scaleImage2(){
        ImageIcon icon2 = new ImageIcon(getClass().getResource("/imagepicker/view.png"));
        //scaling image to fit in the hlabel.
        Image img2 = icon2.getImage();
        Image imgScale2 = img2.getScaledInstance(EyeLabel.getWidth(), EyeLabel.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon scaledIcon2 = new ImageIcon(imgScale2);
        EyeLabel.setIcon(scaledIcon2);
    }
    
    public void scaleImage3(){
        ImageIcon icon3 = new ImageIcon(getClass().getResource("/imagepicker/hide.png"));
        //scaling image to fit in the hlabel.
        Image img3 = icon3.getImage();
        Image imgScale2 = img3.getScaledInstance(EyeLabel.getWidth(), EyeLabel.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon scaledIcon3 = new ImageIcon(imgScale2);
        EyeLabel.setIcon(scaledIcon3);
    }
    
    //Main
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> new LoginPage().setVisible(true));
    }

    
    // Adding these public getter methods below your main method:
    public javax.swing.JTextField getEmailTextField() {
        return EmailTextField;
    }

    public javax.swing.JPasswordField getPasswordField() {
        return PasswordTextField;
    }

    public javax.swing.JComboBox<String> getRoleComboBox() {
        return RoleComboBox;
    }

    public javax.swing.JButton getLoginButton() {
        return LoginButton;
    }

    public javax.swing.JLabel getForgotPasswordLabel() {
        return ForgotPasswordLabel;
    }

    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel BgLabel;
    private javax.swing.JLabel EmailLabel;
    private javax.swing.JTextField EmailTextField;
    private javax.swing.JLabel EyeLabel;
    private javax.swing.JLabel ForgotPasswordLabel;
    private javax.swing.JButton LoginButton;
    private javax.swing.JPanel LoginPanel;
    private javax.swing.JLabel NoAccountTextLabel;
    private javax.swing.JLabel PasswordLabel;
    private javax.swing.JPasswordField PasswordTextField;
    private javax.swing.JComboBox<String> RoleComboBox;
    private javax.swing.JLabel RoleLabel;
    private javax.swing.JLabel SignUpLabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    // End of variables declaration//GEN-END:variables
}
