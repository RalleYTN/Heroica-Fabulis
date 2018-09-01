package de.ralleytn.engine.caveman.io;

import java.io.IOException;

/**
 * This interface is meant to be implemented by classes that have a {@linkplain File} as private field from which all of the data can be loaded.
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 10.08.2018/0.1.0
 * @since 10.08.2018/0.1.0
 */
public interface Loadable {

	/**
	 * Loads the data.
	 * @throws IOException if the {@linkplain File} could not be read
	 * @since 10.08.2018/0.1.0
	 */
	public void load() throws IOException;
}
