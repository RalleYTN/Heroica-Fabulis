package de.ralleytn.games.heroicafabulis.engine;

import javax.vecmath.Matrix4f;

import de.ralleytn.games.heroicafabulis.engine.rendering.geom.Mesh;
import de.ralleytn.games.heroicafabulis.engine.rendering.shader.Material;
import de.ralleytn.games.heroicafabulis.engine.rendering.shader.ShaderPipeline;

/**
 * Represents a very abstract object on the scene that can be rendered.
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 22.08.2018/0.2.0
 * @since 22.08.2018/0.2.0
 */
public abstract class RenderableObject {

	protected Mesh mesh;
	protected Material material;
	protected ShaderPipeline shaderPipeline;
	protected Matrix4f transformation;
	protected boolean rendering;
	
	/**
	 * Calculates the transformation matrix.
	 * The transformation matrix contains the translation, rotation and the scale of an entity and is used to render the object with all of these values.
	 * @since 22.08.2018/0.2.0
	 */
	protected abstract void calcTransformationMatrix();
	
	/**
	 * Enables or disables rendering for this object.
	 * @param rendering {@code true} to enable rendering, {@code false} to disable it
	 * @since 22.08.2018/0.2.0
	 */
	public void setRendering(boolean rendering) {
		
		this.rendering = rendering;
	}

	/**
	 * Sets the shader pipeline.
	 * @param shaderPipeline the shader pipeline
	 * @since 22.08.2018/0.2.0
	 */
	public void setShaderPipeline(ShaderPipeline shaderPipeline) {
		
		this.shaderPipeline = shaderPipeline;
	}

	/**
	 * Sets the material.
	 * @param material the material
	 * @since 22.08.2018/0.2.0
	 */
	public void setMaterial(Material material) {
		
		this.material = material;
	}

	/**
	 * @return the mesh
	 * @since 22.08.2018/0.2.0
	 */
	public Mesh getMesh() {
		
		return this.mesh;
	}

	/**
	 * @return the transformation matrix
	 * @since 22.08.2018/0.2.0
	 */
	public Matrix4f getTransformationMatrix() {
		
		return this.transformation;
	}

	/**
	 * @return the material
	 * @since 22.08.2018/0.2.0
	 */
	public Material getMaterial() {
		
		return this.material;
	}

	/**
	 * @return the shader pipeline
	 * @since 22.08.2018/0.2.0
	 */
	public ShaderPipeline getShaderPipeline() {
		
		return this.shaderPipeline;
	}
	
	/**
	 * @return {@code true} if this entity is rendering, else {@code false}
	 * @since 22.08.2018/0.2.0
	 */
	public boolean isRendering() {
		
		return this.rendering;
	}
}
