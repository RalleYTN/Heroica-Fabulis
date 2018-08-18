package de.ralleytn.games.heroicafabulis.engine.io.png;

/**
 * 
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 18.08.2018/0.2.0
 * @since 18.08.2018/0.2.0
 */
public class PngChunk {

    /** @since 18.08.2018/0.2.0 */ public static final int CHUNK_TYPE_IHDR = 0x49484452;
    /** @since 18.08.2018/0.2.0 */ public static final int CHUNK_TYPE_PLTE = 0x504C5445;
    /** @since 18.08.2018/0.2.0 */ public static final int CHUNK_TYPE_TRNS = 0x74524E53;
    /** @since 18.08.2018/0.2.0 */ public static final int CHUNK_TYPE_IDAT = 0x49444154;
    /** @since 18.08.2018/0.2.0 */ public static final int CHUNK_TYPE_IEND = 0x49454E44;
	
	private final long length;
	private final int type;
	private final byte[] data;
	
	/**
	 * 
	 * @param length
	 * @param type
	 * @param data
	 * @since 18.08.2018/0.2.0
	 */
	public PngChunk(long length, int type, byte[] data) {
		
		this.length = length;
		this.type = type;
		this.data = data;
	}
	
	/**
	 * 
	 * @return
	 * @since 18.08.2018/0.2.0
	 */
	public final long getLength() {
		
		return this.length;
	}
	
	/**
	 * 
	 * @return
	 * @since 18.08.2018/0.2.0
	 */
	public final int getType() {
		
		return this.type;
	}
	
	/**
	 * 
	 * @return
	 * @since 18.08.2018/0.2.0
	 */
	public final byte[] getData() {
		
		return this.data;
	}
}
