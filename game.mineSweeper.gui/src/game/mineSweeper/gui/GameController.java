package game.mineSweeper.gui;

import game.mineSweeper.core.Position;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.ProgressBar;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import game.mineSweeper.core.Game;
import javafx.scene.media.AudioClip;
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
    private List<Path> gameSounds;
    private Random randomNumber = Game.random;


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

        playSound();
    }

    private void playSound() {
        int chance = 8; // Adjust chance. Smaller number bigger chance. 1/chance (10 = 1/10, 100 = 1/100, 2 = 1/2)
        int chanceToPlay = randomNumber.nextInt(chance);
        if( chanceToPlay < chance -1 ){ return; } // don't play sound if chance to play is not high enough


        // https://stackoverflow.com/a/36139922
        try{
            int i = randomNumber.nextInt(gameSounds.size()); // get random track
            System.out.println("Playing: "+ gameSounds.get(i).toUri().toString());
            new AudioClip(gameSounds.get(i).toUri().toString()).play();
        }catch(Exception e){
            System.err.println("Aborted!");
            e.printStackTrace();
        }
    }

    void createGameSounds(){
        // https://stackoverflow.com/questions/1844688/how-to-read-all-files-in-a-folder-from
        String currentPath = System.getProperty("user.dir");
        // TODO: make ir relative to *.class files
        String path = currentPath +"/game.mineSweeper.gui/src/sounds";

        try (Stream<Path> filePathStream = Files.walk(Paths.get(path))) {
            gameSounds = filePathStream
                    .filter(Files::isRegularFile)
                    .collect(Collectors.toList());
        }catch (IOException e) {
            e.printStackTrace();
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
        openNewWindow("Game Over", "gameOver.fxml");
    }

    private void openWelcomeWindow() {
        openNewWindow("Welcome", "welcome.fxml");
    }

    public void createAboutWindow(ActionEvent actionEvent) {
        openNewWindow("About", "about.fxml");
    }

    private void openNewWindow(String title, String fxmlFileName){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource( fxmlFileName ));
    /*
     * if "fx:controller" is not set in fxml
     * fxmlLoader.setController(NewWindowController);
     */
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL); // makes all other windows(Stage) inactive.
            stage.setTitle( title );
            stage.setScene(scene);
            stage.setAlwaysOnTop(true);
            stage.show();
        } catch (IOException e) {
            Logger logger = Logger.getLogger(getClass().getName());
            logger.log(Level.SEVERE, "Failed to create new Window.", e);
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        createGrid(10,10);

        openWelcomeWindow();

        createGameMap("random");
        createGameSounds();
    }

    public void createGameMap(String option) {
        switch (option.toLowerCase()){
            case "":
            case "random":
                game = Game.create("random"); break;
            case "same":
                game = Game.create("same"); break;
            default:
                System.err.println("Here is no option for: "+ option);
        }
        createGrid(game.sizeX(), game.sizeY());
        updateMineDisplay();

    }

    @FXML
    public void closeGame(ActionEvent actionEvent) {
        Platform.exit();
    }

    public void replayGame(ActionEvent actionEvent) {
        grid.getChildren().clear();
        createGameMap("same");
    }


}
