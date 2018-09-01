package de.ralleytn.engine.caveman.rendering.geom;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;

import de.ralleytn.engine.caveman.rendering.GLBuffer;
import de.ralleytn.engine.caveman.rendering.VertexArray;

/**
 * Represents an unmodifiable mesh.
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 23.08.2018/0.2.0
 * @since 04.08.2018/0.1.0
 */
public class StaticMesh extends Mesh {

	private boolean normals;
	private boolean textureCoordinates;
	
	/**
	 * This constructor is not meant to be called directly.
	 * To create a new mesh use the {@linkplain de.ralleytn.games.heroicafabulis.engine.io.MeshReader}.
	 * @param data
	 * @since 04.08.2018/0.1.0
	 */
	public StaticMesh(MeshData data) {
		
		float[] vertices = data.getVertices();
		int[] indices = data.getIndices();
		float[] textureCoordinates = data.getTextureCoordinates();
		float[] normals = data.getNormals();
		
		this.vertexArray = new VertexArray();
		this.vertexCount = vertices.length / 3;
		this.indexCount = indices.length;
		this.faceCount = indices.length / 3;
		this.vertexArray.bind();
		this.indexBuffer = new GLBuffer(GLBuffer.TYPE_ELEMENT_ARRAY);
		this.indexBuffer.bind();
		this.indexBuffer.setData(indices, GL_STATIC_DRAW);
		GLBuffer vertexBuffer = this.createBuffer(GL_FLOAT, vertices);
		this.vertexArray.store(vertexBuffer, 0, 3, GL_FLOAT);
		
		if(textureCoordinates != null) {
			
			GLBuffer textureCoordinateBuffer = this.createBuffer(GL_FLOAT, textureCoordinates);
			this.vertexArray.store(textureCoordinateBuffer, 1, 2, GL_FLOAT);
			this.textureCoordinates = true;
		}
		
		if(normals != null) {
			
			GLBuffer normalBuffer = this.createBuffer(GL_FLOAT, normals);
			this.vertexArray.store(normalBuffer, 2, 3, GL_FLOAT);
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
	
	@Override
	public MeshData createMeshData() {
		
		MeshData data = new MeshData();
		data.setVertices(this.vertexArray.getBuffer(0).getDataAsFloats());
		data.setIndices(this.indexBuffer.getDataAsInts());
		
		if(this.hasTextureCoordinates()) {
			
			data.setTextureCoordinates(this.vertexArray.getBuffer(1).getDataAsFloats());
		}
		
		if(this.hasNormals()) {
			
			data.setNormals(this.vertexArray.getBuffer(2).getDataAsFloats());
		}
		
		return data;
	}
}
