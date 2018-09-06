package de.ralleytn.engine.caveman.rendering.geom;

import de.ralleytn.engine.caveman.shape3d.Box3D;
import de.ralleytn.engine.caveman.util.ArrayUtil;

/**
 * Represents mesh data.
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 05.09.2018/0.4.0
 * @since 20.08.2018/0.2.0
 */
public class MeshData {

	private float[] vertices;
	private float[] texCoords;
	private float[] normals;
	private int[] indices;
	
	/**
	 * Sets the vertices.
	 * @param vertices the verices.
	 * @since 20.08.2018/0.2.0
	 */
	public void setVertices(float[] vertices) {
		
		this.vertices = vertices;
	}
	
	/**
	 * Sets the texture coordinates.
	 * Can be {@code null}.
	 * @param texCoords the texture coordinates
	 * @since 20.08.2018/0.2.0
	 */
	public void setTextureCoordinates(float[] texCoords) {
		
		this.texCoords = texCoords;
	}
	
	/**
	 * Sets the normals.
	 * Can be {@code null}.
	 * @param normals the normals
	 * @since 20.08.2018/0.2.0
	 */
	public void setNormals(float[] normals) {
		
		this.normals = normals;
	}
	
	/**
	 * Sets the indices.
	 * @param indices the indices
	 * @since 20.08.2018/0.2.0
	 */
	public void setIndices(int[] indices) {
		
		this.indices = indices;
	}
	
	/**
	 * @return the vertices
	 * @since 20.08.2018/0.2.0
	 */
	public float[] getVertices() {
		
		return this.vertices;
	}
	
	/**
	 * @return the texture coordinates
	 * @since 20.08.2018/0.2.0
	 */
	public float[] getTextureCoordinates() {
		
		return this.texCoords;
	}
	
	/**
	 * @return the normals
	 * @since 20.08.2018/0.2.0
	 */
	public float[] getNormals() {
		
		return this.normals;
	}
	
	/**
	 * @return the indices
	 * @since 20.08.2018/0.2.0
	 */
	public int[] getIndices() {
		
		return this.indices;
	}

	/**
	 * 
	 * @return
	 * @since 04.09.2018/0.4.0
	 */
	public MeshData copy() {
		
		MeshData data = new MeshData();
		data.indices = ArrayUtil.copy(this.indices);
		data.normals = ArrayUtil.copy(this.normals);
		data.texCoords = ArrayUtil.copy(this.texCoords);
		data.vertices = ArrayUtil.copy(this.vertices);
		
		return data;
	}
	
	/**
	 * 
	 * @return
	 * @since 05.09.2018/0.4.0
	 */
	public Box3D getBoundingBox() {
		
		float xn = this.vertices[0];
		float yn = this.vertices[1];
		float zn = this.vertices[2];
		float xf = xn;
		float yf = yn;
		float zf = zn;
		
		for(int index = 3; index < this.vertices.length; index += 3) {
			
			float x = this.vertices[index];
			float y = this.vertices[index + 1];
			float z = this.vertices[index + 2];
			
			if(x < xn) xn = x; else if(x > xf) xf = x;
			if(y < yn) yn = y; else if(y > yf) yf = y;
			if(z < zn) zn = z; else if(z > zf) zf = z;
		}
		
		float width = xf - xn;
		float height = yf - yn;
		float depth = zf - zn;
		
		return new Box3D(xn, yn, zn, width, height, depth);
	}
}
