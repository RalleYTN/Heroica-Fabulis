package de.ralleytn.games.heroicafabulis.engine;

import de.ralleytn.games.heroicafabulis.engine.rendering.Graphics3D;

/**
 * 
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 30.07.2018
 * @since 30.07.2018
 */
public interface Renderable {

	/**
	 * 
	 * @param graphics
	 * @since 30.07.2018
	 */
	public void render(Graphics3D graphics);
}
