package sample.model;

import java.sql.ResultSet;
import java.sql.Timestamp;

public class Utwor {
    private int id_utworu;
    private String tytul;
    private Timestamp data_dodania;
    private Timestamp data_wydania;
    private String dlugosc; // actually Interval type
    private int id_gatunku;
    private int id_albumu;
    private int numer_w_albumie;

    public Utwor(int id_utworu, String tytul, Timestamp data_dodania, Timestamp data_wydania, String dlugosc, int id_gatunku, int id_albumu, int numer_w_albumie) {
        this.id_utworu = id_utworu;
        this.tytul = tytul;
        this.data_dodania = data_dodania;
        this.data_wydania = data_wydania;
        this.dlugosc = dlugosc;
        this.id_gatunku = id_gatunku;
        this.id_albumu = id_albumu;
        this.numer_w_albumie = numer_w_albumie;
    }
    public Utwor(ResultSet rs) {
        try {
            this.id_utworu = rs.getInt(1);
            this.tytul = rs.getString(2);
            this.data_dodania = rs.getTimestamp(3);
            this.data_wydania = rs.getTimestamp(4);
            this.dlugosc = rs.getString(5);
            this.id_gatunku = rs.getInt(6);
            this.id_albumu = rs.getInt(7);
            this.numer_w_albumie = rs.getInt(8);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getTytul() {
        return tytul;
    }
    public int getId_albumu() {
        return id_albumu;
    }
    public int getId_gatunku() {
        return id_gatunku;
    }
    public Timestamp getData_dodania() {
        return data_dodania;
    }
    public Timestamp getData_wydania() {
        return data_wydania;
    }
    public String getDlugosc() {
        return dlugosc;
    }

    @Override
    public String toString() {
        return "Utwor{" +
                "id_utworu=" + id_utworu +
                ", tytul='" + tytul + '\'' +
                ", data_dodania=" + data_dodania +
                ", data_wydania=" + data_wydania +
                ", dlugosc='" + dlugosc + '\'' +
                ", id_gatunku=" + id_gatunku +
                ", id_albumu=" + id_albumu +
                ", numer_w_albumie=" + numer_w_albumie +
                '}';
    }
}
