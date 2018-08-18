package de.ralleytn.games.heroicafabulis.engine.io;

import java.io.IOException;

/**
 * 
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 17.08.2018/0.2.0
 * @since 17.08.2018/0.2.0
 */
public interface Savable {

	/**
	 * 
	 * @throws IOException
	 * @since 17.08.2018/0.2.0
	 */
	public void save() throws IOException;
}
