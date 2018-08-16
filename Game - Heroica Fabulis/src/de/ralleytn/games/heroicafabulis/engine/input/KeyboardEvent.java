package de.ralleytn.games.heroicafabulis.engine.input;

import org.lwjgl.glfw.GLFW;

import de.ralleytn.games.heroicafabulis.engine.Event;

/**
 * 
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 16.08.2018/0.1.0
 * @since 13.08.2018/0.1.0
 */
public class KeyboardEvent extends Event {

	private int key;
	private int scanCode;
	private int mods;
	
	/**
	 * 
	 * @param source
	 * @param key
	 * @param scanCode
	 * @param mods
	 * @since 16.08.2018/0.1.0
	 */
	public KeyboardEvent(Object source, int key, int scanCode, int mods) {
		
		super(source);
		
		this.key = key;
		this.scanCode = scanCode;
		this.mods = mods;
	}
	
	/**
	 * 
	 * @return
	 * @since 14.08.2018/0.1.0
	 */
	public int getKey() {
		
		return this.key;
	}
	
	/**
	 * 
	 * @return
	 * @since 14.08.2018/0.1.0
	 */
	public int getScanCode() {
		
		return this.scanCode;
	}
	
	/**
	 * 
	 * @return
	 * @since 14.08.2018/0.1.0
	 */
	public int getMods() {
		
		return this.mods;
	}
	
	/**
	 * 
	 * @return
	 * @since 14.08.2018/0.1.0
	 */
	public String getKeyName() {
		
		String name = GLFW.glfwGetKeyName(this.key, this.scanCode);
		return name != null ? name : "";
	}
}
