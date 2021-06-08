package sample.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import sample.DB.*;
import sample.Main;
import sample.model.*;

import java.net.URL;
import java.util.ResourceBundle;

public class uzytkownikViewController implements Initializable {
    @FXML
    public Label username, playlist_napis, num_followers, num_following;
    @FXML
    public ListView<Playlista> playlists;
    @FXML
    public Button follow_btn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        username.setText(Main.currentTitle.getNazwa());
        playlist_napis.setText("Udostępnione playlisty:");
        num_followers.setText("liczba obserwujących: "+UzytkownikDao.getObesrwujacy(Main.currentTitle.getNazwa()));
        num_following.setText("liczba obserwowanych: "+UzytkownikDao.getObesrwuje(Main.currentTitle.getNazwa()));
        playlists.setItems(PlaylistaDao.getVisiblePlaylistsFor(((Uzytkownik)Main.currentTitle).getId_uzytkownika(), Main.currentUser.getId_uzytkownika()));
        if(UzytkownikDao.doIfollow((int)((Uzytkownik)Main.currentTitle).getId_uzytkownika()))
        follow_btn.setText("nie obserwuj");
        else
            follow_btn.setText("obserwuj");
    }
    public void myPlaylistChosen(javafx.scene.input.MouseEvent mouseEvent){
        ToSearch utwor =playlists.getSelectionModel().getSelectedItem();
        if(utwor != null) {
            Main.currentTitle = utwor;
            Main.setCurrentScene("FXML/playlistaView.fxml");
        }
    }

    public void clickedAction(MouseEvent mouseEvent) {
        if(playlists.getSelectionModel().getSelectedItem()==null)return;
        ToSearch utwor =playlists.getSelectionModel().getSelectedItem();
        if(utwor != null) {
            Main.currentTitle = utwor;
            Main.setCurrentScene("FXML/playlistView.fxml");
        }
    }

    public void goHome(ActionEvent actionEvent) {
        Main.setCurrentScene("FXML/userView.fxml");
    }

    public void follow(ActionEvent actionEvent) {
        if(follow_btn.getText()=="obserwuj"){
            UzytkownikDao.follow(((Uzytkownik)Main.currentTitle).getId_uzytkownika());
            follow_btn.setText("nie obserwuj");
            playlists.setItems(PlaylistaDao.getVisiblePlaylistsFor(((Uzytkownik)Main.currentTitle).getId_uzytkownika(), Main.currentUser.getId_uzytkownika()));
        }
        else{
            UzytkownikDao.unfollow(((Uzytkownik)Main.currentTitle).getId_uzytkownika());
            follow_btn.setText("obserwuj");
            playlists.setItems(PlaylistaDao.getVisiblePlaylistsFor(((Uzytkownik)Main.currentTitle).getId_uzytkownika(), Main.currentUser.getId_uzytkownika()));
        }
        num_followers.setText("liczba obserwujących: "+UzytkownikDao.getObesrwujacy(Main.currentTitle.getNazwa()));
    }
}

