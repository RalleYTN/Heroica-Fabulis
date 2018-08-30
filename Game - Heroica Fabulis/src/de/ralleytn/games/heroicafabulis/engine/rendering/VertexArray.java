package de.ralleytn.games.heroicafabulis.engine.rendering;

import static org.lwjgl.opengl.GL30.*;

import de.ralleytn.games.heroicafabulis.engine.Bindable;
import de.ralleytn.games.heroicafabulis.engine.LWJGLObject;

/**
 * Object wrapper for the OpenGL vertex arrays.
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 30.08.2018/0.3.0
 * @since 04.08.2018/0.1.0
 */
public class VertexArray extends LWJGLObject implements Bindable {

	private GLBuffer[] buffers;
	
	/**
	 * @since 04.08.2018/0.1.0
	 */
	public VertexArray() {
		
		this.buffers = new GLBuffer[16];
		this.id = glGenVertexArrays();
	}
	
	@Override
	public void dispose() {
		
		for(GLBuffer buffer : this.buffers) {
			
			buffer.dispose();
		}
		
		glDeleteVertexArrays(this.id);
		this.buffers = null;
	}

	@Override
	public void bind() {
		
		glBindVertexArray(this.id);
	}

	/**
	 * This method will unbind ANY currently bound vertex array.
	 * @since 04.08.2018/0.1.0
	 */
	@Override
	public void unbind() {
		
		glBindVertexArray(0);
	}
	
	/**
	 * Stores a buffer in the vertex array.
	 * @param buffer the buffer
	 * @param index the index at which the buffer should be stored
	 * @param vertexSize the vertex size
	 * @param dataType the data type
	 * @since 04.08.2018/0.1.0
	 */
	public void store(GLBuffer buffer, int index, int vertexSize, int dataType) {
		
		buffer.bind();
		glVertexAttribPointer(index, vertexSize, dataType, false, 0, 0);
		this.buffers[index] = buffer;
	}
	
	/**
	 * Enables the buffer at the given index.
	 * @param index buffer index
	 * @since 04.08.2018/0.1.0
	 */
	public void enable(int index) {
		
		glEnableVertexAttribArray(index);
	}

	/**
	 * Disables the buffer at the given index.
	 * @param index buffer index
	 * @since 04.08.2018/0.1.0
	 */
	public void disable(int index) {
		
		glDisableVertexAttribArray(index);
	}

	/**
	 * @param index buffer index
	 * @return the buffer at the given index
	 * @since 04.08.2018/0.1.0
	 */
	public GLBuffer getBuffer(int index) {
		
		return this.buffers[index];
	}
}
