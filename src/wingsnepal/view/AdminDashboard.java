/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package wingsnepal.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import wingsnepal.model.UserData;
import javax.swing.table.DefaultTableModel;
import wingsnepal.dao.EmployeeDao;
import wingsnepal.model.Employee;
import java.util.List;
import java.util.ArrayList;
import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

public class AdminDashboard extends javax.swing.JFrame {
    private UserData loggedInUser;
    private JLabel welcomeLabel;

    
    public AdminDashboard(UserData user) {
        this.loggedInUser = user;
        initComponents();
        
        // Set combo box options
        EmployeeComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(
            new String[] { "Employee ID", "Full Name", "Email" }
        ));
        
        DefaultTableModel model = new DefaultTableModel(new String[]{"Emp_ID", "Full Name", "Email", "Emp_Role", "Gender", "Password"}, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        employeeTable.setModel(model);
        loadEmployeeTable();

        //scaling image calling:
        scaleImage1();
        scaleImage2();
        
        //calling setupplaceholder:
        setupPlaceholders();
        setupPlaceholders1();
        
        setupEmployeeLiveSearch();
        
        setTitle("Admin Dashboard - Welcome" + user.getFullName());
        setLocationRelativeTo(null);
        
        // Adding hovering effect to buttons to make it look modern.
        styleFlatHoverButton(DashboardButton);
        styleFlatHoverButton(FlightButton);
        styleFlatHoverButton(PassengerButton);
        styleFlatHoverButton(ReservationButton);
        styleFlatHoverButton(EmployeeButton);
        styleFlatHoverButton(LogOutbutton);
        
        btnAddEmployee.addActionListener(e -> {
            AddEmployeeDialog dialog = new AddEmployeeDialog(this);
            dialog.setVisible(true);
            loadEmployeeTable();
        });

        btnAddReservation.addActionListener(e -> {
            AddReservationDialog dialog = new AddReservationDialog(this);
            dialog.setVisible(true);
        });

    }

        public class AdminButtonEditor extends AbstractCellEditor implements TableCellEditor {
            private JButton button = new JButton("Edit");
            private boolean clicked;

            public AdminButtonEditor(JCheckBox checkBox, JFrame parent) {
                button.setOpaque(true);
                button.setBackground(Color.GREEN);
                button.setForeground(Color.WHITE);
                button.addActionListener(e -> {
                    // your edit logic here
                    clicked = true;
                    fireEditingStopped();
                });
            }

            @Override
            public Component getTableCellEditorComponent(JTable table, Object value,
                    boolean isSelected, int row, int column) {
                button.setText("Edit");
                clicked = false;
                return button;
            }

            @Override
            public Object getCellEditorValue() {
                return "Edit";
            }
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
        
