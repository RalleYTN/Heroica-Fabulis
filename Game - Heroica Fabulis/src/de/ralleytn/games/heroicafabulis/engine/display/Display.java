package de.ralleytn.games.heroicafabulis.engine.display;

import static org.lwjgl.glfw.GLFW.*;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import org.lwjgl.glfw.Callbacks;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.system.MemoryUtil;

import de.ralleytn.games.heroicafabulis.engine.Controller;
import de.ralleytn.games.heroicafabulis.engine.Disposable;
import de.ralleytn.games.heroicafabulis.engine.Engine;
import de.ralleytn.games.heroicafabulis.engine.EngineException;
import de.ralleytn.games.heroicafabulis.engine.Game;
import de.ralleytn.games.heroicafabulis.engine.Options;
import de.ralleytn.games.heroicafabulis.engine.util.MathUtil;

/**
 * There can only be a single instance of this class that was created with the {@link Engine#start(java.io.File, java.io.File, String)} method.
 * It represents the game window.
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 18.08.2018/0.2.0
 * @since 31.07.2018/0.1.0
 */
public class Display implements Disposable, Controller<DisplayEvent> {

	/** @since 16.08.018/0.1.0 */ public static final int EVENT_CLOSE = 0;
	/** @since 16.08.018/0.1.0 */ public static final int EVENT_LOSE_FOCUS = 1;
	/** @since 16.08.018/0.1.0 */ public static final int EVENT_GAIN_FOCUS = 2;
	/** @since 16.08.018/0.1.0 */ public static final int EVENT_MAXIMIZE = 3;
	/** @since 16.08.018/0.1.0 */ public static final int EVENT_ICONIFY = 4;
	/** @since 16.08.018/0.1.0 */ public static final int EVENT_RESIZE = 5;
	/** @since 16.08.018/0.1.0 */ public static final int EVENT_MOVE = 6;
	/** @since 16.08.018/0.1.0 */ public static final int EVENT_RESTORE = 7;
	
	private static final int DEFAULT_WIDTH = 1024;
	private static final int DEFAULT_HEIGHT = 768;
	private static final int DEFAULT_MIN_WIDTH = 640;
	private static final int DEFAULT_MIN_HEIGHT = 480;
	
	@SuppressWarnings("unchecked")
	private final List<Consumer<DisplayEvent>>[] listeners = new List[] {
		new ArrayList<>(),	// CLOSE
		new ArrayList<>(),	// LOSE FOCUS
		new ArrayList<>(),	// GAIN FOCUS
		new ArrayList<>(),	// MAXIMIZE
		new ArrayList<>(),	// ICONIFY
		new ArrayList<>(),	// RESIZE
		new ArrayList<>(),	// MOVE
		new ArrayList<>()	// RESTORE
	};

	private GLFWVidMode vidMode;
	private String title;
	private long id;
	private int minWidth;
	private int minHeight;
	private int maxWidth;
	private int maxHeight;
	private boolean fullscreen;
	private boolean vsync;
	private Game game;
	
