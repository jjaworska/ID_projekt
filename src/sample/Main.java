package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("view/sample.fxml"));
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Hello World");
        this.primaryStage.setScene(new Scene(root));
        this.primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
