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
import javafx.stage.FileChooser;

import java.sql.*;

import com.grp_one.Admin;

public class UserSubmissionController implements Initializable {

    SqlConnector dbConn = new SqlConnector();
    FileChooser chooser = new FileChooser();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
}
