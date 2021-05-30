package sample.DB;

import sample.model.Autor;

import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;

public class AutorDao {
    public static Autor get(long id) {
        ResultSet rs = DBConnection.executeQuery("SELECT * FROM Autorzy WHERE id_Autora = " + id) ;
        try {
            return new Autor(rs);
        } catch(Exception e) {
            return null;
        }
    }

    public static List<Autor> getAll() {
        List<Autor> res = new LinkedList<>();
        try {
            ResultSet rs = DBConnection.executeQuery("SELECT * FROM Autorzy");
            while(rs != null && rs.next()) {
                res.add(new Autor(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }
}
