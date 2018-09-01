package de.ralleytn.engine.caveman.localization;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.ralleytn.engine.caveman.Engine;
import de.ralleytn.engine.caveman.EngineException;
import de.ralleytn.engine.caveman.Errors;

/**
 * Manages all of the locales.
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 15.08.2018/0.1.0
 * @since 31.07.2018/0.1.0
 */
public final class Localization {

	private static final String SYSTEM_LANGUAGE_PROPERTY = System.getProperty("user.language");
	private static final String SYSTEM_LANGUAGE = SYSTEM_LANGUAGE_PROPERTY != null ? SYSTEM_LANGUAGE_PROPERTY : "en";
	
	private static Locale CURRENT_LOCALE;
	private static List<Locale> AVAILABLE_LOCALES;
	
	/**
	 * Private because no instance of this class should exist.
	 * @since 31.07.2018/0.1.0
	 */
	private Localization() {}
	
	/**
	 * Loads all of the available locales names and the translations of the default system locale.
	 * @param localeDirectory the directory containing the locales
	 * @throws IOException if an I/O error occurred
	 * @throws EngineException if one of the locales is invalid
	 * @since 31.07.2018/0.1.0
	 */
	public static final void loadAvailableLocales(File localeDirectory) throws IOException, EngineException {
		
		List<Locale> locales = new ArrayList<>();
		File[] children = localeDirectory.listFiles((dir, name) -> name.toLowerCase().endsWith(".lang"));
		
		if(children != null && children.length > 0) {
			
			for(File file : children) {
				
				if(!file.isDirectory() && file.isFile() && file.canRead()) {
					
					try {
						
						Locale locale = new Locale(file);
						locales.add(locale);
						
						if(file.getName().substring(0, file.getName().lastIndexOf('.')).equalsIgnoreCase(SYSTEM_LANGUAGE)) {
							
							CURRENT_LOCALE = locale;
							locale.load();
						}
						
					} catch(EngineException exception) {
						
						Errors.print(exception);
						Errors.prompt(exception, Errors.log(exception, Engine.getErrLogDirectory()));
					}
				}
			}
		}
		
		if(locales.isEmpty()) {
			
			throw new EngineException("Localization not possible because no locales are available!");
		}
		
		AVAILABLE_LOCALES = locales;
		
		if(CURRENT_LOCALE == null) {
			
			CURRENT_LOCALE = locales.get(0);
			CURRENT_LOCALE.load();
		}
	}
	
	/**
	 * Sets which locale should be used. The old locale will be unloaded.
	 * @param localeShortname the short name is the file name of the locale without the file extension or the directory; the name is case insensitive
	 * @throws IOException if an I/O error occurred
	 * @throws EngineException if the locale is invalid
	 * @since 31.07.2018/0.1.0
	 */
	public static final void setLocale(String localeShortname) throws IOException, EngineException {
		
		boolean changed = false;
		
		for(Locale locale : getAvailableLocales()) {
			
			if(locale.getFile().getName().substring(0, locale.getFile().getName().lastIndexOf('.')).equalsIgnoreCase(localeShortname)) {
				
				locale.load();
				Locale oldLocale = CURRENT_LOCALE;
				CURRENT_LOCALE = locale;
				oldLocale.unload();
				changed = true;
			}
		}
		
		if(!changed) {
			
			throw new EngineException(String.format("There is no locale with the shortname '%s'!", localeShortname));
		}
	}
	
	/**
	 * For this method to work {@link #loadAvailableLocales(File)} has to be called at least once before
	 * @return a list with all of the available locales
	 * @since 31.07.2018/0.1.0
	 */
	public static final List<Locale> getAvailableLocales() {
		
		return AVAILABLE_LOCALES;
	}
	
	/**
	 * @return the currently loaded locale
	 * @since 31.07.2018/0.1.0
	 */
	public static final Locale getCurrentLocale() {
		
		return CURRENT_LOCALE;
	}
	
	/**
	 * @param key translation key
	 * @param params values to fill in the placeholders
	 * @return the translation for the given key
	 * @since 31.07.2018/0.1.0
	 */
	public static final String getLocalizedString(String key, Object... params) {
		
		String localizedString = CURRENT_LOCALE.getLocalizedString(key).replace("\\n", "\n");
		
		if(params != null) {
			
			for(int index = 0; index < params.length; index++) {
				
				localizedString = localizedString.replace(String.format("{%s}", index), String.valueOf(params[index]));
			}
		}
		
		return localizedString;
	}
}
