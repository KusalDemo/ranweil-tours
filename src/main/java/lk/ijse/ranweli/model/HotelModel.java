package lk.ijse.ranweli.model;

import lk.ijse.ranweli.db.DbConnection;
import lk.ijse.ranweli.dto.HotelDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HotelModel {
    public static boolean saveHotel(HotelDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "INSERT INTO hotel VALUES(?,?,?,?)";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, dto.getHotelId());
        pstm.setString(2, dto.getHotelName());
        pstm.setString(3, dto.getHotelType());
        pstm.setString(4, dto.getStatus());

        if(pstm.executeUpdate()>0){
            return true;
        }else{
            return false;
        }
    }

    public static boolean deleteHotel(String hotelId) throws SQLException{
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "DELETE FROM hotel WHERE hotelId = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, hotelId);

        if(pstm.executeUpdate()>0){
            return true;
        }else{
            return false;
        }
    }

    public static boolean updateHotel(HotelDto dto) throws SQLException{
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "UPDATE hotel SET name = ?, type = ?, status = ? WHERE hotelId = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, dto.getHotelName());
        pstm.setString(2, dto.getHotelType());
        pstm.setString(3, dto.getStatus());
        pstm.setString(4, dto.getHotelId());

        if(pstm.executeUpdate()>0){
            return true;
        }else{
            return false;
        }
    }

    public static List<HotelDto> getAllHotels() throws SQLException{
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "SELECT * FROM hotel";
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet rst = pstm.executeQuery();
        List<HotelDto> list = new ArrayList<>();
        while (rst.next()){
            list.add(new HotelDto(rst.getString(1), rst.getString(2), rst.getString(3), rst.getString(4)));
        }
        return list;
    }
}
