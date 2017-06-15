package app;

import gui.Controller;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Controller controller = new Controller();
        controller.setStage(primaryStage);

        Parent root = FXMLLoader.load(getClass().getResource("../gui/gui.fxml"));
        primaryStage.setTitle("JFK-4");
        primaryStage.setScene(new Scene(root, 590, 390));
        primaryStage.setResizable(false);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
