package de.ralleytn.games.heroicafabulis.engine.util;

import java.util.ArrayList;
import java.util.List;

import javax.vecmath.Vector2f;
import javax.vecmath.Vector3f;

/**
 * Utility class containing methods for working with vectors.
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 18.08.2018/0.2.0
 * @since 11.08.2018/0.1.0
 */
public final class VectorUtil {

	/**
	 * Private because no instances of this class should exist.
	 * @since 11.08.2018/0.1.0
	 */
	private VectorUtil() {}
	
	/**
	 * 
	 * @param values
	 * @return
	 * @since 18.08.2018/0.2.0
	 */
	public static final List<Vector2f> toList2f(float[] values) {
		
		List<Vector2f> list = new ArrayList<>();
		
		for(int index = 0; index < values.length; index += 2) {
			
			list.add(new Vector2f(values[index], values[index + 1]));
		}
		
		return list;
	}
	
	/**
	 * Converts a float array into a {@linkplain List} of {@linkplain Vector3f}s.
	 * @param values the float array
	 * @return the created {@linkplain List}
	 * @since 11.08.2018/0.1.0
	 */
	public static final List<Vector3f> toList3f(float[] values) {
		
		List<Vector3f> list = new ArrayList<>();
		
		for(int index = 0; index < values.length; index += 3) {
			
			list.add(new Vector3f(values[index], values[index + 1], values[index + 2]));
		}
		
		return list;
	}
	
	/**
	 * 
	 * @param vectors
	 * @return
	 * @since 18.08.2018/0.2.0
	 */
	public static final float[] toArray3f(List<Vector3f> vectors) {
		
		float[] array = new float[vectors.size() * 3];
		int index = 0;
		
		for(Vector3f vector : vectors) {
			
			array[index] = vector.x;
			array[index + 1] = vector.y;
			array[index + 2] = vector.z;
			index += 3;
		}
		
		return array;
	}
	
	/**
	 * 
	 * @param vectors
	 * @return
	 * @since 18.08.2018/0.2.0
	 */
	public static final float[] toArray2f(List<Vector2f> vectors) {
		
		float[] array = new float[vectors.size() * 2];
		int index = 0;
		
		for(Vector2f vector : vectors) {
			
			array[index] = vector.x;
			array[index + 1] = vector.y;
			index += 2;
		}
		
		return array;
	}
}
