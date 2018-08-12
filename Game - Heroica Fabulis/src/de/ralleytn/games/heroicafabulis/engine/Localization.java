package de.ralleytn.games.heroicafabulis.engine;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 01.08.2018/0.1.0
 * @since 31.07.2018/0.1.0
 */
public final class Localization {

	private static final String SYSTEM_LANGUAGE_PROPERTY = System.getProperty("user.language");
	private static final String SYSTEM_LANGUAGE = SYSTEM_LANGUAGE_PROPERTY != null ? SYSTEM_LANGUAGE_PROPERTY : "en";
	
	private static Locale CURRENT_LOCALE;
	private static List<Locale> AVAILABLE_LOCALES;
	
	/**
	 * @since 31.07.2018/0.1.0
	 */
	private Localization() {}
	
	/**
	 * 
	 * @param localeDirectory
	 * @throws IOException
	 * @throws EngineException
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
	 * 
	 * @param localeShortname
	 * @throws IOException
	 * @throws EngineException 
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
	 * 
	 * @return
	 * @since 31.07.2018/0.1.0
	 */
	public static final List<Locale> getAvailableLocales() {
		
		return AVAILABLE_LOCALES;
	}
	
	/**
	 * 
	 * @return
	 * @since 31.07.2018/0.1.0
	 */
	public static final Locale getCurrentLocale() {
		
		return CURRENT_LOCALE;
	}
	
	/**
	 * 
	 * @param key
	 * @return
	 * @since 31.07.2018/0.1.0
	 */
	public static final String getLocalizedString(String key) {
		
		return CURRENT_LOCALE.getLocalizedString(key);
	}
}
