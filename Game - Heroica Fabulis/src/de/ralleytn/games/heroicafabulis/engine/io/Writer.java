package de.ralleytn.games.heroicafabulis.engine.io;

import java.io.IOException;
import java.io.OutputStream;

/**
 * 
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 12.08.2018/0.1.0
 * @since 11.08.2018/0.1.0
 * @param <T>
 */
public abstract class Writer<T> {
	
	/**
	 * 
	 * @param outputStream
	 * @param data
	 * @throws IOException
	 * @since 11.08.2018/0.1.0
	 */
	public abstract void write(OutputStream outputStream, T data) throws IOException;
}
