package de.ralleytn.engine.caveman;

/**
 * This interface is supposed to be implemented by all classes that have resources that have to be disposed of manually.
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 04.09.2018/0.4.0
 * @since 31.07.2018/0.1.0
 */
public interface Disposable {

	/**
	 * Disposes of the resources that the garbage collector ignores.
	 * @since 31.07.2018/0.1.0
	 */
	public void dispose();
	
	/**
	 * 
	 * @return
	 * @since 04.09.2018/0.4.0
	 */
	public boolean isDisposed();
}
