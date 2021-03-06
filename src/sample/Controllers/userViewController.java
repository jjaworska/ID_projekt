package sample.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
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

public class userViewController implements Initializable {
    @FXML
    public Label username;
    @FXML
    public Label email;
    @FXML
    public ListView<Playlista> playlists;
    @FXML
    public ListView<ToSearch> searchList;
    @FXML
    public ListView<Utwor> recomends;
    @FXML
    public TextField searchField;

    private ObservableList<ToSearch> observableNames;
    private FilteredList<ToSearch> filteredData;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        username.setText(Main.currentUser.getNazwa());
        email.setText("Email: " + Main.currentUser.getEmail());
        playlists.setItems(PlaylistaDao.getByTworca((int)Main.currentUser.getId_uzytkownika()));
        recomends.setItems(UtworDao.getRecomends());
        observableNames = FXCollections.observableArrayList
                (Stream.of(UtworDao.getAll(), UzytkownikDao.getAll(), AlbumDao.getAll(),
                        PlaylistaDao.getVisiblePlaylists(Main.currentUser.getId_uzytkownika()), AutorDao.getAll())
                .flatMap(x -> x.stream())
                .collect(Collectors.toList()));
        filteredData = new FilteredList<>
                (observableNames, p -> true);
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
        searchList.setCellFactory(searchList-> {
            Tooltip tooltip = new Tooltip();
            ListCell<ToSearch> cell = new
                    ListCell<>() {
                        @Override
                        public void updateItem(ToSearch utwor, boolean empty) {
                            super.updateItem(utwor, empty);
                            if (utwor != null) {
                                setText(utwor.getNazwa());
                                if(utwor instanceof Utwor){
                                    tooltip.setText("utwor");
                                } else if(utwor instanceof Uzytkownik){
                                    tooltip.setText("uzytkownik");
                                }else if(utwor instanceof Album){
                                    tooltip.setText("album");
                                }else if(utwor instanceof Autor){
                                    tooltip.setText("autor");
                                }else if(utwor instanceof Playlista){
                                    tooltip.setText("playlista");
                                }
                                    setTooltip(tooltip);
                            } else
                                setText(null);
                        }
                    };
        return cell;
        });
    }
    public void myPlaylistChosen(javafx.scene.input.MouseEvent mouseEvent){
        if(playlists.getSelectionModel().getSelectedItem()==null)return;
        ToSearch utwor =playlists.getSelectionModel().getSelectedItem();
        Main.currentTitle=utwor;
        Main.setCurrentScene("FXML/playlistaView.fxml");
    }
    public void clickedAction(javafx.scene.input.MouseEvent mouseEvent){
        ToSearch utwor = searchList.getSelectionModel().getSelectedItem();
        Main.currentTitle = utwor;
        if(utwor instanceof Utwor){
            Main.setCurrentScene("FXML/utworView.fxml");
        } else if(utwor instanceof Uzytkownik){
            Main.setCurrentScene("FXML/uzytkownikView.fxml");
        }else if(utwor instanceof Album){
            Main.setCurrentScene("FXML/albumView.fxml");
        }else if(utwor instanceof Autor){
            if(((Autor) utwor).getCzy_zespol())
                Main.setCurrentScene("FXML/bandView.fxml");
            else
                Main.setCurrentScene("FXML/autorView.fxml");
        }else if(utwor instanceof Playlista){
            Main.setCurrentScene("FXML/playlistaView.fxml");
        }
    }

    public void clickedAction2(MouseEvent mouseEvent) {
        ToSearch utwor =recomends.getSelectionModel().getSelectedItem();
        if(utwor != null) {
            Main.currentTitle = utwor;
            if (utwor instanceof Utwor) {
                Main.setCurrentScene("FXML/utworView.fxml");
            }
        }
    }

    @FXML
    public void logOut() {
        Main.currentUser = null;
        Main.setCurrentScene("FXML/login.fxml");
    }

    @FXML
    public void newPlaylist() {
        Main.setCurrentScene("FXML/newPlaylistView.fxml");
    }
}
