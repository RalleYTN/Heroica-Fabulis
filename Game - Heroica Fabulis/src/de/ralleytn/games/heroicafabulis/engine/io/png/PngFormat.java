package de.ralleytn.games.heroicafabulis.engine.io.png;

import java.io.IOException;
import java.io.InputStream;

import static de.ralleytn.games.heroicafabulis.engine.util.IOUtil.*;

/**
 * 
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 18.08.2018/0.2.0
 * @since 18.08.2018/0.2.0
 */
public final class PngFormat {

	/**
	 * 
	 * @since 18.08.2018/0.2.0
	 */
	public static final String SIGNATURE = "\211PNG\r\n\32\n";
    
    /** @since 18.08.2018/0.2.0 */ public static final byte COLOR_GREYSCALE = 0;
    /** @since 18.08.2018/0.2.0 */ public static final byte COLOR_TRUECOLOR = 2;
    /** @since 18.08.2018/0.2.0 */ public static final byte COLOR_INDEXED = 3;
    /** @since 18.08.2018/0.2.0 */ public static final byte COLOR_GREYALPHA = 4;
    /** @since 18.08.2018/0.2.0 */ public static final byte COLOR_TRUEALPHA = 6;
	
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
		
		long length = readSignedInt(imageStream, false);
		int type = readSignedInt(imageStream, false);
		byte[] data = new byte[(int)length];
		imageStream.read(data);
		imageStream.skip(4); // SKIP CRC
		
		if(type == PngChunk.CHUNK_TYPE_IHDR) {
			
			return new IHDR(length, data);
		}
		
		return new PngChunk(length, type, data);
	}
}
