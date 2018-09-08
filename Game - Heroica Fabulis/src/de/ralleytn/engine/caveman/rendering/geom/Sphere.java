package de.ralleytn.engine.caveman.rendering.geom;

import javax.vecmath.Vector3f;

import de.ralleytn.engine.caveman.util.VectorUtil;

/**
 * 
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 08.09.2018/0.4.0
 * @since 04.09.2018/0.4.0
 */
public class Sphere {

	public float x;
	public float y;
	public float z;
	public float radius;
	
	/**
	 * @since 04.09.2018/0.4.0
	 */
	public Sphere() {}
	
	/**
	 * 
	 * @param x
	 * @param y
	 * @param z
	 * @param radius
	 * @since 04.09.2018/0.4.0
	 */
	public Sphere(float x, float y, float z, float radius) {
		
		this.setPosition(x, y, z);
		this.radius = radius;
	}
	
	/**
	 * 
	 * @param x
	 * @param y
	 * @param z
	 * @since 04.09.2018/0.4.0
	 */
	public Sphere(float x, float y, float z) {
		
		this.setPosition(x, y, z);
	}
	
	/**
	 * 
	 * @param position
	 * @param radius
	 * @since 04.09.2018/0.4.0
	 */
	public Sphere(Vector3f position, float radius) {
		
		this.setPosition(position);
		this.radius = radius;
	}
	
	/**
	 * 
	 * @param position
	 * @since 04.09.2018/0.4.0
	 */
	public Sphere(Vector3f position) {
		
		this.setPosition(position);
	}
	
	/**
	 * 
	 * @param radius
	 * @since 04.09.2018/0.4.0
	 */
	public Sphere(float radius) {
		
		this.radius = radius;
	}
	
	/**
	 * 
	 * @param radius
	 * @since 04.09.2018/0.4.0
	 */
	public void setRadius(float radius) {
		
		this.radius = radius;
	}
	
