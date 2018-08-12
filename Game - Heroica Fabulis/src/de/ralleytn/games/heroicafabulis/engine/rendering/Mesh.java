package de.ralleytn.games.heroicafabulis.engine.rendering;

import de.ralleytn.games.heroicafabulis.engine.Disposable;

/**
 * 
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
	 * 
	 * @param cullMode
	 * @since 04.08.2018/0.1.0
	 */
	public void setCullMode(int cullMode) {
		
		this.cullMode = cullMode;
	}
	
	/**
	 * 
	 * @return
	 * @since 04.08.2018/0.1.0
	 */
	public int getCullMode() {
		
		return this.cullMode;
	}
	
	/**
	 * 
	 * @return
	 * @since 04.08.2018/0.1.0
	 */
	public int getVertexCount() {
		
		return this.vertexCount;
	}
	
	/**
	 * 
	 * @return
	 * @since 04.08.2018/0.1.0
	 */
	public int getIndexCount() {
		
		return this.indexCount;
	}
	
	/**
	 * 
	 * @return
	 * @since 04.08.2018/0.1.0
	 */
	public int getFaceCount() {
		
		return this.faceCount;
	}
	
	/**
	 * 
	 * @return
	 * @since 04.08.2018/0.1.0
	 */
	public VertexArray getVertexArray() {
		
		return this.vertexArray;
	}
	
	/**
	 * 
	 * @return
	 * @since 10.08.2018/0.1.0
	 */
	public abstract boolean hasNormals();
	
	/**
	 * 
	 * @return
	 * @since 10.08.2018/0.1.0
	 */
	public abstract boolean hasTextureCoordinates();
}
