package lk.ijse.ranweli.controller;

import animatefx.animation.FadeIn;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import lk.ijse.ranweli.dto.EmployeeDto;
import lk.ijse.ranweli.dto.HotelDto;
import lk.ijse.ranweli.dto.PackageDto;
import lk.ijse.ranweli.dto.VehicleDto;
import lk.ijse.ranweli.dto.tm.EmployeeTm;
import lk.ijse.ranweli.dto.tm.HotelTm;
import lk.ijse.ranweli.dto.tm.PackageTm;
import lk.ijse.ranweli.dto.tm.VehicleTm;
import lk.ijse.ranweli.model.*;
import org.controlsfx.control.Notifications;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class BookingFormController {
    public AnchorPane root;
    public TableView<EmployeeTm>tblGuide;
    public TableColumn<?,?> colGuideId;
    public TableColumn<?,?> colGuideName;
    public TableView<HotelTm> tblHotel;
    public TableColumn<?,?> colHotelId;
    public TableColumn<?,?> colHotelName;
    public TableColumn<?,?> colHotelType;
    public Button btnBack;
    public TableView<VehicleTm> tblVehicle;
    public TableColumn<?,?> colVehicleId;
    public TableColumn<?,?> colNumSeats;
    public TableColumn<?,?> colDriverId;
    public Text txtSelectedVehicle;
    public Text txtSelectedGuide;
    public Text txtSelectedHotel;
    public TableView<PackageTm> tblPackages;
    public TableColumn<?,?> colId;
    public TableColumn<?,?> colName;
    public TableColumn<?,?> colPrice;
    public Text txtSelectedPackage;
    public Text txtTotal;
    public Button btnConfirmBooking;

    public static String selectedHotelId;
    public String selectedPackageId;
    public static Double selectedPackagePrice;
    public static String selectedGuideId;
    public static String selectedVehicleId;
    public static String selectedDriverId;

    public void initialize() throws SQLException {
        loadTableDetails();
        setCellValueFactory();

        tblVehicle.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.intValue() >= 0) {
                VehicleTm selectedVehicle = tblVehicle.getItems().get(newValue.intValue());
                txtSelectedVehicle.setText(selectedVehicle.getVehicleId());
                selectedVehicleId = selectedVehicle.getVehicleId();
                selectedDriverId = selectedVehicle.getEmpId();
                //txtSelectedVehicle.setText(selectedVehicle.getEmpId());
            }
        });
        tblGuide.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.intValue() >= 0) {
                EmployeeTm selectedGuide = tblGuide.getItems().get(newValue.intValue());
                txtSelectedGuide.setText(selectedGuide.getEmpName());
                selectedGuideId = selectedGuide.getEmpId();
            }
        });
        tblHotel.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.intValue() >= 0) {
                HotelTm selectedHotel = tblHotel.getItems().get(newValue.intValue());
                txtSelectedHotel.setText(selectedHotel.getHotelName());
                selectedHotelId= selectedHotel.getHotelId();
            }
        });
        tblPackages.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.intValue() >= 0) {
                PackageTm selectedPackage = tblPackages.getItems().get(newValue.intValue());
                txtSelectedPackage.setText(selectedPackage.getPackageName());
                txtTotal.setText(Double.toString(selectedPackage.getPrice()));
                selectedPackageId = selectedPackage.getPackageId();
                selectedPackagePrice = selectedPackage.getPrice();
            }
        });

    }
    public void setCellValueFactory(){
        colVehicleId.setCellValueFactory(new PropertyValueFactory<>("vehicleId"));
        colNumSeats.setCellValueFactory(new PropertyValueFactory<>("numberOfSeats"));
        colDriverId.setCellValueFactory(new PropertyValueFactory<>("empId"));
        colGuideId.setCellValueFactory(new PropertyValueFactory<>("empId"));
        colGuideName.setCellValueFactory(new PropertyValueFactory<>("empName"));
        colHotelId.setCellValueFactory(new PropertyValueFactory<>("hotelId"));
        colHotelName.setCellValueFactory(new PropertyValueFactory<>("hotelName"));
        colHotelType.setCellValueFactory(new PropertyValueFactory<>("hotelType"));
        colId.setCellValueFactory(new PropertyValueFactory<>("packageId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("packageName"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
    }
    public void loadTableDetails() throws SQLException {
        ObservableList<VehicleTm> obListVehicle = FXCollections.observableArrayList();
        ObservableList<EmployeeTm> obListGuides = FXCollections.observableArrayList();
        ObservableList<HotelTm> obListHotels = FXCollections.observableArrayList();
        ObservableList<PackageTm> obListPackages = FXCollections.observableArrayList();
        List<VehicleDto> allVehicles = VehicleModel.getAllVehicles();
        List<EmployeeDto> allEmployees = EmployeeModel.getAllEmployees();
        List<HotelDto> allHotels = HotelModel.getAllHotels();
        List<PackageDto> allPackages = PackageModel.getAllPackages();

        for (EmployeeDto dto : allEmployees) {
            if((dto.getEmpType().equals("GUIDE")) && (dto.getEmpAvailability().equals("YES"))){
                obListGuides.add(new EmployeeTm(dto.getEmpId(),dto.getEmpName(),dto.getEmpAddress(),dto.getEmpType(),dto.getEmpAvailability(),dto.getEmpSalary()));
            }
        }
        tblGuide.setItems(obListGuides);
        tblGuide.refresh();

        for(VehicleDto dto : allVehicles){
            if(dto.getStatus().equals("YES")){
                obListVehicle.add(new VehicleTm(dto.getVehicleId(),dto.getStatus(),dto.getNumberOfSeats(),dto.getEmpId()));
            }
        }
        tblVehicle.setItems(obListVehicle);
        tblVehicle.refresh();

        for(HotelDto dto : allHotels){
            if(dto.getStatus().equals("YES")){
                obListHotels.add(new HotelTm(dto.getHotelId(),dto.getHotelName(),dto.getHotelType(),dto.getStatus()));
            }
        }
        tblHotel.setItems(obListHotels);
        tblHotel.refresh();

        for(PackageDto dto : allPackages){
            obListPackages.add(new PackageTm(dto.getPackageId(),dto.getPackageName(),dto.getDescription(),dto.getPrice()));
        }
        tblPackages.setItems(obListPackages);
        tblPackages.refresh();

    }
    public void btnBackOnAction() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/touristDashboard_form.fxml"));
        Scene scene1 = new Scene(root);
        Stage stage1 = (Stage) btnBack.getScene().getWindow();
        stage1.setScene(scene1);
        stage1.setTitle("Dashboard");
        stage1.centerOnScreen();

        new FadeIn(root).play();
    }

    public void btnConfirmBookingOnAction(ActionEvent actionEvent)  {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Do you want to proceed?");
        alert.setContentText("Choose your option.");

        ButtonType buttonTypeYes = new ButtonType("Yes");
        ButtonType buttonTypeNo = new ButtonType("No");

        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

        alert.showAndWait().ifPresent(response -> {
            if (response == buttonTypeYes) {
                try{
                    boolean isBookingTempUpdated = BookingModel.saveBooking(selectedHotelId, selectedPackageId, selectedVehicleId);
                    if(isBookingTempUpdated){
                        System.out.println("Temparory Updated");
                    }
                    Parent root = FXMLLoader.load(getClass().getResource("/view/payment_form.fxml"));
                    Scene scene1 = new Scene(root);
                    Stage stage1 = (Stage) btnConfirmBooking.getScene().getWindow();
                    stage1.setScene(scene1);
                    stage1.setTitle("Payment Form");
                    stage1.centerOnScreen();

                    new FadeIn(root).play();

                    Notifications.create()
                            .title("Booking Confirmed")
                            .text("You can now proceed the payment \n\n Make sure to upload your payment slip")
                            .position(Pos.TOP_RIGHT)
                            .showConfirm();

                }catch (Exception e){
                    e.printStackTrace();
                }
            } else if (response == buttonTypeNo) {
                System.out.println("User clicked No");
            }
        });
    }


}
