package de.ralleytn.games.heroicafabulis.engine.input;

/**
 * Represents an abstract input event.
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 13.08.2018/0.1.0
 * @since 13.08.2018/0.1.0
 */
public abstract class InputEvent {

	private final Object source;
	
	/**
	 * @param source the cause for this event
	 * @since 13.08.2018/0.1.0
	 */
	public InputEvent(Object source) {
		
		this.source = source;
	}
	
	/**
	 * @return the cause for this event
	 * @since 13.08.2018/0.1.0
	 */
	public final Object getSource() {
		
		return this.source;
	}
}
