package de.ralleytn.games.heroicafabulis.engine.io;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ExecutorService;
import java.util.function.Consumer;

import de.ralleytn.games.heroicafabulis.engine.Engine;

/**
 * Represents a very abstract reader.
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 21.08.2018/0.2.0
 * @since 04.08.2018/0.1.0
 * @param <T> the result type of whatever should be read
 */
public abstract class Reader<T> {

	private static final ExecutorService EXECUTOR = Engine.createExecutor();
	
	/**
	 * Reads {@code T} from the given {@linkplain InputStream}.
	 * @param inputStream the {@linkplain InputStream} which should contain the data of {@code T}
	 * @return the read result
	 * @throws IOException if an I/O error occurs while reading
	 * @since 04.08.2018/0.1.0
	 */
	public abstract T read(InputStream inputStream) throws IOException;
	
	/**
	 * Executes {@link #read(InputStream)} in the thread-pool for reading from the hard drive.
	 * @param inputStream the {@linkplain InputStream} contains the data of {@code T}
	 * @param onError is executed when an array occurs
	 * @param onFinish is executed when the operation finished without errors
	 * @since 21.08.2018/0.2.0
	 */
	public final void readInBackground(InputStream inputStream, Consumer<IOException> onError, Consumer<T> onFinish) {
		
		EXECUTOR.execute(() -> {
			
			try {
				
				onFinish.accept(this.read(inputStream));
				
			} catch(IOException exception) {
				
				onError.accept(exception);
			}
		});
	}
}
