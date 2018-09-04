package de.ralleytn.engine.caveman.rendering;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL21.*;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL31.*;
import static org.lwjgl.opengl.GL40.*;
import static org.lwjgl.opengl.GL42.*;
import static org.lwjgl.opengl.GL43.*;
import static org.lwjgl.opengl.GL44.*;
import static org.lwjgl.opengl.GL45.*;

import de.ralleytn.engine.caveman.Bindable;
import de.ralleytn.engine.caveman.LWJGLObject;
import de.ralleytn.engine.caveman.util.BufferUtil;

/**
 * Represents an OpenGL buffer.
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 15.08.2018/0.1.0
 * @since 04.08.2018/0.1.0
 */
public class GLBuffer extends LWJGLObject implements Bindable {

	// I collected all of these enums here because they are scattered among different classes and it can be really annoying to search for them.
	
	/** @since 04.08.2018/0.1.0 */ public static final int TYPE_ARRAY = GL_ARRAY_BUFFER;
	/** @since 04.08.2018/0.1.0 */ public static final int TYPE_ELEMENT_ARRAY = GL_ELEMENT_ARRAY_BUFFER;
	/** @since 04.08.2018/0.1.0 */ public static final int TYPE_PIXEL_UNPACK = GL_PIXEL_UNPACK_BUFFER;
	/** @since 04.08.2018/0.1.0 */ public static final int TYPE_PIXEL_PACK = GL_PIXEL_PACK_BUFFER;
	/** @since 04.08.2018/0.1.0 */ public static final int TYPE_QUERY = GL_QUERY_BUFFER;
	/** @since 04.08.2018/0.1.0 */ public static final int TYPE_TEXTURE = GL_TEXTURE_BUFFER;
	/** @since 04.08.2018/0.1.0 */ public static final int TYPE_TRANSFORM_FEEDBACK = GL_TRANSFORM_FEEDBACK_BUFFER;
	/** @since 04.08.2018/0.1.0 */ public static final int TYPE_UNIFORM = GL_UNIFORM_BUFFER;
	/** @since 04.08.2018/0.1.0 */ public static final int TYPE_DRAW_INDIRECT = GL_DRAW_INDIRECT_BUFFER;
	/** @since 04.08.2018/0.1.0 */ public static final int TYPE_ATOMIC_COUNTER = GL_ATOMIC_COUNTER_BUFFER;
	/** @since 04.08.2018/0.1.0 */ public static final int TYPE_DISPATCH_INDIRECT = GL_DISPATCH_INDIRECT_BUFFER;
	/** @since 04.08.2018/0.1.0 */ public static final int TYPE_SHADER_STORAGE = GL_SHADER_STORAGE_BUFFER;
	
	private int type;
	
	/**
	 * @param type the buffer type
	 * @since 04.08.2018/0.1.0
	 */
	public GLBuffer(int type) {
		
		this.type = type;
		this.id = glGenBuffers();
	}
	
	@Override
	public void dispose() {
		
		glDeleteBuffers(this.id);
		this.disposed = true;
	}
	
	@Override
	public void bind() {
		
		glBindBuffer(this.type, this.id);
	}
	
	/**
	 * This method will unbind ANY currently bound buffer.
	 * @since 04.08.2018/0.1.0
	 */
	@Override
	public void unbind() {
		
		glBindBuffer(this.type, 0);
	}
	
	/**
	 * Stores data in the buffer.
	 * @param data the data
	 * @param usage the usage
	 * @since 04.08.2018/0.1.0
	 */
	public void setData(byte[] data, int usage) {
		
		glBufferData(this.type, BufferUtil.toByteBuffer(data), usage);
	}
	
	/**
	 * Stores data in the buffer.
	 * @param data the data
	 * @param usage the usage
	 * @since 04.08.2018/0.1.0
	 */
	public void setData(int[] data, int usage) {
		
		glBufferData(this.type, BufferUtil.toIntBuffer(data), usage);
	}
	
	/**
	 * Stores data in the buffer.
	 * @param data the data
	 * @param usage the usage
	 * @since 04.08.2018/0.1.0
	 */
	public void setData(float[] data, int usage) {
		
		glBufferData(this.type, BufferUtil.toFloatBuffer(data), usage);
	}
	
	/**
	 * @return the type of this buffer
	 * @since 04.08.2018/0.1.0
	 */
	public int getType() {
		
		return this.type;
	}
	
	/**
	 * @return the memory this buffer takes up in bytes
	 * @since 04.08.2018/0.1.0
	 */
	public int getBufferSizeInBytes() {
		
		return glGetNamedBufferParameteri(this.id, GL_BUFFER_SIZE);
	}
	
	/**
	 * @return the data stored in this buffer as {@code float}s
	 * @since 04.08.2018/0.1.0
	 */
	public float[] getDataAsFloats() {
		
		float[] data = new float[this.getBufferSizeInBytes() / 4]; // Divided by 4 because one float has 4 bytes
		glGetNamedBufferSubData(this.id, 0, data);
		return data;
	}

	/**
	 * @return the data stored in this buffer as {@code int}s
	 * @since 04.08.2018/0.1.0
	 */
	public int[] getDataAsInts() {
		
		int[] data = new int[this.getBufferSizeInBytes() / 4]; // Divided by 4 because one int has 4 bytes
		glGetNamedBufferSubData(this.id, 0, data);
		return data;
	}
}
