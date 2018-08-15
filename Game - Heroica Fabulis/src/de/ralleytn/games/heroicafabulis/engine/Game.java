package de.ralleytn.games.heroicafabulis.engine;

import java.io.File;
import java.io.IOException;

import org.lwjgl.glfw.GLFW;

import de.ralleytn.games.heroicafabulis.engine.input.KeyboardController;
import de.ralleytn.games.heroicafabulis.engine.input.MouseController;
import de.ralleytn.games.heroicafabulis.engine.rendering.Graphics3D;

import static org.lwjgl.opengl.GL11.*;

/**
 * Represents an abstract game and should be extended by the main class of a project.
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 14.08.2018/0.1.0
 * @since 04.08.2018/0.1.0
 */
public abstract class Game implements Updatable {

	private File nativeDirectory;
	private File localeDirectory;
	private File errLogDirectory;
	private String title;
	private Display display;
	private Scene scene;
	private Camera camera;
	private MouseController mouseController;
	private KeyboardController keyboardController;
	private int fps;
	private int fpsCap;
	
	/**
	 * @param title the game title
	 * @param nativeDirectory the directory containing the native libraries
	 * @since 04.08.2018/0.1.0
	 */
	public Game(String title, File nativeDirectory) {
		
		this.title = title;
		this.nativeDirectory = nativeDirectory;
		this.scene = new Scene();
		this.fpsCap = -1;
	}
	
	/**
	 * Is called when the game loop started.
	 * @param game the game for which the game loop started
	 * @throws EngineException just here to give the exception back to {@link #start()}
	 * @throws IOException just here to give the exception back to {@link #start()}
	 * @since 12.08.2018/0.1.0
	 */
	public abstract void initialize(Game game) throws EngineException, IOException;
	
	/**
	 * Starts the game loop.
	 * @throws EngineException if the engine could not be started
	 * @throws IOException if some of the required files could not be read
	 * @since 04.08.2018/0.1.0
	 */
	public void start() throws EngineException, IOException {
		
		this.display = Engine.start(this);
		this.display.center();
		this.display.setVSync(true);
		this.display.setVisible(true);
		
		this.camera = new Camera(this);
		this.mouseController = new MouseController(this);
		this.keyboardController = new KeyboardController(this);
		
		int wait = 0;
		int frames = 0;
		double fpsTimer1 = GLFW.glfwGetTime();
		double fpsTimer2 = 0;
		double delta = 0;
		long deltaTimer1 = System.nanoTime();
		long deltaTimer2 = 0;
		Graphics3D graphics3D = new Graphics3D(this);
		this.initialize(this);
		
		while(!this.display.isCloseRequested()) {
			
			try {
				
				glEnable(GL_DEPTH_TEST);
				glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
				glClearColor(0.0F, 0.0F, 0.0F, 0.0F);
				
				deltaTimer2 = System.nanoTime();
				delta = (double)(deltaTimer2 - deltaTimer1) / 10000000;
				deltaTimer1 = deltaTimer2;
				
				float fDelta = (float)delta;
				
				this.scene.update(fDelta);
				CameraBehavior behavior = this.camera.getBehavior();
				
				if(behavior != null) {
					
					behavior.update(fDelta);
				}
				
				this.update(fDelta);
				this.scene.render(graphics3D);
				
				this.display.swapBuffers();
				
				frames++;
				fpsTimer2 = GLFW.glfwGetTime();
				
				if(fpsTimer2 - fpsTimer1 >= 1.0D) {
					
					this.fps = frames;
					frames = 0;
					fpsTimer1 = fpsTimer2;
					
					if(this.fpsCap > 0) {
						
						if(this.fps > this.fpsCap) {
							
							wait++;
							
						} else if(this.fps < this.fpsCap && wait > 0) {
							
							wait--;
						}
					}
				}
				
				if(this.fpsCap > 0) {
					
					Thread.sleep(wait);
				}
				
				this.display.poll();
				
			} catch(InterruptedException exception) {
				
				break;
			}
		}
		
		this.stop();
	}
	
	/**
	 * Stops the game.
	 * @since 04.08.2018/0.1.0
	 */
	public void stop() {
		
		this.display.requestClose();
		Engine.stop();
	}
	
	/**
	 * Sets the maximum frames per second.
	 * @param fpsCap the maximum frames per second
	 * @since 05.08.2018/0.1.0
	 */
	public void setFPSCap(int fpsCap) {
		
		this.fpsCap = fpsCap;
	}
	
	/**
	 * Sets the directory in which the locales can be found.
	 * The locales contain all of the localized strings for different languages.
	 * @param localeDirectory the directory
	 * @since 04.08.2018/0.1.0
	 */
	public void setLocaleDirectory(File localeDirectory) {
		
		this.localeDirectory = localeDirectory;
	}
	
	/**
	 * Sets the directory in which all the error logs should be written to.
	 * @param errLogDirectory the directory
	 * @since 04.08.2018/0.1.0
	 */
	public void setErrLogDirectory(File errLogDirectory) {
		
		this.errLogDirectory = errLogDirectory;
	}
	
	/**
	 * @return the directory containing the native libraries
	 * @since 04.08.2018/0.1.0
	 */
	public File getNativeDirectory() {
		
		return this.nativeDirectory;
	}
	
	/**
	 * @return the directory containing the locales
	 * @since 04.08.2018/0.1.0
	 */
	public File getLocaleDirectory() {
		
		return this.localeDirectory;
	}
	
	/**
	 * @return the directory in which the error logs are written
	 * @since 04.08.2018/0.1.0
	 */
	public File getErrLogDirectory() {
		
		return this.errLogDirectory;
	}
	
	/**
	 * @return the game title
	 * @since 04.08.2018/0.1.0
	 */
	public String getTitle() {
		
		return this.title;
	}
	
	/**
	 * @return the display
	 * @since 04.08.2018/0.1.0
	 */
	public Display getDisplay() {
		
		return this.display;
	}
	
	/**
	 * @return the scene
	 * @since 04.08.2018/0.1.0
	 */
	public Scene getScene() {
		
		return this.scene;
	}
	
	/**
	 * @return the camera
	 * @since 12.08.2018/0.1.0
	 */
	public Camera getCamera() {
		
		return this.camera;
	}
	
	/**
	 * @return the maximum frames per second
	 * @since 05.08.2018/0.1.0
	 */
	public int getFPSCap() {
		
		return this.fpsCap;
	}
	
	/**
	 * @return the current frames per second
	 * @since 05.08.2018/0.1.0
	 */
	public int getCurrentFPS() {
		
		return this.fps;
	}
	
	/**
	 * @return object to ask for mouse input
	 * @since 13.08.2018/0.1.0
	 */
	public MouseController getMouseController() {
		
		return this.mouseController;
	}
	
	/**
	 * @return object to ask for keyboard input
	 * @since 14.08.2018/0.1.0
	 */
	public KeyboardController getKeyboardController() {
		
		return this.keyboardController;
	}
}
