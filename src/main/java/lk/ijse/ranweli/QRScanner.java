package lk.ijse.ranweli;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.web.WebView;

import javax.swing.*;
import java.awt.image.BufferedImage;

public class QRScanner {
    private  WebView webView;
    private Label resultLabel;
    private volatile String scannedQRCodeContent;

    public QRScanner() {
        // Initialize your WebView and other components
        webView = new WebView();
        resultLabel = new Label("Scanned QR Code: ");
    }

    public void startScanner() {
        // Create a new stage for the QR code scanner
        JFrame frame = new JFrame("QR Code Scanner");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Create the WebcamPanel for live webcam view
        Webcam webcam = Webcam.getDefault();
        WebcamPanel webcamPanel = new WebcamPanel(webcam);
        webcamPanel.setPreferredSize(new java.awt.Dimension(640, 480));
        frame.add(webcamPanel);

        // Create the result label
        //frame.add(resultLabel);
        System.out.println("Scanned QR Code: " + scannedQRCodeContent);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        // Start the webcam and QR code scanning
        scannedQRCodeContent = startWebcam(webcam);

        // Close the webcam after scanning
        webcam.close();
    }

    private String startWebcam(Webcam webcam) {
        try {
            webcam.open();

            while (true) {
                if (!webcam.isOpen()) {
                    break;
                }

                Result result = scanQRCode(webcam.getImage());

                if (result != null) {
                    String qrCodeContent = result.getText();

                    // Update the result label on the JavaFX UI thread
                    Platform.runLater(() -> resultLabel.setText("Scanned QR Code: " + qrCodeContent));

                    return qrCodeContent;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Close the webcam when done
            webcam.close();
        }

        return null;
    }

    private com.google.zxing.Result scanQRCode(BufferedImage image) {
        try {
            LuminanceSource source = new BufferedImageLuminanceSource(image);
            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
            Reader reader = new MultiFormatReader();

            return reader.decode(bitmap);
        } catch (NotFoundException e) {
            // QR code not found in the image
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getScannedQRCodeContent() {
        return scannedQRCodeContent;
    }
}
