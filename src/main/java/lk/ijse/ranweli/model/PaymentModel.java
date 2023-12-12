package lk.ijse.ranweli.model;

import javafx.scene.image.Image;
import lk.ijse.ranweli.db.DbConnection;
import lk.ijse.ranweli.dto.PaymentDto;

import java.sql.*;
import java.util.ArrayList;

public class PaymentModel {
    public static boolean savePayment(PaymentDto paymentDto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "INSERT INTO payment VALUES(?,?,?,?,?,?)";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, paymentDto.getPayId());
        pstm.setDouble(2, paymentDto.getAmount());
        pstm.setString(3, paymentDto.getStatus());
        pstm.setDate(4, Date.valueOf(paymentDto.getDate()));
        pstm.setString(5, paymentDto.getMethod());
        pstm.setBytes(6, paymentDto.getReceipt());

        if(pstm.executeUpdate()>0){
            return true;
        }else{
            return false;
        }
    }

    public static boolean updatePayment(String vehicleId,String hotelId,String guideId,String driverId) throws SQLException{
        Connection connection = DbConnection.getInstance().getConnection();
        try{
            connection.setAutoCommit(false);
            String sql="UPDATE vehicle SET status = 'NO' WHERE vehicleId = ?";
            PreparedStatement pstm=connection.prepareStatement(sql);
            pstm.setString(1,vehicleId);
            int isVehicleUpdated = pstm.executeUpdate();

            String sql1="UPDATE hotel SET status = 'NO' WHERE hotelId = ?";
            PreparedStatement pstm1=connection.prepareStatement(sql1);
            pstm1.setString(1,hotelId);
            int isHotelUpdated = pstm1.executeUpdate();

            String sql2="UPDATE employee SET availability = 'NO' WHERE empId = ?";
            PreparedStatement pstm2=connection.prepareStatement(sql2);
            pstm2.setString(1,guideId);
            int isGuideUpdated = pstm2.executeUpdate();

            String sql3="UPDATE employee SET availability = 'NO' WHERE empId = ?";
            PreparedStatement pstm3=connection.prepareStatement(sql3);
            pstm3.setString(1,driverId);
            int isDriverUpdated = pstm3.executeUpdate();

            if((isVehicleUpdated>0) &&(isHotelUpdated>0) &&(isGuideUpdated>0) &&(isDriverUpdated>0)){
                connection.commit();
                return true;
            }else{
                connection.rollback();
                return false;
            }
        }catch (Exception e){
            e.printStackTrace();
            return  false;
        }finally {
            connection.setAutoCommit(true);
        }
    }
    public static boolean resetAvailability() throws SQLException{
        Connection connection = DbConnection.getInstance().getConnection();
        try{
            connection.setAutoCommit(false);
            String sql="UPDATE employee SET status = 'YES' WHERE status = 'NO'";
            PreparedStatement pstm=connection.prepareStatement(sql);
            int isVehicleUpdated = pstm.executeUpdate();

            String sql1="UPDATE hotel SET status = 'YES' WHERE status = 'NO'";
            PreparedStatement pstm1=connection.prepareStatement(sql1);
            int isHotelUpdated = pstm1.executeUpdate();

            String sql2="UPDATE employee SET availability = 'YES' WHERE availability = 'NO'";
            PreparedStatement pstm2=connection.prepareStatement(sql2);
            int isEmployeeUpdated = pstm2.executeUpdate();

            if((isVehicleUpdated>0) &&(isHotelUpdated>0) &&(isEmployeeUpdated>0)){
                connection.commit();
                return true;
            }else{
                connection.rollback();
                return false;
            }
        }catch (Exception e){
            e.printStackTrace();
            return  false;
        }finally {
            connection.setAutoCommit(true);
        }
    }
    public static boolean resetAvailability(String vehicleId,String hotelId,String guideId,String driverId) throws SQLException{
        Connection connection = DbConnection.getInstance().getConnection();
        try{
            connection.setAutoCommit(false);
            String sql="UPDATE vehicle SET status = 'YES' WHERE vehicleId = ?";
            PreparedStatement pstm=connection.prepareStatement(sql);
            pstm.setString(1,vehicleId);
            int isVehicleUpdated = pstm.executeUpdate();

            String sql1="UPDATE hotel SET status = 'YES' WHERE hotelId = ?";
            PreparedStatement pstm1=connection.prepareStatement(sql1);
            pstm1.setString(1,hotelId);
            int isHotelUpdated = pstm1.executeUpdate();

            String sql2="UPDATE employee SET availability = 'YES' WHERE empId = ?";
            PreparedStatement pstm2=connection.prepareStatement(sql2);
            pstm2.setString(1,guideId);
            int isGuideUpdated = pstm2.executeUpdate();

            String sql3="UPDATE employee SET availability = 'YES' WHERE empId = ?";
            PreparedStatement pstm3=connection.prepareStatement(sql3);
            pstm3.setString(1,driverId);
            int isDriverUpdated = pstm3.executeUpdate();

            if((isVehicleUpdated>0) &&(isHotelUpdated>0) &&(isGuideUpdated>0) &&(isDriverUpdated>0)){
                connection.commit();
                return true;
            }else{
                connection.rollback();
                return false;
            }
        }catch (Exception e){
            e.printStackTrace();
            return  false;
        }finally {
            connection.setAutoCommit(true);
        }
    }

    public static ArrayList<PaymentDto> getAllPayments() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "SELECT * FROM payment";
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();
        ArrayList<PaymentDto> paymentDtos = new ArrayList<>();

        while(resultSet.next()){
            paymentDtos.add(new PaymentDto(
                    resultSet.getString(1),
                    resultSet.getDouble(2),
                    resultSet.getString(3),
                    resultSet.getDate(4).toLocalDate(),
                    resultSet.getString(5),
                    resultSet.getBytes(6)
            ));
        }return paymentDtos;
    }

    public static Image getReceipt(String payId) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "SELECT receipt FROM payment WHERE payId = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, payId);
        ResultSet resultSet = pstm.executeQuery();

        if (resultSet.next()) {
            Blob blob = resultSet.getBlob("receipt");
            return new Image(blob.getBinaryStream());
        }else{
            return null;
        }
    }

}
