package de.ralleytn.engine.caveman.util;

import javax.vecmath.Matrix4f;
import javax.vecmath.Vector3f;

import de.ralleytn.engine.caveman.Engine;

/**
 * Utility class containg methods for working with matrices.
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 25.08.2018/0.3.0
 * @since 11.08.2018/0.1.0
 */
public final class MatrixUtil {

	/**
	 * @since 11.08.2018/0.1.0
	 */
	private MatrixUtil() {}
	
	/**
	 * Creates a new transformation matrix.
	 * @param translation the translation
	 * @param rotation the rotation
	 * @param scale the scale
	 * @return the created transformation matrix
	 * @since 25.08.2018/0.3.0
	 */
	public static final Matrix4f createTransformationMatrx(Vector3f translation, Vector3f rotation, Vector3f scale) {
		
		Matrix4f transformation = new Matrix4f();
		transformation.setIdentity();
		translate(translation, transformation);
		rotate((float)Math.toRadians(rotation.x), Engine.AXIS_X, transformation);
		rotate((float)Math.toRadians(rotation.y), Engine.AXIS_Y, transformation);
		rotate((float)Math.toRadians(rotation.z), Engine.AXIS_Z, transformation);
		scale(scale, transformation);
		return transformation;
	}
	
	/**
	 * Converts a {@linkplain Matrix4f} to a float array.
	 * @param matrix the {@linkplain Matrix4f}
	 * @return the resulting float array
	 * @since 11.08.2018/0.1.0
	 */
	public static final float[] toArray4f(Matrix4f matrix) {
		
		return new float[] {
				
			matrix.m00, matrix.m01, matrix.m02, matrix.m03,
			matrix.m10, matrix.m11, matrix.m12, matrix.m13,
			matrix.m20, matrix.m21, matrix.m22, matrix.m23,
			matrix.m30, matrix.m31, matrix.m32, matrix.m33,
		};
	}
	
	/**
	 * Multiplies a 4D matrix with a 3D vector.
	 * @param matrix the 4D matrix
	 * @param x X component of the 3D vector
	 * @param y Y component of the 3D vector
	 * @param z Z component of the 3D vector
	 * @return the resulting vector
	 * @since 11.08.2018/0.1.0
	 */
	public static final Vector3f multiply(Matrix4f matrix, float x, float y, float z) {
		
		Vector3f result = new Vector3f();
		result.x = (x * matrix.m00) + (y * matrix.m10) + (z * matrix.m20) + matrix.m30;
		result.y = (x * matrix.m01) + (y * matrix.m11) + (z * matrix.m21) + matrix.m31;
		result.z = (x * matrix.m02) + (y * matrix.m12) + (z * matrix.m22) + matrix.m32;
		return result;
	}
	
	/**
	 * Calculates the rotation matrix.
	 * @param rotation the rotation
	 * @param matrix the matrix
	 * @since 11.08.2018/0.1.0
	 */
	public static final void calcRotationMatrix(Vector3f rotation, Matrix4f matrix) {
		
		matrix.setIdentity();
		MatrixUtil.rotate(rotation.x, Engine.AXIS_X, matrix);
		MatrixUtil.rotate(rotation.y, Engine.AXIS_Y, matrix);
		MatrixUtil.rotate(rotation.z, Engine.AXIS_Z, matrix);
	}
	
	/**
	 * Scales a matrix.
	 * @param scale the scale
	 * @param matrix the matrix
	 * @since 11.08.2018/0.1.0
	 */
	public static final void scale(Vector3f scale, Matrix4f matrix) {
		
		matrix.m00 *= scale.x;
		matrix.m01 *= scale.x;
		matrix.m02 *= scale.x;
		matrix.m03 *= scale.x;
		
		matrix.m10 *= scale.y;
		matrix.m11 *= scale.y;
		matrix.m12 *= scale.y;
		matrix.m13 *= scale.y;
		
		matrix.m20 *= scale.z;
		matrix.m21 *= scale.z;
		matrix.m22 *= scale.z;
		matrix.m23 *= scale.z;
	}
	
	/**
	 * Translates a matrix.
	 * @param translation the translation
	 * @param matrix the matrix
	 * @since 11.08.2018/0.1.0
	 */
	public static final void translate(Vector3f translation, Matrix4f matrix) {
		
		matrix.m30 += matrix.m00 * translation.x + matrix.m10 * translation.y + matrix.m20 * translation.z;
		matrix.m31 += matrix.m01 * translation.x + matrix.m11 * translation.y + matrix.m21 * translation.z;
		matrix.m32 += matrix.m02 * translation.x + matrix.m12 * translation.y + matrix.m22 * translation.z;
		matrix.m33 += matrix.m03 * translation.x + matrix.m13 * translation.y + matrix.m23 * translation.z;
	}
	
	/**
	 * Rotates a matrix.
	 * @param angle the angle
	 * @param axis the axis
	 * @param matrix the matrix
	 * @since 11.08.2018/0.1.0
	 */
	public static final void rotate(float angle, Vector3f axis, Matrix4f matrix) {
		
		float c = (float)Math.cos(angle);
		float s = (float)Math.sin(angle);
		
		float oneminusc = 1.0F - c;
		
		float xy = axis.x * axis.y;
		float yz = axis.y * axis.z;
		float xz = axis.x * axis.z;
		
		float xs = axis.x * s;
		float ys = axis.y * s;
		float zs = axis.z * s;

		float f00 = axis.x * axis.x * oneminusc + c;
		float f01 = xy * oneminusc + zs;
		float f02 = xz * oneminusc - ys;
		float f10 = xy * oneminusc - zs;
		float f11 = axis.y * axis.y * oneminusc + c;
		float f12 = yz * oneminusc + xs;
		float f20 = xz * oneminusc + ys;
		float f21 = yz * oneminusc - xs;
		float f22 = axis.z * axis.z * oneminusc + c;

		float t00 = matrix.m00 * f00 + matrix.m10 * f01 + matrix.m20 * f02;
		float t01 = matrix.m01 * f00 + matrix.m11 * f01 + matrix.m21 * f02;
		float t02 = matrix.m02 * f00 + matrix.m12 * f01 + matrix.m22 * f02;
		float t03 = matrix.m03 * f00 + matrix.m13 * f01 + matrix.m23 * f02;
		float t10 = matrix.m00 * f10 + matrix.m10 * f11 + matrix.m20 * f12;
		float t11 = matrix.m01 * f10 + matrix.m11 * f11 + matrix.m21 * f12;
		float t12 = matrix.m02 * f10 + matrix.m12 * f11 + matrix.m22 * f12;
		float t13 = matrix.m03 * f10 + matrix.m13 * f11 + matrix.m23 * f12;
		
		matrix.m20 = matrix.m00 * f20 + matrix.m10 * f21 + matrix.m20 * f22;
		matrix.m21 = matrix.m01 * f20 + matrix.m11 * f21 + matrix.m21 * f22;
		matrix.m22 = matrix.m02 * f20 + matrix.m12 * f21 + matrix.m22 * f22;
		matrix.m23 = matrix.m03 * f20 + matrix.m13 * f21 + matrix.m23 * f22;
		
		matrix.m00 = t00;
		matrix.m01 = t01;
		matrix.m02 = t02;
		matrix.m03 = t03;
		matrix.m10 = t10;
		matrix.m11 = t11;
		matrix.m12 = t12;
		matrix.m13 = t13;
	}
}
