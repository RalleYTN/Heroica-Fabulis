package de.ralleytn.games.heroicafabulis.engine;

import javax.vecmath.Vector3f;

/**
 * 
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 30.07.2018/0.1.0
 * @since 30.07.2018/0.1.0
 */
public interface Rotatable {

	/**
	 * 
	 * @param newRotation
	 * @since 30.07.2018/0.1.0
	 */
	public default void setRotation(Vector3f newRotation) {
		
		this.getRotation().set(newRotation);
	}

	/**
	 * 
	 * @param x
	 * @param y
	 * @param z
	 * @since 30.07.2018/0.1.0
	 */
	public default void setRotation(float x, float y, float z) {
		
		Vector3f rotation = this.getRotation();
		rotation.x = x;
		rotation.y = y;
		rotation.z = z;
	}

	/**
	 * 
	 * @param velocity
	 * @since 30.07.2018/0.1.0
	 */
	public default void rotate(Vector3f velocity) {
		
		this.getRotation().add(velocity);
	}

	/**
	 * 
	 * @param x
	 * @param y
	 * @param z
	 * @since 30.07.2018/0.1.0
	 */
	public default void rotate(float x, float y, float z) {
		
		Vector3f rotation = this.getRotation();
		rotation.x += x;
		rotation.y += y;
		rotation.z += z;
	}
	
	/**
	 * 
	 * @return
	 * @since 30.07.2018/0.1.0
	 */
	public Vector3f getRotation();

	/**
	 * 
	 * @return
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
