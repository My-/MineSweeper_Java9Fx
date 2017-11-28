package game.mineSweeper.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GameOver {
    public Button exitButton;
    public Button replayButton;


    public void replayAction(ActionEvent actionEvent){
        System.out.println("replayAction in Game Over");
    }


    public void exitAction(ActionEvent actionEvent) {

    }
}
