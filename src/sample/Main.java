package sample;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Text chat");
        primaryStage.setScene(new Scene(root, 300, 300));
        primaryStage.setMinWidth(300);
        primaryStage.setMinHeight(300);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
