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
import sample.model.bandMember;

import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class bandViewController implements Initializable {
    @FXML
    Label autorNazwa;
    @FXML
    Label role;
    @FXML
    ListView<Utwor> greatestHitsView;
    @FXML
    ListView<bandMember> bandMembers;

    Autor band;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        band = (Autor) Main.currentTitle;
        autorNazwa.setText(band.getNazwa());
        StringBuilder roleBuilder = new StringBuilder();
        for(String rola: AutorDao.AutorRole(band.getId_autora())) {
            roleBuilder.append(rola).append(", ");
        }
        ResultSet rs = DBConnection.executeQuery("SELECT * FROM utwory u WHERE id_utworu IN " +
                "(SELECT id_utworu FROM utwory_autorzy WHERE id_autora = " + band.getId_autora() +
                ") ORDER BY u.oceny_suma::NUMERIC/GREATEST(u.oceny_liczba, 1) DESC LIMIT 5");
        ObservableList<Utwor> greatestHits = FXCollections.observableArrayList();
        try {
            while (rs.next()) {
                greatestHits.add(new Utwor(rs));
            }
        } catch (Exception e) {
            // if we fail then the list will be empty...
        }
        greatestHitsView.setItems(greatestHits);

        ObservableList<bandMember> members = FXCollections.observableArrayList();
        rs = DBConnection.executeQuery("SELECT * FROM autorzy a NATURAL JOIN autorzy_zespoly b " +
                "WHERE b.id_zespolu = " + band.getId_autora());
        try {
            while (rs.next())
                members.add(new bandMember(rs));
        } catch (Exception e) {
            // yes
        }
        bandMembers.setItems(members);

        role.setText(roleBuilder.toString());
    }

    @FXML
    public void homeButtonClick() {
        Main.setCurrentScene("FXML/userView.fxml");
    }

    @FXML
    public void selectUtwor(javafx.scene.input.MouseEvent mouseEvent) {
        ToSearch toView = greatestHitsView.getSelectionModel().getSelectedItem();
        if(toView != null) {
            Main.currentTitle = toView;
            Main.setCurrentScene("FXML/utworView.fxml");
        }
    }

    @FXML
    public void selectMember(javafx.scene.input.MouseEvent mouseEvent) {
        bandMember chosen = bandMembers.getSelectionModel().getSelectedItem();
        if(chosen != null) {
            Main.currentTitle = AutorDao.get(chosen.getId_autora());
            Main.setCurrentScene("FXML/autorView.fxml");
        }
    }
}
