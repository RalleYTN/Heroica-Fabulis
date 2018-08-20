package de.ralleytn.games.heroicafabulis.engine.io.textures.png;

/**
 * 
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 19.08.2018/0.2.0
 * @since 19.08.2018/0.2.0
 */
public class IEND extends PngChunk {

	/**
	 * 
	 * @param crc
	 * @since 19.08.2018/0.2.0
	 */
	public IEND(int crc) {
		
		super(0, TYPE_IEND, new byte[0], crc);
	}
}
