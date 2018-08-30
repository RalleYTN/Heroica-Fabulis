package de.ralleytn.games.heroicafabulis.engine.rendering;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;

import javax.vecmath.Color4f;

import static org.lwjgl.opengl.GL45.*;

import de.ralleytn.games.heroicafabulis.engine.Bindable;
import de.ralleytn.games.heroicafabulis.engine.LWJGLObject;

/**
 * Represents an OpenGL texture.
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 30.08.2018/0.3.0
 * @since 04.08.2018/0.1.0
 */
public class Texture extends LWJGLObject implements Bindable {

	// I collect the enums here because they are scattered among different classes which makes it really annoying to find them
	
	/** @since 04.08.2018/0.1.0 */ public static final int FILTER_NEAREST = GL_NEAREST;
	/** @since 04.08.2018/0.1.0 */ public static final int FILTER_LINEAR = GL_LINEAR;
	/** @since 04.08.2018/0.1.0 */ public static final int FILTER_NEAREST_MIPMAP_NEAREST = GL_NEAREST_MIPMAP_NEAREST;
	/** @since 04.08.2018/0.1.0 */ public static final int FILTER_NEAREST_MIPMAP_LINEAR = GL_NEAREST_MIPMAP_LINEAR;
	/** @since 04.08.2018/0.1.0 */ public static final int FILTER_LINEAR_MIPMAP_NEAREST = GL_LINEAR_MIPMAP_NEAREST;
	/** @since 04.08.2018/0.1.0 */ public static final int FILTER_LINEAR_MIPMAP_LINEAR = GL_LINEAR_MIPMAP_LINEAR;
	
	/** @since 04.08.2018/0.1.0 */ public static final int WRAP_REPEAT = GL_REPEAT;
	/** @since 04.08.2018/0.1.0 */ public static final int WRAP_MIRRORED_REPEAT = GL_MIRRORED_REPEAT;
	/** @since 04.08.2018/0.1.0 */ public static final int WRAP_CLAMP_TO_EDGE = GL_CLAMP_TO_EDGE;
	/** @since 04.08.2018/0.1.0 */ public static final int WRAP_CLAMP_TO_BORDER = GL_CLAMP_TO_BORDER;
	
	private int width;
	private int height;
	
	/**
	 * @param data the texture data
	 * @since 04.08.2018/0.1.0
	 */
	public Texture(TextureData data) {
		
		this(data, false);
	}
	
	/**
	 * @param data the texture data
	 * @param generateMipMap if this value is true, a mipmap will be generated for this texture
	 * @since 30.08.2018/0.3.0
	 */
	public Texture(TextureData data, boolean generateMipMap) {
		
		this.initialize(data, generateMipMap);
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, this.width, this.height, 0, GL_RGBA, GL_UNSIGNED_BYTE, this.createBuffer(data));
		
		if(generateMipMap) {
			
			glGenerateTextureMipmap(this.id);
		}
		
