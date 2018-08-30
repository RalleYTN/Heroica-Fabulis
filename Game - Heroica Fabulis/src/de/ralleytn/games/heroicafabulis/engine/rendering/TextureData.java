package de.ralleytn.games.heroicafabulis.engine.rendering;

/**
 * Represents texture data. Temporary container for texture data which is important for loading texture data in another thread.
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 20.08.2018/0.2.0
 * @since 20.08.2018/0.2.0
 */
public class TextureData {

	private int width;
	private int height;
	private int[] pixels;
	private boolean alpha;
	private boolean grayscale;
	private boolean checked;
	
	/**
	 * Sets the image size.
	 * @param width the image width in pixels
	 * @param height the image height in pixels
	 * @since 20.08.2018/0.2.0
	 */
	public void setSize(int width, int height) {
		
		this.width = width;
		this.height = height;
	}
	
	/**
	 * Sets the pixels.
	 * @param pixels the pixels.
	 * @since 20.08.2018/0.2.0
	 */
	public void setPixels(int[] pixels) {
		
		this.pixels = pixels;
	}
	
	/**
	 * @return the image width in pixels
	 * @since 20.08.2018/0.2.0
	 */
	public int getWidth() {
		
		return this.width;
	}
	
	/**
	 * @return the image height in pixels
	 * @since 20.08.2018/0.2.0
	 */
	public int getHeight() {
		
		return this.height;
	}
	
	/**
	 * @return the pixels
	 * @since 20.08.2018/0.2.0
	 */
	public int[] getPixels() {
		
		return this.pixels;
	}
	
	/**
	 * @return {@code true} if the image has alpha, else {@code false}
	 * @since 20.08.2018/0.2.0
	 */
	public boolean hasAlpha() {
		
		if(!this.checked) {
			
			this.checkIfGrayscaleAndHasAlpha();
		}
		
		return this.alpha;
	}
	
	/**
	 * @return {@code true} if the image is grayscale, else {@code false}
	 * @since 20.08.2018/0.2.0
	 */
	public boolean isGrayscale() {
		
		if(!this.checked) {
			
			this.checkIfGrayscaleAndHasAlpha();
		}
		
		return this.grayscale;
	}
	
	/**
	 * Goes through every pixel in the image to find out if this image has alpha and/or if it is a grayscale image.
	 * Normally called only once.
	 * @since 20.08.2018/0.2.0
	 */
	private final void checkIfGrayscaleAndHasAlpha() {
		
		boolean grayscale = true;
		
		for(int index = 0; index < this.pixels.length; index++) {
			
			int pixel = this.pixels[index];
			int alpha = (pixel >> 24) & 0xFF;
			int blue = (pixel >> 16) & 0xFF;
			int green = (pixel >> 8) & 0xFF;
			int red = pixel & 0xFF;
			
			if(alpha < 255) {
				
				this.alpha = true;
			}
			
			if(blue != green || blue != red) {
				
				grayscale = false;
			}
		}
		
		this.grayscale = grayscale;
		this.checked = true;
	}
}
