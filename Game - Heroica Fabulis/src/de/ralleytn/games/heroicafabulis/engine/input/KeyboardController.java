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
	
	/** @since 13.08.2018/0.1.0 */ public static final int EVENT_PRESS = 1;
	/** @since 13.08.2018/0.1.0 */ public static final int EVENT_RELEASE = 2;
	/** @since 13.08.2018/0.1.0 */ public static final int EVENT_REPEAT = 3;
	
	private Game game;
	private List<Consumer<KeyboardEvent>> onPress = new ArrayList<>();
	private List<Consumer<KeyboardEvent>> onRelease = new ArrayList<>();
	private List<Consumer<KeyboardEvent>> onRepeat = new ArrayList<>();
	
	/**
	 * @param game the instance of {@linkplain Game} this controller belongs to
	 * @since 13.08.2018/0.1.0
	 */
	public KeyboardController(Game game) {
		
		this.game = game;
		Display display = game.getDisplay();
		long windowID = display.getID();
		
		glfwSetKeyCallback(windowID, (id, key, scanCode, action, mods) -> {
			
			KeyboardEvent event = new KeyboardEvent(display);
			event.setKey(key);
			event.setScanCode(scanCode);
			event.setMods(mods);
			
			if(action == GLFW_PRESS) {
				
				for(Consumer<KeyboardEvent> listener : this.onPress) {
					
					listener.accept(event);
				}
				
			} else if(action == GLFW_RELEASE) {
				
				for(Consumer<KeyboardEvent> listener : this.onRelease) {
					
					listener.accept(event);
				}
				
			} else if(action == GLFW_REPEAT) {
				
				for(Consumer<KeyboardEvent> listener : this.onRepeat) {
					
					listener.accept(event);
				}
			}
		});
	}
	
	@Override
	public void addListener(int event, Consumer<KeyboardEvent> listener) {
		
		switch(event) {
		
			case EVENT_PRESS:
				this.onPress.add(listener);
				break;
			case EVENT_RELEASE:
				this.onRelease.add(listener);
				break;
			case EVENT_REPEAT:
				this.onRepeat.add(listener);
				break;
		}
	}

	@Override
	public void removeListener(int event, Consumer<KeyboardEvent> listener) {
		
		switch(event) {
		
			case EVENT_PRESS:
				this.onPress.remove(listener);
				break;
			case EVENT_RELEASE:
				this.onRelease.remove(listener);
				break;
			case EVENT_REPEAT:
				this.onRepeat.remove(listener);
				break;
		}
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
}
