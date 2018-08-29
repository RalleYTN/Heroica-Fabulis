package de.ralleytn.games.heroicafabulis.engine.io.textures;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import de.ralleytn.games.heroicafabulis.engine.io.Reader;
import de.ralleytn.games.heroicafabulis.engine.rendering.TextureData;

/**
 * Reads a texture under the assumption that the given {@linkplain InputStream} contains either a PNG, JPEG, GIF or BMP.
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 20.08.2018/0.2.0
 * @since 04.08.2018/0.1.0
 */
public class DefaultTextureReader extends Reader<TextureData> {

	@Override
	public TextureData read(InputStream inputStream) throws IOException {
		
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
			
			// It is necessary to convert from the ARGB color model to ABGR because it is required by OpenGL.
			// See BUG0002.
			int[] pixels = image.getRGB(0, 0, width, height, null, 0, width);
			
			for(int index = 0; index < pixels.length; index++) {
				
				pixels[index] = convertARGBtoABGR(pixels[index]);
			}
			
			TextureData data = new TextureData();
			data.setPixels(pixels);
			data.setSize(width, height);
			
			return data;
		}
	}
	
	/**
	 * Converts a pixel from ARGB to ABGR.
	 * @param pixel the pixel that should be converted
	 * @return the converted pixel
	 * @since 18.08.2018/0.2.0
	 */
	private static final int convertARGBtoABGR(int pixel) {
		
		int alpha = (pixel >> 24) & 0xFF;
		int red = (pixel >> 16) & 0xFF;
		int green = (pixel >> 8) & 0xFF;
		int blue = pixel & 0xFF;
		return ((alpha & 0xFF) << 24) | ((blue & 0xFF) << 16) | ((green & 0xFF) << 8) | (red & 0xFF);
	}
}
