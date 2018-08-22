package de.ralleytn.games.heroicafabulis.engine.io.textures;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import de.ralleytn.games.heroicafabulis.engine.io.Reader;

/**
 * Reader for XIMG image files.
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 22.08.2018/0.2.0
 * @since 19.08.2018/0.2.0
 */
public class XImgTextureReader extends Reader<TextureData> {
	
	@Override
	public TextureData read(InputStream inputStream) throws IOException {
		
		try(InputStream imageStream = inputStream;
			ByteArrayOutputStream buffer = new ByteArrayOutputStream()) {
			
			int readByteCount = 0;
			int offset = 0;
			byte[] bytes = new byte[4096];
			
			while((readByteCount = imageStream.read(bytes)) != -1) {
				
				buffer.write(bytes, 0, readByteCount);
			}
			
			bytes = buffer.toByteArray();
			String signature = new String(new byte[] {bytes[offset++], bytes[offset++], bytes[offset++], bytes[offset++]});
			
			if(signature.equals(XImgFormat.SIGNATURE)) {
				
				int flags = bytes[offset++] & 0xFF;
				int width = ((bytes[offset++] & 0xFF) << 8) | (bytes[offset++] & 0xFF);
				int height = ((bytes[offset++] & 0xFF) << 8) | (bytes[offset++] & 0xFF);
				int[] pixels = new int[width * height];
				boolean hasAlpha = (flags & 1) == 1;
				boolean grayscale = ((flags >> 1) & 1) == 1;
				int index = 0;
				
				if(!hasAlpha && !grayscale) {
					
					while(offset < bytes.length) {
						
						int repetitions = bytes[offset++] & 0xFF;
						byte blue = bytes[offset++];
						byte green = bytes[offset++];
						byte red = bytes[offset++];
						
						for(int repetition = 0; repetition < repetitions; repetition++) {
							
							pixels[index++] = ((255 & 0xFF) << 24) | ((blue & 0xFF) << 16) | ((green & 0xFF) << 8) | (red & 0xFF);
						}
					}
					
				} else if(hasAlpha && !grayscale) {
					
					while(offset < bytes.length) {
						
						int repetitions = bytes[offset++] & 0xFF;
						byte alpha = bytes[offset++];
						byte blue = bytes[offset++];
						byte green = bytes[offset++];
						byte red = bytes[offset++];
						
						for(int repetition = 0; repetition < repetitions; repetition++) {
							
							pixels[index++] = ((alpha & 0xFF) << 24) | ((blue & 0xFF) << 16) | ((green & 0xFF) << 8) | (red & 0xFF);
						}
					}
					
				} else if(!hasAlpha && grayscale) {
					
					while(offset < bytes.length) {
						
						int repetitions = bytes[offset++] & 0xFF;
						byte color = bytes[offset++];
						
						for(int repetition = 0; repetition < repetitions; repetition++) {
							
							pixels[index++] = ((255 & 0xFF) << 24) | ((color & 0xFF) << 16) | ((color & 0xFF) << 8) | (color & 0xFF);
						}
					}
					
				} else if(hasAlpha && grayscale) {
					
					while(offset < bytes.length) {
						
						int repetitions = bytes[offset++] & 0xFF;
						byte alpha = bytes[offset++];
						byte color = bytes[offset++];
						
						for(int repetition = 0; repetition < repetitions; repetition++) {
							
							pixels[index++] = ((alpha & 0xFF) << 24) | ((color & 0xFF) << 16) | ((color & 0xFF) << 8) | (color & 0xFF);
						}
					}
				}
				
				TextureData data = new TextureData();
				data.setSize(width, height);
				data.setPixels(pixels);
				
				return data;
				
			} else {
				
				throw new IOException("Not a XIMG file!");
			}
		}
	}
}
