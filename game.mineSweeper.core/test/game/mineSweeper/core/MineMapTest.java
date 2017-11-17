package game.mineSweeper.core;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;



class MineMapTest {
    @Test
    void toString_Test() {
        int m = 0;
        MineMap map = new MineMap(Data.MAP[m][1]);

        assertEquals(Data.MAP[m][1], map.toString());
    }

}