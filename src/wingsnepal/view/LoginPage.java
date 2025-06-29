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
import wingsnepal.dao.LoginDao;
import wingsnepal.model.UserData;


import java.awt.Image;
import javax.swing.ImageIcon;

/**
 *
 * @author Aayush Kharel
 */

public class LoginPage extends javax.swing.JFrame {

    public LoginPage() {
        initComponents();
 aayush
        setTitle("Login");
        scaleImage1();
        setResizable(false);
        setLocationRelativeTo(null);

        // In your constructor, replace the JCheckBox initialization with:
        JCheckBox.setText("Show");
        JCheckBox.setOpaque(false);
        JCheckBox.setContentAreaFilled(false);
        JCheckBox.setBorderPainted(false);
        JCheckBox.setFocusPainted(false);
        JCheckBox.setForeground(Color.BLACK);
        JCheckBox.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        PasswordTextField.setEchoChar('\u2022'); // default hidden

        JCheckBox.addItemListener(e -> {
        if (JCheckBox.isSelected()) {
            PasswordTextField.setEchoChar((char) 0); // Show
        } else {
            PasswordTextField.setEchoChar('\u2022'); // Hide
            }
        });


        
    // Navigate to SignUp Page
    SignUpLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    SignUpLabel.addMouseListener(new MouseAdapter() {
    @Override
    public void mouseClicked(MouseEvent e) {
        new RegisterPage().setVisible(true); // Replace with your actual SignUp class
        dispose(); // Optional: close the login window
        }
    });

    // Navigate to Forgot Password Page
    ForgotPasswordLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    ForgotPasswordLabel.addMouseListener(new MouseAdapter() {
    @Override
    public void mouseClicked(MouseEvent e) {
        new ForgotPassword().setVisible(true); // Replace with your actual ForgotPassword class
        dispose(); // Optional
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

        // Authenticate user
        LoginDao loginDao = new LoginDao();
        UserData user = loginDao.login(email, password);

        if (user == null) {
            JOptionPane.showMessageDialog(this, "Invalid email or password.");
            return;
        }

        // 👇 Force employee login to bypass DB role check
        if (selectedRole.equalsIgnoreCase("Employee")) {
            JOptionPane.showMessageDialog(this, "Login successful as Employee!");
            new EmployeeDashboard(user).setVisible(true);
            dispose();
            return;
        }

        // 🔐 Match selected role with user's actual role for User/Admin
        if (!user.getRole().equalsIgnoreCase(selectedRole)) {
            JOptionPane.showMessageDialog(this, "Incorrect role selected for this user.");
            return;
        }

        // Proceed with matched roles
        JOptionPane.showMessageDialog(this, "Login successful! Welcome, " + user.getFullName());

        switch (user.getRole()) {
            case "User":
                new UserPortal(user).setVisible(true);
                break;
            case "Admin":
                new AdminDashboard(user).setVisible(true);
                break;
        }

        dispose();
    }

    

        scaleImage();
        
        //Styling ForgotPassword button to make it look like a link.
        ForgotButton.setContentAreaFilled(false);
        ForgotButton.setBorderPainted(false);
        ForgotButton.setFocusPainted(false);
        ForgotButton.setOpaque(false);
        ForgotButton.setFocusable(false);
        ForgotButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        ForgotButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        
        //Styling SignUpButton button to make it look like a link.
        SignUpButton.setContentAreaFilled(false);
        SignUpButton.setBorderPainted(false);
        SignUpButton.setFocusPainted(false);
        SignUpButton.setOpaque(false);
        SignUpButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        SignUpButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        
    }
    public void scaleImage(){
        ImageIcon icon = new ImageIcon("C:\\Users\\Aayush Kharel\\Desktop\\Java project\\WingsNepal\\src\\wingsnepal\\image\\Aeroplane image.jpg");
        //scaling image to fit in the hlabel.
        Image img1 = icon.getImage();
        Image imgScale = img1.getScaledInstance(BgLabel.getWidth(), BgLabel.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(imgScale);
        BgLabel.setIcon(scaledIcon);
    }


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
 main
    @SuppressWarnings("unchecked")
    
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

 aayush
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

        jPanel2 = new javax.swing.JPanel();
        UsernameLabel = new javax.swing.JLabel();
        PasswordLabel = new javax.swing.JLabel();
        RoleLabel = new javax.swing.JLabel();
        UsernameTextField = new javax.swing.JTextField();
        PasswordTextField = new javax.swing.JPasswordField();
        RoleComboBox = new javax.swing.JComboBox<>();
        ForgotButton = new javax.swing.JButton();
        LoginButton = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        SignUpButton = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
 main
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        BgLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

 aayush
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

        jPanel2.setBackground(new java.awt.Color(102, 102, 102, 80));

        UsernameLabel.setFont(new java.awt.Font("Segoe UI Emoji", 0, 16)); // NOI18N
        UsernameLabel.setText("Username");

        PasswordLabel.setFont(new java.awt.Font("Segoe UI Emoji", 0, 16)); // NOI18N
        PasswordLabel.setText("Password");

        RoleLabel.setFont(new java.awt.Font("Segoe UI Emoji", 0, 16)); // NOI18N
        RoleLabel.setText("Role");

        PasswordTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PasswordTextFieldActionPerformed(evt);
            }
        });

        RoleComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Admin", "Employee", "User" }));
        RoleComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RoleComboBoxActionPerformed(evt);
            }
        });

        ForgotButton.setText("Forgot password?");
        ForgotButton.setBorder(null);
        ForgotButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ForgotButtonActionPerformed(evt);
            }
        });

        LoginButton.setFont(new java.awt.Font("Segoe UI Emoji", 0, 16)); // NOI18N
 main
        LoginButton.setText("Login");
        LoginButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LoginButtonActionPerformed(evt);
            }
        });

 aayush
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

        JCheckBox.setBackground(new java.awt.Color(102, 102, 102, 80));
        JCheckBox.setText("Show");
        JCheckBox.setToolTipText("");
        JCheckBox.setActionCommand("Show Password");
        JCheckBox.setContentAreaFilled(false);
        JCheckBox.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        JCheckBox.setFocusPainted(false);
        JCheckBox.setFocusable(false);
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

        jLabel7.setFont(new java.awt.Font("Segoe UI Emoji", 0, 14)); // NOI18N
        jLabel7.setText("Don't have an account?");

        SignUpButton.setFont(new java.awt.Font("Segoe UI Emoji", 0, 14)); // NOI18N
        SignUpButton.setForeground(new java.awt.Color(255, 0, 51));
        SignUpButton.setText("Sign up");
        SignUpButton.setBorder(null);
        SignUpButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SignUpButtonActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Segoe UI Emoji", 0, 10)); // NOI18N
        jLabel8.setText("WingsNepal@2025");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(ForgotButton))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(UsernameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(UsernameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 2, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(RoleLabel)
                                        .addGap(53, 53, 53))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                        .addComponent(PasswordLabel)
                                        .addGap(18, 18, 18)))
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(RoleComboBox, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(PasswordTextField)
                                    .addComponent(LoginButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(55, 55, 55)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(SignUpButton)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(95, 95, 95)
                .addComponent(jLabel8)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(UsernameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(UsernameLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(25, 25, 25)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(PasswordTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(PasswordLabel))
                .addGap(26, 26, 26)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(RoleComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(RoleLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ForgotButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(LoginButton, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(SignUpButton))
                .addGap(18, 18, 18)
                .addComponent(jLabel8))
        );

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 190, 290, 300));

        jLabel1.setFont(new java.awt.Font("Segoe UI Emoji", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Fly high.");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 10, 100, 40));

        jLabel2.setFont(new java.awt.Font("Segoe UI Emoji", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Above the sky.");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 40, 170, 40));

        jLabel3.setFont(new java.awt.Font("Segoe UI Emoji", 0, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Comfortable.secure.your.way.");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 80, 240, -1));

        BgLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/wingsnepal/image/Aeroplane image.jpg"))); // NOI18N
        BgLabel.setText("BgImageLabel");
        getContentPane().add(BgLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 800, 500));
 main

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void RoleComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RoleComboBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_RoleComboBoxActionPerformed

 aayush
    private void LoginButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LoginButtonActionPerformed
        handleLogin();
    }//GEN-LAST:event_LoginButtonActionPerformed

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

    private void PasswordTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PasswordTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_PasswordTextFieldActionPerformed

    private void ForgotButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ForgotButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ForgotButtonActionPerformed

    private void LoginButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LoginButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_LoginButtonActionPerformed

    private void SignUpButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SignUpButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_SignUpButtonActionPerformed

    /**
     * @param args the command line arguments
     */
 main
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> new LoginPage().setVisible(true));
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


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel BgLabel;
 aayush
    private javax.swing.JLabel EmailLabel;
    private javax.swing.JTextField EmailTextField;
    private javax.swing.JLabel ForgotPasswordLabel;
    private javax.swing.JCheckBox JCheckBox;
    private javax.swing.JButton LoginButton;
    private javax.swing.JPanel LoginPanel;
    private javax.swing.JLabel NoAccountTextLabel;

    private javax.swing.JButton ForgotButton;
    private javax.swing.JButton LoginButton;
 main
    private javax.swing.JLabel PasswordLabel;
    private javax.swing.JPasswordField PasswordTextField;
    private javax.swing.JComboBox<String> RoleComboBox;
    private javax.swing.JLabel RoleLabel;
 aayush
    private javax.swing.JLabel SignUpLabel;
    private javax.swing.JLabel WingsNepal2025;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel6;

    private javax.swing.JButton SignUpButton;
    private javax.swing.JLabel UsernameLabel;
    private javax.swing.JTextField UsernameTextField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel2;
 main
    // End of variables declaration//GEN-END:variables
}
