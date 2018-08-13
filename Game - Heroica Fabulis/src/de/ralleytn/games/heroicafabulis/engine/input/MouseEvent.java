package de.ralleytn.games.heroicafabulis.engine.input;

/**
 * 
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
	 * 
	 * @param source
	 * @since 13.08.2018/0.1.0
	 */
	public MouseEvent(Object source) {
		
		super(source);
	}
	
	/**
	 * 
	 * @param x
	 * @param y
	 * @since 13.08.2018/0.1.0
	 */
	public void setPosition(int x, int y) {
		
		this.x = x;
		this.y = y;
	}
	
	/**
	 * 
	 * @param mods
	 * @since 13.08.2018/0.1.0
	 */
	public void setMods(int mods) {
		
		this.mods = mods;
	}
	
	/**
	 * 
	 * @param button
	 * @since 13.08.2018/0.1.0
	 */
	public void setButton(int button) {
		
		this.button = button;
	}
	
	/**
	 * 
	 * @param scrollOffset
	 * @since 13.08.2018/0.1.0
	 */
	public void setScrollOffset(double scrollOffset) {
		
		this.scrollOffset = scrollOffset;
	}
	
	/**
	 * 
	 * @return
	 * @since 13.08.2018/0.1.0
	 */
	public int getX() {
		
		return this.x;
	}

	/**
	 * 
	 * @return
	 * @since 13.08.2018/0.1.0
	 */
	public int getY() {
		
		return this.y;
	}

	/**
	 * 
	 * @return
	 * @since 13.08.2018/0.1.0
	 */
	public int getButton() {
		
		return this.button;
	}

	/**
	 * 
	 * @return
	 * @since 13.08.2018/0.1.0
	 */
	public int getMods() {
		
		return this.mods;
	}

	/**
	 * 
	 * @return
	 * @since 13.08.2018/0.1.0
	 */
	public double getScrollOffset() {
		
		return this.scrollOffset;
	}
}
