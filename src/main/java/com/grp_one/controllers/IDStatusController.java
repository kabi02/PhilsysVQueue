package com.grp_one.controllers;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import com.grp_one.*;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;

public class IDStatusController implements Initializable {

    SqlConnector dbConn = new SqlConnector();
    @FXML
    private Label lblAppStatus;

    @FXML
    private Label lblCTN;

    @FXML
    private Label lblIDStatus;

    @FXML
    private Hyperlink linkIDGoBack;

    @FXML
    void goToDashboard(ActionEvent event) throws Exception {
        User.setRoot("userdashboard", "User Dashboard");
        User.centerRoot();
        User.showStage();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        lblAppStatus.setAlignment(Pos.CENTER);
        try {
            Connection conn = dbConn.dbConn();
            PreparedStatement stmt = null;
            ResultSet rs = null;
            String sql = "select admin.application_status.status, admin.application_status.CTN from admin.application_status where admin.application_status.userID = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, UserApplicationHandler.getSessionUID());
            rs = stmt.executeQuery();
            if (!rs.isBeforeFirst()) {
                lblAppStatus.setText("No Application Yet!");
                lblIDStatus.setText("Pending Claim Status");
            } else {
                rs.next();
                lblAppStatus.setText(rs.getString(1));
                lblCTN.setText(rs.getString(2));
                if (rs.getString(1).equalsIgnoreCase(CustomerInfo.CLAIMING))
                    lblIDStatus.setText("Please wait for your ID!");
            }
        } catch (Exception e) {

        }

    }

}