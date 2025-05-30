/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package wingsnepal.view;

import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

/**
 *
 * @author Aayush Kharel
 */
public class RegisterPage extends javax.swing.JFrame {

    /**
     * Creates new form RegisterPage
     */
    public RegisterPage() {
        initComponents();
            this.addComponentListener(new java.awt.event.ComponentAdapter() {
                @Override
                public void componentResized(java.awt.event.ComponentEvent evt) {
                    scaleImage();
            }
        });   
        
//        scaleImage();
        
        //Code for maximizing output screen.
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        
        // for making it visible.
        setVisible(true); 
    }
        public void scaleImage(){
            
            // Avoid scaling if label is not yet visible or sized
            if (BgImageLabel.getWidth() <= 0 || BgImageLabel.getHeight() <= 0) {
                return;
            }
            
            // Set the bounds of the label to cover the full window
            BgImageLabel.setBounds(0, 0, this.getWidth(), this.getHeight());
            
            // Load image from path into ImageIcon.
            ImageIcon icon = new ImageIcon("C:\\Users\\Aayush Kharel\\Desktop\\Java project\\WingsNepal\\src\\wingsnepal\\image\\Aeroplane Image 2.jpg");
            
            //Get Image from the icon.
            Image img1 = icon.getImage();
            
            //Resize the image to fit the label dimensions.
            Image imgScale = img1.getScaledInstance(BgImageLabel.getWidth(), BgImageLabel.getHeight(), Image.SCALE_SMOOTH);
            
            //Create a new scaled icon.
            ImageIcon scaledIcon1 = new ImageIcon(imgScale);
            
            //Set it to the label.
            BgImageLabel.setIcon(scaledIcon1);

    }
        
        
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        NameLabel = new javax.swing.JLabel();
        NameTextField = new javax.swing.JTextField();
        EmailLabel = new javax.swing.JLabel();
        EmailTextField = new javax.swing.JTextField();
        PhoneLabel = new javax.swing.JLabel();
        PhoneTextField = new javax.swing.JTextField();
        GenderLabel = new javax.swing.JLabel();
        GenderComboBox = new javax.swing.JComboBox<>();
        IDTypeLabel = new javax.swing.JLabel();
        IDTypeComboBox = new javax.swing.JComboBox<>();
        IDNumberLabel = new javax.swing.JLabel();
        IDNumberTextField = new javax.swing.JTextField();
        PasswordLabel = new javax.swing.JLabel();
        PasswordField = new javax.swing.JPasswordField();
        ConfirmPasswordLabel = new javax.swing.JLabel();
        ConfirmPasswordField = new javax.swing.JPasswordField();
        UserLabel = new javax.swing.JLabel();
        UserComboBox = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();
        BgImageLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);

        jPanel1.setBackground(new java.awt.Color(153, 153, 153, 65));

        NameLabel.setFont(new java.awt.Font("Segoe UI Emoji", 0, 18)); // NOI18N
        NameLabel.setText("Fullname");

        NameTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NameTextFieldActionPerformed(evt);
            }
        });

        EmailLabel.setFont(new java.awt.Font("Segoe UI Emoji", 0, 18)); // NOI18N
        EmailLabel.setText("Email");

        PhoneLabel.setFont(new java.awt.Font("Segoe UI Emoji", 0, 18)); // NOI18N
        PhoneLabel.setText("Phone");

        PhoneTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PhoneTextFieldActionPerformed(evt);
            }
        });

        GenderLabel.setFont(new java.awt.Font("Segoe UI Emoji", 0, 18)); // NOI18N
        GenderLabel.setText("Gender");

        GenderComboBox.setFont(new java.awt.Font("Segoe UI Emoji", 0, 14)); // NOI18N
        GenderComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Male", "Female" }));
        GenderComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GenderComboBoxActionPerformed(evt);
            }
        });

        IDTypeLabel.setFont(new java.awt.Font("Segoe UI Emoji", 0, 18)); // NOI18N
        IDTypeLabel.setText("ID Type");

        IDTypeComboBox.setFont(new java.awt.Font("Segoe UI Emoji", 0, 14)); // NOI18N
        IDTypeComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Passport", "Citizenship", "Drivinglicense" }));
        IDTypeComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                IDTypeComboBoxActionPerformed(evt);
            }
        });

        IDNumberLabel.setFont(new java.awt.Font("Segoe UI Emoji", 0, 18)); // NOI18N
        IDNumberLabel.setText("ID Number");

        IDNumberTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                IDNumberTextFieldActionPerformed(evt);
            }
        });

        PasswordLabel.setFont(new java.awt.Font("Segoe UI Emoji", 0, 18)); // NOI18N
        PasswordLabel.setText("Password");

        ConfirmPasswordLabel.setFont(new java.awt.Font("Segoe UI Emoji", 0, 18)); // NOI18N
        ConfirmPasswordLabel.setText("Confirm Password");

        ConfirmPasswordField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ConfirmPasswordFieldActionPerformed(evt);
            }
        });

        UserLabel.setFont(new java.awt.Font("Segoe UI Emoji", 0, 18)); // NOI18N
        UserLabel.setText("User");

        UserComboBox.setFont(new java.awt.Font("Segoe UI Emoji", 0, 14)); // NOI18N
        UserComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Admin", "Employee", "User" }));
        UserComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UserComboBoxActionPerformed(evt);
            }
        });

        jButton1.setBackground(new java.awt.Color(102, 153, 255));
        jButton1.setFont(new java.awt.Font("Segoe UI Emoji", 0, 18)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Sign up");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(ConfirmPasswordLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(ConfirmPasswordField, javax.swing.GroupLayout.PREFERRED_SIZE, 398, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 398, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(UserLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(129, 129, 129)
                                .addComponent(UserComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 398, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(NameLabel)
                            .addComponent(EmailLabel)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(PhoneLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(GenderLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(IDTypeLabel))
                        .addGap(101, 101, 101)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(IDTypeComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(PhoneTextField, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(GenderComboBox, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(NameTextField, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(EmailTextField, javax.swing.GroupLayout.Alignment.TRAILING)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(IDNumberLabel)
                        .addGap(86, 86, 86)
                        .addComponent(IDNumberTextField))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(PasswordLabel)
                        .addGap(98, 98, 98)
                        .addComponent(PasswordField)))
                .addGap(43, 43, 43))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addComponent(NameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(NameLabel)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(EmailTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addComponent(EmailLabel)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addComponent(PhoneLabel))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(PhoneTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(GenderLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(GenderComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(IDTypeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(IDTypeLabel))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(IDNumberTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(IDNumberLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(PasswordField, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(PasswordLabel))
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(ConfirmPasswordLabel)
                    .addComponent(ConfirmPasswordField, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(UserLabel)
                    .addComponent(UserComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(34, 34, 34)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(23, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1);
        jPanel1.setBounds(550, 160, 630, 580);

        BgImageLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/wingsnepal/image/Aeroplane Image 2.jpg"))); // NOI18N
        BgImageLabel.setText("BgLabel");
        getContentPane().add(BgImageLabel);
        BgImageLabel.setBounds(0, 0, 1400, 770);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void NameTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NameTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_NameTextFieldActionPerformed

    private void PhoneTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PhoneTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_PhoneTextFieldActionPerformed

    private void IDTypeComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_IDTypeComboBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_IDTypeComboBoxActionPerformed

    private void IDNumberTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_IDNumberTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_IDNumberTextFieldActionPerformed

    private void GenderComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GenderComboBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_GenderComboBoxActionPerformed

    private void ConfirmPasswordFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ConfirmPasswordFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ConfirmPasswordFieldActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void UserComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UserComboBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_UserComboBoxActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(RegisterPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(RegisterPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(RegisterPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(RegisterPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new RegisterPage().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel BgImageLabel;
    private javax.swing.JPasswordField ConfirmPasswordField;
    private javax.swing.JLabel ConfirmPasswordLabel;
    private javax.swing.JLabel EmailLabel;
    private javax.swing.JTextField EmailTextField;
    private javax.swing.JComboBox<String> GenderComboBox;
    private javax.swing.JLabel GenderLabel;
    private javax.swing.JLabel IDNumberLabel;
    private javax.swing.JTextField IDNumberTextField;
    private javax.swing.JComboBox<String> IDTypeComboBox;
    private javax.swing.JLabel IDTypeLabel;
    private javax.swing.JLabel NameLabel;
    private javax.swing.JTextField NameTextField;
    private javax.swing.JPasswordField PasswordField;
    private javax.swing.JLabel PasswordLabel;
    private javax.swing.JLabel PhoneLabel;
    private javax.swing.JTextField PhoneTextField;
    private javax.swing.JComboBox<String> UserComboBox;
    private javax.swing.JLabel UserLabel;
    private javax.swing.JButton jButton1;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
