package de.ralleytn.engine.caveman;

import javax.vecmath.Matrix4f;
import javax.vecmath.Vector3f;

import de.ralleytn.engine.caveman.rendering.geom.Mesh;
import de.ralleytn.engine.caveman.shape3d.Box3D;
import de.ralleytn.engine.caveman.util.MatrixUtil;

/**
 * Represents an entity. An entity is a transformable and updatable object on the scene.
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 05.09.2018/0.4.0
 * @since 30.07.2018/0.1.0
 */
public class Entity3D extends RenderableObject implements Movable, Scalable, Updatable, Comparable<Entity3D> {
	
	private static long ID_SUPPLY = Long.MIN_VALUE;
	
	private Vector3f translation;
	private Vector3f rotation;
	private Vector3f scale;
	private Mesh mesh;
	private float renderDistance;
	private long id;
	private Box3D boundingBox;
	
	/**
	 * @since 30.07.2018/0.1.0
	 */
	public Entity3D() {
		
		this.translation = new Vector3f();
		this.rotation = new Vector3f();
		this.scale = new Vector3f(1.0F, 1.0F, 1.0F);
		this.transformation = new Matrix4f();
		this.rendering = true;
		this.boundingBox = new Box3D();
		this.renderDistance = 1000.0F;
		this.calcTransformationMatrix();
		this.assignID();
	}
	
	/**
	 * 
	 * @since 04.09.2018/0.4.0
	 */
	private final void assignID() {
		
		long id = ID_SUPPLY++;
		id = id != 0 ? id : ID_SUPPLY++;
		this.id = id;
	}
	
	/**
	 * Sets the render distance.
	 * If the distance between the player and the entity is higher than the render distance, the object will no longer be rendered.
	 * @param renderDistance the render distance
	 * @since 26.08.2018/0.3.0
	 */
	public void setRenderDistance(float renderDistance) {
		
		this.renderDistance = renderDistance;
	}
	
	/**
	 * <br><i>Calling this method will recalculate the transformation matrix</i>
	 */
	@Override
	public void setRotation(float x, float y, float z) {

		Movable.super.setRotation(x, y, z);
		this.calcTransformationMatrix();
		this.calcBoundingBox();
	}
	
	/**
	 * <br><i>Calling this method will recalculate the transformation matrix</i>
	 */
	@Override
	public void setRotation(Vector3f newRotation) {

		Movable.super.setRotation(newRotation);
		this.calcTransformationMatrix();
		this.calcBoundingBox();
	}
	
	/**
	 * <br><i>Calling this method will recalculate the transformation matrix</i>
	 */
	@Override
	public void setScale(float x, float y, float z) {

		Scalable.super.setScale(x, y, z);
		this.calcTransformationMatrix();
		this.calcBoundingBox();
	}
	
	/**
	 * <br><i>Calling this method will recalculate the transformation matrix</i>
	 */
	@Override
	public void setScale(Vector3f newScale) {

		Scalable.super.setScale(newScale);
		this.calcTransformationMatrix();
		this.calcBoundingBox();
	}
	
	/**
	 * <br><i>Calling this method will recalculate the transformation matrix</i>
	 */
	@Override
	public void setTranslation(float x, float y, float z) {

		Movable.super.setTranslation(x, y, z);
		this.calcTransformationMatrix();
		this.calcBoundingBox();
	}
	
	/**
	 * <br><i>Calling this method will recalculate the transformation matrix</i>
	 */
	@Override
	public void setTranslation(Vector3f newTranslation) {

		Movable.super.setTranslation(newTranslation);
		this.calcTransformationMatrix();
		this.calcBoundingBox();
	}
	
	/**
	 * <br><i>Calling this method will recalculate the transformation matrix</i>
	 */
	@Override
	public void scale(float x, float y, float z) {

		Scalable.super.scale(x, y, z);
		this.calcTransformationMatrix();
		this.calcBoundingBox();
	}
	
	/**
	 * <br><i>Calling this method will recalculate the transformation matrix</i>
	 */
	@Override
	public void scale(Vector3f factor) {

		Scalable.super.scale(factor);
		this.calcTransformationMatrix();
		this.calcBoundingBox();
	}
	
	/**
	 * <br><i>Calling this method will recalculate the transformation matrix</i>
	 */
	@Override
	public void rotate(float x, float y, float z) {

		Movable.super.rotate(x, y, z);
		this.calcTransformationMatrix();
		this.calcBoundingBox();
	}
	
	/**
	 * <br><i>Calling this method will recalculate the transformation matrix</i>
	 */
	@Override
	public void rotate(Vector3f velocity) {

		Movable.super.rotate(velocity);
		this.calcTransformationMatrix();
		this.calcBoundingBox();
	}
	
