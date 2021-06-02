package sample.DB;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.Main;
import sample.model.Autor;
import sample.model.Utwor;
import sample.model.Utwor_widok;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class UtworDao {
    public static Utwor get(long id) {
        ResultSet rs = DBConnection.executeQuery("SELECT * FROM utwory WHERE id_utworu = " + id) ;
        try {
            rs.next();
            return new Utwor(rs);
        } catch(Exception e) {
            return null;
        }
    }
    public static Utwor_widok getUtwor_widok(String title) {
        try {
            ResultSet rs = DBConnection.executeQuery("SELECT * from utwory_widok where tytul='"+title+"'");
            while(rs != null && rs.next()) {
                return (new Utwor_widok(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
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
    public static ObservableList<Utwor> getRecomends() {
        ObservableList<Utwor> res=null;
        try {
            ResultSet rs = DBConnection.executeQuery("SELECT rekomendacje("+Main.currentUser.getId_uzytkownika()+")");
            if(rs != null ) {
                rs.next();
                res =  FXCollections.observableArrayList(Arrays.stream((Integer[])rs.getArray(1).getArray()).
                        map(UtworDao::get).toList());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }
    public static void rate(int id_utworu, int ocena) {
        try {
            Statement stmt = DBConnection.getConnection().createStatement();
            stmt.executeUpdate("insert into oceny(id_uzytkownika, id_utworu, ocena) values ("+ Main.currentUser.getId_uzytkownika()+" ,"+id_utworu+", "+ocena+")");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static double getocena(String title) {
        try {
            ResultSet rs = DBConnection.executeQuery("SELECT ocena from utwory_widok where tytul='"+title+"'");
            while(rs != null && rs.next()) {
                return rs.getDouble(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
    public static void main(String[] args) {
        List<Utwor> res_query = (new UtworDao()).getAll();
        for (Utwor u : res_query) {
            System.out.println(u);
        }
    }
}
