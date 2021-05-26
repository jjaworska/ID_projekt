package sample.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.dao.UtworDao;
import sample.model.Utwor;

import java.net.URL;
import java.sql.Timestamp;
import java.util.ResourceBundle;

public class sampleController implements Initializable {
    @FXML
    public TableView<Utwor> table;
    @FXML
    public TableColumn<Utwor, String> tytulCol;
    @FXML
    public TableColumn<Utwor, String> dlugoscCol;
    @FXML
    public TableColumn<Utwor, Timestamp> data_dodaniaCol;
    @FXML
    public TableColumn<Utwor, String> id_gatunkuCol;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tytulCol.setCellValueFactory(new PropertyValueFactory<>("tytul"));
        dlugoscCol.setCellValueFactory(new PropertyValueFactory<>("dlugosc"));
        data_dodaniaCol.setCellValueFactory(new PropertyValueFactory<>("data_dodania"));
        id_gatunkuCol.setCellValueFactory(new PropertyValueFactory<>("id_gatunku"));

        ObservableList<Utwor> entries = FXCollections.observableArrayList();
        entries.setAll((new UtworDao()).getAll());
        table.setItems(entries);
    }
}
