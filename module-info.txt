module com.grp_one {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;

    opens com.grp_one.controllers to javafx.fxml;

    exports com.grp_one;
    exports com.grp_one.controllers;
}