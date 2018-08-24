package de.ralleytn.games.heroicafabulis.engine.rendering.geom;

import java.util.Arrays;

import javax.vecmath.Vector3f;

import de.ralleytn.games.heroicafabulis.engine.io.meshes.MeshData;
import de.ralleytn.games.heroicafabulis.engine.util.MeshUtil;
import de.ralleytn.games.heroicafabulis.engine.util.VectorUtil;

/**
 * 
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 24.08.2018/0.3.0
 * @since 24.08.2018/0.3.0
 */
public class Quad extends StaticMesh {

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
	 * 
	 * @param p1
	 * @param p2
	 * @param p3
	 * @param p4
	 * @since 24.08.2018/0.3.0
	 */
	public Quad(Vector3f p1, Vector3f p2, Vector3f p3, Vector3f p4) {
		
		super(generateMeshData(p1, p2, p3, p4));
	}
	
	/**
	 * 
	 * @param p1
	 * @param p2
	 * @param p3
	 * @param p4
	 * @return
	 * @since 24.08.2018/0.3.0
	 */
	private static final MeshData generateMeshData(Vector3f p1, Vector3f p2, Vector3f p3, Vector3f p4) {
		
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
