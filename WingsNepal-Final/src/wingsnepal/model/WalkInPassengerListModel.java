package wingsnepal.model;

import java.sql.Date;

/**
 * Model class for walk-in passenger data
 * Represents passengers from bookings made by employees for walk-in customers
 * 
 * @author WingsNepal Team
 */
public class WalkInPassengerListModel {
    private int bookingId;
    private int userId; // Employee who made the booking
    private int flightId;
    private String flightCode;
    private String flightName;
    private String fromCity;
    private String toCity;
    private String passengerName;
    private String passengerEmail;
    private String seatClass;
    private String seatNo;
    private Date travelDate;
    private String paymentMethod;
    private String bookingStatus;
    private String employeeName; // Name of employee who made the booking

    // Default constructor
    public WalkInPassengerListModel() {
        this.bookingId = 0;
        this.userId = 0;
        this.flightId = 0;
        this.flightCode = "";
        this.flightName = "";
        this.fromCity = "";
        this.toCity = "";
        this.passengerName = "";
        this.passengerEmail = "";
        this.seatClass = "";
        this.seatNo = "";
        this.travelDate = null;
        this.paymentMethod = "";
        this.bookingStatus = "";
        this.employeeName = "";
    }

    // Full constructor
    public WalkInPassengerListModel(int bookingId, int userId, int flightId, String flightCode, 
                                   String flightName, String fromCity, String toCity, String passengerName, 
                                   String passengerEmail, String seatClass, String seatNo, Date travelDate, 
                                   String paymentMethod, String bookingStatus, String employeeName) {
        this.bookingId = bookingId;
        this.userId = userId;
        this.flightId = flightId;
        this.flightCode = flightCode;
        this.flightName = flightName;
        this.fromCity = fromCity;
        this.toCity = toCity;
        this.passengerName = passengerName;
        this.passengerEmail = passengerEmail;
        this.seatClass = seatClass;
        this.seatNo = seatNo;
        this.travelDate = travelDate;
        this.paymentMethod = paymentMethod;
        this.bookingStatus = bookingStatus;
        this.employeeName = employeeName;
    }

    // Getters and Setters
    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getFlightId() {
        return flightId;
    }

    public void setFlightId(int flightId) {
        this.flightId = flightId;
    }

    public String getFlightCode() {
        return flightCode;
    }

    public void setFlightCode(String flightCode) {
        this.flightCode = flightCode;
    }

    public String getFlightName() {
        return flightName;
    }

    public void setFlightName(String flightName) {
        this.flightName = flightName;
    }

    public String getFromCity() {
        return fromCity;
    }

    public void setFromCity(String fromCity) {
        this.fromCity = fromCity;
    }

    public String getToCity() {
        return toCity;
    }

    public void setToCity(String toCity) {
        this.toCity = toCity;
    }

    public String getPassengerName() {
        return passengerName;
    }

    public void setPassengerName(String passengerName) {
        this.passengerName = passengerName;
    }

    public String getPassengerEmail() {
        return passengerEmail;
    }

    public void setPassengerEmail(String passengerEmail) {
        this.passengerEmail = passengerEmail;
    }

    public String getSeatClass() {
        return seatClass;
    }

    public void setSeatClass(String seatClass) {
        this.seatClass = seatClass;
    }

    public String getSeatNo() {
        return seatNo;
    }

    public void setSeatNo(String seatNo) {
        this.seatNo = seatNo;
    }

    public Date getTravelDate() {
        return travelDate;
    }

    public void setTravelDate(Date travelDate) {
        this.travelDate = travelDate;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(String bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    // Helper methods
    public String getFullRoute() {
        return fromCity + " → " + toCity;
    }

    public String getFlightInfo() {
        return flightCode + " - " + flightName;
    }

    public String getSeatInfo() {
        return seatClass + " - " + seatNo;
    }

    // toString method for debugging
    @Override
    public String toString() {
        return "WalkInPassengerListModel{" +
                "bookingId=" + bookingId +
                ", passengerName='" + passengerName + '\'' +
                ", passengerEmail='" + passengerEmail + '\'' +
                ", flightCode='" + flightCode + '\'' +
                ", seatClass='" + seatClass + '\'' +
                ", seatNo='" + seatNo + '\'' +
                ", employeeName='" + employeeName + '\'' +
                '}';
    }
} 