package de.ralleytn.games.heroicafabulis.engine.rendering;

import javax.vecmath.Color4f;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GL45;

import de.ralleytn.games.heroicafabulis.engine.Bindable;
import de.ralleytn.games.heroicafabulis.engine.LWJGLObject;
import de.ralleytn.games.heroicafabulis.engine.io.TextureReader;

/**
 * Represents an OpenGL texture.
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 15.08.2018/0.1.0
 * @since 04.08.2018/0.1.0
 */
public class Texture extends LWJGLObject implements Bindable {

	// I collect the enums here because they are scattered among different classes which makes it really annoying to find them
	
	/** @since 04.08.2018/0.1.0 */ public static final int FILTER_NEAREST = GL11.GL_NEAREST;
	/** @since 04.08.2018/0.1.0 */ public static final int FILTER_LINEAR = GL11.GL_LINEAR;
	/** @since 04.08.2018/0.1.0 */ public static final int FILTER_NEAREST_MIPMAP_NEAREST = GL11.GL_NEAREST_MIPMAP_NEAREST;
	/** @since 04.08.2018/0.1.0 */ public static final int FILTER_NEAREST_MIPMAP_LINEAR = GL11.GL_NEAREST_MIPMAP_LINEAR;
	/** @since 04.08.2018/0.1.0 */ public static final int FILTER_LINEAR_MIPMAP_NEAREST = GL11.GL_LINEAR_MIPMAP_NEAREST;
	/** @since 04.08.2018/0.1.0 */ public static final int FILTER_LINEAR_MIPMAP_LINEAR = GL11.GL_LINEAR_MIPMAP_LINEAR;
	
	/** @since 04.08.2018/0.1.0 */ public static final int WRAP_REPEAT = GL11.GL_REPEAT;
	/** @since 04.08.2018/0.1.0 */ public static final int WRAP_MIRRORED_REPEAT = GL14.GL_MIRRORED_REPEAT;
	/** @since 04.08.2018/0.1.0 */ public static final int WRAP_CLAMP_TO_EDGE = GL12.GL_CLAMP_TO_EDGE;
	/** @since 04.08.2018/0.1.0 */ public static final int WRAP_CLAMP_TO_BORDER = GL13.GL_CLAMP_TO_BORDER;
	
	private int width;
	private int height;
	
	/**
	 * Instead of using this constructor directly, you should use a {@linkplain TextureReader}.
	 * @since 04.08.2018/0.1.0
	 */
	public Texture() {
		
		this.id = GL11.glGenTextures();
	}
	
	@Override
	public void dispose() {
		
		GL11.glDeleteTextures(this.id);
	}

	@Override
	public void bind() {
		
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.id);
	}

	/**
	 * This method will unbind ANY currently bound texture.
	 * @since 04.08.2018/0.1.0
	 */
	@Override
	public void unbind() {
		
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
	}
	
	/**
	 * Generates a mipmap for this texture.
	 * @since 04.08.2018/0.1.0
	 */
	public void generateMipmap() {
		
		GL45.glGenerateTextureMipmap(this.id);
	}
	
	/**
	 * Sets the wrap mode for S (U or X).
	 * @param wrapS wrap mode
	 * @since 04.08.2018/0.1.0
	 */
	public void setWrapS(int wrapS) {
		
		GL45.glTextureParameteri(this.id, GL11.GL_TEXTURE_WRAP_S, wrapS);
	}
	
	/**
	 * Sets the wrap mode for T (V or Y).
	 * @param wrapT wrap mode
	 * @since 04.08.2018/0.1.0
	 */
	public void setWrapT(int wrapT) {
		
		GL45.glTextureParameteri(this.id, GL11.GL_TEXTURE_WRAP_T, wrapT);
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
		
		GL45.glTextureParameterfv(this.id, GL11.GL_TEXTURE_BORDER_COLOR, new float[] {red, green, blue, alpha});
	}
	
	/**
	 * Sets the border color.
	 * @param color the color
	 * @since 04.08.2018/0.1.0
	 */
	public void setBorderColor(Color4f color) {
		
		GL45.glTextureParameterfv(this.id, GL11.GL_TEXTURE_BORDER_COLOR, new float[] {color.x, color.y, color.z, color.w});
	}
	
	/**
	 * Sets the LOD Bias of this texture.
	 * @param lodBias the LOD bias
	 * @since 04.08.2018/0.1.0
	 */
	public void setLODBias(float lodBias) {
		
		GL45.glTextureParameterf(this.id, GL14.GL_TEXTURE_LOD_BIAS, lodBias);
	}
	
	/**
	 * Sets the min filter.
	 * @param minFilter the min filter
	 * @since 04.08.2018/0.1.0
	 */
	public void setMinFilter(int minFilter) {
		
		GL45.glTextureParameteri(this.id, GL11.GL_TEXTURE_MIN_FILTER, minFilter);
	}
	
	/**
	 * Sets the mag filter.
	 * @param magFilter the mag filter
	 * @since 04.08.2018/0.1.0
	 */
	public void setMagFilter(int magFilter) {
		
		GL45.glTextureParameteri(this.id, GL11.GL_TEXTURE_MAG_FILTER, magFilter);
	}
	
	/**
	 * @return the wrap mode for S (U or X)
	 * @since 04.08.2018/0.1.0
	 */
	public int getWrapS() {
		
		return GL45.glGetTextureParameteri(this.id, GL11.GL_TEXTURE_WRAP_S);
	}
	
	/**
	 * @return the wrap mode for T (V or Y)
	 * @since 04.08.2018/0.1.0
	 */
	public int getWrapT() {
		
		return GL45.glGetTextureParameteri(this.id, GL11.GL_TEXTURE_WRAP_T);
	}
	
	/**
	 * @return the min filter
	 * @since 04.08.2018/0.1.0
	 */
	public int getMinFilter() {
		
		return GL45.glGetTextureParameteri(this.id, GL11.GL_TEXTURE_MIN_FILTER);
	}
	
	/**
	 * @return the mag filter
	 * @since 04.08.2018/0.1.0
	 */
	public int getMagFilter() {
		
		return GL45.glGetTextureParameteri(this.id, GL11.GL_TEXTURE_MAG_FILTER);
	}
	
	/**
	 * @return the LOD bias
	 * @since 04.08.2018/0.1.0
	 */
	public float getLODBias() {
		
		return GL45.glGetTextureParameterf(this.id, GL14.GL_TEXTURE_LOD_BIAS);
	}
	
	/**
	 * @param level the LOD (base = {@code 0})
	 * @return the data of the image as signed integers and RGBA color model
	 * @since 04.08.2018/0.1.0
	 */
	public int[] getData(int level) {
		
		int[] pixels = new int[this.width * this.height];
		GL45.glGetTextureImage(this.id, level, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, pixels);
		return pixels;
	}
	
	/**
	 * @return the border color
	 * @since 04.08.2018/0.1.0
	 */
	public Color4f getBorderColor() {
		
		float[] color = new float[4];
		GL45.glGetTextureParameterfv(this.id, GL11.GL_TEXTURE_BORDER_COLOR, color);
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
}
