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
public final class MeshUtil {

	/**
	 * @since 11.08.2018/0.1.0
	 */
	private MeshUtil() {}
	
	/**
	 * 
	 * @param vertices
	 * @param indices
	 * @return
	 * @since 11.08.2018/0.1.0
	 */
	public static final List<Vector3f> generateNormals(List<Vector3f> vertices, int[] indices) {
		
		List<Vector3f> normals = new ArrayList<>();
		
		for(int index = 0; index < vertices.size(); index++) {
			
			normals.add(new Vector3f());
		}
		
		for(int index = 0; index < indices.length; index += 3) {
			
			Vector3f v1 = vertices.get(indices[index]);
			Vector3f v2 = vertices.get(indices[index + 1]);
			Vector3f v3 = vertices.get(indices[index + 2]);
			
			Vector3f faceNormal = generateFaceNormal(v1, v2, v3);
			
			normals.get(indices[index]).add(faceNormal);
			normals.get(indices[index + 1]).add(faceNormal);
			normals.get(indices[index + 2]).add(faceNormal);
		}
		
		normals.forEach(normal -> {
			
			normal.normalize();
			normal.negate();
		});
		
		return normals;
	}

	/**
	 * 
	 * @param vert1
	 * @param vert2
	 * @param vert3
	 * @return
	 * @since 11.08.2018/0.1.0
	 */
	public static final Vector3f generateFaceNormal(Vector3f vert1, Vector3f vert2, Vector3f vert3) {
		
		Vector3f v1 = new Vector3f(vert2);
		v1.sub(vert1);
		
		Vector3f v2 = new Vector3f(vert3);
		v2.sub(vert1);
		v2.cross(v2, v1);
		v2.normalize();
		
		return v2;
	}
}
