package de.ralleytn.engine.caveman.procgen;

import java.util.ArrayList;
import java.util.List;

import javax.vecmath.Matrix4f;

import de.ralleytn.engine.caveman.Entity3D;

/**
 * 
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 04.09.2018/0.4.0
 * @since 01.09.2018/0.4.0
 */
public class GridCell extends Entity3D {

	protected final boolean[][][] layout;
	protected final Type type;
	protected final List<GridCellOpening> openings;
	
	/**
	 * 
	 * @param layout
	 * @param type
	 * @since 01.09.2018/0.4.0
	 */
	public GridCell(boolean[][][] layout, Type type) {
		
		super();
		
		this.openings = new ArrayList<>();
		this.layout = layout;
		this.type = type;
	}
	
	/**
	 * 
	 * @param opening
	 * @since 04.09.2018/0.4.0
	 */
	public void addOpening(GridCellOpening opening) {
		
		this.openings.add(opening);
	}
	
	/**
	 * 
	 * @param cell
	 * @return
	 * @since 01.09.2018/0.4.0
	 */
	public boolean intersects(GridCell cell) {
		
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
	
	/**
	 * 
	 * @return
	 * @since 04.09.2018/0.4.0
	 */
	public List<GridCellOpening> getOpenings() {
		
		return this.openings;
	}
	
	/**
	 * 
	 * @since 04.09.2018/0.4.0
	 */
	public GridCell copy() {
		
		boolean[][][] layout = new boolean[this.layout.length][this.layout[0].length][this.layout[0][0].length];
		
		for(int x = 0; x < this.getWidth(); x++) {
			
			for(int y = 0; y < this.getHeight(); y++) {
				
				for(int z = 0; z < this.getDepth(); z++) {
					
					layout[x][y][z] = this.layout[x][y][z];
				}
			}
		}
		
		GridCell cell = new GridCell(layout, this.type);
		cell.material = this.material;
		cell.rendering = this.rendering;
		cell.shaderPipeline = this.shaderPipeline;
		cell.transformation = new Matrix4f(this.transformation);
		cell.setMesh(this.getMesh());
		cell.setRenderDistance(this.getRenderDistance());
		cell.setTranslation(this.getTranslation());
		cell.setRotation(this.getRotation());
		cell.setScale(this.getScale());
		
		return cell;
	}
	
	/**
	 * 
	 * @author Ralph Niemitz
	 * @version 04.09.2018/0.4.0
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
		CORRIDOR_X,
		
		/**
		 * @since 04.09.2018/0.4.0
		 */
		STAIRWAY_I,
		
		/**
		 * @since 04.09.2018/0.4.0
		 */
		STAIRWAY_T,
		
		/**
		 * @since 04.09.2018/0.4.0
		 */
		STAIRWAY_U
	}
}
