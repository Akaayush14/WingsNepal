/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package wingsnepal.view;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Color;
import java.awt.Image;
import javax.swing.*;
import java.util.List;
import controller.LoginController;
import wingsnepal.dao.BookingPassengerDao;
import wingsnepal.model.PassengerModel;


/**
 *
 * @author Aayush Kharel
 */

public class LoginPage extends javax.swing.JFrame {

    public LoginPage() {
        initComponents();
        setTitle("Login");
        scaleImage1();
        setResizable(false);
        setLocationRelativeTo(null);

        // Fix checkbox styling to remove blur effect
        JCheckBox.setText("Show");
        JCheckBox.setOpaque(false);
        JCheckBox.setContentAreaFilled(false);
        JCheckBox.setBorderPainted(false);
        JCheckBox.setFocusPainted(false);
        JCheckBox.setRolloverEnabled(false);
        JCheckBox.setForeground(Color.WHITE);
        JCheckBox.setFont(new java.awt.Font("Segoe UI", 0, 12));
        JCheckBox.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        PasswordTextField.setEchoChar('\u2022'); // default hidden

        JCheckBox.addItemListener(e -> {
        if (JCheckBox.isSelected()) {
            PasswordTextField.setEchoChar((char) 0); // Show
        } else {
            PasswordTextField.setEchoChar('\u2022'); // Hide
            }
        });

        // Note: SignUp functionality is handled by LoginController
        SignUpLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        // Navigate to Forgot Password Page
        ForgotPasswordLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
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
        WingsNepal2025 = new javax.swing.JLabel();
        ForgotPasswordLabel = new javax.swing.JLabel();
        SignUpLabel = new javax.swing.JLabel();
        PasswordTextField = new javax.swing.JPasswordField();
        jLabel6 = new javax.swing.JLabel();
        JCheckBox = new javax.swing.JCheckBox();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        BgLabel = new javax.swing.JLabel();
        ContactTextField = new javax.swing.JTextField();

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

        NoAccountTextLabel.setFont(new java.awt.Font("Segoe UI Emoji", 0, 14)); // NOI18N
        NoAccountTextLabel.setText("Don't have an account?");

        WingsNepal2025.setFont(new java.awt.Font("Segoe UI Emoji", 0, 12)); // NOI18N
        WingsNepal2025.setForeground(new java.awt.Color(255, 255, 255));
        WingsNepal2025.setText("WingsNepal@2025");

        ForgotPasswordLabel.setText("Forgot password?");

        SignUpLabel.setFont(new java.awt.Font("Segoe UI Emoji", 0, 14)); // NOI18N
        SignUpLabel.setForeground(new java.awt.Color(255, 51, 0));
        SignUpLabel.setText("Sign up");

        jLabel6.setBackground(new java.awt.Color(204, 204, 204));
        jLabel6.setFont(new java.awt.Font("Segoe UI Emoji", 0, 24)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(204, 204, 204));
        jLabel6.setText("Welcome to the login page!");

        JCheckBox.setText("Show");
        JCheckBox.setToolTipText("");
        JCheckBox.setActionCommand("Show Password");
        JCheckBox.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        JCheckBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                JCheckBoxItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout LoginPanelLayout = new javax.swing.GroupLayout(LoginPanel);
        LoginPanel.setLayout(LoginPanelLayout);
        LoginPanelLayout.setHorizontalGroup(
            LoginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(LoginPanelLayout.createSequentialGroup()
                .addContainerGap(40, Short.MAX_VALUE)
                .addGroup(LoginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, LoginPanelLayout.createSequentialGroup()
                        .addComponent(WingsNepal2025)
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
                                .addGroup(LoginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(LoginPanelLayout.createSequentialGroup()
                                        .addGap(24, 24, 24)
                                        .addGroup(LoginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(LoginButton, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(LoginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                .addComponent(ForgotPasswordLabel)
                                                .addComponent(PasswordTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, LoginPanelLayout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(RoleComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addComponent(JCheckBox))))
                .addContainerGap(48, Short.MAX_VALUE))
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
                .addGroup(LoginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(PasswordTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(PasswordLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 8, Short.MAX_VALUE)
                .addComponent(JCheckBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
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
                .addComponent(WingsNepal2025))
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

    private void EmailTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EmailTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_EmailTextFieldActionPerformed

    private void JCheckBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_JCheckBoxItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_JCheckBoxItemStateChanged

    public void scaleImage1(){
        ImageIcon icon1 = new ImageIcon(getClass().getResource("/imagepicker/Aeroplane Image 1.jpg"));
        //scaling image to fit in the hlabel.
        Image img1 = icon1.getImage();
        Image imgScale1 = img1.getScaledInstance(BgLabel.getWidth(), BgLabel.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon scaledIcon1 = new ImageIcon(imgScale1);
        BgLabel.setIcon(scaledIcon1);
    }

    //Main
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            LoginPage view = new LoginPage();
            new LoginController(view).open();
        });
    }
    
    public javax.swing.JButton getLoginButton() {
        return LoginButton;
    }

    public javax.swing.JLabel getForgotPasswordLabel() {
        return ForgotPasswordLabel;
    }

    public javax.swing.JTextField getEmailTextField() {
        return EmailTextField;
    }

    public javax.swing.JPasswordField getPasswordField() {
        return PasswordTextField;
    }

    public javax.swing.JComboBox<String> getRoleComboBox() {
        return RoleComboBox;
    }
    
    public javax.swing.JLabel getSignUpLabel() {
        return SignUpLabel; // Replace with your actual label variable name
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel BgLabel;
    private javax.swing.JLabel EmailLabel;
    private javax.swing.JTextField EmailTextField;
    private javax.swing.JLabel ForgotPasswordLabel;
    private javax.swing.JCheckBox JCheckBox;
    private javax.swing.JButton LoginButton;
    private javax.swing.JPanel LoginPanel;
    private javax.swing.JLabel NoAccountTextLabel;
    private javax.swing.JLabel PasswordLabel;
    private javax.swing.JPasswordField PasswordTextField;
    private javax.swing.JComboBox<String> RoleComboBox;
    private javax.swing.JLabel RoleLabel;
    private javax.swing.JLabel SignUpLabel;
    private javax.swing.JLabel WingsNepal2025;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JTextField ContactTextField;
    // End of variables declaration//GEN-END:variables
}
