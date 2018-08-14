package de.ralleytn.games.heroicafabulis.engine.io;

import static org.lwjgl.opengl.GL11.GL_RGBA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_UNPACK_ALIGNMENT;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.glPixelStorei;
import static org.lwjgl.opengl.GL11.glTexImage2D;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;

import de.ralleytn.games.heroicafabulis.engine.rendering.Texture;

/**
 * Abstract {@linkplain Reader} that should be extended by all {@linkplain Reader}s that read {@linkplain Texture}s.
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 14.08.2018/0.1.0
 * @since 14.08.2018/0.1.0
 */
public abstract class TextureReader extends Reader<Texture> {

	/**
	 * Wraps the pixel data of an image in an {@linkplain IntBuffer}.
	 * @param pixels the pixel data
	 * @return the created {@linkplain IntBuffer}
	 * @since 14.08.2018/0.1.0
	 */
	protected IntBuffer createBuffer(int[] pixels) {
		
		IntBuffer buffer = ByteBuffer.allocateDirect(pixels.length << 2).order(ByteOrder.nativeOrder()).asIntBuffer();
		buffer.put(pixels);
		buffer.flip();
		
		return buffer;
	}
	
	/**
	 * Makes a {@linkplain Texture} out of an {@linkplain IntBuffer}.
	 * @param buffer the {@linkplain IntBuffer}
	 * @param width width of image in pixel
	 * @param height height of image in pixel
	 * @return the created {@linkplain Texture}
	 * @since 14.08.2018/0.1.0
	 */
	protected Texture createTexture(IntBuffer buffer, int width, int height) {
		
		Texture texture = new Texture();
		texture.bind();
		texture.setMinFilter(Texture.FILTER_NEAREST);
		texture.setMagFilter(Texture.FILTER_NEAREST);
		texture.setWrapS(Texture.WRAP_REPEAT);
		texture.setWrapT(Texture.WRAP_REPEAT);
		glPixelStorei(GL_UNPACK_ALIGNMENT, 1);
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);
		texture.unbind();
		
		return texture;
	}
}
