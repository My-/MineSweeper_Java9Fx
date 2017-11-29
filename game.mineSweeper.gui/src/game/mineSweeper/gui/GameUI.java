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


//        newWindowButton.setOnMouseClicked((event) -> {
//            try {
//                FXMLLoader fxmlLoader = new FXMLLoader();
//                fxmlLoader.setLocation(getClass().getResource("NewWindow.fxml"));
//        /*
//         * if "fx:controller" is not set in fxml
//         * fxmlLoader.setController(NewWindowController);
//         */
//                Scene scene = new Scene(fxmlLoader.load(), 600, 400);
//                Stage stage = new Stage();
//                stage.setTitle("New Window");
//                stage.setScene(scene);
//                stage.show();
//            } catch (IOException e) {
//                Logger logger = Logger.getLogger(getClass().getName());
//                logger.log(Level.SEVERE, "Failed to create new Window.", e);
//            }
//        });



        Scene scene = new Scene(root);
        scene.getStylesheets().add( getClass().getResource("mineSweeperGame.css").toExternalForm() );// <--- add style css file
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        // This method is called then app is cosed
        System.out.printf("Exit Game");
    }
}
