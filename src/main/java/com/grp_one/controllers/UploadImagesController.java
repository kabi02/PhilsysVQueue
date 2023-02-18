package com.grp_one.controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.grp_one.*;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class UploadImagesController
        implements Initializable {
    Dialog<String> dialog = new Dialog<>();
    SqlConnector dbConn = new SqlConnector();
    FileChooser chooser;
    File doc1, doc2, imgProfile;

    @FXML
    private Button btnDoc1;

    @FXML
    private Button btnDoc2;

    @FXML
    private Button btnUploadPic;

    @FXML
    private Hyperlink linkFinishUpload;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dialog.setTitle("Dialog");
        ButtonType type = new ButtonType("Ok", ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(type);

        btnDoc1.setMaxWidth(100.0);
        btnDoc2.setMaxWidth(100.0);
        btnUploadPic.setMaxWidth(100.0);

        btnDoc1.setOnAction(event -> {
            chooser = new FileChooser();
            chooser.getExtensionFilters().add(new ExtensionFilter("Image Files", "*.jpg", "*.png"));
            doc1 = chooser.showOpenDialog(null);
            try {
                btnDoc1.setText(doc1.getName());
            } catch (Exception e) {
            }
        });
        btnDoc2.setOnAction(event -> {
            chooser = new FileChooser();
            chooser.getExtensionFilters().add(new ExtensionFilter("Image Files", "*.jpg", "*.png"));
            doc2 = chooser.showOpenDialog(null);
            try {
                btnDoc2.setText(doc2.getName());
            } catch (Exception e) {
            }
        });
        btnUploadPic.setOnAction(event -> {
            chooser = new FileChooser();
            chooser.getExtensionFilters().add(new ExtensionFilter("Image Files", "*.jpg", "*.png"));
            imgProfile = chooser.showOpenDialog(null);
            try {
                btnUploadPic.setText(imgProfile.getName());
            } catch (Exception e) {
            }
        });
        linkFinishUpload.setOnAction(event -> {
            UserApplicationHandler.uploadInfo();
            try {
                User.setRoot("appsuccess", "Congratulations!");
            } catch (IOException e) {
            }
            User.centerRoot();
            User.showStage();
            // if (imgProfile == null || doc1 == null || doc2 == null) {
            // dialog.setContentText("Incomplete Information");
            // dialog.showAndWait();
            // } else {

            // }
        });

    }
}
