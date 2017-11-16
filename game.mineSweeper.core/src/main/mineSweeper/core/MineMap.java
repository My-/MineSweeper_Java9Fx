package mineSweeper.core;

import java.util.*;

import static java.util.stream.Collectors.toList;

public class MineMap {

    public final int MINES;
    private int minesLeft;
    private final Set<MineMapPosition> map;


    public MineMap( String map) {
        this.map = createMap(map);
        this.MINES = countMines(this.map);
        minesLeft = MINES;
    }

    public int getMinesLeft() {
        return minesLeft;
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

    private static int countMines(Set<MineMapPosition> map){
        return (int)map.stream()
                .filter(MineMapPosition::isMine)
                .count();
    }

}
