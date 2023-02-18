package com.grp_one.controllers;

import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.function.Function;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.css.Styleable;
import javafx.event.ActionEvent;
import javafx.event.EventTarget;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

import com.grp_one.*;

public class AdminDashboardController implements Initializable {

    SqlConnector dbConn = new SqlConnector();

    @FXML
    private TableView<CustomerInfo> dashboardTable;

    @FXML
    private Label requests;
    @FXML
    private Label biometrics;
    @FXML
    private Label claiming;
    @FXML
    private Label finished;

    @FXML
    private TableColumn<CustomerInfo, String> ctnColumn;
    @FXML
    private TableColumn<CustomerInfo, String> nameColumn;
    @FXML
    private TableColumn<CustomerInfo, LocalDate> dateColumn;
    @FXML
    private TableColumn<CustomerInfo, String> actionColumn;

    @FXML
    private void logoutUser() throws Exception {
        Admin.setRoot("adminlogin", "Login");
        Admin.centerRoot();
        Admin.showStage();
    }

    private void initTable() {
        ctnColumn.setCellValueFactory(new PropertyValueFactory<CustomerInfo, String>("CTN"));
        ctnColumn.setResizable(false);
        ctnColumn.setReorderable(false);
        ctnColumn.setEditable(false);

        nameColumn.setCellValueFactory(new PropertyValueFactory<CustomerInfo, String>("Name"));
        nameColumn.setReorderable(false);
        nameColumn.setEditable(false);

        dateColumn.setCellValueFactory(new PropertyValueFactory<CustomerInfo, LocalDate>("Date"));
        dateColumn.setResizable(false);
        dateColumn.setReorderable(false);
        dateColumn.setEditable(false);

        actionColumn.setResizable(false);
        actionColumn.setReorderable(false);
        actionColumn.setEditable(false);
    }

    private void finishedTable() {
        System.out.println("HELLO WORLD");
        ObservableList<CustomerInfo> data = FXCollections.observableArrayList();
        dashboardTable.setItems(null);
        try {
            Connection conn = dbConn.dbConn();
            Statement stmt = null;
            ResultSet rs = null;
            String sql = "SELECT `name`, `CTN`, `transaction_date` FROM admin.user_personal_info INNER JOIN admin.application_status WHERE admin.user_personal_info.userID = admin.application_status.userID and admin.application_status.status = \"Claimed\"";
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                System.out.println("HELLO WORLD");
                data.add(new CustomerInfo(
                        rs.getString("name"),
                        rs.getString("CTN"),
                        rs.getDate("transaction_date").toLocalDate()));
                dashboardTable.setItems(data);
            }
        } catch (Exception e) {

        }
    }

    private void claimingTable() {
        System.out.println("HELLO WORLD");
        ObservableList<CustomerInfo> data = FXCollections.observableArrayList();
        dashboardTable.setItems(null);
        try {
            Connection conn = dbConn.dbConn();
            Statement stmt = null;
            ResultSet rs = null;
            String sql = "SELECT `name`, `CTN`, `transaction_date` FROM admin.user_personal_info INNER JOIN admin.application_status WHERE admin.user_personal_info.userID = admin.application_status.userID and admin.application_status.status = \"Claiming\"";
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                System.out.println("HELLO WORLD");
                data.add(new CustomerInfo(
                        rs.getString("name"),
                        rs.getString("CTN"),
                        rs.getDate("transaction_date").toLocalDate()));
                dashboardTable.setItems(data);
            }
        } catch (Exception e) {

        }
    }

    private void biometricsTable() {
        System.out.println("HELLO WORLD");
        ObservableList<CustomerInfo> data = FXCollections.observableArrayList();
        dashboardTable.setItems(null);
        try {
            Connection conn = dbConn.dbConn();
            Statement stmt = null;
            ResultSet rs = null;
            String sql = "SELECT `name`, `CTN`, `transaction_date` FROM admin.user_personal_info INNER JOIN admin.application_status WHERE admin.user_personal_info.userID = admin.application_status.userID and admin.application_status.status = \"Biometrics\"";
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                System.out.println("HELLO WORLD");
                data.add(new CustomerInfo(
                        rs.getString("name"),
                        rs.getString("CTN"),
                        rs.getDate("transaction_date").toLocalDate()));
                dashboardTable.setItems(data);
            }
        } catch (Exception e) {

        }
    }

    private void requestsTable() {
        System.out.println("HELLO WORLD");
        ObservableList<CustomerInfo> data = FXCollections.observableArrayList();
        dashboardTable.setItems(null);
        try {
            Connection conn = dbConn.dbConn();
            Statement stmt = null;
            ResultSet rs = null;
            String sql = "SELECT `name`, `CTN`, `transaction_date` FROM admin.user_personal_info INNER JOIN admin.application_status WHERE admin.user_personal_info.userID = admin.application_status.userID and admin.application_status.status = \"Process\"";
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                System.out.println("HELLO WORLD");
                data.add(new CustomerInfo(
                        rs.getString("name"),
                        rs.getString("CTN"),
                        rs.getDate("transaction_date").toLocalDate()));
                dashboardTable.setItems(data);
            }
        } catch (Exception e) {

        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initTable();
        requestsTable();
        requests.setCursor(Cursor.HAND);
        biometrics.setCursor(Cursor.HAND);
        finished.setCursor(Cursor.HAND);
        claiming.setCursor(Cursor.HAND);
        requests.setOnMouseClicked(event -> {
            requestsTable();
        });
        biometrics.setOnMouseClicked(event -> {
            biometricsTable();
        });
        finished.setOnMouseClicked(event -> {
            finishedTable();
        });
        claiming.setOnMouseClicked(event -> {
            claimingTable();
        });
    }

}
