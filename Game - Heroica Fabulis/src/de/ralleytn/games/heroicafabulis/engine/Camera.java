package de.ralleytn.games.heroicafabulis.engine;

import javax.vecmath.Matrix4f;
import javax.vecmath.Vector3f;

import de.ralleytn.games.heroicafabulis.engine.util.MatrixUtil;

/**
 * Represents the game camera. There should be only a single instance of it at a time.
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 14.08.2018/0.1.0
 * @since 11.08.2018/0.1.0
 */
public final class Camera implements Movable {

	private float fov;
	private float nearPlaneDistance;
	private float farPlaneDistance;
	private Matrix4f projection;
	private Matrix4f view;
	private Vector3f translation;
	private Vector3f rotation;
	private Display display;
	private Game game;
	private CameraBehavior behavior;
	
	/**
	 * @param game the {@linkplain Game} instance this camera belongs to
	 * @since 11.08.2018/0.1.0
	 */
	Camera(Game game) {
		
		this.fov = 45.0F;
		this.nearPlaneDistance = 0.01F;
		this.farPlaneDistance = 1000.0F;
		this.translation = new Vector3f();
		this.rotation = new Vector3f();
		this.game = game;
		this.display = game.getDisplay();
		this.recalc();
	}
	
	/**
	 * <br><i>Calling this method will recalculate the view matrix</i>
	 */
	@Override
	public void translate(float x, float y, float z) {

		Movable.super.translate(x, y, z);
		this.calcViewMatrix();
	}
	
	/**
	 * <br><i>Calling this method will recalculate the view matrix</i>
	 */
	@Override
	public void translate(Vector3f velocity) {

		Movable.super.translate(velocity);
		this.calcViewMatrix();
	}
	
	/**
	 * <br><i>Calling this method will recalculate the view matrix</i>
	 */
	@Override
	public void setTranslation(float x, float y, float z) {

		Movable.super.setTranslation(x, y, z);
		this.calcViewMatrix();
	}
	
	/**
	 * <br><i>Calling this method will recalculate the view matrix</i>
	 */
	@Override
	public void setTranslation(Vector3f newTranslation) {

		Movable.super.setTranslation(newTranslation);
		this.calcViewMatrix();
	}
	
	/**
	 * <br><i>Calling this method will recalculate the view matrix</i>
	 */
	@Override
	public void rotate(float x, float y, float z) {

		Movable.super.rotate(x, y, z);
		this.calcViewMatrix();
	}
	
	/**
	 * <br><i>Calling this method will recalculate the view matrix</i>
	 */
	@Override
	public void rotate(Vector3f velocity) {

		Movable.super.rotate(velocity);
		this.calcViewMatrix();
	}
	
	/**
	 * <br><i>Calling this method will recalculate the view matrix</i>
	 */
	@Override
	public void setRotation(float x, float y, float z) {

		Movable.super.setRotation(x, y, z);
		this.calcViewMatrix();
	}
	
	/**
	 * <br><i>Calling this method will recalculate the view matrix</i>
	 */
	@Override
	public void setRotation(Vector3f newRotation) {

		Movable.super.setRotation(newRotation);
		this.calcViewMatrix();
	}
	
