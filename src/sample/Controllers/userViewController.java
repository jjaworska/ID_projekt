package sample.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import sample.Main;
import sample.DB.PlaylistaDao;
import sample.model.Playlista;

import java.net.URL;
import java.util.ResourceBundle;

public class userViewController implements Initializable {
    @FXML
    public Label username;
    @FXML
    public Label email;
    @FXML
    public ListView<Playlista> playlists;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        username.setText(Main.currentUser.getNazwa());
        email.setText("Email: " + Main.currentUser.getEmail());
        playlists.setItems(PlaylistaDao.getByTworca(Main.currentUser.getId_uzytkownika()));
    }
}
