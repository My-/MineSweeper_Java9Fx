package game.mineSweeper.core;

import java.util.function.Predicate;

public class Game {

    private MineMap userMap;
    private MineMap mineMap;

    private final static Predicate<Position> inLimits_X = p -> 0 <= p.X && p.X < MineMap.size_X;
    private final static Predicate<Position> inLimits_Y = p -> 0 <= p.Y && p.Y < MineMap.size_Y;
    public final static Predicate<Position> inLimits = inLimits_X.and(inLimits_Y); // two predicates combined

    public Game(String[] data) {
        mineMap = new MineMap(data[1]);
        userMap = new GameMineMap(data[2]);
        // TODO: map validation
    }


    public char open(Position position) throws Boom{
        int value = mineMap.getValue(position);
        if( value == PosValue.MINE ){
             throw new Boom(String.format("Boom! it's a mine! (%d, %d)",
                    position.X, position.Y));
        }
        return GameMineMap.toChar(value);
    }

    public static Game create(int picMap){
        return new Game(Data.MAP[picMap]);
    }






}
