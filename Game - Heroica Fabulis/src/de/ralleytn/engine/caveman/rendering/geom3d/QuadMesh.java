package de.ralleytn.engine.caveman.rendering.geom3d;

import java.util.Arrays;

import javax.vecmath.Vector3f;

import de.ralleytn.engine.caveman.util.MeshUtil;
import de.ralleytn.engine.caveman.util.VectorUtil;

/**
 * Represents a simple quad mesh.
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 25.08.2018/0.3.0
 * @since 24.08.2018/0.3.0
 */
public class QuadMesh extends StaticMesh {

	private static final int[] INDICES = {
			
		3, 1, 0,
		2, 1, 3
	};
	
	private static final float[] TEXCOORDS = {
			
		0.0F, 0.0F,
		0.0F, 1.0F,
		1.0F, 1.0F,
		1.0F, 0.0F
	};
	
	/**
	 * @param p1 point 1
	 * @param p2 point 2
	 * @param p3 point 3
	 * @param p4 point 4
	 * @since 24.08.2018/0.3.0
	 */
	public QuadMesh(Vector3f p1, Vector3f p2, Vector3f p3, Vector3f p4) {
		
		super(generateMeshData(p1, p2, p3, p4));
	}
	
	/**
	 * Generates the mesh data.
	 * @param p1 point 1
	 * @param p2 point 2
	 * @param p3 point 3
	 * @param p4 point 4
	 * @return the generated mesh data
	 * @since 24.08.2018/0.3.0
	 */
	public static final MeshData generateMeshData(Vector3f p1, Vector3f p2, Vector3f p3, Vector3f p4) {
		
		float[] vertices = {
				
			p1.x, p1.y, p1.z,
			p2.x, p2.y, p2.z,
			p3.x, p3.y, p3.z,
			p4.x, p4.y, p4.z
		};

		MeshData data = new MeshData();
		data.setVertices(vertices);
		data.setIndices(INDICES);
		data.setTextureCoordinates(TEXCOORDS);
		data.setNormals(VectorUtil.toArray3f(MeshUtil.generateNormals(Arrays.asList(p1, p2, p3, p4), INDICES)));
		
		return data;
	}
}
