package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import wingsnepal.model.UserDataModel;
import wingsnepal.model.SearchFlightModel;
import wingsnepal.model.BookingFlightModel;
import wingsnepal.model.StripePayment;
import wingsnepal.dao.SearchFlightDao;
import wingsnepal.dao.BookingFlightDao;
import wingsnepal.dao.SeatClassDao;
import wingsnepal.dao.ManageBookingDao;
import wingsnepal.view.UserPortal;
import wingsnepal.view.LoginPage;
import wingsnepal.model.ManageBookingModel;
import wingsnepal.dao.BookingPassengerDao;

public class UserPortalController {
    private UserPortal view;
    private UserDataModel user;
    private SearchFlightDao searchFlightDao;
    private BookingFlightDao bookingFlightDao;
    private SeatClassDao seatClassDao;
    private ManageBookingDao manageBookingDao;

    public UserPortalController(UserPortal view, UserDataModel user) {
        this.view = view;
        this.user = user;
        this.searchFlightDao = new SearchFlightDao();
        this.bookingFlightDao = new BookingFlightDao();
        this.seatClassDao = new SeatClassDao();
        this.manageBookingDao = new ManageBookingDao();
        setupEventListeners();
    }

    public void open() {
        this.view.setVisible(true);
    }

    public void close() {
        this.view.dispose();
    }

    private void setupEventListeners() {
        view.getSearchFlightButton().addActionListener(e -> handleSearchFlights());
        view.getShowAllButton().addActionListener(e -> showAllFlights());
        view.getBookNowButton().addActionListener(e -> handleBookFlight());
        view.getClearButton().addActionListener(e -> handleClearForm());
        view.getShowMyBookingButton().addActionListener(e -> {
            int currentTab = view.getMainTabbedPane().getSelectedIndex();
            if (currentTab == 3) { // Edit Flight tab
                // Show all bookings for editing
                if (view.getManageBookingController() != null) {
                    view.getManageBookingController().showAllBookingsForEdit();
                }
            } else { // Manage Booking tab
                // Show user's own bookings
                showUserBookings(user.getUserId());
            }
        });
        view.getLogOutButton().addActionListener(e -> handleLogout());

        view.getSeatComboBox().addActionListener(e -> {
            updateSeatClassPrice();
            updateAvailableSeats();
        });

        // Add real-time search functionality
        setupLiveSearch();

        view.getMainTabbedPane().addChangeListener(e -> {
            if (view.getMainTabbedPane().getSelectedIndex() == 3) {
                // Don't auto-load - let user manually click "Show Booking" button
                // This preserves the original intended workflow
            }
            if (view.getMainTabbedPane().getSelectedIndex() == 4) {
                // Auto-load user's bookings when switching to Manage Booking tab
                showUserBookings(user.getUserId());
            }
            if (view.getMainTabbedPane().getSelectedIndex() == 2) {
                refreshAvailableSeats();
            }
        });
    }

