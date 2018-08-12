package de.ralleytn.games.heroicafabulis.engine;

import static org.lwjgl.glfw.GLFW.*;

import org.lwjgl.glfw.Callbacks;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.system.MemoryUtil;

/**
 * There can only be a single instance of this class that was created with the {@link Engine#start(java.io.File, java.io.File, String)} method.
 * It represents the game window.
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 01.08.2018
 * @since 31.07.2018
 */
public class Display implements Disposable {

	private static final int DEFAULT_WIDTH = 1024;
	private static final int DEFAULT_HEIGHT = 768;
	private static final int DEFAULT_MIN_WIDTH = 640;
	private static final int DEFAULT_MIN_HEIGHT = 480;
	
	private GLFWVidMode primaryMonitorVidMode;
	private GLFWVidMode vidMode;
	private GLFWVidMode[] vidModes;
	private String title;
	private long id;
	private int minWidth;
	private int minHeight;
	private int maxWidth;
	private int maxHeight;
	private int oldWidth;
	private int oldHeight;
	private boolean fullscreen;
	private boolean vsync;
	
	/**
	 * Initializes the display.
	 * @param title the window title
	 * @throws EngineException if no window could be created
	 * @since 31.07.2018
	 */
	Display(String title) throws EngineException {
		
		this.initializeVidModes();
		
		this.title = title;
		this.minWidth = DEFAULT_MIN_WIDTH;
		this.minHeight = DEFAULT_MIN_HEIGHT;
		this.maxWidth = this.primaryMonitorVidMode.width();
		this.maxHeight = this.primaryMonitorVidMode.height();
		this.id = glfwCreateWindow(DEFAULT_WIDTH, DEFAULT_HEIGHT, title, MemoryUtil.NULL, MemoryUtil.NULL);
	
		if(this.id != MemoryUtil.NULL) {
			
			glfwSetWindowSizeLimits(this.id, this.minWidth, this.minHeight, this.maxWidth, this.maxHeight);

			// TODO
			
		} else {
			
			throw new EngineException("Failed to create a GLFW window!");
		}
	}
	
	/**
	 * Retrieves all the vid modes.
	 * @since 31.07.2018
	 */
	private final void initializeVidModes() {
		
		this.primaryMonitorVidMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
		this.vidMode = this.primaryMonitorVidMode;
		GLFWVidMode.Buffer buffer = glfwGetVideoModes(glfwGetPrimaryMonitor());
		this.vidModes = new GLFWVidMode[buffer.capacity()];
		
		for(int index = 0; index < this.vidModes.length; index++) {
			
			this.vidModes[index] = buffer.get(index);
		}
	}
	
	@Override
	protected void finalize() throws Throwable {
		
		try {
			
			this.dispose();
			
		} finally {
			
			super.finalize();
		}
	}
	
	@Override
	public void dispose() {
		
		Callbacks.glfwFreeCallbacks(this.id);
		glfwDestroyWindow(this.id);
	}
	
	/**
	 * 
	 * @param vsync
	 * @since 31.07.2018
	 */
	public void setVSync(boolean vsync) {
		
		glfwSwapInterval(vsync ? GLFW_TRUE : GLFW_FALSE);
		this.vsync = vsync;
	}
	
	/**
	 * 
	 * @param vidMode
	 * @since 31.07.2018
	 */
	public void setVidMode(GLFWVidMode vidMode) {
		
		this.vidMode = vidMode;
	}
	
	/**
	 * 
	 * @param fullscreen
	 * @since 31.07.2018
	 */
	public void setFullscreen(boolean fullscreen) {
		
		this.fullscreen = fullscreen;
		
		if(fullscreen) {
			
			this.oldWidth = this.getWidth();
			this.oldHeight = this.getHeight();
			glfwSetWindowMonitor(this.id, glfwGetPrimaryMonitor(), 0, 0, this.vidMode.width(), this.vidMode.height(), this.vidMode.refreshRate());
		
		} else {
			
			glfwSetWindowMonitor(this.id, 0, 0, 0, this.oldWidth, this.oldHeight, this.primaryMonitorVidMode.refreshRate());
			this.center();
		}
	}
	
	/**
	 * 
	 * @param numer
	 * @param denom
	 * @since 31.07.2018
	 */
	public void setAspectRatio(int numer, int denom) {
		
		glfwSetWindowAspectRatio(this.id, numer, denom);
	}
	
	/**
	 * 
	 * @param x
	 * @param y
	 * @since 31.07.2018
	 */
	public void setPosition(int x, int y) {
		
		glfwSetWindowPos(this.id, x, y);
	}
	
	/**
	 * 
	 * @param decorated
	 * @since 31.07.2018
	 */
	public void setDecorated(boolean decorated) {
		
		glfwSetWindowAttrib(this.id, GLFW_DECORATED, decorated ? GLFW_TRUE : GLFW_FALSE);
	}
	
	/**
	 * 
	 * @param title
	 * @since 31.07.2018
	 */
	public void setTitle(String title) {
		
		glfwSetWindowTitle(this.id, title);
		this.title = title;
	}
	
	/**
	 * 
	 * @param minWidth
	 * @param minHeight
	 * @since 31.07.2018
	 */
	public void setMinimumSize(int minWidth, int minHeight) {
		
		this.minWidth = minWidth;
		this.minHeight = minHeight;
		glfwSetWindowSizeLimits(this.id, minWidth, minHeight, this.maxWidth, this.maxHeight);
	}
	
	/**
	 * 
	 * @param maxWidth
	 * @param maxHeight
	 * @since 31.07.2018
	 */
	public void setMaximumSize(int maxWidth, int maxHeight) {
		
		this.maxWidth = maxWidth;
		this.maxHeight = maxHeight;
		glfwSetWindowSizeLimits(this.id, this.minWidth, this.minHeight, maxWidth, maxHeight);
	}
	
