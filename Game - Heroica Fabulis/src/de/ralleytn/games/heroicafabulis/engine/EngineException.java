package de.ralleytn.games.heroicafabulis.engine;

/**
 * {@linkplain Exception} that is thrown by some methods in the engine if no other fitting {@linkplain Exception} is.
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 31.07.2018
 * @since 31.07.2018
 */
public class EngineException extends Exception {

	private static final long serialVersionUID = -9173733217064517398L;

	/**
	 * @param message the message
	 * @since 31.07.2018
	 */
	public EngineException(String message) {
		
		super(message);
	}
	
	/**
	 * @param exception the {@linkplain Exception} that should be wrapped inside this {@linkplain Exception}
	 * @since 31.07.2018
	 */
	public EngineException(Exception exception) {
		
		// I can't just use the constructor of the super class that would take in the exception
		// because It would add an unnecessary stack trace
		
		super(exception.getMessage());
		this.setStackTrace(exception.getStackTrace());
	}
}
