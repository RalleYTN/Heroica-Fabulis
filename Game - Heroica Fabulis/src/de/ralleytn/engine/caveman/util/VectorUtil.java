package de.ralleytn.engine.caveman.util;

import java.util.ArrayList;
import java.util.List;

import javax.vecmath.Vector2f;
import javax.vecmath.Vector3f;

/**
 * Utility class containing methods for working with vectors.
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 06.09.2018/0.4.0
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
	 * @param v1
	 * @param v2
	 * @return
	 * @since 06.09.2018/0.4.0
	 */
	public static final Vector3f cross(Vector3f v1, Vector3f v2) {
		
		return new Vector3f(v1.y * v2.z - v1.z * v2.y,
							v2.x * v1.z - v2.z * v1.x,
							v1.x * v2.y - v1.y * v2.x);
	}
	
	/**
	 * 
	 * @param v
	 * @param val
	 * @return
	 * @since 06.09.2018/0.4.0
	 */
	public static final Vector3f multiply(Vector3f v, float val) {
		
		return new Vector3f(v.x * val, v.y * val, v.z * val);
	}
	
	/**
	 * 
	 * @param v1
	 * @param v2
	 * @return
	 * @since 06.09.2018/0.4.0
	 */
	public static final Vector3f multiply(Vector3f v1, Vector3f v2) {
		
		return new Vector3f(v1.x * v2.x, v1.y * v2.y, v1.z * v2.z);
	}
	
	/**
	 * 
	 * @param v1
	 * @param v2
	 * @return
	 * @since 06.09.2018/0.4.0
	 */
	public static final Vector3f add(Vector3f v1, Vector3f v2) {
		
		return new Vector3f(v1.x + v2.x, v1.y + v2.y, v1.z + v2.z);
	}
	
	/**
	 * 
	 * @param v1
	 * @param v2
	 * @return
	 * @since 06.09.2018/0.4.0
	 */
	public static final Vector3f substract(Vector3f v1, Vector3f v2) {
		
		return new Vector3f(v1.x - v2.x, v1.y - v2.y, v1.z - v2.z);
	}
	
	/**
	 * Calculates the distance between two points in a 3D space.
	 * @param p1 point 1
	 * @param p2 point 2
	 * @return the distance
	 * @since 26.08.2018/0.3.0
	 */
	public static final float getAbsoluteDistance(Vector3f p1, Vector3f p2) {

		return (float)Math.abs(getDistance(p1, p2));
	}
	
	/**
	 * 
	 * @param p1
	 * @param p2
	 * @return
	 * @since 06.09.2018/0.4.0
	 */
	public static final float getDistance(Vector3f p1, Vector3f p2) {
		
		return (float)Math.pow(Math.pow(p2.x - p1.x, 2) + Math.pow(p2.y - p1.y, 2) + Math.pow(p2.z - p1.z, 2), 0.5F);
	}
	
	/**
	 * 
	 * @param p1
	 * @param p2
	 * @return
	 * @since 05.09.2018/0.4.0
	 */
	public static final float getAbsoluteDistance(Vector2f p1, Vector2f p2) {
		
		return (float)Math.abs(Math.pow(Math.pow(p2.x - p1.x, 2) + Math.pow(p2.y - p1.y, 2), 0.5F));
	}
	
	/**
	 * Converts a {@code float} array into a list of 2D vectors.
	 * @param values the {@code float} array
	 * @return the created list with the 2D vectors
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
	 * Converts a list with 3D vectors into a {@code float} array.
	 * @param vectors the list with the 3D vectors
	 * @return the created {@code float} array
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
	 * Converts a list with 2D vectors into a {@code float} array.
	 * @param vectors the list with the 2D vectors
	 * @return the created {@code float} array
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
