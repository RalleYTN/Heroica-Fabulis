package de.ralleytn.games.heroicafabulis.engine.util;

/**
 * Utility class containing methods for working with arrays.
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 25.08.2018/0.3.0
 * @since 11.08.2018/0.1.0
 */
public final class ArrayUtil {

	/**
	 * Private because there should be no instance of this class.
	 * @since 11.08.2018/0.1.0
	 */
	private ArrayUtil() {}
	
	/**
	 * Creates a copy of an {@code int} array.
	 * @param array the array
	 * @return the copy
	 * @since 25.08.2018/0.3.0
	 */
	public static final int[] copy(int[] array) {
		
		int[] copy = new int[array.length];
		
		for(int index = 0; index < copy.length; index++) {
			
			copy[index] = array[index];
		}
		
		return copy;
	}
	
	/**
	 * Creates a copy of a {@code float} array.
	 * @param array the array
	 * @return the copy
	 * @since 25.08.2018/0.3.0
	 */
	public static final float[] copy(float[] array) {
		
		float[] copy = new float[array.length];
		
		for(int index = 0; index < copy.length; index++) {
			
			copy[index] = array[index];
		}
		
		return copy;
	}
	
	/**
	 * @param array the array in question
	 * @param value the value which is asked for
	 * @return {@code true} if the given array contains the given value, else {@code false}
	 * @since 11.08.2018/0.1.0
	 */
	public static final boolean contains(int[] array, int value) {
		
		for(int element : array) {
			
			if(element == value) {
				
				return true;
			}
		}
		
		return false;
	}
}
