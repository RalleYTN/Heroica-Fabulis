package de.ralleytn.games.heroicafabulis.engine.display;

import static org.lwjgl.glfw.GLFW.*;

import org.lwjgl.glfw.GLFWVidMode;

/**
 * Manages the video modes.
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 16.08.2018/0.1.0
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
}
