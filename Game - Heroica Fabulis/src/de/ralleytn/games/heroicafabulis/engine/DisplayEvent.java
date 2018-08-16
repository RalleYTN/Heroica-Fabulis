package de.ralleytn.games.heroicafabulis.engine;

/**
 * 
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 16.08.2018/0.1.0
 * @since 16.08.2018/0.1.0
 */
public class DisplayEvent extends Event {

	private int x;
	private int y;
	private int width;
	private int height;
	
	/**
	 * 
	 * @param source
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @since 16.08.2018/0.1.0
	 */
	public DisplayEvent(Object source, int x, int y, int width, int height) {
		
		super(source);
		
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	/**
	 * 
	 * @return
	 * @since 16.08.2018/0.1.0
	 */
	public int getX() {
		
		return this.x;
	}
	
	/**
	 * 
	 * @return
	 * @since 16.08.2018/0.1.0
	 */
	public int getY() {
		
		return this.y;
	}
	
	/**
	 * 
	 * @return
	 * @since 16.08.2018/0.1.0
	 */
	public int getWidth() {
		
		return this.width;
	}
	
	/**
	 * 
	 * @return
	 * @since 16.08.2018/0.1.0
	 */
	public int getHeight() {
		
		return this.height;
	}
}
