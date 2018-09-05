package de.ralleytn.engine.caveman.shape2d;

import javax.vecmath.Vector2f;

/**
 * 
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 05.09.2018/0.4.0
 * @since 04.09.2018/0.4.0
 */
public class Rect2D implements Shape2D {

	public float x;
	public float y;
	public float width;
	public float height;
	
	/**
	 * @since 05.09.2018/0.4.0
	 */
	public Rect2D() {}
	
	/**
	 * 
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @since 05.09.2018/0.4.0
	 */
	public Rect2D(float x, float y, float width, float height) {
		
		this.setBounds(x, y, width, height);
	}
	
	/**
	 * 
	 * @param position
	 * @param size
	 * @since 05.09.2018/0.4.0
	 */
	public Rect2D(Vector2f position, Vector2f size) {
		
		this.setBounds(position, size);
	}
	
	/**
	 * 
	 * @param bounds
	 * @since 05.09.2018/0.4.0
	 */
	public Rect2D(Rect2D bounds) {
		
		this.setBounds(bounds);
	}
	
	/**
	 * 
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @since 05.09.2018/0.4.0
	 */
	public void setBounds(float x, float y, float width, float height) {
		
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	/**
	 * 
	 * @param position
	 * @param size
	 * @since 05.09.2018/0.4.0
	 */
	public void setBounds(Vector2f position, Vector2f size) {
		
		this.x = position.x;
		this.y = position.y;
		this.width = size.x;
		this.height = size.y;
	}
	
	/**
	 * 
	 * @param bounds
	 * @since 05.09.2018/0.4.0
	 */
	public void setBounds(Rect2D bounds) {
		
		this.x = bounds.x;
		this.y = bounds.y;
		this.width = bounds.width;
		this.height = bounds.height;
	}
	
	/**
	 * 
	 * @param x
	 * @param y
	 * @since 05.09.2018/0.4.0
	 */
	public void setPosition(float x, float y) {
		
		this.x = x;
		this.y = y;
	}
	
	/**
	 * 
	 * @param position
	 * @since 05.09.2018/0.4.0
	 */
	public void setPosition(Vector2f position) {
		
		this.x = position.x;
		this.y = position.y;
	}
	
	/**
	 * 
	 * @param width
	 * @param height
	 * @since 05.09.2018/0.4.0
	 */
	public void setSize(float width, float height) {
		
		this.width = width;
		this.height = height;
	}
	
	/**
	 * 
	 * @param size
	 * @since 05.09.2018/0.4.0
	 */
	public void setSize(Vector2f size) {
		
		this.width = size.x;
		this.height = size.y;
	}
	
	/**
	 * 
	 * @param x
	 * @param y
	 * @return
	 * @since 05.09.2018/0.4.0
	 */
	public boolean inside(float x, float y) {
		
		return this.width > 0 && this.height > 0 &&
			   x >= this.x && x < this.x + this.width &&
			   y >= this.y && y < this.y + this.height;
	}
	
	/**
	 * 
	 * @param point
	 * @return
	 * @since 05.09.2018/0.4.0
	 */
	public boolean inside(Vector2f point) {
		
		return this.inside(point.x, point.y);
	}
	
	/**
	 * 
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @return
	 * @since 05.09.2018/0.4.0
	 */
	public boolean contains(float x, float y, float width, float height) {
		
		return this.width > 0 && this.height > 0 &&
			   x >= this.x && x + width <= this.x + this.width &&
			   y >= this.y && y + height <= this.y + this.height;
	}
	
	/**
	 * 
	 * @param position
	 * @param size
	 * @return
	 * @since 05.09.2018/0.4.0
	 */
	public boolean contains(Vector2f position, Vector2f size) {
		
		return this.contains(position.x, position.y, size.x, size.y);
	}
	
	/**
	 * 
	 * @param rect
	 * @return
	 * @since 05.09.2018/0.4.0
	 */
	public boolean contains(Rect2D rect) {
		
		return this.contains(rect.x, rect.y, rect.width, rect.height);
	}
	
	/**
	 * 
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @return
	 * @since 05.09.2018/0.4.0
	 */
	public boolean intersects(float x, float y, float width, float height) {
		
		return this.width > 0 && this.height > 0 &&
			   width > 0 && height > 0 &&
			   x < this.x + this.width && x + width > this.x &&
			   y < this.y + this.height && y + height > this.y;
	}
	
	/**
	 * 
	 * @param position
	 * @param size
	 * @return
	 * @since 05.09.2018/0.4.0
	 */
	public boolean intersects(Vector2f position, Vector2f size) {
		
		return this.intersects(position.x, position.y, size.x, size.y);
	}
	
	/**
	 * 
	 * @param rect
	 * @return
	 * @since 05.09.2018/0.4.0
	 */
	public boolean intersects(Rect2D rect) {
		
		return this.intersects(rect.x, rect.y, rect.width, rect.height);
	}
	
	@Override
	public boolean isEmpty() {
		
		return this.width <= 0 || this.height <= 0;
	}
	
	/**
	 * 
	 * @return
	 * @since 05.09.2018/0.4.0
	 */
	public float getX() {
		
		return this.x;
	}
	
	/**
	 * 
	 * @return
	 * @since 05.09.2018/0.4.0
	 */
	public float getY() {
		
		return this.y;
	}
	
	/**
	 * 
	 * @return
	 * @since 05.09.2018/0.4.0
	 */
	public float getWidth() {
		
		return this.width;
	}
	
	/**
	 * 
	 * @return
	 * @since 05.09.2018/0.4.0
	 */
	public float getHeight() {
		
		return this.height;
	}
	
	/**
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public int outcode(float x, float y) {
		
		int result = 0;
		
		if(this.width <= 0) {
			
			result |= OUT_LEFT | OUT_RIGHT;
			
		} else if(x < this.x) {
			
			result |= OUT_LEFT;
			
		} else if(x > this.x + this.width) {
			
			result |= OUT_RIGHT;
		}
		
		if(this.height <= 0) {
			
			result |= OUT_TOP | OUT_BOTTOM;
			
		} else if(y < this.y) {
			
			result |= OUT_TOP;
			
		} else if(y > this.x + this.height) {
			
			result |= OUT_BOTTOM;
		}
		
		return result;
	}
	
	@Override
	public Rect2D getBounds() {
		
		return this;
	}
	
	@Override
	public String toString() {
		
		StringBuilder builder = new StringBuilder();
		builder.append(this.getClass().getName());
		builder.append("[x=").append(this.x);
		builder.append(",y=").append(this.y);
		builder.append(",width=").append(this.width);
		builder.append(",height=").append(this.height);
		builder.append(']');
		return builder.toString();
	}
}
