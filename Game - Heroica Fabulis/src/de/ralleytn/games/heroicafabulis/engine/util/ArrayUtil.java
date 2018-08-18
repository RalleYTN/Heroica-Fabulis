package de.ralleytn.games.heroicafabulis.engine.util;

import java.util.List;

/**
 * Utility class containing methods for working with arrays.
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 18.08.2018/0.2.0
 * @since 11.08.2018/0.1.0
 */
public final class ArrayUtil {

	/**
	 * Private because there should be no instance of this class.
	 * @since 11.08.2018/0.1.0
	 */
	private ArrayUtil() {}
	
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
	
	/**
	 * 
	 * @param list
	 * @return
	 * @since 18.08.2018/0.2.0
	 */
	public static final int[] toPrimitiveIntArray(List<Integer> list) {
		
		int[] array = new int[list.size()];
		
		for(int index = 0; index < array.length; index++) {
			
			array[index] = list.get(index);
		}
		
		return array;
	}
}
