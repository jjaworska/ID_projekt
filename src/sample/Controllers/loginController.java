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
    @FXML
    public Button signInButton;

    public static int hash(String s)
    {
        int r=0;
        for (int i=0; i<s.length(); i++)
        {
            r=(r*131+s.charAt(i))%104729;
        }
        return r;
    }
    public void loginButtonClick() {
        Main.currentUser = UzytkownikDao.getByUsername(usernameField.getText(), hash(passwordField.getText()));
        if(Main.currentUser != null) {
            Main.setCurrentScene("FXML/userView.fxml");
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Niepoprawne dane");
            alert.setHeaderText("Wpisana nazwa użytkownika i hasło są niepoprawne");
            alert.setContentText("Spróbuj ponownie");
            alert.showAndWait();
        }
    }
    public void signInButtonClick() {
        Main.setCurrentScene("FXML/newUser.fxml");
    }
}
