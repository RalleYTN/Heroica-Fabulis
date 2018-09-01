package de.ralleytn.engine.caveman;

import javax.vecmath.Vector3f;

/**
 * Interface that should be implemented by every class that represents something with a rotation vector.
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 30.07.2018/0.1.0
 * @since 30.07.2018/0.1.0
 */
public interface Rotatable {

	/**
	 * Sets the rotation.
	 * @param newRotation the new rotation
	 * @since 30.07.2018/0.1.0
	 */
	public default void setRotation(Vector3f newRotation) {
		
		this.getRotation().set(newRotation);
	}

	/**
	 * Sets the rotation.
	 * @param x X component of the new rotation
	 * @param y Y component of the new rotation
	 * @param z Z component of the new rotation
	 * @since 30.07.2018/0.1.0
	 */
	public default void setRotation(float x, float y, float z) {
		
		Vector3f rotation = this.getRotation();
		rotation.x = x;
		rotation.y = y;
		rotation.z = z;
	}

	/**
	 * Rotates the object.
	 * @param velocity the velocity by which the object should be rotated
	 * @since 30.07.2018/0.1.0
	 */
	public default void rotate(Vector3f velocity) {
		
		this.getRotation().add(velocity);
	}

	/**
	 * Rotates the object.
	 * @param x X component of the velocity by which the object should be rotated
	 * @param y Y component of the velocity by which the object should be rotated
	 * @param z Z component of the velocity by which the object should be rotated
	 * @since 30.07.2018/0.1.0
	 */
	public default void rotate(float x, float y, float z) {
		
		Vector3f rotation = this.getRotation();
		rotation.x += x;
		rotation.y += y;
		rotation.z += z;
	}
	
	/**
	 * @return the rotation vector.
	 * @since 30.07.2018/0.1.0
	 */
	public Vector3f getRotation();

	/**
	 * Calculates a normalized rotation vector in which no value can be smaller than {@code 0.0F} or greater than {@code 360.0F}.
	 * @return the normalized rotation vector
	 * @since 30.07.2018/0.1.0
	 */
	public default Vector3f getNormalizedRotation() {
		
		Vector3f normalized = new Vector3f(this.getRotation());
		normalized.x %= 360.0F;
		normalized.y %= 360.0F;
		normalized.z %= 360.0F;
		
		if(normalized.x < 0.0F) normalized.x += 360.0F;
		if(normalized.y < 0.0F) normalized.y += 360.0F;
		if(normalized.z < 0.0F) normalized.z += 360.0F;
		
		return normalized;
	}
}
