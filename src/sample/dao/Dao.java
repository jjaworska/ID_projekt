package sample.dao;

import sample.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Dao {
    String getGatunek(int id) {
        try {
            Connection connection = DBConnection.getConnection();
            String query = "SELECT * FROM gatunki WHERE id_gatunku = " + id;
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            return rs.getString(2);
        } catch (SQLException throwables) {
            return null;
        }
    }
}
