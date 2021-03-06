package de.ralleytn.engine.caveman;

import java.io.File;
import java.io.IOException;
import java.lang.Thread.UncaughtExceptionHandler;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JOptionPane;
import javax.swing.UIManager;

import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryUtil;

import de.ralleytn.engine.caveman.audio.OpenAL;
import de.ralleytn.engine.caveman.display.Display;
import de.ralleytn.engine.caveman.display.VidModes;
import de.ralleytn.engine.caveman.localization.Localization;

import static org.lwjgl.glfw.GLFW.*;

/**
 * Class which is used to start and stop the engine. It also contains methods and constants that are important in the rest of the engine but
 * not really utility methods.
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 08.09.2018/0.4.0
 * @since 31.07.2018/0.1.0
 */
public final class Engine {
	
	private static final List<ExecutorService> EXECUTORS = new ArrayList<>();
	
	private static boolean RUNNING;
	private static Game GAME;
	
	/**
	 * Private because this class does not need an instance.
	 * @since 31.07.2018
	 */
	private Engine() {}

	/**
	 * Starts the game engine and creates the display.
	 * @param game the game class
	 * @return the created display
	 * @throws EngineException if the engine is already running or no display could be created
	 * @throws IOException if the locales could not be read correctly
	 * @since 31.07.2018/0.1.0
	 */
	public static final Display start(Game game) throws EngineException, IOException {

		if(!RUNNING) {
			
			setupUncaughtExceptionHandler();
			setSystemLAF();
			loadNatives(game.getNativeDirectory());
			Localization.loadAvailableLocales(game.getLocaleDirectory());
			initilizeGLFW();
			VidModes.initialize();
			Display display = new Display(game);
			display.makeContextCurrent();
			GL.createCapabilities();
			OpenAL.create();
			GAME = game;
			RUNNING = true;
			
			return display;
			
		} else {
			
			throw new EngineException("The engine was already started!");
		}
	}
	
	/**
	 * Stops the game engine.
	 * @since 04.08.2018/0.1.0
	 */
	public static final void stop() {
		
		if(RUNNING) {
			
			OpenAL.destroy();
			glfwTerminate();
			glfwSetErrorCallback(null).free();
			
			for(ExecutorService executor : EXECUTORS) {
				
				executor.shutdown();
			}
			
			RUNNING = false;
		}
	}
	
	/**
	 * Sets up an {@linkplain UncaughtExceptionHandler} for the main thread.
	 * @since 18.08.2018/0.2.0
	 */
	private static final void setupUncaughtExceptionHandler() {
		
		Thread.currentThread().setUncaughtExceptionHandler((thread, exception) -> {
			
			Errors.print(exception);
			Errors.prompt(exception, Errors.log(exception, getErrLogDirectory()));
			Engine.stop();
		});
	}
	
	/**
	 * Sets the current look and feel of the AWT/Swing UI to the one of the operating system.
	 * Only important for the error message dialogs.
	 * @since 31.07.2018/0.1.0
	 */
	private static final void setSystemLAF() {
		
		try {
			
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			
		} catch(Exception exception) {
			
			Errors.print(exception);
			Errors.prompt(exception);
			Errors.log(exception, getErrLogDirectory());
		}
	}
	
	/**
	 * Makes sure that all of the libraries that need system natives know where said system natives are and can properly use them.
	 * @since 31.07.2018/0.1.0
	 */
	private static final void loadNatives(File nativeDirectory) {
		
		System.setProperty("org.lwjgl.librarypath", nativeDirectory.getAbsolutePath());
	}

	/**
	 * Initializes GLFW.
	 * @throws EngineException if GLFW could not be initialized
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
	 * Creates and registers a new {@linkplain ExecutorService}.
	 * @return the created {@linkplain ExecutorService}
	 * @since 21.08.2018/0.2.0
	 */
	public static final ExecutorService createExecutor() {
		
		ExecutorService executor = Executors.newSingleThreadExecutor(runnable -> {
			
			Thread thread = new Thread(runnable);
			thread.setUncaughtExceptionHandler((t, exception) -> {
				
				Errors.print(exception);
				Errors.prompt(exception, Errors.log(exception, getErrLogDirectory()));
				Engine.stop();
			});
			thread.setDaemon(true);
			return thread;
		});
		EXECUTORS.add(executor);
		return executor;
	}
	
	/**
	 * @return the location of the directory in which all error logs should be written
	 * @since 01.08.2018/0.1.0
	 */
	public static final File getErrLogDirectory() {
		
		return GAME.getErrLogDirectory() != null ? GAME.getErrLogDirectory() : new File(System.getProperty("user.dir"));
	}
}
