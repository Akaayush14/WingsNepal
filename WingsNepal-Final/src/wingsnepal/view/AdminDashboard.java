/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package wingsnepal.view;

import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import wingsnepal.model.UserDataModel;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.Component;
import java.awt.Color;
import wingsnepal.view.EmployeeActionButtonRenderer;
import wingsnepal.view.EmployeeActionButtonEditor;
import wingsnepal.view.EmployeeStatusCellRenderer;

public class AdminDashboard extends javax.swing.JFrame {
    private UserDataModel loggedInUser;
    private JLabel totalFlightsLabel;
    private JLabel availableSeatsLabel;
    private JLabel totalPassengersLabel;
    private JLabel totalEmployeesLabel;
    private JLabel totalUsersLabel;
    private JLabel totalBookingsLabel;
    private controller.AdminDashboardController controllerRef;

    
    public AdminDashboard(UserDataModel user) {
        this.loggedInUser = user;
        initComponents();
        
        // Set combo box options
        EmployeeComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(
            new String[] { "Employee ID", "Full Name", "Email", "Phone" }
        ));
        
        DefaultTableModel model = new DefaultTableModel(new String[]{"Employee ID", "Full Name", "Email", "Phone", "Job Title", "Salary", "Gender", "Status", "Actions"}, 0) {
            public boolean isCellEditable(int row, int column) {
                // Only the Actions column is editable (for buttons)
                return column == 8;
            }
        };

        employeeTable.setModel(model);
        // Add custom renderer and editor for Actions column
        employeeTable.getColumnModel().getColumn(8).setCellRenderer(new EmployeeActionButtonRenderer());
        employeeTable.getColumnModel().getColumn(8).setCellEditor(
            new EmployeeActionButtonEditor(employeeTable, new EmployeeActionButtonEditor.EmployeeActionCallback() {
                @Override
                public void onEdit(int row) {
                    // Call controller's edit method
                    if (controllerRef != null) controllerRef.handleEditEmployee(row);
                }
                @Override
                public void onDelete(int row) {
                    if (controllerRef != null) controllerRef.handleDeleteEmployee(row);
                }
            })
        );
        employeeTable.setRowHeight(32);
        
        // Set column widths for better layout
        employeeTable.getColumnModel().getColumn(0).setPreferredWidth(80);  // Employee ID
        employeeTable.getColumnModel().getColumn(1).setPreferredWidth(150); // Full Name
        employeeTable.getColumnModel().getColumn(2).setPreferredWidth(200); // Email
        employeeTable.getColumnModel().getColumn(3).setPreferredWidth(120); // Phone
        employeeTable.getColumnModel().getColumn(4).setPreferredWidth(120); // Job Title
        employeeTable.getColumnModel().getColumn(5).setPreferredWidth(80);  // Salary
        employeeTable.getColumnModel().getColumn(6).setPreferredWidth(80);  // Gender
        employeeTable.getColumnModel().getColumn(7).setPreferredWidth(80);  // Status
        employeeTable.getColumnModel().getColumn(8).setPreferredWidth(180); // Actions (increased size)
        employeeTable.getColumnModel().getColumn(8).setMinWidth(180);       // Minimum width for Actions
        employeeTable.getColumnModel().getColumn(8).setMaxWidth(200);       // Maximum width for Actions
        
        // loadEmployeeTable(); // REMOVED - now handled by controller

        //scaling image calling:
        scaleImage1();
        scaleImage2();
        scaleImage3();
        scaleImage4();
        scaleImage5();
        scaleImage6();
        scaleImage7();
        

        //calling setupplaceholder:
        setupPlaceholders();
        setupPlaceholders1();
        setupFlightSearchPlaceholder();
        // setupUsersSearchPlaceholder(); // Will be set up after creating Users panel
        
        // Initialize metric labels before creating the cards
        totalFlightsLabel = new JLabel("0");
        availableSeatsLabel = new JLabel("0");
        totalPassengersLabel = new JLabel("0");
        totalEmployeesLabel = new JLabel("0");
        totalUsersLabel = new JLabel("0");
        totalBookingsLabel = new JLabel("0");
        
        // Create and add the metrics panel programmatically
        JPanel metricsPanel = new JPanel(new java.awt.GridLayout(2, 3, 20, 20)); // 2 rows, 3 columns
        metricsPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Add padding
        DasboardPanel.setBackground(new java.awt.Color(248, 249, 250)); // Light gray background
        metricsPanel.setBackground(DasboardPanel.getBackground());

        // Total Flights Card
        MetricCardPanel flightsCard = createMetricCard(
            "/imagepicker/aeroplane.jpg", // Ensure this icon exists
            "Total Flights",
            totalFlightsLabel);
        metricsPanel.add(flightsCard);

