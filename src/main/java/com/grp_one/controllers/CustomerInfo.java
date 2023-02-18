package com.grp_one.controllers;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Button;

public class CustomerInfo {

    public String getName() {
        return userName.get();
    }

    public String getCTN() {
        return CTN.get();
    }

    public LocalDate getDate() {
        return date.get();
    }

    public static void removeQuery() {

    }

    public static void updateQuery() {

    }

    public SimpleStringProperty userName;
    public SimpleStringProperty CTN;
    public SimpleObjectProperty<LocalDate> date;

    public SimpleDateFormat dateFormat = new SimpleDateFormat("mm-dd-yyyy");

    public CustomerInfo(String name, String ctn, LocalDate date) {
        this.userName = new SimpleStringProperty(name);
        this.CTN = new SimpleStringProperty(ctn);
        this.date = new SimpleObjectProperty<>(date);
    }
}
