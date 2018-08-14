package de.ralleytn.games.heroicafabulis.engine;

import javax.vecmath.Matrix4f;
import javax.vecmath.Vector3f;

import de.ralleytn.games.heroicafabulis.engine.rendering.Material;
import de.ralleytn.games.heroicafabulis.engine.rendering.Mesh;
import de.ralleytn.games.heroicafabulis.engine.rendering.ShaderPipeline;
import de.ralleytn.games.heroicafabulis.engine.util.MatrixUtil;

/**
 * Represents an entity. An entity is an object on the scene.
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 14.08.2018/0.1.0
 * @since 30.07.2018/0.1.0
 */
public class Entity implements Movable, Scalable, Updatable {
	
	private Vector3f translation;
	private Vector3f rotation;
	private Vector3f scale;
	private Mesh mesh;
	private Material material;
	private Matrix4f transformation;
	private ShaderPipeline shaderPipeline;
	private boolean rendering;
	
	/**
	 * @since 30.07.2018/0.1.0
	 */
	public Entity() {
		
		this.translation = new Vector3f();
		this.rotation = new Vector3f();
		this.scale = new Vector3f(1.0F, 1.0F, 1.0F);
		this.transformation = new Matrix4f();
		this.rendering = true;
		this.calcTransformationMatrix();
	}
	
	/**
	 * Sets the shader pipeline which this entity should use.
	 * @param shaderPipeline the shader pipeline
	 * @since 12.08.2018/0.1.0
	 */
	public void setShaderPipeline(ShaderPipeline shaderPipeline) {
		
		this.shaderPipeline = shaderPipeline;
	}
	
	/**
	 * Enables or disables rendering for this entity.
	 * @param rendering {@code true} to enable rendering, {@code false} to disable it
	 * @since 30.07.2018/0.1.0
	 */
	public void setRendering(boolean rendering) {
		
		this.rendering = rendering;
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
	
	@Override
	public void setScale(float newScale) {

		Scalable.super.setScale(newScale);
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
	public void scale(float factor) {

		Scalable.super.scale(factor);
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
	
	/**
	 * Calculates the transformation matrix.
	 * The transformation matrix contains the translation, rotation and the scale of an entity and is used to render the entity with all of these values.
	 * @since 10.08.2018/0.1.0
	 */
	private final void calcTransformationMatrix() {
		
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
	
	/**
	 * Sets the material of this entity.
	 * The material determines how the shader will render the entity.
	 * @param material the material
	 * @since 10.08.2018/0.1.0
	 */
	public void setMaterial(Material material) {
		
		this.material = material;
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
	 * @return the current transformation matrix
	 * @since 30.07.2018/0.1.0
	 */
	public Matrix4f getTransformationMatrix() {
		
		return this.transformation;
	}
	
	/**
	 * @return the mesh that this entity uses
	 * @since 10.08.2018/0.1.0
	 */
	public Mesh getMesh() {
		
		return this.mesh;
	}
	
	/**
	 * @return the material
	 * @since 10.08.2018/0.1.0
	 */
	public Material getMaterial() {
		
		return this.material;
	}
	
	/**
	 * @return {@code true} if this entity is rendering, else {@code false}
	 * @since 10.08.2018/0.1.0
	 */
	public boolean isRendering() {
		
		return this.rendering;
	}
	
	/**
	 * @return the shader pipeline that this entity is using
	 * @since 12.08.2018/0.1.0
	 */
	public ShaderPipeline getShaderPipeline() {
		
		return this.shaderPipeline;
	}

	@Override
	public void update(float delta) {}
}
