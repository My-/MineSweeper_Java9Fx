package game.mineSweeper.core;

import java.util.function.Predicate;

/**
 * Object to store value
 *
 * Position value (PosValue) is a position who has value (inheritance).
 * Even composition should be picked over inheritance. In this particular case
 * it makes mo sense (for me) to use "is a" (inheritance) instead of "has a" (composition).
 * In this particular case I could get away by not using any of them. But I leave
 * it as it is just as extra security (checking rightness, testing) feature.
 */
public class PosValue extends Position {

    public final static int MINE = 'x';
    public final static int POSSIBLY_MINE = '!';
    public final static int UNKNOWN = '?';

    @Deprecated(forRemoval = true) // Java 9 annotation.
    public final static Predicate<PosValue> isMine = p -> p.getValue() == MINE;

    private int value;

    public PosValue(Position pos, int value) {
        super(pos);
        setValue(value);
    }
    public PosValue(int x, int y, int value) {
        this(new Position(x, y), value);
    }
    public static PosValue of(int x, int y, int value){
        return new PosValue(x, y, value);
    }


    public int getValue(){
        return value;
    }

    /**
     * Sets value to a given value.
     *
     * @param value to set.
     * @throws IllegalArgumentException if value (0 > value > 9) or not one of (MINE: 'x', POSSIBLY_MINE: '!', UNKNOWN: '?')
     */
    void setValue(int value) throws IllegalArgumentException {
        if( 0 <= value && value < 10 || value == MINE || value == POSSIBLY_MINE || value == UNKNOWN ){
            this.value = value;
        }else{
            throw new IllegalArgumentException("Illegal value: "+ value +"("+ (char)value +")");
        }

    }

    /**
     * This method checks if given position has a MINE as value
     *
     * @param position to check
     * @return true if given position is a mine
     * @deprecated
     *      This method shouldn't be here. Reason it exist is to demonstrate possible
     *      Stream.filter() variants (in different class).
     */
    @Deprecated(forRemoval = true) // Java 9 annotation.
    public static boolean isMine(Position position){
        if( position instanceof PosValue) {
            return ((PosValue) position).getValue() == MINE;
        }
        return false;
    }
}
