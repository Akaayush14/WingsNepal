/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package wingsnepal.view;

import java.awt.Image;
import javax.swing.ImageIcon;
import wingsnepal.model.UserDataModel;
import javax.swing.JLabel;

/**
 *
 * @author ronanchettri
 */
public class EmployeeDashboard extends javax.swing.JFrame {
    private UserDataModel loggedInUser; // store the user info
    private controller.EmployeeDashboardController controller;
    private JLabel reservationCountLabel; // For dashboard metrics
    private JLabel assignedFlightsCountLabel; // For assigned flights count
    private JLabel walkInReservationsCountLabel; // For walk-in reservations count
    
    //New constructor accepting Login object
    public EmployeeDashboard(UserDataModel user) {
        initComponents();
        this.loggedInUser = user; // Store the user data
        setTitle("Employee Dashboard - Welcome " + user.getFullName());
        setLocationRelativeTo(null);
        
        // Initialize the controller
        this.controller = new controller.EmployeeDashboardController(this, user);
        
        // Add modern hover effect and consistent button placement
        styleFlatHoverButton(EmpDashboardButton);
        styleFlatHoverButton(EmpFlightsButton);
        styleFlatHoverButton(EmpPassengerButton);
        styleFlatHoverButton(LogoutButton);
        styleFlatHoverButton(EmpReservationButton); // Reservations button
        // Style search buttons to match the blue theme
        styleSearchButton(jButton9);
        styleSearchButton(jButton14);
        styleSearchButton(jButton2); // Style the Show Walk-In Bookings button
        // Set button bounds to match the .form design view exactly
        EmpDashboardButton.setBounds(-20, 150, 220, 30);
        EmpFlightsButton.setBounds(-10, 220, 250, 30);
        EmpPassengerButton.setBounds(0, 290, 210, 30);
        EmpReservationButton.setBounds(0, 360, 200, 30); // Reservations
        LogoutButton.setBounds(-30, 420, 220, 30);
        // These are now handled in the panel layout below.
        //calling sacleimage:
        scaleImage1();  
        scaleImage2(); 
        scaleImage3(); 
        scaleImage4(); 
        scaleImage5(); 
        scaleImage6(); 

        // --- Dashboard Metrics Panel ---
        // Initialize count labels
        reservationCountLabel = new JLabel("0");
        reservationCountLabel.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 28));
        reservationCountLabel.setForeground(new java.awt.Color(52, 58, 64));
        
        assignedFlightsCountLabel = new JLabel("0");
        assignedFlightsCountLabel.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 28));
        assignedFlightsCountLabel.setForeground(new java.awt.Color(52, 58, 64));
        
        walkInReservationsCountLabel = new JLabel("0");
        walkInReservationsCountLabel.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 28));
        walkInReservationsCountLabel.setForeground(new java.awt.Color(52, 58, 64));
        
        // Create metric cards
        MetricCardPanel reservationCard = createMetricCard(
            "/ImagePicker/CheckIn.png",
            "Reservations",
            reservationCountLabel
        );
        
        MetricCardPanel assignedFlightsCard = createMetricCard(
            "/ImagePicker/Flight.png",
            "Assigned Flights",
            assignedFlightsCountLabel
        );
        
        MetricCardPanel walkInReservationsCard = createMetricCard(
            "/ImagePicker/Book.png",
            "Walk-in Reservations",
            walkInReservationsCountLabel
        );
        
        // Create metrics panel with all cards
        javax.swing.JPanel metricsPanel = new javax.swing.JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 20, 20));
        metricsPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 20, 10, 20));
        metricsPanel.setBackground(new java.awt.Color(248, 249, 250));
        metricsPanel.add(reservationCard);
        metricsPanel.add(assignedFlightsCard);
        metricsPanel.add(walkInReservationsCard);
        
        EmpDashboardPanel.setLayout(new java.awt.BorderLayout());
        EmpDashboardPanel.add(metricsPanel, java.awt.BorderLayout.NORTH);

        // Now load dashboard metrics (after labels are initialized)
        controller.loadDashboardMetrics();

        styleFlatHoverButton(EmpReservationButton); // Add hover effect to Reservations button
    }
    // Code to scale images:
    private void scaleImage1(){
        ImageIcon icon1 = new ImageIcon(getClass().getResource("/ImagePicker/WingsNepalLogo.jpg"));
        //scaling image to fit in the hlabel.
        Image img1 = icon1.getImage();
        Image imgScale1 = img1.getScaledInstance(EmpBgDashboardIcon.getWidth(), EmpBgDashboardIcon.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon scaledIcon1 = new ImageIcon(imgScale1);
        EmpBgDashboardIcon.setIcon(scaledIcon1);
    }
    
    private void scaleImage2(){
        ImageIcon icon2 = new ImageIcon(getClass().getResource("/ImagePicker/Dashboard.png"));
        //scaling image to fit in the hlabel.
        Image img2 = icon2.getImage();
        Image imgScale2 = img2.getScaledInstance(EmpDashboardIcon.getWidth(), EmpDashboardIcon.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon scaledIcon2 = new ImageIcon(imgScale2);
        EmpDashboardIcon.setIcon(scaledIcon2);
    }
        
    private void scaleImage3(){
            ImageIcon icon3 = new ImageIcon(getClass().getResource("/ImagePicker/Flight.png"));
            //scaling image to fit in the hlabel.
            Image img3 = icon3.getImage();
            Image imgScale3 = img3.getScaledInstance(EmpFlightsIcon.getWidth(), EmpFlightsIcon.getHeight(), Image.SCALE_SMOOTH);
            ImageIcon scaledIcon3 = new ImageIcon(imgScale3);
            EmpFlightsIcon.setIcon(scaledIcon3);
    }
    private void scaleImage4(){
        ImageIcon icon4 = new ImageIcon(getClass().getResource("/ImagePicker/Passenger.png"));
        //scaling image to fit in the hlabel.
        Image img4 = icon4.getImage();
        Image imgScale4 = img4.getScaledInstance(EmpPassengerIcon.getWidth(), EmpPassengerIcon.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon scaledIcon4 = new ImageIcon(imgScale4);
        EmpPassengerIcon.setIcon(scaledIcon4);
    }  
    private void scaleImage5(){
        ImageIcon icon5 = new ImageIcon(getClass().getResource("/ImagePicker/Reservation.png"));
        //scaling image to fit in the hlabel.
        Image img5 = icon5.getImage();
        Image imgScale5 = img5.getScaledInstance(EmpReservationIcon.getWidth(), EmpReservationIcon.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon scaledIcon5 = new ImageIcon(imgScale5);
        EmpReservationIcon.setIcon(scaledIcon5);
    }  
    private void scaleImage6(){
        ImageIcon icon6 = new ImageIcon(getClass().getResource("/ImagePicker/Logout.png"));
        //scaling image to fit in the hlabel.
        Image img6 = icon6.getImage();
        Image imgScale6 = img6.getScaledInstance(EmpLogoutIcon.getWidth(), EmpLogoutIcon.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon scaledIcon6 = new ImageIcon(imgScale6);
        EmpLogoutIcon.setIcon(scaledIcon6);
    }         
 
    public EmployeeDashboard() {
        initComponents();
        setTitle("Employee Portal");
        setLocationRelativeTo(null);
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTextField1 = new javax.swing.JTextField();
        jRadioButton1 = new javax.swing.JRadioButton();
        jComboBox1 = new javax.swing.JComboBox<>();
        EmpButtonsPanel = new javax.swing.JPanel();
        EmpDashboardIcon = new javax.swing.JLabel();
        EmpFlightsIcon = new javax.swing.JLabel();
        EmpPassengerIcon = new javax.swing.JLabel();
        EmpReservationIcon = new javax.swing.JLabel();
        EmpLogoutIcon = new javax.swing.JLabel();
        EmpDashboardButton = new javax.swing.JButton();
        EmpFlightsButton = new javax.swing.JButton();
        EmpPassengerButton = new javax.swing.JButton();
        EmpReservationButton = new javax.swing.JButton();
        LogoutButton = new javax.swing.JButton();
        EmpBgDashboardIcon = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        EmpDashboardPanel = new javax.swing.JPanel();
        EmpFlightsPanel = new javax.swing.JPanel();
        jTextField6 = new javax.swing.JTextField();
        jComboBox3 = new javax.swing.JComboBox<>();
        jButton9 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        EmpPassengerPanel = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jButton2 = new javax.swing.JButton();
        jComboBox4 = new javax.swing.JComboBox<>();
        jTextField2 = new javax.swing.JTextField();
        EmpCheckinpanel = new javax.swing.JPanel();
        jTextField10 = new javax.swing.JTextField();
        jComboBox2 = new javax.swing.JComboBox<>();
        jButton14 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();

        jTextField1.setText("jTextField1");

        jRadioButton1.setText("jRadioButton1");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        EmpButtonsPanel.setBackground(new java.awt.Color(45, 62, 80));
        EmpButtonsPanel.setForeground(new java.awt.Color(45, 62, 80));
        EmpButtonsPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        EmpDashboardIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ImagePicker/dashbord.png"))); // NOI18N
        EmpButtonsPanel.add(EmpDashboardIcon, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 150, 20, 20));

        EmpFlightsIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ImagePicker/Flight.png"))); // NOI18N
        EmpButtonsPanel.add(EmpFlightsIcon, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 220, 20, 20));
        EmpButtonsPanel.add(EmpPassengerIcon, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 290, 20, 20));

        EmpReservationIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ImagePicker/Reservation.png"))); // NOI18N
        EmpButtonsPanel.add(EmpReservationIcon, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 360, 20, 20));
        EmpButtonsPanel.add(EmpLogoutIcon, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 420, 20, 20));

        EmpDashboardButton.setBackground(new java.awt.Color(45, 62, 80));
        EmpDashboardButton.setFont(new java.awt.Font("Segoe UI Emoji", 1, 18)); // NOI18N
        EmpDashboardButton.setForeground(new java.awt.Color(255, 255, 255));
        EmpDashboardButton.setText("Dashbord ");
        EmpDashboardButton.setBorderPainted(false);
        EmpDashboardButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        EmpDashboardButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EmpDashboardButtonActionPerformed(evt);
            }
        });
        EmpButtonsPanel.add(EmpDashboardButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(-20, 150, 220, 30));

        EmpFlightsButton.setBackground(new java.awt.Color(45, 62, 80));
        EmpFlightsButton.setFont(new java.awt.Font("Segoe UI Emoji", 1, 18)); // NOI18N
        EmpFlightsButton.setForeground(new java.awt.Color(255, 255, 255));
        EmpFlightsButton.setText("Assigned Flights");
        EmpFlightsButton.setBorderPainted(false);
        EmpFlightsButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        EmpFlightsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EmpFlightsButtonActionPerformed(evt);
            }
        });
        EmpButtonsPanel.add(EmpFlightsButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(-10, 220, 250, 30));

        EmpPassengerButton.setBackground(new java.awt.Color(45, 62, 80));
        EmpPassengerButton.setFont(new java.awt.Font("Segoe UI Emoji", 1, 18)); // NOI18N
        EmpPassengerButton.setForeground(new java.awt.Color(255, 255, 255));
        EmpPassengerButton.setText("Passenger List");
        EmpPassengerButton.setBorderPainted(false);
        EmpPassengerButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        EmpPassengerButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EmpPassengerButtonActionPerformed(evt);
            }
        });
        EmpButtonsPanel.add(EmpPassengerButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 290, 210, 30));

        EmpReservationButton.setBackground(new java.awt.Color(45, 62, 80));
        EmpReservationButton.setFont(new java.awt.Font("Segoe UI Emoji", 1, 18)); // NOI18N
        EmpReservationButton.setForeground(new java.awt.Color(255, 255, 255));
        EmpReservationButton.setText("Reservations");
        EmpReservationButton.setBorder(null);
        EmpButtonsPanel.add(EmpReservationButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 360, 200, 30));

        LogoutButton.setBackground(new java.awt.Color(45, 62, 80));
        LogoutButton.setFont(new java.awt.Font("Segoe UI Emoji", 1, 18)); // NOI18N
        LogoutButton.setForeground(new java.awt.Color(255, 255, 255));
        LogoutButton.setText("Logout");
        LogoutButton.setBorder(null);
        LogoutButton.setBorderPainted(false);
        LogoutButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        LogoutButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LogoutButtonActionPerformed(evt);
            }
        });
        EmpButtonsPanel.add(LogoutButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(-30, 420, 220, 30));

        EmpBgDashboardIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagepicker/WingsNepalLogo.jpg"))); // NOI18N
        EmpButtonsPanel.add(EmpBgDashboardIcon, new org.netbeans.lib.awtextra.AbsoluteConstraints(-30, -40, 250, 220));

        getContentPane().add(EmpButtonsPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 190, 605));

        jTabbedPane1.setBackground(new java.awt.Color(255, 255, 255));
        jTabbedPane1.setForeground(new java.awt.Color(255, 255, 255));
        jTabbedPane1.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);

        EmpDashboardPanel.setBackground(new java.awt.Color(255, 255, 255));
        EmpDashboardPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jTabbedPane1.addTab("", EmpDashboardPanel);

        EmpFlightsPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        EmpFlightsPanel.add(jTextField6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 190, 30));

        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Flight Code", "From", "To", " ", " " }));
        EmpFlightsPanel.add(jComboBox3, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 50, 190, 30));

        jButton9.setText("Search Flights");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });
        EmpFlightsPanel.add(jButton9, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 50, 190, 30));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Flight Code", "From", "To ", "Date", "Action"
            }
        ));
        jScrollPane2.setViewportView(jTable1);

        EmpFlightsPanel.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 800, 530));

        jTabbedPane1.addTab("Assigned flights ", EmpFlightsPanel);

        EmpPassengerPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Passenger name", "Passenger Id", "Date", "Flight Code", "Seat No", "Seat Class", "Action"
            }
        ));
        jScrollPane3.setViewportView(jTable2);

        EmpPassengerPanel.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, 800, 520));

        jButton2.setText("Search Passenger");
        EmpPassengerPanel.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 60, 150, 30));

        jComboBox4.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Passenger Name", "Fligh Code", "Seat Class", " " }));
        EmpPassengerPanel.add(jComboBox4, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 60, 170, 30));
        EmpPassengerPanel.add(jTextField2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 180, 30));

        jTabbedPane1.addTab("Bookings", EmpPassengerPanel);

        EmpCheckinpanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTextField10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField10ActionPerformed(evt);
            }
        });
        EmpCheckinpanel.add(jTextField10, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 200, 30));

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Check-in", "Check-out" }));
        jComboBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox2ActionPerformed(evt);
            }
        });
        EmpCheckinpanel.add(jComboBox2, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 50, 200, 30));

        jButton14.setText("Search");
        jButton14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton14ActionPerformed(evt);
            }
        });
        EmpCheckinpanel.add(jButton14, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 50, 210, 30));

        jTable3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Passenger Id", "Passenger Name", "Flight Name", "Flight Code", "Departure Time", "Action"
            }
        ));
        jScrollPane1.setViewportView(jTable3);

        EmpCheckinpanel.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 800, 530));

        jTabbedPane1.addTab("Check In", EmpCheckinpanel);

        getContentPane().add(jTabbedPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(189, -45, 810, 650));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void EmpDashboardButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EmpDashboardButtonActionPerformed
        // TODO add your handling code here:
        jTabbedPane1.setSelectedIndex(0);
       
    }//GEN-LAST:event_EmpDashboardButtonActionPerformed
    
    private void EmpFlightsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EmpFlightsButtonActionPerformed
        // TODO add your handling code here:
        jTabbedPane1.setSelectedIndex(1);
        // Load assigned flights when tab is selected
        if (controller != null) {
            controller.loadAssignedFlightsTable();
        }
    }//GEN-LAST:event_EmpFlightsButtonActionPerformed

    private void EmpPassengerButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EmpPassengerButtonActionPerformed
        // TODO add your handling code here:
        jTabbedPane1.setSelectedIndex(2);
    }//GEN-LAST:event_EmpPassengerButtonActionPerformed

    private void LogoutButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LogoutButtonActionPerformed
        // Display a confirmation dialog
        int choice = javax.swing.JOptionPane.showConfirmDialog(
            this,
            "Are you sure you want to log out?",
            "Logout Confirmation",
            javax.swing.JOptionPane.YES_NO_OPTION,
            javax.swing.JOptionPane.QUESTION_MESSAGE
        );

        // Check if the user clicked "Yes"
        if (choice == javax.swing.JOptionPane.YES_OPTION) {
            // Close the current EmployeeDashboard window
            this.dispose();

            // Open the LoginPage
            new LoginPage().setVisible(true);
        }
    }//GEN-LAST:event_LogoutButtonActionPerformed

    private void jComboBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox2ActionPerformed
        // TODO add your handling code here:
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Checkin", "Checkout" }));

    }//GEN-LAST:event_jComboBox2ActionPerformed

    private void jTextField10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField10ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField10ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        // Search functionality for assigned flights
        if (controller != null) {
            String searchText = jTextField6.getText().trim();
            String searchCriteria = (String) jComboBox3.getSelectedItem();
            controller.searchAssignedFlights(searchText, searchCriteria);
        }
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton14ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton14ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            UserDataModel dummy = new UserDataModel(1, "Employee", "employee@wingsnepal.com", "Employee");
            new EmployeeDashboard(dummy).setVisible(true);
        });
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel EmpBgDashboardIcon;
    private javax.swing.JPanel EmpButtonsPanel;
    private javax.swing.JPanel EmpCheckinpanel;
    private javax.swing.JButton EmpDashboardButton;
    private javax.swing.JLabel EmpDashboardIcon;
    private javax.swing.JPanel EmpDashboardPanel;
    private javax.swing.JButton EmpFlightsButton;
    private javax.swing.JLabel EmpFlightsIcon;
    private javax.swing.JPanel EmpFlightsPanel;
    private javax.swing.JLabel EmpLogoutIcon;
    private javax.swing.JButton EmpPassengerButton;
    private javax.swing.JLabel EmpPassengerIcon;
    private javax.swing.JPanel EmpPassengerPanel;
    private javax.swing.JButton EmpReservationButton;
    private javax.swing.JLabel EmpReservationIcon;
    private javax.swing.JButton LogoutButton;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton9;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JComboBox<String> jComboBox4;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField10;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField6;
    // End of variables declaration//GEN-END:variables

    // This method styles buttons to look flat and modern, and adds hover effects for better user experience.
    // It is called after init components to give for which button it is applicable.
    private void styleFlatHoverButton(javax.swing.JButton button) {
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setOpaque(false);

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setContentAreaFilled(true);
                button.setBackground(new java.awt.Color(173,216,230)); 
                button.setForeground(java.awt.Color.WHITE); 
                button.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setContentAreaFilled(false);
                button.setForeground(java.awt.Color.WHITE); 
            }
        });
    }

    // New method to style search buttons
    private void styleSearchButton(javax.swing.JButton button) {
        button.setBackground(new java.awt.Color(0, 122, 204));
        button.setForeground(java.awt.Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12));
        button.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new java.awt.Color(0, 102, 184)); // Darker blue on hover
                button.setForeground(java.awt.Color.WHITE); 
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new java.awt.Color(0, 122, 204)); // Original blue
                button.setForeground(java.awt.Color.WHITE); 
            }
        });
    }

    public javax.swing.JButton getEmpDashboardButton() { return EmpDashboardButton; }
    public javax.swing.JButton getEmpFlightsButton() { return EmpFlightsButton; }
    public javax.swing.JButton getEmpPassengerButton() { return EmpPassengerButton; }
    public javax.swing.JButton getLogoutButton() { return LogoutButton; }
    public javax.swing.JButton getReservationsButton() { return EmpReservationButton; }
    public javax.swing.JTabbedPane getTabbedPane() { return jTabbedPane1; }
    public javax.swing.JTable getJTable3() { return jTable3; }
    public javax.swing.JTable getJTable1() { return jTable1; }
    
    // Accessor methods for flights functionality (matching admin dashboard pattern)
    public javax.swing.JButton getSearchFlightjButton() { return jButton9; }
    public javax.swing.JTextField getSearchFlightsTextField() { return jTextField6; }
    public javax.swing.JComboBox<String> getSearchFlightsComboBox() { return jComboBox3; }

    public void setController(controller.EmployeeDashboardController controller) {
        this.controller = controller;
    }

    // Helper to create a metric card (copied from AdminDashboard)
    private MetricCardPanel createMetricCard(String iconPath, String title, JLabel numberLabel) {
        MetricCardPanel card = new MetricCardPanel();
        card.setLayout(new java.awt.BorderLayout(15, 0));
        card.setPreferredSize(new java.awt.Dimension(220, 100));
        card.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 15, 10, 15));
        try {
            ImageIcon originalIcon = new ImageIcon(getClass().getResource(iconPath));
            Image scaledImage = originalIcon.getImage().getScaledInstance(48, 48, Image.SCALE_SMOOTH);
            JLabel iconLabel = new JLabel(new ImageIcon(scaledImage));
            card.add(iconLabel, java.awt.BorderLayout.WEST);
        } catch (Exception e) {
            e.printStackTrace();
            card.add(new JLabel(), java.awt.BorderLayout.WEST);
        }
        javax.swing.JPanel textPanel = new javax.swing.JPanel(new java.awt.GridLayout(2, 1));
        textPanel.setOpaque(false);
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 16));
        titleLabel.setForeground(new java.awt.Color(108, 117, 125));
        numberLabel.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 28));
        numberLabel.setForeground(new java.awt.Color(52, 58, 64));
        textPanel.add(titleLabel);
        textPanel.add(numberLabel);
        card.add(textPanel, java.awt.BorderLayout.CENTER);
        return card;
    }

    // Passenger List tab getters for walk-in customer booking management
    public javax.swing.JTable getJTable2() { return jTable2; }
    public javax.swing.JButton getShowWalkInBookingsButton() { return jButton2; }
    public javax.swing.JTextField getWalkInSearchTextField() { return jTextField2; }
    public javax.swing.JComboBox<String> getWalkInSearchComboBox() { return jComboBox4; }
    
    // Getter methods for dashboard metrics
    public JLabel getReservationCountLabel() { return reservationCountLabel; }
    public JLabel getAssignedFlightsCountLabel() { return assignedFlightsCountLabel; }
    public JLabel getWalkInReservationsCountLabel() { return walkInReservationsCountLabel; }
}
