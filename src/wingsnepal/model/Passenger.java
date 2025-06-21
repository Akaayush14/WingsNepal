/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package wingsnepal.model;

import java.sql.Date;

public class Passenger {
    private String fullName;
    private int passengerId;
    private Date travelDate;
    private String flightCode;
    private String seatNo;
    private String seatClass;
    private String paymentMethod;

    public Passenger(String fullName, int passengerId, Date travelDate, String flightCode,
                     String seatNo, String seatClass, String paymentMethod) {
        this.fullName = fullName;
        this.passengerId = passengerId;
        this.travelDate = travelDate;
        this.flightCode = flightCode;
        this.seatNo = seatNo;
        this.seatClass = seatClass;
        this.paymentMethod = paymentMethod;
    }

    // Getters
    public String getFullName() { return fullName; }
    public int getPassengerId() { return passengerId; }
    public Date getTravelDate() { return travelDate; }
    public String getFlightCode() { return flightCode; }
    public String getSeatNo() { return seatNo; }
    public String getSeatClass() { return seatClass; }
    public String getPaymentMethod() { return paymentMethod; }
}
