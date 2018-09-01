package de.ralleytn.engine.caveman.io.textures;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.plugins.jpeg.JPEGImageWriteParam;
import javax.imageio.stream.ImageOutputStream;

import de.ralleytn.engine.caveman.io.Writer;
import de.ralleytn.engine.caveman.rendering.TextureData;

/**
 * Can write images in PNG, JPG, BMP and GIF format.
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 21.08.2018/0.2.0
 * @since 21.08.2018/0.2.0
 */
public class DefaultTextureWriter extends Writer<TextureData> {

	/** @since 21.08.2018/0.2.0 */ public static final String FORMAT_PNG = "PNG";
	/** @since 21.08.2018/0.2.0 */ public static final String FORMAT_JPG = "JPG";
	/** @since 21.08.2018/0.2.0 */ public static final String FORMAT_BMP = "BMP";
	/** @since 21.08.2018/0.2.0 */ public static final String FORMAT_GIF = "GIF";
	
	private String outputFormat;
	
	/**
	 * @since 21.08.2018/0.2.0
	 */
	public DefaultTextureWriter() {
		
		this.outputFormat = FORMAT_PNG;
	}
	
	@Override
	public void write(OutputStream outputStream, TextureData data) throws IOException {
		
		try(OutputStream imageStream = outputStream) {
			
			BufferedImage image = convertImage(toBufferedImage(data), data.hasAlpha());
			
			if(this.outputFormat.equals(FORMAT_JPG)) {
				
				JPEGImageWriteParam jpegParams = new JPEGImageWriteParam(null);
				jpegParams.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
				jpegParams.setCompressionQuality(1.0F);
				
				try(ImageOutputStream out = ImageIO.createImageOutputStream(outputStream)) {
					
					ImageWriter writer = ImageIO.getImageWritersByFormatName(FORMAT_JPG).next();
					writer.setOutput(imageStream);
					writer.write(null, new IIOImage(image, null, null), jpegParams);
				}
				
			} else {
				
				ImageIO.write(image, this.outputFormat, imageStream);
			}
		}
	}
	
	/**
	 * Creates a {@linkplain BufferedImage} based on an instance of {@linkplain TextureData}.
	 * @param data the texture data
	 * @return the created {@linkplain BufferedImage}
	 * @since 21.08.2018/0.2.0
	 */
	private static final BufferedImage toBufferedImage(TextureData data) {
		
		BufferedImage image = new BufferedImage(data.getWidth(), data.getHeight(), BufferedImage.TYPE_INT_ARGB);
		int index = 0;
		
		for(int y = 0; y < data.getHeight(); y++) {
			
			for(int x = 0; x < data.getWidth(); x++) {
				
				int pixel = data.getPixels()[index++];
				int alpha = (pixel >> 24) & 0xFF;
				int blue = (pixel >> 16) & 0xFF;
				int green = (pixel >> 8) & 0xFF;
				int red = pixel & 0xFF;
				
				image.setRGB(x, y, ((alpha & 0xFF) << 24) | ((red & 0xFF) << 16) | ((green & 0xFF) << 8) | (blue & 0xFF));
			}
		}
		
		return image;
	}
	
	/**
	 * Makes sure that if an image has no alpha that the color model will be RGB instead of ARGB.
	 * @param image the image
	 * @param hasAlpha {@code true} if the image has alpha, else {@code false}
	 * @return the converted image.
	 * @since 21.08.2018/0.2.0
	 */
	private final BufferedImage convertImage(BufferedImage image, boolean hasAlpha) {
		
		int type = hasAlpha ? BufferedImage.TYPE_INT_ARGB : BufferedImage.TYPE_INT_RGB;
		
		if(image.getType() == type) {
			
			return image;
		}
		
		BufferedImage newImage = new BufferedImage(image.getWidth(), image.getHeight(), type);
		Graphics graphics = newImage.createGraphics();
		graphics.drawImage(image, 0, 0, null);
		graphics.dispose();
		
		return newImage;
	}
	
	/**
	 * Sets the output format.
	 * @param format the output format
	 * @since 21.08.2018/0.2.0
	 */
	public void setOutputFormat(String format) {
		
		this.outputFormat = format;
	}
	
	/**
	 * @return the output format
	 * @since 21.08.2018/0.2.0
	 */
	public String getOutputFormat() {
		
		return this.outputFormat;
	}
}
