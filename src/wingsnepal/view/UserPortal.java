/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package wingsnepal.view;

import java.awt.Desktop;
import java.awt.Image;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import wingsnepal.dao.SearchFlightDao;
import wingsnepal.model.UserData;
import wingsnepal.model.SearchFlight;
import wingsnepal.dao.SeatClassDao;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import wingsnepal.dao.BookingFlightDao;
import wingsnepal.model.BookingFlight;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import wingsnepalController.ManageBookingController;
import wingsnepal.model.StripePayment;
import wingsnepal.view.BookButtonRenderer;
import wingsnepalController.TicketGenerator;




/**
 *
 * @author Aayush Kharel
 */
public class UserPortal extends javax.swing.JFrame{
    
    private UserData loggedInUser;
    private javax.swing.JTable JTable2;

    //Constructor for login-based usage
    public UserPortal(UserData user) {
        this.loggedInUser = user; // store the user
        initComponents();
 
        
        //Show user's name on screen or use it anywhere you want
        setTitle("User Portal - Welcome, " + loggedInUser.getFullName());
        
        
        // Adding hovering effect to buttons to make it look modern.
        styleFlatHoverButton(DashboardButton);
        styleFlatHoverButton(FlightButton);
        styleFlatHoverButton(BookFlightButton);
        styleFlatHoverButton(EditButton);
        styleFlatHoverButton(LogOutButton);
        
            
        // Adding scrolling feature.
        jScrollPane1.setViewportView(jTable1);
            
        //ActionListener for showall button.
        ShowAllButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ShowAllButtonActionPerformed(evt);
            }
        });
        
        
        
        PaymentComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] {
            "Cash", "Stripe", "eSewa", "Khalti"
        }));

        
        //Disable maximize button
        setResizable(false); 
        
        //Center window
        setLocationRelativeTo(null); 
        
        //calling sacleimage:
        scaleImage1();  
        scaleImage2();
        scaleImage3();
        scaleImage4();
        scaleImage5();
        scaleImage6();
        scaleImage7();
        
        //calling setupPlaceholder();
        setupPlaceholders();
        
        //Font for textfields:
        Font textFieldFont = new Font("Segoe UI", Font.PLAIN, 16); // Choose a clean, modern font
        FromTextField.setFont(textFieldFont);
        ToTextField.setFont(textFieldFont);
        TravelDayChooser.setFont(textFieldFont); // your day input field

        FlightCodeTextField.setFont(textFieldFont);
        FlightNameTextField.setFont(textFieldFont);
        FullNameTextField.setFont(textFieldFont);
        EmailTextField.setFont(textFieldFont);
        PriceTextField.setFont(textFieldFont);    
   
    }
    
    // Getter method for loggedInUser
    public UserData getLoggedInUser() {
        return loggedInUser;
    }
    
    // Getter method for jTable2
    public javax.swing.JTable getJTable2() {
        return jTable2;
    }

    //It is for placinf text in a textfield and on clicking to write something wilol disappear the text written there.
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

        TravelDayChooser.setForeground(java.awt.Color.GRAY);
        TravelDayChooser.setValue(0); // Default placeholder value (e.g. 0 = "not selected")

        TravelDayChooser.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
                public void focusGained(java.awt.event.FocusEvent evt) {
                if (TravelDayChooser.getValue() == 0) {
                    TravelDayChooser.setForeground(java.awt.Color.BLACK);
                }
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (TravelDayChooser.getValue() == 0) {
                    TravelDayChooser.setForeground(java.awt.Color.GRAY);
                }
            }
        });
    }
    // Method to refresh the Search Flights tab
    public void refreshSearchFlights() {
        // Fetch updated flight data
        SearchFlightDao searchFlightDao = new SearchFlightDao();
        List<SearchFlight> flights = searchFlightDao.getAllFlights();  // Or any specific search method

        // Clear the previous search results in jTable1
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);  // Clear existing rows

        // Populate the table with updated flight data
        for (SearchFlight flight : flights) {
            model.addRow(new Object[] {
                flight.getFlightCode(),
                flight.getFlightName(),
                flight.getTime(),
                flight.getPrice(),
                flight.getDuration(),
                "Book"
            });
        }

        // Ensure the seat availability is updated after deleting the booking
        TableColumn bookColumn = jTable1.getColumn("Action");
        bookColumn.setCellRenderer(new BookButtonRenderer());
        bookColumn.setCellEditor(new BookButtonEditor(this, jTable1));
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
    private void scaleImage7(){
        ImageIcon icon7 = new ImageIcon(getClass().getResource("/imagepicker/Dashboard_bg.png"));
        //scaling image to fit in the label.
        Image img7 = icon7.getImage();
        Image imgScale7 = img7.getScaledInstance(DashboardBgLabel.getWidth(), DashboardBgLabel.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon scaledIcon7 = new ImageIcon(imgScale7);
        DashboardBgLabel.setIcon(scaledIcon7);
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
        int year = TravelDateChooser.getYear();
        int month = TicektMonthFiled.getMonth() + 1; 
        int day = TravelDayChooser.getValue();
        if (day < 1 || day > 31) {
            JOptionPane.showMessageDialog(this, "Please enter a valid day (1–31)");
            return;
        }

        String date = String.format("%04d-%02d-%02d", year, month, day);

        SearchFlightDao dao = new SearchFlightDao();
        List<SearchFlight> flights = dao.searchFlights(from, to, date);

        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0); 

        for (SearchFlight f : flights) {
            model.addRow(new Object[]{
                f.getFlightCode(),
                f.getFlightName(),
                f.getTime(),
                f.getPrice(),
                f.getDuration(),
                "Book"
            });
        }
        TableColumn bookColumn = jTable1.getColumn("Action");
        bookColumn.setCellRenderer(new BookButtonRenderer());
        bookColumn.setCellEditor(new BookButtonEditor(this, jTable1));
  
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
        EditButton = new javax.swing.JButton();
        LogOutButton = new javax.swing.JButton();
        WingsNepalLogo = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        DashboardPanel = new javax.swing.JPanel();
        DashboardBgLabel = new javax.swing.JLabel();
        SearchFlightPanel = new javax.swing.JPanel();
        HeadingPanel = new javax.swing.JPanel();
        SearchFlightLabel = new javax.swing.JLabel();
        FromLabel = new javax.swing.JLabel();
        ToLabel = new javax.swing.JLabel();
        DateLabel = new javax.swing.JLabel();
        FromTextField = new javax.swing.JTextField();
        ToTextField = new javax.swing.JTextField();
        SearchFlightButton = new javax.swing.JButton();
        jScrollBar1 = new javax.swing.JScrollBar();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        ShowAllButton = new javax.swing.JButton();
        TravelDateChooser = new com.toedter.calendar.JYearChooser();
        TravelMonthChooser = new com.toedter.calendar.JMonthChooser();
        TravelDayChooser = new com.toedter.components.JSpinField();
        BookFlightPanel = new javax.swing.JPanel();
        BookFlightHeadingPanel = new javax.swing.JPanel();
        BookFlightLabel = new javax.swing.JLabel();
        FlightCodeLabel = new javax.swing.JLabel();
        FullNameLabel = new javax.swing.JLabel();
        EmailLabel = new javax.swing.JLabel();
        SeatClassLabel = new javax.swing.JLabel();
        PriceLabel = new javax.swing.JLabel();
        TicketsDateLabel = new javax.swing.JLabel();
        PaymentLabel = new javax.swing.JLabel();
        BookNowButton = new javax.swing.JButton();
        ClearButton = new javax.swing.JButton();
        FlightCodeTextField = new javax.swing.JTextField();
        FullNameTextField = new javax.swing.JTextField();
        PriceTextField = new javax.swing.JTextField();
        SeatComboBox = new javax.swing.JComboBox<>();
        PaymentComboBox = new javax.swing.JComboBox<>();
        TicketsLAbel = new javax.swing.JLabel();
        EmailTextField = new javax.swing.JTextField();
        FlightNameLabel = new javax.swing.JLabel();
        FlightNameTextField = new javax.swing.JTextField();
        SeatNumberLabel = new javax.swing.JLabel();
        TicektMonthFiled = new com.toedter.calendar.JMonthChooser();
        TicketSpinField = new com.toedter.components.JSpinField();
        TravelYearChooser = new com.toedter.calendar.JYearChooser();
        TravelDaySpinnerField = new com.toedter.components.JSpinField();
        SeatNoComboBox = new javax.swing.JComboBox<>();
        ManageBookingPanel = new javax.swing.JPanel();
        jScrollBar2 = new javax.swing.JScrollBar();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        ShowMyBookingButton = new javax.swing.JButton();
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

        EditButton.setBackground(new java.awt.Color(46, 62, 79));
        EditButton.setFont(new java.awt.Font("Segoe UI Emoji", 1, 18)); // NOI18N
        EditButton.setForeground(new java.awt.Color(255, 255, 255));
        EditButton.setText("Edit Flight");
        EditButton.setBorderPainted(false);
        EditButton.setContentAreaFilled(false);
        EditButton.setFocusPainted(false);
        EditButton.setVerifyInputWhenFocusTarget(false);
        EditButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EditButtonActionPerformed(evt);
            }
        });
        ButtonPanel.add(EditButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 390, 230, 40));

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

        DashboardBgLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagepicker/Dashboard_bg.png"))); // NOI18N
        DashboardPanel.add(DashboardBgLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 790, 640));

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

        SearchFlightButton.setBackground(new java.awt.Color(0, 102, 153));
        SearchFlightButton.setFont(new java.awt.Font("Segoe UI Emoji", 1, 18)); // NOI18N
        SearchFlightButton.setForeground(new java.awt.Color(255, 255, 255));
        SearchFlightButton.setText("Search");
        SearchFlightButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SearchFlightButtonActionPerformed(evt);
            }
        });
        SearchFlightPanel.add(SearchFlightButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 460, 190, 30));
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
                "Flight code", "Flight name", "Time", "Price", "Duration ", "Date", "Action"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.String.class, java.lang.Object.class
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
        SearchFlightPanel.add(ShowAllButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 500, 190, 30));
        SearchFlightPanel.add(TravelDateChooser, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 300, 190, 30));
        SearchFlightPanel.add(TravelMonthChooser, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 350, 190, 30));
        SearchFlightPanel.add(TravelDayChooser, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 400, 70, 30));

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

        FlightCodeLabel.setFont(new java.awt.Font("Segoe UI Emoji", 1, 18)); // NOI18N
        FlightCodeLabel.setText("Flight Code");
        BookFlightPanel.add(FlightCodeLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 80, -1, -1));

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
        BookNowButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BookNowButtonActionPerformed(evt);
            }
        });
        BookFlightPanel.add(BookNowButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 600, 190, 30));

        ClearButton.setBackground(new java.awt.Color(0, 102, 153));
        ClearButton.setFont(new java.awt.Font("Segoe UI Emoji", 0, 16)); // NOI18N
        ClearButton.setForeground(new java.awt.Color(255, 255, 255));
        ClearButton.setText("Clear");
        ClearButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ClearButtonActionPerformed(evt);
            }
        });
        BookFlightPanel.add(ClearButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 600, 180, 30));

        FlightCodeTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FlightCodeTextFieldActionPerformed(evt);
            }
        });
        BookFlightPanel.add(FlightCodeTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 70, 380, 30));

        FullNameTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FullNameTextFieldActionPerformed(evt);
            }
        });
        BookFlightPanel.add(FullNameTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 190, 380, 30));
        BookFlightPanel.add(PriceTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 370, 380, 30));

        SeatComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Economy", "First Class", "Business" }));
        SeatComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SeatComboBoxActionPerformed(evt);
            }
        });
        BookFlightPanel.add(SeatComboBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 310, 140, 30));

        PaymentComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Cash", "Esewa", "Khalti", " " }));
        PaymentComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PaymentComboBoxActionPerformed(evt);
            }
        });
        BookFlightPanel.add(PaymentComboBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 550, 380, 30));

        TicketsLAbel.setFont(new java.awt.Font("Segoe UI Emoji", 1, 18)); // NOI18N
        TicketsLAbel.setText("Tickets");
        BookFlightPanel.add(TicketsLAbel, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 440, -1, -1));
        BookFlightPanel.add(EmailTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 250, 380, 30));

        FlightNameLabel.setFont(new java.awt.Font("Segoe UI Emoji", 1, 18)); // NOI18N
        FlightNameLabel.setText("Flight Name");
        BookFlightPanel.add(FlightNameLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 140, -1, -1));

        FlightNameTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FlightNameTextFieldActionPerformed(evt);
            }
        });
        BookFlightPanel.add(FlightNameTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 130, 380, 30));

        SeatNumberLabel.setFont(new java.awt.Font("Segoe UI Emoji", 1, 18)); // NOI18N
        SeatNumberLabel.setText("Seat No");
        BookFlightPanel.add(SeatNumberLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 320, -1, -1));
        BookFlightPanel.add(TicektMonthFiled, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 490, 160, 30));
        BookFlightPanel.add(TicketSpinField, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 430, 380, 30));
        BookFlightPanel.add(TravelYearChooser, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 490, 170, 30));
        BookFlightPanel.add(TravelDaySpinnerField, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 490, 80, 30));

        SeatNoComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SeatNoComboBoxActionPerformed(evt);
            }
        });
        BookFlightPanel.add(SeatNoComboBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 310, 150, 30));

        jTabbedPane1.addTab("tab3", BookFlightPanel);

        ManageBookingPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        ManageBookingPanel.add(jScrollBar2, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 0, -1, 640));

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
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
                "Booking Id", "Flight Code", "Seat No", "Seat Class", "Travel Date", "Payment Method", "Action"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane3.setViewportView(jTable2);

        ManageBookingPanel.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 70, 790, 570));

        ShowMyBookingButton.setBackground(new java.awt.Color(0, 102, 153));
        ShowMyBookingButton.setFont(new java.awt.Font("Segoe UI Emoji", 1, 18)); // NOI18N
        ShowMyBookingButton.setForeground(new java.awt.Color(255, 255, 255));
        ShowMyBookingButton.setText("My Bookings");
        ShowMyBookingButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ShowMyBookingButtonActionPerformed(evt);
            }
        });
        ManageBookingPanel.add(ShowMyBookingButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, -1, -1));

        jTabbedPane1.addTab("tab4", ManageBookingPanel);
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

    private void EditButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EditButtonActionPerformed
        // TODO add your handling code here:
        jTabbedPane1.setSelectedIndex(3); //Check in
    }//GEN-LAST:event_EditButtonActionPerformed

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
            f.getFlightCode(),
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
        bookColumn.setCellRenderer(new BookButtonRenderer());
        bookColumn.setCellEditor(new ButtonEditor(new JCheckBox(), this, jTable1));

    }//GEN-LAST:event_ShowAllButtonActionPerformed

    private void ToTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ToTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ToTextFieldActionPerformed

    private void PaymentComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PaymentComboBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_PaymentComboBoxActionPerformed

    private void FlightCodeTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FlightCodeTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_FlightCodeTextFieldActionPerformed

    private void FullNameTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FullNameTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_FullNameTextFieldActionPerformed

    private void SeatComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SeatComboBoxActionPerformed
        String selectedSeatClass = (String) SeatComboBox.getSelectedItem();
        String flightCode = FlightCodeTextField.getText();

        if (!flightCode.isEmpty()) {
            SeatClassDao dao = new SeatClassDao();
            int flightId = dao.getFlightIdByCode(flightCode);

            // Fetch available seats for the selected flight and seat class
            List<String> seatNumbers = dao.getAvailableSeats(flightId, selectedSeatClass);
            SeatNoComboBox.removeAllItems(); // clear existing items

            if (seatNumbers.isEmpty()) {
                SeatNoComboBox.addItem("No seats available");
            } else {
                for (String seat : seatNumbers) {
                    SeatNoComboBox.addItem(seat);
                }
            }
        }

    }//GEN-LAST:event_SeatComboBoxActionPerformed

    private void FlightNameTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FlightNameTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_FlightNameTextFieldActionPerformed

    private void ClearButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ClearButtonActionPerformed
        FlightCodeTextField.setText("");
        FlightNameTextField.setText("");
        FullNameTextField.setText("");
        EmailTextField.setText("");
        PriceTextField.setText("");

        restoreEditableField(FlightCodeTextField);
        restoreEditableField(FlightNameTextField);
        restoreEditableField(FullNameTextField);
        restoreEditableField(EmailTextField);

        SeatComboBox.setSelectedIndex(0);
        PaymentComboBox.setSelectedIndex(0);
        TicketSpinField.setValue(1);
        TravelDaySpinnerField.setValue(1);

        unlockTravelDateFields();
    }//GEN-LAST:event_ClearButtonActionPerformed

    private void BookNowButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BookNowButtonActionPerformed
        System.out.println("BookNow button clicked");

        try {
            // Collect booking details
            int userId = loggedInUser.getUserId();
            String flightCode = FlightCodeTextField.getText();
            String seatClass = SeatComboBox.getSelectedItem().toString();
            String seatNo = SeatNoComboBox.getSelectedItem().toString();
            String fullName = FullNameTextField.getText();
            String email = EmailTextField.getText();
            int tickets = TicketSpinField.getValue();
            int year = TravelYearChooser.getYear();
            int month = TravelMonthChooser.getMonth() + 1;
            int day = TravelDaySpinnerField.getValue();
            String paymentMethod = PaymentComboBox.getSelectedItem().toString();

            // Parse the travel date
            java.sql.Date travelDate = java.sql.Date.valueOf(String.format("%04d-%02d-%02d", year, month, day));

            // Get seat_id using SeatClassDao
            SeatClassDao seatDao = new SeatClassDao();
            int flightId = seatDao.getFlightIdByCode(flightCode);
            int seatId = seatDao.getSeatIdByFlightAndClass(flightId, seatClass);

            if (seatId == -1) {
                JOptionPane.showMessageDialog(this, "Invalid seat class or flight ID.");
                return;
            }

            // Handle the payment method
            if (paymentMethod.equals("Stripe")) {
                // Handle Stripe Payment in a separate thread to avoid UI freezing
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            // Create a Stripe session in the background
                            StripePayment stripe = new StripePayment();
                            String sessionUrl = stripe.createCheckoutSession();

                            // Open the Stripe payment page in the browser
                            if (sessionUrl != null) {
                                java.awt.Desktop.getDesktop().browse(new java.net.URI(sessionUrl));
                                JOptionPane.showMessageDialog(null, "Payment page opened in browser.\nPlease complete the payment.");

                                // Wait for payment completion
                                boolean success = stripe.checkPaymentStatus(sessionUrl);
                                if (success) {
                                    processBooking(userId, flightId, seatId, fullName, email, seatClass, seatNo, tickets, travelDate, paymentMethod);
                                } else {
                                    JOptionPane.showMessageDialog(null, "Payment failed or cancelled.");
                                }
                            } else {
                                JOptionPane.showMessageDialog(null, "Failed to initialize Stripe payment.");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            JOptionPane.showMessageDialog(null, "An error occurred during the payment process.");
                        }
                    }
                }).start(); // Start the thread
            } else if (paymentMethod.equals("Cash")) {
                // Handle Cash payment (e.g., assume payment is successful)
                processBooking(userId, flightId, seatId, fullName, email, seatClass, seatNo, tickets, travelDate, "Cash");
            } else if (paymentMethod.equals("eSewa")) {
                // Handle eSewa payment
                processWalletPayment("eSewa", userId, flightId, seatId, fullName, email, seatClass, seatNo, tickets, travelDate);
            } else if (paymentMethod.equals("Khalti")) {
                // Handle Khalti payment
                processWalletPayment("Khalti", userId, flightId, seatId, fullName, email, seatClass, seatNo, tickets, travelDate);
            } else {
                JOptionPane.showMessageDialog(this, "Please select a valid payment method.");
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            ex.printStackTrace();
        }
    }//GEN-LAST:event_BookNowButtonActionPerformed
    
    private void processWalletPayment(String walletName, int userId, int flightId, int seatId, String fullName, String email, String seatClass, String seatNo, int tickets, java.sql.Date travelDate) {
        boolean isPaymentSuccess = showWalletPaymentDialog(walletName);

        if (isPaymentSuccess) {
            // Proceed with booking if payment is successful
            processBooking(userId, flightId, seatId, fullName, email, seatClass, seatNo, tickets, travelDate, walletName);
        } else {
            JOptionPane.showMessageDialog(this, "Payment failed or cancelled.");
        }
    }

    private boolean showWalletPaymentDialog(String walletName) {
        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        JTextField phoneField = new JTextField();
        JTextField otpField = new JTextField();

        panel.add(new JLabel(walletName + " Mobile Number:"));
        panel.add(phoneField);
        panel.add(new JLabel("OTP Code:"));
        panel.add(otpField);

        int result = JOptionPane.showConfirmDialog(
            this, panel, "Pay with " + walletName,
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE
        );

        if (result == JOptionPane.OK_OPTION) {
            String phone = phoneField.getText().trim();
            String otp = otpField.getText().trim();
            if (!phone.matches("\\d{10}") || !otp.matches("\\d{4,6}")) {
                JOptionPane.showMessageDialog(this, "Invalid phone or OTP");
                return false;
            }
            return true; // Simulate success
        }

        return false; // Cancelled
    }

    private void processBooking(int userId, int flightId, int seatId, String fullName, String email, String seatClass, String seatNo, int tickets, java.sql.Date travelDate, String paymentMethod) {
        try {
            // Create the booking (save it to the database or any other storage)
            BookingFlight booking = new BookingFlight(userId, flightId, seatId, fullName, email, seatClass, seatNo, tickets, travelDate, paymentMethod);
            BookingFlightDao dao = new BookingFlightDao();

            if (dao.saveBooking(booking)) {
                // Mark the seat as booked
                SeatClassDao seatDao = new SeatClassDao();
                seatDao.markSeatAsBooked(flightId, seatClass, seatNo);

                // Fetch flight name and flight code from your database or model
                SearchFlightDao flightDao = new SearchFlightDao();
                String flightCode = flightDao.getFlightCodeById(flightId);
                String flightName = flightDao.getFlightNameById(flightId);

                // Generate the ticket after successful booking
                TicketGenerator ticketGenerator = new TicketGenerator();
                ticketGenerator.generateTicket(userId, flightCode, flightName, seatClass, seatNo, fullName, email, travelDate, paymentMethod);

                JOptionPane.showMessageDialog(this, "Flight booked and ticket generated successfully!");
                ClearButtonActionPerformed(null); // Clear the form after booking
            } else {
                JOptionPane.showMessageDialog(this, "Failed to book flight.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error processing booking: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    
    
    private void updateSeatClassPrice() {    
        try {
            String flightCode = FlightCodeTextField.getText().trim();
            String selectedClass = (String) SeatComboBox.getSelectedItem();

            if (!flightCode.isEmpty() && selectedClass != null && !selectedClass.isEmpty()) {
                SeatClassDao dao = new SeatClassDao();
                int flightId = dao.getFlightIdByCode(flightCode);

                int price = dao.getPriceByFlightAndClass(flightId, selectedClass);

                if (price != -1) {
                    PriceTextField.setText(String.valueOf(price));
                } else {
                    PriceTextField.setText("N/A");
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            PriceTextField.setText("Error");
        }
    }
    private void SeatNoComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SeatNoComboBoxActionPerformed
//        String selectedSeatClass = (String) SeatComboBox.getSelectedItem();
//        String flightCode = FlightCodeTextField.getText();
//
//        if (!flightCode.isEmpty()) {
//            SeatClassDao dao = new SeatClassDao();
//            int flightId = dao.getFlightIdByCode(flightCode);  // make sure this works
//
//            List<String> availableSeats = dao.getAvailableSeats(flightId, selectedSeatClass);
//            SeatNoComboBox.removeAllItems();
//
//            if (availableSeats.isEmpty()) {
//                SeatNoComboBox.addItem("No seats available");
//            } else {
//                for (String seat : availableSeats) {
//                }
//            }
//        }
    }//GEN-LAST:event_SeatNoComboBoxActionPerformed
    public void showBookings(int userId) {
        // Assuming you have a ManageBookingController that handles the logic for showing bookings
        ManageBookingController controller = new ManageBookingController(this);
        controller.showBookings(userId);  // Fetch and display the user's bookings
    }

    private void ShowMyBookingButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ShowMyBookingButtonActionPerformed
    // Get the logged-in user's ID
    int userId = loggedInUser.getUserId();
    
    // Call the controller to display the bookings for the logged-in user
    ManageBookingController controller = new ManageBookingController(this);
    controller.showBookings(userId);  // Pass the user ID to fetch the bookings
    
    TableColumn actionColumn = jTable2.getColumn("Action");
    jTable2.setRowHeight(35);
    jTable2.getColumn("Action").setCellRenderer(new EditButtonRenderer());
    jTable2.getColumn("Action").setCellEditor(new EditButtonEditor(this, jTable2));
    jTable2.getColumn("Action").setPreferredWidth(180); // Ensure enough space
    
    jTable2.getSelectionModel().addListSelectionListener(e -> {
        int row = jTable2.getSelectedRow();
        if (row >= 0) {
            System.out.println("Selected row: " + row);  // Debugging to check if a row is selected
        }
    });
    }//GEN-LAST:event_ShowMyBookingButtonActionPerformed
    

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
            Object dateValue = table.getValueAt(row, 5);

            userPortal.FlightCodeTextField.setText(flightId.toString());
            userPortal.FlightNameTextField.setText(flightName.toString());
            userPortal.SeatComboBox.setSelectedItem("Economy");
            
            String flightCode = flightId.toString();
            SeatClassDao seatDao = new SeatClassDao();
            int flightIdInt = seatDao.getFlightIdByCode(flightCode);
            
            // Now flightIdInt is defined, so this works:
            List<String> seatNumbers = seatDao.getAvailableSeats(flightIdInt, "Economy");
            userPortal.SeatNoComboBox.removeAllItems();
            for (String seat : seatNumbers) {
                userPortal.SeatNoComboBox.addItem(seat);
            }
            
            userPortal.SeatComboBox.setSelectedItem("Economy");
            int economyPrice = seatDao.getPriceByFlightAndClass(flightIdInt, "Economy");
            userPortal.PriceTextField.setText(String.valueOf(economyPrice));

            // Autofill user data
            userPortal.FullNameTextField.setText(userPortal.loggedInUser.getFullName());
            userPortal.EmailTextField.setText(userPortal.loggedInUser.getEmail());

            // Lock fields visually (clean gray with border)
            makeFieldReadOnly(userPortal.FlightCodeTextField);
            makeFieldReadOnly(userPortal.FlightNameTextField);
            makeFieldReadOnly(userPortal.FullNameTextField);
            makeFieldReadOnly(userPortal.EmailTextField);

            // Lock travel date but keep visible
            userPortal.lockTravelDateFields();

            // Set travel date from table column
            if (dateValue != null) {
                try {
                    String formattedDate = dateValue.toString().trim().substring(0, 10);
                    java.sql.Date sqlDate = java.sql.Date.valueOf(formattedDate);
                    java.util.Calendar cal = java.util.Calendar.getInstance();
                    cal.setTime(sqlDate);
                    userPortal.TravelYearChooser.setYear(cal.get(java.util.Calendar.YEAR));
                    userPortal.TravelMonthChooser.setMonth(cal.get(java.util.Calendar.MONTH));
                    userPortal.TravelDaySpinnerField.setValue(cal.get(java.util.Calendar.DAY_OF_MONTH));
                } catch (IllegalArgumentException e) {
                    JOptionPane.showMessageDialog(null, "Invalid date format: " + dateValue.toString());
                    e.printStackTrace();
                }
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

    //Processt to make text inside non-changeable fields gray and preventing its border from changing its color.
    private void disableField(javax.swing.JTextField field) {
        field.setEditable(false);
        field.setBackground(new java.awt.Color(238, 238, 238)); // Light gray
        field.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
    }
    
    // Lock Travel Date fields (look normal but are uneditable)
    private void lockTravelDateFields() {
        TravelDaySpinnerField.getSpinner().setEnabled(false);
        TravelDaySpinnerField.getSpinner().setBackground(java.awt.Color.WHITE);

        // Lock Month
        TravelMonthChooser.getComboBox().setEnabled(false);
        TravelMonthChooser.getComboBox().setBackground(java.awt.Color.WHITE);

        // Lock Year (safely cast)
        java.awt.Component yearComponent = TravelYearChooser.getSpinner();
        if (yearComponent instanceof javax.swing.JSpinner) {
            javax.swing.JSpinner spinner = (javax.swing.JSpinner) yearComponent;
            spinner.setEnabled(false);
            JComponent editor = spinner.getEditor();
            if (editor instanceof javax.swing.JSpinner.DefaultEditor) {
                javax.swing.JSpinner.DefaultEditor defaultEditor = (javax.swing.JSpinner.DefaultEditor) editor;
                defaultEditor.getTextField().setBackground(java.awt.Color.WHITE);
            }
        }   
    }
    
    private void unlockTravelDateFields() {
        TravelDaySpinnerField.getSpinner().setEnabled(true);
        TravelDaySpinnerField.getSpinner().setBackground(java.awt.Color.WHITE);

        TravelMonthChooser.getComboBox().setEnabled(true);
        TravelMonthChooser.getComboBox().setBackground(java.awt.Color.WHITE);

        java.awt.Component yearComponent = TravelYearChooser.getSpinner();
        if (yearComponent instanceof javax.swing.JSpinner) {
            javax.swing.JSpinner spinner = (javax.swing.JSpinner) yearComponent;
            spinner.setEnabled(true);
            JComponent editor = spinner.getEditor();
            if (editor instanceof javax.swing.JSpinner.DefaultEditor) {
                javax.swing.JSpinner.DefaultEditor defaultEditor = (javax.swing.JSpinner.DefaultEditor) editor;
                defaultEditor.getTextField().setBackground(java.awt.Color.WHITE);
            }
        }
    }
    //Making fields unchangeable, swing remove border and in order to prevent it:
    private void makeFieldReadOnly(javax.swing.JTextField field) {
        field.setEditable(false);
        field.setBackground(new java.awt.Color(245, 245, 245)); // very light gray
        field.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(200, 200, 200)));
    }
    
    //Restore default styling in ClearButtonActionPerformed():
    private void restoreEditableField(javax.swing.JTextField field) {
        field.setEditable(true);
        field.setBackground(java.awt.Color.WHITE);
        field.setBorder(javax.swing.UIManager.getBorder("TextField.border"));
    }
   
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> {
            UserData dummy = new UserData(1, "User", "user@wingsnepal.com", "user123", "User");
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
    private javax.swing.JButton ClearButton;
    private javax.swing.JLabel DashboardBgLabel;
    private javax.swing.JButton DashboardButton;
    private javax.swing.JLabel DashboardIcon;
    private javax.swing.JPanel DashboardPanel;
    private javax.swing.JLabel DateLabel;
    private javax.swing.JButton EditButton;
    private javax.swing.JLabel EmailLabel;
    private javax.swing.JTextField EmailTextField;
    private javax.swing.JButton FlightButton;
    private javax.swing.JLabel FlightCodeLabel;
    private javax.swing.JTextField FlightCodeTextField;
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
    private javax.swing.JPanel ManageBookingPanel;
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
    private javax.swing.JComboBox<String> SeatNoComboBox;
    private javax.swing.JLabel SeatNumberLabel;
    private javax.swing.JButton ShowAllButton;
    private javax.swing.JButton ShowMyBookingButton;
    private com.toedter.calendar.JMonthChooser TicektMonthFiled;
    private com.toedter.components.JSpinField TicketSpinField;
    private javax.swing.JLabel TicketsDateLabel;
    private javax.swing.JLabel TicketsLAbel;
    private javax.swing.JLabel ToLabel;
    private javax.swing.JTextField ToTextField;
    private com.toedter.calendar.JYearChooser TravelDateChooser;
    private com.toedter.components.JSpinField TravelDayChooser;
    private com.toedter.components.JSpinField TravelDaySpinnerField;
    private com.toedter.calendar.JMonthChooser TravelMonthChooser;
    private com.toedter.calendar.JYearChooser TravelYearChooser;
    private javax.swing.JLabel WingsNepalLogo;
    private javax.swing.JList<String> jList1;
    private javax.swing.JScrollBar jScrollBar1;
    private javax.swing.JScrollBar jScrollBar2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    // End of variables declaration//GEN-END:variables
    
    // ==== Public getters for BookButtonEditor ====
    public JTextField getFlightCodeTextField() {
        return FlightCodeTextField;
    }

    public JTextField getFlightNameTextField() {
        return FlightNameTextField;
    }

    public JComboBox<String> getSeatComboBox() {
        return SeatComboBox;
    }

    public JComboBox<String> getSeatNoComboBox() {
        return SeatNoComboBox;
    }

    public JTextField getPriceTextField() {
        return PriceTextField;
    }

    public JTextField getFullNameTextField() {
        return FullNameTextField;
    }

    public JTextField getEmailTextField() {
        return EmailTextField;
    }

    public void lockTravelFields() {
        TravelYearChooser.setEnabled(false);
        TravelMonthChooser.setEnabled(false);
        TravelDaySpinnerField.setEnabled(false);
    }

    public com.toedter.calendar.JYearChooser getTravelYearChooser() {
        return TravelYearChooser;
    }

    public com.toedter.calendar.JMonthChooser getTravelMonthChooser() {
        return TravelMonthChooser;
    }

    public com.toedter.components.JSpinField getTravelDaySpinnerField() {
        return TravelDaySpinnerField;
    }

    public JTabbedPane getMainTabbedPane() {
        return jTabbedPane1;
    }

}
