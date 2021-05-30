package sample.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import sample.Main;
import sample.DB.UzytkownikDao;

public class loginController {
    @FXML
    public TextField usernameField;
    @FXML
    public PasswordField passwordField;
    @FXML
    public Button loginButton;

    public void loginButtonClick() {
        Main.currentUser = UzytkownikDao.getByUsername(usernameField.getText());
        if(Main.currentUser != null) {
            Main.setCurrentScene("FXML/userView.fxml");
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid username");
            alert.setHeaderText("The provided username or password is incorrect");
            alert.setContentText("Please try again");
            alert.showAndWait();
        }
    }
}