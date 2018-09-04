package de.ralleytn.engine.caveman.procgen;

import de.ralleytn.engine.caveman.Direction;

/**
 * 
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 04.09.2018/0.4.0
 * @since 04.09.2018/0.4.0
 */
public class GridCellOpening {

	private int x;
	private int y;
	private int z;
	private Direction direction;
	
	/**
	 * 
	 * @param x
	 * @param y
	 * @param z
	 * @param direction
	 * @since 04.09.2018/0.4.0
	 */
	public GridCellOpening(int x, int y, int z, Direction direction) {
		
		this.x = x;
		this.y = y;
		this.z = z;
		this.direction = direction;
	}
	
	/**
	 * 
	 * @return
	 * @since 04.09.2018/0.4.0
	 */
	public int getX() {
		
		return this.x;
	}
	
	/**
	 * 
	 * @return
	 * @since 04.09.2018/0.4.0
	 */
	public int getY() {
		
		return this.y;
	}
	
	/**
	 * 
	 * @return
	 * @since 04.09.2018/0.4.0
	 */
	public int getZ() {
		
		return this.z;
	}
	
	/**
	 * 
	 * @return
	 * @since 04.09.2018/0.4.0
	 */
	public Direction getDirection() {
		
		return this.direction;
	}
}
