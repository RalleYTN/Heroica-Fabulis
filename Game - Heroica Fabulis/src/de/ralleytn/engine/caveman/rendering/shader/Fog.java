package de.ralleytn.engine.caveman.rendering.shader;

import javax.vecmath.Color3f;

/**
 * Container for values that decide how "foggy" objects become when the distance between the camera and the object increases/decreases.
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 25.08.2018/0.3.0
 * @since 25.08.2018/0.3.0
 */
public class Fog {

	private static final String UNIFORM_GRADIENT = "fogGradient";
	private static final String UNIFORM_DENSITY = "fogDensity";
	private static final String UNIFORM_COLOR = "fogColor";
	
	private float gradient;
	private float density;
	private Color3f color;
	private boolean changed;
	
	/**
	 * @since 25.08.2018/0.3.0
	 */
	public Fog() {
		
		this.gradient = 1.5F;
		this.density = 0.007F;
		this.color = new Color3f(1.0F, 1.0F, 1.0F);
	}
	
	/**
	 * Sets the gradient.
	 * @param gradient the gradient
	 * @since 25.08.2018/0.3.0
	 */
	public void setGradient(float gradient) {
		
		this.gradient = gradient;
		this.changed = true;
	}
	
	/**
	 * Sets the density.
	 * @param density the density
	 * @since 25.08.2018/0.3.0
	 */
	public void setDensity(float density) {
		
		this.density = density;
		this.changed = true;
	}
	
	/**
	 * Sets the color.
	 * @param color the color
	 * @since 25.08.2018/0.3.0
	 */
	public void setColor(Color3f color) {
		
		this.color.set(color);
		this.changed = true;
	}
	
	/**
	 * @return the gradient
	 * @since 25.08.2018/0.3.0
	 */
	public float getGradient() {
		
		return this.gradient;
	}
	
	/**
	 * @return the density
	 * @since 25.08.2018/0.3.0
	 */
	public float getDensity() {
		
		return this.density;
	}
	
	/**
	 * @return the color
	 * @since 25.08.2018/0.3.0
	 */
	public Color3f getColor() {
		
		return this.color;
	}
	
	/**
	 * @return {@code true} if one of the values was changed since they were applied the last time, else {@code true}
	 * @since 25.08.2018/0.3.0
	 */
	public boolean hasChanged() {
		
		return changed;
	}
	
	/**
	 * Applies the fog to a shader pipeline.
	 * @param pipeline the shader pipeline.
	 * @since 25.08.2018/0.3.0
	 */
	public void applyToShader(ShaderPipeline pipeline) {
		
		pipeline.setUniform(UNIFORM_DENSITY, this.density);
		pipeline.setUniform(UNIFORM_GRADIENT, this.gradient);
		pipeline.setUniform(UNIFORM_COLOR, this.color);
		
		this.changed = false;
	}
}
