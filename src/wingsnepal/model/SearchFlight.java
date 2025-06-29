/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package wingsnepal.model;

/**
 *
 * @author Aayush Kharel
 */
// This class is for storing flight searching data such as:
// flight name, departure time, ticket price, flight duration
public class SearchFlight {
    private String flightCode;
    private String flightName;
    private String time;
    private int price;
    private String duration;
    private String date;
    
    // Constructor: Special method that runs automatically when we create an object of the class.
    // In WingsNepal: It allows to fill all flight details when making a SearchFlight object.
    public SearchFlight(String flightCode, String flightName, String time, int price, String duration, String date) {
        this.flightCode = flightCode;
        this.flightName = flightName;
        this.time = time;
        this.price = price;
        this.duration = duration;
        this.date = date;
    }
    
    // Getters: This helps to safely access data in the objcet.
    public String getFlightCode() { return flightCode; } 
    public String getFlightName() { return flightName; }
    public String getTime() { return time; }
    public int getPrice() { return price; }
    public String getDuration() { return duration; }
    public String getDate() { return date; }
}
