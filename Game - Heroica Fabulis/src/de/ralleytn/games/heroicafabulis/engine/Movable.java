package de.ralleytn.games.heroicafabulis.engine;

import javax.vecmath.Vector3f;

/**
 * Interface that extends {@linkplain Translatable} and {@linkplain Rotatable} and adds translation based on rotation on the Y axis.
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 29.08.2018/0.3.0
 * @since 14.08.2018/0.1.0
 */
public interface Movable extends Translatable, Rotatable {

	/**
	 * Performs a translation into the given direction relative to the rotation on the Y axis.
	 * @param direction the direction
	 * @param units the amount of translation
	 * @since 14.08.2018/0.1.0
	 */
	public default void move(Direction direction, float units) {
		
		float nUnits = -units;
		Vector3f rotation = this.getRotation();
		
		float rY = 0.0F;
		float x = 0.0F;
		float z = 0.0F;
		float y = 0.0F;
		
		switch(direction) {
		
			case BACKWARD:
				rY = (float)Math.toRadians(rotation.y - 90.0F);
				x = (float)Math.cos(rY) * nUnits;
				z = (float)Math.sin(rY) * nUnits;
				break;
			
			case DOWN:
				y = nUnits;
				break;
			
			case FORWARD:
				rY = (float)Math.toRadians(rotation.y - 90.0F);
				x = (float)Math.cos(rY) * units;
				z = (float)Math.sin(rY) * units;
				break;
			
			case LEFT:
				rY = (float)Math.toRadians(rotation.y);
				x = (float)Math.cos(rY) * nUnits;
				z = (float)Math.sin(rY) * nUnits;
				break;
			
			case RIGHT:
				rY = (float)Math.toRadians(rotation.y);
				x = (float)Math.cos(rY) * units;
				z = (float)Math.sin(rY) * units;
				break;
			
			case UP:
				y = units;
		}
		
		this.translate(x, y, z);
	}
}
