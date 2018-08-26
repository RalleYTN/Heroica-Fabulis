package de.ralleytn.games.heroicafabulis.engine;

import javax.vecmath.Matrix4f;
import javax.vecmath.Vector3f;

import de.ralleytn.games.heroicafabulis.engine.rendering.geom.Mesh;
import de.ralleytn.games.heroicafabulis.engine.util.MatrixUtil;

/**
 * Represents an entity. An entity is an object on the scene.
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 26.08.2018/0.3.0
 * @since 30.07.2018/0.1.0
 */
public class Entity extends RenderableObject implements Movable, Scalable, Updatable, Copyable<Entity> {
	
	private Vector3f translation;
	private Vector3f rotation;
	private Vector3f scale;
	private Mesh mesh;
	private float renderDistance;
	
	/**
	 * @since 30.07.2018/0.1.0
	 */
	public Entity() {
		
		this.translation = new Vector3f();
		this.rotation = new Vector3f();
		this.scale = new Vector3f(1.0F, 1.0F, 1.0F);
		this.transformation = new Matrix4f();
		this.rendering = true;
		this.renderDistance = 1000.0F;
		this.calcTransformationMatrix();
	}
	
	/**
	 * 
	 * @param renderDistance
	 * @since 26.08.2018/0.3.0
	 */
	public void setRenderDistance(float renderDistance) {
		
		this.renderDistance = renderDistance;
	}
	
	/**
	 * <br><i>Calling this method will recalculate the transformation matrix</i>
	 */
	@Override
	public void setRotation(float x, float y, float z) {

		Movable.super.setRotation(x, y, z);
		this.calcTransformationMatrix();
	}
	
	/**
	 * <br><i>Calling this method will recalculate the transformation matrix</i>
	 */
	@Override
	public void setRotation(Vector3f newRotation) {

		Movable.super.setRotation(newRotation);
		this.calcTransformationMatrix();
	}
	
	/**
	 * <br><i>Calling this method will recalculate the transformation matrix</i>
	 */
	@Override
	public void setScale(float x, float y, float z) {

		Scalable.super.setScale(x, y, z);
		this.calcTransformationMatrix();
	}
	
	/**
	 * <br><i>Calling this method will recalculate the transformation matrix</i>
	 */
	@Override
	public void setScale(Vector3f newScale) {

		Scalable.super.setScale(newScale);
		this.calcTransformationMatrix();
	}
	
	/**
	 * <br><i>Calling this method will recalculate the transformation matrix</i>
	 */
	@Override
	public void setTranslation(float x, float y, float z) {

		Movable.super.setTranslation(x, y, z);
		this.calcTransformationMatrix();
	}
	
	/**
	 * <br><i>Calling this method will recalculate the transformation matrix</i>
	 */
	@Override
	public void setTranslation(Vector3f newTranslation) {

		Movable.super.setTranslation(newTranslation);
		this.calcTransformationMatrix();
	}
	
	/**
	 * <br><i>Calling this method will recalculate the transformation matrix</i>
	 */
	@Override
	public void scale(float x, float y, float z) {

		Scalable.super.scale(x, y, z);
		this.calcTransformationMatrix();
	}
	
	/**
	 * <br><i>Calling this method will recalculate the transformation matrix</i>
	 */
	@Override
	public void scale(Vector3f factor) {

		Scalable.super.scale(factor);
		this.calcTransformationMatrix();
	}
	
	/**
	 * <br><i>Calling this method will recalculate the transformation matrix</i>
	 */
	@Override
	public void rotate(float x, float y, float z) {

		Movable.super.rotate(x, y, z);
		this.calcTransformationMatrix();
	}
	
	/**
	 * <br><i>Calling this method will recalculate the transformation matrix</i>
	 */
	@Override
	public void rotate(Vector3f velocity) {

		Movable.super.rotate(velocity);
		this.calcTransformationMatrix();
	}
	
	/**
	 * <br><i>Calling this method will recalculate the transformation matrix</i>
	 */
	@Override
	public void translate(float x, float y, float z) {

		Movable.super.translate(x, y, z);
		this.calcTransformationMatrix();
	}
	
	/**
	 * <br><i>Calling this method will recalculate the transformation matrix</i>
	 */
	@Override
	public void translate(Vector3f velocity) {

		Movable.super.translate(velocity);
		this.calcTransformationMatrix();
	}
	
	@Override
	protected final void calcTransformationMatrix() {
		
		this.transformation.setIdentity();
		MatrixUtil.translate(this.translation, this.transformation);
		MatrixUtil.rotate((float)Math.toRadians(this.rotation.x), Engine.AXIS_X, this.transformation);
		MatrixUtil.rotate((float)Math.toRadians(this.rotation.y), Engine.AXIS_Y, this.transformation);
		MatrixUtil.rotate((float)Math.toRadians(this.rotation.z), Engine.AXIS_Z, this.transformation);
		MatrixUtil.scale(this.scale, this.transformation);
	}
	
	/**
	 * Sets the mesh that this entity should use.
	 * @param mesh the mesh
	 * @since 10.08.2018/0.1.0
	 */
	public void setMesh(Mesh mesh) {
		
		if(this.mesh != null) {
			
			this.mesh.dispose();
		}
		
		this.mesh = mesh;
	}

	@Override
	public Vector3f getTranslation() {
		
		return this.translation;
	}

	@Override
	public Vector3f getRotation() {
		
		return this.rotation;
	}

	@Override
	public Vector3f getScale() {
		
		return this.scale;
	}
	
	/**
	 * @return the mesh
	 * @since 22.08.2018/0.2.0
	 */
	public Mesh getMesh() {
		
		return this.mesh;
	}

	@Override
	public void update(float delta) {}

	@Override
	public Entity copy() {
		
		Entity entity = new Entity();
		entity.material = this.material;
		entity.mesh = this.mesh;
		entity.rendering = this.rendering;
		entity.rotation = new Vector3f(this.rotation);
		entity.scale = new Vector3f(this.scale);
		entity.translation = new Vector3f(this.translation);
		entity.transformation = new Matrix4f(this.transformation);
		entity.shaderPipeline = this.shaderPipeline;
		
		return entity;
	}
	
	/**
	 * 
	 * @return
	 * @since 26.08.2018/0.3.0
	 */
	public float getRenderDistance() {
		
		return this.renderDistance;
	}
}
