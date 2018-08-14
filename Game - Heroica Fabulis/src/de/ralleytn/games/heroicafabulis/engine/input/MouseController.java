package de.ralleytn.games.heroicafabulis.engine.input;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import de.ralleytn.games.heroicafabulis.engine.Display;
import de.ralleytn.games.heroicafabulis.engine.Engine;
import de.ralleytn.games.heroicafabulis.engine.Errors;
import de.ralleytn.games.heroicafabulis.engine.Game;

import static org.lwjgl.glfw.GLFW.*;

/**
 * 
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 13.08.2018/0.1.0
 * @since 13.08.2018/0.1.0
 */
public class MouseController extends InputController<MouseEvent> {

	// When it comes to input performance has to have more priority than design.
	// I decided against a Map because it would add another layer that would slow everything down quite significantly.
	
	/** @since 13.08.2018/0.1.0 */ public static final int EVENT_ENTER = 1;
	/** @since 13.08.2018/0.1.0 */ public static final int EVENT_LEAVE = 2;
	/** @since 13.08.2018/0.1.0 */ public static final int EVENT_MOVE = 3;
	/** @since 13.08.2018/0.1.0 */ public static final int EVENT_DRAG = 4;
	/** @since 13.08.2018/0.1.0 */ public static final int EVENT_PRESS = 5;
	/** @since 13.08.2018/0.1.0 */ public static final int EVENT_RELEASE = 6;
	/** @since 13.08.2018/0.1.0 */ public static final int EVENT_SCROLL = 7;
	/** @since 13.08.2018/0.1.0 */ public static final int EVENT_REPEAT = 8;
	
	private boolean drag;
	private Game game;
	private List<Consumer<MouseEvent>> onEnter = new ArrayList<>();
	private List<Consumer<MouseEvent>> onLeave = new ArrayList<>();
	private List<Consumer<MouseEvent>> onMove = new ArrayList<>();
	private List<Consumer<MouseEvent>> onDrag = new ArrayList<>();
	private List<Consumer<MouseEvent>> onPress = new ArrayList<>();
	private List<Consumer<MouseEvent>> onRelease = new ArrayList<>();
	private List<Consumer<MouseEvent>> onScroll = new ArrayList<>();
	private List<Consumer<MouseEvent>> onRepeat = new ArrayList<>();
	
	/**
	 * 
	 * @param display
	 * @since 13.08.2018/0.1.0
	 */
	public MouseController(Game game) {
		
		this.game = game;
		Display display = game.getDisplay();
		long windowID = display.getID();
		glfwSetCursorEnterCallback(windowID, (window, entered) -> {
			
			try {
				
				MouseEvent event = new MouseEvent(display);
				
				for(Consumer<MouseEvent> listener : (entered ? this.onEnter : this.onLeave)) {
					
					listener.accept(event);
				}
				
			} catch(Exception exception) {
				
				Errors.print(exception);
				Errors.prompt(exception, Errors.log(exception, Engine.getErrLogDirectory()));
			}
			
		});
		glfwSetCursorPosCallback(windowID, (window, x, y) -> {
			
			try {
				
				MouseEvent event = new MouseEvent(display);
				event.setPosition((int)x, (int)y);
				
				for(Consumer<MouseEvent> listener : (this.drag ? this.onDrag : this.onMove)) {
					
					listener.accept(event);
				}
				
			} catch(Exception exception) {
				
				Errors.print(exception);
				Errors.prompt(exception, Errors.log(exception, Engine.getErrLogDirectory()));
			}
		});
		glfwSetMouseButtonCallback(windowID, (window, button, action, mods) -> {
			
			try {
				
				MouseEvent event = new MouseEvent(display);
				event.setMods(mods);
				event.setButton(button);
				
				if(action == GLFW_PRESS) {
					
					this.drag = (button == GLFW_MOUSE_BUTTON_LEFT);
					
					for(Consumer<MouseEvent> listener : this.onPress) {
						
						listener.accept(event);
					}
					
				} else if(action == GLFW_RELEASE) {
					
					this.drag = false;
					
					for(Consumer<MouseEvent> listener : this.onRelease) {
						
						listener.accept(event);
					}
				
				} else if(action == GLFW_REPEAT) {
					
					for(Consumer<MouseEvent> listener : this.onRepeat) {
						
						listener.accept(event);
					}
				}
				
			} catch(Exception exception) {
				
				Errors.print(exception);
				Errors.prompt(exception, Errors.log(exception, Engine.getErrLogDirectory()));
			}
		});
		glfwSetScrollCallback(windowID, (window, xoffset, yoffset) -> {
			
			try {
				
				MouseEvent event = new MouseEvent(display);
				event.setScrollOffset(yoffset);
				
				for(Consumer<MouseEvent> listener : this.onScroll) {
					
					listener.accept(event);
				}
				
			} catch(Exception exception) {
				
				Errors.print(exception);
				Errors.prompt(exception, Errors.log(exception, Engine.getErrLogDirectory()));
			}
		});
	}
	
	@Override
	public void addListener(int event, Consumer<MouseEvent> listener) {
		
		switch(event) {
		
			case EVENT_MOVE:
				this.onMove.add(listener);
				break;
			case EVENT_PRESS:
				this.onPress.add(listener);
				break;
			case EVENT_RELEASE:
				this.onRelease.add(listener);
				break;
			case EVENT_SCROLL:
				this.onScroll.add(listener);
				break;
			case EVENT_DRAG:
				this.onDrag.add(listener);
				break;
			case EVENT_REPEAT:
				this.onRepeat.add(listener);
				break;
			case EVENT_ENTER:
				this.onEnter.add(listener);
				break;
			case EVENT_LEAVE:
				this.onLeave.add(listener);
				break;
		}
	}
	
	@Override
	public void removeListener(int event, Consumer<MouseEvent> listener) {
		
		switch(event) {
		
			case EVENT_MOVE:
				this.onMove.remove(listener);
				break;
			case EVENT_PRESS:
				this.onPress.remove(listener);
				break;
			case EVENT_RELEASE:
				this.onRelease.remove(listener);
				break;
			case EVENT_SCROLL:
				this.onScroll.remove(listener);
				break;
			case EVENT_DRAG:
				this.onDrag.remove(listener);
				break;
			case EVENT_REPEAT:
				this.onRepeat.remove(listener);
				break;
			case EVENT_ENTER:
				this.onEnter.remove(listener);
				break;
			case EVENT_LEAVE:
				this.onLeave.remove(listener);
				break;
		}
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
	 * @since 1.0.0
	 */
	public int getCursorX() {
		
		double[] xBuffer = new double[1];
		glfwGetCursorPos(this.game.getDisplay().getID(), xBuffer, new double[1]);
		return (int)xBuffer[0];
	}

	/**
	 * @return the position of the mouse cursor on the y axis relative to the game window
	 * @since 1.0.0
	 */
	public int getCursorY() {

		double[] yBuffer = new double[1];
		glfwGetCursorPos(this.game.getDisplay().getID(), new double[1], yBuffer);
		return (int)yBuffer[0];
	}
	
	/**
	 * @return {@code true} if the cursor is visible
	 * @since 1.0.0
	 */
	public boolean isCursorVisible() {
		
		return glfwGetInputMode(this.game.getDisplay().getID(), GLFW_CURSOR) == GLFW_CURSOR_NORMAL;
	}
}
