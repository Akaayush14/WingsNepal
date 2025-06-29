package wingsnepal.model;

/**
 * Model class to hold passenger information
 * 
 * @author WingsNepal Team
 */
public class PassengerModel {
    private int passengerId;
    private int userId; // User who created this passenger record
    private String fullName;
    private String status;
    private String upcomingFlight;
    private String email;
    private String seatClass;
    private String relationship; // "Self", "Family", "Friend", "Colleague", etc.
    private String phone; // Phone number from user's account
    private String seatNumber; // Seat number from booking
    private String bookingStatus; // Booking status (Active, Suspended, etc.)

    public PassengerModel() {
        this.passengerId = 0;
        this.userId = 0;
        this.fullName = "";
        this.status = "";
        this.upcomingFlight = "";
        this.email = "";
        this.seatClass = "";
        this.relationship = "";
        this.phone = "";
        this.seatNumber = "";
        this.bookingStatus = "";
    }

    public PassengerModel(int passengerId, int userId, String fullName, String status, 
                    String upcomingFlight, String email, String seatClass, String relationship, String phone, String seatNumber, String bookingStatus) {
        this.passengerId = passengerId;
        this.userId = userId;
        this.fullName = fullName;
        this.status = status;
        this.upcomingFlight = upcomingFlight;
        this.email = email;
        this.seatClass = seatClass;
        this.relationship = relationship;
        this.phone = phone;
        this.seatNumber = seatNumber;
        this.bookingStatus = bookingStatus;
    }

    // Backward compatibility constructor
    public PassengerModel(int passengerId, String fullName, String status, 
                    String upcomingFlight, String email, String seatClass) {
        this(passengerId, 0, fullName, status, upcomingFlight, email, seatClass, "", "", "", "");
    }
    
    // Constructor without phone (for backward compatibility)
    public PassengerModel(int passengerId, int userId, String fullName, String status, 
                    String upcomingFlight, String email, String seatClass, String relationship) {
        this(passengerId, userId, fullName, status, upcomingFlight, email, seatClass, relationship, "", "", "");
    }

    // Constructor with phone but without seat number (for backward compatibility)
    public PassengerModel(int passengerId, int userId, String fullName, String status, 
                    String upcomingFlight, String email, String seatClass, String relationship, String phone) {
        this(passengerId, userId, fullName, status, upcomingFlight, email, seatClass, relationship, phone, "", "");
    }

    // Constructor with phone and seat number but without booking status (for backward compatibility)
    public PassengerModel(int passengerId, int userId, String fullName, String status, 
                    String upcomingFlight, String email, String seatClass, String relationship, String phone, String seatNumber) {
        this(passengerId, userId, fullName, status, upcomingFlight, email, seatClass, relationship, phone, seatNumber, "");
    }

    // Getters
    public int getPassengerId() {
        return passengerId;
    }

    public int getUserId() {
        return userId;
    }

    public String getFullName() {
        return fullName;
    }

    public String getStatus() {
        return status;
    }

    public String getUpcomingFlight() {
        return upcomingFlight;
    }

    public String getEmail() {
        return email;
    }

    public String getSeatClass() {
        return seatClass;
    }

    public String getRelationship() {
        return relationship;
    }

    public String getPhone() {
        return phone;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public String getBookingStatus() {
        return bookingStatus;
    }

    // Setters
    public void setPassengerId(int passengerId) {
        this.passengerId = passengerId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setUpcomingFlight(String upcomingFlight) {
        this.upcomingFlight = upcomingFlight;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSeatClass(String seatClass) {
        this.seatClass = seatClass;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public void setBookingStatus(String bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    @Override
    public String toString() {
        return "Passenger{" +
                "passengerId=" + passengerId +
                ", userId=" + userId +
                ", fullName='" + fullName + '\'' +
                ", status='" + status + '\'' +
                ", upcomingFlight='" + upcomingFlight + '\'' +
                ", email='" + email + '\'' +
                ", seatClass='" + seatClass + '\'' +
                ", relationship='" + relationship + '\'' +
                ", phone='" + phone + '\'' +
                ", seatNumber='" + seatNumber + '\'' +
                ", bookingStatus='" + bookingStatus + '\'' +
                '}';
    }
} 