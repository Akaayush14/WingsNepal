package wingsnepal.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import wingsnepal.dao.SearchFlightDao;
import wingsnepal.model.SearchFlightModel;

public class EditFlightDialog extends JDialog {
    
    // Callback interface for notifying parent when flight is updated
    public interface FlightUpdateCallback {
        void onFlightUpdated(String flightCode);
    }
    
    private JTextField flightCodeField, flightNameField;
    private JTextField originField, destinationField;
    private JTextField departureDateField, departureTimeField, durationField;
    private JTextField economyPriceField, businessPriceField, firstPriceField;
    private JButton saveButton, cancelButton;
    
    private String originalFlightCode;
    private SearchFlightDao flightDao;
    private FlightUpdateCallback updateCallback;

    public EditFlightDialog(JFrame parent, String flightCode) {
        this(parent, flightCode, null);
    }
    
    public EditFlightDialog(JFrame parent, String flightCode, FlightUpdateCallback callback) {
        super(parent, "Edit Flight", true);
        this.originalFlightCode = flightCode;
        this.flightDao = new SearchFlightDao();
        this.updateCallback = callback;
        
        initializeComponents();
        setupLayout();
        populateFields();
        setupEventListeners();
        
        setSize(750, 700);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(true);
    }
    
    private void initializeComponents() {
        // All fields are editable for admin
        flightCodeField = new JTextField();
        flightNameField = new JTextField();
        originField = new JTextField();
        destinationField = new JTextField();
        departureDateField = new JTextField();
        departureTimeField = new JTextField();
        durationField = new JTextField();
        
        // Pricing fields
        economyPriceField = new JTextField();
        businessPriceField = new JTextField();
        firstPriceField = new JTextField();
        
        // Buttons with exact colors matching other dialogs in the project
        saveButton = new JButton("Save");
        saveButton.setBackground(new Color(0, 102, 153)); // Exact blue color from passenger dialogs
        saveButton.setForeground(Color.WHITE);
        saveButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        saveButton.setPreferredSize(new Dimension(120, 45));
        saveButton.setFocusPainted(false);
        saveButton.setBorderPainted(false);
        
        cancelButton = new JButton("Cancel");
        cancelButton.setBackground(new Color(220, 53, 69)); // Exact red color from passenger dialogs
        cancelButton.setForeground(Color.WHITE);
        cancelButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        cancelButton.setPreferredSize(new Dimension(120, 45));
        cancelButton.setFocusPainted(false);
        cancelButton.setBorderPainted(false);
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE);
        
