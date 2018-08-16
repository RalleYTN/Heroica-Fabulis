package de.ralleytn.games.heroicafabulis.engine.input;

import de.ralleytn.games.heroicafabulis.engine.Event;

/**
 * Container for data of a mouse input event.
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 16.08.2018/0.1.0
 * @since 13.08.2018/0.1.0
 */
public class MouseEvent extends Event {

	private int x;
	private int y;
	private int mods;
	private int button;
	private double scrollOffset;
	
	/**
	 * @param source the cause of the event
	 * @since 13.08.2018/0.1.0
	 */
	public MouseEvent(Object source) {
		
		super(source);
	}
	
	/**
	 * 
	 * @param source
	 * @param x
	 * @param y
	 * @param button
	 * @param mods
	 * @since 16.08.2018/0.1.0
	 */
	public MouseEvent(Object source, int x, int y, int button, int mods) {
		
		super(source);
		
		this.x = x;
		this.y = y;
		this.button = button;
		this.mods = mods;
	}
	
	/**
	 * 
	 * @param source
	 * @param scrollOffset
	 * @since 16.08.2018/0.1.0
	 */
	public MouseEvent(Object source, double scrollOffset) {
		
		super(source);
		
		this.scrollOffset = scrollOffset;
	}
	
	/**
	 * @return mouse cursor position on the X axis in pixel relative to the display
	 * @since 13.08.2018/0.1.0
	 */
	public int getX() {
		
		return this.x;
	}

	/**
	 * @return mouse cursor position on the Y axis in pixel relative to the display
	 * @since 13.08.2018/0.1.0
	 */
	public int getY() {
		
		return this.y;
	}

	/**
	 * @return the button associated with this event
	 * @since 13.08.2018/0.1.0
	 */
	public int getButton() {
		
		return this.button;
	}

	/**
	 * @return a bit sequence with the modifier flags
	 * @since 13.08.2018/0.1.0
	 */
	public int getMods() {
		
		return this.mods;
	}

	/**
	 * @return the scroll offset
	 * @since 13.08.2018/0.1.0
	 */
	public double getScrollOffset() {
		
		return this.scrollOffset;
	}
}
