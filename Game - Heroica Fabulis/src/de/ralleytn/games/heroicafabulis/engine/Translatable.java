 package de.ralleytn.games.heroicafabulis.engine;

import javax.vecmath.Vector3f;

/**
 * This interface is supposed to be implemented by classes that represent objects that can be moved in 3D space.
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 30.07.2018/0.1.0
 * @since 30.07.2018/0.1.0
 */
public interface Translatable {

	/**
	 * Sets the translation.
	 * @param newTranslation the new translation
	 * @since 30.07.2018/0.1.0
	 */
	public default void setTranslation(Vector3f newTranslation) {
		
		this.getTranslation().set(newTranslation);
	}
	
	/**
	 * Sets the translation.
	 * @param x translation on the X axis (left, right)
	 * @param y translation on the Y axis (up, down)
	 * @param z translation on the Z axis (forward, backward)
	 * @since 30.07.2018/0.1.0
	 */
	public default void setTranslation(float x, float y, float z) {
		
		Vector3f translation = this.getTranslation();
		translation.x = x;
		translation.y = y;
		translation.z = z;
	}
	
	/**
	 * Moves the object by the given velocity.
	 * @param velocity the velocity
	 * @since 30.07.2018/0.1.0
	 */
	public default void translate(Vector3f velocity) {
		
		this.getTranslation().add(velocity);
	}
	
	/**
	 * Moves the object by the given velocity.
	 * @param xVelocity velocity on the X axis (left, right)
	 * @param yVelocity velocity on the Y axis (up, down)
	 * @param zVelocity velocity on the Z axis (forward, backward)
	 * @since 30.07.2018/0.1.0
	 */
	public default void translate(float xVelocity, float yVelocity, float zVelocity) {
		
		Vector3f translation = this.getTranslation();
		translation.x += xVelocity;
		translation.y += yVelocity;
		translation.z += zVelocity;
	}
	
	/**
	 * @return the current translation vector
	 * @since 30.07.2018/0.1.0
	 */
	public Vector3f getTranslation();
}
