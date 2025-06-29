package wingsnepal.model;

import java.sql.Date;

public class BookingFlightModel {
    private int bookingId;
    private int userId;
    private int flightId;
    private int seatId;
    private String fullName;
    private String email;
    private String className;
    private String seatNo;
    private int tickets;
    private Date date;
    private String paymentMethod;
    private boolean isBooked;
    private String flightCode;

    // ✅ No-argument constructor
    public BookingFlightModel() {
    }

    // Constructor WITHOUT bookingId
    public BookingFlightModel(int userId, int flightId, int seatId,
                         String fullName, String email, String className,
                         String seatNo, int tickets, Date travelDate, String paymentMethod, String flightCode) {
        this.userId = userId;
        this.flightId = flightId;
        this.seatId = seatId;
        this.fullName = fullName;
        this.email = email;
        this.className = className;
        this.seatNo = seatNo;
        this.tickets = tickets;
        this.date = travelDate;
        this.paymentMethod = paymentMethod;
        this.flightCode = flightCode;
    }

    // Overloaded Constructor WITH bookingId
    public BookingFlightModel(int bookingId, int userId, int flightId, int seatId,
                         String fullName, String email, String className,
                         String seatNo, int tickets, Date travelDate, String paymentMethod, String flightCode) {
        this.bookingId = bookingId;
        this.userId = userId;
        this.flightId = flightId;
        this.seatId = seatId;
        this.fullName = fullName;
        this.email = email;
        this.className = className;
        this.seatNo = seatNo;
        this.tickets = tickets;
        this.date = travelDate;
        this.paymentMethod = paymentMethod;
        this.flightCode = flightCode;
    }

    // Getters
    public int getBookingId() {
        return bookingId;
    }

    public int getUserId() {
        return userId;
    }

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

    public String getClassName() {
        return className;
    }

    public String getSeatNo() {
        return seatNo;
    }

    public int getTickets() {
        return tickets;
    }

    public Date getTravelDate() {
        return date;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public boolean isBooked() {
        return isBooked;
    }

    public String getFlightCode() {
        return flightCode;
    }

    // Setters
    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

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

    public void setClassName(String className) {
        this.className = className;
    }

    public void setSeatNo(String seatNo) {
        this.seatNo = seatNo;
    }

    public void setTickets(int tickets) {
        this.tickets = tickets;
    }

    public void setTravelDate(Date travelDate) {
        this.date = travelDate;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public void setBooked(boolean isBooked) {
        this.isBooked = isBooked;
    }

    public void setFlightCode(String flightCode) {
        this.flightCode = flightCode;
    }
}
