package de.ralleytn.engine.caveman;

import javax.vecmath.Matrix4f;
import javax.vecmath.Vector3f;

import de.ralleytn.engine.caveman.rendering.geom.AxisAlignedBox;
import de.ralleytn.engine.caveman.rendering.geom.Mesh;
import de.ralleytn.engine.caveman.rendering.geom.OrientedBox;
import de.ralleytn.engine.caveman.util.MatrixUtil;

/**
 * Represents an entity. An entity is a transformable and updatable object on the scene.
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 08.09.2018/0.4.0
 * @since 30.07.2018/0.1.0
 */
public class Entity extends RenderableObject implements Transformable, Updatable, Comparable<Entity> {
	
	private static long ID_SUPPLY = Long.MIN_VALUE;
	
	private Vector3f translation;
	private Vector3f rotation;
	private Vector3f scale;
	private Mesh mesh;
	private float renderDistance;
	private long id;
	private AxisAlignedBox aabb;
	private OrientedBox obb;
	
	/**
	 * @since 30.07.2018/0.1.0
	 */
	public Entity() {
		
		this.translation = new Vector3f();
		this.rotation = new Vector3f();
		this.scale = new Vector3f(1.0F, 1.0F, 1.0F);
		this.transformation = new Matrix4f();
		this.rendering = true;
		this.aabb = new AxisAlignedBox();
		this.obb = new OrientedBox();
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

		Transformable.super.setRotation(x, y, z);
		this.calcBoundingBoxes();
	}
	
	/**
	 * <br><i>Calling this method will recalculate the transformation matrix</i>
	 */
	@Override
	public void setRotation(Vector3f newRotation) {

		Transformable.super.setRotation(newRotation);
		this.calcBoundingBoxes();
	}
	
	/**
	 * <br><i>Calling this method will recalculate the transformation matrix</i>
	 */
	@Override
	public void setScale(float x, float y, float z) {

		Transformable.super.setScale(x, y, z);
		this.calcBoundingBoxes();
	}
	
	/**
	 * <br><i>Calling this method will recalculate the transformation matrix</i>
	 */
	@Override
	public void setScale(Vector3f newScale) {

		Transformable.super.setScale(newScale);
		this.calcBoundingBoxes();
	}
	
	/**
	 * <br><i>Calling this method will recalculate the transformation matrix</i>
	 */
	@Override
	public void setTranslation(float x, float y, float z) {

		Transformable.super.setTranslation(x, y, z);
		this.calcBoundingBoxes();
	}
	
	/**
	 * <br><i>Calling this method will recalculate the transformation matrix</i>
	 */
	@Override
	public void setTranslation(Vector3f newTranslation) {

		Transformable.super.setTranslation(newTranslation);
		this.calcBoundingBoxes();
	}
	
	/**
	 * <br><i>Calling this method will recalculate the transformation matrix</i>
	 */
	@Override
	public void scale(float x, float y, float z) {

		Transformable.super.scale(x, y, z);
		this.calcBoundingBoxes();
	}
	
	/**
	 * <br><i>Calling this method will recalculate the transformation matrix</i>
	 */
	@Override
	public void scale(Vector3f factor) {

		Transformable.super.scale(factor);
		this.calcBoundingBoxes();
	}
	
	/**
	 * <br><i>Calling this method will recalculate the transformation matrix</i>
	 */
	@Override
	public void rotate(float x, float y, float z) {

		Transformable.super.rotate(x, y, z);
		this.calcBoundingBoxes();
	}
	
	/**
	 * <br><i>Calling this method will recalculate the transformation matrix</i>
	 */
	@Override
	public void rotate(Vector3f velocity) {

		Transformable.super.rotate(velocity);
		this.calcBoundingBoxes();
	}
	
	/**
	 * <br><i>Calling this method will recalculate the transformation matrix</i>
	 */
	@Override
	public void translate(float x, float y, float z) {

		Transformable.super.translate(x, y, z);
		this.calcBoundingBoxes();
	}
	
