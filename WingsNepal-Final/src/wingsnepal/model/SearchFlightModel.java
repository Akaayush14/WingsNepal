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
public class SearchFlightModel {
    private String flightCode;
    private String flightName;
    private String fromCity;
    private String toCity;
    private String time;
    private int economyPrice;
    private int businessPrice;
    private int firstClassPrice;
    private String duration;
    private String date;
    
    // Constructor for listing flights (only needs economy price)
    public SearchFlightModel(String flightCode, String flightName, String fromCity, String toCity, String time, int economyPrice, String duration, String date) {
        this.flightCode = flightCode;
        this.flightName = flightName;
        this.fromCity = fromCity;
        this.toCity = toCity;
        this.time = time;
        this.economyPrice = economyPrice;
        this.duration = duration;
        this.date = date;
    }

    // Constructor for editing (needs all prices)
    public SearchFlightModel(String flightCode, String flightName, String fromCity, String toCity, String time, int economyPrice, int businessPrice, int firstClassPrice, String duration, String date) {
        this.flightCode = flightCode;
        this.flightName = flightName;
        this.fromCity = fromCity;
        this.toCity = toCity;
        this.time = time;
        this.economyPrice = economyPrice;
        this.businessPrice = businessPrice;
        this.firstClassPrice = firstClassPrice;
        this.duration = duration;
        this.date = date;
    }
    
    // Getters: This helps to safely access data in the objcet.
    public String getFlightCode() { return flightCode; } 
    public String getFlightName() { return flightName; }
    public String getFromCity() { return fromCity; }
    public String getToCity() { return toCity; }
    public String getTime() { return time; }
    public int getPrice() { return economyPrice; } // For backward compatibility with table view
    public int getEconomyPrice() { return economyPrice; }
    public int getBusinessPrice() { return businessPrice; }
    public int getFirstClassPrice() { return firstClassPrice; }
    public String getDuration() { return duration; }
    public String getDate() { return date; }
}
