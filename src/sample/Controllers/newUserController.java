package sample.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import sample.DB.UzytkownikDao;
import sample.Main;
import sample.model.Uzytkownik;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class newUserController {
    @FXML
    public TextField usernameField;
    @FXML
    public PasswordField passwordField;
    @FXML
    public PasswordField repeatPasswordField;
    @FXML
    public TextField emailField;
    @FXML
    public Button signInButton;

    public void signInButtonClick() {
        Uzytkownik u = UzytkownikDao.getByUsername(usernameField.getText());
        Alert alert = new Alert(Alert.AlertType.ERROR);
        Pattern p = Pattern.compile(".+@.+");
        Matcher m = p.matcher(emailField.getText());
        if(u != null || usernameField.getText()=="")
        {
            alert.setTitle("Niepoprawna nazwa użytkownika");
            alert.setHeaderText("Ten login jest już zajęty");
            alert.setContentText("Wybierz inny login");
            alert.showAndWait();
        }
        else if (!m.find())
        {
            alert.setTitle("Niepoprawny email");
            alert.setHeaderText("Podany email jest niepoprawny");
            alert.setContentText("Wpisz prawidłowy email");
            alert.showAndWait();
        }
        else if(!passwordField.getText().equals(repeatPasswordField.getText()))
        {
            alert.setTitle("Niepoprawne hasło");
            alert.setHeaderText("Dwa wpisane hasła się różnią");
            alert.setContentText("Upewnij się, że wpisane hasła są jednakowe");
            alert.showAndWait();
        }
        else if(passwordField.getText().length() >20)
        {
            alert.setTitle("Niepoprawne hasło");
            alert.setHeaderText("Hasło jest zbyt długie");
            alert.setContentText("Długość hasła to 5-20 znaków");
            alert.showAndWait();
        }
        else if(passwordField.getText().length() <5)
        {
            alert.setTitle("Niepoprawne hasło");
            alert.setHeaderText("Hasło jest zbyt krótkie");
            alert.setContentText("Długość hasła to 5-20 znaków");
            alert.showAndWait();
        }
        else
        {
            Main.currentUser=UzytkownikDao.addUser(usernameField.getText(), emailField.getText(), loginController.hash(passwordField.getText()));
            Main.setCurrentScene("FXML/userView.fxml");
        }
    }
    public void goBackButtonClick() {
        Main.setCurrentScene("FXML/login.fxml");
    }
}
