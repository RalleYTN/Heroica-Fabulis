package de.ralleytn.games.heroicafabulis.engine.io;

import java.nio.ByteBuffer;

import de.ralleytn.games.heroicafabulis.engine.audio.ALBuffer;

/**
 * 
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 17.08.2018/0.2.0
 * @since 17.08.2018/0.2.0
 */
public abstract class AudioReader extends Reader<ALBuffer> {
	
	/**
	 * 
	 * @param format
	 * @param data
	 * @param frequency
	 * @return
	 * @since 17.08.2018/0.2.0
	 */
	protected static final ALBuffer createBuffer(int format, ByteBuffer data, int frequency) {
		
		ALBuffer buffer = new ALBuffer();
		buffer.setData(format, data, frequency);
		return buffer;
	}
}
