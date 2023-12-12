package lk.ijse.ranweli.model;

import lk.ijse.ranweli.db.DbConnection;
import lk.ijse.ranweli.dto.TouristDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TouristModel {
    public static boolean saveTourist(TouristDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql="INSERT INTO tourist VALUES (?,?, AES_ENCRYPT(?, '43ad-8c7a-603b'),?)";
        //String sql = "INSERT INTO tourist VALUES(?,?,?,?)";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, dto.getIdentityDetails());
        pstm.setString(2, dto.getName());
        pstm.setString(3, dto.getPassword());
        pstm.setString(4, dto.getEmail());
        if(pstm.executeUpdate()>0){
            return true;
        }else{
            return false;
        }
    }
    public static TouristDto getTourist(String id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql="SELECT identityDetails,name, CONVERT(AES_DECRYPT(password,'43ad-8c7a-603b') USING utf8)AS decrypted_password FROM tourist WHERE identityDetails=?";
        //String sql = "SELECT * FROM tourist WHERE identityDetails=?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, id);

        ResultSet rs = pstm.executeQuery();
        TouristDto dto = new TouristDto();
            if(rs.next()){
                dto.setIdentityDetails(rs.getString("identityDetails"));
                dto.setName(rs.getString("name"));
                dto.setPassword(rs.getString("decrypted_password"));
                return dto;
            } else {
                return null;
            }
    }

    public static String getTouristEmailFromId(String id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "SELECT email FROM tourist WHERE identityDetails=?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, id);

        ResultSet rs = pstm.executeQuery();
        if(rs.next()){
            return rs.getString("email");
        }else{
            return null;
        }
    }
    public static boolean changePassword(String id, String password) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "UPDATE tourist SET password=AES_ENCRYPT(?, '43ad-8c7a-603b') WHERE identityDetails=?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, password);
        pstm.setString(2, id);
        if(pstm.executeUpdate()>0){
            return true;
        }else{
            return false;
        }
    }

}
