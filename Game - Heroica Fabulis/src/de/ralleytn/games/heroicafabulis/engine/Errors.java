package de.ralleytn.games.heroicafabulis.engine;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.JOptionPane;

/**
 * Contains methods for dealing with exceptions.
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 01.08.2018
 * @since 01.08.2018
 */
public final class Errors {

	/**
	 * Made private because no instance of this class should be created.
	 * @since 01.08.2018
	 */
	private Errors() {}
	
	/**
	 * Just calls {@link Throwable#printStackTrace()}.
	 * @param exception the {@linkplain Exception}
	 * @since 01.08.2018
	 */
	public static final void print(Throwable exception) {
		
		// Only really important for when you are working in eclipse
		// It has to be printed with this method or you will not be able to fast navigate by clicking in the console!
		exception.printStackTrace();
	}
	
	/**
	 * Prompts an error message.
	 * @param exception the {@linkplain Exception}
	 * @since 01.08.2018
	 */
	public static final void prompt(Throwable exception) {
		
		String title = exception.getClass().getName();
		String message = getStackTraceForDisplay(exception);
		int option = JOptionPane.DEFAULT_OPTION;
		int type = JOptionPane.ERROR_MESSAGE;
		
		JOptionPane.showConfirmDialog(null, message, title, option, type);
	}
	
	/**
	 * Prompts an error message with the information that an error log was created and were to find it.
	 * @param exception the {@linkplain Exception}
	 * @param log {@linkplain File} object representing the created error log
	 * @since 01.08.2018
	 */
	public static final void prompt(Throwable exception, File log) {
		
		String title = exception.getClass().getName();
		int option = JOptionPane.DEFAULT_OPTION;
		int type = JOptionPane.ERROR_MESSAGE;
		
		StringBuilder message = new StringBuilder();
		message.append("An error log was created in '");
		message.append(log.getAbsolutePath());
		message.append("'");
		message.append("\n\n");
		message.append(getStackTraceForDisplay(exception));
		
		JOptionPane.showConfirmDialog(null, message, title, option, type);
	}
	
	/**
	 * Logs an error.
	 * @param exception the {@linkplain Exception}
	 * @param logDirectory the directory in which the error log should be created in
	 * @return the created log file
	 * @since 01.08.2018
	 */
	public static final File log(Throwable exception, File logDirectory) {
		
		if(!logDirectory.exists() || !logDirectory.isDirectory()) {
			
			logDirectory.mkdirs();
		}
		
		File log = new File(logDirectory, String.format("log-%s.txt", System.currentTimeMillis()));
		
		try {
			
			log.createNewFile();
			
			try(PrintWriter writer = new PrintWriter(log)) {
				
				writer.println(getStackTraceForLog(exception));
			}
			
		} catch(IOException ioException) {
			
			print(ioException);
			prompt(ioException);
		}
		
		return log.exists() ? log : null;
	}
	
	/**
	 * @param exception the {@linkplain Exception}
	 * @return the text that should be displayed when prompting an error message
	 * @since 01.08.2018
	 */
	private static final String getStackTraceForDisplay(Throwable exception) {
		
		StringBuilder builder = new StringBuilder();
		builder.append(exception.getMessage()).append(':');
		
		for(StackTraceElement stackTraceElement : exception.getStackTrace()) {
			
			builder.append("\n\t");
			builder.append(stackTraceElement);
		}
		
		return builder.toString();
	}
	
	/**
	 * @param exception the {@linkplain Exception}
	 * @return the text that should be written in an error log
	 * @since 01.08.2018
	 */
	private static final String getStackTraceForLog(Throwable exception) {
		
		StringBuilder builder = new StringBuilder();
		Throwable cause = exception;
		
		while(cause != null) {
			
			builder.append(cause.getClass().getName()).append(": ");
			builder.append(cause.getMessage());
			
			for(StackTraceElement stackTraceElement : exception.getStackTrace()) {
				
				builder.append("\n\t");
				builder.append(stackTraceElement);
			}
			
			builder.append("\n\n");
			cause = cause.getCause();
		}
		
		return builder.toString();
	}
}
