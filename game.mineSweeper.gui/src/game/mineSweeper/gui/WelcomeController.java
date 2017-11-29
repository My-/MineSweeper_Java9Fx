package game.mineSweeper.gui;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class WelcomeController implements Initializable{

    @FXML public CheckBox checkBox;
    @FXML public AnchorPane anchorPane;

    private Stage stage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // https://stackoverflow.com/a/30910015
        anchorPane.sceneProperty().addListener((observableScene, oldScene, newScene) -> {
            if (oldScene == null && newScene != null) {
                // scene is set for the first time. Now its the time to listen stage changes.
                newScene.windowProperty().addListener((observableWindow, oldWindow, newWindow) -> {
                    if (oldWindow == null && newWindow != null) {
                        // stage is set. now is the right time to do whatever we need to the stage in the controller.
                        stage = (Stage) newWindow;
                        ((Stage) newWindow).setOnCloseRequest(e-> goAhead(new ActionEvent()));
                    }
                });
            }
        });

    }

    public void goAhead(ActionEvent actionEvent) {
        if(checkBox.isSelected()){
            stage.close();
        }else{
            Platform.exit();
        }
    }
}
