package de.ralleytn.games.heroicafabulis.engine.util;

import java.util.ArrayList;
import java.util.List;

import javax.vecmath.Vector3f;

/**
 * 
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 11.08.2018/0.1.0
 * @since 11.08.2018/0.1.0
 */
public final class VectorUtil {

	/**
	 * @since 11.08.2018/0.1.0
	 */
	private VectorUtil() {}
	
	/**
	 * 
	 * @param vertices
	 * @return
	 * @since 11.08.2018/0.1.0
	 */
	public static final List<Vector3f> toList(float[] vertices) {
		
		List<Vector3f> list = new ArrayList<Vector3f>();
		
		for(int index = 0; index < vertices.length; index += 3) {
			
			list.add(new Vector3f(vertices[index], vertices[index + 1], vertices[index + 2]));
		}
		
		return list;
	}
}
