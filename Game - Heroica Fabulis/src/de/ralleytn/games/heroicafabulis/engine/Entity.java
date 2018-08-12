package de.ralleytn.games.heroicafabulis.engine;

import javax.vecmath.Matrix4f;
import javax.vecmath.Vector3f;

import de.ralleytn.games.heroicafabulis.engine.rendering.Material;
import de.ralleytn.games.heroicafabulis.engine.rendering.Mesh;
import de.ralleytn.games.heroicafabulis.engine.rendering.ShaderPipeline;
import de.ralleytn.games.heroicafabulis.engine.util.MatrixUtil;

/**
 * 
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 12.08.2018/0.1.0
 * @since 30.07.2018/0.1.0
 */
public class Entity implements Translatable, Rotatable, Scalable, Updatable {
	
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
		this.recalcTransformationMatrix();
	}
	
	/**
	 * 
	 * @param shaderPipeline
	 * @since 12.08.2018/0.1.0
	 */
	public void setShaderPipeline(ShaderPipeline shaderPipeline) {
		
		this.shaderPipeline = shaderPipeline;
	}
	
	/**
	 * 
	 * @param rendering
	 * @since 30.07.2018/0.1.0
	 */
	public void setRendering(boolean rendering) {
		
		this.rendering = rendering;
	}
	
	@Override
	public void setRotation(float x, float y, float z) {

		Rotatable.super.setRotation(x, y, z);
		this.recalcTransformationMatrix();
	}
	
	@Override
	public void setRotation(Vector3f newRotation) {

		Rotatable.super.setRotation(newRotation);
		this.recalcTransformationMatrix();
	}
	
	@Override
	public void setScale(float newScale) {

		Scalable.super.setScale(newScale);
		this.recalcTransformationMatrix();
	}
	
	@Override
	public void setScale(float x, float y, float z) {

		Scalable.super.setScale(x, y, z);
		this.recalcTransformationMatrix();
	}
	
	@Override
	public void setScale(Vector3f newScale) {

		Scalable.super.setScale(newScale);
		this.recalcTransformationMatrix();
	}
	
	@Override
	public void setTranslation(float x, float y, float z) {

		Translatable.super.setTranslation(x, y, z);
		this.recalcTransformationMatrix();
	}
	
	@Override
	public void setTranslation(Vector3f newTranslation) {

		Translatable.super.setTranslation(newTranslation);
		this.recalcTransformationMatrix();
	}
	
	@Override
	public void scale(float factor) {

		Scalable.super.scale(factor);
		this.recalcTransformationMatrix();
	}
	
	@Override
	public void scale(float x, float y, float z) {

		Scalable.super.scale(x, y, z);
		this.recalcTransformationMatrix();
	}
	
	@Override
	public void scale(Vector3f factor) {

		Scalable.super.scale(factor);
		this.recalcTransformationMatrix();
	}
	
	@Override
	public void rotate(float x, float y, float z) {

		Rotatable.super.rotate(x, y, z);
		this.recalcTransformationMatrix();
	}
	
	@Override
	public void rotate(Vector3f velocity) {

		Rotatable.super.rotate(velocity);
		this.recalcTransformationMatrix();
	}
	
	@Override
	public void translate(float x, float y, float z) {

		Translatable.super.translate(x, y, z);
		this.recalcTransformationMatrix();
	}
	
	@Override
	public void translate(Vector3f velocity) {

		Translatable.super.translate(velocity);
		this.recalcTransformationMatrix();
	}
	
	/**
	 * @since 10.08.2018/0.1.0
	 */
	private final void recalcTransformationMatrix() {
		
		this.transformation.setIdentity();
		MatrixUtil.translate(this.translation, this.transformation);
		MatrixUtil.rotate((float)Math.toRadians(this.rotation.x), Engine.AXIS_X, this.transformation);
		MatrixUtil.rotate((float)Math.toRadians(this.rotation.y), Engine.AXIS_Y, this.transformation);
		MatrixUtil.rotate((float)Math.toRadians(this.rotation.z), Engine.AXIS_Z, this.transformation);
		MatrixUtil.scale(this.scale, this.transformation);
	}
	
	/**
	 * 
	 * @param mesh
	 * @since 10.08.2018/0.1.0
	 */
	public void setMesh(Mesh mesh) {
		
		if(this.mesh != null) {
			
			this.mesh.dispose();
		}
		
		this.mesh = mesh;
	}
	
	/**
	 * 
	 * @param material
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
	 * 
	 * @return
	 * @since 30.07.2018/0.1.0
	 */
	public Matrix4f getTransformationMatrix() {
		
		return this.transformation;
	}
	
	/**
	 * 
	 * @return
	 * @since 10.08.2018/0.1.0
	 */
	public Mesh getMesh() {
		
		return this.mesh;
	}
	
	/**
	 * 
	 * @return
	 * @since 10.08.2018/0.1.0
	 */
	public Material getMaterial() {
		
		return this.material;
	}
	
	/**
	 * 
	 * @return
	 * @since 10.08.2018/0.1.0
	 */
	public boolean isRendering() {
		
		return this.rendering;
	}
	
	/**
	 * 
	 * @return
	 * @since 12.08.2018/0.1.0
	 */
	public ShaderPipeline getShaderPipeline() {
		
		return this.shaderPipeline;
	}

	@Override
	public void update(float delta) {}
}
