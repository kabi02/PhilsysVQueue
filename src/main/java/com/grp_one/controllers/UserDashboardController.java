package com.grp_one.controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import com.grp_one.User;
import com.grp_one.ChatBot;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.stage.Modality;
import javafx.stage.Stage;

import com.grp_one.*;

public class UserDashboardController implements Initializable {

    private boolean botInit = false;
    SqlConnector dbConn = new SqlConnector();
    private Application chatBotInstance = new ChatBot();
    private Stage chatBotStage = new Stage();
    Dialog<String> dialog = new Dialog<>();

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
    void openAppStatus(ActionEvent event) throws Exception {
        User.setRoot("appstatus", "Check Application Status");
        User.centerRoot();
        User.showStage();
    }

    @FXML
    void openIDStatus(ActionEvent event) throws Exception {
        User.setRoot("idstatus", "Check ID Status");
        User.centerRoot();
        User.showStage();
    }

    @FXML
    private void openChatbot(final ActionEvent event) throws Exception {
        Platform.runLater(new Runnable() {
            public void run() {
                try {
                    if (!botInit) {
                        chatBotStage.initModality(Modality.APPLICATION_MODAL);
                        chatBotInstance.start(chatBotStage);
                        botInit = true;
                    } else {
                        chatBotStage.show();
                    }
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });
    }

    @FXML
    void backToUserLogin(ActionEvent event) throws Exception {
        int confirm = JOptionPane.showConfirmDialog(null, "Do you really want to logout?");
        if (confirm == 0) {
            User.setRoot("userlogin", "User Login");
            User.centerRoot();
            User.showStage();
        }
    }

    @FXML
    void openRegForm(ActionEvent event) throws Exception {
        try {
            Connection conn = dbConn.dbConn();
            PreparedStatement stmt = null;
            ResultSet rs = null;
            String sql = "select admin.application_status.status from admin.application_status where admin.application_status.userID = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, UserApplicationHandler.getSessionUID());
            rs = stmt.executeQuery();
            if (!rs.isBeforeFirst()) {
                User.setRoot("userinfo", "Registration Form");
                User.centerRoot();
                User.showStage();
            } else {
                rs.next();
                if (rs.getString(1).equalsIgnoreCase(CustomerInfo.DECLINED)) {
                    User.setRoot("userinfo", "Registration Form");
                    User.centerRoot();
                    User.showStage();
                } else if (rs.getString(1).equalsIgnoreCase(CustomerInfo.PROCESS)) {
                    dialog.setContentText("Application being Processed!");
                    dialog.showAndWait();
                } else {
                    dialog.setContentText("Application Processed!");
                    dialog.showAndWait();
                }
            }
        } catch (Exception e) {

        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dialog.setTitle("Dialog");
        ButtonType type = new ButtonType("Ok", ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(type);
    }
}
