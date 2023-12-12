package lk.ijse.ranweli.controller.WelcomeFormControllers;

import animatefx.animation.SlideInRight;
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
import javafx.util.Duration;

import java.io.IOException;

public class GalleryFormController {
    public Button btnPrev;
    public AnchorPane anchorRoot;
    public ImageView imgRanweli;


    public void initialize(){
      new SlideInRight(imgRanweli).play();
    }

    public void btnPrevOnAction(MouseEvent mouseEvent) throws IOException {
        Parent root= FXMLLoader.load(getClass().getResource("/view/welcome_form/services_form.fxml"));
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
}
