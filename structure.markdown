# MineSweeper game structure

## Modules
Modules:
- Mine Sweeper GUI
- Mine Sweeper Solver
- Mine Sweeper GameLogick

###### Mine Sweeper GUI
packages:
classes:

###### Mine Sweeper Solver
packages:
classes:

###### Mine Sweeper GameLogick
> This module will have all code related to game logick. Like: create mine map, open particular position in a map or mark mine.
- packages:
    - **code.mindaugas**
- classes:
    - **Position** class:
        - fields:
            - x:int,
            - y:int
        - methods:
            - getX():int,
            - getY():int
            - isCardinal(Position):bool
            - compareTo(Position):int (Override.Comparable)
            - equals(Object):bool (Override.Object)
            - toString():String (Override.Object)
    - **MineMap**
        - fields:
            - mines:int
            - minesLeft:int
            - map:int[][]
        - methods:
            - open(Position):this (throws:Boom, IllegalArgumentException)
            - mark(Position, String):this (throws:IllegalArgumentException. String can be "mine", "posibly mine", "duno")
            - check(Position):String (returns "0 - 9", "mine", "posiblyMine", "unchecked")
    - **Boom** Exeption (extends:RuntimeExeption)
    - **NearByPositions** implements Iterable
        - const:
            - ptivate list of offsets from curent position
        - methods:
            - Iterator.hasNext() (Override)
            - Iterator.next() (Override)
            - spliterator():Spliterator (Override)
            - stream():Stream (Override)
    - _**Tests**_:
        - fields:
            - test data
