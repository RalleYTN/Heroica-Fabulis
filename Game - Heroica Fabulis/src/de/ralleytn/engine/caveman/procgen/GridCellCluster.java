package de.ralleytn.engine.caveman.procgen;

/**
 * 
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 04.09.2018/0.4.0
 * @since 01.09.2018/0.4.0
 */
public class GridCellCluster {

	private final GridCell[] cells;
	private final int width;
	private final int height;
	private final int depth;
	private final int x;
	private final int y;
	private final int z;
	private final GridCell.Type type;
	
	/**
	 * 
	 * @param cell
	 * @since 01.09.2018/0.4.0
	 */
	public GridCellCluster(GridCell cell) {
		
		this.cells = new GridCell[] {cell};
		this.type = cell.getType();
		
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
	 * @param type
	 * @since 01.09.2018/0.4.0
	 */
	public GridCellCluster(GridCell[] cells, GridCell.Type type) {
		
		this.cells = cells;
		this.type = type;
		
		this.width = 0;
		this.height = 0;
		this.depth = 0;
		
		this.x = 0;
		this.y = 0;
		this.z = 0;
	}
	
	public boolean intersects(GridCellCluster cluster) {
		
		return false;
	}
	
	/**
	 * 
	 * @return
	 * @since 01.09.2018/0.4.0
	 */
	public GridCell[] getCells() {
		
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
	public GridCell.Type getType() {
		
		return this.type;
	}
	
	/**
	 * 
	 * @return
	 * @since 04.09.2018/0.4.0
	 */
	public GridCellCluster copy() {
		
		GridCell[] cells = new GridCell[this.cells.length];
		
		for(int index = 0; index < cells.length; index++) {
			
			cells[index] = this.cells[index].copy();
		}
		
		GridCellCluster cluster = new GridCellCluster(cells, this.type);
		return cluster;
	}
}
