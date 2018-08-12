 package de.ralleytn.games.heroicafabulis.engine;

/**
 * This interface is meant to be implemented by classes that represent objects that can be bound to something.
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 04.08.2018/0.1.0
 * @since 04.08.2018/0.1.0
 */
public interface Bindable {

	/**
	 * Binds the object.
	 * @since 04.08.2018/0.1.0
	 */
	public void bind();
	
	/**
	 * Unbinds the object.
	 * @since 04.08.2018/0.1.0
	 */
	public void unbind();
}
