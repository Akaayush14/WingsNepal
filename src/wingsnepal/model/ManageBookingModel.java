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
    private String className;
    private String seatNo;
    private int tickets;
    private Date travelDate;
    private String paymentMethod;

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

    // Getters and Setters
    public int getBookingId() { return bookingId; }
    public void setBookingId(int bookingId) { this.bookingId = bookingId; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public int getFlightId() { return flightId; }
    public void setFlightId(int flightId) { this.flightId = flightId; }

    public String getFlightCode() { return flightCode; }
    public void setFlightCode(String flightCode) { this.flightCode = flightCode; }

    public String getClassName() { return className; }
    public void setClassName(String seatClass) { this.className = className; }

    public String getSeatNo() { return seatNo; }
    public void setSeatNo(String seatNo) { this.seatNo = seatNo; }

    public int getTickets() { return tickets; }
    public void setTickets(int tickets) { this.tickets = tickets; }

    public Date getTravelDate() { return travelDate; }
    public void setTravelDate(Date travelDate) { this.travelDate = travelDate; }

    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }
}
