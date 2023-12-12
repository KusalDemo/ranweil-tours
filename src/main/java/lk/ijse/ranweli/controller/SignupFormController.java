package lk.ijse.ranweli.controller;

import animatefx.animation.SlideInLeft;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.util.Duration;
import lk.ijse.ranweli.dto.AdminDto;
import lk.ijse.ranweli.model.AdminModel;

import java.io.IOException;

public class SignupFormController {
    public TextField txtUserName;
    public PasswordField txtPassword;
    public TextField txtEmail;
    public TextField txtRole;
    public AnchorPane root;
    public Button btnSignUp;
    public Button btnAlreadyHaveAnAccount;
    public Text txtSubText;

    public void initialize() {
        new SlideInLeft(txtSubText).play();
    }
    public void signUpbtnOnAction(ActionEvent actionEvent) {
            String email=txtUserName.getText();
            String userName=txtEmail.getText();
            String password=txtPassword.getText();
            String role=txtRole.getText();

            AdminDto dto =new AdminDto(email,userName,password,role);

            try{
                boolean isSaved = AdminModel.saveAdmin(dto);
                if(isSaved){
                    new Alert(Alert.AlertType.INFORMATION, "Sign Up Successful").show();
                }
            }catch (Exception e){
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
            }
    public void alreadyHaveAnAccountbtnOnAction(ActionEvent actionEvent) throws IOException {
        Parent root= FXMLLoader.load(getClass().getResource("/view/welcome_form/signup_form.fxml"));
        Scene scene= btnAlreadyHaveAnAccount.getScene();

        root.translateYProperty().set(-scene.getHeight());
        StackPane parentContainer=(StackPane) scene.getRoot();
        parentContainer.getChildren().add(root);

        Timeline timeline=new Timeline();
        KeyValue kv = new KeyValue(root.translateYProperty(), 0, Interpolator.EASE_BOTH);
        KeyFrame kf = new KeyFrame(Duration.seconds(1), kv);
        timeline.getKeyFrames().add(kf);
        timeline.setOnFinished(event1 -> {
            parentContainer.getChildren().remove(root);
        });
        timeline.play();
    }
}



