package lk.ijse.ranweli.controller;

import animatefx.animation.FadeIn;
import animatefx.animation.SlideInLeft;
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
import lk.ijse.ranweli.dto.VehicleDto;
import lk.ijse.ranweli.dto.tm.VehicleTm;
import lk.ijse.ranweli.model.EmployeeModel;
import lk.ijse.ranweli.model.VehicleModel;
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

public class VehicleFormController {

    public TextField txtVehicleId;
    public TextField txtStatus;
    public TextField txtNumberOfSeats;
    public TableView<VehicleTm> tblVehicle;
    public TableColumn<?,?> colId;
    public TableColumn<?,?> colStatus;
    public TableColumn<?,?> colNumberOfSeats;
    public TableColumn<?,?> colEmployeeId;
    public ComboBox cmbEmployeeId;
    public Button btnBack;
    public Button btnReport;
    public Text txtVehicle;


    public void initialize(){
        new SlideInLeft(txtVehicle).play();
        loadAllVehicles();
        setCellValueFactory();
        loadEmployeeIds();

        tblVehicle.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.intValue() >= 0) {
                VehicleTm selectedHotel = tblVehicle.getItems().get(newValue.intValue());
                txtVehicleId.setText(selectedHotel.getVehicleId());
                txtStatus.setText(selectedHotel.getStatus());
                txtNumberOfSeats.setText(String.valueOf(selectedHotel.getNumberOfSeats()));
            }
        });
    }

    private void setCellValueFactory(){
        colId.setCellValueFactory(new PropertyValueFactory<>("vehicleId"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colNumberOfSeats.setCellValueFactory(new PropertyValueFactory<>("numberOfSeats"));
        colEmployeeId.setCellValueFactory(new PropertyValueFactory<>("empId"));
    }

    private  void loadAllVehicles(){
        try{
            ObservableList<VehicleTm> obList = FXCollections.observableArrayList();
            List<VehicleDto> dtoLIst = VehicleModel.getAllVehicles();
            for(VehicleDto dto: dtoLIst){
                obList.add(new VehicleTm(dto.getVehicleId(),dto.getStatus(),dto.getNumberOfSeats(),dto.getEmpId()));
            }
            tblVehicle.setItems(obList);
            tblVehicle.refresh();
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
    private void loadEmployeeIds(){
        ObservableList<String> obList = FXCollections.observableArrayList();
        try{
            List<EmployeeDto> dtoLIst = EmployeeModel.getAllEmployees();
            for(EmployeeDto dto: dtoLIst){
                if((dto.getEmpType().equals("DRIVER"))&&(dto.getEmpAvailability().equals("YES"))){
                    obList.add(dto.getEmpId());
                }
            }
            cmbEmployeeId.setItems(obList);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
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

    public void saveBtnOnAction(ActionEvent actionEvent) {
        String vehicleId=txtVehicleId.getText();
        String status=txtStatus.getText();
        int numberOfSeats= Integer.parseInt(txtNumberOfSeats.getText());
        String empId= String.valueOf(cmbEmployeeId.getValue());

        VehicleDto dto=new VehicleDto(vehicleId,status,numberOfSeats,empId);
        try {
            boolean isSaved = VehicleModel.saveVehicle(dto);
            if(isSaved){
                new Alert(Alert.AlertType.INFORMATION, "Save Successful").show();
                clearFields();
                loadAllVehicles();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void clearBtnOnAction(ActionEvent actionEvent) {
        clearFields();
    }
    public void deleteBtnOnAction(ActionEvent actionEvent) {
        String vehicleId=txtVehicleId.getText();

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
                    boolean isDeleted = VehicleModel.deleteVehicle(vehicleId);
                    if(isDeleted){
                        new Alert(Alert.AlertType.INFORMATION, "Delete Successful").show();
                        clearFields();
                        loadAllVehicles();
                    }else {
                        new Alert(Alert.AlertType.ERROR, "Delete Failed").show();
                    }
                }catch (Exception e){
                    new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
                }
            } else if (response == buttonTypeNo) {
                System.out.println("User clicked No");
            }
        });
    }
    public void updateBtnOnAction(ActionEvent actionEvent) {
        String vehicleId=txtVehicleId.getText();
        String status=txtStatus.getText();
        int numberOfSeats= Integer.parseInt(txtNumberOfSeats.getText());
        String empId= String.valueOf(cmbEmployeeId.getValue());

        VehicleDto dto=new VehicleDto(vehicleId,status,numberOfSeats,empId);

        try {
            boolean isUpdated= VehicleModel.updateVehicle(dto);
            if(isUpdated){
                new Alert(Alert.AlertType.INFORMATION, "Update Successful").show();
                clearFields();
                loadAllVehicles();
            }else{
                new Alert(Alert.AlertType.ERROR, "Update Failed").show();
            }
        }catch (Exception e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }
    public String generateVehicleId() {
        String prefix = "VH";
        long timestamp = System.currentTimeMillis();
        SimpleDateFormat dateFormat = new SimpleDateFormat("HHmmss");
        String timestampString = dateFormat.format(new Date(timestamp));
        Random random = new Random();
        int randomComponent = random.nextInt(1000);
        String vehicleId = prefix + timestampString + randomComponent;
        return vehicleId;
    }
    public void clearFields(){
        txtVehicleId.clear();
        txtStatus.clear();
        txtNumberOfSeats.clear();
        cmbEmployeeId.setValue(null);
        //cmbEmployeeId.setSelectionModel(null);
    }

    public void txtVehicleIdOnAction(MouseEvent mouseEvent) {
        txtVehicleId.setText(generateVehicleId());
    }

    public void btnReportOnAction(ActionEvent actionEvent) throws JRException, SQLException {
        InputStream resourceAsStream = getClass().getResourceAsStream("/report/vehicleReport.jrxml");
        JasperDesign load = JRXmlLoader.load(resourceAsStream);
        JasperReport jasperReport = JasperCompileManager.compileReport(load);
        JasperPrint jasperPrint = JasperFillManager.fillReport(
                jasperReport,
                null,
                DbConnection.getInstance().getConnection());
        JasperViewer.viewReport(jasperPrint, false);
    }
}
