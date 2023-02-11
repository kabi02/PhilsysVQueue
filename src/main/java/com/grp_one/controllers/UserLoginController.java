package com.grp_one.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import com.grp_one.*;
import java.sql.*;

public class UserLoginController implements Initializable{
    SqlConnector dbConn = new SqlConnector();

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
        Main.setRoot("userSignup", "Sign Up");
        Main.centerRoot();
        Main.showStage();
    }

    @FXML
    void goToUserDashboard(ActionEvent event) throws Exception{
        Connection conn = dbConn.dbConn();
        Statement stmt = null;
        ResultSet rs = null;
        String userEmail, userPass;
        userEmail = emailFieldUser.getText();
        userPass = passFieldUser.getText();
        System.out.println("Email: " + userEmail + "\nPassword: " + userPass);
        try {
            String sql = "select * from user.userlogincreds where userEmail = \"" + userEmail + "\" and userPass = \"" + userPass + "\"";
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery(sql);
            while(rs.next()) {
                if(rs.getString("userEmail").equals(userEmail)) {
                    if(rs.getString("userPass").equals(userPass)) {
                        JOptionPane.showMessageDialog(null, "Login Success");
                    }
                    else {
                        JOptionPane.showMessageDialog(null, "Incorrect Username/Password!");
                    }
                }
                else {
                    JOptionPane.showMessageDialog(null, "Incorrect Username/Password!");
                }
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
}
