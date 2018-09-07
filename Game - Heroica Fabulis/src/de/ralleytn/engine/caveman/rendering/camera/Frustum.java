package de.ralleytn.engine.caveman.rendering.camera;

import javax.vecmath.Vector3f;

import static de.ralleytn.engine.caveman.util.VectorUtil.*;

/**
 * 
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 06.09.2018/0.4.0
 * @since 06.09.2018/0.4.0
 */
public final class Frustum {

	private final Camera camera;
	private final Plane near;
	private final Plane far;
	private final Vector3f frontNormal;
	private final Vector3f backNormal;
	private final Vector3f leftNormal;
	private final Vector3f rightNormal;
	private final Vector3f upNormal;
	private final Vector3f downNormal;
	
	/**
	 * 
	 * @param camera
	 * @since 06.09.2018/0.4.0
	 */
	Frustum(Camera camera) {
		
		this.camera = camera;
		this.near = new Plane();
		this.far = new Plane();
		this.frontNormal = new Vector3f();
		this.backNormal = new Vector3f();
		this.leftNormal = new Vector3f();
		this.rightNormal = new Vector3f();
		this.upNormal = new Vector3f();
		this.downNormal = new Vector3f();
	}
	
	/**
	 * @since 06.09.2018/0.4.0
	 */
	public void update() {
		
		float fov = this.camera.getFOV();
		float near = this.camera.getNearPlaneDistance();
		float far = this.camera.getFarPlaneDistance();
		float ratio = this.camera.getGame().getDisplay().getRatio();
		
		Vector3f direction = this.camera.getFront();
		Vector3f up = this.camera.getUp();
		Vector3f right = this.camera.getRight();
		Vector3f position = this.camera.getTranslation();
		
		float l = 2.0F * (float)Math.tan(fov / 2.0F);
		float nearHeight = l * near;
		float nearWidth = nearHeight * ratio;
		float farHeight = l * far;
		float farWidth = farHeight * ratio;
		
		Vector3f fc = add(position, multiply(direction, far));
		float fwh = farWidth * 0.5F;
		float fhh = farHeight * 0.5F;
		this.far.tl = substract(add(fc, multiply(up, fhh)), multiply(right, fwh));
		this.far.tr = add(add(fc, multiply(up, fhh)), multiply(right, fwh));
		this.far.bl = substract(substract(fc, multiply(up, fhh)), multiply(right, fwh));
		this.far.br = add(substract(fc, multiply(up, fhh)), multiply(right, fwh));
		
		Vector3f nc = add(position, multiply(direction, near));
		float nwh = nearWidth * 0.5F;
		float nhh = nearHeight * 0.5F;
		this.near.tl = substract(add(nc, multiply(up, nhh)), multiply(right, nwh));
		this.near.tr = add(add(nc, multiply(up, nhh)), multiply(right, nwh));
		this.near.bl = substract(substract(nc, multiply(up, nhh)), multiply(right, nwh));
		this.near.br = add(substract(nc, multiply(up, nhh)), multiply(right, nwh));
		

		float fovh = fov * 0.5F;
		this.frontNormal.set(direction);
		this.backNormal.set(-direction.x, -direction.y, -direction.z);
		this.rightNormal.set(rotateX(direction, 90 - fovh));
		this.leftNormal.set(-this.rightNormal.x, -this.rightNormal.y, -this.rightNormal.z);
		this.upNormal.set(rotateY(direction, 90 - fovh));
		this.downNormal.set(-this.upNormal.x, -this.upNormal.y, -this.upNormal.z);

	}
	
	/**
	 * 
	 * @return
	 * @since 06.09.2018/0.4.0
	 */
	public final Camera getCamera() {
		
		return this.camera;
	}
	
	/**
	 * 
	 * @return
	 * @since 06.09.2018/0.4.0
	 */
	public final Plane getNearPlane() {
		
		return this.near;
	}
	
	/**
	 * 
	 * @return
	 * @since 06.09.2018/0.4.0
	 */
	public final Plane getFarPlane() {
		
		return this.far;
	}
	
	/**
	 * 
	 * @return
	 * @since 06.09.2018/0.4.0
	 */
	public final Vector3f getLeftNormal() {
		
		return this.leftNormal;
	}
	
	/**
	 * 
	 * @return
	 * @since 06.09.2018/0.4.0
	 */
	public final Vector3f getRightNormal() {
		
		return this.rightNormal;
	}
	
	/**
	 * 
	 * @return
	 * @since 06.09.2018/0.4.0
	 */
	public final Vector3f getFrontNormal() {
		
		return this.frontNormal;
	}
	
	/**
	 * 
	 * @return
	 * @since 06.09.2018/0.4.0
	 */
	public final Vector3f getBackNormal() {
		
		return this.backNormal;
	}
	
	/**
	 * 
	 * @return
	 * @since 06.09.2018/0.4.0
	 */
	public final Vector3f getUpNormal() {
		
		return this.upNormal;
	}
	
	/**
	 * 
	 * @return
	 * @since 06.09.2018/0.4.0
	 */
	public final Vector3f getDownNormal() {
		
		return this.downNormal;
	}
}
