package lk.ijse.ranweli.controller;

import animatefx.animation.FadeIn;
import animatefx.animation.SlideInLeft;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import lk.ijse.ranweli.model.PaymentModel;
import org.controlsfx.control.Notifications;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;


public class DashboardFormController {

    public Text txtUserName;
    public AnchorPane root;
    public AnchorPane btnEmployeeManagement;
    public AnchorPane btnHotelManagement;
    public AnchorPane btnVehicleManagement;
    public AnchorPane btnPackageManagement;
    public Label lblTime;
    public Button btnLogOut;
    public AnchorPane btnManagePayments;
    public Text txtWelcome;
    public Text txtLoggedAdminName;
    public AnchorPane btnConfirmJourney;

    //private Label lblTime;
    //private volatile boolean stop;

    public void initialize(){
        new SlideInLeft(txtWelcome).play();
        txtLoggedAdminName.setText(LoginFormController.loggedAdmin);
        new SlideInLeft(txtLoggedAdminName).play();
        //setTime();

        /*Timer timer = new Timer();
        scheduleAtEndOfMonth(timer);*/

    }

    public void manageEmployeesOnAction(MouseEvent mouseEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/employee_form.fxml"));
            Scene scene1 = new Scene(root);
            Stage stage1 = (Stage) btnEmployeeManagement.getScene().getWindow();
            stage1.setScene(scene1);
            stage1.setTitle("Employee Management");
            stage1.centerOnScreen();

            new FadeIn(root).play();
        }catch (Exception e){
            System.out.println(e);
        }
    }

    public void logOutBtnOnAction(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Do you want to log out?");
        alert.setContentText("Choose your option.");

        ButtonType buttonTypeYes = new ButtonType("Yes");
        ButtonType buttonTypeNo = new ButtonType("No");

        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

        alert.showAndWait().ifPresent(response -> {
            if (response == buttonTypeYes) {
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("/view/login_form.fxml"));
                    Scene scene1 = new Scene(root);
                    Stage stage1 = (Stage) btnLogOut.getScene().getWindow();
                    stage1.setScene(scene1);
                    stage1.setTitle("Login Form");
                    stage1.centerOnScreen();

                    new FadeIn(root).play();

                    Notifications.create()
                            .title("Log Out")
                            .text("You have been logged out \nTime : "+ LocalDateTime.now())
                            .position(Pos.TOP_RIGHT)
                            .showConfirm();

                }catch (Exception e){
                    System.out.println(e);
                }
            } else if (response == buttonTypeNo) {
                System.out.println("User clicked No");
            }
        });
    }



    public void manageVehiclesOnAction(MouseEvent mouseEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/vehicle_form.fxml"));
            Scene scene1 = new Scene(root);
            Stage stage1 = (Stage) btnVehicleManagement.getScene().getWindow();
            stage1.setScene(scene1);
            stage1.setTitle("Vehicle Management");
            stage1.centerOnScreen();

            new FadeIn(root).play();
        }catch (Exception e){
            System.out.println(e);
        }
    }

    public void managePackagesOnAction(MouseEvent mouseEvent) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/view/package_form.fxml"));
        Scene scene1 = new Scene(root);
        Stage stage1 = (Stage) btnPackageManagement.getScene().getWindow();
        stage1.setScene(scene1);
        stage1.setTitle("Package Management");
        stage1.centerOnScreen();

        new FadeIn(root).play();

    }

    public void manageHotelsOnAction(MouseEvent mouseEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/hotel_form.fxml"));
            Scene scene1 = new Scene(root);
            Stage stage1 = (Stage) btnHotelManagement.getScene().getWindow();
            stage1.setScene(scene1);
            stage1.setTitle("Hotel Management");
            stage1.centerOnScreen();

            new FadeIn(root).play();
        }catch (Exception e){
            System.out.println(e);
        }
    }

    public void btnManagePaymentsOnAction(MouseEvent mouseEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/paymentManage_form.fxml"));
        Scene scene1 = new Scene(root);
        Stage stage1 = (Stage) btnManagePayments.getScene().getWindow();
        stage1.setScene(scene1);
        stage1.setTitle("Payment Management Form");
        stage1.centerOnScreen();

        new FadeIn(root).play();
    }
    private void scheduleAtEndOfMonth(Timer timer) {
        Calendar currentDate = Calendar.getInstance();
        currentDate.set(Calendar.DAY_OF_MONTH, currentDate.getActualMaximum(Calendar.DAY_OF_MONTH));
        int daysUntilEndOfMonth = currentDate.getActualMaximum(Calendar.DAY_OF_MONTH) - currentDate.get(Calendar.DAY_OF_MONTH);
        long delayMillis = daysUntilEndOfMonth * 24 * 60 * 60 * 1000;

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                System.out.println("End of month reached!");
                try {
                    boolean isResetSuccessfully = PaymentModel.resetAvailability();
                    if(isResetSuccessfully) {
                        System.out.println("Availability reset successfully.");
                    } else {
                        System.out.println("Failed to reset availability.");
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }, delayMillis, 30 * 24 * 60 * 60 * 1000);
    }

    public void btnConfirmJourneyOnAction(MouseEvent mouseEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/confirmJourney_form.fxml"));
            Scene scene1 = new Scene(root);
            Stage stage1 = (Stage) btnConfirmJourney.getScene().getWindow();
            stage1.setScene(scene1);
            stage1.setTitle("Confirm Journey End Form");
            stage1.centerOnScreen();

            new FadeIn(root).play();
        }catch (Exception e){
            System.out.println(e);
        }
    }
    /*private void setTime(){
        Thread dateAndTime = new Thread(() -> {
            SimpleDateFormat sdf= new SimpleDateFormat("HH:mm:ss");
            while (!stop){
               try{
                   Thread.sleep(1000);
               }catch (Exception e){
                   System.out.println(e);
               }
               String time = sdf.format(new Date());
               Platform.runLater(() -> {
                   lblTime.setText(time);
               });
            }
        });
        dateAndTime.start();
    }*/
}
