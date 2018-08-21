package de.ralleytn.games.heroicafabulis.engine.io.textures;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

import static de.ralleytn.games.heroicafabulis.engine.util.BinaryUtil.*;
import static de.ralleytn.games.heroicafabulis.engine.util.IOUtil.*;

/**
 * 
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 21.08.2018/0.2.0
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
	// for width * height
	//	3 byte repeat count
	// 	ABGR data
	
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
		boolean grayscale = data.isGrayscale();
		int[] pixels = data.getPixels();
		
			   if(!hasAlpha && !grayscale) {writePixelsBGR(imageStream, pixels);
		} else if( hasAlpha && !grayscale) {writePixelsABGR(imageStream, pixels);
		} else if(!hasAlpha &&  grayscale) {writePixelsG(imageStream, pixels);
		} else if( hasAlpha &&  grayscale) {writePixelsAG(imageStream, pixels);
		}
	}
	
	/**
	 * 
	 * @param imageStream
	 * @param pixels
	 * @throws IOException
	 * @since 20.08.2018/0.2.0
	 */
	private static final void writePixelsABGR(OutputStream imageStream, int[] pixels) throws IOException {
		
		int index = 1;
		int oldPixel = pixels[0];
		int repeat = 1;
		
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
	}
	
	/**
	 * 
	 * @param imageStream
	 * @param pixels
	 * @throws IOException
	 * @since 20.08.2018/0.2.0
	 */
	private static final void writePixelsBGR(OutputStream imageStream, int[] pixels) throws IOException {
		
		int index = 1;
		int oldPixel = pixels[0];
		int repeat = 1;
		
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
	}
	
	/**
	 * 
	 * @param imageStream
	 * @param pixels
	 * @throws IOException
	 * @since 20.08.2018/0.2.0
	 */
	private static final void writePixelsAG(OutputStream imageStream, int[] pixels) throws IOException {
		
		int index = 1;
		int oldPixel = pixels[0];
		int repeat = 1;
		
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
	
	/**
	 * 
	 * @param imageStream
	 * @param pixels
	 * @throws IOException
	 * @since 20.08.2018/0.2.0
	 */
	private static final void writePixelsG(OutputStream imageStream, int[] pixels) throws IOException {
		
		int index = 1;
		int oldPixel = pixels[0];
		int repeat = 1;
		
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

		try(ByteArrayOutputStream buffer = new ByteArrayOutputStream()) {
			
			write(imageStream, buffer);
			byte[] data = buffer.toByteArray();
			
				   if(!hasAlpha && !grayscale) {readPixelsBGR(data, pixels);
			} else if( hasAlpha && !grayscale) {readPixelsABGR(data, pixels);
			} else if(!hasAlpha &&  grayscale) {readPixelsG(data, pixels);
			} else if( hasAlpha &&  grayscale) {readPixelsAG(data, pixels);
			}
		}
		
		return pixels;
	}
	
	/**
	 * 
	 * @param data
	 * @param pixels
	 * @since 20.08.2018/0.2.0
	 */
	private static final void readPixelsABGR(byte[] data, int[] pixels) {
		
		int index = 0;
		
		for(int offset = 0; offset < data.length; offset += 5) {
			
			int repetitions = data[offset] & 0xFF;
			byte alpha = data[offset + 1];
			byte blue = data[offset + 2];
			byte green = data[offset + 3];
			byte red = data[offset + 4];
			
			for(int repetition = 0; repetition < repetitions; repetition++) {
				
				pixels[index++] = ((alpha & 0xFF) << 24) | ((blue & 0xFF) << 16) | ((green & 0xFF) << 8) | (red & 0xFF);
			}
		}
	}
	
	/**
	 * 
	 * @param data
	 * @param pixels
	 * @since 20.08.2018/0.2.0
	 */
	private static final void readPixelsBGR(byte[] data, int[] pixels) {
		
		int index = 0;
		
		for(int offset = 0; offset < data.length; offset += 4) {
			
			int repetitions = data[offset] & 0xFF;
			byte blue = data[offset + 1];
			byte green = data[offset + 2];
			byte red = data[offset + 3];
			
			for(int repetition = 0; repetition < repetitions; repetition++) {
				
				pixels[index++] = ((255 & 0xFF) << 24) | ((blue & 0xFF) << 16) | ((green & 0xFF) << 8) | (red & 0xFF);
			}
		}
	}
	
	/**
	 * 
	 * @param data
	 * @param pixels
	 * @since 20.08.2018/0.2.0
	 */
	private static final void readPixelsG(byte[] data, int[] pixels) {
		
		int index = 0;
		
		for(int offset = 0; offset < data.length; offset += 2) {
			
			int repetitions = data[offset] & 0xFF;
			byte color = data[offset + 1];
			
			for(int repetition = 0; repetition < repetitions; repetition++) {
				
				pixels[index++] = ((255 & 0xFF) << 24) | ((color & 0xFF) << 16) | ((color & 0xFF) << 8) | (color & 0xFF);
			}
		}
	}
	
	/**
	 * 
	 * @param data
	 * @param pixels
	 * @since 20.08.2018/0.2.0
	 */
	private static final void readPixelsAG(byte[] data, int[] pixels) {
		
		int index = 0;
		
		for(int offset = 0; offset < data.length; offset += 3) {
			
			int repetitions = data[offset] & 0xFF;
			byte alpha = data[offset + 1];
			byte color = data[offset + 2];
			
			for(int repetition = 0; repetition < repetitions; repetition++) {
				
				pixels[index++] = ((alpha & 0xFF) << 24) | ((color & 0xFF) << 16) | ((color & 0xFF) << 8) | (color & 0xFF);
			}
		}
	}
}
