/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package wingsnepal.view;

import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import wingsnepal.dao.LoginDao;
import wingsnepal.model.Login;

/**
 *
 * @author Aayush Kharel
 */
public class LoginPage extends javax.swing.JFrame {

    /**
     * Creates new form LoginPage
     */
    public LoginPage() {
        initComponents();
        setTitle("Login");
        scaleImage();
        setResizable(false);         //Disable maximize button
        setLocationRelativeTo(null); //Center window
        
    }
    public void scaleImage(){
        ImageIcon icon = new ImageIcon(getClass().getResource("/imagepicker/Aeroplane Image 1.jpg"));
        //scaling image to fit in the hlabel.
        Image img1 = icon.getImage();
        Image imgScale = img1.getScaledInstance(BgLabel.getWidth(), BgLabel.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(imgScale);
        BgLabel.setIcon(scaledIcon);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        LoginPanel = new javax.swing.JPanel();
        EmailLabel = new javax.swing.JLabel();
        PasswordLabel = new javax.swing.JLabel();
        RoleLabel = new javax.swing.JLabel();
        UsernameTextField = new javax.swing.JTextField();
        RoleComboBox = new javax.swing.JComboBox<>();
        LoginButton = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jPasswordField1 = new javax.swing.JPasswordField();
        jLabel6 = new javax.swing.JLabel();
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

        UsernameTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UsernameTextFieldActionPerformed(evt);
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

        jLabel7.setFont(new java.awt.Font("Segoe UI Emoji", 0, 14)); // NOI18N
        jLabel7.setText("Don't have an account?");

        jLabel8.setFont(new java.awt.Font("Segoe UI Emoji", 0, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("WingsNepal@2025");

        jLabel4.setText("Forgot password?");

        jLabel5.setFont(new java.awt.Font("Segoe UI Emoji", 0, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 51, 0));
        jLabel5.setText("Sign up");

        jLabel6.setBackground(new java.awt.Color(204, 204, 204));
        jLabel6.setFont(new java.awt.Font("Segoe UI Emoji", 0, 24)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(204, 204, 204));
        jLabel6.setText("Welcome to the login page!");

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
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel5))
                    .addGroup(LoginPanelLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(LoginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(LoginPanelLayout.createSequentialGroup()
                                .addComponent(EmailLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(UsernameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(LoginPanelLayout.createSequentialGroup()
                                .addGroup(LoginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(PasswordLabel)
                                    .addComponent(RoleLabel))
                                .addGap(24, 24, 24)
                                .addGroup(LoginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(LoginButton, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(LoginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabel4)
                                        .addGroup(LoginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(RoleComboBox, 0, 227, Short.MAX_VALUE)
                                            .addComponent(jPasswordField1))))))))
                .addContainerGap(48, Short.MAX_VALUE))
        );
        LoginPanelLayout.setVerticalGroup(
            LoginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(LoginPanelLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel6)
                .addGap(42, 42, 42)
                .addGroup(LoginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(UsernameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(EmailLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(28, 28, 28)
                .addGroup(LoginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jPasswordField1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(PasswordLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
                .addGroup(LoginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(RoleComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(RoleLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addGap(18, 18, 18)
                .addComponent(LoginButton, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(LoginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jLabel5))
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
        // TODO add your handling code here:
        String email = UsernameTextField.getText();
        String password = new String(jPasswordField1.getPassword());
        String selectedRole = (String) RoleComboBox.getSelectedItem();

        LoginDao loginDao = new LoginDao();
        Login user = loginDao.login(email, password);

        if (user != null && user.getRole().equalsIgnoreCase(selectedRole)) {
            // You may store the user in a static field or session-like structure if needed
            JOptionPane.showMessageDialog(this, "Login successful! Welcome, " + user.getFullName());

            // Redirect to appropriate dashboard
            if ("User".equalsIgnoreCase(user.getRole())) {
                new UserPortal(user).setVisible(true);  // Make sure UserPortal has a constructor that accepts User
            } else if ("Employee".equalsIgnoreCase(user.getRole())) {
                new EmployeeDashboard(user).setVisible(true); 
            } else if("Admin".equalsIgnoreCase(user.getRole())) {
                new AdminDashboard(user).setVisible(true);
            }
            dispose(); // close the login window
            
        } else {
            JOptionPane.showMessageDialog(this, "Invalid email, password, or role.");
        }
    }//GEN-LAST:event_LoginButtonActionPerformed

    private void UsernameTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UsernameTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_UsernameTextFieldActionPerformed

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LoginPage().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel BgLabel;
    private javax.swing.JLabel EmailLabel;
    private javax.swing.JButton LoginButton;
    private javax.swing.JPanel LoginPanel;
    private javax.swing.JLabel PasswordLabel;
    private javax.swing.JComboBox<String> RoleComboBox;
    private javax.swing.JLabel RoleLabel;
    private javax.swing.JTextField UsernameTextField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPasswordField jPasswordField1;
    // End of variables declaration//GEN-END:variables
}
