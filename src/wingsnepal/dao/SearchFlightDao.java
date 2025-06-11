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
        String query = "SELECT flight_id, flight_name, from_city, to_city, date, time, price, duration FROM flights";
        Connection conn = db.openConnection();
        
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, from);
            stmt.setString(2, to);
            stmt.setString(3, date);
            
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                SearchFlight flight = new SearchFlight(
                    rs.getInt("flight_id"),
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
    String query = "SELECT flight_id, flight_name, from_city, to_city, date, time, price, duration FROM flights";
    Connection conn = db.openConnection();

    try {
        PreparedStatement stmt = conn.prepareStatement(query);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            SearchFlight flight = new SearchFlight(
                rs.getInt("flight_id"),
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
}
