package game.mineSweeper.core;

/**
 * Thrown to indicate what we "stepped" on the mine.
 *
 * @author Mindaugas Sharskus
 * @created 14/11/2017
 */
class Boom extends RuntimeException{
    Boom(String s){
        super(s);
    }
}
