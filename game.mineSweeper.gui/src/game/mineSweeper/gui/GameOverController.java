package game.mineSweeper.gui;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.stage.Stage;

public class GameOverController {

    @FXML
    public void newGame(ActionEvent actionEvent) {
        // https://stackoverflow.com/a/35331321
        final Node source = (Node) actionEvent.getSource();
        final Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void exitGame(ActionEvent actionEvent) {
        Platform.exit();
    }
}
