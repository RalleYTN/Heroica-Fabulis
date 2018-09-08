package de.ralleytn.engine.caveman.rendering.geom;

import javax.vecmath.Vector3f;

/**
 * 
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 08.09.2018/0.4.0
 * @since 08.09.2018/0.4.0
 */
public abstract class Box {

	/**
	 * @since 08.09.2018/0.4.0
	 */
	public static final int[] INDICES = {
			
		3, 1, 0,
		2, 1, 3,
		4, 5, 7,
		7, 5, 6,
		11, 9, 8,
		10, 9, 11,
		12, 13, 15,
		15, 13, 14,
		19, 17, 16,
		18, 17 ,19,
		20, 21, 23,
		23, 21, 22
	};
	
	/**
	 * @since 08.09.2018/0.4.0
	 */
	public static final float[] TEXCOORDS = {
			
		0.0F, 0.0F,
		0.0F, 1.0F,
		1.0F, 1.0F,
		1.0F, 0.0F,
		0.0F, 0.0F,
		0.0F, 1.0F,
		1.0F, 1.0F,
		1.0F, 0.0F,
		0.0F, 0.0F,
		0.0F, 1.0F,
		1.0F, 1.0F,
		1.0F, 0.0F,
		0.0F, 0.0F,
		0.0F, 1.0F,
		1.0F, 1.0F,
		1.0F, 0.0F,
		0.0F, 0.0F,
		0.0F, 1.0F,
		1.0F, 1.0F,
		1.0F, 0.0F,
		0.0F, 0.0F,
		0.0F, 1.0F,
		1.0F, 1.0F,
		1.0F, 0.0F
	};
	
	public float width;
	public float height;
	public float depth;
	public float x;
	public float y;
	public float z;

	/**
	 * @since 08.09.2018/0.4.0
	 */
	public Box() {}

	/**
	 * 
	 * @param box
	 * @since 08.09.2018/0.4.0
	 */
	public Box(Box box) {
		
		this.set(box);
	}

	/**
	 * 
	 * @param x
	 * @param y
	 * @param z
	 * @param width
	 * @param height
	 * @param depth
	 * @since 08.09.2018/0.4.0
	 */
	public Box(float x, float y, float z, float width, float height, float depth) {
		
		this.set(x, y, z, width, height, depth);
	}

	/**
	 * 
	 * @param position
	 * @param size
	 * @since 08.09.2018/0.4.0
	 */
	public Box(Vector3f position, Vector3f size) {
		
		this.set(position, size);
	}

	/**
	 * 
	 * @param box
	 * @since 08.09.2018/0.4.0
	 */
	public void set(Box box) {
		
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
	 * @since 08.09.2018/0.4.0
	 */
	public void set(float x, float y, float z, float width, float height, float depth) {
		
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
	 * @since 08.09.2018/0.4.0
	 */
	public void set(Vector3f position, Vector3f size) {
		
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
	 * @since 08.09.2018/0.4.0
	 */
	public void setPosition(float x, float y, float z) {
		
		this.x = x;
		this.y = y;
		this.z = z;
	}

	/**
	 * 
	 * @param position
	 * @since 08.09.2018/0.4.0
	 */
	public void setPosition(Vector3f position) {
		
		this.setPosition(position.x, position.y, position.z);
	}

	/**
	 * 
	 * @param width
	 * @param height
	 * @param depth
	 * @since 08.09.2018/0.4.0
	 */
	public void setSize(float width, float height, float depth) {
		
		this.width = width;
		this.height = height;
		this.depth = depth;
	}

	/**
	 * 
	 * @param size
	 * @since 08.09.2018/0.4.0
	 */
	public void setSize(Vector3f size) {
		
		this.width = size.x;
		this.height = size.y;
		this.depth = size.z;
	}
	
	/**
	 * 
	 * @param point
	 * @return
	 * @since 08.09.2018/0.4.0
	 */
	public boolean inside(Vector3f point) {
		
		return this.inside(point.x, point.y, point.z);
	}
	
	/**
	 * 
	 * @param x
	 * @param y
	 * @param z
	 * @return
	 * @since 08.09.2018/0.4.0
	 */
	public abstract boolean inside(float x, float y, float z);
	
	/**
	 * 
	 * @return
	 * @since 08.09.2018/0.4.0
	 */
	public abstract Vector3f center();

	/**
	 * 
	 * @return
	 * @since 08.09.2018/0.4.0
	 */
	public boolean isEmpty() {

		return this.width <= 0 || this.height <= 0 || this.depth <= 0;
	}

	/**
	 * 
	 * @return
	 * @since 08.09.2018/0.4.0
	 */
	public float getX() {
		
		return this.x;
	}

	/**
	 * 
	 * @return
	 * @since 08.09.2018/0.4.0
	 */
	public float getY() {
		
		return this.y;
	}

	/**
	 * 
	 * @return
	 * @since 08.09.2018/0.4.0
	 */
	public float getZ() {
		
		return this.z;
	}

	/**
	 * 
	 * @return
	 * @since 08.09.2018/0.4.0
	 */
	public float getDepth() {
		
		return this.depth;
	}

	/**
	 * 
	 * @return
	 * @since 08.09.2018/0.4.0
	 */
	public float getHeight() {
		
		return this.height;
	}

	/**
	 * 
	 * @return
	 * @since 08.09.2018/0.4.0
	 */
	public float getWidth() {
		
		return this.width;
	}

	/**
	 * 
	 * @return
	 * @since 08.09.2018/0.4.0
	 */
	public Vector3f getPosition() {
		
		return new Vector3f(this.x, this.y, this.z);
	}

	/**
	 * 
	 * @return
	 * @since 08.09.2018/0.4.0
	 */
	public Vector3f getSize() {
		
		return new Vector3f(this.width, this.height, this.depth);
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
