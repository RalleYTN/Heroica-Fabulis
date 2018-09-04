package de.ralleytn.engine.caveman.procgen;

import java.util.List;

/**
 * 
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 04.09.2018/0.4.0
 * @since 04.09.2018/0.4.0
 */
public abstract class LevelGenerator {

	private List<CellCluster> clusterBaseSet;
	
	/**
	 * 
	 * @param clusterBaseSet
	 * @since 04.09.2018/0.4.0
	 */
	public LevelGenerator(List<CellCluster> clusterBaseSet) {
		
		this.clusterBaseSet = clusterBaseSet;
	}
}
