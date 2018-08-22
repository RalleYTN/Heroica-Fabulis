package de.ralleytn.games.heroicafabulis.engine.io.meshes;

/**
 * Represents mesh data.
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 20.08.2018/0.2.0
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
}
