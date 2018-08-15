package de.ralleytn.games.heroicafabulis.engine.input;

import java.util.function.Consumer;

/**
 * Represents an abstract controller for input devices.
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 13.08.2018/0.1.0
 * @since 13.08.2018/0.1.0
 * @param <T> the input event this controller works with
 */
public abstract class InputController<T extends InputEvent> {

	/**
	 * Adds a new listener for the given event.
	 * @param event the event ID
	 * @param listener the listener
	 * @since 13.08.2018/0.1.0
	 */
	public abstract void addListener(int event, Consumer<T> listener);
	
	/**
	 * Removes a listener from the given event.
	 * @param event the event ID
	 * @param listener the listener
	 * @since 13.08.2018/0.1.0
	 */
	public abstract void removeListener(int event, Consumer<T> listener);
}
