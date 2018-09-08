package de.ralleytn.engine.caveman.rendering.geom;

import javax.vecmath.Matrix4f;
import javax.vecmath.Vector3f;

import de.ralleytn.engine.caveman.util.MatrixUtil;
import de.ralleytn.engine.caveman.util.VectorUtil;

/**
 * 
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 08.09.2018/0.4.0
 * @since 08.09.2018/0.4.0
 */
public class OrientedBox extends Box {

	public float rotX;
	public float rotY;
	public float rotZ;
	
	/**
	 * 
	 * @param box
	 * @return
	 * @since 08.09.2018/0.4.0
	 */
	public static final float[] createVertices(OrientedBox box) {
		
		float[] aaVertices = AxisAlignedBox.createVertices(new AxisAlignedBox(box));
		float[] vertices = new float[aaVertices.length];
		Matrix4f transformation = box.createTransformation();
		
		for(int index = 0; index < aaVertices.length; index += 3) {
			
			Vector3f vertex = MatrixUtil.multiply(transformation, aaVertices[index], aaVertices[index + 1], aaVertices[index + 2]);
			vertices[index] = vertex.x;
			vertices[index + 1] = vertex.y;
			vertices[index + 2] = vertex.z;
		}
		
		return vertices;
	}
	
	@Override
	public boolean inside(float x, float y, float z) {
		
		Vector3f center = this.center();
		
		if(Math.abs(center.x - x) > (this.width * 0.5F)) return false;
		if(Math.abs(center.y - y) > (this.height * 0.5F)) return false;
		if(Math.abs(center.z - z) > (this.depth * 0.5F)) return false;
		
		return true;
	}
	
	@Override
	public Vector3f center() {
		
		return MatrixUtil.multiply(this.createTransformation(), this.x + this.width * 0.5F,
																this.y + this.height * 0.5F,
																this.z + this.depth * 0.5F);
	}
	
	/**
	 * 
	 * @return
	 * @since 08.09.2018/0.4.0
	 */
	public float getRotX() {
		
		return this.x;
	}
	
	/**
	 * 
	 * @return
	 * @since 08.09.2018/0.4.0
	 */
	public float getRotY() {
		
		return this.y;
	}
	
	/**
	 * 
	 * @return
	 * @since 08.09.2018/0.4.0
	 */
	public float getRotZ() {
		
		return this.z;
	}
	
	/**
	 * 
	 * @return
	 * @since 08.09.2018/0.4.0
	 */
	public Matrix4f createTransformation() {
		
		return MatrixUtil.createTransformationMatrx(
			new Vector3f(this.x, this.y, this.z),
			new Vector3f(this.rotX, this.rotY, this.rotZ),
			VectorUtil.DEFAULT_SCALE
		);
	}
}
