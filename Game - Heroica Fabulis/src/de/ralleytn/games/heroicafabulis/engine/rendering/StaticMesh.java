package de.ralleytn.games.heroicafabulis.engine.rendering;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;

/**
 * Represents an unmodifiable mesh.
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 15.08.2018/0.1.0
 * @since 04.08.2018/0.1.0
 */
public class StaticMesh extends Mesh {

	private boolean normals;
	private boolean textureCoordinates;
	
	/**
	 * This constructor is not meant to be called directly.
	 * To create a new mesh use the {@linkplain de.ralleytn.games.heroicafabulis.engine.io.MeshReader}.
	 * @param vertices the vertices
	 * @param indices the indices
	 * @param textureCoordinates the texture coordinates
	 * @param normals the normals
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
		indexBuffer.setData(indices, GL_STATIC_DRAW);
		GLBuffer vertexBuffer = this.createBuffer(GL_FLOAT, vertices);
		this.vertexArray.store(vertexBuffer, 3, GL_FLOAT);
		
		if(textureCoordinates != null) {
			
			GLBuffer textureCoordinateBuffer = this.createBuffer(GL_FLOAT, textureCoordinates);
			this.vertexArray.store(textureCoordinateBuffer, 2, GL_FLOAT);
			this.textureCoordinates = true;
		}
		
		if(normals != null) {
			
			GLBuffer normalBuffer = this.createBuffer(GL_FLOAT, normals);
			this.vertexArray.store(normalBuffer, 3, GL_FLOAT);
			this.normals = true;
		}
		
		vertexBuffer.unbind();
		this.vertexArray.unbind();
	}
	
	/**
	 * Creates a new buffer and stores the given data in it.
	 * @param type the data type
	 * @param data the data to store
	 * @return the created buffer
	 * @since 04.08.2018/0.1.0
	 */
	private GLBuffer createBuffer(int type, float[] data) {
		
		GLBuffer buffer = new GLBuffer(GLBuffer.TYPE_ARRAY);
		buffer.bind();
		buffer.setData(data, GL_STATIC_DRAW);
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
