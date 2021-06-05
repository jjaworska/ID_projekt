package sample.DB;

import java.sql.*;

// A class for miscellaneous queries
public class DBCommunication {
    static String getGatunek(int id) {
        try {
            ResultSet rs = DBConnection.executeQuery("SELECT * FROM gatunki WHERE id_gatunku = " + id);
            return rs.getString(2);
        } catch (Exception e) {
            return null;
        }
    }
}
