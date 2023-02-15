package com.grp_one.controllers;

import java.io.IOException;
import java.net.URL;
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
import javafx.scene.control.Hyperlink;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class UserDashboardController implements Initializable {

    private boolean botInit = false;
    private Application chatBotInstance = new ChatBot();
    private Stage chatBotStage = new Stage();

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

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }
}
