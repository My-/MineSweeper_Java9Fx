package game.mineSweeper.core;

import java.util.function.Predicate;

public class GameMineMap extends MineMap {

    private int minesLeft;

    /**
     * @param map
     **********************************************************/
    public GameMineMap(String map) {
        super(map);
        minesLeft = MINES;
    }

    //    public final static Predicate<PosValue> isMine = p -> p.getValue() == PosValue.MINE;
    public final static Predicate<PosValue> isMine = PosValue::isMine;
    public final static Predicate<PosValue> isNumber = p -> 0 <= p.getValue() && p.getValue() < 10;
    public final static Predicate<PosValue> isUnknown = p -> p.getValue() == PosValue.UNKNOWN;
    public final static Predicate<PosValue> isClose = isUnknown;
    public final static Predicate<PosValue> isOpen = isClose.negate(); // https://stackoverflow.com/questions/21488056/how-to-negate-a-method-reference-predicate


    public int getMinesLeft() {
        return minesLeft;
    }

    public MineMap markMine(Position position){
        minesLeft--;
        return this;
    }
    public MineMap removeMark(Position position){
        int val = this.getValue(position);
        if( 0 > val || val > 9 ) {
            if( val == PosValue.MINE){ minesLeft++; }

            super.map.get(position).setValue(PosValue.UNKNOWN);
        }
        return this;
    }

    public static char toChar(int value){
        return (char)(0 > value || value > 9 ? value : value +'0');
    }
}
