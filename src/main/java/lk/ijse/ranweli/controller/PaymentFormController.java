package lk.ijse.ranweli.controller;

import animatefx.animation.FadeIn;
import animatefx.animation.SlideInLeft;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import lk.ijse.ranweli.Mail;
import lk.ijse.ranweli.QRGenerator;
import lk.ijse.ranweli.dto.PaymentDto;
import lk.ijse.ranweli.model.PaymentModel;
import lk.ijse.ranweli.model.TouristModel;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.view.JasperViewer;
import org.controlsfx.control.Notifications;

import java.io.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;


public class PaymentFormController {
    public Button btnLogOut;
    public AnchorPane btnBooking;
    public TextField txtImage;
    public Text txtAmount;
    public Text txtId;
    public Text txtDate;
    public LocalDate date=LocalDate.now();

    public Button btnBack;
    public ComboBox cmbMethod;
    public byte[] imageData;
    public AnchorPane btnPrintDocs;
    public ImageView imgUpload;
    public Text txtUploadImageState;
    public Text txtPayment;


    public void initialize(){
        new SlideInLeft(txtPayment).play();
        txtId.setText(generatePaymentId());
        txtAmount.setText(Double.toString(BookingFormController.selectedPackagePrice));
        txtDate.setText(LocalDate.now().toString());
        cmbMethod.getItems().addAll("CASH", "CARD", "ONLINE");
        cmbMethod.setValue("ONLINE");

    }
    public void start(Stage stage) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Your Payment Image");
        FileChooser.ExtensionFilter jpegFiles = new FileChooser.ExtensionFilter("JPEG Files", "*.jpg");
        FileChooser.ExtensionFilter pngFiles = new FileChooser.ExtensionFilter("PNG Files", "*.png");
        fileChooser.getExtensionFilters().addAll(jpegFiles, pngFiles);

