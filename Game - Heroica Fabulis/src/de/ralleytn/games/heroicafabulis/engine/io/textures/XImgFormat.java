package de.ralleytn.games.heroicafabulis.engine.io.textures;

/**
 * Contains methods for reading and writing XIMG image files.
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 22.08.2018/0.2.0
 * @since 19.08.2018/0.2.0
 */
public final class XImgFormat {

	// BIG ENDIAN
	// 4 byte = XIMG signature
	// 1 byte = flags
	//	- 0 = has alpha
	//	- 1 = grayscale
	// 2 byte integer = width
	// 2 byte integer = height
	// for restLength
	//	- 1 byte integer = repeat count
	// 	-      if !hasAlpha && !grayscale = 3 byte BGR
	//	- else if  hasAlpha && !grayscale = 4 byte ABGR
	//	- else if !hasAlpha &&  grayscale = 1 byte G
	//	- else if  hasAlpha &&  grayscale = 2 byte AG
	
	/**
	 * @since 19.08.2018/0.2.0
	 */
	public static final String SIGNATURE = "XIMG";
	
	/**
	 * @since 19.08.2018/0.2.0
	 */
	private XImgFormat() {}
}
