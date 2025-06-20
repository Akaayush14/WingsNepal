/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package wingsnepal.dao;

import wingsnepal.database.DbConnection;
import wingsnepal.database.MySqlConnection;
import wingsnepal.model.SearchFlight;

import java.sql.*;
import java.util.ArrayList;
import java.util.*;

/**
 * SearchFlightDao uses DbConnection interface and MySqlConnection implementation
 * to search for available flights from the database.
 */
public class SearchFlightDao {
    
    DbConnection db = new MySqlConnection();

    public List<SearchFlight> searchFlights(String from, String to, String date) {
        List<SearchFlight> flightList = new ArrayList<>();
        String query = "SELECT flight_code, flight_name, from_city, to_city, date, time, price, duration " + "FROM flights WHERE from_city = ? AND to_city = ? AND date = ?";

        Connection conn = db.openConnection();
        
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, from);
            stmt.setString(2, to);
            stmt.setString(3, date);
            
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                SearchFlight flight = new SearchFlight(
                    rs.getString("flight_code"),
                    rs.getString("flight_name"),
                    rs.getString("time"),
                    rs.getInt("price"),
                    rs.getString("duration"),
                    rs.getString("date")
                );
                flightList.add(flight);
            }
        } catch (SQLException e) {
            System.out.println("Error while searching flights: " + e.getMessage());
        } finally {
            db.closeConnection(conn);
        }
        
        return flightList;
    }

    public List<SearchFlight> getAllFlights() {
        List<SearchFlight> flightList = new ArrayList<>();
        String query = "SELECT flight_code, flight_name, from_city, to_city, date, time, price, duration FROM flights";  

        Connection conn = db.openConnection();

        try {
            PreparedStatement stmt = conn.prepareStatement(query);  // No parameters to set
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                SearchFlight flight = new SearchFlight(
                    rs.getString("flight_code"),
                    rs.getString("flight_name"),
                    rs.getString("time"),
                    rs.getInt("price"),
                    rs.getString("duration"),
                    rs.getString("date")
                );
                flightList.add(flight);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching all flights: " + e.getMessage());
        } finally {
            db.closeConnection(conn);
        }

        return flightList;
    }
    
        // Method to get the flight code by flight ID
        public String getFlightCodeById(int flightId) {
            String flightCode = null;
            String query = "SELECT flight_code FROM flights WHERE flight_id = ?";
            Connection conn = db.openConnection();
            
            try (
                PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setInt(1, flightId);  // Set flightId parameter
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    flightCode = rs.getString("flight_code");  // Get flight code
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return flightCode;
        }

        // Method to get the flight name by flight ID
        public String getFlightNameById(int flightId) {
            String flightName = null;
            String query = "SELECT flight_name FROM flights WHERE flight_id = ?";
            Connection conn = db.openConnection();
            
            try (
                 PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setInt(1, flightId);  // Set flightId parameter
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    flightName = rs.getString("flight_name");  // Get flight name
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return flightName;
        }
    }


