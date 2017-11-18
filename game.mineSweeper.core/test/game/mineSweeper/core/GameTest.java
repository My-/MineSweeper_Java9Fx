package game.mineSweeper.core;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Random;
import java.util.function.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class GameTest {

    Game game;
    int n = 0; // pick a map

    @Test
    @DisplayName("Testing: object creation")
    void ObjectCreation_Test(){
        game = new Game(Data.MAP[n]);
        // TODO:

    }

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
        Position pos = new Position(1,1);

        game.markMine(pos);
        char ch = game.getValue(pos);

        assertEquals('x', ch, "x != "+ ch +" int: "+ (int)ch);
    }

    @Test
    @DisplayName("Testing: removeMark(Position):Game")
    void removeMark_Test(){
        game = new Game(Data.MAP[n]);
        Position pos = new Position(1,1);
        game.markMine(pos);
        char ch = game.removeMark(pos).getValue(pos);

        assertEquals('?', ch, "? != "+ ch);

    }

    @Test
    @DisplayName("Testing: static toSymbol(int):char")
    void toSymbol_Test(){
        int n = 100;
        Random random = new Random();
        IntPredicate validNumbers = num -> 0 <= num && num < 10;
        IntPredicate positive = num -> num >= 0;
        IntConsumer goodNumbers = num -> assertEquals(num +'0', Game.toSymbol(num), "Fail goodNumbers "+ num);
        IntUnaryOperator badNumbers = num -> {
            assertFalse(num+'0' == Game.toSymbol(num), "Fail badNumbers: "+ num);
            return num;
        };
        IntConsumer goodCharacters = num -> {
            char ch = Game.toSymbol(num);
            assertEquals(num, ch, "Fail goodCharacters: "+num +" ("+ (char)num +") NOT "+(int)ch +"("+ ch +")" );
        };

//        for(int i = n; i > 0; i--){
//            int rn = random.nextInt(10);
//            assertEquals(rn, Game.toSymbol(rn));
//        }
        // same as above
        random.ints(0,10)
                .limit(100)
                .forEach(goodNumbers);

        random.ints(-100,100)
                .filter(validNumbers.negate())
                .limit(100)
                .map(badNumbers)
                .filter(positive)
                .forEach(goodCharacters);
    }
}