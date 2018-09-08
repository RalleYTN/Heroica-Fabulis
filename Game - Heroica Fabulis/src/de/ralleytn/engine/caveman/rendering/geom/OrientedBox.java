package de.ralleytn.engine.caveman.rendering.geom;

import javax.vecmath.Matrix4f;
import javax.vecmath.Vector3f;

import de.ralleytn.engine.caveman.util.MatrixUtil;
import de.ralleytn.engine.caveman.util.MeshUtil;
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
	 * @since 08.09.2018/0.4.0
	 */
	public OrientedBox() {
		
		super();
	}
	
	/**
	 * 
	 * @param box
	 * @since 08.09.2018/0.4.0
	 */
	public OrientedBox(OrientedBox box) {
		
		this.set(box);
	}
	
	/**
	 * 
	 * @param position
	 * @param size
	 * @param rotation
	 * @since 08.09.2018/0.4.0
	 */
	public OrientedBox(Vector3f position, Vector3f size, Vector3f rotation) {
		
		this.set(position, size, rotation);
	}
	
	/**
	 * 
	 * @param x
	 * @param y
	 * @param z
	 * @param width
	 * @param height
	 * @param depth
	 * @param rotX
	 * @param rotY
	 * @param rotZ
	 * @since 08.09.2018/0.4.0
	 */
	public OrientedBox(float x, float y, float z, float width, float height, float depth, float rotX, float rotY, float rotZ) {
		
		this.set(x, y, z, width, height, depth, rotX, rotY, rotZ);
	}
	
	/**
	 * 
	 * @param box
	 * @since 08.09.2018/0.4.0
	 */
	public void set(OrientedBox box) {
		
		super.set(box);
		
		this.rotX = box.rotX;
		this.rotY = box.rotY;
		this.rotZ = box.rotZ;
	}
	
	/**
	 * 
	 * @param position
	 * @param size
	 * @param rotation
	 * @since 08.09.2018/0.4.0
	 */
	public void set(Vector3f position, Vector3f size, Vector3f rotation) {
		
		super.set(position, size);
		this.setRotation(rotation);
	}
	
	/**
	 * 
	 * @param x
	 * @param y
	 * @param z
	 * @param width
	 * @param height
	 * @param depth
	 * @param rotX
	 * @param rotY
	 * @param rotZ
	 * @since 08.09.2018/0.4.0
	 */
	public void set(float x, float y, float z, float width, float height, float depth, float rotX, float rotY, float rotZ) {
		
		super.set(x, y, z, width, height, depth);
		this.setRotation(rotX, rotY, rotZ);
	}
	
	/**
	 * 
	 * @param rotX
	 * @param rotY
	 * @param rotZ
	 * @since 08.09.2018/0.4.0
	 */
	public void setRotation(float rotX, float rotY, float rotZ) {
		
		this.rotX = rotX;
		this.rotY = rotY;
		this.rotZ = rotZ;
	}
	
	/**
	 * 
	 * @param rotation
	 * @since 08.09.2018/0.4.0
	 */
	public void setRotation(Vector3f rotation) {
		
		this.rotX = rotation.x;
		this.rotY = rotation.y;
		this.rotZ = rotation.z;
	}
	
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
			vertices[index] = box.x + vertex.x;
			vertices[index + 1] = box.y + vertex.y;
			vertices[index + 2] = box.z + vertex.z;
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
		
		Vector3f center = new Vector3f(this.width * 0.5F,
										this.height * 0.5F,
										this.depth * 0.5F);
		center = MatrixUtil.multiply(this.createTransformation(), center.x, center.y, center.z);
		//System.out.println(this.width);
		return center;
	}
	
	/**
	 * 
	 * @param box
	 * @return
	 * @since 08.09.2018/0.4.0
	 */
	public boolean intersects(OrientedBox box) {
		
		Vector3f ac = this.center();
		Vector3f ar = new Vector3f(this.width * 0.5F, this.height * 0.5F, this.depth * 0.5F);
		Vector3f bc = box.center();
		Vector3f br = new Vector3f(box.width * 0.5F, box.height * 0.5F, box.depth * 0.5F);
		
		if(Math.abs(ac.x - bc.x) > (ar.x + br.x)) return false;
		if(Math.abs(ac.y - bc.y) > (ar.y + br.y)) return false;
		if(Math.abs(ac.z - bc.z) > (ar.z + br.z)) return false;
		
		return true;
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
	
	/**
	 * 
	 * @return
	 * @since 08.09.2018/0.4.0
	 */
	public MeshData createMeshData() {
		
		MeshData data = new MeshData();
		data.setIndices(INDICES);
		data.setTextureCoordinates(TEXCOORDS);
		data.setVertices(createVertices(this));
		data.setNormals(VectorUtil.toArray3f(MeshUtil.generateNormals(VectorUtil.toList3f(data.getVertices()), INDICES)));
		
		return data;
	}
}
