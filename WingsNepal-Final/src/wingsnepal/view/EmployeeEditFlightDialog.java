package wingsnepal.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import wingsnepal.dao.SearchFlightDao;
import wingsnepal.model.SearchFlightModel;

public class EmployeeEditFlightDialog extends JDialog {
    
    // Callback interface for notifying parent when flight is updated
    public interface FlightUpdateCallback {
        void onFlightUpdated(String flightCode);
    }
    
    private JTextField flightCodeField, flightNameField;
    private JTextField departureDateField, departureTimeField, durationField;
    private JTextField economyPriceField, businessPriceField, firstPriceField;
    private JButton saveButton, cancelButton;
    
    private String flightCode;
    private SearchFlightDao flightDao;
    private FlightUpdateCallback updateCallback;

    public EmployeeEditFlightDialog(JFrame parent, String flightCode) {
        this(parent, flightCode, null);
    }
    
    public EmployeeEditFlightDialog(JFrame parent, String flightCode, FlightUpdateCallback callback) {
        super(parent, "Employee Edit Flight", true);
        this.flightCode = flightCode;
        this.flightDao = new SearchFlightDao();
        this.updateCallback = callback;
        
        initializeComponents();
        setupLayout();
        populateFields();
        setupEventListeners();
        
        setSize(750, 850); // Increased height to ensure all pricing fields are visible
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(true); // Allow resizing if needed
    }
    
    private void initializeComponents() {
        // Read-only flight information
        flightCodeField = new JTextField();
        flightCodeField.setEditable(false);
        flightCodeField.setBackground(new Color(240, 240, 240));
        
        flightNameField = new JTextField();
        flightNameField.setEditable(false);
        flightNameField.setBackground(new Color(240, 240, 240));
        
        // Editable operational fields
        departureDateField = new JTextField();
        departureTimeField = new JTextField();
        durationField = new JTextField();
        
        // Pricing fields
        economyPriceField = new JTextField();
        businessPriceField = new JTextField();
        firstPriceField = new JTextField();
        
        // Buttons
        saveButton = new JButton("Save Changes");
        saveButton.setBackground(new Color(23, 162, 184));
        saveButton.setForeground(Color.WHITE);
        saveButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        saveButton.setPreferredSize(new Dimension(140, 45));
        
        cancelButton = new JButton("Cancel");
        cancelButton.setBackground(new Color(220, 53, 69));
        cancelButton.setForeground(Color.WHITE);
        cancelButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        cancelButton.setPreferredSize(new Dimension(120, 45));
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE);
        
