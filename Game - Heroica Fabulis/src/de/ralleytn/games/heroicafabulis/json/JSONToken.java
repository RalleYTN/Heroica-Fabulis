package de.ralleytn.games.heroicafabulis.json;

/**
 * 
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 17.08.2018/0.2.0
 * @since 17.08.2018/0.2.0
 */
public final class JSONToken {
	
	/** @since 17.08.2018/0.2.0 */ public static final int TYPE_VALUE = 0;
	/** @since 17.08.2018/0.2.0 */ public static final int TYPE_LEFT_BRACE = 1;
	/** @since 17.08.2018/0.2.0 */ public static final int TYPE_RIGHT_BRACE = 2;
	/** @since 17.08.2018/0.2.0 */ public static final int TYPE_LEFT_SQUARE = 3;
	/** @since 17.08.2018/0.2.0 */ public static final int TYPE_RIGHT_SQUARE = 4;
	/** @since 17.08.2018/0.2.0 */ public static final int TYPE_COMMA = 5;
	/** @since 17.08.2018/0.2.0 */ public static final int TYPE_COLON = 6;
	/** @since 17.08.2018/0.2.0 */ public static final int TYPE_EOF = -1;
	
	private final int type;
	private final Object value;

	/**
	 * 
	 * @param type
	 * @param value
	 * @since 17.08.2018/0.2.0
	 */
	public JSONToken(int type, Object value) {
		
		this.type = type;
		this.value = value;
	}
	
	/**
	 * 
	 * @return
	 * @since 17.08.2018/0.2.0
	 */
	public final int getType() {
		
		return this.type;
	}
	
	/**
	 * 
	 * @return
	 * @since 17.08.2018/0.2.0
	 */
	public final Object getValue() {
		
		return this.value;
	}
}