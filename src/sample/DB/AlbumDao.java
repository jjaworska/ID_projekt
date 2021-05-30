package sample.DB;

import sample.model.Album;

import java.sql.ResultSet;
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
