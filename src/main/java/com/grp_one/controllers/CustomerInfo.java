package com.grp_one.controllers;

import java.sql.Date;
import java.text.SimpleDateFormat;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Button;

public class CustomerInfo {
    public SimpleStringProperty userName;

    public SimpleStringProperty getUserName() {
        return userName;
    }

    public void setUserName(SimpleStringProperty userName) {
        this.userName = userName;
    }

    public SimpleStringProperty getCTN() {
        return CTN;
    }

    public void setCTN(SimpleStringProperty cTN) {
        CTN = cTN;
    }

    public SimpleObjectProperty<Date> getDate() {
        return date;
    }

    public void setDate(SimpleObjectProperty<Date> date) {
        this.date = date;
    }

    public SimpleObjectProperty<Button> getButton() {
        return button;
    }

    public void setButton(SimpleObjectProperty<Button> button) {
        this.button = button;
    }

    public SimpleStringProperty CTN;
    public SimpleObjectProperty<Date> date;
    public SimpleObjectProperty<Button> button;

    public SimpleDateFormat dateFormat = new SimpleDateFormat("mm-dd-yyyy");

    public CustomerInfo(String name, String ctn, Date date, Button button) {
        this.userName = new SimpleStringProperty(name);
        this.CTN = new SimpleStringProperty(ctn);
        this.date = new SimpleObjectProperty<>(date);
        this.button = new SimpleObjectProperty<>(button);
    }
}
