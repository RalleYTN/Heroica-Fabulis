package de.ralleytn.games.heroicafabulis.engine.rendering;

import de.ralleytn.games.heroicafabulis.engine.Disposable;

/**
 * Represents an abstract mesh.
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 10.08.2018/0.1.0
 * @since 04.08.2018/0.1.0
 */
public abstract class Mesh implements Disposable {

	/** @since 04.08.2018/0.1.0 */ public static final int CULLING_DISABLED = 0;
	/** @since 04.08.2018/0.1.0 */ public static final int CULLING_BACK = 1;
	/** @since 04.08.2018/0.1.0 */ public static final int CULLING_FRONT = 2;
	
	protected VertexArray vertexArray;
	protected int vertexCount;
	protected int indexCount;
	protected int faceCount;
	protected int cullMode;
	
	/**
	 * @since 04.08.2018/0.1.0
	 */
	protected Mesh() {
		
		this.cullMode = CULLING_BACK;
	}
	
	@Override
	protected void finalize() throws Throwable {
		
		try {
			
			this.dispose();
			
		} finally {
			
			super.finalize();
		}
	}

	@Override
	public void dispose() {
		
		if(this.vertexArray != null) {
			
			this.vertexArray.dispose();
		}
	}
	
	/**
	 * Sets the face culling mode.
	 * @param cullMode the cull mode (default = {@link #CULLING_BACK})
	 * @since 04.08.2018/0.1.0
	 */
	public void setCullMode(int cullMode) {
		
		this.cullMode = cullMode;
	}
	
	/**
	 * @return the mode used for face culling
	 * @since 04.08.2018/0.1.0
	 */
	public int getCullMode() {
		
		return this.cullMode;
	}
	
	/**
	 * @return the vertex count of this mesh
	 * @since 04.08.2018/0.1.0
	 */
	public int getVertexCount() {
		
		return this.vertexCount;
	}
	
	/**
	 * @return the index count
	 * @since 04.08.2018/0.1.0
	 */
	public int getIndexCount() {
		
		return this.indexCount;
	}
	
	/**
	 * @return the face count
	 * @since 04.08.2018/0.1.0
	 */
	public int getFaceCount() {
		
		return this.faceCount;
	}
	
	/**
	 * @return the {@linkplain VertexArray} used to store the mesh data
	 * @since 04.08.2018/0.1.0
	 */
	public VertexArray getVertexArray() {
		
		return this.vertexArray;
	}
	
	/**
	 * @return {@code true} if this mesh has normals, else {@code false}
	 * @since 10.08.2018/0.1.0
	 */
	public abstract boolean hasNormals();
	
	/**
	 * @return {@code true} if this mesh has texture coordinates, else {@code false}
	 * @since 10.08.2018/0.1.0
	 */
	public abstract boolean hasTextureCoordinates();
}
