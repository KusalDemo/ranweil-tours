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
import javafx.scene.text.Text;
import javafx.stage.Stage;
import lk.ijse.ranweli.dto.PackageDto;
import lk.ijse.ranweli.dto.tm.PackageTm;
import lk.ijse.ranweli.model.PackageModel;

import java.sql.SQLException;
import java.util.List;

public class PackageFormController {

    public TextField txtPackageId;
    public TextField txtPackageName;
    public TextField txtPrice;
    public TableView<PackageTm> tblPackage;
    public TableColumn<?,?> colId;
    public TableColumn<?,?> colName;
    public TableColumn<?,?> colDescription;
    public TableColumn<?,?> colPrice;
    public TextField txtDescription;
    public Button btnBack;
    public Text txtPackage;

    public void initialize(){
        new SlideInLeft(txtPackage).play();
        loadAllPackages();
        setCellValueFactory();

        tblPackage.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.intValue() >= 0) {
                PackageTm selectedHotel = tblPackage.getItems().get(newValue.intValue());
                txtPackageId.setText(selectedHotel.getPackageId());
                txtPackageName.setText(selectedHotel.getPackageName());
                txtDescription.setText(selectedHotel.getDescription());
                txtPrice.setText(String.valueOf(selectedHotel.getPrice()));
            }
        });
    }

    public void setCellValueFactory(){
        colId.setCellValueFactory(new PropertyValueFactory<>("packageId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("packageName"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
    }
    public void loadAllPackages(){
        try{
            ObservableList<PackageTm> obList = FXCollections.observableArrayList();
            List<PackageDto> dtoLIst = PackageModel.getAllPackages();
            for(PackageDto dto: dtoLIst){
                obList.add(new PackageTm(dto.getPackageId(),dto.getPackageName(),dto.getDescription(),dto.getPrice()));
            }
            tblPackage.setItems(obList);
            tblPackage.refresh();
        }catch (Exception e){
           e.printStackTrace();
        }
    }

    public void saveBtnOnAction(ActionEvent actionEvent) throws SQLException {
        String packageId=txtPackageId.getText();
        String packageName=txtPackageName.getText();
        String description=txtDescription.getText();
        Double price=Double.parseDouble(txtPrice.getText());

        PackageDto dto = new PackageDto(packageId, packageName, description, price);
        try{
            if(PackageModel.savePackage(dto)){
                new Alert(Alert.AlertType.INFORMATION, "Package Saved").show();
                clearFields();
                loadAllPackages();
            }else {
                new Alert(Alert.AlertType.ERROR, "Package Not Saved").show();
            }
        }catch (Exception e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void updateBtnOnAction(ActionEvent actionEvent) {
        String packageId=txtPackageId.getText();
        String packageName=txtPackageName.getText();
        String description=txtDescription.getText();
        Double price=Double.parseDouble(txtPrice.getText());

        PackageDto dto = new PackageDto(packageId, packageName, description, price);
        try{
            if(PackageModel.updatePackage(dto)){
                new Alert(Alert.AlertType.INFORMATION, "Package Updated").show();
                clearFields();
                loadAllPackages();
            }else {
                new Alert(Alert.AlertType.ERROR, "Package Not Updated").show();
            }
        }catch (Exception e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }
    public void deleteBtnOnAction(ActionEvent actionEvent) {
        String packageId=txtPackageId.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Do you want to delete?");
        alert.setContentText("Choose your option.");

        ButtonType buttonTypeYes = new ButtonType("Yes");
        ButtonType buttonTypeNo = new ButtonType("No");

        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

        alert.showAndWait().ifPresent(response -> {
            if (response == buttonTypeYes) {
                try{
                    if(PackageModel.deletePackage(packageId)){
                        new Alert(Alert.AlertType.INFORMATION, "Package Deleted").show();
                        clearFields();
                        loadAllPackages();
                    }else {
                        new Alert(Alert.AlertType.ERROR, "Package Not Deleted").show();
                    }
                }catch (Exception e){
                    new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
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

    public void clearBtnOnAction(ActionEvent actionEvent) {
        clearFields();
    }

    public void clearFields(){
        txtPackageId.clear();
        txtPackageName.clear();
        txtPrice.clear();
        txtDescription.clear();
    }


}
