package de.ralleytn.games.heroicafabulis.engine;

/**
 * This interface is supposed to be implemented by all classes that have resources that have to be disposed of manually.
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 31.07.2018/0.1.0
 * @since 31.07.2018/0.1.0
 */
public interface Disposable {

	/**
	 * Disposes of the resources that the garbage collector ignores.
	 * @since 31.07.2018/0.1.0
	 */
	public void dispose();
}
