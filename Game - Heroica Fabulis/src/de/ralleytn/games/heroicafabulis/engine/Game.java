 package de.ralleytn.games.heroicafabulis.engine;

import java.io.File;
import java.io.IOException;

import org.lwjgl.glfw.GLFW;

import de.ralleytn.games.heroicafabulis.engine.rendering.Graphics3D;

import static org.lwjgl.opengl.GL11.*;

/**
 * 
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 12.08.2018/0.1.0
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
	private int fps;
	private int fpsCap;
	
	/**
	 * 
	 * @param title
	 * @param nativeDirectory
	 * @since 04.08.2018/0.1.0
	 */
	public Game(String title, File nativeDirectory) {
		
		this.title = title;
		this.nativeDirectory = nativeDirectory;
		this.scene = new Scene();
		this.fpsCap = -1;
	}
	
	/**
	 * 
	 * @param game
	 * @throws EngineException
	 * @throws IOException
	 * @since 12.08.2018/0.1.0
	 */
	public abstract void initialize(Game game) throws EngineException, IOException;
	
	/**
	 * 
	 * @throws EngineException
	 * @throws IOException
	 * @since 04.08.2018/0.1.0
	 */
	public void start() throws EngineException, IOException {
		
		this.display = Engine.start(this);
		this.display.center();
		this.display.setVSync(true);
		this.display.setVisible(true);
		
		this.camera = new Camera(this.display);
		
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
	 * 
	 * @since 04.08.2018/0.1.0
	 */
	public void stop() {
		
		this.display.requestClose();
		Engine.stop();
	}
	
	/**
	 * 
	 * @param fpsCap
	 * @since 05.08.2018/0.1.0
	 */
	public void setFPSCap(int fpsCap) {
		
		this.fpsCap = fpsCap;
	}
	
	/**
	 * 
	 * @param localeDirectory
	 * @since 04.08.2018/0.1.0
	 */
	public void setLocaleDirectory(File localeDirectory) {
		
		this.localeDirectory = localeDirectory;
	}
	
	/**
	 * 
	 * @param errLogDirectory
	 * @since 04.08.2018/0.1.0
	 */
	public void setErrLogDirectory(File errLogDirectory) {
		
		this.errLogDirectory = errLogDirectory;
	}
	
	/**
	 * 
	 * @return
	 * @since 04.08.2018/0.1.0
	 */
	public File getNativeDirectory() {
		
		return this.nativeDirectory;
	}
	
	/**
	 * 
	 * @return
	 * @since 04.08.2018/0.1.0
	 */
	public File getLocaleDirectory() {
		
		return this.localeDirectory;
	}
	
	/**
	 * 
	 * @return
	 * @since 04.08.2018/0.1.0
	 */
	public File getErrLogDirectory() {
		
		return this.errLogDirectory;
	}
	
	/**
	 * 
	 * @return
	 * @since 04.08.2018/0.1.0
	 */
	public String getTitle() {
		
		return this.title;
	}
	
	/**
	 * 
	 * @return
	 * @since 04.08.2018/0.1.0
	 */
	public Display getDisplay() {
		
		return this.display;
	}
	
	/**
	 * 
	 * @return
	 * @since 04.08.2018/0.1.0
	 */
	public Scene getScene() {
		
		return this.scene;
	}
	
	/**
	 * 
	 * @return
	 * @since 12.08.2018/0.1.0
	 */
	public Camera getCamera() {
		
		return this.camera;
	}
	
	/**
	 * 
	 * @return
	 * @since 05.08.2018/0.1.0
	 */
	public int getFPSCap() {
		
		return this.fpsCap;
	}
	
	/**
	 * 
	 * @return
	 * @since 05.08.2018/0.1.0
	 */
	public int getCurrentFPS() {
		
		return this.fps;
	}
}
