package controller;

import wingsnepal.view.EmployeeDashboard;
import wingsnepal.view.WalkInBookingDialog;
import wingsnepal.model.UserDataModel;
import wingsnepal.model.SearchFlightModel;
import wingsnepal.dao.SearchFlightDao;
import javax.swing.*;
import java.awt.FlowLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import wingsnepal.dao.ManageBookingDao;

public class EmployeeDashboardController {
    private EmployeeDashboard view;
    private UserDataModel user;

    public EmployeeDashboardController(EmployeeDashboard view, UserDataModel user) {
        this.view = view;
        this.user = user;
        setupEventListeners();
        loadFlightsTable(); // Load flights on startup
        
        // Also initialize and load walk-in passengers on startup
        System.out.println("🚀 Initializing walk-in passenger system on startup...");
        initializeWalkInPassengerList();
    }

    private void setupEventListeners() {
        // Dashboard button
        view.getEmpDashboardButton().addActionListener(e -> view.getTabbedPane().setSelectedIndex(0));
        // Flights button
        view.getEmpFlightsButton().addActionListener(e -> {
            view.getTabbedPane().setSelectedIndex(1);
            loadFlightsTable(); // Refresh flights when tab is selected
        });
        // Passenger List button
        view.getEmpPassengerButton().addActionListener(e -> {
            view.getTabbedPane().setSelectedIndex(2);
            loadWalkInBookings(); // Load walk-in customer bookings when tab is selected
        });
        // Reservations button - open booking dialog for walk-in customers
        view.getReservationsButton().addActionListener(e -> handleWalkInBooking());
        // Logout button
        view.getLogoutButton().addActionListener(e -> handleLogout());

        // Flight management event listeners
        view.getSearchFlightjButton().addActionListener(e -> handleSearchFlights());
        
        // Live search for flights (enhanced like Admin Dashboard)
        view.getSearchFlightsTextField().addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent evt) {
                String keyword = view.getSearchFlightsTextField().getText().trim();
                if (keyword.isEmpty() || keyword.equals("Search Flights...")) {
                    loadFlightsTable(); // Show all flights when search is cleared
                } else {
                    searchFlights(keyword); // Live search as user types
                }
            }
        });

        // Walk-in booking management event listeners
        view.getShowWalkInBookingsButton().addActionListener(e -> {
            System.out.println("🔘 DEBUG: Show Walk-In Bookings button clicked!");
            loadWalkInBookings();
        });
        
        // Live search for walk-in bookings
        view.getWalkInSearchTextField().addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent evt) {
                String keyword = view.getWalkInSearchTextField().getText().trim();
                System.out.println("🔘 DEBUG: Walk-in search text changed: '" + keyword + "'");
                if (keyword.isEmpty()) {
                    loadWalkInBookings();
                } else {
                    searchWalkInBookings(keyword);
                }
            }
        });
        
        // Add a debug test method for walk-in bookings
        testWalkInBookingSystem();
    }

    private void handleLogout() {
        int choice = JOptionPane.showConfirmDialog(view, "Are you sure you want to log out?", "Logout Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (choice == JOptionPane.YES_OPTION) {
            view.dispose();
            new wingsnepal.view.LoginPage().setVisible(true);
        }
    }

    private void handleWalkInBooking() {
        WalkInBookingDialog dialog = new WalkInBookingDialog((JFrame) SwingUtilities.getWindowAncestor(view), user);
        dialog.setVisible(true);
    }

    // =========================== FLIGHTS FUNCTIONALITY ===========================
    // Complete copy of admin dashboard flights functionality

    public void loadFlightsTable() {
        SearchFlightDao dao = new SearchFlightDao();
        List<SearchFlightModel> flights = dao.getAllFlightsWithPricing(); // Get all pricing information for edit dialogs

        DefaultTableModel model = new DefaultTableModel(
            new Object[]{"S.N.", "Flight Code", "Flight Name", "Origin", "Destination", "Dep. Date", "Dep. Time", "Price", "Duration", "Actions"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Only the "Actions" column is editable
                return column == 9; // Back to original index
            }
        };

        view.getJTable1().setModel(model);

        // Re-apply EMPLOYEE-SPECIFIC button renderer, editor, and styles AFTER setting the model
        view.getJTable1().setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
        view.getJTable1().setRowHeight(35);
        view.getJTable1().getColumn("Actions").setCellRenderer(new wingsnepal.view.EmployeeFlightButtonRenderer());
        view.getJTable1().getColumn("Actions").setCellEditor(new wingsnepal.view.EmployeeFlightButtonEditor(new javax.swing.JCheckBox()));
        view.getJTable1().getColumn("Actions").setPreferredWidth(180); // Increased width for Edit + View buttons
        view.getJTable1().getColumn("Actions").setMinWidth(180); // Ensure minimum width
        view.getJTable1().getColumn("Actions").setMaxWidth(200); // Prevent too much expansion
        
        // Set width for the S.N. column
        view.getJTable1().getColumn("S.N.").setPreferredWidth(40);

        int sn = 1;
        for (SearchFlightModel flight : flights) {
            model.addRow(new Object[]{
                sn++,
                flight.getFlightCode(),
                flight.getFlightName(),
                flight.getFromCity(),
                flight.getToCity(),
                flight.getDate(),
                formatTime(flight.getTime()),
                "Rs. " + flight.getEconomyPrice(),      // Only show Economy price in table
                flight.getDuration(),
                ""
            });
        }
        
        System.out.println("Employee Dashboard: Loaded " + flights.size() + " flights (showing Economy price, but all prices available for editing)");
    }
    
    public void searchFlights(String keyword) {
        if (keyword.isEmpty()) {
            loadFlightsTable();
            return;
        }
        SearchFlightDao dao = new SearchFlightDao();
        List<SearchFlightModel> flights = dao.getAllFlightsWithPricing(); // Use pricing method for search too
        
        // Simple live search across all fields (like Admin Dashboard)
        List<SearchFlightModel> filteredFlights = new ArrayList<>();
        String keywordLower = keyword.toLowerCase();
        
        for (SearchFlightModel flight : flights) {
            if (flight.getFlightCode().toLowerCase().contains(keywordLower) ||
                flight.getFlightName().toLowerCase().contains(keywordLower) ||
                flight.getFromCity().toLowerCase().contains(keywordLower) ||
                flight.getToCity().toLowerCase().contains(keywordLower) ||
                flight.getDate().contains(keyword)) {
                filteredFlights.add(flight);
            }
        }

        DefaultTableModel model = (DefaultTableModel) view.getJTable1().getModel();
        model.setRowCount(0);

        int sn = 1;
        for (SearchFlightModel flight : filteredFlights) {
            model.addRow(new Object[]{
                sn++,
                flight.getFlightCode(),
                flight.getFlightName(),
                flight.getFromCity(),
                flight.getToCity(),
                flight.getDate(),
                formatTime(flight.getTime()),
                "Rs. " + flight.getEconomyPrice(),      // Only show Economy price in table
                flight.getDuration(),
                ""
            });
        }
        
        System.out.println("Employee live search found " + filteredFlights.size() + " flights matching '" + keyword + "'");
    }
    
    // Add Flight functionality removed - employees cannot add flights
    // This is an admin-only capability
    
    private void handleSearchFlights() {
        String keyword = view.getSearchFlightsTextField().getText().trim();
        if (keyword.isEmpty() || keyword.equals("Search Flights...")) {
            loadFlightsTable();
        } else {
            searchFlights(keyword);
        }
    }

    // Helper method to format time (copied from AdminDashboardController)
    private String formatTime(String time) {
        try {
            // If time is already in correct format, return as is
            if (time.contains("AM") || time.contains("PM")) {
                return time;
            }
            
            // Parse time in HH:mm format and convert to 12-hour format
            String[] parts = time.split(":");
            if (parts.length >= 2) {
                int hour = Integer.parseInt(parts[0]);
                int minute = Integer.parseInt(parts[1]);
                
                String ampm = hour < 12 ? "AM" : "PM";
                if (hour == 0) hour = 12;
                else if (hour > 12) hour -= 12;
                
                return String.format("%d:%02d %s", hour, minute, ampm);
            }
            return time;
        } catch (Exception e) {
            return time; // Return original if parsing fails
        }
    }

    // =========================== LEGACY METHODS ===========================
    // Keeping old methods for backwards compatibility but they will be unused

    public void loadAssignedFlightsTable() {
        // This method is now replaced by loadFlightsTable()
        // Keeping for backwards compatibility but calling new method
        loadFlightsTable();
    }

    // Search assigned flights based on criteria
    public void searchAssignedFlights(String searchText, String searchCriteria) {
        // This method is now replaced by searchFlights()
        // Keeping for backwards compatibility but calling new method
        searchFlights(searchText);
    }

    // ButtonRenderer for Edit button (legacy - now using FlightButtonRenderer)
    class ButtonRenderer extends javax.swing.table.DefaultTableCellRenderer {
        javax.swing.JButton button = new javax.swing.JButton();

        public ButtonRenderer() {
            button.setOpaque(true);
        }

        @Override
        public java.awt.Component getTableCellRendererComponent(javax.swing.JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            button.setText("Edit");
            button.setBackground(new java.awt.Color(0, 123, 255));
            button.setForeground(java.awt.Color.WHITE);
            button.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12));
            return button;
        }
    }

    // ButtonEditor for Edit button (legacy - now using FlightButtonEditor)
    class ButtonEditor extends javax.swing.DefaultCellEditor {
        javax.swing.JButton button;
        String label;
        boolean clicked;
        EmployeeDashboard parentView;

        public ButtonEditor(javax.swing.JCheckBox checkBox, EmployeeDashboard view) {
            super(checkBox);
            this.parentView = view;
            button = new javax.swing.JButton();
            button.setOpaque(true);
            button.addActionListener(e -> fireEditingStopped());
        }

        @Override
        public java.awt.Component getTableCellEditorComponent(javax.swing.JTable table, Object value, boolean isSelected, int row, int column) {
            label = "Edit";
            button.setText(label);
            button.setBackground(new java.awt.Color(0, 123, 255));
            button.setForeground(java.awt.Color.WHITE);
            button.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12));
            clicked = true;
            return button;
        }

        @Override
        public Object getCellEditorValue() {
            if (clicked) {
                // Handle edit action - for now just show a message
                javax.swing.JOptionPane.showMessageDialog(parentView, "Edit functionality will be implemented later.");
            }
            clicked = false;
            return label;
        }

        @Override
        public boolean stopCellEditing() {
            clicked = false;
            return super.stopCellEditing();
        }
    }

    /**
     * Public method to refresh flight data after employee updates
     * This ensures system-wide consistency when flight data is modified
     */
    public void refreshFlightData() {
        try {
            System.out.println("🔄 Refreshing Employee Dashboard flight data...");
            
            // Maintain current search state if user was searching
            String currentSearch = view.getSearchFlightsTextField().getText().trim();
            if (!currentSearch.isEmpty() && !currentSearch.equals("Search Flights...")) {
                searchFlights(currentSearch); // Refresh search results
                System.out.println("✅ Employee Dashboard search results refreshed for: '" + currentSearch + "'");
            } else {
                loadFlightsTable(); // Refresh all flights
                System.out.println("✅ Employee Dashboard flight data refreshed successfully");
            }
            
        } catch (Exception ex) {
            System.err.println("❌ Error refreshing Employee Dashboard flight data: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
    
    /**
     * Enhanced method to get current search keyword from UI
     */
    private String getCurrentSearchKeyword() {
        String keyword = view.getSearchFlightsTextField().getText().trim();
        return (keyword.isEmpty() || keyword.equals("Search Flights...")) ? "" : keyword;
    }
    
    /**
     * Static method to handle system-wide flight data updates
     * This can be called from anywhere in the application to ensure consistency
     */
    public static void notifyFlightDataChanged(String flightCode, String changeType) {
        try {
            System.out.println("🔔 SYSTEM NOTIFICATION: Flight data changed");
            System.out.println("   Flight Code: " + flightCode);
            System.out.println("   Change Type: " + changeType);
            System.out.println("   Timestamp: " + java.time.LocalDateTime.now());
            
            // Log to indicate which parts of the system should refresh
            System.out.println("📊 Components that should refresh:");
            System.out.println("   ✓ Employee Dashboard");
            System.out.println("   ✓ Admin Dashboard (next access)");
            System.out.println("   ✓ User Portal search results");
            System.out.println("   ✓ Booking system pricing");
            System.out.println("   ✓ All future database queries");
            
        } catch (Exception ex) {
            System.err.println("❌ Error in flight data change notification: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
    
    // =========================== WALK-IN CUSTOMER BOOKING MANAGEMENT ===========================
    // Complete walk-in customer booking management functionality for Employee Dashboard
    
    /**
     * Load walk-in customer bookings in the Passenger List tab
     */
    // Initialize walk-in passenger list controller
    private controller.WalkInPassengerListController walkInPassengerController;
    
    public void initializeWalkInPassengerList() {
        if (walkInPassengerController == null) {
            walkInPassengerController = new controller.WalkInPassengerListController(view);
        }
    }
    
    public void loadWalkInBookings() {
        try {
            System.out.println("🔍 DEBUG: Loading walk-in passengers using new controller...");
            System.out.println("🔍 Current user: " + user.getFullName() + " (Role: " + user.getRole() + ", ID: " + user.getUserId() + ")");
            
            // Initialize the walk-in passenger controller if not already done
            initializeWalkInPassengerList();
            
            // Use the new walk-in passenger controller to load data
            walkInPassengerController.loadWalkInPassengers();
            
            System.out.println("✅ DEBUG: Walk-in passengers loaded successfully using new system");
            
            // Column widths are now set by WalkInPassengerListController
            System.out.println("✅ DEBUG: Walk-in passenger table initialized with proper column widths");
            
            // Apply status renderer if available
                        System.out.println("✅ DEBUG: Walk-in passengers loaded successfully using new controller");
            
        } catch (Exception ex) {
            System.err.println("❌ ERROR: Failed to load walk-in passengers");
            System.err.println("❌ Error message: " + ex.getMessage());
            ex.printStackTrace();
            JOptionPane.showMessageDialog(view, 
                "Error loading walk-in passengers:\n" + ex.getMessage() + 
                "\n\nPlease check the console for detailed error information.", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Handle editing a walk-in customer booking
     */
    private void handleEditWalkInBooking(int rowIndex) {
        try {
            DefaultTableModel model = (DefaultTableModel) view.getJTable2().getModel();
            int bookingId = (int) model.getValueAt(rowIndex, 0);
            
            // TODO: Create and show the walk-in customer edit dialog when class is available
            // For now, show simple message
            JOptionPane.showMessageDialog(view, 
                "Edit functionality will be available soon.\nBooking ID: " + bookingId, 
                "Edit Walk-In Booking", 
                JOptionPane.INFORMATION_MESSAGE);
            
            /*
            // Will implement later when WalkInCustomerEditDialog is created
            WalkInCustomerEditDialog editDialog = new WalkInCustomerEditDialog(
                (JFrame) SwingUtilities.getWindowAncestor(view), 
                bookingId, 
                () -> {
                    // Refresh callback
                    loadWalkInBookings();
                    System.out.println("✅ Walk-in customer booking table refreshed after edit");
                }
            );
            editDialog.setVisible(true);
            */
            
        } catch (Exception ex) {
            System.err.println("❌ Error editing walk-in booking: " + ex.getMessage());
            ex.printStackTrace();
            JOptionPane.showMessageDialog(view, 
                "Error editing walk-in customer booking: " + ex.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Handle deleting a walk-in customer booking
     */
    private void handleDeleteWalkInBooking(int rowIndex) {
        try {
            DefaultTableModel model = (DefaultTableModel) view.getJTable2().getModel();
            int bookingId = (int) model.getValueAt(rowIndex, 0);
            String customerName = (String) model.getValueAt(rowIndex, 1);
            String flightCode = (String) model.getValueAt(rowIndex, 3);
            String seatNo = (String) model.getValueAt(rowIndex, 7);
            
            int confirmation = JOptionPane.showConfirmDialog(view, 
                "Delete this booking?", 
                "Confirm Delete", 
                JOptionPane.YES_NO_OPTION, 
                JOptionPane.WARNING_MESSAGE);
            
            if (confirmation == JOptionPane.YES_OPTION) {
                ManageBookingDao manageBookingDao = new ManageBookingDao();
                boolean success = manageBookingDao.deleteWalkInBooking(bookingId);
                
                if (success) {
                    JOptionPane.showMessageDialog(view, 
                        "Booking deleted successfully!", 
                        "Success", 
                        JOptionPane.INFORMATION_MESSAGE);
                    
                    // Refresh the table
                    loadWalkInBookings();
                    
                    // Notify system of booking change
                    notifyFlightDataChanged(flightCode, "Walk-in Customer Booking Deleted");
                    
                } else {
                    JOptionPane.showMessageDialog(view, 
                        "Failed to delete booking.", 
                        "Error", 
                        JOptionPane.ERROR_MESSAGE);
                }
            }
            
        } catch (Exception ex) {
            System.err.println("❌ Error deleting walk-in booking: " + ex.getMessage());
            ex.printStackTrace();
            JOptionPane.showMessageDialog(view, 
                "Error deleting walk-in customer booking: " + ex.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Search walk-in customer bookings using new controller
     */
    public void searchWalkInBookings(String keyword) {
        try {
            // Initialize the walk-in passenger controller if not already done
            initializeWalkInPassengerList();
            
            // Use the new walk-in passenger controller to search
            walkInPassengerController.searchWalkInPassengers(keyword);
            
            System.out.println("✅ Walk-in passenger search completed using new system");
            
        } catch (Exception ex) {
            System.err.println("❌ Error searching walk-in passengers: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    // Add a debug test method for walk-in bookings
    private void testWalkInBookingSystem() {
        try {
            System.out.println("\n🧪 DIAGNOSTIC TEST: Walk-In Booking System");
            System.out.println("================================================");
            
            ManageBookingDao manageBookingDao = new ManageBookingDao();
            
            // Test 1: Check all bookings (without filtering)
            List<wingsnepal.model.ManageBookingModel> allBookings = manageBookingDao.getAllBookingsWithFlightInfo();
            System.out.println("📊 Total bookings in system: " + allBookings.size());
            
            if (allBookings.size() > 0) {
                System.out.println("✅ Sample booking data:");
                for (int i = 0; i < Math.min(3, allBookings.size()); i++) {
                    wingsnepal.model.ManageBookingModel booking = allBookings.get(i);
                    System.out.println("   Booking " + (i+1) + ": ID=" + booking.getBookingId() + 
                                     ", Flight=" + booking.getFlightCode() + 
                                     ", Passenger=" + booking.getPassengerName());
                }
            }
            
            // Test 2: Check walk-in bookings specifically
            List<wingsnepal.model.ManageBookingModel> walkInBookings = manageBookingDao.getWalkInBookings();
            System.out.println("📊 Walk-in bookings found: " + walkInBookings.size());
            
            // Test 3: Check regular user bookings
            List<wingsnepal.model.ManageBookingModel> regularBookings = manageBookingDao.getRegularUserBookings();
            System.out.println("📊 Regular user bookings: " + regularBookings.size());
            
            // Test 4: Check current user role
            System.out.println("👤 Current user: " + user.getFullName() + ", Role: " + user.getRole());
            
            System.out.println("\n💡 RECOMMENDATIONS:");
            if (walkInBookings.isEmpty() && allBookings.size() > 0) {
                System.out.println("   • All existing bookings are from regular users");
                System.out.println("   • Use 'Reservations' button to create walk-in customer bookings");
                System.out.println("   • Walk-in bookings are identified by being created by employees");
            } else if (allBookings.isEmpty()) {
                System.out.println("   • No bookings exist in the system yet");
                System.out.println("   • Create some bookings first (either regular or walk-in)");
            } else {
                System.out.println("   • Walk-in booking system appears to be working correctly");
            }
            
            System.out.println("================================================\n");
            
        } catch (Exception ex) {
            System.err.println("❌ DIAGNOSTIC TEST FAILED: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    /**
     * Filter walk-in passengers by seat class using new controller
     */
    public void filterWalkInPassengersBySeatClass(String seatClass) {
        try {
            // Initialize the walk-in passenger controller if not already done
            initializeWalkInPassengerList();
            
            // Use the new walk-in passenger controller to filter
            walkInPassengerController.filterBySeatClass(seatClass);
            
            System.out.println("✅ Walk-in passenger filtering completed using new system");
            
        } catch (Exception ex) {
            System.err.println("❌ Error filtering walk-in passengers by seat class: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    /**
     * Load and display dashboard metrics
     */
    public void loadDashboardMetrics() {
        try {
            // Load assigned flights count
            SearchFlightDao flightDao = new SearchFlightDao();
            List<SearchFlightModel> assignedFlights = flightDao.getAllFlightsWithPricing();
            view.getAssignedFlightsCountLabel().setText(String.valueOf(assignedFlights.size()));
            
            // Load walk-in reservations count
            wingsnepal.dao.WalkInPassengerListDao walkInDao = new wingsnepal.dao.WalkInPassengerListDao();
            List<wingsnepal.model.WalkInPassengerListModel> walkInReservations = walkInDao.getAllWalkInPassengers();
            view.getWalkInReservationsCountLabel().setText(String.valueOf(walkInReservations.size()));
            
            // Load general reservations count (all bookings)
            ManageBookingDao bookingDao = new ManageBookingDao();
            List<wingsnepal.model.ManageBookingModel> allBookings = bookingDao.getAllBookingsWithFlightInfo();
            view.getReservationCountLabel().setText(String.valueOf(allBookings.size()));
            
            System.out.println("📊 Dashboard metrics loaded: " + assignedFlights.size() + " flights, " + 
                             walkInReservations.size() + " walk-in reservations, " + allBookings.size() + " total bookings");
            
        } catch (Exception e) {
            System.err.println("❌ Error loading dashboard metrics: " + e.getMessage());
            e.printStackTrace();
            // Set default values on error
            view.getAssignedFlightsCountLabel().setText("0");
            view.getWalkInReservationsCountLabel().setText("0");
            view.getReservationCountLabel().setText("0");
        }
    }
} 