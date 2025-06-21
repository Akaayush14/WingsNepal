/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package wingsnepal.model;

import java.util.Date;
/**
 *
 * @author Aayush Kharel
 */
public class Ticket {
    private String bookingReference;
    private String passengerName;
    private String flightDetails;
    private String seatNumber;
    private String seatClass;
    private Date issueDate;

    // Constructor
    public Ticket(String bookingReference, String passengerName, String flightDetails, 
                  String seatNumber, String seatClass, Date issueDate) {
        this.bookingReference = bookingReference;
        this.passengerName = passengerName;
        this.flightDetails = flightDetails;
        this.seatNumber = seatNumber;
        this.seatClass = seatClass;
        this.issueDate = issueDate;
    }

    // Getters and Setters
    public String getBookingReference() {
        return bookingReference;
    }

    public void setBookingReference(String bookingReference) {
        this.bookingReference = bookingReference;
    }

    public String getPassengerName() {
        return passengerName;
    }

    public void setPassengerName(String passengerName) {
        this.passengerName = passengerName;
    }

    public String getFlightDetails() {
        return flightDetails;
    }

    public void setFlightDetails(String flightDetails) {
        this.flightDetails = flightDetails;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public String getSeatClass() {
        return seatClass;
    }

    public void setSeatClass(String seatClass) {
        this.seatClass = seatClass;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    // toString method to print ticket details
    @Override
    public String toString() {
        return "Ticket{" +
               "bookingReference='" + bookingReference + '\'' +
               ", passengerName='" + passengerName + '\'' +
               ", flightDetails='" + flightDetails + '\'' +
               ", seatNumber='" + seatNumber + '\'' +
               ", seatClass='" + seatClass + '\'' +
               ", issueDate=" + issueDate +
               '}';
    }
}
