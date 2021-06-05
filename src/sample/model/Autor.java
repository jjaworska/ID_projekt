package sample.model;

import java.sql.ResultSet;
import java.sql.Timestamp;

public class Autor implements ToSearch{
    protected int id_autora;
    protected String nazwa;
    protected String czy_zespol;

    // parameterless constructor to enable inheritance in bandMember
    Autor() {
    }

    public Autor(int id_autora, String nazwa, String czy_zespol) {
        this.id_autora = id_autora;
        this.nazwa = nazwa;
        this.czy_zespol = czy_zespol;
    }
    public Autor(ResultSet rs) {
        try {
            this.nazwa = rs.getString(2);
            this.czy_zespol = rs.getString(3);
            this.id_autora = rs.getInt(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getNazwa() {
        return nazwa;
    }
    public int getId_autora() {
        return id_autora;
    }
    public boolean getCzy_zespol() {
        return czy_zespol.equals("T");
    }

    @Override
    public String toString() {
        return nazwa;
    }
}
