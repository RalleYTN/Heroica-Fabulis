package de.ralleytn.engine.caveman.rendering.geom3d;

import de.ralleytn.engine.caveman.Disposable;
import de.ralleytn.engine.caveman.rendering.GLBuffer;
import de.ralleytn.engine.caveman.rendering.VertexArray;

/**
 * Represents an abstract mesh.
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 04.09.2018/0.4.0
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
	protected boolean disposed;
	
	// Has to be a member because it isn't stored in the vertex array.
	// Because it is not stored in the vertex array, the garbage collector will try to collect it and call its finalize method.
	// The finalize method would then try to delete the buffer while it is still used.
	// see BUG0001
	protected GLBuffer indexBuffer;
	
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
		
		if(!this.disposed && this.vertexArray != null) {
			
			this.vertexArray.dispose();
			this.disposed = true;
		}
	}
	
	/**
	 * Wraps the mesh data in an instance of {@linkplain MeshData}.
	 * @return the created instance of {@linkplain MeshData}
	 * @since 23.08.2018/0.2.0
	 */
	public abstract MeshData createMeshData();
	
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
	
	/**
	 * @return the index buffer
	 * @since 18.08.2018/0.2.0
	 */
	public GLBuffer getIndexBuffer() {
		
		return this.indexBuffer;
	}
	
	@Override
	public boolean isDisposed() {
		
		return this.disposed;
	}
}