        // Available Seats Card
        MetricCardPanel seatsCard = createMetricCard(
            "/imagepicker/Book.png", 
            "Available Seats",
            availableSeatsLabel);
        metricsPanel.add(seatsCard);

        // Total Passengers Card
        MetricCardPanel passengersCard = createMetricCard(
            "/imagepicker/Passenger.png", // Use the new Passenger.png icon
            "Passenger List",
            totalPassengersLabel);
        metricsPanel.add(passengersCard);

        // Total Employees Card
        MetricCardPanel employeesCard = createMetricCard(
            "/imagepicker/Employees.png", 
            "Total Employees",
            totalEmployeesLabel);
        metricsPanel.add(employeesCard);

        // Total Users Card
        MetricCardPanel usersCard = createMetricCard(
            "/imagepicker/user.png", 
            "Total Users",
            totalUsersLabel);
        metricsPanel.add(usersCard);

        // Total Bookings Card
        MetricCardPanel bookingsCard = createMetricCard(
            "/imagepicker/Book.png", 
            "Total Bookings",
            totalBookingsLabel);
        metricsPanel.add(bookingsCard);

        DasboardPanel.setLayout(new java.awt.BorderLayout());
        DasboardPanel.add(metricsPanel, java.awt.BorderLayout.NORTH);
        
        // Load flights table
        // loadFlightsTable(); // REMOVED - now handled by controller

        // Load dashboard metrics
        // loadDashboardMetrics(); // REMOVED - now handled by controller

        setTitle("Admin Dashboard - Welcome" + user.getFullName());
        setLocationRelativeTo(null);
        
        // Adding hovering effect to buttons to make it look modern.
        styleFlatHoverButton(DashboardButton);
        styleFlatHoverButton(FlightButton);
        styleFlatHoverButton(PassengerButton);
        styleFlatHoverButton(Logoutbutton);
        styleFlatHoverButton(EmployeeButton);
        styleFlatHoverButton(UserButton);
        
        // btnAddEmployee.addActionListener(e -> { ... }); // REMOVED - now handled by controller
        // btnAddReservation.addActionListener(e -> { ... }); // REMOVED - now handled by controller

        // Use custom renderer and editor for Status column in jTable3


        // Add custom renderer for Status column (index 7 - after adding Phone column)
        employeeTable.getColumnModel().getColumn(7).setCellRenderer(new EmployeeStatusCellRenderer());
        
