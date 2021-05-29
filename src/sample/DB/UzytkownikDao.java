package sample.DB;

import sample.model.Uzytkownik;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class UzytkownikDao {
    public static Uzytkownik get(long id) {
        ResultSet rs = DBConnection.executeQuery("SELECT * FROM uzytkownicy WHERE id_uzytkownika = " + id) ;
        try {
            return new Uzytkownik(rs);
        } catch(Exception e) {
            return null;
        }
    }

    public static Uzytkownik getByUsername(String username) {
        ResultSet rs = DBConnection.executeQuery("SELECT * FROM uzytkownicy WHERE nazwa = \'" + username + "\'") ;
        try {
            rs.next();
            return new Uzytkownik(rs);
        } catch(SQLException throwables) {
            return null;
        }
    }

    public static List<Uzytkownik> getAll() {
        List<Uzytkownik> res = new LinkedList<>();
        try {
            ResultSet rs = DBConnection.executeQuery("SELECT * FROM uzytkownicy");
            while(rs != null && rs.next()) {
                res.add(new Uzytkownik(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    public static void main(String[] args) {
        List<Uzytkownik> res_query = (new UzytkownikDao()).getAll();
        for (Uzytkownik u : res_query) {
            System.out.println(u);
        }
    }
}


