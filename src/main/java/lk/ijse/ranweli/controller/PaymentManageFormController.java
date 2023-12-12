package lk.ijse.ranweli.controller;

import animatefx.animation.FadeIn;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lk.ijse.ranweli.db.DbConnection;
import lk.ijse.ranweli.dto.PaymentDto;
import lk.ijse.ranweli.dto.tm.PaymentTm;
import lk.ijse.ranweli.model.PaymentModel;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;

public class PaymentManageFormController {

    public TextField txtPayId;
    public TextField txtPaymentType;
    public TextField txtDate;
    public TextField txtAmount;
    public TableView<PaymentTm> tblPayment;

    public TableColumn<?,?> colId;
    public TableColumn<?,?> colAmount;
    public TableColumn<?,?> colStatus;
    public TableColumn<?,?> colDate;
    public TableColumn<?,?> colMethod;
    public Button btnView;
    public Button btnBack;
    public Button btnReport;

    public void initialize() throws SQLException {
        setAllPayments();
        setCellValueFactory();

        tblPayment.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.intValue() >= 0) {
                PaymentTm selectedPayment = tblPayment.getItems().get(newValue.intValue());
                txtPayId.setText(selectedPayment.getPayId());
                txtAmount.setText(String.valueOf(selectedPayment.getAmount()));
                txtDate.setText(String.valueOf(selectedPayment.getDate()));
                txtPaymentType.setText(selectedPayment.getMethod());
            }
        });
    }
    public void btnViewOnAction(ActionEvent actionEvent) throws SQLException {
        String id= txtPayId.getText();
        Image receipt = PaymentModel.getReceipt(id);
        try{
            ImageView imageView = new ImageView(receipt);
            //ScrollPane scrollPane = new ScrollPane(imageView);
            imageView.setPreserveRatio(false);
            imageView.setFitWidth(800);
            imageView.setFitHeight(600);

            Stage childStage = new Stage();
            childStage.initStyle(StageStyle.UTILITY);
            childStage.setTitle("Image Viewer");

            Scene scene = new Scene(new ScrollPane(imageView), 800, 600);
            childStage.setScene(scene);

            childStage.show();
        }catch (Exception e){
            System.out.println(e);
        }
    }

    public void clearBtnOnAction(ActionEvent actionEvent) {
        clearFields();
    }
    public void setAllPayments() throws SQLException {
        ObservableList<PaymentTm> obList = FXCollections.observableArrayList();
        ArrayList<PaymentDto> allPayments = PaymentModel.getAllPayments();

        for (PaymentDto dto : allPayments){
            obList.add(new PaymentTm(
                    dto.getPayId(),
                    dto.getAmount(),
                    dto.getStatus(),
                    dto.getDate(),
                    dto.getMethod(),
                    dto.getReceipt()
            ));
        }
        tblPayment.setItems(obList);
        tblPayment.refresh();
    }

    public void setCellValueFactory(){
        colId.setCellValueFactory(new PropertyValueFactory<>("payId"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colMethod.setCellValueFactory(new PropertyValueFactory<>("method"));
    }

    public void backBtnOnAction(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/dashboard_form.fxml"));
        Scene scene1 = new Scene(root);
        Stage stage1 = (Stage) btnBack.getScene().getWindow();
        stage1.setScene(scene1);
        stage1.setTitle("Dashboard Form");
        stage1.centerOnScreen();

        new FadeIn(root).play();
    }

    public void clearFields(){
        txtPayId.clear();
        txtPaymentType.clear();
        txtDate.clear();
        txtAmount.clear();
    }

    public void btnReportOnAction(ActionEvent actionEvent) throws JRException, SQLException {
        InputStream resourceAsStream = getClass().getResourceAsStream("/report/paymentReport.jrxml");
        JasperDesign load = JRXmlLoader.load(resourceAsStream);
        JasperReport jasperReport = JasperCompileManager.compileReport(load);
        JasperPrint jasperPrint = JasperFillManager.fillReport(
                jasperReport,
                null,
                DbConnection.getInstance().getConnection());
        JasperViewer.viewReport(jasperPrint, false);
    }

}
