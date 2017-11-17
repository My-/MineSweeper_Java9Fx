package game.mineSweeper.core;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GameTest {

    Game game;
    int n = 0; // pick a map

    @Test
    @DisplayName("Testing: open(Position):char")
    void open_Test(){
        game = new Game(Data.MAP[n]);
        // TODO:

    }

    @Test
    @DisplayName("Testing: get(Position):char")
    void getValue_Test(){
        game = new Game(Data.MAP[n]);
        // TODO:

    }

    @Test
    @DisplayName("Testing: getMinesLeft():int")
    void getMinesLeft_Test(){
        game = new Game(Data.MAP[n]);
        // TODO:

    }

    @Test
    @DisplayName("Testing: markMine(Position):Game")
    void markMine_Test(){
        game = new Game(Data.MAP[n]);
        // TODO:

    }

    @Test
    @DisplayName("Testing: removeMark(Position):Game")
    void removeMark_Test(){
        game = new Game(Data.MAP[n]);
        // TODO:

    }

    @Test
    @DisplayName("Testing: static toSymbol(int):char")
    void toSymbol_Test(){
        Random random = new Random();
        int n = 100;

        for(int i = n; i > 0; i--){
            int rn = random.nextInt(10);
            assertEquals(rn, Game.toSymbol(rn));
        }

    }
}