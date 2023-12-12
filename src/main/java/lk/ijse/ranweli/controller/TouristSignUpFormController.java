package lk.ijse.ranweli.controller;

import animatefx.animation.FadeIn;
import animatefx.animation.SlideInDown;
import animatefx.animation.SlideInLeft;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import lk.ijse.ranweli.dto.TouristDto;
import lk.ijse.ranweli.model.TouristModel;
import lk.ijse.ranweli.regex;

public class TouristSignUpFormController {

    public AnchorPane root;
    public TextField txtUserName;
    public Button btnSignUp;
    public Button btnAlreadyHaveAnAccount;
    public PasswordField txtPassword;
    public TextField txtId;
    public TextField txtRePassword;
    public Text txtSubText;
    public TextField txtEmail;

    public void initialize() {
        new SlideInLeft(txtSubText).play();
    }

    public void signUpBtnOnAction(ActionEvent actionEvent) {
        String userId=txtId.getText();
        String userName=txtUserName.getText();
        String password=txtPassword.getText();
        String rePassword=txtRePassword.getText();
        String email=txtEmail.getText();

        regex regex = new regex();
        boolean isIdNumMatched = regex.isIdNumValid(userId);
        boolean isEmailAddressMathced = regex.isEmailValid(email);
        if(isIdNumMatched){
           if(isEmailAddressMathced){
               if(userId!=null&&userName!=null&&password!=null&&rePassword!=null&&isEmailAddressMathced) {
                   if (password.equals(rePassword)) {
                       boolean passwordValid = regex.isPasswordValid(txtPassword.getText());
                       System.out.println(passwordValid);
                       if (!passwordValid) {
                           new Alert(Alert.AlertType.ERROR, "Password must be containing at least one uppercase, one lowercase, one digit and one special character").show();
                       }else{
                           TouristDto touristDto = new TouristDto(userId, userName, password,email);
                           try {
                               boolean isSaved = TouristModel.saveTourist(touristDto);
                               if (isSaved) {
                                   clearFields();
                                   new Alert(Alert.AlertType.INFORMATION, "Sign Up Successful").showAndWait();
                                   Parent root = FXMLLoader.load(getClass().getResource("/view/touristDashboard_form.fxml"));
                                   Scene scene1 = new Scene(root);
                                   Stage stage1 = (Stage) btnSignUp.getScene().getWindow();
                                   stage1.setScene(scene1);
                                   stage1.setTitle("Dashboard");
                                   stage1.centerOnScreen();

                                   new FadeIn(root).play();
                               } else {
                                   new Alert(Alert.AlertType.ERROR, "Sign Up Failed").show();
                               }
                           } catch (Exception e) {
                               e.printStackTrace();
                           }
                       }
                   } else {
                       new Alert(Alert.AlertType.ERROR, "Passwords do not match").show();
                       txtRePassword.clear();
                   }
               }else{
                   new Alert(Alert.AlertType.ERROR, "All fields are required").show();
               }
           }else{
               new Alert(Alert.AlertType.ERROR, "Invalid Email Address").show();
           }
        }else{
            new Alert(Alert.AlertType.ERROR, "Invalid ID Number").show();
        }
    }
    public void alreadyHaveAnAccountBtnOnAction(ActionEvent actionEvent){
       try {
           Parent root = FXMLLoader.load(getClass().getResource("/view/touristLogin_form.fxml"));
           Scene scene1 = new Scene(root);
           Stage stage1 = (Stage) btnAlreadyHaveAnAccount.getScene().getWindow();
           stage1.setScene(scene1);
           stage1.setTitle("Login Form");
           stage1.centerOnScreen();

           new SlideInDown(root).play();
       }catch (Exception e){
           e.printStackTrace();
       }
    }
    public void clearFields(){
        txtId.clear();
        txtPassword.clear();
        txtRePassword.clear();
        txtUserName.clear();
    }
}
