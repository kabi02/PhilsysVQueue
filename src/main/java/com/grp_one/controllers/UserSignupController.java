package com.grp_one.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.ButtonBar.ButtonData;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import com.grp_one.*;

public class UserSignupController
        implements Initializable {

    SqlConnector dbConn = new SqlConnector();
    Dialog<String> dialog = new Dialog<>();

    @FXML
    private Button btnSignup;

    @FXML
    private PasswordField confPassFieldSignup;

    @FXML
    private TextField emailFieldSignup;

    @FXML
    private PasswordField passwordFieldSignup;

    @FXML
    private void getDetails(ActionEvent event) throws Exception {
        String userEmail, userPass, confirmPass;
        userEmail = emailFieldSignup.getText();
        userPass = passwordFieldSignup.getText();
        confirmPass = confPassFieldSignup.getText();

        try {
            Connection conn = dbConn.dbConn();
            String insUserData = "INSERT INTO admin.userlogincreds (userID, userEmail, userPass) VALUES(null,?,?)";
            PreparedStatement stmt = conn.prepareStatement(insUserData);
            if (userPass.equals(confirmPass)) {
                stmt.setString(1, userEmail);
                stmt.setString(2, userPass);
                stmt.executeUpdate();
                dialog.setContentText("Registered Successfully!");
                dialog.showAndWait();
                emailFieldSignup.setText("");
                passwordFieldSignup.setText("");
                confPassFieldSignup.setText("");
            } else {
                dialog.setContentText("Passwords do not match!");
                dialog.showAndWait();
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    @FXML
    void backToLogin(ActionEvent event) throws Exception {
        Main.setRoot("userlogin", "User Login");
        Main.centerRoot();
        Main.showStage();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dialog.setTitle("Dialog");
        ButtonType type = new ButtonType("Ok", ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(type);

    }
}
