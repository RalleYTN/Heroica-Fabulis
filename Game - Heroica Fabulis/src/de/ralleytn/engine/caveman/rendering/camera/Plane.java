package de.ralleytn.engine.caveman.rendering.camera;

import javax.vecmath.Vector3f;

/**
 * 
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 06.09.2018/0.4.0
 * @since 06.09.2018/0.4.0
 */
public class Plane {

	public float w;
	public float h;
	public Vector3f tl;
	public Vector3f tr;
	public Vector3f bl;
	public Vector3f br;
	
	/**
	 * 
	 * @since 06.09.2018/0.4.0
	 */
	Plane() {
		
		this.tl = new Vector3f();
		this.tr = new Vector3f();
		this.bl = new Vector3f();
		this.br = new Vector3f();
	}
}
