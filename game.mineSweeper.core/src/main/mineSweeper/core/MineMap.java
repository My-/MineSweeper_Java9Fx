package mineSweeper.core;

import java.util.*;

import static java.util.stream.Collectors.toList;

public class MineMap {

    public final int MINES;
    private int minesLeft;
    private Set<MineMapPosition> map;

    public MineMap(final int MINES, String map) {
        this.MINES = MINES;
        minesLeft = MINES;
        this.map = createMap(map);
    }

    /**
     * Creates map form String representation of the map.
     *
     * @param data String representtion of the map
     * @return created map
     */
    private static Set<MineMapPosition> createMap(String data) {
        Set<MineMapPosition> map = new HashSet<>();
        String lines[] = data.split( "\n");
        for(int i = 0; i < lines.length; i++){
            String[] square = lines[i].split(" ");
            for(int j = 0; j < square.length; j++){
                char ch = square[j].charAt(0); // get value
                if( '0' <= ch && ch <= '9' ){ // if valid number
                    int n = square[j].charAt(0) - '0'; // convert char to number
                    map.add(new MineMapPosition(i, j, n));
                }
                else{
                    int n = square[j].charAt(0); // store char as ascii number
                    map.add(new MineMapPosition(i, j, n));
                }
            }
        }
        return map;
    }

    @Override
    public String toString() {
        String s = "";
        StringJoiner sj = new StringJoiner(" ");
        List<MineMapPosition> list = this.map.stream().sorted().collect(toList());
        LinkedList<MineMapPosition> sortedList = new LinkedList<>(list);

        // TODO: fix it
        int prevX = 0, prevY = 0;
        for(MineMapPosition p : list){

            if()

            for(int ch : cha){
                if( ch > 10 ){ sj.add((char)ch +""); }
                else{ sj.add(ch +""); }
            }
            s += sj.toString() + "\n";
            sj = new StringJoiner(" ");
        }
        return s.trim();
    }

}
