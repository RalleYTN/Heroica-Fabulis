package de.ralleytn.engine.caveman.util;

/**
 * Utility class containing methods for math problems that don't fit in any of the other utility classes.
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 05.09.2018/0.4.0
 * @since 16.08.2018/0.1.0
 */
public final class MathUtil {

	/**
	 * Private because no instance of this class should exist.
	 * @since 16.08.2018/0.1.0
	 */
	private MathUtil() {}
	
	/**
	 * 
	 * @param n
	 * @param m
	 * @return
	 * @since 05.09.2018/0.4.0
	 */
	public static final int roundm(float n, float m) {
		
		return (int)(Math.floor(((n + m - 1) / m)) * m);
	}
	
	/**
	 * @param x first value
	 * @param y second value
	 * @return the smallest common factor
	 * @since 15.08.2018/0.1.0
	 */
	public static final int smallestCommonFactor(int x, int y) {
		
	    return (y == 0) ? x : smallestCommonFactor(y, x % y);
	}
	
	/**
	 * A method with the functionality of {@link Math#floor(double)} but faster.
	 * @param x the value
	 * @return the result
	 * @since 21.08.2018/0.2.0
	 * @see Math#floor(double)
	 */
	public static final int fastfloor(double x) {
		
		int xAsInt = (int)x;
		return x < xAsInt ? xAsInt - 1 : xAsInt;
	}
}
