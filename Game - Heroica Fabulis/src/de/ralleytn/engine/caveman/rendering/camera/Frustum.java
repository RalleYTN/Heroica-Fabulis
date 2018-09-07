package de.ralleytn.engine.caveman.rendering.camera;

import javax.vecmath.Vector3f;

import static de.ralleytn.engine.caveman.util.VectorUtil.*;

/**
 * 
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 07.09.2018/0.4.0
 * @since 06.09.2018/0.4.0
 */
public final class Frustum {

	private final Camera camera;
	
	public Vector3f fn = new Vector3f();
	public Vector3f bn = new Vector3f();
	public Vector3f ln = new Vector3f();
	public Vector3f rn = new Vector3f();
	public Vector3f un = new Vector3f();
	public Vector3f dn = new Vector3f();
	
	public Vector3f ftl = new Vector3f();
	public Vector3f ftr = new Vector3f();
	public Vector3f fbl = new Vector3f();
	public Vector3f fbr = new Vector3f();
	
	public Vector3f ntl = new Vector3f();
	public Vector3f ntr = new Vector3f();
	public Vector3f nbl = new Vector3f();
	public Vector3f nbr = new Vector3f();
	
	/**
	 * 
	 * @param camera
	 * @since 06.09.2018/0.4.0
	 */
	public Frustum(Camera camera) {
		
		this.camera = camera;
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
		float fovh = fov * 0.5F;
		float nearHeight = l * near;
		float nearWidth = nearHeight * ratio;
		float farHeight = l * far;
		float farWidth = farHeight * ratio;
		
		Vector3f fc = add(position, multiply(direction, far));
		float fwh = farWidth * 0.5F;
		float fhh = farHeight * 0.5F;
		this.ftl = substract(add(fc, multiply(up, fhh)), multiply(right, fwh));
		this.ftr = add(add(fc, multiply(up, fhh)), multiply(right, fwh));
		this.fbl = substract(substract(fc, multiply(up, fhh)), multiply(right, fwh));
		this.fbr = add(substract(fc, multiply(up, fhh)), multiply(right, fwh));
		this.fn.set(direction);
		
		Vector3f nc = add(position, multiply(direction, near));
		float nwh = nearWidth * 0.5F;
		float nhh = nearHeight * 0.5F;
		this.ntl = substract(add(nc, multiply(up, nhh)), multiply(right, nwh));
		this.ntr = add(add(nc, multiply(up, nhh)), multiply(right, nwh));
		this.nbl = substract(substract(nc, multiply(up, nhh)), multiply(right, nwh));
		this.nbr = add(substract(nc, multiply(up, nhh)), multiply(right, nwh));
		this.bn.set(-direction.x, -direction.y, -direction.z);
		
		this.rn = rotateX(direction, 90 - fovh);
		this.rn.normalize();
		
		this.ln.set(-this.rn.x, -this.rn.y, -this.rn.z);
		
		this.un = rotateY(direction, 90 - fovh);
		this.un.normalize();
		
		this.dn.set(-this.un.x, -this.un.y, -this.un.z);
	}
	
	/**
	 * 
	 * @return
	 * @since 06.09.2018/0.4.0
	 */
	public final Camera getCamera() {
		
		return this.camera;
	}
}
