package game.mineSweeper.core;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;

import static java.util.stream.Collectors.toList;

public class MineMap {
    /** Fields **/
    public final int MINES;
    static int size_X, size_Y;
    private final Map<Position, PosValue> map;

    /** Constructors **/
    public MineMap(String mapData) {
        this.map = createMap(mapData);
        this.MINES = countMines();;
        setLimits(mapData);
    }

    /** Factory **/
    public static MineMap of(String mapData){
        return new MineMap(mapData);
    }

    /** Functions **/

    /** Methods: Getters **/
    int getValueAsInt(Position position){
        return map.get(position).getValue();
    }
    PosValue getValue(Position position){
        return map.get(position);
    }

    Map<Position, PosValue> getMap(){
        return this.map;
    }

    /** Methods: Setters **/
    void set(PosValue posValue){
        // TODO: make it more readable.
        map.get(Position.of(posValue.X, posValue.Y)).setValue(posValue.getValue());
    }
    void set(Position position, int value){
        // TODO: make it more readable.
        map.get(position).setValue(value);
    }

    @Override
    public String toString() {
        int[][] intMap = new int[size_X][size_Y];

        map.values().parallelStream()
                // TODO: In functional code don't use external values!!
                .forEach( it-> intMap[it.X][it.Y] = it.getValue() );


        return toString(intMap);
    }

    private String toString(int[][] map){
        String s = "";
        StringJoiner sj = new StringJoiner(" ");
        for(int[] cha : map){
            for(int ch : cha){
                if( ch > 10 ){ sj.add((char)ch +""); }
                else{ sj.add(ch +""); }
            }
            s += sj.toString() + "\n";
            sj = new StringJoiner(" ");
        }
        return s.trim();
    }

    /********************************************************/
    // Private methods
    /**
     * Creates map form String representation of the map.
     *
     * @param data String representtion of the map
     * @return created map
     */
    private static Map<Position, PosValue> createMap(String data) {
        // TODO: make it more Java8'ish(if possible)
        // https://stackoverflow.com/questions/2836267/concurrenthashmap-in-java
        Map<Position, PosValue> map = new ConcurrentHashMap<>();
        String[] lines = data.split( "\n");

        for(int i = 0; i < lines.length; i++){
            String[] square = lines[i].split(" ");

            for(int j = 0; j < square.length; j++){
                char ch = square[j].charAt(0); // get value

                if( '0' <= ch && ch <= '9' ){ // if valid number
                    int n = square[j].charAt(0) - '0'; // convert char to number ( '5'(char) to 5(int) )
                    map.put(Position.of(i, j), PosValue.of(i, j, n));
                }else{
                    int n = square[j].charAt(0); // store character as it representative ascii number
                    map.put(Position.of(i, j), PosValue.of(i, j, n));
                }

            }
        }
        return map;
    }

    /**
     * Counts mines in a given map.
     * In the method here is overloaded Stream.filter() to demonstrate how it cold be done.
     *
     * @param map were mines will be checked
     * @return number of mines in a given map
     */
    int countMines(){
        Predicate<PosValue> isMine = PosValue::isMine;

        return (int)map.values().stream()
                // same thing in 7 ways
                .filter(it -> it.getValue() == PosValue.MINE)    // use method as instance of MinMapPosition class
                .filter(it -> it.getValue() == it.MINE)          // use method as instance of MinMapPosition class
                .filter(it -> PosValue.isMine(it))               // use static method in MinMapPosition class (must be static)
                .filter(it -> it.isMine(it))        // use method as instance of MinMapPosition class
                .filter(PosValue::isMine)    // use static method in MinMapPosition class (must be static)
                .filter(PosValue.isMine)     // use Predicate in MinMapPosition class
                .filter(isMine)              // use local Predicate.
                .count();
    }

    private void setLimits(String map) {
        String[] rows= map.split("\n");
        this.size_X = rows.length;
        this.size_Y = rows[0].split(" ").length;
    }
}
