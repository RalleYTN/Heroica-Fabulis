package de.ralleytn.games.heroicafabulis.engine.audio;

import javax.vecmath.Vector3f;

import static org.lwjgl.openal.AL11.*;
import static org.lwjgl.openal.AL10.*;

import de.ralleytn.games.heroicafabulis.engine.LWJGLObject;
import de.ralleytn.games.heroicafabulis.engine.Movable;

/**
 * Represents a source from which audio is emitted.
 * 3D audio can only be simulated with mono sounds.
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 17.08.2018/0.2.0
 * @since 17.08.2018/0.2.0
 */
public class Source extends LWJGLObject implements Movable {

	// Collected them here because I don't like searching for this stuff.
	/** @since 17.08.2018/0.2.0 */ public static final int TYPE_UNDETERMINED = AL_UNDETERMINED;
	/** @since 17.08.2018/0.2.0 */ public static final int TYPE_STATIC = AL_STATIC;
	/** @since 17.08.2018/0.2.0 */ public static final int TYPE_STREAMING = AL_STREAMING;
	
	/** @since 17.08.2018/0.2.0 */ public static final int STATE_INITIAL = AL_INITIAL;
	/** @since 17.08.2018/0.2.0 */ public static final int STATE_PAUSED = AL_PAUSED;
	/** @since 17.08.2018/0.2.0 */ public static final int STATE_PLAYING = AL_PLAYING;
	/** @since 17.08.2018/0.2.0 */ public static final int STATE_STOPPED = AL_STOPPED;
	
	private ALBuffer buffer;
	
	/**
	 * @since 17.08.2018/0.2.0
	 */
	public Source() {
		
		this.id = alGenSources();
	}
	
	@Override
	public void dispose() {
		
		this.stop();
		this.setBuffer(null);
		alDeleteSources(this.id);
	}
	
	/**
	 * Plays the audio.
	 * @since 17.08.2018/0.2.0
	 */
	public void play() {
		
		alSourcePlay(this.id);
	}
	
	/**
	 * Pauses the audio.
	 * @since 17.08.2018/0.2.0
	 */
	public void pause() {
		
		alSourcePause(this.id);
	}

	/**
	 * Stops the audio.
	 * Basically pauses the audio and sets the position back to {@code 0}.
	 * @since 17.08.2018/0.2.0
	 */
	public void stop() {
		
		alSourceStop(this.id);
	}

	/**
	 * Rewinds the audio.
	 * @since 17.08.2018/0.2.0
	 */
	public void rewind() {
		
		alSourceRewind(this.id);
	}
	
	/**
	 * Sets the {@linkplain ALBuffer} with the audio data.
	 * @param buffer the {@linkplain ALBuffer}
	 * @since 17.08.2018/0.2.0
	 */
	public void setBuffer(ALBuffer buffer) {
		
		this.stop();
		this.buffer = buffer;
		alSourcei(this.id, AL_BUFFER, buffer.getID());
	}
	
	/**
	 * Sets the volume of the source.
	 * @param gain the volume
	 * @since 17.08.2018/0.2.0
	 */
	public void setGain(float gain) {
		
		alSourcef(this.id, AL_GAIN, gain);
	}

	/**
	 * Sets the pitch.
	 * @param pitch the pitch.
	 * @since 17.08.2018/0.2.0
	 */
	public void setPitch(float pitch) {
		
		alSourcef(this.id, AL_PITCH, pitch);
	}

	/**
	 * Sets the distance in which the sound can still be heard.
	 * @param maxDistance the distance
	 * @since 17.08.2018/0.2.0
	 */
	public void setMaxDistance(float maxDistance) {
		
		alSourcef(this.id, AL_MAX_DISTANCE, maxDistance);
	}

	/**
	 * Sets the roll-of factor.
	 * @param rolloffFactor the roll-off factor
	 * @since 17.08.2018/0.2.0
	 */
	public void setRolloffFactor(float rolloffFactor) {
		
		alSourcef(this.id, AL_ROLLOFF_FACTOR, rolloffFactor);
	}

	/**
	 * Sets the minimum volume.
	 * @param minGain the minimum volume
	 * @since 17.08.2018/0.2.0
	 */
	public void setMinGain(float minGain) {
		
		alSourcef(this.id, AL_MIN_GAIN, minGain);
	}

