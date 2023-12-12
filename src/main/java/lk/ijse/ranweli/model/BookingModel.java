package lk.ijse.ranweli.model;

import lk.ijse.ranweli.db.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

public class BookingModel {
    public static boolean saveBooking(String hotelId, String PackageId, String vehicleId) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        try{
            connection.setAutoCommit(false);
            String sql="INSERT INTO hotel_package_details VALUES(?,?,?)";
            PreparedStatement pstm=connection.prepareStatement(sql);
            pstm.setString(1,hotelId);
            pstm.setString(2,PackageId);
            pstm.setDate(3,java.sql.Date.valueOf(LocalDate.now()));
            int isHotelPackageDetailsSaved = pstm.executeUpdate();

            String sql1="INSERT INTO vehicle_package_details VALUES(?,?)";
            PreparedStatement pstm1=connection.prepareStatement(sql1);
            pstm1.setString(1,vehicleId);
            pstm1.setString(2,PackageId);
            int isVehiclePackageDetailsSaved = pstm1.executeUpdate();

            if((isHotelPackageDetailsSaved>0) &&( isVehiclePackageDetailsSaved>0)){
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
}
