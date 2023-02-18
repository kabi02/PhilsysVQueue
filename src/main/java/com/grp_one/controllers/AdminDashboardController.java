package com.grp_one.controllers;

import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

import javafx.css.Styleable;
import javafx.event.ActionEvent;
import javafx.event.EventTarget;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import com.grp_one.Admin;

public class AdminDashboardController implements Initializable {

    @FXML
    private TableView<Object> dashboardTable;

    @FXML
    private TableColumn<CustomerInfo, String> ctnColumn;
    @FXML
    private TableColumn<CustomerInfo, String> nameColumn;
    @FXML
    private TableColumn<CustomerInfo, String> dateColumn;
    @FXML
    private TableColumn<CustomerInfo, Button> actionColumn;

    @FXML
    private void logoutUser() throws Exception {
        Admin.setRoot("adminlogin", "Login");
        Admin.centerRoot();
        Admin.showStage();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ctnColumn.setResizable(false);
        ctnColumn.setReorderable(false);
        ctnColumn.setEditable(false);

        nameColumn.setReorderable(false);
        nameColumn.setEditable(false);

        dateColumn.setResizable(false);
        dateColumn.setReorderable(false);
        dateColumn.setEditable(false);

        actionColumn.setResizable(false);
        actionColumn.setReorderable(false);
        actionColumn.setEditable(false);
    }

}