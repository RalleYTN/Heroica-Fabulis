package de.ralleytn.engine.caveman.procgen;

import de.ralleytn.engine.caveman.Entity;

/**
 * 
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 04.09.2018/0.4.0
 * @since 01.09.2018/0.4.0
 */
public class Cell extends Entity {

	protected final boolean[][][] layout;
	protected final Type type;
	
	/**
	 * 
	 * @param layout
	 * @param type
	 * @since 01.09.2018/0.4.0
	 */
	public Cell(boolean[][][] layout, Type type) {
		
		this.layout = layout;
		this.type = type;
	}
	
	/**
	 * 
	 * @param cell
	 * @return
	 * @since 01.09.2018/0.4.0
	 */
	public boolean intersects(Cell cell) {
		
		int x = (int)this.getTranslation().x;
		int y = (int)this.getTranslation().y;
		int z = (int)this.getTranslation().z;
		
		int cx = (int)cell.getTranslation().x;
		int cy = (int)cell.getTranslation().y;
		int cz = (int)cell.getTranslation().z;
		
		return x + this.layout.length > cx &&
			   x < cx + cell.layout.length &&
			   y + this.layout[0].length > cy &&
			   y < cy + cell.layout[0].length &&
			   z + this.layout[0][0].length > cz &&
			   z < cz + cell.layout[0][0].length;
	}
	
	/**
	 * 
	 * @return
	 * @since 01.09.2018/0.4.0
	 */
	public int getWidth() {
		
		return this.layout.length;
	}
	
	/**
	 * 
	 * @return
	 * @since 01.09.2018/0.4.0
	 */
	public int getHeight() {
		
		return this.layout[0].length;
	}
	
	/**
	 * 
	 * @return
	 * @since 01.09.2018/0.4.0
	 */
	public int getDepth() {
		
		return this.layout[0][0].length;
	}
	
	/**
	 * 
	 * @return
	 * @since 01.09.2018/0.4.0
	 */
	public Type getType() {
		
		return this.type;
	}
	
	@Override
	public Entity copy() {
		
		boolean[][][] layout = new boolean[this.layout.length][this.layout[0].length][this.layout[0][0].length];
		
		for(int x = 0; x < this.getWidth(); x++) {
			
			for(int y = 0; y < this.getHeight(); y++) {
				
				for(int z = 0; z < this.getDepth(); z++) {
					
					layout[x][y][z] = this.layout[x][y][z];
				}
			}
		}
		
		Cell cell = new Cell(layout, this.type);
		// TODO
		
		return cell;
	}
	
	/**
	 * 
	 * @author Ralph Niemitz
	 * @version 01.09.2018/0.4.0
	 * @since 01.09.2018/0.4.0
	 */
	public static enum Type {
		
		/**
		 * @since 01.09.2018/0.4.0
		 */
		ROOM,
		
		/**
		 * @since 01.09.2018/0.4.0
		 */
		CORRIDOR_I,
		
		/**
		 * @since 01.09.2018/0.4.0
		 */
		CORRIDOR_T,
		
		/**
		 * @since 01.09.2018/0.4.0
		 */
		CORRIDOR_X
	}
}
