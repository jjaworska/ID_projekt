package sample.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import sample.DB.*;
import sample.Main;
import sample.model.Album;
import sample.model.Playlista;
import sample.model.ToSearch;
import sample.model.Utwor;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class albumViewController implements Initializable {
    @FXML
    public Label albumName;
    @FXML
    public ListView<Utwor> titles;
    @FXML
    public Label autor_albumu;
    private ObservableList<Utwor> observableNames;
    Album album;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        album=(Album) Main.currentTitle;
        albumName.setText(album.getNazwa());
        autor_albumu.setText("autorzy:\n"+ AutorDao.getByAlbum(album.getId_albumu()).stream()
                .map(n -> String.valueOf(n))
                .collect(Collectors.joining("\n   ")));
        observableNames= FXCollections.observableArrayList(AlbumDao.getUtwory(album.getId_albumu()));
        titles.setItems(observableNames);
                //stream().map(x->x.getNumer_w_albumie()+". "+x.getNazwa()).toList());
    }
    @FXML
    public void goHome(ActionEvent actionEvent) {
        Main.setCurrentScene("FXML/userView.fxml");
    }

    public void select_utwor(MouseEvent mouseEvent) {
        ToSearch utwor = titles.getSelectionModel().getSelectedItem();
        Main.currentTitle=utwor;
        if(utwor instanceof Utwor){
            Main.setCurrentScene("FXML/utworView.fxml");
        }
    }
}