        File file = fileChooser.showOpenDialog(stage);
        if(file != null){
            txtUploadImageState.setText("Image Selected");
            Notifications.create()
                    .title("Image Selected")
                    .text("Your Payment slip is ready to upload")
                    .position(Pos.TOP_RIGHT)
                    .showConfirm();
            //txtImage.setText(file.getAbsolutePath());
            FileInputStream fileInputStream = new FileInputStream(file.getAbsolutePath());
            imageData = new byte[fileInputStream.available()];
            fileInputStream.read(imageData);
        }

    }

    public void btnBookingOnAction(MouseEvent mouseEvent) {
        if(imageData != null){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Do you want to Proceed?");
            alert.setContentText("Choose your option.");

            ButtonType buttonTypeYes = new ButtonType("Yes");
            ButtonType buttonTypeNo = new ButtonType("No");

            alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

            alert.showAndWait().ifPresent(response -> {
                if (response == buttonTypeYes) {
                    try{
                        PaymentDto paymentDto = new PaymentDto(txtId.getText(), Double.parseDouble(txtAmount.getText()), "PAID", date, "ONLINE", imageData);
                        boolean isSaved = PaymentModel.savePayment(paymentDto);
                        if(isSaved){
                            boolean isTransactionCompleted = PaymentModel.updatePayment(BookingFormController.selectedVehicleId, BookingFormController.selectedHotelId, BookingFormController.selectedGuideId, BookingFormController.selectedDriverId);
                            new Alert(Alert.AlertType.CONFIRMATION, "Payment Successful").show();
                            if (isTransactionCompleted){
                                System.out.println("Transaction Completed");

                                Notifications.create()
                                        .title("Payment Completed")
                                        .text("Payment Completed Successfully : "+ txtId.getText())
                                        .position(Pos.TOP_RIGHT)
                                        .showConfirm();

                                String values =txtId.getText() + "," + txtAmount.getText() + "," + date + "," + BookingFormController.selectedVehicleId + "," +BookingFormController.selectedHotelId + "," + BookingFormController.selectedGuideId + "," + BookingFormController.selectedDriverId;

                                String filepath = "/home/kitty/Documents/qrCodes/"+ "qr"+txtId.getText() +".png";
                                boolean isGenerated = QRGenerator.generateQrCode(values, 1250, 1250, filepath);

                                String touristEmail = TouristModel.getTouristEmailFromId(TouristLoginFormController.loggedUser);
                                if(isGenerated){
                                    Mail mail = new Mail();
                                    mail.setMsg("Payment Successful..");
                                    mail.setTo(touristEmail);
                                    mail.setSubject("Ranweli Payments");
                                    mail.setFile(new File(filepath));

                                    Thread thread = new Thread(mail);
                                    thread.start();

                                    Notifications.create()
                                            .title("Payment Confirmation sent")
                                            .text("Payment Detailed QR Code is sent to your email")
                                            .position(Pos.TOP_RIGHT)
                                            .showConfirm();
                                }else{
                                    System.out.println("QR Code Not Generated");
                                }
                            }
                        }else{
                            new Alert(Alert.AlertType.ERROR, "Payment Failed").show();
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                } else if (response == buttonTypeNo) {
                    System.out.println("User clicked No");
                }
            });
        }else{
            new Alert(Alert.AlertType.ERROR, "Please Upload Payment Image").show();
        }
    }

    public void txtImageOnAction(MouseEvent mouseEvent) throws IOException {
        start(new Stage());
    }
    public String generatePaymentId() {
        String prefix = "PAY";
        long timestamp = System.currentTimeMillis();
        SimpleDateFormat dateFormat = new SimpleDateFormat("YYmmddHHmmss");
        String timestampString = dateFormat.format(new Date(timestamp));
        String payId = prefix +TouristLoginFormController.loggedUser+ timestampString;
        return payId;
    }

    public void btnBackOnAction(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Do you want to Cancel? This will erase your selections.");
        alert.setContentText("Choose your option.");

        ButtonType buttonTypeYes = new ButtonType("Yes");
        ButtonType buttonTypeNo = new ButtonType("No");

        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

        alert.showAndWait().ifPresent(response -> {
            if (response == buttonTypeYes) {
                try{
                    Parent root = FXMLLoader.load(getClass().getResource("/view/booking_form.fxml"));
                    Scene scene1 = new Scene(root);
                    Stage stage1 = (Stage) btnBack.getScene().getWindow();
                    stage1.setScene(scene1);
                    stage1.setTitle("Booking Form");
                    stage1.centerOnScreen();

                    new FadeIn(root).play();
                }catch (Exception e){
                    e.printStackTrace();
                }
            } else if (response == buttonTypeNo) {
                System.out.println("User clicked No");
            }
        });
    }

    public void btnPrintDocsOnAction(MouseEvent mouseEvent) throws JRException {
        String paymentId = txtId.getText();
        String amount = txtAmount.getText();
        String date = txtDate.getText();
        String method = String.valueOf(cmbMethod.getValue());

        if (!paymentId.isEmpty() && !amount.isEmpty() && !date.isEmpty() && !method.isEmpty()) {
            HashMap hashMap = new HashMap();
            hashMap.put("payId", paymentId);
            hashMap.put("amount", amount);
            hashMap.put("date", date);
            hashMap.put("method", method);

            try{
                InputStream resourceAsStream = getClass().getResourceAsStream("/report/paymentReceipt.jrxml");
                JasperDesign load = JRXmlLoader.load(resourceAsStream);
                JasperReport jasperReport = JasperCompileManager.compileReport(load);
                JasperPrint jasperPrint = JasperFillManager.fillReport(
                        jasperReport,
                        hashMap,
                        new JREmptyDataSource()
                );
                JasperViewer.viewReport(jasperPrint, false);

                String filePath = "/home/kitty/Documents/ProjectRanweliPaymentSlipsDoc/"+paymentId+".pdf";
                JasperExportManager.exportReportToPdfFile(jasperPrint,filePath);
                System.out.println("Receipt Saved Successfully..");

                String touristEmail = TouristModel.getTouristEmailFromId(TouristLoginFormController.loggedUser);
                Mail mail = new Mail();
                mail.setMsg("Payment Receipt for Payment : "+txtId);
                mail.setTo(touristEmail);
                mail.setSubject("Ranweli Payment Confirmation");
                mail.setFile(new File(filePath));

                Thread thread = new Thread(mail);
                thread.start();
            }catch (Exception e){
                e.printStackTrace();
            }
        }else{
            new Alert(Alert.AlertType.ERROR, "Please fill all the fields").show();
        }
    }

    public void imgUploadOnAction(MouseEvent mouseEvent) throws IOException {
        start(new Stage());
    }

    private byte[] exportJasperPrintToPDF(JasperPrint jasperPrint) throws JRException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        // Export to PDF
        JRPdfExporter exporter = new JRPdfExporter();
        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(byteArrayOutputStream));
        exporter.exportReport();

        return byteArrayOutputStream.toByteArray();
    }
}
