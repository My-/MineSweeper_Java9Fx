package game.mineSweeper.gui;

import game.mineSweeper.core.Game;
import game.mineSweeper.core.PosValue;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;

import java.util.function.Function;

public final class Util {

    static Button createButton(PosValue posValue){
        char value = Game.toSymbol( posValue.getValue() );
        Button button = new Button(""+ value);
        if( '0' <= value && value <= '9' ){ button.getStyleClass().add("button-open"); }
        GridPane.setConstraints(button, posValue.Y, posValue.X);
        return button;
    }






}
