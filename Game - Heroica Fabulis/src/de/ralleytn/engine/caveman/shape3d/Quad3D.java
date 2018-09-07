package de.ralleytn.engine.caveman.shape3d;

import javax.vecmath.Vector3f;

import de.ralleytn.engine.caveman.rendering.geom3d.Box3D;
import de.ralleytn.engine.caveman.rendering.geom3d.Shape3D;

/**
 * 
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 07.09.2018/0.4.0
 * @since 07.09.2018/0.4.0
 */
public class Quad3D implements Shape3D {

	public Vector3f p1;
	public Vector3f p2;
	public Vector3f p3;
	public Vector3f p4;
	
	@Override
	public Box3D getBounds() {
		
		return null;
	}
	
	@Override
	public boolean isEmpty() {
		
		return this.getBounds().isEmpty();
	}
}
