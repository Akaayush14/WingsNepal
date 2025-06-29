package controller;

import wingsnepal.dao.PassengerDao;
import wingsnepal.model.PassengerModel;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Controller class for passenger management operations
 * 
 * @author WingsNepal Team
 */
public class PassengerController {
    
    private PassengerDao passengerDao;
    private JTable passengersTable;
    private DefaultTableModel tableModel;
    private JTextField searchField;
    private JComboBox<String> statusFilter;
    private JComboBox<String> seatClassFilter;
    
    public PassengerController() {
        this.passengerDao = new PassengerDao();
    }
    
    /**
     * Initialize the controller with UI components
     */
    public void initialize(JTable passengersTable, DefaultTableModel tableModel, 
                          JTextField searchField, JComboBox<String> statusFilter, 
                          JComboBox<String> seatClassFilter) {
        this.passengersTable = passengersTable;
        this.tableModel = tableModel;
        this.searchField = searchField;
        this.statusFilter = statusFilter;
        this.seatClassFilter = seatClassFilter;
        
        loadPassengersTable();
    }
    
    /**
     * Load all passengers into the table
     */
    public void loadPassengersTable() {
        try {
            List<PassengerModel> passengers = passengerDao.getAllPassengers();
            updateTableModel(passengers);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, 
                "Error loading passengers: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Search passengers by keyword
     */
    public void searchPassengers() {
        String keyword = searchField.getText().trim();
        if (keyword.isEmpty()) {
            loadPassengersTable();
            return;
        }
        
        try {
            List<PassengerModel> passengers = passengerDao.searchPassengers(keyword);
            updateTableModel(passengers);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, 
                "Error searching passengers: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Filter passengers by status
     */
    public void filterByStatus() {
        String selectedStatus = (String) statusFilter.getSelectedItem();
        if (selectedStatus == null || selectedStatus.equals("All")) {
            loadPassengersTable();
            return;
        }
        
        try {
            List<PassengerModel> passengers = passengerDao.getPassengersByStatus(selectedStatus);
            updateTableModel(passengers);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, 
                "Error filtering passengers: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Filter passengers by seat class
     */
    public void filterBySeatClass() {
        String selectedSeatClass = (String) seatClassFilter.getSelectedItem();
        if (selectedSeatClass == null || selectedSeatClass.equals("All")) {
            loadPassengersTable();
            return;
        }
        
        try {
            List<PassengerModel> passengers = passengerDao.getPassengersBySeatClass(selectedSeatClass);
            updateTableModel(passengers);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, 
                "Error filtering passengers: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Add a new passenger
     */
    public boolean addPassenger(PassengerModel passenger) {
        try {
            boolean success = passengerDao.addPassenger(passenger);
            if (success) {
                JOptionPane.showMessageDialog(null, 
                    "Passenger added successfully!", 
                    "Success", JOptionPane.INFORMATION_MESSAGE);
                loadPassengersTable();
                return true;
            } else {
                JOptionPane.showMessageDialog(null, 
                    "Failed to add passenger", 
                    "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, 
                "Error adding passenger: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    /**
     * Update an existing passenger
     */
    public boolean updatePassenger(PassengerModel passenger) {
        try {
            boolean success = passengerDao.updatePassenger(passenger);
            if (success) {
                JOptionPane.showMessageDialog(null, 
                    "Passenger updated successfully!", 
                    "Success", JOptionPane.INFORMATION_MESSAGE);
                loadPassengersTable();
                return true;
            } else {
                JOptionPane.showMessageDialog(null, 
                    "Failed to update passenger", 
                    "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, 
                "Error updating passenger: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    /**
     * Delete a passenger
     */
    public boolean deletePassenger(int passengerId) {
        int confirm = JOptionPane.showConfirmDialog(null, 
            "Are you sure you want to delete this passenger?", 
            "Confirm Delete", JOptionPane.YES_NO_OPTION);
            
        if (confirm != JOptionPane.YES_OPTION) {
            return false;
        }
        
        try {
            boolean success = passengerDao.deletePassenger(passengerId);
            if (success) {
                JOptionPane.showMessageDialog(null, 
                    "Passenger deleted successfully!", 
                    "Success", JOptionPane.INFORMATION_MESSAGE);
                loadPassengersTable();
                return true;
            } else {
                JOptionPane.showMessageDialog(null, 
                    "Failed to delete passenger", 
                    "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, 
                "Error deleting passenger: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    /**
     * Get passenger by ID
     */
    public PassengerModel getPassengerById(int passengerId) {
        try {
            return passengerDao.getPassengerById(passengerId);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, 
                "Error getting passenger: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }
    
    /**
     * Get total passenger count
     */
    public int getTotalPassengersCount() {
        try {
            return passengerDao.getTotalPassengersCount();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
    
    /**
     * Get passengers with upcoming flights
     */
    public List<PassengerModel> getPassengersWithUpcomingFlights() {
        try {
            return passengerDao.getPassengersWithUpcomingFlights();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, 
                "Error getting passengers with upcoming flights: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }
    
    /**
     * Update the table model with passenger data
     */
    private void updateTableModel(List<PassengerModel> passengers) {
        tableModel.setRowCount(0);
        
        for (PassengerModel passenger : passengers) {
            Object[] row = {
                passenger.getPassengerId(),
                passenger.getFullName(),
                passenger.getStatus(),
                passenger.getUpcomingFlight(),
                passenger.getEmail(),
                passenger.getSeatClass()
            };
            tableModel.addRow(row);
        }
    }
    
    /**
     * Get passenger from selected table row
     */
    public PassengerModel getSelectedPassenger() {
        int selectedRow = passengersTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, 
                "Please select a passenger first", 
                "No Selection", JOptionPane.WARNING_MESSAGE);
            return null;
        }
        
        int passengerId = (Integer) tableModel.getValueAt(selectedRow, 0);
        return getPassengerById(passengerId);
    }
    
    /**
     * Create action listener for search button
     */
    public ActionListener createSearchActionListener() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchPassengers();
            }
        };
    }
    
    /**
     * Create action listener for status filter
     */
    public ActionListener createStatusFilterActionListener() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                filterByStatus();
            }
        };
    }
    
    /**
     * Create action listener for seat class filter
     */
    public ActionListener createSeatClassFilterActionListener() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                filterBySeatClass();
            }
        };
    }
    
    /**
     * Create action listener for refresh button
     */
    public ActionListener createRefreshActionListener() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadPassengersTable();
            }
        };
    }
} 