package com.grp_one.controllers;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

import com.grp_one.*;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;

public class AppStatusController
        implements Initializable {

    SqlConnector dbConn = new SqlConnector();

    @FXML
    private Label lblAppStatus;

    @FXML
    private Hyperlink linkStatusDocStat;

    @FXML
    private Hyperlink linkStatusGoBack;

    @FXML
    void backToDashboard(ActionEvent event) throws Exception {
        User.setRoot("userdashboard", "User Dashboard");
        User.centerRoot();
        User.showStage();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            Connection conn = dbConn.dbConn();
            PreparedStatement stmt = null;
            ResultSet rs = null;
            String sql = "select admin.application_status.status from admin.application_status where admin.application_status.userID = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, UserApplicationHandler.getSessionUID());
            rs = stmt.executeQuery();
            if (!rs.isBeforeFirst()) {
                lblAppStatus.setText("No Application");
            } else {
                rs.next();
                lblAppStatus.setText(rs.getString(1));
            }
        } catch (Exception e) {

        }
    }
}
