package de.ralleytn.engine.caveman;

/**
 * Should be implemented by all classes that represent an object where a lot of setters are present.
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 04.09.2018/0.4.0
 * @since 25.08.2018/0.3.0
 * @param <T>
 */
public interface Copyable<T> {
	
	/**
	 * Creates a copy.
	 * @return the created copy
	 * @since 25.08.2018/0.3.0
	 */
	public T copy();
}
