package de.ralleytn.games.heroicafabulis.engine;

import java.io.File;
import java.io.IOException;

import org.lwjgl.glfw.GLFW;

import de.ralleytn.games.heroicafabulis.engine.audio.Music;
import de.ralleytn.games.heroicafabulis.engine.display.Display;
import de.ralleytn.games.heroicafabulis.engine.input.KeyboardController;
import de.ralleytn.games.heroicafabulis.engine.input.MouseController;
import de.ralleytn.games.heroicafabulis.engine.rendering.Graphics3D;
import de.ralleytn.games.heroicafabulis.engine.rendering.camera.Camera;
import de.ralleytn.games.heroicafabulis.engine.rendering.camera.CameraBehavior;

import static org.lwjgl.opengl.GL11.*;

/**
 * Represents an abstract game and should be extended by the main class of a project.
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 28.08.2018/0.3.0
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
	private Options options;
	private Music music;
	private int fps;
	private int fpsCap;
	
	/**
	 * @param title the game title
	 * @param optionsFile file containing the game options
	 * @param defaultResource alternative location within the class path containing the default options
	 * @throws IOException If the options file or the default options could not be read
	 * @since 04.08.2018/0.1.0
	 */
	public Game(String title, File optionsFile, String defaultResource) throws IOException {
		
		this.options = new Options(optionsFile, defaultResource);
		this.options.load();
		
		this.title = title;
		this.nativeDirectory = new File(this.options.getString(Options.OPTION_NATIVE_DIRECTORY));
		this.errLogDirectory = new File(this.options.getString(Options.OPTION_ERR_LOG_DIRECTORY));
		this.localeDirectory = new File(this.options.getString(Options.OPTION_LOCALE_DIRECTORY));
		this.scene = new Scene(this);
		this.fpsCap = this.options.getInt(Options.OPTION_FPS_CAP);
	}
	
	/**
	 * @return the system time in milliseconds
	 * @since 28.08.2018/0.3.0
	 */
	public static final long getTime() {
		
		return System.nanoTime() / 1000000;
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
		this.display.setResizable(false);
		this.display.setVSync(this.options.getBoolean(Options.OPTION_VSYNC));
		this.display.setVisible(true);
		this.display.setFullscreen(this.options.getBoolean(Options.OPTION_FULLSCREEN));
		
		this.mouseController = new MouseController(this);
		this.mouseController.setCursorPosition(this.display.getFrameBufferWidth() / 2, this.display.getFrameBufferHeight() / 2);
		
		this.keyboardController = new KeyboardController(this);
		this.camera = new Camera(this);
		this.music = new Music();
		
		// FIXED: BUG0003
		// the delta calculation seems to be wrong.
		// the game runs at different speeds when in windowed mode or fullscreen
		
		int wait = 0;
		int frames = 0;
		double fpsTimer1 = GLFW.glfwGetTime();
		double fpsTimer2 = 0;
		float delta = 0;
		long lastFrameTime = getTime();
		Graphics3D graphics3D = new Graphics3D(this);
		Exception exitException = null;
		this.initialize(this);
		
		while(!this.display.isCloseRequested()) {
			
			try {
				
				glEnable(GL_DEPTH_TEST);
				glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
				glClearColor(0.0F, 0.0F, 0.0F, 0.0F);
				
				long currentFrameTime = getTime();
				delta = currentFrameTime - lastFrameTime;
				lastFrameTime = currentFrameTime;
				
				this.scene.update(delta);
				CameraBehavior behavior = this.camera.getBehavior();
				
				if(behavior != null) {
					
					behavior.update(delta);
				}
				
				this.update(delta);
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
				
			} catch(Exception exception) {
				
				exitException = exception;
				break;
			}
		}
		
		this.stop();
		
		if(exitException != null) {
			
			throw new EngineException(exitException);
		}
	}
	
	/**
	 * Stops the game.
	 * @since 04.08.2018/0.1.0
	 */
	public void stop() {
		
		try {
			
			this.options.save();
			
		} catch(IOException exception) {
			
			Errors.print(exception);
			Errors.prompt(exception, Errors.log(exception, this.errLogDirectory));
		}
		
		if(this.display != null) {
			
			this.display.requestClose();
		}
		
		Engine.stop();
	}
	
	/**
	 * Sets the maximum frames per second.
	 * @param fpsCap the maximum frames per second
	 * @since 05.08.2018/0.1.0
	 */
	public void setFPSCap(int fpsCap) {
		
		this.fpsCap = fpsCap;
		this.options.set(Options.OPTION_FPS_CAP, fpsCap);
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
	
	/**
	 * @return the game options
	 * @since 17.08.2018/0.2.0
	 */
	public Options getOptions() {
		
		return this.options;
	}
	
	/**
	 * @return the music player for the game
	 * @since 26.08.2018/0.3.0
	 */
	public Music getMusic() {
		
		return this.music;
	}
}
