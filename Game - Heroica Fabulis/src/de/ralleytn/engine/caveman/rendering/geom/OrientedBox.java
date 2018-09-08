package de.ralleytn.engine.caveman.rendering.geom;

import javax.vecmath.Vector3f;

public class OrientedBox extends Box {

	public float rotX;
	public float rotY;
	public float rotZ;
	
	@Override
	public boolean inside(float x, float y, float z) {
		
		return false;
	}
	@Override
	public Vector3f center() {
		
		return null;
	}
}
