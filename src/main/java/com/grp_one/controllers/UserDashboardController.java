package com.grp_one.controllers;

import javax.swing.JOptionPane;

import com.grp_one.Main;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;


public class UserDashboardController {
    @FXML
    private Button appstatusbtn;

    @FXML
    private Hyperlink chatbot;

    @FXML
    private Button idapplybtn;

    @FXML
    private Button idstatusbtn;

    @FXML
    private Hyperlink logoutuser;

    @FXML
    void backToUserLogin(ActionEvent event) throws Exception{
        int confirm = JOptionPane.showConfirmDialog(null, "Do you really want to logout?");
        if(confirm == 0) {
            Main.setRoot("userlogin", "User Login");
            Main.centerRoot();
            Main.showStage();
        }
    }
}
