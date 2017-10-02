package board;

/**
 * A class to represent all directions and rotation directions
 * @author Daniel McCrystal
 *
 */
public abstract class Directions {
	
	public static final int UP=0, RIGHT=1, DOWN=2, LEFT=3;
	public static final int NORTHWEST=UP, NORTHEAST=RIGHT, SOUTHEAST=DOWN, SOUTHWEST=LEFT;
	public static final int CLOCKWISE=0, COUNTERCLOCKWISE=1;
}
