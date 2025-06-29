package wingsnepal.view;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.TableCellEditor;
import wingsnepal.dao.SeatClassDao;
import java.util.List;
import wingsnepal.dao.SearchFlightDao;
import wingsnepal.model.SearchFlightModel;
import wingsnepal.model.BookingFlightModel;
import wingsnepal.dao.UserDao;

/**
 * Button editor for the "Book" action in UserPortal's JTable
 */
public class BookButtonEditor extends AbstractCellEditor implements TableCellEditor {
    private final JButton button = new JButton("Book");
    private final UserPortal userPortal;
    private final JTable table;
    private boolean isPushed = false;
    private Object label = "Book";
    private JComboBox<String> bookingForComboBox;

    public BookButtonEditor(UserPortal userPortal, JTable table) {
        this.userPortal = userPortal;
        this.table = table;

        button.setOpaque(true);
        button.setBackground(new Color(0, 153, 102)); // Match Add Flights button (green)
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("Segoe UI Emoji", Font.BOLD, 15));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(0, 153, 102).darker(), 2, true),
            BorderFactory.createEmptyBorder(10, 25, 10, 25)
        ));
        // Add hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(0, 153, 102).brighter());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(0, 153, 102));
            }
        });
        button.addActionListener(e -> handleBooking());
    }

    private void handleBooking() {
        int row = table.getSelectedRow();
        if (row != -1) {
            // Debug: print all values in the selected row
            System.out.println("Selected row values:");
            for (int col = 0; col < table.getColumnCount(); col++) {
                System.out.println("Col " + col + " (" + table.getColumnName(col) + "): " + table.getValueAt(row, col));
            }

            Object flightCode = table.getValueAt(row, 0);
            Object flightName = table.getValueAt(row, 1);
            Object date = table.getValueAt(row, 4); // Use column 4 for date
            Object time = table.getValueAt(row, 5); // Use column 5 for time

            userPortal.getFlightCodeTextField().setText(flightCode.toString());
            userPortal.getFlightNameTextField().setText(flightName.toString());

            // Show booking choice dialog first
            showBookingChoiceDialog();

            // Lock flight-related fields that should not be editable
            userPortal.getFlightCodeTextField().setEditable(false);
            userPortal.getFlightNameTextField().setEditable(false);
            userPortal.getPriceTextField().setEditable(false);

            if (date != null) {
                try {
                    String dateStr = date.toString().trim();
                    java.util.regex.Matcher m = java.util.regex.Pattern.compile("\\d{4}-\\d{2}-\\d{2}").matcher(dateStr);
                    if (m.find()) {
                        dateStr = m.group();
                        java.sql.Date sqlDate = java.sql.Date.valueOf(dateStr);
                        java.util.Calendar cal = java.util.Calendar.getInstance();
                        cal.setTime(sqlDate);

                        userPortal.getTravelYearChooser().setYear(cal.get(java.util.Calendar.YEAR));
                        userPortal.getTravelMonthChooser().setMonth(cal.get(java.util.Calendar.MONTH));
                        userPortal.getTravelDaySpinnerField().setValue(cal.get(java.util.Calendar.DAY_OF_MONTH));
                        
                        // Lock travel date fields
                        userPortal.getTravelYearChooser().setEnabled(false);
                        userPortal.getTravelMonthChooser().setEnabled(false);
                        userPortal.getTravelDaySpinnerField().setEnabled(false);
                    } else {
                        System.err.println("Could not extract date from: " + dateStr);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            userPortal.getMainTabbedPane().setSelectedIndex(2); // Go to Book Flights tab

            // --- Trigger seat update logic ---
            // Use the new direct method to refresh available seats
            String flightCodeStr = userPortal.getFlightCodeTextField().getText().trim();
            String seatClass = (String) userPortal.getSeatComboBox().getSelectedItem();
            if (!flightCodeStr.isEmpty() && seatClass != null) {
                userPortal.updateAvailableSeatsDirectly(flightCodeStr, seatClass);
            }
        }
        fireEditingStopped();
    }

    private void showBookingChoiceDialog() {
        // Create a modern dialog for booking choice
        JDialog choiceDialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(userPortal), "Booking For", true);
        choiceDialog.setSize(550, 350);
        choiceDialog.setLocationRelativeTo(userPortal);
        choiceDialog.setLayout(new BorderLayout());
        choiceDialog.setResizable(false);
        
        // Title panel
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.setBackground(new Color(51, 122, 183));
        titlePanel.setPreferredSize(new Dimension(550, 80));
        JLabel titleLabel = new JLabel("Who are you booking this flight for?");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(25, 30, 25, 30));
        titlePanel.add(titleLabel);
        
        // Main content wrapper
        JPanel mainWrapper = new JPanel(new BorderLayout());
        mainWrapper.setBackground(Color.WHITE);
        
        // Content panel with explicit sizing
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(40, 50, 40, 50));
        contentPanel.setPreferredSize(new Dimension(450, 160));
        
        // Radio buttons with explicit sizing
        JRadioButton myselfRadio = new JRadioButton("Myself (" + userPortal.getLoggedInUser().getFullName() + ")", true);
        JRadioButton someoneElseRadio = new JRadioButton("Someone Else (Enter passenger details)");
        
        // Set explicit sizes for radio buttons
        myselfRadio.setPreferredSize(new Dimension(400, 35));
        myselfRadio.setMaximumSize(new Dimension(400, 35));
        someoneElseRadio.setPreferredSize(new Dimension(400, 35));
        someoneElseRadio.setMaximumSize(new Dimension(400, 35));
        
        myselfRadio.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        someoneElseRadio.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        myselfRadio.setBackground(Color.WHITE);
        someoneElseRadio.setBackground(Color.WHITE);
        myselfRadio.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        someoneElseRadio.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        
        // Add some visual styling
        myselfRadio.setFocusPainted(false);
        someoneElseRadio.setFocusPainted(false);
        myselfRadio.setAlignmentX(Component.LEFT_ALIGNMENT);
        someoneElseRadio.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        ButtonGroup group = new ButtonGroup();
        group.add(myselfRadio);
        group.add(someoneElseRadio);
        
        contentPanel.add(myselfRadio);
        contentPanel.add(Box.createVerticalStrut(20));
        contentPanel.add(someoneElseRadio);
        contentPanel.add(Box.createVerticalGlue());
        
        mainWrapper.add(contentPanel, BorderLayout.CENTER);
        
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(15, 30, 25, 30));
        buttonPanel.setPreferredSize(new Dimension(550, 70));
        
        JButton cancelButton = new JButton("Cancel");
        JButton continueButton = new JButton("Continue");
        
        // Style cancel button
        cancelButton.setPreferredSize(new Dimension(100, 42));
        cancelButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        cancelButton.setBackground(new Color(220, 220, 220));
        cancelButton.setForeground(new Color(60, 60, 60));
        cancelButton.setBorder(BorderFactory.createEmptyBorder());
        cancelButton.setFocusPainted(false);
        cancelButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Style continue button
        continueButton.setPreferredSize(new Dimension(120, 42));
        continueButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        continueButton.setBackground(new Color(51, 122, 183));
        continueButton.setForeground(Color.WHITE);
        continueButton.setBorder(BorderFactory.createEmptyBorder());
        continueButton.setFocusPainted(false);
        continueButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Add event handlers
        cancelButton.addActionListener(e -> {
            choiceDialog.dispose();
            // Reset any pre-filled flight details since user cancelled
            userPortal.getFlightCodeTextField().setText("");
            userPortal.getFlightNameTextField().setText("");
            // Reset phone field to default state
            userPortal.getPhoneTextField().setText("");
            userPortal.getPhoneTextField().setEditable(true);
            userPortal.getPhoneTextField().setBackground(Color.WHITE);
        });
        
        continueButton.addActionListener(e -> {
            if (myselfRadio.isSelected()) {
                // Book for account holder - lock fields
                userPortal.getFullNameTextField().setText(userPortal.getLoggedInUser().getFullName());
                userPortal.getEmailTextField().setText(userPortal.getLoggedInUser().getEmail());
                userPortal.getFullNameTextField().setEditable(false);
                userPortal.getEmailTextField().setEditable(false);
                userPortal.getFullNameTextField().setBackground(new Color(245, 245, 245));
                userPortal.getEmailTextField().setBackground(new Color(245, 245, 245));
                
                // Handle phone field for self-booking
                UserDao userDao = new UserDao();
                String userPhone = userDao.getUserPhoneById(userPortal.getLoggedInUser().getUserId());
                if (userPhone != null && !userPhone.trim().isEmpty()) {
                    userPortal.getPhoneTextField().setText(userPhone);
                } else {
                    userPortal.getPhoneTextField().setText("");
                }
                userPortal.getPhoneTextField().setEditable(false);
                userPortal.getPhoneTextField().setBackground(new Color(245, 245, 245));
                
                // Add visual indicator
                addBookingModeIndicator("Booking for: " + userPortal.getLoggedInUser().getFullName() + " (Account Holder)", 
                                      new Color(40, 167, 69));
            } else {
                // Book for someone else - unlock fields
                userPortal.getFullNameTextField().setText("");
                userPortal.getEmailTextField().setText("");
                userPortal.getPhoneTextField().setText("");
                userPortal.getFullNameTextField().setEditable(true);
                userPortal.getEmailTextField().setEditable(true);
                userPortal.getPhoneTextField().setEditable(true);
                userPortal.getFullNameTextField().setBackground(Color.WHITE);
                userPortal.getEmailTextField().setBackground(Color.WHITE);
                userPortal.getPhoneTextField().setBackground(Color.WHITE);
                
                // Add visual indicator
                addBookingModeIndicator("Booking for: Someone Else (Please enter passenger details)", 
                                      new Color(255, 193, 7));
            }
            choiceDialog.dispose();
        });
        
        buttonPanel.add(cancelButton);
        buttonPanel.add(Box.createHorizontalStrut(10));
        buttonPanel.add(continueButton);
        
        // Assemble the dialog
        choiceDialog.add(titlePanel, BorderLayout.NORTH);
        choiceDialog.add(mainWrapper, BorderLayout.CENTER);
        choiceDialog.add(buttonPanel, BorderLayout.SOUTH);
        
        // Show dialog
        choiceDialog.setVisible(true);
    }
    
    private void addBookingModeIndicator(String message, Color color) {
        // Find booking panel and add indicator
        Component[] components = userPortal.getContentPane().getComponents();
        for (Component comp : components) {
            if (comp instanceof JTabbedPane) {
                JTabbedPane tabbedPane = (JTabbedPane) comp;
                Component bookingTab = tabbedPane.getComponentAt(2); // Book Flight tab
                if (bookingTab instanceof JPanel) {
                    JPanel bookingPanel = (JPanel) bookingTab;
                    
                    // Remove existing indicator if any
                    for (Component child : bookingPanel.getComponents()) {
                        if (child instanceof JLabel && "BOOKING_MODE_INDICATOR".equals(child.getName())) {
                            bookingPanel.remove(child);
                            break;
                        }
                    }
                    
                    // Add new indicator with proper AbsoluteLayout constraints
                    JLabel indicator = new JLabel(message);
                    indicator.setName("BOOKING_MODE_INDICATOR");
                    indicator.setFont(new Font("Segoe UI", Font.BOLD, 12));
                    indicator.setForeground(Color.WHITE);
                    indicator.setOpaque(true);
                    indicator.setBackground(color);
                    indicator.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
                    
                    // Use AbsoluteConstraints for AbsoluteLayout
                    bookingPanel.add(indicator, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 600, 30));
                    bookingPanel.revalidate();
                    bookingPanel.repaint();
                    break;
                }
            }
        }
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value,
                                                 boolean isSelected, int row, int column) {
        return button;
    }

    @Override
    public Object getCellEditorValue() {
        // The main logic is now handled in handleBooking() method
        // This method just returns the label for the table cell
        isPushed = false;
        return label;
    }
}
