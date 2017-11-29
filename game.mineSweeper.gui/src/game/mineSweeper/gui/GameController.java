package game.mineSweeper.gui;

import game.mineSweeper.core.PosValue;
import game.mineSweeper.core.Position;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import game.mineSweeper.core.Game;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class GameController implements Initializable{
    @FXML public GridPane grid;
    @FXML public AnchorPane mapArea;

    @FXML public Menu minesLeftMenu;
    @FXML public Label minesLeft;
    @FXML public Label minesTotal;
    @FXML public ProgressBar mineProgressBar;

    private Game game;



    public GridPane getGrid() {
        return grid;
    }

    public void createGrid(int x, int y){
        createButtonGrid(this.grid, x, y);
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


        // TODO: function is to long. Can't do static...
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

//        Function<Button, Button> mouseOff = it -> {
//            it.setOnMouseExited(e-> ((Button)e.getSource()).setStyle("-fx-text-fill: rgb(0,0,0);") );
//            return it;
//        };

        // Slightly shorter/cleaner version (like above commented code)
        UnaryOperator<Button> mouseOff = it -> {
            it.setOnMouseExited(e-> ((Button)e.getSource()).setStyle("-fx-text-fill: rgb(0,0,0);") );
            return it;
        };

        Function<Button, Button> mouseHover = mouseOn.andThen(mouseOff);  //Same as: it-> mouseOff.apply( mouseOn.apply(it) );

        // shorten version
        grid.getChildren().addAll(
                game.stream()
                        .map(Util::createButton)
                        .map(mouseClicked)
                        .map(mouseHover)
                        .collect(Collectors.toList())
        );

        // All this commented (below) code logic is same as code block above (7 lines)

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
        int total = game.getMinesTotal();
        int left = game.getMinesLeft();
        minesLeft.setText(""+ left);
        minesTotal.setText(""+ total);
        minesLeftMenu.setText(left +"/"+ total);

        // update progress bar
        double progress = 1.0 -(double)left / total;
        mineProgressBar.setProgress(progress);
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
        //TODO: add game over splash screen
//        mapArea.getChildren().remove(grid);

        String cssClass = "game-over";
        button.getStyleClass().add(cssClass);
        minesLeftMenu.setText("Game Over");

        openGameOverWindow();



//        minesLeftMenu.setStyle("-fx-background-color: #de1237;");
    }

    private void openGameOverWindow() {

        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("gameOver.fxml"));
    /*
     * if "fx:controller" is not set in fxml
     * fxmlLoader.setController(NewWindowController);
     */
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL); // makes all other windows(Stage) inactive.
            stage.setTitle("New Window");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            Logger logger = Logger.getLogger(getClass().getName());
            logger.log(Level.SEVERE, "Failed to create new Window.", e);
        }
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
        game = Game.create(8);
        createGrid(game.sizeX(), game.sizeY());
        updateMineDisplay();

    }

    @FXML
    public void closeGame(ActionEvent actionEvent) {
        Platform.exit();
    }
}
