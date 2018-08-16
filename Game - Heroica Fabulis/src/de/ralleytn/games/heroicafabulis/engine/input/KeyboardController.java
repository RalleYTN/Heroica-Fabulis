package de.ralleytn.games.heroicafabulis.engine.input;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import static org.lwjgl.glfw.GLFW.*;

import de.ralleytn.games.heroicafabulis.engine.Controller;
import de.ralleytn.games.heroicafabulis.engine.Display;
import de.ralleytn.games.heroicafabulis.engine.Game;

/**
 * Class with methods to listen for inputs from the keyboard.
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 16.08.2018/0.1.0
 * @since 13.08.2018/0.1.0
 */
public class KeyboardController implements Controller<KeyboardEvent> {

	// When it comes to input performance has to have more priority than design.
	// I decided against a Map because it would add another layer that would slow everything down quite significantly.
	
	/** @since 13.08.2018/0.1.0 */ public static final int EVENT_PRESS = 0;
	/** @since 13.08.2018/0.1.0 */ public static final int EVENT_RELEASE = 1;
	/** @since 13.08.2018/0.1.0 */ public static final int EVENT_REPEAT = 2;
	
	@SuppressWarnings("unchecked")
	private final List<Consumer<KeyboardEvent>>[] listeners = new List[] {
		new ArrayList<>(),	// PRESS
		new ArrayList<>(),	// RELEASE
		new ArrayList<>()	// REPEAT
	};
	private final Game game;
	
	/**
	 * @param game the instance of {@linkplain Game} this controller belongs to
	 * @since 13.08.2018/0.1.0
	 */
	public KeyboardController(Game game) {
		
		this.game = game;
		Display display = game.getDisplay();
		long windowID = display.getID();
		
		glfwSetKeyCallback(windowID, (id, key, scanCode, action, mods) -> {
			
			KeyboardEvent event = new KeyboardEvent(display, key, scanCode, mods);
			
			       if(action == GLFW_PRESS) {this.trigger(EVENT_PRESS, event);
			} else if(action == GLFW_RELEASE) {this.trigger(EVENT_RELEASE, event);
			} else if(action == GLFW_REPEAT) {this.trigger(EVENT_REPEAT, event);
			}
		});
	}
	
	/**
	 * @param key the key ID
	 * @return the name for the given key or an empty {@linkplain String} if there is no name for that key
	 * @since 14.08.2018/0.1.0
	 */
	public String getKeyName(int key) {
		
		String name = glfwGetKeyName(key, glfwGetKeyScancode(key));
		return name != null ? name : "";
	}
	
	/**
	 * @param key the key ID
	 * @return {@code true} if the given key is currently pushed down, else {@code false}
	 * @since 14.08.2018/0.1.0
	 */
	public boolean isKeyDown(int key) {
		
		return glfwGetKey(this.game.getDisplay().getID(), key) == GLFW_PRESS;
	}

	@Override
	public List<Consumer<KeyboardEvent>>[] getListeners() {

		return this.listeners;
	}
}