	/**
	 * Calculates the projection matrix.
	 * The projection matrix is used to project the 3D scene onto a 2D display.
	 * @since 11.08.2018/0.1.0
	 * @see <a href="http://www.songho.ca/opengl/gl_projectionmatrix.html">http://www.songho.ca/opengl/gl_projectionmatrix.html</a>
	 */
	private void calcProjectionMatrix() {

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
	 * Calculates the view matrix.
	 * In a video game you don't move the camera, you move the entire scene.
	 * The view matrix is used to determine how it has to be moved.
	 * @since 11.08.2018/0.1.0
	 */
	private void calcViewMatrix() {
		
		this.view = new Matrix4f();
		this.view.setIdentity(); // Doesn't work without this line
		MatrixUtil.rotate((float)Math.toRadians(this.rotation.x), Engine.AXIS_X, this.view);
		MatrixUtil.rotate((float)Math.toRadians(this.rotation.y), Engine.AXIS_Y, this.view);
		MatrixUtil.rotate((float)Math.toRadians(this.rotation.z), Engine.AXIS_Z, this.view);
		Vector3f negativeTranslation = new Vector3f(-this.translation.x, -this.translation.y, -this.translation.z);
		MatrixUtil.translate(negativeTranslation, this.view);
	}
	
	/**
	 * Calls {@link #calcProjectionMatrix()} and {@link #calcViewMatrix()}.
	 * @since 11.08.2018/0.1.0
	 */
	public void recalc() {
		
		this.calcProjectionMatrix();
		this.calcViewMatrix();
	}
	
	/**
	 * Sets the field of view.
	 * <br><i>Calling this method will recalculate the projection matrix</i>
	 * @param fov the field of view.
	 * @since 11.08.2018/0.1.0
	 */
	public void setFOV(float fov) {
		
		this.fov = fov;
		this.calcProjectionMatrix();
	}
	
	/**
	 * Sets the camera behavior.
	 * @param behavior the camera behavior
	 * @since 13.08.2018/0.1.0
	 */
	public void setBehavior(CameraBehavior behavior) {
		
		if(this.behavior != null) {
			
			this.behavior.setCamera(null);
		}
		
		this.behavior = behavior;
		
		if(this.behavior != null) {
			
			this.behavior.setCamera(this);
		}
	}
	
	/**
	 * Sets the near plane distance which determines how close and object can get before it won't be rendered anymore.
	 * <br><i>Calling this method will recalculate the projection matrix</i>
	 * @param nearPlaneDistance the near plane distance
	 * @since 11.08.2018/0.1.0
	 */
	public void setNearPlaneDistance(float nearPlaneDistance) {
		
		this.nearPlaneDistance = nearPlaneDistance;
		this.calcProjectionMatrix();
	}
	
	/**
	 * Sets the far plane distance which determines how far something can be away from the camera before it won't be rendered anymore.
	 * <br><i>Calling this method will recalculate the projection matrix</i>
	 * @param farPlaneDistance the far plane distance
	 * @since 11.08.2018/0.1.0
	 */
	public void setFarPlaneDistance(float farPlaneDistance) {
		
		this.farPlaneDistance = farPlaneDistance;
		this.calcProjectionMatrix();
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
	 * @return the field of view
	 * @since 11.08.2018/0.1.0
	 */
	public float getFOV() {
		
		return this.fov;
	}
	
	/**
	 * @return the far plane distance
	 * @since 11.08.2018/0.1.0
	 */
	public float getNearPlaneDistance() {
		
		return this.nearPlaneDistance;
	}
	
	/**
	 * @return the far plane distance
	 * @since 11.08.2018/0.1.0
	 */
	public float getFarPlaneDistance() {
		
		return this.farPlaneDistance;
	}
	
	/**
	 * The object returned by this method is not meant to be modified by anything outside of this class.
	 * @return the current projection matrix
	 * @since 11.08.2018/0.1.0
	 */
	public Matrix4f getProjectionMatrix() {
		
		return this.projection;
	}
	
	/**
	 * The object returned by this method is not meant to be modified by anything outside of this class.
	 * @return the current view matrix
	 * @since 11.08.2018/0.1.0
	 */
	public Matrix4f getViewMatrix() {
		
		return this.view;
	}
	
	/**
	 * @return the camera behavior or {@code null} if this camera has no behavior
	 * @since 13.08.2018/0.1.0
	 */
	public CameraBehavior getBehavior() {
		
		return this.behavior;
	}
	
	/**
	 * @return the {@linkplain Game} instance this camera belongs to
	 * @since 13.08.2018/0.1.0
	 */
	public Game getGame() {
		
		return this.game;
	}
}
