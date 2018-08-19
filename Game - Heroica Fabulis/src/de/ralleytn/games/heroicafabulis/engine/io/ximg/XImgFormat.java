package de.ralleytn.games.heroicafabulis.engine.io.ximg;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static de.ralleytn.games.heroicafabulis.engine.util.BinaryUtil.*;
import static de.ralleytn.games.heroicafabulis.engine.util.IOUtil.*;

/**
 * 
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 19.08.2018/0.2.0
 * @since 19.08.2018/0.2.0
 */
public final class XImgFormat {

	// BIG ENDIAN
	// XIMG
	// Flags
	//	- 1 = use repeat byte
	//	- 2 = has alpha
	//	- 3 = grayscale
	// short width
	// short height
	// ABGR data
	
	/** @since 19.08.2018/0.2.0 */ public static final String SIGNATURE = "XIMG";
	
	/**
	 * @since 19.08.2018/0.2.0
	 */
	private XImgFormat() {}
	
	/**
	 * 
	 * @param imageStream
	 * @throws IOException
	 * @since 19.08.2018/0.2.0
	 */
	public static final void readSignature(InputStream imageStream) throws IOException {
		
		String header = readString(imageStream, SIGNATURE.length(), StandardCharsets.UTF_8);
		
		if(!header.equals(SIGNATURE)) {
			
			throw new IOException("Not a XIMG file!");
		}
	}
	
	/**
	 * 
	 * @param imageStream
	 * @return
	 * @throws IOException
	 * @since 19.08.2018/0.2.0
	 */
	public static final int readFlags(InputStream imageStream) throws IOException {
		
		return imageStream.read();
	}
	
	/**
	 * 
	 * @param imageStream
	 * @return
	 * @throws IOException
	 * @since 19.08.2018/0.2.0
	 */
	public static final int readWidth(InputStream imageStream) throws IOException {
		
		return readSignedShort(imageStream, true);
	}
	
	/**
	 * 
	 * @param imageStream
	 * @return
	 * @throws IOException
	 * @since 19.08.2018/0.2.0
	 */
	public static final int readHeight(InputStream imageStream) throws IOException {
		
		return readSignedShort(imageStream, true);
	}
	
	/**
	 * 
	 * @param imageStream
	 * @param flags
	 * @param width
	 * @param height
	 * @return
	 * @throws IOException
	 * @since 19.08.2018/0.2.0
	 */
	public static final int[] readPixels(InputStream imageStream, int flags, int width, int height) throws IOException {
		
		boolean useRepeatByte = getBit(flags, 0);
		boolean hasAlpha = getBit(flags, 1);
		boolean grayscale = getBit(flags, 2);
		int[] pixels = new int[width * height];

		for(int index = 0; index < pixels.length;) {
			
			int repeatByte = 1;
			int pixel = 0;
			
			if(useRepeatByte) {
				
				repeatByte = imageStream.read();
			}
			
			if(hasAlpha && !grayscale) {
				
				pixel = readSignedInt(imageStream, true);
				
			} else if(hasAlpha && grayscale) {
				
				int color = imageStream.read();
				int alpha = imageStream.read();
				
				pixel = ((alpha & 0xFF) << 24) | ((color & 0xFF) << 16) | ((color & 0xFF) << 8) | (color & 0xFF);
				
			} else if(!hasAlpha && !grayscale) {
				
				int blue = imageStream.read();
				int green = imageStream.read();
				int red = imageStream.read();
				
				pixel = ((255 & 0xFF) << 24) | ((blue & 0xFF) << 16) | ((green & 0xFF) << 8) | (red & 0xFF);
				
			} else if(!hasAlpha && grayscale) {
				
				int color = imageStream.read();
				pixel = ((255 & 0xFF) << 24) | ((color & 0xFF) << 16) | ((color & 0xFF) << 8) | (color & 0xFF);
			}
				
			for(int i = 0; i < repeatByte; i++) {
					
				pixels[index++] = pixel;
			}
		}
		
		return pixels;
	}
}
