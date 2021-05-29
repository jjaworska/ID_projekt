package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.model.Uzytkownik;

import java.io.IOException;

public class Main extends Application {

    public static Stage primaryStage;
    public static Uzytkownik currentUser = null;

    @Override
    public void start(Stage newPrimaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("FXML/login.fxml"));
        primaryStage = newPrimaryStage;
        primaryStage.setTitle("The music app");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void setCurrentScene(String pathToFXML) {
        Parent root = null;
        try {
            root = FXMLLoader.load(Main.class.getResource(pathToFXML));
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
