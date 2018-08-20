package de.ralleytn.games.heroicafabulis.engine.io.textures;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
	//	- 1 = has alpha
	//	- 2 = grayscale
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
	 * @since 20.08.2018/0.2.0
	 */
	public static final void writeSignature(OutputStream imageStream) throws IOException {
		
		for(int index = 0; index < SIGNATURE.length(); index++) {
			
			imageStream.write(SIGNATURE.charAt(index));
		}
	}
	
	/**
	 * 
	 * @param imageStream
	 * @param data
	 * @throws IOException
	 * @since 20.08.2018/0.2.0
	 */
	public static final void writeFlags(OutputStream imageStream, TextureData data) throws IOException {
		
		int flags = 0b00000000;
		flags = setBit(flags, 0, data.hasAlpha());
		flags = setBit(flags, 1, data.isGrayscale());
		imageStream.write(flags);
	}
	
	/**
	 * 
	 * @param imageStream
	 * @param data
	 * @throws IOException
	 * @since 20.08.2018/0.2.0
	 */
	public static final void writeSize(OutputStream imageStream, TextureData data) throws IOException {
		
		writeShort(imageStream, (short)data.getWidth(), true);
		writeShort(imageStream, (short)data.getHeight(), true);
	}
	
	/**
	 * 
	 * @param imageStream
	 * @param data
	 * @throws IOException
	 * @since 20.08.2018/0.2.0
	 */
	public static final void writePixels(OutputStream imageStream, TextureData data) throws IOException {
		
		boolean hasAlpha = data.hasAlpha();
		boolean isGrayscale = data.isGrayscale();
		int[] pixels = data.getPixels();

		for(int index = 0; index < pixels.length; index++) {
			
			int pixel = pixels[index];
			
			int alpha = (pixel >> 24) & 0xFF;
			int blue = (pixel >> 16) & 0xFF;
			int green = (pixel >> 8) & 0xFF;
			int red = pixel & 0xFF;
			
			if(hasAlpha && !isGrayscale) {
				
				writeInt(imageStream, pixel, true);
				
			} else if(!hasAlpha && !isGrayscale) {
				
				imageStream.write(blue);
				imageStream.write(green);
				imageStream.write(red);
				
			} else if(hasAlpha && isGrayscale) {
				
				imageStream.write(alpha);
				imageStream.write(blue);
				
			} else if(!hasAlpha && isGrayscale) {
				
				imageStream.write(blue);
			}
		}
	}
	
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
		
		boolean hasAlpha = getBit(flags, 0);
		boolean grayscale = getBit(flags, 1);
		int[] pixels = new int[width * height];

		for(int index = 0; index < pixels.length;) {
			
			int pixel = 0;

			if(hasAlpha && !grayscale) {
				
				pixel = readSignedInt(imageStream, true);
				
			} else if(hasAlpha && grayscale) {
				
				int alpha = imageStream.read();
				int color = imageStream.read();
				
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

			pixels[index++] = pixel;
		}
		
		return pixels;
	}
}
