package lk.ijse.ranweli.model;

import lk.ijse.ranweli.db.DbConnection;
import lk.ijse.ranweli.dto.VehicleDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VehicleModel {
    public static boolean saveVehicle(VehicleDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "INSERT INTO vehicle VALUES(?,?,?,?)";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, dto.getVehicleId());
        pstm.setString(2, dto.getStatus());
        pstm.setInt(3, dto.getNumberOfSeats());
        pstm.setString(4, dto.getEmpId());

        if(pstm.executeUpdate()>0){
            return true;
        }else{
            return false;
        }
    }

    public static boolean deleteVehicle(String vehicleId) throws SQLException{
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "DELETE FROM vehicle WHERE vehicleId = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, vehicleId);

        if(pstm.executeUpdate()>0){
            return true;
        }else{
            return false;
        }
    }

    public static boolean updateVehicle(VehicleDto dto) throws SQLException{
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "UPDATE vehicle SET status = ?, numberOfSeats = ?, empId = ? WHERE vehicleId = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, dto.getStatus());
        pstm.setInt(2, dto.getNumberOfSeats());
        pstm.setString(3, dto.getEmpId());
        pstm.setString(4, dto.getVehicleId());

        if(pstm.executeUpdate()>0){
            return true;
        }else{
            return false;
        }
    }

    public static List<VehicleDto> getAllVehicles() throws SQLException{
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "SELECT * FROM vehicle";
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet rst = pstm.executeQuery();

        List<VehicleDto> list = new ArrayList<>();
        while(rst.next()){
            list.add(new VehicleDto(rst.getString(1), rst.getString(2), rst.getInt(3), rst.getString(4)));
        }
        return list;
    }

    public static VehicleDto getVehicle(String vehicleId) throws SQLException{
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "SELECT * FROM vehicle WHERE vehicleId = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, vehicleId);
        ResultSet rst = pstm.executeQuery();

        if(rst.next()){
            return new VehicleDto(rst.getString(1), rst.getString(2), rst.getInt(3), rst.getString(4));
        }else{
            return null;
        }
    }

}