	/**
	 * 
	 * @param x
	 * @param y
	 * @param z
	 * @since 04.09.2018/0.4.0
	 */
	public void setPosition(float x, float y, float z) {
		
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	/**
	 * 
	 * @param position
	 * @since 04.09.2018/0.4.0
	 */
	public void setPosition(Vector3f position) {
		
		this.x = position.x;
		this.y = position.y;
		this.z = position.z;
	}
	
	/**
	 * 
	 * @param x
	 * @param y
	 * @param z
	 * @return
	 * @since 05.09.2018/0.4.0
	 */
	public boolean inside(float x, float y, float z) {
		
		return this.inside(new Vector3f(x, y, z));
	}
	
	/**
	 * 
	 * @param point
	 * @return
	 * @since 05.09.2018/0.4.0
	 */
	public boolean inside(Vector3f point) {
		
		return VectorUtil.getAbsoluteDistance(this.getMiddle(), point) < this.radius;
	}
	
	/**
	 * 
	 * @param sphere
	 * @return
	 * @since 05.09.2018/0.4.0
	 */
	public boolean contains(Sphere sphere) {
		
		return sphere.radius * 2 < this.radius &&
			   VectorUtil.getAbsoluteDistance(this.getMiddle(), sphere.getMiddle()) + sphere.radius > this.radius;
	}
	
	/**
	 * 
	 * @param position
	 * @param radius
	 * @return
	 * @since 05.09.2018/0.4.0
	 */
	public boolean contains(Vector3f position, float radius) {
		
		return this.contains(new Sphere(position, radius));
	}
	
	/**
	 * 
	 * @param x
	 * @param y
	 * @param z
	 * @param radius
	 * @return
	 * @since 05.09.2018/0.4.0
	 */
	public boolean contains(float x, float y, float z, float radius) {
		
		return this.contains(new Sphere(x, y, z, radius));
	}
	
	/**
	 * 
	 * @param sphere
	 * @return
	 * @since 05.09.2018/0.4.0
	 */
	public boolean intersects(Sphere sphere) {
		
		return VectorUtil.getAbsoluteDistance(this.getMiddle(), sphere.getMiddle()) < this.radius + sphere.radius;
	}
	
	/**
	 * 
	 * @param position
	 * @param radius
	 * @return
	 * @since 05.09.2018/0.4.0
	 */
	public boolean intersects(Vector3f position, float radius) {
		
		return this.intersects(new Sphere(position, radius));
	}
	
	/**
	 * 
	 * @param x
	 * @param y
	 * @param z
	 * @param radius
	 * @return
	 * @since 05.09.2018/0.4.0
	 */
	public boolean intersects(float x, float y, float z, float radius) {
		
		return this.intersects(new Sphere(x, y, z, radius));
	}
	
	/**
	 * 
	 * @return
	 * @since 07.09.2018/0.4.0
	 */
	public boolean isEmpty() {
		
		return this.radius <= 0;
	}
	
	/**
	 * 
	 * @return
	 * @since 04.09.2018/0.4.0
	 */
	public float getX() {
		
		return this.x;
	}
	
	/**
	 * 
	 * @return
	 * @since 04.09.2018/0.4.0
	 */
	public float getY() {
		
		return this.y;
	}
	
	/**
	 * 
	 * @return
	 * @since 04.09.2018/0.4.0
	 */
	public float getZ() {
		
		return this.z;
	}
	
	/**
	 * 
	 * @return
	 * @since 04.09.2018/0.4.0
	 */
	public float getRadius() {
		
		return this.radius;
	}
	
	/**
	 * 
	 * @return
	 * @since 05.09.2018/0.4.0
	 */
	public Vector3f getMiddle() {
		
		return new Vector3f(this.x + this.radius, this.y + this.radius, this.z + this.radius);
	}
	
	/**
	 * 
	 * @return
	 * @since 07.09.2018/0.4.0
	 */
	public AxisAlignedBox createAABB() {
		
		float size = this.radius * 2.0F;
		return new AxisAlignedBox(this.x - this.radius, this.y - this.radius, this.z - this.radius, size, size, size);
	}
	
	/**
	 * 
	 * @param rings
	 * @param sectors
	 * @return
	 * @since 06.09.2018/0.4.0
	 */
	public MeshData createMeshData(int rings, int sectors) {
		
		final float R = (float)(1./(rings - 1));
		final float S = (float)(1./(sectors - 1));
		int rs = rings * sectors;
		int vnCount = rs * 3;
		int vtCount = rs * 2;
		float[] vertices = new float[vnCount];
		float[] normals = new float[vnCount];
		float[] texCoords = new float[vtCount];
		int[] indices = new int[rs * 4];
		int i = 0;
		int v = 0;
		int vn = 0;
		int vt = 0;
		
		for(int r = 0; r < rings; r++) {
			
			for(int s = 0; s < sectors; s++) {
				
				float y = (float)Math.sin( -(Math.PI / 2) + Math.PI * r * R);
				float x = (float)(Math.cos(2 * Math.PI * s * S) * Math.sin(Math.PI * r * R));
				float z = (float)(Math.sin(2 * Math.PI * s * S) * Math.sin(Math.PI * r * R));
				
				texCoords[vt++] = s * S;
				texCoords[vt++] = r * R;
				
				vertices[v++] = x * radius;
				vertices[v++] = y * radius;
				vertices[v++] = z * radius;
				
				normals[vn++] = x;
				normals[vn++] = y;
				normals[vn++] = z;
				
				int r1 = r + 1;
				int s1 = s + 1;
				
				indices[i++] = rs + s;
				indices[i++] = rs + s1;
				indices[i++] = r1 * sectors + s1;
				indices[i++] = r1 * sectors + s;
			}
		}
		
		MeshData data = new MeshData();
		data.setVertices(vertices);
		data.setIndices(indices);
		data.setTextureCoordinates(texCoords);
		data.setNormals(normals);
		
		return data;
	}
}
