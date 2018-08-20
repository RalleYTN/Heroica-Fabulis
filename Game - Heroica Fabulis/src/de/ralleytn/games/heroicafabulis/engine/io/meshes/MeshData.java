package de.ralleytn.games.heroicafabulis.engine.io.meshes;

/**
 * 
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
	 * 
	 * @param vertices
	 * @since 20.08.2018/0.2.0
	 */
	public void setVertices(float[] vertices) {
		
		this.vertices = vertices;
	}
	
	/**
	 * 
	 * @param texCoords
	 * @since 20.08.2018/0.2.0
	 */
	public void setTextureCoordinates(float[] texCoords) {
		
		this.texCoords = texCoords;
	}
	
	/**
	 * 
	 * @param normals
	 * @since 20.08.2018/0.2.0
	 */
	public void setNormals(float[] normals) {
		
		this.normals = normals;
	}
	
	/**
	 * 
	 * @param indices
	 * @since 20.08.2018/0.2.0
	 */
	public void setIndices(int[] indices) {
		
		this.indices = indices;
	}
	
	/**
	 * 
	 * @return
	 * @since 20.08.2018/0.2.0
	 */
	public float[] getVertices() {
		
		return this.vertices;
	}
	
	/**
	 * 
	 * @return
	 * @since 20.08.2018/0.2.0
	 */
	public float[] getTextureCoordinates() {
		
		return this.texCoords;
	}
	
	/**
	 * 
	 * @return
	 * @since 20.08.2018/0.2.0
	 */
	public float[] getNormals() {
		
		return this.normals;
	}
	
	/**
	 * 
	 * @return
	 * @since 20.08.2018/0.2.0
	 */
	public int[] getIndices() {
		
		return this.indices;
	}
}
