package de.ralleytn.engine.caveman.shape3d;

import javax.vecmath.Vector3f;

/**
 * 
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 04.09.2018/0.4.0
 * @since 04.09.2018/0.4.0
 */
public class Box3D implements Shape3D {

	public float x;
	public float y;
	public float z;
	public float width;
	public float height;
	public float depth;
	
	/**
	 * @since 04.09.2018/0.4.0
	 */
	public Box3D() {}
	
	/**
	 * 
	 * @param box
	 * @since 04.09.2018/0.4.0
	 */
	public Box3D(Box3D box) {
		
		this.setBounds(box);
	}
	
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
	public Box3D(float x, float y, float z, float width, float height, float depth) {
		
		this.setBounds(x, y, z, width, height, depth);
	}
	
	/**
	 * 
	 * @param position
	 * @param size
	 * @since 04.09.2018/0.4.0
	 */
	public Box3D(Vector3f position, Vector3f size) {
		
		this.setBounds(position, size);
	}
	
	/**
	 * 
	 * @param box
	 * @since 04.09.2018/0.4.0
	 */
	public void setBounds(Box3D box) {
		
		this.x = box.x;
		this.y = box.y;
		this.z = box.z;
		this.width = box.width;
		this.height = box.height;
		this.depth = box.depth;
	}
	
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
	public void setBounds(float x, float y, float z, float width, float height, float depth) {
		
		this.x = x;
		this.y = y;
		this.z = z;
		this.width = width;
		this.height = height;
		this.depth = depth;
	}
	
	/**
	 * 
	 * @param position
	 * @param size
	 * @since 04.09.2018/0.4.0
	 */
	public void setBounds(Vector3f position, Vector3f size) {
		
		this.x = position.x;
		this.y = position.y;
		this.z = position.z;
		this.width = size.x;
		this.height = size.y;
		this.depth = size.z;
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
	 * @param position
	 * @since 04.09.2018/0.4.0
	 */
	public void setPosition(Vector3f position) {
		
		this.setPosition(position.x, position.y, position.z);
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
	 * @param size
	 * @since 04.09.2018/0.4.0
	 */
	public void setSize(Vector3f size) {
		
		this.width = size.x;
		this.height = size.y;
		this.depth = size.z;
	}
	
	/**
	 * 
	 * @param x
	 * @param y
	 * @param z
	 * @return
	 * @since 04.09.2018/0.4.0
	 */
	public boolean inside(float x, float y, float z) {
		
		return this.width > 0 && this.height > 0 && this.depth > 0 &&
			   x >= this.x && x < this.x + this.width &&
			   y >= this.y && y < this.y + this.height &&
			   z >= this.z && z < this.z + this.depth;
	}
	
	/**
	 * 
	 * @param point
	 * @return
	 * @since 04.09.2018/0.4.0
	 */
	public boolean inside(Vector3f point) {
		
		return this.inside(point.x, point.y, point.z);
	}
	
	/**
	 * 
	 * @param position
	 * @param size
	 * @return
	 * @since 04.09.2018/0.4.0
	 */
	public boolean contains(Vector3f position, Vector3f size) {
		
		return this.contains(position.x, position.y, position.z, size.x, size.y, size.z);
	}
	
	/**
	 * 
	 * @param box
	 * @return
	 * @since 04.09.2018/0.4.0
	 */
	public boolean contains(Box3D box) {
		
		return this.contains(box.x, box.y, box.z, box.width, box.height, box.depth);
	}
	
	/**
	 * 
	 * @param x
	 * @param y
	 * @param z
	 * @param width
	 * @param height
	 * @param depth
	 * @return
	 * @since 04.09.2018/0.4.0
	 */
	public boolean contains(float x, float y, float z, float width, float height, float depth) {
		
		return this.width > 0 && this.height > 0 && this.depth > 0 &&
			   x >= this.x && x + width <= this.x + this.width &&
			   y >= this.y && y + height <= this.y + this.height &&
			   z >= this.z && z + depth <= this.z + this.depth;
	}
	
	/**
	 * 
	 * @param position
	 * @param size
	 * @return
	 * @since 04.09.2018/0.4.0
	 */
	public boolean intersects(Vector3f position, Vector3f size) {
		
		return this.intersects(position.x, position.y, position.z, size.x, size.y, size.z);
	}
	
	/**
	 * 
	 * @param bounds
	 * @return
	 * @since 04.09.2018/0.4.0
	 */
	public boolean intersects(Box3D bounds) {
		
		return this.intersects(bounds.x, bounds.y, bounds.z, bounds.width, bounds.height, bounds.depth);
	}
	
	/**
	 * 
	 * @param x
	 * @param y
	 * @param z
	 * @param width
	 * @param height
	 * @param depth
	 * @return
	 * @since 04.09.2018/0.4.0
	 */
	public boolean intersects(float x, float y, float z, float width, float height, float depth) {
		
		return this.width > 0 && this.height > 0 && this.depth > 0 &&
			   width > 0 && height > 0 && depth > 0 &&
			   x < this.x + this.width && x + width > this.x &&
			   y < this.y + this.height && y + height > this.y &&
			   z < this.z + this.depth && z + depth > this.z;
	}
	
	/**
	 * 
	 * @return
	 * @since 04.09.2018/0.4.0
	 */
	public boolean isEmpty() {

		return this.width <= 0 || this.height <= 0 || this.depth <= 0;
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

	@Override
	public Box3D getBounds() {
		
		return this;
	}
	
	/**
	 * 
	 * @param x
	 * @param y
	 * @param z
	 * @return
	 * @since 04.09.2018/0.4.0
	 */
	public int outcode(float x, float y, float z) {
		
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
			
			result |= OUT_BOTTOM;
			
		} else if(y > this.x + this.height) {
			
			result |= OUT_TOP;
		}
		
		if(this.depth <= 0) {
			
			result |= OUT_BACK | OUT_FRONT;
			
		} else if(z < this.z) {
			
			result |= OUT_BACK;
			
		} else if(z > this.z + this.depth) {
			
			result |= OUT_FRONT;
		}
		
		return result;
	}
	
	@Override
	public String toString() {
		
		StringBuilder builder = new StringBuilder();
		builder.append(this.getClass().getName());
		builder.append("[x=").append(this.x);
		builder.append(",y=").append(this.y);
		builder.append(",z=").append(this.z);
		builder.append(",width=").append(this.width);
		builder.append(",height=").append(this.height);
		builder.append(",depth=").append(this.depth);
		builder.append(']');
		return builder.toString();
	}
}