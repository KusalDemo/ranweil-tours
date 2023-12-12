package lk.ijse.ranweli.controller;

import animatefx.animation.FadeIn;
import animatefx.animation.FadeInLeft;
import animatefx.animation.FadeInRight;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import lk.ijse.ranweli.QRScanner;
import lk.ijse.ranweli.model.PaymentModel;
import org.controlsfx.control.Notifications;

import java.sql.SQLException;
import java.time.LocalDateTime;

public class ConfirmJourneyFormController {

    public Text txtPaymentId;
    public Text txtWelcome;
    public Text txtUserName;
    public Text txtSelectedVehicle;
    public Text txtSelectedGuide;
    public Text txtSelectedHotel;
    public Text txtSelectedDriver;
    public Text txtAmount;
    public Text txtDate;
    public Button btnConfirmJourney;
    public Button btnBack;
    public ImageView img1;
    public ImageView img2;

    private String paymentId;
    private String amount;
    private String date;
    private String vehicleID;
    private String hotelID;
    private String guideID;
    private String driverID;

    public Button btnQRScanner;

    public void initialize(){
        new FadeInLeft(txtWelcome).play();
        new FadeInLeft(img1).play();
        new FadeInRight(img2).play();

        btnQRScanner.setOnAction(event -> {
            QRScanner qrCodeScanner = new QRScanner();
            qrCodeScanner.startScanner();
            String scannedContent = qrCodeScanner.getScannedQRCodeContent();
            System.out.println("Scanned QR Code Content: " + scannedContent);
            String[] values = scannedContent.split(",");
            if (values.length >= 7) {
                paymentId = values[0];
                amount = values[1];
                date = values[2];
                vehicleID = values[3];
                hotelID = values[4];
                guideID = values[5];
                driverID = values[6];

                txtPaymentId.setText(paymentId);
                txtAmount.setText(amount);
                txtDate.setText(date);
                txtSelectedVehicle.setText(vehicleID);
                txtSelectedGuide.setText(guideID);
                txtSelectedHotel.setText(hotelID);
                txtSelectedDriver.setText(driverID);
            }
        });
    }


    public void btnConfirmJourneyOnAction(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Do you want to proceed ? All availability will be reset");
        alert.setContentText("Choose your option.");

        ButtonType buttonTypeYes = new ButtonType("Yes");
        ButtonType buttonTypeNo = new ButtonType("No");

        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

        alert.showAndWait().ifPresent(response -> {
            if (response == buttonTypeYes) {
                try {
                    boolean isAvailabilityReset = PaymentModel.resetAvailability(txtSelectedVehicle.getText(), txtSelectedHotel.getText(), txtSelectedGuide.getText(), txtSelectedDriver.getText());
                    if (isAvailabilityReset) {
                        new Alert(Alert.AlertType.CONFIRMATION, "Journey has been ended successfully                    All Availability has been reset").show();
                        Notifications.create()
                                .title("Availability Reset")
                                .text("All Reservations have been reset \nTime : "+ LocalDateTime.now())
                                .position(Pos.TOP_RIGHT)
                                .showConfirm();
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            } else if (response == buttonTypeNo) {
                System.out.println("User clicked No");
            }
        });
    }

    public void btnBackOnAction(ActionEvent actionEvent) {
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


    public void btnQRScannerOnAction(ActionEvent actionEvent) {
    }
}
