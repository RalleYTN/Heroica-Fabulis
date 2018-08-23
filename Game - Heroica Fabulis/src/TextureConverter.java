import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * Tool for converting between PNG and XIMG images.
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 22.08.2018/1.0.0
 * @since 22.08.2018/1.0.0
 */
public final class TextureConverter {

	private static final String XIMG_SIGNATURE = "XIMG";
	
	/**
	 * @since 22.08.2018/0.2.0
	 */
	private TextureConverter() {}
	
	/**
	 * @param args 0 = source file path, 1 = target file path
	 * @throws IOException if an I/O error occurred
	 * @since 22.08.2018/1.0.0
	 */
	public static void main(String[] args) throws IOException {

		File source = new File(args[0]);
		File target = new File(args[1]);
		
		try(FileOutputStream out = new FileOutputStream(target);
			FileInputStream in = new FileInputStream(source)) {
			
			// XIMG -> PNG
			if(source.getName().toLowerCase().endsWith(".ximg")) {
				
				try(ByteArrayOutputStream buffer = new ByteArrayOutputStream()) {
					
					// Read the entire image into memory
					int readByteCount = 0;
					int offset = 0;
					byte[] data = new byte[4096];
					
					while((readByteCount = in.read(data)) != -1) {
						
						buffer.write(data, 0, readByteCount);
					}
					
					data = buffer.toByteArray();
					
					// Check the signature
					String signature = new String(new byte[] {data[offset++], data[offset++], data[offset++], data[offset++]});
					
					if(signature.equals(XIMG_SIGNATURE)) {
						
						// Read the head data
						int flags = data[offset++] & 0xFF;
						int width = ((data[offset++] & 0xFF) << 8) | (data[offset++] & 0xFF);
						int height = ((data[offset++] & 0xFF) << 8) | (data[offset++] & 0xFF);
						int[] pixels = new int[width * height];
						boolean hasAlpha = (flags & 1) == 1;
						boolean grayscale = ((flags >> 1) & 1) == 1;
						int index = 0;
						
						// Read the pixel data as BGR and convert it to ARGB
						if(!hasAlpha && !grayscale) {
							
							while(offset < data.length) {
								
								int repetitions = data[offset++] & 0xFF;
								byte blue = data[offset++];
								byte green = data[offset++];
								byte red = data[offset++];
								
								for(int repetition = 0; repetition < repetitions; repetition++) {
									
									pixels[index++] = ((255 & 0xFF) << 24) | ((red & 0xFF) << 16) | ((green & 0xFF) << 8) | (blue & 0xFF);
								}
							}
							
						// Read the pixel data as ABGR and convert it to ARGB
						} else if(hasAlpha && !grayscale) {
							
							while(offset < data.length) {
								
								int repetitions = data[offset++] & 0xFF;
								byte alpha = data[offset++];
								byte blue = data[offset++];
								byte green = data[offset++];
								byte red = data[offset++];
								
								for(int repetition = 0; repetition < repetitions; repetition++) {
									
									pixels[index++] = ((alpha & 0xFF) << 24) | ((red & 0xFF) << 16) | ((green & 0xFF) << 8) | (blue & 0xFF);
								}
							}
							
						// Read the pixel data as G and convert it to ARGB
						} else if(!hasAlpha && grayscale) {
							
							while(offset < data.length) {
								
								int repetitions = data[offset++] & 0xFF;
								byte color = data[offset++];
								
								for(int repetition = 0; repetition < repetitions; repetition++) {
									
									pixels[index++] = ((255 & 0xFF) << 24) | ((color & 0xFF) << 16) | ((color & 0xFF) << 8) | (color & 0xFF);
								}
							}
							
						// Read the pixel data as AG and convert it to ARGB
						} else if(hasAlpha && grayscale) {
							
							while(offset < data.length) {
								
								int repetitions = data[offset++] & 0xFF;
								byte alpha = data[offset++];
								byte color = data[offset++];
								
								for(int repetition = 0; repetition < repetitions; repetition++) {
									
									pixels[index++] = ((alpha & 0xFF) << 24) | ((color & 0xFF) << 16) | ((color & 0xFF) << 8) | (color & 0xFF);
								}
							}
						}
						
						// Write the PNG
						BufferedImage image = new BufferedImage(width, height, hasAlpha ? BufferedImage.TYPE_INT_ARGB : BufferedImage.TYPE_INT_RGB);
						image.setRGB(0, 0, width, height, pixels, 0, width);
						ImageIO.write(image, "PNG", out);
						
					} else {
						
						throw new IOException("Not a XIMG file!");
					}
				}
			
			// PNG, GIF, BMP, JPG -> XIMG
			} else {
				
				// Load the raw image
				BufferedImage rawImage = ImageIO.read(in);
				int width = rawImage.getWidth();
				int height = rawImage.getHeight();
				
				// Make sure the color model is ARGB
				BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
				Graphics graphics = image.createGraphics();
				graphics.drawImage(rawImage, 0, 0, null);
				graphics.dispose();

				int[] pixels = image.getRGB(0, 0, width, height, null, 0, width);
				boolean hasAlpha = false;
				boolean grayscale = true;
				int width1 = (width >> 16) & 0xFF;
				int width2 = (width >> 8) & 0xFF;
				int height1 = (height >> 16) & 0xFF;
				int height2 = (height >> 8) & 0xFF;
				
				for(int index = 0; index < pixels.length; index++) {
					
					// Convert the color model from ARGB to ABGR (necessary because BufferedImage doesn't support ABGR)
					int pixel = pixels[index];
					int alpha = (pixel >> 24) & 0xFF;
					int red = (pixel >> 16) & 0xFF;
					int green = (pixel >> 8) & 0xFF;
					int blue = pixel & 0xFF;
					pixels[index] = ((alpha & 0xFF) << 24) | ((blue & 0xFF) << 16) | ((green & 0xFF) << 8) | (red & 0xFF);
					
					// Check if the image truly has alpha
					if(alpha < 255) {
						
						hasAlpha = true;
					}
					
					// Check if the image is basically a grayscale image
					if(red != green || red != blue) {
						
						grayscale = false;
					}
				}
				
				int index = 0;
				int oldPixel = pixels[0];
				int repeat = 1;
				
				// Write head data
				out.write(XIMG_SIGNATURE.getBytes());
				out.write(setBit(setBit(0b00000000, 1, grayscale), 0, hasAlpha));
				out.write(width1);
				out.write(width2);
				out.write(height1);
				out.write(height2);
				
				// Write pixel data in BGR
				if(!hasAlpha && !grayscale) {
					
					do {
						
						int pixel = pixels[index++];
						
						if(oldPixel != pixel || repeat == 255) {

							out.write(repeat);
							out.write((oldPixel >> 16) & 0xFF);
							out.write((oldPixel >> 8) & 0xFF);
							out.write(oldPixel & 0xFF);
							repeat = 0;
						}
						
						oldPixel = pixel;
						++repeat;
						
					} while(index < pixels.length);
					
				// Write pixel data in ABGR
				} else if(hasAlpha && !grayscale) {
					
					do {
						
						int pixel = pixels[index++];
						
						if(oldPixel != pixel || repeat == 255) {

							out.write(repeat);
							out.write((oldPixel >> 24) & 0xFF);
							out.write((oldPixel >> 16) & 0xFF);
							out.write((oldPixel >> 8) & 0xFF);
							out.write(oldPixel & 0xFF);
							repeat = 0;
						}
						
						oldPixel = pixel;
						++repeat;
						
					} while(index < pixels.length);
					
				// Write pixel data in G
				} else if(!hasAlpha && grayscale) {
					
					do {
						
						int pixel = pixels[index++];
						
						if(oldPixel != pixel || repeat == 255) {

							out.write(repeat);
							out.write(oldPixel & 0xFF);
							repeat = 0;
						}
						
						oldPixel = pixel;
						++repeat;
						
					} while(index < pixels.length);
					
				// Write pixel data in AG
				} else if(hasAlpha && grayscale) {
					
					do {
						
						int pixel = pixels[index++];
						
						if(oldPixel != pixel || repeat == 255) {

							out.write(repeat);
							out.write((oldPixel >> 24) & 0xFF);
							out.write(oldPixel & 0xFF);
							repeat = 0;
						}
						
						oldPixel = pixel;
						++repeat;
						
					} while(index < pixels.length);
				}
			}
		}
	}
	
	/**
	 * Sets a single bit in a binary sequence.
	 * @param binary the binary sequence
	 * @param position the position (right to left starting from 0)
	 * @param bit the value of the bit
	 * @return the binary sequence with the set bit
	 * @since 22.08.2018/1.0.0
	 */
	private static final int setBit(int binary, int position, boolean bit) {
		
		return bit ? binary | (1 << position) : binary & ~(1 << position);
	}
}
