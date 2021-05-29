module ID.test {
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.fxml;
    requires java.sql;

    opens sample;
    opens sample.FXML;
    opens sample.model;
    opens sample.DB;
    opens sample.Controllers;
}
