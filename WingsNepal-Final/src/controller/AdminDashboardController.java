/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import wingsnepal.model.UserDataModel;
import wingsnepal.model.SearchFlightModel;
import wingsnepal.model.PassengerModel;
import wingsnepal.model.DashboardMetrics;
import wingsnepal.dao.UserDao;
import wingsnepal.dao.SearchFlightDao;
import wingsnepal.dao.PassengerDao;
import wingsnepal.dao.ManageBookingDao;
import wingsnepal.view.AdminDashboard;
import wingsnepal.view.LoginPage;
import wingsnepal.view.AddEmployeeDialog;
import wingsnepal.view.AddFlightDialog;
import wingsnepal.view.AddReservationDialog;

import wingsnepal.view.PassengerActionButtonRenderer;
import wingsnepal.view.PassengerActionButtonEditor;
import wingsnepal.view.EditPassengerDialog;
import javax.swing.SwingUtilities;
import java.awt.Frame;
import wingsnepal.view.EmployeeStatusCellRenderer;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.Font;
import javax.swing.BorderFactory;
import java.awt.Cursor;

/**
 *
 * @author aayus
 */
public class AdminDashboardController {
    private AdminDashboard view;
    private UserDataModel user;
    private String name;

    public AdminDashboardController(AdminDashboard view, UserDataModel user) {
        this.view = view;
        this.user = user;
        this.name = user.getFullName();
        
        this.view.setController(this);
        
        // Setup event listeners
        setupEventListeners();
        
        // Add mouse listener for employee table actions
        view.getEmployeeTable().addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = view.getEmployeeTable().rowAtPoint(evt.getPoint());
                int col = view.getEmployeeTable().columnAtPoint(evt.getPoint());
                if (col == 8) { // Actions column
                    // Get the bounds of the buttons
                    javax.swing.JTable table = view.getEmployeeTable();
                    javax.swing.table.TableCellEditor editor = table.getCellEditor(row, col);
                    if (editor instanceof wingsnepal.view.EmployeeActionButtonEditor) {
                        // Already handled by editor
                        return;
                    }
                    // Determine which button was clicked
                    java.awt.Rectangle cellRect = table.getCellRect(row, col, false);
                    int x = evt.getX() - cellRect.x;
                    // Assume Edit is left, Delete is right (50/50 split)
                    if (x < cellRect.width / 2) {
                        handleEditEmployee(row);
                    } else {
                        handleDeleteEmployee(row);
                    }
                }
            }
        });
        
        // Initialize default booking statuses for existing bookings
        ManageBookingDao bookingDao = new ManageBookingDao();
        bookingDao.setDefaultBookingStatus();
        
        // Load initial data
        loadEmployeeTable();
        loadFlightsTable();
        loadPassengersTable();
        loadUsersTable();
        loadDashboardMetrics();
    }
    
    public void open() {
        this.view.setVisible(true);
    }
    
    public void close() {
        this.view.dispose();
    }
    
    private void setupEventListeners() {
        // Employee management
        view.getBtnAddEmployee().addActionListener(e -> handleAddEmployee());
        view.getSearchButton().addActionListener(e -> handleSearchEmployees());
        
        // Flight management
        view.getAddFlightsjButton().addActionListener(e -> handleAddFlight());
        view.getSearchFlightjButton().addActionListener(e -> handleSearchFlights());
        
        // Passenger management
        view.getSearchPassengerButton().addActionListener(e -> handleSearchPassengers());
        view.getShowAllButton().addActionListener(e -> loadPassengersTable());
        
        // Users management
        view.getSearchUsersButton().addActionListener(e -> handleSearchUsers());
        view.getSearchUsersTextField().addActionListener(e -> handleSearchUsers());
        
        // Reservation management
        // view.getBtnAddReservation().addActionListener(e -> handleAddReservation());
        
        // Navigation
        view.getLogOutbutton().addActionListener(e -> handleLogout());
        
        // Tab navigation
        setupTabNavigation();
        // Add tab change listener to clear passenger table on tab switch
        view.getJTabbedPane1().addChangeListener(e -> {
            int selectedIndex = view.getJTabbedPane1().getSelectedIndex();
            // Assuming passenger tab is index 2 (adjust if needed)
            if (selectedIndex == 2) {
                // Clear passenger table
                javax.swing.table.DefaultTableModel model = (javax.swing.table.DefaultTableModel) view.getJTable3().getModel();
                model.setRowCount(0);
            }
        });
        
        // Live search
        setupLiveSearch();
    }
    
    private void setupTabNavigation() {
        // These will be handled by the view's existing action listeners
        // The controller will just handle the business logic
    }
    
    private void setupLiveSearch() {
        // Employee search
        view.getSearchTextField().addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent evt) {
                String keyword = view.getSearchTextField().getText().trim();
                if (keyword.isEmpty() || keyword.equals("Search Employees")) {
                    loadEmployeeTable();
                } else {
                    // This will be updated later to use a more specific search in UserDao
                    loadEmployeeTable(); 
                }
            }
        });
        
        // Flight search
        view.getSearchFlightsTextField().addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent evt) {
                String keyword = view.getSearchFlightsTextField().getText().trim();
                searchFlights(keyword);
            }
        });
        
        // Passenger search
        view.getSearchPassengerTextField().addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent evt) {
                String keyword = view.getSearchPassengerTextField().getText().trim();
                if (keyword.isEmpty() || keyword.equals("Search Passengers")) {
                    loadPassengersTable();
                } else {
                    searchPassengers(keyword);
                }
            }
        });
        
        // Passenger seat class filter
        view.getSeatClassTextField().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                String seatClass = (String) view.getSeatClassTextField().getSelectedItem();
                loadPassengersBySeatClass(seatClass);
            }
        });
        
        // Users search
        view.getSearchUsersTextField().addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent evt) {
                String keyword = view.getSearchUsersTextField().getText().trim();
                if (keyword.isEmpty() || keyword.equals("Search User Accounts") || keyword.equals("Search User Accounts...")) {
                    loadUsersTable();
                } else {
                    handleSearchUsers();
                }
            }
        });
    }
    
    // Employee Management
    public void loadEmployeeTable() {
        UserDao dao = new UserDao();
        List<UserDataModel> allUsers = dao.getAllUsers();
        
        // Filter to only include employees
        List<UserDataModel> employees = new ArrayList<>();
        for (UserDataModel user : allUsers) {
            if ("Employee".equals(user.getRole())) {
                employees.add(user);
            }
        }

        DefaultTableModel model = (DefaultTableModel) view.getEmployeeTable().getModel();
        model.setRowCount(0); // Clear existing data

        // Set up action buttons for Actions column (index 8 - after adding Phone column)
        view.getEmployeeTable().getColumnModel().getColumn(8).setCellRenderer(new wingsnepal.view.EmployeeActionButtonRenderer());
        view.getEmployeeTable().getColumnModel().getColumn(8).setCellEditor(
            new wingsnepal.view.EmployeeActionButtonEditor(view.getEmployeeTable(), new wingsnepal.view.EmployeeActionButtonEditor.EmployeeActionCallback() {
                @Override
                public void onEdit(int row) {
                    handleEditEmployee(row);
                }
                @Override
                public void onDelete(int row) {
                    handleDeleteEmployee(row);
                }
            })
        );
        
        // Fix action column width to prevent button wrapping
        view.getEmployeeTable().getColumnModel().getColumn(8).setPreferredWidth(180);
        view.getEmployeeTable().getColumnModel().getColumn(8).setMinWidth(180);
        view.getEmployeeTable().getColumnModel().getColumn(8).setMaxWidth(200);
        
        // Set other column widths for better layout
        view.getEmployeeTable().getColumnModel().getColumn(0).setPreferredWidth(80);  // Employee ID
        view.getEmployeeTable().getColumnModel().getColumn(1).setPreferredWidth(150); // Full Name
        view.getEmployeeTable().getColumnModel().getColumn(2).setPreferredWidth(180); // Email
        view.getEmployeeTable().getColumnModel().getColumn(3).setPreferredWidth(120); // Phone
        view.getEmployeeTable().getColumnModel().getColumn(4).setPreferredWidth(120); // Job Title
        view.getEmployeeTable().getColumnModel().getColumn(5).setPreferredWidth(80);  // Salary
        view.getEmployeeTable().getColumnModel().getColumn(6).setPreferredWidth(80);  // Gender
        view.getEmployeeTable().getColumnModel().getColumn(7).setPreferredWidth(80);  // Status
        
        view.getEmployeeTable().setRowHeight(32);

        for (UserDataModel emp : employees) {
            model.addRow(new Object[]{
                emp.getUserId(),
                emp.getFullName(),
                emp.getEmail(),
                emp.getPhone() != null ? emp.getPhone() : "",
                emp.getJobTitle(),
                emp.getSalary(),
                emp.getGender(),
                emp.getStatus(),
                ""
            });
        }
    }
    
    private void handleAddEmployee() {
        AddEmployeeDialog dialog = new AddEmployeeDialog(view);
        dialog.setVisible(true);
        loadEmployeeTable();
    }
    
    private void handleSearchEmployees() {
        String keyword = view.getSearchTextField().getText().trim();

        UserDao dao = new UserDao();
        List<UserDataModel> allUsers = dao.getAllUsers();
        
        // Filter to only include employees and apply search
        List<UserDataModel> filteredEmployees = new ArrayList<>();
        for (UserDataModel user : allUsers) {
            if ("Employee".equals(user.getRole()) && 
                (keyword.isEmpty() || keyword.equals("Search Employees") ||
                user.getFullName().toLowerCase().contains(keyword.toLowerCase()) ||
                user.getEmail().toLowerCase().contains(keyword.toLowerCase()) ||
                (user.getPhone() != null && user.getPhone().toLowerCase().contains(keyword.toLowerCase())) ||
                (user.getJobTitle() != null && user.getJobTitle().toLowerCase().contains(keyword.toLowerCase())) ||
                String.valueOf(user.getUserId()).contains(keyword))) {
                filteredEmployees.add(user);
            }
        }

        DefaultTableModel model = (DefaultTableModel) view.getEmployeeTable().getModel();
        model.setRowCount(0);

        for (UserDataModel emp : filteredEmployees) {
            model.addRow(new Object[]{
                emp.getUserId(),
                emp.getFullName(),
                emp.getEmail(),
                emp.getPhone() != null ? emp.getPhone() : "",
                emp.getJobTitle(),
                emp.getSalary(),
                emp.getGender(),
                emp.getStatus(),
                ""
            });
        }
    }
    
    // Flight Management
    public void loadFlightsTable() {
        SearchFlightDao dao = new SearchFlightDao();
        List<SearchFlightModel> flights = dao.getAllFlights();

        DefaultTableModel model = new DefaultTableModel(
            new Object[]{"S.N.", "Flight Code", "Flight Name", "Origin", "Destination", "Dep. Date", "Dep. Time", "Price", "Duration", "Actions"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Only the "Actions" column is editable
                return column == 9;
            }
        };

        view.getJTable4().setModel(model);

        // Re-apply button renderer, editor, and styles AFTER setting the model
        view.getJTable4().setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
        view.getJTable4().setRowHeight(35);
        view.getJTable4().getColumn("Actions").setCellRenderer(new wingsnepal.view.FlightButtonRenderer());
        
        // Create FlightButtonEditor with callback to refresh table after updates
        wingsnepal.view.FlightButtonEditor.TableRefreshCallback refreshCallback = () -> {
            System.out.println("Refreshing flights table after flight update...");
            loadFlightsTable();
        };
        view.getJTable4().getColumn("Actions").setCellEditor(new wingsnepal.view.FlightButtonEditor(new javax.swing.JCheckBox(), refreshCallback));
        
        view.getJTable4().getColumn("Actions").setPreferredWidth(160);
        
        // Set width for the S.N. column
        view.getJTable4().getColumn("S.N.").setPreferredWidth(40);

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
                flight.getPrice(),
                flight.getDuration(),
                ""
            });
        }
    }
    
    public void searchFlights(String keyword) {
        if (keyword.isEmpty()) {
            loadFlightsTable();
            return;
        }
        SearchFlightDao dao = new SearchFlightDao();
        List<SearchFlightModel> flights = dao.getAllFlights(); // For now, we'll filter from all flights
        
        // Filter flights based on keyword
        List<SearchFlightModel> filteredFlights = new ArrayList<>();
        for (SearchFlightModel flight : flights) {
            if (flight.getFlightCode().toLowerCase().contains(keyword.toLowerCase()) ||
                flight.getFlightName().toLowerCase().contains(keyword.toLowerCase()) ||
                flight.getFromCity().toLowerCase().contains(keyword.toLowerCase()) ||
                flight.getToCity().toLowerCase().contains(keyword.toLowerCase()) ||
                flight.getDate().contains(keyword)) {
                filteredFlights.add(flight);
            }
        }
        
        DefaultTableModel model = (DefaultTableModel) view.getJTable4().getModel();
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
                flight.getPrice(),
                flight.getDuration(),
                ""
            });
        }
    }
    
    private void handleAddFlight() {
        AddFlightDialog dialog = new AddFlightDialog(view);
        dialog.setVisible(true);
        loadFlightsTable(); // Refresh the table after adding a flight
    }
    
    private void handleSearchFlights() {
        String keyword = view.getSearchFlightsTextField().getText().trim();
        if (keyword.isEmpty() || keyword.equals("Search Flights...")) {
            loadFlightsTable();
        } else {
            searchFlights(keyword);
        }
    }
    
    // Passenger Management
    public void loadPassengersTable() {
        try {
            PassengerDao passengerDao = new PassengerDao();
            // Only run restoration if there's actual corruption, not on every load
            // passengerDao.restorePassengerDataIntegrity(); // REMOVED - too aggressive
            
            List<PassengerModel> passengers = passengerDao.getAllPassengers();

            DefaultTableModel model = new DefaultTableModel(
                new Object[]{"Passenger ID", "Full Name", "Email", "Phone", "Seat Class", "Seat No", "Flight Code", "Booking Status", "Actions"}, 0) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return column == 8; // Only Actions column is editable
                }
            };

            view.getJTable3().setModel(model);

            // Set up renderers and editors
            view.getJTable3().getColumnModel().getColumn(7).setCellRenderer(new wingsnepal.view.BookingStatusCellRenderer());

            view.getJTable3().getColumnModel().getColumn(8).setCellRenderer(new PassengerActionButtonRenderer());
            view.getJTable3().getColumnModel().getColumn(8).setCellEditor(new PassengerActionButtonEditor(
                e -> handleEditPassenger(view.getJTable3().getSelectedRow()),
                e -> handleDeletePassenger(view.getJTable3().getSelectedRow())
            ));

            // Set column widths for passenger table
            view.getJTable3().getColumnModel().getColumn(8).setPreferredWidth(180); // Actions
            view.getJTable3().getColumnModel().getColumn(8).setMinWidth(180);
            view.getJTable3().getColumnModel().getColumn(8).setMaxWidth(200);

            view.getJTable3().setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
            view.getJTable3().setRowHeight(35);

            for (PassengerModel passenger : passengers) {
                model.addRow(new Object[]{
                    passenger.getPassengerId(),
                    passenger.getFullName() != null ? passenger.getFullName() : "",
                    passenger.getEmail() != null ? passenger.getEmail() : "",
                    passenger.getPhone() != null ? passenger.getPhone() : "",
                    passenger.getSeatClass() != null ? passenger.getSeatClass() : "",
                    passenger.getSeatNumber() != null ? passenger.getSeatNumber() : "",
                    passenger.getUpcomingFlight() != null ? passenger.getUpcomingFlight() : "",
                    passenger.getBookingStatus() != null ? passenger.getBookingStatus() : "",
                    "Actions"
                });
            }

            System.out.println("Loaded " + passengers.size() + " passengers");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error loading passengers table: " + e.getMessage());
            JOptionPane.showMessageDialog(view,
                "Error loading passengers: " + e.getMessage(),
                "Database Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Manually restore passenger data integrity when corruption is detected
     * This is a utility method that can be called when needed
     */
    public void manuallyRestorePassengerIntegrity() {
        try {
            PassengerDao passengerDao = new PassengerDao();
            boolean restored = passengerDao.restorePassengerDataIntegrity();
            if (restored) {
                JOptionPane.showMessageDialog(view,
                    "Passenger data integrity restored successfully.\nAny corrupted records have been fixed.",
                    "Data Integrity Restored",
                    JOptionPane.INFORMATION_MESSAGE);
                // Refresh the passengers table to show the cleaned data
                loadPassengersTable();
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(view,
                "Error restoring passenger data integrity: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Refresh passengers table after user status changes to handle any booking status updates
     */
    public void refreshPassengersAfterUserStatusChange() {
        try {
            // Small delay to allow booking status updates to complete
            Thread.sleep(100);
            loadPassengersTable();
            System.out.println("Passengers table refreshed after user status change");
        } catch (Exception e) {
            System.err.println("Error refreshing passengers table: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public void searchPassengers(String keyword) {
        try {
            if (keyword.isEmpty() || keyword.equals("Search Passengers")) {
                loadPassengersTable();
                return;
            }
            
            PassengerDao dao = new PassengerDao();
            List<PassengerModel> passengers = dao.searchPassengers(keyword);
            
            DefaultTableModel model = (DefaultTableModel) view.getJTable3().getModel();
            model.setRowCount(0);

            for (PassengerModel passenger : passengers) {
                model.addRow(new Object[]{
                    passenger.getPassengerId(),
                    passenger.getFullName() != null ? passenger.getFullName() : "",
                    passenger.getEmail() != null ? passenger.getEmail() : "",
                    passenger.getPhone() != null ? passenger.getPhone() : "",
                    passenger.getSeatClass() != null ? passenger.getSeatClass() : "",
                    passenger.getSeatNumber() != null ? passenger.getSeatNumber() : "",
                    passenger.getUpcomingFlight() != null ? passenger.getUpcomingFlight() : "",
                    passenger.getBookingStatus() != null ? passenger.getBookingStatus() : "",
                    ""
                });
            }
            
            System.out.println("Found " + passengers.size() + " passengers matching: " + keyword);
        } catch (Exception e) {
            System.err.println("Error searching passengers: " + e.getMessage());
            e.printStackTrace();
            JOptionPane.showMessageDialog(view, 
                "Error searching passengers: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void loadPassengersBySeatClass(String seatClass) {
        try {
            if (seatClass == null || seatClass.equals("Seat Class")) {
                loadPassengersTable();
                return;
            }
            PassengerDao dao = new PassengerDao();
            List<PassengerModel> passengers = dao.getPassengersBySeatClass(seatClass);
            DefaultTableModel model = (DefaultTableModel) view.getJTable3().getModel();
            model.setRowCount(0);
            for (PassengerModel passenger : passengers) {
                model.addRow(new Object[]{
                    passenger.getPassengerId(),
                    passenger.getFullName() != null ? passenger.getFullName() : "",
                    passenger.getEmail() != null ? passenger.getEmail() : "",
                    passenger.getPhone() != null ? passenger.getPhone() : "",
                    passenger.getSeatClass() != null ? passenger.getSeatClass() : "",
                    passenger.getSeatNumber() != null ? passenger.getSeatNumber() : "",
                    passenger.getUpcomingFlight() != null ? passenger.getUpcomingFlight() : "",
                    passenger.getBookingStatus() != null ? passenger.getBookingStatus() : "",
                    ""
                });
            }
            System.out.println("Found " + passengers.size() + " passengers in seat class: " + seatClass);
        } catch (Exception e) {
            System.err.println("Error loading passengers by seat class: " + e.getMessage());
            e.printStackTrace();
            JOptionPane.showMessageDialog(view, 
                "Error filtering passengers: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void handleSearchPassengers() {
        String keyword = view.getSearchPassengerTextField().getText().trim();
        String seatClass = (String) view.getSeatClassTextField().getSelectedItem();
        
        if (keyword.isEmpty() || keyword.equals("Search Passengers")) {
            // If no keyword, filter by seat class only
            loadPassengersBySeatClass(seatClass);
        } else {
            // If keyword provided, search by keyword and filter by seat class
            searchPassengers(keyword);
        }
    }
    
    // Reservation Management
    private void handleAddReservation() {
        AddReservationDialog dialog = new AddReservationDialog(view, true, () -> {
            // Refresh reservations if needed
        });
        dialog.setVisible(true);
    }
    
    // Dashboard Metrics
    private void loadDashboardMetrics() {
        try {
            SearchFlightDao flightDao = new SearchFlightDao();
            int flightsCount = flightDao.getTotalFlightsCount();
            view.getTotalFlightsLabel().setText(String.valueOf(flightsCount));

            int availableSeatsCount = flightDao.getTotalAvailableSeats();
            view.getAvailableSeatsLabel().setText(String.valueOf(availableSeatsCount));

            PassengerDao passengerDao = new PassengerDao();
            int totalPassengersCount = passengerDao.getTotalPassengersCount();
            view.getTotalPassengersLabel().setText(String.valueOf(totalPassengersCount));

            // Load new metrics
            UserDao userDao = new UserDao();
            
            // Get employees count
            List<UserDataModel> allUsers = userDao.getAllUsers();
            int employeesCount = 0;
            int regularUsersCount = 0;
            for (UserDataModel user : allUsers) {
                if ("Employee".equals(user.getRole())) {
                    employeesCount++;
                } else {
                    regularUsersCount++;
                }
            }
            view.getTotalEmployeesLabel().setText(String.valueOf(employeesCount));
            view.getTotalUsersLabel().setText(String.valueOf(regularUsersCount));

            // Get bookings count
            ManageBookingDao bookingDao = new ManageBookingDao();
            int totalBookingsCount = bookingDao.getTotalBookingsCount();
            view.getTotalBookingsLabel().setText(String.valueOf(totalBookingsCount));
            
        } catch (Exception e) {
            System.err.println("Error loading dashboard metrics: " + e.getMessage());
            e.printStackTrace();
            // Set default values if there's an error
            view.getTotalFlightsLabel().setText("0");
            view.getAvailableSeatsLabel().setText("0");
            view.getTotalPassengersLabel().setText("0");
            view.getTotalEmployeesLabel().setText("0");
            view.getTotalUsersLabel().setText("0");
            view.getTotalBookingsLabel().setText("0");
        }
    }
    
    // Logout
    private void handleLogout() {
        int confirm = JOptionPane.showConfirmDialog(view, "Are you sure you want to log out?", "Log out", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            close(); // Close the current window
            wingsnepal.view.LoginPage loginView = new wingsnepal.view.LoginPage();
            new controller.LoginController(loginView).open(); // Open the login window
        }
    }
    
    // Utility methods
    private String formatTime(String time24h) {
        if (time24h == null || time24h.isEmpty()) {
            return "";
        }
        try {
            java.time.LocalTime time = java.time.LocalTime.parse(time24h);
            return time.format(java.time.format.DateTimeFormatter.ofPattern("hh:mm a"));
        } catch (java.time.format.DateTimeParseException e) {
            // Return original string if it's not in a parsable format
            return time24h;
        }
    }

    // Add these methods to handle edit/delete actions
    private void handleEditPassenger(int row) {
        if (row < 0) return;
        JTable table = view.getJTable3();
        
        // Add null checks to prevent NullPointerException
        Object passengerIdObj = table.getValueAt(row, 0);
        Object fullNameObj = table.getValueAt(row, 1);
        Object emailObj = table.getValueAt(row, 2);
        Object phoneObj = table.getValueAt(row, 3);
        Object seatClassObj = table.getValueAt(row, 4);
        Object seatNumberObj = table.getValueAt(row, 5);
        Object upcomingFlightObj = table.getValueAt(row, 6);
        Object bookingStatusObj = table.getValueAt(row, 7);
        
        // Handle null values safely
        if (passengerIdObj == null) {
            JOptionPane.showMessageDialog(view, "Invalid passenger data. Please refresh the table.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        int passengerId = Integer.parseInt(passengerIdObj.toString());
        String fullName = fullNameObj != null ? fullNameObj.toString() : "";
        String email = emailObj != null ? emailObj.toString() : "";
        String phone = phoneObj != null ? phoneObj.toString() : "";
        String seatClass = seatClassObj != null ? seatClassObj.toString() : "";
        String seatNumber = seatNumberObj != null ? seatNumberObj.toString() : "";
        String upcomingFlight = upcomingFlightObj != null ? upcomingFlightObj.toString() : "";
        String bookingStatus = bookingStatusObj != null ? bookingStatusObj.toString() : "";

        // Get account holder details to determine if this is a self-booking
        PassengerDao passengerDao = new PassengerDao();
        wingsnepal.model.PassengerModel existingPassenger = passengerDao.getPassengerById(passengerId);
        boolean isAccountHolderBooking = false;
        
        if (existingPassenger != null) {
            wingsnepal.dao.UserDao userDao = new wingsnepal.dao.UserDao();
            wingsnepal.model.UserDataModel accountHolder = userDao.findUserById(existingPassenger.getUserId());
            
            if (accountHolder != null) {
                isAccountHolderBooking = isPassengerSameAsAccountHolder(
                    fullName, email, accountHolder.getFullName(), accountHolder.getEmail()
                );
            }
        }

        EditPassengerDialog dialog = new EditPassengerDialog(
            (Frame) SwingUtilities.getWindowAncestor(view),
            fullName, phone, email, bookingStatus, seatClass, seatNumber, upcomingFlight,
            isAccountHolderBooking, null // No callback needed
        );
        dialog.setVisible(true);
        
        // After dialog closes, update if any values changed
        if (!fullName.equals(dialog.getFullName()) || 
            !phone.equals(dialog.getPhone()) || 
            !seatNumber.equals(dialog.getSeatNumber()) ||
            !upcomingFlight.equals(dialog.getFlightCode()) ||
            !bookingStatus.equals(dialog.getBookingStatus())) {
            if (existingPassenger != null) {
                // Update the editable fields
                existingPassenger.setFullName(dialog.getFullName());
                
                // Update passenger in database
                boolean passengerUpdated = passengerDao.updatePassenger(existingPassenger);
                
                // SMART LOGIC: Only update user account if passenger is the account holder
                wingsnepal.dao.UserDao userDao = new wingsnepal.dao.UserDao();
                wingsnepal.model.UserDataModel accountHolder = userDao.findUserById(existingPassenger.getUserId());
                
                if (accountHolder != null) {
                    // Use the already determined isAccountHolderBooking value
                    
                    if (isAccountHolderBooking) {
                        // This is a "booking for self" case - update both passenger and user account
                        System.out.println("ADMIN EDIT: Passenger is account holder - updating user account");
                        
                        if (!fullName.equals(dialog.getFullName())) {
                            userDao.updateUserFullName(existingPassenger.getUserId(), dialog.getFullName());
                        }
                        
                        if (!phone.equals(dialog.getPhone())) {
                            userDao.updateUserPhone(existingPassenger.getUserId(), dialog.getPhone());
                        }
                    } else {
                        // This is a "booking for someone else" case - DON'T touch user account
                        System.out.println("ADMIN EDIT: Passenger is NOT account holder - preserving user account");
                        System.out.println("Account Holder: " + accountHolder.getFullName() + " (" + accountHolder.getEmail() + ")");
                        System.out.println("Passenger: " + dialog.getFullName() + " (" + dialog.getEmail() + ")");
                    }
                }
                
                // Update booking status if changed
                if (!bookingStatus.equals(dialog.getBookingStatus())) {
                    wingsnepal.dao.ManageBookingDao bookingDao = new wingsnepal.dao.ManageBookingDao();
                    bookingDao.updateBookingStatus(existingPassenger.getUserId(), upcomingFlight, seatNumber, dialog.getBookingStatus());
                }
                
                if (passengerUpdated) {
                    JOptionPane.showMessageDialog(view, "Passenger updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    loadPassengersTable();
                    loadUsersTable(); // Refresh users table to show updated name
                } else {
                    JOptionPane.showMessageDialog(view, "Failed to update passenger!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    private void handleDeletePassenger(int row) {
        if (row < 0) return;
        JTable table = view.getJTable3();
        
        // Add null checks to prevent NullPointerException
        Object passengerIdObj = table.getValueAt(row, 0);
        Object emailObj = table.getValueAt(row, 2);
        
        if (passengerIdObj == null) {
            JOptionPane.showMessageDialog(view, "Invalid passenger data. Please refresh the table.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        int passengerId = Integer.parseInt(passengerIdObj.toString());
        String email = emailObj != null ? emailObj.toString() : "";
        
        int confirm = JOptionPane.showConfirmDialog(view, "Are you sure you want to delete this passenger and all their bookings?", "Delete Passenger", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            // 1. Delete all bookings for this passenger (and free seats)
            ManageBookingDao bookingDao = new ManageBookingDao();
            bookingDao.deleteAllBookingsForPassenger(passengerId, email);
            // 2. Delete the passenger
            PassengerDao passengerDao = new PassengerDao();
            passengerDao.deletePassenger(passengerId);
            // 3. Remove from table model
            ((DefaultTableModel) table.getModel()).removeRow(row);
            // 4. Refresh booking table (Edit Flight tab)
            loadPassengersTable(); // Refresh passenger table
            loadFlightsTable();    // Refresh booking/flight table (if needed)
            JOptionPane.showMessageDialog(view, "Passenger and all their bookings deleted.", "Delete", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void handleEditEmployee(int row) {
        int empId = (int) view.getEmployeeTable().getValueAt(row, 0);
        wingsnepal.view.EditEmployeeDialog dialog = new wingsnepal.view.EditEmployeeDialog(view, empId);
        dialog.setVisible(true);
        loadEmployeeTable();
    }

    public void handleDeleteEmployee(int row) {
        int empId = (int) view.getEmployeeTable().getValueAt(row, 0);
        String empName = (String) view.getEmployeeTable().getValueAt(row, 1);
        
        // Enhanced confirmation message
        String confirmMessage = "Are you sure you want to delete employee '" + empName + "'?\n\n" +
                               "This will permanently remove:\n" +
                               "• Employee account and profile\n" +
                               "• All flight bookings (seats will be released)\n" +
                               "• All passenger records\n" +
                               "• All reservation data\n\n" +
                               "This action cannot be undone!";
        
        int confirm = javax.swing.JOptionPane.showConfirmDialog(view, 
            confirmMessage, 
            "Confirm Employee Deletion", 
            javax.swing.JOptionPane.YES_NO_OPTION,
            javax.swing.JOptionPane.WARNING_MESSAGE);
            
        if (confirm == javax.swing.JOptionPane.YES_OPTION) {
            wingsnepal.dao.UserDao dao = new wingsnepal.dao.UserDao();
            boolean success = dao.deleteUser(empId);
            
            if (success) {
                // Enhanced success message
                String successMessage = "Employee '" + empName + "' and all related data deleted successfully!\n\n" +
                                      "All bookings cancelled and seats released back to inventory.";
                javax.swing.JOptionPane.showMessageDialog(view, 
                    successMessage, 
                    "Deletion Complete", 
                    javax.swing.JOptionPane.INFORMATION_MESSAGE);
                
                // Refresh all tables that might be affected by employee deletion
                loadEmployeeTable();     // Refresh employee table
                loadPassengersTable();   // Refresh passengers table (to remove deleted employee's passengers)
                loadFlightsTable();      // Refresh flights table (to show released seats)
                loadDashboardMetrics();  // Refresh dashboard metrics
                
            } else {
                javax.swing.JOptionPane.showMessageDialog(view, 
                    "Failed to delete employee '" + empName + "'.\n\nThe employee might be an admin account or there was a database error.", 
                    "Deletion Failed", 
                    javax.swing.JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Users Management
    public void loadUsersTable() {
        UserDao dao = new UserDao();
        List<UserDataModel> allUsers = dao.getAllUsers();
        
        // Filter to only include regular users (exclude employees)
        List<UserDataModel> regularUsers = new ArrayList<>();
        for (UserDataModel user : allUsers) {
            if (!"Employee".equals(user.getRole())) {
                regularUsers.add(user);
            }
        }

        DefaultTableModel model = (DefaultTableModel) view.getUsersTable().getModel();
        model.setRowCount(0); // Clear existing data

        for (UserDataModel user : regularUsers) {
            model.addRow(new Object[]{
                user.getUserId(),
                user.getFullName(),
                user.getEmail(),
                user.getPhone() != null ? user.getPhone() : "",
                user.getRole(),
                user.getStatus(),
                ""
            });
        }
        
        // Add custom renderer for Status column (index 5) 
        view.getUsersTable().getColumnModel().getColumn(5).setCellRenderer(new wingsnepal.view.StatusButtonRenderer());
        
        // Add custom renderer and editor for Actions column (index 6)
        view.getUsersTable().getColumnModel().getColumn(6).setCellRenderer(new wingsnepal.view.EmployeeActionButtonRenderer());
        view.getUsersTable().getColumnModel().getColumn(6).setCellEditor(
            new wingsnepal.view.EmployeeActionButtonEditor(view.getUsersTable(), new wingsnepal.view.EmployeeActionButtonEditor.EmployeeActionCallback() {
                @Override
                public void onEdit(int row) {
                    handleEditUser(row);
                }
                @Override
                public void onDelete(int row) {
                    handleDeleteUser(row);
                }
            })
        );
        
        // Set column widths for user table
        view.getUsersTable().getColumnModel().getColumn(6).setPreferredWidth(180); // Actions
        view.getUsersTable().getColumnModel().getColumn(6).setMinWidth(180);
        view.getUsersTable().getColumnModel().getColumn(6).setMaxWidth(200);
        
        view.getUsersTable().setRowHeight(32);
    }
    
    public void handleSearchUsers() {
        String keyword = view.getSearchUsersTextField().getText().trim();

        UserDao dao = new UserDao();
        List<UserDataModel> allFilteredList = dao.searchUsers(keyword);
        
        // Filter to only include regular users (exclude employees)
        List<UserDataModel> filteredList = new ArrayList<>();
        for (UserDataModel user : allFilteredList) {
            if (!"Employee".equals(user.getRole())) {
                filteredList.add(user);
            }
        }

        DefaultTableModel model = (DefaultTableModel) view.getUsersTable().getModel();
        model.setRowCount(0);

        for (UserDataModel user : filteredList) {
            model.addRow(new Object[]{
                user.getUserId(),
                user.getFullName(),
                user.getEmail(),
                user.getPhone() != null ? user.getPhone() : "",
                user.getRole(),
                user.getStatus(),
                ""
            });
        }
        
        // Reapply renderers after search
        view.getUsersTable().getColumnModel().getColumn(5).setCellRenderer(new wingsnepal.view.StatusButtonRenderer());
        view.getUsersTable().getColumnModel().getColumn(6).setCellRenderer(new wingsnepal.view.EmployeeActionButtonRenderer());
        view.getUsersTable().getColumnModel().getColumn(6).setCellEditor(
            new wingsnepal.view.EmployeeActionButtonEditor(view.getUsersTable(), new wingsnepal.view.EmployeeActionButtonEditor.EmployeeActionCallback() {
                @Override
                public void onEdit(int row) {
                    handleEditUser(row);
                }
                @Override
                public void onDelete(int row) {
                    handleDeleteUser(row);
                }
            })
        );
        
        // Set column widths for user table after search
        view.getUsersTable().getColumnModel().getColumn(6).setPreferredWidth(180); // Actions
        view.getUsersTable().getColumnModel().getColumn(6).setMinWidth(180);
        view.getUsersTable().getColumnModel().getColumn(6).setMaxWidth(200);
    }
    
    public void handleEditUser(int row) {
        int userId = (int) view.getUsersTable().getValueAt(row, 0);
        wingsnepal.view.EditUserDialog dialog = new wingsnepal.view.EditUserDialog(view, userId);
        dialog.setVisible(true);
        
        // Refresh both users and passengers tables after user edit
        // This ensures booking status changes from user inactivation are reflected
        loadUsersTable();
        refreshPassengersAfterUserStatusChange();
        loadDashboardMetrics(); // Also refresh metrics in case counts changed
    }

    public void handleDeleteUser(int row) {
        int userId = (int) view.getUsersTable().getValueAt(row, 0);
        String userName = (String) view.getUsersTable().getValueAt(row, 1);
        
        // Enhanced confirmation message
        String confirmMessage = "Are you sure you want to delete user '" + userName + "'?\n\n" +
                               "This will permanently remove:\n" +
                               "• User account and profile\n" +
                               "• All flight bookings (seats will be released)\n" +
                               "• All passenger records\n" +
                               "• All reservation data\n\n" +
                               "This action cannot be undone!";
        
        int confirm = javax.swing.JOptionPane.showConfirmDialog(view, 
            confirmMessage, 
            "Confirm User Deletion", 
            javax.swing.JOptionPane.YES_NO_OPTION,
            javax.swing.JOptionPane.WARNING_MESSAGE);
            
        if (confirm == javax.swing.JOptionPane.YES_OPTION) {
            wingsnepal.dao.UserDao dao = new wingsnepal.dao.UserDao();
            boolean success = dao.deleteUser(userId);
            
            if (success) {
                // Enhanced success message
                String successMessage = "User '" + userName + "' and all related data deleted successfully!\n\n" +
                                      "All bookings cancelled and seats released back to inventory.";
                javax.swing.JOptionPane.showMessageDialog(view, 
                    successMessage, 
                    "Deletion Complete", 
                    javax.swing.JOptionPane.INFORMATION_MESSAGE);
                
                // Refresh all tables that might be affected by user deletion
                loadUsersTable();        // Refresh users table
                loadPassengersTable();   // Refresh passengers table (to remove deleted user's passengers)
                loadFlightsTable();      // Refresh flights table (to show released seats)
                loadDashboardMetrics();  // Refresh dashboard metrics
                
            } else {
                javax.swing.JOptionPane.showMessageDialog(view, 
                    "Failed to delete user '" + userName + "'.\n\nThe user might be an admin account or there was a database error.", 
                    "Deletion Failed", 
                    javax.swing.JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void styleButton(JButton button, Color backgroundColor) {
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setBackground(backgroundColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }
    
    /**
     * Helper method to determine if passenger details match the account holder
     * This helps distinguish between "booking for self" vs "booking for someone else"
     */
    private boolean isPassengerSameAsAccountHolder(String passengerName, String passengerEmail, 
                                                  String accountHolderName, String accountHolderEmail) {
        // Normalize strings for comparison (handle null values and trim whitespace)
        String pName = (passengerName != null) ? passengerName.trim().toLowerCase() : "";
        String pEmail = (passengerEmail != null) ? passengerEmail.trim().toLowerCase() : "";
        String aName = (accountHolderName != null) ? accountHolderName.trim().toLowerCase() : "";
        String aEmail = (accountHolderEmail != null) ? accountHolderEmail.trim().toLowerCase() : "";
        
        // Consider them the same person if both name and email match
        boolean nameMatches = pName.equals(aName);
        boolean emailMatches = pEmail.equals(aEmail);
        
        return nameMatches && emailMatches;
    }
}

