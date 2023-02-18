package com.grp_one.controllers;

import com.grp_one.SqlConnector;

import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import java.sql.*;

import com.grp_one.Admin;

public class AdminLoginController implements Initializable {

    SqlConnector dbConn = new SqlConnector();

    @FXML
    private TextField userField;

    @FXML
    private PasswordField passField;

    @FXML
    private void adminLogin(ActionEvent event) throws Exception {
        String adminUser, adminPass;
        adminUser = userField.getText();
        adminPass = passField.getText();
        System.out.println("Username: " + adminUser + "\nPassword: " + adminPass);
        try {
            Connection conn = dbConn.dbConn();
            String sql = "select * from admin.admin_acc where admin_un = \"" + adminUser + "\" and admin_pw = \""
                    + adminPass + "\"";
            Statement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                if (rs.getString("admin_un").equals(adminUser)) {
                    if (rs.getString("admin_pw").equals(adminPass)) {
                        JOptionPane.showMessageDialog(null, "Login Success");
                        Admin.setRoot("admindashboard", "Dashboard");
                        Admin.centerRoot();
                        Admin.showStage();
                    } else {
                        JOptionPane.showMessageDialog(null, "Incorrect Username/Password!");
                    }
                } else {
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
