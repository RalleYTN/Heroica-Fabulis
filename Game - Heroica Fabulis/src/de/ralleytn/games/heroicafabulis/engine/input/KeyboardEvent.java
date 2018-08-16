package de.ralleytn.games.heroicafabulis.engine.input;

import org.lwjgl.glfw.GLFW;

import de.ralleytn.games.heroicafabulis.engine.Event;

/**
 * Event that can be triggered by the keyboard.
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 16.08.2018/0.1.0
 * @since 13.08.2018/0.1.0
 */
public class KeyboardEvent extends Event {

	private int key;
	private int scanCode;
	private int mods;
	
	/**
	 * @param source the cause for this event
	 * @param key the key ID
	 * @param scanCode the scan code for the key
	 * @param mods a bit sequence containing modifier flags
	 * @since 16.08.2018/0.1.0
	 */
	public KeyboardEvent(Object source, int key, int scanCode, int mods) {
		
		super(source);
		
		this.key = key;
		this.scanCode = scanCode;
		this.mods = mods;
	}
	
	/**
	 * @return the key ID
	 * @since 14.08.2018/0.1.0
	 */
	public int getKey() {
		
		return this.key;
	}
	
	/**
	 * @return the scan code
	 * @since 14.08.2018/0.1.0
	 */
	public int getScanCode() {
		
		return this.scanCode;
	}
	
	/**
	 * @return a bit sequence containing modifier flags
	 * @since 14.08.2018/0.1.0
	 */
	public int getMods() {
		
		return this.mods;
	}
	
	/**
	 * @return the name of the associated key or an empty string if there is no name for that key
	 * @since 14.08.2018/0.1.0
	 */
	public String getKeyName() {
		
		String name = GLFW.glfwGetKeyName(this.key, this.scanCode);
		return name != null ? name : "";
	}
}
