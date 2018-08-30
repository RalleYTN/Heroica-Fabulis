package de.ralleytn.games.heroicafabulis.engine.audio;

import javax.vecmath.Vector3f;

import static org.lwjgl.openal.AL11.*;

import de.ralleytn.games.heroicafabulis.engine.LWJGLObject;
import de.ralleytn.games.heroicafabulis.engine.Movable;

/**
 * Represents a source from which audio is emitted.
 * 3D audio can only be simulated with mono sounds.
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 29.08.2018/0.3.0
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
	 * Queues a buffer.
	 * @param buffer the buffer
	 * @since 26.08.2018/0.3.0
	 * @see <a href="https://www.openal.org/documentation/openal-1.1-specification.pdf">OpenAL 1.1 Specification</a>
	 */
	public void queueBuffer(ALBuffer buffer) {
		
		alSourceQueueBuffers(this.id, buffer.getID());
	}
	
	/**
	 * Queues multiple buffers.
	 * @param buffers the buffers
	 * @since 26.08.2018/0.3.0
	 * @see <a href="https://www.openal.org/documentation/openal-1.1-specification.pdf">OpenAL 1.1 Specification</a>
	 */
	public void queueBuffers(ALBuffer[] buffers) {
		
		int[] ids = new int[buffers.length];
		
		for(int index = 0; index < buffers.length; index++) {
			
			ids[index] = buffers[index].getID();
		}
		
		alSourceQueueBuffers(this.id, ids);
	}
	
	/**
	 * Unqueues all buffers.
	 * @since 26.08.2018/0.3.0
	 * @see <a href="https://www.openal.org/documentation/openal-1.1-specification.pdf">OpenAL 1.1 Specification</a>
	 */
	public void unqueueBuffers() {
		
		alSourceUnqueueBuffers(this.id);
	}
	
	/**
	 * Unqueues selected buffers-
	 * @param buffers the buffers
	 * @since 26.08.2018/0.3.0
	 * @see <a href="https://www.openal.org/documentation/openal-1.1-specification.pdf">OpenAL 1.1 Specification</a>
	 */
	public void unqueueBuffers(ALBuffer[] buffers) {
		
		int[] ids = new int[buffers.length];
		
		for(int index = 0; index < buffers.length; index++) {
			
			ids[index] = buffers[index].getID();
		}
		
		alSourceUnqueueBuffers(this.id, ids);
	}
	
	/**
	 * Plays the audio.
	 * @since 17.08.2018/0.2.0
	 * @see <a href="https://www.openal.org/documentation/openal-1.1-specification.pdf">OpenAL 1.1 Specification</a>
	 */
	public void play() {
		
		alSourcePlay(this.id);
	}
	
	/**
	 * Pauses the audio.
	 * @since 17.08.2018/0.2.0
	 * @see <a href="https://www.openal.org/documentation/openal-1.1-specification.pdf">OpenAL 1.1 Specification</a>
	 */
	public void pause() {
		
		alSourcePause(this.id);
	}

	/**
	 * Stops the audio.
	 * Basically pauses the audio and sets the position back to {@code 0}.
	 * @since 17.08.2018/0.2.0
	 * @see <a href="https://www.openal.org/documentation/openal-1.1-specification.pdf">OpenAL 1.1 Specification</a>
	 */
	public void stop() {
		
		alSourceStop(this.id);
	}

	/**
	 * Rewinds the audio.
	 * @since 17.08.2018/0.2.0
	 * @see <a href="https://www.openal.org/documentation/openal-1.1-specification.pdf">OpenAL 1.1 Specification</a>
	 */
	public void rewind() {
		
		alSourceRewind(this.id);
	}
	
	/**
	 * Sets the {@linkplain ALBuffer} with the audio data.
	 * @param buffer the {@linkplain ALBuffer}
	 * @since 17.08.2018/0.2.0
	 * @see <a href="https://www.openal.org/documentation/openal-1.1-specification.pdf">OpenAL 1.1 Specification</a>
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
	 * @see <a href="https://www.openal.org/documentation/openal-1.1-specification.pdf">OpenAL 1.1 Specification</a>
	 */
	public void setGain(float gain) {
		
		alSourcef(this.id, AL_GAIN, gain);
	}

	/**
	 * Sets the pitch.
	 * @param pitch the pitch.
	 * @since 17.08.2018/0.2.0
	 * @see <a href="https://www.openal.org/documentation/openal-1.1-specification.pdf">OpenAL 1.1 Specification</a>
	 */
	public void setPitch(float pitch) {
		
		alSourcef(this.id, AL_PITCH, pitch);
	}

	/**
	 * Sets the distance in which the sound can still be heard.
	 * @param maxDistance the distance
	 * @since 17.08.2018/0.2.0
	 * @see <a href="https://www.openal.org/documentation/openal-1.1-specification.pdf">OpenAL 1.1 Specification</a>
	 */
	public void setMaxDistance(float maxDistance) {
		
		alSourcef(this.id, AL_MAX_DISTANCE, maxDistance);
	}

	/**
	 * Sets the roll-of factor.
	 * @param rolloffFactor the roll-off factor
	 * @since 17.08.2018/0.2.0
	 * @see <a href="https://www.openal.org/documentation/openal-1.1-specification.pdf">OpenAL 1.1 Specification</a>
	 */
	public void setRolloffFactor(float rolloffFactor) {
		
		alSourcef(this.id, AL_ROLLOFF_FACTOR, rolloffFactor);
	}

	/**
	 * Sets the minimum volume.
	 * @param minGain the minimum volume
	 * @since 17.08.2018/0.2.0
	 * @see <a href="https://www.openal.org/documentation/openal-1.1-specification.pdf">OpenAL 1.1 Specification</a>
	 */
	public void setMinGain(float minGain) {
		
		alSourcef(this.id, AL_MIN_GAIN, minGain);
	}

	/**
	 * Sets the maximum volume.
	 * @param maxGain the maximum volume
	 * @since 17.08.2018/0.2.0
	 * @see <a href="https://www.openal.org/documentation/openal-1.1-specification.pdf">OpenAL 1.1 Specification</a>
	 */
	public void setMaxGain(float maxGain) {
		
		alSourcef(this.id, AL_MAX_GAIN, maxGain);
	}

	/**
	 * Sets the reference distance.
	 * @param referenceDistance the reference distance
	 * @since 17.08.2018/0.2.0
	 * @see <a href="https://www.openal.org/documentation/openal-1.1-specification.pdf">OpenAL 1.1 Specification</a>
	 */
	public void setReferenceDistance(float referenceDistance) {
		
		alSourcef(this.id, AL_REFERENCE_DISTANCE, referenceDistance);
	}

	/**
	 * Sets the cone outer gain.
	 * @param coneOuterGain the cone outer gain
	 * @since 17.08.2018/0.2.0
	 * @see <a href="https://www.openal.org/documentation/openal-1.1-specification.pdf">OpenAL 1.1 Specification</a>
	 */
	public void setConeOuterGain(float coneOuterGain) {
		
		alSourcef(this.id, AL_CONE_OUTER_GAIN, coneOuterGain);
	}

	/**
	 * Sets the cone inner angle.
	 * @param coneInnerAngle the cone inner angle
	 * @since 17.08.2018/0.2.0
	 * @see <a href="https://www.openal.org/documentation/openal-1.1-specification.pdf">OpenAL 1.1 Specification</a>
	 */
	public void setConeInnerAngle(float coneInnerAngle) {
		
		alSourcef(this.id, AL_CONE_INNER_ANGLE, coneInnerAngle);
	}

	/**
	 * Sets the cone outer angle.
	 * @param coneOuterAngle the cone outer angle
	 * @since 17.08.2018/0.2.0
	 * @see <a href="https://www.openal.org/documentation/openal-1.1-specification.pdf">OpenAL 1.1 Specification</a>
	 */
	public void setConeOuterAngle(float coneOuterAngle) {
		
		alSourcef(this.id, AL_CONE_OUTER_ANGLE, coneOuterAngle);
	}
	
	/**
	 * Sets if the audio should be relative to the listener or not.
	 * @param relativeToListener {@code true} = the audio is always relative to the listener, {@code false} = the audio has its own position
	 * @since 17.08.2018/0.2.0
	 * @see <a href="https://www.openal.org/documentation/openal-1.1-specification.pdf">OpenAL 1.1 Specification</a>
	 */
	public void setRelativeToListener(boolean relativeToListener) {
		
		alSourcei(this.id, AL_SOURCE_RELATIVE, relativeToListener ? AL_TRUE : AL_FALSE);
	}

	/**
	 * Sets the offset in seconds.
	 * @param offset the offset
	 * @since 17.08.2018/0.2.0
	 * @see <a href="https://www.openal.org/documentation/openal-1.1-specification.pdf">OpenAL 1.1 Specification</a>
	 */
	public void setOffsetInSeconds(float offset) {
		
		alSourcef(this.id, AL_SEC_OFFSET, offset);
	}

	/**
	 * Sets the offset in samples.
	 * @param offset the offset
	 * @since 17.08.2018/0.2.0
	 * @see <a href="https://www.openal.org/documentation/openal-1.1-specification.pdf">OpenAL 1.1 Specification</a>
	 */
	public void setOffsetInSamples(float offset) {
		
		alSourcef(this.id, AL_SAMPLE_OFFSET, offset);
	}

	/**
	 * Sets the offset in bytes.
	 * @param offset the offset
	 * @since 17.08.2018/0.2.0
	 * @see <a href="https://www.openal.org/documentation/openal-1.1-specification.pdf">OpenAL 1.1 Specification</a>
	 */
	public void setOffsetInBytes(int offset) {
		
		alSourcei(this.id, AL_BYTE_OFFSET, offset);
	}
	
	/**
	 * Sets the looping flag.
	 * @param looping value of the looping flag
	 * @since 17.08.2018/0.2.0
	 * @see <a href="https://www.openal.org/documentation/openal-1.1-specification.pdf">OpenAL 1.1 Specification</a>
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
	 * @return the volume
	 * @since 17.08.2018/0.2.0
	 * @see <a href="https://www.openal.org/documentation/openal-1.1-specification.pdf">OpenAL 1.1 Specification</a>
	 */
	public float getGain() {
		
		return alGetSourcef(this.id, AL_GAIN);
	}

	/**
	 * @return the pitch
	 * @since 17.08.2018/0.2.0
	 * @see <a href="https://www.openal.org/documentation/openal-1.1-specification.pdf">OpenAL 1.1 Specification</a>
	 */
	public float getPitch() {
		
		return alGetSourcef(this.id, AL_PITCH);
	}

	/**
	 * @return the maximum distance
	 * @since 17.08.2018/0.2.0
	 * @see <a href="https://www.openal.org/documentation/openal-1.1-specification.pdf">OpenAL 1.1 Specification</a>
	 */
	public float getMaxDistance() {
		
		return alGetSourcef(this.id, AL_MAX_DISTANCE);
	}

	/**
	 * @return the roll off factor
	 * @since 17.08.2018/0.2.0
	 * @see <a href="https://www.openal.org/documentation/openal-1.1-specification.pdf">OpenAL 1.1 Specification</a>
	 */
	public float getRolloffFactor() {
		
		return alGetSourcef(this.id, AL_ROLLOFF_FACTOR);
	}

	/**
	 * @return the minimum volume
	 * @since 17.08.2018/0.2.0
	 * @see <a href="https://www.openal.org/documentation/openal-1.1-specification.pdf">OpenAL 1.1 Specification</a>
	 */
	public float getMinGain() {
		
		return alGetSourcef(this.id, AL_MIN_GAIN);
	}
	
	/**
	 * @return the maximum volume
	 * @since 17.08.2018/0.2.0
	 * @see <a href="https://www.openal.org/documentation/openal-1.1-specification.pdf">OpenAL 1.1 Specification</a>
	 */
	public float getMaxGain() {
		
		return alGetSourcef(this.id, AL_MAX_GAIN);
	}

	/**
	 * @return the reference distance
	 * @since 17.08.2018/0.2.0
	 * @see <a href="https://www.openal.org/documentation/openal-1.1-specification.pdf">OpenAL 1.1 Specification</a>
	 */
	public float getReferenceDistance() {
		
		return alGetSourcef(this.id, AL_REFERENCE_DISTANCE);
	}

	/**
	 * @return the cone outer gain
	 * @since 17.08.2018/0.2.0
	 * @see <a href="https://www.openal.org/documentation/openal-1.1-specification.pdf">OpenAL 1.1 Specification</a>
	 */
	public float getConeOuterGain() {
		
		return alGetSourcef(this.id, AL_CONE_OUTER_GAIN);
	}

	/**
	 * @return the cone inner angle
	 * @since 17.08.2018/0.2.0
	 * @see <a href="https://www.openal.org/documentation/openal-1.1-specification.pdf">OpenAL 1.1 Specification</a>
	 */
	public float getConeInnerAngle() {
		
		return alGetSourcef(this.id, AL_CONE_INNER_ANGLE);
	}

	/**
	 * @return the cone outer angle
	 * @since 17.08.2018/0.2.0
	 * @see <a href="https://www.openal.org/documentation/openal-1.1-specification.pdf">OpenAL 1.1 Specification</a>
	 */
	public float getConeOuterAngle() {
		
		return alGetSourcef(this.id, AL_CONE_OUTER_ANGLE);
	}

	/**
	 * @return {@code true} if the audio is played relative to the listener, else {@code false}
	 * @since 17.08.2018/0.2.0
	 * @see <a href="https://www.openal.org/documentation/openal-1.1-specification.pdf">OpenAL 1.1 Specification</a>
	 */
	public boolean isRelativeToListener() {
		
		return alGetSourcei(this.id, AL_SOURCE_RELATIVE) == AL_TRUE;
	}

	/**
	 * @return {@code true} if the looping flag is set, otherwise {@code false}
	 * @since 17.08.2018/0.2.0
	 * @see <a href="https://www.openal.org/documentation/openal-1.1-specification.pdf">OpenAL 1.1 Specification</a>
	 */
	public boolean isLooping() {
		
		return alGetSourcei(this.id, AL_LOOPING) == AL_TRUE;
	}

	/**
	 * @return the state of the source
	 * @since 17.08.2018/0.2.0
	 * @see <a href="https://www.openal.org/documentation/openal-1.1-specification.pdf">OpenAL 1.1 Specification</a>
	 */
	public int getState() {
		
		return alGetSourcei(this.id, AL_SOURCE_STATE);
	}

	/**
	 * @return the audio offset in seconds
	 * @since 17.08.2018/0.2.0
	 * @see <a href="https://www.openal.org/documentation/openal-1.1-specification.pdf">OpenAL 1.1 Specification</a>
	 */
	public float getOffsetInSeconds() {
		
		return alGetSourcef(this.id, AL_SEC_OFFSET);
	}

	/**
	 * @return the audio offset in samples
	 * @since 17.08.2018/0.2.0
	 * @see <a href="https://www.openal.org/documentation/openal-1.1-specification.pdf">OpenAL 1.1 Specification</a>
	 */
	public float getOffsetInSamples() {
		
		return alGetSourcef(this.id, AL_SAMPLE_OFFSET);
	}

	/**
	 * @return the audio offset in bytes
	 * @since 17.08.2018/0.2.0
	 * @see <a href="https://www.openal.org/documentation/openal-1.1-specification.pdf">OpenAL 1.1 Specification</a>
	 */
	public int getOffsetInBytes() {

		return alGetSourcei(this.id, AL_BYTE_OFFSET);
	}

	/**
	 * @return the current buffer
	 * @since 17.08.2018/0.2.0
	 * @see <a href="https://www.openal.org/documentation/openal-1.1-specification.pdf">OpenAL 1.1 Specification</a>
	 */
	public ALBuffer getBuffer() {
		
		return this.buffer;
	}

	/**
	 * @return the source type
	 * @since 17.08.2018/0.2.0
	 * @see <a href="https://www.openal.org/documentation/openal-1.1-specification.pdf">OpenAL 1.1 Specification</a>
	 */
	public int getType() {
		
		return alGetSourcei(this.id, AL_SOURCE_TYPE);
	}
	
	/**
	 * @return the amount of buffers that were processed in the queue
	 * @since 26.08.2018/0.3.0
	 * @see <a href="https://www.openal.org/documentation/openal-1.1-specification.pdf">OpenAL 1.1 Specification</a>
	 */
	public int getProcessedBuffersCount() {
		
		return alGetSourcei(this.id, AL_BUFFERS_PROCESSED);
	}
	
	/**
	 * @return the velocity
	 * @since 17.08.2018/0.2.0
	 * @see <a href="https://www.openal.org/documentation/openal-1.1-specification.pdf">OpenAL 1.1 Specification</a>
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
