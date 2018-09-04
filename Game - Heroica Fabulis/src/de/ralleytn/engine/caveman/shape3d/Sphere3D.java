package de.ralleytn.engine.caveman.shape3d;

import javax.vecmath.Vector3f;

/**
 * 
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 04.09.2018/0.4.0
 * @since 04.09.2018/0.4.0
 */
public class Sphere3D implements Shape3D {

	public float x;
	public float y;
	public float z;
	public float radius;
	
	/**
	 * @since 04.09.2018/0.4.0
	 */
	public Sphere3D() {}
	
	/**
	 * 
	 * @param x
	 * @param y
	 * @param z
	 * @param radius
	 * @since 04.09.2018/0.4.0
	 */
	public Sphere3D(float x, float y, float z, float radius) {
		
		this.setPosition(x, y, z);
		this.radius = radius;
	}
	
	/**
	 * 
	 * @param x
	 * @param y
	 * @param z
	 * @since 04.09.2018/0.4.0
	 */
	public Sphere3D(float x, float y, float z) {
		
		this.setPosition(x, y, z);
	}
	
	/**
	 * 
	 * @param position
	 * @param radius
	 * @since 04.09.2018/0.4.0
	 */
	public Sphere3D(Vector3f position, float radius) {
		
		this.setPosition(position);
		this.radius = radius;
	}
	
	/**
	 * 
	 * @param position
	 * @since 04.09.2018/0.4.0
	 */
	public Sphere3D(Vector3f position) {
		
		this.setPosition(position);
	}
	
	/**
	 * 
	 * @param radius
	 * @since 04.09.2018/0.4.0
	 */
	public Sphere3D(float radius) {
		
		this.radius = radius;
	}
	
	/**
	 * 
	 * @param radius
	 * @since 04.09.2018/0.4.0
	 */
	public void setRadius(float radius) {
		
		this.radius = radius;
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
		
		this.x = position.x;
		this.y = position.y;
		this.z = position.z;
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
	public float getRadius() {
		
		return this.radius;
	}
	
	@Override
	public Box3D getBounds() {
		
		float size = this.radius * 2.0F;
		return new Box3D(this.x, this.y, this.z, size, size, size);
	}
}
