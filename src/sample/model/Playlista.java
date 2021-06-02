package sample.model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Playlista implements ToSearch {
    int id_playlisty;
    int tworca;
    String nazwa;
    String dostep;

    public Playlista(ResultSet rs) throws SQLException {
        this.id_playlisty = rs.getInt(1);
        this.tworca = rs.getInt(2);
        this.nazwa = rs.getString(3);
        this.dostep = rs.getString(4);
    }

    public String getNazwa() {
        return nazwa;
    }

    public int getId_playlisty(){return id_playlisty;}

    public int getId_tworcy(){return tworca;}

    @Override
    public String toString() {
        return nazwa;
    }
}
