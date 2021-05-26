package sample.dao;

import sample.DBConnection;
import sample.model.Utwor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class UtworDao implements AbstractDao<Utwor> {
    @Override
    public Utwor get(long id) {
        try {
            Connection connection = DBConnection.getConnection();
            String query = "SELECT * FROM utwory WHERE id_utworu = " + id;
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            return new Utwor(rs.getInt(1), rs.getString(2), rs.getTimestamp(3), rs.getTimestamp(4),
                    rs.getString(5), rs.getInt(6), rs.getInt(7), rs.getInt(8));
        } catch(Exception e) {
            return null;
        }
    }

    @Override
    public List<Utwor> getAll() {
        List<Utwor> res = new LinkedList<>();
        try {
            Connection connection = DBConnection.getConnection();
            String query = "SELECT * FROM utwory";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            while(rs.next()) {
                res.add(new Utwor(rs.getInt(1), rs.getString(2), rs.getTimestamp(3), rs.getTimestamp(4),
                        rs.getString(5), rs.getInt(6), rs.getInt(7), rs.getInt(8)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    public static void main(String[] args) {
        List<Utwor> res_query = (new UtworDao()).getAll();
        for (Utwor u : res_query) {
            System.out.println(u);
        }
    }
}
