package sample.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
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
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        playlist_napis.setText(Main.currentTitle.getNazwa()+"'s playlists:");
        num_followers.setText("followers: "+UzytkownikDao.getObesrwujacy(Main.currentTitle.getNazwa()));
        num_following.setText("following: "+UzytkownikDao.getObesrwuje(Main.currentTitle.getNazwa()));
        playlists.setItems(PlaylistaDao.getByTworca((int)((Uzytkownik)Main.currentTitle).getId_uzytkownika()));
        if(UzytkownikDao.doIfollow((int)((Uzytkownik)Main.currentTitle).getId_uzytkownika()))
        follow_btn.setText("unfollow");
        else
            follow_btn.setText("follow");
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
        if(follow_btn.getText()=="follow"){
            UzytkownikDao.follow(((Uzytkownik)Main.currentTitle).getId_uzytkownika());
            follow_btn.setText("unfollow");
        }
        else{
            UzytkownikDao.unfollow(((Uzytkownik)Main.currentTitle).getId_uzytkownika());
            follow_btn.setText("follow");
        }
        num_followers.setText("followers: "+UzytkownikDao.getObesrwujacy(Main.currentTitle.getNazwa()));
    }
}

