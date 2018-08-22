package de.ralleytn.games.heroicafabulis.engine.util;

import java.util.List;

/**
 * 
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 22.08.2018/0.2.0
 * @since 22.08.2018/0.2.0
 */
public final class ListUtil {

	/**
	 * 
	 * @since 22.08.2018/0.2.0
	 */
	private ListUtil() {}
	
	/**
	 * 
	 * @param list
	 * @return
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
