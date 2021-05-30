package sample.model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Uzytkownik implements ToSearch{
    int id_uzytkownika;
    String nazwa;
    String email;

    Uzytkownik(int id_uzytkownika, String nazwa, String email) {
        this.id_uzytkownika = id_uzytkownika;
        this.nazwa = nazwa;
        this.email = email;
    }
    public Uzytkownik(ResultSet rs) throws SQLException {
        this.id_uzytkownika = rs.getInt(1);
        this.nazwa = rs.getString(2);
        this.email = rs.getString(3);
    }

    public String getNazwa() {
        return nazwa;
    }
    public String getEmail() {
        return email;
    }

    public int getId_uzytkownika() {
        return id_uzytkownika;
    }

    @Override
    public String toString() {
        return "Uzytkownik{" +
                "id_uzytkownika=" + id_uzytkownika +
                ", nazwa='" + nazwa + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
