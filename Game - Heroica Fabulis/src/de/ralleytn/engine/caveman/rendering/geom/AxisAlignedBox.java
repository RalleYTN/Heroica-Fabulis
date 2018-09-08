package de.ralleytn.engine.caveman.rendering.geom;

import javax.vecmath.Vector3f;

import de.ralleytn.engine.caveman.util.MeshUtil;
import de.ralleytn.engine.caveman.util.VectorUtil;

/**
 * 
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 08.09.2018/0.4.0
 * @since 04.09.2018/0.4.0
 */
public class AxisAlignedBox extends Box {
	
	/**
	 * @since 04.09.2018/0.4.0
	 */
	public AxisAlignedBox() {
		
		super();
	}
	
	/**
	 * 
	 * @param box
	 * @since 04.09.2018/0.4.0
	 */
	public AxisAlignedBox(Box box) {
		
		super(box);
	}
	
	/**
	 * 
	 * @param x
	 * @param y
	 * @param z
	 * @param width
	 * @param height
	 * @param depth
	 * @since 04.09.2018/0.4.0
	 */
	public AxisAlignedBox(float x, float y, float z, float width, float height, float depth) {
		
		super(x, y, z, width, height, depth);
	}
	
	/**
	 * 
	 * @param position
	 * @param size
	 * @since 04.09.2018/0.4.0
	 */
	public AxisAlignedBox(Vector3f position, Vector3f size) {
		
		super(position, size);
	}
	
	/**
	 * 
	 * @param bounds
	 * @return
	 * @since 06.09.2018/0.4.0
	 */
	public static final float[] createVertices(AxisAlignedBox bounds) {
		
		float rx = bounds.x + bounds.width;
		float ry = bounds.y + bounds.height;
		float rz = bounds.z + bounds.depth;
		
		return new float[] {
				
			bounds.x, ry, bounds.z,
			bounds.x, bounds.y, bounds.z,
			rx, bounds.y, bounds.z,
			rx, ry, bounds.z,
					
			bounds.x, ry, rz,
			bounds.x, bounds.y, rz,
			rx, bounds.y, rz,
			rx, ry, rz,
					
			rx, ry, bounds.z,
			rx, bounds.y, bounds.z,
			rx, bounds.y, rz,
			rx, ry, rz,
					
			bounds.x, ry, bounds.z,	
			bounds.x, bounds.y, bounds.z,	
			bounds.x, bounds.y, rz,	
			bounds.x, ry, rz,
					
			bounds.x, ry, rz,
			bounds.x, ry, bounds.z,
			rx, ry, bounds.z,
			rx, ry, rz,
					
			bounds.x, bounds.y, rz,
			bounds.x, bounds.y, bounds.z,
			rx, bounds.y, bounds.z,
			rx, bounds.y, rz
		};
	}

	@Override
	public boolean inside(float x, float y, float z) {
		
		return this.width > 0 && this.height > 0 && this.depth > 0 &&
			   x > this.x && x < this.x + this.width &&
			   y > this.y && y < this.y + this.height &&
			   z > this.z && z < this.z + this.depth;
	}
	
	/**
	 * 
	 * @param position
	 * @param size
	 * @return
	 * @since 04.09.2018/0.4.0
	 */
	public boolean contains(Vector3f position, Vector3f size) {
		
		return this.contains(position.x, position.y, position.z, size.x, size.y, size.z);
	}
	
	/**
	 * 
	 * @param box
	 * @return
	 * @since 04.09.2018/0.4.0
	 */
	public boolean contains(AxisAlignedBox box) {
		
		return this.contains(box.x, box.y, box.z, box.width, box.height, box.depth);
	}
	
	/**
	 * 
	 * @param x
	 * @param y
	 * @param z
	 * @param width
	 * @param height
	 * @param depth
	 * @return
	 * @since 04.09.2018/0.4.0
	 */
	public boolean contains(float x, float y, float z, float width, float height, float depth) {
		
		return this.width > 0 && this.height > 0 && this.depth > 0 &&
			   x >= this.x && x + width <= this.x + this.width &&
			   y >= this.y && y + height <= this.y + this.height &&
			   z >= this.z && z + depth <= this.z + this.depth;
	}
	
	/**
	 * 
	 * @param position
	 * @param size
	 * @return
	 * @since 04.09.2018/0.4.0
	 */
	public boolean intersects(Vector3f position, Vector3f size) {
		
		return this.intersects(position.x, position.y, position.z, size.x, size.y, size.z);
	}
	
	/**
	 * 
	 * @param bounds
	 * @return
	 * @since 04.09.2018/0.4.0
	 */
	public boolean intersects(AxisAlignedBox bounds) {
		
		return this.intersects(bounds.x, bounds.y, bounds.z, bounds.width, bounds.height, bounds.depth);
	}
	
	/**
	 * 
	 * @param x
	 * @param y
	 * @param z
	 * @param width
	 * @param height
	 * @param depth
	 * @return
	 * @since 04.09.2018/0.4.0
	 */
	public boolean intersects(float x, float y, float z, float width, float height, float depth) {
		
		return this.width > 0 && this.height > 0 && this.depth > 0 &&
			   width > 0 && height > 0 && depth > 0 &&
			   x < this.x + this.width && x + width > this.x &&
			   y < this.y + this.height && y + height > this.y &&
			   z < this.z + this.depth && z + depth > this.z;
	}
	
	@Override
	public Vector3f center() {
		
		return new Vector3f(this.x + this.width * 0.5F,
							this.y + this.height * 0.5F,
							this.z + this.depth * 0.5F);
	}
	
	/**
	 * 
	 * @return
	 * @since 06.09.2018/0.4.0
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
