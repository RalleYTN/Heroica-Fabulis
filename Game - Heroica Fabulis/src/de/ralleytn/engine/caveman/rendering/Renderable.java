package de.ralleytn.engine.caveman.rendering;

/**
 * Interface that should be implemented by everything that needs to render something.
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 30.07.2018
 * @since 30.07.2018
 */
public interface Renderable {

	/**
	 * Is called every frame at least once after the frame update.
	 * @param graphics object with some methods for rendering
	 * @since 30.07.2018
	 */
	public void render(Graphics3D graphics);
}
