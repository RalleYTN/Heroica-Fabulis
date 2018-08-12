package de.ralleytn.games.heroicafabulis.engine;

import java.io.IOException;

/**
 * 
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 10.08.2018/0.1.0
 * @since 10.08.2018/0.1.0
 */
public interface Loadable {

	/**
	 * 
	 * @throws IOException
	 * @since 10.08.2018/0.1.0
	 */
	public void load() throws IOException;
}
