package wingsnepal.model;

public class Employee {
    private int empId;
    private String fullName;
    private String empEmail;
    private String empRole;
    private String gender;
    private String password;

    // ✅ No-arg constructor (required by DAO for setters)
    public Employee() {}

    // ✅ Parameterized constructor
    public Employee(int empId, String fullName, String empEmail, String empRole, String gender, String password) {
        this.empId = empId;
        this.fullName = fullName;
        this.empEmail = empEmail;
        this.empRole = empRole;
        this.gender = gender;
        this.password = password;
    }

    public int getEmpId() { return empId; }
    public String getFullName() { return fullName; }
    public String getEmpEmail() { return empEmail; }
    public String getEmpRole() { return empRole; }
    public String getGender() { return gender; }
    public String getPassword() { return password; }

    public void setEmpId(int empId) { this.empId = empId; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public void setEmpEmail(String empEmail) { this.empEmail = empEmail; }
    public void setEmpRole(String empRole) { this.empRole = empRole; }
    public void setGender(String gender) { this.gender = gender; }
    public void setPassword(String password) { this.password = password; }
}
