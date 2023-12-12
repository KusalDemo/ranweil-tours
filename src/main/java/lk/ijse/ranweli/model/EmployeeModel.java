package lk.ijse.ranweli.model;

import lk.ijse.ranweli.db.DbConnection;
import lk.ijse.ranweli.dto.EmployeeDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeModel {
    public static boolean saveEmployee(EmployeeDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "INSERT INTO employee VALUES(?,?,?,?,?,?,?)";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, dto.getEmpId());
        pstm.setString(2, dto.getEmpName());
        pstm.setString(3, dto.getEmpAddress());
        pstm.setString(4, dto.getEmpType());
        pstm.setString(5, dto.getEmpAvailability());
        pstm.setDouble(6, dto.getEmpSalary());
        pstm.setString(7,"kusalgunasekara2002@gmail.com");

        if(pstm.executeUpdate()>0){
            return true;
        }else{
            return false;
        }


    }

    public static boolean deleteEmployee(String employeeId) throws SQLException{
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "DELETE FROM employee WHERE empId = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, employeeId);

        if(pstm.executeUpdate()>0){
            return true;
        }else{
            return false;
        }
    }

    public static boolean updateEmployee(EmployeeDto dto) throws SQLException{
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "UPDATE employee SET name = ?, address = ?, type = ?, availability = ?, salary = ? WHERE empId = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, dto.getEmpName());
        pstm.setString(2, dto.getEmpAddress());
        pstm.setString(3, dto.getEmpType());
        pstm.setString(4, dto.getEmpAvailability());
        pstm.setDouble(5, dto.getEmpSalary());
        pstm.setString(6, dto.getEmpId());

        if(pstm.executeUpdate()>0){
            return true;
        }else {
            return false;
        }
    }

    public static List<EmployeeDto> getAllEmployees() throws  SQLException{
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "SELECT * FROM employee";
        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        ArrayList<EmployeeDto> dtoList= new ArrayList<>();
        while(resultSet.next()){
            dtoList.add(new EmployeeDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getDouble(6)
            ));
        }
        return dtoList;
    }
}
