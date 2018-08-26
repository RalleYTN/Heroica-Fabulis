package de.ralleytn.games.heroicafabulis.engine.util;

import java.util.List;

/**
 * Utility class providing methods for working with lists and other collections.
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 26.08.2018/0.3.0
 * @since 22.08.2018/0.2.0
 */
public final class ListUtil {

	/**
	 * Private because no instance of this class should exist.
	 * @since 22.08.2018/0.2.0
	 */
	private ListUtil() {}
	
	/**
	 * 
	 * @param list
	 * @param array
	 * @since 26.08.2018/0.3.0
	 */
	public static final void addFloatArray(List<Float> list, float[] array) {
		
		for(float element : array) {
			
			list.add(element);
		}
	}
	
	/**
	 * 
	 * @param list
	 * @return
	 * @since 26.08.2018/0.3.0
	 */
	public static final float[] toPrimitiveFloatArray(List<Float> list) {
		
		float[] array = new float[list.size()];
		
		for(int index = 0; index < array.length; index++) {
			
			array[index] = list.get(index);
		}
		
		return array;
	}
	
	/**
	 * Converts a list of integers to an {@code int} array.
	 * @param list the list
	 * @return the {@code int} array
	 * @since 22.08.2018/0.2.0
	 */
	public static final int[] toPrimitiveIntArray(List<Integer> list) {
		
		int[] array = new int[list.size()];
		
		for(int index = 0; index < array.length; index++) {
			
			array[index] = list.get(index);
		}
		
		return array;
	}
}
