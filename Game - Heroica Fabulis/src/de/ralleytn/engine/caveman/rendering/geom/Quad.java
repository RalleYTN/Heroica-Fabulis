package de.ralleytn.engine.caveman.rendering.geom;

import javax.vecmath.Vector3f;

import de.ralleytn.engine.caveman.util.MeshUtil;
import de.ralleytn.engine.caveman.util.VectorUtil;

/**
 * 
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 08.09.2018/0.4.0
 * @since 07.09.2018/0.4.0
 */
public class Quad {

	/**
	 * 
	 * @since 07.09.2018/0.4.0
	 */
	public static final int[] INDICES = {
			
		3, 1, 0,
		2, 1, 3
	};
		
	/**
	 * @since 07.09.2018/0.4.0
	 */
	public static final float[] TEXCOORDS = {
				
		0.0F, 0.0F,
		0.0F, 1.0F,
		1.0F, 1.0F,
		1.0F, 0.0F
	};
	
	public Vector3f p1;
	public Vector3f p2;
	public Vector3f p3;
	public Vector3f p4;
	
	/**
	 * 
	 * @since 07.09.2018/0.4.0
	 */
	public Quad() {
		
		this.p1 = new Vector3f();
		this.p2 = new Vector3f();
		this.p3 = new Vector3f();
		this.p4 = new Vector3f();
	}
	
	/**
	 * 
	 * @param quad
	 * @return
	 * @since 07.09.2018/0.4.0
	 */
	public static final float[] createVertices(Quad quad) {
		
		return new float[] {
				
			quad.p1.x, quad.p1.y, quad.p1.z,
			quad.p2.x, quad.p2.y, quad.p2.z,
			quad.p3.x, quad.p3.y, quad.p3.z,
			quad.p4.x, quad.p4.y, quad.p4.z
		};
	}
	
	/**
	 * 
	 * @param p1
	 * @param p2
	 * @param p3
	 * @param p4
	 * @since 07.09.2018/0.4.0
	 */
	public Quad(Vector3f p1, Vector3f p2, Vector3f p3, Vector3f p4) {
		
		this.set(p1, p2, p3, p4);
	}
	
	/**
	 * 
	 * @param p1
	 * @param p2
	 * @param p3
	 * @param p4
	 * @since 07.09.2018/0.4.0
	 */
	public void set(Vector3f p1, Vector3f p2, Vector3f p3, Vector3f p4) {
		
		this.p1 = p1;
		this.p2 = p2;
		this.p3 = p3;
		this.p4 = p4;
	}
	
	/**
	 * 
	 * @param p1
	 * @since 07.09.2018/0.4.0
	 */
	public void setP1(Vector3f p1) {
		
		this.p1 = p1;
	}
	
	/**
	 * 
	 * @param p2
	 * @since 07.09.2018/0.4.0
	 */
	public void setP2(Vector3f p2) {
		
		this.p2 = p2;
	}
	
	/**
	 * 
	 * @param p3
	 * @since 07.09.2018/0.4.0
	 */
	public void setP3(Vector3f p3) {
		
		this.p3 = p3;
	}
	
	/**
	 * 
	 * @param p4
	 * @since 07.09.2018/0.4.0
	 */
	public void setP4(Vector3f p4) {
		
		this.p4 = p4;
	}
	
	/**
	 * 
	 * @return
	 * @since 07.09.2018/0.4.0
	 */
	public AxisAlignedBox createAABB() {
		
		float xn = Math.min(Math.min(this.p1.x, this.p2.x), Math.min(this.p3.x, this.p4.x));
		float yn = Math.min(Math.min(this.p1.y, this.p2.y), Math.min(this.p3.y, this.p4.y));
		float zn = Math.min(Math.min(this.p1.z, this.p2.z), Math.min(this.p3.z, this.p4.z));
		
		float xf = Math.max(Math.max(this.p1.x, this.p2.x), Math.max(this.p3.x, this.p4.x));
		float yf = Math.max(Math.max(this.p1.y, this.p2.y), Math.max(this.p3.y, this.p4.y));
		float zf = Math.max(Math.max(this.p1.z, this.p2.z), Math.max(this.p3.z, this.p4.z));
		
		float width = xf - xn;
		float height = yf - yn;
		float depth = zf - zn;
		
		return new AxisAlignedBox(xn, yn, zn, width, height, depth);
	}
	
	@Override
	public String toString() {
		
		StringBuilder builder = new StringBuilder();
		builder.append("[p1=").append(VectorUtil.toString(this.p1));
		builder.append(",p2=").append(VectorUtil.toString(this.p2));
		builder.append(",p3=").append(VectorUtil.toString(this.p3));
		builder.append(",p4=").append(VectorUtil.toString(this.p4));
		builder.append(']');
		
		return builder.toString();
	}
	
	/**
	 * 
	 * @return
	 * @since 07.09.2018/0.4.0
	 */
	public MeshData createMeshData() {
		
		MeshData data = new MeshData();
		data.setIndices(INDICES);
		data.setTextureCoordinates(TEXCOORDS);
		data.setVertices(createVertices(this));
		data.setNormals(VectorUtil.toArray3f(MeshUtil.generateNormals(VectorUtil.toList3f(data.getVertices()), INDICES)));
		
		return data;
	}
	
	/**
	 * 
	 * @return
	 * @since 07.08.2018/0.4.0
	 */
	public Vector3f getP1() {
		
		return this.p1;
	}
	
	/**
	 * 
	 * @return
	 * @since 07.09.2018/0.4.0
	 */
	public Vector3f getP2() {
		
		return this.p2;
	}
	
	/**
	 * 
	 * @return
	 * @since 07.09.2018/0.4.0
	 */
	public Vector3f getP3() {
		
		return this.p3;
	}
	
	/**
	 * 
	 * @return
	 * @since 07.09.2018/0.4.0
	 */
	public Vector3f getP4() {
		
		return this.p4;
	}
}
