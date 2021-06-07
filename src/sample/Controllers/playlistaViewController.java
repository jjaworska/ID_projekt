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
import sample.model.Utwor;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


public class playlistaViewController implements Initializable {
    @FXML
    public Label albumName;
    @FXML
    public ListView<Utwor> titles;
    @FXML
    public Label autor_playlisty;
    private ObservableList<Utwor> observableNames;
    Playlista playlista;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        playlista=(Playlista) Main.currentTitle;
        albumName.setText(playlista.getNazwa());

        autor_playlisty.setText(UzytkownikDao.get(playlista.getId_tworcy()).getNazwa());
        final List<Integer>i=new ArrayList<>();
        i.add(1);
        observableNames= FXCollections.observableArrayList(PlaylistaDao.getUtwory(playlista.getId_playlisty())/*.
                stream().map(x->(i.set(0, i.get(0)+1))+". "+x.getNazwa()).toList()*/);
        titles.setItems(observableNames);
    }
    @FXML
    public void goHome(ActionEvent actionEvent) {
        Main.setCurrentScene("FXML/userView.fxml");
    }

    @FXML
    public void selectUtwor(javafx.scene.input.MouseEvent mouseEvent) {
        if(titles.getSelectionModel().getSelectedItem()==null)return;
        Main.currentTitle = titles.getSelectionModel().getSelectedItem();
        Main.setCurrentScene("FXML/utworView.fxml");
    }
}
