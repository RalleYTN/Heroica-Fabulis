package de.ralleytn.games.heroicafabulis.engine.input;

import java.util.function.Consumer;

/**
 * 
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 13.08.2018/0.1.0
 * @since 13.08.2018/0.1.0
 * @param <T>
 */
public abstract class InputController<T extends InputEvent> {

	/**
	 * 
	 * @param event
	 * @param listener
	 * @since 13.08.2018/0.1.0
	 */
	public abstract void addListener(int event, Consumer<T> listener);
	
	/**
	 * 
	 * @param event
	 * @param listener
	 * @since 13.08.2018/0.1.0
	 */
	public abstract void removeListener(int event, Consumer<T> listener);
}
