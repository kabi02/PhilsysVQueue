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
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

import com.grp_one.*;

public class AdminDashboardController implements Initializable {

    SqlConnector dbConn = new SqlConnector();
    public static CustomerInfo selection;
    @FXML
    private Button btnDelete;

    @FXML
    private Button btnView;

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
    Dialog<String> dialog = new Dialog<>();

    @FXML
    private void logoutUser() throws Exception {
        Admin.setRoot("adminlogin", "Login");
        Admin.centerRoot();
        Admin.showStage();
    }

    @FXML
    void viewInfo(ActionEvent event) throws Exception {
        selection = dashboardTable.getSelectionModel().getSelectedItem();
        if (selection != null) {
            Admin.setRoot("userinfo_adminside", "Customer Details");
            Admin.centerRoot();
            Admin.showStage();
        }
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
        ObservableList<CustomerInfo> data = FXCollections.observableArrayList();
        dashboardTable.setItems(null);
        try {
            Connection conn = dbConn.dbConn();
            PreparedStatement stmt = null;
            ResultSet rs = null;
            String sql = "SELECT `lname`,`fname`,`mname`, `CTN`, `transaction_date` FROM admin.user_personal_info INNER JOIN admin.application_status WHERE admin.user_personal_info.userID = admin.application_status.userID and admin.application_status.status = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, CustomerInfo.CLAIMED);
            rs = stmt.executeQuery();
            while (rs.next()) {
                System.out.println("HELLO WORLD");
                data.add(new CustomerInfo(
                        rs.getString("lname") + ", " + rs.getString("fname") + rs.getString("mname"),
                        rs.getString("CTN"),
                        rs.getDate("transaction_date").toLocalDate()));
                dashboardTable.setItems(data);
            }
        } catch (Exception e) {

        }
    }

    private void claimingTable() {
        ObservableList<CustomerInfo> data = FXCollections.observableArrayList();
        dashboardTable.setItems(null);
        try {
            Connection conn = dbConn.dbConn();
            PreparedStatement stmt = null;
            ResultSet rs = null;
            String sql = "SELECT `lname`,`fname`,`mname`, `CTN`, `transaction_date` FROM admin.user_personal_info INNER JOIN admin.application_status WHERE admin.user_personal_info.userID = admin.application_status.userID and admin.application_status.status = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, CustomerInfo.CLAIMING);
            rs = stmt.executeQuery();
            while (rs.next()) {
                System.out.println("HELLO WORLD");
                data.add(new CustomerInfo(
                        rs.getString("lname") + ", " + rs.getString("fname") + " " + rs.getString("mname"),
                        rs.getString("CTN"),
                        rs.getDate("transaction_date").toLocalDate()));
                dashboardTable.setItems(data);
            }
        } catch (Exception e) {

        }
    }

    private void biometricsTable() {
        ObservableList<CustomerInfo> data = FXCollections.observableArrayList();
        dashboardTable.setItems(null);
        try {
            Connection conn = dbConn.dbConn();
            PreparedStatement stmt = null;
            ResultSet rs = null;
            String sql = "SELECT `lname`, `fname`, `mname`, `CTN`, `transaction_date` FROM admin.user_personal_info INNER JOIN admin.application_status WHERE admin.user_personal_info.userID = admin.application_status.userID and admin.application_status.status = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, CustomerInfo.BIOMETRICS);
            rs = stmt.executeQuery();
            while (rs.next()) {
                System.out.println("HELLO WORLD");
                data.add(new CustomerInfo(
                        rs.getString("lname") + ", " + rs.getString("fname") + " " + rs.getString("mname"),
                        rs.getString("CTN"),
                        rs.getDate("transaction_date").toLocalDate()));
                dashboardTable.setItems(data);
            }
        } catch (Exception e) {

        }
    }

    private void requestsTable() {
        ObservableList<CustomerInfo> data = FXCollections.observableArrayList();
        dashboardTable.setItems(null);
        System.out.println(CustomerInfo.PROCESS);
        try {
            Connection conn = dbConn.dbConn();
            PreparedStatement stmt = null;
            ResultSet rs = null;
            String sql = "SELECT `lname`, `fname`, `mname`, `CTN`, `transaction_date` FROM admin.user_personal_info INNER JOIN admin.application_status WHERE admin.user_personal_info.userID = admin.application_status.userID and admin.application_status.status = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, CustomerInfo.PROCESS);
            rs = stmt.executeQuery();
            while (rs.next()) {
                System.out.println(CustomerInfo.PROCESS);
                data.add(new CustomerInfo(
                        rs.getString("lname") + ", " + rs.getString("fname") + " " + rs.getString("mname"),
                        rs.getString("CTN"),
                        rs.getDate("transaction_date").toLocalDate()));
                dashboardTable.setItems(data);
            }
        } catch (Exception e) {

        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dialog.setTitle("Dialog");
        ButtonType type = new ButtonType("Ok", ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(type);
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
        btnDelete.setOnMouseClicked(event -> {
            try {
                selection = dashboardTable.getSelectionModel().getSelectedItem();
                if (selection != null) {
                    Connection conn = dbConn.dbConn();
                    PreparedStatement stmt = null;
                    ResultSet rs = null;
                    String sql = "select admin.application_status.status from admin.application_status where admin.application_status.CTN = ?";
                    stmt = conn.prepareStatement(sql);
                    stmt.setString(1, selection.getCTN());
                    rs = stmt.executeQuery();
                    rs.next();
                    System.out.println(rs.getString(1));
                    if (rs.getString(1).equalsIgnoreCase(CustomerInfo.CLAIMED)) {
                        sql = "delete from admin.application_status where admin.application_status.CTN = ?";
                        stmt = conn.prepareStatement(sql);
                        stmt.setString(1, selection.getCTN());
                        stmt.executeUpdate();
                        claimingTable();
                        dialog.setContentText("Successfully Deleted!");
                        dialog.showAndWait();
                    } else {
                        dialog.setContentText("Application is being Processed, Please Decline to remove from database");
                        dialog.showAndWait();
                    }
                }
            } catch (Exception e) {

            }
        });
    }

}
