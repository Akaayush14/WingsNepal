/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package wingsnepal.model;

import java.sql.Date;

/**
 *
 * @author Aayush Kharel
 */



public class ManageBookingModel{
    private int bookingId;
    private int userId;
    private int flightId;
    private String flightCode;
    private String flightName;
    private String className;
    private String seatNo;
    private int tickets;
    private Date travelDate;
    private String paymentMethod;
    private String fullName;
    private String email;
    private int durationMinutes;
    private String fromCity;
    private String toCity;
    private String bookingStatus;

    public ManageBookingModel(int bookingId, int userId, int flightId, String flightCode, String className, String seatNo, int tickets, Date travelDate, String paymentMethod) {
        this.bookingId = bookingId;
        this.userId = userId;
        this.flightId = flightId;
        this.flightCode = flightCode;
        this.className = className;
        this.seatNo = seatNo;
        this.tickets = tickets;
        this.travelDate = travelDate;
        this.paymentMethod = paymentMethod;
    }
    
    public ManageBookingModel(int bookingId, int userId, int flightId, String flightCode, String flightName, String className, String seatNo, int tickets, Date travelDate, String paymentMethod, String fullName, String email) {
        this.bookingId = bookingId;
        this.userId = userId;
        this.flightId = flightId;
        this.flightCode = flightCode;
        this.flightName = flightName;
        this.className = className;
        this.seatNo = seatNo;
        this.tickets = tickets;
        this.travelDate = travelDate;
        this.paymentMethod = paymentMethod;
        this.fullName = fullName;
        this.email = email;
    }

    // Getters and Setters
    public int getBookingId() { return bookingId; }
    public void setBookingId(int bookingId) { this.bookingId = bookingId; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public int getFlightId() { return flightId; }
    public void setFlightId(int flightId) { this.flightId = flightId; }

    public String getFlightCode() { return flightCode; }
    public void setFlightCode(String flightCode) { this.flightCode = flightCode; }

    public String getFlightName() { return flightName; }
    public void setFlightName(String flightName) { this.flightName = flightName; }

    public String getClassName() { return className; }
    public void setClassName(String seatClass) { this.className = seatClass; }

    public String getSeatClass() { return className; }

    public String getSeatNo() { return seatNo; }
    public void setSeatNo(String seatNo) { this.seatNo = seatNo; }

    public int getTickets() { return tickets; }
    public void setTickets(int tickets) { this.tickets = tickets; }

    public Date getTravelDate() { return travelDate; }
    public void setTravelDate(Date travelDate) { this.travelDate = travelDate; }

    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public int getDurationMinutes() {
        return durationMinutes;
    }

    public void setDurationMinutes(int durationMinutes) {
        this.durationMinutes = durationMinutes;
    }

    public String getFromCity() { return fromCity; }
    public void setFromCity(String fromCity) { this.fromCity = fromCity; }
    public String getToCity() { return toCity; }
    public void setToCity(String toCity) { this.toCity = toCity; }
    public String getBookingStatus() { return bookingStatus; }
    public void setBookingStatus(String bookingStatus) { this.bookingStatus = bookingStatus; }
    
    // Convenience methods for walk-in customer bookings
    public String getPassengerName() { return fullName; }
    public void setPassengerName(String passengerName) { this.fullName = passengerName; }
    
    public String getPassengerEmail() { return email; }
    public void setPassengerEmail(String passengerEmail) { this.email = passengerEmail; }
}
