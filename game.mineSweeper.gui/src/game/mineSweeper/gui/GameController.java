package game.mineSweeper.gui;

import game.mineSweeper.core.PosValue;
import game.mineSweeper.core.Position;
import javafx.event.Event;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

import game.mineSweeper.core.Game;

public class GameController implements Initializable{
    public GridPane grid;
    public AnchorPane mapArea;

    public Menu minesLeftMenu;
    public Label minesLeft;
    public Label minesTotal;

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

        // TODO: function is to long. convert to method
        Function<PosValue, Button> createButton = it->{
            char value = game.getValue(it);
            Button button = new Button(""+ value);
            if( '0' <= value && value <= '9' ){ button.getStyleClass().add("button-open"); }
            GridPane.setConstraints(button, it.Y, it.X);
            return button;
        };

        Function<Button, Button> mouseClicked = it->{
            it.setOnMouseClicked(e->{
                if( e.getButton().equals(MouseButton.SECONDARY) ){ markAsMine((Button) e.getSource()); }
                else{ openCell((Button) e.getSource()); }
            });
            return it;
        };

        Function<Button, Button> mouseOn = it -> {
            it.setOnMouseMoved(e-> ((Button)e.getSource()).setStyle("-fx-text-fill: rgba(0,0,0,0.29);") );
            return it;
        };

        Function<Button, Button> mouseOff = it -> {
            it.setOnMouseExited(e-> ((Button)e.getSource()).setStyle("-fx-text-fill: rgb(0,0,0);") );
            return it;
        };

        Function<Button, Button> mouseHover = mouseOn.andThen(mouseOff);  //Same as: it-> mouseOff.apply( mouseOn.apply(it) );
;

        // shorten version
        grid.getChildren().addAll(
                game.stream()
                        .map(createButton)
                        .map(mouseClicked)
                        .map(mouseHover)
                        .collect(Collectors.toList())
        );

//        for(int x = 0; x < X; x++){
//            for(int y = 0; y < Y; y++){
//                Position position = new Position(y,x);
//
//
//                Button button;
////                button = new Button(""+ x +", "+ y);
////                button = new Button(""+ (int)(Math.random() *10)); // ad random number to button text
//
//                String buttonText = ""+ game.getValue(position);
//                button = new Button(""+ buttonText);
//                // https://stackoverflow.com/a/23230943
//                button.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
////                button.setPrefSize(50,50);
//
////                button.setOnAction(e-> button.setText(""+ (int)(Math.random() *10)));
//
//                button.setOnMouseClicked(e-> {
//                    if( e.isControlDown() && e.getButton().equals(MouseButton.SECONDARY)){
////                        System.out.println("Ctrl + R-mouse");
//                    }else if( e.getButton().equals(MouseButton.SECONDARY) ){
////                        System.out.println("R-mouse");
//                        markAsMine((Button) e.getSource());
//                    }else{
////                        System.out.println("else");
//                        openCell((Button) e.getSource());
//                    }
//                });
//
//                button.setOnMouseMoved(e->{
//                    ((Button)e.getSource()).setStyle("-fx-text-fill: rgba(0,0,0,0.29);");
//                });
//
//                button.setOnMouseExited(e->{
//                    ((Button)e.getSource()).setStyle("-fx-text-fill: rgb(0,0,0);");
//                });
//
////                button.autosize();
//                GridPane.setFillWidth(button, true);
//                GridPane.setFillHeight(button, true);
//                grid.add(button , x, y);
//
//
//            }
//        }


    }

    private void markAsMine(Button button) {
        if( button.getStyleClass().contains("button-open") ){ return; } // if cell is open don't mark as mine

        int x = GridPane.getRowIndex(button);
        int y = GridPane.getColumnIndex(button);
        Position pos = Position.of(x, y);

        String cssClass = "button-mine";

        if( button.getStyleClass().contains(cssClass) ){ // is marked as mine??
            game.removeMark(pos);
            button.getStyleClass().remove(cssClass);
        }else{
            game.markMine(pos);
            button.getStyleClass().add(cssClass);
        }

        button.setText("" + game.getValue(pos));
        updateMineDisplay();

//        button.getStyleClass().stream().forEachOrdered(System.out::println); // prints all style classes on this element
//        button.setStyle("-fx-background-color: darkorange;");
    }

    private void updateMineDisplay() {
        String total = ""+ game.getMinesTotal();
        String left = ""+ game.getMinesLeft();
        minesLeft.setText(left);
        minesTotal.setText(total);
        minesLeftMenu.setText(left +"/"+ total);
    }



    private  void openCell(Button button) {
        if( button.getStyleClass().contains("button-mine") ){ return; } // if cell marked as mine don't open
        String cssClass = "button-open";

        int x = GridPane.getRowIndex(button);
        int y = GridPane.getColumnIndex(button);
        Position pos = Position.of(x, y);
//        System.out.println("In openCell(): "+ x +","+ y); // print cell coordinates

        try {
            button.setText("" + game.open(pos));
        }catch (Exception ex){
            System.err.println("GAME OVER");
            gameOver(button);
            return;
        }

        if( !button.getStyleClass().contains(cssClass) ){ // DONE: make it add only once.
            button.getStyleClass().add(cssClass);
        }
    }

    private void gameOver(Button button) {
        //TODO: add game over  on screen
//        mapArea.getChildren().remove(grid);
        String cssClass = "game-over";

        button.getStyleClass().add(cssClass);

        minesLeftMenu.setText("Game Over");
//        minesLeftMenu.setStyle("-fx-background-color: #de1237;");
    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        createGrid(10,10);
        createGameMap();
    }

    public void newGame(){
        grid.getChildren().clear();
        createGameMap();
    }

    public void createGameMap() {
        game = Game.create(0);
        createGrid(game.sizeX(), game.sizeY());
        updateMineDisplay();

    }
}
