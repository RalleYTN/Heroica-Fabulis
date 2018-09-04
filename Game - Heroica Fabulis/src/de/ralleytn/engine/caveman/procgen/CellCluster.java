package de.ralleytn.engine.caveman.procgen;

/**
 * 
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 04.09.2018/0.4.0
 * @since 01.09.2018/0.4.0
 */
public class CellCluster {

	private final Cell[] cells;
	private final int width;
	private final int height;
	private final int depth;
	private final int x;
	private final int y;
	private final int z;
	
	/**
	 * 
	 * @param cell
	 * @since 01.09.2018/0.4.0
	 */
	public CellCluster(Cell cell) {
		
		this.cells = new Cell[] {cell};
		
		this.width = cell.getWidth();
		this.height = cell.getHeight();
		this.depth = cell.getDepth();
		
		this.x = (int)cell.getTranslation().x;
		this.y = (int)cell.getTranslation().y;
		this.z = (int)cell.getTranslation().z;
	}
	
	/**
	 * 
	 * @param cells
	 * @since 01.09.2018/0.4.0
	 */
	public CellCluster(Cell[] cells) {
		
		this.cells = cells;
		
		this.width = 0;
		this.height = 0;
		this.depth = 0;
		
		this.x = 0;
		this.y = 0;
		this.z = 0;
	}
	
	public boolean intersects(CellCluster cluster) {
		
		return false;
	}
	
	/**
	 * 
	 * @return
	 * @since 01.09.2018/0.4.0
	 */
	public Cell[] getCells() {
		
		return this.cells;
	}
	
	/**
	 * 
	 * @return
	 * @since 01.09.2018/0.4.0
	 */
	public int getWidth() {
		
		return this.width;
	}
	
	/**
	 * 
	 * @return
	 * @since 01.09.2018/0.4.0
	 */
	public int getHeight() {
		
		return this.height;
	}
	
	/**
	 * 
	 * @return
	 * @since 01.09.2018/0.4.0
	 */
	public int getDepth() {
		
		return this.depth;
	}
	
	/**
	 * 
	 * @return
	 * @since 01.09.2018/0.4.0
	 */
	public int getX() {
		
		return this.x;
	}
	
	/**
	 * 
	 * @return
	 * @since 01.09.2018/0.4.0
	 */
	public int getY() {
		
		return this.y;
	}
	
	/**
	 * 
	 * @return
	 * @since 01.09.2018/0.4.0
	 */
	public int getZ() {
		
		return this.z;
	}
	
	/**
	 * 
	 * @return
	 * @since 04.09.2018/0.4.0
	 */
	public CellCluster copy() {
		
		Cell[] cells = new Cell[this.cells.length];
		
		for(int index = 0; index < cells.length; index++) {
			
			cells[index] = this.cells[index].copy();
		}
		
		CellCluster cluster = new CellCluster(cells);
		return cluster;
	}
}
