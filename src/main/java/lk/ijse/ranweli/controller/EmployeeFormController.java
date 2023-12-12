package lk.ijse.ranweli.controller;

import animatefx.animation.FadeIn;
import animatefx.animation.FadeInLeft;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import lk.ijse.ranweli.db.DbConnection;
import lk.ijse.ranweli.dto.EmployeeDto;
import lk.ijse.ranweli.dto.tm.EmployeeTm;
import lk.ijse.ranweli.model.EmployeeModel;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.InputStream;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class EmployeeFormController {
    public Text txtUserName;
    public TextField txtEmployeeId;
    public TextField txtEmployeeName;
    public TextField txtEmployeeAddress;
    public TextField txtEmployeeAvailability;
    public TextField txtEmployeeSalary;
    public TextField txtEmployeeType;
    public TableView<EmployeeTm>tblEmployee;
    public TableColumn<?,?> colId;
    public TableColumn<?,?>colName;
    public TableColumn<?,?> colAddress;
    public TableColumn <?,?>colType;
    public TableColumn <?,?>colAvailability;
    public TableColumn <?,?>colSalary;
    public Button btnBack;
    public Button btnReport;
    public Text txtTitle;

    public void initialize(){
        new FadeInLeft(txtTitle).play();
       loadAllEmployess();
       setCellValueFactory();

        tblEmployee.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.intValue() >= 0) {
                EmployeeTm selectedHotel = tblEmployee.getItems().get(newValue.intValue());
                txtEmployeeId.setText(selectedHotel.getEmpId());
                txtEmployeeName.setText(selectedHotel.getEmpName());
                txtEmployeeAddress.setText(selectedHotel.getEmpAddress());
                txtEmployeeType.setText(selectedHotel.getEmpType());
                txtEmployeeAvailability.setText(selectedHotel.getEmpAvailability());
                txtEmployeeSalary.setText(String.valueOf(selectedHotel.getEmpSalary()));
            }
        });
   }
    private void setCellValueFactory(){
        colId.setCellValueFactory(new PropertyValueFactory<>("empId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("empName"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("empAddress"));
        colType.setCellValueFactory(new PropertyValueFactory<>("empType"));
        colAvailability.setCellValueFactory(new PropertyValueFactory<>("empAvailability"));
        colSalary.setCellValueFactory(new PropertyValueFactory<>("empSalary"));
    }

    private void loadAllEmployess(){
        EmployeeModel model=new EmployeeModel();
        ObservableList<EmployeeTm> obList = FXCollections.observableArrayList();

        try{
            List<EmployeeDto> dtoLIst= model.getAllEmployees();
            for(EmployeeDto dto: dtoLIst){
                obList.add(new EmployeeTm(dto.getEmpId(),dto.getEmpName(),dto.getEmpAddress(),dto.getEmpType(),dto.getEmpAvailability(),dto.getEmpSalary()));
            }
            tblEmployee.setItems(obList);
            tblEmployee.refresh();
        }catch (Exception e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }
    public void saveBtnOnAction(ActionEvent actionEvent) {
            String employeeId=txtEmployeeId.getText();
            String employeeName=txtEmployeeName.getText();
            String employeeAddress=txtEmployeeAddress.getText();
            String employeeAvailability=txtEmployeeAvailability.getText();
            Double employeeSalary=Double.parseDouble(txtEmployeeSalary.getText());
            String employeeType=txtEmployeeType.getText();

            EmployeeDto dto=new EmployeeDto(employeeId,employeeName,employeeAddress,employeeType,employeeAvailability,employeeSalary);

            try {
                boolean isSaved = EmployeeModel.saveEmployee(dto);
                if(isSaved){
                    new Alert(Alert.AlertType.INFORMATION, "Save Successful").show();
                    loadAllEmployess();
                    clearFields();
                }
            }catch (Exception e){
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }



    }
    public void clearBtnOnAction(ActionEvent actionEvent) {
        clearFields();
    }

    public void updateBtnOnAction(ActionEvent actionEvent) {
        String employeeId=txtEmployeeId.getText();
        String employeeName=txtEmployeeName.getText();
        String employeeAddress=txtEmployeeAddress.getText();
        String employeeAvailability=txtEmployeeAvailability.getText();
        Double employeeSalary=Double.parseDouble(txtEmployeeSalary.getText());
        String employeeType=txtEmployeeType.getText();

        EmployeeDto dto=new EmployeeDto(employeeId,employeeName,employeeAddress,employeeType,employeeAvailability,employeeSalary);

        try {
            boolean isUpdated= EmployeeModel.updateEmployee(dto);
            if(isUpdated){
                new Alert(Alert.AlertType.INFORMATION, "Update Successful").show();
                clearFields();
                loadAllEmployess();
            }else{
                new Alert(Alert.AlertType.ERROR, "Update Failed").show();
            }
        }catch (Exception e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void deleteBtnOnAction(ActionEvent actionEvent) {
        String employeeId=txtEmployeeId.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Do you want to delete?");
        alert.setContentText("Choose your option.");

        ButtonType buttonTypeYes = new ButtonType("Yes");
        ButtonType buttonTypeNo = new ButtonType("No");

        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

        alert.showAndWait().ifPresent(response -> {
            if (response == buttonTypeYes) {
                try {
                    boolean isDeleted = EmployeeModel.deleteEmployee(employeeId);
                    if(isDeleted){
                        new Alert(Alert.AlertType.INFORMATION, "Delete Successful").show();
                        clearFields();
                        loadAllEmployess();

                    }else {
                        new Alert(Alert.AlertType.ERROR, "Delete Failed").show();
                    }
                }catch (Exception e){
                    System.out.println(e);
                }
            } else if (response == buttonTypeNo) {
                System.out.println("User clicked No");
            }
        });
    }

    public void backBtnOnAction(ActionEvent actionEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/dashboard_form.fxml"));
            Scene scene1 = new Scene(root);
            Stage stage1 = (Stage) btnBack.getScene().getWindow();
            stage1.setScene(scene1);
            stage1.setTitle("Dashboard");
            stage1.centerOnScreen();

            new FadeIn(root).play();
        }catch (Exception e){
            System.out.println(e);
        }
    }
    public String generateEmployeeId() {
        String prefix = "EMP";
        long timestamp = System.currentTimeMillis();
        SimpleDateFormat dateFormat = new SimpleDateFormat("HHmmss");
        String timestampString = dateFormat.format(new Date(timestamp));
        Random random = new Random();
        int randomComponent = random.nextInt(1000);
        String empId = prefix + timestampString + randomComponent;
        return empId;
    }

    public void clearFields(){
        txtEmployeeId.clear();
        txtEmployeeName.clear();
        txtEmployeeAddress.clear();
        txtEmployeeAvailability.clear();
        txtEmployeeSalary.clear();
        txtEmployeeType.clear();
    }

    public void txtEmpIdOnAction(MouseEvent mouseEvent) {
        txtEmployeeId.setText(generateEmployeeId());
    }

    public void btnReportOnAction(ActionEvent actionEvent) throws JRException, SQLException {
        InputStream resourceAsStream = getClass().getResourceAsStream("/report/employeeReport.jrxml");
        JasperDesign load = JRXmlLoader.load(resourceAsStream);
        JasperReport jasperReport = JasperCompileManager.compileReport(load);
        JasperPrint jasperPrint = JasperFillManager.fillReport(
                jasperReport,
                null,
                DbConnection.getInstance().getConnection());
        JasperViewer.viewReport(jasperPrint, false);
    }
}
