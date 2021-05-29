package sample.DB;

import java.sql.ResultSet;

public class DBCommunication {
    String getGatunek(int id) {
        try {
            ResultSet rs = DBConnection.executeQuery("SELECT * FROM gatunki WHERE id_gatunku = " + id);
            return rs.getString(2);
        } catch (Exception e) {
            return null;
        }
    }
}
