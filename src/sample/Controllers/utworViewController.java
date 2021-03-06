package sample.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import sample.DB.UtworDao;
import sample.Main;
import sample.model.Utwor;
import sample.model.Utwor_widok;

import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class utworViewController implements Initializable {
    @FXML
    public Label tytul;
    @FXML
    public Label data;
    @FXML
    public Label album;
    @FXML
    public Label autorzy;
    @FXML
    public Label dlugosc;
    @FXML
    public Label gatunek;
    @FXML
    public Label ocena;
    @FXML
    public Button button_ocena;
    @FXML
    public Slider slider_rate;
    Utwor utwor;
    Utwor_widok utwor_widok;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        utwor=(Utwor) Main.currentTitle;
        utwor_widok=UtworDao.getUtwor_widok(utwor.getId_utworu());
        tytul.setText(utwor.getNazwa());
        data.setText("data wydania: "+utwor_widok.getData_wydania().toLocalDateTime().format( DateTimeFormatter.ofPattern("dd-MM-yyyy")).toString());
        dlugosc.setText("długość utworu: "+utwor.getDlugosc());
        autorzy.setText("autorzy:\n   "+utwor_widok.getAutorzy().stream()
                .map(n -> String.valueOf(n))
                .collect(Collectors.joining("\n   ")));
        if(utwor_widok.getAlbum()==null)
            album.setText("(brak albumu)");
        else
        album.setText("album: "+utwor_widok.getAlbum());
        ocena.setText("ocena użytkowników: "+utwor_widok.ocena());
        gatunek.setText("gatunek: "+utwor_widok.getGatunek());
    }

    public void button_rate(ActionEvent actionEvent) {
        UtworDao.rate(utwor.getId_utworu(), (int)slider_rate.getValue());
        ocena.setText("ocena użytkowników: "+UtworDao.getocena(utwor.getId_utworu()));
    }

    public void goHome(ActionEvent actionEvent) {
        Main.setCurrentScene("FXML/userView.fxml");
    }
}
