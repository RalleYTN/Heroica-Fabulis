package de.ralleytn.games.heroicafabulis.engine.input;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import static org.lwjgl.glfw.GLFW.*;

import de.ralleytn.games.heroicafabulis.engine.Display;
import de.ralleytn.games.heroicafabulis.engine.Game;

/**
 * 
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 14.08.2018/0.1.0
 * @since 13.08.2018/0.1.0
 */
public class KeyboardController extends InputController<KeyboardEvent> {

	/** @since 13.08.2018/0.1.0 */ public static final int EVENT_PRESS = 1;
	/** @since 13.08.2018/0.1.0 */ public static final int EVENT_RELEASE = 2;
	/** @since 13.08.2018/0.1.0 */ public static final int EVENT_REPEAT = 3;
	
	private Game game;
	private List<Consumer<KeyboardEvent>> onPress = new ArrayList<>();
	private List<Consumer<KeyboardEvent>> onRelease = new ArrayList<>();
	private List<Consumer<KeyboardEvent>> onRepeat = new ArrayList<>();
	
	public KeyboardController(Game game) {
		
		this.game = game;
		Display display = game.getDisplay();
		long windowID = display.getID();
		
		glfwSetKeyCallback(windowID, (id, key, scancode, action, mods) -> {
			
			if(action == GLFW_PRESS) {
				
				
			} else if(action == GLFW_RELEASE) {
				
				
			} else if(action == GLFW_REPEAT) {
				
				
			}
		});
	}
	
	@Override
	public void addListener(int event, Consumer<KeyboardEvent> listener) {
		
		
	}

	@Override
	public void removeListener(int event, Consumer<KeyboardEvent> listener) {
		
		
	}
	
	/**
	 * 
	 * @param key
	 * @return
	 * @since 14.08.2018/0.1.0
	 */
	public String getKeyName(int key) {
		
		String name = glfwGetKeyName(key, glfwGetKeyScancode(key));
		return name != null ? name : "";
	}
}
