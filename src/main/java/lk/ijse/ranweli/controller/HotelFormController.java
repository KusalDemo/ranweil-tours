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
import lk.ijse.ranweli.dto.HotelDto;
import lk.ijse.ranweli.dto.tm.HotelTm;
import lk.ijse.ranweli.model.HotelModel;
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

public class HotelFormController {

    public TextField txtHotelId;
    public TextField txtHotelName;
    public TextField txtHotelType;
    public TableView <HotelTm>tblHotel;
    public TableColumn<?,?>colId;
    public TableColumn<?,?> colName;
    public TableColumn<?,?> colType;
    public TableColumn<?,?> colStatus;
    public TextField txtStatus;
    public Button btnBack;
    public Button btnReport;
    public Text txtTitle;

    public void initialize(){
        new FadeInLeft(txtTitle).play();
        loadAllHotels();
        setCellValueFactory();

        tblHotel.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.intValue() >= 0) {
                HotelTm selectedHotel = tblHotel.getItems().get(newValue.intValue());
                txtHotelId.setText(selectedHotel.getHotelId());
                txtHotelName.setText(selectedHotel.getHotelName());
                txtHotelType.setText(selectedHotel.getHotelType());
                txtStatus.setText(selectedHotel.getStatus());
            }
        });
    }

    private void setCellValueFactory(){
        colId.setCellValueFactory(new PropertyValueFactory<>("hotelId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("hotelName"));
        colType.setCellValueFactory(new PropertyValueFactory<>("hotelType"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
    }

    private void loadAllHotels(){
        try{
            ObservableList<HotelTm> obList = FXCollections.observableArrayList();
            List<HotelDto> dtoLIst = HotelModel.getAllHotels();
            for(HotelDto dto: dtoLIst){
                obList.add(new HotelTm(dto.getHotelId(),dto.getHotelName(),dto.getHotelType(),dto.getStatus()));
            }
            tblHotel.setItems(obList);
            tblHotel.refresh();
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

    public void clearBtnOnAction(ActionEvent actionEvent) {
        clearFields();
    }

    public void updateBtnOnAction(ActionEvent actionEvent) {
        String hotelId=txtHotelId.getText();
        String hotelName=txtHotelName.getText();
        String hotelType=txtHotelType.getText();
        String status=txtStatus.getText();

        HotelDto dto=new HotelDto(hotelId,hotelName,hotelType,status);
        try {
            boolean isUpdated= HotelModel.updateHotel(dto);
            if(isUpdated){
                new Alert(Alert.AlertType.INFORMATION, "Update Successful").show();
                clearFields();
                loadAllHotels();
            }else{
                new Alert(Alert.AlertType.ERROR, "Update Failed").show();
            }
        }catch (Exception e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void deleteBtnOnAction(ActionEvent actionEvent) {
        String hotelId=txtHotelId.getText();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Do you want to delete?");
        alert.setContentText("Choose your option.");

        ButtonType buttonTypeYes = new ButtonType("Yes");
        ButtonType buttonTypeNo = new ButtonType("No");

        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

        alert.showAndWait().ifPresent(response -> {
            if (response == buttonTypeYes) {
                boolean isDeleted = false;
                try {
                    isDeleted = HotelModel.deleteHotel(hotelId);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                if(isDeleted){
                    new Alert(Alert.AlertType.INFORMATION, "Delete Successful").show();
                    clearFields();
                    loadAllHotels();
                }else{
                    new Alert(Alert.AlertType.ERROR, "Delete Failed").show();
                }
            } else if (response == buttonTypeNo) {
                System.out.println("User clicked No");
            }
        });
    }
    public void saveBtnOnAction(ActionEvent actionEvent) {
        String hotelId=txtHotelId.getText();
        String hotelName=txtHotelName.getText();
        String hotelType=txtHotelType.getText();
        String status=txtStatus.getText();

        HotelDto dto=new HotelDto(hotelId,hotelName,hotelType,status);
        try {
            boolean isSaved = HotelModel.saveHotel(dto);
            if(isSaved){
                new Alert(Alert.AlertType.INFORMATION, "Save Successful").show();
                clearFields();
                loadAllHotels();
            }else{
                new Alert(Alert.AlertType.ERROR, "Save Failed").show();
            }
        }catch (Exception e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }
    public String generateHotelId() {
        String prefix = "HTL";
        long timestamp = System.currentTimeMillis();
        SimpleDateFormat dateFormat = new SimpleDateFormat("HHmmss");
        String timestampString = dateFormat.format(new Date(timestamp));
        Random random = new Random();
        int randomComponent = random.nextInt(2000);
        String hotelId = prefix + timestampString + randomComponent;
        return hotelId;
    }
    public void clearFields(){
        txtHotelId.clear();
        txtHotelName.clear();
        txtHotelType.clear();
        txtStatus.clear();
    }

    public void txtHotelIdOnAction(MouseEvent mouseEvent) {
        txtHotelId.setText(generateHotelId());
    }

    public void btnReportOnAction(ActionEvent actionEvent) throws JRException, SQLException {
        InputStream resourceAsStream = getClass().getResourceAsStream("/report/hotelReport.jrxml");
        JasperDesign load = JRXmlLoader.load(resourceAsStream);
        JasperReport jasperReport = JasperCompileManager.compileReport(load);
        JasperPrint jasperPrint = JasperFillManager.fillReport(
                jasperReport,
                null,
                DbConnection.getInstance().getConnection());
        JasperViewer.viewReport(jasperPrint, false);
    }
}
