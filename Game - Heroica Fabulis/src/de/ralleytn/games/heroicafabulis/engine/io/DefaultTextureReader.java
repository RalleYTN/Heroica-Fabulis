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
 * @version 14.08.2018/0.1.0
 * @since 04.08.2018/0.1.0
 */
public class DefaultTextureReader extends TextureReader {

	@Override
	public Texture read(InputStream inputStream) throws IOException {
		
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
		
		return this.createTexture(this.createBuffer(image.getRGB(0, 0, width, height, null, 0, width)), width, height);
	}
}