	/**
	 * 
	 * @param maxGain
	 * @since 17.08.2018/0.2.0
	 */
	public void setMaxGain(float maxGain) {
		
		alSourcef(this.id, AL_MAX_GAIN, maxGain);
	}

	/**
	 * 
	 * @param referenceDistance
	 * @since 17.08.2018/0.2.0
	 */
	public void setReferenceDistance(float referenceDistance) {
		
		alSourcef(this.id, AL_REFERENCE_DISTANCE, referenceDistance);
	}

	/**
	 * 
	 * @param coneOuterGain
	 * @since 17.08.2018/0.2.0
	 */
	public void setConeOuterGain(float coneOuterGain) {
		
		alSourcef(this.id, AL_CONE_OUTER_GAIN, coneOuterGain);
	}

	/**
	 * 
	 * @param coneInnerAngle
	 * @since 17.08.2018/0.2.0
	 */
	public void setConeInnerAngle(float coneInnerAngle) {
		
		alSourcef(this.id, AL_CONE_INNER_ANGLE, coneInnerAngle);
	}

	/**
	 * 
	 * @param coneOuterAngle
	 * @since 17.08.2018/0.2.0
	 */
	public void setConeOuterAngle(float coneOuterAngle) {
		
		alSourcef(this.id, AL_CONE_OUTER_ANGLE, coneOuterAngle);
	}
	
	/**
	 * 
	 * @param relativeToListener
	 * @since 17.08.2018/0.2.0
	 */
	public void setRelativeToListener(boolean relativeToListener) {
		
		alSourcei(this.id, AL_SOURCE_RELATIVE, relativeToListener ? AL_TRUE : AL_FALSE);
	}

	/**
	 * 
	 * @param offset
	 * @since 17.08.2018/0.2.0
	 */
	public void setOffsetInSeconds(float offset) {
		
		alSourcef(this.id, AL_SEC_OFFSET, offset);
	}

	/**
	 * 
	 * @param offset
	 * @since 17.08.2018/0.2.0
	 */
	public void setOffsetInSamples(float offset) {
		
		alSourcef(this.id, AL_SAMPLE_OFFSET, offset);
	}

	/**
	 * 
	 * @param offset
	 * @since 17.08.2018/0.2.0
	 */
	public void setOffsetInBytes(int offset) {
		
		alSourcei(this.id, AL_BYTE_OFFSET, offset);
	}
	
	/**
	 * 
	 * @param looping
	 * @since 17.08.2018/0.2.0
	 */
	public void setLooping(boolean looping) {
		
		alSourcei(this.id, AL_LOOPING, looping ? AL_TRUE : AL_FALSE);
	}
	
	@Override
	public void setTranslation(float x, float y, float z) {
		
		alSource3f(this.id, AL_POSITION, x, y, z);
	}
	
	@Override
	public void setTranslation(Vector3f newTranslation) {
		
		alSource3f(this.id, AL_POSITION, newTranslation.x, newTranslation.y, newTranslation.z);
	}
	
	@Override
	public void translate(float xVelocity, float yVelocity, float zVelocity) {
		
		Vector3f translation = this.getTranslation();
		alSource3f(this.id, AL_POSITION, translation.x + xVelocity, translation.y + yVelocity, translation.z + zVelocity);
	}
	
	@Override
	public void translate(Vector3f velocity) {
		
		Vector3f translation = this.getTranslation();
		alSource3f(this.id, AL_POSITION, translation.x + velocity.x, translation.y + velocity.y, translation.z + velocity.z);
	}
	
	@Override
	public void setRotation(float x, float y, float z) {
		
		alSource3f(this.id, AL_DIRECTION, x, y, z);
	}
	
	@Override
	public void setRotation(Vector3f newRotation) {
		
		alSource3f(this.id, AL_DIRECTION, newRotation.x, newRotation.y, newRotation.z);
	}
	
	@Override
	public void rotate(float x, float y, float z) {
		
		Vector3f rotation = this.getRotation();
		alSource3f(this.id, AL_DIRECTION, rotation.x + x, rotation.y + y, rotation.z + z);
	}
	
