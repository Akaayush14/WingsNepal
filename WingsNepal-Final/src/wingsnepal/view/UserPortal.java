/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package wingsnepal.view;

import java.awt.Desktop;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import wingsnepal.model.UserDataModel;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import wingsnepal.view.BookButtonRenderer;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import javax.swing.border.Border;
import javax.swing.JFormattedTextField;
import javax.swing.JButton;
import com.toedter.calendar.JMonthChooser;
import com.toedter.calendar.JYearChooser;
import com.toedter.components.JSpinField;
import java.awt.Insets;
import controller.ManageBookingController;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableCellEditor;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.AbstractCellEditor;

/**
 *
 * @author Aayush Kharel
 */
public class UserPortal extends javax.swing.JFrame{
    
    private UserDataModel loggedInUser;
    private javax.swing.JTable JTable2;
    private ManageBookingController manageBookingController;

    // Modern UI Colors & Fonts
    private static final Color BACKGROUND_COLOR = new Color(245, 245, 245);
    private static final Color FOREGROUND_COLOR = new Color(51, 51, 51);
    private static final Color PRIMARY_COLOR = new Color(0, 102, 153);
    private static final Color SECONDARY_COLOR = new Color(108, 117, 125);
    private static final Color DANGER_COLOR = new Color(220, 53, 69);
    private static final Color BORDER_COLOR = new Color(220, 220, 220);
    private static final Color FIELD_BG = new Color(247, 250, 253); // very light blue
    private static final Color FIELD_BORDER = new Color(200, 220, 240); // subtle blue-gray
    private static final Font LABEL_FONT = new Font("Segoe UI", Font.BOLD, 14);
    private static final Font FIELD_FONT = new Font("Segoe UI", Font.PLAIN, 14);

