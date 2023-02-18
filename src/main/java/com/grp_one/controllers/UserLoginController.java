package com.grp_one.controllers;

import java.io.File;
import java.io.FileWriter;
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
                            UserApplicationHandler.setSessionUID(rs.getInt("userID"));
                            updateChatUserInfo();
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

    private void updateChatUserInfo() {
        String filePath = User.getResourcesPath() + "/bots/super/maps/userInfo.txt";
        File deleteFile = new File(filePath);
        deleteFile.delete();
        File writeFile = new File(filePath);
        try {
            FileWriter userInfo = new FileWriter(writeFile, false);
            Connection conn = dbConn.dbConn();
            PreparedStatement stmt = null;
            ResultSet rs = null;
            String sql = "select admin.application_status.status, admin.user_personal_info.fname from admin.user_personal_info inner join admin.application_status where admin.application_status.userID = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, UserApplicationHandler.getSessionUID());
            rs = stmt.executeQuery();
            if (!rs.isBeforeFirst()) {
                userInfo.write("name:User\nstats:unknown");
            } else {
                rs.next();
                userInfo.write("name:" + rs.getString(2) + "\nstats:" + rs.getString(1).toLowerCase());
            }
            userInfo.close();
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
