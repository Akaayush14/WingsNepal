/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package wingsnepal.view;
import java.awt.Image;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import wingsnepal.dao.SearchFlightDao;
import wingsnepal.model.SearchFlight;




/**
 *
 * @author Aayush Kharel
 */
public class UserPortal extends javax.swing.JFrame{

    /**
     * Creates new form UserPortal
     */
    public UserPortal() {
        initComponents();
        
            // Adding hovering effect to buttons to make it look modern.
            styleFlatHoverButton(DashboardButton);
            styleFlatHoverButton(FlightButton);
            styleFlatHoverButton(BookFlightButton);
            styleFlatHoverButton(CheckInButton);
            styleFlatHoverButton(LogOutButton);
            
            // Adding scrolling feature:
            jScrollPane1.setViewportView(jTable1);

        setResizable(false);         //Disable maximize button
        setLocationRelativeTo(null); //Center window
        scaleImage1();  
        scaleImage2();
        scaleImage3();
        scaleImage4();
        scaleImage5();
        scaleImage6();
        
        //Adding codfe to automatically remove in- built text from textfield while entering input.
        // --------- FROM TEXT FIELD PLACEHOLDER ----------
    FromTextField.setForeground(java.awt.Color.GRAY);
    FromTextField.setText("Enter departure city or airport");
    FromTextField.addFocusListener(new java.awt.event.FocusAdapter() {
        public void focusGained(java.awt.event.FocusEvent evt) {
            if (FromTextField.getText().equals("Enter departure city or airport")) {
                FromTextField.setText("");
                FromTextField.setForeground(java.awt.Color.BLACK);
            }
        }
        public void focusLost(java.awt.event.FocusEvent evt) {
            if (FromTextField.getText().isEmpty()) {
                FromTextField.setForeground(java.awt.Color.GRAY);
                FromTextField.setText("Enter departure city or airport");
            }
        }
    });

        // --------- TO TEXT FIELD PLACEHOLDER ----------
        ToTextField.setForeground(java.awt.Color.GRAY);
        ToTextField.setText("Enter destination city or airport");
        ToTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
            if (ToTextField.getText().equals("Enter destination city or airport")) {
                ToTextField.setText("");
                ToTextField.setForeground(java.awt.Color.BLACK);
            }
        }
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (ToTextField.getText().isEmpty()) {
                    ToTextField.setForeground(java.awt.Color.GRAY);
                    ToTextField.setText("Enter destination city or airport");
                }
            }
        });

        // --------- DAY TEXT FIELD PLACEHOLDER ----------
        jDayChooser1.setForeground(java.awt.Color.GRAY);
        jDayChooser1.setText("Day");
        jDayChooser1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (jDayChooser1.getText().equals("Day")) {
                    jDayChooser1.setText("");
                    jDayChooser1.setForeground(java.awt.Color.BLACK);
                }
            }
            
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (jDayChooser1.getText().isEmpty()) {
                    jDayChooser1.setForeground(java.awt.Color.GRAY);
                    jDayChooser1.setText("Day");
                }
            }
        });
 
    }
    // Code to scale images:
        public void scaleImage1(){
        ImageIcon icon1 = new ImageIcon(getClass().getResource("/imagepicker/Dashboard.png"));
        //scaling image to fit in the hlabel.
        Image img1 = icon1.getImage();
        Image imgScale1 = img1.getScaledInstance(DashboardIcon.getWidth(), DashboardIcon.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon scaledIcon1 = new ImageIcon(imgScale1);
        DashboardIcon.setIcon(scaledIcon1);
    }
        public void scaleImage2(){
        ImageIcon icon2 = new ImageIcon(getClass().getResource("/imagepicker/Search.png"));
        //scaling image to fit in the hlabel.
        Image img2 = icon2.getImage();
        Image imgScale2 = img2.getScaledInstance(SearchFlightIcon.getWidth(), SearchFlightIcon.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon scaledIcon2 = new ImageIcon(imgScale2);
        SearchFlightIcon.setIcon(scaledIcon2);
    }
        public void scaleImage3(){
        ImageIcon icon3 = new ImageIcon(getClass().getResource("/imagepicker/Book.png"));
        //scaling image to fit in the hlabel.
        Image img3 = icon3.getImage();
        Image imgScale3 = img3.getScaledInstance(BookFlightIcon.getWidth(), BookFlightIcon.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon scaledIcon3 = new ImageIcon(imgScale3);
        BookFlightIcon.setIcon(scaledIcon3);
    }
        public void scaleImage4(){
        ImageIcon icon4 = new ImageIcon(getClass().getResource("/imagepicker/CheckIn.png"));
        //scaling image to fit in the hlabel.
        Image img4 = icon4.getImage();
        Image imgScale4 = img4.getScaledInstance(CancelFlightIcon.getWidth(), CancelFlightIcon.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon scaledIcon4 = new ImageIcon(imgScale4);
        CancelFlightIcon.setIcon(scaledIcon4);
    }
        public void scaleImage5(){
        ImageIcon icon5 = new ImageIcon(getClass().getResource("/imagepicker/LogOut.png"));
        //scaling image to fit in the hlabel.
        Image img5 = icon5.getImage();
        Image imgScale5 = img5.getScaledInstance(LogOutIcon.getWidth(), LogOutIcon.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon scaledIcon5 = new ImageIcon(imgScale5);
        LogOutIcon.setIcon(scaledIcon5);
    }
        public void scaleImage6(){
        ImageIcon icon6 = new ImageIcon(getClass().getResource("/imagepicker/WingsNepalLogo.jpg"));
        //scaling image to fit in the hlabel.
        Image img6 = icon6.getImage();
        Image imgScale6 = img6.getScaledInstance(WingsNepalLogo.getWidth(), WingsNepalLogo.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon scaledIcon6 = new ImageIcon(imgScale6);
        WingsNepalLogo.setIcon(scaledIcon6);
    }
        //This method styles buttons to look flat and modern, and adds hover effects for better user experience.
        //It is called after in it components to give for which button it is applicable.
        private void styleFlatHoverButton(javax.swing.JButton button) {
            button.setFocusPainted(false);
            button.setContentAreaFilled(false);
            button.setBorderPainted(false);
            button.setOpaque(false);

            button.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setContentAreaFilled(true);
                button.setBackground(new java.awt.Color(46,62,80)); 
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

        //It calls Dao, fetches flights and fills the jTable. 
        private void SearchFlightButtonActionPerformed(java.awt.event.ActionEvent evt) {
            String from = FromTextField.getText();
            String to = ToTextField.getText();
            int year = jYearChooser1.getYear();
            int month = jMonthChooser1.getMonth() + 1; 
            String day = jDayChooser1.getText();

            String date = year + "-" + String.format("%02d", month) + "-" + day;

            SearchFlightDao dao = new SearchFlightDao();
            List<SearchFlight> flights = dao.searchFlights(from, to, date);

            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            model.setRowCount(0); 

            for (SearchFlight f : flights) {
                model.addRow(new Object[]{
                    f.getFlightName(),
                    f.getTime(),
                    f.getPrice(),
                    f.getDuration(),
                    "Book"
                });
            }
            
            // After adding all rows
            TableColumn bookColumn = jTable1.getColumn("Action");
            bookColumn.setCellRenderer(new ButtonRenderer());
            bookColumn.setCellEditor(new ButtonEditor(new JCheckBox()));

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
        DashboardIcon = new javax.swing.JLabel();
        SearchFlightIcon = new javax.swing.JLabel();
        BookFlightIcon = new javax.swing.JLabel();
        CancelFlightIcon = new javax.swing.JLabel();
        LogOutIcon = new javax.swing.JLabel();
        DashboardButton = new javax.swing.JButton();
        FlightButton = new javax.swing.JButton();
        BookFlightButton = new javax.swing.JButton();
        CheckInButton = new javax.swing.JButton();
        LogOutButton = new javax.swing.JButton();
        WingsNepalLogo = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        FromLabel = new javax.swing.JLabel();
        ToLabel = new javax.swing.JLabel();
        DateLabel = new javax.swing.JLabel();
        FromTextField = new javax.swing.JTextField();
        ToTextField = new javax.swing.JTextField();
        jYearChooser1 = new com.toedter.calendar.JYearChooser();
        jMonthChooser1 = new com.toedter.calendar.JMonthChooser();
        jDayChooser1 = new javax.swing.JTextField();
        HeadingPanel = new javax.swing.JPanel();
        SearchFlightLabel = new javax.swing.JLabel();
        SearchFlightButton = new javax.swing.JButton();
        jScrollBar1 = new javax.swing.JScrollBar();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(46, 62, 79));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        DashboardIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagepicker/Dashboard.png"))); // NOI18N
        jPanel1.add(DashboardIcon, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 170, 20, 20));

        SearchFlightIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagepicker/search.png"))); // NOI18N
        jPanel1.add(SearchFlightIcon, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 240, 30, 30));

        BookFlightIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagepicker/Book.png"))); // NOI18N
        BookFlightIcon.setText("jLabel5");
        jPanel1.add(BookFlightIcon, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 320, 40, 40));

        CancelFlightIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagepicker/CheckIn.png"))); // NOI18N
        jPanel1.add(CancelFlightIcon, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 400, 30, 30));

        LogOutIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagepicker/Logout.png"))); // NOI18N
        LogOutIcon.setText("jLabel1");
        jPanel1.add(LogOutIcon, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 480, 30, 30));

        DashboardButton.setBackground(new java.awt.Color(46, 62, 79));
        DashboardButton.setFont(new java.awt.Font("Segoe UI Emoji", 1, 18)); // NOI18N
        DashboardButton.setForeground(new java.awt.Color(255, 255, 255));
        DashboardButton.setText("User Dashboard");
        DashboardButton.setAutoscrolls(true);
        DashboardButton.setBorder(null);
        DashboardButton.setBorderPainted(false);
        DashboardButton.setContentAreaFilled(false);
        DashboardButton.setFocusPainted(false);
        DashboardButton.setVerifyInputWhenFocusTarget(false);
        DashboardButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DashboardButtonActionPerformed(evt);
            }
        });
        jPanel1.add(DashboardButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 160, 230, 40));

        FlightButton.setBackground(new java.awt.Color(46, 62, 79));
        FlightButton.setFont(new java.awt.Font("Segoe UI Emoji", 1, 18)); // NOI18N
        FlightButton.setForeground(new java.awt.Color(255, 255, 255));
        FlightButton.setText("Search Flights");
        FlightButton.setBorderPainted(false);
        FlightButton.setContentAreaFilled(false);
        FlightButton.setFocusPainted(false);
        FlightButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FlightButtonActionPerformed(evt);
            }
        });
        jPanel1.add(FlightButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 240, 230, 40));

        BookFlightButton.setBackground(new java.awt.Color(46, 62, 79));
        BookFlightButton.setFont(new java.awt.Font("Segoe UI Emoji", 1, 18)); // NOI18N
        BookFlightButton.setForeground(new java.awt.Color(255, 255, 255));
        BookFlightButton.setText("Book Flights");
        BookFlightButton.setBorderPainted(false);
        BookFlightButton.setContentAreaFilled(false);
        BookFlightButton.setFocusPainted(false);
        BookFlightButton.setVerifyInputWhenFocusTarget(false);
        BookFlightButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BookFlightButtonActionPerformed(evt);
            }
        });
        jPanel1.add(BookFlightButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 315, 230, 40));

        CheckInButton.setBackground(new java.awt.Color(46, 62, 79));
        CheckInButton.setFont(new java.awt.Font("Segoe UI Emoji", 1, 18)); // NOI18N
        CheckInButton.setForeground(new java.awt.Color(255, 255, 255));
        CheckInButton.setText("Cancel Flight");
        CheckInButton.setBorderPainted(false);
        CheckInButton.setContentAreaFilled(false);
        CheckInButton.setFocusPainted(false);
        CheckInButton.setVerifyInputWhenFocusTarget(false);
        CheckInButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CheckInButtonActionPerformed(evt);
            }
        });
        jPanel1.add(CheckInButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 400, 230, 40));

        LogOutButton.setBackground(new java.awt.Color(46, 62, 79));
        LogOutButton.setFont(new java.awt.Font("Segoe UI Emoji", 1, 18)); // NOI18N
        LogOutButton.setForeground(new java.awt.Color(255, 255, 255));
        LogOutButton.setText("Log out");
        LogOutButton.setBorderPainted(false);
        LogOutButton.setContentAreaFilled(false);
        LogOutButton.setFocusPainted(false);
        LogOutButton.setVerifyInputWhenFocusTarget(false);
        LogOutButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LogOutButtonActionPerformed(evt);
            }
        });
        jPanel1.add(LogOutButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 475, 230, 40));

        WingsNepalLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagepicker/WingsNepalLogo.jpg"))); // NOI18N
        jPanel1.add(WingsNepalLogo, new org.netbeans.lib.awtextra.AbsoluteConstraints(-20, -70, 270, 270));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, -6, 230, 640));

        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jTabbedPane1.addTab("tab1", jPanel2);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        FromLabel.setFont(new java.awt.Font("Segoe UI Emoji", 1, 18)); // NOI18N
        FromLabel.setText("From");
        jPanel3.add(FromLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, -1, -1));

        ToLabel.setFont(new java.awt.Font("Segoe UI Emoji", 1, 18)); // NOI18N
        ToLabel.setText("To");
        jPanel3.add(ToLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 210, -1, -1));

        DateLabel.setFont(new java.awt.Font("Segoe UI Emoji", 1, 18)); // NOI18N
        DateLabel.setText("Date");
        jPanel3.add(DateLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 320, -1, -1));

        FromTextField.setText("Enter departure city or airport");
        FromTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FromTextFieldActionPerformed(evt);
            }
        });
        jPanel3.add(FromTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 140, 260, 30));

        ToTextField.setText("Enter destination city or airport");
        jPanel3.add(ToTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 240, 260, 30));
        jPanel3.add(jYearChooser1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 350, 80, 30));
        jPanel3.add(jMonthChooser1, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 350, 130, 30));

        jDayChooser1.setText("Day");
        jDayChooser1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jDayChooser1ActionPerformed(evt);
            }
        });
        jPanel3.add(jDayChooser1, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 350, 50, 30));

        HeadingPanel.setBackground(new java.awt.Color(153, 153, 153));

        SearchFlightLabel.setFont(new java.awt.Font("Segoe UI Emoji", 1, 24)); // NOI18N
        SearchFlightLabel.setForeground(new java.awt.Color(255, 255, 255));
        SearchFlightLabel.setText("Search Flights");

        javax.swing.GroupLayout HeadingPanelLayout = new javax.swing.GroupLayout(HeadingPanel);
        HeadingPanel.setLayout(HeadingPanelLayout);
        HeadingPanelLayout.setHorizontalGroup(
            HeadingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(HeadingPanelLayout.createSequentialGroup()
                .addGap(301, 301, 301)
                .addComponent(SearchFlightLabel)
                .addContainerGap(333, Short.MAX_VALUE))
        );
        HeadingPanelLayout.setVerticalGroup(
            HeadingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, HeadingPanelLayout.createSequentialGroup()
                .addContainerGap(17, Short.MAX_VALUE)
                .addComponent(SearchFlightLabel)
                .addContainerGap())
        );

        jPanel3.add(HeadingPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 790, 50));

        SearchFlightButton.setBackground(new java.awt.Color(0, 102, 153));
        SearchFlightButton.setFont(new java.awt.Font("Segoe UI Emoji", 1, 18)); // NOI18N
        SearchFlightButton.setForeground(new java.awt.Color(255, 255, 255));
        SearchFlightButton.setText("Search");
        SearchFlightButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SearchFlightButtonActionPerformed(evt);
            }
        });
        jPanel3.add(SearchFlightButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 440, 260, 40));
        jPanel3.add(jScrollBar1, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 110, 10, 540));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Flight ID", "Time", "Price", "Duration ", "Action"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jPanel3.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 110, 510, 530));

        jButton1.setBackground(new java.awt.Color(0, 102, 153));
        jButton1.setFont(new java.awt.Font("Segoe UI Emoji", 1, 18)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Show all");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 500, 260, 40));

        jTabbedPane1.addTab("tab2", jPanel3);

        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel7.setBackground(new java.awt.Color(153, 153, 153));

        jLabel1.setFont(new java.awt.Font("Segoe UI Emoji", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Book Flight");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(309, 309, 309)
                .addComponent(jLabel1)
                .addContainerGap(353, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addGap(0, 10, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel4.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 790, 50));

        jTabbedPane1.addTab("tab3", jPanel4);

        jLabel4.setText("Aayush4");
        jPanel5.add(jLabel4);

        jTabbedPane1.addTab("tab4", jPanel5);

        jLabel5.setText("Aayush5");
        jPanel6.add(jLabel5);

        jTabbedPane1.addTab("tab5", jPanel6);

        getContentPane().add(jTabbedPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, -40, 790, 670));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void DashboardButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DashboardButtonActionPerformed
        // TODO add your handling code here:
        jTabbedPane1.setSelectedIndex(0);//Dashboard tab
    }//GEN-LAST:event_DashboardButtonActionPerformed

    private void FlightButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FlightButtonActionPerformed
        // TODO add your handling code here:
        jTabbedPane1.setSelectedIndex(1);//Search Flights tab
    }//GEN-LAST:event_FlightButtonActionPerformed

    private void BookFlightButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BookFlightButtonActionPerformed
        // TODO add your handling code here:
        jTabbedPane1.setSelectedIndex(2); //Book Flights tab
    }//GEN-LAST:event_BookFlightButtonActionPerformed

    private void CheckInButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CheckInButtonActionPerformed
        // TODO add your handling code here:
        jTabbedPane1.setSelectedIndex(3); //Check in
    }//GEN-LAST:event_CheckInButtonActionPerformed

    private void LogOutButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LogOutButtonActionPerformed
        // TODO add your handling code here:
        int confirm = javax.swing.JOptionPane.showConfirmDialog(this, "Are you sure you want to log out?", "Log Out", javax.swing.JOptionPane.YES_NO_OPTION);
        if (confirm == javax.swing.JOptionPane.YES_OPTION) {
        this.dispose(); // Close the current window
        new LoginPage().setVisible(true); // Open the login window
    }
        
    }//GEN-LAST:event_LogOutButtonActionPerformed

    private void FromTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FromTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_FromTextFieldActionPerformed

    private void jDayChooser1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jDayChooser1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jDayChooser1ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

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
            java.util.logging.Logger.getLogger(UserPortal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(UserPortal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(UserPortal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UserPortal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new UserPortal().setVisible(true);
            }
        });
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BookFlightButton;
    private javax.swing.JLabel BookFlightIcon;
    private javax.swing.JLabel CancelFlightIcon;
    private javax.swing.JButton CheckInButton;
    private javax.swing.JButton DashboardButton;
    private javax.swing.JLabel DashboardIcon;
    private javax.swing.JLabel DateLabel;
    private javax.swing.JButton FlightButton;
    private javax.swing.JLabel FromLabel;
    private javax.swing.JTextField FromTextField;
    private javax.swing.JPanel HeadingPanel;
    private javax.swing.JButton LogOutButton;
    private javax.swing.JLabel LogOutIcon;
    private javax.swing.JButton SearchFlightButton;
    private javax.swing.JLabel SearchFlightIcon;
    private javax.swing.JLabel SearchFlightLabel;
    private javax.swing.JLabel ToLabel;
    private javax.swing.JTextField ToTextField;
    private javax.swing.JLabel WingsNepalLogo;
    private javax.swing.JButton jButton1;
    private javax.swing.JTextField jDayChooser1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private com.toedter.calendar.JMonthChooser jMonthChooser1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollBar jScrollBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    private com.toedter.calendar.JYearChooser jYearChooser1;
    // End of variables declaration//GEN-END:variables
}
