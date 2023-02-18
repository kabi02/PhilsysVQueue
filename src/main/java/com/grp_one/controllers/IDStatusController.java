package com.grp_one.controllers;

import com.grp_one.*;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;

public class IDStatusController {

    @FXML
    private Label lblAppStatus;

    @FXML
    private Label lblCTN;

    @FXML
    private Label lblIDStatus;

    @FXML
    private Hyperlink linkIDGoBack;

    @FXML
    void goToDashboard(ActionEvent event) throws Exception {
        User.setRoot("userdashboard", "User Dashboard");
        User.centerRoot();
        User.showStage();
    }

}