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
			byte bitDepth = ihdr.getBitDepth();
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
						byte[] prunedData = prune(width, height, bitDepth, inflated.toByteArray());
						int[] pixels = new int[width * height];
						int pixel = 0;
						
						switch(colorType) {
						
							case IHDR.COLOR_TYPE_TRUECOLOR: // FIXME
								for(int index = 0; index < prunedData.length - 1; index += 3) {
									
									int red = prunedData[index] & 0xFF;
									int green = prunedData[index + 1] & 0xFF;
									int blue = prunedData[index + 2] & 0xFF;
									pixels[pixel] = ((255 & 0xFF) << 24) | ((blue & 0xFF) << 16) | ((green & 0xFF) << 8) | (red & 0xFF);
									pixel++;
								}
								break;
							case IHDR.COLOR_TYPE_GREYSCALE:
								break;
							case IHDR.COLOR_TYPE_INDEXED:
								for(pixel = 0; pixel < pixels.length; pixel++) {
									
									Color3f paletteEntry = palette[prunedData[pixel]];
									int red = (int)(paletteEntry.x);
									int green = (int)(paletteEntry.y);
									int blue = (int)(paletteEntry.z);
									pixels[pixel] = ((255 & 0xFF) << 24) | ((blue & 0xFF) << 16) | ((green & 0xFF) << 8) | (red & 0xFF);
								}
								break;
							case IHDR.COLOR_TYPE_TRUEALPHA: // FIXME
								for(int index = 0; index < prunedData.length; index += 4) {
									
									int red = prunedData[index] & 0xFF;
									int green = prunedData[index + 1] & 0xFF;
									int blue = prunedData[index + 2] & 0xFF;
									int alpha = prunedData[index + 3] & 0xFF;
									pixels[pixel] = ((alpha & 0xFF) << 24) | ((blue & 0xFF) << 16) | ((green & 0xFF) << 8) | (red & 0xFF);
									pixel++;
								}
								break;
							case IHDR.COLOR_TYPE_GREYALPHA:
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
	
	private static final byte[] prune(int width, int height, byte bitDepth, byte[] inflatedData) {
		
		byte[] prunedData = new byte[width * height * bitDepth / 8];
		int index = 0;
		
        for(int i = 0; i < prunedData.length; i++) {
        	
        	if((i * 8 / bitDepth) % width == 0) {
        	  
        		index++; // Skip the filter byte.
        	}
          
        	prunedData[i] = inflatedData[index++];
        }
        
        return prunedData;
	}
}
