package de.ralleytn.games.heroicafabulis.engine.rendering;

import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL21;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL31;
import org.lwjgl.opengl.GL40;
import org.lwjgl.opengl.GL42;
import org.lwjgl.opengl.GL43;
import org.lwjgl.opengl.GL44;
import org.lwjgl.opengl.GL45;

import de.ralleytn.games.heroicafabulis.engine.Bindable;
import de.ralleytn.games.heroicafabulis.engine.LWJGLObject;
import de.ralleytn.games.heroicafabulis.engine.util.BufferUtil;

/**
 * 
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 11.08.2018/0.1.0
 * @since 04.08.2018/0.1.0
 */
public class GLBuffer extends LWJGLObject implements Bindable {

	/** @since 04.08.2018/0.1.0 */ public static final int TYPE_ARRAY = GL15.GL_ARRAY_BUFFER;
	/** @since 04.08.2018/0.1.0 */ public static final int TYPE_ELEMENT_ARRAY = GL15.GL_ELEMENT_ARRAY_BUFFER;
	/** @since 04.08.2018/0.1.0 */ public static final int TYPE_PIXEL_UNPACK = GL21.GL_PIXEL_UNPACK_BUFFER;
	/** @since 04.08.2018/0.1.0 */ public static final int TYPE_PIXEL_PACK = GL21.GL_PIXEL_PACK_BUFFER;
	/** @since 04.08.2018/0.1.0 */ public static final int TYPE_QUERY = GL44.GL_QUERY_BUFFER;
	/** @since 04.08.2018/0.1.0 */ public static final int TYPE_TEXTURE = GL31.GL_TEXTURE_BUFFER;
	/** @since 04.08.2018/0.1.0 */ public static final int TYPE_TRANSFORM_FEEDBACK = GL30.GL_TRANSFORM_FEEDBACK_BUFFER;
	/** @since 04.08.2018/0.1.0 */ public static final int TYPE_UNIFORM = GL31.GL_UNIFORM_BUFFER;
	/** @since 04.08.2018/0.1.0 */ public static final int TYPE_DRAW_INDIRECT = GL40.GL_DRAW_INDIRECT_BUFFER;
	/** @since 04.08.2018/0.1.0 */ public static final int TYPE_ATOMIC_COUNTER = GL42.GL_ATOMIC_COUNTER_BUFFER;
	/** @since 04.08.2018/0.1.0 */ public static final int TYPE_DISPATCH_INDIRECT = GL43.GL_DISPATCH_INDIRECT_BUFFER;
	/** @since 04.08.2018/0.1.0 */ public static final int TYPE_SHADER_STORAGE = GL43.GL_SHADER_STORAGE_BUFFER;
	
	/** @since 04.08.2018/0.1.0 */ public static final int USAGE_STATIC_DRAW = GL15.GL_STATIC_DRAW;
	/** @since 04.08.2018/0.1.0 */ public static final int USAGE_STATIC_READ = GL15.GL_STATIC_READ;
	/** @since 04.08.2018/0.1.0 */ public static final int USAGE_STATIC_COPY = GL15.GL_STATIC_COPY;
	/** @since 04.08.2018/0.1.0 */ public static final int USAGE_STREAM_DRAW = GL15.GL_STREAM_DRAW;
	/** @since 04.08.2018/0.1.0 */ public static final int USAGE_STREAM_READ = GL15.GL_STREAM_READ;
	/** @since 04.08.2018/0.1.0 */ public static final int USAGE_STREAM_COPY = GL15.GL_STREAM_COPY;
	/** @since 04.08.2018/0.1.0 */ public static final int USAGE_DYNAMIC_DRAW = GL15.GL_DYNAMIC_DRAW;
	/** @since 04.08.2018/0.1.0 */ public static final int USAGE_DYNAMIC_READ = GL15.GL_DYNAMIC_READ;
	/** @since 04.08.2018/0.1.0 */ public static final int USAGE_DYNAMIC_COPY = GL15.GL_DYNAMIC_COPY;
	
	private int type;
	
	/**
	 * 
	 * @param type
	 * @since 04.08.2018/0.1.0
	 */
	public GLBuffer(int type) {
		
		this.type = type;
		this.id = GL15.glGenBuffers();
	}
	
	@Override
	public void dispose() {
		
		GL15.glDeleteBuffers(this.id);
	}
	
	@Override
	public void bind() {
		
		GL15.glBindBuffer(this.type, this.id);
	}
	
	/**
	 * This method will unbind ANY currently bound buffer.
	 * @since 04.08.2018/0.1.0
	 */
	@Override
	public void unbind() {
		
		GL15.glBindBuffer(this.type, 0);
	}
	
	/**
	 * 
	 * @param data
	 * @param usage
	 * @since 04.08.2018/0.1.0
	 */
	public void setData(byte[] data, int usage) {
		
		GL15.glBufferData(this.type, BufferUtil.toByteBuffer(data), usage);
	}
	
	/**
	 * 
	 * @param data
	 * @param usage
	 * @since 04.08.2018/0.1.0
	 */
	public void setData(int[] data, int usage) {
		
		GL15.glBufferData(this.type, BufferUtil.toIntBuffer(data), usage);
	}
	
	/**
	 * 
	 * @param data
	 * @param usage
	 * @since 04.08.2018/0.1.0
	 */
	public void setData(float[] data, int usage) {
		
		GL15.glBufferData(this.type, BufferUtil.toFloatBuffer(data), usage);
	}
	
	/**
	 * 
	 * @return
	 * @since 04.08.2018/0.1.0
	 */
	public int getType() {
		
		return this.type;
	}
	
	/**
	 * 
	 * @return
	 * @since 04.08.2018/0.1.0
	 */
	public int getBufferSizeInBytes() {
		
		return GL45.glGetNamedBufferParameteri(this.id, GL15.GL_BUFFER_SIZE);
	}
	
	/**
	 * 
	 * @return
	 * @since 04.08.2018/0.1.0
	 */
	public float[] getDataAsFloats() {
		
		float[] data = new float[this.getBufferSizeInBytes() / 4]; // Divided by 4 because one float has 4 bytes
		GL45.glGetNamedBufferSubData(this.id, 0, data);
		return data;
	}

	/**
	 * 
	 * @return
	 * @since 04.08.2018/0.1.0
	 */
	public int[] getDataAsInts() {
		
		int[] data = new int[this.getBufferSizeInBytes() / 4]; // Divided by 4 because one int has 4 bytes
		GL45.glGetNamedBufferSubData(this.id, 0, data);
		return data;
	}
}
