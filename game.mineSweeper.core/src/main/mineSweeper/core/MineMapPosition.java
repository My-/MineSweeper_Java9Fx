package mineSweeper.core;

import java.util.function.Predicate;

public class MineMapPosition extends Position {

    public final static int MINE = '*';
    public final static int POSSIBLY_MINE = '!';
    public final static int UNKNOWN = '?';

    public final static Predicate<MineMapPosition> isMine = p -> p.getValue() == MINE;

    private int value;

    public MineMapPosition(Position pos, int value) {
        super(pos);
        setValue(value);
    }
    public MineMapPosition(int x, int y, int value) {
        this(new Position(x, y), value);
    }

    public int getValue(){
        return value;
    }

    private void setValue(int value) throws IllegalArgumentException {
        if( 0 < value && value < 9 || value == MINE || value == POSSIBLY_MINE || value == UNKNOWN ){
            this.value = value;
        }else{
            throw new IllegalArgumentException("Illegal value: "+ value +"("+ (char)value +")");
        }

    }

    /**
     * This method checks if given position has a MINE as value
     *
     *
     * @param position
     * @return
     */
    public static boolean isMine(Position position){
        if( position instanceof MineMapPosition) {
            return ((MineMapPosition) position).getValue() == MINE;
        }
        return false;
    }
}
