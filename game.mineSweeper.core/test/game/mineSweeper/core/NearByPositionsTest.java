package game.mineSweeper.core;

import game.mineSweeper.core.NearByPositions;
import game.mineSweeper.core.Position;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;
import java.util.Set;

class NearByPositionsTest {
    @Test
    void of() {
        Position pos = new Position(1,1);
        NearByPositions testIt = NearByPositions.of(pos);

        assertEquals(new NearByPositions(pos).getClass(), testIt.getClass());
    }

    @Test
    void iterator() {
        Position pos = new Position(0,0);
        NearByPositions testIt = NearByPositions.of(pos);

        Set<Position> set = new HashSet<>();
        for(Position p : testIt){
//            System.out.println(p);
            set.add(p);
        }

        assertEquals(NearByPositions.POSITION_LIST, set);
    }

//    @Test
//    void spliterator() {
//    }

    @Test
    void stream() {
        Position pos = new Position(0,0);
        NearByPositions testIt = NearByPositions.of(pos);
        // TODO: make normal test!
        System.out.println("Inside: NearByPositionTest.stream()");
        testIt.stream().sorted().forEachOrdered(System.out::println);
    }

}