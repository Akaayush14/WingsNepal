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
public class BookingFlight {

    private int flightId;
    private int seatId;
    private String fullName;
    private String email;
    private String seatClass;
    private String seatNo;
    private int tickets;
    private Date travelDate;
    private String paymentMethod;

    //Constructor
    public BookingFlight(int flightId, int seatId, String fullName, String email, String seatClass,
                   String seatNo, int tickets, Date travelDate, String paymentMethod) {
        this.flightId = flightId;
        this.seatId = seatId;
        this.fullName = fullName;
        this.email = email;
        this.seatClass = seatClass;
        this.seatNo = seatNo;
        this.tickets = tickets;
        this.travelDate = travelDate;
        this.paymentMethod = paymentMethod;
    }
    // Getters
    public int getFlightId() {
        return flightId;
    }

    public int getSeatId() {
        return seatId;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getSeatClass() {
        return seatClass;
    }

    public String getSeatNo() {
        return seatNo;
    }

    public int getTickets() {
        return tickets;
    }

    public Date getTravelDate() {
        return travelDate;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }
    // Setters
    public void setFlightId(int flightId) {
        this.flightId = flightId;
    }

    public void setSeatId(int seatId) {
        this.seatId = seatId;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSeatClass(String seatClass) {
        this.seatClass = seatClass;
    }

    public void setSeatNo(String seatNo) {
        this.seatNo = seatNo;
    }

    public void setTickets(int tickets) {
        this.tickets = tickets;
    }

    public void setTravelDate(Date travelDate) {
        this.travelDate = travelDate;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}
