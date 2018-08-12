package de.ralleytn.games.heroicafabulis.engine.rendering;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import de.ralleytn.games.heroicafabulis.engine.Bindable;
import de.ralleytn.games.heroicafabulis.engine.LWJGLObject;

/**
 * 
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 11.08.2018/0.1.0
 * @since 04.08.2018/0.1.0
 */
public class VertexArray extends LWJGLObject implements Bindable {

	private List<GLBuffer> buffers;
	
	/**
	 * @since 04.08.2018/0.1.0
	 */
	public VertexArray() {
		
		this.buffers = new ArrayList<>();
		this.id = GL30.glGenVertexArrays();
	}
	
	@Override
	public void dispose() {
		
		for(GLBuffer buffer : this.buffers) {
			
			buffer.dispose();
		}
		
		GL30.glDeleteVertexArrays(this.id);
		this.buffers = null;
	}

	@Override
	public void bind() {
		
		GL30.glBindVertexArray(this.id);
	}

	/**
	 * 
	 * @since 04.08.2018/0.1.0
	 */
	@Override
	public void unbind() {
		
		GL30.glBindVertexArray(0);
	}
	
	/**
	 * 
	 * @param buffer
	 * @param vertexSize
	 * @param dataType
	 * @since 04.08.2018/0.1.0
	 */
	public void store(GLBuffer buffer, int vertexSize, int dataType) {
		
		buffer.bind();
		GL20.glVertexAttribPointer(this.buffers.size(), vertexSize, dataType, false, 0, 0);
		this.buffers.add(buffer);
	}
	
	/**
	 * 
	 * @param index
	 * @since 04.08.2018/0.1.0
	 */
	public void enable(int index) {
		
		GL20.glEnableVertexAttribArray(index);
	}

	/**
	 * 
	 * @param index
	 * @since 04.08.2018/0.1.0
	 */
	public void disable(int index) {
		
		GL20.glDisableVertexAttribArray(index);
	}

	/**
	 * 
	 * @param index
	 * @return
	 * @since 04.08.2018/0.1.0
	 */
	public GLBuffer getBuffer(int index) {
		
		return this.buffers.get(index);
	}
}
