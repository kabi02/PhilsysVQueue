package com.grp_one.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.css.Styleable;
import javafx.event.ActionEvent;
import javafx.event.EventTarget;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import com.grp_one.Main;

public class DashboardController implements Initializable {

    @FXML
    private TableView<Object> dashboardTable;

    @FXML
    private TableColumn<String, String> ctnColumn;
    @FXML
    private TableColumn<String, String> nameColumn;
    @FXML
    private TableColumn<String, String> dateColumn;
    @FXML
    private TableColumn<Button, Button> actionColumn;

    @FXML
    private void logoutUser() throws Exception {
        Main.setRoot("login", "Login");
        Main.centerRoot();
        Main.showStage();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ctnColumn.setResizable(false);
        ctnColumn.setReorderable(false);
        ctnColumn.setEditable(false);

        nameColumn.setResizable(false);
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