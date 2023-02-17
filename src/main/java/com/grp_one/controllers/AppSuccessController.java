package com.grp_one.controllers;

import com.grp_one.Main;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class AppSuccessController {

    @FXML
    private Button okButton;

    @FXML
    void backToDashboard(ActionEvent event) throws Exception{
        Main.setRoot("userdashboard", "User Dashboard");
        Main.centerRoot();
        Main.showStage();
    }

}
