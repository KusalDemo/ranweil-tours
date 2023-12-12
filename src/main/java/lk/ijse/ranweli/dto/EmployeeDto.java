package lk.ijse.ranweli.dto;

public class EmployeeDto {
    private String empId;
    private String empName;
    private String empAddress;
    private String empType;
    private String empAvailability;
    private Double empSalary;

    public EmployeeDto() {

    }

    public EmployeeDto(String empId, String empName, String empAddress, String empType, String empAvailability, Double empSalary) {
        this.empId = empId;
        this.empName = empName;
        this.empAddress = empAddress;
        this.empType = empType;
        this.empAvailability = empAvailability;
        this.empSalary = empSalary;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getEmpAddress() {
        return empAddress;
    }

    public void setEmpAddress(String empAddress) {
        this.empAddress = empAddress;
    }

    public String getEmpType() {
        return empType;
    }

    public void setEmpType(String empType) {
        this.empType = empType;
    }

    public String getEmpAvailability() {
        return empAvailability;
    }

    public void setEmpAvailability(String empAvailability) {
        this.empAvailability = empAvailability;
    }

    public Double getEmpSalary() {
        return empSalary;
    }

    public void setEmpSalary(Double empSalary) {
        this.empSalary = empSalary;
    }

    @Override
    public String toString() {
        return "EmployeeDto{" +
                "empId='" + empId + '\'' +
                ", empName='" + empName + '\'' +
                ", empAddress='" + empAddress + '\'' +
                ", empType='" + empType + '\'' +
                ", empAvailability='" + empAvailability + '\'' +
                ", empSalary=" + empSalary +
                '}';
    }
}