        // Create Users panel programmatically
        createUsersPanel();
    }
    
    public void setController(controller.AdminDashboardController controller) {
        this.controllerRef = controller;
    }
    
        private void scaleImage1(){
            ImageIcon icon1 = new ImageIcon(getClass().getResource("/imagepicker/WingsNepalLogo.jpg"));
            //scaling image to fit in the hlabel.
            Image img1 = icon1.getImage();
            Image imgScale1 = img1.getScaledInstance(WingsNepalLogo.getWidth(), WingsNepalLogo.getHeight(), Image.SCALE_SMOOTH);
            ImageIcon scaledIcon1 = new ImageIcon(imgScale1);
            WingsNepalLogo.setIcon(scaledIcon1);
        }
        
        private void scaleImage2(){
            ImageIcon icon2 = new ImageIcon(getClass().getResource("/imagepicker/Dashboard.png"));
            //scaling image to fit in the hlabel.
            Image img2 = icon2.getImage();
            Image imgScale2 = img2.getScaledInstance(DashboardIcon.getWidth(), DashboardIcon.getHeight(), Image.SCALE_SMOOTH);
            ImageIcon scaledIcon2 = new ImageIcon(imgScale2);
            DashboardIcon.setIcon(scaledIcon2);
        }
        private void scaleImage3(){
            ImageIcon icon3 = new ImageIcon(getClass().getResource("/imagepicker/Logout.png"));
            //scaling image to fit in the hlabel.
            Image img3 = icon3.getImage();
            Image imgScale3 = img3.getScaledInstance(LogoutIcon.getWidth(), LogoutIcon.getHeight(), Image.SCALE_SMOOTH);
            ImageIcon scaledIcon3 = new ImageIcon(imgScale3);
            LogoutIcon.setIcon(scaledIcon3);
        }
        
        private void scaleImage4(){
            ImageIcon icon4 = new ImageIcon(getClass().getResource("/imagepicker/Flight.png"));
            //scaling image to fit in the hlabel.
            Image img4 = icon4.getImage();
            Image imgScale4 = img4.getScaledInstance(FlightsIcon.getWidth(), FlightsIcon.getHeight(), Image.SCALE_SMOOTH);
            ImageIcon scaledIcon4 = new ImageIcon(imgScale4);
            FlightsIcon.setIcon(scaledIcon4);
        }
        
        private void scaleImage5(){
            ImageIcon icon5 = new ImageIcon(getClass().getResource("/imagepicker/Passenger.png"));
            //scaling image to fit in the hlabel.
            Image img5 = icon5.getImage();
            Image imgScale5 = img5.getScaledInstance(PassengerIcon.getWidth(), PassengerIcon.getHeight(), Image.SCALE_SMOOTH);
            ImageIcon scaledIcon5 = new ImageIcon(imgScale5);
            PassengerIcon.setIcon(scaledIcon5);
        }
        
        private void scaleImage6(){
             ImageIcon icon6 = new ImageIcon(getClass().getResource("/imagepicker/Employees.png"));
             //scaling image to fit in the hlabel.
             Image img6 = icon6.getImage();
             Image imgScale6 = img6.getScaledInstance(EmployeeIcon.getWidth(), EmployeeIcon.getHeight(), Image.SCALE_SMOOTH);
             ImageIcon scaledIcon6 = new ImageIcon(imgScale6);
             EmployeeIcon.setIcon(scaledIcon6);
        }
        
        private void scaleImage7(){
             ImageIcon icon7 = new ImageIcon(getClass().getResource("/imagepicker/user.png"));
             //scaling image to fit in the hlabel.
             Image img7 = icon7.getImage();
             Image imgScale7 = img7.getScaledInstance(UserIcon.getWidth(), UserIcon.getHeight(), Image.SCALE_SMOOTH);
             ImageIcon scaledIcon7 = new ImageIcon(imgScale7);
             UserIcon.setIcon(scaledIcon7);
        }
        
        private void setupPlaceholders(){
        String placeholderFrom = "Search Employees";
        SearchTextField.setForeground(java.awt.Color.GRAY);
        SearchTextField.setText(placeholderFrom);
        SearchTextField.addFocusListener(new java.awt.event.FocusAdapter() {
        public void focusGained(java.awt.event.FocusEvent evt) {
            if (SearchTextField.getText().equals(placeholderFrom)) {
                SearchTextField.setText("");
                SearchTextField.setForeground(java.awt.Color.BLACK);
            }
        }
        public void focusLost(java.awt.event.FocusEvent evt) {
            if (SearchTextField.getText().isEmpty()) {
                SearchTextField.setForeground(java.awt.Color.GRAY);
                SearchTextField.setText(placeholderFrom);
            }
        }
    });
}
        private void setupPlaceholders1(){
        String placeholderFrom = "Search Passengers";
        SearchPassengerTextField.setForeground(java.awt.Color.GRAY);
        SearchPassengerTextField.setText(placeholderFrom);
        SearchPassengerTextField.addFocusListener(new java.awt.event.FocusAdapter() {
        public void focusGained(java.awt.event.FocusEvent evt) {
            if (SearchPassengerTextField.getText().equals(placeholderFrom)) {
                SearchPassengerTextField.setText("");
                SearchPassengerTextField.setForeground(java.awt.Color.BLACK);
            }
        }
        public void focusLost(java.awt.event.FocusEvent evt) {
            if (SearchPassengerTextField.getText().isEmpty()) {
                SearchPassengerTextField.setForeground(java.awt.Color.GRAY);
                SearchPassengerTextField.setText(placeholderFrom);
            }
        }
    });
}
        private void setupFlightSearchPlaceholder() {
            String placeholder = "Search Flights...";
            SearchFlightsTextField.setForeground(java.awt.Color.GRAY);
            SearchFlightsTextField.setText(placeholder);
            SearchFlightsTextField.addFocusListener(new java.awt.event.FocusAdapter() {
                @Override
                public void focusGained(java.awt.event.FocusEvent evt) {
                    if (SearchFlightsTextField.getText().equals(placeholder)) {
                        SearchFlightsTextField.setText("");
                        SearchFlightsTextField.setForeground(java.awt.Color.BLACK);
                    }
                }
                @Override
                public void focusLost(java.awt.event.FocusEvent evt) {
                    if (SearchFlightsTextField.getText().isEmpty()) {
                        SearchFlightsTextField.setForeground(java.awt.Color.GRAY);
                        SearchFlightsTextField.setText(placeholder);
                    }
                }
            });
        }

    // Users panel components will be created programmatically
    private javax.swing.JPanel UsersPanel;
    private javax.swing.JTable usersTable;
    private javax.swing.JTextField SearchUsersTextField;
    private javax.swing.JButton SearchUsersButton;
    private javax.swing.JComboBox<String> UsersComboBox;
    
    private void setupUsersSearchPlaceholder() {
        String placeholder = "Search User Accounts...";
        SearchUsersTextField.setForeground(java.awt.Color.GRAY);
        SearchUsersTextField.setText(placeholder);
        SearchUsersTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (SearchUsersTextField.getText().equals(placeholder)) {
                    SearchUsersTextField.setText("");
                    SearchUsersTextField.setForeground(java.awt.Color.BLACK);
                }
            }
            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (SearchUsersTextField.getText().isEmpty()) {
                    SearchUsersTextField.setForeground(java.awt.Color.GRAY);
                    SearchUsersTextField.setText(placeholder);
                }
            }
        });
    }
    
    private void createUsersPanel() {
        // Create Users panel
        UsersPanel = new javax.swing.JPanel();
        UsersPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        
        // Create inner panel for content
        javax.swing.JPanel usersContentPanel = new javax.swing.JPanel();
        usersContentPanel.setBackground(new java.awt.Color(255, 255, 255));
        usersContentPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        

        
        // Create search text field
        SearchUsersTextField = new javax.swing.JTextField();
        usersContentPanel.add(SearchUsersTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 166, 31));
        
        // Create combo box
        UsersComboBox = new javax.swing.JComboBox<>();
        UsersComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(
            new String[] { "User ID", "Full Name", "Email", "Phone", "Account Type" }
        ));
        usersContentPanel.add(UsersComboBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 50, 170, 30));
        
        // Create search button
        SearchUsersButton = new javax.swing.JButton();
        SearchUsersButton.setBackground(new java.awt.Color(0, 102, 153));
        SearchUsersButton.setForeground(new java.awt.Color(255, 255, 255));
        SearchUsersButton.setText("Search");
        usersContentPanel.add(SearchUsersButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 50, 180, 30));
        
        // Create table - Updated to include Phone and remove Job Title and Salary columns for regular users
        usersTable = new javax.swing.JTable();
        DefaultTableModel usersTableModel = new DefaultTableModel(
            new String[]{"User ID", "Full Name", "Email", "Phone", "Account Type", "Status", "Actions"}, 0) {
            public boolean isCellEditable(int row, int column) {
                return column == 6; // Only Actions column is editable (now index 6)
            }
        };
        usersTable.setModel(usersTableModel);
        usersTable.setRowHeight(32);
        
        // Create scroll pane for table
        javax.swing.JScrollPane usersScrollPane = new javax.swing.JScrollPane();
        usersScrollPane.setViewportView(usersTable);
        usersContentPanel.add(usersScrollPane, new org.netbeans.lib.awtextra.AbsoluteConstraints(4, 96, 830, 510));
        
        // Add content panel to Users panel
        UsersPanel.add(usersContentPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 830, 600));
        
        // Add Users panel as a new tab at index 4 (before logout panel)
        jTabbedPane1.insertTab("tab4", null, UsersPanel, null, 4);
        
        // Set up placeholder for search field
        setupUsersSearchPlaceholder();
        
        // Add action listeners
        SearchUsersButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SearchUsersButtonActionPerformed(evt);
            }
        });
        
        SearchUsersTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SearchUsersTextFieldActionPerformed(evt);
            }
        });
        
        UsersComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UsersComboBoxActionPerformed(evt);
            }
        });
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel5 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        DashboardIcon = new javax.swing.JLabel();
        FlightsIcon = new javax.swing.JLabel();
        PassengerIcon = new javax.swing.JLabel();
        EmployeeIcon = new javax.swing.JLabel();
        UserIcon = new javax.swing.JLabel();
        LogoutIcon = new javax.swing.JLabel();
        DashboardButton = new javax.swing.JButton();
        FlightButton = new javax.swing.JButton();
        PassengerButton = new javax.swing.JButton();
        EmployeeButton = new javax.swing.JButton();
        UserButton = new javax.swing.JButton();
        Logoutbutton = new javax.swing.JButton();
        WingsNepalLogo = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        FlightPanel = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        SearchFlightsTextField = new javax.swing.JTextField();
        SearchFlightjButton = new javax.swing.JButton();
        AddFlightsjButton = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable4 = new javax.swing.JTable();
        PassengerPanel = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        SearchPassengerButton = new javax.swing.JButton();
        SearchPassengerTextField = new javax.swing.JTextField();
        SeatClassTextField = new javax.swing.JComboBox<>();
        ShowAllButton = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        EmployeesPanel = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        employeeTable = new javax.swing.JTable();
        SearchTextField = new javax.swing.JTextField();
        btnAddEmployee = new javax.swing.JButton();
        SearchButton = new javax.swing.JButton();
        EmployeeComboBox = new javax.swing.JComboBox<>();
        AdminLogoutPanel = new javax.swing.JPanel();
        DasboardPanel = new javax.swing.JPanel();

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(46, 62, 79));
        jPanel2.setLayout(null);

        DashboardIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagepicker/Dashboard.png"))); // NOI18N
        jPanel2.add(DashboardIcon);
        DashboardIcon.setBounds(20, 130, 20, 20);

        FlightsIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagepicker/Flight.png"))); // NOI18N
        jPanel2.add(FlightsIcon);
        FlightsIcon.setBounds(10, 190, 30, 30);

        PassengerIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagepicker/Passenger.png"))); // NOI18N
        jPanel2.add(PassengerIcon);
        PassengerIcon.setBounds(10, 250, 30, 30);

        EmployeeIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagepicker/Employees.png"))); // NOI18N
        jPanel2.add(EmployeeIcon);
        EmployeeIcon.setBounds(7, 310, 30, 30);

        UserIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagepicker/user.png"))); // NOI18N
        jPanel2.add(UserIcon);
        UserIcon.setBounds(10, 370, 30, 30);

        LogoutIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagepicker/Logout.png"))); // NOI18N
        jPanel2.add(LogoutIcon);
        LogoutIcon.setBounds(10, 430, 30, 30);

        DashboardButton.setBackground(new java.awt.Color(46, 62, 79));
        DashboardButton.setFont(new java.awt.Font("Segoe UI Emoji", 1, 18)); // NOI18N
        DashboardButton.setForeground(new java.awt.Color(255, 255, 255));
        DashboardButton.setText("Dashboard");
        DashboardButton.setBorder(null);
        DashboardButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DashboardButtonActionPerformed(evt);
            }
        });
        jPanel2.add(DashboardButton);
        DashboardButton.setBounds(0, 130, 200, 31);

        FlightButton.setBackground(new java.awt.Color(46, 62, 79));
        FlightButton.setFont(new java.awt.Font("Segoe UI Emoji", 1, 18)); // NOI18N
        FlightButton.setForeground(new java.awt.Color(255, 255, 255));
        FlightButton.setText("Flights");
        FlightButton.setBorder(null);
        FlightButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FlightButtonActionPerformed(evt);
            }
        });
        jPanel2.add(FlightButton);
        FlightButton.setBounds(-30, 190, 230, 31);

        PassengerButton.setBackground(new java.awt.Color(46, 62, 79));
        PassengerButton.setFont(new java.awt.Font("Segoe UI Emoji", 1, 18)); // NOI18N
        PassengerButton.setForeground(new java.awt.Color(255, 255, 255));
        PassengerButton.setText("Passengers");
        PassengerButton.setBorder(null);
        PassengerButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PassengerButtonActionPerformed(evt);
            }
        });
        jPanel2.add(PassengerButton);
        PassengerButton.setBounds(0, 250, 200, 33);

        EmployeeButton.setBackground(new java.awt.Color(46, 62, 79));
        EmployeeButton.setFont(new java.awt.Font("Segoe UI Emoji", 1, 18)); // NOI18N
        EmployeeButton.setForeground(new java.awt.Color(255, 255, 255));
        EmployeeButton.setText("Employees");
        EmployeeButton.setBorder(null);
        EmployeeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EmployeeButtonActionPerformed(evt);
            }
        });
        jPanel2.add(EmployeeButton);
        EmployeeButton.setBounds(0, 310, 200, 33);

        UserButton.setBackground(new java.awt.Color(46, 62, 79));
        UserButton.setFont(new java.awt.Font("Segoe UI Emoji", 1, 18)); // NOI18N
        UserButton.setForeground(new java.awt.Color(255, 255, 255));
        UserButton.setText("Users");
        UserButton.setBorder(null);
        UserButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UsersButtonActionPerformed(evt);
            }
        });
        jPanel2.add(UserButton);
        UserButton.setBounds(-30, 370, 220, 30);

        Logoutbutton.setBackground(new java.awt.Color(46, 62, 79));
        Logoutbutton.setFont(new java.awt.Font("Segoe UI Emoji", 1, 18)); // NOI18N
        Logoutbutton.setForeground(new java.awt.Color(255, 255, 255));
        Logoutbutton.setText("Logout");
        Logoutbutton.setBorder(null);
        Logoutbutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LogoutbuttonActionPerformed(evt);
            }
        });
        jPanel2.add(Logoutbutton);
        Logoutbutton.setBounds(-20, 430, 220, 32);

        WingsNepalLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagepicker/WingsNepalLogo.jpg"))); // NOI18N
        jPanel2.add(WingsNepalLogo);
        WingsNepalLogo.setBounds(-30, -70, 220, 220);

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 170, 600));

        FlightPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));
        jPanel9.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        SearchFlightsTextField.setText("Search Flights");
        SearchFlightsTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SearchFlightsTextFieldActionPerformed(evt);
            }
        });
        jPanel9.add(SearchFlightsTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 166, 30));

        SearchFlightjButton.setBackground(new java.awt.Color(0, 102, 153));
        SearchFlightjButton.setForeground(new java.awt.Color(255, 255, 255));
        SearchFlightjButton.setText("Search");
        SearchFlightjButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SearchFlightjButtonActionPerformed(evt);
            }
        });
        jPanel9.add(SearchFlightjButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 60, 179, 30));

        AddFlightsjButton.setBackground(new java.awt.Color(0, 153, 102));
        AddFlightsjButton.setForeground(new java.awt.Color(255, 255, 255));
        AddFlightsjButton.setText("+  Add Flights");
        AddFlightsjButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddFlightsjButtonActionPerformed(evt);
            }
        });
        jPanel9.add(AddFlightsjButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 60, 166, 31));

        jTable4.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "S.N.", "Flight Code", "Flight Name", "Origin", "Destination", "Dep. Date", "Dep. Time", "Price", "Duration", "Actions"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.String.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane4.setViewportView(jTable4);

        jPanel9.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 100, 820, 510));

        FlightPanel.add(jPanel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 830, 600));

        // Add tabs in the correct order to match button indices
        // Index 0: Dashboard
        DasboardPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jTabbedPane1.addTab("tab1", DasboardPanel);
        
        // Index 1: Flight
        jTabbedPane1.addTab("tab2", FlightPanel);

        // Index 2: Passenger
        PassengerPanel.setPreferredSize(new java.awt.Dimension(1000, 600));
        PassengerPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));
        jPanel8.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        SearchPassengerButton.setBackground(new java.awt.Color(0, 102, 153));
        SearchPassengerButton.setForeground(new java.awt.Color(255, 255, 255));
        SearchPassengerButton.setText("Search");
        SearchPassengerButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SearchPassengerButtonActionPerformed(evt);
            }
        });
        jPanel8.add(SearchPassengerButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 50, 175, 30));

        SearchPassengerTextField.setText("Search");
        SearchPassengerTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SearchPassengerTextFieldActionPerformed(evt);
            }
        });
        jPanel8.add(SearchPassengerTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 181, 30));

        SeatClassTextField.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Name", "Phone", "Flight Code", "Seat Class", "Seat Number" }));
        SeatClassTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SeatClassTextFieldActionPerformed(evt);
            }
        });
        jPanel8.add(SeatClassTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 50, 170, 31));

        ShowAllButton.setBackground(new java.awt.Color(0, 102, 153));
        ShowAllButton.setForeground(new java.awt.Color(255, 255, 255));
        ShowAllButton.setText("Show All");
        ShowAllButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ShowAllButtonActionPerformed(evt);
            }
        });
        jPanel8.add(ShowAllButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 50, 170, 30));

        jTable3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Full Name", "Email", "Status", "Seat Class", "Flight Code", "Relationship", "Actions"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane3.setViewportView(jTable3);

        jPanel8.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 93, 820, 520));

        PassengerPanel.add(jPanel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(-4, 0, 840, 610));

        jTabbedPane1.addTab("tab3", PassengerPanel);

        // Index 3: Employee
        EmployeesPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        employeeTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Employee ID", "Full Name", "Email", "Phone", "Job Title", "Salary", "Gender", "Status", "Actions"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Double.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane1.setViewportView(employeeTable);

        jPanel4.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(4, 86, 830, 520));

        SearchTextField.setText("Search Employee");
        SearchTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SearchTextFieldActionPerformed(evt);
            }
        });
        jPanel4.add(SearchTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 166, 31));

        btnAddEmployee.setBackground(new java.awt.Color(0, 153, 102));
        btnAddEmployee.setForeground(new java.awt.Color(255, 255, 255));
        btnAddEmployee.setText("+   Add Employee");
        btnAddEmployee.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddEmployeeActionPerformed(evt);
            }
        });
        jPanel4.add(btnAddEmployee, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 40, 180, 32));

        SearchButton.setBackground(new java.awt.Color(0, 102, 153));
        SearchButton.setForeground(new java.awt.Color(255, 255, 255));
        SearchButton.setText("Search");
        SearchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SearchButtonActionPerformed(evt);
            }
        });
        jPanel4.add(SearchButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 40, 180, 30));

        EmployeeComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Employee ID", "Full Name", "Email", "Phone" }));
        EmployeeComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EmployeeComboBoxActionPerformed(evt);
            }
        });
        jPanel4.add(EmployeeComboBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 40, 170, 30));

        EmployeesPanel.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 830, 600));

        jTabbedPane1.addTab("tab5", EmployeesPanel);
        
        // Index 4: Logout (will be index 5 after Users tab is inserted)
        jTabbedPane1.addTab("tab6", AdminLogoutPanel);

        getContentPane().add(jTabbedPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, -40, 830, 640));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void PassengerButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PassengerButtonActionPerformed
        jTabbedPane1.setSelectedIndex(2);
    }//GEN-LAST:event_PassengerButtonActionPerformed

    private void DashboardButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DashboardButtonActionPerformed
        jTabbedPane1.setSelectedIndex(0);
    }//GEN-LAST:event_DashboardButtonActionPerformed

    private void LogoutbuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LogoutbuttonActionPerformed

    }//GEN-LAST:event_LogoutbuttonActionPerformed

    private void EmployeeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EmployeeButtonActionPerformed
        jTabbedPane1.setSelectedIndex(3);
    }//GEN-LAST:event_EmployeeButtonActionPerformed

    private void UsersButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UsersButtonActionPerformed
        jTabbedPane1.setSelectedIndex(4);
    }//GEN-LAST:event_UsersButtonActionPerformed

    private void FlightButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FlightButtonActionPerformed
        jTabbedPane1.setSelectedIndex(1);
    }//GEN-LAST:event_FlightButtonActionPerformed

    private void EmployeeComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EmployeeComboBoxActionPerformed
        
    }//GEN-LAST:event_EmployeeComboBoxActionPerformed

    private void SearchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SearchButtonActionPerformed
        // handleSearchEmployees(); // REMOVED - now handled by controller
    }//GEN-LAST:event_SearchButtonActionPerformed

    private void btnAddEmployeeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddEmployeeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAddEmployeeActionPerformed

    private void SearchTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SearchTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_SearchTextFieldActionPerformed

    private void ShowAllButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ShowAllButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ShowAllButtonActionPerformed

    private void SeatClassTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SeatClassTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_SeatClassTextFieldActionPerformed

    private void SearchPassengerTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SearchPassengerTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_SearchPassengerTextFieldActionPerformed

    private void SearchPassengerButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SearchPassengerButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_SearchPassengerButtonActionPerformed

    private void SearchFlightsTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SearchFlightsTextFieldActionPerformed
        // handleSearchFlights(); // REMOVED - now handled by controller
    }//GEN-LAST:event_SearchFlightsTextFieldActionPerformed

    private void SearchFlightjButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SearchFlightjButtonActionPerformed
        // handleSearchFlights(); // REMOVED - now handled by controller
    }//GEN-LAST:event_SearchFlightjButtonActionPerformed

    private void AddFlightsjButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddFlightsjButtonActionPerformed
        // handleAddFlight(); // REMOVED - now handled by controller
    }//GEN-LAST:event_AddFlightsjButtonActionPerformed

    private void SearchUsersTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SearchUsersTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_SearchUsersTextFieldActionPerformed

    private void SearchUsersButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SearchUsersButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_SearchUsersButtonActionPerformed

    private void UsersComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UsersComboBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_UsersComboBoxActionPerformed



    public static void main(String[] args) {
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            // Create a dummy user for testing purposes, without a password.
            UserDataModel dummyUser = new UserDataModel(0, "Admin User", "admin@wingsnepal.com", "Admin");
            AdminDashboard view = new AdminDashboard(dummyUser);
            new controller.AdminDashboardController(view, dummyUser).open();
        });
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AddFlightsjButton;
    private javax.swing.JPanel AdminLogoutPanel;
    private javax.swing.JPanel DasboardPanel;
    private javax.swing.JButton DashboardButton;
    private javax.swing.JLabel DashboardIcon;
    private javax.swing.JButton EmployeeButton;
    private javax.swing.JComboBox<String> EmployeeComboBox;
    private javax.swing.JLabel EmployeeIcon;
    private javax.swing.JPanel EmployeesPanel;
    private javax.swing.JButton FlightButton;
    private javax.swing.JPanel FlightPanel;
    private javax.swing.JLabel FlightsIcon;
    private javax.swing.JLabel LogoutIcon;
    private javax.swing.JButton Logoutbutton;
    private javax.swing.JButton PassengerButton;
    private javax.swing.JLabel PassengerIcon;
    private javax.swing.JPanel PassengerPanel;
    private javax.swing.JButton SearchButton;
    private javax.swing.JButton SearchFlightjButton;
    private javax.swing.JTextField SearchFlightsTextField;
    private javax.swing.JButton SearchPassengerButton;
    private javax.swing.JTextField SearchPassengerTextField;
    private javax.swing.JTextField SearchTextField;
    private javax.swing.JComboBox<String> SeatClassTextField;
    private javax.swing.JButton ShowAllButton;
    private javax.swing.JButton UserButton;
    private javax.swing.JLabel UserIcon;
    private javax.swing.JLabel WingsNepalLogo;
    private javax.swing.JButton btnAddEmployee;
    private javax.swing.JTable employeeTable;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable3;
    private javax.swing.JTable jTable4;
    // End of variables declaration//GEN-END:variables

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

        JPanel textPanel = new JPanel(new java.awt.GridLayout(2, 1));
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
    
    private String formatTime(String time24h) {
        if (time24h == null || time24h.isEmpty()) {
            return "";
        }
        try {
            java.time.LocalTime time = java.time.LocalTime.parse(time24h);
            return time.format(java.time.format.DateTimeFormatter.ofPattern("hh:mm a"));
        } catch (java.time.format.DateTimeParseException e) {
            // Return original string if it's not in a parsable format
            return time24h;
        }
    }
    
    // Getter methods for controller access
    public javax.swing.JTable getEmployeeTable() {
        return employeeTable;
    }
    
    public javax.swing.JTable getJTable4() {
        return jTable4;
    }
    
    public javax.swing.JTable getJTable3() {
        return jTable3;
    }
    
    public javax.swing.JTextField getSearchTextField() {
        return SearchTextField;
    }
    
    public javax.swing.JTextField getSearchFlightsTextField() {
        return SearchFlightsTextField;
    }
    
    public javax.swing.JTextField getSearchPassengerTextField() {
        return SearchPassengerTextField;
    }
    
    public javax.swing.JComboBox<String> getEmployeeComboBox() {
        return EmployeeComboBox;
    }
    
    public javax.swing.JComboBox<String> getSeatClassTextField() {
        return SeatClassTextField;
    }
    
    public javax.swing.JButton getBtnAddEmployee() {
        return btnAddEmployee;
    }
    
    public javax.swing.JButton getAddFlightsjButton() {
        return AddFlightsjButton;
    }
    
    public javax.swing.JButton getSearchButton() {
        return SearchButton;
    }
    
    public javax.swing.JButton getSearchFlightjButton() {
        return SearchFlightjButton;
    }
    
    public javax.swing.JButton getSearchPassengerButton() {
        return SearchPassengerButton;
    }
    
    public javax.swing.JButton getLogOutbutton() {
        return Logoutbutton;
    }
    
    public javax.swing.JTabbedPane getJTabbedPane1() {
        return jTabbedPane1;
    }
    
    public javax.swing.JLabel getTotalFlightsLabel() {
        return totalFlightsLabel;
    }
    
    public javax.swing.JLabel getAvailableSeatsLabel() {
        return availableSeatsLabel;
    }
    
    public javax.swing.JLabel getTotalPassengersLabel() {
        return totalPassengersLabel;
    }

    public javax.swing.JLabel getTotalEmployeesLabel() {
        return totalEmployeesLabel;
    }

    public javax.swing.JLabel getTotalUsersLabel() {
        return totalUsersLabel;
    }

    public javax.swing.JLabel getTotalBookingsLabel() {
        return totalBookingsLabel;
    }

    public javax.swing.JButton getShowAllButton() {
        return ShowAllButton;
    }

    public javax.swing.JTable getUsersTable() {
        return usersTable;
    }

    public javax.swing.JTextField getSearchUsersTextField() {
        return SearchUsersTextField;
    }

    public javax.swing.JButton getSearchUsersButton() {
        return SearchUsersButton;
    }

    public javax.swing.JComboBox<String> getUsersComboBox() {
        return UsersComboBox;
    }

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
}

class StatusCellRenderer extends DefaultTableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(javax.swing.JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        if (value != null && value.toString().equalsIgnoreCase("Active")) {
            c.setForeground(new Color(0, 153, 51)); // Dark green text
            c.setBackground(new Color(220, 255, 220)); // Light green background
        } else {
            c.setForeground(Color.BLACK);
            c.setBackground(Color.WHITE);
        }
        // Keep selection highlight
        if (isSelected) {
            c.setBackground(table.getSelectionBackground());
            c.setForeground(table.getSelectionForeground());
        }
        return c;
    }
}


    
