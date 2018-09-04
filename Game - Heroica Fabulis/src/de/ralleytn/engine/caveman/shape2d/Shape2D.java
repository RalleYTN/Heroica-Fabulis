package de.ralleytn.engine.caveman.shape2d;

/**
 * 
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 04.09.2018/0.4.0
 * @since 04.09.2018/0.4.0
 */
public interface Shape2D {

	/** @since 04.09.2018/0.4.0 */ public static final int OUT_LEFT = 0b1;
	/** @since 04.09.2018/0.4.0 */ public static final int OUT_RIGHT = 0b10;
	/** @since 04.09.2018/0.4.0 */ public static final int OUT_TOP = 0b100;
	/** @since 04.09.2018/0.4.0 */ public static final int OUT_BOTTOM = 0b1000;
	
	/**
	 * 
	 * @return
	 * @since 04.09.2018/0.4.0
	 */
	public Box2D getBounds();
}
