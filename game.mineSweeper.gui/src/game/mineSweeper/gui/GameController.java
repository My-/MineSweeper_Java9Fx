package game.mineSweeper.gui;

import game.mineSweeper.core.Position;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import game.mineSweeper.core.Game;

public class GameController implements Initializable{
    public GridPane grid;

    private Game game;

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



    public void createButtonGrid(GridPane grid, int X, int Y){
        // https://stackoverflow.com/a/35345799
        for (int rowIndex = 0; rowIndex < grid.getRowCount(); rowIndex++) {
            RowConstraints rc = new RowConstraints();
            rc.setVgrow(Priority.ALWAYS) ; // allow row to grow
            rc.setFillHeight(true); // ask nodes to fill height for row
            // other settings as needed...
            grid.getRowConstraints().add(rc);
        }
        for (int colIndex = 0; colIndex < grid.getColumnCount(); colIndex++) {
            ColumnConstraints cc = new ColumnConstraints();
            cc.setHgrow(Priority.ALWAYS) ; // allow column to grow
            cc.setFillWidth(true); // ask nodes to fill space for column
            // other settings as needed...
            grid.getColumnConstraints().add(cc);
        }

        for(int x = 0; x < X; x++){
            for(int y = 0; y < Y; y++){
                Position position = new Position(y,x);
                Button button;
//                button = new Button(""+ x +", "+ y);
//                button = new Button(""+ (int)(Math.random() *10)); // ad random number to button text
                button = new Button(""+ game.getValue(position));
                // https://stackoverflow.com/a/23230943
                button.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
//                button.setPrefSize(50,50);

//                button.setOnAction(e-> button.setText(""+ (int)(Math.random() *10)));

                button.setOnMouseClicked(e-> {
                    if( e.isControlDown() && e.getButton().equals(MouseButton.SECONDARY)){
                        System.out.println("Ctrl + R-mouse");
                    }else if( e.getButton().equals(MouseButton.SECONDARY) ){
                        System.out.println("R-mouse");
                        button.setText(""+ game.markMine(position).getValue(position));
                    }else{
                        System.out.println("else");
                        openCell((Button)e.getSource());
                    }
                });
//                button.autosize();
                GridPane.setFillWidth(button, true);
                GridPane.setFillHeight(button, true);
                grid.add(button , x, y);


            }
        }

    }

    private  void openCell(Button button) {
        int x = GridPane.getRowIndex(button);
        int y = GridPane.getColumnIndex(button);
        System.out.println(x +","+ y);
        Position pos = Position.of(x, y);

        button.setText(""+ game.open(pos));

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        createGrid(10,10);
        createGameMap();
    }

    private void createGameMap() {
        game = Game.create(0);
        createGrid(game.sizeX(), game.sizeY());

    }
}
