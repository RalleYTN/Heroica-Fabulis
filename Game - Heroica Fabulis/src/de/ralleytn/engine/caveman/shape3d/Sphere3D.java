package de.ralleytn.engine.caveman.shape3d;

import javax.vecmath.Vector3f;

import de.ralleytn.engine.caveman.util.VectorUtil;

/**
 * 
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 05.09.2018/0.4.0
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
	 * @param x
	 * @param y
	 * @param z
	 * @return
	 * @since 05.09.2018/0.4.0
	 */
	public boolean inside(float x, float y, float z) {
		
		return this.inside(new Vector3f(x, y, z));
	}
	
	/**
	 * 
	 * @param point
	 * @return
	 * @since 05.09.2018/0.4.0
	 */
	public boolean inside(Vector3f point) {
		
		return VectorUtil.getDistance(this.getMiddle(), point) < this.radius;
	}
	
	/**
	 * 
	 * @param sphere
	 * @return
	 * @since 05.09.2018/0.4.0
	 */
	public boolean contains(Sphere3D sphere) {
		
		return sphere.radius * 2 < this.radius &&
			   VectorUtil.getDistance(this.getMiddle(), sphere.getMiddle()) + sphere.radius > this.radius;
	}
	
	/**
	 * 
	 * @param position
	 * @param radius
	 * @return
	 * @since 05.09.2018/0.4.0
	 */
	public boolean contains(Vector3f position, float radius) {
		
		return this.contains(new Sphere3D(position, radius));
	}
	
	/**
	 * 
	 * @param x
	 * @param y
	 * @param z
	 * @param radius
	 * @return
	 * @since 05.09.2018/0.4.0
	 */
	public boolean contains(float x, float y, float z, float radius) {
		
		return this.contains(new Sphere3D(x, y, z, radius));
	}
	
	/**
	 * 
	 * @param sphere
	 * @return
	 * @since 05.09.2018/0.4.0
	 */
	public boolean intersects(Sphere3D sphere) {
		
		return VectorUtil.getDistance(this.getMiddle(), sphere.getMiddle()) < this.radius + sphere.radius;
	}
	
	/**
	 * 
	 * @param position
	 * @param radius
	 * @return
	 * @since 05.09.2018/0.4.0
	 */
	public boolean intersects(Vector3f position, float radius) {
		
		return this.intersects(new Sphere3D(position, radius));
	}
	
	/**
	 * 
	 * @param x
	 * @param y
	 * @param z
	 * @param radius
	 * @return
	 * @since 05.09.2018/0.4.0
	 */
	public boolean intersects(float x, float y, float z, float radius) {
		
		return this.intersects(new Sphere3D(x, y, z, radius));
	}
	
	@Override
	public boolean isEmpty() {
		
		return this.radius <= 0;
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
	
	/**
	 * 
	 * @return
	 * @since 05.09.2018/0.4.0
	 */
	public Vector3f getMiddle() {
		
		return new Vector3f(this.x + this.radius, this.y + this.radius, this.z + this.radius);
	}
	
	@Override
	public Box3D getBounds() {
		
		float size = this.radius * 2.0F;
		return new Box3D(this.x, this.y, this.z, size, size, size);
	}
}
