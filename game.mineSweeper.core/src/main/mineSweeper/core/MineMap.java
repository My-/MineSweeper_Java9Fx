package mineSweeper.core;

import java.util.*;
import java.util.function.Predicate;

import static java.util.stream.Collectors.toList;

public class MineMap {

    public final int MINES;
    private int minesLeft;
    private final Set<MineMapPosition> map; // TODO: convert to the Map<Position, ... >

//    public final static Predicate<MineMapPosition> isMine = p -> p.getValue() == MineMapPosition.MINE;
    public final static Predicate<MineMapPosition> isMine = MineMapPosition::isMine; // kinda stupid way
    public final static Predicate<MineMapPosition> isNumber = p -> 0 <= p.getValue() && p.getValue() < 10;

    public MineMap( String map) {
        this.map = createMap(map);
        this.MINES = countMines(this.map);
        minesLeft = MINES;
    }



    public int getMinesLeft() {
        return minesLeft;
    }

    int get(Position position){
        return map.
    }

    public MineMap markMine(Position position){
        minesLeft--;
        return this;
    }

    @Override
    public String toString() {
        // TODO: make it more Java8'ish(if possible)
        String s = "";
        StringJoiner sj = new StringJoiner(" ");
        List<MineMapPosition> list = this.map.stream().sorted().collect(toList());
        LinkedList<MineMapPosition> sortedList = new LinkedList<>(list);

        int prevX = 0;
        for(MineMapPosition p : sortedList){

            if( prevX < p.X ){
                s += sj.toString() + "\n";
                sj = new StringJoiner(" ");
                prevX = p.X;
            }

            if( p.getValue() > 10 ){ sj.add((char)p.getValue() +""); }
            else{ sj.add(p.getValue()+""); }
        }

        return s += sj.toString().trim(); //TODO: make better
    }

    /********************************************************/
    // Private methods
    /**
     * Creates map form String representation of the map.
     *
     * @param data String representtion of the map
     * @return created map
     */
    private static Set<MineMapPosition> createMap(String data) {
        // TODO: make it more Java8'ish(if possible)
        Set<MineMapPosition> map = new HashSet<>();
        String[] lines = data.split( "\n");

        for(int i = 0; i < lines.length; i++){
            String[] square = lines[i].split(" ");

            for(int j = 0; j < square.length; j++){
                char ch = square[j].charAt(0); // get value

                if( '0' <= ch && ch <= '9' ){ // if valid number
                    int n = square[j].charAt(0) - '0'; // convert char to number ( '5'(char) to 5(int) )
                    map.add(new MineMapPosition(i, j, n));
                }else{
                    int n = square[j].charAt(0); // store character as it representative ascii number
                    map.add(new MineMapPosition(i, j, n));
                }

            }
        }
        return map;
    }

    /**
     * Counts mines in a given map.
     *
     * @param map were mines will be checked
     * @return number of mines in a given map
     */
    private static int countMines(Set<MineMapPosition> map){
        return (int)map.stream()
                // same thing in 7 ways
                .filter(it -> it.getValue() == MineMapPosition.MINE)    // use method as instance of MinMapPosition class
                .filter(it -> it.getValue() == it.MINE)                 // use method as instance of MinMapPosition class
                .filter(it -> MineMapPosition.isMine(it))               // use static method in MinMapPosition class (must be static)
                .filter(it -> it.isMine(it))        // use method as instance of MinMapPosition class
                .filter(MineMapPosition::isMine)    // use static method in MinMapPosition class (must be static)
                .filter(MineMapPosition.isMine)     // use Predicate in MinMapPosition class
                .filter(isMine)                     // use Predicate in MineMap (this) class.
                .count();
    }

}