        // Title
        JLabel titleLabel = new JLabel("Employee Edit Flight", JLabel.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(new Color(23, 162, 184));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        add(titleLabel, BorderLayout.NORTH);
        
        // Main content panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(0, 40, 20, 40));
        
        // Flight Information Section (Read-only)
        mainPanel.add(createSectionTitle("Flight Information"));
        mainPanel.add(createFieldPanel("Flight Code:", flightCodeField, false));
        mainPanel.add(createFieldPanel("Flight Name:", flightNameField, false));
        
        mainPanel.add(Box.createVerticalStrut(20));
        
        // Operational Information Section (Editable)
        mainPanel.add(createSectionTitle("Operational Settings (Employee Editable)"));
        mainPanel.add(createFieldPanel("Departure Date:", departureDateField, true));
        mainPanel.add(createFieldPanel("Departure Time:", departureTimeField, true));
        mainPanel.add(createFieldPanel("Flight Duration:", durationField, true));
        
        mainPanel.add(Box.createVerticalStrut(20));
        
        // Pricing Information Section (Editable)
        mainPanel.add(createSectionTitle("Seat Pricing by Class (Employee Editable)"));
        mainPanel.add(createFieldPanel("Economy Class Price (NPR):", economyPriceField, true));
        mainPanel.add(createFieldPanel("Business Class Price (NPR):", businessPriceField, true));
        mainPanel.add(createFieldPanel("First Class Price (NPR):", firstPriceField, true));
        mainPanel.add(Box.createVerticalStrut(20)); // Add some spacing at the bottom
        
        // Wrap main panel in a scroll pane to ensure all content is accessible
        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        
        add(scrollPane, BorderLayout.CENTER);
        
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 20));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    private JLabel createSectionTitle(String title) {
        JLabel label = new JLabel(title);
        label.setFont(new Font("Segoe UI", Font.BOLD, 18));
        label.setForeground(new Color(68, 68, 68));
        label.setBorder(BorderFactory.createEmptyBorder(10, 0, 15, 0));
        label.setAlignmentX(Component.LEFT_ALIGNMENT); // Force left alignment in BoxLayout
        return label;
    }
    
    private JPanel createFieldPanel(String labelText, JTextField field, boolean editable) {
        JPanel panel = new JPanel(new BorderLayout(10, 0));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(8, 0, 8, 0));
        panel.setAlignmentX(Component.LEFT_ALIGNMENT); // Force left alignment in BoxLayout
        
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        label.setPreferredSize(new Dimension(220, 30));
        
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setPreferredSize(new Dimension(300, 35));
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        
        if (!editable) {
            field.setBackground(new Color(240, 240, 240));
            field.setForeground(new Color(100, 100, 100));
        }
        
        panel.add(label, BorderLayout.WEST);
        panel.add(field, BorderLayout.CENTER);
        
        return panel;
    }
    
    private void populateFields() {
        // Get flight data from database using the proper method that includes all pricing
        SearchFlightModel flight = flightDao.getFlightByCode(flightCode);
        if (flight != null) {
            flightCodeField.setText(flight.getFlightCode());
            flightNameField.setText(flight.getFlightName());
            departureDateField.setText(flight.getDate());
            departureTimeField.setText(flight.getTime());
            durationField.setText(flight.getDuration());
            
            // Set actual pricing from database (using the multi-price constructor)
            economyPriceField.setText(String.valueOf(flight.getEconomyPrice()));
            businessPriceField.setText(String.valueOf(flight.getBusinessPrice()));
            firstPriceField.setText(String.valueOf(flight.getFirstClassPrice()));
        } else {
            // If flight not found, show default values
            JOptionPane.showMessageDialog(this, 
                "Flight data not found for code: " + flightCode, 
                "Data Error", 
                JOptionPane.WARNING_MESSAGE);
            economyPriceField.setText("450");
            businessPriceField.setText("675");
            firstPriceField.setText("1125");
        }
    }
    
    private SearchFlightModel getFlightByCode(String code) {
        // Get specific flight by code using the DAO method that includes pricing
        return flightDao.getFlightByCode(code);
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
            // Validate input
            String newDate = departureDateField.getText().trim();
            String newTime = departureTimeField.getText().trim();
            String newDuration = durationField.getText().trim();
            String economyPrice = economyPriceField.getText().trim();
            String businessPrice = businessPriceField.getText().trim();
            String firstPrice = firstPriceField.getText().trim();
            
            if (newDate.isEmpty() || newTime.isEmpty() || newDuration.isEmpty()) {
                JOptionPane.showMessageDialog(this, 
                    "Please fill all operational fields.", 
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
                        "Validation Error", 
                        JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, 
                    "Please enter valid price numbers.", 
                    "Validation Error", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Perform database update using the new updateFlight method
            boolean updateSuccess = flightDao.updateFlight(flightCode, newDate, newTime, newDuration,
                                                          economyPriceInt, businessPriceInt, firstPriceInt);
            
            if (updateSuccess) {
                JOptionPane.showMessageDialog(this, 
                    "Flight updated successfully!",
                    "Success", 
                    JOptionPane.INFORMATION_MESSAGE);
                
                // Notify the system that flight data has changed
                controller.EmployeeDashboardController.notifyFlightDataChanged(flightCode, "Employee Update");
                
                if (updateCallback != null) {
                    updateCallback.onFlightUpdated(flightCode);
                }
                
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Failed to update flight. Please try again.",
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, 
                "An error occurred: " + ex.getMessage(),
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

} 