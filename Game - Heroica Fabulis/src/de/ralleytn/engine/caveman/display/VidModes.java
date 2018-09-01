package de.ralleytn.engine.caveman.display;

import static org.lwjgl.glfw.GLFW.*;

import org.lwjgl.glfw.GLFWVidMode;

import de.ralleytn.engine.caveman.Options;

/**
 * Manages the video modes.
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 18.08.2018/0.2.0
 * @since 16.08.2018/0.1.0
 */
public final class VidModes {

	private static GLFWVidMode PRIMARY_MONITOR_VID_MODE;
	private static GLFWVidMode[] VID_MODES;
	
	/**
	 * Private because no instance of this class should exist.
	 * @since 16.08.2018/0.1.0
	 */
	private VidModes() {}
	
	/**
	 * Initializes the video modes.
	 * @since 16.08.2018/0.1.0
	 */
	public static final void initialize() {
		
		PRIMARY_MONITOR_VID_MODE = glfwGetVideoMode(glfwGetPrimaryMonitor());
		GLFWVidMode.Buffer buffer = glfwGetVideoModes(glfwGetPrimaryMonitor());
		VID_MODES = new GLFWVidMode[buffer.capacity()];
		
		for(int index = 0; index < VID_MODES.length; index++) {
			
			VID_MODES[index] = buffer.get(index);
		}
	}
	
	/**
	 * @return the video mode for the primary monitor
	 * @since 16.08.2018/0.1.0
	 */
	public static final GLFWVidMode getPrimaryMonitorVidMode() {
		
		return PRIMARY_MONITOR_VID_MODE;
	}
	
	/**
	 * @return an array with all of the available video modes
	 * @since 16.08.2018/0.1.0
	 */
	public static final GLFWVidMode[] getAvailableVidModes() {
		
		return VID_MODES;
	}
	
	/**
	 * @param options the game options
	 * @return the vid mode from the game options
	 * @since 18.08.2018/0.2.0
	 */
	public static final GLFWVidMode getVidMode(Options options) {
		
		int width = options.getInt(Options.OPTION_DISPLAY_WIDTH);
		int height = options.getInt(Options.OPTION_DISPLAY_HEIGHT);
		GLFWVidMode vidModeToReturn = null;
		int highestRefreshRate = 0;
		
		for(GLFWVidMode vidMode : VID_MODES) {
			
			if(vidMode.width() == width && vidMode.height() == height) {

				if(vidMode.refreshRate() > highestRefreshRate) {
					
					highestRefreshRate = vidMode.refreshRate();
					vidModeToReturn = vidMode;
				}
			}
		}

		return vidModeToReturn != null ? vidModeToReturn : getPrimaryMonitorVidMode();
	}
}
