package com.grp_one.controllers;

import com.grp_one.Main;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;

public class AppStatusController {
    @FXML
    private Label lblAppStatus;

    @FXML
    private Hyperlink linkStatusDocStat;

    @FXML
    private Hyperlink linkStatusGoBack;

    @FXML
    void backToDashboard(ActionEvent event) throws Exception{
        Main.setRoot("userdashboard", "User Dashboard");
        Main.centerRoot();
        Main.showStage();
    }
}
