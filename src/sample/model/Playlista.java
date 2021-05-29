package sample.model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Playlista {
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

    @Override
    public String toString() {
        return nazwa;
    }
}
