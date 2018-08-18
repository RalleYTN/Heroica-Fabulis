package de.ralleytn.games.heroicafabulis.engine.io.png;

import java.io.IOException;
import java.io.InputStream;

import de.ralleytn.games.heroicafabulis.engine.io.TextureReader;
import de.ralleytn.games.heroicafabulis.engine.rendering.Texture;

import static de.ralleytn.games.heroicafabulis.engine.io.png.PngFormat.*;
import static de.ralleytn.games.heroicafabulis.engine.util.BinaryUtil.*;

/**
 * 
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 18.08.2018/0.2.0
 * @since 18.08.2018/0.2.0
 */
public class PngReader extends TextureReader {
	
	@Override
	public Texture read(InputStream inputStream) throws IOException {
		
		try(InputStream imageStream = inputStream) {
			
			readSignature(imageStream);
			PngChunk chunk = readChunk(imageStream);
			if(chunk.getType() != PngChunk.CHUNK_TYPE_IHDR) {
				
				throw new IOException("Corrupted PNG file!");
			}
			
			chunk = readChunk(imageStream);
			
			while(chunk.getType() != PngChunk.CHUNK_TYPE_IEND) {
				
				boolean ancillary = getBit(chunk.getType(), 27);
				boolean priv = getBit(chunk.getType(), 19); // private
				boolean reservedSet = getBit(chunk.getType(), 11);
				
				if(!reservedSet && !ancillary && !priv) {
					
					
				}
				
				chunk = readChunk(imageStream);
			}
			
			return null;
		}
	}
}
