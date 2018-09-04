package de.ralleytn.engine.caveman.procgen;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 04.09.2018/0.4.0
 * @since 04.09.2018/0.4.0
 */
public abstract class GridBasedLevelGenerator {

	protected final List<GridCellCluster> clusterBaseSet;
	protected final List<GridCell> cells;
	
	/**
	 * 
	 * @param clusterBaseSet
	 * @since 04.09.2018/0.4.0
	 */
	public GridBasedLevelGenerator(List<GridCellCluster> clusterBaseSet) {
		
		this.clusterBaseSet = clusterBaseSet;
		this.cells = new ArrayList<>();
	}
	
	/**
	 * 
	 * @return
	 * @since 04.09.2018/0.4.0
	 */
	public abstract void generateLayout();
	
	/**
	 * 
	 * @return
	 * @since 04.09.2018/0.4.0
	 */
	public List<GridCell> getCells() {
		
		return this.cells;
	}
}
