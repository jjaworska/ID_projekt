package sample.model;

import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

public class Utwor_widok {
        //private int id_utworu;
        private int id;
        private String tytul;
        private Array autorzy;
        private Timestamp data_wydania;
        private String dlugosc; // actually Interval type
        private String gatunek;
        private float ocena;
        private String album;

        public Utwor_widok(ResultSet rs) {
            try {
                this.id = rs.getInt(1);
                this.tytul = rs.getString(2);
                this.autorzy = rs.getArray(3);
                this.gatunek = rs.getString(6);
                this.data_wydania = rs.getTimestamp(4);
                this.dlugosc = rs.getString(5);
                this.ocena = rs.getFloat(7);
                this.album = rs.getString(8);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public String getNazwa() {
            return tytul;
        }
        public String getAlbum() {
            return album;
        }
        public float ocena() {
            return ocena;
        }
        public List<String> getAutorzy() {
            try {
                return Arrays.asList((String[])autorzy.getArray());
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            return null;
        }
        public Timestamp getData_wydania() {
            return data_wydania;
        }
        public String getDlugosc() {
            return dlugosc;
        }
        public String getGatunek() {
            return gatunek;
        }

    }
