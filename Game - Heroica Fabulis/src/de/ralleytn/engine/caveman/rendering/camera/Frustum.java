package de.ralleytn.engine.caveman.rendering.camera;

import javax.vecmath.Vector3f;

import de.ralleytn.engine.caveman.rendering.geom.OrientedBox;
import de.ralleytn.engine.caveman.util.VectorUtil;

import static de.ralleytn.engine.caveman.util.VectorUtil.*;

/**
 * 
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 08.09.2018/0.4.0
 * @since 06.09.2018/0.4.0
 */
public final class Frustum {

	private final Camera camera;
	private final OrientedBox obb;
	
	/**
	 * 
	 * @param camera
	 * @since 06.09.2018/0.4.0
	 */
	public Frustum(Camera camera) {
		
		this.camera = camera;
		this.obb = new OrientedBox();
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
		Vector3f ftl = substract(add(fc, multiply(up, fhh)), multiply(right, fwh));
		Vector3f ftr = add(add(fc, multiply(up, fhh)), multiply(right, fwh));
		Vector3f fbl = substract(substract(fc, multiply(up, fhh)), multiply(right, fwh));
		
		Vector3f nc = add(position, multiply(direction, near));
		float nwh = nearWidth * 0.5F;
		float nhh = nearHeight * 0.5F;
		Vector3f ntl = substract(add(nc, multiply(up, nhh)), multiply(right, nwh));
		
		float width = VectorUtil.getAbsoluteDistance(ftl, ftr);
		float height = VectorUtil.getAbsoluteDistance(ftl, fbl);
		float depth = VectorUtil.getAbsoluteDistance(ftl, ntl);
		float x = -(width * 0.5F);
		
		this.obb.set(x, 0, 0, width, height, depth, this.camera.getRotation().x, this.camera.getRotation().y, this.camera.getRotation().z);
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
	 * @since 08.09.2018/0.4.0
	 */
	public final OrientedBox getOBB() {
		
		return this.obb;
	}
}
