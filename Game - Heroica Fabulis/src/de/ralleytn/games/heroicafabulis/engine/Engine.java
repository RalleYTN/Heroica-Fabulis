package de.ralleytn.games.heroicafabulis.engine;

import java.io.File;
import java.io.IOException;

import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.vecmath.Vector3f;

import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryUtil;

import static org.lwjgl.glfw.GLFW.*;

/**
 * 
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 12.08.2018/0.1.0
 * @since 31.07.2018/0.1.0
 */
public final class Engine {

	/**
	 * NEVER CHANGE THE VALUES!
	 * @since 11.08.2018/0.1.0
	 */
	public static final Vector3f AXIS_X = new Vector3f(1.0F, 0.0F, 0.0F);
	
	/**
	 * NEVER CHANGE THE VALUES!
	 * @since 11.08.2018/0.1.0
	 */
	public static final Vector3f AXIS_Y = new Vector3f(0.0F, 1.0F, 0.0F);
	
	/**
	 * NEVER CHANGE THE VALUES!
	 * @since 11.08.2018/0.1.0
	 */
	public static final Vector3f AXIS_Z = new Vector3f(0.0F, 0.0F, 1.0F);
	
	private static boolean RUNNING;
	private static Game GAME;
	
	/**
	 * @since 31.07.2018
	 */
	private Engine() {}

	/**
	 * 
	 * @param game
	 * @return
	 * @throws EngineException
	 * @throws IOException
	 * @since 31.07.2018/0.1.0
	 */
	public static final Display start(Game game) throws EngineException, IOException {

		if(!RUNNING) {
			
			setSystemLAF();
			loadNatives(game.getNativeDirectory());
			
			if(game.getLocaleDirectory() != null) {
				
				Localization.loadAvailableLocales(game.getLocaleDirectory());
			}
			
			initilizeGLFW();
			Display display = new Display(game.getTitle());
			display.makeContextCurrent();
			GL.createCapabilities();
			GAME = game;
			RUNNING = true;
			
			return display;
			
		} else {
			
			throw new EngineException("The engine was already started!");
		}
	}
	
	/**
	 * 
	 * @param game
	 * @since 04.08.2018/0.1.0
	 */
	public static final void stop(Game game) {
		
		if(RUNNING) {
			
			glfwTerminate();
			glfwSetErrorCallback(null).free();
			RUNNING = false;
		}
	}
	
	/**
	 * @since 31.07.2018/0.1.0
	 */
	private static final void setSystemLAF() {
		
		try {
			
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			
		} catch(Exception exception) {
			
			Errors.print(exception);
			Errors.log(exception, getErrLogDirectory());
			System.exit(1);
		}
	}
	
	/**
	 * 
	 * @since 31.07.2018/0.1.0
	 */
	private static final void loadNatives(File nativeDirectory) {
		
		System.setProperty("org.lwjgl.librarypath", nativeDirectory.getAbsolutePath());
	}

	/**
	 * 
	 * @throws EngineException
	 * @since 31.07.2018/0.1.0
	 */
	private static final void initilizeGLFW() throws EngineException {
		
		glfwSetErrorCallback((error, descriptionID) -> {
	
			String message = MemoryUtil.memUTF8(descriptionID);
			System.err.printf("ERROR %d: %s", error, message);
			JOptionPane.showConfirmDialog(null, message, "ERROR " + error, JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		});
			
		if(!glfwInit()) {
			
			throw new EngineException("GLFW could not be initialized!");
		}
			
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
		glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
	}
	
	/**
	 * 
	 * @return
	 * @since 01.08.2018/0.1.0
	 */
	public static final File getErrLogDirectory() {
		
		return GAME.getErrLogDirectory() != null ? GAME.getErrLogDirectory() : new File(System.getProperty("user.dir"));
	}
}
