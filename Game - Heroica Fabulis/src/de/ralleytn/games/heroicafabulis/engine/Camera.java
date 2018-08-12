package de.ralleytn.games.heroicafabulis.engine;

import javax.vecmath.Matrix4f;
import javax.vecmath.Vector3f;

import de.ralleytn.games.heroicafabulis.engine.util.MatrixUtil;

/**
 * Represents the game camera. There should be only a single instance of it at a time.
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 11.08.2018/0.1.0
 * @since 11.08.2018/0.1.0
 */
public final class Camera implements Translatable, Rotatable {

	private float fov;
	private float nearPlaneDistance;
	private float farPlaneDistance;
	private Matrix4f projection;
	private Matrix4f view;
	private Vector3f translation;
	private Vector3f rotation;
	private Display display;
	
	/**
	 * @param display the game display
	 * @since 11.08.2018/0.1.0
	 */
	Camera(Display display) {
		
		this.fov = 45.0F;
		this.nearPlaneDistance = 0.01F;
		this.farPlaneDistance = 1000.0F;
		this.translation = new Vector3f();
		this.rotation = new Vector3f();
		this.display = display;
		this.update();
	}
	
	@Override
	public void translate(float x, float y, float z) {

		Translatable.super.translate(x, y, z);
		this.recalcViewMatrix();
	}
	
	@Override
	public void translate(Vector3f velocity) {

		Translatable.super.translate(velocity);
		this.recalcViewMatrix();
	}
	
	@Override
	public void setTranslation(float x, float y, float z) {

		Translatable.super.setTranslation(x, y, z);
		this.recalcViewMatrix();
	}
	
	@Override
	public void setTranslation(Vector3f newTranslation) {

		Translatable.super.setTranslation(newTranslation);
		this.recalcViewMatrix();
	}
	
	@Override
	public void rotate(float x, float y, float z) {

		Rotatable.super.rotate(x, y, z);
		this.recalcViewMatrix();
	}
	
	@Override
	public void rotate(Vector3f velocity) {

		Rotatable.super.rotate(velocity);
		this.recalcViewMatrix();
	}
	
	@Override
	public void setRotation(float x, float y, float z) {

		Rotatable.super.setRotation(x, y, z);
		this.recalcViewMatrix();
	}
	
	@Override
	public void setRotation(Vector3f newRotation) {

		Rotatable.super.setRotation(newRotation);
		this.recalcViewMatrix();
	}
	
	/**
	 * @since 11.08.2018/0.1.0
	 */
	private void recalcProjectionMatrix() {

        float aspectRatio = (float)this.display.getFrameBufferWidth() / (float)this.display.getFrameBufferHeight();
        float yScale = (float)((1.0F / Math.tan(Math.toRadians(this.fov / 2.0F))));
        float xScale = yScale / aspectRatio;
        float frustumLength = this.farPlaneDistance - this.nearPlaneDistance;
        
        this.projection = new Matrix4f();
        this.projection.m00 = xScale;
        this.projection.m11 = yScale;
        this.projection.m22 = -((this.farPlaneDistance + this.nearPlaneDistance) / frustumLength);
        this.projection.m23 = -1.0F;
        this.projection.m32 = -((2.0F * this.nearPlaneDistance * this.farPlaneDistance) / frustumLength);
        this.projection.m33 = 0.0F;
	}

	/**
	 * @since 11.08.2018/0.1.0
	 */
	private void recalcViewMatrix() {
		
		this.view = new Matrix4f();
		this.view.setIdentity(); // Doesn't work without this line
		MatrixUtil.rotate((float)Math.toRadians(this.rotation.x), Engine.AXIS_X, this.view);
		MatrixUtil.rotate((float)Math.toRadians(this.rotation.y), Engine.AXIS_Y, this.view);
		MatrixUtil.rotate((float)Math.toRadians(this.rotation.z), Engine.AXIS_Z, this.view);
		Vector3f negativeTranslation = new Vector3f(-this.translation.x, -this.translation.y, -this.translation.z);
		MatrixUtil.translate(negativeTranslation, this.view);
	}
	
	/**
	 * @since 11.08.2018/0.1.0
	 */
	public void update() {
		
		this.recalcProjectionMatrix();
		this.recalcViewMatrix();
	}
	
	/**
	 * 
	 * @param fov
	 * @since 11.08.2018/0.1.0
	 */
	public void setFOV(float fov) {
		
		this.fov = fov;
		this.recalcProjectionMatrix();
	}
	
	/**
	 * 
	 * @param nearPlaneDistance
	 * @since 11.08.2018/0.1.0
	 */
	public void setNearPlaneDistance(float nearPlaneDistance) {
		
		this.nearPlaneDistance = nearPlaneDistance;
		this.recalcProjectionMatrix();
	}
	
	/**
	 * 
	 * @param farPlaneDistance
	 * @since 11.08.2018/0.1.0
	 */
	public void setFarPlaneDistance(float farPlaneDistance) {
		
		this.farPlaneDistance = farPlaneDistance;
		this.recalcProjectionMatrix();
	}
	
	@Override
	public Vector3f getRotation() {

		return this.rotation;
	}
	
	@Override
	public Vector3f getTranslation() {

		return this.translation;
	}
	
	/**
	 * 
	 * @return
	 * @since 11.08.2018/0.1.0
	 */
	public float getFOV() {
		
		return this.fov;
	}
	
	/**
	 * 
	 * @return
	 * @since 11.08.2018/0.1.0
	 */
	public float getNearPlaneDistance() {
		
		return this.nearPlaneDistance;
	}
	
	/**
	 * 
	 * @return
	 * @since 11.08.2018/0.1.0
	 */
	public float getFarPlaneDistance() {
		
		return this.farPlaneDistance;
	}
	
	/**
	 * 
	 * @return
	 * @since 11.08.2018/0.1.0
	 */
	public Matrix4f getProjectionMatrix() {
		
		return this.projection;
	}
	
	/**
	 * 
	 * @return
	 * @since 11.08.2018/0.1.0
	 */
	public Matrix4f getViewMatrix() {
		
		return this.view;
	}
}
