package de.ralleytn.games.heroicafabulis.engine.display;

import de.ralleytn.games.heroicafabulis.engine.Event;

/**
 * Event that can be triggered by the display.
 * Not all values are used for every event.
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
	 * @param source the cause of the event
	 * @param newX new position on the X axis in pixel
	 * @param newY new position on the Y axis in pixel
	 * @param newWidth new frame width in pixel
	 * @param newHeight new frame height in pixel
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
	 * @param source the cause of the event
	 * @since 16.08.2018/0.1.0
	 */
	public DisplayEvent(Object source) {
		
		super(source);
	}
	
	/**
	 * @return new position on the X axis in pixel
	 * @since 16.08.2018/0.1.0
	 */
	public int getNewX() {
		
		return this.newX;
	}
	
	/**
	 * @return new position on the Y axis in pixel
	 * @since 16.08.2018/0.1.0
	 */
	public int getNewY() {
		
		return this.newY;
	}
	
	/**
	 * @return new frame width in pixel
	 * @since 16.08.2018/0.1.0
	 */
	public int getNewWidth() {
		
		return this.newWidth;
	}
	
	/**
	 * @return new frame height in pixel
	 * @since 16.08.2018/0.1.0
	 */
	public int getNewHeight() {
		
		return this.newHeight;
	}
}
