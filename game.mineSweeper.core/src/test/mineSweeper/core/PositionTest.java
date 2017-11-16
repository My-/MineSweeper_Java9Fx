package mineSweeper.core;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PositionTest {
    @Test
    void of_Test() {
        Position testIt = Position.of(1,1);

        assertEquals(new Position(1,1).getClass(), testIt.getClass());
    }

    @Test
    void getX_Test() {
        Position testIt = Position.of(1,1);

        assertEquals(1, testIt.getX());
    }

    @Test
    void getY_Test() {
        Position testIt = Position.of(1,1);

        assertEquals(1, testIt.getX());
    }

    @Test
    void isCardinal_Test() {
        Position testIt = Position.of(1,1);
        List<Position> cardinalList = List.of(
                new Position(0,1), new Position(2,1),
                new Position(1,0), new Position(1,2)
        );
        List<Position> notCardinal = List.of(
                new Position(0,0), new Position(2,0),
                new Position(2,0), new Position(2,2),
                new Position(2,5), new Position(-2,-1),
                new Position(3,-8), new Position(0,-1)
        );
        List<Position> cardinalGlobal = List.of(
                new Position(1,8), new Position(1,-6),
                new Position(-5,1), new Position(5,1)
        );


        cardinalList.stream().forEach(it->
                assertTrue(testIt.isCardinal(it), "Error in cardinal: "+ it));
        notCardinal.stream().forEach(it->
                assertFalse(testIt.isCardinal(it), "Error in NOT cardinal: "+ it));
        cardinalGlobal.stream().forEach(it->
                assertTrue(testIt.isCardinal(it, true), "Error in cardinal and global: "+ it));
        notCardinal.stream().forEach(it->
                assertFalse(testIt.isCardinal(it, true), "Error in NOT cardinal and global: "+ it));
    }

    @Test
    void compareTo_Test() {
        Position testIt = Position.of(1,1);
        List<Position> smaler = List.of(
                new Position(0,0), new Position(0,1),
                new Position(1,0), new Position(-1,-1)
        );
        List<Position> bigger = List.of(
                new Position(2,2), new Position(2,1),
                new Position(1,2), new Position(2,3)
        );

        smaler.stream().forEach(it->
                assertTrue(it.compareTo(testIt) < 0,
                        String.format("Should be (%s) smaller than (%s)", it, testIt)));
        smaler.stream().forEach(it->
                assertFalse(it.compareTo(testIt) > 0,
                        String.format("Should be (%s) smaller than (%s)", it, testIt)));
        bigger.stream().forEach(it->
                assertTrue(it.compareTo(testIt) > 0,
                        String.format("Should be (%s) bigger than (%s)", it, testIt)));
        bigger.stream().forEach(it->
                assertFalse(it.compareTo(testIt) < 0,
                        String.format("Should be (%s) bigger than (%s)", it, testIt)));
        assertTrue(testIt.compareTo(new Position(1,1)) == 0);
    }

    @Test
    void equals_Test() {
        Position testIt = Position.of(1,1);

        assertTrue(testIt.equals(new Position(1,1)));
    }

    @Test
    void hashCode_Test() {
    }

    @Test
    void toString_Test() {
    }

}