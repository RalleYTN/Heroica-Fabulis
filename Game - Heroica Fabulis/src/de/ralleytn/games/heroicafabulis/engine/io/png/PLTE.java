package de.ralleytn.games.heroicafabulis.engine.io.png;

import javax.vecmath.Color3f;

/**
 * 
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 19.08.2018/0.2.0
 * @since 19.08.2018/0.2.0
 */
public class PLTE extends PngChunk {

	private Color3f[] palette;
	
	/**
	 * 
	 * @param length
	 * @param data
	 * @param crc
	 * @since 19.08.2018/0.2.0
	 */
	public PLTE(long length, byte[] data, int crc) {
		
		super(length, TYPE_PLTE, data, crc);
		
		this.palette = new Color3f[(int)(length / 3)];
		int entry = 0;
		
		for(int index = 0; index < data.length; index += 3) {

			this.palette[entry++] = new Color3f(data[index + 0] & 0xFF, data[index + 1] & 0xFF, data[index + 2] & 0xFF);
		}
	}
	
	/**
	 * 
	 * @return
	 * @since 19.08.2018/0.2.0
	 */
	public Color3f[] getPalette() {
		
		return this.palette;
	}
}
