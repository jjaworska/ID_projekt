package sample.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import sample.DB.*;
import sample.Main;
import sample.model.*;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class newPlaylistViewController implements Initializable {

    @FXML
    public TextField nameField;
    @FXML
    public TextField searchField;
    @FXML
    public ListView<Utwor> searchList;
    @FXML
    public ListView<Utwor> preview;
    @FXML
    public ChoiceBox<String> dostep;

    private ObservableList<Utwor> utworToSearch;
    private FilteredList<Utwor> filteredData;
    private ObservableList<Utwor> previewList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        utworToSearch = FXCollections.observableArrayList
                (Stream.of(UtworDao.getAll())
                        .flatMap(x -> x.stream())
                        .collect(Collectors.toList()));
        filteredData = new FilteredList<>
                (utworToSearch, p -> true);
        searchList.setItems(filteredData);
        searchField.textProperty()
                .addListener((observable, oldValue, newValue) ->
                        filteredData.setPredicate(str -> {
                            if (newValue == null || newValue.isEmpty())
                                return true;
                            if (str.getNazwa().toLowerCase().contains
                                    (newValue.toLowerCase()))
                                return true;
                            return false;
                        }));
        searchList.getSelectionModel().setSelectionMode
                (SelectionMode.SINGLE);
        searchList.setPrefHeight(Integer.MAX_VALUE);
        previewList = FXCollections.observableArrayList();
        preview.setItems(previewList);

        dostep.getItems().addAll("Publiczny", "Dla obserwujących", "Ukryty");
    }

    @FXML
    public void utworSelected() {
        Utwor utwor = searchList.getSelectionModel().getSelectedItem();
        if(utwor != null) {
            previewList.add(utwor);
            utworToSearch.remove(utwor);
        }
    }

    @FXML
    public void createPlaylist() {
        if(nameField.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Data missing");
            alert.setHeaderText("You have to specify the playlist name");
            alert.setContentText("Fill in the form and try again");
            alert.showAndWait();
        }
        else if(dostep.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Data missing");
            alert.setHeaderText("You have to specify the playlist access mode");
            alert.setContentText("Choose one of the fields and try again");
            alert.showAndWait();
        }
        else if(previewList.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Data missing");
            alert.setHeaderText("A playlist must contain at least one song");
            alert.setContentText("Choose any song and try again");
            alert.showAndWait();
        }
        if(PlaylistaDao.getByTworca((int)Main.currentUser.getId_uzytkownika()).stream().map(x->x.getNazwa()).
                toList().contains(nameField.getText())){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Same name");
            alert.setHeaderText("Two playlists cannot have the same name");
            alert.setContentText("Change the name and try again");
            alert.showAndWait();
        }
        else {
            String nazwa = nameField.getText();
            String dostepChosen = dostep.getSelectionModel().getSelectedItem();
            String realDostep=null;
            if(dostepChosen.equals("Publiczny"))
                realDostep = "P";
            if(dostepChosen.equals("Dla obserwujących"))
                realDostep = "O";
            if(dostepChosen.equals("Ukryty"))
                realDostep = "U";
            PlaylistaDao.addPlaylist(nazwa, realDostep);
            previewList.stream().forEach(x->PlaylistaDao.addUtworToPlaylist(x.getId_utworu()));
            // TODO : wklepać te dane do bazy
            // nazwa, tworca, dostep i utwory
        }
    }

    @FXML
    public void utworDeselected() {
        Utwor utwor = preview.getSelectionModel().getSelectedItem();
        if(utwor != null) {
            previewList.remove(utwor);
            utworToSearch.add(utwor);
        }
    }

    @FXML
    public void homeButtonClick() {
        Main.setCurrentScene("FXML/userView.fxml");
    }
}