	/**
	 * @param game the instance of {@linkplain Game} this display belongs to
	 * @throws EngineException if no window could be created
	 * @since 31.07.2018/0.1.0
	 */
	public Display(Game game) throws EngineException {
		
		this.game = game;
		this.title = game.getTitle();
		this.minWidth = DEFAULT_MIN_WIDTH;
		this.minHeight = DEFAULT_MIN_HEIGHT;
		this.vidMode = VidModes.getPrimaryMonitorVidMode();
		this.maxWidth = this.vidMode.width();
		this.maxHeight = this.vidMode.height();
		this.vidMode = VidModes.getVidMode(game.getOptions());
		this.id = glfwCreateWindow(DEFAULT_WIDTH, DEFAULT_HEIGHT, this.title, MemoryUtil.NULL, MemoryUtil.NULL);
	
		if(this.id != MemoryUtil.NULL) {
			
			glfwSetWindowSizeLimits(this.id, this.minWidth, this.minHeight, this.maxWidth, this.maxHeight);
			glfwSetWindowCloseCallback(this.id, window -> this.trigger(EVENT_CLOSE, new DisplayEvent(this)));
			glfwSetWindowFocusCallback(this.id, (window, focused) -> this.trigger(focused ? EVENT_GAIN_FOCUS : EVENT_LOSE_FOCUS, new DisplayEvent(this)));
			glfwSetWindowMaximizeCallback(this.id, (window, maximized) -> this.trigger(maximized ? EVENT_MAXIMIZE : EVENT_RESTORE, new DisplayEvent(this)));
			glfwSetWindowSizeCallback(this.id, (window, width, height) -> this.trigger(EVENT_RESIZE, new DisplayEvent(this, 0, 0, width, height)));
			glfwSetWindowPosCallback(this.id, (window, x, y) -> this.trigger(EVENT_MOVE, new DisplayEvent(this, x, y, 0, 0)));
			glfwSetWindowIconifyCallback(this.id, (window, iconified) -> this.trigger(iconified ? EVENT_ICONIFY : EVENT_RESTORE, new DisplayEvent(this)));
			glfwSetFramebufferSizeCallback(this.id, (long window, int width, int height) -> this.updateViewPort());
			
		} else {
			
			throw new EngineException("Failed to create a GLFW window!");
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
	 * Enables or disables vertical synchronization.
	 * Vertical synchronization makes sure that the frame only is displayed when it can be displayed in one piece.
	 * This prevents screen tearing and also makes sure that the game never runs with more frames per second than the monitor's refresh rate,
	 * which is good because it means that the CPU and GPU don't overheat as fast due to unnecessary calculations.
	 * @param vsync {@code true} to enable vertical synchronization, {@code false} to disable it
	 * @since 31.07.2018/0.1.0
	 */
	public void setVSync(boolean vsync) {
		
		glfwSwapInterval(vsync ? GLFW_TRUE : GLFW_FALSE);
		this.vsync = vsync;
		this.game.getOptions().set(Options.OPTION_VSYNC, vsync);
	}
	
	/**
	 * Sets the video mode. The video mode contains information about the resolution at which the game should be displayed, the bit depth of
	 * the colors and the maximum refresh rate in Hertz.
	 * @param vidMode the video mode
	 * @since 31.07.2018/0.1.0
	 */
	public void setVidMode(GLFWVidMode vidMode) {
		
		this.vidMode = vidMode;
		
		if(!this.fullscreen) {
			
			this.setSize(vidMode.width(), vidMode.height());
			
		} else {
			
			this.setFullscreen(true);
		}
	}
	
	/**
	 * Enables or disables fullscreen.
	 * Some graphical effects are only available in fullscreen.
	 * @param fullscreen {@code true} to enable fullscreen, {@code false} to disable it
	 * @since 31.07.2018/0.1.0
	 */
	public void setFullscreen(boolean fullscreen) {
		
		this.fullscreen = fullscreen;
		Options options = this.game.getOptions();
		
		if(fullscreen) {
			
			glfwSetWindowMonitor(this.id, glfwGetPrimaryMonitor(), 0, 0, this.vidMode.width(), this.vidMode.height(), this.vidMode.refreshRate());
			options.set(Options.OPTION_DISPLAY_WIDTH, this.vidMode.width());
			options.set(Options.OPTION_DISPLAY_HEIGHT, this.vidMode.height());
			
		} else {
			
			int width = this.getWidth();
			int height = this.getHeight();
			glfwSetWindowMonitor(this.id, 0, 0, 0, width, height, VidModes.getPrimaryMonitorVidMode().refreshRate());
			this.center();
			options.set(Options.OPTION_DISPLAY_WIDTH, width);
			options.set(Options.OPTION_DISPLAY_HEIGHT, height);
		}
		
		options.set(Options.OPTION_FULLSCREEN, fullscreen);
	}
	
	/**
	 * Sets the aspect ratio.
	 * @param numer number for the X axis (example: 16)
	 * @param denom number for the Y axis (example: 9)
	 * @since 31.07.2018/0.1.0
	 */
	public void setAspectRatio(int numer, int denom) {
		
		glfwSetWindowAspectRatio(this.id, numer, denom);
	}
	
	/**
	 * Sets the window position.
	 * @param x position on the X axis in pixel
	 * @param y position on the Y axis in pixel
	 * @since 31.07.2018/0.1.0
	 */
	public void setPosition(int x, int y) {
		
		glfwSetWindowPos(this.id, x, y);
	}
	
	/**
	 * Enables or disables the window frame.
	 * Important for borderless window fullscreen which is not the actual fullscreen mode.
	 * @param decorated {@code true} to enable the window frame, {@code false} to disable it
	 * @since 31.07.2018/0.1.0
	 */
	public void setDecorated(boolean decorated) {
		
		glfwSetWindowAttrib(this.id, GLFW_DECORATED, decorated ? GLFW_TRUE : GLFW_FALSE);
	}
	
	/**
	 * Sets the window title.
	 * @param title the window title
	 * @since 31.07.2018/0.1.0
	 */
	public void setTitle(String title) {
		
		glfwSetWindowTitle(this.id, title);
		this.title = title;
	}
	
	/**
	 * Sets the minimum size the window can have.
	 * @param minWidth minimum width in pixels
	 * @param minHeight minimum height in pixels
	 * @since 31.07.2018/0.1.0
	 */
	public void setMinimumSize(int minWidth, int minHeight) {
		
		this.minWidth = minWidth;
		this.minHeight = minHeight;
		glfwSetWindowSizeLimits(this.id, minWidth, minHeight, this.maxWidth, this.maxHeight);
	}
	
	/**
	 * Sets the maximum size the window can have.
	 * @param maxWidth maximum width in pixels
	 * @param maxHeight maximum height in pixels
	 * @since 31.07.2018/0.1.0
	 */
	public void setMaximumSize(int maxWidth, int maxHeight) {
		
		this.maxWidth = maxWidth;
		this.maxHeight = maxHeight;
		glfwSetWindowSizeLimits(this.id, this.minWidth, this.minHeight, maxWidth, maxHeight);
	}
	
	/**
	 * Sets the window size.
	 * @param width width in pixels
	 * @param height height in pixels
	 * @since 31.07.2018/0.1.0
	 */
	public void setSize(int width, int height) {
		
		glfwSetWindowSize(this.id, width, height);
		Options options = this.game.getOptions();
		options.set(Options.OPTION_DISPLAY_WIDTH, width);
		options.set(Options.OPTION_DISPLAY_HEIGHT, height);
	}
	
	/**
	 * Enables or disables the ability to resize the window.
	 * @param resizable {@code true} to enable resizing, {@code false} to disable it
	 * @since 31.07.2018/0.1.0
	 */
	public void setResizable(boolean resizable) {
		
		glfwSetWindowAttrib(this.id, GLFW_RESIZABLE, resizable ? GLFW_TRUE : GLFW_FALSE);
	}
	
	/**
	 * Sets the visibility of the window.
	 * @param visible {@code true} to make the window visible, {@code false} to make it unvisible
	 * @since 31.07.2018/0.1.0
	 */
	public void setVisible(boolean visible) {
		
		if(visible) {
			
			glfwShowWindow(this.id);
			
		} else {
			
			glfwHideWindow(this.id);
		}
	}
	
	/**
	 * Requests the window to close.
	 * Will try to close the window at the start of the next frame.
	 * @since 31.07.2018/0.1.0
	 */
	public void requestClose() {
		
		glfwSetWindowShouldClose(this.id, true);
	}
	
	/**
	 * Updates the viewport. The viewport is the part of the display in which the game is rendered.
	 * This method should be called every time the size the of the window changes or when switching between window mode and fullscreen.
	 * @since 31.07.2018/0.1.0
	 */
	public void updateViewPort() {

		GL11.glViewport(0, 0, this.getFrameBufferWidth(), this.getFrameBufferHeight());
		
		int[] width = new int[1];
		int[] height = new int[1];
		glfwGetFramebufferSize(this.id, width, height);
		
		int factor = MathUtil.smallestCommonFactor(width[0], height[0]);
		int numer = width[0] / factor;
		int denom = height[0] / factor;
		glfwSetWindowAspectRatio(this.id, numer, denom);
	}
	
	/**
	 * Swaps the buffers. Will swap the image that was painted in the background with the image that is currently displayed and use the image
	 * that was just currently displayed as canvas for the next background image. This has to be called every frame.
	 * @since 31.07.2018/0.1.0
	 */
	public void swapBuffers() {
		
		glfwSwapBuffers(this.id);
	}
	
	/**
	 * Makes the current GL context use this display for rendering.
	 * @since 31.07.2018/0.1.0
	 */
	public void makeContextCurrent() {
		
		glfwMakeContextCurrent(this.id);
	}
	
	/**
	 * Positions the window at the center of the primary screen.
	 * @since 31.07.2018/0.1.0
	 */
	public void center() {
		
		GLFWVidMode primaryMonitorVidMode = VidModes.getPrimaryMonitorVidMode();
		int x = (primaryMonitorVidMode.width() - this.getWidth()) / 2;
		int y = (primaryMonitorVidMode.height() - this.getHeight()) / 2;
		this.setPosition(x, y);
	}
	
	/**
	 * Polls the window events.
	 * It is really important to call this method at the end of every frame.
	 * @since 31.07.2018/0.1.0
	 */
	public void poll() {
		
		glfwPollEvents();
	}
	
	/**
	 * @return the window width in pixels
	 * @since 31.07.2018/0.1.0
	 */
	public int getWidth() {
		
		int[] widthBuffer = new int[1];
		glfwGetWindowSize(this.id, widthBuffer, new int[1]);
		return widthBuffer[0];
	}
	
	/**
	 * @return the window height in pixels
	 * @since 31.07.2018/0.1.0
	 */
	public int getHeight() {
		
		int[] heightBuffer = new int[1];
		glfwGetWindowSize(this.id, new int[1], heightBuffer);
		return heightBuffer[0];
	}
	
	/**
	 * @return the display width in pixels
	 * @since 31.07.2018/0.1.0
	 */
	public int getFrameBufferWidth() {
		
		int[] widthBuffer = new int[1];
		glfwGetFramebufferSize(this.id, widthBuffer, new int[1]);
		return widthBuffer[0];
	}

	/**
	 * @return the display height in pixels
	 * @since 31.07.2018/0.1.0
	 */
	public int getFrameBufferHeight() {
		
		int[] heightBuffer = new int[1];
		glfwGetFramebufferSize(this.id, new int[1], heightBuffer);
		return heightBuffer[0];
	}
	
	/**
	 * @return {@code true} if vertical synchronization is enabled, else {@code false}
	 * @since 31.07.2018/0.1.0
	 */
	public boolean isVSync() {
		
		return this.vsync;
	}

	/**
	 * @return the minimum window width in pixels
	 * @since 31.07.2018/0.1.0
	 */
	public int getMinWidth() {
		
		return this.minWidth;
	}

	/**
	 * @return the minimum window height in pixels
	 * @since 31.07.2018/0.1.0
	 */
	public int getMinHeight() {
		
		return this.minHeight;
	}

	/**
	 * @return the maximum window width in pixels
	 * @since 31.07.2018/0.1.0
	 */
	public int getMaxWidth() {
		
		return this.maxWidth;
	}

	/**
	 * @return the maximum window height in pixels
	 * @since 31.07.2018/0.1.0
	 */
	public int getMaxHeight() {
		
		return this.maxHeight;
	}
	
	/**
	 * @return the window title
	 * @since 31.07.2018/0.1.0
	 */
	public String getTitle() {
		
		return this.title;
	}
	
	/**
	 * @return {@code true} if it was requested for the window to be closed at the start of the next frame, else {@code false}
	 * @since 31.07.2018/0.1.0
	 */
	public boolean isCloseRequested() {
		
		return glfwWindowShouldClose(this.id);
	}
	
	/**
	 * @return {@code true} if this window is visible, else {@code false}
	 * @since 31.07.2018/0.1.0
	 */
	public boolean isVisible() {
		
		return glfwGetWindowAttrib(this.id, GLFW_VISIBLE) == GLFW_TRUE;
	}
	
	/**
	 * @return {@code true} if this window is resizable, else {@code false}
	 * @since 31.07.2018/0.1.0
	 */
	public boolean isResizable() {
		
		return glfwGetWindowAttrib(this.id, GLFW_RESIZABLE) == GLFW_TRUE;
	}

	/**
	 * @return the window position on the X axis in pixels
	 * @since 31.07.2018/0.1.0
	 */
	public int getX() {
		
		int[] xBuffer = new int[1];
		glfwGetWindowPos(this.id, xBuffer, new int[1]);
		return xBuffer[0];
	}

	/**
	 * @return the window position on the Y axis in pixels
	 * @since 31.07.2018/0.1.0
	 */
	public int getY() {
		
		int[] yBuffer = new int[1];
		glfwGetWindowPos(this.id, new int[1], yBuffer);
		return yBuffer[0];
	}

	/**
	 * @return {@code true} if the window frame is enabled, else {@code false}
	 * @since 31.07.2018/0.1.0
	 */
	public boolean isDecorated() {
		
		return glfwGetWindowAttrib(this.id, GLFW_DECORATED) == GLFW_TRUE;
	}

	/**
	 * @return {@code true} if the window is currently in fullscreen mode, else {@code false}
	 * @since 31.07.2018/0.1.0
	 */
	public boolean isFullscreen() {
		
		return this.fullscreen;
	}
	
	/**
	 * @return the ID of this display
	 * @since 13.08.2018/0.1.0
	 */
	public long getID() {
		
		return this.id;
	}
	
	@Override
	public List<Consumer<DisplayEvent>>[] getListeners() {

		return listeners;
	}
}
