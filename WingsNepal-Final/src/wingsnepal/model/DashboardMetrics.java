package wingsnepal.model;

/**
 * Model class to hold dashboard metrics and statistics
 * 
 * @author WingsNepal Team
 */
public class DashboardMetrics {
    private int totalFlights;
    private int availableSeats;
    private int totalReservations;
    private int totalEmployees;
    private int totalPassengers;

    public DashboardMetrics() {
        this.totalFlights = 0;
        this.availableSeats = 0;
        this.totalReservations = 0;
        this.totalEmployees = 0;
        this.totalPassengers = 0;
    }

    public DashboardMetrics(int totalFlights, int availableSeats, int totalReservations, 
                          int totalEmployees, int totalPassengers) {
        this.totalFlights = totalFlights;
        this.availableSeats = availableSeats;
        this.totalReservations = totalReservations;
        this.totalEmployees = totalEmployees;
        this.totalPassengers = totalPassengers;
    }

    // Getters
    public int getTotalFlights() {
        return totalFlights;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public int getTotalReservations() {
        return totalReservations;
    }

    public int getTotalEmployees() {
        return totalEmployees;
    }

    public int getTotalPassengers() {
        return totalPassengers;
    }

    // Setters
    public void setTotalFlights(int totalFlights) {
        this.totalFlights = totalFlights;
    }

    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }

    public void setTotalReservations(int totalReservations) {
        this.totalReservations = totalReservations;
    }

    public void setTotalEmployees(int totalEmployees) {
        this.totalEmployees = totalEmployees;
    }

    public void setTotalPassengers(int totalPassengers) {
        this.totalPassengers = totalPassengers;
    }

    @Override
    public String toString() {
        return "DashboardMetrics{" +
                "totalFlights=" + totalFlights +
                ", availableSeats=" + availableSeats +
                ", totalReservations=" + totalReservations +
                ", totalEmployees=" + totalEmployees +
                ", totalPassengers=" + totalPassengers +
                '}';
    }
} 