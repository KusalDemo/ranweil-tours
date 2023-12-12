package lk.ijse.ranweli.controller.WelcomeFormControllers;

import animatefx.animation.SlideInLeft;
import animatefx.animation.SlideInRight;
import animatefx.animation.SlideInUp;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class ServicesFormController {
    public AnchorPane anchorRoot;
    public Button btnPrev;
    public Button btnGallery;
    public Button btnGetStarted;
    public Text txtOurServices;
    public Text txtAboutServices;
    public Text txtService1;
    public Text txtAboutService1;
    public Text txtService2;
    public Text txtAboutService2;
    public Text txtService3;
    public Text txtAboutService3;
    public Text txtService4;
    public Text txtAboutService4;
    public Text txtGallery;
    public ImageView imgServices;

    public void initialize(){
        new SlideInLeft(txtOurServices).play();
        new SlideInLeft(txtAboutServices).play();
        new SlideInLeft(txtService1).play();
        new SlideInLeft(txtAboutService1).play();
        new SlideInLeft(txtService2).play();
        new SlideInLeft(txtAboutService2).play();
        new SlideInLeft(txtService3).play();
        new SlideInLeft(txtAboutService3).play();
        new SlideInLeft(txtService4).play();
        new SlideInLeft(txtAboutService4).play();
        new SlideInRight(txtGallery).play();
    }

    public void btnPrevOnAction(MouseEvent mouseEvent) throws IOException {
        Parent root= FXMLLoader.load(getClass().getResource("/view/welcome_form/welcome_form.fxml"));
        Scene scene= btnPrev.getScene();

        root.translateYProperty().set(-scene.getHeight());
        StackPane parentContainer=(StackPane) scene.getRoot();
        parentContainer.getChildren().add(root);

        Timeline timeline=new Timeline();
        KeyValue kv = new KeyValue(root.translateYProperty(), 0, Interpolator.EASE_BOTH);
        KeyFrame kf = new KeyFrame(Duration.seconds(1), kv);
        timeline.getKeyFrames().add(kf);
        timeline.setOnFinished(event1 -> {
            parentContainer.getChildren().remove(anchorRoot);
        });
        timeline.play();
    }
    public void btnGalleryOnAction(MouseEvent mouseEvent) throws IOException {
        Parent root= FXMLLoader.load(getClass().getResource("/view/welcome_form/gallery_form.fxml"));
        Scene scene= btnGallery.getScene();

        root.translateYProperty().set(scene.getHeight());
        StackPane parentContainer=(StackPane) scene.getRoot();
        parentContainer.getChildren().add(root);

        Timeline timeline=new Timeline();
        KeyValue kv = new KeyValue(root.translateYProperty(), 0, Interpolator.EASE_BOTH);
        KeyFrame kf = new KeyFrame(Duration.seconds(1), kv);
        timeline.getKeyFrames().add(kf);
        timeline.setOnFinished(event1 -> {
            parentContainer.getChildren().remove(anchorRoot);
        });
        timeline.play();
    }
    public void btnGetStartedOnAction(MouseEvent mouseEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/touristSignUp_form.fxml"));
        Scene scene1 = new Scene(root);
        Stage stage1 = (Stage) btnGetStarted.getScene().getWindow();
        stage1.setScene(scene1);
        stage1.setTitle("Dashboard");
        stage1.centerOnScreen();

        new SlideInUp(root).play();
    }
}
