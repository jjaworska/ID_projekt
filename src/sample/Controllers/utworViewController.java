package sample.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import sample.Main;
import sample.model.Utwor;

import java.net.URL;
import java.util.ResourceBundle;

public class utworViewController implements Initializable {
    @FXML
    public TextField opis;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        opis.setText(Main.currentTitle.toString());
    }
}
