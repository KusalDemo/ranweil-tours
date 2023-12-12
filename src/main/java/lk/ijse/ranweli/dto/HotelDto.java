package lk.ijse.ranweli.dto;

public class HotelDto {
    private String hotelId;
    private String hotelName;
    private String hotelType;
    private String Status;

    public HotelDto(){

    }

    public HotelDto(String hotelId, String hotelName, String hotelType, String status) {
        this.hotelId = hotelId;
        this.hotelName = hotelName;
        this.hotelType = hotelType;
        Status = status;
    }

    public String getHotelId() {
        return hotelId;
    }

    public void setHotelId(String hotelId) {
        this.hotelId = hotelId;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public String getHotelType() {
        return hotelType;
    }

    public void setHotelType(String hotelType) {
        this.hotelType = hotelType;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    @Override
    public String toString() {
        return "HotelDto{" +
                "hotelId='" + hotelId + '\'' +
                ", hotelName='" + hotelName + '\'' +
                ", hotelType='" + hotelType + '\'' +
                ", Status='" + Status + '\'' +
                '}';
    }
}
