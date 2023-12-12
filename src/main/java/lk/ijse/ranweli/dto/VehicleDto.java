package lk.ijse.ranweli.dto;

public class VehicleDto {
    public String vehicleId;

    public String status;

    public int numberOfSeats;

    public String empId;

    public VehicleDto(){}

    public VehicleDto(String vehicleId, String status, int numberOfSeats, String empId) {
        this.vehicleId = vehicleId;
        this.status = status;
        this.numberOfSeats = numberOfSeats;
        this.empId = empId;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    @Override
    public String toString() {
        return "VehicleDto{" +
                "vehicleId='" + vehicleId + '\'' +
                ", status='" + status + '\'' +
                ", numberOfSeats=" + numberOfSeats +
                ", empId='" + empId + '\'' +
                '}';
    }
}