	@Override
	public void rotate(Vector3f velocity) {
		
		Vector3f rotation = this.getRotation();
		alSource3f(this.id, AL_DIRECTION, rotation.x + velocity.x, rotation.y + velocity.y, rotation.z + velocity.z);
	}
	
	/**
	 * 
	 * @return
	 * @since 17.08.2018/0.2.0
	 */
	public float getGain() {
		
		return alGetSourcef(this.id, AL_GAIN);
	}

	/**
	 * 
	 * @return
	 * @since 17.08.2018/0.2.0
	 */
	public float getPitch() {
		
		return alGetSourcef(this.id, AL_PITCH);
	}

	/**
	 * 
	 * @return
	 * @since 17.08.2018/0.2.0
	 */
	public float getMaxDistance() {
		
		return alGetSourcef(this.id, AL_MAX_DISTANCE);
	}

	/**
	 * 
	 * @return
	 * @since 17.08.2018/0.2.0
	 */
	public float getRolloffFactor() {
		
		return alGetSourcef(this.id, AL_ROLLOFF_FACTOR);
	}

	/**
	 * 
	 * @return
	 * @since 17.08.2018/0.2.0
	 */
	public float getMinGain() {
		
		return alGetSourcef(this.id, AL_MIN_GAIN);
	}
	
	/**
	 * 
	 * @return
	 * @since 17.08.2018/0.2.0
	 */
	public float getMaxGain() {
		
		return alGetSourcef(this.id, AL_MAX_GAIN);
	}

	/**
	 * 
	 * @return
	 * @since 17.08.2018/0.2.0
	 */
	public float getReferenceDistance() {
		
		return alGetSourcef(this.id, AL_REFERENCE_DISTANCE);
	}

	/**
	 * 
	 * @return
	 * @since 17.08.2018/0.2.0
	 */
	public float getConeOuterGain() {
		
		return alGetSourcef(this.id, AL_CONE_OUTER_GAIN);
	}

	/**
	 * 
	 * @return
	 * @since 17.08.2018/0.2.0
	 */
	public float getConeInnerAngle() {
		
		return alGetSourcef(this.id, AL_CONE_INNER_ANGLE);
	}

	/**
	 * 
	 * @return
	 * @since 17.08.2018/0.2.0
	 */
	public float getConeOuterAngle() {
		
		return alGetSourcef(this.id, AL_CONE_OUTER_ANGLE);
	}

	/**
	 * 
	 * @return
	 * @since 17.08.2018/0.2.0
	 */
	public boolean isRelativeToListener() {
		
		return alGetSourcei(this.id, AL_SOURCE_RELATIVE) == AL_TRUE;
	}

	/**
	 * 
	 * @return
	 * @since 17.08.2018/0.2.0
	 */
	public boolean isLooping() {
		
		return alGetSourcei(this.id, AL_LOOPING) == AL_TRUE;
	}

	/**
	 * 
	 * @return
	 * @since 17.08.2018/0.2.0
	 */
	public int getState() {
		
		return alGetSourcei(this.id, AL_SOURCE_STATE);
	}

	/**
	 * 
	 * @return
	 * @since 17.08.2018/0.2.0
	 */
	public float getOffsetInSeconds() {
		
		return alGetSourcef(this.id, AL_SEC_OFFSET);
	}

	/**
	 * 
	 * @return
	 * @since 17.08.2018/0.2.0
	 */
	public float getOffsetInSamples() {
		
		return alGetSourcef(this.id, AL_SAMPLE_OFFSET);
	}

	/**
	 * 
	 * @return
	 * @since 17.08.2018/0.2.0
	 */
	public int getOffsetInBytes() {

		return alGetSourcei(this.id, AL_BYTE_OFFSET);
	}

	/**
	 * 
	 * @return
	 * @since 17.08.2018/0.2.0
	 */
	public ALBuffer getBuffer() {
		
		return this.buffer;
	}

	/**
	 * 
	 * @return
	 * @since 17.08.2018/0.2.0
	 */
	public int getType() {
		
		return alGetSourcei(this.id, AL_SOURCE_TYPE);
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

	@Override
	public Vector3f getRotation() {
		
		float[] x = new float[1];
		float[] y = new float[1];
		float[] z = new float[1];
		alGetListener3f(AL_DIRECTION, x, y, z);
		return new Vector3f(x[0], y[0], z[0]);
	}
}