	/**
	 * <br><i>Calling this method will recalculate the transformation matrix</i>
	 */
	@Override
	public void translate(Vector3f velocity) {

		Transformable.super.translate(velocity);
		this.calcBoundingBoxes();
	}
	
	/**
	 * 
	 * @since 05.09.2018/0.4.0
	 */
	public void calcBoundingBoxes() {
		
		if(this.mesh != null) {
			
			float[] vertices = this.mesh.getVertexArray().getBuffer(0).getDataAsFloats();
			
			float xaabbn = Float.MAX_VALUE;
			float yaabbn = xaabbn;
			float zaabbn = xaabbn;
			
			float xobbn = Float.MAX_VALUE;
			float yobbn = xobbn;
			float zobbn = xobbn;
			
			float xaabbf = -Float.MAX_VALUE;
			float yaabbf = xaabbf;
			float zaabbf = xaabbf;
			
			float xobbf = -Float.MAX_VALUE;
			float yobbf = xobbf;
			float zobbf = xobbf;
			
			for(int index = 0; index < vertices.length; index += 3) {
				
				float xobb = vertices[index];
				float yobb = vertices[index + 1];
				float zobb = vertices[index + 2];
				
				if(xobb < xobbn) xobbn = xobb; else if(xobb > xobbf) xobbf = xobb;
				if(yobb < yobbn) yobbn = yobb; else if(yobb > yobbf) yobbf = yobb;
				if(zobb < zobbn) zobbn = zobb; else if(zobb > zobbf) zobbf = zobb;
				
				Vector3f vertex = MatrixUtil.multiply(this.transformation, vertices[index], vertices[index + 1], vertices[index + 2]);
				
				float xaabb = vertex.x;
				float yaabb = vertex.y;
				float zaabb = vertex.z;
				
				if(xaabb < xaabbn) xaabbn = xaabb; else if(xaabb > xaabbf) xaabbf = xaabb;
				if(yaabb < yaabbn) yaabbn = yaabb; else if(yaabb > yaabbf) yaabbf = yaabb;
				if(zaabb < zaabbn) zaabbn = zaabb; else if(zaabb > zaabbf) zaabbf = zaabb;
			}
			
			float waabb = xaabbf - xaabbn;
			float haabb = yaabbf - yaabbn;
			float daabb = zaabbf - zaabbn;
			
			float wobb = xobbf - xobbn;
			float hobb = yobbf - yobbn;
			float dobb = zobbf - zobbn;
			
			System.out.println(wobb);
			
			this.aabb.set(xaabbn, yaabbn, zaabbn, waabb, haabb, daabb);
			this.obb.set(this.translation.x - xobbn, this.translation.y - yobbn, this.translation.z - zobbn, wobb, hobb, dobb, this.rotation.x, this.rotation.y, this.rotation.z);
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
	
	public Matrix4f getTransformation() {
		
		return this.transformation;
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
	public Entity copy() {
		
		Entity entity = new Entity();
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
	
	/**
	 * 
	 * @param entity
	 * @return
	 * @since 07.09.2018/0.4.0
	 */
	public boolean collidesWithOBB(Entity entity) {
		
		return entity.obb.intersects(this.obb);
	}
	
	/**
	 * 
	 * @param entity
	 * @return
	 * @since 07.09.2018/0.4.0
	 */
	public boolean collidesWithAABB(Entity entity) {
		
		return entity.aabb.intersects(this.aabb);
	}

	@Override
	public int compareTo(Entity o) {
		
		int sortValue = this.getSortValue();
		int oSortValue = o.getSortValue();
		return sortValue == oSortValue ? 0 : (sortValue > oSortValue ? 1 : -1);
	}
	
	/**
	 * 
	 * @return
	 * @since 05.09.2018/0.4.0
	 */
	public AxisAlignedBox getAABB() {
		
		return this.aabb;
	}
	
	/**
	 * 
	 * @return
	 * @since 08.09.2018/0.4.0
	 */
	public OrientedBox getOBB() {
		
		return this.obb;
	}
}
