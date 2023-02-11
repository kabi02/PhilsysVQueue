package com.grp_one.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import com.grp_one.*;

public class UserLoginController implements Initializable{
    @FXML
    private TextField emailFieldUser;

    @FXML
    private Button loginBtn;

    @FXML
    private PasswordField passFieldEmail;

    @FXML
    private Hyperlink signupLink;

    @FXML
    void userSignup(ActionEvent event) throws Exception {
        Main.setRoot("userSignup", "Sign Up");
        Main.centerRoot();
        Main.showStage();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
}
