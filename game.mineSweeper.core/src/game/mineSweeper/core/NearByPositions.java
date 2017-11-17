package game.mineSweeper.core;


import java.util.*;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

class NearByPositions implements Iterable<Position>{

    public static final Set<Position> POSITION_LIST = Set.of(
            new Position(0, -1), new Position(0, 1),
            new Position(1, -1), new Position(-1, -1),
            new Position(1, 0), new Position(-1, 0),
            new Position(1, 1), new Position(-1, 1)
    );

    private final Position currentPosition;

    public NearByPositions(Position pos){ this.currentPosition = pos; }

    public static NearByPositions of(Position pos){
        return new NearByPositions(pos);
    }


    @Override
    public Iterator<Position> iterator() {
        return new Iterator<>() {
            private final Iterator<Position> positionIterator = POSITION_LIST.iterator();

            @Override
            public boolean hasNext() {
                return positionIterator.hasNext();
            }

            @Override
            public Position next() {
                Position offset = positionIterator.next();
                return new Position(currentPosition.X +offset.X, currentPosition.Y +offset.Y);
            }
        };
    }

    // TODO: find out how to override this method.
//    @Override
//    public void forEach(Consumer<? super Position> action) {
//
//    }

    @Override
    public Spliterator<Position> spliterator() {
        return Spliterators.spliteratorUnknownSize(this.iterator(), Spliterator.ORDERED);
    }

    /**
     * Is used to create stream of near by positions.
     * Can be used fo Java 8 functional coding style.
     *
     * @return stream of near by positions.
     */
    public Stream<Position> stream() {
        return StreamSupport.stream(this.spliterator(), true);
    }
}
