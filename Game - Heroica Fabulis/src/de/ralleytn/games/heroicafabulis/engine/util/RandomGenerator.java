package de.ralleytn.games.heroicafabulis.engine.util;

import java.util.Random;

/**
 * Utility class containing methods for working with random numbers.
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 16.08.2018/0.1.0
 * @since 14.08.2018/0.1.0
 */
public class RandomGenerator {

	private long seed;
	private Random random;
	
	/**
	 * @since 16.08.2018/0.1.0
	 */
	public RandomGenerator(long seed) {
		
		this.seed = seed;
		this.random = new Random(seed);
	}
	
	/**
	 * @param objects the object array
	 * @return a random object from the given array; Every second entry in the array has to be an {@code int} that defines the weight of the entry before
	 * @since 16.08.2018/0.1.0
	 */
	public Object getObject(Object[] objects) {
		
		int sum = 0;
		
		for(int index = 1; index < objects.length; index += 2) {
			
			sum += (int)objects[index];
		}
		
		int value = this.getInt(0, sum);
		int pointer = 0;
		
		for(int index = 1; index < objects.length; index += 2) {
			
			pointer += (int)objects[index];
			
			if(pointer >= value) {
				
				return objects[index - 1];
			}
		}
		
		return null;
	}
	
	/**
	 * Generates a random number between the given minimal and maximal value.
	 * Both values are inclusive.
	 * @param min the minimum value
	 * @param max the maximum value
	 * @return the generated number
	 * @since 14.08.2018/0.1.0
	 */
	public int getInt(int min, int max) {
		
		return min + this.random.nextInt(max - min + 1);
	}
	
	/**
	 * Generates a random number between the given minimal and maximal value.
	 * Both values are inclusive.
	 * @param min the minimum value
	 * @param max the maximum value
	 * @return the generated number
	 * @since 14.08.2018/0.1.0
	 */
	public float getFloat(float min, float max) {
		
		return min + (this.random.nextFloat() * (max - min));
	}
	
	/**
	 * @return the seed that is used for generating numbers
	 * @since 14.08.2018/0.1.0
	 */
	public long getSeed() {
		
		return this.seed;
	}
}
