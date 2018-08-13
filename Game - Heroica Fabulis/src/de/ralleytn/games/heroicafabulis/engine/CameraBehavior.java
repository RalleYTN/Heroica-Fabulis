package de.ralleytn.games.heroicafabulis.engine;

/**
 * The camera behavior determines how a camera behaves, as the name would suggest.
 * Without it a camera is just stationary, it gives the camera the possibility to be controlled and moved.
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 13.08.2018/0.1.0
 * @since 12.08.2018/0.1.0
 */
public abstract class CameraBehavior implements Updatable {

	private Camera camera;
	
	/**
	 * Sets the camera which should be affected by this behavior.
	 * @param camera the camera
	 * @since 13.08.2018/0.1.0
	 */
	void setCamera(Camera camera) {
		
		this.camera = camera;
	}
	
	/**
	 * @return the camera that is affected by this behavior
	 * @since 13.08.2018/0.1.0
	 */
	public final Camera getCamera() {
		
		return this.camera;
	}
}
