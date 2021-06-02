package sample.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import sample.DB.PlaylistaDao;
import sample.DB.UzytkownikDao;
import sample.Main;
import sample.model.Playlista;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


public class playlistaViewController implements Initializable {
    @FXML
    public Label albumName;
    @FXML
    public ListView<String> titles;
    @FXML
    public Label autor_playlisty;
    private ObservableList<String> observableNames;
    Playlista playlista;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        playlista=(Playlista) Main.currentTitle;
        albumName.setText(playlista.getNazwa());
        System.out.println(playlista.getId_tworcy());

        autor_playlisty.setText(UzytkownikDao.get(playlista.getId_tworcy()).getNazwa());
        final List<Integer>i=new ArrayList<>();
        i.add(1);
        observableNames= FXCollections.observableArrayList(PlaylistaDao.getUtwory(playlista.getId_playlisty()).
                stream().map(x->(i.set(0, i.get(0)+1))+". "+x.getNazwa()).toList());
        titles.setItems(observableNames);
    }
    @FXML
    public void goHome(ActionEvent actionEvent) {
        Main.setCurrentScene("FXML/userView.fxml");
    }
}
