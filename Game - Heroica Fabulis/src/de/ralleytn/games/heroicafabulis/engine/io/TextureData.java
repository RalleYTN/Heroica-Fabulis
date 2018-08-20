package de.ralleytn.games.heroicafabulis.engine.io;

/**
 * 
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
	private boolean foundOut;
	
	/**
	 * 
	 * @param width
	 * @param height
	 * @since 20.08.2018/0.2.0
	 */
	public void setSize(int width, int height) {
		
		this.width = width;
		this.height = height;
	}
	
	/**
	 * 
	 * @param pixels
	 * @since 20.08.2018/0.2.0
	 */
	public void setPixels(int[] pixels) {
		
		this.pixels = pixels;
	}
	
	/**
	 * 
	 * @return
	 * @since 20.08.2018/0.2.0
	 */
	public int getWidth() {
		
		return this.width;
	}
	
	/**
	 * 
	 * @return
	 * @since 20.08.2018/0.2.0
	 */
	public int getHeight() {
		
		return this.height;
	}
	
	/**
	 * 
	 * @return
	 * @since 20.08.2018/0.2.0
	 */
	public int[] getPixels() {
		
		return this.pixels;
	}
	
	/**
	 * 
	 * @return
	 * @since 20.08.2018/0.2.0
	 */
	public boolean hasAlpha() {
		
		if(!this.foundOut) {
			
			this.findOutIfGrayscaleAndHasAlpha();
		}
		
		return this.alpha;
	}
	
	/**
	 * 
	 * @return
	 * @since 20.08.2018/0.2.0
	 */
	public boolean isGrayscale() {
		
		if(!this.foundOut) {
			
			this.findOutIfGrayscaleAndHasAlpha();
		}
		
		return this.grayscale;
	}
	
	/**
	 * @since 20.08.2018/0.2.0
	 */
	private final void findOutIfGrayscaleAndHasAlpha() {
		
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
		this.foundOut = true;
	}
}
