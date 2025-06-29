package wingsnepal.model;

public class EmployeeModel {
    private int empId;
    private String fullName;
    private String email;
    private String role;
    private String gender;
    private String password;
    private String jobTitle;
    private Double salary;
    private String status;

    public EmployeeModel(int empId, String fullName, String email, String role, String gender, String password, String jobTitle, Double salary, String status) {
        this.empId = empId;
        this.fullName = fullName;
        this.email = email;
        this.role = role;
        this.gender = gender;
        this.password = password;
        this.jobTitle = jobTitle;
        this.salary = salary;
        this.status = status;
    }

    // Getters
    public int getEmpId() { return empId; }
    public String getFullName() { return fullName; }
    public String getEmail() { return email; }
    public String getRole() { return role; }
    public String getGender() { return gender; }
    public String getPassword() { return password; }
    public String getJobTitle() { return jobTitle; }
    public Double getSalary() { return salary; }
    public String getStatus() { return status; }

    // Setters
    public void setEmpId(int empId) { this.empId = empId; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public void setEmail(String email) { this.email = email; }
    public void setRole(String role) { this.role = role; }
    public void setGender(String gender) { this.gender = gender; }
    public void setPassword(String password) { this.password = password; }
    public void setJobTitle(String jobTitle) { this.jobTitle = jobTitle; }
    public void setSalary(Double salary) { this.salary = salary; }
    public void setStatus(String status) { this.status = status; }
} 