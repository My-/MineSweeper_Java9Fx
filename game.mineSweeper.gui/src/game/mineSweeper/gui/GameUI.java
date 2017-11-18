package game.mineSweeper.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;



public class GameUI extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("gameUI.fxml"));
        primaryStage.setTitle("Mine Sweeper");

//        Controller C = new Controller();
//
//        C.createGrid( 10, 10);
//
//        root.getChildren().add(C.getGrid());


        primaryStage.setScene(new Scene(root, 500, 500));
        primaryStage.show();
    }
}
