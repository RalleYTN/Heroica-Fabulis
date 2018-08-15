package de.ralleytn.games.heroicafabulis.engine.io;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Represents a very abstract writer.
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 12.08.2018/0.1.0
 * @since 11.08.2018/0.1.0
 * @param <T> the type that should be written
 */
public abstract class Writer<T> {
	
	/**
	 * Writes the given data on the {@linkplain OutputStream}.
	 * @param outputStream the {@linkplain OutputStream}
	 * @param data the data to write
	 * @throws IOException if an I/O error occurred
	 * @since 11.08.2018/0.1.0
	 */
	public abstract void write(OutputStream outputStream, T data) throws IOException;
}
