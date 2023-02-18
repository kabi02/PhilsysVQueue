package com.grp_one.controllers;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

import com.grp_one.Main;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.ButtonBar.ButtonData;

import com.grp_one.*;
import com.grp_one.controllers.Bot.*;
import java.sql.*;

public class UserInfoController implements Initializable {
    ObservableList<String> maritalStatusList = FXCollections.observableArrayList("Single", "Married", "Widowed",
            "Divorced", "Legally Separated", "Annulled", "Nullified");
    ObservableList<String> bloodTypeList = FXCollections.observableArrayList("A+", "A-", "B+", "B-", "AB+", "AB-", "O+",
            "O-", "Unknown");
    SqlConnector dbConn = new SqlConnector();
    Dialog<String> dialog = new Dialog<>();
    HashMap<String, Object> userInfo = new HashMap<>();
    @FXML
    private ChoiceBox<String> bloodTypeBox;

    @FXML
    private ChoiceBox<String> maritalStatusBox;

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
    private TextField regInfoEmail;

    @FXML
    private TextField regInfoContact;

    @FXML
    private Button btnFormNext;

    @FXML
    void goToUploadPic(ActionEvent event) throws Exception {
        // Connection conn = dbConn.dbConn();
        RadioButton selected = (RadioButton) genderSel.getSelectedToggle();
        RadioButton selectedResidence = (RadioButton) filOrAlien.getSelectedToggle();
        // String lname, fname, mname, dob, city, province, country, bloodtype,
        // filalien, mstatus, addrfu, addhlb,
        // addstreet, addsubdiv, addcity, addprovince, addcountry, contact;
        // char sex;
        try {
            userInfo.put(ChatContextProvider.LNAME, txtFieldLN.getText());
            userInfo.put(ChatContextProvider.FNAME, txtFieldFN.getText());
            userInfo.put(ChatContextProvider.MNAME, txtFieldMN.getText());
            userInfo.put(ChatContextProvider.SEX, selected.getText().charAt(0));
            userInfo.put(ChatContextProvider.BDAY, dateBirth.getValue());
            userInfo.put(ChatContextProvider.CITY, txtFieldCity.getText());
            userInfo.put(ChatContextProvider.PROVINCE, txtFieldProv.getText());
            userInfo.put(ChatContextProvider.COUNTRY, txtFieldCountry.getText());
            userInfo.put(ChatContextProvider.BTYPE, bloodTypeBox.getValue().toString());
            userInfo.put(ChatContextProvider.FILoALIEN, selectedResidence.getText());
            userInfo.put(ChatContextProvider.MARITAL, maritalStatusBox.getValue().toString());
            userInfo.put(ChatContextProvider.ROOM, addressRFU.getText());
            userInfo.put(ChatContextProvider.HOUSE, addressHLB.getText());
            userInfo.put(ChatContextProvider.STRT, addressStreet.getText());
            userInfo.put(ChatContextProvider.SUBDIV, addressSub.getText());
            userInfo.put(ChatContextProvider.CITY2, addressCity.getText());
            userInfo.put(ChatContextProvider.PROVINCE2, addressProv.getText());
            userInfo.put(ChatContextProvider.COUNTRY2, addressCountry.getText());
            userInfo.put(ChatContextProvider.CONTACT, regInfoContact.getText());
            UserApplicationHandler.submitInfo(userInfo);
            User.setRoot("uploadimages", "User Dashboard");
            User.centerRoot();
            User.showStage();
        } catch (Exception e) {
            dialog.setContentText("Incomplete Information");
            dialog.showAndWait();
        }
    }

    @FXML
    void backToDashboard(ActionEvent event) throws Exception {
        User.setRoot("userdashboard", "User Dashboard");
        User.centerRoot();
        User.showStage();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dialog.setTitle("Dialog");
        ButtonType type = new ButtonType("Ok", ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(type);

        maritalStatusBox.setValue("Single");
        maritalStatusBox.setItems(maritalStatusList);
        bloodTypeBox.setValue("A+");
        bloodTypeBox.setItems(bloodTypeList);
    }

}
