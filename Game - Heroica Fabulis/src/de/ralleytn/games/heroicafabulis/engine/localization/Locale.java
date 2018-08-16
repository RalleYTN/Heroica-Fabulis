package de.ralleytn.games.heroicafabulis.engine.localization;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

import de.ralleytn.games.heroicafabulis.engine.EngineException;
import de.ralleytn.games.heroicafabulis.engine.io.Loadable;

/**
 * Represents the localization table for a single language.
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 16.08.2018/0.1.0
 * @since 31.07.2018/0.1.0
 */
public class Locale implements Loadable {

	private HashMap<String, String> translations;
	private String langName;
	private File file;
	
	/**
	 * @param file the file containing the localization table
	 * @throws IOException if the file could not be read
	 * @throws EngineException if the localization table doesn't have the {@code LangName} property
	 * @since 31.07.2018/0.1.0
	 */
	public Locale(File file) throws IOException, EngineException {
		
		this.file = file;
		this.loadLangName();
	}
	
	/**
	 * Loads the language name.
	 * @throws IOException if the file could not be read
	 * @throws EngineException if the localization table doesn't have the {@code LangName} property
	 * @since 31.07.2018/0.1.0
	 */
	private final void loadLangName() throws IOException, EngineException {
		
		try(BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(this.file), StandardCharsets.UTF_8))) {
			
			String line = null;
			
			while((line = reader.readLine()) != null) {
				
				line = line.trim();
				
				if(line.startsWith("LangName=")) {
					
					this.langName = line.substring(line.indexOf('=') + 1);
					break;
				}
			}
			
			if(this.langName == null) {
				
				throw new EngineException(String.format("The property 'LangName' is missing in the locale '%s'!", this.file.getAbsolutePath()));
			}
		}
	}
	
	@Override
	public void load() throws IOException {
		
		this.translations = new HashMap<>();
		
		try(BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(this.file), StandardCharsets.UTF_8))) {
			
			String line = null;
			
			while((line = reader.readLine()) != null) {
				
				line = line.trim();
				
				if(!line.isEmpty() && !line.startsWith("#") && line.contains("=") && !line.startsWith("LangName=")) {
					
					int indexOfSeperator = line.indexOf('=');
					String key = line.substring(0, indexOfSeperator);
					String value = line.substring(indexOfSeperator + 1);
					this.translations.put(key, value);
				}
			}
		}
	}
	
	/**
	 * Nulls the localization table so it can be collected by the GC.
	 * @since 31.07.2018/0.1.0
	 */
	public void unload() {
		
		this.translations = null;
	}
	
	/**
	 * @param key the key for the localized string
	 * @return the value for this key or the key itself if no value is available to at least provide something
	 * @since 31.07.2018/0.1.0
	 */
	public String getLocalizedString(String key) {
		
		String localizedString = key;
		
		if(this.translations.containsKey(key)) {
			
			localizedString = this.translations.get(key);
		}
		
		return localizedString;
	}
	
	/**
	 * @return {@code true} if the localization table is loaded, else {@code false}
	 * @since 31.07.2018/0.1.0
	 */
	public boolean isLoaded() {
		
		return this.translations != null;
	}
	
	/**
	 * @return the file containing the localization table
	 * @since 31.07.2018/0.1.0
	 */
	public File getFile() {
		
		return this.file;
	}
}
