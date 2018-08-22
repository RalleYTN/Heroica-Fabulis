package de.ralleytn.games.heroicafabulis.engine;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import de.ralleytn.games.heroicafabulis.engine.io.Loadable;
import de.ralleytn.games.heroicafabulis.engine.io.Savable;

/**
 * Manages the game options.
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 17.08.2018/0.2.0
 * @since 17.08.2018/0.2.0
 */
public class Options implements Loadable, Savable {

	/** @since 17.08.2018/0.2.0 */ public static final String OPTION_NATIVE_DIRECTORY = "NativeDirectory";
	/** @since 17.08.2018/0.2.0 */ public static final String OPTION_ERR_LOG_DIRECTORY = "ErrLogDirectory";
	/** @since 17.08.2018/0.2.0 */ public static final String OPTION_LOCALE_DIRECTORY = "LocaleDirectory";
	/** @since 17.08.2018/0.2.0 */ public static final String OPTION_FPS_CAP = "FpsCap";
	/** @since 17.08.2018/0.2.0 */ public static final String OPTION_VSYNC = "VSync";
	/** @since 17.08.2018/0.2.0 */ public static final String OPTION_FULLSCREEN = "Fullscreen";
	/** @since 17.08.2018/0.2.0 */ public static final String OPTION_DISPLAY_WIDTH = "DisplayWidth";
	/** @since 17.08.2018/0.2.0 */ public static final String OPTION_DISPLAY_HEIGHT = "DisplayHeight";
	
	private Map<String, Object> options;
	private File file;
	private String defaultResource;
	
	/**
	 * @param file the options file
	 * @param defaultResource resource on the class path with the default options
	 * @since 17.08.2018/0.2.0
	 */
	public Options(File file, String defaultResource) {
		
		this.file = file;
		this.defaultResource = defaultResource;
		this.options = new HashMap<>();
	}
	
	/**
	 * Sets an option.
	 * @param key option name
	 * @param value option value
	 * @since 17.08.2018/0.2.0
	 */
	public void set(String key, Object value) {
		
		this.options.put(key, value);
	}
	
	@Override
	public void save() throws IOException {
		
		try(PrintWriter writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(this.file), StandardCharsets.UTF_8))) {
			
			for(Map.Entry<String, Object> entry : this.options.entrySet()) {
				
				Object value = entry.getValue();
				writer.print(getDataType(value));
				writer.print(entry.getKey());
				writer.print('=');
				writer.println(String.valueOf(value));
			}
		}
	}

	@Override
	public void load() throws IOException {
		
		this.options.clear();
		boolean validFile = this.file.exists() && !this.file.isDirectory() && this.file.canRead();
		
		try(BufferedReader reader = new BufferedReader(new InputStreamReader(validFile ? new FileInputStream(this.file) : this.getClass().getClassLoader().getResourceAsStream(this.defaultResource), StandardCharsets.UTF_8))) {
			
			String line = null;
			
			while((line = reader.readLine()) != null) {
				
				line = line.trim();
				
				if(line.contains("=")) {
					
					int indexOfSeperator = line.indexOf('=');
					char dataType = line.charAt(0);
					String key = line.substring(1, indexOfSeperator);
					Object value = getValue(dataType, line.substring(indexOfSeperator + 1));
					this.options.put(key, value);
				}
			}
		}
		
		if(!validFile) {
			
			this.save();
		}
	}
	
	/**
	 * @param key option name
	 * @return the option value as {@code boolean}
	 * @since 17.08.2018/0.2.0
	 */
	public boolean getBoolean(String key) {
		
		return (boolean)this.options.get(key);
	}
	
	/**
	 * @param key option name
	 * @return the option value as {@code float}
	 * @since 17.08.2018/0.2.0
	 */
	public float getFloat(String key) {
		
		return (float)this.options.get(key);
	}
	
	/**
	 * @param key option name
	 * @return the option value as {@code int}
	 * @since 17.08.2018/0.2.0
	 */
	public int getInt(String key) {
		
		return (int)this.options.get(key);
	}
	
	/**
	 * @param key option name
	 * @return the option value as {@linkplain String}
	 * @since 17.08.2018/0.2.0
	 */
	public String getString(String key) {
		
		return (String)this.options.get(key);
	}
	
	/**
	 * @param value an option value
	 * @return the datatype of this value
	 * @since 17.08.2018/0.2.0
	 */
	private static final char getDataType(Object value) {
		
		return value instanceof Boolean ? 'b' :		// boolean
			  (value instanceof Float ? 'f' :		// float
			  (value instanceof Integer ? 'i' :		// int
			  ('s')));								// String
	}
	
	/**
	 * @param dataType the data type of the option value
	 * @param toParse the raw option value data
	 * @return the option value
	 * @since 17.08.2018/0.2.0
	 */
	private static final Object getValue(char dataType, String toParse) {
		
		return dataType == 'b' ? toParse.equals("true") :		// boolean
			  (dataType == 'f' ? Float.parseFloat(toParse) :	// float
			  (dataType == 'i' ? Integer.parseInt(toParse) :	// int
			  (toParse)));										// String
	}
}
