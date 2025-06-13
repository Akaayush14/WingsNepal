/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package wingsnepal.view;

import java.awt.Image;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import wingsnepal.dao.SearchFlightDao;
import wingsnepal.model.Login;
import wingsnepal.model.SearchFlight;
import wingsnepal.dao.SeatClassDao;
import java.awt.Font;



/**
 *
 * @author Aayush Kharel
 */
public class UserPortal extends javax.swing.JFrame{
    
    private Login loggedInUser;

    //Constructor for login-based usage
    public UserPortal(Login user) {
        this.loggedInUser = user; // store the user
        initComponents();
        
        // Optional: Show user's name on screen or use it anywhere you want
        setTitle("User Portal - Welcome, " + loggedInUser.getFullName());
        
        
        // Adding hovering effect to buttons to make it look modern.
        styleFlatHoverButton(DashboardButton);
        styleFlatHoverButton(FlightButton);
        styleFlatHoverButton(BookFlightButton);
        styleFlatHoverButton(CheckInButton);
        styleFlatHoverButton(LogOutButton);
            
        // Adding scrolling feature.
        jScrollPane1.setViewportView(jTable1);
            
        //ActionListener for showall button.
        ShowAllButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ShowAllButtonActionPerformed(evt);
            }
        });

        setResizable(false);         //Disable maximize button
        setLocationRelativeTo(null); //Center window
        
        scaleImage1();  
        scaleImage2();
        scaleImage3();
        scaleImage4();
        scaleImage5();
        scaleImage6();
        
        // calling setupPlaceholder();
        setupPlaceholders();
        
        //Font for textfields
        Font textFieldFont = new Font("Segoe UI", Font.PLAIN, 16); // Choose a clean, modern font

        FromTextField.setFont(textFieldFont);
        ToTextField.setFont(textFieldFont);
        jDayChooser1.setFont(textFieldFont); // your day input field

        FlightIdTextField.setFont(textFieldFont);
        FlightNameTextField.setFont(textFieldFont);
        FullNameTextField.setFont(textFieldFont);
        EmailTextField.setFont(textFieldFont);
        PriceTextField.setFont(textFieldFont);

        
    }

    
    private void setupPlaceholders() {
    String placeholderFrom = "Departure city/airport";
    FromTextField.setForeground(java.awt.Color.GRAY);
    FromTextField.setText(placeholderFrom);
    FromTextField.addFocusListener(new java.awt.event.FocusAdapter() {
        public void focusGained(java.awt.event.FocusEvent evt) {
            if (FromTextField.getText().equals(placeholderFrom)) {
                FromTextField.setText("");
                FromTextField.setForeground(java.awt.Color.BLACK);
            }
        }
        public void focusLost(java.awt.event.FocusEvent evt) {
            if (FromTextField.getText().isEmpty()) {
                FromTextField.setForeground(java.awt.Color.GRAY);
                FromTextField.setText(placeholderFrom);
            }
        }
    });


        ToTextField.setForeground(java.awt.Color.GRAY);
        ToTextField.setText("Destination city/airport"); // Match this text
        ToTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (ToTextField.getText().equals("Destination city/airport")) {
                    ToTextField.setText("");
                    ToTextField.setForeground(java.awt.Color.BLACK);
                }
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
            if (ToTextField.getText().isEmpty()) {
                ToTextField.setForeground(java.awt.Color.GRAY);
                ToTextField.setText("Destination city/airport");
            }
        }
    });

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
        

    // Code to scale images:
    private void scaleImage1(){
        ImageIcon icon1 = new ImageIcon(getClass().getResource("/imagepicker/Dashboard.png"));
        //scaling image to fit in the hlabel.
        Image img1 = icon1.getImage();
        Image imgScale1 = img1.getScaledInstance(DashboardIcon.getWidth(), DashboardIcon.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon scaledIcon1 = new ImageIcon(imgScale1);
        DashboardIcon.setIcon(scaledIcon1);
    }
    private void scaleImage2(){
        ImageIcon icon2 = new ImageIcon(getClass().getResource("/imagepicker/Search.png"));
        //scaling image to fit in the hlabel.
        Image img2 = icon2.getImage();
        Image imgScale2 = img2.getScaledInstance(SearchFlightIcon.getWidth(), SearchFlightIcon.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon scaledIcon2 = new ImageIcon(imgScale2);
        SearchFlightIcon.setIcon(scaledIcon2);
    }
    private void scaleImage3(){
        ImageIcon icon3 = new ImageIcon(getClass().getResource("/imagepicker/Book.png"));
        //scaling image to fit in the hlabel.
        Image img3 = icon3.getImage();
        Image imgScale3 = img3.getScaledInstance(BookFlightIcon.getWidth(), BookFlightIcon.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon scaledIcon3 = new ImageIcon(imgScale3);
        BookFlightIcon.setIcon(scaledIcon3);
    }
    private void scaleImage4(){
        ImageIcon icon4 = new ImageIcon(getClass().getResource("/imagepicker/CheckIn.png"));
        //scaling image to fit in the hlabel.
        Image img4 = icon4.getImage();
        Image imgScale4 = img4.getScaledInstance(CancelFlightIcon.getWidth(), CancelFlightIcon.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon scaledIcon4 = new ImageIcon(imgScale4);
        CancelFlightIcon.setIcon(scaledIcon4);
    }
    private void scaleImage5(){
        ImageIcon icon5 = new ImageIcon(getClass().getResource("/imagepicker/LogOut.png"));
        //scaling image to fit in the hlabel.
        Image img5 = icon5.getImage();
        Image imgScale5 = img5.getScaledInstance(LogOutIcon.getWidth(), LogOutIcon.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon scaledIcon5 = new ImageIcon(imgScale5);
        LogOutIcon.setIcon(scaledIcon5);
    }
    private void scaleImage6(){
        ImageIcon icon6 = new ImageIcon(getClass().getResource("/imagepicker/WingsNepalLogo.jpg"));
        //scaling image to fit in the hlabel.
        Image img6 = icon6.getImage();
        Image imgScale6 = img6.getScaledInstance(WingsNepalLogo.getWidth(), WingsNepalLogo.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon scaledIcon6 = new ImageIcon(imgScale6);
        WingsNepalLogo.setIcon(scaledIcon6);
    }
        
    private void scaleToLabel(javax.swing.JLabel label, String resourcePath) {
        ImageIcon icon = new ImageIcon(getClass().getResource(resourcePath));
        Image img = icon.getImage();
        Image imgScaled = img.getScaledInstance(label.getWidth(), label.getHeight(), Image.SCALE_SMOOTH);
        label.setIcon(new ImageIcon(imgScaled));
    }

    //It calls Dao, fetches flights and fills the jTable. 
    private void SearchFlightButtonActionPerformed(java.awt.event.ActionEvent evt) {
        String from = FromTextField.getText();
        String to = ToTextField.getText();
        int year = TravelYearChooser.getYear();
        int month = TravelMonthChooser.getMonth() + 1; 
        String day = jDayChooser1.getText().trim();
        if (!day.matches("\\d{1,2}")) {
            JOptionPane.showMessageDialog(this, "Please enter a valid day (1–31)");
            return;
        }

        String date = year + "-" + String.format("%02d", month) + "-" + String.format("%02d", Integer.parseInt(day));

        SearchFlightDao dao = new SearchFlightDao();
        List<SearchFlight> flights = dao.searchFlights(from, to, date);

        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0); 

        for (SearchFlight f : flights) {
            model.addRow(new Object[]{
                f.getFlightId(),
                f.getFlightName(),
                f.getTime(),
                f.getPrice(),
                f.getDuration(),
                "Book"
            });
        }
        TableColumn bookColumn = jTable1.getColumn("Action");
        bookColumn.setCellRenderer(new ButtonRenderer());
        bookColumn.setCellEditor(new ButtonEditor(new JCheckBox(), this, jTable1));
  
    }
            
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<>();
        ButtonPanel = new javax.swing.JPanel();
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
        DashboardPanel = new javax.swing.JPanel();
        SearchFlightPanel = new javax.swing.JPanel();
        HeadingPanel = new javax.swing.JPanel();
        SearchFlightLabel = new javax.swing.JLabel();
        FromLabel = new javax.swing.JLabel();
        ToLabel = new javax.swing.JLabel();
        DateLabel = new javax.swing.JLabel();
        FromTextField = new javax.swing.JTextField();
        ToTextField = new javax.swing.JTextField();
        jDayChooser1 = new javax.swing.JTextField();
        SearchFlightButton = new javax.swing.JButton();
        jScrollBar1 = new javax.swing.JScrollBar();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        ShowAllButton = new javax.swing.JButton();
        BookFlightPanel = new javax.swing.JPanel();
        BookFlightHeadingPanel = new javax.swing.JPanel();
        BookFlightLabel = new javax.swing.JLabel();
        FlightIdLabel = new javax.swing.JLabel();
        FullNameLabel = new javax.swing.JLabel();
        EmailLabel = new javax.swing.JLabel();
        SeatClassLabel = new javax.swing.JLabel();
        PriceLabel = new javax.swing.JLabel();
        TicketsDateLabel = new javax.swing.JLabel();
        PaymentLabel = new javax.swing.JLabel();
        BookNowButton = new javax.swing.JButton();
        ClearButton = new javax.swing.JButton();
        FlightIdTextField = new javax.swing.JTextField();
        FullNameTextField = new javax.swing.JTextField();
        PriceTextField = new javax.swing.JTextField();
        SeatComboBox = new javax.swing.JComboBox<>();
        PaymentComboBox = new javax.swing.JComboBox<>();
        TicketsLAbel = new javax.swing.JLabel();
        EmailTextField = new javax.swing.JTextField();
        FlightNameLabel = new javax.swing.JLabel();
        FlightNameTextField = new javax.swing.JTextField();
        CancelFlightPanel = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        LogOutPanel = new javax.swing.JPanel();

        jList1.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane2.setViewportView(jList1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        ButtonPanel.setBackground(new java.awt.Color(46, 62, 79));
        ButtonPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        DashboardIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagepicker/Dashboard.png"))); // NOI18N
        ButtonPanel.add(DashboardIcon, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 170, 20, 20));

        SearchFlightIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagepicker/search.png"))); // NOI18N
        ButtonPanel.add(SearchFlightIcon, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 240, 30, 30));

        BookFlightIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagepicker/Book.png"))); // NOI18N
        BookFlightIcon.setText("jLabel5");
        ButtonPanel.add(BookFlightIcon, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 310, 40, 40));

        CancelFlightIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagepicker/CheckIn.png"))); // NOI18N
        ButtonPanel.add(CancelFlightIcon, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 390, 30, 30));

        LogOutIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagepicker/Logout.png"))); // NOI18N
        LogOutIcon.setText("jLabel1");
        ButtonPanel.add(LogOutIcon, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 460, 30, 30));

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
        ButtonPanel.add(DashboardButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 160, 230, 40));

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
        ButtonPanel.add(FlightButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 240, 230, 40));

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
        ButtonPanel.add(BookFlightButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 315, 230, 40));

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
        ButtonPanel.add(CheckInButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 390, 230, 40));

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
        ButtonPanel.add(LogOutButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 460, 230, 40));

        WingsNepalLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagepicker/WingsNepalLogo.jpg"))); // NOI18N
        ButtonPanel.add(WingsNepalLogo, new org.netbeans.lib.awtextra.AbsoluteConstraints(-20, -70, 270, 270));

        getContentPane().add(ButtonPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, -6, 230, 640));

        DashboardPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jTabbedPane1.addTab("tab1", DashboardPanel);

        SearchFlightPanel.setBackground(new java.awt.Color(255, 255, 255));
        SearchFlightPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        HeadingPanel.setBackground(new java.awt.Color(153, 153, 153));
        HeadingPanel.setLayout(null);

        SearchFlightLabel.setFont(new java.awt.Font("Segoe UI Emoji", 1, 24)); // NOI18N
        SearchFlightLabel.setForeground(new java.awt.Color(255, 255, 255));
        SearchFlightLabel.setText("Search Flights");
        HeadingPanel.add(SearchFlightLabel);
        SearchFlightLabel.setBounds(310, 20, 158, 27);

        SearchFlightPanel.add(HeadingPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 790, 50));

        FromLabel.setFont(new java.awt.Font("Segoe UI Emoji", 1, 18)); // NOI18N
        FromLabel.setText("From");
        SearchFlightPanel.add(FromLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, -1, -1));

        ToLabel.setFont(new java.awt.Font("Segoe UI Emoji", 1, 18)); // NOI18N
        ToLabel.setText("To");
        SearchFlightPanel.add(ToLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 170, -1, -1));

        DateLabel.setFont(new java.awt.Font("Segoe UI Emoji", 1, 18)); // NOI18N
        DateLabel.setText("Date");
        SearchFlightPanel.add(DateLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 270, -1, -1));

        FromTextField.setText("Enter departure city or airport");
        FromTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FromTextFieldActionPerformed(evt);
            }
        });
        SearchFlightPanel.add(FromTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, 190, 30));

        ToTextField.setText("Enter destination city or airport");
        ToTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ToTextFieldActionPerformed(evt);
            }
        });
        SearchFlightPanel.add(ToTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 200, 190, 30));

        jDayChooser1.setText("Day");
        jDayChooser1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jDayChooser1ActionPerformed(evt);
            }
        });
        SearchFlightPanel.add(jDayChooser1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 400, 50, 30));

        SearchFlightButton.setBackground(new java.awt.Color(0, 102, 153));
        SearchFlightButton.setFont(new java.awt.Font("Segoe UI Emoji", 1, 18)); // NOI18N
        SearchFlightButton.setForeground(new java.awt.Color(255, 255, 255));
        SearchFlightButton.setText("Search");
        SearchFlightButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SearchFlightButtonActionPerformed(evt);
            }
        });
        SearchFlightPanel.add(SearchFlightButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 460, 190, 40));
        SearchFlightPanel.add(jScrollBar1, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 90, 10, 560));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Flight id", "Flight name", "Time", "Price", "Duration ", "Date", "Action"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.String.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTable1);

        SearchFlightPanel.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 90, 580, 560));

        ShowAllButton.setBackground(new java.awt.Color(0, 102, 153));
        ShowAllButton.setFont(new java.awt.Font("Segoe UI Emoji", 1, 18)); // NOI18N
        ShowAllButton.setForeground(new java.awt.Color(255, 255, 255));
        ShowAllButton.setText("Show all");
        ShowAllButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ShowAllButtonActionPerformed(evt);
            }
        });
        SearchFlightPanel.add(ShowAllButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 530, 190, 40));

        jTabbedPane1.addTab("tab2", SearchFlightPanel);

        BookFlightPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        BookFlightHeadingPanel.setBackground(new java.awt.Color(153, 153, 153));
        BookFlightHeadingPanel.setLayout(null);

        BookFlightLabel.setFont(new java.awt.Font("Segoe UI Emoji", 1, 24)); // NOI18N
        BookFlightLabel.setForeground(new java.awt.Color(255, 255, 255));
        BookFlightLabel.setText("Book Flight");
        BookFlightHeadingPanel.add(BookFlightLabel);
        BookFlightLabel.setBounds(320, 20, 129, 27);

        BookFlightPanel.add(BookFlightHeadingPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 790, 50));

        FlightIdLabel.setFont(new java.awt.Font("Segoe UI Emoji", 1, 18)); // NOI18N
        FlightIdLabel.setText("Flight ID");
        BookFlightPanel.add(FlightIdLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 80, -1, -1));

        FullNameLabel.setFont(new java.awt.Font("Segoe UI Emoji", 1, 18)); // NOI18N
        FullNameLabel.setText("Full Name");
        BookFlightPanel.add(FullNameLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 200, -1, -1));

        EmailLabel.setFont(new java.awt.Font("Segoe UI Emoji", 1, 18)); // NOI18N
        EmailLabel.setText("Email");
        BookFlightPanel.add(EmailLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 260, -1, -1));

        SeatClassLabel.setFont(new java.awt.Font("Segoe UI Emoji", 1, 18)); // NOI18N
        SeatClassLabel.setText("Seat Class");
        BookFlightPanel.add(SeatClassLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 320, -1, -1));

        PriceLabel.setFont(new java.awt.Font("Segoe UI Emoji", 1, 18)); // NOI18N
        PriceLabel.setText("Price");
        BookFlightPanel.add(PriceLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 380, -1, -1));

        TicketsDateLabel.setFont(new java.awt.Font("Segoe UI Emoji", 1, 18)); // NOI18N
        TicketsDateLabel.setText("Travel Date");
        BookFlightPanel.add(TicketsDateLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 500, -1, -1));

        PaymentLabel.setFont(new java.awt.Font("Segoe UI Emoji", 1, 18)); // NOI18N
        PaymentLabel.setText("Payment");
        BookFlightPanel.add(PaymentLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 560, -1, -1));

        BookNowButton.setBackground(new java.awt.Color(0, 102, 153));
        BookNowButton.setFont(new java.awt.Font("Segoe UI Emoji", 0, 16)); // NOI18N
        BookNowButton.setForeground(new java.awt.Color(255, 255, 255));
        BookNowButton.setText("Book now");
        BookFlightPanel.add(BookNowButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 600, 140, 30));

        ClearButton.setBackground(new java.awt.Color(0, 102, 153));
        ClearButton.setFont(new java.awt.Font("Segoe UI Emoji", 0, 16)); // NOI18N
        ClearButton.setForeground(new java.awt.Color(255, 255, 255));
        ClearButton.setText("Clear");
        ClearButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ClearButtonActionPerformed(evt);
            }
        });
        BookFlightPanel.add(ClearButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 600, 130, 30));

        FlightIdTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FlightIdTextFieldActionPerformed(evt);
            }
        });
        BookFlightPanel.add(FlightIdTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 70, 280, 30));

        FullNameTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FullNameTextFieldActionPerformed(evt);
            }
        });
        BookFlightPanel.add(FullNameTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 190, 280, 30));
        BookFlightPanel.add(PriceTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 370, 280, 30));

        SeatComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Economy", "First Class", "Business" }));
        SeatComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SeatComboBoxActionPerformed(evt);
            }
        });
        BookFlightPanel.add(SeatComboBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 310, 280, 30));

        PaymentComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Card", "Cash" }));
        PaymentComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PaymentComboBoxActionPerformed(evt);
            }
        });
        BookFlightPanel.add(PaymentComboBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 550, 280, 30));

        TicketsLAbel.setFont(new java.awt.Font("Segoe UI Emoji", 1, 18)); // NOI18N
        TicketsLAbel.setText("Tickets");
        BookFlightPanel.add(TicketsLAbel, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 440, -1, -1));
        BookFlightPanel.add(EmailTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 250, 280, 30));

        FlightNameLabel.setFont(new java.awt.Font("Segoe UI Emoji", 1, 18)); // NOI18N
        FlightNameLabel.setText("Flight Name");
        BookFlightPanel.add(FlightNameLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 140, -1, -1));

        FlightNameTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FlightNameTextFieldActionPerformed(evt);
            }
        });
        BookFlightPanel.add(FlightNameTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 130, 280, 30));

        jTabbedPane1.addTab("tab3", BookFlightPanel);

        CancelFlightPanel.add(jLabel4);

        jTabbedPane1.addTab("tab4", CancelFlightPanel);
        jTabbedPane1.addTab("tab5", LogOutPanel);

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
        int confirm = javax.swing.JOptionPane.showConfirmDialog(this, "Are you sure you want to log out?", "Log out", javax.swing.JOptionPane.YES_NO_OPTION);
        if (confirm == javax.swing.JOptionPane.YES_OPTION) {
            this.dispose(); // Close the current window
            new LoginPage().setVisible(true); // Open the login window
        }
        
    }//GEN-LAST:event_LogOutButtonActionPerformed

    private void FromTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FromTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_FromTextFieldActionPerformed

    private void ShowAllButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ShowAllButtonActionPerformed
        // TODO add your handling code here:
        SearchFlightDao dao = new SearchFlightDao();
        List<SearchFlight> flights = dao.getAllFlights();  

        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);  // Clear existing rows
        for (SearchFlight f : flights) {
            model.addRow(new Object[]{
            f.getFlightId(),
            f.getFlightName(),
            f.getTime(),
            f.getPrice(),
            f.getDuration(),   
            f.getDate(),       
            "Book"
        });
    }


        //Using button renderer and button editor to place book flight button in every jTable1.
        TableColumn bookColumn = jTable1.getColumn("Action");
        bookColumn.setCellRenderer(new ButtonRenderer());
        bookColumn.setCellEditor(new ButtonEditor(new JCheckBox(), this, jTable1));

    }//GEN-LAST:event_ShowAllButtonActionPerformed

    private void ToTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ToTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ToTextFieldActionPerformed

    private void PaymentComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PaymentComboBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_PaymentComboBoxActionPerformed

    private void FlightIdTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FlightIdTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_FlightIdTextFieldActionPerformed

    private void FullNameTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FullNameTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_FullNameTextFieldActionPerformed

    private void SeatComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SeatComboBoxActionPerformed
        String seatClass = (String) SeatComboBox.getSelectedItem();
        String flightIdText = FlightIdTextField.getText();

        if (!flightIdText.isEmpty()) {
            try {
                int flightId = Integer.parseInt(flightIdText);
                SeatClassDao dao = new SeatClassDao();
                int price = dao.getPriceByFlightAndClass(flightId, seatClass);

                if (price != -1) {
                    PriceTextField.setText(String.valueOf(price));
                } else {
                    PriceTextField.setText("N/A");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid flight ID format.");
            }
        }
    }//GEN-LAST:event_SeatComboBoxActionPerformed

    private void FlightNameTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FlightNameTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_FlightNameTextFieldActionPerformed

    private void jDayChooser1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jDayChooser1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jDayChooser1ActionPerformed

    private void ClearButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ClearButtonActionPerformed
        // Clear all text fields
    FlightIdTextField.setText("");
    FlightNameTextField.setText("");
    FullNameTextField.setText("");
    EmailTextField.setText("");
    PriceTextField.setText("");

    // Reset combo boxes
    SeatComboBox.setSelectedIndex(0);
    PaymentComboBox.setSelectedIndex(0);

    // Reset spin fields
    TicketSpinField.setValue(1);
    TravelDaySpinnerField.setValue(1);
    TravelMonthChooser.setMonth(0); // January
    TravelYearChooser.setYear(java.util.Calendar.getInstance().get(java.util.Calendar.YEAR));
    }//GEN-LAST:event_ClearButtonActionPerformed

    class ButtonEditor extends javax.swing.DefaultCellEditor {
    private javax.swing.JButton button;
    private String label;
    private boolean isPushed;
    private UserPortal userPortal;
    private javax.swing.JTable table;

    public ButtonEditor(JCheckBox checkBox, UserPortal userPortal, javax.swing.JTable table) {
        super(checkBox);
        this.userPortal = userPortal;
        this.table = table;
        button = new javax.swing.JButton();
        button.setOpaque(true);
        button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                fireEditingStopped();
            }
        });
    }

    @Override
    public java.awt.Component getTableCellEditorComponent(javax.swing.JTable table, Object value,
            boolean isSelected, int row, int column) {
        label = (value == null) ? "Book" : value.toString();
        button.setText(label);
        isPushed = true;
        return button;
    }

    @Override
    public Object getCellEditorValue() {
        if (isPushed) {
            int row = table.getSelectedRow();

            Object flightId = table.getValueAt(row, 0);
            Object flightName = table.getValueAt(row, 1);
            Object dateValue = table.getValueAt(row, 5); // column index 5 = Date

            userPortal.FlightIdTextField.setText(flightId.toString());
            userPortal.FlightNameTextField.setText(flightName.toString());
            userPortal.SeatComboBox.setSelectedItem("Economy");

            // Fetch economy class price
            int economyPrice = new SeatClassDao().getPriceByFlightAndClass(Integer.parseInt(flightId.toString()), "Economy");
            userPortal.PriceTextField.setText(String.valueOf(economyPrice));

            // Auto-fill FullName and Email from logged-in user
            userPortal.FullNameTextField.setText(userPortal.loggedInUser.getFullName());
            userPortal.EmailTextField.setText(userPortal.loggedInUser.getEmail());
            
            // Fill the travel data    
            if (dateValue != null) {
                java.sql.Date sqlDate = java.sql.Date.valueOf(dateValue.toString());
                java.util.Calendar cal = java.util.Calendar.getInstance();
                cal.setTime(sqlDate);
                userPortal.TravelYearChooser.setYear(cal.get(java.util.Calendar.YEAR));
                userPortal.TravelMonthChooser.setMonth(cal.get(java.util.Calendar.MONTH));
                userPortal.TravelDaySpinnerField.setValue(cal.get(java.util.Calendar.DAY_OF_MONTH));
            }

            userPortal.jTabbedPane1.setSelectedIndex(2); // Go to Book Flight tab

        }
        isPushed = false;
        return label;
    }

    @Override
    public boolean stopCellEditing() {
        isPushed = false;
        return super.stopCellEditing();
    }

    @Override
    protected void fireEditingStopped() {
        super.fireEditingStopped();
    }
}

    
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            Login dummy = new Login(1, "User", "user@wingsnepal.com", "user123", "User");
            new UserPortal(dummy).setVisible(true);
        });
    }

    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BookFlightButton;
    private javax.swing.JPanel BookFlightHeadingPanel;
    private javax.swing.JLabel BookFlightIcon;
    private javax.swing.JLabel BookFlightLabel;
    private javax.swing.JPanel BookFlightPanel;
    private javax.swing.JButton BookNowButton;
    private javax.swing.JPanel ButtonPanel;
    private javax.swing.JLabel CancelFlightIcon;
    private javax.swing.JPanel CancelFlightPanel;
    private javax.swing.JButton CheckInButton;
    private javax.swing.JButton ClearButton;
    private javax.swing.JButton DashboardButton;
    private javax.swing.JLabel DashboardIcon;
    private javax.swing.JPanel DashboardPanel;
    private javax.swing.JLabel DateLabel;
    private javax.swing.JLabel EmailLabel;
    private javax.swing.JTextField EmailTextField;
    private javax.swing.JButton FlightButton;
    private javax.swing.JLabel FlightIdLabel;
    private javax.swing.JTextField FlightIdTextField;
    private javax.swing.JLabel FlightNameLabel;
    private javax.swing.JTextField FlightNameTextField;
    private javax.swing.JLabel FromLabel;
    private javax.swing.JTextField FromTextField;
    private javax.swing.JLabel FullNameLabel;
    private javax.swing.JTextField FullNameTextField;
    private javax.swing.JPanel HeadingPanel;
    private javax.swing.JButton LogOutButton;
    private javax.swing.JLabel LogOutIcon;
    private javax.swing.JPanel LogOutPanel;
    private javax.swing.JComboBox<String> PaymentComboBox;
    private javax.swing.JLabel PaymentLabel;
    private javax.swing.JLabel PriceLabel;
    private javax.swing.JTextField PriceTextField;
    private javax.swing.JButton SearchFlightButton;
    private javax.swing.JLabel SearchFlightIcon;
    private javax.swing.JLabel SearchFlightLabel;
    private javax.swing.JPanel SearchFlightPanel;
    private javax.swing.JLabel SeatClassLabel;
    private javax.swing.JComboBox<String> SeatComboBox;
    private javax.swing.JButton ShowAllButton;
    private javax.swing.JLabel TicketsDateLabel;
    private javax.swing.JLabel TicketsLAbel;
    private javax.swing.JLabel ToLabel;
    private javax.swing.JTextField ToTextField;
    private javax.swing.JLabel WingsNepalLogo;
    private javax.swing.JTextField jDayChooser1;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JList<String> jList1;
    private javax.swing.JScrollBar jScrollBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables

}
