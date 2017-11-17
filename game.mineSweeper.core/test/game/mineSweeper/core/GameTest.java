package game.mineSweeper.core;

import game.mineSweeper.core.Game;
import game.mineSweeper.core.Data;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class GameTest {

    Game game;
    int n = 0; // pick a map

    @Test
    @DisplayName("Testing: get(Position):int")
    void get_Test(){
        game = new Game(Data.MAP[n]);
        // TODO:

    }

    @Test
    @DisplayName("Testing: open(Position):void")
    void open_Test(){
        game = new Game(Data.MAP[n]);
        // TODO:

    }

    @Test
    @DisplayName("Testing: openAround(Position):void")
    void openAround_Test(){
        game = new Game(Data.MAP[n]);
        // TODO:

    }
}