package de.ralleytn.games.heroicafabulis.engine;

import java.util.function.Consumer;

/**
 * Represents an abstract controller.
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 16.08.2018/0.1.0
 * @since 13.08.2018/0.1.0
 * @param <T> the event this controller works with
 */
public interface Controller<T extends Event> {

	/**
	 * Adds a new listener for the given event.
	 * @param event the event ID
	 * @param listener the listener
	 * @since 13.08.2018/0.1.0
	 */
	public void addListener(int event, Consumer<T> listener);
	
	/**
	 * Removes a listener from the given event.
	 * @param event the event ID
	 * @param listener the listener
	 * @since 13.08.2018/0.1.0
	 */
	public void removeListener(int event, Consumer<T> listener);
}
