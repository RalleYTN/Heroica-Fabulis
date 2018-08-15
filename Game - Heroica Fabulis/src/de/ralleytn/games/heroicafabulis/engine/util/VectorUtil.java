package de.ralleytn.games.heroicafabulis.engine.util;

import java.util.ArrayList;
import java.util.List;

import javax.vecmath.Vector3f;

/**
 * Utility class containing methods for working with vectors.
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 14.08.2018/0.1.0
 * @since 11.08.2018/0.1.0
 */
public final class VectorUtil {

	/**
	 * Private because no instances of this class should exist.
	 * @since 11.08.2018/0.1.0
	 */
	private VectorUtil() {}
	
	/**
	 * Converts a float array into a {@linkplain List} of {@linkplain Vector3f}s.
	 * @param vertices the float array
	 * @return the created {@linkplain List}
	 * @since 11.08.2018/0.1.0
	 */
	public static final List<Vector3f> toList3f(float[] vertices) {
		
		List<Vector3f> list = new ArrayList<Vector3f>();
		
		for(int index = 0; index < vertices.length; index += 3) {
			
			list.add(new Vector3f(vertices[index], vertices[index + 1], vertices[index + 2]));
		}
		
		return list;
	}
}