    // Add real-time search functionality
    private void setupLiveSearch() {
        // Add key listener to From field
        view.getFromTextField().addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent evt) {
                performLiveSearch();
            }
        });

        // Add key listener to To field
        view.getToTextField().addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent evt) {
                performLiveSearch();
            }
        });
    }

    // Perform live search based on current input
    private void performLiveSearch() {
        String from = view.getFromTextField().getText().trim();
        String to = view.getToTextField().getText().trim();
        
        // Don't search if both fields are empty
        if (from.isEmpty() && to.isEmpty()) {
            return;
        }
        
        // Don't search if fields contain placeholder text
        if (from.equals("Departure city/airport") || to.equals("Destination city/airport")) {
            return;
        }
        
        try {
            List<SearchFlightModel> flights;
            
            if (!from.isEmpty() && !to.isEmpty()) {
                // Search by both from and to (without date for live search)
                flights = searchFlightDao.searchFlights(from, to, "");
            } else if (!from.isEmpty()) {
                // Search by departure city only
                flights = searchFlightDao.searchFlightsByFrom(from);
            } else if (!to.isEmpty()) {
                // Search by destination city only
                flights = searchFlightDao.searchFlightsByTo(to);
            } else {
                // Fallback to all flights
                flights = searchFlightDao.getAllFlights();
            }
            
            populateFlightTable(flights);
        } catch (Exception e) {
            System.err.println("Error in live search: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void showAllFlights() {
        List<SearchFlightModel> flights = searchFlightDao.getAllFlights();
        populateFlightTable(flights);
    }

    private void handleSearchFlights() {
        String from = view.getFromTextField().getText().trim();
        String to = view.getToTextField().getText().trim();
        String date = getTravelDateString();

        if (from.isEmpty() || to.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Please enter departure and destination cities.");
            return;
        }

        List<SearchFlightModel> flights = searchFlightDao.searchFlights(from, to, date);
        populateFlightTable(flights);
    }

    private void populateFlightTable(List<SearchFlightModel> flights) {
        DefaultTableModel model = new DefaultTableModel(
            new Object[]{"Flight Code", "Flight Name", "From", "To", "Date", "Time", "Price", "Duration", "Actions"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 8;
            }
        };

        view.getJTable1().setModel(model);

        for (SearchFlightModel flight : flights) {
            model.addRow(new Object[]{
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

        view.getJTable1().getColumn("Actions").setCellRenderer(new wingsnepal.view.BookButtonRenderer());
        view.getJTable1().getColumn("Actions").setCellEditor(new wingsnepal.view.BookButtonEditor(view, view.getJTable1()));
    }

    private void handleBookFlight() {
        if (!validateBookingForm()) return;

        String flightCode = view.getFlightCodeTextField().getText();
        String fullName = view.getFullNameTextField().getText();
        String email = view.getEmailTextField().getText();
        String phone = view.getPhoneTextField().getText();
        String seatClass = (String) view.getSeatComboBox().getSelectedItem();
        String seatNo = (String) view.getSeatNoComboBox().getSelectedItem();
        String paymentMethod = (String) view.getPaymentComboBox().getSelectedItem();
        Date travelDate = getTravelDate();

        if (travelDate == null) {
            JOptionPane.showMessageDialog(view, "Please select a valid travel date.");
            return;
        }

        int flightId = getFlightId(flightCode);
        int seatId = getSeatId(seatClass);

        processBooking(user.getUserId(), flightId, seatId, fullName, email, phone, seatClass, seatNo, 1, travelDate, paymentMethod);
    }

    private boolean validateBookingForm() {
        return !view.getFlightCodeTextField().getText().trim().isEmpty() &&
               !view.getFullNameTextField().getText().trim().isEmpty() &&
               !view.getEmailTextField().getText().trim().isEmpty() &&
               !view.getPhoneTextField().getText().trim().isEmpty() &&
               view.getSeatComboBox().getSelectedItem() != null &&
               view.getSeatNoComboBox().getSelectedItem() != null;
    }

    private void processBooking(int userId, int flightId, int seatId, String fullName,
                                String email, String phone, String seatClass, String seatNo, int tickets,
                                Date travelDate, String paymentMethod) {

        String flightCode = searchFlightDao.getFlightCodeById(flightId);

        int price;
        try {
            price = Integer.parseInt(view.getPriceTextField().getText().replaceAll("[^0-9]", ""));
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(view, "Invalid price format.");
            return;
        }

        if ("Stripe".equals(paymentMethod)) {
            try {
                StripePayment stripe = new StripePayment();
                String sessionUrl = stripe.createCheckoutSession(price, fullName, email, flightCode, seatClass, seatNo);

                if (sessionUrl != null) {
                    if (java.awt.Desktop.isDesktopSupported()) {
                        java.awt.Desktop.getDesktop().browse(new java.net.URI(sessionUrl));
                    }

                    JOptionPane.showMessageDialog(view, "Complete payment in your browser and return.");

                    if (stripe.checkPaymentStatus(sessionUrl)) {
                        saveBooking(userId, flightId, seatId, fullName, email, phone, seatClass, seatNo, tickets, travelDate, "Stripe", flightCode);
                    } else {
                        JOptionPane.showMessageDialog(view, "Payment failed or not completed.");
                    }
                } else {
                    JOptionPane.showMessageDialog(view, "Stripe session creation failed.");
                }

            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(view, "Stripe error: " + e.getMessage());
            }

        } else {
            saveBooking(userId, flightId, seatId, fullName, email, phone, seatClass, seatNo, tickets, travelDate, "Cash", flightCode);
        }
    }

    private void saveBooking(int userId, int flightId, int seatId, String fullName,
                             String email, String phone, String seatClass, String seatNo, int tickets,
                             Date travelDate, String paymentMethod, String flightCode) {
        System.out.println("[DEBUG] saveBooking called with: userId=" + userId + ", flightId=" + flightId + ", seatId=" + seatId + ", fullName=" + fullName + ", email=" + email + ", seatClass=" + seatClass + ", seatNo=" + seatNo + ", tickets=" + tickets + ", travelDate=" + travelDate + ", paymentMethod=" + paymentMethod + ", flightCode=" + flightCode);

        BookingFlightModel booking = new BookingFlightModel(userId, flightId, seatId, fullName, email, seatClass, seatNo, tickets, travelDate, paymentMethod, flightCode);
        booking.setBooked(true);

        if (bookingFlightDao.saveBooking(booking)) {
            seatClassDao.markSeatAsBooked(flightId, seatClass, seatNo);
            BookingPassengerDao bookingPassengerDao = new BookingPassengerDao();
            bookingPassengerDao.syncBookingToPassengerWithPhone(booking, phone);
            String flightName = searchFlightDao.getFlightNameById(flightId);

            // Generate ticket after successful booking
            try {
                controller.TicketGenerator ticketGenerator = new controller.TicketGenerator();
                ticketGenerator.generateTicket(userId, flightCode, flightName, seatClass, seatNo, 
                                             fullName, email, travelDate, paymentMethod);
                
                // Success - show confirmation message with ticket info
                JOptionPane.showMessageDialog(view, 
                    "Booking successful!\nBooking ID: " + booking.getBookingId() + 
                    "\nTicket has been generated and will open automatically.", 
                    "Booking Confirmed", 
                    JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception e) {
                e.printStackTrace();
                // Still show success message even if ticket generation fails
                JOptionPane.showMessageDialog(view, 
                    "Booking successful!\nBooking ID: " + booking.getBookingId() + 
                    "\nNote: Ticket generation failed. Please contact support.", 
                    "Booking Confirmed", 
                    JOptionPane.WARNING_MESSAGE);
            }
            
            // Smart refresh: Only refresh Edit Flight tab if it already has data (Show Booking was clicked)
            javax.swing.table.DefaultTableModel tableModel = (javax.swing.table.DefaultTableModel) view.getJTable2().getModel();
            if (tableModel.getRowCount() > 0 && view.getManageBookingController() != null) {
                view.getManageBookingController().showAllBookingsForEdit();
            }
            
            // Clear the form for next booking
            handleClearForm();
        } else {
            JOptionPane.showMessageDialog(view, "Booking failed.");
        }
    }

    public void showUserBookings(int userId) {
        // Use the new method that excludes walk-in bookings made by employees
        List<ManageBookingModel> bookings = manageBookingDao.getRegularUserBookingsByUserId(userId);
        DefaultTableModel model = new DefaultTableModel(
            new Object[]{"Booking ID", "Flight", "From", "To", "Date", "Seat", "Class", "Status", "Actions"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 8;
            }
        };

        view.getJTable2().setModel(model);

        for (ManageBookingModel booking : bookings) {
            // Use actual booking status or default to "Confirmed" if null/empty
            String bookingStatus = booking.getBookingStatus();
            if (bookingStatus == null || bookingStatus.trim().isEmpty()) {
                bookingStatus = "Confirmed";
            }
            
            model.addRow(new Object[]{
                booking.getBookingId(),
                booking.getFlightCode(),
                getFlightFrom(booking.getFlightId()),
                getFlightTo(booking.getFlightId()),
                booking.getTravelDate(),
                booking.getSeatNo(),
                booking.getClassName(),
                bookingStatus,
                ""
            });
        }
        
        // Apply BookingStatusCellRenderer to Status column (index 7)
        view.getJTable2().getColumnModel().getColumn(7).setCellRenderer(new wingsnepal.view.BookingStatusCellRenderer());
        
        System.out.println("✅ USER PORTAL: Loaded " + bookings.size() + " regular user bookings (walk-in bookings excluded)");
    }

    private void updateSeatClassPrice() {
        String seatClass = (String) view.getSeatComboBox().getSelectedItem();
        String flightCode = view.getFlightCodeTextField().getText().trim();

        if (seatClass != null && !flightCode.isEmpty()) {
            int flightId = seatClassDao.getFlightIdByCode(flightCode);
            int price = seatClassDao.getPriceByFlightAndClass(flightId, seatClass);
            view.getPriceTextField().setText("NPR " + price);
        }
    }

    private void updateAvailableSeats() {
        String seatClass = (String) view.getSeatComboBox().getSelectedItem();
        String flightCode = view.getFlightCodeTextField().getText().trim();

        if (seatClass != null && !flightCode.isEmpty()) {
            int flightId = seatClassDao.getFlightIdByCode(flightCode);
            List<String> availableSeats = seatClassDao.getAvailableSeatsByClass(flightId, seatClass);
            view.getSeatNoComboBox().removeAllItems();
            for (String seat : availableSeats) {
                view.getSeatNoComboBox().addItem(seat);
            }
        }
    }

    private void handleClearForm() {
        view.getFlightCodeTextField().setText("");
        view.getFlightNameTextField().setText("");
        view.getFullNameTextField().setText("");
        view.getEmailTextField().setText("");
        view.getPhoneTextField().setText("");
        view.getPriceTextField().setText("");
        view.getSeatComboBox().setSelectedIndex(0);
        view.getSeatNoComboBox().removeAllItems();
        view.getPaymentComboBox().setSelectedIndex(0);
        resetTravelDate();
        
        // Reset field states to default (editable)
        view.getFullNameTextField().setEditable(true);
        view.getEmailTextField().setEditable(true);
        view.getPhoneTextField().setEditable(true);
        view.getFullNameTextField().setBackground(java.awt.Color.WHITE);
        view.getEmailTextField().setBackground(java.awt.Color.WHITE);
        view.getPhoneTextField().setBackground(java.awt.Color.WHITE);
        view.getFlightCodeTextField().setEditable(true);
        view.getFlightNameTextField().setEditable(true);
        view.getTravelYearChooser().setEnabled(true);
        view.getTravelMonthChooser().setEnabled(true);
        view.getTravelDaySpinnerField().setEnabled(true);
    }

    private void handleLogout() {
        int confirm = javax.swing.JOptionPane.showConfirmDialog(view, "Are you sure you want to log out?", "Log out", javax.swing.JOptionPane.YES_NO_OPTION);
        if (confirm == javax.swing.JOptionPane.YES_OPTION) {
            close();
            wingsnepal.view.LoginPage loginView = new wingsnepal.view.LoginPage();
            new LoginController(loginView).open();
        }
    }

    private void refreshAvailableSeats() {
        updateAvailableSeats();
    }

    // Placeholder methods
    private String getTravelDateString() {
        return "";
    }

    private Date getTravelDate() {
        return new Date(System.currentTimeMillis());
    }

    private void resetTravelDate() {
    }

    private int getFlightId(String flightCode) {
        return seatClassDao.getFlightIdByCode(flightCode);
    }

    private int getSeatId(String seatClass) {
        return 1;
    }

    private String getFlightFrom(int flightId) {
        return "FromCity";
    }

    private String getFlightTo(int flightId) {
        return "ToCity";
    }

    private String formatTime(String time24h) {
        try {
            java.time.LocalTime time = java.time.LocalTime.parse(time24h);
            return time.format(java.time.format.DateTimeFormatter.ofPattern("hh:mm a"));
        } catch (Exception e) {
            return time24h;
        }
    }
}
