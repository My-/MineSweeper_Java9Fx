package game.mineSweeper.gui;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class GameController implements Initializable{
    public GridPane grid;

    public GridPane getGrid() {
        return grid;
    }

    public void createGrid(int x, int y){
        createButtonGrid(this.grid, x, y);
    }

    private void createImageGrid(GridPane grid, int X, int Y) {
//        System.out.println(System.getProperty("user.dir")); // find the IDE working dir.
        File mine2_File = new File("img/mine2.png");
        Image mine2 = new Image(mine2_File.toURI().toString(), 100, 150, false, false);
//        Image mine2 = new Image(getClass().getResource("mine2.png").toExternalForm());


        for(int x = 0; x < X; x++){
            for(int y = 0; y < Y; y++){
                ImageView element = new ImageView(mine2);
                element.autosize();

                GridPane.setConstraints(element, x, y);
                grid.getChildren().add(element);
            }
        }

    }

    public static void createButtonGrid(GridPane grid, int X, int Y){

        for(int x = 0; x < X; x++){
            for(int y = 0; y < Y; y++){
                Button button;
//                button = new Button(""+ x +", "+ y);
//                button = new Button(""+ (int)(Math.random() *10));
                button = new Button("");
                // https://stackoverflow.com/a/23230943
                button.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
//                button.setPrefSize(50,50);
                // TODO: add event
                button.setOnAction(e-> button.setText(""+ (int)(Math.random() *10)));
                button.autosize();
                grid.add(button , x, y);


            }
        }

    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        createGrid(10,10);
    }
}
