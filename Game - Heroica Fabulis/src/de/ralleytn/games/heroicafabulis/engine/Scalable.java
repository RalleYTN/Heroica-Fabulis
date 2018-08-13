package de.ralleytn.games.heroicafabulis.engine;

import javax.vecmath.Vector3f;

/**
 * 
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 30.07.2018
 * @since 30.07.2018
 */
public interface Scalable {

	/**
	 * 
	 * @param units
	 * @since 30.07.2018
	 */
	public default void scale(float units) {
		
		Vector3f scale = this.getScale();
		scale.x += units;
		scale.y += units;
		scale.z += units;
	}

	/**
	 * 
	 * @param units
	 * @since 30.07.2018
	 */
	public default void scale(Vector3f units) {
		
		Vector3f scale = this.getScale();
		scale.x += units.x;
		scale.y += units.y;
		scale.z += units.z;
	}

	/**
	 * 
	 * @param x
	 * @param y
	 * @param z
	 * @since 30.07.2018
	 */
	public default void scale(float x, float y, float z) {
		
		Vector3f scale = this.getScale();
		scale.x *= x;
		scale.y *= y;
		scale.z *= z;
	}

	/**
	 * 
	 * @param newScale
	 * @since 30.07.2018
	 */
	public default void setScale(float newScale) {
		
		this.getScale().set(newScale, newScale, newScale);
	}

	/**
	 * 
	 * @param newScale
	 * @since 30.07.2018
	 */
	public default void setScale(Vector3f newScale) {
		
		this.getScale().set(newScale);
	}

	/**
	 * 
	 * @param x
	 * @param y
	 * @param z
	 * @since 30.07.2018
	 */
	public default void setScale(float x, float y, float z) {
		
		this.getScale().set(x, y, z);
	}

	/**
	 * 
	 * @return
	 * @since 30.07.2018
	 */
	public Vector3f getScale();
}