        private void setupEmployeeLiveSearch() {
            SearchTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
            String keyword = SearchTextField.getText().trim();
            if (keyword.isEmpty() || keyword.equals("Search Employees")) {
                loadEmployeeTable();
            } else {
                EmployeeDao dao = new EmployeeDao();
                List<Employee> filteredList = dao.searchEmployeesByName(keyword);

                DefaultTableModel model = (DefaultTableModel) employeeTable.getModel();
                model.setRowCount(0);

                for (Employee emp : filteredList) {
                    model.addRow(new Object[]{
                        emp.getEmpId(),
                        emp.getFullName(),
                        emp.getEmpEmail(),
                        emp.getEmpRole(),
                        emp.getGender(),
                        emp.getPassword()
                    });
                }
            }
        }
    });
}
    public JLabel getWelcomeLabel() {
        return welcomeLabel;
    }
     
    //This method styles buttons to look flat and modern, and adds hover effects for better user experience.
    //It is called after init components to give for which button it is applicable.
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
        public void loadEmployeeTable() {
            EmployeeDao dao = new EmployeeDao();
            List<Employee> employees = dao.getAllEmployees();

            DefaultTableModel model = new DefaultTableModel(new Object[]{"ID", "Name", "Email", "Role", "Gender", "Password", "Actions"}, 0) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return column == 6; // Only the "Actions" column is editable
                }
            };

            for (Employee emp : employees) {
                model.addRow(new Object[]{
                    emp.getEmpId(),
                    emp.getFullName(),
                    emp.getEmpEmail(),
                    emp.getEmpRole(),
                    emp.getGender(),
                    emp.getPassword(),
                    "" // Placeholder for Edit/Delete buttons
                });
            }

            employeeTable.setModel(model);
            employeeTable.getColumn("Actions").setCellRenderer(new AdminButtonRenderer());
            employeeTable.getColumn("Actions").setCellEditor(new AdminButtonEditor(new JCheckBox(), this));
            
            employeeTable.getColumnModel().getColumn(6).setPreferredWidth(160);  // Set width of the Actions column

        }
        
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel5 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        DashboardIcon = new javax.swing.JLabel();
        DashboardButton = new javax.swing.JButton();
        FlightButton = new javax.swing.JButton();
        PassengerButton = new javax.swing.JButton();
        ReservationButton = new javax.swing.JButton();
        EmployeeButton = new javax.swing.JButton();
        LogOutbutton = new javax.swing.JButton();
        WingsNepalLogo = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        WelcAdminLabel = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        DasboardPanel = new javax.swing.JPanel();
        FlightPanel = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        AddFlightsjButton = new javax.swing.JButton();
        SearchFlightjButton = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable4 = new javax.swing.JTable();
        SearchFlightsTextField = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        PassengerPanel = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        SearchPassengerButton = new javax.swing.JButton();
        SearchPassengerTextField = new javax.swing.JTextField();
        SeatClassTextField = new javax.swing.JComboBox<>();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        FlightIdLabel = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        ReservationPanel = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        btnAddReservation = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        EmployeesPanel = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        employeeTable = new javax.swing.JTable();
        SearchTextField = new javax.swing.JTextField();
        btnAddEmployee = new javax.swing.JButton();
        SearchButton = new javax.swing.JButton();
        EmployeeComboBox = new javax.swing.JComboBox<>();
        EmpDirLabel = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();

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

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(46, 62, 79));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        DashboardIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagepicker/Dashboard.png"))); // NOI18N
        jPanel2.add(DashboardIcon, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, 20, 20));

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
        jPanel2.add(DashboardButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 80, 170, 31));

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
        jPanel2.add(FlightButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(-30, 120, 200, 31));

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
        jPanel2.add(PassengerButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 160, 170, 33));

        ReservationButton.setBackground(new java.awt.Color(46, 62, 79));
        ReservationButton.setFont(new java.awt.Font("Segoe UI Emoji", 1, 18)); // NOI18N
        ReservationButton.setForeground(new java.awt.Color(255, 255, 255));
        ReservationButton.setText("Reservations");
        ReservationButton.setBorder(null);
        ReservationButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ReservationButtonActionPerformed(evt);
            }
        });
        jPanel2.add(ReservationButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 200, 180, 33));

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
        jPanel2.add(EmployeeButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(-10, 240, 180, 33));

        LogOutbutton.setBackground(new java.awt.Color(46, 62, 79));
        LogOutbutton.setFont(new java.awt.Font("Segoe UI Emoji", 1, 18)); // NOI18N
        LogOutbutton.setForeground(new java.awt.Color(255, 255, 255));
        LogOutbutton.setText("Logout");
        LogOutbutton.setBorder(null);
        LogOutbutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LogOutbuttonActionPerformed(evt);
            }
        });
        jPanel2.add(LogOutbutton, new org.netbeans.lib.awtextra.AbsoluteConstraints(-30, 280, 200, 32));

        WingsNepalLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagepicker/WingsNepalLogo.jpg"))); // NOI18N
        jPanel2.add(WingsNepalLogo, new org.netbeans.lib.awtextra.AbsoluteConstraints(-30, -70, 220, 220));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 30, 170, 570));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        WelcAdminLabel.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        WelcAdminLabel.setForeground(new java.awt.Color(51, 51, 51));
        WelcAdminLabel.setText("Welcome, Admin!");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(WelcAdminLabel)
                .addContainerGap(837, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(WelcAdminLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1010, 30));

        javax.swing.GroupLayout DasboardPanelLayout = new javax.swing.GroupLayout(DasboardPanel);
        DasboardPanel.setLayout(DasboardPanelLayout);
        DasboardPanelLayout.setHorizontalGroup(
            DasboardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1078, Short.MAX_VALUE)
        );
        DasboardPanelLayout.setVerticalGroup(
            DasboardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 619, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("tab1", DasboardPanel);

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));

        AddFlightsjButton.setBackground(new java.awt.Color(0, 153, 102));
        AddFlightsjButton.setForeground(new java.awt.Color(255, 255, 255));
        AddFlightsjButton.setText("+  Add Flights");
        AddFlightsjButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddFlightsjButtonActionPerformed(evt);
            }
        });

        SearchFlightjButton.setBackground(new java.awt.Color(0, 102, 153));
        SearchFlightjButton.setForeground(new java.awt.Color(255, 255, 255));
        SearchFlightjButton.setText("Search");
        SearchFlightjButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SearchFlightjButtonActionPerformed(evt);
            }
        });

        jTable4.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Flight", "Route", "Departure", "Arrival", "Aircraft", "Actions"
            }
        ));
        jScrollPane4.setViewportView(jTable4);

        SearchFlightsTextField.setText("Search Flights...");
        SearchFlightsTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SearchFlightsTextFieldActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(SearchFlightsTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(SearchFlightjButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 328, Short.MAX_VALUE)
                        .addComponent(AddFlightsjButton, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AddFlightsjButton, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(SearchFlightjButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(SearchFlightsTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel2.setFont(new java.awt.Font("Segoe UI Emoji", 1, 18)); // NOI18N
        jLabel2.setText("Flights Management");

        javax.swing.GroupLayout FlightPanelLayout = new javax.swing.GroupLayout(FlightPanel);
        FlightPanel.setLayout(FlightPanelLayout);
        FlightPanelLayout.setHorizontalGroup(
            FlightPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(FlightPanelLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(FlightPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(270, Short.MAX_VALUE))
        );
        FlightPanelLayout.setVerticalGroup(
            FlightPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, FlightPanelLayout.createSequentialGroup()
                .addContainerGap(63, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27))
        );

        jTabbedPane1.addTab("tab2", FlightPanel);

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));

        SearchPassengerButton.setBackground(new java.awt.Color(0, 102, 153));
        SearchPassengerButton.setForeground(new java.awt.Color(255, 255, 255));
        SearchPassengerButton.setText("Search");
        SearchPassengerButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SearchPassengerButtonActionPerformed(evt);
            }
        });

        SearchPassengerTextField.setText("Search Passengers");
        SearchPassengerTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SearchPassengerTextFieldActionPerformed(evt);
            }
        });

        SeatClassTextField.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seat Class", "Economy", "First Class", "Business Class" }));
        SeatClassTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SeatClassTextFieldActionPerformed(evt);
            }
        });

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
                "Name", "Contact", "Status", "Last Flight", "Upcoming", "Actions"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane3.setViewportView(jTable3);

        FlightIdLabel.setText("Flight ID");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 771, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(SearchPassengerTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)
                        .addComponent(SeatClassTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(FlightIdLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(SearchPassengerButton, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(26, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(SearchPassengerTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(SearchPassengerButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(SeatClassTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(FlightIdLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 403, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(60, 60, 60))
        );

        jLabel3.setFont(new java.awt.Font("Segoe UI Emoji", 1, 18)); // NOI18N
        jLabel3.setText("Passenger Management");

        javax.swing.GroupLayout PassengerPanelLayout = new javax.swing.GroupLayout(PassengerPanel);
        PassengerPanel.setLayout(PassengerPanelLayout);
        PassengerPanelLayout.setHorizontalGroup(
            PassengerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PassengerPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(269, 269, 269))
            .addGroup(PassengerPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        PassengerPanelLayout.setVerticalGroup(
            PassengerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PassengerPanelLayout.createSequentialGroup()
                .addContainerGap(50, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, 484, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(58, 58, 58))
        );

        jTabbedPane1.addTab("tab3", PassengerPanel);

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
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
                "RID", "Passenger", "Flight	", "Route", "Date & Time", "Seat", "Class", "Status", "Amount", "Actions"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.String.class, java.lang.Object.class, java.lang.String.class, java.lang.Integer.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane2.setViewportView(jTable2);

        btnAddReservation.setBackground(new java.awt.Color(0, 153, 102));
        btnAddReservation.setForeground(new java.awt.Color(255, 255, 255));
        btnAddReservation.setText("+   New Reservation");
        btnAddReservation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddReservationActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnAddReservation, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 776, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(22, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(btnAddReservation, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 401, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(29, Short.MAX_VALUE))
        );

        jLabel1.setFont(new java.awt.Font("Segoe UI Emoji", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 51, 51));
        jLabel1.setText("Reservation Management");

        javax.swing.GroupLayout ReservationPanelLayout = new javax.swing.GroupLayout(ReservationPanel);
        ReservationPanel.setLayout(ReservationPanelLayout);
        ReservationPanelLayout.setHorizontalGroup(
            ReservationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ReservationPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(ReservationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(57, 57, 57))
        );
        ReservationPanelLayout.setVerticalGroup(
            ReservationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ReservationPanelLayout.createSequentialGroup()
                .addGap(55, 55, 55)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(53, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("tab4", ReservationPanel);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        employeeTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "emp_id", "Full Name", "Email", "emp_role", "gender", "password"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.Object.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane1.setViewportView(employeeTable);

        SearchTextField.setText("Search Employee...");
        SearchTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SearchTextFieldActionPerformed(evt);
            }
        });

        btnAddEmployee.setBackground(new java.awt.Color(0, 153, 102));
        btnAddEmployee.setForeground(new java.awt.Color(255, 255, 255));
        btnAddEmployee.setText("+   Add Employee");
        btnAddEmployee.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddEmployeeActionPerformed(evt);
            }
        });

        SearchButton.setBackground(new java.awt.Color(0, 102, 153));
        SearchButton.setForeground(new java.awt.Color(255, 255, 255));
        SearchButton.setText("Search");
        SearchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SearchButtonActionPerformed(evt);
            }
        });

        EmployeeComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Employee ID", "Full Name", "Email" }));
        EmployeeComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EmployeeComboBoxActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(SearchTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(EmployeeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(SearchButton, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnAddEmployee, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 790, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(EmployeeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(SearchTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(SearchButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnAddEmployee, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 448, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24))
        );

        EmpDirLabel.setFont(new java.awt.Font("Segoe UI Emoji", 1, 18)); // NOI18N
        EmpDirLabel.setText("Employee Directory");

        javax.swing.GroupLayout EmployeesPanelLayout = new javax.swing.GroupLayout(EmployeesPanel);
        EmployeesPanel.setLayout(EmployeesPanelLayout);
        EmployeesPanelLayout.setHorizontalGroup(
            EmployeesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(EmployeesPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(EmployeesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(EmpDirLabel))
                .addContainerGap(24, Short.MAX_VALUE))
        );
        EmployeesPanelLayout.setVerticalGroup(
            EmployeesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(EmployeesPanelLayout.createSequentialGroup()
                .addGap(58, 58, 58)
                .addComponent(EmpDirLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("tab5", EmployeesPanel);

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1078, Short.MAX_VALUE)
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 619, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("tab6", jPanel12);

        jPanel1.add(jTabbedPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, -40, 840, 650));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void PassengerButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PassengerButtonActionPerformed
        jTabbedPane1.setSelectedIndex(2);
    }//GEN-LAST:event_PassengerButtonActionPerformed

    private void DashboardButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DashboardButtonActionPerformed
        jTabbedPane1.setSelectedIndex(0);
    }//GEN-LAST:event_DashboardButtonActionPerformed

    private void ReservationButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ReservationButtonActionPerformed
        jTabbedPane1.setSelectedIndex(3);
    }//GEN-LAST:event_ReservationButtonActionPerformed

    private void LogOutbuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LogOutbuttonActionPerformed
        jTabbedPane1.setSelectedIndex(5); 
        int confirm = javax.swing.JOptionPane.showConfirmDialog(this, "Are you sure you want to log out?", "Log out", javax.swing.JOptionPane.YES_NO_OPTION);
        if (confirm == javax.swing.JOptionPane.YES_OPTION) {
            this.dispose(); // Close the current window
            new LoginPage().setVisible(true); // Open the login window
        }
    }//GEN-LAST:event_LogOutbuttonActionPerformed

    private void EmployeeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EmployeeButtonActionPerformed
        jTabbedPane1.setSelectedIndex(4);
    }//GEN-LAST:event_EmployeeButtonActionPerformed

    private void SearchTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SearchTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_SearchTextFieldActionPerformed

    private void FlightButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FlightButtonActionPerformed
        jTabbedPane1.setSelectedIndex(1);
    }//GEN-LAST:event_FlightButtonActionPerformed

    private void btnAddEmployeeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddEmployeeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAddEmployeeActionPerformed

    private void btnAddReservationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddReservationActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAddReservationActionPerformed

    private void SearchPassengerTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SearchPassengerTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_SearchPassengerTextFieldActionPerformed

    private void SearchPassengerButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SearchPassengerButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_SearchPassengerButtonActionPerformed

    private void SearchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SearchButtonActionPerformed
        // TODO add your handling code here
        String keyword = SearchTextField.getText().trim();
        String selectedField = (String) EmployeeComboBox.getSelectedItem();

        EmployeeDao dao = new EmployeeDao();
        List<Employee> filteredList = new ArrayList<>();

        if (selectedField.equals("Full Name")) {
            // Partial match
            filteredList = dao.searchEmployeesByField("emp_full_name", keyword.toLowerCase(), true);
        } else if (selectedField.equals("Email")) {
            // Exact match
            filteredList = dao.searchEmployeesByField("emp_email", keyword.toLowerCase(), false);
        } else if (selectedField.equals("Employee ID")) {
            try {
                int id = Integer.parseInt(keyword);
                filteredList = dao.searchEmployeesByField("emp_id", String.valueOf(id), false);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid Employee ID");
                return;
            }
        }

        // Update table
        DefaultTableModel model = (DefaultTableModel) employeeTable.getModel();
        model.setRowCount(0);

        for (Employee emp : filteredList) {
            model.addRow(new Object[]{
                emp.getEmpId(),
                emp.getFullName(),
                emp.getEmpEmail(),
                emp.getEmpRole(),
                emp.getGender(),
                emp.getPassword()
            });
        }



    }//GEN-LAST:event_SearchButtonActionPerformed

    private void SeatClassTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SeatClassTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_SeatClassTextFieldActionPerformed

    private void AddFlightsjButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddFlightsjButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_AddFlightsjButtonActionPerformed

    private void SearchFlightsTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SearchFlightsTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_SearchFlightsTextFieldActionPerformed

    private void SearchFlightjButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SearchFlightjButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_SearchFlightjButtonActionPerformed

    private void EmployeeComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EmployeeComboBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_EmployeeComboBoxActionPerformed

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> {
            UserData dummy = new UserData(1, "Admin", "admin@wingsnepal.com", "admin123", "Admin");
            new AdminDashboard(dummy).setVisible(true);
        });
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AddFlightsjButton;
    private javax.swing.JPanel DasboardPanel;
    private javax.swing.JButton DashboardButton;
    private javax.swing.JLabel DashboardIcon;
    private javax.swing.JLabel EmpDirLabel;
    private javax.swing.JButton EmployeeButton;
    private javax.swing.JComboBox<String> EmployeeComboBox;
    private javax.swing.JPanel EmployeesPanel;
    private javax.swing.JButton FlightButton;
    private javax.swing.JTextField FlightIdLabel;
    private javax.swing.JPanel FlightPanel;
    private javax.swing.JButton LogOutbutton;
    private javax.swing.JButton PassengerButton;
    private javax.swing.JPanel PassengerPanel;
    private javax.swing.JButton ReservationButton;
    private javax.swing.JPanel ReservationPanel;
    private javax.swing.JButton SearchButton;
    private javax.swing.JButton SearchFlightjButton;
    private javax.swing.JTextField SearchFlightsTextField;
    private javax.swing.JButton SearchPassengerButton;
    private javax.swing.JTextField SearchPassengerTextField;
    private javax.swing.JTextField SearchTextField;
    private javax.swing.JComboBox<String> SeatClassTextField;
    private javax.swing.JLabel WelcAdminLabel;
    private javax.swing.JLabel WingsNepalLogo;
    private javax.swing.JButton btnAddEmployee;
    private javax.swing.JButton btnAddReservation;
    private javax.swing.JTable employeeTable;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    private javax.swing.JTable jTable4;
    // End of variables declaration//GEN-END:variables

// other methods above...







    
}
    
