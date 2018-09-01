package de.ralleytn.engine.caveman.io;

import java.io.IOException;

/**
 * This interface is meant to be implemented by classes that have a {@linkplain File} as private field to which all of the data can be saved.
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 17.08.2018/0.2.0
 * @since 17.08.2018/0.2.0
 */
public interface Savable {

	/**
	 * Saves the data.
	 * @throws IOException if there was an error while writing to the {@linkplain File} instance
	 * @since 17.08.2018/0.2.0
	 */
	public void save() throws IOException;
}
