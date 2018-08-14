package de.ralleytn.games.heroicafabulis.engine.io;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;

import javax.imageio.ImageIO;

import static org.lwjgl.opengl.GL11.*;

import de.ralleytn.games.heroicafabulis.engine.rendering.Texture;

/**
 * Reads a texture under the assumption that the given {@linkplain InputStream} contains either a PNG, JPEG, GIF or BMP.
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 14.08.2018/0.1.0
 * @since 04.08.2018/0.1.0
 */
public class DefaultTextureReader extends Reader<Texture> {

	@Override
	public Texture read(InputStream inputStream) throws IOException {
		
		BufferedImage bimg = ImageIO.read(inputStream);
		int width = bimg.getWidth();
		int height = bimg.getHeight();
		BufferedImage argbImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics graphics = argbImage.createGraphics();
		graphics.drawImage(bimg, 0, 0, null);
		graphics.dispose();
		int[] rawPixels = argbImage.getRGB(0, 0, width, height, null, 0, width);
		
		IntBuffer buffer = ByteBuffer.allocateDirect(rawPixels.length << 2).order(ByteOrder.nativeOrder()).asIntBuffer();
		buffer.put(rawPixels);
		buffer.flip();
		
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
