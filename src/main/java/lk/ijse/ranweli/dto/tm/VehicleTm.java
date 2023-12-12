package lk.ijse.ranweli.dto.tm;

public class VehicleTm {
    private String vehicleId;
    private String status;
    private String numberOfSeats;
    private String empId;
    public VehicleTm(){}
    public VehicleTm(String vehicleId, String status, int numberOfSeats, String empId) {
        this.vehicleId = vehicleId;
        this.status = status;
        this.numberOfSeats = String.valueOf(numberOfSeats);
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

    public String getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = String.valueOf(numberOfSeats);
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    @Override
    public String toString() {
        return "VehicleTm{" +
                "vehicleId='" + vehicleId + '\'' +
                ", status='" + status + '\'' +
                ", numberOfSeats=" + numberOfSeats +
                ", empId='" + empId + '\'' +
                '}';
    }
}
