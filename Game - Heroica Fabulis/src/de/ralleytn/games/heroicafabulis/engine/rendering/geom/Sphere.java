package de.ralleytn.games.heroicafabulis.engine.rendering.geom;

import de.ralleytn.games.heroicafabulis.engine.io.meshes.MeshData;

/**
 * 
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 24.08.2018/0.3.0
 * @since 24.08.2018/0.3.0
 */
public class Sphere extends StaticMesh {

	/**
	 * 
	 * @param radius
	 * @param rings
	 * @param sectors
	 * @since 24.08.2018/0.3.0
	 */
	public Sphere(float radius, int rings, int sectors) {
		
		super(generateMeshData(radius, rings, sectors));
	}

	/**
	 * 
	 * @param radius
	 * @param rings
	 * @param sectors
	 * @return
	 * @since 24.08.2018/0.3.0
	 */
	private static final MeshData generateMeshData(float radius, int rings, int sectors) {
		
		final float R = (float)(1./(rings - 1));
		final float S = (float)(1./(sectors - 1));
		int rs = rings * sectors;
		int vnCount = rs * 3;
		int vtCount = rs * 2;
		float[] vertices = new float[vnCount];
		float[] normals = new float[vnCount];
		float[] texCoords = new float[vtCount];
		int[] indices = new int[rs * 4];
		int i = 0;
		int v = 0;
		int vn = 0;
		int vt = 0;
		
		for(int r = 0; r < rings; r++) {
			
			for(int s = 0; s < sectors; s++) {
				
				float y = (float)Math.sin( -(Math.PI / 2) + Math.PI * r * R);
				float x = (float)(Math.cos(2 * Math.PI * s * S) * Math.sin(Math.PI * r * R));
				float z = (float)(Math.sin(2 * Math.PI * s * S) * Math.sin(Math.PI * r * R));
				
				texCoords[vt++] = s * S;
				texCoords[vt++] = r * R;
				
				vertices[v++] = x * radius;
				vertices[v++] = y * radius;
				vertices[v++] = z * radius;
				
				normals[vn++] = x;
				normals[vn++] = y;
				normals[vn++] = z;
				
				int r1 = r + 1;
				int s1 = s + 1;
				
				indices[i++] = rs + s;
				indices[i++] = rs + s1;
				indices[i++] = r1 * sectors + s1;
				indices[i++] = r1 * sectors + s;
			}
		}
		
		MeshData data = new MeshData();
		data.setVertices(vertices);
		data.setIndices(indices);
		data.setTextureCoordinates(texCoords);
		data.setNormals(normals);
		
		return data;
	}
}
