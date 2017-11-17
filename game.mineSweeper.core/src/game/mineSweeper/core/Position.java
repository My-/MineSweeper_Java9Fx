/*
Mindaugas Sharskus
@dateCreated: 14/11/2017
 */
package game.mineSweeper.core;


public class Position implements Comparable<Position>{
    /**
     * x & y Coordinates of position (immutable)
     */
    public final int X, Y;

    /**
     * Constructs position with given coordinates
     *
     * @param x coordinates of position
     * @param y coordinates of position
     */
    public Position(int x, int y) {
        X = x;
        Y = y;
    }

    /**
     * Constructs position with given coordinates
     *
     * @param pos coordinates of position([0]=x, [1]=y)
     */
    public Position(int[] pos) {
        X = pos[0];
        Y = pos[1];
    }

    /**
     * Constructs position with position (copy constructor)
     *
     * @param pos copy position
     */
    public Position(Position pos){
        X = pos.X;
        Y = pos.Y;
    }

    /**
     * Factory method for creating Position Object
     *
     * @param x coordinates of position
     * @param y coordinates of position
     * @return newly created object
     */
    public static Position of(int x, int y){ return new Position(x, y); }

    public int getX() { return X; }
    public int getY() { return Y; }

    /**
     * Checks if given position is cardinal and close.
     * Cardinal is North, East, South and West only( not NorthEast or any other "corner" direction).
     * For global cardinal use: isCardinal(Position pos, boolean global)
     *
     * @param pos position to check.
     * @return true if positions are cardinal to each other. Equal positions isn't cardinal.
     */
    public boolean isCardinal(Position pos){
        if( this.equals(pos) ){ return false; }
        return X == pos.X && Math.abs(Y -pos.Y) == 1
                || Y == pos.Y && Math.abs(X -pos.X) == 1;
    }

    /**
     * Checks if given position is cardinal.
     * Cardinal is North, East, South and West only( not NorthEast or any other "corner" direction).
     * For global cardinal use: isCardinal(Position pos, boolean global)
     *
     * @param pos position to check.
     * @param global if true checks global, false checks close position
     * @return true if positions are cardinal to each other. Equal positions isn't cardinal.
     */
    public boolean isCardinal(Position pos, boolean global){
        if( !global ){ return this.isCardinal(pos); }
        if( this.equals(pos) ){ return false; }
        return X == pos.X || Y == pos.Y;
    }

    /**
     * Compares Position object based on coordinates.
     * First checks X coordinate if equals then checks Y coordinate.
     *
     * @param pos Position object we comparing against
     * @return 1(bigger), 0(equals), -1(smaller)
     */
    @Override
    public int compareTo(Position pos) {
        if( X > pos.X ){ return 1; }
        if( X == pos.X ) {
            if( Y > pos.Y ){ return 1; }
            if( Y == pos.Y ){ return 0; }
        }
        return -1;
    }

    @Override
    public boolean equals(Object obj){
        if( obj instanceof Position ){
            return X == ((Position)obj).X && Y == ((Position)obj).Y;
        }
        return false;
    }

    @Override
    public int hashCode() {
        // https://stackoverflow.com/a/11742634
        int hash = 31;
        return X *hash +Y;
    }

    @Override
    public String toString() { return String.format("Pos(%d,%d)", X, Y); }
}
