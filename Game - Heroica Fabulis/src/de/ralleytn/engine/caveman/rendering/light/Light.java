package de.ralleytn.engine.caveman.rendering.light;

import javax.vecmath.Color3f;
import javax.vecmath.Vector3f;

import de.ralleytn.engine.caveman.Translatable;

/**
 * Represents a basic positional light with infinite range.
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 11.08.2018/0.1.0
 * @since 11.08.2018/0.1.0
 */
public class Light implements Translatable {

	private Vector3f translation;
	private Color3f color;
	
	/**
	 * @since 11.08.2018/0.1.0
	 */
	public Light() {
		
		this.translation = new Vector3f();
		this.color = new Color3f(1.0F, 1.0F, 1.0F);
	}
	
	/**
	 * Sets the color of the light.
	 * @param color the color
	 * @since 11.08.2018/0.1.0
	 */
	public void setColor(Color3f color) {
		
		this.color.set(color);
	}
	
	/**
	 * @return the color of the light
	 * @since 11.08.2018/0.1.0
	 */
	public Color3f getColor() {
		
		return this.color;
	}
	
	@Override
	public Vector3f getTranslation() {

		return this.translation;
	}
}
