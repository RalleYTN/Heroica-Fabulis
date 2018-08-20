package de.ralleytn.games.heroicafabulis.engine.io.textures.png;

import java.io.IOException;
import java.io.InputStream;

import static de.ralleytn.games.heroicafabulis.engine.util.IOUtil.*;

/**
 * 
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 19.08.2018/0.2.0
 * @since 18.08.2018/0.2.0
 */
public final class PngFormat {

	/**
	 * 
	 * @since 18.08.2018/0.2.0
	 */
	public static final String SIGNATURE = "\211PNG\r\n\32\n";
	
    /**
     * @since 18.08.2018/0.2.0
     */
	private PngFormat() {}
	
	/**
	 * 
	 * @param imageStream
	 * @throws IOException
	 * @since 18.08.2018/0.2.0
	 */
	public static final void readSignature(InputStream imageStream) throws IOException {

		for(int index = 0; index < SIGNATURE.length(); index++) {
			
			if(imageStream.read() != SIGNATURE.charAt(index)) {
				
				throw new IOException("Not a PNG file!");
			}
		}
	}
	
	/**
	 * 
	 * @param imageStream
	 * @return
	 * @throws IOException
	 * @since 18.08.2018/0.2.0
	 */
	public static final PngChunk readChunk(InputStream imageStream) throws IOException {
		
		long length = readSignedInt(imageStream, true);
		long type = readSignedInt(imageStream, true);
		byte[] data = new byte[0];
		
		if(length >= 0) {
			
			data = new byte[(int)length];
			imageStream.read(data);
		}
		
		int crc = readSignedInt(imageStream, true);
		
		if(type == PngChunk.TYPE_IHDR) {
			
			return new IHDR(length, data, crc);
			
		} else if(type == PngChunk.TYPE_IDAT) {
			
			return new IDAT(length, data, crc);
			
		} else if(type == PngChunk.TYPE_PLTE) {
			
			return new PLTE(length, data, crc);
			
		} else if(type == PngChunk.TYPE_IEND) {
			
			return new IEND(crc);
		}
		
		return new PngChunk(length, (int)type, data, crc);
	}
}
