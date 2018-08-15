package de.ralleytn.games.heroicafabulis.engine.util;

import java.util.Random;

/**
 * Utility class containing methods for working with random numbers.
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 14.08.2018/0.1.0
 * @since 14.08.2018/0.1.0
 */
public final class RandomUtil {

	private static long SEED = System.currentTimeMillis();
	private static final Random RANDOM = new Random(SEED);
	
	/**
	 * Private because no instances of this class should exist.
	 * @since 14.08.2018/0.1.0
	 */
	private RandomUtil() {}
	
	/**
	 * Sets the seed that is used for generating numbers.
	 * @param seed the seed (only 48 bit are actually used)
	 * @since 14.08.2018/0.1.0
	 */
	public static final void setSeed(long seed) {
		
		RANDOM.setSeed(seed);
		SEED = seed;
	}
	
	/**
	 * Generates a random number between the given minimal and maximal value.
	 * Both values are inclusive.
	 * @param min the minimum value
	 * @param max the maximum value
	 * @return the generated number
	 * @since 14.08.2018/0.1.0
	 */
	public static final int getInt(int min, int max) {
		
		return min + RANDOM.nextInt(max - min + 1);
	}
	
	/**
	 * Generates a random number between the given minimal and maximal value.
	 * Both values are inclusive.
	 * @param min the minimum value
	 * @param max the maximum value
	 * @return the generated number
	 * @since 14.08.2018/0.1.0
	 */
	public static final float getFloat(float min, float max) {
		
		return min + (RANDOM.nextFloat() * (max - min));
	}
	
	/**
	 * @return the seed that is used for generating numbers
	 * @since 14.08.2018/0.1.0
	 */
	public static final long getSeed() {
		
		return SEED;
	}
}
