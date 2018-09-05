package de.ralleytn.engine.caveman.util;

import java.util.Random;

import javax.vecmath.Vector2f;

/**
 * Utility class containing methods for working with random numbers.
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 05.09.2018/0.4.0
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
	 * 
	 * @param radius
	 * @return
	 * @since 05.09.2018/0.4.0
	 */
	public Vector2f getPointInCircle(float radius) {
		
		double t = 2.0F * Math.PI * this.random.nextFloat();
		double u = this.random.nextFloat() + this.random.nextFloat();
		double r = u > 1.0D ? 2.0D - u : u;
		r *= radius;
		return new Vector2f((float)(r * Math.cos(t)), (float)(r * Math.sin(t)));
	}
	
	/**
	 * 
	 * @param width
	 * @param height
	 * @return
	 * @since 05.09.2018/0.4.0
	 */
	public Vector2f getPointInEllipse(float width, float height) {
		
		double t = 2.0F * Math.PI * this.random.nextFloat();
		double u = this.random.nextFloat() + this.random.nextFloat();
		double r = u > 1.0D ? 2.0D - u : u;
		return new Vector2f((float)(width * r * Math.cos(t) / 2), (float)(height * r * Math.sin(t) / 2));
	}
	
	/**
	 * 
	 * @param width
	 * @param height
	 * @return
	 * @since 05.09.2018/0.4.0
	 */
	public Vector2f getPointInRect(float width, float height) {
		
		return new Vector2f(this.random.nextFloat() * width, this.random.nextFloat() * height);
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
