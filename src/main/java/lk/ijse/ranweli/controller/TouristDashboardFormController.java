package lk.ijse.ranweli.controller;

import animatefx.animation.FadeIn;
import animatefx.animation.SlideInLeft;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import lk.ijse.ranweli.dto.PackageDto;
import lk.ijse.ranweli.dto.tm.PackageTm;
import lk.ijse.ranweli.model.PackageModel;
import org.controlsfx.control.Notifications;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public class TouristDashboardFormController {
    public AnchorPane root;
    public Text txtTimeNow;
    public Button btnLogOut;
    public AnchorPane btnBooking;
    public Label lblTime;
    public TableView<PackageTm> tblPackages;
    public TableColumn<?,?> colId;
    public TableColumn<?,?> colName;
    public TableColumn<?,?> colDesc;
    public TableColumn<?,?> colPrice;
    public Text txtWelcome;
    public Text txtAbout;
    public Text txtLoggedUserName;
    @FXML
    private HBox imageContainer;

    private static final String[] IMAGE_URLS = {
            "/assets/images/ranweliAd5.png",
            "/assets/images/ranweliAd2.png",
            "/assets/images/ranweliAd4.png",
            "/assets/images/ranweliAd6.png"
            // Add more image URLs as needed
    };

    private static final int IMAGE_WIDTH = 400;
    private static final int ANIMATION_DURATION = 3000;

    private int currentIndex = 0;


    public void initialize() throws SQLException {
        new SlideInLeft(txtWelcome).play();
        txtLoggedUserName.setText(TouristLoginFormController.loggedUserName);
        new SlideInLeft(txtLoggedUserName).play();
        new SlideInLeft(txtAbout).play();

        getAllPackages();
        setCellValueFactory();
        loadImages();
        startCarouselAnimation();
    }
    public void setCellValueFactory(){
        colId.setCellValueFactory(new PropertyValueFactory<>("packageId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("packageName"));
        colDesc.setCellValueFactory(new PropertyValueFactory<>("description"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
    }
    public void btnBookingOnAction(MouseEvent mouseEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/booking_form.fxml"));
        Scene scene1 = new Scene(root);
        Stage stage1 = (Stage) btnBooking.getScene().getWindow();
        stage1.setScene(scene1);
        stage1.setTitle("Let's Book");
        stage1.centerOnScreen();

        new FadeIn(root).play();
    }

    public void logOutBtnOnAction(ActionEvent actionEvent) throws IOException {
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
                    Parent root = FXMLLoader.load(getClass().getResource("/view/touristLogin_form.fxml"));
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
    public void getAllPackages() throws SQLException {
        ObservableList<PackageTm> obList = FXCollections.observableArrayList();
        List<PackageDto> allPackages = PackageModel.getAllPackages();
        for(PackageDto dto: allPackages){
            obList.add(new PackageTm(
                    dto.getPackageId(),
                    dto.getPackageName(),
                    dto.getDescription(),
                    dto.getPrice()
            ));
        }
        tblPackages.setItems(obList);
        tblPackages.refresh();
    }
    private void loadImages() {
        imageContainer.getChildren().clear(); // Clear previous images

        // Load the first image
        String firstImageUrl = IMAGE_URLS[0];
        firstImageUrl = firstImageUrl.startsWith("/") ? firstImageUrl.substring(1) : firstImageUrl;
        InputStream firstImageStream = getClass().getClassLoader().getResourceAsStream(firstImageUrl);

        if (firstImageStream != null) {
            Image firstImage = new Image(firstImageStream);
            ImageView firstImageView = new ImageView(firstImage);
            firstImageView.setFitWidth(IMAGE_WIDTH);
            firstImageView.setPreserveRatio(true);

            imageContainer.getChildren().add(firstImageView);
        } else {
            System.err.println("Failed to load the first image: " + firstImageUrl);
        }
    }

    private void showNextImage() {
        currentIndex = (currentIndex + 1) % IMAGE_URLS.length;
        String imageUrl = IMAGE_URLS[currentIndex];
        imageUrl = imageUrl.startsWith("/") ? imageUrl.substring(1) : imageUrl;
        InputStream imageStream = getClass().getClassLoader().getResourceAsStream(imageUrl);

        if (imageStream != null) {
            Image image = new Image(imageStream);
            ImageView imageView = (ImageView) imageContainer.getChildren().get(0);
            imageView.setImage(image);
        } else {
            System.err.println("Failed to load image: " + imageUrl);
        }
    }
    private void startCarouselAnimation() {
        Timeline timeline = new Timeline();
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(ANIMATION_DURATION), event -> showNextImage()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }






}
