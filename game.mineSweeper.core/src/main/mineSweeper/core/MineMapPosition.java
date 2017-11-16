package mineSweeper.core;

public class MineMapPosition extends Position {

    public final static int MINE = '*';
    public final static int POSSIBLY_MINE = '?';
    public final static int UNCHECKED = '_';

    private int value;

    public MineMapPosition(Position pos, int value) {
        super(pos);
        this.value = value;
    }
    public MineMapPosition(int x, int y, int value) {
        this(new Position(x, y), value);
    }

    public int getValue(){
        return value;
    }

    public void setValue(int value) throws IllegalArgumentException {
        if( 0 < value && value < 9 || value == MINE || value == POSSIBLY_MINE || value == UNCHECKED ){
            this.value = value;
        }else{
            throw new IllegalArgumentException("Illegal value: "+ value +"("+ (char)value +")");
        }

    }

    public static boolean isMine(Position position){
        if( position instanceof MineMapPosition) {
            return ((MineMapPosition) position).getValue() == MINE;
        }
        return false;
    }


}
