module ID.test {
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.fxml;
    requires java.sql;

    opens sample;
    opens sample.view;
    opens sample.model;
    opens sample.dao;
}