		glBindTexture(GL_TEXTURE_2D, 0);
	}
	
	/**
	 * @param data the texture data for level 0
	 * @param mipmap the texture data for the other levels (will not use mipmaps if {@code null})
	 * @since 30.08.2018/0.3.0
	 */
	public Texture(TextureData data, TextureData[] mipmap) {

		boolean hasMipmap = mipmap != null;
		this.initialize(data, hasMipmap);
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, this.width, this.height, 0, GL_RGBA, GL_UNSIGNED_BYTE, this.createBuffer(data));
		
		if(hasMipmap) {
			
			for(int index = 0; index < mipmap.length; index++) {
				
				glTexImage2D(GL_TEXTURE_2D, index + 1, GL_RGBA, this.width, this.height, 0, GL_RGBA, GL_UNSIGNED_BYTE, this.createBuffer(mipmap[index]));
			}
		}
		
		glBindTexture(GL_TEXTURE_2D, 0);
	}
	
	/**
	 * Initializes the class fields and default texture parameters.
	 * @param data the texture data on level 0
	 * @param useMipmap {@code true} if mipmaps are used, else {@code false}
	 * @since 30.08.2018/0.3.0
	 */
	private final void initialize(TextureData data, boolean useMipmap) {
		
		this.width = data.getWidth();
		this.height = data.getHeight();
		
		this.id = glGenTextures();
		glBindTexture(GL_TEXTURE_2D, this.id);
		glTextureParameteri(this.id, GL_TEXTURE_MIN_FILTER, useMipmap ? FILTER_NEAREST_MIPMAP_NEAREST : FILTER_NEAREST);
		glTextureParameteri(this.id, GL_TEXTURE_MAG_FILTER, FILTER_NEAREST);
		glTextureParameteri(this.id, GL_TEXTURE_WRAP_S, WRAP_REPEAT);
		glTextureParameteri(this.id, GL_TEXTURE_WRAP_T, WRAP_REPEAT);
		glPixelStorei(GL_UNPACK_ALIGNMENT, 1);
	}
	
	/**
	 * Wraps the pixel data in an {@linkplain IntBuffer}.
	 * @param data the texture data
	 * @return the created {@linkplain IntBuffer}
	 * @since 30.08.2018/0.3.0
	 */
	private final IntBuffer createBuffer(TextureData data) {
		
		int[] pixels = data.getPixels();
		IntBuffer buffer = ByteBuffer.allocateDirect(pixels.length << 2).order(ByteOrder.nativeOrder()).asIntBuffer();
		buffer.put(pixels);
		buffer.flip();
		
		return buffer;
	}
	
	@Override
	public void dispose() {
		
		glDeleteTextures(this.id);
	}

	@Override
	public void bind() {
		
		glBindTexture(GL_TEXTURE_2D, this.id);
	}

	/**
	 * This method will unbind ANY currently bound texture.
	 * @since 04.08.2018/0.1.0
	 */
	@Override
	public void unbind() {
		
		glBindTexture(GL_TEXTURE_2D, 0);
	}
	
	/**
	 * Generates a mipmap for this texture.
	 * @since 04.08.2018/0.1.0
	 */
	public void generateMipmap() {
		
		glGenerateTextureMipmap(this.id);
	}
	
	/**
	 * Sets the wrap mode for S (U or X).
	 * @param wrapS wrap mode
	 * @since 04.08.2018/0.1.0
	 */
	public void setWrapS(int wrapS) {
		
		glTextureParameteri(this.id, GL_TEXTURE_WRAP_S, wrapS);
	}
	
	/**
	 * Sets the wrap mode for T (V or Y).
	 * @param wrapT wrap mode
	 * @since 04.08.2018/0.1.0
	 */
	public void setWrapT(int wrapT) {
		
		glTextureParameteri(this.id, GL_TEXTURE_WRAP_T, wrapT);
	}
	
	/**
	 * Sets the border color.
	 * @param red red component (min = {@code 0.0F}, max = {@code 1.0F})
	 * @param green green component (min = {@code 0.0F}, max = {@code 1.0F})
	 * @param blue blue component (min = {@code 0.0F}, max = {@code 1.0F})
	 * @param alpha alpha component (min = {@code 0.0F}, max = {@code 1.0F})
	 * @since 04.08.2018/0.1.0
	 */
	public void setBorderColor(float red, float green, float blue, float alpha) {
		
		glTextureParameterfv(this.id, GL_TEXTURE_BORDER_COLOR, new float[] {red, green, blue, alpha});
	}
	
	/**
	 * Sets the border color.
	 * @param color the color
	 * @since 04.08.2018/0.1.0
	 */
	public void setBorderColor(Color4f color) {
		
		glTextureParameterfv(this.id, GL_TEXTURE_BORDER_COLOR, new float[] {color.x, color.y, color.z, color.w});
	}
	
	/**
	 * Sets the LOD Bias of this texture.
	 * @param lodBias the LOD bias
	 * @since 04.08.2018/0.1.0
	 */
	public void setLODBias(float lodBias) {
		
		glTextureParameterf(this.id, GL_TEXTURE_LOD_BIAS, lodBias);
	}
	
	/**
	 * Sets the min filter.
	 * @param minFilter the min filter
	 * @since 04.08.2018/0.1.0
	 */
	public void setMinFilter(int minFilter) {
		
		glTextureParameteri(this.id, GL_TEXTURE_MIN_FILTER, minFilter);
	}
	
	/**
	 * Sets the mag filter.
	 * @param magFilter the mag filter
	 * @since 04.08.2018/0.1.0
	 */
	public void setMagFilter(int magFilter) {
		
		glTextureParameteri(this.id, GL_TEXTURE_MAG_FILTER, magFilter);
	}
	
	/**
	 * @return the wrap mode for S (U or X)
	 * @since 04.08.2018/0.1.0
	 */
	public int getWrapS() {
		
		return glGetTextureParameteri(this.id, GL_TEXTURE_WRAP_S);
	}
	
	/**
	 * @return the wrap mode for T (V or Y)
	 * @since 04.08.2018/0.1.0
	 */
	public int getWrapT() {
		
		return glGetTextureParameteri(this.id, GL_TEXTURE_WRAP_T);
	}
	
	/**
	 * @return the min filter
	 * @since 04.08.2018/0.1.0
	 */
	public int getMinFilter() {
		
		return glGetTextureParameteri(this.id, GL_TEXTURE_MIN_FILTER);
	}
	
	/**
	 * @return the mag filter
	 * @since 04.08.2018/0.1.0
	 */
	public int getMagFilter() {
		
		return glGetTextureParameteri(this.id, GL_TEXTURE_MAG_FILTER);
	}
	
	/**
	 * @return the LOD bias
	 * @since 04.08.2018/0.1.0
	 */
	public float getLODBias() {
		
		return glGetTextureParameterf(this.id, GL_TEXTURE_LOD_BIAS);
	}
	
	/**
	 * @param level the LOD (base = {@code 0})
	 * @return the data of the image as signed integers and RGBA color model
	 * @since 04.08.2018/0.1.0
	 */
	public int[] getData(int level) {
		
		int[] pixels = new int[this.width * this.height];
		glGetTextureImage(this.id, level, GL_RGBA, GL_UNSIGNED_BYTE, pixels);
		return pixels;
	}
	
	/**
	 * @return the border color
	 * @since 04.08.2018/0.1.0
	 */
	public Color4f getBorderColor() {
		
		float[] color = new float[4];
		glGetTextureParameterfv(this.id, GL_TEXTURE_BORDER_COLOR, color);
		return new Color4f(color[0], color[1], color[2], color[3]);
	}
	
	/**
	 * @return the width of the image
	 * @since 04.08.2018/0.1.0
	 */
	public int getWidth() {
		
		return this.width;
	}
	
	/**
	 * @return the height of the image
	 * @since 04.08.2018/0.1.0
	 */
	public int getHeight() {
		
		return this.height;
	}
	
	/**
	 * Creates a {@linkplain TextureData} object based on this texture.
	 * @return the created object
	 * @since 20.08.2018/0.2.0
	 */
	public TextureData createTextureData() {
		
		TextureData data = new TextureData();
		data.setSize(this.width, this.height);
		data.setPixels(this.getData(0));
		
		return data;
	}
}
