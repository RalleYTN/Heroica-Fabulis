package de.ralleytn.games.heroicafabulis.engine.rendering;

import java.io.InputStream;

import javax.vecmath.Color4f;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GL45;

import de.ralleytn.games.heroicafabulis.engine.Bindable;
import de.ralleytn.games.heroicafabulis.engine.LWJGLObject;

/**
 * 
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 11.08.2018/0.1.0
 * @since 04.08.2018/0.1.0
 */
public class Texture extends LWJGLObject implements Bindable {

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
	 * 
	 * @param inputStream
	 * @since 04.08.2018/0.1.0
	 */
	public Texture(InputStream inputStream) {
		
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
	 * 
	 * @since 04.08.2018/0.1.0
	 */
	public void generateMipmap() {
		
		GL45.glGenerateTextureMipmap(this.id);
	}
	
	/**
	 * 
	 * @param wrapS
	 * @since 04.08.2018/0.1.0
	 */
	public void setWrapS(int wrapS) {
		
		GL45.glTextureParameteri(this.id, GL11.GL_TEXTURE_WRAP_S, wrapS);
	}
	
	/**
	 * 
	 * @param wrapT
	 * @since 04.08.2018/0.1.0
	 */
	public void setWrapT(int wrapT) {
		
		GL45.glTextureParameteri(this.id, GL11.GL_TEXTURE_WRAP_T, wrapT);
	}
	
	/**
	 * 
	 * @param red
	 * @param green
	 * @param blue
	 * @param alpha
	 * @since 04.08.2018/0.1.0
	 */
	public void setBorderColor(float red, float green, float blue, float alpha) {
		
		GL45.glTextureParameterfv(this.id, GL11.GL_TEXTURE_BORDER_COLOR, new float[] {red, green, blue, alpha});
	}
	
	/**
	 * 
	 * @param color
	 * @since 04.08.2018/0.1.0
	 */
	public void setBorderColor(Color4f color) {
		
		GL45.glTextureParameterfv(this.id, GL11.GL_TEXTURE_BORDER_COLOR, new float[] {color.x, color.y, color.z, color.w});
	}
	
	/**
	 * 
	 * @param lodBias
	 * @since 04.08.2018/0.1.0
	 */
	public void setLODBias(float lodBias) {
		
		GL45.glTextureParameterf(this.id, GL14.GL_TEXTURE_LOD_BIAS, lodBias);
	}
	
	/**
	 * 
	 * @param minFilter
	 * @since 04.08.2018/0.1.0
	 */
	public void setMinFilter(int minFilter) {
		
		GL45.glTextureParameteri(this.id, GL11.GL_TEXTURE_MIN_FILTER, minFilter);
	}
	
	/**
	 * 
	 * @param magFilter
	 * @since 04.08.2018/0.1.0
	 */
	public void setMagFilter(int magFilter) {
		
		GL45.glTextureParameteri(this.id, GL11.GL_TEXTURE_MAG_FILTER, magFilter);
	}
	
	/**
	 * 
	 * @return
	 * @since 04.08.2018/0.1.0
	 */
	public int getWrapS() {
		
		return GL45.glGetTextureParameteri(this.id, GL11.GL_TEXTURE_WRAP_S);
	}
	
	/**
	 * 
	 * @return
	 * @since 04.08.2018/0.1.0
	 */
	public int getWrapT() {
		
		return GL45.glGetTextureParameteri(this.id, GL11.GL_TEXTURE_WRAP_T);
	}
	
	/**
	 * 
	 * @return
	 * @since 04.08.2018/0.1.0
	 */
	public int getMinFilter() {
		
		return GL45.glGetTextureParameteri(this.id, GL11.GL_TEXTURE_MIN_FILTER);
	}
	
	/**
	 * 
	 * @return
	 * @since 04.08.2018/0.1.0
	 */
	public int getMagFilter() {
		
		return GL45.glGetTextureParameteri(this.id, GL11.GL_TEXTURE_MAG_FILTER);
	}
	
	/**
	 * 
	 * @return
	 * @since 04.08.2018/0.1.0
	 */
	public float getLODBias() {
		
		return GL45.glGetTextureParameterf(this.id, GL14.GL_TEXTURE_LOD_BIAS);
	}
	
	/**
	 * 
	 * @param level
	 * @return
	 * @since 04.08.2018/0.1.0
	 */
	public int[] getData(int level) {
		
		int[] pixels = new int[this.width * this.height];
		GL45.glGetTextureImage(this.id, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, pixels);
		return pixels;
	}
	
	/**
	 * 
	 * @return
	 * @since 04.08.2018/0.1.0
	 */
	public Color4f getBorderColor() {
		
		float[] color = new float[4];
		GL45.glGetTextureParameterfv(this.id, GL11.GL_TEXTURE_BORDER_COLOR, color);
		return new Color4f(color[0], color[1], color[2], color[3]);
	}
	
	/**
	 * 
	 * @return
	 * @since 04.08.2018/0.1.0
	 */
	public int getWidth() {
		
		return this.width;
	}
	
	/**
	 * 
	 * @return
	 * @since 04.08.2018/0.1.0
	 */
	public int getHeight() {
		
		return this.height;
	}
}