        // Title Panel
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.setBackground(Color.WHITE);
        titlePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));
        
        JLabel titleLabel = new JLabel("Edit Flight");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 26));
        titleLabel.setForeground(new Color(23, 162, 184));
        titlePanel.add(titleLabel);
        add(titlePanel, BorderLayout.NORTH);
        
        // Main content panel with proper alignment
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 40, 20, 40));
        
        // Flight Identification Section
        mainPanel.add(createLeftAlignedSectionTitle("Flight Identification"));
        mainPanel.add(createFieldPanel("Flight Code:", flightCodeField));
        mainPanel.add(createFieldPanel("Flight Name:", flightNameField));
        mainPanel.add(Box.createVerticalStrut(20));
        
        // Route Information Section
        mainPanel.add(createLeftAlignedSectionTitle("Route Information"));
        mainPanel.add(createFieldPanel("Origin City:", originField));
        mainPanel.add(createFieldPanel("Destination City:", destinationField));
        mainPanel.add(Box.createVerticalStrut(20));
        
        // Schedule Information Section
        mainPanel.add(createLeftAlignedSectionTitle("Schedule Information"));
        mainPanel.add(createFieldPanel("Departure Date:", departureDateField));
        mainPanel.add(createFieldPanel("Departure Time:", departureTimeField));
        mainPanel.add(createFieldPanel("Flight Duration:", durationField));
        mainPanel.add(Box.createVerticalStrut(20));
        
        // Pricing Section - This was getting cut off before!
        mainPanel.add(createLeftAlignedSectionTitle("Seat Class Pricing"));
        mainPanel.add(createFieldPanel("Economy Class Price (NPR):", economyPriceField));
        mainPanel.add(createFieldPanel("Business Class Price (NPR):", businessPriceField));
        mainPanel.add(createFieldPanel("First Class Price (NPR):", firstPriceField));
        mainPanel.add(Box.createVerticalStrut(20)); // Add some spacing at the bottom
        
        // Wrap main panel in a scroll pane to ensure all content is accessible
        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        
        add(scrollPane, BorderLayout.CENTER);
        
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 25));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    private JPanel createLeftAlignedSectionTitle(String title) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        panel.setBackground(Color.WHITE);
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel label = new JLabel(title);
        label.setFont(new Font("Segoe UI", Font.BOLD, 18));
        label.setForeground(new Color(68, 68, 68));
        label.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));
        
        panel.add(label);
        return panel;
    }
    
    private JPanel createFieldPanel(String labelText, JTextField field) {
        JPanel panel = new JPanel(new BorderLayout(15, 0));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(8, 0, 8, 0));
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        label.setPreferredSize(new Dimension(200, 35));
        label.setVerticalAlignment(SwingConstants.CENTER);
        
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setPreferredSize(new Dimension(400, 35));
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        
        panel.add(label, BorderLayout.WEST);
        panel.add(field, BorderLayout.CENTER);
        
        return panel;
    }
    
    private void populateFields() {
        // Get flight data from database with enhanced pricing retrieval
        SearchFlightModel flight = flightDao.getFlightByCode(originalFlightCode);
        if (flight != null) {
            // Populate basic flight information
            flightCodeField.setText(flight.getFlightCode());
            flightNameField.setText(flight.getFlightName());
            originField.setText(flight.getFromCity());
            destinationField.setText(flight.getToCity());
            departureDateField.setText(flight.getDate());
            departureTimeField.setText(flight.getTime());
            durationField.setText(flight.getDuration());
            
            // Enhanced seat class pricing retrieval
            try {
                // Try to get the specific class prices first
                int economyPrice = flight.getEconomyPrice();
                int businessPrice = flight.getBusinessPrice(); 
                int firstClassPrice = flight.getFirstClassPrice();
                
                System.out.println("Retrieved pricing data for " + originalFlightCode + ":");
                System.out.println("   Raw Economy: " + economyPrice);
                System.out.println("   Raw Business: " + businessPrice);
                System.out.println("   Raw First Class: " + firstClassPrice);
                
                // Check if we got valid prices (greater than 0)
                if (economyPrice > 0 && businessPrice > 0 && firstClassPrice > 0) {
                    // Use the retrieved prices
                    economyPriceField.setText(String.valueOf(economyPrice));
                    businessPriceField.setText(String.valueOf(businessPrice));
                    firstPriceField.setText(String.valueOf(firstClassPrice));
                    
                    System.out.println("Using database seat class prices");
                } else {
                    // Fallback: Try to use the base price and calculate others
                    int basePrice = flight.getPrice();
                    if (basePrice > 0) {
                        economyPrice = basePrice;
                        businessPrice = (int)(basePrice * 1.5);
                        firstClassPrice = (int)(basePrice * 2.5);
                        
                        economyPriceField.setText(String.valueOf(economyPrice));
                        businessPriceField.setText(String.valueOf(businessPrice));
                        firstPriceField.setText(String.valueOf(firstClassPrice));
                        
                        System.out.println("⚠Using calculated prices from base price: " + basePrice);
                    } else {
                        // Last resort: Use default prices
                        economyPriceField.setText("450");
                        businessPriceField.setText("675");
                        firstPriceField.setText("1125");
                        
                        System.out.println("Using default fallback prices");
                    }
                }
                
            } catch (Exception e) {
                // Error handling - use safe default prices
                economyPriceField.setText("450");
                businessPriceField.setText("675");
                firstPriceField.setText("1125");
                
                System.out.println("Error retrieving prices for " + originalFlightCode + ": " + e.getMessage());
                System.out.println("   Using default prices");
            }
            
            System.out.println("Final pricing displayed:");
            System.out.println("   Economy Field: " + economyPriceField.getText());
            System.out.println("   Business Field: " + businessPriceField.getText());
            System.out.println("   First Class Field: " + firstPriceField.getText());
            
        } else {
            // Flight not found - show error and use defaults
            JOptionPane.showMessageDialog(this, 
                "Flight data not found. Using default values.",
                "Data Not Found", 
                JOptionPane.WARNING_MESSAGE);
            
            // Set default values for all fields
            flightCodeField.setText(originalFlightCode);
            flightNameField.setText("");
            originField.setText("");
            destinationField.setText("");
            departureDateField.setText("");
            departureTimeField.setText("");
            durationField.setText("");
            
            // Default pricing
            economyPriceField.setText("450");
            businessPriceField.setText("675");
            firstPriceField.setText("1125");
            
            System.out.println("Flight " + originalFlightCode + " not found in database");
        }
    }
    
    private void setupEventListeners() {
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveChanges();
            }
        });
        
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }
    
    private void saveChanges() {
        try {
            // Get all field values
            String flightCode = flightCodeField.getText().trim();
            String flightName = flightNameField.getText().trim();
            String origin = originField.getText().trim();
            String destination = destinationField.getText().trim();
            String date = departureDateField.getText().trim();
            String time = departureTimeField.getText().trim();
            String duration = durationField.getText().trim();
            String economyPrice = economyPriceField.getText().trim();
            String businessPrice = businessPriceField.getText().trim();
            String firstPrice = firstPriceField.getText().trim();
            
            // Validate required fields
            if (flightCode.isEmpty() || flightName.isEmpty() || origin.isEmpty() || 
                destination.isEmpty() || date.isEmpty() || time.isEmpty() || duration.isEmpty()) {
                JOptionPane.showMessageDialog(this, 
                    "Please fill all required fields.", 
                    "Validation Error", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Validate pricing
            int economyPriceInt, businessPriceInt, firstPriceInt;
            try {
                economyPriceInt = Integer.parseInt(economyPrice);
                businessPriceInt = Integer.parseInt(businessPrice);
                firstPriceInt = Integer.parseInt(firstPrice);
                
                if (economyPriceInt <= 0 || businessPriceInt <= 0 || firstPriceInt <= 0) {
                    JOptionPane.showMessageDialog(this, 
                        "All prices must be positive numbers.", 
                        "Invalid Pricing", 
                        JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, 
                    "Please enter valid numbers for all price fields.", 
                    "Invalid Price Format", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Perform database update
            boolean updateSuccess = flightDao.updateFlight(originalFlightCode, date, time, duration,
                                                          economyPriceInt, businessPriceInt, firstPriceInt);
            
            if (updateSuccess) {
                JOptionPane.showMessageDialog(this, 
                    "Flight updated successfully!",
                    "Success", 
                    JOptionPane.INFORMATION_MESSAGE);
                
                if (updateCallback != null) {
                    updateCallback.onFlightUpdated(flightCode);
                }
                
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Failed to update flight. Please try again.",
                    "Update Failed", 
                    JOptionPane.ERROR_MESSAGE);
            }
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, 
                "An error occurred: " + ex.getMessage(),
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
}
