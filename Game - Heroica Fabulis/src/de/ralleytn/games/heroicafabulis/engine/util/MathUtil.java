package de.ralleytn.games.heroicafabulis.engine.util;

/**
 * Utility class containing methods for math problems that don't fit in any of the other utility classes.
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 16.08.2018/0.1.0
 * @since 16.08.2018/0.1.0
 */
public final class MathUtil {

	/**
	 * Private because no instance of this class should exist.
	 * @since 16.08.2018/0.1.0
	 */
	private MathUtil() {}
	
	/**
	 * @param x first value
	 * @param y second value
	 * @return the smallest common factor
	 * @since 15.08.2018/0.1.0
	 */
	public static final int smallestCommonFactor(int x, int y) {
		
	    return (y == 0) ? x : smallestCommonFactor(y, x % y);
	}
}
