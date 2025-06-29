package controller;

import wingsnepal.dao.WalkInPassengerListDao;
import wingsnepal.model.WalkInPassengerListModel;
import wingsnepal.view.EmployeeDashboard;
// import wingsnepal.view.WalkInPassengerEditDialog;  // Will be created later
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.Component;
import java.awt.Color;

/**
 * Controller class for walk-in passenger list operations
 * Manages walk-in passengers (bookings made by employees for walk-in customers)
 * 
 * @author WingsNepal Team
 */
public class WalkInPassengerListController {
    
    private WalkInPassengerListDao walkInPassengerDao;
    private EmployeeDashboard view;
    private DefaultTableModel tableModel;
    private List<WalkInPassengerListModel> currentPassengers; // Store passenger objects for edit/delete operations
    
    public WalkInPassengerListController(EmployeeDashboard view) {
        this.view = view;
        this.walkInPassengerDao = new WalkInPassengerListDao();
        this.currentPassengers = new ArrayList<>();
        setupTableModel();
    }
    
    /**
     * Setup the table model for walk-in passengers
     */
    private void setupTableModel() {
        String[] columnNames = {
            "Customer Name", "Flight Code", "Route", "Date", "Class", "Seat", "Status", "Actions"
        };
        
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 7; // Only Actions column is editable (now at index 7)
            }
        };
        
        view.getJTable2().setModel(tableModel);
        setupTableStyling();
    }
    
    /**
     * Setup table styling and column widths
     */
    private void setupTableStyling() {
        JTable table = view.getJTable2();
        
        // Set row height
        table.setRowHeight(35);
        
        // Set column widths for 8-column structure
        table.getColumnModel().getColumn(0).setPreferredWidth(150); // Customer Name
        table.getColumnModel().getColumn(1).setPreferredWidth(90);  // Flight Code
        table.getColumnModel().getColumn(2).setPreferredWidth(140); // Route
        table.getColumnModel().getColumn(3).setPreferredWidth(90);  // Date
        table.getColumnModel().getColumn(4).setPreferredWidth(70);  // Class
        table.getColumnModel().getColumn(5).setPreferredWidth(50);  // Seat
        table.getColumnModel().getColumn(6).setPreferredWidth(80);  // Status
        table.getColumnModel().getColumn(7).setPreferredWidth(180); // Actions (matching AdminDashboard)
        
        // Set minimum and maximum widths for Actions column
        table.getColumnModel().getColumn(7).setMinWidth(160);
        table.getColumnModel().getColumn(7).setMaxWidth(200);
        
        // Set auto resize mode
        table.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
        
        // Set up renderers and editors
        // Status column renderer with color coding
        table.getColumnModel().getColumn(6).setCellRenderer(new StatusCellRenderer());
        
        // Actions column renderer and editor
        table.getColumnModel().getColumn(7).setCellRenderer(new wingsnepal.view.WalkInPassengerActionButtonRenderer());
        table.getColumnModel().getColumn(7).setCellEditor(new wingsnepal.view.WalkInPassengerActionButtonEditor(
            e -> handleEditPassenger(table.getSelectedRow()),
            e -> handleDeletePassenger(table.getSelectedRow()),
            e -> handleChangeStatus(table.getSelectedRow())
        ));
    }
    
    /**
     * Load all walk-in passengers into the table
     */
    public void loadWalkInPassengers() {
        try {
            System.out.println("\n🔍 DIAGNOSTIC: Loading walk-in passengers...");
            
            // First, let's run a comprehensive diagnostic
            runDiagnostics();
            
            List<WalkInPassengerListModel> passengers = walkInPassengerDao.getAllWalkInPassengers();
            updateTableModel(passengers);
            
            System.out.println("✅ Loaded " + passengers.size() + " walk-in passengers");
            
            // Don't show the "No walk-in passengers found" message anymore
            // The user knows this system is working and they can create bookings via Reservations
            
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("❌ Error loading walk-in passengers: " + e.getMessage());
            JOptionPane.showMessageDialog(view, 
                "Error loading walk-in passengers:\n" + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Run comprehensive diagnostics to understand the database state
     */
    private void runDiagnostics() {
        try {
            System.out.println("\n🔍 DIAGNOSTIC REPORT:");
            System.out.println("====================");
            
            // Check 1: Total bookings in database
            wingsnepal.dao.ManageBookingDao manageBookingDao = new wingsnepal.dao.ManageBookingDao();
            java.util.List<wingsnepal.model.ManageBookingModel> allBookings = manageBookingDao.getAllBookingsWithFlightInfo();
            System.out.println("📊 Total bookings in system: " + allBookings.size());
            
            // Check 2: Show sample bookings with user roles
            if (allBookings.size() > 0) {
                System.out.println("\n📋 Sample bookings (first 3):");
                for (int i = 0; i < Math.min(3, allBookings.size()); i++) {
                    wingsnepal.model.ManageBookingModel booking = allBookings.get(i);
                    System.out.println("   Booking " + (i+1) + ": ID=" + booking.getBookingId() + 
                                     ", User ID=" + booking.getUserId() + 
                                     ", Flight=" + booking.getFlightCode() + 
                                     ", Passenger=" + booking.getPassengerName());
                }
            }
            
            // Check 3: Check users with Employee role
            wingsnepal.dao.UserDao userDao = new wingsnepal.dao.UserDao();
            java.util.List<wingsnepal.model.UserDataModel> employees = userDao.getAllEmployees();
            System.out.println("\n👥 Employees in system: " + employees.size());
            if (employees.size() > 0) {
                System.out.println("   Employee IDs: ");
                for (wingsnepal.model.UserDataModel emp : employees) {
                    System.out.println("   - " + emp.getUserId() + ": " + emp.getFullName() + " (" + emp.getRole() + ")");
                }
            }
            
            // NEW: Check what user ID 1 actually has in the database
            System.out.println("\n🔍 CHECKING USER ID 1 IN DATABASE:");
            try {
                java.sql.Connection conn = database.MySqlConnection.getConnection();
                java.sql.PreparedStatement stmt = conn.prepareStatement("SELECT user_id, full_name, role FROM users WHERE user_id = 1");
                java.sql.ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    System.out.println("   ✅ User ID 1 exists: " + rs.getString("full_name") + ", Role: " + rs.getString("role"));
                } else {
                    System.out.println("   ❌ User ID 1 does NOT exist in database!");
                }
                conn.close();
            } catch (Exception e) {
                System.out.println("   ❌ Error checking user ID 1: " + e.getMessage());
            }
            
            // Check 4: Check if any bookings are made by employees
            int employeeBookings = 0;
            for (wingsnepal.model.ManageBookingModel booking : allBookings) {
                for (wingsnepal.model.UserDataModel emp : employees) {
                    if (booking.getUserId() == emp.getUserId()) {
                        employeeBookings++;
                        System.out.println("🎯 Found employee booking: Booking ID " + booking.getBookingId() + 
                                         " by Employee " + emp.getFullName() + " (User ID: " + emp.getUserId() + ")");
                        break;
                    }
                }
            }
            System.out.println("📊 Employee-made bookings found: " + employeeBookings);
            
            // Check 5: Test the actual query
            java.util.List<WalkInPassengerListModel> walkInPassengers = walkInPassengerDao.getAllWalkInPassengers();
            System.out.println("📊 Walk-in passengers from DAO: " + walkInPassengers.size());
            
            System.out.println("\n💡 RECOMMENDATIONS:");
            if (allBookings.isEmpty()) {
                System.out.println("   ❌ No bookings exist in the system yet");
                System.out.println("   ➡️ Create some bookings first using the Reservations button");
            } else if (employees.isEmpty()) {
                System.out.println("   ❌ No employees found in the system");
                System.out.println("   ➡️ Add employees through Admin Dashboard");
            } else if (employeeBookings == 0) {
                System.out.println("   ❌ All existing bookings are made by regular users");
                System.out.println("   ➡️ Log in as an Employee and use 'Reservations' to create walk-in bookings");
                System.out.println("   ➡️ Walk-in bookings are identified by being created by Employee users");
                System.out.println("   ⚠️ CHECK: Make sure your logged-in user has Employee role in database");
            } else {
                System.out.println("   ✅ System appears to be working correctly");
                System.out.println("   ✅ Found " + employeeBookings + " employee bookings");
            }
            
            System.out.println("====================\n");
            
        } catch (Exception e) {
            System.err.println("❌ Diagnostic failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Search walk-in passengers by keyword
     * @param keyword Search keyword
     */
    public void searchWalkInPassengers(String keyword) {
        try {
            if (keyword == null || keyword.trim().isEmpty()) {
                loadWalkInPassengers();
                return;
            }
            
            List<WalkInPassengerListModel> passengers = walkInPassengerDao.searchWalkInPassengers(keyword.trim());
            updateTableModel(passengers);
            
            System.out.println("🔍 Found " + passengers.size() + " walk-in passengers matching '" + keyword + "'");
            
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("❌ Error searching walk-in passengers: " + e.getMessage());
            JOptionPane.showMessageDialog(view, 
                "Error searching walk-in passengers:\n" + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Filter walk-in passengers by seat class
     * @param seatClass Seat class to filter by
     */
    public void filterBySeatClass(String seatClass) {
        try {
            if (seatClass == null || seatClass.equals("All") || seatClass.equals("Seat Class")) {
                loadWalkInPassengers();
                return;
            }
            
            List<WalkInPassengerListModel> passengers = walkInPassengerDao.getWalkInPassengersBySeatClass(seatClass);
            updateTableModel(passengers);
            
            System.out.println("🔍 Found " + passengers.size() + " walk-in passengers in " + seatClass + " class");
            
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("❌ Error filtering walk-in passengers: " + e.getMessage());
            JOptionPane.showMessageDialog(view, 
                "Error filtering walk-in passengers:\n" + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Update the table model with passenger data
     * @param passengers List of walk-in passengers
     */
    private void updateTableModel(List<WalkInPassengerListModel> passengers) {
        tableModel.setRowCount(0);
        this.currentPassengers.clear();
        
        for (WalkInPassengerListModel passenger : passengers) {
            // Create route string (From → To)
            String route = (passenger.getFromCity() != null ? passenger.getFromCity() : "N/A") + 
                          " → " + 
                          (passenger.getToCity() != null ? passenger.getToCity() : "N/A");
            
            Object[] row = {
                passenger.getPassengerName() != null ? passenger.getPassengerName() : "N/A", // Customer Name
                passenger.getFlightCode(),                                                    // Flight Code
                route,                                                                        // Route
                passenger.getTravelDate(),                                                    // Date
                passenger.getSeatClass(),                                                     // Class
                passenger.getSeatNo(),                                                        // Seat
                passenger.getBookingStatus() != null ? passenger.getBookingStatus() : "Confirmed", // Status
                "" // Actions column
            };
            tableModel.addRow(row);
            this.currentPassengers.add(passenger); // Store passenger object at same index as table row
        }
        
        // Force table refresh
        view.getJTable2().revalidate();
        view.getJTable2().repaint();
    }
    
    /**
     * Handle editing a walk-in passenger
     * @param rowIndex Selected row index
     */
    private void handleEditPassenger(int rowIndex) {
        try {
            if (rowIndex < 0 || rowIndex >= currentPassengers.size()) {
                JOptionPane.showMessageDialog(view, 
                    "Please select a passenger to edit.", 
                    "No Selection", 
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            WalkInPassengerListModel passenger = currentPassengers.get(rowIndex);
            int bookingId = passenger.getBookingId();
            String passengerName = passenger.getPassengerName();
            String flightCode = passenger.getFlightCode();
            
            // Create callback to refresh the table after edit
            Runnable refreshCallback = () -> {
                loadWalkInPassengers();
                System.out.println("✅ Walk-in passengers table refreshed after edit");
            };
            
            // Open the edit dialog
            wingsnepal.view.WalkInPassengerEditDialog editDialog = new wingsnepal.view.WalkInPassengerEditDialog(
                (JFrame) javax.swing.SwingUtilities.getWindowAncestor(view), 
                bookingId, 
                refreshCallback
            );
            editDialog.setVisible(true);
            
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("❌ Error editing walk-in passenger: " + e.getMessage());
            JOptionPane.showMessageDialog(view, 
                "Error editing walk-in passenger:\n" + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Handle deleting a walk-in passenger
     * @param rowIndex Selected row index
     */
    private void handleDeletePassenger(int rowIndex) {
        try {
            if (rowIndex < 0 || rowIndex >= currentPassengers.size()) {
                JOptionPane.showMessageDialog(view, 
                    "Please select a passenger to delete.", 
                    "No Selection", 
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            WalkInPassengerListModel passenger = currentPassengers.get(rowIndex);
            int bookingId = passenger.getBookingId();
            String passengerName = passenger.getPassengerName();
            String flightCode = passenger.getFlightCode();
            String seatNo = passenger.getSeatNo();
            
            int confirmation = JOptionPane.showConfirmDialog(view, 
                "Delete this booking?", 
                "Confirm Delete", 
                JOptionPane.YES_NO_OPTION, 
                JOptionPane.QUESTION_MESSAGE);
            
            if (confirmation == JOptionPane.YES_OPTION) {
                boolean success = walkInPassengerDao.deleteWalkInPassenger(bookingId);
                
                if (success) {
                    JOptionPane.showMessageDialog(view, 
                        "Booking deleted successfully!", 
                        "Success", 
                        JOptionPane.INFORMATION_MESSAGE);
                    
                    // Refresh the table
                    loadWalkInPassengers();
                    
                    System.out.println("✅ Deleted walk-in passenger: " + passengerName + " (Booking ID: " + bookingId + ")");
                    
                } else {
                    JOptionPane.showMessageDialog(view, 
                        "Failed to delete booking.", 
                        "Error", 
                        JOptionPane.ERROR_MESSAGE);
                }
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("❌ Error deleting walk-in passenger: " + e.getMessage());
            JOptionPane.showMessageDialog(view, 
                "Error deleting walk-in passenger:\n" + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Get total walk-in passengers count
     * @return Total count
     */
    public int getTotalWalkInPassengersCount() {
        try {
            return walkInPassengerDao.getTotalWalkInPassengersCount();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("❌ Error getting total walk-in passengers count: " + e.getMessage());
            return 0;
        }
    }
    
    /**
     * Get selected walk-in passenger
     * @return Selected passenger or null
     */
    public WalkInPassengerListModel getSelectedPassenger() {
        int selectedRow = view.getJTable2().getSelectedRow();
        if (selectedRow == -1 || selectedRow >= currentPassengers.size()) {
            JOptionPane.showMessageDialog(view, 
                "Please select a passenger first.", 
                "No Selection", 
                JOptionPane.WARNING_MESSAGE);
            return null;
        }
        
        return currentPassengers.get(selectedRow);
    }
    
    /**
     * Refresh the walk-in passengers table
     */
    public void refreshTable() {
        loadWalkInPassengers();
    }
    
    /**
     * Create a test walk-in booking for testing purposes
     * This method helps verify the system is working correctly
     */
    public void createTestWalkInBooking() {
        try {
            // Check if we have any employees and flights
            wingsnepal.dao.UserDao userDao = new wingsnepal.dao.UserDao();
            java.util.List<wingsnepal.model.UserDataModel> employees = userDao.getAllEmployees();
            
            if (employees.isEmpty()) {
                JOptionPane.showMessageDialog(view, 
                    "Cannot create test booking: No employees found in system.\n" +
                    "Please add employees through Admin Dashboard first.", 
                    "Test Booking Failed", 
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            // Get available flights
            wingsnepal.dao.SearchFlightDao flightDao = new wingsnepal.dao.SearchFlightDao();
            java.util.List<wingsnepal.model.SearchFlightModel> flights = flightDao.getAllFlights();
            
            if (flights.isEmpty()) {
                JOptionPane.showMessageDialog(view, 
                    "Cannot create test booking: No flights found in system.\n" +
                    "Please add flights through Admin Dashboard first.", 
                    "Test Booking Failed", 
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            // Use first employee and first flight for test
            wingsnepal.model.UserDataModel testEmployee = employees.get(0);
            wingsnepal.model.SearchFlightModel testFlight = flights.get(0);
            
            // Get flight ID using flight code
            wingsnepal.dao.SeatClassDao seatDao = new wingsnepal.dao.SeatClassDao();
            int flightId = seatDao.getFlightIdByCode(testFlight.getFlightCode());
            
            // Create test booking
            wingsnepal.dao.BookingFlightDao bookingDao = new wingsnepal.dao.BookingFlightDao();
            wingsnepal.model.BookingFlightModel testBooking = new wingsnepal.model.BookingFlightModel(
                testEmployee.getUserId(), // Employee user ID
                flightId,
                1, // seat ID
                "Test Walk-In Customer",
                "test.walkin@wingsnepal.com",
                "Economy",
                "A1",
                1, // tickets
                new java.sql.Date(System.currentTimeMillis()),
                "Cash",
                testFlight.getFlightCode()
            );
            testBooking.setBooked(true);
            
            boolean success = bookingDao.saveBooking(testBooking);
            
            if (success) {
                JOptionPane.showMessageDialog(view, 
                    "✅ Test walk-in booking created successfully!\n\n" +
                    "Employee: " + testEmployee.getFullName() + "\n" +
                    "Customer: Test Walk-In Customer\n" +
                    "Flight: " + testFlight.getFlightCode() + "\n" +
                    "Seat: Economy A1\n\n" +
                    "The Passenger List should now show this booking.", 
                    "Test Booking Created", 
                    JOptionPane.INFORMATION_MESSAGE);
                
                // Refresh the table to show the new booking
                loadWalkInPassengers();
            } else {
                JOptionPane.showMessageDialog(view, 
                    "❌ Failed to create test walk-in booking.\n" +
                    "Please check database connection and try again.", 
                    "Test Booking Failed", 
                    JOptionPane.ERROR_MESSAGE);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(view, 
                "Error creating test booking: " + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Custom cell renderer for status column with color coding
     */
    private static class StatusCellRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, 
                                                     boolean hasFocus, int row, int column) {
            Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            
            if (!isSelected && value != null) {
                String status = value.toString().toLowerCase();
                switch (status) {
                    case "confirmed":
                        component.setBackground(new Color(212, 237, 218)); // Light green background
                        component.setForeground(new Color(21, 87, 36));   // Dark green text
                        break;
                    case "cancelled":
                        component.setBackground(new Color(248, 215, 218)); // Light red background
                        component.setForeground(new Color(132, 32, 41));   // Dark red text
                        break;
                    default:
                        component.setBackground(table.getBackground());
                        component.setForeground(table.getForeground());
                        break;
                }
            } else if (isSelected) {
                // Keep default selection colors when row is selected
                component.setBackground(table.getSelectionBackground());
                component.setForeground(table.getSelectionForeground());
            }
            
            // Center align the text
            setHorizontalAlignment(CENTER);
            
            return component;
        }
    }
    
    /**
     * Handle changing the status of a walk-in passenger
     * @param rowIndex Selected row index
     */
    private void handleChangeStatus(int rowIndex) {
        try {
            if (rowIndex < 0 || rowIndex >= currentPassengers.size()) {
                JOptionPane.showMessageDialog(view, 
                    "Please select a passenger to change status.", 
                    "No Selection", 
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            WalkInPassengerListModel passenger = currentPassengers.get(rowIndex);
            int bookingId = passenger.getBookingId();
            String currentStatus = passenger.getBookingStatus() != null ? passenger.getBookingStatus() : "Confirmed";
            
            // Open the status change dialog
            wingsnepal.view.ChangePassengerStatusDialog statusDialog = new wingsnepal.view.ChangePassengerStatusDialog(
                (JFrame) javax.swing.SwingUtilities.getWindowAncestor(view), 
                currentStatus
            );
            statusDialog.setVisible(true);
            
            // Check if user confirmed the status change
            if (statusDialog.isConfirmed()) {
                String newStatus = statusDialog.getSelectedStatus();
                
                if (!currentStatus.equals(newStatus)) {
                    // Update the booking status in the database
                    boolean statusUpdated = walkInPassengerDao.updateBookingStatus(bookingId, newStatus);
                    
                    if (statusUpdated) {
                        // Handle seat management based on status change
                        if ("Cancelled".equals(newStatus)) {
                            // Release the seat when cancelling
                            wingsnepal.dao.SeatClassDao seatClassDao = new wingsnepal.dao.SeatClassDao();
                            int flightId = seatClassDao.getFlightIdByCode(passenger.getFlightCode());
                            if (flightId != -1) {
                                seatClassDao.releaseSeat(flightId, passenger.getSeatClass(), passenger.getSeatNo());
                                System.out.println("✅ Seat released due to cancellation: " + passenger.getSeatClass() + " - " + passenger.getSeatNo());
                            }
                        } else if ("Confirmed".equals(newStatus) && "Cancelled".equals(currentStatus)) {
                            // Mark seat as booked when confirming a previously cancelled booking
                            wingsnepal.dao.SeatClassDao seatClassDao = new wingsnepal.dao.SeatClassDao();
                            int flightId = seatClassDao.getFlightIdByCode(passenger.getFlightCode());
                            if (flightId != -1) {
                                seatClassDao.markSeatAsBooked(flightId, passenger.getSeatClass(), passenger.getSeatNo());
                                System.out.println("✅ Seat marked as booked due to confirmation: " + passenger.getSeatClass() + " - " + passenger.getSeatNo());
                            }
                        }
                        
                        // Show success message
                        String message = "Status updated successfully!";
                        
                        if ("Cancelled".equals(newStatus)) {
                            message += "\nSeat has been released.";
                        } else if ("Confirmed".equals(newStatus) && "Cancelled".equals(currentStatus)) {
                            message += "\nSeat has been booked.";
                        }
                        
                        JOptionPane.showMessageDialog(view, message, "Success", JOptionPane.INFORMATION_MESSAGE);
                        
                        // Refresh the table to show the new status
                        loadWalkInPassengers();
                    } else {
                        JOptionPane.showMessageDialog(view, 
                            "Failed to update status.", 
                            "Error", 
                            JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("❌ Error changing walk-in passenger status: " + e.getMessage());
            JOptionPane.showMessageDialog(view, 
                "Error changing passenger status:\n" + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
} 