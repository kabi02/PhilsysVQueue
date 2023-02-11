package com.grp_one.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.sql.*;

import javax.swing.JOptionPane;

import com.grp_one.*;

public class UserSignupController {

    SqlConnector dbConn = new SqlConnector();

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
            String insUserData = "INSERT INTO user.userlogincreds (userID, userEmail, userPass) VALUES(null,?,?)";
            PreparedStatement stmt = conn.prepareStatement(insUserData);
            if(userPass.equals(confirmPass)) {
                stmt.setString(1, userEmail);
                stmt.setString(2, userPass);
                stmt.executeUpdate();
                JOptionPane.showMessageDialog(null, "Registered Successfully!");
                emailFieldSignup.setText("");
                passwordFieldSignup.setText("");
                confPassFieldSignup.setText("");
            }
            else {
                JOptionPane.showMessageDialog(null, "Passwords do not match!");
            }
        } catch(SQLException e) {
            System.out.println(e);
        }
    }
    @FXML
    void backToLogin(ActionEvent event) throws Exception{
        Main.setRoot("userlogin", "User Login");
        Main.centerRoot();
        Main.showStage();
    } 
}
