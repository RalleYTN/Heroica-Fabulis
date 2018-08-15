package de.ralleytn.games.heroicafabulis.engine;

import javax.vecmath.Vector3f;

/**
 * Interface that extends {@linkplain Translatable} and {@linkplain Rotatable} and adds translation based on rotation.
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 14.08.2018/0.1.0
 * @since 14.08.2018/0.1.0
 */
public interface Movable extends Translatable, Rotatable {

	// TODO: I would really like if up and down would be actually up and down relative to the object but I have no idea how to do it
	
	/**
	 * Performs a translation into the given direction relative to the rotation.
	 * @param direction the direction
	 * @param units the amount of translation
	 * @since 14.08.2018/0.1.0
	 */
	public default void move(Direction direction, float units) {
		
		float nUnits = -units;
		Vector3f rotation = this.getRotation();
		
		// If-Else is faster than Switch-Case
		// And since this is called over and over again in the main game loop we need the speed.
		if(direction == Direction.FORWARD) {
			
			float rY = (float)Math.toRadians(rotation.y - 90.0F);
			float x = (float)Math.cos(rY) * units;
			float z = (float)Math.sin(rY) * units;
			
			this.translate(x, 0.0F, z);
			
		} else if(direction == Direction.LEFT) {
		 
			float rY = (float)Math.toRadians(rotation.y);
			float x = (float)Math.cos(rY) * nUnits;
			float z = (float)Math.sin(rY) * nUnits;
			
			this.translate(x, 0.0F, z);
			
		} else if(direction == Direction.RIGHT) {
			
			float rY = (float)Math.toRadians(rotation.y);
			float x = (float)Math.cos(rY) * units;
			float z = (float)Math.sin(rY) * units;
			
			this.translate(x, 0.0F, z);
			
		} else if(direction == Direction.UP) {
			
			this.translate(0.0F, units, 0.0F);
			
		} else if(direction == Direction.DOWN) {
			
			this.translate(0.0F, nUnits, 0.0F);
			
		} else if(direction == Direction.BACKWARD) {
			
			float rY = (float)Math.toRadians(rotation.y - 90.0F);
			float x = (float)Math.cos(rY) * nUnits;
			float z = (float)Math.sin(rY) * nUnits;
			
			this.translate(x, 0.0F, z);
		}
	}
}
