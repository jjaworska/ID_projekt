package sample.model;

import java.sql.ResultSet;

public class Uzytkownik {
    int id_uzytkownika;
    String nazwa;
    String email;

    Uzytkownik(int id_uzytkownika, String nazwa, String email) {
        this.id_uzytkownika = id_uzytkownika;
        this.nazwa = nazwa;
        this.email = email;
    }
}
