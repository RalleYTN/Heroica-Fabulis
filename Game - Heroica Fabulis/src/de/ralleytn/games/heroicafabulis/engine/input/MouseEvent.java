package de.ralleytn.games.heroicafabulis.engine.input;

/**
 * Container for data of a mouse input event.
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 13.08.2018/0.1.0
 * @since 13.08.2018/0.1.0
 */
public class MouseEvent extends InputEvent {

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
	 * Sets the coordinates of the mouse cursor relative to the display.
	 * @param x the position on the X axis in pixel
	 * @param y the position on the Y axis in pixel
	 * @since 13.08.2018/0.1.0
	 */
	public void setPosition(int x, int y) {
		
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Sets a bit sequence with modifier flags.
	 * @param mods the modifier flags
	 * @since 13.08.2018/0.1.0
	 */
	public void setMods(int mods) {
		
		this.mods = mods;
	}
	
	/**
	 * Sets the button associated with this event
	 * @param button the button
	 * @since 13.08.2018/0.1.0
	 */
	public void setButton(int button) {
		
		this.button = button;
	}
	
	/**
	 * Sets the scroll offset.
	 * @param scrollOffset negative = DOWN, positiv = UP
	 * @since 13.08.2018/0.1.0
	 */
	public void setScrollOffset(double scrollOffset) {
		
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
