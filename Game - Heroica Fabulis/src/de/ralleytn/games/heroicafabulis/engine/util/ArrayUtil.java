package de.ralleytn.games.heroicafabulis.engine.util;

/**
 * 
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 11.08.2018/0.1.0
 * @since 11.08.2018/0.1.0
 */
public final class ArrayUtil {

	private ArrayUtil() {}
	
	/**
	 * 
	 * @param array
	 * @param value
	 * @return
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
