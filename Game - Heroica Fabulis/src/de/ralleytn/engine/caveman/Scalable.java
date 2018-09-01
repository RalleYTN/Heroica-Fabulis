package de.ralleytn.engine.caveman;

import javax.vecmath.Vector3f;

/**
 * Interface that should be implemented by every class that represents something with a scale vector.
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 15.08.2018/0.1.0
 * @since 30.07.2018/0.1.0
 */
public interface Scalable {

	/**
	 * Scales the object by the given vector.
	 * @param units vector containing the information by how much the object has to be scaled for each axis
	 * @since 30.07.2018/0.1.0
	 */
	public default void scale(Vector3f units) {
		
		Vector3f scale = this.getScale();
		scale.x += units.x;
		scale.y += units.y;
		scale.z += units.z;
	}

	/**
	 * Scales the object by the given values.
	 * @param x units to be scaled on the X axis
	 * @param y units to be scaled on the Y axis
	 * @param z units to be scaled on the Z axis
	 * @since 30.07.2018/0.1.0
	 */
	public default void scale(float x, float y, float z) {
		
		Vector3f scale = this.getScale();
		scale.x *= x;
		scale.y *= y;
		scale.z *= z;
	}

	/**
	 * Sets the scale.
	 * @param newScale the new scale
	 * @since 30.07.2018/0.1.0
	 */
	public default void setScale(Vector3f newScale) {
		
		this.getScale().set(newScale);
	}

	/**
	 * Sets the scale.
	 * @param x X component of the new scale vector
	 * @param y Y component of the new scale vector
	 * @param z Z component of the new scale vector
	 * @since 30.07.2018/0.1.0
	 */
	public default void setScale(float x, float y, float z) {
		
		this.getScale().set(x, y, z);
	}

	/**
	 * @return the scale vector
	 * @since 30.07.2018/0.1.0
	 */
	public Vector3f getScale();
}
