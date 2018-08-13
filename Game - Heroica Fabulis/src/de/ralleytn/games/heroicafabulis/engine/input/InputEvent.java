package de.ralleytn.games.heroicafabulis.engine.input;

/**
 * 
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 13.08.2018/0.1.0
 * @since 13.08.2018/0.1.0
 */
public abstract class InputEvent {

	private final Object source;
	
	/**
	 * 
	 * @param source
	 * @since 13.08.2018/0.1.0
	 */
	public InputEvent(Object source) {
		
		this.source = source;
	}
	
	/**
	 * 
	 * @return
	 * @since 13.08.2018/0.1.0
	 */
	public final Object getSource() {
		
		return this.source;
	}
}
