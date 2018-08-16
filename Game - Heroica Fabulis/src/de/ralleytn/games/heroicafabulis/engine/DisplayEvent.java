package de.ralleytn.games.heroicafabulis.engine;

/**
 * 
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 16.08.2018/0.1.0
 * @since 16.08.2018/0.1.0
 */
public class DisplayEvent extends Event {

	private int newX;
	private int newY;
	private int newWidth;
	private int newHeight;
	
	/**
	 * 
	 * @param source
	 * @param newX
	 * @param newY
	 * @param newWidth
	 * @param newHeight
	 * @since 16.08.2018/0.1.0
	 */
	public DisplayEvent(Object source, int newX, int newY, int newWidth, int newHeight) {
		
		super(source);
		
		this.newX = newX;
		this.newY = newY;
		this.newWidth = newWidth;
		this.newHeight = newHeight;
	}
	
	/**
	 * 
	 * @param source
	 * @since 16.08.2018/0.1.0
	 */
	public DisplayEvent(Object source) {
		
		super(source);
	}
	
	/**
	 * 
	 * @return
	 * @since 16.08.2018/0.1.0
	 */
	public int getNewX() {
		
		return this.newX;
	}
	
	/**
	 * 
	 * @return
	 * @since 16.08.2018/0.1.0
	 */
	public int getNewY() {
		
		return this.newY;
	}
	
	/**
	 * 
	 * @return
	 * @since 16.08.2018/0.1.0
	 */
	public int getNewWidth() {
		
		return this.newWidth;
	}
	
	/**
	 * 
	 * @return
	 * @since 16.08.2018/0.1.0
	 */
	public int getNewHeight() {
		
		return this.newHeight;
	}
}
