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
	private final Plane n;
	private final Plane f;
	private final Vector3f fn;
	private final Vector3f bn;
	private final Vector3f ln;
	private final Vector3f rn;
	private final Vector3f un;
	private final Vector3f dn;
	
	/**
	 * 
	 * @param camera
	 * @since 06.09.2018/0.4.0
	 */
	Frustum(Camera camera) {
		
		this.camera = camera;
		this.n = new Plane();
		this.f = new Plane();
		this.fn = new Vector3f();
		this.bn = new Vector3f();
		this.ln = new Vector3f();
		this.rn = new Vector3f();
		this.un = new Vector3f();
		this.dn = new Vector3f();
	}
	
	/**
	 * @since 06.09.2018/0.4.0
	 */
	public void update() {
		
		float fov = this.camera.getFOV();
		float near = this.camera.getNearPlaneDistance();
		float far = this.camera.getFarPlaneDistance();
		float ratio = this.camera.getGame().getDisplay().getRatio();
		
		Vector3f d = this.camera.getFront();
		Vector3f up = this.camera.getUp();
		Vector3f right = this.camera.getRight();
		Vector3f p = this.camera.getTranslation();
		
		float l = 2.0F * (float)Math.tan(fov / 2.0F);
		this.n.h = l * near;
		this.n.w = this.n.h * ratio;
		this.f.h = l * far;
		this.f.w = this.n.h * ratio;
		
		Vector3f fc = add(p, multiply(d, far));
		float fwh = this.f.w * 0.5F;
		float fhh = this.f.h * 0.5F;
		this.f.tl = substract(add(fc, multiply(up, fhh)), multiply(right, fwh));
		this.f.tr = add(add(fc, multiply(up, fhh)), multiply(right, fwh));
		this.f.bl = substract(substract(fc, multiply(up, fhh)), multiply(right, fwh));
		this.f.br = add(substract(fc, multiply(up, fhh)), multiply(right, fwh));
		
		Vector3f nc = add(p, multiply(d, near));
		float nwh = this.n.w * 0.5F;
		float nhh = this.n.h * 0.5F;
		this.n.tl = substract(add(nc, multiply(up, nhh)), multiply(right, nwh));
		this.n.tr = add(add(nc, multiply(up, nhh)), multiply(right, nwh));
		this.n.bl = substract(substract(nc, multiply(up, nhh)), multiply(right, nwh));
		this.n.br = add(substract(nc, multiply(up, nhh)), multiply(right, nwh));
		
		this.fn.set(d);
		this.bn.set(-d.x, -d.y, -d.z);
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
		
		return this.n;
	}
	
	/**
	 * 
	 * @return
	 * @since 06.09.2018/0.4.0
	 */
	public final Plane getFarPlane() {
		
		return this.f;
	}
	
	/**
	 * 
	 * @return
	 * @since 06.09.2018/0.4.0
	 */
	public final Vector3f getLeftNormal() {
		
		return this.ln;
	}
	
	/**
	 * 
	 * @return
	 * @since 06.09.2018/0.4.0
	 */
	public final Vector3f getRightNormal() {
		
		return this.rn;
	}
	
	/**
	 * 
	 * @return
	 * @since 06.09.2018/0.4.0
	 */
	public final Vector3f getFrontNormal() {
		
		return this.fn;
	}
	
	/**
	 * 
	 * @return
	 * @since 06.09.2018/0.4.0
	 */
	public final Vector3f getBackNormal() {
		
		return this.bn;
	}
	
	/**
	 * 
	 * @return
	 * @since 06.09.2018/0.4.0
	 */
	public final Vector3f getUpNormal() {
		
		return this.un;
	}
	
	/**
	 * 
	 * @return
	 * @since 06.09.2018/0.4.0
	 */
	public final Vector3f getDownNormal() {
		
		return this.dn;
	}
}
