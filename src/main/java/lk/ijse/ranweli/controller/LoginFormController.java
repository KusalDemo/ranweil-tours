package lk.ijse.ranweli.controller;

import animatefx.animation.FadeIn;
import animatefx.animation.SlideInDown;
import animatefx.animation.SlideInLeft;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import lk.ijse.ranweli.Mail;
import lk.ijse.ranweli.dto.AdminDto;
import lk.ijse.ranweli.model.AdminModel;
import org.controlsfx.control.Notifications;

import java.io.IOException;
import java.time.LocalDateTime;

public class LoginFormController {

    public TextField txtUserName;
    public PasswordField txtPassword;

    public AnchorPane root;
    public Button btnLogin;
    public Button btnSignUp;
    public Button btnBack;
    public Text txtSubText;
    public static String loggedAdmin;

    public void initialize() {
        new SlideInLeft(txtSubText).play();
    }
    public void loginbtnOnAction(ActionEvent actionEvent)   {
        String email = txtUserName.getText();
        try {
            AdminDto admindto= AdminModel.searchAdmin(email);

            if(admindto != null){
                if(admindto.getPassword().equals(txtPassword.getText())){
                    //new Alert(Alert.AlertType.INFORMATION, "Login Successful").show();
                    System.out.println("Login Successful");
                    loggedAdmin=admindto.getUserName();
                    try{
                        Parent root = FXMLLoader.load(getClass().getResource("/view/dashboard_form.fxml"));
                        Scene scene1 = new Scene(root);
                        Stage stage1 = (Stage) btnLogin.getScene().getWindow();
                        stage1.setScene(scene1);
                        stage1.setTitle("Dashboard");
                        stage1.centerOnScreen();

                        new FadeIn(root).play();

                        Notifications.create()
                                .title("Welcome Administrator")
                                .text("You successfully logged in \nTime : "+ LocalDateTime.now())
                                .position(Pos.TOP_RIGHT)
                                .showConfirm();

                        Mail mail = new Mail();
                        mail.setMsg("Welcome..!"+ "\n\n\tUser :  "+ email +" \n\n\tNew Login Detected at :  "+ LocalDateTime.now() + " \n\nThank You,\n" +
                                "Ranweli Tours Support Team");
                        mail.setTo(email);
                        mail.setSubject("Ranweli Tours Login Detection");


                        Thread thread = new Thread(mail);
                        thread.start();
                    }catch (Exception e){
                        //new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
                        System.out.println(e);
                    }
                }else{
                    new Alert(Alert.AlertType.ERROR, "Login Failed : CHECK YOUR PASSWORD").show();
                    System.out.println("Login Failed");
                }
            }else{
                System.out.println("Login Failed ");
                new Alert(Alert.AlertType.ERROR,"Login Failed : CHECK YOUR EMAIL").show();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            System.out.println(e);
        }
    }


    /*@FXML
    public void signUpBtnOnAction(ActionEvent actionEvent) {
        try {
            root = FXMLLoader.load(getClass().getResource("/view/signup_form.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Let,s SignUp");
            stage.centerOnScreen();
            stage.show();
            Scene scene = new Scene(root);
            Stage stage1 = (Stage) root.getScene().getWindow();
            stage1.setScene(scene);
            stage.close();
        }catch (Exception e){
            System.out.println(e);
        }
    }*/

    public void btnBackOnAction(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/welcome_form/welcome_form.fxml"));
        Scene scene1 = new Scene(root);
        Stage stage1 = (Stage) btnBack.getScene().getWindow();
        stage1.setScene(scene1);
        stage1.setTitle("Payment Form");
        stage1.centerOnScreen();

        new SlideInDown(root).play();
    }
}
