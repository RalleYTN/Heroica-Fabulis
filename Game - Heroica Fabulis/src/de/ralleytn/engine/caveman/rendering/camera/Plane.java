package de.ralleytn.engine.caveman.rendering.camera;

import javax.vecmath.Vector3f;

/**
 * 
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 07.09.2018/0.4.0
 * @since 06.09.2018/0.4.0
 */
public class Plane {

	public Vector3f tl;	// top left
	public Vector3f tr;	// top right
	public Vector3f bl;	// bottom left
	public Vector3f br;	// bottom right
	public Vector3f n;	// normal
	
	/**
	 * 
	 * @since 06.09.2018/0.4.0
	 */
	public Plane() {
		
		this.tl = new Vector3f();
		this.tr = new Vector3f();
		this.bl = new Vector3f();
		this.br = new Vector3f();
		this.n = new Vector3f();
	}
}
