package de.ralleytn.games.heroicafabulis.engine.io.png;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.InflaterInputStream;

import javax.vecmath.Color3f;

import de.ralleytn.games.heroicafabulis.engine.io.TextureReader;
import de.ralleytn.games.heroicafabulis.engine.rendering.Texture;
import de.ralleytn.games.heroicafabulis.engine.util.IOUtil;

import static de.ralleytn.games.heroicafabulis.engine.io.png.PngFormat.*;

/**
 * 
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 19.08.2018/0.2.0
 * @since 18.08.2018/0.2.0
 */
public class PngReader extends TextureReader {
	
	@Override
	public Texture read(InputStream inputStream) throws IOException {
		
		try(InputStream imageStream = inputStream) {
			
			readSignature(imageStream);
			PngChunk chunk = readChunk(imageStream);
			if(!(chunk instanceof IHDR)) {
				
				throw new IOException("Corrupted PNG file!");
			}
			
			IHDR ihdr = (IHDR)chunk;
			int width = ihdr.getWidth();
			int height = ihdr.getHeight();
			byte colorType = ihdr.getColorType();
			Color3f[] palette = null;
			
			if(ihdr.getInterlaceMethod() == 0) {
				
				chunk = readChunk(imageStream);
				
				try(ByteArrayOutputStream deflated = new ByteArrayOutputStream()) {
					
					while(!(chunk instanceof IEND)) {

						if(chunk instanceof IDAT) {
							
							deflated.write(((IDAT)chunk).getData());
							
						} else if(chunk instanceof PLTE) {
							
							palette = ((PLTE)chunk).getPalette();
						}
						
						chunk = readChunk(imageStream);
					}
					
					deflated.flush();
					
					try(InflaterInputStream inflater = new InflaterInputStream(new ByteArrayInputStream(deflated.toByteArray()));
						ByteArrayOutputStream inflated = new ByteArrayOutputStream()) {
							
						IOUtil.write(inflater, inflated);
						inflated.flush();
						byte[] imageData = inflated.toByteArray();
						int[] pixels = new int[width * height];
						int pixel = 0;
						
						switch(colorType) {
						
							case IHDR.COLOR_TYPE_TRUECOLOR: // FIXME
								for(int index = 0; index < imageData.length; index += 3) {
									
									int red = imageData[index] & 0xFF;
									int green = imageData[index + 1] & 0xFF;
									int blue = imageData[index + 2] & 0xFF;
									pixels[pixel++] = ((255 & 0xFF) << 24) | ((blue & 0xFF) << 16) | ((green & 0xFF) << 8) | (red & 0xFF);
									
									if(pixel % width == 0) {
										
										index++;
									}
								}
								break;
							case IHDR.COLOR_TYPE_GREYSCALE: // TODO
								break;
							case IHDR.COLOR_TYPE_INDEXED:
								for(int index = 0; index < imageData.length; index++) {
									
									Color3f paletteEntry = palette[imageData[index]];
									int red = (int)(paletteEntry.x);
									int green = (int)(paletteEntry.y);
									int blue = (int)(paletteEntry.z);
									pixels[pixel++] = ((255 & 0xFF) << 24) | ((blue & 0xFF) << 16) | ((green & 0xFF) << 8) | (red & 0xFF);
									
									if(pixel % width == 0) {
										
										index++;
									}
								}
								
								break;
							case IHDR.COLOR_TYPE_TRUEALPHA: // FIXME
								for(int index = 0; index < imageData.length; index += 4) {
									
									int red = imageData[index];
									int green = imageData[index + 1];
									int blue = imageData[index + 2];
									int alpha = imageData[index + 3];
									
									pixels[pixel++] = ((alpha & 0xFF) << 24) | ((blue & 0xFF) << 16) | ((green & 0xFF) << 8) | (red & 0xFF);
									System.out.println(Integer.toHexString(pixels[pixel - 1]));
									
									if(pixel % width == 0) {
										
										index++;
									}
								}
								
								break;
							case IHDR.COLOR_TYPE_GREYALPHA: // TODO
								
								break;
						}
						
						return createTexture(createBuffer(pixels), width, height);
					}
				}
			
			} else {
				
				throw new IOException("Interlaced PNGs are not supported!");
			}
		}
	}
}
