package de.ralleytn.games.heroicafabulis.engine.io;

import java.io.IOException;
import java.io.InputStream;

/**
 * Represents a very abstract reader.
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 04.08.2018/0.1.0
 * @since 04.08.2018/0.1.0
 * @param <T> the result type of whatever should be read
 */
public abstract class Reader<T> {

	/**
	 * Reads {@code T} from the given {@linkplain InputStream}.
	 * @param inputStream the {@linkplain InputStream} which should contain the data of {@code T}
	 * @return the read result
	 * @throws IOException if an I/O error occurs while reading
	 * @since 04.08.2018/0.1.0
	 */
	public abstract T read(InputStream inputStream) throws IOException;
}
