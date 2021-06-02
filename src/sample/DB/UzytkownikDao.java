package sample.DB;

import sample.Main;
import sample.model.Uzytkownik;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

public class UzytkownikDao {
    public static Uzytkownik get(long id) {
        ResultSet rs = DBConnection.executeQuery("SELECT * FROM uzytkownicy WHERE id_uzytkownika = " + id) ;
        try {
            rs.next();
            return new Uzytkownik(rs);
        } catch(Exception e) {
            return null;
        }
    }
    public static Integer getObesrwujacy(String nazwa) {
        ResultSet rs = DBConnection.executeQuery("SELECT obserwujacy FROM uzytkownicy_widok WHERE nazwa= '" + nazwa+"'") ;
        try {
            rs.next();
            return rs.getInt(1);
        } catch(Exception e) {
            return null;
        }
    }
    public static Integer getObesrwuje(String nazwa) {
        ResultSet rs = DBConnection.executeQuery("SELECT obserwuje FROM uzytkownicy_widok WHERE nazwa= '" + nazwa+"'") ;
        try {
            rs.next();
            return rs.getInt(1);
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

    public static void follow(long id) {
        try {
            Statement stmt = DBConnection.getConnection().createStatement();
            stmt.executeUpdate("insert into obserwujacy (id_obserwujacego, id_obserwowanego) values ("+Main.currentUser.getId_uzytkownika()+", "+id+")");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void unfollow(long id) {
        try {
            Statement stmt = DBConnection.getConnection().createStatement();
            stmt.executeUpdate("delete from obserwujacy where id_obserwujacego="+Main.currentUser.getId_uzytkownika()+" and id_obserwowanego="+id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean doIfollow(long id) {
        try {
            ResultSet rs = DBConnection.executeQuery("select count(*) from obserwujacy where id_obserwujacego="+Main.currentUser.getId_uzytkownika()+" and id_obserwowanego="+id);
            rs.next();
            return rs.getInt(1)==1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}


