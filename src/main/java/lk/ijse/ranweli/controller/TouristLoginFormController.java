package lk.ijse.ranweli.controller;

import animatefx.animation.FadeIn;
import animatefx.animation.SlideInDown;
import animatefx.animation.SlideInLeft;
import animatefx.animation.SlideInUp;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import lk.ijse.ranweli.Mail;
import lk.ijse.ranweli.dto.TouristDto;
import lk.ijse.ranweli.model.TouristModel;
import org.controlsfx.control.Notifications;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Random;

public class TouristLoginFormController {

    public TextField txtUserId;
    public Button btnLogin;
    public Button btnSignUp;
    public PasswordField txtPassword;
    public AnchorPane root;
    public Button btnBack;
    public static String loggedUser;
    public static String loggedUserName;
    public static String attemptingUser;
    public Text txtSubText;
    public static int oneTimePassword;
    public Text txtForgetPassword;

    public String userEmail;



    public void initialize() {
        new SlideInLeft(txtSubText).play();
    }

    public void loginBtnOnAction(ActionEvent actionEvent) throws SQLException, IOException {
        String touristId = txtUserId.getText();
        String password = txtPassword.getText();
        TouristDto tourist = TouristModel.getTourist(touristId);
        if(tourist!=null && tourist.getPassword().equals(password)){
            loggedUser = txtUserId.getText();
            loggedUserName=tourist.getName();
            String touristEmail = TouristModel.getTouristEmailFromId(txtUserId.getText());
            Mail mail1 = new Mail();
            mail1.setMsg("Hi "+tourist.getName()+ ",\n\n\tUser :  "+ tourist.getIdentityDetails() +" \n\n\tNew Login Detected at :  "+ LocalDateTime.now() + " \n\nThank You,\n" +
                    "Ranweli Tours Support Team");
            mail1.setTo(touristEmail);
            mail1.setSubject("Ranweli Tours Login Detection");

            Thread thread = new Thread(mail1);
            thread.start();

            Parent root = FXMLLoader.load(getClass().getResource("/view/touristDashboard_form.fxml"));
            Scene scene1 = new Scene(root);
            Stage stage1 = (Stage) btnBack.getScene().getWindow();
            stage1.setScene(scene1);
            stage1.setTitle("Dashboard");
            stage1.centerOnScreen();

            new FadeIn(root).play();

            Notifications.create()
                    .title("Welcome "+tourist.getName())
                    .text("You successfully logged in \n Time : "+ LocalDateTime.now())
                    .position(Pos.TOP_RIGHT)
                    .showConfirm();
        }else{
            new Alert(Alert.AlertType.ERROR, "Login Failed : CHECK YOUR ID AND PASSWORD AGAIN").show();
        }


    }

    public void signUpBtnOnAction(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/touristSignUp_form.fxml"));
        Scene scene1 = new Scene(root);
        Stage stage1 = (Stage) btnBack.getScene().getWindow();
        stage1.setScene(scene1);
        stage1.setTitle("Let's Sign Up");
        stage1.centerOnScreen();

        new SlideInUp(root).play();
    }

    public void btnBackOnAction(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/welcome_form/welcome_form.fxml"));
        Scene scene1 = new Scene(root);
        Stage stage1 = (Stage) btnBack.getScene().getWindow();
        stage1.setScene(scene1);
        stage1.setTitle("Dashboard");
        stage1.centerOnScreen();

        new SlideInDown(root).play();
    }

    public static int generateOTP(){
        Random random = new Random();
        int password = random.nextInt(9000000) + 1000000;
        System.out.println(password);
        return password;
    }

    public void txtForgetPasswordOnAction(MouseEvent mouseEvent) throws SQLException {
        if(!txtUserId.getText().isEmpty()){
            if(TouristModel.getTourist(txtUserId.getText())!=null) {
                attemptingUser = txtUserId.getText();
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation");
                alert.setHeaderText("Do you want to Change Your Password ?");
                alert.setContentText("Send OTP To Your Email");

                ButtonType buttonTypeYes = new ButtonType("OK");
                ButtonType buttonTypeNo = new ButtonType("Cancel");

                alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

                alert.showAndWait().ifPresent(response -> {
                    if (response == buttonTypeYes) {
                        try {
                            oneTimePassword = generateOTP();
                            String touristEmail = TouristModel.getTouristEmailFromId(txtUserId.getText());
                            Mail mail = new Mail();
                            mail.setMsg("Hello," + "\n\n\tUser : " + touristEmail + " \n\n\tAn OTP Request Detected at :  " + LocalDateTime.now() + " \n\n\tOTP : " + oneTimePassword + " \n\nThank You,\n" +
                                    "Ranweli Tours Support Team");
                            mail.setTo(touristEmail);
                            mail.setSubject("Ranweli Tours OTP Verification");

                            Thread thread = new Thread(mail);
                            thread.start();

                            Parent root = FXMLLoader.load(getClass().getResource("/view/forgetPassword_form.fxml"));
                            Scene scene1 = new Scene(root);
                            Stage stage1 = (Stage) txtForgetPassword.getScene().getWindow();
                            stage1.setScene(scene1);
                            stage1.setTitle("OTP Verification");
                            stage1.centerOnScreen();

                            new FadeIn(root).play();
                        } catch (Exception e) {
                            System.out.println(e);
                        }
                    } else if (response == buttonTypeNo) {
                        System.out.println("User Canceled the Operation");
                    }
                });
            }else{
                new Alert(Alert.AlertType.ERROR, "Check your User ID again..").show();
            }
        }else{
            new Alert(Alert.AlertType.ERROR, "Enter your ID first !").show();
        }
    }
}
