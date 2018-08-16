package de.ralleytn.games.heroicafabulis.engine.input;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import de.ralleytn.games.heroicafabulis.engine.Controller;
import de.ralleytn.games.heroicafabulis.engine.Display;
import de.ralleytn.games.heroicafabulis.engine.Game;

import static org.lwjgl.glfw.GLFW.*;

/**
 * 
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 16.08.2018/0.1.0
 * @since 13.08.2018/0.1.0
 */
public class MouseController implements Controller<MouseEvent> {

	// When it comes to input performance has to have more priority than design.
	// I decided against a Map because it would add another layer that would slow everything down quite significantly.
	
	/** @since 13.08.2018/0.1.0 */ public static final int EVENT_ENTER = 0;
	/** @since 13.08.2018/0.1.0 */ public static final int EVENT_LEAVE = 1;
	/** @since 13.08.2018/0.1.0 */ public static final int EVENT_MOVE = 2;
	/** @since 13.08.2018/0.1.0 */ public static final int EVENT_DRAG = 3;
	/** @since 13.08.2018/0.1.0 */ public static final int EVENT_PRESS = 4;
	/** @since 13.08.2018/0.1.0 */ public static final int EVENT_RELEASE = 5;
	/** @since 13.08.2018/0.1.0 */ public static final int EVENT_SCROLL = 6;
	/** @since 13.08.2018/0.1.0 */ public static final int EVENT_REPEAT = 7;
	
	
	@SuppressWarnings("unchecked")
	private final List<Consumer<MouseEvent>>[] listeners = new List[] {
			
		new ArrayList<>(),	// ENTER
		new ArrayList<>(),	// LEAVE
		new ArrayList<>(),	// MOVE
		new ArrayList<>(),	// DRAG
		new ArrayList<>(),	// PRESS
		new ArrayList<>(),	// RELEASE
		new ArrayList<>(),	// SCROLL
		new ArrayList<>()	// REPEAT
	};
	private final Game game;
	private boolean drag;
	
	/**
	 * 
	 * @param display
	 * @since 13.08.2018/0.1.0
	 */
	public MouseController(Game game) {
		
		this.game = game;
		Display display = game.getDisplay();
		long windowID = display.getID();
		glfwSetCursorEnterCallback(windowID, (window, entered) -> this.trigger(entered ? EVENT_ENTER : EVENT_LEAVE, new MouseEvent(display)));
		glfwSetCursorPosCallback(windowID, (window, x, y) -> this.trigger(this.drag ? EVENT_DRAG : EVENT_MOVE, new MouseEvent(display, (int)x, (int)y, 0, 0)));
		glfwSetMouseButtonCallback(windowID, (window, button, action, mods) -> {

			MouseEvent event = new MouseEvent(display, 0, 0, button, mods);

			if(action == GLFW_PRESS) {
					
				this.drag = (button == GLFW_MOUSE_BUTTON_LEFT);
				this.trigger(EVENT_PRESS, event);
					
			} else if(action == GLFW_RELEASE) {
					
				this.drag = false;
				this.trigger(EVENT_RELEASE, event);
				
			} else if(action == GLFW_REPEAT) {
					
				this.trigger(EVENT_REPEAT, event);
			}
		});
		glfwSetScrollCallback(windowID, (window, xOffset, yOffset) -> this.trigger(EVENT_SCROLL, new MouseEvent(display, yOffset)));
	}
	
	/**
	 * 
	 * @param x
	 * @param y
	 * @since 13.08.2018/0.1.0
	 */
	public void setCursorPosition(int x, int y) {
		
		glfwSetCursorPos(this.game.getDisplay().getID(), x, y);
	}
	
	/**
	 * 
	 * @param visible
	 * @since 13.08.2018/0.1.0
	 */
	public void setCursorVisible(boolean visible) {
		
		glfwSetInputMode(this.game.getDisplay().getID(), GLFW_CURSOR, visible ? GLFW_CURSOR_NORMAL : GLFW_CURSOR_HIDDEN);
	}
	
	/**
	 * @return the position of the mouse cursor on the x axis relative to the game window
	 * @since 13.08.2018/0.1.0
	 */
	public int getCursorX() {
		
		double[] xBuffer = new double[1];
		glfwGetCursorPos(this.game.getDisplay().getID(), xBuffer, new double[1]);
		return (int)xBuffer[0];
	}

	/**
	 * @return the position of the mouse cursor on the y axis relative to the game window
	 * @since 13.08.2018/0.1.0
	 */
	public int getCursorY() {

		double[] yBuffer = new double[1];
		glfwGetCursorPos(this.game.getDisplay().getID(), new double[1], yBuffer);
		return (int)yBuffer[0];
	}
	
	/**
	 * @return {@code true} if the cursor is visible
	 * @since 13.08.2018/0.1.0
	 */
	public boolean isCursorVisible() {
		
		return glfwGetInputMode(this.game.getDisplay().getID(), GLFW_CURSOR) == GLFW_CURSOR_NORMAL;
	}
	
	/**
	 * 
	 * @param button
	 * @return
	 * @since 14.08.2018/0.1.0
	 */
	public boolean isButtonDown(int button) {
		
		return glfwGetMouseButton(this.game.getDisplay().getID(), button) == GLFW_PRESS;
	}

	@Override
	public List<Consumer<MouseEvent>>[] getListeners() {

		return this.listeners;
	}
}
