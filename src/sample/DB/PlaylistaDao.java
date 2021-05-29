package sample.DB;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.model.Playlista;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class PlaylistaDao {
    public static Playlista get(long id) {
        ResultSet rs = DBConnection.executeQuery("SELECT * FROM playlisty WHERE id_uzytkownika = " + id) ;
        try {
            return new Playlista(rs);
        } catch(Exception e) {
            return null;
        }
    }

    public static ObservableList<Playlista> getByTworca(int id) {
        ResultSet rs = DBConnection.executeQuery("SELECT * FROM playlisty WHERE tworca = " + id);
        ObservableList<Playlista> x = FXCollections.observableArrayList();
        try {
            while (rs.next())
                x.add(new Playlista(rs));
        } catch(SQLException throwables) {
            // I'm not really worried by exceptions
        }
        return x;
    }

    public static List<Playlista> getAll() {
        return null;
    }
}
