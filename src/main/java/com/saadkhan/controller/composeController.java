/**
 * Sample Skeleton for 'composePage.fxml' Controller Class
 */

package com.saadkhan.controller;

import com.saadkhan.data.EmailFxBean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Orientation;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class composeController {

    @FXML
    public MenuItem reAllTxt;
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;
    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;
    @FXML // fx:id="replyTxt"
    private MenuItem replyTxt; // Value injected by FXMLLoader
    @FXML // fx:id="toTxt"
    private Label toTxt; // Value injected by FXMLLoader
    @FXML // fx:id="closeBtn"
    private Button closeBtn; // Value injected by FXMLLoader
    @FXML // fx:id="editMn"
    private Menu editMn; // Value injected by FXMLLoader
    @FXML // fx:id="ccIn"
    private TextField ccIn; // Value injected by FXMLLoader
    @FXML // fx:id="fwdAllTxt"
    private MenuItem fwdAllTxt; // Value injected by FXMLLoader
    @FXML // fx:id="addBtn"
    private Button addBtn; // Value injected by FXMLLoader
    @FXML // fx:id="ccTxt"
    private Label ccTxt; // Value injected by FXMLLoader
    @FXML // fx:id="bccTxt"
    private Label bccTxt; // Value injected by FXMLLoader
    @FXML // fx:id="bccIn"
    private TextField bccIn; // Value injected by FXMLLoader
    @FXML // fx:id="fwdTxt"
    private MenuItem fwdTxt; // Value injected by FXMLLoader
    @FXML // fx:id="fileMn"
    private Menu fileMn; // Value injected by FXMLLoader
    @FXML // fx:id="sendBtn"
    private Button sendBtn; // Value injected by FXMLLoader
    @FXML // fx:id="toIn"
    private TextField toIn; // Value injected by FXMLLoader
    @FXML // fx:id="helpMn"
    private Menu helpMn; // Value injected by FXMLLoader
    @FXML // fx:id="subjectIn"
    private TextField subjectIn; // Value injected by FXMLLoader
    @FXML // fx:id="subjectTx"
    private Label subjectTx; // Value injected by FXMLLoader
    @FXML // fx:id="sOptionBtn"
    private SplitMenuButton sOptionBtn; // Value injected by FXMLLoader
    @FXML
    private ListView attachyHolder;

    private Stage primaryStage;
    private final EmailFxBean efb;

    private final static Logger LOG = LoggerFactory.getLogger(confController.class);
    private Locale locale;
    private List<File> list;

    public composeController() {
        efb = new EmailFxBean();
    }

    @FXML
    public void sendEmail(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Sent");
        alert.setContentText("Your Email has been succesfully sent");
        alert.showAndWait();
        close(null);
    }

    @FXML
    public void addFolder(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        try {
            list = fileChooser.showOpenMultipleDialog((Stage) closeBtn.getScene().getWindow());
            drawAttachList();
        } catch (NullPointerException e) {
            LOG.info(" File explorer closed unexpectedly");
        }
    }

    private void drawAttachList() {
        for (File f : list) {
            Button b = new Button(f.getName().length() < 15 ? f.getName() : f.getName().substring(0, 15));
            b.setId(f.getName().length() < 15 ? f.getName() : f.getName().substring(0, 15) + "Btn");
            b.setStyle("-fx-background-color: #b2c3ff; ");
            b.setOnMouseClicked(e -> {
                int spot = attachyHolder.getItems().indexOf(b);
                attachyHolder.getItems().remove(spot);
                this.list = new ArrayList<>(list);
                list.remove(spot);
            });
            attachyHolder.setOrientation(Orientation.HORIZONTAL);
            attachyHolder.getItems().add(b);
        }
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    public void initialize() {
        this.locale = new Locale("en", "US");
    }

    public void close(ActionEvent actionEvent) {
        try {
            Stage stage = (Stage) closeBtn.getScene().getWindow();
            stage.close();
            ResourceBundle rb = ResourceBundle.getBundle("Strings", locale);
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/mainPage.fxml"), rb);
            Parent root = (AnchorPane) loader.load();
            mainController controller = loader.getController();
            controller.setSceneStageController(primaryStage);
            Scene scene = new Scene(root);
            scene.getStylesheets().add("/styles/emailCSS.css");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setSceneStageController(Stage stage) {
        this.primaryStage = stage;
    }

    public void changeFrench(ActionEvent actionEvent) {
        this.locale = new Locale("en", "US");
        recreateWindow();
    }

    public void changeEnglish(ActionEvent actionEvent) {
        this.locale = new Locale("fr", "CA");
        recreateWindow();
    }

    private void recreateWindow() {
        try {
            Stage stage = (Stage) closeBtn.getScene().getWindow();
            stage.close();
            ResourceBundle rb = ResourceBundle.getBundle("Strings", locale);
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/composePage.fxml"), rb);
            Parent root = (AnchorPane) loader.load();
            composeController controller = loader.getController();
            controller.setSceneStageController(primaryStage);
            Scene scene = new Scene(root);
            scene.getStylesheets().add("/styles/emailCSS.css");
            Stage main = new Stage();
            main.setScene(scene);
            main.initModality(Modality.APPLICATION_MODAL);
            main.setTitle("JAG: Email Create");
            main.initStyle(StageStyle.UNDECORATED);
            main.show();
        } catch (IOException e) {
            LOG.error(e.getMessage());
        }
    }

    public void changeSettings(ActionEvent actionEvent) {
        try {
            ResourceBundle rb = ResourceBundle.getBundle("Strings", locale);
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/comfPage.fxml"), rb);
            Parent root = (AnchorPane) loader.load();
            confController controller = loader.getController();
            controller.setSceneStageController(primaryStage);
            //controller.setData();
            Scene scene = new Scene(root);
            scene.getStylesheets().add("/styles/emailCSS.css");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            LOG.error(e.getMessage());
        }
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }
}
