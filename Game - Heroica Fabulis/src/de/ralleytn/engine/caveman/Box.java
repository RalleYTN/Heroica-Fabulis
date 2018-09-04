package de.ralleytn.engine.caveman;

/**
 * 
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 04.09.2018/0.4.0
 * @since 04.09.2018/0.4.0
 */
public class Box {

	public float x;
	public float y;
	public float z;
	public float width;
	public float height;
	public float depth;
	
	/**
	 * @since 04.09.2018/0.4.0
	 */
	public Box() {}
	
	/**
	 * 
	 * @param x
	 * @param y
	 * @param z
	 * @param width
	 * @param height
	 * @param depth
	 * @since 04.09.2018/0.4.0
	 */
	public Box(float x, float y, float z, float width, float height, float depth) {
		
		this.x = x;
		this.y = y;
		this.z = z;
		this.width = width;
		this.height = height;
		this.depth = depth;
	}
	
	/**
	 * 
	 * @param x
	 * @param y
	 * @param z
	 * @since 04.09.2018/0.4.0
	 */
	public void setPosition(float x, float y, float z) {
		
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	/**
	 * 
	 * @param width
	 * @param height
	 * @param depth
	 * @since 04.09.2018/0.4.0
	 */
	public void setSize(float width, float height, float depth) {
		
		this.width = width;
		this.height = height;
		this.depth = depth;
	}
	
	/**
	 * 
	 * @param box
	 * @return
	 * @since 04.09.2018/0.4.0
	 */
	public boolean intersects(Box box) {
		
		return false;
	}
	
	/**
	 * 
	 * @return
	 * @since 04.09.2018/0.4.0
	 */
	public float getX() {
		
		return this.x;
	}
	
	/**
	 * 
	 * @return
	 * @since 04.09.2018/0.4.0
	 */
	public float getY() {
		
		return this.y;
	}
	
	/**
	 * 
	 * @return
	 * @since 04.09.2018/0.4.0
	 */
	public float getZ() {
		
		return this.z;
	}
	
	/**
	 * 
	 * @return
	 * @since 04.09.2018/0.4.0
	 */
	public float getDepth() {
		
		return this.depth;
	}
	
	/**
	 * 
	 * @return
	 * @since 04.09.2018/0.4.0
	 */
	public float getHeight() {
		
		return this.height;
	}
	
	/**
	 * 
	 * @return
	 * @since 04.09.2018/0.4.0
	 */
	public float getWidth() {
		
		return this.width;
	}
}
