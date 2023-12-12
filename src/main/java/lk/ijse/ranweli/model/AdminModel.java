package lk.ijse.ranweli.model;

import lk.ijse.ranweli.db.DbConnection;
import lk.ijse.ranweli.dto.AdminDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminModel {
    public static AdminDto searchAdmin(String email) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        //String sql= "SELECT email,userName,CONVERT(AES_DECRYPT(password, '43ad-8c7a-603b') USING utf8) AS decrypted_password,type FROM admin WHERE email = ?";
        String sql = "SELECT * FROM admin WHERE email = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, email);

        ResultSet resultSet = pstm.executeQuery();
        AdminDto dto=null;

        if(resultSet.next()){
            dto = new AdminDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4)
            );
        }
        return dto;

    }

    public static boolean saveAdmin(AdminDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        //String sql="INSERT INTO admin VALUES (?,?, AES_ENCRYPT(?,'43ad-8c7a-603b'),?)";
        String sql = "INSERT INTO admin VALUES(?,?,?,?)";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, dto.getEmail());
        pstm.setString(2, dto.getUserName());
        pstm.setString(3, dto.getPassword());
        pstm.setString(4, dto.getType());
        return pstm.executeUpdate() > 0;
    }
}
