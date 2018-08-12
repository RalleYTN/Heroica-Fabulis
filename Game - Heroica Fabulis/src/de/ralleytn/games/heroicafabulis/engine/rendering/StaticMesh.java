package de.ralleytn.games.heroicafabulis.engine.rendering;

import org.lwjgl.opengl.GL11;

/**
 * 
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 10.08.2018/0.1.0
 * @since 04.08.2018/0.1.0
 */
public class StaticMesh extends Mesh {

	private boolean normals;
	private boolean textureCoordinates;
	
	/**
	 * 
	 * @param vertices
	 * @param indices
	 * @param textureCoordinates
	 * @param normals
	 * @since 04.08.2018/0.1.0
	 */
	public StaticMesh(float[] vertices, int[] indices, float[] textureCoordinates, float[] normals) {
		
		this.vertexArray = new VertexArray();
		this.vertexCount = vertices.length / 3;
		this.indexCount = indices.length;
		this.faceCount = indices.length / 3;
		this.vertexArray.bind();
		GLBuffer indexBuffer = new GLBuffer(GLBuffer.TYPE_ELEMENT_ARRAY);
		indexBuffer.bind();
		indexBuffer.setData(indices, GLBuffer.USAGE_STATIC_DRAW);
		GLBuffer vertexBuffer = this.createBuffer(3, GL11.GL_FLOAT, vertices);
		this.vertexArray.store(vertexBuffer, 3, GL11.GL_FLOAT);
		
		if(textureCoordinates != null) {
			
			GLBuffer textureCoordinateBuffer = this.createBuffer(2, GL11.GL_FLOAT, textureCoordinates);
			this.vertexArray.store(textureCoordinateBuffer, 2, GL11.GL_FLOAT);
			this.textureCoordinates = true;
		}
		
		if(normals != null) {
			
			GLBuffer normalBuffer = this.createBuffer(3, GL11.GL_FLOAT, normals);
			this.vertexArray.store(normalBuffer, 3, GL11.GL_FLOAT);
			this.normals = true;
		}
		
		vertexBuffer.unbind();
		this.vertexArray.unbind();
	}
	
	/**
	 * 
	 * @param size
	 * @param type
	 * @param data
	 * @return
	 * @since 04.08.2018/0.1.0
	 */
	private GLBuffer createBuffer(int size, int type, float[] data) {
		
		GLBuffer buffer = new GLBuffer(GLBuffer.TYPE_ARRAY);
		buffer.bind();
		buffer.setData(data, GLBuffer.USAGE_STATIC_DRAW);
		return buffer;
	}

	@Override
	public boolean hasNormals() {
		
		return this.normals;
	}

	@Override
	public boolean hasTextureCoordinates() {
		
		return this.textureCoordinates;
	}
}