    //Constructor for login-based usage
    public UserPortal(UserDataModel user) {
        this.loggedInUser = user; // store the user
        initComponents();
        styleBookFlightPanel();
        setupPlaceholders();
        
        //Show user's name on screen or use it anywhere you want
        setTitle("User Portal - Welcome, " + loggedInUser.getFullName());
        
        
        // Adding hovering effect to buttons to make it look modern.
        styleFlatHoverButton(DashboardButton);
        styleFlatHoverButton(FlightButton);
        styleFlatHoverButton(BookFlightButton);
        styleFlatHoverButton(EditButton);
        styleFlatHoverButton(LogoutButton);
        
            
        // Adding scrolling feature.
        jScrollPane1.setViewportView(jTable1);
        
        PaymentComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] {
            "Cash", "Stripe"
        }));

        
        //Disable maximize button
        setResizable(false); 
        
        //Center window
        setLocationRelativeTo(null); 
        
        // Set ticket spinner to 1 and disable it since only 1 ticket per seat is allowed
        TicketSpinField.setValue(1);
        TicketSpinField.setEnabled(false);
        
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

        stylePrimaryButton(SearchFlightButton);
        stylePrimaryButton(ShowAllButton);

        // Apply consistent styling to search text fields
        styleTextField(FromTextField);
        styleTextField(ToTextField);

        manageBookingController = new controller.ManageBookingController(this);
    }
    
    // Getter method for loggedInUser
    public UserDataModel getLoggedInUser() {
        return loggedInUser;
    }
    
    // Getter method for jTable2
    public javax.swing.JTable getJTable2() {
        return jTable2;
    }
    
    // Getter method for manageBookingController
    public ManageBookingController getManageBookingController() {
        return manageBookingController;
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
    private void SearchFlightButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SearchFlightButtonActionPerformed
        // handleSearchFlights(); // REMOVED - now handled by controller
    }//GEN-LAST:event_SearchFlightButtonActionPerformed
            
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
        LogoutButton = new javax.swing.JButton();
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
        PhoneLabel = new javax.swing.JLabel();
        PhoneTextField = new javax.swing.JTextField();
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

        LogoutButton.setBackground(new java.awt.Color(46, 62, 79));
        LogoutButton.setFont(new java.awt.Font("Segoe UI Emoji", 1, 18)); // NOI18N
        LogoutButton.setForeground(new java.awt.Color(255, 255, 255));
        LogoutButton.setText("Log out");
        LogoutButton.setBorderPainted(false);
        LogoutButton.setContentAreaFilled(false);
        LogoutButton.setFocusPainted(false);
        LogoutButton.setVerifyInputWhenFocusTarget(false);
        LogoutButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LogoutButtonActionPerformed(evt);
            }
        });
        ButtonPanel.add(LogoutButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 460, 230, 40));

        WingsNepalLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagepicker/WingsNepalLogo.jpg"))); // NOI18N
        ButtonPanel.add(WingsNepalLogo, new org.netbeans.lib.awtextra.AbsoluteConstraints(-20, -70, 270, 270));

        getContentPane().add(ButtonPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, -6, 230, 637));

        DashboardPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        DashboardBgLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagepicker/Dashboard_bg.png"))); // NOI18N
        DashboardBgLabel.setPreferredSize(new java.awt.Dimension(900, 660));
        DashboardPanel.add(DashboardBgLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 800, -1));

        jTabbedPane1.addTab("tab1", DashboardPanel);

        SearchFlightPanel.setBackground(new java.awt.Color(255, 255, 255));
        SearchFlightPanel.setPreferredSize(new java.awt.Dimension(790, 662));
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
        SearchFlightPanel.add(FromTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, 180, 30));

        ToTextField.setText("Enter destination city or airport");
        ToTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ToTextFieldActionPerformed(evt);
            }
        });
        SearchFlightPanel.add(ToTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 200, 180, 30));

        SearchFlightButton.setBackground(new java.awt.Color(0, 102, 153));
        SearchFlightButton.setFont(new java.awt.Font("Segoe UI Emoji", 1, 18)); // NOI18N
        SearchFlightButton.setForeground(new java.awt.Color(255, 255, 255));
        SearchFlightButton.setText("Search");
        SearchFlightButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SearchFlightButtonActionPerformed(evt);
            }
        });
        SearchFlightPanel.add(SearchFlightButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 460, 180, 30));
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

        SearchFlightPanel.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 90, 590, 560));

        ShowAllButton.setBackground(new java.awt.Color(0, 102, 153));
        ShowAllButton.setFont(new java.awt.Font("Segoe UI Emoji", 1, 18)); // NOI18N
        ShowAllButton.setForeground(new java.awt.Color(255, 255, 255));
        ShowAllButton.setText("Show all");
        ShowAllButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ShowAllButtonActionPerformed(evt);
            }
        });
        SearchFlightPanel.add(ShowAllButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 500, 180, 30));
        SearchFlightPanel.add(TravelDateChooser, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 300, 180, 30));
        SearchFlightPanel.add(TravelMonthChooser, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 350, 190, 30));
        SearchFlightPanel.add(TravelDayChooser, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 400, 70, 30));

        jTabbedPane1.addTab("tab2", SearchFlightPanel);

        BookFlightPanel.setPreferredSize(new java.awt.Dimension(790, 632));
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
        BookFlightPanel.add(FlightCodeLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 95, -1, -1));

        FullNameLabel.setFont(new java.awt.Font("Segoe UI Emoji", 1, 18)); // NOI18N
        FullNameLabel.setText("Full Name");
        BookFlightPanel.add(FullNameLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 195, -1, -1));

        EmailLabel.setFont(new java.awt.Font("Segoe UI Emoji", 1, 18)); // NOI18N
        EmailLabel.setText("Email");
        BookFlightPanel.add(EmailLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 245, -1, -1));

        PhoneLabel.setFont(new java.awt.Font("Segoe UI Emoji", 1, 18)); // NOI18N
        PhoneLabel.setText("Phone");
        BookFlightPanel.add(PhoneLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 295, -1, -1));

        SeatClassLabel.setFont(new java.awt.Font("Segoe UI Emoji", 1, 18)); // NOI18N
        SeatClassLabel.setText("Seat Class");
        BookFlightPanel.add(SeatClassLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 345, -1, -1));

        PriceLabel.setFont(new java.awt.Font("Segoe UI Emoji", 1, 18)); // NOI18N
        PriceLabel.setText("Price");
        BookFlightPanel.add(PriceLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 395, -1, -1));

        TicketsDateLabel.setFont(new java.awt.Font("Segoe UI Emoji", 1, 18)); // NOI18N
        TicketsDateLabel.setText("Travel Date");
        BookFlightPanel.add(TicketsDateLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 495, -1, -1));

        PaymentLabel.setFont(new java.awt.Font("Segoe UI Emoji", 1, 18)); // NOI18N
        PaymentLabel.setText("Payment");
        BookFlightPanel.add(PaymentLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 545, -1, -1));

        BookNowButton.setBackground(new java.awt.Color(0, 102, 153));
        BookNowButton.setFont(new java.awt.Font("Segoe UI Emoji", 0, 16)); // NOI18N
        BookNowButton.setForeground(new java.awt.Color(255, 255, 255));
        BookNowButton.setText("Book now");
        BookNowButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BookNowButtonActionPerformed(evt);
            }
        });
        BookFlightPanel.add(BookNowButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 580, 190, 30));

        ClearButton.setBackground(new java.awt.Color(0, 102, 153));
        ClearButton.setFont(new java.awt.Font("Segoe UI Emoji", 0, 16)); // NOI18N
        ClearButton.setForeground(new java.awt.Color(255, 255, 255));
        ClearButton.setText("Clear");
        ClearButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ClearButtonActionPerformed(evt);
            }
        });
        BookFlightPanel.add(ClearButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 580, 180, 30));

        FlightCodeTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FlightCodeTextFieldActionPerformed(evt);
            }
        });
        BookFlightPanel.add(FlightCodeTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 90, 380, 30));

        FullNameTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FullNameTextFieldActionPerformed(evt);
            }
        });
        BookFlightPanel.add(FullNameTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 190, 380, 30));
        BookFlightPanel.add(PriceTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 390, 380, 30));

        SeatComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Economy", "First Class", "Business" }));
        SeatComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SeatComboBoxActionPerformed(evt);
            }
        });
        BookFlightPanel.add(SeatComboBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 340, 140, 30));

        PaymentComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Cash", "Esewa", "Khalti", " " }));
        PaymentComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PaymentComboBoxActionPerformed(evt);
            }
        });
        BookFlightPanel.add(PaymentComboBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 540, 380, 30));

        TicketsLAbel.setFont(new java.awt.Font("Segoe UI Emoji", 1, 18)); // NOI18N
        TicketsLAbel.setText("Tickets");
        BookFlightPanel.add(TicketsLAbel, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 445, -1, -1));
        BookFlightPanel.add(EmailTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 240, 380, 30));

        PhoneTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PhoneTextFieldActionPerformed(evt);
            }
        });
        BookFlightPanel.add(PhoneTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 290, 380, 30));

        FlightNameLabel.setFont(new java.awt.Font("Segoe UI Emoji", 1, 18)); // NOI18N
        FlightNameLabel.setText("Flight Name");
        BookFlightPanel.add(FlightNameLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 145, -1, -1));

        FlightNameTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FlightNameTextFieldActionPerformed(evt);
            }
        });
        BookFlightPanel.add(FlightNameTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 140, 380, 30));

        SeatNumberLabel.setFont(new java.awt.Font("Segoe UI Emoji", 1, 18)); // NOI18N
        SeatNumberLabel.setText("Seat No");
        BookFlightPanel.add(SeatNumberLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 345, -1, -1));
        BookFlightPanel.add(TicektMonthFiled, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 490, 160, 30));
        BookFlightPanel.add(TicketSpinField, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 440, 380, 30));
        BookFlightPanel.add(TravelYearChooser, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 490, 170, 30));
        BookFlightPanel.add(TravelDaySpinnerField, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 490, 80, 30));

        SeatNoComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SeatNoComboBoxActionPerformed(evt);
            }
        });
        BookFlightPanel.add(SeatNoComboBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 340, 150, 30));

        jTabbedPane1.addTab("tab3", BookFlightPanel);

        ManageBookingPanel.setPreferredSize(new java.awt.Dimension(790, 642));
        ManageBookingPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        ManageBookingPanel.add(jScrollBar2, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 70, -1, 570));

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
        ShowMyBookingButton.setText("Show Bookings");
        ShowMyBookingButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ShowMyBookingButtonActionPerformed(evt);
            }
        });
        ManageBookingPanel.add(ShowMyBookingButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, -1, -1));

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
        
        // Refresh seats when entering the booking tab to ensure they're up-to-date
        String flightCode = FlightCodeTextField.getText().trim();
        String seatClass = (String) SeatComboBox.getSelectedItem();
        if (!flightCode.isEmpty() && seatClass != null) {
            updateAvailableSeatsDirectly(flightCode, seatClass);
        }
    }//GEN-LAST:event_BookFlightButtonActionPerformed

    private void EditButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EditButtonActionPerformed
        // Show the edit flight tab - user will manually click "Show Booking" button to load data
        jTabbedPane1.setSelectedIndex(3); // Edit Flight tab
        // Don't auto-load - preserve manual "Show Booking" button workflow
    }//GEN-LAST:event_EditButtonActionPerformed

    private void LogoutButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LogoutButtonActionPerformed
        // Do nothing here. Logout is handled by the controller (MVC).
    }//GEN-LAST:event_LogoutButtonActionPerformed

    private void FromTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FromTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_FromTextFieldActionPerformed
                                         

    private void ShowAllButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ShowAllButtonActionPerformed
        // showAllFlights(); // REMOVED - now handled by controller
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

    private void PhoneTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PhoneTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_PhoneTextFieldActionPerformed

    private void SeatComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SeatComboBoxActionPerformed
        // This method is called when a seat class is selected
        // Get current flight code and seat class
        String flightCode = FlightCodeTextField.getText().trim();
        String seatClass = (String) SeatComboBox.getSelectedItem();
        
        if (!flightCode.isEmpty() && seatClass != null) {
            // Directly update available seats without triggering recursion
            updateAvailableSeatsDirectly(flightCode, seatClass);
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
        PhoneTextField.setText("");
        PriceTextField.setText("");

        restoreEditableField(FlightCodeTextField);
        restoreEditableField(FlightNameTextField);
        restoreEditableField(FullNameTextField);
        restoreEditableField(EmailTextField);
        restoreEditableField(PhoneTextField);

        SeatComboBox.setSelectedIndex(0);
        PaymentComboBox.setSelectedIndex(0);
        TicketSpinField.setValue(1);
        TicketSpinField.setEnabled(false); // Keep disabled since only 1 ticket per seat
        TravelDaySpinnerField.setValue(1);

        unlockTravelDateFields();
    }//GEN-LAST:event_ClearButtonActionPerformed

    private void BookNowButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BookNowButtonActionPerformed
        // handleBookFlight(); // REMOVED - now handled by controller
    }//GEN-LAST:event_BookNowButtonActionPerformed
    
    private void SeatNoComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SeatNoComboBoxActionPerformed
        // This method is called when a seat number is selected
        // We can add additional logic here if needed
        String selectedSeatNo = (String) SeatNoComboBox.getSelectedItem();
        if (selectedSeatNo != null && !selectedSeatNo.equals("No seats available")) {
            // Seat is selected, we can update price or perform other actions
            // updateSeatClassPrice();
        }
    }//GEN-LAST:event_SeatNoComboBoxActionPerformed
    
    private void ShowMyBookingButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ShowMyBookingButtonActionPerformed
        // showUserBookings(loggedInUser.getUserId()); // REMOVED - now handled by controller
    }//GEN-LAST:event_ShowMyBookingButtonActionPerformed

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
        java.awt.Component comp = TravelMonthChooser.getComboBox();
        if (comp instanceof JComboBox) {
            ((JComboBox<?>) comp).setEnabled(false);
        }

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

        java.awt.Component comp = TravelMonthChooser.getComboBox();
        if (comp instanceof JComboBox) {
            ((JComboBox<?>) comp).setEnabled(true);
        }

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
        field.setBackground(Color.WHITE);
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(0, 102, 153), 2, true), // Blue border
            BorderFactory.createEmptyBorder(3, 12, 3, 12) // Reduced vertical padding to prevent text clipping
        ));
    }
   
    public static void main(String[] args) {
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            // Create a dummy user for testing purposes
            UserDataModel dummyUser = new UserDataModel(1, "Test User", "test@example.com", "Customer");
            UserPortal view = new UserPortal(dummyUser);
            new controller.UserPortalController(view, dummyUser).open();
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
    private javax.swing.JLabel PhoneLabel;
    private javax.swing.JTextField PhoneTextField;
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
    private javax.swing.JLabel LogOutIcon;
    private javax.swing.JPanel LogOutPanel;
    private javax.swing.JButton LogoutButton;
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

    public JTextField getPhoneTextField() {
        return PhoneTextField;
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

    private void styleLabel(JLabel label) {
        if (label != null) {
            label.setFont(new Font("Segoe UI", Font.BOLD, 15));
            label.setForeground(new Color(40, 40, 40));
        }
    }

    private void styleTextField(JTextField textField) {
        if (textField != null) {
            textField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            textField.setBackground(Color.WHITE); // Clean white background
            textField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0, 102, 153), 2, true), // Blue border
                BorderFactory.createEmptyBorder(3, 12, 3, 12) // Reduced vertical padding to prevent text clipping
            ));
        }
    }

    private void styleComboBox(JComboBox<?> comboBox) {
        if (comboBox != null) {
            comboBox.setFont(new Font("Segoe UI", Font.PLAIN, 14)); // Consistent font size
            comboBox.setBackground(Color.WHITE); // Clean white background
            comboBox.setBorder(BorderFactory.createLineBorder(new Color(0, 102, 153), 2, true)); // Blue border
        }
    }

    private void styleSpinner(com.toedter.components.JSpinField spinner) {
        if (spinner != null) {
            for (Component child : spinner.getComponents()) {
                if (child instanceof JTextField) {
                    styleTextField((JTextField) child);
                }
            }
        }
    }

    private void styleYearChooser(com.toedter.calendar.JYearChooser yearChooser) {
        if (yearChooser != null) {
            for (Component child : yearChooser.getComponents()) {
                if (child instanceof com.toedter.components.JSpinField) {
                    styleSpinner((com.toedter.components.JSpinField) child);
                }
            }
        }
    }

    private void styleMonthChooser(com.toedter.calendar.JMonthChooser monthChooser) {
        if (monthChooser != null) {
            for (Component child : monthChooser.getComponents()) {
                if (child instanceof JComboBox) {
                    styleComboBox((JComboBox<?>) child);
                }
            }
        }
    }
    
    private void styleButton(JButton button, Color bgColor) {
        if (button != null) {
            button.setFont(new Font("Segoe UI", Font.BOLD, 14));
            button.setBackground(bgColor);
            button.setForeground(Color.WHITE);
            button.setFocusPainted(false);
            button.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
            button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }
    }

    private void styleBookFlightPanel() {
        BookFlightPanel.setBackground(Color.WHITE);
        // Style all labels
        styleLabel(FlightCodeLabel);
        styleLabel(FlightNameLabel);
        styleLabel(FullNameLabel);
        styleLabel(EmailLabel);
        styleLabel(PhoneLabel);
        styleLabel(SeatClassLabel);
        styleLabel(SeatNumberLabel);
        styleLabel(PriceLabel);
        styleLabel(TicketsLAbel);
        styleLabel(TicketsDateLabel);
        styleLabel(PaymentLabel);
        // Style all text fields
        styleTextField(FlightCodeTextField);
        styleTextField(FlightNameTextField);
        styleTextField(FullNameTextField);
        styleTextField(EmailTextField);
        styleTextField(PhoneTextField);
        styleTextField(PriceTextField);
        // Style combo boxes
        styleComboBox(SeatComboBox);
        styleComboBox(SeatNoComboBox);
        styleComboBox(PaymentComboBox);
        // Style spinners and date choosers
        styleSpinner(TicketSpinField);
        styleYearChooser(TravelYearChooser);
        styleMonthChooser(TravelMonthChooser);
        styleSpinner(TravelDaySpinnerField);
        // Style Buttons
        stylePrimaryButton(BookNowButton);
        styleDangerButton(ClearButton);
    }

    // Additional getter methods for controller access
    public javax.swing.JButton getSearchFlightButton() {
        return SearchFlightButton;
    }
    
    public javax.swing.JButton getShowAllButton() {
        return ShowAllButton;
    }
    
    public javax.swing.JButton getBookNowButton() {
        return BookNowButton;
    }
    
    public javax.swing.JButton getClearButton() {
        return ClearButton;
    }
    
    public javax.swing.JButton getShowMyBookingButton() {
        return ShowMyBookingButton;
    }
    
    public javax.swing.JButton getLogOutButton() {
        return LogoutButton;
    }
    
    public javax.swing.JTextField getFromTextField() {
        return FromTextField;
    }
    
    public javax.swing.JTextField getToTextField() {
        return ToTextField;
    }
    
    public javax.swing.JComboBox<String> getPaymentComboBox() {
        return PaymentComboBox;
    }
    
    public javax.swing.JTable getJTable1() {
        return jTable1;
    }

    // Helper for consistent blue button styling
    private void stylePrimaryButton(javax.swing.JButton button) {
        button.setBackground(PRIMARY_COLOR);
        button.setForeground(java.awt.Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(PRIMARY_COLOR.darker(), 2, true),
            BorderFactory.createEmptyBorder(10, 25, 10, 25)
        ));
        button.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        button.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 15));
        // Add hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(PRIMARY_COLOR.brighter());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(PRIMARY_COLOR);
            }
        });
    }

    // Modern danger button styling
    private void styleDangerButton(JButton button) {
        button.setBackground(DANGER_COLOR);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(DANGER_COLOR.darker(), 2, true),
            BorderFactory.createEmptyBorder(10, 25, 10, 25)
        ));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setFont(new Font("Segoe UI", Font.BOLD, 15));
        // Add hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(DANGER_COLOR.brighter());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(DANGER_COLOR);
            }
        });
    }

    // Lock all booking fields that should not be editable when coming from Book button
    public void lockBookingFields() {
        FlightCodeTextField.setEditable(false);
        FlightNameTextField.setEditable(false);
        TravelYearChooser.setEnabled(false);
        TravelMonthChooser.setEnabled(false);
        TravelDaySpinnerField.setEnabled(false);
        TicketSpinField.setEnabled(false);
        // Also disable the month combo box inside TravelMonthChooser
        java.awt.Component comp = TravelMonthChooser.getComboBox();
        if (comp instanceof JComboBox) {
            ((JComboBox<?>) comp).setEnabled(false);
        }
    }

    // Add after initComponents() in the constructor or after showAllBookingsForEdit() is called:
    public void setupActionsColumn() {
        jTable2.getColumnModel().getColumn(8).setCellRenderer(new ActionsRenderer());
        jTable2.getColumnModel().getColumn(8).setCellEditor(new ActionsEditor(jTable2, manageBookingController));
        jTable2.getColumnModel().getColumn(8).setPreferredWidth(180);
        jTable2.getColumnModel().getColumn(8).setMinWidth(140);
        jTable2.setRowHeight(32);
    }

    // Method to refresh seat selection dropdown
    public void refreshSeatSelection() {
        // Get current flight code and seat class
        String flightCode = FlightCodeTextField.getText().trim();
        String seatClass = (String) SeatComboBox.getSelectedItem();
        
        // Only refresh if we have both flight code and seat class
        if (!flightCode.isEmpty() && seatClass != null) {
            // Trigger the seat class change event to refresh available seats
            SeatComboBoxActionPerformed(new java.awt.event.ActionEvent(SeatComboBox, java.awt.event.ActionEvent.ACTION_PERFORMED, ""));
        }
    }

    // Method to force refresh seats when a flight is selected (called from BookButtonEditor)
    public void forceRefreshSeats() {
        // Get current flight code
        String flightCode = FlightCodeTextField.getText().trim();
        
        if (!flightCode.isEmpty()) {
            // Get the current seat class selection
            String seatClass = (String) SeatComboBox.getSelectedItem();
            
            // If no seat class is selected, default to Economy
            if (seatClass == null) {
                SeatComboBox.setSelectedItem("Economy");
                seatClass = "Economy";
            }
            
            // Directly update available seats without triggering the action listener
            updateAvailableSeatsDirectly(flightCode, seatClass);
        }
    }
    
    // Direct method to update available seats without triggering action listeners
    public void updateAvailableSeatsDirectly(String flightCode, String seatClass) {
        try {
            wingsnepal.dao.SeatClassDao seatDao = new wingsnepal.dao.SeatClassDao();
            int flightId = seatDao.getFlightIdByCode(flightCode);
            
            if (flightId != -1) {
                // Get available seats for the selected class
                java.util.List<String> availableSeats = seatDao.getAvailableSeatsByClass(flightId, seatClass);
                
                // Clear and repopulate the seat dropdown
                SeatNoComboBox.removeAllItems();
                for (String seat : availableSeats) {
                    SeatNoComboBox.addItem(seat);
                }
                
                // Update price
                int price = seatDao.getPriceByFlightAndClass(flightId, seatClass);
                PriceTextField.setText("NPR " + price);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // --- Renderer ---
    class ActionsRenderer extends JPanel implements TableCellRenderer {
        private final JButton editButton = new JButton("Edit");
        private final JButton deleteButton = new JButton("Delete");
        public ActionsRenderer() {
            setLayout(new FlowLayout(FlowLayout.CENTER, 5, 0));
            editButton.setBackground(new Color(0, 153, 112)); // Vibrant green
            editButton.setForeground(Color.WHITE);
            editButton.setFocusPainted(false);
            deleteButton.setBackground(new Color(220, 53, 69));
            deleteButton.setForeground(Color.WHITE);
            deleteButton.setFocusPainted(false);
            add(editButton);
            add(deleteButton);
            setOpaque(true);
        }
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            return this;
        }
    }

    // --- Editor ---
    class ActionsEditor extends AbstractCellEditor implements TableCellEditor {
        private final JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
        private final JButton editButton = new JButton("Edit");
        private final JButton deleteButton = new JButton("Delete");
        private int row;
        private JTable table;
        private ManageBookingController controller;
        public ActionsEditor(JTable table, ManageBookingController controller) {
            this.table = table;
            this.controller = controller;
            editButton.setBackground(new Color(0, 153, 112)); // Vibrant green
            editButton.setForeground(Color.WHITE);
            editButton.setFocusPainted(false);
            deleteButton.setBackground(new Color(220, 53, 69));
            deleteButton.setForeground(Color.WHITE);
            deleteButton.setFocusPainted(false);
            panel.add(editButton);
            panel.add(deleteButton);
            editButton.addActionListener(e -> {
                fireEditingStopped();
                controller.handleBookingActions(row, "Edit");
            });
            deleteButton.addActionListener(e -> {
                fireEditingStopped();
                controller.handleBookingActions(row, "Delete");
            });
        }
        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            this.row = row;
            return panel;
        }
        @Override
        public Object getCellEditorValue() {
            return null;
        }
    }
}

