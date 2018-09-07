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
	
	public final Plane near;
	public final Plane far;
	public final Plane left;
	public final Plane right;
	public final Plane up;
	public final Plane down;
	
	/**
	 * 
	 * @param camera
	 * @since 06.09.2018/0.4.0
	 */
	public Frustum(Camera camera) {
		
		this.camera = camera;
		this.near = new Plane();
		this.far = new Plane();
		this.left = new Plane();
		this.right = new Plane();
		this.up = new Plane();
		this.down = new Plane();
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
		this.far.tl = substract(add(fc, multiply(up, fhh)), multiply(right, fwh));
		this.far.tr = add(add(fc, multiply(up, fhh)), multiply(right, fwh));
		this.far.bl = substract(substract(fc, multiply(up, fhh)), multiply(right, fwh));
		this.far.br = add(substract(fc, multiply(up, fhh)), multiply(right, fwh));
		this.far.n.set(direction);
		
		Vector3f nc = add(position, multiply(direction, near));
		float nwh = nearWidth * 0.5F;
		float nhh = nearHeight * 0.5F;
		this.near.tl = substract(add(nc, multiply(up, nhh)), multiply(right, nwh));
		this.near.tr = add(add(nc, multiply(up, nhh)), multiply(right, nwh));
		this.near.bl = substract(substract(nc, multiply(up, nhh)), multiply(right, nwh));
		this.near.br = add(substract(nc, multiply(up, nhh)), multiply(right, nwh));
		this.near.n.set(-direction.x, -direction.y, -direction.z);
		
		this.right.tl = this.near.tr;
		this.right.tr = this.far.tr;
		this.right.bl = this.near.br;
		this.right.br = this.far.br;
		this.right.n = rotateX(direction, 90 - fovh);
		this.right.n.normalize();
		
		this.left.tl = this.far.tl;
		this.left.tr = this.near.tl;
		this.left.bl = this.far.bl;
		this.left.br = this.near.bl;
		this.left.n.set(-this.right.n.x, -this.right.n.y, -this.right.n.z);
		
		this.up.tl = this.far.tl;
		this.up.tr = this.far.tr;
		this.up.bl = this.near.tl;
		this.up.br = this.near.tr;
		this.up.n = rotateY(direction, 90 - fovh);
		this.up.n.normalize();
		
		this.down.tl = this.near.bl;
		this.down.tr = this.near.br;
		this.down.bl = this.far.bl;
		this.down.br = this.far.br;
		this.down.n.set(-this.up.n.x, -this.up.n.y, -this.up.n.z);
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