	/**
	 * <br><i>Calling this method will recalculate the transformation matrix</i>
	 */
	@Override
	public void translate(float x, float y, float z) {

		Movable.super.translate(x, y, z);
		this.calcTransformationMatrix();
		this.calcBoundingBox();
	}
	
	/**
	 * <br><i>Calling this method will recalculate the transformation matrix</i>
	 */
	@Override
	public void translate(Vector3f velocity) {

		Movable.super.translate(velocity);
		this.calcTransformationMatrix();
		this.calcBoundingBox();
	}
	
	@Override
	protected final void calcTransformationMatrix() {
		
		this.transformation.setIdentity();
		MatrixUtil.translate(this.translation, this.transformation);
		MatrixUtil.rotate((float)Math.toRadians(this.rotation.x), Engine.AXIS_X, this.transformation);
		MatrixUtil.rotate((float)Math.toRadians(this.rotation.y), Engine.AXIS_Y, this.transformation);
		MatrixUtil.rotate((float)Math.toRadians(this.rotation.z), Engine.AXIS_Z, this.transformation);
		MatrixUtil.scale(this.scale, this.transformation);
	}
	
	/**
	 * 
	 * @since 05.09.2018/0.4.0
	 */
	private final void calcBoundingBox() {
		
		if(this.mesh != null) {
			
			float[] vertices = this.mesh.getVertexArray().getBuffer(0).getDataAsFloats();
			
			float xn = Float.MAX_VALUE;
			float yn = xn;
			float zn = xn;
			
			float xf = -Float.MAX_VALUE;
			float yf = xf;
			float zf = xf;
			
			for(int index = 0; index < vertices.length; index += 3) {
				
				Vector3f vertex = MatrixUtil.multiply(this.transformation, vertices[index], vertices[index + 1], vertices[index + 2]);
				
				float x = vertex.x;
				float y = vertex.y;
				float z = vertex.z;
				
				if(x < xn) xn = x; else if(x > xf) xf = x;
				if(y < yn) yn = y; else if(y > yf) yf = y;
				if(z < zn) zn = z; else if(z > zf) zf = z;
			}
			
			float width = xf - xn;
			float height = yf - yn;
			float depth = zf - zn;
			
			this.boundingBox.setBounds(xn, yn, zn, width, height, depth);
		}
	}
	
	/**
	 * Sets the mesh that this entity should use.
	 * @param mesh the mesh
	 * @since 10.08.2018/0.1.0
	 */
	public void setMesh(Mesh mesh) {
		
		if(this.mesh != null) {
			
			this.mesh.dispose();
		}
		
		this.mesh = mesh;
	}

	@Override
	public Vector3f getTranslation() {
		
		return this.translation;
	}

	@Override
	public Vector3f getRotation() {
		
		return this.rotation;
	}

	@Override
	public Vector3f getScale() {
		
		return this.scale;
	}
	
	/**
	 * @return the mesh
	 * @since 22.08.2018/0.2.0
	 */
	public Mesh getMesh() {
		
		return this.mesh;
	}

	@Override
	public void update(float delta) {}
	
	/**
	 * @return the render distance
	 * @since 26.08.2018/0.3.0
	 */
	public float getRenderDistance() {
		
		return this.renderDistance;
	}
	
	/**
	 * 
	 * @return
	 * @since 04.09.2018/0.4.0
	 */
	public final long getID() {
		
		return this.id;
	}
	
	/**
	 * 
	 * @return
	 * @since 04.09.2018/0.4.0
	 */
	private final int getSortValue() {
		
		return this.material.hashCode() * this.mesh.hashCode();
	}
	
	/**
	 * 
	 * @return
	 * @since 04.09.2018/0.4.0
	 */
	public Entity3D copy() {
		
		Entity3D entity = new Entity3D();
		entity.material = this.material;
		entity.mesh = this.mesh;
		entity.rendering = this.rendering;
		entity.rotation = new Vector3f(this.rotation);
		entity.scale = new Vector3f(this.scale);
		entity.translation = new Vector3f(this.translation);
		entity.transformation = new Matrix4f(this.transformation);
		entity.shaderPipeline = this.shaderPipeline;
		
		return entity;
	}

	@Override
	public int compareTo(Entity3D o) {
		
		int sortValue = this.getSortValue();
		int oSortValue = o.getSortValue();
		return sortValue == oSortValue ? 0 : (sortValue > oSortValue ? 1 : -1);
	}
	
	/**
	 * 
	 * @return
	 * @since 05.09.2018/0.4.0
	 */
	public Box3D getBoundingBox() {
		
		return this.boundingBox;
	}
}
