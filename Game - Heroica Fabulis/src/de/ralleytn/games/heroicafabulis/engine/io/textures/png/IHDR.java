package de.ralleytn.games.heroicafabulis.engine.io.textures.png;

/**
 * 
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 23.08.2018/0.2.0
 * @since 18.08.2018/0.2.0
 */
public class IHDR extends PngChunk {

	/** @since 18.08.2018/0.2.0 */ public static final byte COLOR_TYPE_GREYSCALE = 0;
    /** @since 18.08.2018/0.2.0 */ public static final byte COLOR_TYPE_TRUECOLOR = 2;
    /** @since 18.08.2018/0.2.0 */ public static final byte COLOR_TYPE_INDEXED = 3;
    /** @since 18.08.2018/0.2.0 */ public static final byte COLOR_TYPE_GREYALPHA = 4;
    /** @since 18.08.2018/0.2.0 */ public static final byte COLOR_TYPE_TRUEALPHA = 6;
	
	private int width;
	private int height;
	private byte bitDepth;
	private byte colorType;
	private byte compressionMethod;
	private byte filterMethod;
	private byte interlaceMethod;
	
	/**
	 * 
	 * @param length
	 * @param data
	 * @param crc
	 * @since 18.08.2018/0.2.0
	 */
	public IHDR(long length, byte[] data, int crc) {
		
		super(length, TYPE_IHDR, data, crc);
		
		this.width = (int)((((data[0] & 0xFF) << 24) | ((data[1] & 0xFF) << 16) | ((data[2] & 0xFF) << 8) | (data[3] & 0xFF)) & 0xFFFFFFFFL);
		this.height = (int)((((data[4] & 0xFF) << 24) | ((data[5] & 0xFF) << 16) | ((data[6] & 0xFF) << 8) | (data[7] & 0xFF)) & 0xFFFFFFFFL);
		this.bitDepth = data[8];
		this.colorType = data[9];
		this.compressionMethod = data[10];
		this.filterMethod = data[11];
		this.interlaceMethod = data[12];
	}

	/**
	 * 
	 * @return
	 * @since 18.08.2018/0.2.0
	 */
	public int getWidth() {

		return this.width;
	}
	
	/**
	 * 
	 * @return
	 * @since 18.08.2018/0.2.0
	 */
	public int getHeight() {
		
		return this.height;
	}
	
	/**
	 * 
	 * @return
	 * @since 18.08.2018/0.2.0
	 */
	public byte getBitDepth() {
		
		return this.bitDepth;
	}
	
	/**
	 * 
	 * @return
	 * @since 18.08.2018/0.2.0
	 */
	public byte getColorType() {
		
		return this.colorType;
	}
	
	/**
	 * 
	 * @return
	 * @since 18.08.2018/0.2.0
	 */
	public byte getCompressionMethod() {
		
		return this.compressionMethod;
	}
	
	/**
	 * 
	 * @return
	 * @since 18.08.2018/0.2.0
	 */
	public byte getFilterMethod() {
		
		return this.filterMethod;
	}
	
	/**
	 * 
	 * @return
	 * @since 18.08.2018/0.2.0
	 */
	public byte getInterlaceMethod() {
		
		return this.interlaceMethod;
	}
}
