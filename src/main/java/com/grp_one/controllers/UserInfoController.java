package com.grp_one.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import com.grp_one.Main;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

import com.grp_one.*;
import java.sql.*;

public class UserInfoController {
    SqlConnector dbConn = new SqlConnector();
    ObservableList<String> maritalStatusList = FXCollections.observableArrayList("Single", "Married", "Widowed", "Divorced", "Legally Separated", "Annulled", "Nullified");
    ObservableList<String> bloodTypeList = FXCollections.observableArrayList("A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-", "Unknown");
    @FXML
    private ChoiceBox<String> bloodTypeBox;

    @FXML private ChoiceBox<String> maritalStatusBox;

    @FXML
    private void initialize() {
        maritalStatusBox.setValue("Single");
        maritalStatusBox.setItems(maritalStatusList);
        bloodTypeBox.setValue("A+");
        bloodTypeBox.setItems(bloodTypeList);
    }

    @FXML
    private RadioButton btnFemale;

    @FXML
    private RadioButton btnMale;

    @FXML
    private DatePicker dateBirth;

    @FXML
    private ToggleGroup genderSel;

    @FXML
    private ToggleGroup filOrAlien;

    @FXML
    private TextField txtFieldCity;

    @FXML
    private TextField txtFieldCountry;

    @FXML
    private TextField txtFieldFN;

    @FXML
    private TextField txtFieldLN;

    @FXML
    private TextField txtFieldMN;

    @FXML
    private TextField txtFieldProv;

    @FXML
    private Hyperlink dashboardLink;

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
    private RadioButton btnFil;

    @FXML
    private RadioButton btnAlien;

    @FXML 
    private TextField regInfoContact;

    @FXML
    private Button btnFormNext;

    @FXML
    void goToUploadPic(ActionEvent event) throws Exception{
        Connection conn = dbConn.dbConn();
        RadioButton selected = (RadioButton)genderSel.getSelectedToggle();
        RadioButton selectedResidence = (RadioButton)filOrAlien.getSelectedToggle();
        String lname, fname, mname, dob, city, province, country, bloodtype, filalien, mstatus, addrfu, addhlb, addstreet, addsubdiv, addcity, addprovince, addcountry, contact;
        char sex;
        lname = txtFieldLN.getText();
        fname = txtFieldFN.getText();
        mname = txtFieldMN.getText();
        sex = selected.getText().charAt(0);
        dob = dateBirth.getValue().toString();
        city = txtFieldCity.getText();
        province = txtFieldProv.getText();
        country = txtFieldCountry.getText();
        bloodtype = bloodTypeBox.getValue().toString();
        filalien = selectedResidence.getText();
        mstatus = maritalStatusBox.getValue().toString();
        addrfu = addressRFU.getText();
        addhlb = addressHLB.getText();
        addstreet = addressStreet.getText();
        addsubdiv = addressSub.getText();
        addcity = addressCity.getText();
        addprovince = addressProv.getText();
        addcountry = addressCountry.getText();
        contact = regInfoContact.getText();
    }

    @FXML
    void backToDashboard(ActionEvent event) throws Exception{
        Main.setRoot("userdashboard", "User Dashboard");
        Main.centerRoot();
        Main.showStage();
    }

}
