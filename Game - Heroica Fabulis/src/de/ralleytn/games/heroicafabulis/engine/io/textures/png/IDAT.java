package de.ralleytn.games.heroicafabulis.engine.io.textures.png;

/**
 * 
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 19.08.2018/0.2.0
 * @since 19.08.2018/0.2.0
 */
public class IDAT extends PngChunk {

	/**
	 * 
	 * @param length
	 * @param data
	 * @param crc
	 * @since 19.08.2018/0.2.0
	 */
	public IDAT(long length, byte[] data, int crc) {
		
		super(length, TYPE_IDAT, data, crc);
	}
}
