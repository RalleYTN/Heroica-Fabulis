package de.ralleytn.engine.caveman;

/**
 * This interface is supposed to be implemented by classes that want to be updated by the main game loop.
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 30.07.2018/0.1.0
 * @since 30.07.2018/0.1.0
 */
public interface Updatable {

	/**
	 * Gets called every frame and is meant to be used to calculate and nothing else.
	 * @param delta multiply all the values that have something to do with movement with this to ensure that
	 *        the game doesn't slow down or speed up when the framerate changes.
	 * @since 30.07.2018/0.1.0
	 */
	public void update(float delta);
}
