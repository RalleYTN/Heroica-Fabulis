package de.ralleytn.engine.caveman;

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
public interface Transformable extends Movable, Scalable {

	/**
	 * 
	 * @return
	 * @since 08.09.2018/0.4.0
	 */
	public Matrix4f getTransformation();
	
	/**
	 * 
	 * @since 08.09.2018/0.4.0
	 */
	public default void calcTransformationMatrix() {
		
		Matrix4f transformation = this.getTransformation();
		Vector3f rotation = this.getRotation();
		transformation.setIdentity();
		MatrixUtil.translate(this.getTranslation(), transformation);
		MatrixUtil.rotate((float)Math.toRadians(rotation.x), VectorUtil.AXIS_X, transformation);
		MatrixUtil.rotate((float)Math.toRadians(rotation.y), VectorUtil.AXIS_Y, transformation);
		MatrixUtil.rotate((float)Math.toRadians(rotation.z), VectorUtil.AXIS_Z, transformation);
		MatrixUtil.scale(this.getScale(), transformation);
	}
	
	@Override
	public default void setRotation(float x, float y, float z) {
		
		Movable.super.setRotation(x, y, z);
		this.calcTransformationMatrix();
	}
	
	@Override
	public default void setRotation(Vector3f newRotation) {

		Movable.super.setRotation(newRotation);
		this.calcTransformationMatrix();
	}
	
	@Override
	public default void rotate(float x, float y, float z) {

		Movable.super.rotate(x, y, z);
		this.calcTransformationMatrix();
	}
	
	@Override
	public default void rotate(Vector3f velocity) {

		Movable.super.rotate(velocity);
		this.calcTransformationMatrix();
	}
	
	@Override
	public default void setTranslation(float x, float y, float z) {

		Movable.super.setTranslation(x, y, z);
		this.calcTransformationMatrix();
	}
	
	@Override
	public default void setTranslation(Vector3f newTranslation) {

		Movable.super.setTranslation(newTranslation);
		this.calcTransformationMatrix();
	}
	
	@Override
	public default void translate(float xVelocity, float yVelocity, float zVelocity) {

		Movable.super.translate(xVelocity, yVelocity, zVelocity);
		this.calcTransformationMatrix();
	}
	
	@Override
	public default void translate(Vector3f velocity) {

		Movable.super.translate(velocity);
		this.calcTransformationMatrix();
	}
	
	@Override
	public default void setScale(float x, float y, float z) {

		Scalable.super.setScale(x, y, z);
		this.calcTransformationMatrix();
	}
	
	@Override
	public default void setScale(Vector3f newScale) {

		Scalable.super.setScale(newScale);
		this.calcTransformationMatrix();
	}
	
	@Override
	public default void scale(float x, float y, float z) {

		Scalable.super.scale(x, y, z);
		this.calcTransformationMatrix();
	}
	
	@Override
	public default void scale(Vector3f units) {

		Scalable.super.scale(units);
		this.calcTransformationMatrix();
	}
}
