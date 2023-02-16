package com.grp_one.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.ButtonBar.ButtonData;

import com.grp_one.*;
import java.sql.*;

public class UserLoginController implements Initializable {
    SqlConnector dbConn = new SqlConnector();
    Dialog<String> dialog = new Dialog<>();

    @FXML
    private TextField emailFieldUser;

    @FXML
    private Button loginBtn;

    @FXML
    private PasswordField passFieldUser;

    @FXML
    private Hyperlink signupLink;

    @FXML
    private void userSignup(ActionEvent event) throws Exception {
        User.setRoot("userSignup", "Sign Up");
        User.centerRoot();
        User.showStage();
    }

    @FXML
    void goToUserDashboard(ActionEvent event) throws Exception {

        Connection conn = dbConn.dbConn();
        Statement stmt = null;
        ResultSet rs = null;
        String userEmail, userPass;
        userEmail = emailFieldUser.getText();
        userPass = passFieldUser.getText();
        System.out.println("Email: " + userEmail + "\nPassword: " + userPass);
        if (conn != null) {
            try {
                String sql = "select * from admin.userlogincreds where userEmail = \"" + userEmail
                        + "\" and userPass = \""
                        + userPass + "\"";
                stmt = conn.prepareStatement(sql);
                rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    if (rs.getString("userEmail").equals(userEmail)) {
                        if (rs.getString("userPass").equals(userPass)) {
                            dialog.setContentText("Login Succesful!");
                            dialog.show();
                            User.setRoot("userdashboard", "User Dashboard");
                            User.centerRoot();
                            User.showStage();
                        } else {
                            dialog.setContentText("Incorrect Username or password.");
                            dialog.showAndWait();
                        }
                    } else {
                        dialog.setContentText("Incorrect Username or password.");
                        dialog.showAndWait();
                    }
                }
            } catch (SQLException e) {
                System.out.println(e);
            }
        } else {
            dialog.setContentText("Failed to connect.");
            dialog.showAndWait();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dialog.setTitle("Dialog");
        ButtonType type = new ButtonType("Ok", ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(type);
    }
}
