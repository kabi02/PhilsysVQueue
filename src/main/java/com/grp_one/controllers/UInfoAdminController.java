package com.grp_one.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

import com.grp_one.*;

public class UInfoAdminController implements Initializable {
    SqlConnector dbConn = new SqlConnector();

    @FXML
    private TextField addressCity;

    @FXML
    private TextField addressCountry;

    @FXML
    private TextField addressHLB;

    @FXML
    private TextField addressProv;

    @FXML
    private TextField addressRFU;

    @FXML
    private TextField addressStreet;

    @FXML
    private TextField addressSub;

    @FXML
    private Button btnAccept;
    @FXML
    private Button decline;

    @FXML
    private TextField regInfoContact;

    @FXML
    private TextField txtBloodType;

    @FXML
    private TextField txtDob;

    @FXML
    private TextField txtFieldCity;

    @FXML
    private TextField txtFieldCountry;

    @FXML
    private TextField txtFieldFN;

    @FXML
    private TextField txtFieldLN;
    @FXML
    private Hyperlink linkBack2Dash;

    @FXML
    private TextField txtFieldMN;

    @FXML
    private TextField txtFieldProv;

    @FXML
    private TextField txtIsFilo;

    @FXML
    private TextField txtMStatus;

    @FXML
    private TextField txtSex;

    @FXML
    void backToDash(ActionEvent event) throws Exception {
        Admin.setRoot("admindashboard", "Admin Dashboard");
        Admin.centerRoot();
        Admin.showStage();
    }

    private void showDeets() {
        try {
            String ln;
            Connection conn = dbConn.dbConn();
            PreparedStatement pst = null;
            ResultSet rs = null;
            String query = "SELECT * FROM admin.user_personal_info LEFT JOIN admin.application_status ON admin.user_personal_info.userID = admin.application_status.userID WHERE admin.application_status.CTN = ?;";
            String query2 = "SELECT * FROM admin.user_address LEFT JOIN admin.application_status ON admin.user_address.userID = admin.application_status.userID WHERE admin.application_status.CTN = ?;";
            pst = conn.prepareStatement(query);
            pst.setString(1, AdminDashboardController.selection.getCTN());
            rs = pst.executeQuery();

            while (rs.next()) {
                txtFieldLN.setText(rs.getString("lname").toString());
                txtFieldFN.setText(rs.getString("fname").toString());
                txtFieldMN.setText(rs.getString("mname").toString());
                txtSex.setText(rs.getString("sex").toString());
                txtDob.setText(rs.getDate("birthdate").toString());
                txtBloodType.setText(rs.getString("blood_type").toString());
                txtMStatus.setText(rs.getString("marital_status").toString());
                txtIsFilo.setText(rs.getString("nationality").toString());
                txtFieldCity.setText(rs.getString("pob_city"));
                txtFieldProv.setText(rs.getString("pob_province"));
                txtFieldCountry.setText(rs.getString("pob_country"));
                regInfoContact.setText(rs.getString("contact"));
            }
            pst = conn.prepareStatement(query2);
            pst.setString(1, AdminDashboardController.selection.getCTN());
            rs = pst.executeQuery();
            while (rs.next()) {
                addressRFU.setText(rs.getString("unit_no"));
                addressHLB.setText(rs.getString("house_no"));
                addressStreet.setText(rs.getString("street"));
                addressSub.setText(rs.getString("barangay_or_subd"));
                addressCity.setText(rs.getString("city"));
                addressProv.setText(rs.getString("province"));
                addressCountry.setText(rs.getString("country"));
            }

        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        showDeets();
        btnAccept.setOnMouseClicked(event -> {
            try {
                Connection conn = dbConn.dbConn();
                PreparedStatement stmt = null;
                ResultSet rs = null;
                String sql = "select admin.application_status.status from admin.application_status where admin.application_status.CTN = ?";
                stmt = conn.prepareStatement(sql);
                stmt.setString(1, AdminDashboardController.selection.getCTN());
                rs = stmt.executeQuery();
                rs.next();
                if (rs.getString(1).equalsIgnoreCase(CustomerInfo.PROCESS)) {
                    sql = "update admin.application_status set status=? where admin.application_status.CTN = ?";
                    stmt = conn.prepareStatement(sql);
                    stmt.setString(1, CustomerInfo.BIOMETRICS);
                    stmt.setString(2, AdminDashboardController.selection.getCTN());
                    stmt.executeUpdate();
                } else if (rs.getString(1).equalsIgnoreCase(CustomerInfo.BIOMETRICS)) {
                    sql = "update admin.application_status set status=? where admin.application_status.CTN = ?";
                    stmt = conn.prepareStatement(sql);
                    stmt.setString(1, CustomerInfo.CLAIMING);
                    stmt.setString(2, AdminDashboardController.selection.getCTN());
                    stmt.executeUpdate();
                } else if (rs.getString(1).equalsIgnoreCase(CustomerInfo.CLAIMING)) {
                    sql = "update admin.application_status set status=? where admin.application_status.CTN = ?";
                    stmt = conn.prepareStatement(sql);
                    stmt.setString(1, CustomerInfo.CLAIMED);
                    stmt.setString(2, AdminDashboardController.selection.getCTN());
                    stmt.executeUpdate();

                }
                Admin.setRoot("admindashboard", "Admin Dashboard");
                Admin.centerRoot();
                Admin.showStage();
            } catch (Exception e) {

            }
        });
        decline.setOnMouseClicked(event -> {
            try {
                Connection conn = dbConn.dbConn();
                PreparedStatement stmt = null;
                String sql = "update admin.application_status set status=? where admin.application_status.CTN = ?";
                stmt = conn.prepareStatement(sql);
                stmt.setString(1, CustomerInfo.DECLINED);
                stmt.setString(2, AdminDashboardController.selection.getCTN());
                stmt.executeUpdate();
                Admin.setRoot("admindashboard", "Admin Dashboard");
                Admin.centerRoot();
                Admin.showStage();
            } catch (Exception e) {

            }
        });
    }

}
