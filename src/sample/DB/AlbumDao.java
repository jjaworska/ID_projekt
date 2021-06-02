package sample.DB;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.model.Album;
import sample.model.Utwor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class AlbumDao {
    public static Album get(long id) {
        ResultSet rs = DBConnection.executeQuery("SELECT * FROM albumy WHERE id_albumu = " + id) ;
        try {
            return new Album(rs);
        } catch(Exception e) {
            return null;
        }
    }
    public static ObservableList<Utwor> getUtwory(int id){
        ResultSet rs = DBConnection.executeQuery("SELECT utwory.* FROM utwory WHERE id_albumu=" + id +" order by numer_w_albumie");
        ObservableList<Utwor> x = FXCollections.observableArrayList();
        try {
            while (rs.next())
                x.add(new Utwor(rs));
        } catch(SQLException throwables) {
            // I'm not really worried by exceptions
        }
        return x;
    }
    public static List<Album> getAll() {
        List<Album> res = new LinkedList<>();
        try {
            ResultSet rs = DBConnection.executeQuery("SELECT * FROM albumy");
            while(rs != null && rs.next()) {
                res.add(new Album(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }
}
