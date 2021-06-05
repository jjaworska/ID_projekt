package sample.DB;

import sample.model.Autor;

import java.sql.*;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static sample.DB.DBConnection.getConnection;

public class AutorDao {
    public static Autor get(long id) {
        ResultSet rs = DBConnection.executeQuery("SELECT * FROM Autorzy WHERE id_Autora = " + id) ;
        try {
            rs.next();
            return new Autor(rs);
        } catch(Exception e) {
            return null;
        }
    }
    public static List<String> getByAlbum(long id_albumu) {
        List<Autor> res = new LinkedList<>();
            ResultSet rs = DBConnection.executeQuery("SELECT album_autorzy("+id_albumu+")");
            try {
                rs.next();
                return Arrays.asList((String[])rs.getArray(1).getArray());
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

    public static List<String> AutorRole(int id) {
        try {
            Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT wypisz_role_autora(" + id + ")");
            ResultSet rs = statement.executeQuery();
            rs.next();
            Array roles = rs.getArray(1);
            return Arrays.asList((String[]) roles.getArray());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return new LinkedList<String>();
        }
    }

    // Again, a basic test
    public static void main(String[] args) {
        System.out.println(AutorRole(7));
    }
}
