package de.ralleytn.games.heroicafabulis.engine.io;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import de.ralleytn.games.heroicafabulis.engine.rendering.Texture;

/**
 * Reads a texture under the assumption that the given {@linkplain InputStream} contains either a PNG, JPEG, GIF or BMP.
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 18.08.2018/0.2.0
 * @since 04.08.2018/0.1.0
 */
public class DefaultTextureReader extends TextureReader {

	@Override
	public Texture read(InputStream inputStream) throws IOException {
		
		try(InputStream imageInputStream = inputStream) {
			
			BufferedImage rawImage = ImageIO.read(inputStream);
			int width = rawImage.getWidth();
			int height = rawImage.getHeight();
			BufferedImage image = null;
			
			if(rawImage.getType() == BufferedImage.TYPE_INT_ARGB) {
				
				image = rawImage;
				
			} else {
				
				image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
				Graphics graphics = image.createGraphics();
				graphics.drawImage(rawImage, 0, 0, null);
				graphics.dispose();
			}
			
			// It is necessary to convert from the ARGB color model to RGBA because it is required by OpenGL.
			// See BUG0002.
			int[] rawPixels = image.getRGB(0, 0, width, height, null, 0, width);
			
			for(int index = 0; index < rawPixels.length; index++) {
				
				rawPixels[index] = convertARGBtoRGBA(rawPixels[index]);
			}
			
			return this.createTexture(this.createBuffer(rawPixels), width, height);
		}
	}
	
	/**
	 * 
	 * @param pixel
	 * @return
	 * @since 18.08.2018/0.2.0
	 */
	private static final int convertARGBtoRGBA(int pixel) {
		
		int alpha = (pixel >> 24) & 0xFF;
		int red = (pixel >> 16) & 0xFF;
		int green = (pixel >> 8) & 0xFF;
		int blue = pixel & 0xFF;
		return ((alpha & 0xFF) << 24) | ((blue & 0xFF) << 16) | ((green & 0xFF) << 8) | (red & 0xFF);
	}
}
