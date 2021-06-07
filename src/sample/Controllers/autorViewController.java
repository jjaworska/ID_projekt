package sample.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import sample.DB.AutorDao;
import sample.DB.DBConnection;
import sample.Main;
import sample.model.Autor;
import sample.model.ToSearch;
import sample.model.Utwor;

import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class autorViewController implements Initializable {
    @FXML
    Label autorNazwa;
    @FXML
    Label role;
    @FXML
    ListView<Utwor> greatestHitsView;

    Autor autor;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        autor = (Autor) Main.currentTitle;
        autorNazwa.setText(autor.getNazwa());
        StringBuilder roleBuilder = new StringBuilder();
        for(String rola: AutorDao.AutorRole(autor.getId_autora())) {
            roleBuilder.append(rola).append(", ");
        }
        ResultSet rs = DBConnection.executeQuery("SELECT * FROM utwory WHERE id_utworu IN " +
                "(SELECT id_utworu FROM utwory_autorzy WHERE id_autora = " + autor.getId_autora() + ") LIMIT 5");
        ObservableList<Utwor> greatestHits = FXCollections.observableArrayList();
        try {
            while (rs.next()) {
                greatestHits.add(new Utwor(rs));
            }
        } catch (Exception e) {
            // if we fail then the list will be empty...
        }
        greatestHitsView.setItems(greatestHits);
        role.setText(roleBuilder.toString());
    }

    public void homeButtonClick() {
        Main.setCurrentScene("FXML/userView.fxml");
    }

    public void selectUtwor(javafx.scene.input.MouseEvent mouseEvent) {
        ToSearch toView = greatestHitsView.getSelectionModel().getSelectedItem();
        if(toView != null) {
            Main.currentTitle = toView;
            Main.setCurrentScene("FXML/utworView.fxml");
        }
    }
}
