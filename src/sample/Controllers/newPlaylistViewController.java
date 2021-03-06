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

        dostep.getItems().addAll("Publiczny", "Dla obserwuj??cych", "Ukryty");
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
            alert.setTitle("Niekompletne dane");
            alert.setHeaderText("Nale??y poda?? nazw?? playlisty");
            //alert.setContentText("Fill in the form and try again");
            alert.showAndWait();
        }
        else if(dostep.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Niekompletne dane");
            alert.setHeaderText("Trzeba wybra?? rodzaj dost??pu");
            //alert.setContentText("Choose one of the fields and try again");
            alert.showAndWait();
        }
        else if(previewList.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Niekompletne dane");
            alert.setHeaderText("Playlista musi zawiera?? przynajmniej jeden utw??r");
            //alert.setContentText("Choose any song and try again");
            alert.showAndWait();
        }
        else if(PlaylistaDao.getByTworca((int)Main.currentUser.getId_uzytkownika()).stream().map(x->x.getNazwa()).
                toList().contains(nameField.getText())){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Powtarzaj??ca si?? nazwa");
            alert.setHeaderText("U??ytkownik nie mo??e mie?? dw??ch playlist o jednakowej nazwie");
            //alert.setContentText("Change the name and try again");
            alert.showAndWait();
        }
        else {
            String nazwa = nameField.getText();
            String dostepChosen = dostep.getSelectionModel().getSelectedItem();
            String realDostep=null;
            if(dostepChosen.equals("Publiczny"))
                realDostep = "P";
            if(dostepChosen.equals("Dla obserwuj??cych"))
                realDostep = "O";
            if(dostepChosen.equals("Ukryty"))
                realDostep = "U";
            try {
                PlaylistaDao.addPlaylist(nazwa, realDostep);
                previewList.stream().forEach(x -> PlaylistaDao.addUtworToPlaylist(x.getId_utworu()));
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Dodano playlist??");
                alert.setHeaderText("Twoja playlista jest teraz w bazie danych");
                alert.showAndWait();
                Main.setCurrentScene("FXML/userView.fxml");
            } catch(Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Nieoczekiwany b????d");
                alert.setHeaderText("Wyst??pi?? niespodziewany b????d, nie mogli??my doda?? playlisty do bazy");
                alert.setContentText("Spr??buj ponownie");
                alert.showAndWait();
            }
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
