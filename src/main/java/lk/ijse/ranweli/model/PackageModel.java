package lk.ijse.ranweli.model;

import lk.ijse.ranweli.db.DbConnection;
import lk.ijse.ranweli.dto.PackageDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PackageModel {
    public static boolean savePackage(PackageDto dto) throws SQLException  {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "INSERT INTO package VALUES(?,?,?,?)";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, dto.getPackageId());
        pstm.setString(2, dto.getPackageName());
        pstm.setString(3, dto.getDescription());
        pstm.setDouble(4, dto.getPrice());

        if(pstm.executeUpdate()>0){
            return true;
        }else{
            return false;
        }
    }

    public static boolean deletePackage(String packageId) throws SQLException{
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "DELETE FROM package WHERE packageId = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, packageId);

        if(pstm.executeUpdate()>0){
            return true;
        }else {
            return false;
        }
    }
    public static boolean updatePackage(PackageDto dto) throws SQLException{
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "UPDATE package SET name = ?, description = ?, price = ? WHERE packageId = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, dto.getPackageName());
        pstm.setString(2, dto.getDescription());
        pstm.setDouble(3, dto.getPrice());
        pstm.setString(4, dto.getPackageId());

        if(pstm.executeUpdate()>0){
            return true;
        }else {
            return false;
        }
    }
    public static List<PackageDto> getAllPackages() throws SQLException{
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "SELECT * FROM package";
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet rst = pstm.executeQuery();
        List<PackageDto> list = new ArrayList<>();
        while(rst.next()){
            PackageDto dto = new PackageDto();
            dto.setPackageId(rst.getString("packageId"));
            dto.setPackageName(rst.getString("name"));
            dto.setDescription(rst.getString("description"));
            dto.setPrice(rst.getDouble("price"));
            //dto.setPrice(Double.parseDouble(rst.getString("price")));
            list.add(dto);
        }
        return list;

    }
    public static PackageDto searchPackage(String packageId) throws SQLException{
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "SELECT * FROM package WHERE packageId = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, packageId);
        ResultSet rst = pstm.executeQuery();
        if(rst.next()){
            PackageDto dto = new PackageDto();
            dto.setPackageId(rst.getString("packageId"));
            dto.setPackageName(rst.getString("name"));
            dto.setDescription(rst.getString("description"));
            dto.setPrice(Double.parseDouble(rst.getString("price")));
            return dto;
        }
        return null;
    }
}
