package sample.DB;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.Main;
import sample.model.Playlista;
import sample.model.Utwor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
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


    public static ObservableList<Playlista> getVisiblePlaylists(long id) {
        ResultSet rs = DBConnection.executeQuery("select * from widoczne_playlisty("+id+")");
        ObservableList<Playlista> answer = FXCollections.observableArrayList();
        try {
            while(rs.next())
                answer.add(new Playlista(rs));
        } catch (Exception e) {
            // the list will be empty then
        }
        return answer;
    }

    public static ObservableList<Playlista> getVisiblePlaylistsFor(long id_user, long id) {
        ResultSet rs = DBConnection.executeQuery("select * from widoczne_playlisty_tworcy("+id+","+id_user+")");
        ObservableList<Playlista> answer = FXCollections.observableArrayList();
        try {
            while(rs.next())
                answer.add(new Playlista(rs));
        } catch (Exception e) {
            // the list will be empty then
        }
        return answer;
    }

    public static ObservableList<Utwor> getUtwory(int id){
        ResultSet rs = DBConnection.executeQuery("SELECT utwory.* FROM utwory natural join utwory_playlisty WHERE id_playlisty=" + id +" order by nr_w_playliscie");
        ObservableList<Utwor> x = FXCollections.observableArrayList();
        try {
            while (rs.next())
                x.add(new Utwor(rs));
        } catch(SQLException throwables) {
            // I'm not really worried by exceptions
        }
        return x;
    }

    public static List<Playlista> getAll() {
        List<Playlista> res = new LinkedList<>();
        try {
            ResultSet rs = DBConnection.executeQuery("SELECT * FROM playlisty");
            while(rs != null && rs.next()) {
                res.add(new Playlista(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    public static void addPlaylist(String nazwa, String dostep) {
        try {
            Statement stmt = DBConnection.getConnection().createStatement();
            stmt.executeUpdate("insert into playlisty (tworca, nazwa, dostep) values " +
                    "("+ Main.currentUser.getId_uzytkownika()+", '"+ nazwa+"', '"+dostep+"')");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void addUtworToPlaylist(long id) {
        Integer id_playlisty=0;
        ResultSet rs = DBConnection.executeQuery("select max(id_playlisty) from playlisty");
        try {
            if(rs != null && rs.next()) {
                id_playlisty=rs.getInt(1);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            Statement stmt = DBConnection.getConnection().createStatement();
            stmt.executeUpdate("insert into utwory_playlisty values ("+id_playlisty+", "+id+")");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