	/**
	 * 
	 * @param width
	 * @param height
	 * @since 31.07.2018
	 */
	public void setSize(int width, int height) {
		
		glfwSetWindowSize(this.id, width, height);
	}
	
	/**
	 * 
	 * @param resizable
	 * @since 31.07.2018
	 */
	public void setResizable(boolean resizable) {
		
		glfwSetWindowAttrib(this.id, GLFW_RESIZABLE, resizable ? GLFW_TRUE : GLFW_FALSE);
	}
	
	/**
	 * 
	 * @param visible
	 * @since 31.07.2018
	 */
	public void setVisible(boolean visible) {
		
		if(visible) {
			
			glfwShowWindow(this.id);
			
		} else {
			
			glfwHideWindow(this.id);
		}
	}
	
	/**
	 * 
	 * @since 31.07.2018
	 */
	public void requestClose() {
		
		glfwSetWindowShouldClose(this.id, true);
	}
	
	/**
	 * 
	 * @since 31.07.2018
	 */
	public void updateViewPort() {

		GL11.glViewport(0, 0, this.getFrameBufferWidth(), this.getFrameBufferHeight());
	}
	
	/**
	 * 
	 * @since 31.07.2018
	 */
	public void swapBuffers() {
		
		glfwSwapBuffers(this.id);
	}
	
	/**
	 * 
	 * @since 31.07.2018
	 */
	public void makeContextCurrent() {
		
		glfwMakeContextCurrent(this.id);
	}
	
	/**
	 * 
	 * @since 31.07.2018
	 */
	public void center() {
		
		int x = (this.primaryMonitorVidMode.width() - this.getWidth()) / 2;
		int y = (this.primaryMonitorVidMode.height() - this.getHeight()) / 2;
		this.setPosition(x, y);
	}
	
	/**
	 * 
	 * @since 31.07.2018
	 */
	public void poll() {
		
		glfwPollEvents();
	}
	
	/**
	 * 
	 * @return
	 * @since 31.07.2018
	 */
	public int getWidth() {
		
		int[] widthBuffer = new int[1];
		glfwGetWindowSize(this.id, widthBuffer, new int[1]);
		return widthBuffer[0];
	}
	
	/**
	 * 
	 * @return
	 * @since 31.07.2018
	 */
	public int getHeight() {
		
		int[] heightBuffer = new int[1];
		glfwGetWindowSize(this.id, new int[1], heightBuffer);
		return heightBuffer[0];
	}
	
	/**
	 * 
	 * @return
	 * @since 31.07.2018
	 */
	public int getFrameBufferWidth() {
		
		int[] widthBuffer = new int[1];
		glfwGetFramebufferSize(this.id, widthBuffer, new int[1]);
		return widthBuffer[0];
	}

	/**
	 * 
	 * @return
	 * @since 31.07.2018
	 */
	public int getFrameBufferHeight() {
		
		int[] heightBuffer = new int[1];
		glfwGetFramebufferSize(this.id, new int[1], heightBuffer);
		return heightBuffer[0];
	}
	
	/**
	 * 
	 * @return
	 * @since 31.07.2018
	 */
	public boolean isVSync() {
		
		return this.vsync;
	}

	/**
	 * 
	 * @return
	 * @since 31.07.2018
	 */
	public int getMinWidth() {
		
		return this.minWidth;
	}

	/**
	 * 
	 * @return
	 * @since 31.07.2018
	 */
	public int getMinHeight() {
		
		return this.minHeight;
	}

	/**
	 * 
	 * @return
	 * @since 31.07.2018
	 */
	public int getMaxWidth() {
		
		return this.maxWidth;
	}

	/**
	 * 
	 * @return
	 * @since 31.07.2018
	 */
	public int getMaxHeight() {
		
		return this.maxHeight;
	}
	
	/**
	 * 
	 * @return
	 * @since 31.07.2018
	 */
	public String getTitle() {
		
		return this.title;
	}
	
	/**
	 * 
	 * @return
	 * @since 31.07.2018
	 */
	public boolean isCloseRequested() {
		
		return glfwWindowShouldClose(this.id);
	}
	
	/**
	 * 
	 * @return
	 * @since 31.07.2018
	 */
	public boolean isVisible() {
		
		return glfwGetWindowAttrib(this.id, GLFW_VISIBLE) == GLFW_TRUE;
	}
	
	/**
	 * 
	 * @return
	 * @since 31.07.2018
	 */
	public boolean isResizable() {
		
		return glfwGetWindowAttrib(this.id, GLFW_RESIZABLE) == GLFW_TRUE;
	}

	/**
	 * 
	 * @return
	 * @since 31.07.2018
	 */
	public int getX() {
		
		int[] xBuffer = new int[1];
		glfwGetWindowPos(this.id, xBuffer, new int[1]);
		return xBuffer[0];
	}

	/**
	 * 
	 * @return
	 * @since 31.07.2018
	 */
	public int getY() {
		
		int[] yBuffer = new int[1];
		glfwGetWindowPos(this.id, new int[1], yBuffer);
		return yBuffer[0];
	}

	/**
	 * 
	 * @return
	 * @since 31.07.2018
	 */
	public boolean isDecorated() {
		
		return glfwGetWindowAttrib(this.id, GLFW_DECORATED) == GLFW_TRUE;
	}

	/**
	 * 
	 * @return
	 * @since 31.07.2018
	 */
	public boolean isFullscreen() {
		
		return this.fullscreen;
	}
}
