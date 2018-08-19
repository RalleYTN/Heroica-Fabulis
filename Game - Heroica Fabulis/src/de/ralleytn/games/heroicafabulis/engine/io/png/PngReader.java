package de.ralleytn.games.heroicafabulis.engine.io.png;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.InflaterInputStream;

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
			
			if(ihdr.getInterlaceMethod() == 0) {
				
				chunk = readChunk(imageStream);
				
				try(ByteArrayOutputStream deflated = new ByteArrayOutputStream()) {
					
					while(!(chunk instanceof IEND)) {

						if(chunk instanceof IDAT) {
							
							deflated.write(((IDAT)chunk).getData());
						}
						
						chunk = readChunk(imageStream);
					}
					
					try(InflaterInputStream inflater = new InflaterInputStream(new ByteArrayInputStream(deflated.toByteArray()));
						ByteArrayOutputStream inflated = new ByteArrayOutputStream()) {
							
						IOUtil.write(inflater, inflated);
						inflated.flush();
						byte[] inflatedData = inflated.toByteArray();
						int[] pixels = new int[width * height];
					}
				}
				
				return null;
			
			} else {
				
				throw new IOException("Interlaced PNGs are not supported!");
			}
		}
	}
}
