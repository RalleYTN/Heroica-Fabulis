package de.ralleytn.games.heroicafabulis.engine.audio;

import javax.vecmath.Vector3f;

import static org.lwjgl.openal.AL10.*;

import de.ralleytn.games.heroicafabulis.engine.Translatable;

/**
 * 
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 17.08.2018/0.2.0
 * @since 17.08.2018/0.2.0
 */
public final class Listener implements Translatable {

	/**
	 * @since 17.08.2018/0.2.0
	 */
	Listener() {}

	/**
	 * 
	 * @param gain
	 * @since 17.08.2018/0.2.0
	 */
	public void setGain(float gain) {
		
		alListenerf(AL_GAIN, gain);
	}

	/**
	 * 
	 * @param orientation
	 * @since 17.08.2018/0.2.0
	 */
	public void setOrientation(int orientation) {
		
		alListeneri(AL_ORIENTATION, orientation);
	}

	/**
	 * 
	 * @param x
	 * @param y
	 * @param z
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
	 * 
	 * @return
	 * @since 17.08.2018/0.2.0
	 */
	public float getGain() {
		
		return alGetListenerf(AL_GAIN);
	}

	/**
	 * 
	 * @return
	 * @since 17.08.2018/0.2.0
	 */
	public int getOrientation() {
		
		return alGetListeneri(AL_ORIENTATION);
	}

	/**
	 * 
	 * @return
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
