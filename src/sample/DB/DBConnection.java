package sample.DB;

import java.sql.*;

public class DBConnection {
    private final static String URL = "jdbc:postgresql://localhost:5433/projekt";
    private final static String USER = "projekt_user";
    private final static String PASSWD = "projekt";
    private static Connection connection;

    public static Connection getConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(URL, USER, PASSWD);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return connection;
    }

    public static ResultSet executeQuery(String query) {
        try {
            Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            return statement.executeQuery();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    // veery basic test
    public static void main(String[] args) {
        try {
            Connection connection = getConnection();
            // time for some fun
            String query = "SELECT * FROM utwory";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                System.out.println(rs.getString(1) + " " + rs.getString(2));
            }
            System.out.println("Hurrra!");
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
