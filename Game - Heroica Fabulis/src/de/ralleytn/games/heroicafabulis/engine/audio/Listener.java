package de.ralleytn.games.heroicafabulis.engine.audio;

import javax.vecmath.Matrix4f;
import javax.vecmath.Vector3f;

import static org.lwjgl.openal.AL10.*;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import de.ralleytn.games.heroicafabulis.engine.Translatable;

/**
 * Represents the listener for sound.
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 26.08.2018/0.3.0
 * @since 17.08.2018/0.2.0
 */
public final class Listener implements Translatable {

	private static final int ORIENTATION_BUFFER_SIZE = 24;
	
	/**
	 * @since 17.08.2018/0.2.0
	 */
	Listener() {}

	/**
	 * Sets the master volume.
	 * @param gain the master volume
	 * @since 17.08.2018/0.2.0
	 */
	public void setGain(float gain) {
		
		alListenerf(AL_GAIN, gain);
	}

	/**
	 * Sets the orientation.
	 * @param viewMatrix the orientation
	 * @since 17.08.2018/0.2.0
	 */
	public void setOrientation(Matrix4f viewMatrix) {
		
		ByteBuffer byteBuffer = ByteBuffer.allocateDirect(ORIENTATION_BUFFER_SIZE);
	    byteBuffer.order(ByteOrder.nativeOrder());
	    FloatBuffer orientationBuffer = byteBuffer.asFloatBuffer();
	    orientationBuffer.put(0, viewMatrix.m01);
	    orientationBuffer.put(1, viewMatrix.m02);
	    orientationBuffer.put(2, viewMatrix.m03);
	    orientationBuffer.put(3, viewMatrix.m11);
	    orientationBuffer.put(4, viewMatrix.m12);
	    orientationBuffer.put(5, viewMatrix.m13);
		
		alListenerfv(AL_ORIENTATION, orientationBuffer);
	}

	/**
	 * Sets the velocity of the listener.
	 * Required for Doppler effect.
	 * @param x velocity on X axis
	 * @param y velocity on Y axis
	 * @param z velocity on Z axis
	 * @since 17.08.2018/0.2.0
	 */
	public void setVelocity(float x, float y, float z) {
		
		alListener3f(AL_VELOCITY, x, y, z);
	}
	
	@Override
	public void setTranslation(float x, float y, float z) {

		alListener3f(AL_POSITION, x, y, z);
	}
	
	@Override
	public void setTranslation(Vector3f newTranslation) {

		alListener3f(AL_POSITION, newTranslation.x, newTranslation.y, newTranslation.z);
	}
	
	@Override
	public void translate(float xVelocity, float yVelocity, float zVelocity) {

		Vector3f translation = this.getTranslation();
		alListener3f(AL_POSITION, translation.x + xVelocity, translation.y + yVelocity, translation.z + zVelocity);
	}
	
	@Override
	public void translate(Vector3f velocity) {

		Vector3f translation = this.getTranslation();
		alListener3f(AL_POSITION, translation.x + velocity.x, translation.y + velocity.y, translation.z + velocity.z);
	}

	/**
	 * @return the master volume
	 * @since 17.08.2018/0.2.0
	 */
	public float getGain() {
		
		return alGetListenerf(AL_GAIN);
	}

	/**
	 * @return the orientation
	 * @since 17.08.2018/0.2.0
	 */
	public int getOrientation() {
		
		return alGetListeneri(AL_ORIENTATION);
	}

	/**
	 * @return the velocity
	 * @since 17.08.2018/0.2.0
	 */
	public Vector3f getVelocity() {
		
		float[] x = new float[1];
		float[] y = new float[1];
		float[] z = new float[1];
		alGetListener3f(AL_VELOCITY, x, y, z);
		return new Vector3f(x[0], y[0], z[0]);
	}

	@Override
	public Vector3f getTranslation() {
		
		float[] x = new float[1];
		float[] y = new float[1];
		float[] z = new float[1];
		alGetListener3f(AL_POSITION, x, y, z);
		return new Vector3f(x[0], y[0], z[0]);
	}
}
