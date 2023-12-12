package lk.ijse.ranweli.dto.tm;

public class HotelTm {
    private String hotelId;
    private String hotelName;
    private String hotelType;
    private String status;

    public HotelTm(){
    }

    public HotelTm(String hotelId, String hotelName, String hotelType, String status) {
        this.hotelId = hotelId;
        this.hotelName = hotelName;
        this.hotelType = hotelType;
        this.status = status;
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
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "HotelTm{" +
                "hotelId='" + hotelId + '\'' +
                ", hotelName='" + hotelName + '\'' +
                ", hotelType='" + hotelType + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
