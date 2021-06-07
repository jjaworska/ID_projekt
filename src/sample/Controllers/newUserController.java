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
            alert.setTitle("Invalid username");
            alert.setHeaderText("Provided username is already taken");
            alert.setContentText("Please choose different username");
            alert.showAndWait();
        }
        else if (!m.find())
        {
            alert.setTitle("Invalid email");
            alert.setHeaderText("Provided email is incorrect");
            alert.setContentText("Please choose valid email");
            alert.showAndWait();
        }
        else if(!passwordField.getText().equals(repeatPasswordField.getText()))
        {
            alert.setTitle("Invalid password");
            alert.setHeaderText("Passwords do not match");
            alert.setContentText("Please make sure both passwords are same");
            alert.showAndWait();
        }
        else if(passwordField.getText().length() >20)
        {
            alert.setTitle("Invalid password");
            alert.setHeaderText("Password is too long");
            alert.setContentText("Password length should be 5-20 characters");
            alert.showAndWait();
        }
        else if(passwordField.getText().length() <5)
        {
            alert.setTitle("Invalid password");
            alert.setHeaderText("Password is too short");
            alert.setContentText("Password length should be 5-20 characters");
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
