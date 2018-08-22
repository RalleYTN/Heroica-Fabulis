package de.ralleytn.games.heroicafabulis.engine.io;

import java.io.IOException;
import java.io.OutputStream;
import java.util.concurrent.ExecutorService;
import java.util.function.Consumer;

import de.ralleytn.games.heroicafabulis.engine.Engine;

/**
 * Represents a very abstract writer.
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 21.08.2018/0.2.0
 * @since 11.08.2018/0.1.0
 * @param <T> the type that should be written
 */
public abstract class Writer<T> {
	
	private static final ExecutorService EXECUTOR = Engine.createExecutor();
	
	/**
	 * Writes the given data on the {@linkplain OutputStream}.
	 * @param outputStream the {@linkplain OutputStream}
	 * @param data the data to write
	 * @throws IOException if an I/O error occurred
	 * @since 11.08.2018/0.1.0
	 */
	public abstract void write(OutputStream outputStream, T data) throws IOException;
	
	/**
	 * Executed {@link #write(OutputStream, Object)} in the thread-pool for writing to the hard drive.
	 * @param outputStream the {@linkplain OutputStream}
	 * @param data the data to write
	 * @param onError is executed when an error occurred
	 * @param onFinish is executed when the operation finished without errors
	 * @since 0.2.0
	 */
	public final void writeInBackground(OutputStream outputStream, T data, Consumer<IOException> onError, Runnable onFinish) {
		
		EXECUTOR.execute(() ->  {
			
			try {
				
				this.write(outputStream, data);
				onFinish.run();
				
			} catch(IOException exception) {
				
				onError.accept(exception);
			}
		});
	}
}
