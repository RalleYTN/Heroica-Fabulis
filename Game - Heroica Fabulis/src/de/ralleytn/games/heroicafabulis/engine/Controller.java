package de.ralleytn.games.heroicafabulis.engine;

import java.util.List;
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
	 * @param eventName the event ID
	 * @param listener the listener
	 * @since 13.08.2018/0.1.0
	 */
	public default void addListener(int eventName, Consumer<T> listener) {
		
		this.getListeners()[eventName].add(listener);
	}
	
	/**
	 * Removes a listener from the given event.
	 * @param eventName the event ID
	 * @param listener the listener
	 * @since 13.08.2018/0.1.0
	 */
	public default void removeListener(int eventName, Consumer<T> listener) {
		
		this.getListeners()[eventName].remove(listener);
	}
	
	/**
	 * Triggers an event.
	 * @param eventName the event ID
	 * @param event the event
	 * @since 16.08.2018/0.1.0
	 */
	public default void trigger(int eventName, T event) {
		
		for(Consumer<T> listener : this.getListeners()[eventName]) {
			
			listener.accept(event);
		}
	}
	
	/**
	 * @return the array with all of the lists containing listeners
	 * @since 13.08.2018/0.1.0
	 */
	public List<Consumer<T>>[] getListeners();
}
