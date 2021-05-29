package sample.DB;

import sample.model.Utwor;

import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;

public class UtworDao {
    public static Utwor get(long id) {
        ResultSet rs = DBConnection.executeQuery("SELECT * FROM utwory WHERE id_utworu = " + id) ;
        try {
            return new Utwor(rs);
        } catch(Exception e) {
            return null;
        }
    }

    public static List<Utwor> getAll() {
        List<Utwor> res = new LinkedList<>();
        try {
            ResultSet rs = DBConnection.executeQuery("SELECT * FROM utwory");
            while(rs != null && rs.next()) {
                res.add(new Utwor(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    public static void main(String[] args) {
        List<Utwor> res_query = (new UtworDao()).getAll();
        for (Utwor u : res_query) {
            System.out.println(u);
        }
    }
}
