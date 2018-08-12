package de.ralleytn.games.heroicafabulis.engine.rendering;

import java.util.List;

import javax.vecmath.Vector3f;

import de.ralleytn.games.heroicafabulis.engine.util.MeshUtil;
import de.ralleytn.games.heroicafabulis.engine.util.VectorUtil;

/**
 * 
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 11.08.2018/0.1.0
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
	 * 
	 * @param size
	 * @since 05.08.2018/0.1.0
	 */
	public Box(Vector3f size) {
		
		this(size.x, size.y, size.z);
	}
	
	/**
	 * 
	 * @param width
	 * @param height
	 * @param depth
	 * @since 05.08.2018/0.1.0
	 */
	public Box(float width, float height, float depth) {

		super(createVertices(width, height, depth), INDICES, TEXCOORDS, createNormals(width, height, depth));
	}
	
	/**
	 * 
	 * @param width
	 * @param height
	 * @param depth
	 * @param indices
	 * @return
	 * @since 11.08.2018/0.1.0
	 */
	private static final float[] createNormals(float width, float height, float depth) {
		
		List<Vector3f> list = MeshUtil.generateNormals(VectorUtil.toList(createVertices(width, height, depth)), INDICES);
		float[] normals = new float[list.size() * 3];
		int index = 0;
		
		for(Vector3f vector : list) {
			
			normals[index] = vector.x;
			normals[index + 1] = vector.y;
			normals[index + 2] = vector.z;
			index += 3;
		}
		
		return normals;
	}
	
	/**
	 * 
	 * @param width
	 * @param height
	 * @param depth
	 * @return
	 * @since 05.08.2018/0.1.0
	 */
	private static final float[] createVertices(float width, float height, float depth) {
		
		float xh = width * 0.5F;
		float yh = height * 0.5F;
		float zh = depth * 0.5F;
		
		float x1 = xh - width;
		float y1 = yh - height;
		float z1 = zh - depth;
		
		float x2 = xh + width;
		float y2 = yh + height;
		float z2 = zh + depth;
		
		return new float[] {
				
			x1, y2, z1,
			x1, y1, z1,
			x2, y1, z1,
			x2, y2, z1,
				
			x1, y2, z2,
			x1, y1, z2,
			x2, y1, z2,
			x2, y2, z2,
				
			x2, y2, z1,
			x2, y1, z1,
			x2, y1, z2,
			x2, y2, z2,
				
			x1, y2, z1,	
			x1, y1, z1,	
			x1, y1, z2,	
			x1, y2, z2,
				
			x1, y2, z2,
			x1, y2, z1,
			x2, y2, z1,
			x2, y2, z2,
				
			x1, y1, z2,
			x1, y1, z1,
			x2, y1, z1,
			x2, y1, z2
		};
	}
}
