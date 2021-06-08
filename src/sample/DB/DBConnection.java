package sample.DB;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import sample.Main;

import java.sql.*;

public class DBConnection {
    @FXML
    public TextField host;
    @FXML
    public TextField port;
    @FXML
    public TextField user;
    @FXML
    public TextField dbname;
    @FXML
    public PasswordField passwd;

    private static String URL = "jdbc:postgresql://localhost:5432/projekt";
    private static String USER = "projekt_user";
    private static String PASSWD = "projekt";
    private static Connection connection;

    public void connect() {
        try {
            URL = "jdbc:postgresql://" + host.getText() + ":" + port.getText() + "/" + dbname.getText();
            USER = user.getText();
            PASSWD = passwd.getText();
            connection = DriverManager.getConnection(URL, USER, PASSWD);
            Main.setCurrentScene("FXML/login.fxml");
        } catch(Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Wystąpił błąd połączenia");
            alert.setHeaderText("Nie udało się połączyć ze wskazaną bazą");
            alert.showAndWait();
        }
    }

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
