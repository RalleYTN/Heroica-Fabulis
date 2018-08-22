package de.ralleytn.games.heroicafabulis.engine.io.textures;

import java.io.IOException;
import java.io.OutputStream;

import de.ralleytn.games.heroicafabulis.engine.io.Writer;
import static de.ralleytn.games.heroicafabulis.engine.util.BinaryUtil.*;

/**
 * Writer for XIMG image files.
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 22.08.2018/0.2.0
 * @since 19.08.2018/0.2.0
 */
public class XImgTextureWriter extends Writer<TextureData> {
	
	@Override
	public void write(OutputStream outputStream, TextureData data) throws IOException {
		
		try(OutputStream imageStream = outputStream) {
			
			int width = data.getWidth();
			int height = data.getHeight();
			boolean hasAlpha = data.hasAlpha();
			boolean grayscale = data.isGrayscale();
			int[] pixels = data.getPixels();
			int index = 0;
			int oldPixel = pixels[0];
			int repeat = 1;
			
			imageStream.write(XImgFormat.SIGNATURE.getBytes());
			imageStream.write(setBit(setBit(0b00000000, 1, grayscale), 0, hasAlpha));
			imageStream.write(getByte(width, 1));
			imageStream.write(getByte(width, 0));
			imageStream.write(getByte(height, 1));
			imageStream.write(getByte(height, 0));
			
			if(!hasAlpha && !grayscale) {
				
				do {
					
					int pixel = pixels[index++];
					
					if(oldPixel != pixel || repeat == 255) {

						imageStream.write(repeat);
						imageStream.write((oldPixel >> 16) & 0xFF);
						imageStream.write((oldPixel >> 8) & 0xFF);
						imageStream.write(oldPixel & 0xFF);
						repeat = 0;
					}
					
					oldPixel = pixel;
					++repeat;
					
				} while(index < pixels.length);
				
			} else if(hasAlpha && !grayscale) {
				
				do {
					
					int pixel = pixels[index++];
					
					if(oldPixel != pixel || repeat == 255) {

						imageStream.write(repeat);
						imageStream.write((oldPixel >> 24) & 0xFF);
						imageStream.write((oldPixel >> 16) & 0xFF);
						imageStream.write((oldPixel >> 8) & 0xFF);
						imageStream.write(oldPixel & 0xFF);
						repeat = 0;
					}
					
					oldPixel = pixel;
					++repeat;
					
				} while(index < pixels.length);
				
			} else if(!hasAlpha && grayscale) {
				
				do {
					
					int pixel = pixels[index++];
					
					if(oldPixel != pixel || repeat == 255) {

						imageStream.write(repeat);
						imageStream.write(oldPixel & 0xFF);
						repeat = 0;
					}
					
					oldPixel = pixel;
					++repeat;
					
				} while(index < pixels.length);
				
			} else if(hasAlpha && grayscale) {
				
				do {
					
					int pixel = pixels[index++];
					
					if(oldPixel != pixel || repeat == 255) {

						imageStream.write(repeat);
						imageStream.write((oldPixel >> 24) & 0xFF);
						imageStream.write(oldPixel & 0xFF);
						repeat = 0;
					}
					
					oldPixel = pixel;
					++repeat;
					
				} while(index < pixels.length);
			}
		}
	}
}
