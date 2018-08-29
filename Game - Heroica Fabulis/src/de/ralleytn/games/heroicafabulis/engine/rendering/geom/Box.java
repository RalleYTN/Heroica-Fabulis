package de.ralleytn.games.heroicafabulis.engine.rendering.geom;

import java.util.List;

import javax.vecmath.Vector3f;

import de.ralleytn.games.heroicafabulis.engine.util.MeshUtil;
import de.ralleytn.games.heroicafabulis.engine.util.VectorUtil;

/**
 * Represents a simple box mesh.
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 29.08.2018/0.3.0
 * @since 05.08.2018/0.1.0
 */
public class Box extends StaticMesh {

	private static final int[] INDICES = {
			
		3, 1, 0,
		2, 1, 3,
		4, 5, 7,
		7, 5, 6,
		11, 9, 8,
		10, 9, 11,
		12, 13, 15,
		15, 13, 14,
		19, 17, 16,
		18, 17 ,19,
		20, 21, 23,
		23, 21, 22
	};
	private static final float[] TEXCOORDS = {
			
		0.0F, 0.0F,
		0.0F, 1.0F,
		1.0F, 1.0F,
		1.0F, 0.0F,
		0.0F, 0.0F,
		0.0F, 1.0F,
		1.0F, 1.0F,
		1.0F, 0.0F,
		0.0F, 0.0F,
		0.0F, 1.0F,
		1.0F, 1.0F,
		1.0F, 0.0F,
		0.0F, 0.0F,
		0.0F, 1.0F,
		1.0F, 1.0F,
		1.0F, 0.0F,
		0.0F, 0.0F,
		0.0F, 1.0F,
		1.0F, 1.0F,
		1.0F, 0.0F,
		0.0F, 0.0F,
		0.0F, 1.0F,
		1.0F, 1.0F,
		1.0F, 0.0F
	};
	
	/**
	 * @param size the size the box should have
	 * @since 05.08.2018/0.1.0
	 */
	public Box(Vector3f size) {
		
		this(size.x, size.y, size.z);
	}
	
	/**
	 * @param width the width
	 * @param height the height
	 * @param depth the depth
	 * @since 05.08.2018/0.1.0
	 */
	public Box(float width, float height, float depth) {

		super(generateMeshData(width, height, depth));
	}
	
	/**
	 * Creates the mesh data for the box.
	 * @param width the box width
	 * @param height the box height
	 * @param depth the box depth
	 * @return the created mesh data
	 * @since 20.08.2018/0.2.0
	 */
	public static final MeshData generateMeshData(float width, float height, float depth) {
		
		float[] vertices = new float[] {
				
			0, height, 0,
			0, 0, 0,
			width, 0, 0,
			width, height, 0,
				
			0, height, depth,
			0, 0, depth,
			width, 0, depth,
			width, height, depth,
				
			width, height, 0,
			width, 0, 0,
			width, 0, depth,
			width, height, depth,
				
			0, height, 0,	
			0, 0, 0,	
			0, 0, depth,	
			0, height, depth,
				
			0, height, depth,
			0, height, 0,
			width, height, 0,
			width, height, depth,
				
			0, 0, depth,
			0, 0, 0,
			width, 0, 0,
			width, 0, depth
		};
		
		List<Vector3f> list = MeshUtil.generateNormals(VectorUtil.toList3f(vertices), INDICES);
		float[] normals = new float[list.size() * 3];
		int index = 0;
		
		for(Vector3f vector : list) {
			
			normals[index] = vector.x;
			normals[index + 1] = vector.y;
			normals[index + 2] = vector.z;
			index += 3;
		}
		
		MeshData data = new MeshData();
		data.setVertices(vertices);
		data.setNormals(normals);
		data.setIndices(INDICES);
		data.setTextureCoordinates(TEXCOORDS);
		
		return data;
	}
}
