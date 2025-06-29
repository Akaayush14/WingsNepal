package wingsnepal.dao;

import database.MySqlConnection;
import wingsnepal.model.SearchFlightModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FlightAssignmentDao {

    public List<SearchFlightModel> getAssignedFlightsByEmployeeId(int employeeId) {
        List<SearchFlightModel> assignedFlights = new ArrayList<>();
        
        // First, try to get assigned flights
        String assignedSql = "SELECT f.flight_code, f.flight_name, f.from_city, f.to_city, f.flight_time, f.flight_date, f.duration_minutes, " +
                     "MAX(CASE WHEN sc.class_name = 'Economy' THEN sc.price END) as economy_price, " +
                     "fa.assigned_role " +
                     "FROM flights f " +
                     "JOIN flight_assignments fa ON f.flight_id = fa.flight_id " +
                     "JOIN seat_classes sc ON f.flight_id = sc.flight_id " +
                     "WHERE fa.user_id = ? " +
                     "GROUP BY f.flight_id, f.flight_code, f.flight_name, f.from_city, f.to_city, f.flight_time, f.flight_date, f.duration_minutes, fa.assigned_role";

        System.out.println("Executing assigned flights query for employee ID: " + employeeId);

        try (Connection conn = new MySqlConnection().openConnection();
             PreparedStatement stmt = conn.prepareStatement(assignedSql)) {
            
            stmt.setInt(1, employeeId);
            ResultSet rs = stmt.executeQuery();

            int count = 0;
            while (rs.next()) {
                count++;
                int durationMinutes = rs.getInt("duration_minutes");
                String durationStr = (durationMinutes >= 60)
                    ? (durationMinutes / 60) + "h " + (durationMinutes % 60) + "m"
                    : durationMinutes + " min";
                    
                SearchFlightModel flight = new SearchFlightModel(
                    rs.getString("flight_code"),
                    rs.getString("flight_name"),
                    rs.getString("from_city"),
                    rs.getString("to_city"),
                    rs.getString("flight_time"),
                    rs.getInt("economy_price"),
                    durationStr,
                    rs.getString("flight_date")
                );
                assignedFlights.add(flight);
                System.out.println("Found assigned flight: " + flight.getFlightCode() + " - " + flight.getFlightName());
            }
            
            System.out.println("Total assigned flights found: " + count);
            
            // If no assigned flights found, show all flights as a fallback
            if (count == 0) {
                System.out.println("No assigned flights found. Showing all available flights...");
                
                String allFlightsSql = "SELECT f.flight_code, f.flight_name, f.from_city, f.to_city, f.flight_time, f.flight_date, f.duration_minutes, " +
                                      "MAX(CASE WHEN sc.class_name = 'Economy' THEN sc.price END) as economy_price " +
                                      "FROM flights f " +
                                      "JOIN seat_classes sc ON f.flight_id = sc.flight_id " +
                                      "GROUP BY f.flight_id, f.flight_code, f.flight_name, f.from_city, f.to_city, f.flight_time, f.flight_date, f.duration_minutes";
                
                try (PreparedStatement allFlightsStmt = conn.prepareStatement(allFlightsSql)) {
                    ResultSet allFlightsRs = allFlightsStmt.executeQuery();
                    
                    while (allFlightsRs.next()) {
                        int durationMinutes = allFlightsRs.getInt("duration_minutes");
                        String durationStr = (durationMinutes >= 60)
                            ? (durationMinutes / 60) + "h " + (durationMinutes % 60) + "m"
                            : durationMinutes + " min";
                            
                        SearchFlightModel flight = new SearchFlightModel(
                            allFlightsRs.getString("flight_code"),
                            allFlightsRs.getString("flight_name"),
                            allFlightsRs.getString("from_city"),
                            allFlightsRs.getString("to_city"),
                            allFlightsRs.getString("flight_time"),
                            allFlightsRs.getInt("economy_price"),
                            durationStr,
                            allFlightsRs.getString("flight_date")
                        );
                        assignedFlights.add(flight);
                        System.out.println("Found available flight: " + flight.getFlightCode() + " - " + flight.getFlightName());
                    }
                }
                
                System.out.println("Total available flights shown: " + assignedFlights.size());
            }
            
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
            e.printStackTrace();
        }
        return assignedFlights;
    }
} 