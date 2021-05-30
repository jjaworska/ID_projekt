package sample.model;

import java.sql.ResultSet;
import java.sql.Timestamp;

public class Album implements ToSearch{
    private int id_albumu;
    private String tytul;
    private Timestamp data_wydania;

    public Album(int id_albumu, String tytul, Timestamp data_wydania) {
        this.id_albumu = id_albumu;
        this.tytul = tytul;
        this.data_wydania = data_wydania;
    }
    public Album(ResultSet rs) {
        try {
            this.tytul = rs.getString(2);
            this.data_wydania = rs.getTimestamp(3);
            this.id_albumu = rs.getInt(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getNazwa() {
        return tytul;
    }
    public int getId_albumu() {
        return id_albumu;
    }
    public Timestamp getData_wydania() {
        return data_wydania;
    }

    @Override
    public String toString() {
        return "Utwor{" +
                ", tytul='" + tytul + '\'' +
                ", data_wydania=" + data_wydania +
                ", id_albumu=" + id_albumu +
                '}';
    }
}
