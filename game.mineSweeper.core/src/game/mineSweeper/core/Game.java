package game.mineSweeper.core;

import java.util.function.Predicate;

public class Game {
    /** Fields **/
    private MineMap userMap;
    private MineMap mineMap;
    private int minesLeft;

    /** Constructors **/
    public Game(String[] data) {
        mineMap = new MineMap(data[1]);
        userMap = new MineMap(data[2]);
        // TODO: map validation
    }

    /** Factory **/
    public static Game create(int picMap){
        return new Game(Data.MAP[picMap]);
    }

    /** Methods **/
    public char open(Position position) throws Boom{
        int value = mineMap.getValueAsInt(position);
        if( value == PosValue.MINE ){
             throw new Boom(String.format("Boom! it's a mine! (%d, %d)",
                    position.X, position.Y));
        }
        userMap.set(position, value);
        return toSymbol(value);
    }
    public char getValue(Position position){
        int n = userMap.getValueAsInt(position);
        return toSymbol(n);
    }
    public int getMinesLeft() {
        return minesLeft;
    }
    public Game markMine(Position position){
        userMap.set(mineMap.getValue(position));
        minesLeft--;
        return this;
    }
    public Game removeMark(Position position){
        int val = userMap.getValueAsInt(position);
        if( 0 > val || val > 9 ) {
            if( val == PosValue.MINE){ minesLeft++; }

            userMap.getValue(position)
                    .setValue(PosValue.UNKNOWN);
        }
        return this;
    }
    public static char toSymbol(int value){
        return (char)(0 > value || value > 9 ? value +'0': value);
    }

    /** Functions **/
    public final static Predicate<Position> inLimits_X = p -> 0 <= p.X && p.X < MineMap.size_X;
    public final static Predicate<Position> inLimits_Y = p -> 0 <= p.Y && p.Y < MineMap.size_Y;
    public final static Predicate<Position> inLimits = inLimits_X.and(inLimits_Y); // two predicates combine
    //    public final static Predicate<PosValue> isMine = p -> p.getValue() == PosValue.MINE;
    public final static Predicate<PosValue> isMine = PosValue::isMine;
    public final static Predicate<PosValue> isNumber = p -> 0 <= p.getValue() && p.getValue() < 10;
    public final static Predicate<PosValue> isUnknown = p -> p.getValue() == PosValue.UNKNOWN;
    public final static Predicate<PosValue> isClose = isUnknown;
    // https://stackoverflow.com/questions/21488056/how-to-negate-a-method-reference-predicate
    public final static Predicate<PosValue> isOpen = isClose.negate();
}
